<template>
	<view style="padding-bottom: 140rpx;">
		<view class="contont ">
			<!-- 顶部群信息 -->
			<view class="info ">
				<view class="info-l">
					<view class="info-l-title">
						{{crowdInfo.name?crowdInfo.name:''}}
					</view>
					<view class="info-l-address">
						{{crowdInfo.province?crowdInfo.province:''}}-{{crowdInfo.city?crowdInfo.city:''}}
					</view>
					<view class="info-l-time flex">
						加群有效期：
						<view class="flex align-center">
							{{crowdInfo.qrStartTime?timeFormat(crowdInfo.qrStartTime, 'yyyy/mm/dd'):''}} —
							{{crowdInfo.expiredTime?timeFormat(crowdInfo.expiredTime, 'yyyy/mm/dd'):''}}

						</view>
					</view>
				</view>

				<view class="info-r flex justify-between flex-wrap" v-if="coverImg.length>=8">
					<image :src="item" v-for="(item,index) in coverImg" :key="index" mode="aspectFill">
					</image>
				</view>
				<view class="info-r" v-if="coverImg.length<=8">
					<image :src="qunlogo?qunlogo:'../../static/image/qun/qunlogo.png'" mode=""
						style="width: 140rpx;height: 140rpx;">
					</image>
				</view>
			</view>
			<!-- 数量统计 -->
			<view class="mathNum flex align-center justify-between">
				<view class="mathNum-item" style="margin-left: 28rpx;">
					<view class="mathNum-item-num">
						{{crowdInfo.joinNum?crowdInfo.joinNum:0}}人
					</view>
					<view class="mathNum-item-txt">
						已加入人数
					</view>
				</view>
				<view class="mathNum-item">
					<view class="mathNum-item-num">
						{{crowdInfo.typeName?crowdInfo.typeName:'暂无'}}
					</view>
					<view class="mathNum-item-txt">
						群聊类型
					</view>
				</view>
				<view class="mathNum-item" style="margin-right: 28rpx;">
					<view class="mathNum-item-num">
						{{crowdInfo.views?crowdInfo.views:0}}
					</view>
					<view class="mathNum-item-txt">
						浏览次数
					</view>
				</view>
			</view>
			<!-- 群简介 -->
			<view class="detail">
				<view class="detail-title">
					群简介
				</view>
				<view class="detail-txt" v-if="crowdInfo.description">
					<text>{{crowdInfo.description}}</text>
				</view>
				<view class="detail-txt" v-else>
					{{'暂无'}}
				</view>
			</view>
			<!-- 群主信息 -->
			<view @click="goUserInfo()" class="detailInfo ">
				<view class="detailInfo-l flex align-center">
					<view class="detailInfo-l-img">
						<image :src="crowdInfo.avatar?crowdInfo.avatar:'../../static/logos.png'" mode="aspectFill"></image>
					</view>
					<view class="detailInfo-l-txt">
						<view class="detailInfo-l-txt-name">
							{{crowdInfo.userName?crowdInfo.userName:'匿名用户'}}
						</view>
						<!-- <view class="detailInfo-l-txt-num">创建：{{crowdInfo.dayCount?crowdInfo.dayCount:0}}天</view> -->
					</view>
				</view>
				<u-icon name="arrow-right" color="#333333" size="22"></u-icon>
			</view>

			<!-- 邀请解锁 -->
			<view class="yaobox" v-if="!lockStatus&&applyCondition != 0&&yaonum>0">
				<view class="yaoleft">
					<view class="title">邀请好友</view>
					<view class="yaonum">邀请<text>满{{yaonum}}名</text>好友可免费解锁当前群</view>
				</view>
				<!-- #ifdef MP-WEIXIN -->
				<view class="">
					<button open-type="share" catchtap="share">
						<view class="yaobtn">去邀请</view>
					</button>
				</view>
				<!-- #endif -->
				<!-- #ifndef MP-WEIXIN -->
				<view class="yaobtn" @tap.stop="getShareImg()">去邀请</view>
				<!-- #endif -->
			</view>

			<!-- 群详情图片 -->
			<view class="infoImg">
				<view class="infoImg-title">群详情</view>
				<view class="infoImg-imgs flex flex-wrap" v-if="groupImgList.length>0">
					<image @click="privImgs(item.groupImg)" v-for="(item,index) in groupImgList" :key="index"
						:src="item" mode="widthFix" style="border-radius: 12rpx;margin-bottom: 10rpx;">
					</image>
				</view>
				<view v-else class="infoImg-imgs flex flex-wrap" style="color: #999999;">
					<image src="../../static/image/empty.png" style="width: 400rpx;margin: 0 auto;" mode="widthFix">
					</image>
					<view class="" style="width: 100%;text-align: center;">
						暂无详情
					</view>
				</view>

			</view>
		</view>

		<view class="botomBtn">
			<view class="botomBtn-box ">
				<!-- #ifndef MP-WEIXIN -->

				<view class="botomBtn-box-l" @tap.stop="getShareImg()">
					<image src="../static/groupChat/share.png" mode=""></image>
					<view class="botomBtn-box-l-txt">
						分享
					</view>

				</view>
				<!-- #endif -->
				<!-- #ifdef MP-WEIXIN -->
				<view class="">
					<button open-type="share" catchtap="share">
						<view class="botomBtn-box-l">
							<image src="../static/groupChat/share.png" mode=""></image>
							<view class="botomBtn-box-l-txt">
								分享
							</view>
						</view>
					</button>
				</view>
				<!-- #endif -->
				<view class="tabbtn">

					<block v-if="yaonum>0&&applyCondition==1&& !crowdInfo.isJoinGroup">
						<!-- #ifdef MP-WEIXIN -->
						<view class="" v-if="!lockStatus&&applyCondition != 0">
							<button open-type="share" catchtap="share">
								<view class="tanyao">
									<view class="text-center">
										<view class="tabnum">满{{yaonum}}名</view>
										<view class="tabtit">邀好友解锁</view>
									</view>
								</view>
							</button>
						</view>
						<!-- #endif -->
						<!-- #ifndef MP-WEIXIN -->
						<view class="tanyao" v-if="!lockStatus&&applyCondition != 0" @tap.stop="getShareImg()">
							<view class="text-center">
								<view class="tabnum">满{{yaonum}}名</view>
								<view class="tabtit">邀好友解锁</view>
							</view>

						</view>
						<!-- #endif -->
					</block>

					<view class="tanyao" v-if="crowdInfo.isJoinGroup&&!lockStatus" @click="mianfeiqun">
						<view class="text-center">
							<view class="tabnum">免费解锁</view>
						</view>
					</view>
					<!-- lockStatus false 未解锁 -->
					
					<view class="" v-if="!lockStatus">
						<!-- applyCondition  群聊是否免费 -->
						<view class="botomBtn-box-r flex align-center justify-center" v-if="applyCondition == 0"
							@click="selectQrCode()">
							免费解锁
						</view>
						<view class=" " v-else :class="yaonum>0?'tanqun':'botomBtn-box-r'" @click="payCrowd()">
							<view class="text-center">
								<view class="tabqprice">
									￥
									<text>{{crowdInfo.charge}}</text>
								</view>
								<view class="tabqtit">付费进群</view>
							</view>
						</view>
					</view>
					<view class="botomBtn-box-r flex align-center justify-center" v-else @click="showQrCode = true">
						查看群二维码
					</view>
				</view>

			</view>
		</view>


		<!-- 支付类型选择弹窗 -->
		<up-popup :show="showPayType" @close="showPayType = false" round="24" mode="bottom" closeable>
			<view class="payType">
				<view class="payType-title">请选择支付类型</view>
				<radio-group name="openWay" style="width: 100%;">
					<view @tap='selectWay(item)' class="payType-item" v-for="(item,index) in openList" :key="index">
						<view class="payType-item-l">
							<image :src="item.image" mode=""></image>
							{{item.text}}
						</view>
						<view class="payType-item-r">
							<radio color="#53D8A4" :checked="openWay === item.id ? true : false"
								style="transform:scale(0.8)" @tap='selectWay(item)' />
						</view>
					</view>
				</radio-group>

				<view class="margin-top text-sm flex align-center">

					<up-checkbox-group v-model="checked" @change="bindchecked">
						<up-checkbox name="agree" activeColor="#53D8A4" shape="circle">
						</up-checkbox>
					</up-checkbox-group>
					我已阅读并同意遵守<text style="color:#53D8A4 ;" @click.stop="goNav('/my/setting/qunpayxieyi')">《解锁群协议》</text>
				</view>

				<view @click="pay()" class="payType-btn">确认支付</view>
			</view>
		</up-popup>

		<!-- 群二维码弹窗 -->
		<up-popup :show="showQrCode" round="24" mode="center">
			<view class="qrCode">
				<view class="qrCodeBg">
					<image src="/my/static/groupChat/qrCodebg.png"></image>
				</view>
				<!-- #ifndef APP -->
				<view class="qrCode-txt">长按识别二维码添加</view>
				<!-- #endif -->
				<!-- #ifdef APP -->
				<view class="qrCode-txt">长按保存图片，微信识别二维码</view>
				<!-- #endif -->
				<view class="qrCode-img ">
					<swiper class="swiper-container" :autoplay="true" :interval="4000" :circular="true"
						:indicator-dots="false" indicator-active-color="#f2ad44" indicator-color="#cccccc">
						<block v-for="(item, index3) in groupQrcode" :key="index3">
							<swiper-item class="swiper-wrapper" @click="privImgs(item)" v-if="item">
								<image lazy-load="true" fade-show="true" :src="item"
									style="width: 100%; height:320rpx;border-radius: 24rpx;" mode="aspectFill">
								</image>
							</swiper-item>
						</block>
					</swiper>
				</view>
				<view class="qrCode-close ">
					<u-icon @click="showQrCode = false" name="close-circle" color="#000000" size="25"></u-icon>
				</view>
			</view>
		</up-popup>

		<!-- 二维码 -->
		<!-- #ifdef H5 -->
		<view class="qrcodeBox" v-if="shareH5Url">
			<up-qrcode :size="200" @result="resultQr" :val="shareH5Url"></up-qrcode>
		</view>

		<canvas canvas-id="myShareImg" id="myShareImg" :style="{width:+'px',height:shareImgHeight+'px'}"
			class="myShareImg"></canvas>
		<!-- #endif -->

		<up-popup :show="showShareImg" @close="closeShareImg" mode="center" :safeAreaInsetBottom="false">
			<view class="flex">
				<image :src="canvasImgSrc" :style="{width:+'px',height:shareImgHeight+'px'}" mode="">
				</image>
			</view>
		</up-popup>
	</view>
