<template>
	<view class="page" style="background-color: #ffffff;">
		<view class="feedback-title">
			<text>问题和意见</text>
			<text @tap="chooseMsg">快速键入</text>
		</view>
		<view class="feedback-body">
			<textarea placeholder="请详细描述你的问题和意见..." v-model="sendDate.content" class="feedback-textare" />
		</view>
		<view class="feedback-title">联系方式</view>
		<view class="feedback-body">
			<input class="feedback-input" v-model="sendDate.contact" placeholder="方便我们联系你 " />
		</view>

		<button style="" class="feedback-submit" @tap="send">提交</button>

		<!-- 快速选择 -->
		<up-action-sheet :actions="list" :show="show" cancelText="取消" @close="show = false"
			@select="selectChange"></up-action-sheet>

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
	const {
		proxy
	} = getCurrentInstance();

	const XCXIsSelect = ref('是');

	const sendDate = reactive({
		score: 5,
		content: '',
		contact: ''
	});

	// 使用 ref 创建响应式数据   快速选择
	const show = ref(false);
	const list = ref([{
		name: '界面显示错乱',
	}, {
		name: '启动缓慢',
	}, {
		name: 'UI设计不好看',
	}, {
		name: '偶发性崩溃',
	}]);


	// 快速选择
	const chooseMsg = () => {
		show.value = true
	};
	// 选择展示
	const selectChange = (ite) => {
		sendDate.content = ite.name
	};

	// 提交
	const send = () => {

		if (!sendDate.content) {
			uni.showToast({
				icon: 'none',
				title: '请输入反馈内容'
			});
			return;
		}
		if (!sendDate.contact) {
			uni.showToast({
				icon: 'none',
				title: '请输入联系方式'
			});
			return;
		}
		uni.showLoading({
			title: '加载中...'
		})

		proxy.$http.postJson('/app/feedback/save', {
			contact: sendDate.contact,
			opinion: JSON.stringify(sendDate),
			userId: uni.getStorageSync('userId'),
		}).then(res => {
			if (res.code === 0) {
				uni.showToast({
					title: '投诉成功'
				});
				setTimeout(function() {
					uni.navigateBack();
				}, 1000);
			} else {
				uni.hideLoading();
				uni.showModal({
					showCancel: false,
					title: '投诉失败',
					content: res.msg
				});
			}
		});
	};

	onLoad(option => {
		//#ifdef APP
		let deviceInfo = {
			appid: plus.runtime.appid,
			imei: plus.device.imei, //设备标识
			p: plus.os.name === 'Android' ? 'a' : 'i', //平台类型，i表示iOS平台，a表示Android平台。
			md: plus.device.model, //设备型号
			app_version: plus.runtime.version,
			plus_version: plus.runtime.innerVersion, //基座版本号
			os: plus.os.version,
			net: '' + plus.networkinfo.getCurrentType()
		};
		Object.assign(sendDate, deviceInfo);
		//#endif
		XCXIsSelect.value = uni.getStorageSync('XCXIsSelect')
	});
	onShow(() => {

	});
	onPullDownRefresh(() => {

	});
	onReachBottom(() => {

	});
</script>

<style>
	@font-face {
		font-family: uniicons;
		font-weight: normal;
		font-style: normal;
		src: url('https://img-cdn-qiniu.dcloud.net.cn/fonts/uni.ttf') format('truetype');
	}

	page {
		background-color: #FFFFFF !important;
	}

	view {
		font-size: 28upx;
	}


	/*问题反馈*/
	.feedback-title {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		align-items: center;
		padding: 20upx;
		color: #8f8f94;
		font-size: 32upx;
	}

	.feedback-body {
		margin: 16upx;
		border-radius: 16upx;
		background: #F5F5F5;
	}

	.feedback-textare {
		height: 200upx;
		font-size: 28upx;
		line-height: 50upx;
		width: 100%;
		box-sizing: border-box;
		padding: 20upx 25upx 0;

	}

	.feedback-input {
		font-size: 28upx;
		height: 60upx;
		padding: 15upx 25upx;
		line-height: 50upx;
	}


	.feedback-submit {
		background: linear-gradient(121deg, #53D8A4, #ABEC4F);
		color: #FFFFFF;
		margin: 20upx;
		margin-top: 32upx;
	}
</style>