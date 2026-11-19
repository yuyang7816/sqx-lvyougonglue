<template>
	<view class="">
		<up-sticky offset-top="0" bgColor="#FFFFFF">
			<view class="serach">
				<up-search placeholder="搜索需要的景点" shape="square" v-model="keyword" :showAction="false"
					@search="searchInput"></up-search>
				<view class="serachright" @click="serachBack">取消</view>
			</view>
			<view class="" v-if="isTrue">
				<up-tabs :list="tablist" @click="click" :activeStyle="activeStyle" :inactiveStyle="inactiveStyle"
					:lineColor="lineColor" :scrollable="false" :current="current"></up-tabs>
			</view>

		</up-sticky>

		<view class="contont" v-if="!isTrue">
			<view class="search_tit">热门搜索</view>
			<view class="search_hot">
				<view v-for="(item,index) in hotlist" :key="index" class="search_hotbox"
					@click="bindSearch(item.keyword)">
					{{item.keyword}}
				</view>
			</view>
		</view>



		<view class="" v-if="current==0&&isTrue">
			<!-- 列表 -->
			<view class="listbbox">
				<view class="listbbox_left">
					<travellist v-for="(item,index) in listLeft" :key="index" :InfoItem="item" @clickItem="clickItem">
					</travellist>
				</view>
				<view class="listbbox_right">
					<travellist v-for="(item,index) in listRight" :key="index" :InfoItem="item" @clickItem="clickItem">
					</travellist>
				</view>
			</view>
			<!-- 加载数据 -->
			<up-loadmore :status="status" v-if="list.length>0" />
			<!-- 暂无数据 -->
			<empty v-if="list.length==0"></empty>
		</view>

		<view class="ScenicSpot" v-if="current==1&&isTrue">
			<view v-for="(item,index) in list" :key="index" class="ScenicSpot_info"
				@click.stop="goScenicspotDtail(item)">
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
				</view>
			</view>

			<!-- 加载数据 -->
			<up-loadmore :status="status" v-if="list.length>0" />
			<!-- 暂无数据 -->
			<empty v-if="list.length==0"></empty>

		</view>

		<view v-if="current==2&&isTrue">
			<view class="listbox" v-for="(item,index) in list" :key="index" @click="goDetail(item)">
				<view class="listbox-image">
					<image :src="item.imgUrl" mode="aspectFill"></image>
					<view class="listbox-image_label">
						<image src="/my/static/route/lvsc.png"></image>
						{{item.collectCount}}收藏
					</view>
				</view>
				<view class="listbox_cot">
					<view class="listbox_cot_tit">{{item.title}}</view>
					<view class="listbox_cot_lavel">
						<view class="listbox_cot_lavelbox" v-for="(ite,ind) in item.tags" :key="ind">
							{{ite}}
						</view>
					</view>
				</view>
			</view>

			<!-- 加载数据 -->
			<up-loadmore :status="status" v-if="list.length>0" />
			<!-- 暂无数据 -->
			<empty v-if="list.length==0"></empty>
		</view>

		<view v-if="current==3&&isTrue">
			<view class="box">
				<view v-for="(item,index) in list" :key="index" @click="goShopDetail(item)" class="boxitem">
					<image :src="item.img" mode="aspectFill"></image>
					<view class="boxitem_right">
						<view class="boxitem_right_name">{{item.name}}</view>
						<view class="boxitem_rightinfo">
							<view class="boxitem_rightinfo_rate">{{item.grade}}</view>
							<view class="boxitem_rightinfo_tit">{{item.collectNum}}人收藏</view>
						</view>
						<view class="boxitem_right_labe">
							<view class="boxitem_right_labebox" v-for="(ite,ind) in item.tags" :key="ind">{{ite}}</view>
							<view class="boxitem_right_labebox">{{item.intro}}</view>
						</view>
					</view>
				</view>
			</view>

			<!-- 加载数据 -->
			<up-loadmore :status="status" v-if="list.length>0" />
			<!-- 暂无数据 -->
			<empty v-if="list.length==0"></empty>
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
	} from '@dcloudio/uni-app';
	const {
		proxy
	} = getCurrentInstance();
	// 占位图
	import empty from '@/components/empty/empty.vue';
	// 首页底部列表
	import travellist from '@/components/travellist/index.vue'
	// 响应式数据  
	const keyword = ref('');
	const isTrue = ref(false);
	// 创建响应式数据   //搜索类型 1=动态 2=景点 3=路线 4=商家
	const tablist = reactive([{
		name: '动态',
		type: 1
	}, {
		name: '景点',
		type: 2
	}, {
		name: '路线',
		type: 3
	}, {
		name: '商家',
		type: 4
	}]);
	const current = ref(0);
	const activeStyle = ref({
		'color': '#00BC4F',
		'fontWeight': 'bold',
		'fontSize': '32rpx',
		'transform': 'scale(1.05)'
	});
	const inactiveStyle = ref({
		'color': '#666666',
		'transform': 'scale(1)',
		'fontSize': '28rpx',
	});
	const lineColor = ref('linear-gradient(121deg, #53D8A4, #ABEC4F)');

	const hotlist = reactive([]);

	const pages = ref(1); //分页
	const page = ref(1);
	const limit = ref(10);
	const status = ref('loadmore');
	const list = reactive([]); //动态列表
	const listLeft = reactive([]);
	const listRight = reactive([]);


	onLoad(option => {
		gethotlist()
	});
	onShow(() => {

	});
	// 跳转玩乐租赁详情
	const goShopDetail = (item) => {
		uni.navigateTo({
			url: '/my/Leasing/LeasingDtail?id=' + item.id
		})
	};
	// 跳转路线详情
	const goDetail = (e) => {
		uni.navigateTo({
			url: '/my/Route/detail?id=' + e.id
		})
	};
	// 点击热门搜索
	const bindSearch = (ite) => {
		keyword.value = ite
		isTrue.value = true
		page.value = 1;
		getlist()
	};
	// 获取热门搜索
	const gethotlist = () => {

		proxy.$http.get('/app/searchHistory/hot').then(res => {
			if (res.code == 0) {
				hotlist.splice(0, hotlist.length, ...res.data);
			}
		});
	};
	// 跳转景点详情
	const goScenicspotDtail = (ite) => {
		uni.navigateTo({
			url: '/my/Route/ScenicspotDtail?id=' + ite.id
		})
	};
	// 定义方法
	const click = (item) => {
		console.log('item', item);
		current.value = item.index
		page.value = 1;
		getlist()
	};
	// 搜索回调事件
	const searchInput = () => {
		isTrue.value = true
		page.value = 1
		getlist()
	};
	const serachBack = () => {
		console.log('=====')
		var pages = getCurrentPages();
		if (pages.length > 1) {
			uni.navigateBack()
		} else {
			uni.switchTab({
				url: '/pages/index/index'
			})
		}
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
	// 获取列表
	const getlist = () => {
		let type = tablist[current.value] ? tablist[current.value].type : ''
		let data = {
			keyword: keyword.value, //关键词
			type: type, //搜索类型 1=动态 2=景点 3=路线 4=商家
			userId: 1,
			page: page.value,
			limit: limit.value
		}
		if (current.value == 0) {
			if (page.value == 1) {
				listLeft.splice(0, listLeft.length)
				listRight.splice(0, listRight.length)
			}
		}
		status.value = 'loadmore';
		proxy.$http.postJson('/app/searchHistory/search', data).then(res => {
			if (res.code == 0 && res.data) {
				if (current.value == 0) { //动态列表处理
					let DtList = res.data.momentsVoList
					pages.value = DtList.pages
					if (page.value < pages.value) {
						status.value = 'loadmore';
					} else {
						status.value = 'nomore';
					}
					DtList.records.forEach(d => {
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
						list.splice(0, list.length, ...DtList.records);
					} else {
						list.push(...DtList.records);
					}

					list.map((item, index) => {
						if (item.img) {
							item.img = item.img[0]
						}
						if (index % 2 === 0) {
							listLeft.push(item)
						} else {
							listRight.push(item)
						}
					})
				} else if (current.value == 1) { //景点列表处理
					let ScerList = res.data.scenicSpotVoList

					pages.value = ScerList.pages
					if (page.value < pages.value) {
						status.value = 'loadmore';
					} else {
						status.value = 'nomore';
					}

					if (page.value === 1) {
						list.splice(0, list.length, ...ScerList.records);
					} else {
						list.push(...ScerList.records);
					}
				} else if (current.value == 2) {
					let RouterList = res.data.baseInfoVoList
					pages.value = RouterList.pages
					if (page.value < pages.value) {
						status.value = 'loadmore';
					} else {
						status.value = 'nomore';
					}
					RouterList.records.forEach(f => {
						if (f.tags) {
							f.tags = f.tags.split(',')
						}
						// 处理收藏大于1万
						if (f.collectCount) {
							if (f.collectCount >= 10000) {
								f.collectCount = Number(f.collectCount / 10000).toFixed(0) + '万'
							} else {
								f.collectCount = Number(f.collectCount).toFixed(0)
							}
						} else {
							f.collectCount = '0'
						}
					})

					if (page.value === 1) {
						list.splice(0, list.length, ...RouterList.records);
					} else {
						list.push(...RouterList.records);
					}
				}else{
					let shopList = res.data.shopVoList
					
					pages.value = shopList.pages
					if (page.value < pages.value) {
						status.value = 'loadmore';
					} else {
						status.value = 'nomore';
					}
					shopList.records.forEach(d => {
						if (d.tags) {
							d.tags = d.tags.split(',')
						}
					})
					
					if (page.value === 1) {
						list.splice(0, list.length, ...shopList.records);
					} else {
						list.push(...shopList.records);
					}
				}


				status.value = 'nomore';
			} else {
				status.value = 'nomore';
				list.length = 0
			}
			status.value = 'nomore';
			uni.stopPullDownRefresh();
		});
	};
	onPullDownRefresh(() => {

	});
	onReachBottom(() => {

	});
</script>

<style lang="scss">
	page {
		background: #F2F2F2;
	}

	.serach {
		padding: 20rpx 30rpx;
		background: #FFFFFF;
		display: flex;
		align-items: center;

		.serachright {
			margin-left: 20rpx;
			font-family: PingFang SC;
			font-weight: bold;
			font-size: 28rpx;
			color: #333333;
		}
	}

	.contont {
		padding: 30rpx;

		.search_tit {
			font-family: PingFang SC;
			font-weight: bold;
			font-size: 28rpx;
			color: #333333;
		}

		.search_hot {
			display: flex;
			flex-wrap: wrap;
			margin-top: 20rpx;

			.search_hotbox {
				height: 60rpx;
				background: #FFFFFF;
				border-radius: 8rpx;
				font-family: PingFang SC;
				font-weight: 500;
				font-size: 24rpx;
				color: #333333;
				padding: 0 20rpx;
				display: flex;
				align-items: center;
				justify-content: center;
				margin: 0 20rpx 20rpx 0;
			}
		}

	}

	// 动态样式
	.listbbox {
		display: flex;
		justify-content: space-between;
		margin: 30rpx;

		.listbbox_left {
			width: 338rpx;
			height: auto;
		}

		.listbbox_right {
			width: 338rpx;
			height: auto;
		}
	}

	// 景点列表
	.ScenicSpot {
		padding: 30rpx;

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

	// 路线样式
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

	// 商家列表样式
	.box {
		padding: 20rpx 30rpx;

		.boxitem {
			background: #FFFFFF;
			border-radius: 24rpx;
			margin-bottom: 20rpx;
			padding: 20rpx;
			display: flex;

			image {
				width: 200rpx;
				height: 170rpx;
				border-radius: 24rpx;
			}

			.boxitem_right {
				margin-left: 15rpx;
				display: flex;
				flex-direction: column;
				justify-content: space-between;

				.boxitem_right_name {
					font-family: Source Han Sans CN;
					font-weight: 500;
					font-size: 32rpx;
					color: #202020;
				}

				.boxitem_rightinfo {
					display: flex;
					align-items: center;

					.boxitem_rightinfo_rate {
						width: 74rpx;
						height: 40rpx;
						background: linear-gradient(90deg, #4AEBE5 0%, #03D5E4 100%);
						border-radius: 20rpx 22rpx 0rpx 20rpx;
						font-family: Source Han Sans CN;
						font-weight: bold;
						font-size: 28rpx;
						color: #FFFFFF;
						display: flex;
						align-items: center;
						justify-content: center;
					}

					.boxitem_rightinfo_tit {
						font-family: Source Han Sans CN;
						font-weight: 400;
						font-size: 26rpx;
						color: #999999;
						margin-left: 20rpx;
					}
				}

				.boxitem_right_labe {
					display: flex;
					align-items: center;
					flex-wrap: wrap;

					.boxitem_right_labebox {
						height: 38rpx;
						background: #F0F1F5;
						border-radius: 6rpx;
						font-family: PingFang SC;
						font-weight: 500;
						font-size: 22rpx;
						color: #666666;
						display: flex;
						align-items: center;
						justify-content: center;
						padding: 0 10rpx;
						margin-right: 10rpx;
					}
				}
			}
		}
	}
</style>