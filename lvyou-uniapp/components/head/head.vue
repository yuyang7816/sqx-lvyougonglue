<template>
	<view>
		<view v-for="(item,index) in list" :key="index" class="dtbox" @click.stop="clickItem(0,item)">
			<view class="dtbox_head">
				<view class="" @click.stop="clickItem(1,item)">
					<image :src="item.avatar?item.avatar:'/static/logos.png'"></image>
				</view>
				<view class="dtbox_left">
					<view>
						<view class="dtbox_leftuser">
							<view class="username">{{item.userName}}</view>
							<view class="womanbox " v-if="item.sex==2">
								<image src="/static/image/dt/woman.png" mode="widthFix"></image>
								<text v-if="item.age">{{item.age}}</text>
							</view>
							<view class="manbox " v-if="item.sex==1">
								<up-icon name="man" color="#78c6f9" size="10" style="margin-top: 2rpx;"></up-icon>
								<text v-if="item.age">{{item.age}}</text>
							</view>
						</view>
						<view class="data">{{item.createTime}}</view>
					</view>
					<!-- 投诉 -->
					<view class="dt_ts" v-if="item.userId!=userId" @click.stop="clickItem(2,item)">
						<image src="/static/image/dt/more.png"></image>
					</view>

					<view class="flex"
						v-if="item.userId==userId&&className&&(item.auditStatus == 0||item.auditStatus == 2)">
						<view class="dtbtn" @click.stop="clickItem(3,item)">编辑</view>
						<view class="dtbtns" @click.stop="clickItem(4,item)">删除</view>
					</view>
				</view>
			</view>
			<view class="dtbox_Title">{{item.title}}</view>
			<view class="dtbox_content">
				<text v-html="item.content" class="dtbox_content_cut"></text>
			</view>

			<!-- #ifndef APP -->
			<view class="margin-top-sm" v-if="item.videoUrl">
				<video :src="item.videoUrl" style="width: 100%;height: 320rpx;border-radius: 15rpx;"></video>
			</view>
			<!-- #endif -->
			<!-- #ifdef APP -->
			<view v-if="item.videoUrl" class=" margin-top-sm">
				<image src="/static/logos.png"
					style="width: 100%;height: 320rpx;border-radius: 15rpx;"></image>
			</view>
			<!-- #endif -->

			<view class="dtbox_image" v-if="item.img&&item.img.length!=0"
				:style="item.img>=3?'justify-content: space-between;':''">
				<view v-for="(imgs,indimg) in item.img" :key="indimg" :style="indimg==2?'position: relative;':''"
					@click.stop="clickItem(5,item,indimg)">
					<image :src="imgs" mode="aspectFill" class="img" :style="item.img>=3?'':'margin-right:15rpx;'">
					</image>
					<view class="imgbox" v-if="indimg==2&&item.imgleng&&item.imgleng.length-item.img.length>0">
						<u-icon name="photo" color="#FFFFFF"></u-icon>
						+{{item.imgleng.length-item.img.length}}
					</view>
				</view>
			</view>

			<!--  评论 点赞 收藏-->
			<view class="user_mk">
				<view class="mk">
					<image src="../../static/image/dt/msg.png" class="mk_image">
					</image>
					<view class="mk_num">{{item.commentNum}}</view>
				</view>
				<view class="mk" @click.stop="clickItem(6,item)">
					<image src="../../static/image/dt/zan.png" class="mk_image" v-if="!item.isLike">
					</image>
					<image src="../../static/image/dt/zans.png" class="mk_image" v-else>
					</image>
					<view class="mk_num">{{item.likeNum}}</view>
				</view>

				<view class="mk" @click.stop="clickItem(7,item)">
					<image src="../../static/image/dt/like.png" class="mk_image" v-if="!item.isCollect"></image>
					<image src="../../static/image/dt/likes.png" class="mk_image" v-else></image>
					<view class="mk_num">{{item.collectNum}}</view>
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
		//数据
		list: {
			type: Array,
			default: []
		},
		className: {
			type: Boolean,
			default: false
		},

	})
	// 定义响应式数据
	const userId = ref('');
	const emit = defineEmits(['vcodeChange']);

	const clickItem = (index, item, imgIndex) => {
		emit('clickItem', {
			index: index,
			item: item,
			imgIndex: imgIndex
		});
	};
	// 组件挂载时执行
	onMounted(() => {
		userId.value = uni.getStorageSync('userId');
	});
