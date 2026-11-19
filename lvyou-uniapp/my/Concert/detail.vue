<template>
	<view class="">
		<!-- 2.0.19支持autoBack，默认为false -->
		<up-navbar @rightClick="rightClick" :autoBack="true" :bgColor="isTop?'#FFFFFF':'transparent'"
			:leftIconColor="isTop?'#333333':'#FFFFFF'">
		</up-navbar>

		<!-- 背景图 -->
		<view class="bgImgbox" @click="saveImg(concertInfo.pic)">
			<image lazy-load="true" fade-show="true" :src="concertInfo.pic" mode="aspectFill">
			</image>
		</view>

		<view class="Info">
			<view class="Infobox">

				<!-- 演唱会信息 -->
				<view class="listbox_ite">
					<image :src="concertInfo.img" mode="aspectFill" class="listbox_ite_img"></image>
					<view class="listbox_ite_bot">
						<view class="listbox_ite_bot_title">{{concertInfo.title}}</view>
						<view class="listbox_ite_bot_tit padding-bottom-sm">
							<view>演出时间:{{concertInfo.concertTime}}</view>
							<view class="margin-top-xs">演出地点: {{concertInfo.location}}</view>
						</view>
					</view>
				</view>


			</view>
			<!-- 动态景点 -->
			<up-sticky>
				<!-- tab切换模块 -->
				<view class="tabbox">
					<view v-for="(item,index) in tablist" :key="index" class="tabbox_itm"
						:class="tabIndex ==index?'tabbox_itmAct':''" @click="bindChange(index)">
						{{item.name}}
						<image src="/static/image/index/tjdin.png" v-if="tabIndex ==index"></image>
					</view>
				</view>
			</up-sticky>

			<!-- 相关动态 -->
			<view class="Dtcont" v-if="tabIndex==0">

				<view class="Dtbbox">
					<view class="Dtbbox_left">
						<travellist v-for="(item,index) in listLeft" :key="index" :InfoItem="item"
							@clickItem="clickItem">
						</travellist>
					</view>
					<view class="Dtbbox_right">
						<travellist v-for="(item,index) in listRight" :key="index" :InfoItem="item"
							@clickItem="clickItem">
						</travellist>
					</view>
				</view>
				<!-- 加载数据 -->
				<up-loadmore :status="status" v-if="list.length>0" />
				<!-- 暂无数据 -->
				<empty v-if="list.length==0"></empty>
			</view>

			<!-- 景点列表 -->
			<view class="ScenicSpot" v-if="tabIndex==1">
				<view v-for="(item,index) in list" :key="index" class="ScenicSpot_info" @click.stop="goScenicspotDtail(item)">
					<image mode="aspectFill" class="ScenicSpot_info_img" :src="item.img">
					</image>
					<view class="ScenicSpot_info_box">
						<view class="ScenicSpot_info_boxleft">
							<view class="ScenicSpot_info_boxleft_name">{{item.name}}</view>
							<view class="flex" v-if="item.tags">
								<view class="ScenicSpot_info_boxleft_label">{{item.tags}}</view>
							</view>
							<view class="ScenicSpot_info_boxleft_tit">{{item.collectNum?item.collectNum:0}}人收藏</view>
						</view>
						<view class="ScenicSpot_info_boxright" @click.stop="collectJd(item)">
							<image src="../../static/image/dt/shoucang.png" v-if="!item.isCollect"></image>
							<image src="../../static/image/dt/shoucangs.png" v-else></image>
						</view>
					</view>
				</view>

				<!-- 加载数据 -->
				<up-loadmore :status="status" v-if="list.length>0" />
				<!-- 暂无数据 -->
				<empty v-if="list.length==0"></empty>

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
	import {
		onLoad,
		onShow,
		onPullDownRefresh,
		onReachBottom,
		onPageScroll
	} from '@dcloudio/uni-app';
	const {
		proxy
	} = getCurrentInstance();
	onPageScroll(e => {
		// console.log(e)
		if (e.scrollTop > 140) {
			isTop.value = true
		} else {
			isTop.value = false
		}
	});
	// 占位图
	import empty from '@/components/empty/empty.vue';
	// 动态列表
	import travellist from '@/components/travellist/index.vue'
	const status = ref('loadmore');
	// 创建响应式数据
	const isTop = ref(false);

	// tab列表
	const tablist = reactive([{
		id: 1,
		name: '相关动态'
	}, {
		id: 2,
		name: '相关景点'
	}]);
	const tabIndex = ref(0);
	const list = reactive([]);
	const listLeft = reactive([]);
	const listRight = reactive([]);
	const pages = ref(1); //分页
	const page = ref(1);
	const limit = ref(10);
	const userId = ref('');
	const concertId = ref('');
	const concertInfo = ref('');


	onLoad(option => {
		userId.value = uni.getStorageSync('userId');
		if (option.concertId) {
			concertId.value = option.concertId
			getDetailConcert();

		}
	});
	onShow(() => {

	});
	//收藏
	const collectJd = (ite) => {
		let userId = uni.getStorageSync('userId');
		let data = {
			dataId: ite.id,
			userId: userId,
			type: 2 //1动态 2景点 3路线 4商户
		}
		proxy.$http.postJson('/app/collect/save', data).then(res => {
			if (res.code == 0) {
				ite.isCollect= !ite.isCollect
			} else {
				uni.showToast({
					title: res.msg,
					icon: 'none'
				})
			}
		});
	};
	
	const getDetailConcert = () => {
		proxy.$http.postJson('/app/concert/info', {
			id: concertId.value,
		}).then(res => {
			if (res.code == 0) {
				concertInfo.value = res.data
				page.value = 1
				if (tabIndex.value == 0) {
					getTaskList();
				} else {
					getscenicSpotList();
				}
			}
		})
	};
	// 跳转景点详情
	const goScenicspotDtail = (ite) => {
		uni.navigateTo({
			url: '/my/Route/ScenicspotDtail?id='+ite.id
		})
	};
	// tab切换
	const bindChange = (index) => {
		tabIndex.value = index
		page.value = 1
		if (tabIndex.value == 0) {
			getTaskList();
		} else {
			getscenicSpotList();
		}
	};
	// 返回上一页
	const rightClick = () => {
		var pages = getCurrentPages();
		if (pages.length > 1) {
			uni.navigateBack()
		} else {
			uni.switchTab({
				url: '/pages/index/index'
			})
		}
	};
	// 查看图片
	const saveImg = (photoImg) => {
		let imgsArray = [];
		imgsArray[0] = photoImg;
		uni.previewImage({
			current: 0,
			urls: imgsArray
		});
	};
	// 动态点击事件
	const clickItem = (dt) => {
		console.log(dt, '====', dt.item.id)
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
		} else if (dt.index == 6) { //点赞
			saveLove(dt.item.id, dt.item);
		}
	};
	// 点赞
	const saveLove = (talkId, item) => {
		let userId = uni.getStorageSync('userId');
		let data = {
			talkId: talkId,
			userId: userId
		}
		proxy.$http.postJson('/app/gambit/saveGoodTalk', data).then(res => {
			if (res.status == 0) {
				if (item.goodTalkByUserCount == 1) {
					item.goodTalkByUserCount = 0
					item.goodTalkCount -= 1
				} else {
					item.goodTalkByUserCount = 1
					item.goodTalkCount += 1
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
		let data = {
			page: page.value,
			limit: limit.value,
			// userId: userId.value,
			city: concertInfo.value.city

		}
		status.value = 'loadmore';
		if (page.value == 1) {
			listLeft.splice(0, listLeft.length)
			listRight.splice(0, listRight.length)
		}
		proxy.$http.postJson("/app/moments/page", data).then(res => {
			if (res.code == 0) {
				pages.value = res.data.totalPage
				if (page.value < pages.value) {
					status.value = 'loadmore';
				} else {
					status.value = 'nomore';
				}
				res.data.records.forEach(d => {
					let imgarr = []
					d.imgleng = []
					if (d.img) {
						d.img.split(',').forEach((dite, ind) => {
							if (dite) {
								imgarr.push(dite)
								d.imgleng.push(dite)
							}
						})
						d.img = imgarr.splice(0, 3)
					}
				})

				if (page.value === 1) {
					list.splice(0, list.length, ...res.data.records);
				} else {
					list.push(...res.data.records);
				}
				// 处理动态列表左右数据
				list.forEach((item, index) => {
					if (item.img) {
						item.img = item.img[0]
					}
					if (index % 2 === 0) {
						listLeft.push(item)
					} else {
						listRight.push(item)
					}
				})
			}
			status.value = 'nomore';
			uni.stopPullDownRefresh();
		});
	};
	const getscenicSpotList = () => {
		let data = {
			page: page.value,
			limit: limit.value,
			city: concertInfo.value.city
		}
		status.value = 'loadmore';
		proxy.$http.postJson("/app/scenicSpot/page", data).then(res => {
			if (res.code == 0) {
				pages.value = res.data.totalPage
				if (page.value < pages.value) {
					status.value = 'loadmore';
				} else {
					status.value = 'nomore';
				}

				if (page.value === 1) {
					list.splice(0, list.length, ...res.data.records);
				} else {
					list.push(...res.data.records);
				}

			}
			status.value = 'nomore';
			uni.stopPullDownRefresh();
		});
	};
	onPullDownRefresh(() => {
		let token = uni.getStorageSync('token');
		if (!token) {
			goLoginInfo();
			return;
		}
		page.value = 1
		if (tabIndex.value == 0) {
			getTaskList();
		} else {
			getscenicSpotList();
		}

	});
	onReachBottom(() => {
		let token = uni.getStorageSync('token');
		if (!token) {
			goLoginInfo();
			return;
		}
		if (page.value < pages.value) {
			page.value += 1
			status.value = 'loading';
			if (tabIndex.value == 0) {
				getTaskList();
			} else {
				getscenicSpotList();
			}
		} else {
			uni.showToast({
				title: '已经到底了~',
				icon: 'none'
			})
		}
	});
</script>

<style lang="scss">
	page {
		background: #F6F6F6;
	}

	.bgImgbox {
		width: 100%;
		height: 442rpx;
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		z-index: 3;

		image {
			width: 100%;
			height: 100%;
		}
	}

	.Info {
		position: relative;
		top: 410rpx;
		left: 0;
		right: 0;
		z-index: 9;

		.Infobox {
			background: #FFFFFF;
			border-radius: 24rpx 24rpx 0 0;
			padding: 30rpx;
		}
	}

	.listbox_ite {
		width: 100%;
		background: #FFFFFF;
		border-radius: 24rpx;
		display: flex;

		.listbox_ite_img {
			width: 200rpx;
			height: 250rpx;
			border-radius: 24rpx;
		}

		.listbox_ite_bot {
			flex: 1;
			display: flex;
			flex-direction: column;
			justify-content: space-between;
			margin-left: 18rpx;

			.listbox_ite_bot_title {
				font-family: Source Han Sans CN;
				font-weight: bold;
				font-size: 32rpx;
				color: #4D4D4D;
				display: -webkit-box;
				-webkit-box-orient: vertical;
				-webkit-line-clamp: 2;
				overflow: hidden;
			}

			.listbox_ite_bot_tit {
				font-family: Source Han Sans CN;
				font-weight: 400;
				font-size: 26rpx;
				color: #999999;
			}
		}
	}

	.tabbox {
		background: linear-gradient(to bottom, #FFFFFF, #F6F6F6);
		margin-top: 20rpx;
		padding: 30rpx 80rpx;
		display: flex;
		align-items: center;

		.tabbox_itm {
			flex: 1;
			text-align: center;
			position: relative;

			image {
				width: 23rpx;
				height: 29rpx;
				position: absolute;
				bottom: 0rpx;
			}

			font-family: PingFang SC;
			font-weight: 500;
			font-size: 28rpx;
			color: #999999;
		}

		.tabbox_itmAct {
			font-family: PingFang SC;
			font-weight: 800;
			font-size: 32rpx;
			color: #181717;
		}
	}

	// 景点列表
	.ScenicSpot {
		background: #F6F6F6;
		padding: 0 30rpx;

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
				align-items: center;
				justify-content: space-between;

				.ScenicSpot_info_boxleft {
					height: 160rpx;
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

				.ScenicSpot_info_boxright {
					width: 42rpx;
					height: 42rpx;

					image {
						width: 100%;
						height: 100%;
					}
				}
			}

		}
	}

	// 动态样式
	.Dtcont {
		background: #F6F6F6;

		.Dtbbox {
			display: flex;
			justify-content: space-between;
			margin: 0 30rpx;

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
</style>