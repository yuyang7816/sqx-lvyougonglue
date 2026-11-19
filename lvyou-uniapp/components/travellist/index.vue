<template>
	<view class="itembox" @click.stop="clickItem(0,InfoItem)">
		
		<view class="itembox_image">
			<image :src="InfoItem.img?InfoItem.img:'/static/logos.png'" mode="widthFix" v-if=" InfoItem.img"></image>
			<image src="/static/logos.png"
				v-else mode="widthFix"></image>
			<view class="itembox_image_add">
				<image src="/static/image/index/whiteAdd.png" mode="widthFix"></image>
				<view class="tit">{{InfoItem.city}}·{{InfoItem.location}}</view>
			</view>
		</view>
		<view class="itembox_cot">
			<view class="itembox_cot_tit">{{InfoItem.title}}</view>
			<view class="itembox_cot_user">
				<view class="itembox_cot_user_Info" @click.stop="clickItem(1,InfoItem)">
					<image :src="InfoItem.avatar?InfoItem.avatar:'../static/logos.png'" mode="aspectFill"></image>
					<text>{{InfoItem.userName}}</text>
				</view>
				
				<view class="itembox_cot_user_like" @click.stop="clickItem(6,InfoItem)">
					<image src="/static/image/dt/xin.png" v-if="!InfoItem.isLike "></image>
					<image src="/static/image/dt/xins.png" v-else></image>
					<text>{{InfoItem.likeNum}}</text>
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
		InfoItem: {
			type: Object,
			default: () => {}
		}
	})
	const emit = defineEmits(['vcodeChange']);

	const clickItem = (index, item, imgIndex) => {
		emit('clickItem', {
			index: index,
			item: item,
			imgIndex: imgIndex
		});
	};
</script>

<style lang="scss">
	.itembox {
		width: 338rpx;
		height: auto;
		background: #FFFFFF;
		border-radius: 24rpx;
		margin-bottom: 20rpx;

		.itembox_image {
			width: 100%;
			border-radius: 24rpx;
			min-height: 300rpx;
			position: relative;

			image {
				width: 100%;
				min-height: 300rpx;
				border-radius: 24rpx;
			}

			.itembox_image_add {
				position: absolute;
				bottom: 25rpx;
				left: 20rpx;
				padding: 0 15rpx;
				height: 34rpx;
				background-color: rgba(0, 0, 0, 0.5);
				border-radius: 17rpx;
				display: flex;
				align-items: center;

				image {
					width: 19rpx;
					min-height: 23rpx;

				}

				.tit {
					width: 200rpx;
					font-family: PingFang SC;
					font-weight: 500;
					font-size: 20rpx;
					color: #FFFFFF;
					white-space: nowrap;
					overflow: hidden;
					text-overflow: ellipsis;
					margin-left: 10rpx;
				}
			}
		}

		.itembox_cot {
			padding: 0 15rpx 15rpx;
			background: #FFFFFF;
			border-radius: 0 0 24rpx 24rpx;

			.itembox_cot_tit {
				width: 100%;
				font-family: Source Han Sans CN;
				font-weight: 400;
				font-size: 28rpx;
				color: #232323;
				display: -webkit-box;
				-webkit-box-orient: vertical;
				-webkit-line-clamp: 2;
				overflow: hidden;
			}

			.itembox_cot_user {
				display: flex;
				justify-content: space-between;
				margin-top: 15rpx;

				.itembox_cot_user_Info {
					display: flex;
					align-items: center;

					image {
						width: 40rpx;
						height: 40rpx;
						border-radius: 55rpx;
					}

					text {
						font-family: Source Han Sans CN;
						font-weight: 400;
						font-size: 26rpx;
						color: #999999;
						margin-left: 10rpx;
					}
				}

				.itembox_cot_user_like {
					display: flex;
					align-items: center;

					image {
						width: 30rpx;
						height: 28rpx;
					}
					text{
						font-family: Source Han Sans CN;
						font-weight: 400;
						font-size: 26rpx;
						color: #999999;
						margin-left: 10rpx;
					}
				}
			}
		}





	}
</style>