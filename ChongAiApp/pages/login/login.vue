<template>
	<view class="container">
		<!-- 顶部logo -->
		<view class="logo">
			<image src="/static/logo.png" mode="" style="width: 350rpx; height: 350rpx; border-radius: 50%;"></image>
		</view>
		<view class="title">
			宠爱
		</view>	
		<!-- 登录按钮 -->
		<view class="button-box">
				<button type="primary" class="sjh"  @click="handlePhoneLogin">手机号登录/注册</button>
				<button type="default" class="qt"  @click="navigateToOtherLogin">其他方式登录> </button>
		</view>、
		<!-- 底部协议描述 -->
		<view class="desc">
			 <view class="desc-content">
			      <checkbox-group @change="handleAgreeChange">
			        <checkbox  style="transform:scale(0.7)" color="#007aff" />
			      </checkbox-group>
			      <text class="desc">
			        我已阅读并同意
			        <text class="link" @click="navigateTo('agreement')">《宠主用户协议》</text>、
			        <text class="link" @click="navigateTo('privacy')">《个人信息保护政策》</text>，
			        我已明确知晓宠主平台禁止未满18周岁的未成年注册和使用
			      </text>
			    </view>
		</view>
	</view>
	
	
</template>

<script setup>	
import { ref } from 'vue'

const agreed = ref(false)
//点击手机号登录
const handlePhoneLogin = () => {
  if (!agreed.value) {
    uni.showToast({ title: '请先同意用户协议和隐私政策', icon: 'none' })
    return
  }else{
	  uni.navigateTo({ url: '/pages/login/sms' })
  }
}
// 点击其他方式登录
const navigateToOtherLogin = () => {
   uni.showToast({ title: '暂不支持其他登录方式，敬请期待', icon: 'none' })
}	
// 监听勾选框的状态
const handleAgreeChange = (e) => {
  agreed.value = e.detail.value.length > 0  
}
</script>

<style lang="scss" scoped>
	       .container{
					display: flex;
					flex-direction: column;
					align-items: center;
					justify-content: center;		
			   .logo{
					margin-top: 100rpx;
			   }
			   .button-box{
				    margin-top: 50rpx;
					width: 55%;
					border-radius: 80%;
					button{
						height: 70rpx;
						border-radius: 60rpx;
						line-height: 70rpx;
						font-size: 30rpx;
						
					}
					.sjh{
							color: white;
					}
					.qt{
						border-radius: 60rpx;
						margin-top: 20rpx;
						margin-bottom: 40rpx;
						color: black;
					}
			   }
			 .desc{
				 width: 85%;
				 .desc-content{
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
