<template>
	<view v-if="shangxianSelect != '否'" style="padding-bottom: 50rpx;">
		<view class="head">
			按照相关规定！需要进行实名认证后才可提现！我们会对您的信息保密，请填写本人真实信息</view>
		<view class="head text-center " style="color: red;" v-if="status===2">{{form.remek}}</view>
		<view class="head text-center " style="color: green;" v-if="status===0">
			审核中
		</view>
		<view class="box">
			<up-form labelPosition="top" :model="form" ref="uForm" :labelStyle='lableStyle'>
				<up-form-item label="真实姓名">
					<up-input v-model="form.name" border="none" placeholder="请输入真实姓名"></up-input>
				</up-form-item>
				<up-form-item label="证件号码" prop="userInfo.sex">
					<up-input v-model="form.idNumber" placeholder="请输入身份证号码" border="none"></up-input>
				</up-form-item>
			</up-form>

		</view>

		<view v-if="!disabled" @click="submit" class="btn">
			提交认证
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
		onReachBottom,
	} from '@dcloudio/uni-app';
	const {
		proxy
	} = getCurrentInstance();

	const shangxianSelect = ref('是');
	const lableStyle = ref({
		fontSize: '32upx',
		width: '300rpx'
	});
	const form = ref({
		name: '',
		idNumber: '',
		remek: ''
	});
	const disabled = ref(false);

	onLoad(option => {
		getUserInfo();
	});
	onShow(() => {

	});
	const getUserInfo= () => {
		proxy.$http.get("/app/userCertification/queryInsert").then(res => {
			if (res.code == 0) {
				if (res.data) {
					form.value = {
						name: res.data.name,
						idNumber: res.data.idNumber,
						status: res.data.status,
					}
					if (res.data.status == 0 || res.data.status == 1) { //已通过
						disabled.value = true
					}
					if (res.data.status == 2) { //已拒绝
						form.value = {
							remek: res.data.remek
						}
					}
				}
			}
		});
	};
	const submit = () => {
		if (!form.value.name) {
			uni.showToast({
				title: '请输入真实姓名',
				icon: 'none',
				duration: 1000
			})
			return;
		}
		if (!form.value.idNumber) {
			uni.showToast({
				title: '请输入身份证号',
				icon: 'none',
				duration: 1000
			})
			return;
		}
		let regX = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
		if (!regX.test(form.value.idNumber)) {
			uni.showToast({
				title: '请输入正确的身份证号',
				icon: 'none',
				duration: 1000
			})
			return;
		}
		proxy.$http.post("/app/userCertification/insert", form.value).then(res => {
			if (res.code == 0) {
				uni.showToast({
					title: '认证提交成功！',
					icon: 'none'
				})
				setTimeout(function() {
					uni.navigateBack()
				}, 1000)
			} else {
				uni.showToast({
					title: res.msg,
					icon: 'none'
				})
			}
		});
	};
	onPullDownRefresh(() => {

	});
	onReachBottom(() => {

	});
</script>

<style lang="scss">
	page {
		background: #f2f2f2;
	}


	.head {
		background: #ffffff;
		padding: 30rpx;
		font-size: 28rpx;
	}

	.box {
		background: #ffffff;
		padding: 30rpx;
		margin: 20rpx 30rpx;
		border-radius: 24rpx;

	}

	.btn {
		margin: 60rpx 30rpx;
		height: 90rpx;
		background: linear-gradient(121deg, #53D8A4, #ABEC4F);
		border-radius: 55rpx;
		font-family: PingFang SC;
		font-weight: 500;
		font-size: 28rpx;
		color: #FFFFFF;
		display: flex;
		align-items: center;
		justify-content: center;
	}
</style>