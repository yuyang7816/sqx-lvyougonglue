<template>
	<view class="vcode-input-body">
		<text class="vcode-input-item" :class="isBorderLine?'vcode-input-line':'vcode-input-border'"
			v-for="(v,index) in sum" :key="index" @tap.stop="setFocus" :style="{
			borderColor:text.length===index&&focus?borderActiveColor:(text.length>index?borderValueColor:borderColor),
			color:'#000000'
		}">{{text[index]}}</text>
		<view class="hidden-input">
			<input type="number" :show-confirm-bar="false" auto-blur :cursor="99" :focus="focus" :maxlength="sum"
				v-model="suMunber" @blur="setBlur" @focus="setFocus" :password="isPassword" placeholder="验证码"
				:cursor-spacing="1000000" />
		</view>
	</view>
</template>

<script setup>
	/**
	 * vcode-input 验证码输入框
	 * @description 验证码输入框。
	 * @property {Boolean}			autofocus			自动获取焦点
	 * @property {Number}			sum					验证码长度
	 * @property {Boolean}			isBorderLine		显示的输入框的样式
	 * @property {String}			borderColor			输入框未输入边框颜色
	 * @property {String}			borderValueColor	输入框已输入边框颜色
	 * @property {String}			borderActiveColor	输入框选中边框颜色
	 * @property {Boolean}			isAutoComplete		当输入长度达到sum规定时才回调
	 * @property {Boolean}			isPassword			是否为密文输入
	 * @example <vcode-input ref="VcodeInput" @vcodeInput="vcodeInput"></vcode-input>
	 */
	import {
		ref,
		reactive,
		watch,
		onMounted,
		defineEmits,
	} from 'vue';

	// 接收一下父组件的传值
	const props = defineProps({
		autofocus: {
			type: Boolean,
			default: true
		},
		sum: {
			type: Number,
			default: 6
		},
		isBorderLine: {
			type: Boolean,
			default: false
		},
		borderColor: {
			type: String,
			default: '#DADADA'
		},
		borderValueColor: {
			type: String,
			default: '#5'
		},
		borderActiveColor: {
			type: String,
			default: '#FF6B00'
		},
		isAutoComplete: {
			type: Boolean,
			default: true
		},
		isPassword: {
			type: Boolean,
			default: false
		}
	})
	// 定义响应式数据
	const focus = ref(false);
	const text = reactive([]);
	const suMunber = ref('');

	const emit = defineEmits(['vcodeChange']);
	// 监听 value 的变化
	watch(suMunber, (val) => {
		if (props.isAutoComplete) {
			// console.log(props.sum,'****',suMunber.value)
			if (suMunber.value.length >= props.sum) {
				// 触发自定义事件
				emit('vcodeChange', suMunber.value);
			}
		} else {
			emit('vcodeChange', suMunber.value);
		}

		console.log(props.isPassword, '99999999')
		if (props.isPassword) {
			let val = '';
			for (let i = 0; i < suMunber.value.length; i++) {
				val += '●';
			}
			suMunber.value = val;
		} else {
			if (suMunber.value) {
				console.log(suMunber.value, '1111', text.value)
				text.splice(0, text.length, ...suMunber.value.split(""));
			} else {
				text.splice(0, text.length)
			}
		}
	});

	// 组件挂载时执行
	onMounted(() => {
		initInput()
	});

	const initInput = () => {
		// #ifndef MP-WEIXIN
		if (props.autofocus)
			focus.value = true;
		// #endif
	};
	const setBlur = () => {
		uni.hideKeyboard();
		focus.value = false;
	};
	const setFocus = () => {
		focus.value = true;
	};
	const clearValue = () => {
		setBlur();
		suMunber.value = '';
		text.splice(0, text.length)
	}
</script>



<style lang="scss">
	.vcode-input-body {
		margin-left: -36rpx;
		margin-right: -36rpx;
		position: relative;
		overflow: hidden;
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		justify-content: center;
		align-items: center;
	}

	.vcode-input-item {
		background: #F5F5F5;
		border-radius: 16rpx;
		width: 101rpx;
		height: 101rpx;
		margin-left: 12rpx;
		margin-right: 12rpx;
		line-height: 101rpx;
		text-align: center;
		font-weight: 500;
		font-size: 36rpx;
	}

	.vcode-input-border {
		// border-style: solid;
		// border-width: 2rpx;
		// border-color: $uni-border-color;
		// border-radius: 4rpx;
	}

	.vcode-input-line {
		border-bottom-style: solid;
		border-bottom-width: 2rpx;
		border-color: $uni-border-color;
	}

	.hidden-input {
		width: 1px;
		height: 1px;
		position: absolute;
		left: 60px;
		bottom: 30px;
		overflow: hidden;
	}
</style>