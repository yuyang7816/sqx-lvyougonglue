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

	// biz_about_us	关于我们
	const getText = () => {
		proxy.$http.postJson('/app/config/info', {
			key: 'biz_about_us'
		}).then(res => {
			if (res.code == 0) {
				content.value = res.data.value;
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