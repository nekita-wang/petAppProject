<template>
	<view class="profile-container">

		<!-- 头像上传 -->
		<view class="avatar-upload" @click="UploadImage">
			<image :src=avatar class="avatar" />
			<!-- <image src="/static/camera-icon.svg" class="camera-icon" /> -->
		</view>

		<!-- 表单区域 -->
		<view class="form-group">
			<!-- 手机号-->
			<view class="form-item">
				<text class="label">手机号:</text>
				<input v-model="authStore.phone" placeholder="请输入" maxlength="11" disabled />
			</view>

			<!-- 昵称 -->
			<view class="form-item">
				<text class="label">昵称:</text>
				<input v-model="nickname" placeholder="请输入" maxlength="10" />
			</view>
			<!-- 性别 -->
			<!-- 性别选择区域 -->
			<view class="form-item">
				<text class="label">性别:</text>
				<view class="gender-container">
					<!-- 男性选项 -->
					<view class="gender-option">
						<view class="sex-male" :class="{ active: gender === '0' }">
							<image src="/static/nan.svg" class="gender-icon" />
						</view>
						<text class="gender-tag male" :class="{ active: gender === '0' }" @click="gender = '0'">男</text>
					</view>

					<!-- 女性选项 -->
					<view class="gender-option">
						<view class="sex-female" :class="{ active: gender === '1' }">
							<image src="/static/nv.svg" class="gender-icon" />
						</view>
						<text class="gender-tag female" :class="{ active: gender === '1' }"
							@click="gender = '1'">女</text>
					</view>
				</view>
			</view>

			<!-- 生日-->
			<view class="form-item">
				<text class="label">您的生日:</text>
				<DatePicker @date-change="handleBirthdayChange" />
			</view>

			<!-- 密码 -->
			<view class="form-item">
				<text class="label">密码:</text>
				<view class="form-item-pwd"> <input v-model="password" style="ime-mode:disabled;"
						:password="!showPassword" placeholder="请输入" @input="checkPasswordStrength" maxlength="10" />
					<view class="eye-btn" @click="togglePassword">
						<image :src="showPassword ? '/static/eye.svg' : '/static/eye_close.svg'" class="eye-icon" />
					</view>
				</view>


			</view>
			<!-- 密码强度 -->
			<view class="password-strength" v-if="ShowStrenth">
				<view class="strength-bar" :class="{
							'weak': strengthLevel === 1,
							'medium': strengthLevel === 2,
							'strong': strengthLevel === 3,
							'active': strengthLevel >= 0
						}"></view>
				<view class="strength-bar" :class="{
							'medium': strengthLevel === 2,
							'strong': strengthLevel === 3,
							'active': strengthLevel >= 2
						}"></view>
				<view class="strength-bar" :class="{
							'strong': strengthLevel === 3,
							'active': strengthLevel >= 3
						}"></view>
				<text class="strength-text">{{ strengthText }}</text>
			</view>
			<!-- 密码确认 -->
			<view class="form-item">
				<text class="label">密码确认:</text>
				<view class="form-item-pwd">
					<input v-model="confirmPassword" :password="!showCmPassword" placeholder="请输入" maxlength="10" />
					<view class="eye-btn" @click="toggleCmPassword">
						<image :src="showCmPassword ? '/static/eye.svg' : '/static/eye_close.svg'" class="eye-icon" />
					</view>
				</view>

			</view>
		</view>

		<!-- 下一步按钮 -->
		<button class="next-btn" @click="handelNext" :disabled="!isFormValid">下一步</button>
	</view>
</template>

