<template>
	<view>
		<view class="info">
			<view class="zhuye" @click="goguanzhu">
				<image src="../../static/image/my/zhuyes.png" style="width: 128rpx;height: 56rpx;"></image>
			</view>

			<view class="flex align-center justify-center" style="position: relative;">
				<image :src="avatar?avatar:'../../static/logos.png'"
					style="width: 239rpx;height:239rpx;border-radius: 50%;"></image>
				<!-- #ifdef MP-WEIXIN -->
				<view class="phtone">
					<button open-type="chooseAvatar" @chooseavatar="onChooseAvatar">
						<image src="../../static/image/my/avater.png" style="width: 76rpx;height: 76rpx;"></image>
					</button>
				</view>
				<!-- #endif -->
				<!-- #ifndef MP-WEIXIN -->
				<view class="phtone" @click="uploadImg()">
					<image src="../../static/image/my/avater.png" style="width: 76rpx;height: 76rpx;"></image>
				</view>
				<!-- #endif -->
			</view>
			<view style="padding:25rpx 0">
				<view class="name">昵称</view>
				<view class="from-item">
					<up-input v-model="userName" type="nickname" border="none" placeholder="请填写昵称"
						placeholder-class="input-placeholder"></up-input>
				</view>
			</view>
			<view style="padding:25rpx 0">
				<view class="name">年龄</view>
				<view class="from-item">
					<up-input v-model="age" type="number" border="none" placeholder="请填写年龄"
						placeholder-class="input-placeholder"></up-input>
				</view>
			</view>
			<view>
				<view class="name">性别</view>
				<view class="flex">
					<image @click="subSex(1)"
						:src="sexs=='男'?'../../static/image/my/manS.png':'../../static/image/my/man.png'"
						style="width: 220rpx;height: 100rpx" mode=""></image>
					<image @click="subSex(2)"
						:src="sexs=='女'?'../../static/image/my/womanS.png':'../../static/image/my/woman.png'"
						style="width: 220rpx;height: 100rpx;margin-left: 20rpx" mode=""></image>
				</view>
			</view>
			<view v-if="phone" style=";padding:25rpx 0">
				<view class="name">联系电话</view>
				<view class="from-item ">
					<up-input placeholder="请填写联系电话" border="none" :readonly="true" v-model="phone"></up-input>
				</view>
			</view>
			<view v-else style=";padding:25rpx 0">
				<view class="name">联系电话</view>
				<view class="flex align-center from-item">
					<!-- #ifdef MP-WEIXIN -->
					<view class="flex align-center justify-center"
						style="background: #000000;width: 100%;height: 96rpx;border-radius: 18rpx;margin-top: 20rpx;">
						<button class="bottom" open-type="getPhoneNumber" @getphonenumber="getPhoneNumber">
							授权手机号
						</button>
					</view>
					<!-- #endif -->
					<!-- #ifndef MP-WEIXIN -->
					<up-input placeholder="请填写联系电话" border="none" :readonly="true" v-model="phone"></up-input>
					<!-- #endif -->
				</view>
			</view>
			<view style="padding:25rpx 0">
				<view class="name">标签</view>
				<view class="flex align-center flex-wrap margin-top-sm">

					<view class="labebtn" :class="isTips(item)?'tipsActive':''" v-for="(item,index) in labels"
						:key="index" @click="addlabel(item)" style="margin-bottom:20rpx;">
						{{item}}
					</view>
				</view>
			</view>
			<view class="" style="border-bottom:1rpx solid #F2F2F2;padding:25rpx 0 0 0">
				<view class="name">个人简介</view>
				<view class="margin-top-sm" style="border: 1px solid #E6E6E6;border-radius: 18rpx;padding:2rpx ;">
					<up-textarea v-model="content" border="none" placeholder="请填写个人简介" :height="100" :autoHeight="false"
						:maxlength="-1"></up-textarea>
				</view>
			</view>

			<view class="" style="padding:25rpx 0">
				<view class="name margin-bottom-sm">个人相册展示</view>
				<view class="flex align-center justify-between flex-wrap">
					<view v-for="(item,index) in photos" :key="index" class="margin-top-xs "
						style="position: relative;">
						<image :src="item" mode="aspectFill"
							style="width: 190rpx;height: 190rpx;border-radius: 8rpx;margin-right:11rpx">
						</image>
						<view style="position: absolute;top: -10rpx;right: -10rpx;z-index: 999;"
							@click="detePhone(index)">
							<u-icon name="close-circle" color="#000000" size="24"></u-icon>
						</view>
					</view>
					<view class="flex align-center justify-center"
						style="width: 190rpx;height: 190rpx;border-radius: 8rpx;border: 1px solid #EEEEEE;"
						@click="addImage()" v-if="photos.length<=5">
						<u-icon name="camera" color="#000000" size="38"></u-icon>
					</view>
					<view class="" style="width: 190rpx;height: 0;"></view>
				</view>
			</view>
		</view>

		<view class="btn" @click="updeteUsInfo()">保存</view>
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
	const userId = ref('');
	// 用户头像
	const avatar = ref('');
	// 用户昵称
	const userName = ref('');
	// 用户联系方式
	const phone = ref('');
	// 用户性别
	const sexs = ref('');
	const sex = ref('');
	const isSex = ref(false)
	// 用户年龄
	const age = ref('');
	// 用户标签
	const labels = reactive([]);
	// 用户简介
	const content = ref('');
	// 个人相册
	const photos = reactive([]);
	const photo = reactive([]);
	const isShowTips = ref(false);
	// 跳转主页
	const goguanzhu = () => {
		uni.navigateTo({
			url: '/my/gird/zhuye?userId=' + uni.getStorageSync('userId')
		})
	};
	const subSex = (index) => {
		if (!isSex.value) {
			uni.showModal({
				title: '提示',
				content: '性别一旦设置 后续无法修改',
				complete(ret) {
					if (ret.confirm) {
						if (index == 1) {
							sexs.value = '男'
							sex.value = 1
						} else {
							sexs.value = '女'
							sex.value = 2
						}
					}
				}
			})
		} else {
			uni.showToast({
				title: '性别已设置，无法修改',
				icon: 'none',
				duration: 3000
			})
		}
	};
	// 标签选择
	const addlabel = (e) => {
		if (content.value) {
			let a = content.value.indexOf(e)
			if (a != -1) {
				uni.showToast({
					title: '标签已添加',
					icon: 'none'
				})
			} else {
				content.value = content.value + ',' + e
			}
		} else {
			content.value = e
		}
	};
	//判断标签是否在简介里面
	const isTips = (label) => {
		if (!content.value || content.value == void 0) {
			return
		}
		return content.value.includes(label);
	};
	// 获取用户信息
	const getUserInfo = () => {
		proxy.$http.get("/app/base/user/info").then(res => {
			if (res.code == 0) {
				avatar.value = res.data.avatar
				userName.value = res.data.userName
				phone.value = res.data.phone
				age.value = res.data.age
				sex.value = res.data.sex
				content.value = res.data.remark
				if (res.data.sex == 1) {
					sexs.value = '男'
				} else if (res.data.sex == 2) {
					sexs.value = '女'
				} else {
					sexs.value = ''
				}
				if (sex.value) {
					isSex.value = true
				} else {
					isSex.value = false
				}
				if (res.data.pic) {
					photo.splice(0, photo.length, ...res.data.pic.split(','))
					photos.splice(0, photos.length, ...res.data.pic.split(','))
				}
			}
		});
	};
	const updeteUsInfo = () => {
		if (!avatar.value) {
			uni.showToast({
				title: '请设置你的头像',
				icon: "none"
			})
			return;
		}
		if (userName.value == '微信用户') {
			uni.showToast({
				title: '请设置你的昵称',
				icon: "none"
			})
			return;
		}
		if (!age.value) {
			uni.showToast({
				title: '请输入年龄',
				icon: "none"
			})
			return;
		}
		if (Number(age.value) < 18) {
			uni.showToast({
				title: '很抱歉，您的年龄未满18岁，无法使用当前软件！',
				icon: "none"
			})
			return;
		}
		if (!sex.value) {
			uni.showToast({
				title: '请设置你的性别',
				icon: "none"
			})
			return;
		}
		if (!phone.value) {
			uni.showToast({
				title: '请设置你的联系电话',
				icon: "none"
			})
			return;
		}
		photo.value = photos
		photo.value = photo.value.toString()
		proxy.$http.postJson("/app/base/user/update", {
			userName: userName.value,
			avatar: avatar.value,
			phone: phone.value,
			sex: sex.value,
			pic: photo.value,
			age: age.value,
			remark: content.value,
			userId: uni.getStorageSync('userId')
		}).then(res => {
			if (res.code === 0) {
				uni.showToast({
					title: '保存成功',
					icon: "none"
				})
				getUserInfo()
				setTimeout(function() {
					uni.navigateBack()
				}, 1000)
			} else {
				uni.showToast({
					title: res.msg,
					icon: "none"
				})
			}
		})
	};
	// 个人相册删除
	const detePhone = (index) => {
		photos.splice(index, 1)
	};
	// 个人相册上传
	const addImage = () => {
		let count = 6 - photos.length
		uni.chooseImage({
			count: count,
			sourceType: ['album', 'camera'],
			success: res => {
				for (let i = 0; i < res.tempFilePaths.length; i++) {
					let filePath = res.tempFilePaths[i]
					
					console.log(filePath.indexOf("file"));
					uni.showLoading({
						title: '加载中'
					});
					uni.uploadFile({ // 上传接口
						// url: config.APIHOST + '/file/upload',
						// url: config.APIHOST + '/file/uploads', //真实的接口地址
						url: config.APIHOST + '/file/upload',
						filePath: filePath,
						name: 'file',
						success: (uploadFileRes) => {
							uni.hideLoading();
							let url = JSON.parse(uploadFileRes.data)
							if (url.code == 0) {
								photos.push(url.data.path)
								
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
	// 上传头像 H5
	const uploadImg = () => {
		let token = uni.getStorageSync('token')
		if (!token) {
			uni.navigateTo({
				url: '/pages/public/login'
			})
			return;
		}
		let that = this;
		// #ifdef APP
		// 在弹出选择弹框之前先判断一下是否获取了相册权限
		const sysInfo = uni.getSystemInfoSync()
		if (sysInfo.osName == 'ios') { //ios

		} else { //安卓
			var Manifest = plus.android.importClass("android.Manifest");
			var MainActivity = plus.android.runtimeMainActivity();
			let flag = MainActivity.checkSelfPermission(Manifest.permission.CAMERA)
			console.log(flag); // 查看是否拥有相机权限 -1/0
			if (flag == -1) { //没有授权
				isShowTips.value = true
				plus.android.requestPermissions(['android.permission.CAMERA'], (success) => {
					isShowTips.value = false
				}, (err) => {
					isShowTips.value = false
				})
				return
			} else {
				isShowTips.value = false
			}

		}
		// #endif
		var url = null;
		uni.showActionSheet({
			// itemList按钮的文字接受的是数组
			itemList: ["查看头像", "从相册选择图片"],
			success(e) {
				var index = e.tapIndex
				if (index === 0) {
					// 用户点击了预览当前图片
					// 可以自己实现当前头像链接的读取
					let url = that.avatar;
					let arr = []
					arr.push(url)
					uni.previewImage({
						// 预览功能图片也必须是数组的
						urls: arr
					})
				} else if (index === 1) {
					uni.chooseImage({
						count: 1, //默认9
						sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
						sourceType: ['album'], //从相册选择
						success: function(res) {
							uni.showLoading({
								title: '上传中...'
							});
							let token = uni.getStorageSync('token');
							let userId = uni.getStorageSync('userId');
							uni.uploadFile({
								// url: config.APIHOST + '/file/upload', //真实的接口地址
								url: config.APIHOST + '/file/upload',
								filePath: res.tempFilePaths[0],
								header: {
									token: token
								},
								name: 'file',
								success: uploadFileRes => {
									uni.hideLoading();
									let url = JSON.parse(uploadFileRes.data)
									
									if (url.code == 0) {
										avatar.value = url.data.path
										
									} else {
										uni.showToast({
											title: url.msg,
											icon: 'none'
										})
									}

								}
							});
						}
					});
				}
			}
		})
	};
	// 小程序获取微信头像
	const onChooseAvatar = () => {
		let that = this;
		let token = uni.getStorageSync('token');
		uni.showLoading({
			title: '上传中...'
		});
		uni.uploadFile({
			url: config.APIHOST + '/file/upload',
			// url: config.APIHOST + '/file/uploads', //真实的接口地址
			filePath: e.detail.avatarUrl,
			header: {
				token: token
			},
			name: 'file',
			success: uploadFileRes => {
				uni.hideLoading();
				let url = JSON.parse(uploadFileRes.data)
				if (url.code == 0) {
					avatar.value = url.data.path
				} else {
					uni.showToast({
						title: url.msg,
						icon: 'none',
						duration: 2000
					})
				}
			}
		});

	};
	//小程序微信登录后获取手机号
	const getPhoneNumber = (e) => {
		if (e.detail.errMsg == 'getPhoneNumber:fail user deny') {
			console.log('用户拒绝提供手机号');
		} else {
			console.log('用户同意提供手机号');
			setPhoneByInsert(e.detail.encryptedData, e.detail.iv);
		}
	};
	//小程序微信登录后获取手机号
	const setPhoneByInsert = (decryptData, iv) => {
		let sessionkey = uni.getStorageSync('sessionkey')
		let data = {
			decryptData: decryptData,
			key: sessionkey,
			iv: iv
		};
		proxy.$http.postJson('/app/Login/selectPhone', data).then(res => {
			if (res.code == 0) {
				phone.value = res.data.phoneNumber;
			} else {
				uni.showToast({
					title: res.msg,
					icon: 'none',
					duration: 2000
				});
			}
		})
	};
	onLoad(option => {
		userId.value = uni.getStorageSync('userId')
		// 系统预设标签	854
		proxy.$http.postJson('/app/config/info', {
			key: 'app_tags'
		}).then(res => {
			if (res.code == 0) {
				if (res.data && res.data.value) {
					let arr = res.data.value.split(',')
					labels.splice(0, labels.length, ...arr)
				}
			}
		});
		getUserInfo()
	});
	onShow(() => {

	});
	onPullDownRefresh(() => {

	});
	onReachBottom(() => {

	});
</script>

<style lang="scss">
	page {
		background: #F2F2F7;
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
		line-height: 96rpx;
		overflow: hidden;
		color: #FFFFFF;
		/* background-color: #fff; */
		background-color: rgba(255, 255, 255, 0) !important;
		font-size: 28rpx;
		width: 100%;
		height: 100%;
		color: #21FD91;
	}

	.input-placeholder {
		font-family: Microsoft YaHei;
		font-weight: 400;
		font-size: 26rpx;
		color: #868484 !important;
	}

	.info {
		border-radius: 24rpx;
		padding: 30rpx;
		margin: 30rpx;
		background: #FFFFFF;
		position: relative;

		.zhuye {
			position: absolute;
			right: 40rpx;
			top: 30rpx;
			z-index: 99;
		}

		.phtone {
			position: absolute;
			right: 30%;
			/* #ifdef H5 */
			bottom: -10rpx;
			transform: translate(-20%, 0);
			/* #endif */
			/* #ifndef H5 */
			bottom: -40rpx;
			transform: translate(0, 0);
			/* #endif */

		}

		.name {
			width: 230rpx;
			font-size: 32rpx;
			font-family: PingFang SC;
			font-weight: bold;
			color: #000000;
		}

		.from-item {
			border: 1px solid #E6E6E6;
			border-radius: 18rpx;
			padding: 20rpx 30rpx;
			margin-top: 20rpx;
		}

		.labebtn {
			background-color: #F2F2F2;
			color: #AAAAAA;
			margin-right: 15rpx;
			border-radius: 55rpx;
			padding: 8rpx 30rpx;
			font-size: 26rpx;
		}

		.tipsActive {
			color: #00BC4F;
			background-color: #D6FFE7 !important;
		}
	}

	.btn {
		width: 686rpx;
		height: 100rpx;
		background: linear-gradient(121deg, #53D8A4, #ABEC4F);
		border-radius: 24rpx;
		font-size: 32rpx;
		font-family: PingFang SC;
		font-weight: bold;
		color: #FFFFFF;
		margin: 0 auto;
		text-align: center;
		line-height: 100rpx;
	}
</style>