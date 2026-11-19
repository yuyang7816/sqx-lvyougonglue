<template>
	<view style="padding-bottom: 200rpx;">
		<up-sticky bgColor="#FFFFFF">
			<view class="boxtop">
				<up-tabs :list="tablist" @click="click" :activeStyle="activeStyle" :inactiveStyle="inactiveStyle"
					:lineColor="lineColor" :scrollable="false" :current="current"></up-tabs>
			</view>
		</up-sticky>

		<view>
			<view class="margin">
				<Head :list="list" :className="true" @clickItem="clickItem"></Head>
			</view>
			<up-loadmore :status="status" v-if="list.length>0" />

			<empty contont="暂无数据" v-if="list.length == 0" />
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
		onReachBottom
	} from '@dcloudio/uni-app';

	import empty from '@/components/empty/empty.vue';
	import Head from '@/components/head/head.vue'
	const {
		proxy
	} = getCurrentInstance();
	// 创建响应式数据  
	const tablist = reactive([{
			name: '全部',
			status: ''
		},
		{
			name: '审核中',
			status: 0
		},
		{
			name: '已发布',
			status: 1
		},
		{
			name: '已拒绝',
			status: 2
		}
	]);
	const current = ref(0);
	const activeStyle = ref({
		'color': '#00BC4F',
		'fontWeight': 'bold',
		'fontSize': '32rpx',
		'transform': 'scale(1.05)'
	});
	const inactiveStyle = ref({
		'color': '#666666',
		'transform': 'scale(1)',
		'fontSize': '28rpx',
	});
	const lineColor = ref('linear-gradient(121deg, #53D8A4, #ABEC4F)');
	const list = reactive([]); //动态列表
	const pages = ref(1); //分页
	const page = ref(1);
	const limit = ref(10);
	const statusId = ref('')
	//加载状态
	const status = ref('loadmore');
	const userId = ref('')


	// 定义方法  
	const click = (item) => {
		console.log('item', item);
		statusId.value = item.status
		page.value = 1;
		getList()
	};
	// 动态点击事件
	const clickItem = (dt) => {
		console.log(dt, '====', dt.item.talkId)
		if (dt.index == 0) { //跳转详情
			uni.navigateTo({
				url: '/my/dynamic/listDetails?talkId=' + dt.item.id
			})
		} else if (dt.index == 1) { //跳转主页
			uni.navigateTo({
				url: '/my/gird/zhuye?userId=' + dt.item.userId
			})
		} else if (dt.index == 2) { //投诉
			uni.navigateTo({
				url: '/my/order/complain?byUserId=' + dt.item.userId + '&platform=3&platformId=' + dt.item
					.talkId
			})
		} else if (dt.index == 3) { //编辑
			console.log(dt.index, '编辑编辑编辑编辑', dt.item.talkId)
			uni.navigateTo({
				url: '/my/dynamic/releaseone?talkId=' + dt.item.id
			})
		} else if (dt.index == 4) { //删除
			deleteSave(dt.item.id)
		} else if (dt.index == 5) { //查看图片
			let Imagelist = dt.item.img
			saveImg(dt.imgIndex, Imagelist);
		} else if (dt.index == 6) { //点赞
			saveLove(dt.item.id, dt.item);
		} else if (dt.index == 7) { //收藏
			collectDt(dt.item.id, dt.item)
		}
	};
	// 点赞
	const saveLove = (talkId, item) => {
		let userId = uni.getStorageSync('userId');
		let data = {
			dataId: talkId,
			userId: userId,
			type: 1 //1动态 2景点 3路线
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
	//收藏
	const collectDt = (talkId, item) => {
		let userId = uni.getStorageSync('userId');
		let data = {
			dataId: talkId,
			userId: userId,
			type: 1 //1动态 2景点 3路线 4商户
		}
		proxy.$http.postJson('/app/collect/save', data).then(res => {
			if (res.code == 0) {
				item.isCollect = !item.isCollect
				if (item.isCollect) {
					item.collectNum += 1
				} else {
					item.collectNum -= 1
				}

			} else {
				uni.showToast({
					title: res.msg,
					icon: 'none'
				})
			}
		});
	};
	// 查看图片
	const saveImg = (index, Imagelist) => {
		uni.previewImage({
			current: index,
			urls: Imagelist
		});
	};
	// 删除动态
	const deleteSave = (talkId) => {
		uni.showModal({
			title: '删除提示',
			content: '您确认要删除当前动态吗？',
			success: function(res) {
				if (res.confirm) {
					uni.showLoading({
						title: '删除中...'
					})
					let data = {
						id: talkId
					}
					proxy.$http.postJson('/app/moments/delete', data).then(res => {
						if (res.code == 0) {
							uni.hideLoading();
							page.value = 1;
							getList();
						} else {
							uni.hideLoading();
							uni.showToast({
								title: res.msg,
								icon: 'non'
							})
						}
					});
				} else if (res.cancel) {
					console.log('用户点击取消');
				}
			}
		});
	};
	// 获取动态列表
	const getList = () => {
		let data = {
			page: page.value,
			limit: limit.value,
			// byUserId: userId.value,
			userId: userId.value,
			// followUserId: 0,
			auditStatus: statusId.value,
			userId: userId.value
		}
		proxy.$http.postJson("/app/moments/page", data).then(res => {
			if (res.code == 0) {
				pages.value = res.data.pages
				if (page.value < pages.value) {
					status.value = 'loadmore';
				} else {
					status.value = 'nomore';
				}

				res.data.records.forEach(d => {
					let imgarr = []
					d.imgleng = []
					if (d.img) {
						d.img.split(',').forEach((dite, ind) => {
							if (dite) {
								imgarr.push(dite)
								d.imgleng.push(dite)
							}
						})
						d.img = imgarr.splice(0, 3)
					}
				})

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
	onLoad(option => {
		userId.value = uni.getStorageSync('userId');
		getList();
	});
	onShow(() => {

	});
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
		background: #F2F5FA;
	}

	.boxtop {
		background: #FFFFFF;
	}
</style>