</template>

<script setup>
	import {
		ref,
		reactive,
		getCurrentInstance
	} from 'vue';
	import {
		onLoad,
		onShow,
		onPullDownRefresh,
		onReachBottom
	} from '@dcloudio/uni-app';
	import {
		timeFormat
	} from '@/uni_modules/uview-plus';
	const {
		proxy
	} = getCurrentInstance();
	import configdata from '../../common/config.js';
	const userId = ref('');
	const crowdInfo = ref({}); //群详情数据
	const qunlogo = ref(''); //默认群头像
	const id = ref(''); //群id
	const groupImgList = reactive([]); //群详情
	const coverImg = reactive([]); //群头像
	const groupQrcode = ref(''); //群二维码
	const lockStatus = ref(false); //是否解锁视频 false:未解锁 true:已解锁
	const applyCondition = ref(''); //0:免费 1：付费
	const yaonum = ref(0); //免费解锁群聊需要邀请人数
	const showPayType = ref(false); //支付弹框
	const openList = reactive([]); //支付类型
	const openWay = ref(''); //1 支付宝  2微信  3 余额
	const checked = ref(false); //群协议
	const orderNo = ref(); //订单号
	const showQrCode = ref(false); //群二维码弹框


	const canvasImgSrc = ref(''); ///canvas生成的图片
	const showShareImg = ref(false); ////是否显示海报弹窗
	const backgroundImage = ref(''); //海报背景图
	const shareImgHeight = ref(0); ////海报高度
	const shareImgWidth = ref(uni.upx2px(686)); //海报宽度
	const invitationCode = ref('');
	const userName = ref('');
	const avatar = ref('');
	//推广文案
	const tuiguang1 = ref('');
	const url = ref('');
	const qrImg = ref('');
	const shareH5Url = ref('');


	onLoad(option => {
		if (option.id) {
			id.value = option.id
			getInfo();
		}
		qunlogo.value = uni.getStorageSync('qunlogo');
		if (option.invitation) {
			uni.setStorageSync('inviterCode', option.invitation)
		}
		// #ifdef MP-WEIXIN
		if (option.scene) {
			const scene = decodeURIComponent(option.scene);
			uni.setStorageSync('inviterCode', scene.split(',')[0])
			id.value = scene.split(',')[1]
		}
		// #endif
		invitationCode.value = uni.getStorageSync('invitationCode');

		getBackImageList()
		userId.value = uni.getStorageSync('userId');
		if (userId.value) {
			getUserInfo();
		}
		// 免费解锁群聊需要邀请人数
		proxy.$http.postJson('/app/config/info', {
			key: 'biz_unlock_group_num'
		}).then(res => {
			if (res.code == 0) {
				if (res.data && res.data.value) {
					yaonum.value = res.data.value
				}
			}
		});

		let payType = []

		// #ifdef APP-PLUS
		payType = [{
			image: '../static/zhifubao.png',
			text: '支付宝支付',
			id: 1
		}, {
			image: '../static/icon_weixin.png',
			text: '微信支付',
			id: 2
		}];
		openWay.value = 1;
		// #endif

		// #ifdef MP-WEIXIN
		payType = [{
			image: '../static/icon_weixin.png',
			text: '微信支付',
			id: 2
		}];
		openWay.value = 2;
		// #endif

		// #ifdef H5
		let target = navigator.userAgent.toLowerCase();
		let isWeixin = (/micromessenger/.test(target)) ? true : false;
		if (isWeixin) {
			payType = [{
				image: '../static/icon_weixin.png',
				text: '微信支付',
				id: 2
			}];
			openWay.value = 2;
		} else {
			payType = [{
				image: '../static/zhifubao.png',
				text: '支付宝支付',
				id: 1
			}];
			openWay.value = 1;
		}
		// #endif
		payType.push({
			image: '../static/yue.png',
			text: '零钱支付',
			id: 3
		});
		openWay.value = 3;
		openList.splice(0, openList.length, ...payType)
	});
	onShow(() => {

	});
	//文案推广
	const getTuiguang1 = () => {
		// 邀请文案
		proxy.$http.postJson('/app/config/info', {
			key: 'biz_invitation_text'
		}).then(res => {
			if (res.code == 0) {
				if (res.data && res.data.value) {
					tuiguang1.value = res.data.value;
				}
			}
		});

		// #ifndef H5
		proxy.$http.postJson('/app/config/info', {
			key: 'sys_apk_url'
		}).then(res => {
			if (res.code == 0) {
				if (res.data && res.data.value) {
					url.value = res.data.value;
				}
			}
		});

		//#endif
		// #ifdef H5
		proxy.$http.postJson('/app/config/info', {
			key: 'sys_h5_domain'
		}).then(ress => {
			if (ress.code === 0) {
				if (ress.data && ress.data.value) {
					url.value = ress.data.value;
					shareH5Url.value = url.value + '/my/GroupChat/groupChatDetail?invitation=' + invitationCode
						.value + '&id=' + id.value
				} else {
					url.value = proxy.$publicYuMing();
					shareH5Url.value = url.value + '/my/GroupChat/groupChatDetail?invitation=' + invitationCode
						.value + '&id=' + id.value
				}
				// console.log(shareH5Url.value, url.value, 'shareH5UrlshareH5Url')
			}
		});
		//#endif
	};
	//获取用户信息
	const getUserInfo = () => {
		proxy.$http.getT('/app/base/user/info').then(res => {
			if (res.code == 0) {
				invitationCode.value = res.data.invitationCode
				userName.value = res.data.userName
				avatar.value = res.data.avatar
				uni.setStorageSync('avatar', res.data.avatar)
				getTuiguang1();
			}
		})
	};
	//购买入群资格
	const payCrowd = () => {
		if (uni.getStorageSync('token')) {
			showPayType.value = true
		} else {
			uni.navigateTo({
				url: '/pages/public/login'
			})
		}
	};
	// 跳转用户主页
	const goUserInfo = () => {
		uni.navigateTo({
			url: '/my/gird/zhuye?userId=' + crowdInfo.value.userId
		})
	};
	//获取详情
	const getInfo = () => {
		let userId = uni.getStorageSync('userId')
		let data = {
			id: id.value,
			userId: userId
		}
		proxy.$http.postJson('/app/groupInfo/info', data).then(res => {
			if (res.code == 0) {
				crowdInfo.value = res.data
				if (crowdInfo.value.qrCode) {
					groupQrcode.value = crowdInfo.value.qrCode.split(',')
				}
				lockStatus.value = crowdInfo.value.isJoinGroup
				console.log(lockStatus.value)
				applyCondition.value = crowdInfo.value.applyCondition
				if (crowdInfo.value.coverImg) {
					coverImg.push(crowdInfo.value.coverImg.split(','))
				}
				if (crowdInfo.value.remark) {
					groupImgList.splice(0, groupImgList.length, ...crowdInfo.value.remark.split(','))
				}

			} else {
				uni.showToast({
					title: res.msg,
					icon: 'none'
				})
			}
		})
	};
	//预览图片
	const privImgs = (photoImg) => {
		showQrCode.value = false
		let imgsArray = [];
		imgsArray[0] = photoImg;
		uni.previewImage({
			current: 0,
			urls: imgsArray
		});
	};
	//查看群二维码
	const selectQrCode = () => {
		if (uni.getStorageSync('token')) {
			//生成订单
			proxy.$http.getT('/app/groupInfo/joinGroup/' + id.value).then(res => {
				if (res.code == 0) {
					getInfo();
				} else {
					uni.hideLoading()
					uni.showToast({
						title: res.msg,
						icon: 'none'
					})
				}
			})
		} else {
			uni.navigateTo({
				url: '/pages/public/login'
			})
		}
	};
	// 万能跳转
	const goNav = (url) => {
		let userId = uni.getStorageSync('userId')
		if (!userId) {
			uni.navigateTo({
				url: '/pages/public/login'
			})
			return
		}
		uni.navigateTo({
			url
		})
	};
	// 同意解锁群协议
	const bindchecked = (e) => {
		console.log(e)
		checked.value = !checked.value
	};
	//选择支付类型
	const selectWay = (item) => {
		console.log(item)
		openWay.value = item.id
	};
	const pay = () => {
		if (!checked.value) {
			uni.showToast({
				title: '请阅读并同意协议',
				icon: 'none'
			})
			return
		}

		showPayType.value = false
		uni.showLoading({
			title: '支付中'
		})
		//生成订单

		if (openWay.value == 1) { //支付宝
			let data = {
				businessType: 2, //业务类型 1会员 2付费群组
				redirectUri: '/my/GroupChat/JoinGroupChat',
				amount: crowdInfo.value.charge,
				aliPayParam: {
					clientId: 'sys_h5_domain'
				},
				extensions: {
					groupId: id.value
				},
				userId: uni.getStorageSync('userId'),
			}
			proxy.$http.postJson('/app/base/alipay/wapPay', data).then(ret => {
				if (ret.code == 0) {
					// #ifdef APP
					isCheckPay(ret.code, 'alipay', ret.data);
					// #endif
					// #ifdef H5
					const div = document.createElement('div')
					div.innerHTML = ret.data //此处form就是后台返回接收到的数据
					document.body.appendChild(div)
					document.forms[0].submit()
					// #endif
				} else {
					uni.hideLoading()
					uni.showToast({
						title: '支付失败',
						icon: 'none'
					})
				}
			})
		} else if (openWay.value == 2) { //微信
			let tradeType = ''
			// #ifdef MP-WEIXIN
			tradeType = 'JSAPI'
			// #endif
			// #ifdef APP
			tradeType = 'APP'

			// #endif
			// #ifdef H5
			tradeType = 'JSAPI'
			// #endif
			let data = {
				businessType: 2, //业务类型 1会员 2付费群组
				redirectUri: '/my/GroupChat/JoinGroupChat',
				amount: crowdInfo.value.charge,
				wxPayParam: {
					tradeType: tradeType, //交易类型 JSAPI-公众号支付 NATIVE-原生扫码支付 APP-app支付
					clientId: 'sys_h5_domain'
				},
				extensions: {
					groupId: id.value
				},
				userId: uni.getStorageSync('userId'),

			}

			proxy.$http.postJson('/app/base/wxpay/pay', data).then(ret => {
				if (ret.code == 0) {
					// #ifdef APP
					isCheckPay(ret.code, 'wxpay', JSON.stringify(ret.data));
					// #endif

					// #ifdef H5
					callPay(ret.data);
					// #endif

					// #ifdef MP-WEIXIN
					uni.hideLoading()
					uni.requestPayment({
						provider: 'wxpay',
						timeStamp: ret.data.timeStamp,
						nonceStr: ret.data.nonceStr,
						package: ret.data.packageValue,
						signType: ret.data.signType,
						paySign: ret.data.paySign,
						success: function(suc) {
							uni.showToast({
								title: '支付成功',
								icon: 'success'
							})
							getInfo();
						},
						fail: function(err) {
							uni.hideLoading()
							uni.showToast({
								title: '支付失败',
								icon: 'none'
							})
						}
					});

					// #endif
				} else {
					uni.hideLoading()
					uni.showToast({
						title:ret.msg,
						icon: 'none'
					})
				}
			})
		} else if (openWay.value == 3) { //零钱
			let tradeType = ''
			// #ifdef MP-WEIXIN
			tradeType = 'NATIVE'
			// #endif
			// #ifdef APP
			tradeType = 'APP'
			// #endif
			// #ifdef H5
			tradeType = 'JSAPI'
			// #endif
			let data = {
				businessType: 2, //业务类型 1会员 2付费群组
				redirectUri: '/my/GroupChat/JoinGroupChat',
				amount: crowdInfo.value.charge,
				userId: uni.getStorageSync('userId'),
				extensions: {
					groupId: id.value
				},
			}
			proxy.$http.postJson('/app/base/local/pay', data).then(ret => {
				if (ret.code == 0) {
					uni.showToast({
						title: '支付成功',
						icon: 'success'
					})
					getInfo();
				} else {
					uni.hideLoading()
					uni.showToast({
						title:ret.msg,
						icon: 'none'
					})
				}
			})
		}

	};
	const callPay = (response) => {
		if (typeof WeixinJSBridge === "undefined") {
			if (document.addEventListener) {
				document.addEventListener('WeixinJSBridgeReady', onBridgeReady(response), false);
			} else if (document.attachEvent) {
				document.attachEvent('WeixinJSBridgeReady', onBridgeReady(response));
				document.attachEvent('onWeixinJSBridgeReady', onBridgeReady(response));
			}
		} else {
			onBridgeReady(response);
		}
	};
	const onBridgeReady = (response) => {
		if (!response.package) {
			return;
		}

		WeixinJSBridge.invoke(
			'getBrandWCPayRequest', {
				"appId": response.appid, //公众号名称，由商户传入
				"timeStamp": response.timestamp, //时间戳，自1970年以来的秒数
				"nonceStr": response.noncestr, //随机串
				"package": response.package,
				"signType": response.signType, //微信签名方式：
				"paySign": response.sign //微信签名
			},
			function(res) {
				console.log(res, '/*-/*-/*-')
				if (res.err_msg === "get_brand_wcpay_request:ok") {
					// 使用以上方式判断前端返回,微信团队郑重提示：
					//res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
					uni.hideLoading()
					uni.showLoading({
						title: '支付成功'
					});
					getInfo()
				} else {
					uni.hideLoading();
				}
				WeixinJSBridge.log(response.err_msg);
			}
		);
	};
	const isCheckPay = (status, name, order) => {
		if (status == 0) {
			setPayment(name, order);
		} else {
			uni.hideLoading();
			uni.showToast({
				title: '支付信息有误',
				icon: 'none'
			});
		}
	};
	const setPayment = (name, order) => {
		uni.requestPayment({
			provider: name,
			orderInfo: order, //微信、支付宝订单数据
			success: function(res) {
				uni.hideLoading();
				uni.showLoading({
					title: '支付成功'
				});
				getInfo();
			},
			fail: function(err) {
				console.log(err)
				uni.hideLoading();
			},
			complete() {
				uni.hideLoading();
			}
		});
	};

	//获取分享海报的图片
	const getBackImageList = () => {
		proxy.$http.postJson('/app/base/banner/page', {
			classify: 3, // 分类1banner图 2金刚区分类	 3海报背景图
			state: 1, //状态1正常 2隐藏
		}).then(async res => {
			if (res.code == 0 && res.data && res.data.records.length != 0) {
				backgroundImage.value = res.data.records[0].imageUrl;
				let _imgInfo = await getImageInfo(backgroundImage.value);
				shareImgHeight.value = (shareImgWidth.value * _imgInfo.height) / _imgInfo.width;
			}
		})
	};
	//关闭海报弹窗
	const closeShareImg = () => {
		showShareImg.value = false
	};
	// 分享
	const getShareImg = async () => {
		if (!uni.getStorageSync('token')) {
			uni.navigateTo({
				url: '/pages/public/login'
			})
			return
		}
		//如果已经生成了海报，则直接显示
		if (canvasImgSrc.value) {
			showShareImg.value = true
		} else { //没有则去用canvas生成
			await shareImg();
		}
	};

	//生成海报
	const shareImg = async () => {
		uni.showLoading({
			title: '生成中...'
		})
		let _imgInfo = await getImageInfo(backgroundImage.value);
		console.log(_imgInfo)
		//获取canvas的上下文对象
		const ctx = uni.createCanvasContext('myShareImg');
		//设置画布的高度（根据图片的宽高比）
		shareImgHeight.value = (shareImgWidth.value * _imgInfo.height) / _imgInfo.width;
		console.log(shareImgHeight.value)
		ctx.setFillStyle('#ffffff'); //canvas背景颜色
		ctx.fillRect(0, 0, shareImgWidth.value, shareImgHeight.value); //canvas画布大小
		//绘制背景图片
		ctx.drawImage(_imgInfo.path, 0, 0, shareImgWidth.value, shareImgHeight.value);
		// 在底部绘制个人信息与二维码
		//绘制头像
		const _avatarInfo = ref('');
		//头像宽高
		const avatarWidthOrHeight = ref(60);
		//边距
		const paddingNum = ref(20);
		//如果有头像
		if (avatar.value) {
			_avatarInfo.value = await getImageInfo(avatar.value)
		} else { //没有头像使用本地logo绘制
			_avatarInfo.value = await getImageInfo('/static/logos.png')
			console.log(_avatarInfo.value.path)
		}

		ctx.save();
		ctx.beginPath();
		//计算头像的中心坐标
		const avatarCenterX = paddingNum.value + avatarWidthOrHeight.value / 2;
		const avatarCenterY = shareImgHeight.value - paddingNum.value - avatarWidthOrHeight.value / 2;
		//绘制圆形头像
		ctx.arc(avatarCenterX, avatarCenterY, avatarWidthOrHeight.value / 2, 0, 2 * Math.PI);
		ctx.clip();
		//绘制头像
		ctx.drawImage(_avatarInfo.value.path, paddingNum.value, shareImgHeight.value - paddingNum.value -
			avatarWidthOrHeight.value, avatarWidthOrHeight.value,
			avatarWidthOrHeight.value);
		ctx.restore();
		//绘制用户昵称
		ctx.setFontSize(18);
		ctx.setFillStyle('#333333');
		ctx.fillText(userName.value, paddingNum.value + paddingNum.value / 2 + avatarWidthOrHeight.value,
			shareImgHeight.value - (
				avatarWidthOrHeight.value / 2) - paddingNum.value - paddingNum.value / 2);
		//绘制用户邀请码
		ctx.fillText('ID:' + invitationCode.value, paddingNum.value + paddingNum.value / 2 + avatarWidthOrHeight
			.value, shareImgHeight
			.value - avatarWidthOrHeight.value / 2);

		//绘制二维码
		ctx.drawImage(qrImg.value, shareImgWidth.value - paddingNum.value - avatarWidthOrHeight.value,
			shareImgHeight.value - paddingNum.value - avatarWidthOrHeight.value, avatarWidthOrHeight.value,
			avatarWidthOrHeight.value);
		console.log(qrImg.value)
		ctx.draw(false, () => {
			console.log('绘制完成')
			uni.hideLoading()
			getCanvasNewImage();
		});
	};
	//把canvas画布转为图片
	const getCanvasNewImage = () => {
		uni.canvasToTempFilePath({
			canvasId: 'myShareImg',
			quality: 1,
			complete: res => {
				// console.log(res.tempFilePath)
				canvasImgSrc.value = res.tempFilePath;
				showShareImg.value = true;
			}
		});
	};
	//异步获取图片信息
	const getImageInfo = async (imgSrc) => {
		return new Promise((resolve, errs) => {
			uni.getImageInfo({
				src: imgSrc,
				success: function(image) {
					resolve(image);
				},
				fail(err) {
					errs(err);
					uni.showToast({
						title: '海报生成失败'
					})
					uni.hideLoading()
				}
			});
		});
	};
	//生成二维码回掉的图片（base64）
	const resultQr = (e) => {
		qrImg.value = e;
	};
	onPullDownRefresh(() => {

	});
	onReachBottom(() => {

	});
