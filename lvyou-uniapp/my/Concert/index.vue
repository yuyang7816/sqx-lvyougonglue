<template>
	<view class="">
		<view class="infobox">

			<view class="TopImage">
				<image src="/my/static/concert/Concertbg.png" mode="aspectFill"></image>
				<view class="titname">演唱会</view>
			</view>

			<view class="listbox">
				<view class="listbox_ite" v-for="(item,index) in list" :key="index" @click="goDetail(item)">
					<image :src="item.img" mode="aspectFill" class="listbox_ite_img" ></image>
					<view class="listbox_ite_bot">
						<view class="listbox_ite_bot_title">{{item.title}}</view>
						<view class="listbox_ite_bot_tit">
							<view>演出时间:{{item.concertTime}}</view>
							<view class="margin-top-xs">演出地点: {{item.location}}</view>
						</view>
					</view>
				</view>

				<!-- 加载数据 -->
				<up-loadmore :status="status" v-if="list.length>0" color="#FFFFFF"/>
				<!-- 暂无数据 -->
				<empty v-if="list.length==0"></empty>
			</view>
		</view>
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
	const list = reactive([]);
	const page = ref('1');
	const pages = ref('1');
	const limit = ref('10');
	//加载状态
	const status = ref('loadmore');

	onLoad(option => {
		getConcertlist();
	});
	onShow(() => {

	});
	// 跳转详情
	const goDetail = (ite) => {
		uni.navigateTo({
			url: '/my/Concert/detail?concertId='+ite.id
		})
	};
	// 获取演唱会
	const getConcertlist = () => {
		proxy.$http.postJson('/app/concert/page', {
			page: page.value,
			limit: page.value
		}).then(res => {
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
		})
	};
	onPullDownRefresh(() => {
		page.value = 1;
		getConcertlist();
	});
	onReachBottom(() => {
		if (page.value < pages.value) {
			page.value += 1
			status.value = 'loading';
			getConcertlist();
		}
	});
</script>

<style lang="scss">
	page {
		background: #30015E;
	}

	.infobox {
		position: relative;
	}

	.TopImage {
		width: 100%;
		height: 376rpx;
		position: relative;

		image {
			width: 100%;
			height: 100%;
		}

		.titname {
			font-family: Source Han Sans CN;
			font-weight: 400;
			font-size: 56rpx;
			color: #999999;
			background: linear-gradient(98deg, #F7D994 2.1240234375%, #FFFFFF 52.001953125%, #F8DB9D 100%);
			-webkit-background-clip: text;
			-webkit-text-fill-color: transparent;
			position: absolute;
			top: 110rpx;
			left: 50rpx;
		}
	}

	.listbox {
		position: absolute;
		top: 240rpx;
		left: 0;
		right: 0;

		.listbox_ite {
			width: 686rpx;
			background: #FFFFFF;
			border-radius: 24rpx;
			margin: 19rpx auto;

			display: flex;

			.listbox_ite_img {
				padding: 30rpx 0 30rpx 30rpx;
				width: 200rpx;
				height: 250rpx;
				border-radius: 24rpx;
			}

			.listbox_ite_bot {
				padding: 30rpx 20rpx 30rpx 0;
				flex: 1;
				display: flex;
				flex-direction: column;
				justify-content: space-between;
				margin-left: 18rpx;

				.listbox_ite_bot_title {
					font-family: Source Han Sans CN;
					font-weight: bold;
					font-size: 32rpx;
					color: #4D4D4D;
					display: -webkit-box;
					-webkit-box-orient: vertical;
					-webkit-line-clamp: 2;
					overflow: hidden;
				}

				.listbox_ite_bot_tit {
					font-family: Source Han Sans CN;
					font-weight: 400;
					font-size: 26rpx;
					color: #999999;
				}
			}
		}
	}
</style>