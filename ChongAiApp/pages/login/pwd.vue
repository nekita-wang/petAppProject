<template>
	<view class="login-container">
		<view class="status_bar">
			<!-- 状态栏 -->
		</view>
		<view class="custom-navbar">
			<view class="nav-left">
				<uni-icons @click="handleBack" type="arrow-left" size="24"></uni-icons>
			</view>
			<view class="nav-right" @click="ToSMSLogin">
				手机号验证码登录
			</view>
		</view>
		<!-- 手机号输入 -->
		<view class="phone-box">
			<view class="input-group">
				<view class="prefix">+86</view>
				<input v-model="phone" type="number" placeholder="请输入手机号" placeholder-class="placeholder" maxlength="11"
					:class="{ error: showPhoneError }" @input="handlePhoneInput" @focus="showKeyboard = true" />
			</view>

			<!-- 密码输入组 -->
			<view class="input-group-pwd">
				<input v-model="password" :password="!showPassword" placeholder="请输入密码" placeholder-class="placeholder"
					:disable-default-paste="true" />
				<!-- 眼睛图标按钮 -->
				<view class="eye-btn" @click="togglePassword">
					<image :src="showPassword ? '/static/eye.svg' : '/static/eye_close.svg'" class="eye-icon" />
				</view>
			</view>
			<!-- 登录按钮 -->
			<button class="login-btn" :class="{ active: isFormValid }" @click="handleLogin">
				登录
			</button>
			<view class="">
				{{msg}}
			</view>
		</view>
	</view>

</template>

<script setup>
	import {
		ref,
		computed
	} from 'vue'
	const phone = ref('13812345678') //手机号
	const password = ref('') //密码
	const showPhoneError = ref(false) //验证手机号
	const showPassword = ref(false) //密码显示按钮
	const msg = ref('')
	//点击跳转手机密码登录
	const ToSMSLogin = () => {
		uni.navigateTo({
			url: '/pages/login/sms',
		})
	}
	// 返回按钮
	const handleBack = () => {
		uni.navigateBack()
	}
	// 手机号输入处理（自动清理无效字符）
	const handlePhoneInput = (e) => {
		// 移除所有非数字字符
		phone.value = e.detail.value.replace(/[^\d]/g, '')
		let value = e.detail.value
		if (value.length === 1 && value !== '1') {
			phone.value = ''
			return uni.showToast({
				title: '请输入正确的手机号',
				icon: 'none'
			})
		}
		// 实时校验格式
		showPhoneError.value = phone.value.length > 0 && !isPhoneValid.value
	}
	// 手机号验证（11位且1开头）
	const isPhoneValid = computed(() => {
		return /^1[3-9]\d{9}$/.test(phone.value)
	})
	// 切换密码可见状态
	const togglePassword = () => {
		showPassword.value = !showPassword.value
	}
	// 禁止复制粘贴
	const handlePaste = (e) => {
		e.preventDefault()
	}
	// 登录按钮状态
	const isFormValid = computed(() => {
		return phone.value.length === 11 && password.value.length >= 6
	})
	// 点击登录
	const handleLogin = () => {
		uni.request({
			// url: 'http://localhost/dev-api/auth/login',  //本地地址
			url: 'http://192.168.0.224:8080/auth/login', //真机调试地址
			// url:'https://637c-112-48-4-41.ngrok-free.app/auth/login', 
			sslVerify: false, // 真机调试时关闭证书验证（仅开发环境）
			data: {
				grantType: "password", //后端指定类型
				phone: phone.value,
				password: password.value
			},
			method: 'POST',
			header: {
				'Content-Type': 'application/json',
				// 添加必要头信息
				'Accept': 'application/json',
				'ngrok-skip-browser-warning': 'true', // 关键！
			},
			success: (res) => {
				console.log(res.data);
				// 成功跳转
				if (res.data.code == 200) {
					console.log(res.data.data.token);
					uni.setStorageSync('token', res.data.data.token)
					uni.navigateTo({
						url: '/pages/petSelection/petSelection'
					})
				} else if (res.data.code == 1000) {
					uni.showToast({
							title: res.data.msg,
							icon: 'none'
						}),
						// 跳转到注册页面
						uni.navigateTo({
							url: '/pages/register/register'
						})
				} else {
					// 显示其他情况
					uni.showToast({
						title: res.data.msg,
						icon: 'error'
					})
				}
			},
			fail: (err) => {
				msg.value = err.errMsg
				uni.showToast({
					title: err.errMsg,
					icon: 'none'
				})
			}
		});

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

		// 手机号输入
		.phone-box {
			padding: 40rpx;
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

		.input-group-pwd {
			display: flex;
			align-items: center;
			border: 1rpx solid #eee;
			border-radius: 30rpx;
			height: 65rpx;
			background-color: #e8e8e8;

			input {
				width: 100%;
				/* 禁用文本复制 */
				user-select: none;
				-webkit-user-select: none;
				padding-left: 25rpx;
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
	}
</style>