</script>

<style lang="scss">
	.dtbox {
		background: #FFFFFF;
		padding: 30rpx;
		border-radius: 24rpx;
		margin-bottom: 20rpx;

		.dtbox_head {
			display: flex;

			image {
				width: 74rpx;
				height: 74rpx;
				border-radius: 66rpx;
			}

			.dtbox_left {
				flex: 1;
				display: flex;
				align-items: start;
				justify-content: space-between;
				margin-left: 10rpx;

				.dt_ts {
					width: 40rpx;
					height: 10rpx;

					image {
						width: 100%;
						height: 100%;
					}
				}

				.dtbtn {
					height: fit-content;
					font-size: 24upx;
					padding: 10rpx 20rpx;
					background: linear-gradient(90deg, #2FC959 0%, #62DF9D 47%);
					color: #FFFFFF;
					border-radius: 24rpx;
				}

				.dtbtns {
					margin-left: 20rpx;
					height: fit-content;
					font-size: 24upx;
					padding: 10rpx 20rpx;
					background-color: #E6E6E6;
					color: #A3A3A3;
					border-radius: 24rpx;
				}

				.dtbox_leftuser {
					display: flex;
					align-items: center;

					.username {
						font-family: PingFang SC;
						font-weight: bold;
						font-size: 28rpx;
						color: #1E1E1E;
						margin-right: 10rpx;
					}

					.womanbox {
						background: #FFEBF3;
						border-radius: 55rpx;
						font-family: Microsoft YaHei;
						font-weight: 400;
						font-size: 22rpx;
						color: #FA6D8C;
						display: flex;
						align-items: center;
						justify-content: center;
						margin-right: 20rpx;
						height: 38rpx;
						padding: 0 15rpx;
						image{
							width: 23rpx;
							height: 23rpx;
						}
						text{
							margin-left:8rpx;
						}
					}

					.manbox {
						background: #d3e6f9;
						border-radius: 55rpx;
						font-family: Microsoft YaHei;
						font-weight: 400;
						font-size: 22rpx;
						color: #78c6f9;
						display: flex;
						align-items: center;
						justify-content: center;
						margin-right: 20rpx;
						height: 38rpx;
						padding: 0 15rpx;
					}
				}

				.data {
					font-family: PingFang SC;
					font-weight: 500;
					font-size: 24rpx;
					color: #999999;
				}
			}
		}

		.dtbox_Title {
			font-family: PingFang SC;
			font-weight: 800;
			font-size: 32rpx;
			color: #1C1C1C;
			margin-top: 20rpx;
		}

		.dtbox_content {
			font-family: PingFang SC;
			font-weight: 500;
			font-size: 28rpx;
			color: #1C1C1C;
			margin-top: 20rpx;
		}

		.dtbox_content_cut {
			display: -webkit-box; //将元素设置为WebKit内核的弹性盒子模型。 
			overflow: hidden; //隐藏超出容器范围的内容。
			-webkit-line-clamp: 8; //限制显示的文本行数。
			-webkit-box-orient: vertical; //设置弹性盒子的排列方向为垂直。 
		}

		.dtbox_image {
			margin-top: 20rpx;
			display: flex;
			align-items: center;


			.img {
				width: 200rpx;
				height: 160rpx;
				border-radius: 15rpx;
			}

			.imgbox {
				position: absolute;
				bottom: 20rpx;
				right: 20rpx;
				z-index: 9;
				background: rgba(0, 0, 0, 0.5);
				// background: red;
				font-family: Microsoft YaHei;
				font-weight: 400;
				font-size: 24rpx;
				color: #FFFFFF;
				display: flex;
				align-items: center;
				justify-content: center;
				padding: 2rpx 10rpx;
				border-radius: 12rpx;
			}
		}

		.user_mk {
			display: flex;
			justify-content: space-between;
			margin-top: 20rpx;

			.mk {
				display: flex;
				align-items: center;

				.mk_image {
					width: 36rpx;
					height: 36rpx;
				}

				.mk_num {
					font-family: PingFang SC;
					font-weight: 500;
					font-size: 21rpx;
					color: #666666;
					margin-left: 6rpx;
				}
			}

		}


	}
</style>