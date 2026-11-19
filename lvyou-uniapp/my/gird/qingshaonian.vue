<template>
	<view>
		<view>
			<view class="text-center" style="margin: 0rpx auto 60rpx;">
				<image src="/static/image/qun/qunbg.png" style="width: 100%;height: 382rpx;"></image>
				<view class="text-lg text-bold margin-top-sm" v-if="babyModel">青少年模式未开启</view>
				<view class="text-lg text-bold margin-top-sm" v-if="!babyModel">青少年模式已开启</view>
			</view>
			<view v-if="qsnMsg" class="box" v-html="qsnMsg"></view>

			<view class="btn" v-if="babyModel" @tap="qingshow = true">开启青少年模式 </view>
			<view class="btn" v-if="!babyModel" @tap="qingshow = true">关闭青少年模式 </view>


			<up-popup :show="qingshow" @close="qingshow = false"  mode="center" :round="24">
				<view class="text-center" style="padding: 48rpx 30rpx;width: 551rpx;">
					<view class="title">{{mimaMsg}}</view>
					<view class="margin-top-xl">
						<VcodeInput :sum="4" ref="VcodeI" @vcodeChange="vcodeChange" />
					</view>
					<view class="btn1" @tap="savePwd">确定</view>
				</view>
			</up-popup>


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
	import VcodeInput from '@/my/components/vcode-input/vcode-input.vue';
	const {
		proxy
	} = getCurrentInstance();

	const babyModel = ref(false);
	// 协议文本
	const qsnMsg = ref('');
	// 密码弹框
	const qingshow = ref(false);
	// 密码
	const keyPwd = ref('');
	// 弹框标题
	const mimaMsg = ref('设置密码');


	// 获取"青少年模式介绍"协议  919
	const getText = () => {
		proxy.$http.getT('/app/common/type/919').then(res => {
			if (res.code === 0) {
				if (res.data && res.data.value) {
					qsnMsg.value = res.data.value;
				}
			}
		});
	};

	const vcodeChange = (e) => {
		console.log(e,'Miami')
		keyPwd.value = e;
		if (keyPwd.value.length == 4) {
			savePwd();
		}
	};

	// 设置密码
	const savePwd = () => {
		
		if (keyPwd.value.length != 4) {
			uni.showToast({
				title: '请输入四位数正确密码！',
				icon: 'none'
			})
			return;
		}
		if (babyModel.value) {
			proxy.$http.postT('/app/user/setBabyPwd?babyPwd=' + keyPwd.value).then(res => {
				if (res.code == 0) {
					uni.showToast({
						title: '青少年模式已开启！',
						icon: 'none'
					})
					setTimeout(d => {
						getUserInfo();
						uni.reLaunch({
							url: '/my/gird/qingshaonian'
						})
					}, 2000);
				} else {
					uni.showToast({
						title: res.msg,
						icon: 'none'
					})
				}
				keyPwd.value = '';
			});
		} else {
			proxy.$http.postT('/app/user/closeBaby?babyPwd=' +keyPwd.value).then(res => {
				if (res.code == 0) {
					uni.showToast({
						title: '已关闭青少年模式！',
						icon: 'none'
					})
					setTimeout(d => {
						uni.switchTab({
							url: '/pages/index/index'
						})
						getUserInfo();
					}, 2000);
				} else {
					uni.showToast({
						title: res.msg,
						icon: 'none'
					})
				}
				keyPwd.value = '';
			});
		}
	};

	// 获取用户 信息
	const getUserInfo = () => {
		proxy.$http.get("/app/user/selectUserById").then(res => {
			if (res.code == 0) {
				keyPwd.value = '';
				if (!res.data.babyModel || res.data.babyModel == 2) {
					mimaMsg.value = '设置密码';
					babyModel.value = true;
				} else {
					mimaMsg.value = '输入密码';
					babyModel.value = false;
				}
			}
		});
	};

	onLoad(option => {
		getText()
		getUserInfo()
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

	.u-mode-center-box {
		border-radius: 28rpx !important;
	}

	.box {
		border-radius: 24rpx;
		margin: 40rpx 32rpx;
		padding: 30rpx 40rpx;
		color: #666666;
	}

	.btn {
		width: 686rpx;
		margin: 0 auto;
		height: 100rpx;
		background: #2FD7C8;
		border-radius: 50rpx;
		text-align: center;
		line-height: 100rpx;
		font-family: PingFang SC;
		font-weight: bold;
		font-size: 30rpx;
		color: #FFFFFF;
		position: fixed;
		bottom: 60rpx;
		left: 0;
		right: 0;
		z-index: 99;
	}

	.btn1 {
		width: 400rpx;
		height: 70rpx;
		background: #2FD7C8;
		border-radius: 35rpx;
		text-align: center;
		line-height: 70rpx;
		margin: 40rpx auto;
		color: #FFFFFF;
	}

	.title {
		font-size: 36rpx;
		font-weight: 800;
	}
</style>