<template>
	<view style="padding-bottom: 40rpx;">
		<!-- 系统消息 -->
		
		
		<view class="listTz flex align-center justify-center">
			<view class="listTz-box flex align-center" @click="goNav('/pages/msg/message')">
				<image src="/static/image/msg/msgindexmsg.png" mode=""></image>
				<view class="listTz-box-contont">
					<view class="listTz-box-contont-t">
						新的评论
					</view>
					<view style="width: 100%;" class="flex align-center justify-between">
						<view class="listTz-box-contont-s" v-if="commentLastContent">
							{{commentLastContent}}
						</view>
						<view class="listTz-box-contont-s" v-else>
							暂无评论消息
						</view>
						<view class="" v-if="commentUnreadCount>0">
							<up-badge max="99" :value="commentUnreadCount"></up-badge>
						</view>
					</view>
				</view>
			</view>
		</view>

		<!-- 官方公告 -->
		<view class="listTz flex align-center justify-center">
			<view class="listTz-box flex align-center" @click="goNav('/pages/msg/gonggao')">
				<image src="/static/image/msg/guanfang.png"
					mode=""></image>
				<view class="listTz-box-contont">
					<view class="listTz-box-contont-t">
						官方公告
					</view>
					<view style="width: 100%;" class="flex align-center justify-between">
						<view class="listTz-box-contont-s">{{systemNoticeLastContent?systemNoticeLastContent:'暂无消息'}} </view>
						<view class="" v-if="systemNoticeUnreadCount>0">
							<up-badge max="99" :value="systemNoticeUnreadCount"></up-badge>
						</view>
					</view>
				</view>
			</view>
		</view>

		<!-- 在线客服 -->
		<view class="listTz flex align-center justify-center">
			<view class="listTz-box flex align-center" @click="goChat" v-if="SelKeFu==3">
				<image src="/static/image/msg/zaixian.png" mode=""></image>
				<view class="listTz-box-contont">
					<view class="listTz-box-contont-t">
						在线客服
					</view>
					<view style="width: 100%;" class="flex align-center justify-between">
						<view class="listTz-box-contont-s">有问题请联系在线客服~ </view>
					</view>
				</view>
			</view>

			<view class="listTz-box" v-else>
				<button type="default" open-type="contact">
					<view class=" flex align-center">
						<image src="/static/image/msg/zaixian.png" mode=""></image>
						<view class="listTz-box-contont" style="text-align: left;">
							<view class="listTz-box-contont-t">
								在线客服
							</view>
							<view style="width: 100%;" class="flex align-center justify-between">
								<view class="listTz-box-contont-s">有问题请联系在线客服~ </view>
							</view>
						</view>
					</view>
				</button>
			</view>
		</view>

		<!-- 自定义导航栏 -->
		<myTabbar :value="3" />
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
		onPullDownRefresh,
		onReachBottom,
		onShow,
		onHide
	} from '@dcloudio/uni-app';
	const {
		proxy
	} = getCurrentInstance();
	// 引入底部 Tabbar 组件
	import myTabbar from '@/components/myTabbar/myTabbar.vue';
	//空内容
	import empty from '@/components/empty/empty.vue'
	//消息列表

	const SelKeFu = ref('');


	
	//唯独消息定时器
	const sysmegInter = ref(null);
	const commentLastContent = ref('') //评论消息最后一条消息
	const commentUnreadCount = ref(0) //评论消息未读数
	const systemNoticeLastContent = ref('') //系统公告最后一条消息
	const systemNoticeUnreadCount = ref(0) //系统公告未读数

	onLoad(option => {
		uni.hideTabBar();

	});
	onShow(() => {
		SelKeFu.value = uni.getStorageSync('SelKeFu');
		if (uni.getStorageSync('token')) {

			selectMessageByUserIdLimit1();
			sysmegInter.value = setInterval(() => {
				selectMessageByUserIdLimit1();
			}, 1000 * 5)
		}
	});
	//隐藏的时候清除定时器
	onHide(() => {
		clearInterval(sysmegInter.value);
		sysmegInter.value = null;
	});


	// 联系客服
	const goChat = () => {
		let that = this
		let SelKeFu = uni.getStorageSync('SelKeFu');
		if (SelKeFu == '1') {
			// #ifdef MP-WEIXIN
			try {
				wx.openCustomerServiceChat({
					extInfo: {
						url: uni.getStorageSync('SelKeFuLink')
					},
					corpId: uni.getStorageSync('SelKeFuAppId'),
					success(res) {},
					fail(res) {
						console.error(res)
					}
				})
			} catch (error) {
				console.error("catchcatch" + error)
				uni.showToast({
					title: '请更新至微信最新版本'
				});
			}
			// #endif
			// #ifndef MP-WEIXIN
			let url = uni.getStorageSync('SelKeFuLink');

			if (url.indexOf('/pages/') !== -1 || url.indexOf('/my/') !== -1) {
				uni.navigateTo({
					url
				});
			} else {
				//#ifndef H5
				uni.navigateTo({
					url: '/pages/index/webView?url=' + url
				});
				//#endif
				//#ifdef H5
				window.location.href = url;
				//#endif
			}
			// #endif
		} else if (SelKeFu == '2') {
			let token = uni.getStorageSync('token');
			if (token) {
				uni.makePhoneCall({
					phoneNumber: uni.getStorageSync('kefuPhone')
				});
			} else {
				goLoginInfo();
			}
		} else if (SelKeFu == '4') {
			let token = uni.getStorageSync('token');
			if (token) {
				uni.navigateTo({
					url: '/my/setting/customer'
				});
			} else {
				goLoginInfo();
			}
		}
	};
	// 跳转登录
	const goLoginInfo = () => {
		uni.navigateTo({
			url: '/pages/public/login'
		})
	};

	//获取系统消息通知
	const selectMessageByUserIdLimit1 = () => {
		proxy.$http.postT('/app/message/messageList', {
			userId: uni.getStorageSync('userId')
		}).then(res => {
			if (res.code == 0) {
				// if (res.data.list.length > 0) {
				// 	Object.assign(systemMsg, res.data.list[0])
				// }
				
				commentLastContent.value = res.data.commentLastContent //评论消息最后一条消息
				commentUnreadCount.value = res.data.commentUnreadCount //评论消息未读数
				systemNoticeLastContent.value = res.data.systemNoticeLastContent //系统公告最后一条消息
				systemNoticeUnreadCount.value = res.data.systemNoticeUnreadCount //系统公告未读数


			}
		})
	};

	const goNav = (url) => {
		uni.navigateTo({
			url: url
		});
	};
