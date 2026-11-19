<template>
	<view class="container">
		<!-- 自定义顶部导航栏 -->
		<up-navbar title="" :placeholder="true" :bgColor="bgColor" leftIconColor="#FFFFFF" :autoBack="true"
			@rightClick="rightClick">
		</up-navbar>

		<view class="bgBot">
			<image src="/static/image/qun/qunbg.png" style="width: 100%;height: 100vh;"></image>
		</view>


		<view class="wrapper">
			<image style="width: 393rpx;height: 106upx;margin-top: 280upx;border-radius:20upx"
				src="/static/logos.png" mode="widthFix"></image>
			<view style="font-size: 32upx;margin-top: 30upx;" v-html="title"></view>


			<!-- #ifdef MP-WEIXIN -->
			<button class='confirm-btn margin-xxxl' bindtap="getUserProfile" @tap="wxGetUserInfokJ" v-if="!isUserPhone">
				手机号快捷登录
			</button>

			<view v-else class="margin-xxxl">
				<button v-if="!check" class='confirm-btn' bindtap="getUserProfile" @tap="wxPhone">
					手机号快捷登录
				</button>
				<button v-else class="confirm-btn" open-type="getPhoneNumber" @getphonenumber="getPhoneNumber">
					手机号快捷登录
				</button>
			</view>
			<!-- #endif -->


			<!-- #ifndef MP-WEIXIN -->
			<!-- #ifdef APP -->
			<button class='confirm-btn' @click="login_phone" v-if="AppPhone">本机号码一键登录</button>

			<button v-if="weixinLogin" class="confirm-btn margin-xxxl" @click="weixinLo">微信登录</button>
			<button v-if="!weixinLogin" class='confirm-btn margin-xxxl' @click="register">账号登录</button>
			<!-- #endif -->

			<!-- #ifndef APP -->
			<button v-if="weixinLogin" class="confirm-btn margin-xxxl" @click="weixinLo">微信登录</button>
			<button v-if="!weixinLogin" class='confirm-btn margin-xxxl' @click="register">账号登录</button>
			<!-- #endif -->
			<!-- #endif -->

			<!-- 协议 -->
			<view class="loginXy flex align-center justify-center">
				<view class="loginXy-box flex align-center justify-center">
					<up-checkbox activeColor="#01C3C5" shape="circle" label="" name="agree" usedAlone
						v-model:checked="check">
					</up-checkbox>
					<view @click="check = !check" class="flex align-center">
						登录即代表同意
						<text @click.stop.prevent="goNav('/my/setting/xieyi')">《用户服务协议》</text>
						及
						<text @click.stop.prevent="goNav('/my/setting/mimi')">《隐私政策》</text>
					</view>
				</view>
			</view>
		</view>

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
	const {
		proxy
	} = getCurrentInstance();
	// 用户 userId
	const userId = ref('');
	// 顶部导航栏背景色
	const bgColor = ref('transparent');
	// 顶部副标题
	const title = ref('真实有趣的社交新选择');
	//是否选择协议
	const check = ref(false);
	const isUserPhone = ref(true);
	const sendDataList = ref({});
	const phone = ref('');
	const flag = ref('');

	// 账号登录
	const register = () => {
		if (!check.value) {
			uni.showToast({
				title: '请阅读并同意《用户服务协议》与《隐私政策》',
				icon: 'none'
			});
			return
		};
		uni.navigateTo({
			url: '/pages/public/loginphone'
		});
	};
	onLoad(option => {
		userId.value = uni.getStorageSync('userId')


		// #ifdef MP-WEIXIN
		uni.login({
			provider: 'weixin',
			success: function(loginRes) {
				console.log(loginRes, 'code')
				let data = {
					code: loginRes.code
				}
				proxy.$http.get('/app/base/auth/wxLogin', data).then(res => {
					if (res.code == 0 && res.data) {
						uni.setStorageSync('openId', res.data.openid)
						uni.setStorageSync('unionId', res.data.unionid)
						uni.setStorageSync('sessionkey', res.data.sessionKey)
						let inviterCode = '';
						if (uni.getStorageSync('inviterCode')) {
							invitationCode = uni.getStorageSync(
								'inviterCode')
						}
						let sendData = {
							openId: uni.getStorageSync('openId'),
							unionId: uni.getStorageSync('unionId'),
							userName: '微信用户',
							avatar: '',
							sex: '', //性别
							inviterCode: inviterCode //别人登录进来携带你的邀请码
						};
						sendDataList.value = sendData;
						if (res.data.bindPhone) {
							isUserPhone.value = false
						} else {
							isUserPhone.value = true
						}
					}
				})

			}
		});
		// #endif

	});
	onShow(() => {

	});
	const goNav = (url)=>{
		uni.navigateTo({
			url
		})
	};
	// 协议提示
	const wxPhone = () => {
		if (!check.value) {
			uni.showToast({
				title: '请阅读并同意《用户服务协议》与《隐私政策》',
				icon: 'none'
			});
		}
	};
	// 快捷登录
	const wxGetUserInfokJ = () => {
		if (!check.value) {
			uni.showToast({
				title: '请阅读并同意《用户服务协议》与《隐私政策》',
				icon: 'none'
			});
			return
		};
		uni.login({
			provider: 'weixin',
			success: function(loginRes) {
				console.log(loginRes, 'code')

				let data = {
					code: loginRes.code
				}
				proxy.$http.get('/app/base/auth/wxLogin', data).then(res => {
					if (res.code == 0 && res.data) {

						uni.setStorageSync('openId', res.data.openid)
						uni.setStorageSync('unionId', res.data.unionid)
						uni.setStorageSync('flag', res.data.bindPhone)
						uni.setStorageSync('sessionkey', res.data.sessionKey)
						// console.log(res.data.bindPhone, 'flagflagflag')
						if (res.data.bindPhone) {
							uni.setStorageSync('token', res.data.loginVO.token)

							uni.setStorageSync('userName', res.data.userEntity.userName)
							uni.setStorageSync('avatar', res.data.userEntity.avatar)
							uni.setStorageSync('phone', res.data.userEntity.phone)
							uni.setStorageSync('invitationCode', res.data.userEntity
								.invitationCode)
							uni.setStorageSync('userId', res.data.userEntity.userId)
							uni.setStorageSync('openId', res.data.userEntity.openId)
							rightClick();
						} else {
							let invitationCode = '';
							if (uni.getStorageSync('inviterCode')) {
								invitationCode = uni.getStorageSync(
									'inviterCode')
							}
							let sendData = {
								openId: uni.getStorageSync('openId'),
								unionId: uni.getStorageSync('unionId'),
								userName: '游客',
								avatar: '/static/logos.png',
								sex: '', //性别
								inviterCode: invitationCode, //别人登录进来携带你的邀请码
							};
							getWeixinInfo(sendData);
						}

					}
				})

			}
		});
	};
	//小程序微信登录后获取手机号
	const getPhoneNumber = (e) => {
		if (e.detail.errMsg == 'getPhoneNumber:fail user deny') {
			console.log('用户拒绝提供手机号');
		} else {
			console.log('用户同意提供手机号');
			if (e.detail.iv) {
				setPhoneByInsert(e.detail.encryptedData, e.detail.iv);
			}

		}
	};
	//小程序微信登录后获取手机号
	const setPhoneByInsert = (decryptData, iv) => {
		// console.log(decryptData, iv, '565656565656')
		let data = {
			decryptData: decryptData,
			key: uni.getStorageSync('sessionkey'),
			iv: iv
		};
		proxy.$http.postJson('/app/base/auth/decryptPhone', data).then(res => {
			if (res.code == 0) {
				phone.value = res.data.phoneNumber;
				getWeixinInfo(sendDataList.value);
			} else {
				uni.showToast({
					title: res.msg,
					icon: 'none',
					duration: 2000
				});
			}
		})
	};
	//获取个人信息
	const getWeixinInfo = (sendData) => {
		if (!sendData) {
			let sendData = sendDataList.value
		}
		uni.showLoading({
			title: '登录中...'
		});
		let postData = {
			openId: sendData.openId ? sendData.openId : uni.getStorageSync('openId'), //小程序openId
			// unionId: sendData.unionId ? sendData.unionId : uni.getStorageSync('unionId'), //unionId
			userName: sendData.userName, //微信名称
			avatar: '', //头像
			sex: sendData.sex, //性别
			phone: phone.value,
			superiorInvitationCode: sendData.inviterCode ? sendData.inviterCode : uni.getStorageSync('inviterCode')
		};
		proxy.$http.postJson('/app/base/auth/wxJsRegister', postData).then(res => {
			uni.hideLoading();
			if (res.code == 0) {
				uni.setStorageSync('token', res.data.token)
				uni.setStorageSync('userName', res.data.user.userName)
				uni.setStorageSync('avatar', res.data.user.avatar)
				uni.setStorageSync('phone', res.data.user.phone)
				uni.setStorageSync('invitationCode', res.data.user.invitationCode)
				uni.setStorageSync('sex', res.data.user.sex)
				uni.setStorageSync('userId', res.data.user.userId)
				uni.setStorageSync('openId', res.data.user.openId)

				rightClick();
			} else {
				uni.showModal({
					showCancel: false,
					title: '登录失败',
					content: res.msg,
				});
			}
		})
	};
	// 返回上一页
	const rightClick = () => {
		var pages = getCurrentPages();
		if (pages.length > 1) {
			uni.navigateBack()
		} else {
			uni.switchTab({
				url: '/pages/index/index'
			})
		}
	};
	onPullDownRefresh(() => {

	});
	onReachBottom(() => {

	});
