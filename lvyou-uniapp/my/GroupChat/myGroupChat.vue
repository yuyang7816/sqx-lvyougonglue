<template>
	<view>
		<up-sticky offset-top="0" bgColor="#fff">
			<up-tabs :list="tablist" @click="clickTab" :lineColor="lineColor" :activeStyle="activeStyle"
				:inactiveStyle="inactiveStyle" :scrollable="false" :current="current"></up-tabs>
		</up-sticky>

		<view class="list-box">
			<view class="list-box-item" v-for="(item,index) in chatList" :key="index">
				<view class="list-box-item-info">
					<view class="list-box-item-info-l">
						<image :src="ite" v-for="(ite,ind) in item.coverImg" :key="ind" v-if="item.coverImg.length>=8"
							mode=""></image>

						<image :src="qunlogo?qunlogo:'../../static/image/qun/qunlogo.png'"
							v-if="item.coverImg.length<=8" mode="" style="width: 120rpx;height: 120rpx;">
						</image>
					</view>

					<view class="list-box-item-info-r ">
						<view class="list-box-item-info-r-name ">
							<view class="list-box-item-info-r-name-names">
								{{item.name}}
							</view>
							<view v-if="item.applyCondition != 0" class="list-box-item-info-r-name-price">
								<text>¥</text>{{item.charge?item.charge:0}}
							</view>
							<view v-else class="list-box-item-info-r-name-price">
								免费
							</view>
						</view>
						<view class="list-box-item-info-r-num">
							已加入{{item.joinNum}}人
						</view>
						<view class="list-box-item-info-r-detail">
							{{item.description}}
						</view>
					</view>
				</view>
				<view class="list-box-item-info-status flex align-center justify-between">
					<view class="list-box-item-info-status-l" style="color: #999999;" v-if="item.status ==3">
						已下架
					</view>

					<block v-else>
						<view v-if="item.auditStatus == 0" class="list-box-item-info-status-l">
							审核中
						</view>
						<view v-if="item.auditStatus == 1" class="list-box-item-info-status-l">
							审核成功
						</view>
						<view style="color: red;" v-if="item.auditStatus == 2" class="list-box-item-info-status-l">
							审核拒绝
						</view>

					</block>
					<view class="list-box-item-info-status-r flex aligin-center">
						<view @click="upOrDn(item)" v-if="item.auditStatus == 1 && item.status == 0"
							class="list-box-item-info-status-r-btnl">
							下架
						</view>
						<view @click="upOrDn(item)" v-if="item.auditStatus == 1 && item.status == 1"
							class="list-box-item-info-status-r-btnl">
							上架
						</view>
						<view @click="upData(item)" class="list-box-item-info-status-r-btnr">
							修改
						</view>
						<view @click="upDataQrcode(item)" v-if="item.status == 3"
							class="list-box-item-info-status-r-btnr">
							更新二维码
						</view>
					</view>
				</view>
			</view>
		</view>
		<up-loadmore :status="status" v-if="chatList.length>0" />

		<empty contont="暂无群聊" v-if="chatList.length == 0" />


		<!-- 发布群聊 -->
		<view @click="goAddChat()" class="sendChat ">
			<u-icon name="plus" color="#ffffff" :bold="true" size="24"></u-icon>
		</view>

		<!-- 更新群二维码弹窗 -->
		<up-popup v-model:show="showQrCode" @close="closeQr" round="24" mode="center" closeable>
			<view class="qrCode">
				<view class="qrCode-title ">更新群二维码</view>
				<view class="qrCode-img ">
					<image @click="uploadImg()" :show-menu-by-longpress="true" lazy-load :src="groupQrcode"
						mode="aspectFill"></image>
				</view>
				<view class="qrCode-input" @click="showTime = true">
					<up-input placeholder="请选择群有效期" border="surround" v-model="groupExpiredDate" disabled
						disabledColor="#ffffff"></up-input>
				</view>
				<view @click="updataQrCodes()" class="qrCode-btn">确认更新</view>
			</view>
		</up-popup>

		<!-- 选择时间弹窗 -->
		<up-datetime-picker :show="showTime" v-model="groupExpiredDates" mode="datetime" @cancel="close" @close="close"
			@confirm="confirmTime" :formatter="formatter"></up-datetime-picker>

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
	//引入内容为空组件
	import empty from '@/components/empty/empty.vue';
	import config from '@/common/config.js';
	import {
		timeFormat
	} from '@/uni_modules/uview-plus';
	const {
		proxy
	} = getCurrentInstance();

	const userId = ref('');
	const chatList = reactive([]); //列表
	const pages = ref(1); //分页
	const page = ref(1); //分页
	const limit = ref(20);
	const qunlogo = ref(''); //默认群logo
	//加载状态
	const status = ref('loadmore');
	// 创建响应式数据  
	const lineColor = ref('linear-gradient(121deg, #53D8A4, #ABEC4F)');
	const activeStyle = ref({
		color: '#00BC4F',
		fontWeight: 'bold',
		fontSize: '32rpx',
		transform: 'scale(1.05)'
	});
	const inactiveStyle = ref({
		color: '#666666',
		transform: 'scale(1)'
	});

	const tablist = reactive([{
			name: '全部群',
			state: ''
		},
		{
			name: '审核中',
			state: 0
		},
		{
			name: '审核成功',
			state: 1
		},
		{
			name: '审核拒绝',
			state: 2
		},
		{
			name: '已下架',
			state: 1
		}
	]);
	const current = ref(0);

	const showQrCode = ref(false); //群二维码弹框
	const groupQrcode = ref(''); //二维码
	const groupId = ref(''); //群id
	const showTime = ref(false); //群有效期弹框
	const groupExpiredDate = ref(''); //群聊有效期
	const groupExpiredDates = ref(Date.now()); //群聊有效期


	onLoad(option => {
		console.log(groupExpiredDates.value)
		userId.value = uni.getStorageSync('userId');
		qunlogo.value = uni.getStorageSync('qunlogo');
	});
	onShow(() => {
		getCharList();
	});
	//创建群聊
	const goAddChat = () => {
		uni.navigateTo({
			url: '/my/GroupChat/addGroupChat'
		})
	};
	//打开更新群二维码弹窗
	const upDataQrcode = (item) => {
		groupQrcode.value = item.qrCode
		groupId.value = item.id
		groupExpiredDate.value = ''
		showQrCode.value = true
	};
	// tab点击
	const clickTab = (e) => {
		console.log(e)
		current.value = e.index
		page.value = 1
		getCharList();
	};
	//修改群
	const upData = (item) => {
		uni.navigateTo({
			url: '/my/GroupChat/addGroupChat?id=' + item.id
		})
	};
	// 获取群列表
	const getCharList = () => {
		let statusT = tablist[current.value].state
		let data = {
			page: page.value,
			limit: limit.value,
			userId: uni.getStorageSync('userId'),
			auditStatus: statusT,
			status:current.value==4?1:''
		}
		proxy.$http.postJson('/app/groupInfo/owner', data).then(res => {
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
	//上下架
	const upOrDn = (item) => {
		let type = item.status //群状态（0正常 1禁用 2过期 3人数已达最大）
		let data = {
			groupId: item.id
		}
		// if (type == 4) {
		// 	data.groupStatus = 0
		// } else {
		// 	data.groupStatus = 4
		// }
		uni.showModal({
			title: '提示',
			content: type == 0 ? '下架后，群聊不可见，确认下架吗？' : '确认上架吗？',
			confirmText: '确认',
			confirmColor: '#00BC4F',
			complete(ret) {
				if (ret.confirm) {
					proxy.$http.postJson('/app/groupInfo/enable', data).then(res => {
						if (res.code == 0) {
							if (type == 1) {
								uni.showToast({
									title: '已上架'
								})
							} else {
								uni.showToast({
									title: '已下架'
								})
							}
							page.value = 1
							getCharList()
						} else {
							uni.showToast({
								title: res.msg,
								icon: 'none'
							})
						}
					})
				}
			}
		})

	};
	// 上传二维码
	const uploadImg = (type) => {
		uni.chooseImage({
			count: 1,
			success(res) {
				uni.showLoading({
					title: '上传中'
				})
				uni.uploadFile({
					// url: config.APIHOST + '/file/uploads',
					url: config.APIHOST + '/file/upload', //真实的接口地址
					filePath: res.tempFilePaths[0],
					header: {
						token: uni.getStorageSync('token')
					},
					name: 'file',
					success: uploadFileRes => {
						uni.hideLoading();
						let url = JSON.parse(uploadFileRes.data)
						if (url.code == 0) {
							groupQrcode.value = url.data.path
						} else {
							uni.showToast({
								title: url.msg,
								icon: 'none'
							})
						}


					}
				});
			},
			fail(err) {
				uni.showToast({
					title: '上传失败',
					icon: 'none'
				})
			}
		})
	};
	// 时间必须大于当前时间
	const isTime = (time) => {
		//格式化时间，并转换为时间戳
		let endTime = new Date(time).getTime()
		//获取当前时间戳
		let startTime = new Date().getTime()

		return endTime > startTime ? true : false
	};
	//确认更新群二维码
	const updataQrCodes = () => {
		if (!groupQrcode.value) {
			uni.showToast({
				title: '请上传群二维码',
				icon: 'none'
			})
			return
		}
		if (!groupExpiredDate.value) {
			uni.showToast({
				title: '请选择过期时间',
				icon: 'none'
			})
			return
		}
		if (isTime(groupExpiredDate.value) == false) {
			uni.showToast({
				title: '过期时间必须大于当前时间',
				icon: 'none'
			})
			return
		}
		let data = {
			id: groupId.value,
			qrCode: groupQrcode.value,
			expiredTime: groupExpiredDate.value
		}
		proxy.$http.postJson('/app/groupInfo/update', data).then(res => {
			if (res.code == 0) {
				uni.showToast({
					title: '更新成功'
				})
				closeQr()
				page.value = 1
				getCharList()
			} else {
				uni.showToast({
					title: res.msg,
					icon: 'none'
				})
			}
		})
	};
	// 关闭群二维码弹框
	const closeQr = () => {
		showQrCode.value = false
	};
	// 关闭群有效期弹框
	const close = () => {
		showTime.value = false
	};
	//选择时间
	const confirmTime = (e) => {
		let timeer = timeFormat(e.value, 'yyyy-mm-dd hh:MM:ss');
		groupExpiredDate.value = timeer
		close()
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
						display: flex;
						align-items: center;
						justify-content: space-between;

						.list-box-item-info-r-name-names {
							font-family: PingFang SC;
							font-weight: 800;
							font-size: 30rpx;
							color: #333333;
						}

						.list-box-item-info-r-name-price {
							font-family: Source Han Sans CN;
							font-weight: 800;
							font-size: 30rpx;
							color: #FF5252;

							text {
								font-size: 24rpx;
							}
						}
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

			.list-box-item-info-status {
				width: 100%;
				margin-top: 20rpx;

				.list-box-item-info-status-l {
					color: #00BC4F;
					font-size: 26rpx;
					font-weight: 500;
				}

				.list-box-item-info-status-r-btnl {
					padding: 14rpx 38rpx;
					background: #E6E6E6;
					border-radius: 30rpx;
					font-weight: 500;
					font-size: 24rpx;
					color: #808080;

				}

				.list-box-item-info-status-r-btnr {
					padding: 14rpx 38rpx;
					background: linear-gradient(121deg, #53D8A4, #ABEC4F);
					border-radius: 30rpx;
					font-weight: 500;
					font-size: 24rpx;
					color: #ffffff;
					margin-left: 20rpx;
				}
			}
		}
	}

	// 更新二维码弹框
	.qrCode {
		width: 580rpx;
		background-color: #ffffff;
		border-radius: 24rpx;

		.qrCode-title {
			width: 100%;
			margin-top: 30rpx;
			font-weight: bold;
			font-size: 32rpx;
			text-align: center;
		}

		.qrCode-img {
			width: 100%;
			margin-top: 40rpx;
			text-align: center;

			image {
				width: 420rpx;
				height: 420rpx;
				border-radius: 20rpx;
			}
		}

		.qrCode-input {
			width: 420rpx;
			margin: 0 auto;
			margin-top: 30rpx;
		}

		.qrCode-btn {
			width: 500rpx;
			height: 80rpx;
			background: linear-gradient(121deg, #53D8A4, #ABEC4F);
			margin: 30rpx auto;
			border-radius: 44rpx;
			color: #ffffff;
			font-weight: bold;
			display: flex;
			align-items: center;
			justify-content: center;
		}
	}

	// 发布
	.sendChat {
		width: 110rpx;
		height: 110rpx;
		background: linear-gradient(121deg, #53D8A4, #ABEC4F);
		box-shadow: 0rpx 8rpx 16rpx 0rpx #c8e1bf;
		border-radius: 50%;
		position: fixed;
		right: 50rpx;
		bottom: 140rpx;
		display: flex;
		align-items: center;
		justify-content: center;
	}
</style>