<template>
	<view class="">
		<view class="swiperbox" v-if="SceniceInfo.img">
			<up-swiper :list="SceniceInfo.img" @change="change" @click="clickSwper" previousMargin="0"
				:nextMargin="SceniceInfo.img.length>=1?20:70" circular :autoplay="false" radius="12" bgColor="#ffffff"
				height="331rpx"></up-swiper>
		</view>

		<view class="info">
			<view class="info_Top">
				<view class="info_TopLeft">
					<view class="info_TopLeft_name">{{SceniceInfo.name}}</view>
					<view class="info_TopStart">
						<up-rate :count="count" v-model="SceniceInfo.rate" active-color="#FC6801"
							inactive-color="#CCCCCC" gutter="0" inactiveIcon="star-fill" size="14"></up-rate>
						<text class="margin-left-xs">{{SceniceInfo.rate?SceniceInfo.rate:0}}</text>
					</view>
				</view>
				<view class="info_Topbtn" @click="collectJd()">
					<image src="../static/route/sc.png" mode="widthFix"></image>
					<view class="info_Topbtn_name">{{!SceniceInfo.isCollect?'收藏':'已收藏'}}</view>
				</view>
			</view>


			<view class="info_card">
				<view class="info_cardTime">
					<text>开放时间</text> {{SceniceInfo.openHours}}
				</view>
				<view class="margin-top-xs">
					{{SceniceInfo.location}}
				</view>
				<view class="info_card_bot">
					<view class="">{{SceniceInfo.name}}门票</view>
					<view>￥{{SceniceInfo.ticketPrice}}起</view>
				</view>
			</view>
		</view>

		<up-sticky bgColor="#FFFFFF">
			<view class="tabox">
				<up-tabs :list="tablist" @click="click" :scrollable="false" :lineColor="lineColor"
					:activeStyle="activeStyle" :inactiveStyle="inactiveStyle" :current="current"></up-tabs>
			</view>
		</up-sticky>

		<view class="contont" v-if="current==0">
			<view class="contont_title">简介</view>
			<view class="contont_tit">
				{{SceniceInfo.intro}}
			</view>
		</view>

		<view class="contont" v-if="current!=2">
			<view class="contont_title">交通攻略</view>
			<view class="contont_tit">
				{{SceniceInfo.traffic}}
			</view>
			<!-- <view class="contont_tit">地铁: 乘坐地铁9号线至华清池地铁站下车，即可到达</view> -->
		</view>
		<view class="contont" v-if="current!=2">
			<view class="contont_title">门票攻略</view>
			<!-- <view class="contont_card" v-html="SceniceInfo.nameintro"></view> -->
			<view >
				
			</view>
			<view class="contont_card" v-if="SceniceInfo.travelScenicSpotTickets">
				<view class="contont_card_data">
					{{SceniceInfo.travelScenicSpotTickets[0].startTime}}至{{SceniceInfo.travelScenicSpotTickets[0].endTime}}
				</view>
				
				<view class="contont_card_ite" v-for="(item,index) in SceniceInfo.travelScenicSpotTickets" :key="index">
					<view class="contont_card_left">{{item.name}}：</view>
					<view class="contont_card_right" v-html="item.nameIntro"></view>
				</view>
		<!-- 		<view class="contont_card_ite">
					<view class="contont_card_left">免票：</view>
					<view class="contont_card_right">
						身高1.2米以下儿童;65周岁(含)老年
						人持本人身份证原件在华清宫景区木
						华水清门登记入园;享受国家特殊津
						贴及省级以上突出贡献的专家、劳动
						模范持本人有效证件;残疾人持本人
						有效证件在华清宫景区木华水清门登
						记入园</view>
				</view>
				<view class="contont_card_ite">
					<view class="contont_card_left">半价票：</view>
					<view class="contont_card_right">身高1.2米(含)以上或6周岁(含)-18周岁(不含)未成年人</view>
				</view> -->
			</view>
		</view>

		<view class="contont">
			<view class="contont_title">达人笔记</view>
			<view class="Dtbbox">
				<view class="Dtbbox_left">
					<travellist v-for="(item,index) in listLeft" :key="index" :InfoItem="item" @clickItem="clickItem">
					</travellist>
				</view>
				<view class="Dtbbox_right">
					<travellist v-for="(item,index) in listRight" :key="index" :InfoItem="item" @clickItem="clickItem">
					</travellist>
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
	// 动态列表
	import travellist from '@/components/travellist/index.vue'
	const status = ref('loadmore');
	// 使用 reactive 创建响应式数组  
	const list1 = reactive([
		'/static/logos.png',
		'/static/image/empty.png',
		'/static/image/qun/qunbg.png',
	]);
	const count = ref(5);
	const value = ref(4);
	// 创建响应式数据  
	const tablist = reactive([{
			name: '简介'
		},
		{
			name: '攻略'
		},
		{
			name: '达人笔记'
		}
	]);
	const lineColor = ref('linear-gradient(121deg, #53D8A4, #ABEC4F)');
	const activeStyle = ref({
		color: '#222222',
		fontWeight: 'bold',
		fontSize: '30rpx',
		transform: 'scale(1.05)'
	});
	const inactiveStyle = ref({
		color: '#999999',
		transform: 'scale(1)',
		fontSize: '30rpx',
	});
	const current = ref(0);
	const list = reactive([]);
	const listLeft = reactive([]);
	const listRight = reactive([]);
	const pages = ref(1); //分页
	const page = ref(1);
	const limit = ref(10);
	const userId = ref('');
	const scenicSpotId = ref(''); //景点id
	const SceniceInfo = reactive({}); //景点详情





	onLoad(option => {
		if (option.id) {
			scenicSpotId.value = option.id
			getSceniceDetail()
		}
		userId.value = uni.getStorageSync('userId');
		getTaskList();
	});
	onShow(() => {

	});
	const clickSwper = (e) => {
		console.log(e)
	};
	const change = (e) => {
		console.log(e)
	};
	const click = (e) => {
		console.log(e)
		current.value = e.index
	};
	/* 获取景点详情 */
	const getSceniceDetail = () => {
		let data = {
			id: scenicSpotId.value
		}
		proxy.$http.postJson('/app/scenicSpot/info', data).then(res => {
			if (res.code == 0) {
				if (res.data.img) {
					res.data.img = res.data.img.split(',')
				}

				Object.assign(SceniceInfo, res.data);

			} else {
				uni.showToast({
					title: res.msg,
					icon: 'none'
				})
			}
		});
	};
	//收藏
	const collectJd = (ite) => {
		let userId = uni.getStorageSync('userId');
		let data = {
			dataId: scenicSpotId.value,
			userId: userId,
			type: 2 //1动态 2景点 3路线 4商户
		}
		proxy.$http.postJson('/app/collect/save', data).then(res => {
			if (res.code == 0) {
				getSceniceDetail()
			} else {
				uni.showToast({
					title: res.msg,
					icon: 'none'
				})
			}
		});
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
			scenicSpotId: scenicSpotId.value,
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
	onPullDownRefresh(() => {
		let token = uni.getStorageSync('token');
		if (!token) {
			goLoginInfo();
			return;
		}
		page.value = 1;
		getTaskList();
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
			getTaskList();
		}
	});
