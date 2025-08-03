<template>
	<view class="login-container">
		<!-- 自定义导航栏 -->
		<up-navbar rightText="手机号密码登录" :autoBack="false"  @leftClick="customBack" @rightClick="ToPasswordLogin" fixed></up-navbar>
		<!-- 手机号输入 -->
		<view class="phone-box">
			<view class="input-group">
				<view class="prefix">+86</view>
				<up-input placeholder="请输入手机号" focus type='number' shape="circle" clearable v-model="smsReactive.phone" 
					maxlength="11"></up-input>
			</view>
			<!-- 验证码输入组 -->
			<view class="input-group-code">
				<!-- 输入框 -->
				<u-input v-model="smsReactive.code" type="number" placeholder="输入验证码" placeholder-class="placeholder"
					maxlength="6" />
				<!-- 分隔线 -->
				<view class="divider"></view>
				<!-- 获取验证码按钮 -->
				<view v-if="smsReactive.phone" class="get-code-btn" @click="getSMSCode">
					{{ countdown > 0 ? `请${countdown}秒后重新获取` : '获取验证码' }}
				</view>
			</view>
			<!-- 登录按钮 -->
			<up-button class="login-btn" :disabled="!isFormValid" @click="handleLogin">
				登录
			</up-button>
			<!-- 底部提示 -->
			<view class="tip">未注册时，登录将自动注册</view>
		</view>
	</view>
</template>

<script setup lang="ts">
	import {
		ref,
		computed,
		onMounted,
		onDeactivated,
		reactive
	} from 'vue'
	import { useUserStore } from '@/stores/user'
	import { request } from '@/utils/request'
		const userStore = useUserStore()
	// 定义SMS登录表单类型
	interface SMSForm {
		grantType: string
		phone: string
		code: string
	}
	
	// 定义登录返回的数据类型
	interface LoginResponseData {
		userId: number
		token: string
		expire: number
		needPetInfo: boolean
	}
	
	//验证码登录
	const smsReactive = reactive<SMSForm>({
		grantType: 'phone',
		phone: '',
		code: ''
	})
	
	const showPhoneError = ref<boolean>(false)
	const countdown = ref<number>(0)
	
	// 返回
	const customBack = (): void => {
		uni.redirectTo({
			url: '/pages/login/login'
		})
	}
	
	// 点击跳转手机密码登录
	const ToPasswordLogin = (): void => {
		uni.navigateTo({
			url: '/pages/login/pwd'
		})
	}

	// 按钮状态
	const isFormValid = computed<boolean>(() => Boolean(smsReactive.phone && smsReactive.code))
	
	// 获取验证码
	const getSMSCode = async (): Promise<void> => {
		if (!smsReactive.phone || countdown.value > 0) return
		try {
			const res = await request<Response<string>>({ 
				url: '/app/auth/sendCode',
				method: 'POST',
				data: {
					'phone': smsReactive.phone
				},
				header: {}
			})
			
			if (!res?.success) {
				return uni.showToast({
					title: res?.msg || '发送失败',
					icon: 'none'
				})
			}

			uni.showToast({
				title: '验证码已发送!',
				icon: 'success'
			})
			smsReactive.code = String(res.data)
			countdown.value = 60

			const timer = setInterval(() => {
				if (countdown.value <= 0) {
					clearInterval(timer)
				} else {
					countdown.value--
				}
			}, 1000)
		} catch (err) {
			console.log(err);
			uni.showToast({
				title: '获取验证码失败，请重试',
				icon: 'none'
			})
		}
	}

	// 点击登录
	const handleLogin = async (): Promise<void> => {
		try {
			const res = await request<LoginResponseData>({
				url: '/app/auth/login',
				method: 'POST',
				data: {
					...smsReactive
				},
				header: {}
			})
			userStore.setUserInfo({
				token: res.data.token , 
				userId: String(res.data.userId),
				phone: smsReactive.phone
			})
			uni.navigateTo({
				url: res.data.needPetInfo ?  
					'/pages/petSelection/petSelection' : '/pages/home/home'
			})
			
		} catch (err) {
			console.log(err);
			// 未注册
			if ((err as any).code === 1000) {
				uni.showToast({
					title: (err as any).msg,
					icon: 'none'
				})
				userStore.setUserInfo({
					phone: smsReactive.phone
				})
				uni.navigateTo({
					url: '/pages/login/register'
				})
				return
			}
		}
	}
</script>

<style lang="scss" scoped>
	.login-container {
		position: relative;
		// height: 100vh;
		padding-top: var(--status-bar-height);
	}

	::v-deep .u-navbar__content__right__text span {
		color: #007aff;
		font-size: 30rpx;
		font-weight: bold;
	}

	.phone-box {
		padding-top: 80rpx;
		/* 导航栏高度 */
		padding-left: 40rpx;
		padding-right: 40rpx;
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
			text-align: center;
			color: #007aff;
			padding: 0 30rpx;
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