<template>
	<view>
		<view class="listView" v-for="(item,index) in list" @click.stop="goZhuYe(item.userId)">
			<image :src="item.avatar?item.avatar:'../../static/logos.png'"></image>
			<view class="margin-left-sm flex-sub">
				<view class="flex align-center">
					<view class="listView_name">{{item.userName}}</view>
					<image :src="item.talentImg?item.talentImg:'../../static/images/my/dazirenzheng.png'"
						style="width: 26rpx;height: 28rpx;margin-left: 10rpx;" v-if="item.talentName">
					</image>
				</view>
				<view class="listView_sex">
					<view class="text-sm " v-if="item.sex==1"
						style="background: #38CAFF;display: inline-flex;color: #FFFFFF;border-radius: 10rpx;padding:4rpx 8rpx;">
						<u-icon name="man" color="#FFFFFF" size="14"></u-icon>
						{{item.age?item.age:''}}
					</view>
					<view class="text-sm " v-if="item.sex==2"
						style="background: #FF7FC7;display: flex;color: #FFFFFF;border-radius: 10rpx;padding:4rpx 8rpx;">
						<u-icon name="woman" color="#FFFFFF" size="14"></u-icon>
						{{item.age?item.age:''}}
					</view>
				</view>
			</view>
			<view class="listView_btn" @click.stop="guanzhu(item.userId)" v-if="item.status==2">关注</view>
			<view class="listView_btn" @click.stop="guanzhu(item.userId)" v-if="item.status==1">取关</view>
		</view>

		<up-loadmore :status="status" v-if="list.length>0" />

		<empty contont="暂无数据" v-if="list.length == 0" />


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
	//引入内容为空组件
	import empty from '@/components/empty/empty.vue';
	const {
		proxy
	} = getCurrentInstance();

	const list = reactive([]); //列表
	const pages = ref(1); //分页
	const page = ref(1); //分页
	const limit = ref(20);
	//加载状态
	const status = ref('loadmore');


	// 获取粉丝列表
	const getList = () => {
		let data = {
			page: page.value,
			limit: limit.value,
			targetUserId:uni.getStorageSync('userId')
		}
		proxy.$http.postJson("/app/follow/page", data).then(res => {
			if (res.code == 0) {
				pages.value = res.data.pages
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

	// 关注
	const guanzhu = (userId) => {
		proxy.$http.get("/app/userFollow/insert", {
			followUserId: userId
		}).then(res => {
			if (res.code == 0) {
				uni.showToast({
					title: res.msg,
					icon: 'none'
				})
				page.value = 1;
				getList();
			}
		});
	};
	
	// 跳转主页
	const goZhuYe=(userId)=>{
		uni.navigateTo({
			url:'/my/gird/zhuye?userId='+userId
		})
	};

	onLoad(option => {
		getList()
	});
	onShow(() => {

	});
	onPullDownRefresh(() => {
		page.value = 1;
		getList();
	});
	onReachBottom(() => {
		if (page.value < pages.value) {
			page.value += 1
			status.value = 'loading';
			getList();
		}
	});
</script>


<style lang="scss">
	page {
		background: #F2F6FC;
	}

	.listView {
		background: #FFFFFF;
		border-radius: 24rpx;
		margin: 20rpx 30rpx;
		display: flex;
		padding: 30rpx;
		align-items: center;

		.listView_btn {
			width: 130rpx;
			height: 60rpx;
			border-radius: 45rpx;
			text-align: center;
			display: flex;
			align-items: center;
			justify-content: center;
			font-size: 24rpx;
			font-family: PingFang SC;
			font-weight: bold;
			background: linear-gradient(121deg, #53D8A4, #ABEC4F);
			color: #FFFFFF;
		}

		.listView_sex {
			width: 400rpx;
			display: flex;
			height: 40rpx;
			align-items: center;
			margin-top: 10rpx;

			text {
				font-size: 20rpx;
				font-family: PingFang SC;
				font-weight: 500;
				color: #333333;
				margin-left: 4rpx;
			}

			image {
				width: 30rpx;
				height: 30rpx;
			}
		}

		.listView_name {
			font-size: 32rpx;
			font-family: PingFang SC;
			font-weight: 500;
			color: #1E1E1E;
		}

		image {
			width: 90rpx;
			height: 90rpx;
			border-radius: 50rpx;
		}
	}
</style>