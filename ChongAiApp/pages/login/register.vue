<template>
	<view class="profile-container">

		<!-- 头像上传 -->
		<view class="avatar-upload" @click="UploadImage">
			<up-image :src=avatar width="80px" height="80px" shape="circle"> </up-image>
		</view>
		<!-- 表单区域 -->
		<view class="form-group">
			<!-- 手机号-->
			<view class="form-item">
				<text class="label">手机号:</text>
				<up-input v-model="authStore.phone" disabled placeholder="请输入手机号" shape="circle"></up-input>
			</view>

			<!-- 昵称 -->
			<view class="form-item">
				<text class="label">昵称:</text>
				<up-input v-model="nickname" placeholder="请输入" shape="circle" maxlength="10"></up-input>
			</view>
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
				<view class="form-item-pwd">
					<u-input shape="circle" v-model="password" :type="showPassword ? 'text' : 'password'"
						placeholder="请输入" :passwordIcon="false" maxlength="10" @change="checkPasswordStrength">
						<template #suffix>
							<u-icon :name="showPassword ? 'eye-fill' : 'eye-off'"
								:color="showPassword ? '#2979ff' : '#c0c4cc'" size="25"
								@click="showPassword = !showPassword" />
						</template>
					</u-input>
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
					<u-input shape="circle" v-model="confirmPassword" :type="showCmPassword ? 'text' : 'password'"
						placeholder="请输入" :passwordIcon="false" maxlength="10">
						<template #suffix>
							<u-icon :name="showCmPassword ? 'eye-fill' : 'eye-off'"
								:color="showCmPassword ? '#2979ff' : '#c0c4cc'" size="25"
								@click="showCmPassword = !showCmPassword" />
						</template>
					</u-input>
				</view>

			</view>
		</view>

		<!-- 下一步按钮 -->
		<u-button class="next-btn" :disabled="!isFormValid" @click="handelNext">
			下一步
		</u-button>
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
	const gender = ref('0') //性别
	const birthday = ref('2024-6-6') //生日
	const showPassword = ref(false) //密码显示按钮
	const showCmPassword = ref(false) //确认密码显示按钮

	// 按钮状态
	const isFormValid = computed(() => {
		return (
			nickname.value.trim() !== '' &&
			gender.value !== '' &&
			password.value.length !== 0 && // 密码长度要求 
			confirmPassword.value.length !== 0
		)
	})
	//日期选择器
	const handleBirthdayChange = (date) => {
		birthday.value = date
	}
	// 完善个人资料接口
	const completeProfile = async () => {
		try {
			// 获取密钥
			const publicKey = await getPublicKey()
			const encryptedPwd = encryptWithRSA(publicKey, password.value)
			const res = await apiCompleteProfile({
				userId: authStore.userId,
				nickname: nickname.value,
				password: encryptedPwd,
				avatar: avatar.value,
				gender: gender.value,
				birthday: birthday.value
			})

			res.code === 200 ?
				(uni.showToast({
						title: '注册成功',
						icon: 'success'
					}),
					uni.navigateTo({
						url: '/pages/petSelection/petSelection'
					})) :
				uni.showToast({
					title: res.msg,
					icon: 'none'
				})

		} catch (error) {
			const errorMsg = {
				'getPublicKey': '获取加密密钥失败',
				'encryptWithRSA': '密码加密失败'
			} [error.type] || '操作失败'

			uni.showToast({
				title: errorMsg,
				icon: 'none'
			})
		}
	}
	const handelNext = () => {
		if (password.value !== confirmPassword.value) {
			return uni.showToast({
				title: '两次密码不一致',
				icon: 'none'
			})
		}
		if (strengthLevel.value <= 1) {
			return uni.showToast({
				title: '密码较弱请重新设置',
				icon: 'none'
			})
		}
		completeProfile()
	}
	// 密码强度文本
	const strengthText = computed(() => {
		if (strengthLevel.value === 0) return '弱'
		if (strengthLevel.value === 1) return '弱'
		if (strengthLevel.value === 2) return '中'
		return '强'
	})
	//输入框判断密码强度
	const checkPasswordStrength = debounce((e) => {
		password.value = e.replace(/[^a-zA-Z0-9!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/g, '')
		const pass = password.value

		ShowStrenth.value = pass.length > 0

		if (!pass || pass.length < 8) {
			strengthLevel.value = 0
			return
		}

		const score = [
			pass.length >= 2,
			pass.length >= 5,
			/[a-z]/.test(pass),
			/[A-Z]/.test(pass),
			/\d/.test(pass),
			/[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/.test(pass),
			/[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/.test(pass)
		].filter(Boolean).length

		strengthLevel.value = score <= 3 ? 1 : score <= 6 ? 2 : 3
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
		width: 160rpx;
		height: 160rpx;
		margin: 0 auto;
	}

	::v-deep .u-input {
		background-color: #e8e8e8;
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
		background-color: #007aff;
		color: #fff;
		border-radius: 50rpx;
		height: 90rpx;
		line-height: 90rpx;
		font-size: 32rpx;
		margin-top: 60rpx;
		width: 300rpx;
	}
</style>