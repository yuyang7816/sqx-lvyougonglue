export const getWxAppid = () => {
	return '';
};
export const getInvitation = () => {
	return uni.getStorageSync('publicRelation');
};
export const getAppDownUrl = () => {
	return uni.getStorageSync('appurl');
};
// 开源默认本机域名；分享/表情等相对资源以此为准
export const publicYuMing = () => {
	return 'http://127.0.0.1:9097';
};

export const getChatSearchKeys = (key) => {
	let list = uni.getStorageSync("chatSearchKeys");
	console.log(key)
	let keys = key.replace(/\s*/g, "")
	let over = false
	for (var i = 0; i < keys.length; i++) {
		if (list.indexOf(keys[i]) != -1) {
			over = true;
		}

	}
	return over;
};
