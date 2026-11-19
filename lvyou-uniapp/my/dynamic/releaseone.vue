<template>
	<view class="release">
		<view class="tiits" v-if="tipcont">{{tipcont}}</view>
		<view class="info ">
			<view class="info-box">
				<!-- 动态标题 -->
				<view class="info-box-contont_title">
					<up-input placeholder="请输入标题" border="none" v-model="title"></up-input>
				</view>
				<!-- 动态内容 -->
				<view class="info-box-contont">
					<textarea v-model="contont" placeholder="此时此刻,想和大家分享什么？" height="240rpx" maxlength="-1"
						:autoHeight="false" placeholder-style="font-size:26rpx;color:#B3B3B3;"
						class="info-box-contontText" />
				</view>
				<!-- 上传图片/视频 -->
				<view class="info-box-uploud flex flex-wrap justify-between">
					<view v-for="(item,index) in uploudList" :key="index"
						class="info-box-uploud-item flex aligin-center justify-center">
						<image v-if="item.type == 1" :src="item.src" mode="aspectFill"></image>
						<video v-else :src="item.src" :controls="false"></video>
						<view class="info-box-uploud-item-close" @click="removeImg(index)">
							<u-icon name="close-circle-fill" color="red" size="18"></u-icon>
						</view>
					</view>
					<view @click="openShoeType" class="info-box-uploud-item flex  justify-center"
						style="align-items: center;">
						<view class="text-center">
							<image src="../static/photo.png" style="width: 54rpx;height:47rpx;border-radius: 0;">
							</image>
							<view style="color: #999999;font-size: 22rpx;">添加图片</view>
						</view>
					</view>

					<view class="info-box-uploud-item flex aligin-center justify-center" style="height: 0;padding: 0;">

					</view>
				</view>
				<view style="width: 100%;height: 1rpx;background: #F2F2F2;" class="margin-tb"></view>

				<!-- 地址/关联活动 -->
				<view class="info-box-bot">
					<view class=" flex aligin-center justify-between margin-bottom" @click="openMap()">
						<view class="info-box-address-l ">
							<image src="../static/address.png" style="width:26rpx;height: 28rpx;" mode="widthFix">
							</image>
							<text class="text-cut margin-left-xs"
								style="max-width: 300rpx;">{{address?address:'点击选择位置'}}</text>
						</view>
						<image src="../static/right.png" style="width: 14rpx;height: 24rpx;"></image>

					</view>
					<view class=" flex aligin-center justify-between margin-bottom-sm" @click="scenicSpotShow = true">
						<view class="info-box-address-l flex align-center ">
							<image src="../static/other.png" style="width:26rpx;height: 28rpx;" mode="widthFix">
							</image>
							<text class="text-cut margin-left-xs"
								style="max-width: 300rpx;">{{scenicSpotName?scenicSpotName:'关联景点'}}</text>
						</view>
						<image src="../static/right.png" style="width: 14rpx;height: 24rpx;"></image>

					</view>
					<view class=" flex aligin-center justify-between margin-bottom-sm" @click="goShop()">
						<view class="info-box-address-l flex align-center ">
							<image src="../static/other.png" style="width:26rpx;height: 28rpx;" mode="widthFix">
							</image>
							<text class="text-cut margin-left-xs"
								style="max-width: 300rpx;">{{shopName?shopName:'关联商家'}}</text>
						</view>
						<image src="../static/right.png" style="width: 14rpx;height: 24rpx;"></image>

					</view>

				</view>
			</view>
		</view>

		<!-- 发布按钮 -->
		<view class="submitBtn flex align-center justify-center">
			<view @tap="submited" class="submitBtn-box flex align-center justify-center">
				发布
			</view>
		</view>

		<!-- 选择上传类型(视频/图片) -->
		<up-action-sheet :actions="listType" title="选择上传类型" :show="show" @select="select" round="24"
			@close="close"></up-action-sheet>

		<up-popup :show="scenicSpotShow" @close="scenicSpotShow = false" bgColor="#F2F2F2" :round="18">
			<view class="popup">
				<view class="popup_title">景点列表</view>
				<view>
					<scroll-view scroll-y="true" class="scroll-Y" :refresher-enabled="true"
						refresher-background="#F2F2F2" :refresher-triggered="refresherTriggered"
						@refresherrefresh="upper" @scrolltolower="lower">
						<view v-for="(item,index) in scenicSpotlist" :key="index" class="ScenicSpot_info"
							@click="changeScenicSpot(item)">
							<image mode="aspectFill" class="ScenicSpot_info_img" :src="item.img">
							</image>
							<view class="ScenicSpot_info_boxleft">
								<view class="ScenicSpot_info_boxleft_name">{{item.name}}</view>
								<view class="flex" v-if="item.tags">
									<view class="ScenicSpot_info_boxleft_label">{{item.tags}}</view>
								</view>
								<view class="ScenicSpot_info_boxleft_tit">{{item.collectNum?item.collectNum:0}}人收藏
								</view>
							</view>
						</view>
						<!-- 加载数据 -->
						<up-loadmore :status="status" v-if="scenicSpotlist.length>0" />
						<!-- 暂无数据 -->
						<empty v-if="scenicSpotlist.length==0" style="padding: 0;"></empty>
					</scroll-view>
				</view>
			</view>
		</up-popup>

	</view>
