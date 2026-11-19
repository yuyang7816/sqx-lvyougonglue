<template>
	<view style="padding-bottom: 120rpx;">
		<view v-for="(item, index) in list" :key="index" class="item">
			<view class="flex justify-between"
				style="font-size: 30upx;width: 100%;overflow: hidden;text-overflow: ellipsis;white-space:nowrap">
				<view>{{ item.title }}</view>
				<view v-if="item.isSee == 2"
					style="height: 32rpx;width: 32rpx;border-radius: 100rpx;background-color: red;color: #FFF;text-align: center;">
				</view>
			</view>
			<view style="color: #999999;font-size: 28upx;margin-top: 10upx;">{{ item.content }}</view>

			<view style="color: #999999;font-size: 26upx;text-align: right;">{{ item.createAt }}
			</view>
		</view>
		<up-loadmore :status="status" v-if="list.length > 0" />
		<empty v-if="list.length === 0" des="暂无消息" show="false"></empty>
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
	//空内容
	import empty from '@/components/empty/empty.vue'
	const {
		proxy
	} = getCurrentInstance();
	const list = reactive([]);
	const page = ref(1);
	const pages = ref(1);
	const limit = ref(10);
	const status = ref('loadmore');
	const userId = ref('');

	
	//获取消息列表
	const loadData = () => {
		let token = uni.getStorageSync('token');
		if (token) {
			let data = {
				page: page.value,
				limit: limit.value,
				type:1 // 1系统公告 2评论消息
			}
			proxy.$http.postJson('/app/message/page', data).then(res => {
				if (res.code === 0) {
					pages.value = res.data.pages;
					if (page.value < pages.value) {
						status.value = 'loadmore'
					} else {
						status.value = 'nomore'
					}
					if (page.value == 1) {
						list.splice(0, list.length, ...res.data.records)
					} else {
						list.push(...res.data.records)
					}
				}
				status.value = 'nomore'
				uni.hideLoading();
				uni.stopPullDownRefresh();
			});
		};
	};
	onLoad(option => {

		userId.value = uni.getStorageSync('userId')
		loadData();
	});
	onShow(() => {

	});
	onPullDownRefresh(() => {
		if (uni.getStorageSync('token')) {
			page.value = 1;
			loadData();
		}
	});
	onReachBottom(() => {
		if (page.value < pages.value && uni.getStorageSync('token')) {
			page.value += 1
			loadData();
		}
	});
</script>

<style lang="scss">
	page {
		background: #F2F2F2;
	}

	.item {
		background: #FFFFFF;
		padding: 16rpx;
		margin: 16rpx;
		font-size: 28rpx;
		border-radius: 16upx;


	}
</style>