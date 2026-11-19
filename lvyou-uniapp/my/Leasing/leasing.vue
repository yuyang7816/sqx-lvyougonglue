<template>
	<view class="">
		<view class="box">
			<view v-for="(item,index) in list" :key="index" @click="changeShop(item)" class="boxitem">
				<image :src="item.img" mode="aspectFill"></image>
				<view class="boxitem_right">
					<view class="boxitem_right_name">{{item.name}}</view>
					<view class="boxitem_rightinfo">
						<view class="boxitem_rightinfo_rate">{{item.grade}}分</view>
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
	const list = reactive([]); //列表
	const pages = ref(1); //分页
	const page = ref(1); //分页
	const limit = ref(20);
	const userId = ref('');
	//加载状态
	const status = ref('loadmore');
	onLoad(option => {
		userId.value = uni.getStorageSync('userId');
		getlist();
	});
	onShow(() => {

	});
	// 跳转玩乐租赁详情
	const changeShop = (item) => {
		uni.$emit('shopInfo', item)
		uni.navigateBack()
	};
	//获取列表
	const getlist = () => {
		let data = {
			page: page.value,
			limit: limit.value,
		}
		status.value = 'loadmore';
		proxy.$http.postJson('/app/shop/page', data).then(res => {
			if (res.code == 0) {
				pages.value = res.data.pages
				if (page.value < pages.value) {
					status.value = 'loadmore';
				} else {
					status.value = 'nomore';
				}
				res.data.records.forEach(d => {
					if (d.tags) {
						d.tags = d.tags.split(',')
					}
				})

				if (page.value === 1) {
					list.splice(0, list.length, ...res.data.records);
				} else {
					list.push(...res.data.records);
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
	onPullDownRefresh(() => {
		let token = uni.getStorageSync('token');
		if (!token) {
			goLoginInfo();
			return;
		}
		page.value = 1;
		getlist();
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
			getlist();
		}
	});
</script>

<style lang="scss">
	page {
		background: #F2F2F2;
	}

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
						font-size: 24rpx;
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