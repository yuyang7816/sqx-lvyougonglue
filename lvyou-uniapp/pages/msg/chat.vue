<template>
	<view>
		<view class="topbox ">
			<view class="flex align-center">
				<image src="../../static/image/msg/alert.png" style="width: 88upx;height: 88upx;"></image>
				<view class="text-sm margin-left-sm" style="color: #A1A2B2;width: 84%;">
					{{liaotian}}<text style="color: #2FD7C8;" @click="goTousu()">{{tousu}}</text>
				</view>
			</view>
		</view>

		<view style="width: 100%;padding-bottom:240rpx;padding-top: 280rpx;" @touchstart="hideDrawer">
			<view style="display: flex;flex-direction: column;" v-for="(item,index) in ListItem">
				<view style="margin-top: 15rpx;width: 100%;text-align: center;font-size: 26rpx;color: #999999;"
					v-if="item.createTime">
					{{item.createTime}}
				</view>
				<view v-if="item.userId && userId != item.userId" style="width: 83%;margin-right: 15%;">
					<view class="chat-listitem" style="float: left;margin-left: 10rpx;">
						<view>
							<image @tap="goGuanZhu(item)" :src="item.avatar ? item.avatar : '/static/logos.png'"
								class="chat-listitem-image"></image>
						</view>
						<view class="chat-listitem-text" style="margin-left: 20rpx;" @longpress="detaChat(item)"
							v-if="item.content && item.messageType === 1" v-html="item.content">
						</view>

						<image @tap="viewImg(item.content)" v-if="item.content && item.messageType === 2"
							@longpress="detaChat(item)" :src="item.content" mode="aspectFill"
							style="height: 200rpx;width: 200rpx;margin-left: 20rpx;">
						</image>

						<image class="chat-listitem-text" @tap="viewImg(item.content)" @longpress="detaChat(item)"
							v-if="item.content && item.messageType === 4"
							:src="($publicYuMing() || '') + '/emoji/' +item.content"
							style="height: 80rpx;width: 80rpx;margin-left: 20rpx;"></image>

						<view v-if="item.content && item.messageType === 3" class="chat-listitem-text bubble voice"
							:class="playMsgid == index?'play':''" @longpress="detaChat(item)"
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

						<view class="chat-listitem-text" style="margin-right: 20rpx;" @longpress="detaChat(item)"
							v-if="item.content && item.messageType === 1" v-html="item.content">
						</view>
						<image @tap="viewImg(item.content)" v-if="item.content && item.messageType === 2"
							@longpress="detaChat(item)" :src="item.content" mode="aspectFill"
							style="height: 200rpx;width: 200rpx;margin-right: 20rpx;">
						</image>

						<image class="chat-listitem-text" @tap="viewImg(item.content)"
							v-if="item.content && item.messageType === 4" @longpress="detaChat(item)"
							:src="($publicYuMing() || '') + '/emoji/' +item.content"
							style="height: 80rpx;width: 80rpx;margin-right: 20rpx;">
						</image>

						<view v-if="item.content && item.messageType === 3" class="chat-listitem-text bubble voice"
							:class="playMsgid == index?'play':''" @longpress="detaChat(item)"
							style="margin-right: 20rpx;display: flex;align-items: center;"
							@tap="playVoice(item.content,index)">
							<view class="length">{{item.voiceLength ? item.voiceLength : 0}}</view>
							<image v-if="playMsgid != index" src="../../static/image/msg/labaleft.png"
								style="width: 35upx;height: 40upx;margin-left: 10rpx;"></image>
							<image v-if="playMsgid == index" src="../../static/image/msg/labaleft.png"
								style="width: 35upx;height: 40upx;margin-left: 10rpx;"></image>

						</view>
						<image :src="imageUrl" class="chat-listitem-image"></image>
					</view>
				</view>
			</view>
		</view>

		<!-- 抽屉栏 -->
		<view class="popup-layer" :class="popupLayerClass">

			<swiper class="emoji-swiper" indicator-active-color="#cccccc" :class="{hidden:hideEmoji}"
				indicator-dots="true" duration="150">
				<swiper-item v-for="(page1,pid) in emojiList" :key="pid">
					<view v-for="(em,eid) in page1" :key="eid" @tap="setChatSave(4,em.url)">
						<image mode="widthFix" style="width: 48rpx;height: 48rpx;"
							:src="($publicYuMing() || '') + '/emoji/'+em.url"></image>
					</view>
				</swiper-item>
			</swiper>
			<!-- 更多功能 相册-拍照-红包 -->
			<view class="more-layer" :class="{hidden:hideMore}">
				<view class="list">

					<view>
						<view class="box" @click="chooseImage()">
							<view class="icon tupian2" style="color: #000000;"></view>
						</view>
						<view style="width: 100%;text-align: center;color: #000000;">上传图片</view>
					</view>

					<view>
						<view class="box" @tap="showChangYongSelect">
							<view class="icon jianpan" style="color: #000000;"></view>
						</view>
						<view style="width: 100%;text-align: center;color: #000000;">常用语句</view>
					</view>

					<view>
						<view class="box" @tap="goAdd">
							<view class="icon add" style="color: #000000;"></view>
						</view>
						<view style="width: 100%;text-align: center;color: #000000;">添加语句</view>
					</view>
					<view>
						<view class="box" @tap="goHistory">
							<view class="icon chehui" style="color: #000000;"></view>
						</view>
						<view style="width: 100%;text-align: center;color: #000000;">聊天记录</view>
					</view>
				</view>
			</view>
		</view>
		<!-- 底部输入栏 -->
		<view class="input-box" :class="popupLayerClass">
			<view class="flex " style="padding:0 10rpx 0 15rpx;">

				<view class="textbox" :style="isVoice ? 'width:100%' : ''">
					<view class="voice-mode" :class="[isVoice?'':'hidden',recording?'recording':'']"
						@touchstart="voiceBegin" @touchmove.stop.prevent="voiceIng" @touchend="voiceEnd"
						@touchcancel="voiceCancel">{{voiceTis}}</view>

					<view class="text-mode" :class="isVoice?'hidden':''">
						<view class="box">
							<up-input @confirm="setChatSave(1)" placeholder="开始输入" border="none"
								v-model="content"></up-input>
						</view>
					</view>
				</view>

				<view class="send" :class="isVoice?'hidden':''" @tap='$u.throttle(setChatSave(1), 900)'>
					<view class="btn">发送</view>
				</view>
			</view>
			<view class="action flex justify-between align-center">
				<!-- H5下不能录音，输入栏布局改动一下 -->
				<view class="voice">
					<view class="icon" :class="isVoice?'jianpan':'yuyin'" @tap="switchVoice"></view>
				</view>

				<view class="em" @tap="chooseEmoji">
					<view class="icon biaoqing"></view>
				</view>

				<view class="more" @tap="showMore">
					<view class="icon add"></view>
				</view>

			</view>
		</view>

		<!-- 录音UI效果 -->
		<view class="record" :class="recording?'':'hidden'">
			<view class="ing" :class="willStop?'hidden':''">
				<view class="icon luyin2"></view>
			</view>
			<view class="cancel" :class="willStop?'':'hidden'">
				<view class="icon chehui"></view>
			</view>
			<view class="tis" :class="willStop?'change':''">{{recordTis}}</view>
		</view>

		<!--常用语句  -->
		<up-picker :show="showChangYong" :columns="changYongList" title="常用语句" @confirm="selConfirm"
			@cancel="showChangYong = false" keyName="label"></up-picker>
	</view>
