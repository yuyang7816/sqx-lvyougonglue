<template>
	<view>
		<!-- 2.0.19支持autoBack，默认为false -->
		<up-navbar :bgColor="current==0?'#FFFFFF':(current!=0&&isTop?'#FFFFFF':'transparent')" leftIcon=""
			:placeholder="true">
			<template #center>
				<view class="headNavb">
					<up-tabs :list="tablist" @click="click" :lineColor="lineColor"
						:activeStyle="current==0?activeStylel:(current!=0&&isTop?activeStylel:activeStyle)"
						:inactiveStyle="current==0?inactiveStylel:(current!=0&&isTop?inactiveStylel:inactiveStyle)"
						lineWidth="12" :current="current" :scrollable="false"></up-tabs>
				</view>

			</template>
		</up-navbar>

		<up-sticky :customNavHeight="customNavHeight">
			<!-- 搜索模块 -->
			<view class="search" :class="current==0?'bgColor':(isTop&&current!=0?'bg':'')"
				:style="current!=0?'margin-top: 220rpx;':''" @click="goSearch">
				<view class="searchbox">
					<up-input placeholder="目的地·景点·一日游..." border="none" v-model="keyword" :readonly="true"></up-input>
					<view class="search_icon">
						<image src="/static/image/index/searchIcon.png"></image>
					</view>
				</view>
			</view>
		</up-sticky>

		<!-- 顶部推荐背景图 -->
		<view class="bgTop" v-if="current!=0&&!isTop">
			<!-- <image src="/static/image/index/bg.png"></image> -->
			<swiper :autoplay="true" interval="2000" duration="500" :circular="true"
				style="width: 100%;height: 372rpx;">
				<swiper-item v-for="(item,index) in swiperList" :key='index' @click="goNav(item)">
					<image :src="item.imageUrl" mode="scaleToFill" style="width: 100%;height: 372rpx;"></image>
				</swiper-item>
			</swiper>
		</view>

		<view class="contont" v-if="current!=0">
			<!-- 分类模块 -->
			<view class="gridbox">
				<up-grid :border="false" col="5">
					<up-grid-item v-for="(baseListItem,baseListIndex) in baseList" :key="baseListIndex"
						@click="goNav(baseListItem)">
						<image :src="baseListItem.imageUrl" class="gridImg"></image>
						<text class="gridTit">{{baseListItem.name}}</text>
					</up-grid-item>
				</up-grid>
			</view>

			<!-- 热门推荐 -->
			<view class="HotRetion">
				<view class="HotRetion_tit">
					热门推荐
					<image src="/static/image/index/tjdin.png"></image>
				</view>
				<view class="scroll-viewbox">
					<scroll-view class="scroll-view_H" scroll-x="true" scroll-left="0" @scrolltoupper="scrolltoupper"
						@scrolltolower="scrolltolower">
						<view class="scroll-view-item_H">
							<!-- 右边模块 -->
							<view :style="{backgroundImage:'url('+hotlistFirst.cover+')'}" @click="goNavCity('/my/Route/index?city='+hotlistFirst.city)"
								class="scroll-view-item_Itemhot">
								<view class="scroll-view-item_Hoxhot">
									<view class="sizebox">
										<view class="title">{{hotlistFirst.city}}</view>
										<view class="titmin">{{hotlistFirst.description}}</view>
									</view>
								</view>
							</view>
							<!-- 左边模块 -->
							<view class="scroll-view-item_Itemhotl">
								<!-- 上面模块 -->
								<view class="scroll-view-item_Itemhotl_Top">
									<view v-for="(item,index) in hotlistLeft" :key="index" @click="goNavCity('/my/Route/index?city='+item.city)"
										:style="{backgroundImage:'url('+item.cover+')'}" class="scroll-view-item_Item">
										<view class="scroll-view-item_Hox">
											<view class="sizebox">
												<view class="title">{{item.city}}</view>
												<view class="titmin">{{item.description}}</view>
											</view>
										</view>
									</view>
								</view>
								<!-- 下面模块 -->
								<view class="scroll-view-item_Itemhotl_Bottom">
									<view v-for="(item,index) in hotlistRight" :key="index"
										:style="{backgroundImage:'url('+item.cover+')'}" class="scroll-view-item_Item">
										<view class="scroll-view-item_Hox">
											<view class="sizebox">
												<view class="title">{{item.city}}</view>
												<view class="titmin">{{item.description}}</view>
											</view>
										</view>
									</view>
								</view>
							</view>
						</view>
					</scroll-view>
				</view>
			</view>


			<!-- 演出赛事 -->
			<view class="Performance" @click.stop="goConcat" v-if="concertlist.length!=0">
				<image src="/static/image/index/ycss.png" class="Performance_bg"></image>
				<view class="Performance_box">
					<view class="Performance_tit">演出赛事</view>
					<view class="scroll-view_Hbox">
						<scroll-view class="scroll-view_H" scroll-x="true" scroll-left="0">
							<view v-for="(item,index) in concertlist" :key="index" @click.stop="goConcatDetail(item)"
								class="scroll-view-item_H">
								<view class="Performance_txt">{{item.title}}</view>
								<view class="Performance_add">
									<image src="/static/image/index/add.png" mode="widthFix"></image>
									<text>{{item.location}} {{item.concertTime}}</text>
								</view>
							</view>
						</scroll-view>
					</view>
				</view>
			</view>

			<!-- 精选路线 -->
			<view class="route">
				<view class="route_head">
					<view class="route_head_tit">
						精选线路
						<image src="/static/image/index/tjdin.png"></image>
					</view>
					<view class="route_head_right" @click="goRoute()">
						<text>查看更多</text>
						<image src="/static/image/index/rightl.png"></image>
					</view>
				</view>
				<view class="route_box">
					<view v-for="(item,index) in routelist" :key="index" class="route_boxIte" @click="goRoute(item.id)">
						<view class="route_boxIte_Image">
							<image :src="item.imgUrl?item.imgUrl:'/static/logos.png'" mode="aspectFill"></image>
							<view class="route_boxIte_Image_bbox">1265用户浏览</view>
						</view>
						<view class="route_boxIte_tit">{{item.title}}</view>
					</view>
				</view>
			</view>


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


		<view class="Dtcont" v-if="current==0">
			<Head :list="list" @clickItem="clickItem"></Head>

			<!-- 加载数据 -->
			<up-loadmore :status="status" v-if="list.length>0" />
			<!-- 暂无数据 -->
			<empty v-if="list.length==0"></empty>
		</view>
		<!-- 自定义导航栏 -->
		<myTabbar :value="0" />
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
	// 引入底部 Tabbar 组件
	import myTabbar from '@/components/myTabbar/myTabbar.vue';
	// 首页底部列表
	import travellist from '@/components/travellist/index.vue'
	// 占位图
	import empty from '@/components/empty/empty.vue';
	// 动态列表
	import Head from '@/components/head/head.vue'
	//加载状态
	const status = ref('loadmore');
	const userId = ref('');
	const swiperList = reactive([]);

	const list = reactive([]); //动态列表
	const listLeft = reactive([]);
	const listRight = reactive([]);

	const pages = ref(1); //分页
	const page = ref(1);
	const limit = ref(10);
	const city = ref('');
	const longitude = ref('');
	const latitude = ref('');
	const keyword = ref('');
	// 创建响应式数据  
	const tablist = reactive([{
			name: '关注',
			sort: '1'
		},
		{
			name: '推荐',
			sort: '1'
		},
		{
			name: '附近',
			sort: '1'
		}
	]);
	const current = ref(1);
	const lineColor = ref('linear-gradient(90deg, #3CF57E 0%, #C1F757 100%)');
	const activeStyle = ref({
		color: '#FFFFFF',
		fontWeight: '800',
		fontSize: '32rpx',
		transform: 'scale(1.05)'
	});
	const inactiveStyle = ref({
		color: '#FFFFFF',
		fontSize: '28rpx',
		transform: 'scale(1.05)'
	});
	const activeStylel = ref({
		color: '#333333',
		fontWeight: '800',
		fontSize: '32rpx',
		transform: 'scale(1.05)'
	});
	const inactiveStylel = ref({
		color: '#333333',
		fontSize: '28rpx',
		transform: 'scale(1.05)'
	});

	const baseList = reactive([]); //分类

	const pageT = ref(1);
	const pagesT = ref(1);
	const hotlist = reactive([]); //热门推荐
	const hotlistFirst = reactive({});
	const hotlistLeft = reactive([]);
	const hotlistRight = reactive([]);
	const isTop = ref(false);
	const customNavHeight = ref(98); //自定义导航栏高度
	const concertlist = reactive([]); //演唱会列表
	const routelist = reactive([]); //路线列表


	onPageScroll(e => {
		if (e.scrollTop > 20) {
			isTop.value = true
		} else {
			isTop.value = false
		}
	});
	onLoad(option => {
		uni.hideTabBar()
		userId.value = uni.getStorageSync('userId');

		let systemInfo = uni.getSystemInfoSync();
		//获取手机系统状态栏高度
		let statusBarHeight = systemInfo.statusBarHeight;
		// 判断系统状态栏高度是否小于47
		if (statusBarHeight <= 47) {
			// 如果小于，则粘性布局距离顶部的距离等于导航栏的高度（88）-47跟系统导航栏的差
			customNavHeight.value = 88 - (47 - statusBarHeight)
		}


		if (uni.getStorageSync('longitude') && uni.getStorageSync('latitude')) {
			latitude.value = uni.getStorageSync('latitude');
			longitude.value = uni.getStorageSync('longitude');
			selectCity(longitude.value, latitude.value);
		} else {
			uni.getLocation({
				type: 'wgs84',
				success: function(res) {
					console.log('当前位置的经度：' + res.longitude);
					console.log('当前位置的纬度：' + res.latitude);
					uni.setStorageSync('latitude', res.latitude)
					uni.setStorageSync('longitude', res.longitude)
					latitude.value = res.latitude;
					longitude.value = res.longitude;
					selectCity(res.longitude, res.latitude);
				}
			});
		}



	});

	onShow(() => {
		getBanner(); //获取顶部轮播图
		getBrid(); //获取金刚区
		hotlistLeft.length = 0;
		hotlistRight.length = 0;
		getTuijian(); //获取热门推荐
		getConcertlist(); //获取演唱会列表
		getRoutelist(); //获取路线列表
		// getTaskList(); //获取动态列表
	});

	//获取路线列表
	const getRoutelist = () => {
		proxy.$http.postJson('/app/baseInfo/page', {
			page: 1,
			limit: 3
		}).then(res => {
			if (res.code == 0) {
				routelist.splice(0, routelist.length, ...res.data.records)
			}
		})
	};
	// 滚动到顶部/左边，会触发 scrolltoupper 事件
	const scrolltoupper = (e) => {
		console.log('滚动到左边')
		pageT.value = 1
		hotlistLeft.length = 0;
		hotlistRight.length = 0;
		getTuijian();
	};
	// 滚动到底部/右边，会触发 scrolltolower 事件
	const scrolltolower = () => {
		console.log('滚动到右边')
		if (pagesT.value > pageT.value) {
			pageT.value += 1
			getTuijian();
		}
	};
	// 获取热门推荐
	const getTuijian = () => {
		proxy.$http.postJson('/app/destination/page', {
			page: pageT.value,
			limit: 10
		}).then(res => {
			if (res.code == 0) {
				pagesT.value = res.data.pages
				if (pageT.value === 1) {
					hotlist.splice(0, hotlist.length, ...res.data.records);
				} else {
					hotlist.push(...res.data.records);
				}
				// 处理热门推荐数据截取
				Object.assign(hotlistFirst, hotlist[0]);
				hotlist.splice(0, 1);
				hotlist.forEach((item, index) => {
					if (index % 2 === 0) {
						hotlistLeft.push(item)
					} else {
						hotlistRight.push(item)
					}
				})
			}
		})
	};
	// 获取演唱会
	const getConcertlist = () => {
		proxy.$http.postJson('/app/concert/page', {
			page: 1,
			limit: 10
		}).then(res => {
			if (res.code == 0) {
				concertlist.splice(0, concertlist.length, ...res.data.records)
			}
		})
	};
	// 跳转演唱会列表
	const goConcat = () => {
		let token = uni.getStorageSync('token');
		if (!token) {
			goLoginInfo();
			return;
		}
		uni.navigateTo({
			url: '/my/Concert/index'
		})
	};
	// 跳转演唱会详情
	const goConcatDetail = (ite) => {
		let token = uni.getStorageSync('token');
		if (!token) {
			goLoginInfo();
			return;
		}
		uni.navigateTo({
			url: '/my/Concert/detail?concertId=' + ite.id
		})
	};
	// 获取顶部轮播
	const getBanner = () => {
		proxy.$http.postJson('/app/base/banner/page', {
			classify: 1, // 分类1banner图 2金刚区分类	3海报背景图
			state: 1, //状态1正常 2隐藏
		}).then(res => {
			if (res.code == 0) {

				swiperList.splice(0, swiperList.length, ...res.data.records)

			}
		})
	};
	// 获取顶部金刚区
	const getBrid = () => {
		proxy.$http.postJson('/app/base/banner/page', {
			classify: 2, // 分类1banner图 2金刚区分类	3海报背景图
			state: 1, //状态1正常 2隐藏
		}).then(res => {
			if (res.code == 0) {
				baseList.splice(0, baseList.length, ...res.data.records)
			}
		})
	};

	// 跳转精选路线
	const goRoute = (id) => {
		let token = uni.getStorageSync('token');
		if (!token) {
			goLoginInfo();
			return;
		}
		if(id){
			uni.navigateTo({
				url: '/my/Route/detail?id='+id
			})
		}else{
			uni.navigateTo({
				url: '/my/Route/index'
			})
		}
		
	};
	// 跳转搜索
	const goSearch = () => {
		let token = uni.getStorageSync('token');
		if (!token) {
			goLoginInfo();
			return;
		}
		uni.navigateTo({
			url: '/my/search/index'
		})
	};
	//根据经纬度获取城市地址
	const selectCity = (longitude, latitude) => {
		let data = {
			lat: latitude,
			lng: longitude
		}

		proxy.$http.get("/app/map/reverseGeocode", data).then(res => {
			if (res.code == 0) {
				//修改当前城市
				uni.setStorageSync('city', res.data.city)
				city.value = res.data.city
				tablist[2].name = res.data.city
				page.value = 1;
				getTaskList();
			}
		});

	};

	// 导航栏点击切换
	const click = (e) => {
		console.log(e)
		current.value = e.index
		page.value = 1;
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
		} else if (dt.index == 7) { //收藏
			collectDt(dt.item.id, dt.item);
		}
	};
	//收藏
	const collectDt = (talkId, item) => {
		let userId = uni.getStorageSync('userId');
		let data = {
			dataId: talkId,
			userId: userId,
			type: 1 //1动态 2景点 3路线 4商户
		}
		proxy.$http.postJson('/app/collect/save', data).then(res => {
			if (res.code == 0) {
				item.isCollect = !item.isCollect
				if (item.isCollect) {
					item.collectNum += 1
				} else {
					item.collectNum -= 1
				}

			} else {
				uni.showToast({
					title: res.msg,
					icon: 'none'
				})
			}
		});
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
	// 查看图片
	const saveImg = (index, Imagelist) => {
		uni.previewImage({
			current: index,
			urls: Imagelist
		});
	};

	// 获取动态列表
	const getTaskList = () => {
		let sort = tablist[current.value] ? tablist[current.value].sort : ''
		let data = {
			page: page.value,
			limit: limit.value,
			city: current.value == 2 ? city.value : '',
			longitude: current.value == 2 ? longitude.value : '',
			latitude: current.value == 2 ? latitude.value : '',
			isRecommend: current.value == 1 ? 1 : 2
		}
		if (page.value == 1) {
			listLeft.splice(0, listLeft.length)
			listRight.splice(0, listRight.length)
		}
		status.value = 'loadmore';
		proxy.$http.postJson('/app/moments/page', data).then(res => {
			if (res.code == 0) {
				pages.value = res.data.pages
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

				if (current.value != 0) {
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

				}
				status.value = 'nomore';
			}
			status.value = 'nomore';
			uni.stopPullDownRefresh();
		});
	};
	const goLoginInfo = () => {
		uni.navigateTo({
			url: '/pages/public/login'
		})
	};
	const goNavCity=(url)=>{
		uni.navigateTo({
			url
		});
	};
	const goNav = (item) => {
		if (item.name === '跳转小程序') {
			// #ifdef MP-WEIXIN
			let wxitem = item.describes.split('~');
			uni.navigateToMiniProgram({
				appId: wxitem[0],
				path: wxitem[1],
				envVersion: 'release',
				success(res) {
					// 打开成功
					console.log(res);
				}
			})
			return;
			// #endif
		}
		let url = item.url
		if (url) {
			if (url.indexOf('/pages/') !== -1 || url.indexOf('/my/') !== -1) {
				uni.navigateTo({
					url
				});
			} else {
				if (url) {
					//#ifndef H5
					uni.navigateTo({
						url: '/pages/index/webView?url=' + url
					});
					//#endif
					//#ifdef H5
					window.location.href = url;
					//#endif
				}
			}
		}
	};
	onPullDownRefresh(() => {
		let token = uni.getStorageSync('token');
		if (!token) {
			// goLoginInfo();
			return;
		}

		page.value = 1;
		getTaskList();
	});
	onReachBottom(() => {
		let token = uni.getStorageSync('token');
		if (!token) {
			// goLoginInfo();
			return;
		}
		if (page.value < pages.value) {
			page.value += 1
			status.value = 'loading';
			getTaskList();
		}
	});
</script>

<style lang="scss">
	.bg {
		background: #FFFFFF;
	}

	.bgColor {
		background: linear-gradient(to bottom, #FFFFFF, #F5F5F5);
	}

	.bgTop {
		width: 100%;
		height: 372rpx;
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
	}

	.headNavb {
		width: 686rpx;
	}

	// 搜索模块
	.search {
		padding: 20rpx 30rpx;

		.searchbox {
			height: 90rpx;
			padding: 0 30rpx;
			display: flex;
			align-items: center;
			justify-content: center;
			background: #FFFFFF;
			box-shadow: 0rpx 8rpx 16rpx 0rpx rgba(209, 255, 227, 0.4);
			border-radius: 34rpx;
			border: 1px solid #01BD4F;



			.search_icon {
				width: 40rpx;
				height: 40rpx;

				image {
					width: 40rpx;
					height: 40rpx;
				}
			}
		}
	}

	.contont {
		background: #FFFFFF;
		padding-bottom: 90rpx;

		// 分类模块
		.gridbox {
			margin-top: 5rpx;

			.gridImg {
				width: 58rpx;
				height: 61rpx;
			}

			.gridTit {
				font-family: Source Han Sans CN;
				font-weight: 400;
				font-size: 24rpx;
				color: #333333;
			}
		}

		// 热门推荐
		.HotRetion {
			padding: 30rpx 30rpx 10rpx;

			.HotRetion_tit {
				display: flex;
				align-items: center;
				font-family: Source Han Sans CN;
				font-weight: 500;
				font-size: 32rpx;
				color: #242424;


				image {
					width: 23rpx;
					height: 29rpx;
					position: relative;
					left: 0rpx;
					top: 10rpx;
				}
			}

			.scroll-viewbox {
				margin-top: 10rpx;

				.scroll-view_H {
					white-space: nowrap;
					width: 100%;
				}

				.scroll-view-item_H {
					display: flex;

					// 右边数据
					.scroll-view-item_Itemhot {
						display: inline-block;
						background-size: 100% 100%;
						background-repeat: no-repeat;
						border-radius: 16rpx;
						margin-right: 10rpx;

						.scroll-view-item_Hoxhot {
							background-color: rgba(0, 0, 0, 0.1);
							width: 210rpx;
							height: 270rpx;
							border-radius: 16rpx;
							position: relative;
						}
					}

					// 左边数据
					.scroll-view-item_Itemhotl {
						display: flex;
						flex-direction: column;
						justify-content: space-between;


						//上面
						.scroll-view-item_Itemhotl_Top {
							display: flex;
						}

						// 下面
						.scroll-view-item_Itemhotl_Bottom {
							display: flex;
						}

						.scroll-view-item_Item {
							display: inline-block;
							background-size: 100% 100%;
							background-repeat: no-repeat;
							border-radius: 16rpx;
							margin-right: 10rpx;

							.scroll-view-item_Hox {
								background-color: rgba(0, 0, 0, 0.1);
								width: 200rpx;
								height: 128rpx;
								border-radius: 16rpx;
								position: relative;
							}
						}
					}

					.sizebox {
						position: absolute;
						bottom: 20rpx;
						left: 20rpx;

						.title {
							font-family: Source Han Sans CN;
							font-weight: bold;
							font-size: 28rpx;
							color: #FFFFFF;
						}

						.titmin {
							font-family: Source Han Sans CN;
							font-weight: 400;
							font-size: 20rpx;
							color: #FFFFFF;
						}
					}

				}

			}
		}

		// 演出赛事
		.Performance {
			margin: 0 30rpx;
			position: relative;

			.Performance_bg {
				width: 100%;
				height: 222rpx;
				position: absolute;
				top: 0;
				left: 0;
				right: 0;
			}

			.Performance_box {
				padding: 45rpx 0 10rpx 30rpx;

				.Performance_tit {
					font-family: Source Han Sans CN;
					font-weight: 800;
					font-size: 30rpx;
					color: #242424;
					line-height: 32rpx;
					background: linear-gradient(0deg, #FC6974 0%, #FB5FD9 100%);
					-webkit-background-clip: text;
					-webkit-text-fill-color: transparent;
					position: relative;
				}

				.scroll-view_Hbox {
					margin-top: 10rpx;

					.scroll-view_H {
						white-space: nowrap;
						width: 100%;
					}

					.scroll-view-item_H {
						display: inline-block;

						background: #FFFFFF;
						border-radius: 24rpx;
						margin-right: 10rpx;
						padding: 20rpx;

						.Performance_txt {
							font-family: Source Han Sans CN;
							font-weight: 500;
							font-size: 26rpx;
							color: #242424;
						}

						.Performance_add {
							display: flex;
							align-items: center;
							margin-top: 10rpx;

							image {
								width: 16rpx;
								height: 21rpx;
							}

							text {
								font-family: Source Han Sans CN;
								font-weight: 400;
								font-size: 20rpx;
								color: #242424;
								margin-left: 4rpx;
							}
						}
					}
				}

			}

		}

		// 精选路线
		.route {
			padding: 30rpx;
			margin-top: 20rpx;
		}

		.route_head {
			display: flex;
			align-items: center;
			justify-content: space-between;

			.route_head_tit {
				font-family: Source Han Sans CN;
				font-weight: 500;
				font-size: 32rpx;
				color: #242424;
				display: flex;
				align-items: center;
				display: -webkit-box;
				-webkit-box-orient: vertical;
				-webkit-line-clamp: 2;
				overflow: hidden;

				image {
					width: 23rpx;
					height: 29rpx;
					position: relative;
					left: 0rpx;
					top: 16rpx;
				}
			}

			.route_head_right {
				display: flex;
				align-items: center;

				text {
					font-family: Source Han Sans CN;
					font-weight: 400;
					font-size: 24rpx;
					color: #666666;
					margin-right: 10rpx;
				}

				image {
					width: 10rpx;
					height: 18rpx;
				}
			}
		}

		.route_box {
			display: flex;
			align-items: center;
			justify-content: space-between;

			.route_boxIte {
				width: 222rpx;
				height: auto;
				margin-top: 10rpx;

				.route_boxIte_Image {
					width: 222rpx;
					height: 150rpx;
					border-radius: 24rpx;
					position: relative;

					image {
						width: 100%;
						height: 100%;
						border-radius: 24rpx;
					}

					.route_boxIte_Image_bbox {
						height: 40rpx;
						line-height: 40rpx;
						background: linear-gradient(90deg, #FFECD6 0%, #FDDCB5 100%);
						border-radius: 0rpx 8rpx 8rpx 24rpx;
						font-family: Source Han Sans CN;
						font-weight: 400;
						font-size: 20rpx;
						color: #AA631E;
						position: absolute;
						bottom: 0;
						left: 0;
						padding: 0 15rpx;
					}
				}

				.route_boxIte_tit {
					font-family: Source Han Sans CN;
					font-weight: 500;
					font-size: 26rpx;
					color: #242424;
					width: 100%;
					white-space: nowrap;
					overflow: hidden;
					text-overflow: ellipsis;
					margin-top: 10rpx;
				}
			}
		}


		.listbbox {
			display: flex;
			justify-content: space-between;
			margin: 0 30rpx;

			.listbbox_left {
				width: 338rpx;
				height: auto;
			}

			.listbbox_right {
				width: 338rpx;
				height: auto;
			}
		}

	}

	// 动态样式
	.Dtcont {
		background: #F5F5F5;
		padding: 0 30rpx 90rpx;

	}
</style>