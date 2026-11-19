<template>
	<view>
		<!-- 背景图 -->
		<view class="headTop">
			<image src="/static/image/qun/qunbg.png" mode="widthFix"></image>
		</view>
		<!-- 搜索模块 -->
		<view class="search">
			<up-input placeholder="目的地·景点·一日游..." border="none" v-model="keyword" @change="changeSearch"></up-input>
			<view class="search_icon">
				<image src="/static/image/qun/sousuo.png"></image>
			</view>
		</view>

		<!-- 群列表 -->
		<view class="heatcot">
			
			<crowdList v-for="(item,index) in list" :key="index" :chatInfo="item" :index="index"
				@tap="goDetail(item)"></crowdList>

			<!-- 加载数据 -->
			<up-loadmore :status="status" v-if="list.length>0" />
			<!-- 暂无数据 -->
			<empty v-if="list.length==0"></empty>
		</view>



		<!-- 自定义导航栏 -->
		<myTabbar :value="1" />
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
	const {
		proxy
	} = getCurrentInstance();
	// 引入底部 Tabbar 组件
	import myTabbar from '@/components/myTabbar/myTabbar.vue';
	// 群聊列表组件
	import crowdList from '@/components/crowdList/index.vue';
	const userId = ref('');
	const list = reactive([]);
	const pages = ref(1); //分页
	const page = ref(1); //分页
	const limit = ref(10);
	//加载状态
	const status = ref('loadmore');

	const keyword = ref('');
	onLoad(option => {
		uni.hideTabBar()
		userId.value = uni.getStorageSync('userId')
		getCrowdLists();
	});
	onShow(() => {

	});
	// 搜索
	const changeSearch = (e) => {
		console.log(e)
		keyword.value = e
		page.value = 1
		getCrowdLists();
	};

	// 跳转群详情
	const goDetail = (navIte) => {
		console.log(navIte)
		if (uni.getStorageSync('token')) {
			uni.navigateTo({
				url: '/my/GroupChat/groupChatDetail?id=' + navIte.id
			})
		} else {
			uni.showModal({
				title: '提示',
				content: '您还未登录,请先登录',
				success: function(res) {
					if (res.confirm) {
						console.log('用户点击确定');
						uni.navigateTo({
							url: '/pages/public/login'
						})
					} else if (res.cancel) {
						console.log('用户点击取消');
					}
				}
			})
		}
	};

	//获取群数据
	const getCrowdLists = () => {
		let data = {
			pageNum: page.value,
			pageSize: limit.value,
			name: keyword.value
		}
		proxy.$http.postJson('/app/groupInfo/page', data).then(res => {
			if (res.code == 0) {
				pages.value = res.data.pages
				if (page.value < pages.value) {
					status.value = 'loadmore';
				} else {
					status.value = 'nomore';
				}
				if (res.data.records.length > 0) {
					res.data.records.map((item, index) => {
						if (item.coverImg) {
							item.coverImg = item.coverImg.split(',')
						} else {
							item.coverImg = []
						}
					})
				}
				if (page.value === 1) {
					list.splice(0, list.length, ...res.data.records);
				} else {
					list.push(...res.data.records);
				}
			}
			status.value = 'nomore';
			uni.stopPullDownRefresh()
			uni.hideLoading()
		})
	};

	onPullDownRefresh(() => {
		page.value = 1;
		getCrowdLists();
	});
	onReachBottom(() => {
		if (page.value < pages.value) {
			page.value += 1
			status.value = 'loading';
			getCrowdLists();
		}
	});
</script>

<style lang="scss">
	.headTop {
		width: 100%;
		height: 307rpx;

		image {
			width: 100%;
			height: 100%;
		}
	}

	.search {
		height: 90rpx;
		background: #FFFFFF;
		border-radius: 45rpx;
		border: 1px solid #000001;
		margin: 0 30rpx;
		padding: 0 30rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		position: relative;
		top: -50rpx;

		.search_icon {
			width: 40rpx;
			height: 40rpx;

			image {
				width: 40rpx;
				height: 40rpx;
			}
		}
	}

	.heatcot {
		background: #FFFFFF;
		padding: 0 30rpx;
	}
</style>