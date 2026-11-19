<template>
	<view style="padding-bottom: 120rpx;">
		<view class="contont">
			<!-- 注意，如果需要兼容微信小程序，最好通过setRules方法设置rules规则 -->
			<up-form :labelStyle="labelStyle" labelWidth="auto" labelPosition="left" :model="form" ref="uForm">
				<view class="myForm">
					<view class="myForm-top">
						<view class="" v-if="auditRemark" style="width: 100%;text-align: center;color: red;">
							拒绝理由：{{auditRemark}}
						</view>

						<up-form-item label="群类型" :borderBottom="true" class="myForm-item" @click="openClass()">
							<up-input v-model="form.typeName" border="none" disabled disabledColor="#FFFFFF"
								inputAlign="right" placeholder="请选择群类型" class="inputSize"
								placeholder-class="placeholderText"></up-input>
							<up-icon name="arrow-right" color="#999999"></up-icon>
						</up-form-item>

						<up-form-item label="群名称" class="myForm-item" :borderBottom="true">
							<up-input v-model="form.name" border="none" class="inputSize" inputAlign="right"
								placeholder="请输入群名称" placeholder-class="placeholderText"></up-input>
						</up-form-item>

						<up-form-item label="群聊简介" class="myForm-item" :borderBottom="true" labelPosition="top">
							<textarea placeholder="请输入群聊简介" v-model="form.description"
								style="padding: 0;margin-top: 10rpx;height:220rpx"
								placeholder-class="placeholderText" />
						</up-form-item>

						<up-form-item labelPosition="top" class="myForm-item" label="群详情" :borderBottom="true">
							<view class="myForm-item-imgs flex justify-between flex-wrap">
								<view @click="removeImg(index,1)" class="myForm-item-imgs-item"
									v-for="(item,index) in imgs" :key="index">
									<image :src="item" mode="aspectFill"></image>
									<view class="myForm-item-imgs-item-close">
										<up-icon name="close-circle" color="red" size="20"></up-icon>
									</view>
								</view>
								<view @click="uploadImg(1)" v-if="imgs.length<=8"
									class="myForm-item-imgs-item flex justify-center flex-wrap">
									<up-icon name="camera" color="#333333" size="38"></up-icon>
									<view class="myForm-item-imgs-item-txt">
										添加图片
									</view>
								</view>
								<view class="myForm-item-imgs-item" style="height: 0;"></view>
								<view class="myForm-item-imgs-item" style="height: 0;"></view>
							</view>
						</up-form-item>

						<up-form-item labelPosition="top" class="myForm-item" label="群二维码">
							<view class="myForm-item-imgs flex justify-between flex-wrap">
								<view @click="removeImg(index,2)" class="myForm-item-imgs-item"
									v-for="(item,index) in groupQrcode" :key="index">
									<image :src="item" mode="aspectFill"></image>
									<view class="myForm-item-imgs-item-close">
										<up-icon name="close-circle" color="red" size="20"></up-icon>
									</view>
								</view>
								<view @click="uploadImg(2)" v-if="groupQrcode.length<=8"
									class="myForm-item-imgs-item flex justify-center flex-wrap">
									<up-icon name="camera" color="#333333" size="38"></up-icon>
									<view class="myForm-item-imgs-item-txt">
										添加图片
									</view>
								</view>
								<view class="myForm-item-imgs-item" style="height: 0;"></view>
								<view class="myForm-item-imgs-item" style="height: 0;"></view>
							</view>
						</up-form-item>

					</view>

					<view class="myForm-btom">
						<view @click="showTimes = true" class="myForm-btom-r " style="margin-top: 0;">
							<view class="myForm-btom-rt">
								<image src="../static/groupChat/time.png" style="margin-top: 4rpx;" mode=""></image>
								开始时间
							</view>
							<view class="myForm-btom-rs ">
								<up-input class="inputSize" disabled disabledColor="#ffffff" inputAlign="right"
									v-model="form.qrStartTime" placeholder="请选择群开始时间" border="none"
									placeholder-class="placeholderText"></up-input>
								<up-icon color="#999999" name="arrow-right"></up-icon>
							</view>
						</view>
						<view @click="showTime = true" class="myForm-btom-r ">
							<view class="myForm-btom-rt ">
								<image src="../static/groupChat/time.png" style="margin-top: 4rpx;" mode=""></image>
								结束时间
							</view>
							<view class="myForm-btom-rs">
								<up-input class="inputSize" disabled disabledColor="#ffffff" inputAlign="right"
									v-model="form.expiredTime" placeholder="请选择群结束时间" border="none"
									placeholder-class="placeholderText"></up-input>
								<up-icon color="#999999" name="arrow-right"></up-icon>
							</view>
						</view>

						<view @click="selectAddress" class="myForm-btom-r ">
							<view class="myForm-btom-rt ">
								<image src="../static/groupChat/address.png" style="margin-top: 4rpx;" mode=""></image>
								群地址
							</view>
							<view class="myForm-btom-rs">
								<up-input class="inputSize" disabled disabledColor="#ffffff" inputAlign="right"
									v-model="form.address" placeholder="请选择群地址" border="none"
									placeholder-class="placeholderText"></up-input>
								<up-icon color="#999999" name="arrow-right"></up-icon>
							</view>
						</view>

						<view v-if="minPeople || maxPeople" class="myForm-btom-r  flex-wrap" style="padding-bottom: 0;">
							<view class="myForm-btom-rt">
								<image src="../static/groupChat/people.png" style="margin-top: 4rpx;" mode=""></image>
								群人数
							</view>
							<view class="myForm-btom-rs ">
								<up-input class="inputSize" type="number" disabledColor="#ffffff" inputAlign="right"
									v-model="form.peopleCount" placeholder="请输入群人数" border="none"
									placeholder-class="placeholderText"></up-input>
							</view>
							<view v-if="minPeople || maxPeople" class="myForm-btom-tit">
								<block v-if="minPeople">最小群人数{{minPeople}}，</block>
								<block v-if="maxPeople && maxPeople>0"> 最大群人数{{maxPeople}}</block>
							</view>
						</view>
						<view v-else class="myForm-btom-r ">
							<view class="myForm-btom-rt ">
								<image src="../static/groupChat/people.png" style="margin-top: 4rpx;" mode=""></image>
								群人数
							</view>
							<view class="myForm-btom-rs">
								<up-input class="inputSize" type="number" disabledColor="#ffffff" inputAlign="right"
									v-model="form.peopleCount" placeholder="请输入群人数" border="none"
									placeholder-class="placeholderText"></up-input>
							</view>
						</view>

						<view v-if="minMoney || maxMoney" class="myForm-btom-r flex-wrap"
							style="padding-bottom: 0;border-bottom: none;">
							<view class="myForm-btom-rt">
								<image src="../static/groupChat/price.png" style="margin-top: 4rpx;" mode=""></image>
								进群价格
							</view>
							<view class="myForm-btom-rs ">
								<up-input class="inputSize" type="digit" disabledColor="#ffffff" inputAlign="right"
									v-model="form.charge" placeholder="请输入群价格" border="none"
									placeholder-class="placeholderText"></up-input>
							</view>
							<view v-if="minPeople || maxPeople" class="myForm-btom-tit">
								<block v-if="minMoney">最小群价格{{minMoney}}，</block>
								<block v-if="maxMoney && maxMoney>0">最大群价格{{maxMoney}}</block>
							</view>
						</view>
						<view v-else class="myForm-btom-r " style="border-bottom: none;">
							<view class="myForm-btom-rt ">
								<image src="../static/groupChat/price.png" style="margin-top: 4rpx;" mode=""></image>
								进群价格
							</view>
							<view class="myForm-btom-rs ">
								<up-input class="inputSize" type="digit" disabledColor="#ffffff" inputAlign="right"
									v-model="form.charge" placeholder="请输入群价格" border="none"
									placeholder-class="placeholderText"></up-input>
							</view>
						</view>

					</view>
				</view>
			</up-form>

		</view>


		<view class="save ">
			<view @click="sendCrdw" class="save-box ">{{id?'修改':'发布'}}</view>
		</view>

		<!--群类型  -->
		<up-picker :show="showClass" :columns="classiftyList" title="群类型" @confirm="confirm" @cancel="showClass = false"
			keyName="name"></up-picker>


		<!--选择时间弹窗 结束时间 -->
		<up-datetime-picker @cancel="close" @close="close" @confirm="confirmTime" :show="showTime"
			v-model="expiredTime" mode="datetime"></up-datetime-picker>
		<!--选择时间弹窗 开始时间 -->
		<up-datetime-picker @cancel="close" @close="close" @confirm="confirmTimes" :show="showTimes"
			v-model="qrStartTime" mode="datetime"></up-datetime-picker>

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
	import {
		timeFormat
	} from '@/uni_modules/uview-plus';
	import config from '@/common/config.js';
	const {
		proxy
	} = getCurrentInstance();

	const auditRemark = ref(''); //拒绝理由
	const form = ref({
		typeId: '', //群id
		typeName: '', //群类型
		name: '', //群名称
		description: '', //群简介
		qrStartTime: '', //开始时间
		expiredTime: '', //结束时间
		address: '', //群地址
		province: '',
		city: '',
		region: '',
		peopleCount: '', //群人数
		charge: '', //群价格
	});
	const id = ref('');
	const userId = ref('');
	const labelStyle = ref({
		color: '#333333',
		fontWeight: 'bold',
		fontSize: '28rpx'
	});
	const imgs = reactive([]); //群详情图
	const groupQrcode = reactive([]); //群二维码

	const classiftyList = reactive([]); //群类型列表
	const showClass = ref(false); //群类型弹框
	const maxPeople = ref(''); //最大群人数
	const minPeople = ref(''); //最小群人数
	const maxMoney = ref(''); //最大价格
	const minMoney = ref(''); //最小价格
	const showTime = ref(false); //结束时间
	const expiredTime = ref(Date.now());
	const showTimes = ref(false); //开始时间
	const qrStartTime = ref(Date.now());


	onLoad(option => {
		userId.value = uni.getStorageSync('userId');
		if (option.id) {
			id.value = option.id
			getDetail()
		}
		getClassiftyList()
	});
	onShow(() => {

	});
	//选择时间
	const confirmTime = (e) => {
		let timeer = timeFormat(e.value, 'yyyy-mm-dd hh:MM:ss');
		let a = new Date().getTime(); //今日时间
		let satet = new Date(form.value.qrStartTime).getTime(); //开始时间
		// console.log(a < e.value, a, e.value, '==========')
		if (a < e.value) {
			if (satet) {
				if (satet < e.value) {
					form.value.expiredTime = timeer
					close()
					// console.log(timeer, '111111111')
				} else {
					uni.showToast({
						title: '群聊有效期不能小于开始时间',
						icon: 'none'
					})
				}
			} else {
				form.value.expiredTime = timeer
				close()
			}
		} else {
			uni.showToast({
				title: '群聊有效期不能小于当前时间',
				icon: 'none'
			})
		}
	};
	//选择时间
	const confirmTimes = (e) => {
		let timeer = timeFormat(e.value, 'yyyy-mm-dd hh:MM:ss');
		let a = new Date().getTime(); //今日时间
		// console.log(a < e.value, a, e.value, '==========')
		if (a < e.value) {
			form.value.qrStartTime = timeer
			close()
		} else {
			uni.showToast({
				title: '群聊有效期不能小于当前时间',
				icon: 'none'
			})
		}

	};
	// 关闭弹框
	const close = () => {
		showClass.value = false
		showTime.value = false
		showTimes.value = false
	};

	//打开分类弹窗
	const openClass = () => {
		if (classiftyList[0].length > 0) {
			showClass.value = true
		} else {
			uni.showToast({
				title: '暂无类型',
				icon: 'none'
			})
		}
	};
	//获取群详情
	const getDetail = () => {
		let data = {
			id: id.value
		}
		proxy.$http.postJson('/app/groupInfo/info', data).then(res => {
			if (res.code == 0) {
				if (res.data.groupAuditStatus == 2) {
					auditRemark.value = res.data.auditRemark
				}
				if (res.data.remark) {
					imgs.push(res.data.remark.split(','))
				}
				if (res.data.qrCode) {
					groupQrcode.push(res.data.qrCode.split(','))
				}
				getClassifyMuban(res.data.typeId)
				form.value = {
					typeId: res.data.typeId, //群类型id
					typeName: res.data.typeName, //群类型
					name: res.data.name, //群名称
					description: res.data.description, //群简介
					groupQrcode: res.data.groupQrcode, //群二维码
					expiredTime: res.data.expiredTime, //群有效期  结束时间
					qrStartTime: res.data.qrStartTime, //群开始时间
					address: res.data.address, //群地址
					province: res.data.province, //省
					city: res.data.city, //市
					region: res.data.region, //区
					peopleCount: res.data.peopleCount, //群人数
					charge: res.data.charge, //群金额
					applyCondition: res.data.applyCondition, //0:免费 1:付费
				}
				// res.data.groupImgList.map(item => {
				// 	imgs.push(item.groupImg)
				// })
				
			}
		})
	};

	//选择地址
	const selectAddress = () => {
		uni.chooseLocation({
			success(res) {
				console.log(res)
				getCity(res.latitude, res.longitude, res.name)
			},
			fail() {
				uni.showToast({
					title: '拉起地图失败，请重试',
					icon: 'none'
				})
			}
		})
	};
	//根据经纬度获取省市区
	const getCity = (latitude, longitude, name) => {
		let data = {
			lat: latitude,
			lng: longitude
		}
		proxy.$http.get("/app/map/reverseGeocode", data).then(res => {
			if (res.code == 0) {
				form.value.address = name
				form.value.province = res.data.province
				form.value.city = res.data.city
				form.value.region = res.data.county
				
				
			}
		});
	};
	//获取分类
	const getClassiftyList = () => {
		proxy.$http.postJson('/app/groupType/page', {
			page: 1,
			limit: 100,
			status:0,//状态（0正常 1禁用）
		}).then(res => {
			if (res.code == 0) {
				classiftyList.splice(0, 0, res.data.records)

			}
		})
	};
	// 选择群类型
	const confirm = (item) => {
		console.log(item, '====')
		form.value.typeId = item.value[0].id
		form.value.typeName = item.value[0].name
		maxPeople.value = ''
		minPeople.value = ''
		maxMoney.value = ''
		minMoney.value = ''
		getClassifyMuban(item.value[0].id);
		close();
	};
	//根据分类id查询模板
	const getClassifyMuban = (id) => {
		proxy.$http.postJson('/app/groupType/info', {
			id: id
		}).then(res => {
			if (res.code == 0) {
				maxPeople.value = Number(res.data.maxPeopleNum)
				minPeople.value = Number(res.data.minPeopleNum)
				maxMoney.value = Number(res.data.maxAmount)
				minMoney.value = Number(res.data.minAmount)
			}
		})
	};
	//发布
	const sendCrdw = () => {
		if (!form.value.typeId) {
			uni.showToast({
				title: '请选择群类型',
				icon: 'none'
			})
			return
		}
		if (!form.value.name) {
			uni.showToast({
				title: '请输入群名称',
				icon: 'none'
			})
			return
		}
		if (!form.value.description) {
			uni.showToast({
				title: '请输入群聊简介',
				icon: 'none'
			})
			return
		}
		// if (imgs.length == 0) {
		// 	uni.showToast({
		// 		title: '请上传群详情图',
		// 		icon: 'none'
		// 	})
		// 	return
		// }
		// if (groupQrcode.length == 0) {
		// 	uni.showToast({
		// 		title: '请上传群二维码',
		// 		icon: 'none'
		// 	})
		// 	return
		// }
		if (!form.value.qrStartTime) {
			uni.showToast({
				title: '请选择开始时间',
				icon: 'none'
			})
			return
		}
		if (!form.value.expiredTime) {
			uni.showToast({
				title: '请选择结束时间',
				icon: 'none'
			})
			return
		}
		if (!form.value.address) {
			uni.showToast({
				title: '请选择群地址',
				icon: 'none'
			})
			return
		}

		if (!form.value.peopleCount) {
			uni.showToast({
				title: '请输入群人数',
				icon: 'none'
			})
			return
		}

		let inputNumber = /[^\d]/g;
		if (inputNumber.test(form.value.peopleCount)) {
			uni.showToast({
				title: '请输入正确群人数',
				icon: 'none'
			})
			return
		}
		//有最小群人数限制
		if (minPeople.value > 0) {
			if (form.value.peopleCount < minPeople.value) {
				uni.showToast({
					title: '最小群人数为' + minPeople.value,
					icon: 'none'
				})
				return
			} else {
				//是否有最大群人数限制
				if (maxPeople.value > 0) {
					if (form.value.peopleCount > maxPeople.value) {
						uni.showToast({
							title: '最大群人数为' + maxPeople.value,
							icon: 'none'
						})
						return
					}
				}
			}
		} else { //没有最小人数限制
			//是否有最大群人数限制
			if (maxPeople.value > 0) {

				if (form.value.peopleCount > maxPeople.value) {
					uni.showToast({
						title: '最大群人数为' + maxPeople.value,
						icon: 'none'
					})
					return
				} else if (form.value.peopleCount < 0 || !form.value.peopleCount) {
					uni.showToast({
						title: '请输入群人数',
						icon: 'none'
					})
					return
				}
			}
		}
		//有最小群价格限制
		if (minMoney.value > 0) {
			if (form.value.charge < minMoney.value) {
				uni.showToast({
					title: '最小群价格为' + minMoney.value,
					icon: 'none'
				})
				return
			} else {
				//是否有最大群价格限制
				if (maxMoney.value > 0) {
					if (form.value.charge > maxMoney.value) {
						uni.showToast({
							title: '最大群价格为' + maxMoney.value,
							icon: 'none'
						})
						return
					}
				}
			}
		} else { //没有最小价格限制
			//是否有最大群价格限制
			if (maxMoney.value > 0) {
				if (form.value.charge > maxMoney.value) {
					uni.showToast({
						title: '最大群价格为' + maxMoney.value,
						icon: 'none'
					})
					return
				} else if (form.value.charge < 0 || !form.value.charge) {
					uni.showToast({
						title: '请输入群价格',
						icon: 'none'
					})
					return
				}
			}
		}

		let data = JSON.parse(JSON.stringify(form.value))
		if (form.value.charge && Number(form.value.charge) > 0) {
			data.applyCondition = 1
		} else {
			data.applyCondition = 0
		}

		data.userId = uni.getStorageSync('userId')
		data.remark = imgs.join(',')
		data.qrCode = groupQrcode.join(',')
		
		if (id.value) {
			data.id = id.value
			uni.showLoading({
				title: '修改中',
			})
			proxy.$http.postJson('/app/groupInfo/update', data).then(res => {
				if (res.code == 0) {
					uni.hideLoading()
					uni.showToast({
						title: '修改成功'
					})
					setTimeout(() => {
						uni.navigateBack()
					}, 1000)
				} else {
					uni.hideLoading()
					uni.showToast({
						title: res.msg,
						icon: 'none'
					})
				}
			})
		} else {
			uni.showLoading({
				title: '提交中',
			})
			proxy.$http.postJson('/app/groupInfo/save', data).then(res => {
				if (res.code == 0) {
					uni.hideLoading()
					uni.showToast({
						title: '提交成功'
					})
					setTimeout(() => {
						uni.navigateBack()
					}, 1000)
				} else {
					uni.hideLoading()
					uni.showToast({
						title: res.msg,
						icon: 'none'
					})
				}
			})
		}

	};
	/**
	 * @param {Object} index 图片下标
	 * 移除图片
	 */
	const removeImg = (index, type) => {
		if (type == 1) {
			imgs.splice(index, 1)
		} else {
			groupQrcode.splice(index, 1)
		}
	};
	/**
	 * @param {Object} type 1:详情图 2:二维码
	 * 上传图片
	 */
	const uploadImg = (type) => {
		let count = ''
		if (type == 1) {
			count = 9 - imgs.length
		} else {
			count = 9 - groupQrcode.length
		}
		uni.chooseImage({
			count: count,
			sourceType: ['album', 'camera'],
			success(res) {
				for (let i = 0; i < res.tempFilePaths.length; i++) {
					uni.showLoading({
						title: '上传中'
					});
					uni.uploadFile({
						// url: config.APIHOST + '/file/uploads',
						url: config.APIHOST + '/file/upload', //真实的接口地址
						filePath: res.tempFilePaths[i],
						header: {
							token: uni.getStorageSync('token')
						},
						name: 'file',
						success: uploadFileRes => {
							uni.hideLoading();
							let url = JSON.parse(uploadFileRes.data)
							if (url.code == 0) {
								if (type == 1) {
									imgs.push(url.data.path);
								} else {
									groupQrcode.push(url.data.path);
								}
							} else {
								uni.showToast({
									title: url.msg,
									icon: 'none'
								})
							}


						}
					});
				}

			},
			fail(err) {
				uni.showToast({
					title: '上传失败',
					icon: 'none'
				})
			}
		})
	};
	onPullDownRefresh(() => {

	});
	onReachBottom(() => {

	});