</template>

<script setup>
	import {
		ref,
		reactive,
		computed,
		getCurrentInstance
	} from 'vue';
	import {
		onLoad,
		onShow,
		onPullDownRefresh,
		onReachBottom,

	} from '@dcloudio/uni-app';
	import config from '@/common/config.js'
	const {
		proxy
	} = getCurrentInstance();
	const title = ref('');
	const userId = ref('');
	const tipcont = ref('');
	const contont = ref(''); //发布内容
	const uploudList = reactive([]); //上传资源数组
	const img = reactive([]); //图片资源
	const videoUrl = reactive([]); //视频资源


	const listType = reactive([{
			name: '上传图片',
			type: 1,
		},
		{
			name: '上传视频',
			type: 2
		},
	]);
	const show = ref(false);



	const talkId = ref(''); //动态id
	const latitude = ref('');
	const longitude = ref('');
	const address = ref('');
	const province = ref('');
	const city = ref('');
	const district = ref('');
	const createTime = ref('');

	const page = ref('1');
	const pages = ref('1');
	const limit = ref('10');
	const scenicSpotlist = reactive([]);
	const scenicSpotShow = ref(false);
	const scenicSpotName = ref('');
	const scenicSpotId = ref('');
	//加载状态
	const status = ref('loadmore');
	const refresherTriggered = ref(false); //设置当前下拉刷新状态，true 表示下拉刷新已经被触发，false 表示下拉刷新未被触发

	const shopName = ref('');
	const shopId = ref('');


	//动态发布文字配置
	proxy.$http.postJson('/app/config/info', {
		key: 'moments_publish_text'
	}).then(res => {
		if (res.code == 0) {
			if (res.data && res.data.value) {
				tipcont.value = res.data.value
			}
		}
	});

	onLoad(option => {
		if (option.talkId) {
			talkId.value = option.talkId
			getCont(); //动态详情
		} else {
			getAddress()
		}
		userId.value = uni.getStorageSync('userId')

	});
	onShow(() => {
		uni.$once('shopInfo', function(data) {
			// console.log(JSON.stringify(data))
			shopName.value = data.name
			shopId.value = data.id
		})


		page.value = 1
		getScenicSpotlist()
	});
	// 跳转商家
	const goShop = () => {
		uni.navigateTo({
			url: '/my/Leasing/Leasing'
		})
	};
	// 选择景点
	const changeScenicSpot = (e) => {
		scenicSpotName.value = e.name
		scenicSpotId.value = e.id
		scenicSpotShow.value = false
	};
	// 触发自定义底部加载
	const lower = () => {
		console.log('触发自定义底部加载')
		if (page.value < pages.value) {
			page.value += 1
			status.value = 'loading';
			getScenicSpotlist();
		} else {
			uni.showToast({
				title: '已经到底了~',
				icon: 'none'
			})
		}
	};
	// 触发自定义下拉刷新
	const upper = () => {
		console.log('触发自定义下拉刷新')
		refresherTriggered.value = true
		status.value = 'loading';
		page.value = 1
		getScenicSpotlist()
	};
	// 获取景点列表
	const getScenicSpotlist = () => {
		proxy.$http.postJson('/app/scenicSpot/page', {
			page: page.value,
			limit: limit.value
		}).then(res => {
			if (res.code == 0) {
				pages.value = res.data.pages
				if (page.value < pages.value) {
					status.value = 'loadmore';
				} else {
					status.value = 'nomore';
				}
				if (page.value == 1) {
					scenicSpotlist.splice(0, scenicSpotlist.length, ...res.data.records);
				} else {
					scenicSpotlist.push(...res.data.records);
				}
			}
			refresherTriggered.value = false
			status.value = 'nomore';
		});
	};
	// 获取动态详情
	const getCont = () => {
		let data = {
			id: talkId.value
		}
		proxy.$http.postJson('/app/moments/info', data).then(res => {
			if (res.code == 0) {
				contont.value = res.data.content
				title.value = res.data.title
				scenicSpotId.value = res.data.scenicSpotId
				scenicSpotName.value = res.data.scenicSpotVo ? res.data.scenicSpotVo.name : ''

				address.value = res.data.location
				province.value = res.data.province
				city.value = res.data.city
				district.value = res.data.district
				longitude.value = res.data.longitude
				latitude.value = res.data.latitude
				createTime.value = res.data.createTime
				shopName.value = res.data.shopName
				shopId.value = res.data.shopId

				let img = []
				if (res.data.img) {
					img = res.data.img.split(',')
				}
				let videoUrl = []
				if (res.data.videoUrl) {
					videoUrl = res.data.videoUrl.split(',')
				}

				let arr = []
				img.map(item => { //图片
					let data = {}
					data.type = 1;
					data.src = item
					arr.push(data)
				})

				videoUrl.map(item => { //视频
					let data = {}
					data.type = 2;
					data.src = item
					arr.push(data)
				})
				uploudList.splice(0, uploudList.length, ...arr)

				let artlist = []
				let index = uploudList.findIndex(item => item.type != 1)
				if (index != -1) {
					artlist = [{
						name: '上传图片',
						type: 1,
					}]
				} else {
					artlist = [{
							name: '上传图片',
							type: 1,
						},
						{
							name: '上传视频',
							type: 2
						},
					]
				}
				listType.splice(0, listType.length, ...artlist)



			} else {
				uni.hideLoading();
				uni.showToast({
					title: res.msg,
					icon: 'none'
				})
			}
		});
	};
	// 非法关键字效验
	const getChatSearchKeys = (key) => {
		let list = uni.getStorageSync("chatSearchKeys");
		let keys = key.replace(/\s*/g, "")
		let over = false
		for (var i = 0; i < keys.length; i++) {
			if (list.indexOf(keys[i]) != -1) {
				over = true;
			}

		}
		return over;

	};
	//发布活动
	const submited = () => {
		console.log(123)

		if (!title.value) {
			uni.showToast({
				title: '请输入要分享的标题',
				icon: 'none'
			})
			return
		}
		if (!contont.value) {
			uni.showToast({
				title: '请输入要分享的内容',
				icon: 'none'
			})
			return
		}
		// if (getChatSearchKeys(contont.value)) {
		// 	uni.showToast({
		// 		title: "输入内容带有非法关键字请重新输入",
		// 		mask: false,
		// 		duration: 1500,
		// 		icon: "none"
		// 	});
		// 	proxy.$http.postT('/app/risk/insertRisk?riskType=2&content=' + contont.value).then(res => {})
		// 	return;
		// }
		if (uploudList.length <= 0) {
			uni.showToast({
				title: '请上传图片或者视频',
				icon: 'none'
			})
			return
		}
		if (!latitude.value || !longitude.value) {
			uni.showToast({
				title: '请选择地址',
				icon: 'none'
			})
			return
		}
		uploudList.map(item => {
			if (item.type == 1) { //图片
				img.push(item.src)
			} else {
				videoUrl.push(item.src)
			}
		})
		uni.showLoading({
			title: '加载中...'
		})
		let userId = uni.getStorageSync('userId');
		let data = {
			userId: userId,
			title: title.value,
			content: contont.value,
			img: img.toString(),
			videoUrl: videoUrl.toString(),
			location: address.value,

			province: province.value,
			city: city.value,
			district: district.value,
			longitude: longitude.value,
			latitude: latitude.value,
			scenicSpotId: scenicSpotId.value,
			createTime: createTime.value,
			shopId:shopId.value,
			shopName:shopName.value
		}
		if (talkId.value) {
			data.id = talkId.value
			proxy.$http.postJson('/app/moments/update', data).then(res => {
				if (res.code == 0) {
					uni.hideLoading();
					uni.showToast({
						title: '修改成功',
						icon: 'none'
					})
					setTimeout(d => {
						uni.navigateTo({
							url: '/my/dynamic/index'
						})
					}, 1000)
				} else {
					uni.hideLoading();
					uni.showToast({
						title: res.msg,
						icon: 'none'
					})
				}
				img = []
				videoUrl = []
			});
		} else {
			proxy.$http.postJson('/app/moments/save', data).then(res => {
				if (res.code == 0) {
					uni.hideLoading();
					uni.showToast({
						title: '发布成功',
						icon: 'none'
					})
					setTimeout(d => {
						uni.redirectTo({
							url: '/my/dynamic/index'
						})
					}, 1000)
				} else {
					uni.hideLoading();
					uni.showToast({
						title: res.msg,
						icon: 'none'
					})
				}
				img = []
				videoUrl = []
			});
		}

	};
	//获取当前位置
	const getAddress = () => {
		uni.getLocation({
			success(ret) {
				getLatAndLngAddress(ret.latitude, ret.longitude)
			},
			fail(err) {
				uni.showToast({
					title: '获取位置失败',
					icon: 'none'
				})
			}
		})
	};
	//打开地图选择位置
	const openMap = () => {
		proxy.$http.postJson('/app/config/info', { //更新地图1手动 2自动  
			key: 'sys_update_map'
		}).then(red => {
			// proxy.$http.getT('/app/common/type/918').then(red => { //更新地图1手动 2自动  
			if (red.code == 0) {
				if (red.data && red.data.value == 1) {
					uni.chooseLocation({
						success(ret) {
							let data = {
								lat: ret.latitude,
								lng: ret.longitude,
							}
							proxy.$http.get('/app/map/reverseGeocode', data).then(res => {
								if (res.code == 0) {
									latitude.value = ret.latitude
									longitude.value = ret.longitude
									address.value = ret.name
									province.value = res.data.province
									city.value = res.data.city
									district.value = res.data.county

								} else {
									uni.showToast({
										title: res.msg,
										icon: 'none'
									})
								}
							})
						},
						fail() {
							uni.showToast({
								title: '拉起地图失败',
								icon: 'none'
							})
						}
					})
				} else {
					uni.getLocation({
						type: 'wgs84',
						geocode: true, //设置该参数为true可直接获取经纬度及城市信息
						success(ret) {
							getLatAndLngAddress(ret.latitude, ret.longitude)
						},
						fail(err) {
							uni.showToast({
								title: '获取位置失败',
								icon: 'none'
							})
						}
					})
				}
			}
		});

	};
	//根据经纬度获取地址
	const getLatAndLngAddress = (lat, lng) => {
		let data = {
			lat: lat,
			lng: lng,
		}
		proxy.$http.get('/app/map/reverseGeocode', data).then(res => {
			if (res.code == 0) {
				latitude.value = lat
				longitude.value = lng
				address.value = res.data.address
				province.value = res.data.province
				city.value = res.data.city
				district.value = res.data.county
			} else {
				uni.showToast({
					title: res.msg,
					icon: 'none'
				})
			}
		})
	};
	// 关闭选择上传弹框
	const close = () => {
		show.value = false
	};
	// 选择上传类型
	const select = (e) => {
		uploudRes(e.type)
	};
	// 打开选择上传弹框
	const openShoeType = () => {
		// uploudRes(1)
		// return
		let index = uploudList.findIndex(item => item.type != 1)
		let arr = []
		if (index != -1) {
			arr = [{
				name: '上传图片',
				type: 1,
			}]
		} else {
			arr = [{
					name: '上传图片',
					type: 1,
				},
				{
					name: '上传视频',
					type: 2
				},
			]
		}
		listType.splice(0, listType.length, ...arr)
		show.value = true
	};
	//移除上传
	const removeImg = (index) => {
		uploudList.splice(index, 1)
	};

	/**
	 * @param {Object} type 类型 1:图片 2:视频
	 * 上传资源
	 */
	const uploudRes = (type) => {

		if (type == 1) { //图片
			uni.chooseImage({
				count: 8,
				sourceType: ['album', 'camera'],
				success: res => {
					for (let i = 0; i < res.tempFilePaths.length; i++) {
						let arr = []
						uni.showLoading({
							title: '上传中'
						});
						uni.uploadFile({ // 上传接口
							// url:config.APIHOST + '/file/upload', //真实的接口地址
							url: config.APIHOST + '/file/upload',
							filePath: res.tempFilePaths[i],
							name: 'file',
							success: (uploadFileRes) => {
								uni.hideLoading();
								let url = JSON.parse(uploadFileRes.data)
								if (url.code == 0) {
									let data = {
										src: url.data.path,
										index: i,
										type: 1
									}
									arr.push(data)
									arr.sort((a, b) => a.index - b.index);
									uploudList.push(...arr)

								} else {
									uni.showToast({
										title: url.msg,
										icon: 'none'
									})
								}

							}
						});
					}
				}
			})

		} else { //视频
			uni.chooseVideo({
				count: 1,
				sourceType: ['album', 'camera'],
				success: res => {
					uni.showLoading({
						title: '上传中'
					});
					uni.uploadFile({ // 上传接口
						// url: config.APIHOST + '/file/uploads', //真实的接口地址
						url: config.APIHOST + '/file/upload',
						filePath: res.tempFilePath,
						name: 'file',
						success: (uploadFileRes) => {
							let arr = []
							uni.hideLoading();
							let url = JSON.parse(uploadFileRes.data)
							if (url.code == 0) {
								let data = {
									src: url.data.path,
									type: 2
								}
								arr.push(data)
								uploudList.push(...arr);
								console.log(uploudList, '=======')
							} else {
								uni.showToast({
									title: url.msg,
									icon: 'none'
								})
							}

						}
					});
				}
			})
		}

	};
	onPullDownRefresh(() => {

	});
	onReachBottom(() => {

	});
