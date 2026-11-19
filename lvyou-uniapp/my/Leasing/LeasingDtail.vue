<template>
	<view class="">
		<!-- 2.0.19支持autoBack，默认为false -->
		<up-navbar @rightClick="rightClick" :autoBack="true" :bgColor="isTop?'#FFFFFF':'transparent'"
			:leftIconColor="isTop?'#333333':'#FFFFFF'">
		</up-navbar>

		<!-- 背景图 -->
		<view class="bgImgbox" @click="saveImg(shopInfo.img)">
			<image lazy-load="true" fade-show="true" :src="shopInfo.img" mode="aspectFill">
			</image>
		</view>

		<view class="Info">
			<view class="Infobox">
				<view class="Infobox_title">{{shopInfo.name}}</view>
				<view class="Infobox_rate">
					<view class="Infobox_ratelabe">{{shopInfo.grade}}</view>
					<view class="Infobox_rate_tit">{{shopInfo.intro}}</view>
				</view>
				<view class="Infobox_time">
					<text>开放时间</text>
					{{shopInfo.openTime}}
				</view>
				<view class="Infobox_label">
					<view class="Infobox_labelbtn" v-for="(item,index) in shopInfo.tags" :key="index">
						{{item}}
					</view>
				</view>
				<view class="Infobox_add">
					<view class="Infobox_add_left">{{shopInfo.address}}</view>
					<view class="Infobox_add_dh" @click="openMap()">
						导航
						<up-icon name="arrow-right" color="#03CBC4" size="12"></up-icon>
					</view>
				</view>
			</view>
			<view class="dtCont">
				<view class="dtCont_title">达人笔记</view>

				<view class="Dtbbox">
					<view class="Dtbbox_left">
						<travellist v-for="(item,index) in listLeft" :key="index" :InfoItem="item"
							@clickItem="clickItem">
						</travellist>
					</view>
					<view class="Dtbbox_right">
						<travellist v-for="(item,index) in listRight" :key="index" :InfoItem="item"
							@clickItem="clickItem">
						</travellist>
					</view>
				</view>
				<!-- 加载数据 -->
				<up-loadmore :status="status" v-if="list.length>0" />
				<!-- 暂无数据 -->
				<empty v-if="list.length==0"></empty>
			</view>
		</view>
		<view class="taber">
			<view class="taberleft" @click="collectDt()">
				<view class="taberleft_itm">
					<image src="/static/image/dt/like.png" mode="widthFix" v-if="!shopInfo.isCollect"></image>
					<image src="/static/image/dt/likes.png" mode="widthFix" v-else></image>
					<view>{{shopInfo.isCollect?'已收藏':'收藏'}}</view>
				</view>
				<!-- #ifndef MP-WEIXIN -->
				<view class="taberleft_itm" @tap="getShareImg()">
					<image src="/static/image/dt/shares.png" mode="widthFix"></image>
					<view>分享</view>
				</view>
				<!-- #endif -->
				<!-- #ifdef MP-WEIXIN -->
				<button open-type="share" catchtap="share">
					<view class="taberleft_itm">
						<image src="/static/image/dt/shares.png" mode="widthFix"></image>
						<view>分享</view>
					</view>
				</button>
				<!-- #endif -->

			</view>
			<view class="routebtn" @click="bindPhoe()">立即联系</view>
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
		onPageScroll
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
	// 占位图
	import empty from '@/components/empty/empty.vue';
	// 动态列表
	import travellist from '@/components/travellist/index.vue'
	const status = ref('loadmore');
	const isTop = ref(false);

	const list = reactive([]);
	const listLeft = reactive([]);
	const listRight = reactive([]);
	const pages = ref(1); //分页
	const page = ref(1);
	const limit = ref(10);
	const userId = ref('');

	const id = ref('');
	const shopInfo = ref({});

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
		userId.value = uni.getStorageSync('userId');
		if (option.id) {
			id.value = option.id
			getDetail();
		}
		getTaskList();
		let token = uni.getStorageSync('token');
		if (token) {
			getUserInfo()
		}



	});
	onShow(() => {

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
					shareH5Url.value = url.value + '/my/Leasing/LeasingDtail?invitation=' +
						invitationCode
						.value + '&id=' + id.value
				} else {
					url.value = proxy.$publicYuMing();
					shareH5Url.value = url.value + '/my/Leasing/LeasingDtail?invitation=' +
						invitationCode
						.value + '&id=' + id.value
				}
				// console.log(shareH5Url.value, url.value, 'shareH5UrlshareH5Url')
			}
		});
		//#endif
	});
	// 打电话联系
	const bindPhoe = () => {
		let token = uni.getStorageSync('token');
		if (!token) {
			goLoginInfo();
			return;
		}
		console.log(shopInfo.value.phone)
		uni.makePhoneCall({
			phoneNumber: shopInfo.value.phone //仅为示例
		});

	};
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
	//收藏
	const collectDt = () => {
		let token = uni.getStorageSync('token');
		if (!token) {
			goLoginInfo();
			return;
		}
		
		let userId = uni.getStorageSync('userId');
		let data = {
			dataId: id.value,
			userId: userId,
			type: 4 //1动态 2景点 3路线 4商户
		}
		proxy.$http.postJson('/app/collect/save', data).then(res => {
			if (res.code == 0) {
				getDetail();
			} else {
				uni.showToast({
					title: res.msg,
					icon: 'none'
				})
			}
		});
	};
	// 打开动态位置
	const openMap = () => {
		let token = uni.getStorageSync('token');
		if (!token) {
			goLoginInfo();
			return;
		}
		
		uni.openLocation({
			latitude: shopInfo.value.lat - 0, //要去的纬度-地址       
			longitude: shopInfo.value.lng - 0, //要去的经度-地址
			name: shopInfo.value.address, //地址名称
			address: shopInfo.value.address, //详细地址名称
			success: function() {
				console.log('导航成功');
			},
			fail: function(error) {
				console.log(error)
			}
		});
	};
	// 获取详情
	const getDetail = () => {
		proxy.$http.postJson('/app/shop/info', {
			id: id.value
		}).then(async res => {
			if (res.code == 0) {
				if (res.data.tags) {
					res.data.tags = res.data.tags.split(',')
				}
				shopInfo.value = res.data

				tuiguang1.value = res.data.name;
				if (res.data.img && res.data.img.length != 0) {
					let bgImage = res.data.img.split(',')
					backgroundImage.value = bgImage[0];
					let _imgInfo = await getImageInfo(backgroundImage.value);
					shareImgHeight.value = (shareImgWidth.value * _imgInfo.height) / _imgInfo.width;
				}
			}
		});
	};
	// 动态点击事件
	const clickItem = (dt) => {
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
		} else if (dt.index == 6) { //点赞
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
			byUserId: userId.value,
			shopId: id.value
		}
		status.value = 'loadmore';
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
				// 处理动态列表左右数据
				list.forEach((item, index) => {
					if (item.img) {
						item.img = item.img[0]
					}
					if (index % 2 === 0) {
						listLeft.push(item)
					} else {
						listRight.push(item)
					}
				})
			}
			status.value = 'nomore';
			uni.stopPullDownRefresh();
		});
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
	const goLoginInfo = () => {
		uni.navigateTo({
			url: '/pages/public/login'
		})
	};
	onPullDownRefresh(() => {
		let token = uni.getStorageSync('token');
		if (!token) {
			goLoginInfo();
			return;
		}

		page.value = 1;
		getTaskList();
	});
	onReachBottom(() => {
		let token = uni.getStorageSync('token');
		if (!token) {
			goLoginInfo();
			return;
		}
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

			.Infobox_title {
				font-family: PingFang SC;
				font-weight: 800;
				font-size: 38rpx;
				color: #333333;
			}

			.Infobox_rate {
				display: flex;
				align-items: center;
				margin-top: 10rpx;

				.Infobox_ratelabe {
					width: 74rpx;
					height: 40rpx;
					background: linear-gradient(90deg, #4AEBE5 0%, #03D5E4 100%);
					border-radius: 20rpx 22rpx 0rpx 20rpx;
					font-family: Source Han Sans CN;
					font-weight: bold;
					font-size: 28rpx;
					color: #FFFFFF;
					display: flex;
					align-items: center;
					justify-content: center;
				}

				.Infobox_rate_tit {
					font-family: PingFang SC;
					font-weight: 500;
					font-size: 24rpx;
					color: #999999;
					margin-left: 10rpx;
				}
			}

			.Infobox_time {
				font-family: Source Han Sans CN;
				font-weight: 400;
				font-size: 30rpx;
				color: #181919;
				margin-top: 15rpx;

				text {
					color: #08B579;
				}
			}

			.Infobox_label {
				display: flex;
				flex-wrap: wrap;
				margin-top: 10rpx;

				.Infobox_labelbtn {
					font-family: PingFang SC;
					font-weight: 500;
					font-size: 22rpx;
					color: #666666;
					height: 38rpx;
					background: #F0F1F5;
					border-radius: 6rpx;
					padding: 0 10rpx;
					display: flex;
					align-items: center;
					justify-content: center;
					margin-right: 10rpx;
				}
			}

			.Infobox_add {
				display: flex;
				align-items: center;
				justify-content: space-between;
				margin-top: 20rpx;

				.Infobox_add_left {
					font-family: Source Han Sans CN;
					font-weight: 400;
					font-size: 30rpx;
					color: #333333;
				}

				.Infobox_add_dh {
					display: flex;
					align-items: center;
					font-family: Source Han Sans CN;
					font-weight: 400;
					font-size: 26rpx;
					color: #03CBC4;
				}
			}



		}
	}

	.dtCont {
		background: #FFFFFF;
		padding: 30rpx;
		margin-top: 20rpx;

		.dtCont_title {
			font-family: Source Han Sans CN;
			font-weight: 500;
			font-size: 38rpx;
			color: #272727;
		}

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

		.taberleft {
			display: flex;
			align-items: center;
			justify-content: space-between;
			flex: 1;
			margin-right: 40rpx;

			.taberleft_itm {
				width: 50%;
				text-align: center;
				font-family: PingFang SC;
				font-weight: 400;
				font-size: 24rpx;
				color: #999999;

				image {
					width: 46rpx;
					height: 45rpx;
				}
			}
		}

		.routebtn {
			width: 400rpx;
			height: 80rpx;
			background: linear-gradient(90deg, #2FC959 0%, #62DF9D 47%);
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