</template>

<script setup>
	import {
		ref,
		reactive,
		nextTick,
		getCurrentInstance,
		computed
	} from 'vue';
	import {
		onLoad,
		onShow,
		onPullDownRefresh,
		onReachBottom,
		onUnload
	} from '@dcloudio/uni-app';
	import config from '@/common/config.js';
	const RECONNECT_INTERVAL = 3000; // 延迟3秒后进行重连
	const HEARTBEAT_INTERVAL = 2000; // 每20秒发送一次心跳消息
	const {
		proxy
	} = getCurrentInstance();
	// #ifdef H5
	const recorder = ref({}); // 定义一个MediaRecorder对象
	var stream = ref({}); //定义一个音频流的对象
	var chunks = reactive([]); // 定义一个用于存储音频数据片段的数组
	// #endif

	const userId = ref('');
	const imageUrl = ref('');
	const byUserId = ref(''); //聊天用户id
	const byNickName = ref(''); //聊天用户名
	const chatConversationId = ref(''); //房间id
	const liaotian = ref(''); //聊天提示文字
	const tousu = ref(''); //去投诉
	//scoket是否链接
	const isOpen = ref(false);
	//心跳定时器
	const socketTimer = ref(null);
	const page = ref('1');
	const limit = ref('100');
	const ListItem = reactive([]);

	const popupLayerClass = ref(''); // 抽屉参数
	const hideMore = ref(true);
	const emojiList = reactive([]); //表情包
	const hideEmoji = ref(true);

	//录音相关参数
	// #ifndef H5
	const RECORDER = ref('');
	// #endif
	const blob = ref('');
	const voicePaths = ref('');
	const isVoice = ref(false);
	const voiceTis = ref('按住 说话');
	const recordTis = ref('手指上滑 取消发送');
	const recording = ref(false);
	const willStop = ref(false);
	const initPoint = ref({
		identifier: 0,
		Y: 0
	});
	const recordTimer = ref(null);
	const recordLength = ref(0);
	//播放语音相关参数
	const AUDIO = ref('');
	const playMsgid = ref(null);
	const VoiceTimer = ref(null);

	const content = ref('');
	const formats = ref({});
	const isEmoj = ref(false);
	// 常用语句
	const showChangYong = ref(false);
	const changYongList = reactive([]); //常用语


	onLoad(option => {
		if (option.byUserId) {
			byUserId.value = option.byUserId
			getUserInfo();
		}
		if (option.byNickName) {
			byUserId.byNickName = option.byNickName
		}

		connect();
		if (option.chatConversationId) {
			chatConversationId.value = option.chatConversationId
			getTimeOrListItem();
		} else {
			insertChat();
		}
		AUDIO.value = uni.createInnerAudioContext();
		// #ifndef H5
		RECORDER.value = uni.getRecorderManager();
		// #endif
		userId.value = uni.getStorageSync('userId')


		//投诉配置
		proxy.$http.get('/app/common/type/615').then(res => {
			if (res.code == 0) {
				if (res.data && res.data.value) {
					tousu.value = res.data.value
				}
			}
		});
		//聊天配置
		proxy.$http.get('/app/common/type/614').then(res => {
			if (res.code == 0) {
				if (res.data && res.data.value) {
					liaotian.value = res.data.value
				}
			}
		});
		setEmoList()
		getChangYong()

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

		if (uni.getStorageSync('avatar')) {
			imageUrl.value = uni.getStorageSync('avatar');
			console.log(imageUrl.value)
		} else {
			imageUrl.value = '/static/logos.png';
		}
	});
	onUnload(() => {
		console.log('关闭了')
		//页面隐藏的时候关闭scoket，关闭心跳
		closeScoket();
	});
	// 获取该用户状态
	const getUserInfo = () => {
		proxy.$http.getT("/app/user/selectUserByUserId?userId=" + byUserId.value).then(res => {
			if (res.code == 0) {
				if (res.data.status && res.data.status == 2) {
					uni.showModal({
						showCancel: false,
						title: '提示',
						content: '该用户违反平台规则已被封号，请勿转账，谨防被骗！',
					});
				} else {
					if (res.data.risk && res.data.risk == 1) {
						uni.showModal({
							showCancel: false,
							title: '提示',
							content: '该账号是风险账号，请勿转账，谨防被骗！',
						});
					}
				}


			}
		});
	};
	//添加常用语/自动回复
	const goAdd = () => {
		showMore();
		uni.navigateTo({
			url: '/pages/msg/addmsg'
		});
	};
	// 跳转聊天记录
	const goHistory = () => {
		showMore();
		uni.navigateTo({
			url: '/pages/msg/chathistory?chatConversationId=' + chatConversationId.value
		});
	};
	// 选择常用语
	const selConfirm = (e) => {
		content.value = e.value[0].label;
		showChangYong.value = false
		setChatSave(1);
	};
	// 获取常用语
	const getChangYong = () => {
		proxy.$http.getT('/app/message/selectMessageByUserId?page=1&limit=100&state=20')
			.then(res => {
				if (res.code == 0) {
					let arr = []
					res.data.list.forEach(d => {

						let data = {
							label: d.content,
							id: d.id
						}
						arr.push(data);

					});
					console.log(arr, '=====')
					changYongList.splice(0, 0, arr)
					console.log(changYongList, '=====')
				}
			});
	};
	// 常用语句
	const showChangYongSelect = () => {
		showMore();
		showChangYong.value = !showChangYong.value
	};
	// 跳转主页
	const goGuanZhu = (item) => {
		uni.navigateTo({
			url: '/my/gird/zhuye?userId=' + item.userId
		})
	};
	// 打开表情弹框
	const chooseEmoji = () => {
		isEmoj.value = true
		hideMore.value = true;
		if (hideEmoji.value) {
			hideEmoji.value = false;
			openDrawer();
		} else {
			hideDrawer();
		}
	};
	//更多功能(点击+弹出)
	const showMore = () => {
		isVoice.value = false;
		hideEmoji.value = true;
		if (hideMore.value) {
			hideMore.value = false;
			openDrawer();
		} else {
			hideDrawer();
		}
	};
	// 打开抽屉
	const openDrawer = () => {
		popupLayerClass.value = 'showLayer';
	};
	// 隐藏抽屉
	const hideDrawer = () => {
		popupLayerClass.value = '';
		setTimeout(() => {
			hideMore.value = true;
			hideEmoji.value = true;
		}, 150);
	};
	// 切换语音/文字输入
	const switchVoice = () => {
		hideDrawer();
		isVoice.value = isVoice.value ? false : true;
	};
	//获取焦点，如果不是选表情ing,则关闭抽屉
	const textareaFocus = (e) => {
		if (popupLayerClass.value == 'showLayer' && hideMore.value == false) {
			hideDrawer();

		}
	};

	// 获取表情包
	const setEmoList = () => {
		let bqbNumber = uni.getStorageSync('bqbNumber') ? uni.getStorageSync('bqbNumber') : 0;
		bqbNumber = parseInt(bqbNumber);
		emojiList.value = [];
		let dataList = [];
		for (var i = 1; i <= bqbNumber; i++) {
			let data = {}
			data.url = i + '.gif';
			data.alt = '[表情' + i + ']';
			dataList.push(data)
			if (dataList.length == 24) {
				emojiList.push(dataList);
				dataList = [];
			}
		}
	};
	// 发送心跳包
	const startHeartbeat = () => {
		// HeartBeat 这里是和后端约定好的值，我们发送给后端，后端再把这个值返给我们
		// 目的就是让后端知道我们一直在
		const heartbeatMsg = 'HeartBeat';
		const sendHeartbeat = () => {
			if (isOpen.value) {
				console.log('发送HeartBeat')
				uni.sendSocketMessage({
					data: heartbeatMsg,
				}).catch(error => {
					console.log('发送心跳消息失败:', error);
					//发送消息失败则进行重新链接
					connect();
				});
			}
		};
		socketTimer.value = setInterval(sendHeartbeat, HEARTBEAT_INTERVAL);
	};
	// 开始重连
	const reconnect = () => {
		if (!isOpen.value) {
			clearTimeout(socketTimer.value);
			socketTimer.value = setTimeout(() => {
				console.log('开始重连...');
				connect();
			}, RECONNECT_INTERVAL);
		}
	};
	//链接scoket
	const connect = () => {
		// 链接scoket
		uni.connectSocket({
			url: config.WSHOST1 + uni.getStorageSync('userId'),
			data() {
				return {
					msg: 'Hello'
				}
			},
			header: {
				'content-type': 'application/json'
			},
			method: 'GET'
		})
		//连接打开
		uni.onSocketOpen((res) => {
			isOpen.value = true;
			startHeartbeat();
		});
		//连接异常
		uni.onSocketError((err) => {
			//如果连接异常那么把状态设置为false
			isOpen.value = false;
			//并尝试进行重连
			reconnect();
		});
		//连接关闭
		uni.onSocketClose((res) => {
			isOpen.value = false;
		});
		//接收消息
		uni.onSocketMessage((res) => {
			console.log(res, 'scoket接收到的消息')
			if (res.data == 'HeartBeat') { //如果是心跳回推则不进行处理
				return
			}
			let getRes = JSON.parse(res.data)
			//每次接收到消息的时候刷新一下聊天列表
			// that.getChatList()
		})
	};
	//页面隐藏的时候关闭scoket，关闭心跳
	const closeScoket = () => {
		if (socketTimer.value) {
			uni.closeSocket()
		}
		isOpen.value = false
		clearInterval(socketTimer.value)
		socketTimer.value = null
	};
	const insertChat = () => {
		let userId = uni.getStorageSync('userId');
		let data = {
			focusedUserId: byUserId.value,
			userId: userId
		}
		proxy.$http.postJson('/appChat/insertChatConversation', data).then(res => {
			if (res.code == 0 && res.data) {
				chatConversationId.value = res.data.chatConversationId;
				getTimeOrListItem();
			}
		});
	};
	const getTimeOrListItem = () => {
		let userId = uni.getStorageSync('userId');
		let data = {
			page: page.value,
			limit: limit.value,
			chatConversationId: chatConversationId.value,
			userId: userId.value
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
	// 跳转去投诉
	const goTousu = () => {
		uni.navigateTo({
			url: '/my/order/complain?byUserId=' + byUserId.value
		})
	};
	// 非法关键字效验
	const getChatSearchKeys = (key) => {
		let list = uni.getStorageSync("chatSearchKeys");
		let keys = key.replace(/\s*/g, "")
		let over = false
		for (var i = 0; i < keys.length; i++) {
			if (list.indexOf(keys[i]) != -1) {
				over = true;
			}

		}
		return over;

	};
	//审核发送的消息
	const auditContent = async (content) => {
		let data = {
			content
		};
		let result = await proxy.$http.post('/app/user/auditContent', data);
		if (result.code === 0) {
			return true; // 审核成功，返回true
		} else if (result.code === 501) {
			clear()
			uni.showToast({
				title: '请输入聊天内容',
				icon: 'none'
			})
			return false; // 审核失败，返回false
		} else {
			uni.showToast({
				title: result.msg,
				icon: 'none'
			});
			return false; // 审核失败，返回false
		}
	};
	//清空输入框
	const clear = () => {
		content.value = ''
	};
	// 消息发送
	const setChatSave = async (type, url) => { //type  1 文字   2图片  4表情  3语音
		console.log('123', content.value)
		uni.showLoading({
			title: '发送中...'
		})
		if (type === 1 && content.value == '') {
			uni.showToast({
				title: '请输入聊天内容',
				icon: 'none'
			})
			return;
		}

		if (getChatSearchKeys(content.value)) {
			uni.showToast({
				title: "输入内容带有非法关键字请重新输入",
				mask: false,
				duration: 1500,
				icon: "none"
			});

			proxy.$http.postT('/app/risk/insertRisk?riskType=5&content=' + content.value).then(res => {

			})
			return;
		}


		//调用接口判断发送的内容是否违规
		if (type === 1) {
			let result = await auditContent(content.value); // 需要传入实际的内容参数
			if (result === false) {
				return; // 停止执行 sendMessage 方法的剩余部分
			}
		}


		let userId = uni.getStorageSync('userId');
		if (type === 4) {
			content.value = url;
		}

		let data = {};
		if (type == 3) {
			data = {
				userId: byUserId.value,
				content: content.value,
				voiceLength: url,
				messageType: type,
				chatConversationId: chatConversationId.value
			}
		} else {
			data = {
				userId: byUserId.value,
				content: content.value,
				messageType: type,
				chatConversationId: chatConversationId.value
			}
		}

		data = JSON.stringify(data);
		console.log(data)
		uni.sendSocketMessage({
			data: data,
			success(res) {
				let image_url = uni.getStorageSync('avatar');
				if (!image_url) {
					image_url = '/static/logos.png';
				}
				let data = {
					chat: {
						userHead: image_url
					},
					content: content.value,
					type: type,
					sendType: 1
				}

				setTimeout(() => {
					getTimeOrListItem();
				}, 50);

			},
			fail(err) {
				console.log(err);
			}
		})
		clear();
		content.value = '';
	};

	// 播放语音
	const playVoice = (msg, index) => {
		playMsgid.value = index;
		AUDIO.src = msg[0];
		nextTick(() => {
			AUDIO.play();
		})

	};
	// 录音开始
	const voiceBegin = (e) => {
		// #ifdef H5
		console.log(stream.value.getTracks)
		if (stream.value.getTracks) {
			stream.getTracks().forEach((track) => track.stop())
			stream.value = null
		}
		if (recorder.value) {
			recorder.value = null
		}
		recordLength.value = 0
		voicePaths.value = '';

		startRecord()
		// #endif

		if (e.touches.length > 1) {
			return;
		}
		initPoint.Y = e.touches[0].clientY;
		initPoint.identifier = e.touches[0].identifier;

		// #ifndef H5
		RECORDER.start({
			format: "mp3"
		}); //录音开始,
		// #endif

	};
	//开始录音
	const startRecord = async function() {
		// 获取麦克风音频数据流
		stream.value = await navigator.mediaDevices.getUserMedia({
			audio: true
		})
		// 初始化MediaRecorder对象
		recorder.value = new MediaRecorder(stream.value);
		// 将 stream 转成 blob 来存放
		recorder.ondataavailable = (blobEvent) => {
			chunks.push(blobEvent.data);
		}
		// 停止时生成预览的 blob url
		recorder.onstop = () => {
			const blob = new Blob(chunks, {
				type: 'audio/mp3'
			})
			voicePaths.value = blob;
			blob.value = blob;

		}
		recorder.start();
		recordBegin()
	};
	//录音开始UI效果
	const recordBegin = (e) => {
		recording.value = true;
		voiceTis.value = '松开 结束';
		recordLength.value = 0;
		recordTimer.value = setInterval(() => {
			recordLength.value++;
		}, 1000)
	};
	// 录音被打断
	const voiceCancel = () => {
		recording.value = false;
		voiceTis.value = '按住 说话';
		recordTis.value = '手指上滑 取消发送'
		willStop.value = true; //不发送录音
		RECORDER.value.stop(); //录音结束
	};
	// 录音中(判断是否触发上滑取消发送)
	const voiceIng = (e) => {
		if (!recording.value) {
			return;
		}
		let touche = e.touches[0];
		//上滑一个导航栏的高度触发上滑取消发送
		if (initPoint.Y - touche.clientY >= uni.upx2px(100)) {
			willStop.value = true;
			recordTis.value = '松开手指 取消发送'
		} else {
			willStop.value = false;
			recordTis.value = '手指上滑 取消发送'
		}
	};
	// 结束录音
	const voiceEnd = (e) => {
		if (!recording.value) {
			return;
		}
		recording.value = false;
		voiceTis.value = '按住 说话';
		recordTis.value = '手指上滑 取消发送'
		//原生录音停止
		// #ifdef H5
		//停止录音

		if (recorder.state != "inactive") {
			recorder.stop();
		}
		//把音频流也停止掉
		stream.getTracks().forEach((track) => track.stop())


		uplodMp3(voicePaths.value);
		// #endif

		// #ifndef H5
		RECORDER.stop(); //录音结束
		// #endif

	};
	//录音结束(回调文件)
	const recordEnd = (e) => {
		clearInterval(recordTimer.value);
		if (!willStop.value) {
			uni.showLoading({
				title: '发送中'
			});

			let msg = {
				length: 0,
				url: e.tempFilePath
			}
			let min = parseInt(recordLength.value / 60);
			let sec = recordLength.value % 60;
			min = min < 10 ? '0' + min : min;
			sec = sec < 10 ? '0' + sec : sec;
			msg.length = min + ':' + sec;

			uni.uploadFile({ // 上传接口
				url: config.APIHOST + '/file/uploads', //真实的接口地址
				filePath: e.tempFilePath,
				name: 'file',
				success: (uploadFileRes) => {
					uni.hideLoading();
					let url = JSON.parse(uploadFileRes.data)
					if (url.code == 0) {
						content.value = url.data.path;
						setChatSave(3, msg.length);
					} else {
						uni.showToast({
							title: url.msg,
							icon: 'none'
						})
					}

				}
			});
		}
		willStop.value = false;
	};
	//上传mp3格式的音频
	const uplodMp3 = (recordPath) => {
		clearInterval(recordTimer.value);
		if (!willStop.value) {
			uni.showLoading({
				title: '录音上传中...'
			})
			let msg = {
				length: 0,
				url: recordPath
			}
			let min = parseInt(recordLength.value / 60);
			let sec = recordLength.value % 60;
			min = min < 10 ? '0' + min : min;
			sec = sec < 10 ? '0' + sec : sec;
			if (recordLength.value % 60 > 1) {
				msg.length = min + ':' + sec;
				setTimeout(function() {
					uni.uploadFile({ // 上传接口
						url: config.APIHOST + '/file/uploads', //真实的接口地址
						file: blobData.value,
						name: 'file',
						success: (uploadFileRes) => {
							uni.hideLoading();
							content.value = JSON.parse(uploadFileRes.data).data.path;
							setChatSave(3, msg.length);

						}
					});
				}, 1000)
			} else {
				uni.showToast({
					title: '语音要大于一秒才可以发送！',
					icon: 'none'
				})
			}
		}
		willStop.value = false;
	};
	//发送图片
	const chooseImage = () => {
		uni.chooseImage({
			count: 1,
			sourceType: ['album'],
			success: res => {
				for (let i = 0; i < res.tempFilePaths.length; i++) {
					uni.showLoading({
						title: '上传中'
					});
					uni.uploadFile({ // 上传接口
						url: config.APIHOST + '/file/uploads', //真实的接口地址
						filePath: res.tempFilePaths[i],
						name: 'file',
						success: (uploadFileRes) => {
							uni.hideLoading();
							let url = JSON.parse(uploadFileRes.data)
							if (url.code == 0) {
								content.value = url.data.path;
								setChatSave(2);
							} else {
								uni.showToast({
									title: url.msg,
									icon: 'none'
								})
							}

						}
					});
				}
			}
		})
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
	const detaChat = (e) => {
		let chatContentId = e.chatContentId
		uni.showModal({
			content: '确认删除',
			confirmColor: 'red',
			confirmText: '删除',
			success: function(res) {
				if (res.confirm) {
					// console.log('用户点击确定');
					let data = {
						chatContentId: chatContentId
					}
					proxy.$http.post("/app/chat/removeChatContentById", data).then(res => {
						if (res.code == 0) {
							uni.showToast({
								title: '删除成功',
								icon: 'none'
							})
							getTimeOrListItem()

						}
					});
				}
			}
		});
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

	.topbox {
		background: #FFFFFF;
		position: fixed;
		top: 100rpx;
		left: 0;
		right: 0;
		z-index: 9;
		padding: 30rpx;
		border-radius: 24rpx;
		margin: 20rpx;
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