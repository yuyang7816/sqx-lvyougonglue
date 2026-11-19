<template>
	<view>
		<view class="navbar">
			<view v-for="(item, index) in tabList" :key="index" class="nav-item"
				:class="{ current: tabCurrentIndex === item.state }" @click="tabClicks(item.state)">
				{{ item.text }}
			</view>
		</view>

		<view v-for="(item,index) in list" v-if="tabCurrentIndex == 0" @click="goAdd(1,0,item.id)">
			<view
				style="margin-top: 20upx;color: #333333;display: flex;align-items: center;background: #FFFFFF;padding: 0rpx 20rpx;">
				<view style="height: 80upx;line-height: 80upx;width: 80%;">
					{{item.content}}
				</view>
				<view style="width: 20%;display: flex;justify-content: flex-end;">
					<image src="../../static/image/my/right.png" style="width: 15rpx;height: 30rpx;" mode="widthFix">
					</image>
				</view>
			</view>
		</view>
		<up-loadmore :status="status" v-if="tabCurrentIndex == 0 && list.length != 0" />

		<view v-if="tabCurrentIndex == 1 && contentMsg" @click="goAdd(1,1)"
			style="margin-top: 20upx;color: #333333;display: flex;align-items: center;background: #FFFFFF;padding: 0rpx 20rpx;">
			<view style="height: 80upx;line-height: 80upx;width: 80%;">
				{{contentMsg}}
			</view>
			<view style="width: 20%;display: flex;justify-content: flex-end;">
				<image src="../../static/image/my/right.png" style="width: 15rpx;height: 30rpx;" mode="widthFix">
				</image>
			</view>
		</view>

		<view class="padding-lr-sm" style="position: fixed;bottom: 40upx;width: 95%;z-index: 999;"
			v-if="tabCurrentIndex == 0">
			<u-button class="" :custom-style="customStyle" :hair-line="false" @click="goAdd(0,0)">
				<u-icon name="plus" color="#FFFFFF" size="20" style="margin-right: 10upx;"></u-icon>
				添加
			</u-button>
		</view>
		<view class="padding-lr-sm" style="position: fixed;bottom: 40upx;width: 95%;z-index: 99999;"
			v-if="tabCurrentIndex == 1 && contentMsg == ''">
			<u-button class="" :custom-style="customStyle" :hair-line="false" @click="goAdd(0,1)">
				<u-icon name="plus" color="#FFFFFF" size="20" style="margin-right: 10upx;"></u-icon>
				添加
			</u-button>
		</view>

		<empty v-if="tabCurrentIndex == 0 && list.length == 0" content='暂无常用语信息'></empty>
		<empty v-if="tabCurrentIndex == 1 && contentMsg == ''" content='暂无自动回复信息'></empty>
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
	import empty from '@/components/empty/empty.vue'
	const {
		proxy
	} = getCurrentInstance();

	const tabList = reactive([{
			state: 0,
			text: '常用词语',
			totalElements: 0
		},
		{
			state: 1,
			text: '自动回复',
			totalElements: 0
		}
	]);
	const tabCurrentIndex = ref(0);
	const list = reactive([]);
	const page = ref(1);
	const pages = ref(1);
	const limit = ref(10);
	const userId = ref('');
	const contentMsg = ref('');
	//加载状态
	const status = ref('loadmore');
	const customStyle = ref({
		backgroundColor: "#2FD7C8",
		border: 0,
		color: "#FFFFFF",
		height: '98rpx'
	});
	const goAdd = (index, type, id) => {
		uni.navigateTo({
			url: '/pages/msg/add?type=' + index + '&type1=' + type + '&id=' + id
		});
	};
	//顶部渠道点击
	const tabClicks = (index) => {
		list.value = [];
		page.value = 1;
		tabCurrentIndex.value = index;
		loadData();
	};
	//获取消息列表
	const loadData = () => {
		if (tabCurrentIndex.value == 0) {
			let data = {
				page: page.value,
				limit: limit.value,
				state: 20
			}
			status.value = 'loadmore'
			proxy.$http.getT('/app/message/selectMessageByUserId', data).then(res => {
				if (res.code === 0) {
					pages.value = res.data.totalPage;
					if (page.value < pages.value) {
						status.value = 'loadmore'
					} else {
						status.value = 'nomore'
					}
					if (page.value == 1) {
						list.splice(0, list.length, ...res.data.list)
					} else {
						list.push(...res.data.list)
					}
				}
				status.value = 'nomore'
				uni.hideLoading();
				uni.stopPullDownRefresh();
			});
		} else {
			proxy.$http.getT("/app/user/selectUserById").then(res => {
				if (res.code == 0) {
					contentMsg.value = res.data.msg ? res.data.msg : '';
				}
				uni.hideLoading();
			});
		}
	};
	onLoad(option => {
		loadData()
		userId.value = uni.getStorageSync('userId')

	});
	onShow(() => {
		page.value = 1;
		loadData();
	});
	onPullDownRefresh(() => {
		page.value = 1;
		loadData();
	});
	onReachBottom(() => {
		if (page.value < pages.value) {
			page.value += 1
			status.value = 'loading';
			loadData();
		}
	});
</script>

<style lang="scss">
	page {
		background-color: #F5F5F5;
	}

	.bg {
		background-color: #FFFFFF;
	}

	.navbar {
		display: flex;
		height: 40px;
		padding: 0 5px;
		background: #FFFFFF;
		box-shadow: 0 1px 5px rgba(0, 0, 0, 0.06);
		position: relative;
		z-index: 10;

		.nav-item {
			flex: 1;
			display: flex;
			justify-content: center;
			align-items: center;
			height: 100%;
			font-size: 15px;
			color: #000000;
			position: relative;

			&.current {
				color: #2FD7C8;

				&:after {
					content: '';
					position: absolute;
					left: 50%;
					bottom: 0;
					transform: translateX(-50%);
					width: 44px;
					height: 0;
					border-bottom: 2px solid #2FD7C8;

				}
			}
		}
	}
</style>