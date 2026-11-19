<template>
	<view>
		<view class="list-box">
			<view class="list-box-item" v-for="(item,index) in chatList" :key="index" @click="goNav(item)">
				<view class="list-box-item-info">
					<view class="list-box-item-info-l">
						<image :src="ite" v-for="(ite,ind) in item.coverImg" :key="ind"
							v-if="item.coverImg.length>=8" mode=""></image>
						<image :src="qunlogo?qunlogo:'../../static/image/qun/qunlogo.png'"
							v-if="item.coverImg.length<=8" mode="" style="width: 120rpx;height: 120rpx;">
						</image>
					</view>
					<view class="list-box-item-info-r ">
						<view class="list-box-item-info-r-name ">{{item.name}}</view>
						<view class="list-box-item-info-r-num">
							已加入{{item.joinNum}}人
						</view>
						<view class="list-box-item-info-r-detail">
							{{item.description}}
						</view>
					</view>
				</view>
			</view>
		</view>
		<up-loadmore :status="status" v-if="chatList.length>0" />

		<empty contont="暂无群聊" v-if="chatList.length == 0" />
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

	const userId = ref('');
	const chatList = reactive([]);
	const pages = ref(1); //分页
	const page = ref(1); //分页
	const limit = ref(20);
	const qunlogo = ref(''); //默认群logo
	//加载状态
	const status = ref('loadmore');

	onLoad(option => {
		userId.value = uni.getStorageSync('userId');
		qunlogo.value = uni.getStorageSync('qunlogo');

	});
	onShow(() => {
		getCharList();
	});
	// 跳转群聊详情
	const goNav=(item)=> {
		uni.navigateTo({
			url: '/my/GroupChat/groupChatDetail?id=' + item.id
		})
	};
	// 获取群列表
	const getCharList = () => {
		let data = {
			page: page.value,
			limit: limit.value,
			userId: uni.getStorageSync('userId'),
		}
		proxy.$http.postJson('/app/groupInfo/joinList', data).then(res => {
			if (res.code == 0) {
				pages.value = res.data.pages
				if (res.data.records.length > 0) {
					res.data.records.map(item => {
						if (item.coverImg) {
							item.coverImg = item.coverImg.split(',')
						} else {
							item.coverImg = []
						}
					})
				}
				if (page.value < pages.value) {
					status.value = 'loadmore';
				} else {
					status.value = 'nomore';
				}
				if (page.value === 1) {
					chatList.splice(0, chatList.length, ...res.data.records);
				} else {
					chatList.push(...res.data.records);
				}
			}
			status.value = 'nomore';
			uni.stopPullDownRefresh()
		})
	};

	onPullDownRefresh(() => {
		page.value = 1;
		getCharList();
	});
	onReachBottom(() => {
		if (page.value < pages.value) {
			page.value += 1
			status.value = 'loading';
			getCharList();
		}
	});
</script>

<style lang="scss">
	.list-box {
		width: 100%;

		.list-box-item {
			margin: 20rpx 30rpx 40rpx;
			background-color: #FFFFFF;
			border-radius: 24rpx;

			.list-box-item-info {
				width: 100%;
				display: flex;
				align-items: center;
				justify-content: space-between;

				.list-box-item-info-l {
					width: 120rpx;
					height: 120rpx;
					background-color: #f5f5f5;
					align-content: start;
					display: flex;
					justify-content: space-between;
					flex-wrap: wrap;
				}

				.list-box-item-info-r {
					width: calc(100% - 140rpx);

					.list-box-item-info-r-name {
						width: 100%;
						font-family: PingFang SC;
						font-weight: 800;
						font-size: 30rpx;
						color: #333333;
					}

					.list-box-item-info-r-num {
						width: 100%;
						font-family: PingFang SC;
						font-weight: 500;
						font-size: 24rpx;
						color: #333333;
					}

					.list-box-item-info-r-detail {
						width: 100%;
						font-family: PingFang SC;
						font-weight: 500;
						font-size: 24rpx;
						color: #999999;
						overflow: hidden;
						white-space: nowrap;
						text-overflow: ellipsis;
						-o-text-overflow: ellipsis;
					}

				}
			}


		}
	}
</style>