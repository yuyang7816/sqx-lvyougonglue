<template>
	<view>
		<view class="listView" v-for="(item,index) in list">
			<view @click.stop="goZhuYe(item.targetUserId)">
				<image :src="item.targetUserAvatar?item.targetUserAvatar:'../../static/logos.png'"></image>
			</view>
			<view class="margin-left-sm flex-sub">
				<view class="flex aligin-center">
					<view class="listView_name">{{item.targetUserName}}</view>
				</view>
				<view class="listView_sex">
					<view class="text-sm " v-if="item.targetUserSex==1"
						style="background: #38CAFF;display: inline-flex;color: #FFFFFF;border-radius: 10rpx;padding:4rpx 8rpx;">
						<u-icon name="man" color="#FFFFFF" size="14"></u-icon>
						{{item.targetUserAge?item.targetUserAge:''}}
					</view>
					<view class="text-sm " v-if="item.targetUserSex==2"
						style="background: #FF7FC7;display: inline-flex;color: #FFFFFF;border-radius: 10rpx;padding:4rpx 8rpx;">
						<u-icon name="woman" color="#FFFFFF" size="14"></u-icon>
						{{item.targetUserAge?item.targetUserAge:''}}
					</view>
				</view>
			</view>
			<view class="listView_btn" @click.stop="guanzhu(item)">取关</view>
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

	// 跳转主页
	const goZhuYe = (userId) => {
		uni.navigateTo({
			url: '/my/gird/zhuye?userId=' + userId
		})
	};

	// 获取关注列表
	const getList = () => {
		let data = {
			page: page.value,
			limit: limit.value,
			userId:uni.getStorageSync('userId')
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
			uni.stopPullDownRefresh();
		});
	};

	// 关注
	const guanzhu = (ite) => {
		proxy.$http.postJson("/app/follow/save", {
			userId: ite.userId,
			targetUserId:ite.targetUserId
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
			background: linear-gradient(121deg, #53D8A4, #ABEC4F);
			color: #FFFFFF;
			text-align: center;
			font-size: 24rpx;
			font-family: PingFang SC;
			font-weight: bold;
			display: flex;
			align-items: center;
			justify-content: center;
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