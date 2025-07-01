<template>
	<view class="profile-container">

		<!-- 头像上传 -->
		<view class="avatar-upload" @click="UploadImage">
			<image :src=imagePath class="avatar" />
			<!-- <image src="/static/camera-icon.svg" class="camera-icon" /> -->
		</view>

		<!-- 表单区域 -->
		<view class="form-group">
			<!-- 手机号-->
			<view class="form-item">
				<text class="label">手机号:</text>
				<input v-model="phone" placeholder="请输入" />
			</view>

			<!-- 昵称 -->
			<view class="form-item">
				<text class="label">昵称:</text>
				<input v-model="nickname" placeholder="请输入" />
			</view>
			<!-- 性别 -->
			<!-- 性别选择区域 -->
			<view class="form-item">
				<text class="label">性别:</text>
				<view class="gender-container">
					<!-- 男性选项 -->
					<view class="gender-option">
						<view class="sex-male" :class="{ active: gender === 'male' }">
							<image src="/static/性别男.svg" class="gender-icon" />
						</view>
						<text class="gender-tag male" :class="{ active: gender === 'male' }"
							@click="gender = 'male'">男</text>
					</view>

					<!-- 女性选项 -->
					<view class="gender-option">
						<view class="sex-female" :class="{ active: gender === 'female' }">
							<image src="/static/性别女.svg" class="gender-icon" />
						</view>
						<text class="gender-tag female" :class="{ active: gender === 'female' }"
							@click="gender = 'female'">女</text>
					</view>
				</view>
			</view>

			<!-- 生日-->
			<view class="form-item">
				<text class="label">您的生日:</text>
				<!-- 日期选择器容器 -->
				<view class="date-picker-container">
					<picker-view :indicator-style="indicatorStyle" :mask-style="maskStyle" :value="pickerValue"
						@change="handleDateChange">
						<picker-view-column>
							<view class="item" v-for="(item,index) in years" :key="index">{{item}}年</view>
						</picker-view-column>
						<picker-view-column>
							<view class="item" v-for="(item,index) in months" :key="index">{{item}}月</view>
						</picker-view-column>
						<picker-view-column>
							<view class="item" v-for="(item,index) in days" :key="index">{{item}}日</view>
						</picker-view-column>
					</picker-view>
				</view>
			</view>

			<!-- 密码 -->
			<view class="form-item">
				<text class="label">密码:</text>
				<input v-model="password" password placeholder="请输入" />
			</view>

			<!-- 密码确认 -->
			<view class="form-item">
				<text class="label">密码确认:</text>
				<input v-model="confirmPassword" password placeholder="请输入" />
			</view>
		</view>

		<!-- 下一步按钮 -->
		<button class="next-btn" @click="handelNext">下一步</button>
	</view>
</template>

<script setup>
	import {
			onMounted,
			ref
		} from 'vue'
		const imagePath = ref('/static/图片.svg') //默认图片
		const phone = ref('') //手机号
		const nickname = ref('') //昵称
		const password = ref('') //密码
		const confirmPassword = ref('') // 确认密码
		const gender = ref('male') // 默认选中男性

		// 选择器样式
		const indicatorStyle = ref('') // 选中项样式
		const maskStyle = ref("") // 遮罩层样式
		
		// 初始化当前日期
		const currentDate = new Date()
		const years = ref([]) // 年份列表
		const year = ref(currentDate.getFullYear()) // 当前选中年份
		const months = ref([]) // 月份列表
		const month = ref(currentDate.getMonth() + 1) // 当前选中月份
		const days = ref([]) // 日期列表
		const day = ref(currentDate.getDate()) // 当前选中日期
		const pickerValue = ref([]) // 选择器默认值
		const handelNext =()=>{
			uni.navigateTo({
				url:'/pages/petSelection/petSelection'
			})
		}
		//点击上传图片
		const UploadImage =()=>{
			uni.chooseImage({
				count:1, 
				success(res) {
					imagePath.value = res.tempFilePaths[0] //将默认图片更改掉
				}
			})
		}
		
		// 组件挂载时初始化数据
		onMounted(() => {
		    // 初始化年份范围(1990-当前年份)
		    for (let i = 1990; i <= currentDate.getFullYear(); i++) {
		        years.value.push(i)
		    }
		
		    // 初始化月份(1-12月)
		    for (let i = 1; i <= 12; i++) {
		        months.value.push(i)
		    }
		
		    // 初始化日期(1-31日)
		    for (let i = 1; i <= 31; i++) {
		        days.value.push(i)
		    }
			
			// 设置初始选择器值
			const yearIndex = years.value.indexOf(year.value);
			pickerValue.value = [yearIndex, month.value - 1, day.value - 1];
			
			// 设置选择器样式
			indicatorStyle.value = `height: ${uni.upx2px(80)}px;` // 使用uni的转换方法
			
			// 快手小程序特殊样式
			// #ifdef MP-KUAISHOU
			maskStyle.value = "padding:10px 0"
			// #endif
		})
		
		// 处理日期变化事件
		const handleDateChange = (event) => {
		    const selectedValues = event.detail.value
		    year.value = years.value[selectedValues[0]]
		    month.value = months.value[selectedValues[1]]
		    day.value = days.value[selectedValues[2]]
		}
