<template>
	<view class="">
		<view class="list-item" :show-arrow="false" v-for="(item, index) in list" :key="index">
			<view class="list-item-wrap">
				<view v-if="item.imgUrl">
					<image v-for="(imgs,ind) in item.imgUrl" :key="ind" :src="imgs"
						:class="item.imgUrl.length>1?'img2':'img1'" @click="selectImage(item.imgUrl,ind)"
						mode="aspectFill">
					</image>
				</view>
				<view style="padding: 20rpx 30rpx;">
					<view class="list-title">投诉内容：{{ item.instruction }}</view>
					<view class="list-title" style="margin-top: 10upx;">被投诉人：{{ item.name }}</view>
					<view style="display: flex;justify-content: space-between;margin-top: 10rpx;">
						<view>
							<text>投诉时间：{{ item.createTime }}</text>
						</view>
					</view>
					<view class="list-title" style="margin-top: 10upx;" v-if="item.status==4">状态：通过但不做处理
					</view>
					<view class="list-title" style="margin-top: 10upx;" v-if="item.status==1">状态：等待处理 </view>
					<view class="list-title" style="margin-top: 10upx;" v-if="item.status==2">状态：封禁账号</view>
					<view class="list-title" style="margin-top: 10upx;" v-if="item.status==3">状态：已驳回</view>
					<view class="list-title" style="margin-top: 10upx;" v-if="item.status==3">
						驳回内容：{{ item.feedback }}</view>
				</view>
			</view>
		</view>
		<!-- 加载更多 -->
		<up-loadmore :status="status" v-if="list.length>0" />
		<!-- 暂无数据 -->
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
		getList()
	});
	onShow(() => {

	});
	// 查看图片
	const selectImage = (image, index) => {
		let _this = this;
		let imgsArray = image;
		uni.previewImage({
			current: index,
			urls: imgsArray
		});
	};
	// 获取我的投诉列表
	const getList = () => {
		// "targetType": 1, //1用户 2群聊
		// "reason": "", //投诉原因
		// "status": 1, //状态1等待处理 2封禁账号 3已驳回 4通过但不做处理
		// "page": 1, //当前页
		// "limit": 10 //页大小
		let userId = uni.getStorageSync('userId')
		let data = {
			page: page.value,
			limit: limit.value,
			userId:userId
		}
		proxy.$http.postJson("/app/complaint/page", data).then(res => {
			if (res.code == 0) {
				pages.value = res.data.pages
				if (page.value < pages.value) {
					status.value = 'loadmore';
				} else {
					status.value = 'nomore';
				}
				res.data.records.forEach(d => {
					if (d.imgUrl) {
						d.imgUrl = d.imgUrl.split(',');
					}
				});
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
	page {
		background-color: #F5F5F5;
	}

	.list-item {
		background-color: #FFFFFF;
		border-radius: 16rpx;
		margin: 20rpx;
	}

	.img1 {
		width: 100%;
		height: 400rpx;
		border-radius: 12rpx;
	}

	.img2 {
		width: 210rpx;
		height: 210rpx;
		margin-top: 15rpx;
		margin-right: 10rpx;
		margin-left: 16rpx;
		border-radius: 12rpx;
	}
</style>