</script>

<style lang="scss">
	page {
		background: #FAFAFA;
	}

	button::after {
		border: none;
		background-color: none;
	}

	button {
		position: relative;
		display: block;
		margin-left: auto;
		margin-right: auto;
		padding-left: 0px;
		padding-right: 0px;
		box-sizing: border-box;
		text-decoration: none;
		line-height: 1.35;
		overflow: hidden;
		color: #FFFFFF;
		background-color: rgba(255, 255, 255, 0) !important;
		font-size: 28rpx;
		width: 100%;
		height: 100%;
	}

	::v-deep .u-swipe-action-item__content {
		border-radius: 24rpx;
	}

	::v-deep .u-swipe-action-item__right__button__wrapper {
		border-radius: 0 24rpx 24rpx 0 !important;
	}

	.listTz {
		width: 100%;
		height: auto;
		margin-top: 20rpx;

		.listTz-box {
			width: calc(686rpx - 60rpx);
			height: 100%;
			background-color: #FFFFFF;
			border-radius: 18rpx;

			padding: 30rpx;

			image {
				width: 80rpx;
				height: 80rpx;
				border-radius: 50%;
				margin-right: 20rpx;
			}

			.listTz-box-contont-t {
				color: #333333;
				font-size: 28rpx;
				font-weight: bold;
			}

			.listTz-box-contont {
				width: calc(100% - 108rpx);
			}

			.listTz-box-contont-s {
				width: 80%;
				color: #999999;
				font-size: 24rpx;
				margin-top: 6rpx;
				overflow: hidden;
				white-space: nowrap;
				text-overflow: ellipsis;
				-o-text-overflow: ellipsis;
			}
		}
	}
</style>