<template>
	<view class="">

		<view class="listbox" v-for="(item,index) in list" :key="index" @click="goDetail(item)">
			<view class="listbox-image">
				<image :src="item.imgUrl" mode="aspectFill"></image>
				<view class="listbox-image_label">
					<image src="/my/static/route/lvsc.png"></image>
					{{item.collectCount}}收藏
				</view>
			</view>
			<view class="listbox_cot">
				<view class="listbox_cot_tit">{{item.title}}</view>
				<view class="listbox_cot_lavel">
					<view class="listbox_cot_lavelbox" v-for="(ite,ind) in item.tags" :key="ind">
						{{ite}}
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
	const userId = ref('');
	const list = reactive([]);
	const pages = ref(1); //分页
	const page = ref(1); //分页
	const limit = ref(10);
	//加载状态
	const status = ref('loadmore');

	const city = ref('');

	onLoad(option => {
		if (option.city) {
			city.value = option.city
		}
		getlist()
	});
	onShow(() => {

	});
	// 跳转详情
	const goDetail = (e) => {
		uni.navigateTo({
			url: '/my/Route/detail?id=' + e.id
		})
	};
	//获取路线列表
	const getlist = () => {
		let data = {
			page: page.value,
			limit: limit.value,
			city: city.value
		}
		proxy.$http.postJson('/app/baseInfo/pageByCity', data).then(res => {
			if (res.code == 0) {
				pages.value = res.data.pages
				if (page.value < pages.value) {
					status.value = 'loadmore';
				} else {
					status.value = 'nomore';
				}
				res.data.records.forEach(f => {
					if (f.tags) {
						f.tags = f.tags.split(',')
					}
					if (f.collectCount) {
						if (f.collectCount >= 10000) {
							f.collectCount = Number(f.collectCount / 10000).toFixed(0) + '万'
						} else {
							f.collectCount = Number(f.collectCount).toFixed(0)
						}
					} else {
						f.collectCount = '0'
					}

				})
				// 处理收藏大于1万

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
		getlist();
	});
	onReachBottom(() => {
		if (page.value < pages.value) {
			page.value += 1
			status.value = 'loading';
			getlist();
		}
	});
</script>

<style lang="scss">
	page {
		background: #F9F9F9;
	}

	.listbox {
		margin: 20rpx 30rpx;
		background: #FFFFFF;
		border-radius: 24rpx;

		.listbox-image {
			width: 100%;
			height: 264rpx;
			border-radius: 24rpx 24rpx 0 0;
			position: relative;

			image {
				width: 100%;
				height: 100%;
				border-radius: 24rpx 24rpx 0 0;
			}

			.listbox-image_label {
				height: 44rpx;
				background: rgba(0, 0, 0, 0.5);
				border-radius: 22rpx;
				padding: 0 20rpx;
				font-family: PingFang SC;
				font-weight: 400;
				font-size: 22rpx;
				color: #FFFFFF;
				display: flex;
				align-items: center;
				position: absolute;
				bottom: 10rpx;
				left: 10rpx;

				image {
					width: 24rpx;
					height: 24rpx;
				}
			}
		}

		.listbox_cot {
			border-radius: 0 0 24rpx 24rpx;
			padding: 20rpx 30rpx;

			.listbox_cot_tit {
				font-family: PingFang SC;
				font-weight: 800;
				font-size: 34rpx;
				color: #373737;
			}

			.listbox_cot_lavel {
				display: flex;
				align-items: center;
				margin-top: 10rpx;

				.listbox_cot_lavelbox {
					height: 40rpx;
					background: #D6FFE7;
					border-radius: 8rpx;
					font-family: PingFang SC;
					font-weight: 500;
					font-size: 24rpx;
					color: #00BC4F;
					display: flex;
					align-items: center;
					padding: 0 10rpx;
					margin-right: 10rpx;
				}
			}
		}
	}
</style>