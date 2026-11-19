<template>
	<view class="">
		<view v-for="(item, index) in list" :key="index" class="item">
			<view>
				<view style="color: #FFFFFF;font-size: 28upx;">
					<view style="margin-bottom: 8upx">{{item.title}}</view>

					<view style="margin-bottom: 8upx">{{item.content}}</view>
					<view style="margin-bottom: 8upx"> 创建时间：{{item.createTime}}</view>
					<view style="margin-bottom: 8upx;text-align: right;">
						<text v-if="item.type == 1" style="color: #FF5252;font-size: 32upx;font-weight: 600"><text
								class="text-olive">+</text>{{item.money}}元</text>
						<text v-if="item.type == 2" style="color: #FF5252;font-size: 32upx;font-weight: 600"><text
								class="text-red">-</text>{{item.money}}元</text>
					</view>
				</view>
			</view>
		</view>
		<!-- 加载更多 -->
		<up-loadmore :status="status" v-if="list.length>0" />
		<!-- 暂无数据 -->
		<empty v-if="list.length==0" content="暂无数据"></empty>
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
	//加载状态
	const status = ref('loadmore');
	const byUserId = ref('');

	onLoad(option => {
		uni.showLoading({
			title: '加载中...',
			icon: 'none'
		})
		if (option.byUserId) {
			byUserId.value = option.byUserId
			getList();
		}
	});
	onShow(() => {

	});
	// 获取收益列表
	const getList = () => {
		let data = {
			page: page.value,
			limit: limit.value,
			byUserId: byUserId.value,
			classify: 1,
		}
		proxy.$http.getT("/app/userMoney/selectConsortiaPersonMoneyDetails", data).then(res => {
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
			uni.hideLoading()
		});
	};
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
		background: #F2F2F2;
	}

	.item {
		background: #FFFFFF;
		padding: 30rpx;
		margin:20rpx 30rpx;
		font-size: 28rpx;
		border-radius: 16upx;
	}
</style>