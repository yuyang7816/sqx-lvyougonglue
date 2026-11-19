<template>
	<view>
		<textarea v-model="content" class="text-white text-df flex-sub" placeholder="请输入信息内容" maxlength="100"
			placeholder-style="text-align: left;"
			style="height: 300upx;width: 95%;margin: 20rpx;text-align: left;background: #FFFFFF;padding: 20rpx;border-radius: 15rpx;"></textarea>

		<view class="padding-lr-sm" v-if="type == 0">
			<u-button :custom-style="customStyle1" :hair-line="false" @click="save()">
				添加
			</u-button>
		</view>
		<view class="padding-lr-sm" style="margin-top: 100rpx;display: flex;" v-if="type == 1">
			<u-button style="width: 48%;margin-right: 20rpx;" :custom-style="customStyle2" :hair-line="false"
				@click="deleteMsg()">
				删除
			</u-button>
			<u-button style="width: 48%;" :custom-style="customStyle1" :hair-line="false" @click="updateMsg()">
				保存
			</u-button>
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
		onReachBottom
	} from '@dcloudio/uni-app';
	const {
		proxy
	} = getCurrentInstance();

	const content = ref('');
	const userId = ref('');
	const id = ref('');
	const type1 = ref(''); //0,常用语，1，自动回复
	const type = ref(''); //0，添加  1，编辑

	const customStyle1 = ref({
		backgroundColor: "#2FD7C8",
		border: 0,
		color: "#FFF",
		height: '98rpx'
	});
	const customStyle2 = ref({
		backgroundColor: "#ffffff",
		border: 0,
		color: "#000",
		height: '98rpx'
	});

	onLoad(option => {

		if (option.id) {
			id.value = option.id
			getDetailMsg();
		}
		if (option.type1) {
			type1.value = option.type1
		}
		if (option.type) {
			type.value = option.type
		}
		if (option.type == 1) {
			uni.setNavigationBarTitle({
				title: '编辑信息'
			})
		}
		if (option.type1 == 1) {
			getDetailMsg();
		}
		userId.value = uni.getStorageSync('userId')

	});
	onShow(() => {

	});
	const getDetailMsg = () => {
		if (type1.value == 1) {
			proxy.$http.get("/app/user/selectUserById").then(res => {
				if (res.code == 0) {
					content.value = res.data.msg ? res.data.msg : '';
				}
			});
		} else {
			proxy.$http.getT('/app/message/selectMessageById?id=' + id.value).then(res => {
				if (res.code == 0) {
					content.value = res.data.content;
				}
			});
		}
	};
	// 删除语句
	const deleteMsg=()=> {
		let userId = uni.getStorageSync('userId');
		uni.showModal({
			title: '温馨提示',
			content: '您确认要删除当前信息吗？',
			success: function (res) {
				if (res.confirm) {
					if (type1.value == 0) {
						proxy.$http.postT('/app/message/deleteMessage?id=' +id.value).then(res => {
							if (res.code == 0) {
								uni.hideLoading();
								uni.showToast({
									title:'删除成功！',
									icon:'none'
								})
								content.value = '';
								setTimeout(d => {
									uni.navigateBack();
								}, 1000);
							} else {
								uni.hideLoading();
								uni.showToast({
									title:res.msg,
									icon:'none'
								})
							}
						});
					} else {
						let data = {
							msg: ''
						}
						proxy.$http.postJson('/app/user/updateUser', data).then(res => {
							if (res.code == 0) {
								uni.hideLoading();
								uni.showToast({
									title:'删除成功！',
									icon:'none'
								})
								content.value = '';
								setTimeout(d => {
									uni.navigateBack();
								}, 1000);
							} else {
								uni.hideLoading();
								uni.showToast({
									title:res.msg,
									icon:'none'
								})
							}
						});
					}
				} else if (res.cancel) {
					console.log('用户点击取消');
				}
			}
		});

	};
	// 修改语句
	const updateMsg=()=> {
		uni.showModal({
			title: '温馨提示',
			content: '您确认要修改当前信息吗？',
			success: function (res) {
				if (res.confirm) {
					let userId = uni.getStorageSync('userId');
					let data = {
						state: 20,
						userId: userId,
						content: content.value,
						id: id.value
					}
					if (type1.value == 0) {
						proxy.$http.postJson('/app/message/updateMessage', data).then(res => {
							if (res.code == 0) {
								uni.hideLoading();
								uni.showToast({
									title:'修改成功！',
									icon:'none'
								})
								content.value = '';
								setTimeout(d => {
									uni.navigateBack();
								}, 1000);
							} else {
								uni.hideLoading();
								uni.showToast({
									title:res.msg,
									icon:'none'
								})
							}
						});
					} else {
						let data = {
							msg: content.value
						}
						proxy.$http.postJson('/app/user/updateUser', data).then(res => {
							if (res.code == 0) {
								uni.hideLoading();
								uni.showToast({
									title:'修改成功！',
									icon:'none'
								})
								content.value = '';
								setTimeout(d => {
									uni.navigateBack();
								}, 1000);
							} else {
								uni.hideLoading();
								uni.showToast({
									title:res.msg,
									icon:'none'
								})
							}
						});
					}
					
				} else if (res.cancel) {
					console.log('用户点击取消');
				}
			}
		});

	};
	//添加语句
	const save=()=> {
		
		uni.showLoading({
			title:'上传中...'
		})
		if (content.value == '') {
			uni.hideLoading();
			uni.showToast({
				title:'请输入信息内容！',
				icon:'none'
			})
			return;
		}
		// 添加常用语  分类传个20吧  state ，再传userId   content内容应该就行
		let userId =uni.getStorageSync('userId');
		let data = {
			state: 20,
			userId: userId,
			content: content.value
		}
	
		if (type1.value == 0) {
			proxy.$http.postJson('/app/message/insertMessage', data).then(res => {
				if (res.code == 0) {
					uni.hideLoading();
					uni.showToast({
						title:'添加成功！',
						icon:'none'
					})
					content.value = '';
					setTimeout(d => {
						uni.navigateBack();
					}, 1000);
				} else {
					uni.hideLoading();
					uni.showToast({
						title:res.msg,
						icon:'none'
					})
				}
			});
		} else {
			let data = {
				msg: content.value
			}
			proxy.$http.postJson('/app/user/updateUser', data).then(res => {
				if (res.code == 0) {
					uni.hideLoading();
					uni.showToast({
						title:'添加成功！',
						icon:'none'
					})
					content.value = '';
					setTimeout(d => {
						uni.navigateBack();
					}, 1000);
				} else {
					uni.hideLoading();
					uni.showToast({
						title:res.msg,
						icon:'none'
					})
				}
			});
		}
	};
	
	onPullDownRefresh(() => {

	});
	onReachBottom(() => {

	});
</script>

<style lang="scss">
	page {
		background-color: #F5F5F5;
	}

	.bg {
		background-color: #FFFFFF;
	}
</style>