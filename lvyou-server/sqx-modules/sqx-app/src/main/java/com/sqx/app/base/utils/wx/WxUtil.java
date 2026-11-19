package com.sqx.app.base.utils.wx;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson2.JSONObject;
import com.sqx.app.base.utils.http.OkHttpUtil;
import com.sqx.common.core.exception.BizException;
import com.sqx.common.redis.utils.RedisUtil;
import com.sqx.domain.db.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author chengming
 * @since 2025/8/5
 */
@Component
@Slf4j
public class WxUtil {

    public static final String appIdKey = "biz_js_appid";
    public static final String appSecretKey = "biz_js_secret";

    @Resource
    private ISysConfigService iSysConfigService;

    /**
     * 微信小程序登录
     *
     * @param appId   appId
     * @param secret  secret
     * @param js_code code
     */
    public WxJsLoginRes wxJsLogin(String appId, String secret, String js_code) {
        String api = "https://api.weixin.qq.com/sns/jscode2session";

        Map<String, String> params = new HashMap<>();
        params.put("appid", appId);
        params.put("secret", secret);
        params.put("js_code", js_code);
        params.put("grant_type", "authorization_code");

        String resp = OkHttpUtil.doGet(api, params, null);
        WxJsLoginRes wxJsLoginRes = JSONObject.parseObject(resp, WxJsLoginRes.class);
        if (Objects.nonNull(wxJsLoginRes.getErrcode()) && 0 != wxJsLoginRes.getErrcode()) {
            log.error("微信小程序登录返回结果: {}", wxJsLoginRes);
            throw new BizException("登录失败");
        }

        return wxJsLoginRes;
    }

    /**
     * 解密手机号
     *
     * @param decryptData 加密手机号（微信返回）
     * @param key         session_key
     * @param iv          iv（微信返回）
     */
    public String decryptS5(String decryptData, String key, String iv) {
        try {
            byte[] encData = Base64.decode(decryptData);
            byte[] ivs = Base64.decode(iv);
            byte[] keys = Base64.decode(key);
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivs);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(keys, "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            return new String(cipher.doFinal(encData));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("解密手机号失败");
        }
    }

    /**
     * 该接口用于将code换取用户手机号
     * 说明，每个code只能使用一次，code的有效期为5min
     *
     * <a href="https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/user-info/phone-number/getPhoneNumber.html">参考文档</a>
     *
     * @param code   code
     * @param openId openId
     * @return
     */
    public WxUserPhone getUserPhoneNumber(String code, String openId) {
        String api = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=";

        String accessToken = getStableAccessToken();
        api = api + accessToken;

        Map<String, String> body = new HashMap<>();
        body.put("openid", openId);
        body.put("code", code);
        String resp = OkHttpUtil.doPost(api, JSONObject.toJSONString(body));
        log.info("【获取用户手机号】微信返回值: {}", resp);
        JSONObject respJson = JSONObject.parseObject(resp);
        Integer errcode = respJson.getInteger("errcode");
        if (Objects.isNull(errcode) || 0 != errcode) {
            log.error("【获取用户手机号】失败, 错误信息: {}", respJson.getString("errmsg"));
            throw new BizException("获取手机号失败");
        }

        JSONObject phoneInfo = respJson.getJSONObject("phone_info");

        WxUserPhone wxUserPhone = new WxUserPhone();
        wxUserPhone.setPhoneNumber(phoneInfo.getString("phoneNumber"));
        wxUserPhone.setPurePhoneNumber(phoneInfo.getString("purePhoneNumber"));
        wxUserPhone.setCountryCode(phoneInfo.getString("countryCode"));

        return wxUserPhone;
    }

    public String getStableAccessToken() {
        String cacheKey = "WX::ACCESS_TOKEN";
        if (RedisUtil.hasKey(cacheKey)) {
            return RedisUtil.getCacheObject(cacheKey);
        }

        String appId = iSysConfigService.getConfigValue(appIdKey);
        String secret = iSysConfigService.getConfigValue(appSecretKey);

        String api = "https://api.weixin.qq.com/cgi-bin/stable_token";

        Map<String, String> body = new HashMap<>();
        body.put("appid", appId);
        body.put("secret", secret);
        body.put("grant_type", "client_credential");

        String resp = OkHttpUtil.doPost(api, JSONObject.toJSONString(body));
        log.info("【获取微信access_token】微信返回值: {}", resp);

        JSONObject respJson = JSONObject.parseObject(resp);
        String accessToken = respJson.getString("access_token");
        if (Objects.isNull(accessToken)) {
            throw new BizException("【获取微信access_token】失败");
        }

        Integer expiresIn = respJson.getInteger("expires_in");
        RedisUtil.setCacheObject(cacheKey, accessToken, Duration.ofSeconds(expiresIn));

        return accessToken;
    }
}
