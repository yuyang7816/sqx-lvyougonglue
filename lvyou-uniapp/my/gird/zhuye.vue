<template>
	<view class="">
		<!-- 2.0.19支持autoBack，默认为false -->
		<up-navbar @rightClick="rightClick" :autoBack="true" :bgColor="bgColor" leftIconColor="#FFFFFF">
		</up-navbar>

		<!-- 背景图 -->
		<view class="myBgImgs">
			<view style="position: relative;">
				<view v-if="swiperImage" @click="openpopup(swiperImage)">
					<image lazy-load="true" fade-show="true" :src="swiperImage" style="width: 100%; height:527rpx;"
						mode="aspectFill"></image>
				</view>
				<view v-else @click="openpopup(bgImage)">
					<image lazy-load="true" fade-show="true" :src="bgImage" style="width: 100%; height:527rpx;"
						mode="aspectFill">
					</image>
				</view>
				<view class="huibg"></view>
			</view>
		</view>


		<!-- 用户信息 -->
		<view class="zyInfo">
			<view class="zyInfo_head">
				<view class="avatrTop">
					<view class="zyInfo_headleft  ">
						<view class="zyInfo_avatr">
							<image @click="priveImgs(0,[userInfo.avatar])"
								:src="userInfo && userInfo.avatar?userInfo.avatar:logo" mode="aspectFill"></image>
						</view>

					</view>
					<!-- 联系/关注 -->
					<view v-if="userId != byUserId" class="zyInfo_headRight ">
						<!-- 关注 -->
						<view v-if="isfollow" class="zyInfo-btns" @click="guanzhu">
							已关注
						</view>
						<!-- 取消关注 -->
						<view v-else class="zyInfo-btns" @click="guanzhu">
							关注TA
						</view>
					
					</view>
				</view>

				<view class="zyInfo_bot">
					<view class="flex align-center justify-between " style="padding:6rpx 0">
						<view class="zyInfo_botName ">{{userInfo.userName?userInfo.userName:''}}</view>
						<view class="zyInfo_heimingdan " @click="openShow" v-if="userId != byUserId">
							<image src="/static/image/dt/more.png" mode="widthFix"></image>
						</view>
					</view>
					<!-- 个人简介 -->
					<view class="userInfos-content " v-if="userInfo.remark">
						{{userInfo && userInfo.remark?userInfo.remark:'TA很懒，没有留下介绍'}}
					</view>
				</view>


				<view class="flex align-center margin-top-sm">
					<view class=" flex  ">
						<view class="text-xl text-bold">{{userLikeNum}}</view>
						<view style="color: #999999;font-size: 26rpx;margin:10rpx 0 0 4rpx;">关注</view>
					</view>
					<view class=" flex margin-left">
						<view class="text-xl text-bold">{{userFansNum}}</view>
						<view style="color: #999999;font-size: 26rpx;margin:10rpx 0 0 4rpx;">粉丝</view>
					</view>
					<view class=" flex margin-left">
						<view class="text-xl text-bold">{{userReceivedLike}}</view>
						<view style="color: #999999;font-size: 26rpx;margin:10rpx 0 0 4rpx;">获赞</view>
					</view>
				</view>

			</view>
		</view>


		<view style="margin-top: 318rpx;background: #F2F5FA;position: relative;z-index: 999;padding-top: 1rpx;">
			<view class="margin">
				<Head :list="list" @clickItem="clickItem"></Head>
			</view>
			<up-loadmore :status="status" v-if="list.length>0" />

			<empty contont="暂无数据" v-if="list.length == 0" />
		</view>

		<up-popup :show="bgshow" @close="bgshow = false" :round="20">
			<view class="popupbox">
				<view class="title">选择背景图</view>
				<scroll-view :scroll-top="0" scroll-y="true" class="scroll-Y">
					<view class="scroll-view-item ">
						<view class="flex align-center flex-wrap">
							<view v-for="(item,index) in swiperList" :key="index" class="bgImgbox" style=""
								@click.stop="saveImg(item.imageUrl)">
								<image :src="item.imageUrl" style="width: 100%;height: 100%;border-radius: 12rpx;"
									mode="aspectFill">
								</image>
								<view class="checkbox">
									<u-radio-group v-model="item.radiovalue" placement="column"
										@change="groupChange(item)">
										<u-radio :customStyle="{marginBottom: '0px'}">
										</u-radio>
									</u-radio-group>
								</view>
							</view>
							<view @click="uploadMyImage()" class="bgImgboxs">
								<image src="../../static/image/my/addimage.png">
								</image>
								<view class="margin-top-xs">添加展示图</view>
							</view>
						</view>

					</view>
				</scroll-view>

			</view>
		</up-popup>

		<up-popup :show="isShow" @close="isShow = false" :round="20">
			<view class="" style="background: #F5F5F5;border-radius: 24rpx;padding:40rpx 30rpx 30rpx;">
				<view class="popupbtn" @click="saveLH()">
					<image src="../static/lahei.png" style="width: 50rpx;height: 50rpx;"></image>
					<text v-if="stateLH">移除黑名单</text>
					<text v-if="!stateLH">加入黑名单</text>
				</view>

				<view class="popupbtn" @click="goTousu()">
					<image src="../static/tousu.png" style="width: 50rpx;height: 50rpx;"></image>
					投诉
				</view>
			</view>
		</up-popup>
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
	import config from '@/common/config.js';
	const {
		proxy
	} = getCurrentInstance();
	import empty from '@/components/empty/empty.vue';
	import Head from '@/components/head/head.vue'
	// 创建响应式数据  
	const bgColor = ref('transparent');

	const swiperImage = ref(''); //用户绑定背景图
	const swiperList = ref([]); //系统背景图
	const bgshow = ref(false); //背景图弹框
	const bgImage = ref('');

	const userId = ref('');
	const byUserId = ref(''); //带进来的用户UserId

	const userInfo = ref(''); //用户信息
	const list = reactive([]); //动态列表
	const pages = ref(1); //分页
	const page = ref(1);
	const limit = ref(10);
	//加载状态
	const status = ref('loadmore');

	const isfollow = ref(false); //是否关注
	const isShow = ref(false); //黑名单弹框
	const stateLH = ref(false); //是否加入黑名单
	const HmdId = ref(''); //黑名单id

	const userFansNum = ref(0); //粉丝
	const userLikeNum = ref(0); //关注
	const userReceivedLike = ref(0); //获赞

	//加入黑名单
	const saveLH = () => {
		
		if (!uni.getStorageSync('token')) {
			uni.navigateTo({
				url: '/pages/public/login'
			});
			return
		}
		isShow.value = false
		if (stateLH.value) {
			let data = {
				id:HmdId.value,
				blackUserId:byUserId.value,
			}
			uni.showModal({
				title: '提示',
				content: '是否将当前用户移出黑名单？',
				success: function(res) {
					if (res.confirm) {
						console.log('用户点击确定');
						proxy.$http.postJson('/app/blackList/delete', data).then(res => {
							if (res.code == 0) {
								HmdId.value = ''
								uni.showToast({
									title: '已成功将当前用户移除黑名单！',
									icon: 'none'
								})
								checkLH();
							} else {
								uni.showToast({
									title: res.msg,
									icon: 'none'
								})
							}
						});
					} else if (res.cancel) {
						console.log('用户点击取消');
					}
				}
			})
			
		} else {
			let data = {
				blackUserId: byUserId.value,
				userId:uni.getStorageSync('userId')
			}
			uni.showModal({
				title: '提示',
				content: '是否将当前用户加入黑名单？',
				success: function(res) {
					if (res.confirm) {
						console.log('用户点击确定');
						proxy.$http.postJson('/app/blackList/save', data).then(res => {
							if (res.code == 0) {
								uni.showToast({
									title: '已成功将当前用户加入黑名单中，如需移除请前往黑名单中移除！',
									icon: 'none'
								})
								checkLH();
							} else {
								uni.showToast({
									title: res.msg,
									icon: 'none'
								})
							}
						});
					} else if (res.cancel) {
						console.log('用户点击取消');
					}
				}
			})
		}
		

	};
	// 跳转投诉
	const goTousu = () => {
		if (uni.getStorageSync('token')) {
			isShow.value = false
			uni.navigateTo({
				url: '/my/order/complain?byUserId=' + byUserId.value + '&platform=1&platformId=' + byUserId.value
			})
		} else {
			uni.showModal({
				title: '提示',
				content: '您还未登录,请先登录',
				success: function(res) {
					if (res.confirm) {
						console.log('用户点击确定');
						uni.navigateTo({
							url: '/pages/public/login'
						})
					} else if (res.cancel) {
						console.log('用户点击取消');
					}
				}
			})
		}

	};
	// 查询是否加入黑名单
	const checkLH = () => {
		proxy.$http.postJson('/app/blackList/page',{
			blackUserId:byUserId.value,
			userId:uni.getStorageSync('userId')
		}).then(res => {
			if (res.code == 0 && res.data&&res.data.records.length!=0) {
				stateLH.value = true;
				HmdId.value = res.data.records[0].id
			} else {
				stateLH.value = false;
			}
		});
	};
	// 打开主页 投诉黑名单弹框
	const openShow = () => {
		isShow.value = true
	};
	//查询关注状态
	const getguanzhu = () => {
		proxy.$http.getT("/app/base/user/userData?userId=" + byUserId.value).then(res => {
			if (res.code == 0) {
				isfollow.value = res.data.isFollow
			}
		});
	};
	//关注/取消关注
	const guanzhu = () => {
		if (uni.getStorageSync('token')) {
			proxy.$http.postJson("/app/follow/save", {
				targetUserId: byUserId.value,
				userId:uni.getStorageSync('userId')
			}).then(res => {
				if (res.code == 0) {
					uni.showToast({
						title: res.msg,
						icon: 'none'
					})
					getguanzhu();
					page.value = 1;
					getTaskList();
				} else {
					uni.showToast({
						title: res.msg,
						icon: 'none'
					})
				}
			});
		} else {
			uni.navigateTo({
				url: '/pages/public/login'
			})
		}

	};
	// 返回上一页
	const rightClick = () => {
		var pages = getCurrentPages();
		if (pages.length > 1) {
			uni.navigateBack()
		} else {
			uni.switchTab({
				url: '/pages/index/index'
			})
		}
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
		} else if (dt.index == 2) { //去投诉 投诉目标类型：1代表用户，2代表群聊
			uni.navigateTo({
				url: '/my/order/complain?byUserId=' + dt.item.userId + '&platform=1&platformId=' + dt.item
					.id
			})
		}else if (dt.index == 5) { //查看图片
			let Imagelist = dt.item.img
			saveImgDt(dt.imgIndex, Imagelist);
		} else if (dt.index == 6) { //点赞
			saveLove(dt.item.id, dt.item);
		} else if (dt.index == 7) { //收藏
			collectDt(dt.item.id, dt.item);
		}
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
	// 查看图片
	const saveImgDt = (index, Imagelist) => {
		uni.previewImage({
			current: index,
			urls: Imagelist
		});
	};
	
	// 获取动态列表
	const getTaskList = () => {
		let data = {
			page: page.value,
			limit: limit.value,
			byUserId: userId.value,
			userId: byUserId.value,

		}
		proxy.$http.postJson("/app/moments/page", data).then(res => {
			if (res.code == 0) {
				pages.value = res.data.totalPage
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
	// 获取用户展示数据
	const getNumlist = () => {
		proxy.$http.postJson("/app/base/user/data", {
			userId: byUserId.value
		}).then(res => {
			if (res.code == 0) {
				userFansNum.value = res.data.userFansNum
				userLikeNum.value = res.data.userLikeNum
				userReceivedLike.value = res.data.userReceivedLike
			}
		});
	};
	//获取用户信息
	const getByUserInfo = () => {
		proxy.$http.get("/app/base/user/getUserInfoById", {
			userId: byUserId.value
		}).then(res => {
			if (res.code == 0) {

				//存储用户信息
				userInfo.value = res.data

				if (res.data.userBackgroundImage) {
					swiperImage.value = res.data.userBackgroundImage
				} else {
					getBgImg()
				}

			}
		});

	};
	// 获取系统配置背景图
	const getBgImg = () => {
		proxy.$http.post('/app/userBackground/list').then(res => {
			if (res.code == 0) {
				let arr = []
				res.data.forEach(d => {
					d.radiovalue = false
					d.imageUrl = d.imgUrl
					arr.push(d)
				})
				swiperList.value = arr
				if (res.data.length != 0) {
					bgImage.value = res.data[0].imageUrl
				}

			}
		})
	};
	// 背景图修改查看
	const openpopup = () => {
		if (userId.value == byUserId.value) {
			if (uni.getStorageSync('token')) {
				getBgImg()
				bgshow.value = true
			} else {
				uni.navigateTo({
					url: '/pages/public/login'
				})
			}

		} else {
			saveImg(bgImage.value)
		}
	};
	// 修改背景图
	const groupChange = (n) => {
		let userBackgroundImage = n.imageUrl
		proxy.$http.postJson("/app/userBackground/bind", {
			imgUrl: userBackgroundImage,
			userId: byUserId.value
		}).then(res => {
			if (res.code === 0) {
				uni.showToast({
					title: '上传成功',
					icon: "none"
				})
				getByUserInfo()
				bgshow.value = false
			} else {
				uni.showToast({
					title: res.msg,
					icon: "none"
				})
			}
		})

	};
	// 查看图片
	const saveImg = (photoImg) => {
		bgshow.value = false
		let imgsArray = [];
		imgsArray[0] = photoImg;
		uni.previewImage({
			current: 0,
			urls: imgsArray
		});
	};
	//背景图修改
	const uploadMyImage = (bgImage) => {
		uni.chooseImage({
			count: 1,
			sourceType: ['album', 'camera'],
			success: res => {
				for (let i = 0; i < res.tempFilePaths.length; i++) {
					uni.showLoading({
						title: '加载中'
					});
					uni.uploadFile({ // 上传接口
						// url: config.APIHOST + '/file/upload',
						url: config.APIHOST + '/file/upload', //真实的接口地址
						filePath: res.tempFilePaths[i],
						name: 'file',
						success: (uploadFileRes) => {
							uni.hideLoading();
							let url = JSON.parse(uploadFileRes.data)
							if (url.code == 0) {
								proxy.$http.postJson("/app/userBackground/bind", {
									imgUrl: url.data.path.toString(),
									userId: byUserId.value
								}).then(res => {
									if (res.code === 0) {
										uni.showToast({
											title: '切换成功',
											icon: "none"
										})
										getByUserInfo()
										bgshow.value = false
									} else {
										uni.showToast({
											title: res.msg,
											icon: "none"
										})
									}
								})
							} else {
								uni.showToast({
									title: url.msg,
									icon: 'none',
									duration: 2000
								})
							}

						}
					});
				}
			}
		})
	};
	onLoad(option => {
		byUserId.value = option.userId;
		userId.value = uni.getStorageSync('userId');

		//获取用户信息
		getByUserInfo();
		getNumlist();
		// // 动态列表
		getTaskList();
		// // 查询是否关注
		getguanzhu();
		// // 查询是否加入黑名单
		checkLH();
	});
	onShow(() => {

	});
	onPullDownRefresh(() => {
		status.value = 'loading';
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
		background: #F2F5FA;
	}

	.myBgImgs {
		width: 100%;
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		z-index: 0;

		image {
			width: 100%;
		}

		.huibg {
			width: 100%;
			height: 200rpx;
			background: linear-gradient(to bottom, transparent 20%, #F2F5FA 80%);
			position: absolute;
			bottom: 0;
			left: 0;
			right: 0;
		}
	}

	.tsIcon {
		position: absolute;
		bottom: 180rpx;
		right: 37rpx;
		z-index: 9;

		image {
			width: 55rpx;
			height: 55rpx;
		}
	}

	.zyInfo {
		background: linear-gradient(to bottom, transparent, #F2F5FA);
		padding: 0 30rpx;
		position: relative;
		top: 320rpx;
		z-index: 9;

		.zyInfo_head {
			background: #FFFFFF;
			padding: 30rpx;
			border-radius: 24rpx;
			position: relative;

			.avatrTop {
				display: flex;
				align-items: flex-end;
				position: absolute;
				top: -60rpx;
				left: 30rpx;
				right: 30rpx;

				.zyInfo_headleft {
					position: relative;

					.zyInfo_avatr {
						background: #FFFFFF;
						width: 166rpx;
						height: 166rpx;
						border-radius: 88rpx;
						display: flex;
						align-items: center;
						justify-content: center;

						image {
							width: 155rpx;
							height: 155rpx;
							border-radius: 88rpx;
						}
					}
				}

				.zyInfo_headRight {
					display: flex;
					justify-content: flex-end;
					flex: 1;

					.zyInfo-btns {
						width: 186rpx;
						height: 70rpx;
						background: linear-gradient(90deg, #2FC959 0%, #62DF9D 47%);
						border-radius: 35rpx;
						font-family: PingFang SC;
						font-weight: bold;
						font-size: 24rpx;
						color: #FFFFFF;
						display: flex;
						align-items: center;
						justify-content: center;
					}
				}
			}

			.zyInfo_bot {
				margin-top: 80rpx;

				.zyInfo_botName {
					font-family: Source Han Sans CN;
					font-weight: 500;
					font-size: 40rpx;
					color: #292929;
				}

				.zyInfo_heimingdan {
					image {
						width: 45rpx;
						height: 15rpx;
					}
				}

				.userInfos-content {
					font-family: PingFang SC;
					font-weight: 500;
					font-size: 28rpx;
					color: #666666;
				}
			}
		}
	}

	.popupbtn {
		background: #FFFFFF;
		color: #333333;
		border-radius: 20upx;
		padding: 25upx 30upx;
		margin-bottom: 20rpx;
		display: flex;
		align-items: center;

		image {
			margin-right: 10rpx;
		}
	}

	// 背景弹框样式
	.popupbox {
		height: 600rpx;
		padding: 30rpx 10rpx 60rpx;

		.title {
			font-size: 32rpx;
			text-align: center;
			padding-bottom: 20rpx;
		}

		.scroll-Y {
			height: 500rpx;

			.scroll-view-item {
				height: 500rpx;

				.bgImgbox {
					width: 208rpx;
					height: 208rpx;
					margin-bottom: 12rpx;
					position: relative;
					margin: 0 15rpx 15rpx;

					.checkbox {
						position: absolute;
						top: 10rpx;
						right: 0;
					}
				}

				.bgImgboxs {
					background: #F5F5F5;
					width: 208rpx;
					height: 208rpx;
					border-radius: 12rpx;
					margin: 0 15rpx 12rpx;
					display: flex;
					align-items: center;
					justify-content: center;
					flex-direction: column;
					font-size: 24rpx;
					color: #C2C2C2;

					image {
						width: 54rpx;
						height: 47rpx;
					}
				}
			}
		}



	}
</style>