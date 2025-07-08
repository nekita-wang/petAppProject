<template>
	<view class="login-container">
		<view class="status_bar">
			<!-- 这里是状态栏 -->
		</view>
		<!-- 自定义导航栏 -->
		<view class="custom-navbar">
			<view class="nav-left">
				<uni-icons @click="handleBack" type="arrow-left" size="24"></uni-icons>
			</view>
			<view class="nav-right" @click="ToPasswordLogin">
				手机号密码登录
			</view>
		</view>
		<!-- 手机号输入 -->
		<view class="phone-box">
			<view class="input-group">
				<view class="prefix">+86</view>
				<input v-model="phone" type="number" placeholder="请输入手机号" placeholder-class="placeholder" maxlength="11"
					:class="{ error: inputClass }" @input="handlePhoneInput" @focus="showKeyboard = true" />
			</view>
			
			<!-- 验证码输入组 -->
			<view class="input-group-code">
				<!-- 输入框 -->
				<input v-model="code" type="number" placeholder="输入验证码" placeholder-class="placeholder" maxlength="6" />

				<!-- 分隔线 -->
				<view v-if="showPhoneError" class="divider"></view>

				<!-- 获取验证码按钮 -->
				<view v-if="showPhoneError" class="get-code-btn" @click="getSMSCode">
					{{ countdown > 0 ? `${countdown}s` : '获取验证码' }}
				</view>
			</view>

			<!-- 登录按钮 -->
			<button class="login-btn" :class="{ active: isFormValid }" @click="handleLogin">
				登录
			</button>

			<!-- 底部提示 -->
			<view class="tip">未注册时，登录将自动注册</view>
		</view>

	</view>
</template>

<script setup>
	import {
		ref,
		computed
	} from 'vue'
	import {
		apiGetCode,
		apiGetPwd
	} from '../../api/login'
	import {
		useAuthStore
	} from '@/stores/auth'
	const grantType = ref('phone') //后端指定类型
	const phone = ref('') //手机号
	const code = ref('') //验证码
	const showPhoneError = ref(false) //手机号格式
	const inputClass = ref(false)
	const showKeyboard = ref(false) //显示键盘
	const countdown = ref(0) //验证码倒计时
	//点击跳转手机密码登录
	const ToPasswordLogin = () => {
		uni.navigateTo({
			url: '/pages/login/pwd',
		})
	}
	// 返回按钮
	const handleBack = () => {
		uni.navigateBack()
	}
	// 保证手机号1开头
	const isPhoneValid = computed(() => {
		return /^1[3-9]\d{9}$/.test(phone.value)
	})
	// 手机号输入处理
	const handlePhoneInput = (e) => {
		// 移除所有非数字字符
		phone.value = e.detail.value.replace(/[^\d]/g, '')
		// 实时校验格式
		showPhoneError.value = isPhoneValid.value
		// input格式
		inputClass.value = !isPhoneValid.value
	}
	// 登录按钮状态
	const isFormValid = computed(() => {
		return phone.value.length === 11 && code.value.length >= 4
	})

	// 获取验证码
	const getSMSCode = async () => {
		if (countdown.value > 0) return
		let res = await apiGetCode(phone.value)
		if (res.success == true) {
			// 提示发送手机验证码成功
			uni.showToast({
					title: '验证码已发送,注意短信通知',
					icon: 'success'
				}),
				// 开启验证码倒计时
				countdown.value = 60
			const timer = setInterval(() => {
				if (countdown.value <= 0) {
					clearInterval(timer)
					return
				}
				countdown.value--
			}, 1000)
			console.log('验证码：', res.data);
			code.value = res.data
		} else {
			// 提示验证码接口错误消息
			uni.showToast({
				title: res.msg,
				icon: 'error'
			})
		}
	}
	//点击登录
	const handleLogin = async () => {
		let res = await apiGetPwd(grantType.value, phone.value, null, code.value)
		if (res.success == true) {
			// 保存token
			const authStore = useAuthStore()
			authStore.setUserInfo({
				token: res.data.token,
				userId: res.data.userId, // 确保后端返回userId
				phone: phone.value // 使用前端输入或后端返回的phone
			})
			// 判断是否是新用户
			if (res.data.newUser = true) {
				uni.navigateTo({
					url: '/pages/login/register'
				})
			} else {
				//跳转到您养的宠物
				uni.navigateTo({
					url: '/pages/petSelection/petSelection'
				})
			}

		} else {
			// 显示错误消息
			console.log(res);
			uni.showToast({
				title: res.msg,
				icon: 'error'
			})
		}
	}
</script>

<style lang="scss" scoped>
	.login-container {
		.status_bar {
			height: var(--status-bar-height);
			width: 100%;
		}

		background-color: #fff;
		height: 100vh;
		box-sizing: border-box;

		.custom-navbar {
			padding: 20rpx;
			height: 44rpx;
			display: flex;
			justify-content: space-between;
			align-items: center;
			position: relative;
			z-index: 100;
		}

		.back-icon {
			width: 48rpx;
			height: 48rpx;
		}

		.nav-right {
			color: #007aff;
			font-size: 30rpx;
			font-weight: bold;
		}

		.phone-box {
			padding: 40rpx;
		}

		.login-method {
			color: #007aff;
			font-size: 32rpx;
			text-align: right;
			margin-bottom: 80rpx;
		}

		.input-group {
			display: flex;
			padding: 30rpx 0;
			// 手机号验证不通过 样式

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

			input {
				width: 82%;
				height: 65rpx;
				font-size: 32rpx;
				padding-left: 25rpx;
				border-radius: 30rpx;
				background-color: #e8e8e8;

				&.error {
					border: 1px solid #ff4d4f !important;
				}

			}


		}

		.input-group-code {
			display: flex;
			align-items: center;
			border: 1rpx solid #eee;
			border-radius: 30rpx;
			height: 65rpx;
			background-color: #e8e8e8;

			input {
				width: 70%;
				height: 65rpx;
				font-size: 32rpx;
				padding-left: 25rpx;
				border-radius: 30rpx 0 0 30rpx;

			}

			.divider {
				width: 3rpx;
				height: 40rpx;
				background-color: #eee;
			}

			.get-code-btn {
				width: 30%;
				text-align: center;
				color: #007aff;

				font-size: 28rpx;
				font-weight: bold;
				white-space: nowrap;
			}
		}


		.login-btn {
			background-color: #f5f5f5;
			color: #fff;
			border-radius: 50rpx;
			height: 90rpx;
			line-height: 90rpx;
			font-size: 32rpx;
			margin-top: 60rpx;

			&.active {
				background-color: #007aff;
			}
		}

		.tip {
			color: #999;
			font-size: 24rpx;
			text-align: center;
			margin-top: 30rpx;
		}

	}
</style>