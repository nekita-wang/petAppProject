<template>
	<view class="login-container">
		<!-- 自定义导航栏 -->
		<u-navbar rightText="手机号验证码登录" :autoBack="true" @rightClick="ToSMSLogin" fixed></u-navbar>
		<!-- 手机号输入 -->
		<view class="phone-box">
			<view class="input-group">
				<view class="prefix">+86</view>
				<up-input placeholder="请输入手机号" type='number' shape="circle" clearable v-model="phone"
					maxlength="11"></up-input>
			</view>
			<!-- 密码输入组 -->
			<view class="input-group-pwd">
				<u-input v-model="password" :type="showPassword ? 'text' : 'password'" placeholder="请输入密码"
					:passwordIcon="false" maxlength="10">
					<template #suffix>
						<u-icon :name="showPassword ? 'eye-fill' : 'eye-fill'"
							:color="showPassword ? '#2979ff' : '#c0c4cc'" size="20"
							@click="showPassword = !showPassword" />
					</template>
				</u-input>
			</view>
			<!-- 登录按钮 -->
			<u-button class="login-btn" :disabled="!isFormValid" @click="handleLogin">
				登录
			</u-button>
		</view>
	</view>
</template>

<script setup>
	import {
		ref,
		computed
	} from 'vue'
	import {
		apiGetPwd
	} from '../../api/login'
	import {
		useAuthStore
	} from '@/stores/auth'
	const grantType = ref('password') //后端指定类型
	const phone = ref('') //手机号
	const password = ref('') //密码
	const showPassword = ref(false) //密码显示按钮
	//点击跳转手机验证码登录
	const ToSMSLogin = () => {
		uni.navigateTo({
			url: '/pages/login/sms',
		})
	}
	// 切换密码可见状态
	const togglePassword = () => {
		showPassword.value = !showPassword.value
	}
	// 按钮状态
	const isFormValid = computed(() => {
		return (
			phone.value.length !== 0 &&
			password.value.length !== 0
		)
	})
	// 点击登录
	const handleLogin = async () => {
		const authStore = useAuthStore()
		authStore.setUserInfo({
			phone: phone.value
		})
		try {
			let res = await apiGetPwd(grantType.value, phone.value, password.value)
			if (res.code == 200) {
				// 保存token
				const authStore = useAuthStore()
				authStore.setUserInfo({
					token: res.data.token,
					userId: res.data.userId,
					phone: phone.value
				})
				// 登录成功跳转
				uni.navigateTo({
					url: '/pages/petSelection/petSelection'
				})
			} else if (res.code == 1000) {
				uni.showToast({
					title: res.msg,
					icon: 'none'
				})
				uni.navigateTo({
					url: '/pages/login/register'
				})
			} else {
				// 其他错误情况
				uni.showToast({
					title: res.msg || '登录失败',
					icon: 'none'
				})
			}
		} catch (error) {
			console.error('登录请求失败:', error)
			uni.showToast({
				title: '网络错误，请重试',
				icon: 'none'
			})
		} finally {
			isLoading.value = false
		}
	}
</script>

<style lang="scss" scoped>
	.login-container {
		position: relative;
		height: 100vh;
		padding-top: var(--status-bar-height);

		::v-deep .u-navbar__content__right__text span {
			color: #007aff;
			font-size: 30rpx;
			font-weight: bold;
		}

		.phone-box {
			padding-top: 100rpx;
			/* 导航栏高度 */
			padding-left: 40rpx;
			padding-right: 40rpx;
		}

		.input-group {
			display: flex;
			padding: 30rpx 0;

			.prefix {
				width: 18%;
				height: 65rpx;
				color: #007aff;
				font-size: 32rpx;
				margin-right: 20rpx;
				background-color: #e8e8e8;
				border-radius: 30rpx;
				text-align: center;
				line-height: 65rpx;
				font-weight: bold;
			}

			::v-deep .u-input {
				background-color: #e8e8e8;
			}
		}

		.input-group-pwd {
			display: flex;
			align-items: center;
			border: 1rpx solid #eee;
			border-radius: 30rpx;
			height: 65rpx;
			background-color: #e8e8e8;

			input {
				flex: 1;
				padding-left: 25rpx;
				/* 禁用文本复制 */
				user-select: none;
				-webkit-user-select: none;
			}

			/* 眼睛图标按钮 */
			.eye-btn {
				width: 40rpx;
				height: 40rpx;
				padding: 10rpx;
				margin-right: 20rpx;
			}

			.eye-icon {
				width: 40rpx;
				height: 40rpx;
			}
		}

		.pwdErr-text {
			margin-top: 10rpx;
			color: #ff4d4f;
			font-size: 28rpx;
			text-align: center;
		}

		.login-btn {
			background-color: #007aff;
			color: #fff;
			border-radius: 50rpx;
			height: 90rpx;
			line-height: 90rpx;
			font-size: 32rpx;
			margin-top: 60rpx;
		}
	}
</style>