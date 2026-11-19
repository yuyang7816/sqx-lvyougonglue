<script setup>
	import {
		getCurrentInstance
	} from 'vue';
	const {
		proxy
	} = getCurrentInstance();
	import {
		useMainStore
	} from '@/store'
	import {
		onLaunch,
		onShow,
		onHide
	} from '@dcloudio/uni-app';
	const store = useMainStore()
	onLaunch(() => {
		// #ifdef MP-WEIXIN
		//是否开启小程序自动登录	957
		proxy.$http.postJson('/app/config/info', {
			key: 'biz_enable_mini_login'
		}).then(rep => {
			// proxy.$http.get('/app/common/type/957').then(rep => {
			if (rep.code == 0 && rep.data) {
				if (rep.data.value == '是') {
					//没有登录的时候开始自动登录
					if (!uni.getStorageSync('token')) {
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
											uni.setStorageSync('invitationCode', res.data.userEntity.invitationCode)
											uni.setStorageSync('userId', res.data.userEntity.userId)
											uni.setStorageSync('openId', res.data.userEntity.openId)
										}

									}
								})

							}
						});
					}
				}
			}
		});
		// #endif



		let userId = uni.getStorageSync('userId')
		let setin = setInterval(d => { //定时器，定时去调取聊天未读消息
			let token = uni.getStorageSync('token')
			if (token) {
				proxy.$http.postJson('/app/message/messageList', {
					userId: userId
				}).then(res => {
					if (res.code === 0) {
						let commentUnreadCount = res.data.commentUnreadCount
						let systemNoticeUnreadCount = res.data.systemNoticeUnreadCount

						uni.setStorageSync('commentUnreadCount', commentUnreadCount)
						uni.setStorageSync('systemNoticeUnreadCount', systemNoticeUnreadCount)

						let num = commentUnreadCount + systemNoticeUnreadCount

						// that.$store.dispatch('setParamAction', 0);
						if (num == 0) {
							store.tagNum = 0
							// uni.removeTabBarBadge({
							// 	index: 2
							// })
							return;
						}
						if (store.tagNum != num) {
							store.tagNum = num
						}

						// uni.setTabBarBadge({
						// 	index: 2,
						// 	text: num + ""
						// })
					}
				});
			} else {
				store.tagNum = 0
				// uni.removeTabBarBadge({
				// 	index: 2
				// })
				clearInterval(setin)
			}
		}, 5000);


		// 客服类型   1 企业客服 2电话联系 3小程序客服 4 二维码客服
		proxy.$http.postJson('/app/config/info', {
			key: 'biz_customer_type'
		}).then(res => {
			if (res.code == 0) {
				if (res.data && res.data.value) {
					uni.setStorageSync('SelKeFu', res.data.value)
				}
			}
		});


		//企业微信客服链接
		proxy.$http.postJson('/app/config/info', {
			key: 'biz_customer_link'
		}).then(res => {
			if (res.code == 0) {
				if (res.data && res.data.value) {
					uni.setStorageSync('SelKeFuLink', res.data.value)
				}
			}
		});

		//企业微信客服appid
		proxy.$http.postJson('/app/config/info', {
			key: 'biz_customer_appid'
		}).then(res => {
			if (res.code == 0) {
				if (res.data && res.data.value) {
					uni.setStorageSync('SelKeFuAppId', res.data.value)
				}
			}
		});

		//客服电话
		proxy.$http.postJson('/app/config/info', {
			key: 'biz_customer_phone'
		}).then(res => {
			if (res.code == 0) {
				if (res.data && res.data.value) {
					uni.setStorageSync('kefuPhone', res.data.value)
				}
			}
		});


		//  默认群头像	1022
		proxy.$http.postJson('/app/config/info', {
			key: 'biz_default_group_avatar'
		}).then(res => {
			if (res.code == 0) {
				if (res.data && res.data.value) {
					uni.setStorageSync('qunlogo', res.data.value)
				}
			}
		});

		//关键词过滤
		proxy.$http.postJson('/app/config/info', {
			key: 'sys_keyword'
		}).then(res => {
			if (res.code == 0) {
				if (res.data && res.data.value) {
					uni.setStorageSync('chatSearchKeys', res.data.value)
				}
			}
		});

		// #ifdef APP-PLUS
		// let sysPhone = 1;
		// if (plus.os.name.toLowerCase() === 'android') {
		// 	sysPhone = 1;
		// } else {
		// 	sysPhone = 2;
		// }
		// let clientid = plus.push.getClientInfo().clientid;
		// console.log(clientid, 'clientidclientid')
		// let userId = this.$queue.getData('userId');
		// if (userId) {
		// 	proxy.$http.postT('/app/user/updateClientId?clientId=' + clientid + '&userId=' + userId +
		// 		'&sysPhone=' + sysPhone).then(
		// 		res => {});
		// }

		//#endif

	});
	onShow(() => {


		console.log('onShow1')
	});
	onHide(() => {
		console.log('onHide')
	});
</script>

<style lang="scss">
	/*每个页面公共css */
	//uview-plus公共样式
	@import "@/uni_modules/uview-plus/index.scss";
	//自定义公共样式
	@import "@/static/css/main.css";

	page {
		background-color: #FFFFFF;
	}
</style>