</script>

<style scoped lang="scss">
	.profile-container {
		padding: 0 40rpx;
		background-color: #fff;
	}


	/* 头像上传 */
	.avatar-upload {
		position: relative;
		width: 160rpx;
		height: 160rpx;
		margin: 0 auto;
	}

	.avatar {
		width: 100%;
		height: 100%;
		border-radius: 10%;
	}

	.camera-icon {
		position: absolute;
		right: 0;
		bottom: 0;
		width: 48rpx;
		height: 48rpx;
	}

	/* 表单样式 */
	.form-item {
		padding: 20rpx 0;
		display: flex;
		align-items: center;
	}

	.label {
		text-align: center;
		width: 160rpx;
		font-size: 32rpx;
		color: #333;
	}

	input {
		flex: 1;
		font-size: 32rpx;
		height: 65rpx;
		padding-left: 25rpx;
		border-radius: 30rpx;
		background-color: #e8e8e8;
	}

	.value {
		flex: 1;
		font-size: 32rpx;
	}

	/* 性别选择容器 */
	.gender-container {
		flex: 1;
		display: flex;
		gap: 40rpx;
		/* 选项间距 */
	}

	/* 单个性别选项 */
	.gender-option {
		display: flex;
		align-items: center;

		.sex-male {
			display: flex;
			justify-content: center;
			align-items: center;
			width: 50rpx;
			height: 50rpx;
			background-color: #0091ff;
			margin-right: 20rpx;
			border-radius: 50%;
		}

		.sex-female {
			display: flex;
			justify-content: center;
			align-items: center;
			width: 50rpx;
			height: 50rpx;
			margin-right: 20rpx;
			background-color: #ff4d94;
			border-radius: 50%;
		}

		image {
			width: 70%;
			height: 70%;


		}
	}


	/* 文字标签 */
	.gender-tag {
		padding: 8rpx 50rpx;
		border-radius: 30rpx;
		background-color: #f5f5f5;
		font-size: 32rpx;
	}

	/* 性别标签激活状态 */
	.gender-tag.active.male {
		background-color: #1989fa !important;
		color: white !important;
	}

	.gender-tag.active.female {
		background-color: #ff7bac !important;
		color: white !important;
	}

	/* 日期显示容器（完全还原截图样式） */
	.date-display {
		flex: 1;
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 0 20rpx;
		height: 65rpx;
		background-color: #e8e8e8;
		border-radius: 30rpx;
		font-size: 32rpx;
	}

	.arrow-icon {
		width: 32rpx;
		height: 32rpx;
	}

	.label {
		width: 160rpx;
		font-size: 32rpx;
		color: #333;
	}

	/* 日期选择器容器 */
		.date-picker-container {
			flex: 1;
			height: 300rpx;
			margin-top: 20rpx;
			background-color: #f8f8f8;
			border-radius: 20rpx;
			overflow: hidden;
		}
	
		/* 选择器样式 */
		picker-view {
			width: 100%;
			height: 100%;
		}
		
		/* 选择项样式 */
		.item {
			line-height: 80rpx;
			text-align: center;
			font-size: 32rpx;
			color: #666;
		}
		
		/* 选中项样式 */
		picker-view-column view.item {
			font-weight: bold;
			color: black;
		}



	/* 下一步按钮 */
	.next-btn {
		width: 300rpx;
		background-color: #1989fa;
		color: white;
		border-radius: 50rpx;
		height: 70rpx;
		margin-top: 60rpx;
		line-height: 70rpx;
	}
</style>