<template>
	<view class="container">
		<!-- 顶部logo -->
		<view class="logo">
			<up-image src="/static/imglogo.png" width="350rpx" height="350rpx" mode="widthFix"
				shape="circle"></up-image>
		</view>
		<view class="title">
			宠爱
		</view>
		<!-- 登录按钮 -->
		<view class="button-box">
			<up-button type="primary" class="phone" @click="handlePhoneLogin">手机号登录/注册</up-button>
			<up-button class="other" @click="navigateToOtherLogin">其他方式登录</up-button>
		</view>
		<!-- 底部协议描述 -->
		<view class="desc">
			<view class="desc-content">
				<up-checkbox name="agree" usedAlone v-model:checked="agreed">
				</up-checkbox>
				<text class="desc">
					我已阅读并同意
					<text class="link">《宠主用户协议》</text>
					<text class="link">《个人信息保护政策》</text>
					我已明确知晓宠主平台禁止未满18周岁的未成年注册和使用
				</text>
			</view>
		</view>
	</view>
	<up-modal confirmText="同意" cancelText="不同意" :show="showAgreementModal" :title="title" :content='content'
		showCancelButton width="300px" @confirm="handleAgreement(true)" @cancel="handleAgreement(false)"></up-modal>

</template>

<script setup lang="ts">
	import {
		ref
	} from 'vue'
	
	// 响应式数据定义
	const agreed = ref<boolean>(false)
	const showAgreementModal = ref<boolean>(false)
	const title = ref<string>('温馨提示')
	const content = ref<string>('请先阅读并同意《宠主用户协议》和《个人信息保护政策》')
	
	// 点击手机号登录
	const handlePhoneLogin = (): void => {
		!agreed.value ? showAgreementModal.value = true : uni.navigateTo({
			url: '/pages/login/sms'
		})
	}
	
	// 模态框
	const handleAgreement = (agree: boolean): void => {
		agreed.value = agree
		showAgreementModal.value = false
		if (!agree) uni.showToast({
			title: '请先同意协议条款',
			icon: 'none'
		})
	}
	
	// 点击其他方式登录
	const navigateToOtherLogin = (): void => {
		uni.showToast({
			title: '暂不支持其他登录方式，敬请期待',
			icon: 'none'
		})
	}
</script>

<style lang="scss" scoped>
	.container {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;

		.logo {
			margin-top: 35%;
		}

		.button-box {
			margin-top: 50rpx;
			width: 55%;
			border-radius: 80%;

			button {
				height: 70rpx;
				border-radius: 60rpx;
				line-height: 70rpx;
				font-size: 30rpx;

			}

			.phone {
				color: white;
			}

			.other {
				border-radius: 60rpx;
				margin-top: 20rpx;
				margin-bottom: 40rpx;
				color: black;
			}
		}

		.desc {
			width: 85%;

			.desc-content {
				display: flex;
				font-size: 24rpx;
				color: #666;

				checkbox {
					margin-right: 10rpx;
				}

				.link {
					color: #f5a031;
				}
			}
		}
	}
</style>