</script>

<style lang="scss">
	.container {
		padding-bottom: 0;
		padding-bottom: constant(safe-area-inset-bottom);
		/* 兼容 IOS<11.2 */
		padding-bottom: env(safe-area-inset-bottom);
		/* 兼容 IOS>=11.2 */
		background: transparent;
		position: relative;
		width: 100%;
		height: 100vh;
		overflow: hidden;
		color: #FFF;

		.bgBot {
			width: 100%;
			position: fixed;
			top: 0;
			left: 0;
			right: 0;
			z-index: 9;
		}

		// 小程序
		.mp_wxBox {
			position: absolute;
			top: 180px;
			left: 0;
			right: 0;
			z-index: 90;
			padding-bottom: 20px;
			text-align: center;

			.headers {
				margin: 35% auto 20rpx;
				text-align: center;
				border-radius: 60rpx;
				width: 650rpx;
				height: 106upx;

				image {
					width: 393rpx;
					height: 116upx;
				}
			}

			.content {
				font-size: 32rpx;
				text-align: center;
				letter-spacing: 2px;
				margin-top: 20upx;
			}

			text {
				display: block;
				color: #FFFFFF;

			}

			.bottom {
				line-height: 100upx;
				border-radius: 80upx;
				margin: 260px 50rpx 30rpx 50rpx;
				height: 100upx;
				font-size: 35rpx;
			}
		}

		// H5 样式
		.wrapper {
			position: absolute;
			/* #ifdef H5 */
			top: 0;
			/* #endif */
			/* #ifdef MP-WEIXIN */
			top: 150px;
			/* #endif */
			left: 0;
			right: 0;
			z-index: 9;
			padding-bottom: 20px;
			text-align: center;
		}

	}


	.confirm-btn {
		width: 630rpx;
		height: 100rpx;
		line-height: 100rpx;
		border-radius: 30px;
		font-size: 32rpx;
		background: #2FD7C8;
		color: #FFFFFF;
	}

	.margin-xxxl {
		margin-top: 500rpx;
	}

	.loginXy {
		width: 100%;
		margin-top: 26rpx;

		.loginXy-box {
			width: 646rpx;
			font-size: 24rpx;
			color: #FFFFFF;

			text {
				color: #2FD7C8;
			}
		}
	}
</style>