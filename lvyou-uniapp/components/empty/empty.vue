<template>
	<view style="padding-top: 200rpx;">
		<up-empty :mode="image" :text="contont" :icon="icon" width="260">
		</up-empty>
	</view>
</template>

<script setup>
	defineProps({
		//内容
		contont: {
			type: String,
			default: '暂无数据'
		},
		//图片
		image: {
			type: String,
			default: 'list'
		},
		icon: {
			type: String,
			default: '/static/image/empty.png'
		},
	})
</script>

<style lang="scss">

</style>