</script>

<style lang="scss">
	page {
		background: #F2F2F2;
	}

	.tiits {
		font-family: PingFang SC;
		font-weight: 500;
		font-size: 24rpx;
		color: #2FD7C8;
		width: 686rpx;
		margin: 30rpx auto 0;
	}

	.info {
		width: 100%;
		height: auto;
		margin-top: 20rpx;

		.info-box {
			width: 686rpx;
			height: 100%;
			background: #ffffff;
			border-radius: 16rpx;
			margin: 0 auto;

			.info-box-contont_title {
				width: 100%;
				padding: 20rpx 30rpx;
				border-bottom: 1px solid #F2F2F2;
			}

			// 输入框
			.info-box-contont {
				padding: 20rpx 30rpx;

				.info-box-contontText {
					width: 100%;
				}
			}

			// 图片上传
			.info-box-uploud {
				width: 90%;
				padding: 0 30rpx;

				.info-box-uploud-item {
					background-color: #F5F5F5;
					width: calc(100% / 3 - 30rpx);
					height: 180rpx;
					border-radius: 16rpx;
					margin-top: 20rpx;
					position: relative;
					padding: 10rpx;

					image {
						width: 100%;
						height: 100%;
						border-radius: 16rpx;
					}

					video {
						width: 100%;
						height: 100%;
						border-radius: 16rpx;
					}

					.info-box-uploud-item-close {
						position: absolute;
						right: -10rpx;
						top: -10rpx;
					}
				}
			}

			// 底部地址 其他选择
			.info-box-bot {
				padding: 0 30rpx 10rpx;

				.info-box-address-l {
					font-family: Source Han Sans CN;
					font-weight: 400;
					font-size: 26rpx;
					color: #333333;
					display: flex;
					align-items: center;
				}

			}
		}
	}

	.submitBtn {
		width: 100%;
		height: 88rpx;
		margin-top: 100rpx;

		.submitBtn-box {
			width: 686rpx;
			height: 100%;
			background: linear-gradient(121deg, #53D8A4, #ABEC4F);
			border-radius: 50rpx;
			font-family: PingFang SC;
			font-weight: bold;
			font-size: 32rpx;
			color: #FFFFFF;
		}
	}

	.popup {
		padding: 30rpx;

		.popup_title {
			font-family: PingFang SC;
			font-weight: 800;
			font-size: 32rpx;
			color: #181717;
			text-align: center;
			padding-bottom: 25rpx;
		}

		.scroll-Y {
			height: 800rpx;
		}

		.ScenicSpot_info {
			background: #FFFFFF;
			border-radius: 24rpx;
			padding: 30rpx;
			margin-bottom: 20rpx;
			display: flex;

			.ScenicSpot_info_img {
				width: 160rpx;
				height: 160rpx;
				border-radius: 20rpx;
			}


			.ScenicSpot_info_boxleft {
				flex: 1;
				margin-left: 20rpx;
				height: 160rpx;
				display: flex;
				justify-content: space-between;
				flex-direction: column;

				.ScenicSpot_info_boxleft_name {
					font-family: Source Han Sans CN;
					font-weight: bold;
					font-size: 32rpx;
					color: #202020;
				}

				.ScenicSpot_info_boxleft_label {
					height: 38rpx;
					background: #D6FFE7;
					border-radius: 8rpx;
					font-weight: 400;
					font-size: 24rpx;
					color: #00BC4F;
					padding: 0 20rpx;
					display: flex;
					align-items: center;
					justify-content: center;
				}

				.ScenicSpot_info_boxleft_tit {
					font-weight: 400;
					font-size: 24rpx;
					color: #999999;
				}
			}


		}
	}
</style>