</script>

<style lang="scss">
	button::after {
		border: none;
		background-color: none;
	}

	button {
		display: flex;
		align-items: center;
		position: relative;
		display: block;
		margin-left: auto;
		margin-right: auto;
		padding-left: 0px;
		padding-right: 0px;
		box-sizing: border-box;
		text-decoration: none;
		line-height: 30rpx;
		overflow: hidden;
		color: #FFFFFF;
		background-color: rgba(255, 255, 255, 0) !important;
		font-size: 28rpx;
		width: 100%;
		height: 100%;
		color: #21FD91;
	}

	.contont {
		padding: 30rpx;
	}

	.info {
		width: 100%;
		height: auto;
		padding-bottom: 30rpx;
		border-bottom: 1px solid #F2F2F2;
		display: flex;
		align-items: center;
		justify-content: space-between;

		.info-r {
			width: 130rpx;
			height: 130rpx;
			background-color: #f5f5f5;
			align-content: start;

			image {
				width: 42rpx;
				height: 42rpx;
				margin-bottom: 2rpx;
			}
		}

		.info-l {
			width: calc(100% - 150rpx);
			height: 130rpx;
			display: flex;
			align-content: space-between;
			flex-direction: column;

			.info-l-title {
				width: 100%;
				font-family: PingFang SC;
				font-weight: 800;
				font-size: 38rpx;
				color: #333333;
				overflow: hidden;
				white-space: nowrap;
				text-overflow: ellipsis;
				-o-text-overflow: ellipsis;
			}

			.info-l-address {
				font-family: PingFang SC;
				font-weight: 500;
				font-size: 24rpx;
				color: #00BC4F;
			}

			.info-l-time {
				width: 100%;
				color: #999999;
				font-size: 24rpx;

				view {
					color: #00BC4F;
				}
			}
		}
	}

	.mathNum {
		width: 100%;
		margin-top: 30rpx;
		padding-bottom: 30rpx;
		border-bottom: 1px solid #F2F2F2;

		.mathNum-item {
			text-align: center;
		}

		.mathNum-item-num {
			font-family: Source Han Sans CN;
			font-weight: bold;
			font-size: 28rpx;
			color: #1C1C1C;
		}

		.mathNum-item-txt {
			font-family: PingFang SC;
			font-weight: 500;
			font-size: 24rpx;
			color: #999999;
		}
	}

	// 群简介
	.detail {
		margin-top: 38rpx;
		padding-bottom: 30rpx;
		border-bottom: 1px solid #F2F2F2;

		.detail-title {
			color: #333333;
			font-size: 28rpx;
			font-weight: bold;
		}

		.detail-txt {
			color: #999999;
			font-size: 24rpx;
			margin-top: 10rpx;
		}
	}

	// 群主信息
	.detailInfo {
		margin-top: 30rpx;
		width: 100%;
		padding-bottom: 30rpx;
		border-bottom: 1px solid #F2F2F2;
		display: flex;
		align-items: center;
		justify-content: space-between;

		.detailInfo-l-img {
			margin-right: 20rpx;

			image {
				width: 80rpx;
				height: 80rpx;
				border-radius: 50%;
			}
		}

		.detailInfo-l-txt-name {
			color: #333333;
			font-size: 28rpx;
			font-weight: bold;
		}

		.detailInfo-l-txt-num {
			color: #999999;
			font-size: 22rpx;
		}
	}

	// 邀请解锁
	.yaobox {
		background: #daf7f3;
		border-radius: 18rpx;
		padding: 22rpx 29rpx;
		display: flex;
		align-items: center;
		justify-content: space-between;
		margin-top: 30rpx;

		.yaoleft {
			.title {
				font-family: YouSheBiaoTiHei;
				font-weight: 400;
				font-size: 44rpx;
				color: #2FD7C8;
			}

			.yaonum {
				font-family: PingFang SC;
				font-weight: 500;
				font-size: 26rpx;
				color: #333333;

				text {
					color: #2FD7C8;
				}
			}
		}

		.yaobtn {
			width: 120rpx;
			height: 52rpx;
			background: #2FD7C8;
			border-radius: 26rpx;
			font-family: PingFang SC;
			font-weight: 500;
			font-size: 24rpx;
			color: #FFFFFF;
			display: flex;
			align-items: center;
			justify-content: center;
		}
	}

	// 群详情
	.infoImg {
		margin-top: 30rpx;

		.infoImg-title {
			width: 100%;
			color: #333333;
			font-size: 28rpx;
			font-weight: bold;
		}

		.infoImg-imgs {
			width: 100%;
			margin-top: 20rpx;

			image {
				width: 100%;
			}
		}
	}

	// 底部操作栏
	.botomBtn {
		width: 100%;
		height: 140rpx;
		position: fixed;
		background-color: #ffffff;
		bottom: 0;
		left: 0;
		right: 0;
		display: flex;
		align-items: center;
		justify-content: space-between;

		.botomBtn-box {
			width: 686rpx;
			height: 90rpx;
			margin: 0 auto;
			display: flex;
			align-items: center;
			justify-content: space-between;
		}

		.botomBtn-box-l {
			text-align: center;

			image {
				width: 36rpx;
				height: 36rpx;
			}

			.botomBtn-box-l-txt {
				color: #666666;
				font-size: 24rpx;
			}
		}

		.tabbtn {
			display: flex;
			align-items: center;

			.tanyao {
				width: 300rpx;
				height: 90rpx;
				background: #ABEC4F;
				border-radius: 45rpx 0 0 45rpx;
				display: flex;
				align-items: center;
				justify-content: center;

				.tabnum {
					font-family: PingFang SC;
					font-weight: 800;
					font-size: 30rpx;
					color: #FFFFFF;
				}

				.tabtit {
					font-family: PingFang SC;
					font-weight: bold;
					font-size: 24rpx;
					color: #FFFFFF;
				}
			}

			.tanqun {
				width: 300rpx;
				height: 90rpx;
				background: #53D8A4;
				border-radius: 0rpx 45rpx 45rpx 0rpx;
				display: flex;
				align-items: center;
				justify-content: center;

				.tabqprice {
					font-family: PingFang SC;
					font-weight: bold;
					font-size: 28rpx;
					color: #FFFFFF;

					text {
						font-size: 38rpx;
					}
				}

				.tabqtit {
					font-family: PingFang SC;
					font-weight: bold;
					font-size: 24rpx;
					color: #FFFFFF;
				}
			}
		}

		.botomBtn-box-r {
			width: 600rpx;
			height: 90rpx;
			background: linear-gradient(121deg, #53D8A4, #ABEC4F);
			border-radius: 16rpx;
			color: #ffffff;
			font-size: 28rpx;

			text {
				font-size: 38rpx;
				margin-right: 4rpx;
			}
		}
	}


	// 支付弹框
	.payType {
		height: auto;
		background-color: #ffffff;
		padding: 30rpx;
		border-radius: 30rpx;

		.payType-title {
			width: 100%;
			text-align: center;
			font-size: 30rpx;
			font-weight: bold;
			margin-bottom: 40rpx;
		}

		.payType-item {
			width: 100%;
			margin-bottom: 50rpx;
			display: flex;
			align-items: center;
			justify-content: space-between;
		}

		.payType-item-l {
			display: flex;
			align-items: center;

			image {
				width: 56rpx;
				height: 56rpx;
				border-radius: 50%;
				margin-right: 20rpx;
			}
		}

		.payType-btn {
			width: 100%;
			height: 88rpx;
			border-radius: 44rpx;
			background: linear-gradient(121deg, #53D8A4, #ABEC4F);
			color: #ffffff;
			margin: 30rpx auto;
			font-weight: bold;
			font-size: 30rpx;
			display: flex;
			align-items: center;
			justify-content: center;
		}
	}


	// 群二维码弹窗
	.qrCode {
		width: 580rpx;
		height: 744rpx;
		border-radius: 24rpx;
		position: relative;

		.qrCodeBg {
			width: 580rpx;
			height: 744rpx;

			image {
				width: 100%;
				height: 100%;
			}
		}

		.qrCode-title {
			width: 100%;
			margin-top: 60rpx;

			image {
				width: 320rpx;
			}
		}

		.qrCode-txt {
			width: 100%;
			text-align: center;
			font-family: PingFang SC;
			font-weight: bold;
			font-size: 26rpx;
			color: #03471F;

			position: absolute;
			top: 110rpx;
		}

		.qrCode-img {
			width: 340rpx;
			height: 340rpx;
			border-radius: 34rpx;
			background: #FFFFFF;
			margin: 0 auto;
			position: absolute;
			top: 220rpx;
			left: 0;
			right: 0;
			display: flex;
			align-items: center;
			justify-content: center;

			.swiper-container {
				width: 320rpx;
				height: 320rpx;
				border-radius: 20rpx;
			}
		}

		.qrCode-close {
			width: 100%;
			margin-top: 40rpx;
			display: flex;
			align-items: center;
			justify-content: center;
			position: absolute;
			bottom: 30rpx;
		}
	}

	.qrcodeBox {
		position: fixed;
		left: -1000px;
	}

	.myShareImg {
		position: fixed;
		left: -1000px;
	}
</style>