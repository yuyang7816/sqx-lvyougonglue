<template>
	<view>
		<view class="listInfo" :style="{background: getRandomColor(index).color }">
			<view class="list-box">
				<view class="list-box-img ">
					<image :src="ite" v-for="(ite,ind) in chatInfo.coverImg" v-if="chatInfo.coverImg.length>=8"
						:key="ind" mode="aspectFill"></image>
					<image :src="qunlogo?qunlogo:'../../static/image/qun/qunlogo.png'" v-if="chatInfo.coverImg.length<8"
						mode="" style="width: 90rpx;height: 90rpx;border-radius: 12rpx;">
					</image>
				</view>
				<view class="list-box-right">
					<view class="list-box-right_hed">
						<view class="list-box-right_hed-title">
							{{chatInfo.name}}
						</view>
						<view class="list-box-right_hed-num">
							已加入{{chatInfo.joinNum?chatInfo.joinNum:0}}人
						</view>
					</view>
					<view class="list-box-right_btn ">{{chatInfo.isJoinGroup?'查看':'+加入'}}</view>
				</view>
			</view>
			<view class="list-box-titbox">
				<view class="triangle"></view>
				<view class="list-box-tit">
					{{chatInfo.description?chatInfo.description:'暂无'}}
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
	defineProps({
		chatInfo: {
			type: Object,
			default: () => {}
		},
		index: {
			type: [String, Number],
			default: 0
		},
	})
	const qunlogo = uni.getStorageSync('qunlogo');
	
	
	const colorlist = reactive([{
		color: "#F2FFE5"
	}, {
		color: "#FFF1D7"
	}, {
		color: "#EBE9F6"
	}]);
	const getRandomColor = (index) => {
		// 可以添加一些逻辑来确保每次渲染时颜色不同（例如，基于索引或时间戳）
		// console.log(colorlist[index % colorlist.length])
		return colorlist[index % colorlist.length]; // 简单示例，实际应用中可以更复杂，例如随机选择颜色数组中的颜色
	};
</script>

<style lang="scss">
	.listInfo {
		// width: 100%;
		// height: 261rpx;
		padding: 30rpx;
		border-radius: 24rpx;
		margin-bottom: 20rpx;

		.list-box {
			display: flex;

			.list-box-img {
				width: 90rpx;
				height: 90rpx;
				align-content: start;
				display: flex;
				justify-content: space-between;
				flex-wrap: wrap;
			}

			.list-box-right {
				flex: 1;
				margin-left: 15rpx;
				display: flex;
				justify-content: space-between;
				align-items: center;

				.list-box-right_hed {
					.list-box-right_hed-title {
						font-family: Source Han Sans CN;
						font-weight: 500;
						font-size: 34rpx;
						color: #000001;
					}

					.list-box-right_hed-num {
						font-family: Source Han Sans CN;
						font-weight: 400;
						font-size: 24rpx;
						color: #999999;
					}
				}

				.list-box-right_btn {
					width: 120rpx;
					height: 55rpx;
					background: #000001;
					border-radius: 28rpx;
					font-family: PingFang SC;
					font-weight: 500;
					font-size: 24rpx;
					color: #FFFFFF;
					display: flex;
					justify-content: center;
					align-items: center;
				}
			}

		}

		.list-box-titbox {
			background: #FFFFFF;
			padding: 20rpx 30rpx;
			border-radius: 16rpx;
			position: relative;
			margin-top: 20rpx;

			.triangle {
				width: 0;
				height: 0;
				border-width: 10rpx;
				border-style: solid;
				border-color: transparent transparent #FFFFFF transparent;
				position: absolute;
				top: -20rpx;
			}

			.list-box-tit {
				width: 100%;
				color: #999999;
				font-size: 24rpx;
				overflow: hidden;
				white-space: nowrap;
				text-overflow: ellipsis;
				-o-text-overflow: ellipsis;
			}
		}

	}
</style>