package com.sqx.app.lygl.follow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sqx.app.lygl.follow.db.mapper.TravelFollowMapper;
import com.sqx.app.lygl.follow.domain.bo.FollowPageBo;
import com.sqx.app.lygl.follow.domain.bo.FollowSaveBo;
import com.sqx.app.lygl.follow.domain.vo.FollowVo;
import com.sqx.app.lygl.follow.entity.TravelFollow;
import com.sqx.app.lygl.follow.service.TravelFollowService;
import com.sqx.common.core.domain.Result;
import com.sqx.common.mybatis.utils.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author Administrator
* @description 针对表【travel_follow(用户关注)】的数据库操作Service实现
* @createDate 2024-10-12 15:12:55
*/
@Service
public class TravelFollowServiceImpl extends ServiceImpl<TravelFollowMapper, TravelFollow>
    implements TravelFollowService {

    @Resource
    private TravelFollowMapper followMapper;

    @Override
    public Result<?> saveInfo(FollowSaveBo followSaveBo) {
        if (checkFollow(followSaveBo.getUserId(), followSaveBo.getTargetUserId())) {
            // 取消关注
            this.remove(new LambdaQueryWrapper<TravelFollow>()
                    .eq(TravelFollow::getUserId, followSaveBo.getUserId())
                    .eq(TravelFollow::getTargetUserId, followSaveBo.getTargetUserId())
            );
        } else {
            TravelFollow follow = new TravelFollow();
            BeanUtils.copyProperties(followSaveBo, follow);

            this.save(follow);
        }

        return Result.success();
    }

    private boolean checkFollow(Long userId, Long targetUserId) {
        LambdaQueryWrapper<TravelFollow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(TravelFollow::getUserId, userId)
                .eq(TravelFollow::getTargetUserId, targetUserId);

        return followMapper.exists(queryWrapper);
    }

    @Override
    public Result<?> pageInfo(FollowPageBo followPageBo) {
        Page<TravelFollow> initPage = PageUtil.initPage(followPageBo.getPage(), followPageBo.getLimit());

        IPage<FollowVo> followVoIPage = followMapper.pageInfo(initPage, followPageBo);
        return Result.success(followVoIPage);
    }
}




