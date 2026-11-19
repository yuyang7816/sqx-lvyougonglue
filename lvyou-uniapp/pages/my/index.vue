<template>
	<view>
		<!-- 自定义顶部导航栏 -->
		<up-navbar title="" :placeholder="true" :bgColor="bgColor" leftIcon="">
		</up-navbar>

		<!-- 顶部背景图 -->
		<view class="myBgImgs">
			<view style="position: relative;">
				<view v-if="swiperImage" @tap='openpopup(swiperImage)'>
					<image lazy-load="true" fade-show="true" :src="swiperImage" style="width: 100%; height:415upx;"
						mode="aspectFill"></image>
				</view>
				<view v-else @tap='openpopup(bgImage)'>
					<image lazy-load="true" fade-show="true" :src="bgImage" style="width: 100%; height:415upx;"
						mode="aspectFill">
					</image>
				</view>

				<view class="setIcon" @click="goNav('/my/setting/index')">
					<image src="../../static/image/my/set.png"></image>
				</view>

			</view>
		</view>

		<view class="infobox">
			<!-- 用户信息 -->
			<view class="Info">
				<!-- 头像 -->
				<view class="Info_one">
					<view class="Info_oneAvatr">
						<image :src="avatar?avatar:'/static/logos.png'"></image>
					</view>
					<view class="Info_onebjbtn" @click="goNav('/pages/my/userInfo')">编辑个人资料</view>
				</view>
				<view class="Info_two">
					<!-- 用户昵称 -->
					<view class="Info_title " v-if="isLogin" @click="goNav('/my/gird/zhuye?userId='+userId)">
						{{userName?userName:'匿名'}}
						<image src="/static/logos.png" v-if="consortiaId==1"
							style="width: 30rpx;height: 30rpx;" mode="widthFix" class="margin-left-xs"></image>
					</view>
					<view class="Info_title " v-if="!isLogin" @click="goLogin">登录</view>
					<!-- 性别 位置 -->
					<view class="Info_room" v-if="isLogin&&(sex||city||province)">
						<view v-if="sex==1" class="Info_sexman">
							<u-icon name="man" color="#FFFFFF" size="10"></u-icon>
							{{age}}
						</view>
						<view v-if="sex==2" class="Info_sexwoman">
							<u-icon name="woman" color="#FFFFFF" size="10"></u-icon>
							{{age}}
						</view>
						<view style="background: #F2F0F1;" v-if="city||province" @click="gxweizhi()" class="Info_add">
							<u-icon name="map" color="#333333" size="12"></u-icon>
							<text v-if="province">{{province}}</text>
							<text v-if="city">·{{city}}</text>
						</view>
					</view>

					<!-- 个人简介 -->
					<view class="Info-content " v-if="content">
						{{content}}
					</view>

				</view>
			</view>
			<!-- 粉丝 关注  获赞 -->
			<view class="flex align-center justify-between  margin-top margin-lr-xl">
				<view class="text-center" @click="goNav('/my/gird/fans')">
					<view class="text-xl text-bold">{{fans?fans:'0'}}</view>
					<view style="color:#868484;" class="text-sm">粉丝</view>
				</view>
				<view class="text-center" @click="goNav('/my/gird/guanzhu')">
					<view class="text-xl text-bold">{{attention?attention:'0'}}</view>
					<view style="color: #868484;" class="text-sm">关注</view>
				</view>
				<view class="text-center">
					<view class="text-xl text-bold">{{goodCount?goodCount:'0'}}</view>
					<view style="color: #868484;" class="text-sm">获赞</view>
				</view>
			</view>

			<!-- 我的动态 -->
			<view class="dt">
				<view class="dt_top">
					<view class="dtname">我的动态</view>
					<view class="dtall" @click="goNav('/my/dynamic/index')">
						查看全部
						<image src="../../static/image/my/dtfh.png"></image>
					</view>
				</view>
				<scroll-view class="scroll-view_H" scroll-x="true" scroll-left="0" v-if="Dtlist.length!=0">
					<view class="scroll-view-item_H" v-for="(item,index) in Dtlist" :key="index"
						@click="goNav('/my/dynamic/listDetails?talkId=' +item.id)">
						<image :src="item.imgleng" v-if="item.imgleng" mode="aspectFill"
							style="width: 261rpx;height: 175rpx;border-radius: 16rpx;">
						</image>
						<image v-else mode="aspectFill"
							src="/static/image/my/dtempty.png"
							style="width:261rpx;height: 175rpx;border-radius: 16rpx;"></image>
					</view>
				</scroll-view>
				<view v-if="Dtlist.length==0">
					<image src="../../static/image/my/dtempty.png" style="width: 666rpx;height: 230rpx;"></image>
				</view>
			</view>

			<view class="category">
				<view class="setname">其他工具</view>
				<view class="category-list">

					<view class="boxitem" @click="goNav('/my/GroupChat/myGroupChat')">
						<image src="../../static/image/my/myCrowd.png" mode="widthFix"></image>
						<view class="">我的群聊</view>
					</view>
					<view class="boxitem" @click="goNav('/my/GroupChat/JoinGroupChat')">
						<image src="../../static/image/my/joinCrowd.png" mode="widthFix"></image>
						<view class="">加入群聊</view>
					</view>
					<view class="boxitem" @click="goNav('/my/gird/collection')">
						<image src="../../static/image/my/mysc.png" mode="widthFix"></image>
						<view class="">我的收藏</view>
					</view>
					<view class="boxitem" @click="goNav('/my/gird/like')">
						<image src="../../static/image/my/mylike.png" mode="widthFix"></image>
						<view class="">我的喜欢</view>
					</view>

					<view class="boxitem" @click="goNav('/pages/my/invitationUser')">
						<image src="../../static/image/my/myshare.png" mode="widthFix"></image>
						<view class="">邀请好友</view>
					</view>
					<view class="boxitem" @click="goNav('/my/gird/goreport')">
						<image src="../../static/image/my/myts.png" mode="widthFix"></image>
						<view class="">我的投诉</view>
					</view>
					<view class="boxitem" @click="goNav('/my/gird/heimingdan')">
						<image src="../../static/image/my/myhmd.png" mode="widthFix"></image>
						<view class="">黑名单</view>
					</view>
					<!-- <view class="boxitem" @click="goNav('/my/team/index')">
						<image src="../../static/image/my/myhmd.png" mode="widthFix"></image>
						<view class="">我的团队</view>
					</view> -->
					<view class="boxitem" @click="changeKefu">
						<image src="../../static/image/my/mykf.png" mode="widthFix"></image>
						<view class="">联系客服</view>
					</view>
				</view>
			</view>

		</view>

		<!-- 自定义导航栏 -->
		<myTabbar :value="4" />
		<up-popup :show="bgshow" @close="bgshow = false" :round="20">
			<view class="popupbox">
				<view class="title">选择背景图</view>
				<scroll-view :scroll-top="0" scroll-y="true" class="scroll-Y">
					<view class="scroll-view-item ">
						<view class="flex align-center flex-wrap">
							<view v-for="(item,index) in swiperList" :key="index" class="bgImgbox" style=""
								@click.stop="saveImg(item.imageUrl)">
								<image :src="item.imageUrl" style="width: 100%;height: 100%;border-radius: 12rpx;"
									mode="aspectFill"></image>
								<view class="checkbox">
									<u-radio-group v-model="item.radiovalue" placement="column"
										@change="groupChange(item)">
										<u-radio :customStyle="{marginBottom: '0px'}">
										</u-radio>
									</u-radio-group>
								</view>
							</view>
							<view class="bgImgbox" style="width: 0;margin: 0;"></view>
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
	import config from '@/common/config.js'
	const {
		proxy
	} = getCurrentInstance();
	// 引入底部 Tabbar 组件
	import myTabbar from '@/components/myTabbar/myTabbar.vue';
	// 顶部导航栏背景色  
	const bgColor = ref('transparent');
	// 顶部背景图 用户绑定
	const swiperImage = ref('');
	// 顶部系统默认背景图
	const bgImage = ref('');
	// 用户 userId
	const userId = ref('');
	// 用户 token
	const token = ref('');
	// 默认背景图列表
	const swiperList = reactive([])
	const bgshow = ref(false); //背景图弹框
	// 用户信息
	const invitationCode = ref(''); //邀请码
	const userName = ref(''); //用户昵称
	const avatar = ref(''); //用户头像
	const sex = ref(''); //用户性别
	const age = ref(''); //用户年龄
	const province = ref(''); //用户定位省
	const city = ref(''); //用户定位市
	const content = ref(''); //用户简介
	const fans = ref(0); //用户粉丝
	const attention = ref(0); //用户关注
	const goodCount = ref(0); //用户获赞
	// 小程序消息订阅
	const arr = reactive([]);
	// 小程序上架开关
	const XCXIsSelect = ref('否');

	// 我的动态
	const Dtlist = reactive([])


	const consortiaId = ref(0); //用户是否达人认证  1已认证
	const isLogin = ref(false); //是否登录




	const openpopup = (bgImage) => {
		if (uni.getStorageSync('token')) {
			getImage()
			bgshow.value = true
		} else {
			uni.navigateTo({
				url: '/pages/public/login'
			})
		}

	};
	// 联系客服
	const changeKefu = () => {
		let that = this
		let SelKeFu = uni.getStorageSync('SelKeFu');
		if (SelKeFu == '1') {
			// #ifdef MP-WEIXIN
			try {
				wx.openCustomerServiceChat({
					extInfo: {
						url: uni.getStorageSync('SelKeFuLink')
					},
					corpId: uni.getStorageSync('SelKeFuAppId'),
					success(res) {},
					fail(res) {
						console.error(res)
					}
				})
			} catch (error) {
				console.error("catchcatch" + error)
				uni.showToast({
					title: '请更新至微信最新版本'
				});
			}
			// #endif
			// #ifndef MP-WEIXIN
			let url = uni.getStorageSync('SelKeFuLink');

			if (url.indexOf('/pages/') !== -1 || url.indexOf('/my/') !== -1) {
				uni.navigateTo({
					url
				});
			} else {
				//#ifndef H5
				uni.navigateTo({
					url: '/pages/index/webView?url=' + url
				});
				//#endif
				//#ifdef H5
				window.location.href = url;
				//#endif
			}
			// #endif
		} else if (SelKeFu == '2') {
			let token = uni.getStorageSync('token');
			if (token) {
				uni.makePhoneCall({
					phoneNumber: uni.getStorageSync('kefuPhone')
				});
			} else {
				goLoginInfo();
			}
		} else if (SelKeFu == '4') {
			let token = uni.getStorageSync('token');
			if (token) {
				uni.navigateTo({
					url: '/my/setting/customer'
				});
			} else {
				goLoginInfo();
			}
		}
	};
	// 获取我的动态
	const getList = () => {
		let userId = uni.getStorageSync('userId');
		let data = {
			page: 1,
			size: 20,
			// byUserId: userId,
			userId: userId,
			// followUserId: 0,
			auditStatus: 1, //状态 0待审核 1已发布 2已下架	
			// city: uni.getStorageSync('city')
		}

		proxy.$http.postJson('/app/moments/page', data).then(res => {
			if (res.code == 0) {
				let arr = []
				res.data.records.forEach(d => {
					d.imgleng = ''
					if (d.img) {
						let img = d.img.split(',')
						d.imgleng = img[0]
					}
					arr.push(d);
				})
				Dtlist.splice(0, Dtlist.length, ...arr)
			}

		});
	};
	// 获取用户信息
	const getUserInfo = () => {
		proxy.$http.get("/app/base/user/info").then(res => {
			if (res.code == 0) {
				invitationCode.value = res.data.invitationCode
				userName.value = res.data.userName
				avatar.value = res.data.avatar
				sex.value = res.data.sex
				age.value = res.data.age
				// province.value = res.data.province
				// city.value = res.data.city
				// consortiaId.value = res.data.consortiaId
				content.value = res.data.content
				if (res.data.userBackgroundImage) {
					swiperImage.value = res.data.userBackgroundImage
				} else {
					getImage()
				}
			}
		});
	};
	// 跳转登录
	const goLogin = () => {
		uni.navigateTo({
			url: '/pages/public/login'
		})
	};
	//页面跳转
	const goNav = (e, name) => {

		if (uni.getStorageSync('token')) {
			// #ifdef MP-WEIXIN
			if (uni.getStorageSync('sendMsg')) {
				wx.requestSubscribeMessage({
					tmplIds: arr,
					success(re) {
						var datas = JSON.stringify(re);
						if (datas.indexOf("accept") != -1) {
							// console.log(re)
						}
					},
					fail: (res) => {
						// console.log(res)
					}
				})
			}
			// #endif
			uni.navigateTo({
				url: e
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
	// 更新位置
	const gxweizhi = () => {
		uni.getLocation({
			type: 'wgs84',
			success(ret) {
				getLatAndLngAddress(ret.latitude, ret.longitude)
			},
			fail(err) {
				console.log(err)
				uni.showToast({
					title: '获取位置失败',
					icon: 'none'
				})
			}
		})

	};
	//根据经纬度获取地址
	const getLatAndLngAddress = (lat, lng) => {
		uni.showLoading({
			title: '更新位置中...'
		})
		let data = {
			lat: lat,
			lng: lng,
		}
		proxy.$http.get('/app/Login/selectAddress', data).then(res => {
			if (res.code == 0) {
				let datas = {
					city: res.data.addressComponent.city,
					province: res.data.addressComponent.province
				}
				proxy.$http.post('/app/user/updateUserCity', datas).then(ret => {
					if (ret.code == 0) {
						getUserInfo()
					} else {
						uni.showToast({
							title: ret.msg,
							icon: 'none'
						})
					}
					uni.hideLoading()
				})

			} else {
				uni.showToast({
					title: res.msg,
					icon: 'none'
				})
			}
		})
	};
	// 获取系统配置背景图
	const getImage = () => {
		proxy.$http.post('/app/userBackground/list').then(res => {
			if (res.code == 0) {
				let arr = []
				res.data.forEach(d => {
					d.radiovalue = false
					d.imageUrl = d.imgUrl
					arr.push(d)
				})
				swiperList.splice(0, swiperList.length, ...arr)
				if (res.data.length != 0) {
					bgImage.value = res.data[0].imgUrl
				}
			}
		})
	};
	// 修改背景图
	const groupChange = (n) => {
		let userBackgroundImage = n.imageUrl
		proxy.$http.postJson("/app/userBackground/bind", {
			imgUrl: userBackgroundImage,
			userId: uni.getStorageSync('userId')
		}).then(res => {
			if (res.code === 0) {
				uni.showToast({
					title: '上传成功',
					icon: "none"
				})
				getUserInfo()
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
						url: config.APIHOST + '/file/upload',
						// url: config.APIHOST + '/file/upload', //真实的接口地址
						filePath: res.tempFilePaths[i],
						name: 'file',
						success: (uploadFileRes) => {
							uni.hideLoading();
							let url = JSON.parse(uploadFileRes.data)
							if (url.code == 0) {
								proxy.$http.postJson("/app/userBackground/bind", {
									imgUrl: url.data.path.toString(),
									userId: uni.getStorageSync('userId')
								}).then(res => {
									if (res.code === 0) {
										uni.showToast({
											title: '切换成功',
											icon: "none"
										})
										getUserInfo()
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
	// 获取顶部板块 粉丝 关注 获赞
	
	const getInfo=()=>{
		let userId = uni.getStorageSync('userId')
		proxy.$http.get("/app/index/myData?userId="+userId).then(res => {
			if (res.code == 0) {
				fans.value = res.data.fansCount
				attention.value = res.data.followCount
				goodCount.value = res.data.likerCount
				
			
			}
		});
	};
	onLoad(option => {
		uni.hideTabBar();
		getImage();

	});
	onShow(() => {
		if (uni.getStorageSync('token')) {
			isLogin.value = true
			token.value = uni.getStorageSync('token');
			invitationCode.value = uni.getStorageSync('invitationCode');
			userId.value = uni.getStorageSync('userId');
			userName.value = uni.getStorageSync('userName');
			getUserInfo();
			getInfo();
			getList()
		} else {
			isLogin.value = false
			userId.value = '';
			token.value = '';
			userName.value = '';
			avatar.value = '';
			sex.value = '';
			age.value = '';
			province.value = '';
			city.value = '';
			invitationCode.value = '';
			content.value = '';
			consortiaId.value = 0;
			fans.value = 0;
			attention.value = 0;
			goodCount.value = 0;
			Dtlist.splice(0, Dtlist.length)
		}
	});
	onPullDownRefresh(() => {

	});
	onReachBottom(() => {

	});
</script>

<style lang="scss">
	page {
		background: #FFFFFF;
	}

	.myBgImgs {
		width: 100%;
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		z-index: 9;

		image {
			width: 100%;
		}

		.setIcon {
			position: absolute;
			bottom: 200rpx;
			right: 37rpx;
			z-index: 9;

			image {
				width: 46rpx;
				height: 46rpx;
			}
		}
	}

	.infobox {
		width: 100%;
		background-color: #FFFFFF;
		border-radius: 24rpx 24rpx 0 0;
		position: relative;
		top: 260rpx;
		z-index: 10;

		.Info {
			width: 100%;

			// min-height: 100vh;
			// 头像
			.Info_one {
				display: flex;
				justify-content: space-between;
				align-items: flex-end;
				height: 90rpx;
				padding: 0 30rpx;

				.Info_oneAvatr {
					width: 190rpx;
					height: 190rpx;
					border-radius: 88rpx;
					background: #FFFFFF;
					display: flex;
					align-items: center;
					justify-content: center;

					image {
						width: 170rpx;
						height: 170rpx;
						border-radius: 88rpx;
					}
				}

				.Info_onebjbtn {
					width: 210rpx;
					height: 55rpx;
					background: #FFFFFF;
					border-radius: 28rpx;
					border: 1px solid #00BC4F;
					font-family: Microsoft YaHei;
					font-weight: 400;
					font-size: 24rpx;
					color: #00BC4F;
					display: flex;
					align-items: center;
					justify-content: center;

				}
			}

			.Info_two {
				margin: 15rpx 30rpx;

				// 昵称
				.Info_title {
					font-family: Microsoft YaHei;
					font-weight: bold;
					font-size: 32rpx;
					color: #000000;
				}

				// 性别 位置
				.Info_room {
					display: flex;
					align-items: center;
					margin-top: 10rpx;

					.Info_sexman {
						color: #FFFFFF;
						background-color: #48A0F9;
						padding: 0rpx 15rpx;
						border-radius: 55rpx;
						height: 38rpx;
						font-family: Microsoft YaHei;
						font-weight: 400;
						font-size: 22rpx;
						color: #FFFFFF;
						display: flex;
						align-items: center;
						justify-content: center;
					}

					.Info_sexwoman {
						background-color: #F948B9;
						color: #FFFFFF;
						padding: 0rpx 15rpx;
						border-radius: 55rpx;

						height: 38rpx;
						font-family: Microsoft YaHei;
						font-weight: 400;
						font-size: 22rpx;
						color: #FFFFFF;
						display: flex;
						align-items: center;
						justify-content: center;
					}

					.Info_add {
						padding: 6rpx 10rpx;
						border-radius: 55rpx;
						font-size: 22rpx;
						display: flex;
						align-items: center;
						justify-content: center;
						margin-left: 15rpx;
					}
				}

				// 用户简介
				.Info-content {
					font-family: Source Han Sans CN;
					font-weight: 400;
					font-size: 26rpx;
					color: #000000;
					margin-top: 15rpx;
				}

			}


		}
	}

	// 我的动态
	.dt {
		margin: 30rpx;

		.dt_top {
			display: flex;
			align-items: center;
			justify-content: space-between;

			.dtname {
				font-family: Source Han Sans CN;
				font-weight: bold;
				font-size: 32rpx;
				color: #232323;

			}

			.dtall {
				font-family: Microsoft YaHei;
				font-weight: 400;
				font-size: 22rpx;
				color: #666666;

				image {
					width: 11rpx;
					height: 18rpx;
					margin-left: 10rpx;
				}
			}
		}

		.scroll-view_H {
			white-space: nowrap;
			width: 100%;
			margin-top: 20rpx;
		}

		.scroll-view-item_H {
			display: inline-block;
			width: 261rpx;
			margin-right: 20rpx;
		}
	}



	.category {
		padding-bottom: 80px;
		.setname {
			font-family: Source Han Sans CN;
			font-weight: bold;
			font-size: 32rpx;
			color: #232323;
			margin: 30rpx;
		}

		.category-list {

			width: 100%;
			height: auto;
			display: flex;
			// justify-content: flex-start;
			justify-content: space-between;
			flex-flow: wrap;

			.boxitem {
				width: 25%;
				text-align: center;
				margin-bottom: 35rpx;
				font-family: Microsoft YaHei;
				font-weight: 400;
				font-size: 24rpx;
				color: #000000;

				image {
					width: 43rpx;
					height: 43rpx;
				}
			}
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