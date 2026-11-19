<template>
	<view class="">
		<!-- 2.0.19支持autoBack，默认为false -->
		<up-navbar @rightClick="rightClick" :autoBack="true" :bgColor="isTop?'#FFFFFF':'transparent'"
			:leftIconColor="isTop?'#333333':'#FFFFFF'">
		</up-navbar>

		<!-- 背景图 -->
		<view class="bgImgbox" @click="saveImg(routeInfo.imgUrl)">
			<image lazy-load="true" fade-show="true" :src="routeInfo.imgUrl" mode="aspectFill">
			</image>
		</view>

		<view class="Info">
			<view class="Infobox">
				<view class="route_title">{{routeInfo.title}}</view>
				<view class="route_tit">{{routeInfo.remark}}</view>
				<view class="routebox">
					<view class="routebox_itn">
						<view class="routebox_itn_title">{{routeInfo.days?routeInfo.days:1}}天</view>
						<view class="routebox_itn_tit">行程天数</view>
					</view>
					<view class="line"></view>
					<view class="routebox_itn">
						<view class="routebox_itn_title">{{routeInfo.kilometres}}公里</view>
						<view class="routebox_itn_tit">行驶公里</view>
					</view>
					<view class="line"></view>
					<view class="routebox_itn">
						<view class="routebox_itn_title">{{routeInfo.times?routeInfo.times:'四季皆宜'}}</view>
						<view class="routebox_itn_tit">最佳时间</view>
					</view>
				</view>
			</view>

			<view class="routelist">
				<view class="route_name">行程路线</view>

				<view class="routelist_box">
					<view v-for="(item,index) in routeInfo.travelRoute" :key="index" class="routelist_info">

						<view class="routelist_infoItem">
							<view class="routelist_infoHead">
								<image src="/my/static/route/lxdidn.png"></image>
								<view class="routelist_info_title">DAY{{item.number}}·{{item.title}}</view>
							</view>

							<view class="routelist_infoCont" v-if="item.remark">
								<text class="routelist_infoCont_tit" v-if="item.isTrue">{{item.remark}}</text>
								<text v-else>{{item.remark}}</text>
								<text class="routelist_infoCont_more" v-if="item.isTrue!=null"
									@click="openAll(item,index)">{{item.isTrue?'全部':'收起'}}</text>
							</view>

							<view class="routelist_infojq">
								<view class="routelist_infojq_dian">
									<view class="dian"></view>
								</view>

								<view class="routelist_infojq_box" @click="goScenicspotDtail(ite)"
									v-for="(ite,ind) in item.scenicSpotVoList" :key="ind">
									<image :src="ite.img">
									</image>
									<view class="routelist_infojq_boxright">
										<view class="routelist_infojq_boxright_name">{{ite.name}}</view>
										<view class="routelist_infojq_boxright_tit">
											{{ite.collectNum?ite.collectNum:0}}人收藏
										</view>
										<view class="flex flex-wrap">
											<view class="routelist_infojq_boxright_label"
												v-for="(lable,indexs) in ite.tags" :key="indexs">{{lable}}</view>
										</view>
									</view>
								</view>
							</view>
						</view>
					</view>
				</view>

			</view>


		</view>



		<view class="taber">
			<!-- #ifndef MP-WEIXIN -->
			<view class="sharebtn" @tap="getShareImg()">分享</view>
			<!-- #endif -->
			<!-- #ifdef MP-WEIXIN -->
			<button open-type="share" catchtap="share">
				<view class="sharebtn">分享</view>
			</button>
			<!-- #endif -->


			<view class="routebtn" @click="collectDt">{{routeInfo.isCollect?'取消收藏':'收藏路线'}}</view>
		</view>

		<!-- 二维码 -->
		<!-- #ifdef H5 -->
		<view class="qrcodeBox" v-if="shareH5Url">
			<up-qrcode :size="200" @result="resultQr" :val="shareH5Url"></up-qrcode>
		</view>

		<canvas canvas-id="myShareImg" id="myShareImg" :style="{width:shareImgWidth+'px',height:shareImgHeight+'px'}"
			class="myShareImg"></canvas>
		<!-- #endif -->

		<up-popup :show="showShareImg" @close="closeShareImg" mode="center" :safeAreaInsetBottom="false">
			<view class="flex">
				<image :src="canvasImgSrc" :style="{width:shareImgWidth+'px',height:shareImgHeight+'px'}" mode="">
				</image>
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
		onReachBottom,
		onPageScroll,
	} from '@dcloudio/uni-app';
	const {
		proxy
	} = getCurrentInstance();
	onPageScroll(e => {
		// console.log(e)
		if (e.scrollTop > 140) {
			isTop.value = true
		} else {
			isTop.value = false
		}
	});
	import configdata from '../../common/config.js';
	// 占位图
	import empty from '@/components/empty/empty.vue';
	const isTop = ref(false);
	const list = reactive([])
	const id = ref('');
	const routeInfo = ref('');

	const canvasImgSrc = ref(''); ///canvas生成的图片
	const showShareImg = ref(false); ////是否显示海报弹窗
	const backgroundImage = ref(''); //海报背景图
	const shareImgHeight = ref(0); ////海报高度
	const shareImgWidth = ref(uni.upx2px(686)); //海报宽度
	const invitationCode = ref('');
	const userName = ref('');
	const avatar = ref('');
	//推广文案
	const tuiguang1 = ref('');
	const url = ref('');
	const qrImg = ref('');
	const shareH5Url = ref('');



	onLoad(option => {
		if (option.id) {
			id.value = option.id
			getDetail()
		}
		getUserInfo()
		// #ifndef H5
		proxy.$http.postJson('/app/config/info', {
			key: 'sys_apk_url'
		}).then(res => {
			if (res.code == 0) {
				if (res.data && res.data.value) {
					url.value = res.data.value;
				}
			}
		});

		//#endif
		// #ifdef H5
		proxy.$http.postJson('/app/config/info', {
			key: 'sys_h5_domain'
		}).then(ress => {
			if (ress.code === 0) {
				if (ress.data && ress.data.value) {
					url.value = ress.data.value;
					shareH5Url.value = url.value + '/my/Route/detail?invitation=' +
						invitationCode
						.value + '&id=' + id.value
				} else {
					url.value = proxy.$publicYuMing();
					shareH5Url.value = url.value + '/my/Route/detail?invitation=' +
						invitationCode
						.value + '&id=' + id.value
				}
				// console.log(shareH5Url.value, url.value, 'shareH5UrlshareH5Url')
			}
		});
		//#endif
	});
	onShow(() => {

	});
	//获取用户信息
	const getUserInfo = () => {
		proxy.$http.getT('/app/base/user/info').then(res => {
			if (res.code == 0) {
				invitationCode.value = res.data.invitationCode
				userName.value = res.data.userName
				avatar.value = res.data.avatar

			}
		})
	};
	// 跳转景点详情
	const goScenicspotDtail = (ite) => {
		uni.navigateTo({
			url: '/my/Route/ScenicspotDtail?id=' + ite.id
		})
	};
	// 查看完整路线
	const openAll = (ite, index) => {
		// isTrue = !isTrue
		// console.log(routeInfo.value.travelRoute[index])
		routeInfo.value.travelRoute[index].isTrue = !routeInfo.value.travelRoute[index].isTrue
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
	// 查看图片
	const saveImg = (photoImg) => {
		let imgsArray = [];
		imgsArray[0] = photoImg;
		uni.previewImage({
			current: 0,
			urls: imgsArray
		});
	};
	const getDetail = () => {
		proxy.$http.postJson('/app/baseInfo/info', {
			id: id.value
		}).then(async res => {
			if (res.code == 0) {
				if (res.data.tags) {
					res.data.tags = res.data.tags.split(',')
				}

				res.data.travelRoute.forEach(d => {

					if (d.remark && d.remark.length > 70) {
						d.isTrue = true
					} else {
						d.isTrue = null
					}
					if (d.scenicSpotVoList.length != 0) {
						d.scenicSpotVoList.forEach(ite => {
							if (ite.tags) {
								ite.tags = ite.tags.split(',')
							}
							if (ite.img) {
								let img = ite.img.split(',')
								ite.img = img[0]
							}

						})
					}


				})

				routeInfo.value = res.data
				tuiguang1.value = res.data.title;
				if (res.data.imgUrl && res.data.imgUrl.length != 0) {
					let bgImage = res.data.imgUrl.split(',')
					backgroundImage.value = bgImage[0];
					let _imgInfo = await getImageInfo(backgroundImage.value);
					shareImgHeight.value = (shareImgWidth.value * _imgInfo.height) / _imgInfo.width;
				}
			}
		})
	};
	//收藏
	const collectDt = () => {
		let userId = uni.getStorageSync('userId');
		let data = {
			dataId: routeInfo.value.id,
			userId: userId,
			type: 3 //1动态 2景点 3路线 4商户
		}
		proxy.$http.postJson('/app/collect/save', data).then(res => {
			if (res.code == 0) {
				
				if(routeInfo.value.isCollect){
					uni.showToast({
						title: '取消收藏',
						icon: 'none'
					})
				}else{
					uni.showToast({
						title: '收藏成功',
						icon: 'none'
					})
				}
				getDetail();
			} else {
				uni.showToast({
					title: res.msg,
					icon: 'none'
				})
			}
		});
	};

	//关闭海报弹窗
	const closeShareImg = () => {
		showShareImg.value = false
	};
	// 分享
	const getShareImg = async () => {
		if (!uni.getStorageSync('token')) {
			uni.navigateTo({
				url: '/pages/public/login'
			})
			return
		}
		//如果已经生成了海报，则直接显示
		if (canvasImgSrc.value) {
			showShareImg.value = true
		} else { //没有则去用canvas生成
			await shareImg();
		}
	};

	//生成海报
	const shareImg = async () => {
		uni.showLoading({
			title: '生成中...'
		})
		let _imgInfo = await getImageInfo(backgroundImage.value);
		console.log(_imgInfo)
		//获取canvas的上下文对象
		const ctx = uni.createCanvasContext('myShareImg');
		//设置画布的高度（根据图片的宽高比）
		shareImgHeight.value = (shareImgWidth.value * _imgInfo.height) / _imgInfo.width;
		console.log(shareImgHeight.value)
		ctx.setFillStyle('#ffffff'); //canvas背景颜色
		ctx.fillRect(0, 0, shareImgWidth.value, shareImgHeight.value); //canvas画布大小
		//绘制背景图片
		ctx.drawImage(_imgInfo.path, 0, 0, shareImgWidth.value, shareImgHeight.value);
		// 在底部绘制个人信息与二维码
		//绘制头像
		const _avatarInfo = ref('');
		//头像宽高
		const avatarWidthOrHeight = ref(60);
		//边距
		const paddingNum = ref(20);
		//如果有头像
		if (avatar.value) {
			_avatarInfo.value = await getImageInfo(avatar.value)
		} else { //没有头像使用本地logo绘制
			_avatarInfo.value = await getImageInfo('/static/logos.png')
			console.log(_avatarInfo.value.path)
		}

		ctx.save();
		ctx.beginPath();
		//计算头像的中心坐标
		const avatarCenterX = paddingNum.value + avatarWidthOrHeight.value / 2;
		const avatarCenterY = shareImgHeight.value - paddingNum.value - avatarWidthOrHeight.value / 2;
		//绘制圆形头像
		ctx.arc(avatarCenterX, avatarCenterY, avatarWidthOrHeight.value / 2, 0, 2 * Math.PI);
		ctx.clip();
		//绘制头像
		ctx.drawImage(_avatarInfo.value.path, paddingNum.value, shareImgHeight.value - paddingNum.value -
			avatarWidthOrHeight.value, avatarWidthOrHeight.value,
			avatarWidthOrHeight.value);
		ctx.restore();
		//绘制用户昵称
		ctx.setFontSize(18);
		ctx.setFillStyle('#333333');
		ctx.fillText(userName.value, paddingNum.value + paddingNum.value / 2 + avatarWidthOrHeight.value,
			shareImgHeight.value - (
				avatarWidthOrHeight.value / 2) - paddingNum.value - paddingNum.value / 2);
		//绘制用户邀请码
		ctx.fillText('ID:' + invitationCode.value, paddingNum.value + paddingNum.value / 2 + avatarWidthOrHeight
			.value, shareImgHeight
			.value - avatarWidthOrHeight.value / 2);

		//绘制二维码
		ctx.drawImage(qrImg.value, shareImgWidth.value - paddingNum.value - avatarWidthOrHeight.value,
			shareImgHeight.value - paddingNum.value - avatarWidthOrHeight.value, avatarWidthOrHeight.value,
			avatarWidthOrHeight.value);
		console.log(qrImg.value)
		ctx.draw(false, () => {
			console.log('绘制完成')
			uni.hideLoading()
			getCanvasNewImage();
		});
	};
	//把canvas画布转为图片
	const getCanvasNewImage = () => {
		uni.canvasToTempFilePath({
			canvasId: 'myShareImg',
			quality: 1,
			complete: res => {
				// console.log(res.tempFilePath)
				canvasImgSrc.value = res.tempFilePath;
				showShareImg.value = true;
			}
		});
	};
	//异步获取图片信息
	const getImageInfo = async (imgSrc) => {
		return new Promise((resolve, errs) => {
			uni.getImageInfo({
				src: imgSrc,
				success: function(image) {
					resolve(image);
				},
				fail(err) {
					errs(err);
					uni.showToast({
						title: '海报生成失败'
					})
					uni.hideLoading()
				}
			});
		});
	};
	//生成二维码回掉的图片（base64）
	const resultQr = (e) => {
		qrImg.value = e;
	};
	onPullDownRefresh(() => {

	});
	onReachBottom(() => {

	});
</script>

<style lang="scss">
	page {
		background: #F5F5F5;
	}

	button::after {
		border: none;
		background-color: none;
	}

	button {
		display: flex;
		align-items: center;
		position: relative;
		display: block;
		margin-left: auto;
		margin-right: auto;
		padding-left: 0px;
		padding-right: 0px;
		box-sizing: border-box;
		text-decoration: none;
		line-height: 30rpx;
		overflow: hidden;
		color: #FFFFFF;
		background-color: rgba(255, 255, 255, 0) !important;
		font-size: 28rpx;
		width: 100%;
		height: 100%;
		color: #21FD91;
	}

	.bgImgbox {
		width: 100%;
		height: 442rpx;
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		z-index: 3;

		image {
			width: 100%;
			height: 100%;
		}
	}

	.Info {
		position: relative;
		top: 410rpx;
		left: 0;
		right: 0;
		z-index: 9;
		padding-bottom: 200rpx;

		.Infobox {
			background: #FFFFFF;
			// background: linear-gradient(to bottom, #FFFFFF 90%, #F5F5F5 100%);
			border-radius: 24rpx 24rpx 0 0;
			padding: 30rpx;
		}
	}

	.route_title {
		font-family: Source Han Sans CN;
		font-weight: bold;
		font-size: 40rpx;
		color: #333333;
	}

	.route_tit {
		font-family: Source Han Sans CN;
		font-weight: 400;
		font-size: 24rpx;
		color: #999999;
	}

	.routebox {
		height: 128rpx;
		background: #F7F8FA;
		border-radius: 24rpx;
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 0 50rpx;
		margin-top: 20rpx;

		.routebox_itn {
			text-align: center;

			.routebox_itn_title {
				font-weight: 500;
				font-size: 32rpx;
				color: #1C1C1C;
			}

			.routebox_itn_tit {
				font-weight: 400;
				font-size: 24rpx;
				color: #999999;
			}
		}

		.line {
			width: 1rpx;
			height: 60rpx;
			background: #CCCCCC;
		}
	}


	// 路线列表
	.routelist {
		background: #F5F5F5;
		position: relative;
		top: -40rpx;

		.route_name {
			background: linear-gradient(to bottom, #FFFFFF 5%, #F5F5F5 100%);
			font-family: Source Han Sans CN;
			font-weight: bold;
			font-size: 32rpx;
			color: #1C1C1C;
			margin-top: 30rpx;
			padding: 0 30rpx;
		}
	}

	.routelist_box {
		margin-top: 20rpx;

		.routelist_info {
			margin: 0 38rpx;
			border-left: 1rpx dashed #222222;

			.routelist_infoItem {
				position: relative;
				top: -10rpx;
				left: -15rpx;

				.routelist_infoHead {
					display: flex;
					align-items: center;


					.routelist_info_title {
						font-family: Source Han Sans CN;
						font-weight: 500;
						font-size: 28rpx;
						color: #1C1C1C;
						margin-left: 20rpx;
					}

					image {
						width: 28rpx;
						height: 28rpx;
					}

				}

				.routelist_infoCont {

					font-family: Source Han Sans CN;
					font-weight: 400;
					font-size: 28rpx;
					color: #333333;
					margin-left: 48rpx;
					position: relative;

					.routelist_infoCont_tit {
						width: 580rpx;
						height: 120rpx;
						display: -webkit-box;
						-webkit-box-orient: vertical;
						-webkit-line-clamp: 3;
						overflow: hidden;

					}

					.routelist_infoCont_more {
						color: #46A1FF;
						position: absolute;
						bottom: -6rpx;
						right: 0;

					}
				}

				.routelist_infojq {
					display: flex;
					align-items: center;

					.routelist_infojq_dian {
						width: 28rpx;
						height: 28rpx;
						background: transparent;
						border-radius: 90%;
						display: flex;
						align-items: center;
						justify-content: center;

						.dian {
							width: 20rpx;
							height: 20rpx;
							border-radius: 90%;
							background: #202020;
						}
					}

					.routelist_infojq_box {
						width: 100%;
						height: 180rpx;
						background: #FFFFFF;
						border-radius: 24rpx;
						display: flex;
						margin: 30rpx 0 30rpx 20rpx;

						image {
							width: 180rpx;
							height: 180rpx;
							background: #CCCCCC;
							border-radius: 24rpx;
						}

						.routelist_infojq_boxright {
							flex: 1;
							padding: 20rpx 20rpx;
							display: flex;
							justify-content: space-between;
							flex-direction: column;

							.routelist_infojq_boxright_name {
								font-family: Source Han Sans CN;
								font-weight: bold;
								font-size: 32rpx;
								color: #202020;
							}

							.routelist_infojq_boxright_label {
								height: 38rpx;
								background: #D6FFE7;
								border-radius: 8rpx;
								font-family: Source Han Sans CN;
								font-weight: 400;
								font-size: 24rpx;
								color: #00BC4F;
								padding: 0 20rpx;
								display: flex;
								align-items: center;
								justify-content: center;
							}

							.routelist_infojq_boxright_tit {
								font-family: Source Han Sans CN;
								font-weight: 400;
								font-size: 24rpx;
								color: #999999;
							}
						}


					}
				}

			}
		}
	}

	.taber {
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
		padding: 20rpx 30rpx;
		background: #FFFFFF;
		display: flex;
		align-items: center;
		justify-content: space-between;
		z-index: 99;

		.sharebtn {
			width: 325rpx;
			height: 80rpx;
			background: #F6F7F9;
			border-radius: 40rpx;
			font-family: Source Han Sans CN;
			font-weight: 500;
			font-size: 28rpx;
			color: #4D4D4D;
			display: flex;
			align-items: center;
			justify-content: center;
		}

		.routebtn {
			width: 325rpx;
			height: 80rpx;
			background: linear-gradient(121deg, #53D8A4, #ABEC4F);
			border-radius: 40rpx;
			font-family: Source Han Sans CN;
			font-weight: 500;
			font-size: 28rpx;
			color: #FFFFFF;
			display: flex;
			align-items: center;
			justify-content: center;

		}
	}


	.qrcodeBox {
		position: fixed;
		left: -1000px;
	}

	.myShareImg {
		position: fixed;
		left: -1000px;
	}
</style>