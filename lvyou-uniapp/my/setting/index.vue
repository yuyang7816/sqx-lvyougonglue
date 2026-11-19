<template>
	<view class="padding-lr">
		<view class="flex  padding-tb" @click="goNav('/my/setting/feedback')" v-if="XCXIsSelect!='否'">
			<view class="flex-sub text-df" style="line-height: 50upx;">意见反馈</view>
			<image src="../../static/image/my/right.png" style="line-height: 50upx;width: 18rpx;height: 22rpx;">
			</image>
		</view>

		<view class="flex  padding-tb" @click="goNav('/my/setting/feedbackIndex')">
			<view class="flex-sub text-df" style="line-height: 50upx;">帮助中心</view>
			<image src="../../static/image/my/right.png" style="line-height: 50upx;width: 18rpx;height: 22rpx;">
			</image>
		</view>
		<!-- <view class="flex  padding-tb" @click="goNav('/my/gird/qingshaonian')">
			<view class="flex-sub text-df" style="line-height: 50upx;">青少年模式</view>
			<image src="../../static/image/my/right.png" style="line-height: 50upx;width: 18rpx;height: 22rpx;">
			</image>
		</view> -->

		<!-- <view class="flex  padding-tb">
			<view class="flex-sub text-df" style="line-height: 50upx;">消息通知</view>
			<up-switch v-model="checked" @change="change" activeColor="#00BC4F"></up-switch>
		</view> -->
		<!-- #ifdef APP-PLUS -->
		<!-- <view class="flex  padding-tb">
			<view class="flex-sub text-df" style="line-height: 50upx;">个性化推荐</view>
			<up-switch v-model="checkedgx" @change="changegx" activeColor="#00BC4F"></up-switch>
		</view> -->
		<!-- #endif -->


		<view class="flex  padding-tb" @click="goNav('/my/setting/about')">
			<view class="flex-sub text-df" style="line-height: 50upx;">关于我们</view>
			<image src="../../static/image/my/right.png" style="line-height: 50upx;width: 18rpx;height: 22rpx;">
			</image>
		</view>

		<view class="flex  padding-tb" @click="goNav('/my/setting/xieyi')">
			<view class="flex-sub text-df" style="line-height: 50upx;">用户协议</view>
			<image src="../../static/image/my/right.png" style="line-height: 50upx;width: 18rpx;height: 22rpx;">
			</image>
		</view>

		<view class="flex  padding-tb" @click="goNav('/my/setting/mimi')">
			<view class="flex-sub text-df" style="line-height: 50upx;">隐私政策</view>
			<image src="../../static/image/my/right.png" style="line-height: 50upx;width: 18rpx;height:22rpx;"></image>
		</view>

		<view class="flex  padding-tb" @click="goNav('/my/setting/userSz')">
			<view class="flex-sub text-df" style="line-height: 50upx;">平台用户行为守则</view>
			<image src="../../static/image/my/right.png" style="line-height: 50upx;width: 18rpx;height:22rpx;"></image>
		</view>

		<view class="flex  padding-tb" @click="goNav('/my/setting/qunpayxieyi')">
			<view class="flex-sub text-df" style="line-height: 50upx;">解锁群协议</view>
			<image src="../../static/image/my/right.png" style="line-height: 50upx;width: 18rpx;height:22rpx;"></image>
		</view>

		<view v-if="userId" class="flex  padding-tb" @click="goNav('/my/setting/zhuxiao')">
			<view class="flex-sub text-df" style="line-height: 50upx;">注销账号</view>
			<image src="../../static/image/my/right.png" style="line-height: 50upx;width: 18rpx;height: 22rpx;">
			</image>
		</view>
		<view class="btn" @click="goOut" v-if="userId">退出登录</view>
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
	// 引入底部 Tabbar 组件
	const userId = ref('');
	// 小程序上架开关
	const XCXIsSelect = ref('是');
	const checked = ref(true);
	// 个性化推荐
	const checkedgx = ref(true);
	// 消息通知
	const change=(status)=> {
		proxy.$http.post('/app/user/updateSendMsg', {
			isSendMsg: status ? 1 : 2
		}).then(res => {
			if (res.code == 0) {
	
			}
		});
	};
	// 个性化推荐
	const changegx=(status)=> {
		console.log(status)
		if (status) {
			uni.setStorageSync('gexing', 1);
		} else {
			uni.setStorageSync('gexing', 2);
		}
	};
	// 跳转中心
	const goNav=(e) =>{
		if (e == "/my/detail/goreport" || e == "/my/setting/zhuxiao") {
			if (!uni.getStorageSync('userId')) {
				uni.navigateTo({
					url: '/pages/public/login'
				})
			} else {
				uni.navigateTo({
					url: e
				})
			}
		} else {
			uni.navigateTo({
				url: e
			})
		}
	};
	// 退出登录
	const goOut=()=> {
		uni.showModal({
			title: '提示',
			content: '确定退出登录吗？',
			success: function(res) {
				if (res.confirm) {
					console.log('用户点击确定');
					uni.removeStorageSync('userName')
					uni.removeStorageSync('avatar')
					uni.removeStorageSync('userId')
					uni.removeStorageSync('token')
					uni.removeStorageSync('phone')
					uni.removeStorageSync('zhiFuBaoName')
					uni.removeStorageSync('zhiFuBao')
					uni.removeStorageSync('invitationCode')
					uni.removeStorageSync('unionId')
					uni.removeStorageSync('openId')
					uni.removeStorageSync('isVIP')
					// uni.clearStorageSync()
					uni.showToast({
						title: '退出成功！',
						icon: 'none'
					})
					setTimeout(function() {
						uni.navigateBack()
					}, 1000)
				} else if (res.cancel) {
					console.log('用户点击取消');
				}
			}
		})
	};
	onLoad(option => {
		userId.value = uni.getStorageSync('userId')
		XCXIsSelect.value =  uni.getStorageSync('XCXIsSelect')

	});
	onShow(() => {

	});
	onPullDownRefresh(() => {

	});
	onReachBottom(() => {

	});
</script>

<style lang="scss">
	page {
		background: #FFFFFF;
	}
	.btn {
		width: 100%;
		height: 88upx;
		border-radius: 14upx;
		text-align: center;
		line-height: 80upx;
		margin-top: 40upx;
		font-size: 34upx;
		background: linear-gradient(90deg, #2FC959 0%, #62DF9D 47%);
		color: #FFFFFF;
		
	}
</style>