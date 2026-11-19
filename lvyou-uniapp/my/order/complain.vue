<template>
	<view>
		<!-- #ifdef MP-WEIXIN -->
		<view class="padding-lr">此投诉为本小程序自有投诉渠道，非微信官方投诉渠道</view>
		<!-- #endif -->
		<view class="margin padding" style="background: #FFFFFF;border-radius: 15rpx;">
			<view class="feedback-title">投诉原因</view>
			<view class="feedback-body">
				<up-textarea v-model="sendDate.content" placeholder="请描述事情经过，并提供相关举证" border="none" height="120"></up-textarea>
			</view>
		</view>
		<view class="margin padding-lr padding-tb-sm" style="background: #FFFFFF;border-radius: 15rpx;">
			<view class=" ">投诉图片</view>
			<view class="margin-top-sm">
				<view class="flex flex-wrap justify-between ">
					<view v-for="(item,index) in imageList" :key="index" class="flex align-center justify-center radius"
						style="background: #F5F5F5;width: 150rpx;height: 150rpx;margin-bottom: 12rpx;position: relative;">
						<image :src="item" style="width: 150rpx;height: 150rpx;border-radius: 8rpx;" mode="">
						</image>
						<view style="position: absolute;top: -10rpx;right: -10rpx;">
							<u-icon @click="dleteImg(index)" name="close-circle" color="red" size="20"></u-icon>
						</view>
					</view>
					<view @click="uploudImg" v-if="imageList.length<6" class="flex align-center justify-center radius"
						style="background: #F5F5F5;width: 150rpx;height: 150rpx;margin-bottom: 12rpx;">
						<view class="text-center">
							<image src="../static/photo.png" style="width: 54rpx;height: 47rpx;"></image>
							<view style="font-size: 24rpx;color: #333333;">添加图片</view>
						</view>
					</view>
					<view class="flex align-center justify-center"
						style="background: #F5F5F5;width: 150rpx;height: 0rpx;">
					</view>
					<view class="flex align-center justify-center"
						style="background: #F5F5F5;width: 150rpx;height: 0rpx;">
					</view>

				</view>
			</view>
		</view>
		<button class="feedback-submit" @tap="send">提交投诉</button>
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
	import config from '@/common/config.js';
	const {
		proxy
	} = getCurrentInstance();

	const sendDate = reactive({
		content: ''
	});
	const imageList = reactive([]);
	const platform = ref();
	const platformId = ref();
	const byUserId = ref();

	//根据下标删除数组元素
	const dleteImg = (index) => {
		imageList.splice(index, 1)
	};
	//上传图片
	const uploudImg = () => {
		let count = 6 - imageList.length
		uni.chooseImage({
			count: count,
			sourceType: ['album', 'camera'],
			success: res => {
				for (let i = 0; i < res.tempFilePaths.length; i++) {
					uni.showLoading({
						title: '上传中'
					});
					uni.uploadFile({ // 上传接口
						url: config.APIHOST + '/file/upload',
						filePath: res.tempFilePaths[i],
						name: 'file',
						success: (uploadFileRes) => {
							uni.hideLoading();
							let url = JSON.parse(uploadFileRes.data)
							if (url.code == 0) {
								imageList.push(url.data.path)
							} else {
								uni.showToast({
									title: url.msg,
									icon: 'none'
								})
							}

						}
					});
				}
			}
		})
	};

	const send = () => {
		//发送反馈
		let images = '';
		let userId = uni.getStorageSync('userId');
		if (!userId) {
			uni.navigateTo({
				url: '/pages/public/login'
			});
			return
		}
		if (!sendDate.content) {
			uni.showToast({
				icon: 'none',
				title: '请输入投诉内容'
			});
			return;
		}
		if (imageList.length == 0) {
			uni.hideLoading();
			uni.showToast({
				icon: 'none',
				title: '请添加图片!'
			});
			return;
			return;
		}
		uni.showLoading({
			title: '加载中...'
		})
		proxy.$http.postJson('/app/complaint/save', {
			instruction: sendDate.content,
			imgUrl: imageList.toString(),
			userId: userId,
			// byUserId: byUserId.value,
			targetType: platform.value, ////1用户 2群聊
			targetId: platformId.value //目标id
		}).then(res => {
			if (res.code === 0) {
				uni.showToast({
					title:'投诉成功，等待平台处理！',
					icon:'none'
				})
				setTimeout(function() {
					uni.navigateBack();
				}, 2000);
			} else {
				uni.hideLoading();
				uni.showModal({
					showCancel: false,
					title: '投诉失败',
					content: res.msg
				});
			}
		});
	};

	onLoad(option => {
		if (option.byUserId) {
			byUserId.value = option.byUserId;
		}
		if (option.platform) {
			platform.value = option.platform;
		}
		if (option.platformId) {
			platformId.value = option.platformId;
		}

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
		background-color: #F5F5F5;
	}

	::v-deep .u-textarea {
		background-color: #F5F5F5 !important;
	}

	/*问题反馈*/
	.feedback-title {
		color: #000;
		font-size: 28upx;
	}

	.feedback-body {
		font-size: 32upx;
		border-radius: 16upx;
		background: #F5F5F5;
		color: #000;
		margin-top: 20rpx;
	}

	.feedback-textare {
		height: 200upx;
		font-size: 30upx;
		line-height: 50upx;
		width: 100%;
		box-sizing: border-box;
		padding: 0upx 10upx 0;
	}

	.feedback-input {
		font-size: 32upx;
		height: 60upx;
		padding: 15upx 20upx;
		line-height: 60upx;
	}


	.feedback-submit {
		background: linear-gradient(121deg, #53D8A4, #ABEC4F);
		color: #ffffff;
		margin: 20upx;
		margin-top: 32upx;
	}
</style>