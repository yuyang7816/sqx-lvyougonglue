<template>
	<view class="msgItem flex align-center justify-center">
		<view class="msgItem-box flex justify-between" v-if="userId == msgInfo.userId">
			<image :src="msgInfo.byAvatar?msgInfo.byAvatar:'../../static/logos.png'" mode="aspectFill">
			</image>
			<view class="msgItem-box-info">
				<view class="msgItem-box-info-title flex align-center justify-between">
					<view class="msgItem-box-info-title-l ">{{msgInfo.byUserName}}</view>
					<view class="msgItem-box-info-time">{{msgInfo.messageTime}}</view>
				</view>

				<block v-if="msgInfo.content">
					<view class="flex align-center justify-between">
						<view class="msgItem-box-info-txt" v-if="msgInfo.messageType === 1">[文字]</view>
						<view class="msgItem-box-info-txt" v-if="msgInfo.messageType === 2">[图片]</view>
						<view class="msgItem-box-info-txt" v-if="msgInfo.messageType === 3">[语音]</view>
						<view class="msgItem-box-info-txt" v-if="msgInfo.messageType === 4">[表情]</view>
						<view class="msgItem-box-info-deg" v-if="msgInfo.contentCount && msgInfo.contentCount>0">
							<up-badge max="99" :value="msgInfo.contentCount"></up-badge>
						</view>
					</view>

				</block>
				<view class="msgItem-box-info-txt" v-else>
					暂无消息
				</view>
			</view>
		</view>

		<view class="msgItem-box flex justify-between" v-else>
			<image :src="msgInfo.avatar?msgInfo.avatar:'../../static/logos.png'" mode="aspectFill">
			</image>
			<view class="msgItem-box-info">
				<view class="msgItem-box-info-title flex align-center justify-between">
					<view class="msgItem-box-info-title-l ">{{msgInfo.userName}}</view>
					<view class="msgItem-box-info-time">{{msgInfo.messageTime}}</view>
				</view>

				<block v-if="msgInfo.content">
					<view class="flex align-center justify-between">
						<view class="msgItem-box-info-txt" v-if="msgInfo.messageType === 1">[文字]</view>
						<view class="msgItem-box-info-txt" v-if="msgInfo.messageType === 2">[图片]</view>
						<view class="msgItem-box-info-txt" v-if="msgInfo.messageType === 3">[语音]</view>
						<view class="msgItem-box-info-txt" v-if="msgInfo.messageType === 4">[表情]</view>
						<view class="msgItem-box-info-deg" v-if="msgInfo.contentCount && msgInfo.contentCount>0">
							<up-badge max="99" :value="msgInfo.contentCount"></up-badge>
						</view>
					</view>

				</block>
				<view class="msgItem-box-info-txt" v-else>
					暂无消息
				</view>
			</view>
		</view>

	</view>

</template>

<script setup>
	import {
		ref,
		reactive,
		onMounted,
		defineEmits,
	} from 'vue';
	defineProps({
		msgInfo: {
			type: Object,
			default: () => {}
		}
	})
	// 定义响应式数据
	const userId = ref('');
	// 组件挂载时执行
	onMounted(() => {
		userId.value = uni.getStorageSync('userId');
	});
</script>

<style lang="scss">
	.msgItem {
		width: 100%;
		height: auto;

		.msgItem-box {
			width: calc(686rpx - 60rpx);
			height: 100%;
			background-color: #ffffff;
			border-radius: 18rpx;
			padding: 30rpx;

			image {
				width: 80rpx;
				height: 80rpx;
				border-radius: 50%;
				margin-right: 10rpx;
			}

			.msgItem-box-info {
				width: calc(100% - 100rpx);

			}

			.msgItem-box-info-title {
				text {
					color: #11C5C7;
					font-size: 26rpx;

				}

				.msgItem-box-info-title-s {
					color: #22C7C9;
					font-size: 24rpx;
					border: 1px solid #22C7C9;
					border-radius: 30rpx;
					padding: 6rpx 20rpx;
				}

				.msgItem-box-info-title-l {
					font-family: PingFang SC;
					font-weight: bold;
					font-size: 28rpx;
					color: #333333;
				}
			}

			.msgItem-box-info-time {
				color: #999999;
				font-size: 24rpx;
			}

			.msgItem-box-info-txt {
				width: calc(100% - 70rpx);
				color: #999999;
				font-size: 24rpx;
				overflow: hidden;
				white-space: nowrap;
				text-overflow: ellipsis;
				-o-text-overflow: ellipsis;
				margin-top: 6rpx;
			}
		}
	}
</style>