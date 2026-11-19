<template>
	<view class="">
		<view class="box" v-for="(item,index) in list">
			<view class="box_left">
				<image :src="item.blackUserAvatar ? item.blackUserAvatar : '/static/logos.png'" mode="aspectFill"
					style="border-radius: 50rpx;"></image>
				<view class="username_view">{{item.blackUserName}}</view>
			</view>
			<view class="yichu_view" @tap="saveLH(item.id)">移除</view>
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

	onLoad(option => {
		getList();
	});
	onShow(() => {

	});
	// 移出黑名单
	const saveLH = (id) => {
		let data = {
			id: id
		}
		uni.showModal({
			title: '温馨提示',
			content: '是否将当前用户移出黑名单？',
			showCancel: true,
			cancelText: '取消',
			confirmText: '确认',
			success: red => {
				if (red.confirm) {
					proxy.$http.postJson('/app/blackList/delete', data).then(res => {
						if (res.code == 0) {
							uni.showToast({
								title:'已成功将当前用户移除黑名单!',
								icon:'none'
							})
							page.value = 1;
							getList();
						} else {
							uni.showToast({
								title:res.msg,
								icon:'none'
							})
						}
					});
				}
			}
		});
	};
	// 获取黑名单列表
	const getList = () => {
		let data = {
			page: page.value,
			limit: limit.value,
		}
		proxy.$http.postJson("/app/blackList/page", data).then(res => {
			if (res.code == 0) {
				pages.value = res.data.totalPage
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
	.box {
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 20rpx 30rpx;

		.box_left {
			display: flex;
			align-items: center;

			image {
				width: 80rpx;
				height: 80rpx;
				border-radius: 55rpx;
			}

			.username_view {
				font-size: 30rpx;
				margin-left: 20rpx;
			}
		}

		.yichu_view {
			width: 115rpx;
			height: 60rpx;
			background: linear-gradient(90deg, #2FC959 0%, #62DF9D 47%);
			border-radius: 8rpx;
			font-size: 24rpx;
			font-family: PingFang SC;
			font-weight: 500;
			color: #FFFFFF;
			line-height: 60rpx;
			text-align: center;
		}
	}
</style>