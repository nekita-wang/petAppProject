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
			<u-button type="primary" class="sjh" @click="handlePhoneLogin">手机号登录/注册</u-button>
			<u-button class="qt" @click="navigateToOtherLogin">其他方式登录> </u-button>
		</view>
		<!-- 底部协议描述 -->
		<view class="desc">
			<view class="desc-content">
				<up-checkbox name="agree" usedAlone v-model:checked="agreed">
				</up-checkbox>
				<text class="desc">
					我已阅读并同意
					<text class="link" @click="navigateTo('agreement')">《宠主用户协议》</text>
					<text class="link" @click="navigateTo('privacy')">《个人信息保护政策》</text>
					我已明确知晓宠主平台禁止未满18周岁的未成年注册和使用
				</text>
			</view>
		</view>
	</view>


</template>

<script setup>
	import {
		ref
	} from 'vue'
	const agreed = ref(false)
	// 点击手机号登录
	const handlePhoneLogin = () => {
		!agreed.value ? uni.showModal({
			title: '温馨提示',
			content: '请先阅读并同意《宠主用户协议》和《个人信息保护政策》',
			confirmText: '同意',
			cancelText: '不同意',
			success: ({
				confirm
			}) => confirm && (agreed.value = true)
		}) : uni.navigateTo({
			url: '/pages/login/sms'
		})
	}
	// 点击其他方式登录
	const navigateToOtherLogin = () => {
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
			margin-top: 20%;
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

			.sjh {
				color: white;
			}

			.qt {
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