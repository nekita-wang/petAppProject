<template>
	<view class="login-container">
		<!-- 自定义导航栏 -->
		<up-navbar  rightText="手机号验证码登录" :autoBack="true" @rightClick="ToSMSLogin" fixed></up-navbar>
		<!-- 手机号输入 -->
		<view class="phone-box">
			<view class="input-group">
				<view class="prefix">+86</view>
				<up-input placeholder="请输入手机号" type='number' shape="circle" clearable v-model="pwdReactive.phone"
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
			<u-modal confirmText="去注册" cancelText="取消" :show="showAgreementModal" :title="title" :content='content'
				showCancelButton width="260px" @confirm="handleAgreement(true)"
				@cancel="handleAgreement(false)"></u-modal>
		</view>
	</view>
</template>

<script setup>
	import {
		ref,
		computed,
		reactive
	} from 'vue'
	import {
		useAuthStore
	} from '@/stores/auth'
	import {
		request
	} from '../../utils/request'
	const pwdReactive = reactive({
		grantType: 'password', //后端指定类型
		phone: '', //手机号
		password: '' //密码
	})
	const showAgreementModal = ref(false) //模态框
	const title = ref('温馨提示');
	const content = ref('该手机号未注册，请先通过手机号注册');
	const showPassword = ref(false) //密码显示按钮
	const authStore = useAuthStore();
	//点击跳转手机验证码登录
	const ToSMSLogin = () => uni.navigateTo({
		url: '/pages/login/sms',
	})
	// 模态框
	const handleAgreement = async (agree) => {
		if (agree) {
			await authStore.setUserInfo({
				phone: pwdReactive.phone
			});
			uni.navigateTo({
				url: '/pages/login/register'
			});
		}
		showAgreementModal.value = false;
	}
	// 按钮状态
	const isFormValid = computed(() => pwdReactive.phone && pwdReactive.password)
	// 点击登录
	const handleLogin = async () => {
		try {
			const res = await request({
				url: '/app/auth/login',
				method: 'POST',
				data: {
					...pwdReactive
				}
			});
			if (!res.success) {
				// 未注册
				if (res.code === 1000) {
					return showAgreementModal.value = true
				}
				 uni.showToast({
					title: res.msg || '登录失败',
					icon: 'none'
				});
				return
			}

			await authStore.setUserInfo({
				token: res.data.token || '',
				userId: res.data.userId,
				phone: pwdReactive.phone
			});

			uni.navigateTo({
				url: res.data.needPetInfo ?
					'/pages/petSelection/petSelection' : '/pages/home/home'
			});

		} catch (error) {
			uni.showToast({
				title: error.message || '网络错误，请重试',
				icon: 'none'
			});
		}
	};
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