</script>

<style lang="scss">
	page {
		background: #EAF0F8;
	}

	::v-deep .u-form-item__body__left__content__label {
		font-family: PingFang SC;
		font-weight: bold;
		font-size: 28rpx;
		color: #333333;
	}

	.contont {
		margin-top: 20rpx;

		.myForm {
			width: 100%;
			height: auto;

			.myForm-top {
				margin: 0 30rpx;
				background-color: #ffffff;
				border-radius: 24rpx;
				padding: 30rpx 30rpx 10rpx 30rpx;

				.myForm-item {
					margin-bottom: 20rpx;

					.inputSize {
						font-size: 28rpx;
					}

					.placeholderText {
						font-family: PingFang SC;
						font-weight: 400;
						font-size: 24rpx;
						color: #999999;
					}
				}

				// 群相册
				.myForm-item-imgs {
					width: 100%;

					.myForm-item-imgs-item {
						width: 190rpx;
						height: 190rpx;
						background: #F7F7F7;
						border-radius: 8rpx;
						margin-top: 10rpx;
						align-content: center;
						position: relative;

						image {
							width: 100%;
							height: 100%;
							border-radius: 8rpx;
						}

						.myForm-item-imgs-item-close {
							position: absolute;
							top: -12rpx;
							right: -12rpx;
						}
					}

					.myForm-item-imgs-item-txt {
						width: 100%;
						text-align: center;
						color: #333333;
						font-size: 20rpx;
						font-weight: 500;
					}
				}

			}


			.myForm-btom {
				margin: 20rpx 30rpx;
				padding: 30rpx 30rpx;
				background-color: #ffffff;
				border-radius: 24rpx;

				.myForm-btom-r {
					align-content: start;
					border-bottom: 1px solid rgba(214, 215, 217, 0.6);
					padding-bottom: 30rpx;
					margin-top: 30rpx;
					display: flex;
					align-items: center;
					justify-content: space-between;
				}

				.myForm-btom-rt {
					font-family: PingFang SC;
					font-weight: bold;
					font-size: 28rpx;
					color: #333333;
					display: flex;
					align-items: center;

					image {
						width: 36rpx;
						height: 36rpx;
						margin-right: 10rpx;
					}
				}

				.myForm-btom-rs {
					flex: 1;
					display: flex;
					align-items: center;
				}

				.myForm-btom-tit {
					width: 100%;
					color: red;
					font-size: 22rpx;
					padding-left: 46rpx;
					padding-bottom: 20rpx;
					margin-top: 10rpx;
				}

				.placeholderText {
					font-family: PingFang SC;
					font-weight: 300;
					font-size: 24rpx;
					color: #999999;
				}
			}
		}
	}

	.save {
		width: 100%;
		height: auto;
		margin-top: 34rpx;
		display: flex;
		align-items: center;
		justify-content: center;

		.save-box {
			width: 686rpx;
			height: 98rpx;
			border-radius: 50rpx;
			background: linear-gradient(121deg, #53D8A4, #ABEC4F);
			font-family: PingFang SC;
			font-weight: bold;
			font-size: 32rpx;
			color: #FFFFFF;
			display: flex;
			align-items: center;
			justify-content: center;
		}
	}
</style>