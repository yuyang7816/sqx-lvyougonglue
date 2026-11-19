<template>
	<view class="">
		<up-sticky>
			<view class="tabbox">
				<view v-for="(item,index) in tablist" :key="index" class="tabboxItem"
					:class="tabIndex==index?'tabboxItemAct':''" @click="changeTab(index)">
					{{item.name}}
					<image src="/static/image/index/tjdin.png" v-if="tabIndex==index" mode="widthFix"></image>
				</view>
			</view>
		</up-sticky>


		<view class="dtCont" v-if="tabIndex==0">
			<view class="Dtbbox">
				<view class="Dtbbox_left">
					<travellist v-for="(item,index) in listLeft" :key="index" :InfoItem="item.moments"
						@clickItem="clickItem">
					</travellist>
				</view>
				<view class="Dtbbox_right">
					<travellist v-for="(item,index) in listRight" :key="index" :InfoItem="item.moments"
						@clickItem="clickItem">
					</travellist>
				</view>
			</view>
		</view>

		<view class="ScenicSpot" v-if="tabIndex==1&&list.length!=0">
			<view v-for="(item,index) in list" :key="index">
				<view class="ScenicSpot_info" @click="goScenicspotDtail(item.scenicSpot)" v-if="item.scenicSpot">
					<image mode="aspectFill" class="ScenicSpot_info_img" :src="item.scenicSpot.img">
					</image>
					<view class="ScenicSpot_info_box">

						<view class="ScenicSpot_info_boxleft_name">{{item.scenicSpot.name}}</view>
						<view class="flex">
							<view class="ScenicSpot_info_boxleft_label" v-if="item.scenicSpot.tags">
								{{item.scenicSpot.tags}}
							</view>
						</view>
						<view class="ScenicSpot_info_boxleft_tit">{{item.scenicSpot.collectNum}}人收藏</view>

					</view>
				</view>
			</view>
		</view>

		<view v-for="(item,index) in list" :key="index" @click="goDetail(item)" v-if="tabIndex==2">
			<view class="listbox" v-if="item.baseInfo">
				<view class="listbox-image">
					<image :src="item.baseInfo.imgUrl" mode="aspectFill"></image>
					<view class="listbox-image_label">
						<image src="/my/static/route/lvsc.png"></image>
						{{item.collectNum}}人收藏
					</view>
				</view>
				<view class="listbox_cot">
					<view class="listbox_cot_tit">{{item.baseInfo.title}}</view>
					<view class="listbox_cot_lavel">
						<view v-for="(ite,inde) in item.baseInfo.tags.split(',')" :key="inde" class="listbox_cot_lavelbox">
							{{ite}}
						</view>


					</view>
				</view>
			</view>
		</view>
		<!-- 加载数据 -->
		<up-loadmore :status="status" v-if="list.length>0" />
		<!-- 暂无数据 -->
		<empty v-if="list.length==0"></empty>
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
		onReachBottom,
	} from '@dcloudio/uni-app';
	const {
		proxy
	} = getCurrentInstance();
	// 占位图
	import empty from '@/components/empty/empty.vue';
	// 动态列表
	import travellist from '@/components/travellist/index.vue'
	const list = reactive([]); //列表
	const listLeft = reactive([]);
	const listRight = reactive([]);
	const pages = ref(1); //分页
	const page = ref(1); //分页
	const limit = ref(20);
	const userId = ref('');
	//加载状态
	const status = ref('loadmore');
	const tablist = reactive([{
		name: '动态',
		id: 1
	}, {
		name: '景点',
		id: 2
	}, {
		name: '路线',
		id: 3
	}]);
	const tabIndex = ref(0);

	onLoad(option => {
		userId.value = uni.getStorageSync('userId');

	});
	onShow(() => {
		list.length = 0
		page.value = 1
		getTaskList();
	});
	/* 跳转路线详情 */
	const goDetail=(itye)=>{
		uni.navigateTo({
			url: '/my/Route/detail?id='+itye.baseInfo.id
		})
	};
	// 跳转景点详情
	const goScenicspotDtail = (ite) => {
		uni.navigateTo({
			url: '/my/Route/ScenicspotDtail?id=' + ite.id
		})
	};
	const changeTab = (index) => {
		tabIndex.value = index
		list.length = 0
		getTaskList();
	};
	// 动态点击事件
	const clickItem = (dt) => {
		let token = uni.getStorageSync('token');
		if (!token) {
			goLoginInfo();
			return;
		}
		if (dt.index == 0) { //跳转详情
			uni.navigateTo({
				url: '/my/dynamic/listDetails?talkId=' + dt.item.id
			})
		} else if (dt.index == 1) { //跳转主页
			uni.navigateTo({
				url: '/my/gird/zhuye?userId=' + dt.item.userId
			})
		} else if (dt.index == 2) { //去投诉 投诉目标类型：1代表用户，2代表群聊
			uni.navigateTo({
				url: '/my/order/complain?byUserId=' + dt.item.userId + '&platform=1&platformId=' + dt.item.id
			})
		} else if (dt.index == 5) { //查看图片
			let Imagelist = dt.item.img
			saveImg(dt.imgIndex, Imagelist);
		} else if (dt.index == 6) { //点赞
			saveLove(dt.item.id, dt.item);
		} 
	};
	// 点赞
	const saveLove = (talkId, item) => {
		let userId = uni.getStorageSync('userId');
		let data = {
			dataId: talkId,
			userId: userId,
			type: 1
		}
		proxy.$http.postJson('/app/like/save', data).then(res => {
			if (res.code == 0) {
				item.isLike = !item.isLike
				if (item.isLike) {
					item.likeNum += 1
				} else {
					item.likeNum -= 1
				}
			} else {
				uni.showToast({
					title: res.msg,
					icon: 'none'
				})
			}
		});
	};
	// 获取动态列表
	const getTaskList = () => {
		let type = tablist[tabIndex.value].id
		let data = {
			page: page.value,
			limit: limit.value,
			type: type
		}
		if (page.value == 1) {
			listLeft.splice(0, listLeft.length)
			listRight.splice(0, listRight.length)
		}
		status.value = 'loadmore';
		proxy.$http.postJson("/app/collect/page", data).then(res => {
			if (res.code == 0&&res.data.records&&res.data.records.length!=0) {
				pages.value = res.data.pages
				if (page.value < pages.value) {
					status.value = 'loadmore';
				} else {
					status.value = 'nomore';
				}
				if (tabIndex.value == 0) {
					res.data.records.forEach(d => {
						let imgarr = []
						if (d.moments && d.moments.img) {
							d.moments.img = d.moments.img.split(',')
						}
					})

					if (page.value === 1) {
						list.splice(0, list.length, ...res.data.records);
					} else {
						list.push(...res.data.records);
					}
					// 处理动态列表左右数据
					list.forEach((item, index) => {
						if (item.moments && item.moments.img) {
							item.moments.img = item.moments.img[0]
						}
						if (item.moments) {
							if (index % 2 === 0) {
								listLeft.push(item)
							} else {
								listRight.push(item)
							}
						}
					})
				} else {
					if (page.value === 1) {
						list.splice(0, list.length, ...res.data.records);
					} else {
						list.push(...res.data.records);
					}
				}
			}
			status.value = 'nomore';
			uni.stopPullDownRefresh();
		});
	};
	onPullDownRefresh(() => {
		page.value = 1;
		getTaskList();
	});
	onReachBottom(() => {
		if (page.value < pages.value) {
			page.value += 1
			status.value = 'loading';
			getTaskList();
		}
	});
