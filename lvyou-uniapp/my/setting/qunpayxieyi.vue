<template>
	<view>
		<view style="line-height: 26px;padding: 32upx;" class="home1">
			<view style="font-size: 28upx;" v-html="content"></view>
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

	// biz_unlock_group_agreement	解锁群协议
	const getText = () => {

		proxy.$http.postJson('/app/config/info', {
			key: 'biz_unlock_group_agreement'
		}).then(res => {
			if (res.code == 0) {
				if (res.data && res.data.value) {
					content.value = res.data.value;
				}
			}
		});
	}

	onLoad(option => {
		getText()
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
		background: #FFFFFF;
	}
</style>