</script>

<style lang="scss">
	page {
		background: #F9F9F9;
	}

	.swiperbox {
		background: #FFFFFF;
		padding-left: 30rpx;
	}

	.info {
		background: #FFFFFF;
		padding: 20rpx 30rpx;

		.info_Top {
			display: flex;
			align-items: center;
			justify-content: space-between;

			.info_TopLeft {
				.info_TopLeft_name {
					font-family: PingFang SC;
					font-weight: bold;
					font-size: 40rpx;
					color: #222222;
				}

				.info_TopStart {
					display: flex;
					align-items: center;

					text {
						font-size: 26rpx;
						color: #222222;

					}
				}
			}

			.info_Topbtn {
				display: flex;
				align-items: center;
				width: 150rpx;
				height: 70rpx;
				background: linear-gradient(90deg, #FF9210 0%, #FE593A 100%);
				border-radius: 16rpx;
				justify-content: center;

				.info_Topbtn_name {
					font-weight: 500;
					font-size: 26rpx;
					color: #FFFFFF;
				}

				image {
					width: 34rpx;
					height: 34rpx;
					margin-right: 8rpx;
				}
			}

		}

		.info_card {
			background: #FFFFFF;
			border-radius: 24rpx;
			border: 2px solid #F9F9F9;

			font-weight: 500;
			font-size: 26rpx;
			color: #999999;
			padding: 20rpx;
			margin: 20rpx 0;

			.info_cardTime {
				text {
					color: #00BC4F;
				}
			}

			.info_card_bot {
				display: flex;
				align-items: center;
				justify-content: space-between;
				margin-top: 10rpx;
			}
		}
	}

	.tabox {
		background: #FFFFFF;
		margin-top: 19rpx;
	}

	::v-deep .u-tabs__wrapper__nav__item-1 {
		flex: 2 1 0% !important;
	}

	::v-deep .u-tabs__wrapper__nav__line {
		bottom: 10rpx !important;
	}

	.contont {
		background: #FFFFFF;
		padding: 30rpx;
		margin-top: 20rpx;

		.contont_title {
			font-family: PingFang SC;
			font-weight: bold;
			font-size: 38rpx;
			color: #222222;
		}

		.contont_tit {
			width: 686rpx;
			font-family: Source Han Sans CN;
			font-weight: 400;
			font-size: 26rpx;
			color: #333333;
		}

		// 门票样式
		.contont_card {
			color: #868686;
			font-size: 26rpx;
			margin: 20rpx 0;

			.contont_card_data {
				background: #F6F7F9;
				display: flex;
				flex-direction: column;
				align-items: center;
				justify-content: center;
				padding: 25rpx 0;
			}

			.contont_card_ite {
				border-bottom: 1px solid #F3F3F3;
				padding: 25rpx 0;
				display: flex;

				.contont_card_left {
					// margin-right: 60rpx;
					width: 200rpx;
				}

				.contont_card_right {
					flex: 1;
				}
			}

		}
	}

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
</style>