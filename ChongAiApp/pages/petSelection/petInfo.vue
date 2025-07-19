<template>
	<!-- 自定义导航栏 -->
	<view class="pet-info-container">
		<up-navbar title="您养的宠物（2/2）" rightText="跳过" :autoBack="true" @rightClick="handleSkip" fixed></up-navbar>s
		<!-- 头像上传 -->
		<view class="avatar-upload" @click="UploadImage">
			<up-image width="100%" height="100%" :src="petReactive.petAvatarURL" shape="circle"></up-image>
		</view>
		<view class="section-title">为您的爱宠选一张靓照做头像</view>
		<!-- 表单区域 -->
		<view class="form-group">
			<!-- 手机号-->
			<view class="form-item">
				<text class="label">宠物品种:</text>
				<input v-model="petReactive.petBreed" disabled placeholder="请输入" />
			</view>

			<!-- 昵称 -->
			<view class="form-item">
				<text class="label">宠物昵称:</text>
				<input v-model="petReactive.petNickName" placeholder="请输入宠物昵称" maxlength="10" />
			</view>
			<!-- 性别 -->
			<view class="form-item">
				<text class="label">宠物性别:</text>
				<view class="gender-container">
					<!-- 男性选项 -->
					<view class="gender-option">
						<view class="sex-male" :class="{ active: petReactive.petGender === '0' }">
							<image src="/static/nan.svg" class="gender-icon" />
						</view>
						<text class="gender-tag male" :class="{ active: petReactive.petGender === '0' }"
							@click="petReactive.petGender = '0'">男</text>
					</view>

					<!-- 女性选项 -->
					<view class="gender-option">
						<view class="sex-female" :class="{ active: petReactive.petGender === '1' }">
							<image src="/static/nv.svg" class="gender-icon" />
						</view>
						<text class="gender-tag female" :class="{ active: petReactive.petGender === '1' }"
							@click="petReactive.petGender = '1'">女</text>
					</view>
				</view>
			</view>
			<!-- 绝育状态 -->
			<view class="form-item">
				<text class="label">是否绝育:</text>
				<view class="neuter-options">
					<view :class="['neuter-option', { active: petReactive.sterilized === '1' }]"
						@click="petReactive.sterilized = '1'">
						是
					</view>
					<view :class="['neuter-option', { active: petReactive.sterilized === '0' }]"
						@click="petReactive.sterilized = '0'">
						否
					</view>
				</view>
			</view>
			<!-- 宠物生日 -->
			<view class="form-item">
				<text class="label">宠物生日:</text>
				<DatePicker v-model="petReactive.petBirthday" @date-change="handleBirthdayChange" />
			</view>

			<!-- 到家日期 -->
			<view class="form-item">
				<text class="label">到家日期:</text>
				<DatePicker v-model="petReactive.adoptionDate" @date-change="handleArrivalDateChange" />
			</view>
		</view>

		<!-- 完成按钮 -->
		<button class="next-btn" :disabled="!isFormValid" @click="complete">完成</button>

	</view>
</template>

<script setup>
	import DatePicker from '@/components/DatePicker.vue'
	import {
		onMounted,
		computed,
		reactive,
		ref
	} from 'vue'
	import {
		onLoad
	} from '@dcloudio/uni-app'
	import {
		uploadImg
	} from '../../utils/uploadImg'
	import {
		useAuthStore
	} from '../../stores/auth'
	import {
		request
	} from '../../utils/request'
	const relativePath = ref('')
	const authStore = useAuthStore() //使用pinia
	const petReactive = reactive({
		petAvatarURL: '/static/tx.svg', // 宠物头像
		petBreed: '', // 宠物品种
		petClass: '', // 宠物类别
		petNickName: '', // 宠物昵称
		petGender: '0', // 宠物性别 (0-男, 1-女)
		sterilized: '0', // 是否绝育 (0-否, 1-是)
		petBirthday: '2000-06-06', // 宠物生日
		adoptionDate: '2000-06-06' // 到家日期
	})

	// 跳过方法
	const handleSkip = () => {
		uni.navigateTo({
			url: '/pages/home/home'
		})
	}
	//日期选择器
	const handleBirthdayChange = (date) => {
		petReactive.petBirthday = date
	}
	const handleArrivalDateChange = (date) => {
		petReactive.adoptionDate = date
	}
	// 用户是否上传头像
	const hasUploadedAvatar = computed(() => petReactive.petAvatarURL !== '/static/tx.svg')
	//点击上传图片
	const UploadImage = async () => {
		uploadImg((AvatarCallback) => {
			petReactive.petAvatarURL = AvatarCallback.fullUrl; //带有ip地址的
			relativePath.value = AvatarCallback.relativePath
		});

	}

	// 按钮状态
	const isFormValid = computed(() => petReactive.petNickName && petReactive.petGender)

	// 点击完成
	const complete = async () => {
		if (!hasUploadedAvatar.value) {
			uni.showToast({
				title: '请上传宠物头像',
				icon: 'none'
			})
			return
		}
		// 调用注册接口接口
		const res = await request({
			url: '/app/pet/addPet',
			method: 'POST',
			data: {
				...petReactive,
				petAvatarURL: relativePath.value,
				userId: Number(authStore.userId) // 强制转换为数字
			}
		})
		console.log(res);
		// 防止用户返回处理
		if (res.code === 4002) {
			uni.showToast({
				title: "您已填写过信息,为您前往下一个页面",
				icon: 'none',
				duration: 2000
			})
			uni.navigateTo({
				url: '/pages/home/home'
			});
			return
		}
		if (!res.success) {
			return uni.showToast({
				title: res.msg || '登录失败',
				icon: 'none'
			});
		}

		uni.showToast({
			title: '添加成功',
			icon: 'success'
		})
		uni.navigateTo({
			url: '/pages/home/home'
		})

	}
	onLoad((options) => {
		petReactive.petBreed = decodeURIComponent(options.petBreed) // 必须解码
		petReactive.petClass = decodeURIComponent(options.petClass)
	})
</script>

<style scoped lang="scss">
	.pet-selection-container {
		display: flex;
		flex-direction: column;
	}


	/* 头像上传 */
	.avatar-upload {
		padding-top: 108rpx;
		position: relative;
		width: 160rpx;
		height: 160rpx;
		margin: 0 auto;
	}

	.section-title {
		text-align: center;
		margin-top: 5rpx;
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
		padding: 15rpx 20rpx;
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