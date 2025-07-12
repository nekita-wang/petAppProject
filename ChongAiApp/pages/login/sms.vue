<template>
	<view class="login-container">
		<!-- 自定义导航栏 -->
		<u-navbar rightText="手机号密码登录" :autoBack="true" @rightClick="ToPasswordLogin" fixed></u-navbar>
		<!-- 手机号输入 -->
		<view class="phone-box">
			<view class="input-group">
				<view class="prefix">+86</view>
				<up-input placeholder="请输入手机号" type='number' shape="circle" clearable v-model="phone"
					maxlength="11"></up-input>
			</view>
			<!-- 验证码输入组 -->
			<view class="input-group-code">
				<!-- 输入框 -->
				<u-input v-model="code" type="number" placeholder="输入验证码" placeholder-class="placeholder"
					maxlength="6" />
				<!-- 分隔线 -->
				<view class="divider"></view>
				<!-- 获取验证码按钮 -->
				<view class="get-code-btn" @click="getSMSCode">
					{{ countdown > 0 ? `${countdown}s` : '获取验证码' }}
				</view>
			</view>
			<!-- 登录按钮 -->
			<u-button class="login-btn" :disabled="!isFormValid" @click="handleLogin">
				登录
			</u-button>
			<!-- 底部提示 -->
			<view class="tip">未注册时，登录将自动注册</view>
		</view>
	</view>
</template>

<script setup>
	import {
		ref,
		computed,
		onDeactivated
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
	const countdown = ref(0) //验证码倒计时

	//点击跳转手机密码登录
	const ToPasswordLogin = () => {
		uni.navigateTo({
			url: '/pages/login/pwd',
		})
	}
	// 按钮状态
	const isFormValid = computed(() => {
		return (
			phone.value.length !== 0 &&
			code.value.length !== 0
		)
	})
	// 页面卸载时清除计时器
	onDeactivated(() => {
		countdown.value = 0
	})
	// 获取验证码
	const getSMSCode = async () => {
		if (countdown.value > 0) return

		try {
			const res = await apiGetCode(phone.value)
			if (!res.success) return uni.showToast({
				title: res.msg,
				icon: 'none'
			})

			uni.showToast({
				title: '验证码已发送!',
				icon: 'success'
			})
			code.value = res.data
			countdown.value = 60

			const timer = setInterval(() =>
				(countdown.value <= 0) ? clearInterval(timer) : countdown.value--, 1000)

		} catch (error) {
			console.error('获取验证码失败', error)
			uni.showToast({
				title: '获取验证码失败，请重试',
				icon: 'none'
			})
		}
	}
	// 点击登录
	const handleLogin = async () => {
	  try {
	    const res = await apiGetPwd(grantType.value, phone.value, null,code.value)
	    if (!res.success) return uni.showToast({ title: res.msg, icon: 'none' })
	    
	    const authStore = useAuthStore()
	    authStore.setUserInfo({
	      token: res.data.token,
	      userId: res.data.userId,
	      phone: phone.value
	    })
	    
	    uni.navigateTo({
	      url: res.data.newUser ? '/pages/login/register' : '/pages/petSelection/petSelection'
	    })
	    
	  } catch {
	    uni.showToast({ title: '登录失败，请重试', icon: 'none' })
	  }
	}
</script>

<style lang="scss" scoped>
	.login-container {
		position: relative;
		height: 100vh;
		padding-top: var(--status-bar-height);
		}

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

			::v-deep .u-input {
				background-color: #e8e8e8;
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

		.tip {
			color: #999;
			font-size: 24rpx;
			text-align: center;
			margin-top: 30rpx;
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
	
</style>