<template>
	<view class="profile-container">

		<!-- 头像上传 -->
		<view class="avatar-upload" @click="UploadImage">
			<up-image width="100%" height="100%" :src="rgtReactive.avatarUrl" shape="circle"> </up-image>
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
				<up-input v-model="rgtReactive.nickName" placeholder="请输入昵称" shape="circle" maxlength="10"></up-input>
			</view>
			<!-- 性别选择区域 -->
			<view class="form-item">
				<text class="label">性别:</text>
				<view class="gender-container">
					<!-- 男性选项 -->
					<view class="gender-option">
						<view class="sex-male" :class="{ active: rgtReactive.gender === '0' }">
							<image src="/static/nan.svg" class="gender-icon" />
						</view>
						<text class="gender-tag male" :class="{ active: rgtReactive.gender === '0' }"
							@click="rgtReactive.gender = '0'">男</text>
					</view>

					<!-- 女性选项 -->
					<view class="gender-option">
						<view class="sex-female" :class="{ active: rgtReactive.gender === '1' }">
							<image src="/static/nv.svg" class="gender-icon" />
						</view>
						<text class="gender-tag female" :class="{ active: rgtReactive.gender === '1' }"
							@click="rgtReactive.gender = '1'">女</text>
					</view>
				</view>
			</view>

			<!-- 生日-->
			<view class="form-item">
				<text class="label">您的生日:</text>
				<DatePicker v-model="rgtReactive.birthday" @date-change="handleBirthdayChange"
					:default-date="rgtReactive.birthday" />
			</view>

			<!-- 密码输入 -->
			<view class="form-item">
				<text class="label">密码:</text>
				<view class="form-item-pwd">
					<up-input shape="circle" clearable maxlength="10" v-model="rgtReactive.password"
						:type="showPassword ? 'text' : 'password'" placeholder="10位包含大小写字母、数字特殊符号"
						@change="checkPasswordStrength">
						<template #suffix>
							<up-icon :name="showPassword ? 'eye-fill' : 'eye-off'"
								:color="showPassword ? '#2979ff' : '#c0c4cc'" size="25"
								@click="showPassword = !showPassword" />
						</template>
					</up-input>
				</view>
			</view>
			<view class="password-strength">
				<!-- 密码缺失提示 -->
				<view class="missing-tips" v-if="rgtReactive.password && missingRules.length > 0">
					<text>缺少{{ missingRules.join('，') }}</text>
				</view>
			</view>

			<!-- 密码确认 -->
			<view class="form-item">
				<text class="label">密码确认:</text>
				<view class="form-item-pwd">
					<up-input shape="circle" clearable v-model="rgtReactive.passwordConfirm"
						:type="showCmPassword ? 'text' : 'password'" placeholder="请输入确认密码" :passwordIcon="false"
						maxlength="10">
						<template #suffix>
							<up-icon :name="showCmPassword ? 'eye-fill' : 'eye-off'"
								:color="showCmPassword ? '#2979ff' : '#c0c4cc'" size="25"
								@click="showCmPassword = !showCmPassword" />
						</template>
					</up-input>
				</view>

			</view>
		</view>

		<!-- 下一步按钮 -->
		<up-button class="next-btn" :disabled="!isFormValid" @click="handelNext">
			下一步
		</up-button>
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
		ref,
		reactive
	} from 'vue'
	import {
		useAuthStore
	} from '@/stores/auth'
	import {
		getPublicKey,
		encryptWithRSA
	} from '@/utils/rsa'
	import {
		uploadImg
	} from '../../utils/uploadImg'
	import {
		request
	} from '../../utils/request'
	const strengthLevel = ref(0) //密码强度
	const ShowStrenth = ref(false) //显示密码强度
	const relativePath = ref('') //不携带ip的头像地址
	const authStore = useAuthStore() //使用pinia
	const rgtReactive = reactive({
		phone: authStore.phone,
		nickName: '',
		password: '',
		birthday: '2000-06-06',
		gender: '0',
		avatarUrl: '/static/tx.svg',
		passwordConfirm: ''
	})
	const showPassword = ref(false) //密码显示按钮
	const showCmPassword = ref(false) //确认密码显示按钮

	// 按钮状态
	const isFormValid = computed(() =>
		rgtReactive.nickName &&
		rgtReactive.password &&
		rgtReactive.passwordConfirm
	)
	//日期选择器
	const handleBirthdayChange = (date) => {
		rgtReactive.birthday = date
	}
	// 用户是否上传头像
	const hasUploadedAvatar = computed(() => rgtReactive.avatarUrl !== '/static/tx.svg')
	//点击上传图片
	const UploadImage = () => {
		uploadImg((AvatarCallback) => {
			rgtReactive.avatarUrl = AvatarCallback.fullUrl; //带有ip地址的
			relativePath.value = AvatarCallback.relativePath

		});
	};
	onLoad(() => {
		rgtReactive.birthday = '2000-06-06' // 设置初始值
	})
	// 个人完善接口
	const completeProfile = async () => {
		try {
			// 获取密钥
			const publicKey = await getPublicKey()
			const encryptedPwd = encryptWithRSA(publicKey, rgtReactive.password)
			const res = await request({
				url: '/app/user/registerProfile',
				method: 'POST',
				data: {
					...rgtReactive,
					avatarUrl: relativePath.value,
					password: encryptedPwd,
					passwordConfirm: encryptedPwd
				}
			})
			if (!res.success) {
				return uni.showToast({
					title: res.msg || '登录失败',
					icon: 'none'
				});
			}
			uni.showToast({
					title: '注册成功',
					icon: 'success'
				}),
				authStore.setUserInfo({
					token: res.data.token,
					userId: res.data.userId,
					phone: rgtReactive.phone
				})
			uni.navigateTo({
				url: '/pages/petSelection/petSelection'
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
		if (rgtReactive.password !== rgtReactive.passwordConfirm) {
			return uni.showToast({
				title: '两次密码不一致',
				icon: 'none'
			})
		}
		if (!hasUploadedAvatar.value) {
			return uni.showToast({
				title: '请上传头像',
				icon: 'none'
			})
		}
		completeProfile()
	}
	// 密码规则校验
	const hasLength = computed(() => rgtReactive.password.length >= 10)
	const hasLowercase = computed(() => /[a-z]/.test(rgtReactive.password))
	const hasUppercase = computed(() => /[A-Z]/.test(rgtReactive.password))
	const hasNumber = computed(() => /\d/.test(rgtReactive.password))
	const hasSpecialChar = computed(() => /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/.test(rgtReactive.password))
	// 缺失规则提示
	const missingRules = computed(() => {
		const rules = []
		// if (!hasLength.value) rules.push('长度不足10位')
		if (!hasNumber.value) rules.push('数字')
		if (!hasUppercase.value) rules.push('大写字母')
		if (!hasLowercase.value) rules.push('小写字母')
		if (!hasSpecialChar.value) rules.push('特殊符号')
		return rules
	})
	// 密码强度校验
	const checkPasswordStrength = debounce((e) => {
		rgtReactive.password = e.replace(/[^a-zA-Z0-9!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/g, '')
		const pwd = rgtReactive.password
		if (!pwd) {
			ShowStrenth.value = false
			return
		}
		ShowStrenth.value = true
	}, 300)
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

	//密码校验
	.password-strength {
		margin-left: 160rpx;
		display: flex;
		align-items: center;

	}

	/* 缺失提示样式 */
	.missing-tips {
		font-size: 24rpx;
		font-weight: bold;
		color: #ff4d4f;
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