<script setup>
	import {
		onLoad
	} from '@dcloudio/uni-app'
	import {
		debounce
	} from 'lodash-es'
	import DatePicker from '@/components/DatePicker.vue'
	import {
		onMounted,
		computed,
		ref
	} from 'vue'
	import {
		useAuthStore
	} from '@/stores/auth'
	import {
		apiCompleteProfile
	} from '../../api/login'
	import {
		getPublicKey,
		encryptWithRSA
	} from '@/utils/rsa'
	const strengthLevel = ref(0) //密码强度
	const ShowStrenth = ref(false) //显示密码强度
	const authStore = useAuthStore() //使用pinia
	const avatar = ref('/static/touxiang.svg')
	const phone = ref('') //手机号
	const nickname = ref('') //昵称
	const password = ref('') //密码
	const confirmPassword = ref('') //确认密码
	const gender = ref('') //性别
	const birthday = ref('2024-6-6') //生日
	const showPassword = ref(false) //密码显示按钮
	const showCmPassword = ref(false) //确认密码显示按钮
	// 切换密码可见状态
	const togglePassword = () => {
		showPassword.value = !showPassword.value
	}
	// 切换确认密码可见状态
	const toggleCmPassword = () => {
		showCmPassword.value = !showCmPassword.value
	}
	// 按钮状态
	const isFormValid = computed(() => {
		return (
			nickname.value.trim() !== '' &&
			gender.value !== '' &&
			password.value.length !== 0 && // 密码长度要求 、
			confirmPassword.value.length !== 0
		)
	})
	//日期选择器
	const handleBirthdayChange = (date) => {
		birthday.value = date
	}
	// 点击下一步
	const handelNext = async () => {
		console.log(birthday);
		if (password.value !== confirmPassword.value) {
			uni.showToast({
				title: '两次密码不一致',
				icon: 'none'
			})
			return
		}
		if (strengthLevel.value <= 1) {
			uni.showToast({
				title: '密码较弱请重新设置',
				icon: 'none'
			})
			return
		}
		// 1. 获取RSA公钥
		let publicKey
		try {
			publicKey = await getPublicKey()
		} catch (error) {
			uni.showToast({
				title: '获取加密密钥失败',
				icon: 'none'
			})
			return
		}
		// 2. 加密密码
		let encryptedPwd
		try {
			encryptedPwd = encryptWithRSA(publicKey, password.value)
		} catch (error) {
			uni.showToast({
				title: '密码加密失败',
				icon: 'none'
			})
			return
		}
		// 调用注册接口接口
		const res = await apiCompleteProfile({
			userId: authStore.userId, // 从store获取用户ID
			nickname: nickname.value,
			password: encryptedPwd,
			avatar: avatar.value,
			gender: gender.value,
			birthday: birthday.value
		})
		if (res.code === 200) {
			uni.showToast({
				title: '注册成功',
				icon: 'success'
			})
			uni.navigateTo({
				url: '/pages/petSelection/petSelection'
			})
		} else {
			uni.showToast({
				title: res.msg,
				icon: 'none'
			})
		}
	}
	// 密码强度文本
	const strengthText = computed(() => {
		if (strengthLevel.value === 0) return '弱'
		if (strengthLevel.value === 1) return '弱'
		if (strengthLevel.value === 2) return '中'
		return '强'
	})
	//输入框判断密码强度（防抖判断输入中文）
	const checkPasswordStrength = debounce((e) => {
		const replaceValue = e.detail.value
		password.value = replaceValue.replace(/[^a-zA-Z0-9!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/g, '')
		const pass = password.value
		if (pass.length > 0) {
			ShowStrenth.value = true
		} else {
			ShowStrenth.value = false
		}
		if (!pass || pass.length < 8) {
			strengthLevel.value = 0
			return
		}
		//密码登记判定
		let score = 0
		if (pass.length >= 2) score += 1
		if (pass.length >= 5) score += 1
		if (/[a-z]/.test(pass)) score += 1
		if (/[A-Z]/.test(pass)) score += 1
		if (/\d/.test(pass)) score += 1
		if (/[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/.test(pass)) score += 2

		if (score <= 1) strengthLevel.value = 1
		if (score <= 3) strengthLevel.value = 1
		else if (score <= 6) strengthLevel.value = 2
		else strengthLevel.value = 3
	}, 300)
	// 上传图片
	const UploadImage = () => {
		uni.chooseImage({
			count: 1,
			success(res) {
				avatar.value = res.tempFilePaths[0]
			}
		})
	}
	onLoad(() => {
		function formatDate(date) {
			return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`;
		}
		birthday.value = formatDate(new Date())
	})
</script>

<style scoped lang="scss">
	.profile-container {
		padding: 0 40rpx 0 20rpx;
		background-color: #fff;
	}


	/* 头像上传 */
	.avatar-upload {
		position: relative;
		width: 160rpx;
		height: 160rpx;
		margin: 0 auto;
	}

	.avatar {
		width: 100%;
		height: 100%;
		border-radius: 10%;
	}

	.camera-icon {
		position: absolute;
		right: 0;
		bottom: 0;
		width: 48rpx;
		height: 48rpx;
	}

	/* 表单样式 */
	.form-item {
		padding: 20rpx 0;
		display: flex;
		align-items: center;
	}

	.label {
		text-align: center;
		width: 180rpx;
		font-size: 32rpx;
		color: #333;
	}

	input {
		flex: 1;
		font-size: 32rpx;
		height: 65rpx;
		padding-left: 25rpx;
		border-radius: 30rpx;
		background-color: #e8e8e8;
	}

	.error-text {
		color: #ff4d4f;
		font-size: 24rpx;
		margin-left: 20rpx;
	}

	.value {
		flex: 1;
		font-size: 32rpx;
	}

	/* 性别选择容器 */
	.gender-container {
		flex: 1;
		display: flex;
		gap: 40rpx;
		/* 选项间距 */
	}

	/* 单个性别选项 */
	.gender-option {
		display: flex;
		align-items: center;

		.sex-male {
			display: flex;
			justify-content: center;
			align-items: center;
			width: 50rpx;
			height: 50rpx;
			background-color: #0091ff;
			margin-right: 20rpx;
			border-radius: 50%;
		}

		.sex-female {
			display: flex;
			justify-content: center;
			align-items: center;
			width: 50rpx;
			height: 50rpx;
			margin-right: 20rpx;
			background-color: #ff4d94;
			border-radius: 50%;
		}

		image {
			width: 70%;
			height: 70%;


		}
	}


	/* 文字标签 */
	.gender-tag {
		padding: 8rpx 50rpx;
		border-radius: 30rpx;
		background-color: #f5f5f5;
		font-size: 32rpx;
	}

	/* 性别标签激活状态 */
	.gender-tag.active.male {
		background-color: #1989fa !important;
		color: white !important;
	}

	.gender-tag.active.female {
		background-color: #ff7bac !important;
		color: white !important;
	}

	/* 日期显示容器（完全还原截图样式） */
	.date-display {
		flex: 1;
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 0 20rpx;
		height: 65rpx;
		background-color: #e8e8e8;
		border-radius: 30rpx;
		font-size: 32rpx;
	}

	.arrow-icon {
		width: 32rpx;
		height: 32rpx;
	}

	.label {
		width: 180rpx;
		font-size: 32rpx;
		color: #333;
	}

	/* 日期选择器容器 */
	.date-picker-container {
		flex: 1;
		height: 300rpx;
		margin-top: 20rpx;
		background-color: #f8f8f8;
		border-radius: 20rpx;
		overflow: hidden;
	}

	/* 选择器样式 */
	picker-view {
		width: 100%;
		height: 100%;
	}

	/* 选择项样式 */
	.item {
		line-height: 80rpx;
		text-align: center;
		font-size: 32rpx;
		color: #666;
	}

	/* 选中项样式 */
	picker-view-column view.item {
		font-weight: bold;
		color: black;
	}

	.form-item-pwd {
		width: 100%;
		display: flex;
		align-items: center;
		border-radius: 30rpx;
		background-color: #e8e8e8;
	}

	/* 眼睛图标按钮 */
	.eye-btn {
		width: 40rpx;
		height: 40rpx;
		// background-color: white;
		padding: 10rpx;
		margin-right: 20rpx;
	}

	.eye-icon {
		width: 40rpx;
		height: 40rpx;
	}

	// 密码错误提示
	.error-mimatext {
		color: black;
		font-size: 30rpx;
		text-align: center;
	}

	//密码强度
	.password-strength {
		margin-left: 160rpx;
		display: flex;
		align-items: center;

	}

	.strength-bar {
		height: 6rpx;
		flex: 1;
		margin-right: 4rpx;
		background-color: #eee;
		border-radius: 3rpx;
		transition: all 0.3s;
	}

	.strength-bar.active {
		background-color: #ff4d4f;
	}

	.strength-bar.weak {
		background-color: #ff4d4f;
	}

	.strength-bar.medium {
		background-color: #faad14;
	}

	.strength-bar.strong {
		background-color: #52c41a;
	}

	.strength-text {
		margin-left: 20rpx;
		font-size: 24rpx;
		color: #666;
	}

	/* 下一步按钮 */
	.next-btn {
		width: 300rpx;
		background-color: #007aff;
		color: white;
		border-radius: 50rpx;
		height: 70rpx;
		margin-top: 60rpx;
		line-height: 70rpx;

		&.active {
			background-color: #007aff;
		}
	}
</style>