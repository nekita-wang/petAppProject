<template>
	<view class="pet-info-container">
		<!-- 顶部导航栏 -->
		<uni-nav-bar :border="false" background-color="#ffffff" fixed status-bar>
			<!-- 左侧返回按钮 -->
			<template #left>
				<view class="nav-left" @click="handleBack">
					<uni-icons type="arrow-left" size="24"></uni-icons>
				</view>
			</template>

			<!-- 中间标题 -->
			<view class="nav-title">您养的宠物(2/2)</view>

			<!-- 右侧跳过按钮 -->
			<template #right>
				<view class="nav-right" @click="handleSkip">
					<text class="skip-text">跳过</text>
				</view>
			</template>
		</uni-nav-bar>
		<!-- 头像上传 -->
		<view class="avatar-upload" @click="UploadImage">
			<image :src=avatar class="avatar" />

			<!-- <image src="/static/camera-icon.svg" class="camera-icon" /> -->
		</view>
		<view class="section-title">为您的爱宠选一张靓照做头像</view>
		<!-- 表单区域 -->
		<view class="form-group">
			<!-- 手机号-->
			<view class="form-item">
				<text class="label">宠物品种:</text>
				<input v-model="petBreed" disabled placeholder="请输入" />
			</view>

			<!-- 昵称 -->
			<view class="form-item">
				<text class="label">宠物昵称:</text>
				<input v-model="petNickName" placeholder="请输入宠物昵称" maxlength="10" />
			</view>
			<!-- 性别 -->
			<view class="form-item">
				<text class="label">宠物性别:</text>
				<view class="gender-container">
					<!-- 男性选项 -->
					<view class="gender-option">
						<view class="sex-male" :class="{ active: petGender === '0' }">
							<image src="/static/nan.svg" class="gender-icon" />
						</view>
						<text class="gender-tag male" :class="{ active: petGender === '0' }"
							@click="petGender = '0'">男</text>
					</view>

					<!-- 女性选项 -->
					<view class="gender-option">
						<view class="sex-female" :class="{ active: petGender === '1' }">
							<image src="/static/nv.svg" class="gender-icon" />
						</view>
						<text class="gender-tag female" :class="{ active: petGender === '1' }"
							@click="petGender = '1'">女</text>
					</view>
				</view>
			</view>
			<!-- 绝育状态 -->
			<view class="form-item">
				<text class="label">是否绝育:</text>
				<view class="neuter-options">
					<view :class="['neuter-option', { active: sterilized === '1' }]" @click="sterilized = '1'">
						是
					</view>
					<view :class="['neuter-option', { active: sterilized === '0' }]" @click="sterilized = '0'">
						否
					</view>
				</view>
			</view>
			<!-- 宠物生日 -->
			<view class="form-item">
				<text class="label">宠物生日:</text>
				<DatePicker @date-change="handleBirthdayChange" />
			</view>

			<!-- 到家日期 -->
			<view class="form-item">
				<text class="label">到家日期:</text>
				<DatePicker @date-change="handleArrivalDateChange" />
			</view>
		</view>

		<!-- 完成按钮 -->
		<button class="next-btn" :disabled="!isFormValid" @click="complete">完成</button>

	</view>
</template>

