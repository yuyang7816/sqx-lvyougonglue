<template>
	<view class="">

		<view class="dtCont">
			<view class="Dtbbox">
				<view class="Dtbbox_left">
					<travellist v-for="(item,index) in listLeft" :key="index" :InfoItem="item.moments" @clickItem="clickItem">
					</travellist>
				</view>
				<view class="Dtbbox_right">
					<travellist v-for="(item,index) in listRight" :key="index" :InfoItem="item.moments" @clickItem="clickItem">
					</travellist>
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
	// 动态列表
	import travellist from '@/components/travellist/index.vue'
	const list = reactive([]); //列表
	const listLeft = reactive([]);
	const listRight = reactive([]);
	const pages = ref(1); //分页
	const page = ref(1); //分页
	const limit = ref(20);
	const userId = ref('');
	//加载状态
	const status = ref('loadmore');


	onLoad(option => {
		userId.value = uni.getStorageSync('userId');
		getTaskList();
	});
	onShow(() => {

	});

	// 动态点击事件
	const clickItem = (dt) => {
		console.log(dt, '====', dt.item.talkId)
		let token = uni.getStorageSync('token');
		if (!token) {
			goLoginInfo();
			return;
		}
		if (dt.index == 0) { //跳转详情
			uni.navigateTo({
				url: '/my/dynamic/listDetails?talkId=' + dt.item.id
			})
		} else if (dt.index == 1) { //跳转主页
			uni.navigateTo({
				url: '/my/gird/zhuye?userId=' + dt.item.userId
			})
		}else if (dt.index == 6) { //点赞
			saveLove(dt.item.id, dt.item);
		}
	};
	// 点赞
	const saveLove = (talkId, item) => {
		let userId = uni.getStorageSync('userId');
		let data = {
			dataId: talkId,
			userId: userId,
			type: 1
		}
		proxy.$http.postJson('/app/like/save', data).then(res => {
			if (res.code == 0) {
				item.isLike = !item.isLike
				if (item.isLike) {
					item.likeNum += 1
				} else {
					item.likeNum -= 1
				}
			} else {
				uni.showToast({
					title: res.msg,
					icon: 'none'
				})
			}
		});
	};
	// 获取动态列表
	const getTaskList = () => {
		let data = {
			page: page.value,
			limit: limit.value,
			userId: userId.value,
			type: 1
		}
		status.value = 'loadmore';
		proxy.$http.postJson("/app/like/page", data).then(res => {
			if (res.code == 0) {
				pages.value = res.data.pages
				if (page.value < pages.value) {
					status.value = 'loadmore';
				} else {
					status.value = 'nomore';
				}
				res.data.records.forEach(d => {
					let imgarr = []
					if (d.moments && d.moments.img) {
						d.moments.img = d.moments.img.split(',')
					}
				})

				if (page.value === 1) {
					list.splice(0, list.length, ...res.data.records);
				} else {
					list.push(...res.data.records);
				}
				// 处理动态列表左右数据
				list.forEach((item, index) => {
					if (item.moments && item.moments.img) {
						item.moments.img = item.moments.img[0]
					}
					if (item.moments) {
						if (index % 2 === 0) {
							listLeft.push(item)
						} else {
							listRight.push(item)
						}
					}
				})

			}
			status.value = 'nomore';
			uni.stopPullDownRefresh();
		});
	};
	onPullDownRefresh(() => {
		page.value = 1;
		getTaskList();
	});
	onReachBottom(() => {
		if (page.value < pages.value) {
			page.value += 1
			status.value = 'loading';
			getTaskList();
		}
	});
</script>

<style lang="scss">
	page {
		background: #F2F2F2;
	}

	// 动态列表
	.dtCont {
		padding: 0 30rpx;

		.Dtbbox {
			display: flex;
			justify-content: space-between;
			margin-top: 20rpx;

			.Dtbbox_left {
				width: 338rpx;
				height: auto;

			}

			.Dtbbox_right {
				width: 338rpx;
				height: auto;
			}
		}
	}
</style>