import {
	useAuthStore
} from "../stores/auth";

export function uploadImg(AvatarCallback) {
	const URL = 'https://122.228.237.118:53627';
	const authStore = useAuthStore();
	const token = authStore.token;

	uni.chooseImage({
		count: 1,
		success: async (chooseImageRes) => {
			try {
				const uploadRes = await uni.uploadFile({
					url: `${URL}/public/app/upload/avatar`,
					filePath: chooseImageRes.tempFilePaths[0],
					name: 'avatarfile',
					formData: {
						user: 'test'
					},
					header: {
						Authorization: `Bearer ${token}`
					}
				});
				const response = JSON.parse(uploadRes.data);
				AvatarCallback?.({
					relativePath: response.imgUrl,
					fullUrl: URL + response.imgUrl
				})
				uni.showToast({
					title: '上传成功',
					icon: 'success'
				});
			} catch (error) {
				console.error('上传处理异常:', error);
				uni.showToast({
					title: error.message || '上传失败',
					icon: 'none'
				});
			}
		},
		fail: (error) => {
			console.error('选择图片失败:', error);
			uni.showToast({
				title: '选择图片失败',
				icon: 'none'
			});
		}
	});
}