<script setup>
	import {
		apiaddPet
	} from '../../api/petSelection'
	import DatePicker from '@/components/DatePicker.vue'
	import {
		onMounted,
		computed,
		ref
	} from 'vue'
	import {
		onLoad
	} from '@dcloudio/uni-app'
	const avatar = ref('/static/touxiang.svg') //默认图片
	const petBreed = ref('') //宠物品种
	const petClass = ref('') //宠物类别
	const petNickName = ref('') //宠物昵称
	const petGender = ref('') // 宠物性别
	const sterilized = ref('0') //是否绝育
	const petBirthday = ref(new Date().getDate()) //宠物生日
	const arrivalDate = ref('') //到家日期
	// 返回上一级
	const handleBack = () => {
		uni.navigateBack()
	}
	// 跳过方法
	const handleSkip = () => {
		uni.navigateTo({
			url: '/pages/home/home'
		})
	}
	//点击上传图片
	const UploadImage = () => {
		uni.chooseImage({
			count: 1,
			success(res) {
				avatar.value = res.tempFilePaths[0] //将默认图片更改掉
			}
		})
	}
	// 宠物生日
	const handleBirthdayChange = (date) => {
		petBirthday.value = date
	}

	// 到家日期
	const handleArrivalDateChange = (date) => {
		arrivalDate.value = date
	}
	// 按钮状态
	const isFormValid = computed(() => {
		return (
			petNickName.value !== '' &&
			petGender.value !== ''
		)
	})
	// 点击完成
	const complete = async () => {
		// 调用注册接口接口
		const res = await apiaddPet({
			petAvatarURL: avatar.value,
			petBreed: petBreed.value,
			petClass: petClass.value,
			petNickName: petNickName.value,
			petGender: petGender.value,
			sterilized: sterilized.value,
			petBirthday: petBirthday.value,
			adoptionDate: arrivalDate.value
		})
		console.log(res);
		if (res.code === 200) {
			uni.showToast({
				title: '添加成功',
				icon: 'success'
			})
			uni.navigateTo({
				url: '/pages/home/home'
			})
		} else {
			uni.showToast({
				title: res.msg,
				icon: 'none'
			})
		}
	}
	onLoad((options) => {
		petBreed.value = decodeURIComponent(options.petBreed) // 必须解码
		petClass.value = decodeURIComponent(options.petClass)
		if (petBreed.value === 'undefined') {
			petBreed.value = ''
		}
		function formatDate(date) {
			const year = date.getFullYear();
			const month = String(date.getMonth() + 1).padStart(2, '0'); // 补零
			const day = String(date.getDate()).padStart(2, '0'); // 补零
			return `${year}-${month}-${day}`; // 格式：YYYY-MM-DD
		}
		petBirthday.value = formatDate(new Date());
		arrivalDate.value = formatDate(new Date());
	})
</script>

<style scoped lang="scss">
	.pet-selection-container {
		position: relative;
		height: 100vh;
		display: flex;
		flex-direction: column;
	}

	/* 导航栏整体样式 */
	:deep(.uni-nav-bar) {
		height: 44px;
		line-height: 44px;
	}

	/* 左侧按钮容器 */
	.nav-left {
		padding-left: 10px;
		display: flex;
		align-items: center;
	}

	/* 中间标题样式 */
	.nav-title {
		font-size: 17px;
		font-weight: 500;
		color: #000000;
		line-height: 44px;
		text-align: center;
		flex: 1;
	}

	/* 右侧跳过按钮样式 */
	.nav-right {
		padding-right: 13rpx;
	}

	.skip-text {
		background-color: #aaaaaa;
		padding: 12rpx 21rpx;
		border-radius: 50rpx;
		color: white;
		font-size: 26rpx;
	}

	/* 头像上传 */
	.avatar-upload {
		position: relative;
		width: 160rpx;
		height: 160rpx;
		margin: 0 auto;
	}

	.section-title {
		text-align: center;
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
		padding: 20rpx 20rpx;
		display: flex;
		align-items: center;
	}

	.label {
		font-weight: bold;
		text-align: center;
		width: 160rpx;
		font-size: 32rpx;
		color: #333;
	}

	input {
		width: 70%;
		font-size: 32rpx;
		height: 55rpx;
		margin-top: 10rpx;
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
		justify-content: center;
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

	/* 绝育选择 */
	.neuter-options {
		display: flex;
		justify-content: center;
		flex: 1;
		gap: 20px;
	}

	.neuter-option {
		width: 120rpx;
		padding: 6px 20px;
		border-radius: 20px;
		text-align: center;
		background: #f5f5f5;
		color: #666;
		font-size: 32rpx;
	}

	.neuter-option.active {
		background: #1989fa;
		color: white;
	}

	/* 下一步按钮 */
	.next-btn {
		width: 300rpx;
		background-color: #007aff;
		color: white;
		border-radius: 50rpx;
		height: 70rpx;
		margin-top: 60rpx;
		line-height: 70rpx;

		&.active {
			background-color: #007aff;
		}
	}
</style>