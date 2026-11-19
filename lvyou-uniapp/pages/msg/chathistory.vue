<template>
	<view>
		<view class="index-header">
			<!-- 搜索板块 -->
			<view class="tui-header">
				<view class="tui-search" style="height: 60upx; flex:1;">
					<input type="text" class="text-search" v-model="search" placeholder="请输入搜索内容" />
				</view>
				<view @tap='rtBubble' class="margin-left-sm">搜索</view>
				<view></view>
			</view>
		</view>
		<view style="width: 100%;padding-bottom: 140rpx;background: #F5F5F5;margin-top: 80rpx;">
			<view style="display: flex;flex-direction: column;" v-for="(item,index) in ListItem">
				<view style="margin-top: 15rpx;width: 100%;text-align: center;font-size: 26rpx;color: #999999;">
					{{item.createTime}}
				</view>
				<view v-if="item.userId && userId != item.userId" style="width: 83%;margin-right: 15%;">
					<view class="chat-listitem" style="float: left;margin-left: 10rpx;">
						<view>
							<image @tap="goGuanZhu(item)" :src="item.imageUrl ? item.imageUrl : '/static/logos.png'"
								class="chat-listitem-image"></image>
						</view>


						<view class="chat-listitem-text" style="margin-left: 20rpx;"
							v-if="item.content && item.messageType === 1" v-html="item.content">
						</view>
						<image @tap="viewImg(item.content)" v-if="item.content && item.messageType === 2"
							mode="aspectFill" :src="item.content"
							style="height: 200rpx;width: 200rpx;margin-left: 20rpx;"></image>
						<image class="chat-listitem-text" v-if="item.content && item.messageType === 4"
							:src="($publicYuMing() || '') + '/emoji/' +item.content"
							style="height: 80rpx;width: 80rpx;margin-left: 20rpx;"></image>


						<view v-if="item.content && item.messageType === 3" class="chat-listitem-text bubble voice"
							:class="playMsgid == index?'play':''"
							style="margin-left: 20rpx;display: flex;align-items: center;"
							@tap="playVoice(item.content,index)">
							<image v-if="playMsgid != index" src="../../static/image/msg/laba.png"
								style="width: 35upx;height: 40upx;margin-right: 10rpx;"></image>
							<image v-if="playMsgid == index" src="../../static/image/msg/labagif.gif"
								style="width: 35upx;height: 40upx;margin-right: 10rpx;"></image>
							<view class="length">{{item.voiceLength ? item.voiceLength : 0}}</view>
						</view>


					</view>
				</view>

				<view v-if="item.userId && userId == item.userId" style="width: 83%;margin-left: 15%;">
					<view class="chat-listitem" style="float: right;">
						<view v-if="item.content && item.messageType === 1" @longpress="copy(item.content)"
							class="chat-listitem-text" style="margin-right: 20rpx;" v-html="item.content"></view>

						<image @tap="viewImg(item.content)" v-if="item.content && item.messageType === 2"
							mode="aspectFill" :src="item.content"
							style="height: 200rpx;width: 200rpx;margin-right: 20rpx;"></image>
						<image class="chat-listitem-text" v-if="item.content && item.messageType === 4"
							:src="($publicYuMing() || '') + '/emoji/' +item.content"
							style="height: 80rpx;width: 80rpx;margin-right: 20rpx;">
						</image>

						<view v-if="item.content && item.messageType === 3" class="chat-listitem-text bubble voice"
							:class="playMsgid == index?'play':''"
							style="margin-right: 20rpx;display: flex;align-items: center;"
							@tap="playVoice(item.content,index)">
							<view class="length">{{item.voiceLength ? item.voiceLength : 0}}</view>
							<image v-if="playMsgid != index" src="../../static/image/msg/labaleft.png"
								style="width: 35upx;height: 40upx;margin-left: 10rpx;"></image>
							<image v-if="playMsgid == index" src="../../static/image/msg/labaleft.png"
								style="width: 35upx;height: 40upx;margin-left: 10rpx;"></image>
						</view>



						<view>
							<image :src="imageUrl" class="chat-listitem-image"></image>
						</view>
					</view>
				</view>
			</view>
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

	//录音相关参数
	// #ifndef H5
	const RECORDER = ref('');
	// #endif
	const imageUrl = ref('');
	const userId = ref('');
	const ListItem = reactive([]);
	const chatConversationId = ref(''); //房间id
	const page = ref('1');
	const limit = ref('100');
	const search = ref('');
	const AUDIO = ref('');

	onLoad(option => {
		let image_url = uni.getStorageSync('avatar');
		if (image_url && image_url !== 'undefined') {
			imageUrl.value = image_url;
		} else {
			imageUrl.value = '/static/logos.png';
		}
		AUDIO.value = uni.createInnerAudioContext();
		// #ifndef H5
		RECORDER.value = uni.getRecorderManager();
		// #endif
		userId.value = uni.getStorageSync('userId')
		if (option.chatConversationId) {
			chatConversationId.value = option.chatConversationId
			getTimeOrListItem();
		}
		//语音自然播放结束
		AUDIO.value.onEnded((res) => {
			playMsgid.value = null;
		});
		// #ifndef H5
		//录音开始事件
		RECORDER.value.onStart((e) => {
			recordBegin(e);
		})
		//录音结束事件
		RECORDER.value.onStop((e) => {
			recordEnd(e);
		})
		// #endif
	});
	onShow(() => {

	});
	const rtBubble = () => {
		ListItem.value = []
		page.value =1
		getTimeOrListItem();
	};
	//查看大图
	const viewImg = (item) => {
		let imgsArray = [];
		imgsArray[0] = item;
		uni.previewImage({
			current: 0,
			urls: imgsArray
		});
	};
	// 播放语音
	const playVoice = (msg, index) => {
		playMsgid.value = index;
		AUDIO.src = msg[0];
		nextTick(() => {
			AUDIO.play();
		})

	};
	// 跳转主页
	const goGuanZhu = (item) => {
		uni.navigateTo({
			url: '/my/gird/zhuye?userId=' + item.userId
		})
	};
	// 获取聊天记录
	const getTimeOrListItem = () => {
		let userId = uni.getStorageSync('userId');
		let data = {
			page: page.value,
			limit: limit.value,
			chatConversationId: chatConversationId.value,
			userId: userId.value,
			content: search.value
		}
		
		proxy.$http.getT('/app/chat/selectChatContent', data).then(res => {
			if (res.code == 0) {
				var time = '';
				res.data.list.forEach(d => {
					d.createTime = getDateDiff(d.createTime);
					if (d.createTime) {
						d.createTime = '';
					} else {
						time = d.createTime;
					}

					if (d.messageType === 4) {
						let data = d.content.split(',');
						d.content = data;
					}
					if (d.messageType === 3) {
						let data = d.content.split(',');
						d.content = data;
					}


				});
				if (page.value == 1) {
					ListItem.splice(0, ListItem.length, ...res.data.list)
				} else {
					ListItem.push(...res.data.list)
				}
				ListItem.reverse();
				setTimeout(() => {
					uni.pageScrollTo({
						scrollTop: 99999,
						duration: 0
					});
				}, 50);
			}
			uni.hideLoading();
			uni.stopPullDownRefresh();
		});
	};
	// 处理时间
	const getDateDiff = (data) => {
		// 传进来的data必须是日期格式，不能是时间戳
		//var str = data;
		//将字符串转换成时间格式
		var timePublish = new Date(data);
		var timeNow = new Date();
		var minute = 1000 * 60;
		var hour = minute * 60;
		var day = hour * 24;
		var month = day * 30;
		var result = "2";

		var diffValue = timeNow - timePublish;
		var diffMonth = diffValue / month;
		var diffWeek = diffValue / (7 * day);
		var diffDay = diffValue / day;
		var diffHour = diffValue / hour;
		var diffMinute = diffValue / minute;


		if (diffMonth > 3) {
			result = timePublish.getFullYear() + "-";
			result += timePublish.getMonth() + "-";
			result += timePublish.getDate();
		} else if (diffMonth > 1) { //月
			result = data.substring(0, 10);
		} else if (diffWeek > 1) { //周
			result = data.substring(0, 10);
		} else if (diffDay > 1) { //天
			result = data.substring(0, 10);
		} else if (diffHour > 1) { //小时
			result = parseInt(diffHour) + "小时前";
		} else if (diffMinute > 1) { //分钟
			result = parseInt(diffMinute) + "分钟前";
		} else {
			result = "刚刚";
		}
		return result;
	};
	onPullDownRefresh(() => {
		page.value = 1;
		getTimeOrListItem();
	});
	onReachBottom(() => {
		page.value += 1
		getTimeOrListItem();
	});
</script>

<style lang="scss">
	@import "@/static/image/msg/css/style.scss";

	page {
		background: #F5F5F5;
	}

	.index-header {
		position: fixed;
		top: 80rpx;
		left: 0;
		right: 0;
		z-index: 99;
		background: #FFFFFF;
		padding: 20rpx 30rpx;

		.tui-header {
			display: flex;
			align-items: center;

			.tui-search {
				background: #F5F5F5;
				border-radius: 55rpx;
				padding: 0 20rpx;
				display: flex;
				align-items: center;
			}
		}
	}

	.chat-listitem {
		display: flex;
		margin-top: 20rpx;
		padding: 10rpx;

		.chat-listitem-text {
			color: #333333;
			background: #FFFFFF;
			margin-top: 6rpx;
			width: fit-content;
			padding: 15rpx;
			font-size: 30rpx;
			height: max-content;
			word-wrap: break-word;
			word-break: break-all;
			border-radius: 10rpx;

		}

		.chat-listitem-image {
			margin-top: 5rpx;
			width: 75rpx;
			height: 75rpx;
			border-radius: 55rpx;
		}
	}
</style>