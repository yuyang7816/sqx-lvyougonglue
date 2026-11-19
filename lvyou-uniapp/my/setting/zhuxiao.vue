<template>
	<view>
		<view style="width: 100%;text-align: center;margin: 20upx 0upx;">
			<view style="font-size: 28upx;color: #333333;">很抱歉，我们在这里见面</view>
			<view style="margin-top: 20upx;font-size: 24upx;color: #B2B2B2;">如果您是误操作，请取消申请</view>
		</view>

		<view style="margin: 0upx 10upx;background: #FFFFFF;padding: 20upx 30upx;border-radius: 10upx;">
			<view style="color: #333333;">注销提示：</view>
			<view style="color: #333333;margin-top: 15upx;font-size: 24upx;">若你是二次申请注销，则注销申请会直接通过</view>
			<view style="color: #333333;margin-top: 20upx;font-size: 24upx;">一旦账号注销成功</view>
			<view class="title_item">
				<view class="item_yuan"></view>
				<view class="item_title">您的账号将无法登陆与使用</view>
			</view>
			<view class="title_item">
				<view class="item_yuan"></view>
				<view class="item_title">您的账号信息将会永久清除且无法恢复</view>
			</view>
			<view class="title_item">
				<view class="item_yuan"></view>
				<view class="item_title">您的收益将被永久清空且无法恢复</view>
			</view>
			<view class="title_item">
				<view class="item_yuan"></view>
				<view class="item_title">您的账号所关联的订单将无法查询与找回</view>
			</view>
			<view class="title_item">
				<view class="item_yuan"></view>
				<view class="item_title">您的旅游攻略将无法进行下去</view>
			</view>
			<view class="title_item">
				<view class="item_yuan"></view>
				<view class="item_title">您在旅游攻略的账号信息将会永久清除且无法恢复</view>
			</view>
			<view class="title_item">
				<view class="item_yuan"></view>
				<view class="item_title" style="color: #BD1D14;">您所绑定的手机号码3个月内将无法再次注册旅游攻略</view>
			</view>
		</view>


		<view style="margin-top: 100upx;display: flex;padding: 20upx;">
			<view @tap="send()"
				style="flex: 1;background: #B2B2B2;color: #FFFFFF;height: 80upx;line-height: 80upx;border-radius: 50upx;text-align: center;">
				继续注销</view>
			<view @click="back()"
				style="margin-left: 10upx;flex: 1;background: linear-gradient(121deg, #53D8A4, #ABEC4F);color: #FFFFFF;height: 80upx;line-height: 80upx;border-radius: 50upx;text-align: center;">
				暂不注销</view>
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


	// 暂不注销 返回
	const back = () => {
		uni.navigateBack();
	};
	
	// 注销
	const send = () => {
		let that = this;
		let userId = uni.getStorageSync('userId');
		let userName = uni.getStorageSync('userName') ? uni.getStorageSync('userName') : '';
		uni.showModal({
			title: '温馨提示',
			content: '注销后您的账号信息将会永久清除且无法恢复，继续注销将在三日内清除完毕，确认继续注销吗？',
			showCancel: true,
			cancelText: '暂不注销',
			confirmText: '继续注销',
			success: res => {
				if (res.confirm) {
					uni.showLoading({
						title:'注销中...'
					})
					proxy.$http.postJson('/app/cancelAccount/commit', {
							title: userName ? userName : '注销账号',
							content: '我要注销账号！',
							userName: userName,
							userId: userId
						})
						.then(res => {
							if (res.code === 0) {
								uni.showToast({
									title: '申请成功！'
								});
								uni.removeStorageSync("token");
								uni.removeStorageSync("userId");
								setTimeout(function() {
									uni.switchTab({
										url: '/pages/index/index'
									})
								}, 1000);
							} else {
								uni.hideLoading();
								uni.showModal({
									showCancel: false,
									title: '注销失败',
									content: res.msg
								});
							}
						});
				}
			}
		});
	};
	onLoad(option => {
		
	});
	onShow(() => {

	});
	onPullDownRefresh(() => {

	});
	onReachBottom(() => {

	});
</script>



<style>
	page {
		background: #f1f1f1;
	}

	.title_item {
		display: flex;
		align-items: center;
		margin-top: 15upx;
	}

	.item_yuan {
		width: 10upx;
		height: 10upx;
		border-radius: 50upx;
		background: #999999;
	}

	.item_title {
		margin-left: 10upx;
		font-size: 24upx;
		color: #B2B2B2;
	}
</style>