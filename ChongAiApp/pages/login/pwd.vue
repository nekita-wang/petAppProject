<template>
	<view class="login-container">
		<!-- 自定义导航栏 -->
		<up-navbar  rightText="手机号验证码登录" @leftClick="customBack" @rightClick="ToSMSLogin" fixed></up-navbar>
		<!-- 手机号输入 -->
		<view class="phone-box">
			<view class="input-group">
				<view class="prefix">+86</view>
				<up-input placeholder="请输入手机号" focus type='number' shape="circle" clearable v-model="pwdReactive.phone"
					maxlength="11"></up-input>
			</view>
			<!-- 密码输入组 -->
			<view class="input-group-pwd">
				<up-input v-model="pwdReactive.password" :type="showPassword ? 'text' : 'password'" placeholder="请输入密码"
					:passwordIcon="false" maxlength="10">
					<template #suffix>
						<up-icon :name="showPassword ? 'eye-fill' : 'eye-off'"
							:color="showPassword ? '#2979ff' : '#c0c4cc'" size="25"
							@click="showPassword = !showPassword" />
					</template>
				</up-input>
			</view>
			<!-- 登录按钮 -->
			<up-button class="login-btn" :disabled="!isFormValid" @click="handleLogin">
				登录
			</up-button>
			<up-modal confirmText="去注册" cancelText="取消" :show="showAgreementModal" :title="title" :content='content'
				showCancelButton width="260px" @confirm="handleAgreement(true)"
				@cancel="handleAgreement(false)"></up-modal>
		</view>
	</view>
</template>

<script setup lang="ts">
	import {
		ref,
		computed,
		reactive
	} from 'vue'
	import { useUserStore } from '@/stores/user'
	import { request } from '@/utils/request'
	
	// 定义密码登录表单类型
	interface PwdForm {
		grantType: string
		phone: string
		password: string
	}
	
	// 定义登录返回的数据类型
	interface LoginResponseData {
		userId: number
		token: string
		expire: number
		needPetInfo: boolean
	}
	
	// 响应式数据定义
	const pwdReactive = reactive<PwdForm>({
		grantType: 'password', //后端指定类型
		phone: '', //手机号
		password: '' //密码
	})
	
	const showAgreementModal = ref<boolean>(false) //模态框
	const title = ref<string>('温馨提示')
	const content = ref<string>('该手机号未注册，请先通过手机号注册')
	const showPassword = ref<boolean>(false) //密码显示按钮
	const userStore = useUserStore()
	
	// 返回
	const customBack = (): Promise<any> => uni.redirectTo({
		url:'/pages/login/login'
	})
	
	//点击跳转手机验证码登录
	const ToSMSLogin = (): Promise<any> => uni.navigateTo({
		url: '/pages/login/sms',
	})
	
	// 模态框
	const handleAgreement = async (agree: boolean): Promise<void> => {
		if (agree) {
			userStore.setUserInfo({
				phone: pwdReactive.phone
			})
			await uni.navigateTo({
				url: '/pages/login/register'
			})
		}
		showAgreementModal.value = false
	}
	
	// 按钮状态
	const isFormValid = computed<boolean>(() => Boolean(pwdReactive.phone && pwdReactive.password))
	
	// 点击登录
	const handleLogin = async (): Promise<void> => {
		try {
			const res = await request<LoginResponseData>({
				url: '/app/auth/login',
				method: 'POST',
				data: {
					...pwdReactive
				},
				header: {}
			})
			userStore.setUserInfo({
				token: res.data.token || '',
				userId: String(res.data.userId),
				phone: pwdReactive.phone
			})
			uni.navigateTo({
				url: res.data.needPetInfo ?
					'/pages/petSelection/petSelection' : '/pages/home/home'
			})

		} catch (err) {
			// 未注册
			if ((err as any).code === 1000) {
				showAgreementModal.value = true
				userStore.setUserInfo({
					phone: pwdReactive.phone
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