</script>

<style lang="scss">
	page {
		background: #F2F2F2;
	}

	// tab切换
	.tabbox {
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 20rpx 30rpx;
		background: #FFFFFF;

		.tabboxItem {
			font-family: PingFang SC;
			font-weight: 500;
			font-size: 26rpx;
			color: #999999;
			position: relative;
		}

		.tabboxItemAct {
			font-family: PingFang SC;
			font-weight: 800;
			font-size: 28rpx;
			color: #333333;
		}

		image {
			width: 22rpx;
			height: 28rpx;
			position: absolute;
			bottom: 0;
		}
	}

	// 动态列表
	.dtCont {
		padding: 0 30rpx;

		.Dtbbox {
			display: flex;
			justify-content: space-between;
			margin-top: 20rpx;

			.Dtbbox_left {
				width: 338rpx;
				height: auto;

			}

			.Dtbbox_right {
				width: 338rpx;
				height: auto;
			}
		}
	}


	// 景点列表
	.ScenicSpot {
		background: #F6F6F6;
		padding: 20rpx 30rpx;

		.ScenicSpot_info {
			background: #FFFFFF;
			border-radius: 24rpx;
			padding: 30rpx;
			margin-bottom: 20rpx;
			display: flex;

			.ScenicSpot_info_img {
				width: 160rpx;
				height: 160rpx;
				border-radius: 20rpx;
			}

			.ScenicSpot_info_box {
				flex: 1;
				margin-left: 20rpx;
				display: flex;
				justify-content: space-between;
				flex-direction: column;

				.ScenicSpot_info_boxleft_name {
					font-family: Source Han Sans CN;
					font-weight: bold;
					font-size: 32rpx;
					color: #202020;
				}

				.ScenicSpot_info_boxleft_label {
					height: 38rpx;
					background: #D6FFE7;
					border-radius: 8rpx;
					font-weight: 400;
					font-size: 24rpx;
					color: #00BC4F;
					padding: 0 20rpx;
					display: flex;
					align-items: center;
					justify-content: center;
				}

				.ScenicSpot_info_boxleft_tit {
					font-weight: 400;
					font-size: 24rpx;
					color: #999999;
				}

			}

		}
	}

	// 路线列表
	.listbox {
		margin: 20rpx 30rpx;
		background: #FFFFFF;
		border-radius: 24rpx;

		.listbox-image {
			width: 100%;
			height: 264rpx;
			border-radius: 24rpx 24rpx 0 0;
			position: relative;

			image {
				width: 100%;
				height: 100%;
				border-radius: 24rpx 24rpx 0 0;
			}

			.listbox-image_label {
				height: 44rpx;
				background: rgba(0, 0, 0, 0.5);
				border-radius: 22rpx;
				padding: 0 20rpx;
				font-family: PingFang SC;
				font-weight: 400;
				font-size: 22rpx;
				color: #FFFFFF;
				display: flex;
				align-items: center;
				position: absolute;
				bottom: 10rpx;
				left: 10rpx;

				image {
					width: 24rpx;
					height: 24rpx;
				}
			}
		}

		.listbox_cot {
			border-radius: 0 0 24rpx 24rpx;
			padding: 20rpx 30rpx;

			.listbox_cot_tit {
				font-family: PingFang SC;
				font-weight: 800;
				font-size: 34rpx;
				color: #373737;
			}

			.listbox_cot_lavel {
				display: flex;
				align-items: center;
				margin-top: 10rpx;

				.listbox_cot_lavelbox {
					height: 40rpx;
					background: #D6FFE7;
					border-radius: 8rpx;
					font-family: PingFang SC;
					font-weight: 500;
					font-size: 24rpx;
					color: #00BC4F;
					display: flex;
					align-items: center;
					padding: 0 10rpx;
					margin-right: 10rpx;
				}
			}
		}
	}
</style>