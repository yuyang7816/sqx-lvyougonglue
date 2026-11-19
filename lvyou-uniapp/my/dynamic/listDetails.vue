<template>
	<view style="padding-bottom: 140rpx;">
		<view class="dt_box">
			<view class="dt_head">
				<view class="dt_img" @click="goGuanZhuDetail(item.userId)">
					<image :src="item.avatar ? item.avatar : '/static/logos.png'" class="dt_img"></image>
				</view>
				<view class="dt_left">
					<view class="dt_name">{{item.userName}}</view>

					<view class="dt_btn" v-if="item.userId!=userId">
						<view class="dt_gz" v-if="!isfollow" @click="guanzhu">关注</view>
						<view class="dt_ygz" v-else @click="guanzhu">已关注</view>
						<view class="dt_ts" @click.stop="goTousu(item.userId,1)">
							<image src="/static/image/dt/more.png"></image>
						</view>
					</view>
				</view>
			</view>

			<view class="margin-top-sm">
				<swiper class="swiper-container" :autoplay="true" :interval="4000" :circular="true"
					:indicator-dots="true" indicator-active-color="#1EB05F" indicator-color="#CCCCCC"
					style="height: 800rpx;">
					<block v-for="(image, ind) in item.img" :key="ind" v-if="item.img">
						<swiper-item class="swiper-wrapper" v-if="image" @click="selectImage(item.img,ind)">
							<image lazy-load="true" fade-show="true" :src="image"
								style="width: 100%; height:800rpx;border-radius: 24rpx;" mode="aspectFill">
							</image>
						</swiper-item>
					</block>
					<swiper-item class="swiper-wrapper" v-if="item.videoUrl">
						<video :src="item.videoUrl" style="width: 100%;height: 800rpx;border-radius: 24rpx;"></video>
					</swiper-item>
				</swiper>
			</view>

			<view class="flex margin-top-sm">
				<view class="dt_add" @click="openMap()" v-if="item.location">
					<image src="/static/image/index/add.png"></image>
					<text>{{item.location}}</text>
				</view>
			</view>

			<view class="dt_Title">
				{{item.title}}
			</view>

			<view class="dt_content">
				<text v-html="item.content"></text>
			</view>

			<view class="dt_data">
				<view class="">{{item.createTime}}发布</view>
				<view class="">{{item.views}}浏览</view>
			</view>

			<view class="margin-top">

				<scroll-view scroll-x="true" class="scroll-view_H">
					<view id="demo1" class="scroll-view-item_Ho" v-if="item.scenicSpotVo">

						<image :src="item.scenicSpotVo.img?item.scenicSpotVo.img:'/static/logos.png'"
							class="scroll-view-item_HAvatr"></image>
						<view class="scroll-view-item_Hbox">
							<view class="scroll-view-item_Hboxleft">
								<view class="scroll-view-item_Hboxleft_name">{{item.scenicSpotVo.name}}</view>
								<view class="scroll-view-item_Hboxleft_price">
									<text style="font-size: 24rpx;">￥</text>
									<text>{{item.scenicSpotVo.ticketPrice}}</text>起
								</view>
							</view>
							<view class="scroll-view-item_Hboxrit" @click="goScenicspotDtail(item.scenicSpotVo)">
								去咨询
								<up-icon name="play-right-fill" color="#5b5b5b" size="12"></up-icon>
							</view>
						</view>
					</view>
				</scroll-view>
			</view>
		</view>


		<view class="pl">
			<view class="pl_tit">评论({{totalElement?totalElement:'0'}})</view>
			<view v-for="(ite,ind) in list" :key="ind" class="plItm" @longpress.stop="opentit(ind)">
				<view class="pl_head">
					<image :src="ite.avatar?ite.avatar:'/static/logos.png'" class="pl_avatr"
						@click.stop="goGuanZhuDetail(ite.userId)"></image>
					<view class="pl_box">
						<view class="pl_boxleft">
							<view class="pl_boxleft_name">{{ite.userName}}</view>
							<view class="pl_Author">{{ite.createTime}}</view>
						</view>
						<view class="pl_boxright" @click.stop="dianzan(ite,ind)">
							<image src="@/static/image/dt/xin.png" v-if="!ite.isLike"></image>
							<image src="@/static/image/dt/xins.png" v-else></image>
							<text
								:style="!ite.isLike?'color:#999999':'color:#FF3838'">{{ite.likeCount?ite.likeCount:'0'}}</text>
						</view>
					</view>
				</view>
				<view class="pl_cot">
					{{ite.content}}
					<text class="zhiding" v-if="ite.isRecommend==1">置顶</text>
				</view>
				<view class="pl_Img" @click="saveImg(ite.contentImg,0)" v-if="ite.contentImg">
					<image :src="ite.contentImg" mode="widthFix"></image>
				</view>



			</view>

			<up-loadmore :status="status" v-if="list.length>0" />

		</view>


		<view class="taber">
			<view class="taber_left">
				<view class="taber_leftInput">
					<input confirm-type="send" maxlength="200" @confirm='setChatSave' type="text" v-model="content"
						:focus="focus" :placeholder="placeholder"
						style="color: black;font-size: 26rpx;height: 80rpx;" />
					<image src="/my/static/dtimg.png" mode="widthFix" @click="uploudRes"></image>
				</view>
				<view class="padding-bottom-sm" style="position: relative;width: 80rpx;" v-if="contentImg">
					<image :src="contentImg" style="width: 80rpx;height: 80rpx;border-radius: 12rpx;" mode="aspectFill">
					</image>
					<view style="position: absolute;top: 5rpx;right:5rpx;" @click="clearImage">
						<image src="../static/dtgb.png" style="width:35rpx;height: 35rpx;"></image>
					</view>
				</view>
			</view>

			<view class="taber_right">
				<view class="taber_rightbox" @click="collectDt(item.id)">
					<image src="/static/image/dt/shoucang.png" v-if="!item.isCollect"></image>
					<image src="../../static/image/dt/likes.png" v-else></image>
					<view>{{item.collectNum?item.collectNum:0}}</view>
				</view>
				<view class="taber_rightbox" @tap="saveLove(item.id)">
					<image src="/static/image/dt/zanon.png" v-if="!item.isLike"></image>
					<image src="/static/image/dt/zans.png" v-else></image>
					<view>{{item.likeNum ? item.likeNum : 0}}</view>
				</view>
				<view class="taber_rightbox">
					<image src="/static/image/dt/msgon.png"></image>
					<view>评论</view>
				</view>

				<!-- #ifndef MP-WEIXIN -->
				<view class="taber_rightbox" @tap.stop="getShareImg()">
					<image src="/static/image/dt/shares.png"></image>
					<view class="">分享</view>
				</view>
				<!-- #endif -->
				<!-- #ifdef MP-WEIXIN -->
				<view class="">
					<button open-type="share" catchtap="share">
						<view class="taber_rightbox">
							<image src="/static/image/dt/shares.png"></image>
							<view class="">分享</view>
						</view>
					</button>
				</view>
				<!-- #endif -->


			</view>
		</view>



		<up-popup :show="plshow" @close="plshow = false">
			<view class="" style="background: #F5F5F5;border-radius: 24rpx;padding:40rpx 30rpx 30rpx;">
				<view class="popupbtn" @click="copyClick()">
					<image src="../static/fizhi.png" style="width: 50rpx;height: 50rpx;margin-right: 15rpx;"></image>
					复制该评论
				</view>

				<view class="popupbtn" @click="detepl()" v-if="userId==item.userId||byUserId==userId">
					<image src="../static/shanchu.png" style="width: 50rpx;height: 50rpx;margin-right: 15rpx;"></image>
					删除
				</view>

				<view class="popupbtn" @click="goTousu('',2)">
					<image src="../static/tousu.png" style="width: 50rpx;height: 50rpx;margin-right: 15rpx;"></image>
					投诉
				</view>
			</view>
		</up-popup>

		<!-- 群二维码弹窗 -->
		<up-popup :show="showQrCode" round="24" mode="center">
			<view class="qrCode">
				<view class="qrCodeBg">
					<image src="/my/static/groupChat/qrCodebg.png"></image>
				</view>
				<!-- #ifndef APP -->
				<view class="qrCode-txt">长按识别二维码添加</view>
				<!-- #endif -->
				<!-- #ifdef APP -->
				<view class="qrCode-txt">长按保存图片，微信识别二维码</view>
				<!-- #endif -->
				<view class="qrCode-img ">
					<swiper class="swiper-container" :autoplay="true" :interval="4000" :circular="true"
						:indicator-dots="false" indicator-active-color="#f2ad44" indicator-color="#cccccc">
						<block v-for="(item, index3) in groupQrcode" :key="index3">
							<swiper-item class="swiper-wrapper" @click="privImgs(item)" v-if="item">
								<image lazy-load="true" fade-show="true" :src="item"
									style="width: 100%; height:320rpx;border-radius: 24rpx;" mode="aspectFill">
								</image>
							</swiper-item>
						</block>
					</swiper>
				</view>
				<view class="qrCode-close ">
					<u-icon @click="showQrCode = false" name="close-circle" color="#000000" size="25"></u-icon>
				</view>
			</view>
		</up-popup>

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
		getCurrentInstance,
		nextTick,
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

	const talkId = ref(''); //动态ID
	const item = ref('')
	const list = reactive([]); //评论
	const pages = ref(1);
	const page = ref(1);
	const limit = ref(5);
	const totalElement = ref(''); //评论总数
	const userId = ref('');
	//加载状态
	const status = ref('loadmore');
	// 评论模块
	const byUserId = ref('');
	const commentId = ref('');
	const content = ref('');
	const contentImg = ref('');

	const focus = ref(false);
	const placeholder = ref('发布评论...')
	const plshow = ref(false);
	const copyContent = ref(''); //复制评论内容

	const canvasImgSrc = ref(''); ///canvas生成的图片
	const showShareImg = ref(false); ////是否显示海报弹窗
	const backgroundImage = ref(''); //海报背景图
	const shareImgHeight = ref(0); ////海报高度
	const shareImgWidth = ref(uni.upx2px(686)); //海报宽度
	const invitationCode = ref('');
	const userName = ref('');
	const avatar = ref('');
	const Dtcontent = ref('');
	const DtTitle = ref('');
	//推广文案
	const tuiguang1 = ref('');
	const url = ref('');
	const qrImg = ref('');
	const shareH5Url = ref('');
	const showQrCode = ref(false);
	const isfollow = ref(false); //是否关注


	// 海报绘制
	const sharebg = ref('/static/image/qun/qunbg.png');

	// 查看图片
	const saveImg = (imgs, index) => {
		let imgArr = []
		imgArr.push(imgs);
		// //预览图片
		uni.previewImage({
			urls: imgArr,
			current: imgArr[0]
		});
	};
	// 查看图片
	const selectImage = (url, index) => {
		uni.previewImage({
			current: index,
			urls: url,

		})
	};
	// 跳转投诉
	const goTousu = (userId, index) => {
		if (uni.getStorageSync('token')) {
			if (index == 2) { //评论投诉
				plshow.value = false
				uni.navigateTo({
					url: '/my/order/complain?byUserId=' + byUserId.value + '&platform=1&platformId=' +
						commentId.value
				});
			} else { //动态投诉
				uni.navigateTo({
					url: '/my/order/complain?byUserId=' + userId + '&platform=1&platformId=' + talkId.value
				});
			}
		} else {
			uni.navigateTo({
				url: '/pages/public/login'
			})
		}
	};
	// 跳转用户主页
	const goGuanZhuDetail = (userId) => {
		if (userId) {
			uni.navigateTo({
				url: '/my/gird/zhuye?userId=' + userId
			});
		}
	};
	// 打开动态位置
	const openMap = () => {
		uni.openLocation({
			latitude: item.value.latitude - 0, //要去的纬度-地址       
			longitude: item.value.longitude - 0, //要去的经度-地址
			name: item.value.address, //地址名称
			address: item.value.address, //详细地址名称
			success: function() {
				console.log('导航成功');
			},
			fail: function(error) {
				console.log(error)
			}
		});
	};
	// 动态点赞
	const saveLove = (talkId) => {
		if (uni.getStorageSync('token')) {
			let userId = uni.getStorageSync('userId');
			let data = {
				dataId: talkId,
				userId: userId,
				type: 1 //1动态 2景点 3路线
			}
			proxy.$http.postJson('/app/like/save', data).then(res => {
				if (res.code == 0) {
					getListDetail();
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
	// 跳转景点详情
	const goScenicspotDtail = (ite) => {
		if (uni.getStorageSync('token')) {
			uni.navigateTo({
				url: '/my/Route/ScenicspotDtail?id=' + ite.id
			})
		} else {
			uni.navigateTo({
				url: '/pages/public/login'
			})
		}

	};
	//收藏
	const collectDt = (talkId) => {
		let userId = uni.getStorageSync('userId');
		let data = {
			dataId: talkId,
			userId: userId,
			type: 1 //1动态 2景点 3路线 4商户
		}
		proxy.$http.postJson('/app/collect/save', data).then(res => {
			if (res.code == 0) {
				getListDetail();
			} else {
				uni.showToast({
					title: res.msg,
					icon: 'none'
				})
			}
		});
	};
	// 获取动态详情
	const getListDetail = () => {
		uni.showLoading({
			title: '加载中...'
		});
		let userId = uni.getStorageSync('userId') ? uni.getStorageSync('userId') : ''
		let data = {
			id: talkId.value,
			byUserId: userId,
			userId: userId,
			longitude: uni.getStorageSync('lng'),
			latitude: uni.getStorageSync('lat')
		}
		proxy.$http.postJson('/app/moments/info', data).then(async res => {
			if (res.code == 0) {
				if (res.data.scenicSpotVo && res.data.scenicSpotVo.img) {
					let img = res.data.scenicSpotVo.img.split(',')
					res.data.scenicSpotVo.img = img[0]
				}
				console.log(res.data.scenicSpotVo.img)
				if (res.data.img) {
					res.data.img = res.data.img.split(',');
				}

				// let time = ''
				// res.data.createTime = this.getDateDiff(res.data.createTime);
				if (res.data.createTime) {
					res.data.createTime = res.data.createTime.substr(0, 10);
				}


				// 处理点赞大于1万
				if (res.data.likeNum) {
					if (res.data.likeNum >= 10000) {
						res.data.likeNum = Number(res.data.likeNum / 10000).toFixed(0) + 'w'
					} else {
						res.data.likeNum = Number(res.data.likeNum).toFixed(0)
					}
				} else {
					res.data.likeNum = '0'
				}

				// 处理收藏大于1万
				if (res.data.collectNum) {
					if (res.data.collectNum >= 10000) {
						res.data.collectNum = Number(res.data.collectNum / 10000).toFixed(0) + 'w'
					} else {
						res.data.collectNum = Number(res.data.collectNum).toFixed(0)
					}
				} else {
					res.data.collectNum = '0'
				}

				item.value = res.data;



				userName.value = res.data.userName
				avatar.value = res.data.avatar
				Dtcontent.value = res.data.content
				DtTitle.value = res.data.title
				if (res.data.img) {
					backgroundImage.value = res.data.img[0];
					let _imgInfo = await getImageInfo(backgroundImage.value);
					let caveHeight = (shareImgWidth.value * _imgInfo.height) / _imgInfo.width;
					shareImgHeight.value = parseInt(caveHeight + 100)
				} else {
					proxy.$http.postJson('/app/base/banner/page', {
						classify: 3, // 分类1banner图 2金刚区分类	 3海报背景图
						state: 1, //状态1正常 2隐藏
					}).then(async res => {
						if (res.code == 0 && res.data && res.data.records.length != 0) {
							backgroundImage.value = res.data.records[0].imageUrl;
							let _imgInfo = await getImageInfo(backgroundImage.value);
							let caveHeight = (shareImgWidth.value * _imgInfo.height) / _imgInfo
								.width;
							shareImgHeight.value = parseInt(caveHeight + 100)
						}
					})
				}
				getguanzhu()

			} else {
				uni.navigateBack()
			}
			uni.hideLoading();
		});
	};

	// 获取评论列表
	const getPingLun = () => {
		uni.showLoading({
			title: '加载中...'
		});
		let userId = uni.getStorageSync('userId') ? uni.getStorageSync('userId') : '';
		let data = {
			page: page.value,
			limit: limit.value,
			dataId: talkId.value,
			byUserId: userId,
			type: 1
		}
		status.value = 'loadmore';
		proxy.$http.postJson('/app/comment/page', data).then(res => {
			if (res.code === 0) {
				pages.value = res.data.pages
				if (page.value < pages.value) {
					status.value = 'loadmore';
				} else {
					status.value = 'nomore';
				}
				totalElement.value = res.data.total
				let time = ''
				res.data.records.map((d, index) => {
					d.createTime = getDateDiff(d.createTime);
					d.page = 0;
					if (d.createTime === time) {
						d.createTime = '';
					} else {
						d.createTimee = d.createTime;
					}
				})
				if (page.value == 1) {
					list.splice(0, list.length, ...res.data.records);
				} else {
					list.push(...res.data.records);
				}

			}
			status.value = 'nomore';
			uni.hideLoading();
			uni.stopPullDownRefresh()
		});
	};
	// 清除评论图片
	const clearImage = () => {
		contentImg.value = ''
	};
	//评价长按出现投诉和复制
	const opentit = (index) => {
		byUserId.value = list[index].userId
		copyContent.value = list[index].content
		commentId.value = list[index].id
		plshow.value = true

	};
	//复制内容
	const copyClick = () => {
		uni.setClipboardData({
			data: copyContent.value,
			success: function(res) {
				uni.getClipboardData({
					success: function(res) {
						uni.showToast({
							title: "复制成功",
							icon: 'none',
						});
						plshow.value = false
					},
				});
			},
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
	// 输入框 发送评论
	const setChatSave = () => {
		if (uni.getStorageSync('token')) {
			if (!content.value) {
				uni.showToast({
					title: "请输入评论内容",
					mask: false,
					duration: 1500,
					icon: "none"
				});
				return;
			}
			if (proxy.$getChatSearchKeys(content.value)) {
				uni.showToast({
					title: "输入内容带有非法关键字请重新输入",
					mask: false,
					duration: 1500,
					icon: "none"
				});
				return;
			}

			let userId = uni.getStorageSync('userId');
			let data = {
				dataId: talkId.value,
				type: 1,
				userId: userId,
				content: content.value,
				contentImg: contentImg.value
			}
			proxy.$http.postJson('/app/comment/save', data).then(res => {
				if (res.code == 0) {
					let data1 = {
						userName: uni.getStorageSync('userName'),
						avatar: uni.getStorageSync('avatar'),
						userId: uni.getStorageSync('userId'),
						byNickName: '',
						goodCounts: 0,
						createTime: '刚刚',
						content: content.value,
						childer: [],
						goodUserCounts: 0,
						commentId: res.data ? res.data.commentId : '',
						contentImg: contentImg.value,
					}
					list.push(data1)
					content.value = '';
					contentImg.value = '';
					placeholder.value = '发布评论...'
					totalElement.value = Number(totalElement.value + 1)
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
	//回复评论
	const subhuifu = (index, indexs) => {
		let token = uni.getStorageSync('token')
		if (token) {
			placeholder.value = '发布评论...'
			focus.value = false

			nextTick(() => {
				focus.value = true
			})


		} else {
			uni.navigateTo({
				url: '/pages/public/login'
			})
		}

	};

	//删除评论
	const detepl = () => {
		let data = {
			id: commentId.value,
		}
		proxy.$http.postJson('/app/comment/delete', data).then(res => {
			if (res.code == 0) {
				plshow.value = false
				placeholder.value = '发布评论...'
				subhuifu('', 0)
				commentId.value = ''
				byUserId.value = ''
				getListDetail()
				page.value = 1
				getPingLun();
			} else {
				uni.showToast({
					title: res.msg,
					icon: 'none'
				})
			}
		});
	};
	// 点赞评论
	const dianzan = (item, ind) => {
		let userId = uni.getStorageSync('userId');
		if (!userId) {
			uni.navigateTo({
				url: '/pages/public/login'
			})
			return
		}
		proxy.$http.postJson("/app/like/save", {
			dataId: item.id,
			userId: userId,
			type: 2, //1 动态  2评论
		}).then(res => {
			if (res.code == 0) {
				item.isLike = !item.isLike
				if (item.isLike) {
					item.likeCount += 1
				} else {
					item.likeCount -= 1
				}

			}
		});
	};
	//评论上传图片
	const uploudRes = () => {
		uni.chooseImage({
			count: 1,
			sourceType: ['album', 'camera'],
			success: res => {
				for (let i = 0; i < res.tempFilePaths.length; i++) {
					uni.showLoading({
						title: '上传中'
					});
					uni.uploadFile({ // 上传接口
						// url: this.config("APIHOST1") + '/file/upload', //真实的接口地址
						url: config.APIHOST + '/file/upload',
						filePath: res.tempFilePaths[i],
						name: 'file',
						success: (uploadFileRes) => {
							uni.hideLoading();
							let url = JSON.parse(uploadFileRes.data)
							console.log(url)
							if (url.code == 0) {
								contentImg.value = url.data.path
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
	};
	const getDateDiff = (data) => {
		// 传进来的data必须是日期格式，不能是时间戳
		//var str = data;
		//将字符串转换成时间格式
		var timePublish = new Date(data);
		var timeNow = new Date();
		var minute = 1000 * 60;
		var hour = minute * 60;
		var day = hour * 24;
		var month = day * 30;
		var result = "2";

		var diffValue = timeNow - timePublish;
		var diffMonth = diffValue / month;
		var diffWeek = diffValue / (7 * day);
		var diffDay = diffValue / day;
		var diffHour = diffValue / hour;
		var diffMinute = diffValue / minute;

		if (diffMonth > 3) {
			result = timePublish.getFullYear() + "-";
			result += timePublish.getMonth() + "-";
			result += timePublish.getDate();
		} else if (diffMonth > 1) { //月
			result = data.substring(0, 16);
		} else if (diffWeek > 1) { //周
			result = data.substring(0, 16);
		} else if (diffDay > 1) { //天
			result = data.substring(0, 16);
		} else if (diffHour > 72) { //小时
			// result = parseInt(diffHour) + "小时前";
			result = timePublish.getFullYear() + "-";
			result += timePublish.getMonth() + "-";
			result += timePublish.getDate();
		} else if (diffHour > 1) { //小时
			result = parseInt(diffHour) + "小时前发布";
		} else if (diffMinute > 1) { //分钟
			result = parseInt(diffMinute) + "分钟前发布";
		} else {
			result = "刚刚发布";
		}
		return result;
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
		const C_P = uni.upx2px(50); //canvas Paddng 间距	
		const C_PS = uni.upx2px(20); //canvas Paddng 间距
		//海报底部背景图
		let _sharebg = await getImageInfo(sharebg.value);
		console.log(_sharebg)

		// 邀请背景图
		let _imgInfo = await getImageInfo(backgroundImage.value);
		console.log(_imgInfo)

		//获取canvas的上下文对象
		const ctx = uni.createCanvasContext('myShareImg');

		ctx.setFillStyle('#ffffff'); //canvas背景颜色
		ctx.fillRect(0, 0, shareImgWidth.value, shareImgHeight.value); //canvas画布大小
		//绘制背景图片
		ctx.drawImage(_sharebg.path, 0, 0, shareImgWidth.value, shareImgHeight.value);

		//绘制白色内容框（ 可以渐变色区域）
		const gradient = ctx.createLinearGradient(0, 0, shareImgWidth.value, 0);
		gradient.addColorStop(0, '#FFFFFF'); // 起始颜色  
		gradient.addColorStop(1, '#FFFFFF'); // 结束颜色  
		// 绘制圆角矩形  
		ctx.setFillStyle(gradient); // 设置填充样式为渐变  
		ctx.beginPath(); // 开始绘制路径  
		const radius = 24; // 绘制圆角矩形的四个角  
		const x = C_P - uni.upx2px(4);
		const y = C_P;
		const width = shareImgWidth.value - (45);
		const height = shareImgHeight.value - (2 * C_P) - 80;

		ctx.moveTo(x + radius, y); // 左上角开始  
		ctx.lineTo(x + width - radius, y); // 上边线到右边  
		ctx.arc(x + width - radius, y + radius, radius, 1.5 * Math.PI, 0,
			false); // 右上角  
		ctx.lineTo(x + width, y + height - radius); // 右边线到下边  
		ctx.arc(x + width - radius, y + height - radius, radius, 0, 0.5 * Math.PI,
			false); // 右下角  
		ctx.lineTo(x + radius, y + height); // 下边线到左边  
		ctx.arc(x + radius, y + height - radius, radius, 0.5 * Math.PI, Math.PI,
			false); // 左下角  
		ctx.lineTo(x, y + radius); // 左边线到上边  
		ctx.arc(x + radius, y + radius, radius, Math.PI, 1.5 * Math.PI,
			false); // 左上角  
		ctx.closePath(); // 显式闭合路径
		// 填充路径  
		ctx.fill();


		// 绘制海报背景（带剪切区域）
		ctx.save();

		ctx.beginPath(); // 开始绘制路径  
		//绘制海报图  绘制圆角矩形的四个角  位置
		const radiusimg = 20;
		const xs = C_P + C_PS;
		const ys = C_P + C_PS;
		const widths = shareImgWidth.value - (2 * C_P) - (2 * C_PS);
		const heights = shareImgHeight.value - 208 - C_P - C_PS;

		ctx.moveTo(xs + radiusimg, ys); // 左上角开始  
		ctx.lineTo(xs + widths - radiusimg, ys); // 上边线到右边  
		ctx.arc(xs + widths - radiusimg, ys + radiusimg, radiusimg, 1.5 * Math.PI, 0,
			false); // 右上角  
		ctx.lineTo(xs + widths, ys + heights - radiusimg); // 右边线到下边  
		ctx.arc(xs + widths - radiusimg, ys + heights - radiusimg, radiusimg, 0, 0.5 * Math.PI,
			false); // 右下角  
		ctx.lineTo(xs + radiusimg, ys + heights); // 下边线到左边  
		ctx.arc(xs + radiusimg, ys + heights - radiusimg, radiusimg, 0.5 * Math.PI, Math.PI,
			false); // 左下角  
		ctx.lineTo(xs, ys + radiusimg); // 左边线到上边  
		ctx.arc(xs + radiusimg, ys + radiusimg, radiusimg, Math.PI, 1.5 * Math.PI, false); // 左上角  
		ctx.closePath(); // 显式闭合路径
		// ctx.setFillStyle('rgb(170, 170, 255)'); // 使用白色
		ctx.fill();
		ctx.clip(); // 应用裁剪区域
		//绘制背景图片
		ctx.drawImage(_imgInfo.path, C_P + C_PS, C_P + C_PS, shareImgWidth.value - (2 * C_P) - (2 * C_PS),
			shareImgHeight.value - 208 - C_P - C_PS);

		ctx.restore(); // 恢复状态，取消剪切区域

		// 在底部绘制个人信息与二维码
		//绘制头像
		const _avatarInfo = ref('');
		//头像宽高
		const avatarWidthOrHeight = ref(40);
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
		const avatarCenterX = paddingNum.value + 15 + avatarWidthOrHeight.value / 2;
		const avatarCenterY = shareImgHeight.value - 135 - paddingNum.value - avatarWidthOrHeight.value / 2;
		//绘制圆形头像
		ctx.arc(avatarCenterX, avatarCenterY, avatarWidthOrHeight.value / 2, 0, 2 * Math.PI);
		ctx.closePath();
		ctx.clip();
		//绘制头像
		ctx.drawImage(_avatarInfo.value.path, paddingNum.value + 15, shareImgHeight.value - 135 - paddingNum
			.value -
			avatarWidthOrHeight.value, avatarWidthOrHeight.value,
			avatarWidthOrHeight.value);
		ctx.restore();

		//绘制用户昵称
		ctx.setFontSize(14);
		ctx.setFillStyle('#333333');
		ctx.fillText(userName.value, paddingNum.value + 20 + avatarWidthOrHeight.value,
			shareImgHeight.value - 160 - paddingNum.value);

		//绘制用户邀请码
		ctx.setFontSize(12);
		ctx.fillText('ID:' + invitationCode.value, paddingNum.value + 20 + avatarWidthOrHeight.value,
			shareImgHeight.value - 140 - paddingNum.value);
		// 绘制动态内容
		ctx.setFontSize(14);
		if (Dtcontent.value.length <= 20) {
			ctx.fillText(Dtcontent.value + '...', paddingNum.value + 15,
				shareImgHeight.value - 110 - paddingNum.value);
		} else {
			const Title = Dtcontent.value.substring(0, 20)
			ctx.fillText(Title + '...', paddingNum.value + 15,
				shareImgHeight.value - 110 - paddingNum.value);
		}

		//绘制二维码
		ctx.drawImage(qrImg.value, shareImgWidth.value - paddingNum.value - avatarWidthOrHeight.value - 20,
			shareImgHeight.value - paddingNum
			.value - avatarWidthOrHeight.value - 20, avatarWidthOrHeight.value + 20,
			avatarWidthOrHeight.value + 20);
		console.log(qrImg.value, '=====88888888888===')
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
	//文案推广
	const getTuiguang1 = () => {
		// 邀请文案
		proxy.$http.postJson('/app/config/info', {
			key: 'biz_invitation_text'
		}).then(res => {
			if (res.code == 0) {
				if (res.data && res.data.value) {
					tuiguang1.value = res.data.value;
				}
			}
		});

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
					shareH5Url.value = url.value + '/my/dynamic/listDetails?invitation=' + invitationCode
						.value + '&talkId=' + talkId.value
				} else {
					url.value = proxy.$publicYuMing();
					shareH5Url.value = url.value + '/my/dynamic/listDetails?invitation=' + invitationCode
						.value + '&talkId=' + talkId.value
				}
				console.log(shareH5Url.value, url.value, 'shareH5UrlshareH5Url')
			}
		});
		//#endif
	};
	//查询关注状态
	const getguanzhu = () => {
		proxy.$http.getT("/app/base/user/userData?userId=" + item.value.userId).then(res => {
			if (res.code == 0) {
				isfollow.value = res.data.isFollow
			}
		});
	};
	//关注/取消关注
	const guanzhu = () => {
		if (uni.getStorageSync('token')) {
			proxy.$http.postJson("/app/follow/save", {
				targetUserId: item.value.userId,
				userId: uni.getStorageSync('userId')
			}).then(res => {
				if (res.code == 0) {
					uni.showToast({
						title: res.msg,
						icon: 'none'
					})
					getguanzhu();
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
	onLoad(option => {

		if (option.invitation) {
			uni.setStorageSync('inviterCode', option.invitation)
		}
		// #ifdef MP-WEIXIN
		if (option.scene) {
			const scene = decodeURIComponent(option.scene);
			uni.setStorageSync('inviterCode', scene.split(',')[0])
			talkId.value = scene.split(',')[1]
		}
		// #endif

		talkId.value = option.talkId;
		userId.value = uni.getStorageSync('userId') ? uni.getStorageSync('userId') : '';
		invitationCode.value = uni.getStorageSync('invitationCode');

		getListDetail(); //获取动态详情
		getPingLun(); //获取评论列表
		getTuiguang1(); //分享地址

	});
	onShow(() => {

	});
	onPullDownRefresh(() => {
		page.value = 1;
		getPingLun();
	});
	onReachBottom(() => {
		if (page.value < pages.value) {
			page.value = parseInt(page.value + 1)
			status.value = 'loading';
			getPingLun();
		}
	});
</script>

<style lang="scss">
	page {
		background: #F5F5F5;
	}

	.dt_box {
		background: #FFFFFF;
		padding: 30rpx;

		// 顶部用户信息
		.dt_head {
			display: flex;
			align-items: center;

			.dt_img {
				width: 74rpx;
				height: 74rpx;
				border-radius: 88rpx;
			}

			.dt_left {
				flex: 1;
				display: flex;
				align-items: center;
				justify-content: space-between;
				margin-left: 10rpx;

				.dt_name {
					font-family: PingFang SC;
					font-weight: bold;
					font-size: 28rpx;
					color: #1E1E1E;
				}

				.dt_btn {
					display: flex;
					align-items: center;

					.dt_ygz {
						width: 90rpx;
						height: 40rpx;
						background: #FFFFFF;
						border-radius: 20rpx;
						border: 2px solid #999999;
						font-family: PingFang SC;
						font-weight: 500;
						font-size: 22rpx;
						color: #999999;
						display: flex;
						align-items: center;
						justify-content: center;
					}

					.dt_gz {
						width: 90rpx;
						height: 40rpx;
						background: #FFFFFF;
						border-radius: 55rpx;
						border: 1px solid #00BC4F;
						font-family: PingFang SC;
						font-weight: 500;
						font-size: 22rpx;
						color: #00BC4F;
						display: flex;
						align-items: center;
						justify-content: center;
					}

					.dt_ts {
						margin-left: 20rpx;
						display: flex;
						align-items: center;
						justify-content: center;

						image {
							width: 40rpx;
							height: 10rpx;
						}
					}
				}
			}
		}

		// 位置
		.dt_add {
			padding: 0 20rpx;
			height: 50rpx;
			background: #F2F1EC;
			border-radius: 25rpx;
			display: flex;
			align-items: center;
			justify-content: center;

			text {
				font-family: Source Han Sans CN;
				font-weight: 500;
				font-size: 24rpx;
				color: #1C1C1C;
				margin-left: 8rpx;
			}

			image {
				width: 21rpx;
				height: 26rpx;
			}
		}

		// 标题
		.dt_Title {
			font-family: Source Han Sans CN;
			font-weight: 800;
			font-size: 32rpx;
			color: #343434;
			margin-top: 20rpx;
		}

		// 内容
		.dt_content {
			margin-top: 20rpx;
			font-family: Source Han Sans CN;
			font-weight: 500;
			font-size: 28rpx;
			color: #343434;
		}

		.dt_data {
			display: flex;
			align-items: center;
			justify-content: space-between;
			font-family: PingFang SC;
			font-weight: 500;
			font-size: 24rpx;
			color: #9D9EAB;
			margin-top: 20rpx;
		}
	}

	.scroll-view_H {
		white-space: nowrap;
		width: 100%;

		.scroll-view-item_Ho {
			background: #F5F6F8;
			display: inline-flex;
			width: 97%;
			border-radius: 24rpx;
			padding: 10rpx;
		}

		.scroll-view-item_H {
			background: #F5F6F8;
			display: inline-flex;
			width: 529rpx;
			margin-right: 20rpx;
			border-radius: 24rpx;
			padding: 10rpx;
		}

		.scroll-view-item_HAvatr {
			width: 90rpx;
			height: 90rpx;
			border-radius: 24rpx;
		}

		.scroll-view-item_Hbox {
			display: flex;
			align-items: center;
			justify-content: space-between;
			margin-left: 15rpx;
			flex: 1;

			.scroll-view-item_Hboxleft {
				.scroll-view-item_Hboxleft_name {
					font-family: Source Han Sans CN;
					font-weight: bold;
					font-size: 28rpx;
					color: #333333;
				}

				.scroll-view-item_Hboxleft_price {
					font-family: Source Han Sans CN;
					color: #9D9EAB;
					font-size: 24rpx;

					text {
						font-size: 30rpx;
						color: #222222;
						font-weight: bold;
					}
				}
			}

			.scroll-view-item_Hboxrit {
				display: flex;
				align-items: center;
				margin-right: 10rpx;
				font-family: PingFang SC;
				font-weight: 500;
				font-size: 24rpx;
				color: #252525;
			}
		}

	}

	// 评论模块
	.pl {
		background: #FFFFFF;
		margin-top: 20rpx;
		padding: 20rpx 30rpx;

		.pl_tit {
			font-family: Source Han Sans CN;
			font-weight: 500;
			font-size: 32rpx;
			color: #333333;
		}

		.plItm {
			margin: 30rpx 0;

			.pl_head {
				display: flex;
				align-items: center;

				.pl_avatr {
					width: 70rpx;
					height: 70rpx;
					border-radius: 55rpx;
				}

				.pl_box {
					display: flex;
					align-items: center;
					justify-content: space-between;
					flex: 1;

					.pl_boxleft {
						margin-left: 20rpx;

						.pl_boxleft_name {
							font-family: Microsoft YaHei;
							font-weight: 400;
							font-size: 26rpx;
							color: #000000;
						}

						.pl_Author {
							font-size: 24rpx;
							color: #A8A8A8;
						}
					}

					.pl_boxright {
						display: flex;

						image {
							width: 32rpx;
							height: 30rpx;
						}

						text {
							font-family: Microsoft YaHei;
							font-weight: 400;
							font-size: 26rpx;
							margin-left: 10rpx;
						}
					}
				}
			}

			.pl_cot {
				font-family: Microsoft YaHei;
				font-weight: 400;
				font-size: 28rpx;
				color: #000000;
				margin-left: 90rpx;
				margin-top: 20rpx;

				.zhiding {
					background: #F5F5F5;
					color: #999999;
					font-size: 18rpx;
					padding: 2rpx 10rpx;
					border-radius: 8rpx;
					margin-left: 10rpx;
				}
			}

			.pl_Img {
				margin-left: 90rpx;
				margin-top: 10rpx;

				image {
					width: 150rpx;
					height: 200rpx;
					border-radius: 12rpx;
				}
			}




		}

	}

	// 底部模块
	.taber {
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
		background: #FFFFFF;
		padding: 15rpx 20rpx;
		display: flex;

		.taber_left {
			background: #F5F6F8;
			border-radius: 16rpx;
			width: 380rpx;
			padding: 0 30rpx;


			.taber_leftInput {
				display: flex;
				align-items: center;
				justify-content: space-between;

				image {
					width: 40rpx;
					height: 40rpx;
				}
			}
		}

		.taber_right {
			flex: 1;
			margin-left: 30rpx;
			display: flex;
			align-items: center;
			justify-content: space-between;

			.taber_rightbox {
				text-align: center;
				font-family: Source Han Sans CN;
				font-weight: 400;
				font-size: 22rpx;
				color: #1D1D1D;

				image {
					width: 38rpx;
					height: 38rpx;
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

	.qrcodeBox {
		position: fixed;
		left: -1000px;
	}

	.myShareImg {
		position: fixed;
		left: -1000px;
	}
</style>