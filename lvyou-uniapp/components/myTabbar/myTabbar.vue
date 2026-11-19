<template>
	<view>
		<!-- 自定义导航栏 -->
		<up-tabbar :value="value" :fixed="true" :placeholder="true" :safeAreaInsetBottom="false" :radius="radius"
			:height="120+'rpx'" activeColor="#65DC93" inactiveColor="#CCCCCC" zIndex="99">
			<up-tabbar-item text="首页" @click="goSwitch">
				<template #active-icon>
					<image class="item_icon" src="/static/tabbar/index_s.png">
					</image>
				</template>
				<template #inactive-icon>
					<image class="item_icon" src="/static/tabbar/index.png"></image>
				</template>
			</up-tabbar-item>

			<up-tabbar-item text="攻略群" @click="goSwitch">
				<template #active-icon>
					<image class="item_icon" src="/static/tabbar/qun_s.png">
					</image>
				</template>
				<template #inactive-icon>
					<image class="item_icon" src="/static/tabbar/qun.png"></image>
				</template>
			</up-tabbar-item>

			<up-tabbar-item @click="goSwitch">
				<template #active-icon>
					<image class="item_icons" src="/static/tabbar/addFu.png">
					</image>
				</template>
				<template #inactive-icon>
					<image class="item_icons" src="/static/tabbar/addFu.png"></image>
				</template>
			</up-tabbar-item>

			<up-tabbar-item text="消息" @click="goSwitch" :badge="tagNum">
				<template #active-icon>
					<image class="item_icon" src="/static/tabbar/message_s.png">
					</image>
				</template>
				<template #inactive-icon>
					<image class="item_icon" src="/static/tabbar/message.png"></image>
				</template>
			</up-tabbar-item>

			<up-tabbar-item text="我的" @click="goSwitch">
				<template #active-icon>
					<image class="item_icon" src="/static/tabbar/my_s.png">
					</image>
				</template>
				<template #inactive-icon>
					<image class="item_icon" src="/static/tabbar/my.png"></image>
				</template>
			</up-tabbar-item>
		</up-tabbar>

	</view>
</template>

<script setup>
	import {
		ref,
		reactive,
		getCurrentInstance,
	} from 'vue';
	const {
		proxy
	} = getCurrentInstance();
	import { storeToRefs } from 'pinia'
	import { useMainStore } from '@/store'
	
	const store = useMainStore()
	
	// 使用 storeToRefs 保证解构后的响应性
	const { tagNum, param } = storeToRefs(store)
	
	// 接收一下父组件的传值
	defineProps({
		//下标
		value: {
			type: [String, Number],
			default: ''
		},
	})
	// 底部圆角
	const radius = ref('24rpx 24rpx 0 0');
	// const tagNum = computed(() => store.tagNum)
	// const getTagNum = () => {
	// 	// console.log(store.tagNum, '222222')
	// 	return store.tagNum
	// };

	const goSwitch = (e) => {
		console.log(e)
		if (e !== 2) {
			switch (e) {
				case 0:
					uni.switchTab({
						url: '/pages/index/index'
					})
					break;
				case 1:
					uni.switchTab({
						url: '/pages/groupChat/index'
					})
					break;
				case 3:
					uni.switchTab({
						url: '/pages/msg/index'
					})
					break;
				case 4:
					uni.switchTab({
						url: '/pages/my/index'
					})
					break;
				default:
					break;
			}
		} else {
			releaseFrom()
		}
	};
	const releaseFrom = () => {
		if (!uni.getStorageSync('token')) {
			uni.showModal({
				title: '提示',
				content: '还未登录，请登录后发布',
				showCancel: true,
				complete(ret) {
					if (ret.confirm) {
						uni.navigateTo({
							url: '/pages/public/login'
						});
					}
				}
			});
			return
		}
		uni.navigateTo({
			url: '/my/dynamic/releaseone'
		})
		return
		proxy.$http.get("/app/base/user/info").then(re => {
			if (re.code == 0) {
				let rate = re.data.rate
				let createCount = re.data.createCount
				let consortiaId = re.data.consortiaId
				let user = re.data
				let obj = {
					sex: user.sex,
					age: user.age,
					phone: user.phone,
					userName: user.userName,
					avatar: user.avatar,

				}
				let a = checkEmptyOrNullProperties(obj)
				if (a.length == 0) {
					uni.navigateTo({
						url: '/my/dynamic/releaseone'
					})

				} else {
					a.some(ite => {
						switch (ite) {
							case 'avatar':
								uni.showModal({
									title: '提示',
									content: '请先去个人资料完善头像',
									success: function(res) {
										if (res.confirm) {
											uni.navigateTo({
												url: '/pages/my/userInfo'
											})
										} else if (res.cancel) {
											console.log('用户点击取消');
										}
									}
								});
								return true;
								break;
							case 'userName':
								uni.showModal({
									title: '提示',
									content: '请先去个人资料完善昵称',
									success: function(res) {
										if (res.confirm) {
											uni.navigateTo({
												url: '/pages/my/userInfo'
											})
										} else if (res.cancel) {
											console.log('用户点击取消');
										}
									}
								});
								return true;
								break;
							case 'age':
								uni.showModal({
									title: '提示',
									content: '请先去个人资料完善年龄',
									success: function(res) {
										if (res.confirm) {
											uni.navigateTo({
												url: '/pages/my/userInfo'
											})
										} else if (res.cancel) {
											console.log('用户点击取消');
										}
									}
								});
								return true;
								break;
							case 'sex':
								uni.showModal({
									title: '提示',
									content: '请先去个人资料完善性别',
									success: function(res) {
										if (res.confirm) {
											uni.navigateTo({
												url: '/pages/my/userInfo'
											})
										} else if (res.cancel) {
											console.log('用户点击取消');
										}
									}
								});
								return true;
								break;
							case 'phone':
								uni.showModal({
									title: '提示',
									content: '请先去个人资料完善手机号码',
									success: function(res) {
										if (res.confirm) {
											uni.navigateTo({
												url: '/pages/my/userInfo'
											})
										} else if (res.cancel) {
											console.log('用户点击取消');
										}
									}
								});
								return true;
								break;
							default:
								break;
						}
					})
				}

			}

		});

	};
	const checkEmptyOrNullProperties = (obj) => {
		let keysWithEmptyOrNullValues = [];

		for (let key in obj) {
			if (obj.hasOwnProperty(key)) {
				if (obj[key] === null || obj[key] === '') {
					keysWithEmptyOrNullValues.push(key);
				}
			}
		}

		return keysWithEmptyOrNullValues;
	};
</script>

<style lang="scss">
	.item_icon {
		width: 46rpx;
		height: 46rpx;
	}

	.item_icons {
		width: 65rpx;
		height: 65rpx;
	}
</style>