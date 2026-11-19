<template>
	<view class="">

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
	// 占位图
	import empty from '@/components/empty/empty.vue';
	onLoad(option => {
		
	});
	onShow(() => {
	
	});
	onPullDownRefresh(() => {
		
	});
	onReachBottom(() => {
		
	});
</script>

<style lang="scss">
	
</style>