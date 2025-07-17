<template>
	<!-- 自定义导航栏 -->
	<up-navbar title="您养的宠物（1/2）" rightText="跳过" :autoBack="true" @rightClick="handleSkip" fixed></up-navbar>s
	<view class="pet-selection-container">

		<view class="content-wrapper">
			<!-- 页签区域 -->
			<view class="tabs">
				<view v-for="tab in tabs" :key="tab.petClassId"
					:class="['tab-item', { active: activeTab === tab.petClassId }]" @click="handleTabChange(tab)">
					{{ tab.petClass }}
				</view>
			</view>
			<!-- 未养宠内容区域 -->
			<view v-if="activeTab === '0'" class="no-pet-section">
				<!-- 全屏背景图 -->
				<up--image src="/static/home.png" width="100%" height="100%" mode="aspectFill"></up--image>

				<!-- 文字内容 -->
				<view class="prompt-content">
					<text class="prompt-text">快来领养一只宠物吧</text>
					<text class="brand-text">优爱宠，期待您加入</text>
					<text class="slogan">宠物生活一站式服务平台</text>
				</view>
			</view>
			<!-- 选择宠物状态 -->
			<view v-else class="pet-selection-section">
				<!-- 搜索栏 -->
				<view class="search-bar">
					<uni-search-bar style="padding: 5rpx 5rpx;" placeholder="搜索宠物品种" radius="30" bgColor="#F5F5F5"
						cancelButton="none" @confirm="petSearch" v-model="petBreed" />
				</view>
				<!-- 热门品种 -->
				<view class="hot-section">
					<view class="section-title">热门:</view>
					<view class="hot-tags">
						<text v-for="(pet, index) in hotPets" :key="index" class="hot-tag"
							@click="checkPet(pet,petClass)">
							{{ pet }}
						</text>
					</view>
				</view>

				<!-- 字母分类列表 -->
				<view class="alphabet-section">
					<scroll-view class="alphabet-list" :show-scrollbar='false' scroll-y
						:scroll-into-view="currentLetter">
						<view v-for="(pets, letter) in petData" :key="letter" :id="`letter-${letter}`">
							<text class="letter-title">{{ letter }}</text>
							<view v-for="(pet, index) in pets" :key="index" class="pet-item"
								@click="checkPet(pet.petBreed,petClass)">
								{{ pet.petBreed }}
							</view>
						</view>
					</scroll-view>
					<!-- 字母导航（右侧固定定位） -->
					<view class="alphabet-nav-wrapper">
						<view class="alphabet-nav">
							<text v-for="letter in Object.keys(petData)" :key="letter"
								:class="['alphabet-char', { active: currentLetter === `letter-${letter}` }]"
								@click.="scrollToLetter(letter)">
								{{ letter }}
							</text>
						</view>
					</view>
				</view>
			</view>
		</view>
		<!-- 固定底部按钮 -->
		<view class="fixed-footer" v-if="showBtn">
			<up-button type="primary" shape="circle" hair-line="false" @click="handleSkip">完成</up-button>
		</view>
	</view>
</template>

<script setup>
	import {
		ref,
		onMounted
	} from 'vue'
	import {
		getCurrentInstance
	} from 'vue'
	import {
		useAuthStore
	} from '@/stores/auth' // 导入Pinia store
	import {
		request
	} from '../../utils/request'

	const tabs = ref('') // 宠物分类
	const activeTab = ref('0') //默认尚无养宠
	const currentLetter = ref('letter-A') // 默认选中A字母
	const petClass = ref('') //宠物类名

	const petBreed = ref('')
	const petData = ref('')

	const hotPets = ref([]) //热门宠物
	const showBtn = ref(true) //显示按钮

	const handleSkip = () => {
		uni.navigateTo({
			url: '/pages/home/home'
		})
	}
	// 切换tab栏
	const handleTabChange = async (tab) => {
		petClass.value = tab.petClass
		activeTab.value = tab.petClassId
		// 是否显示按钮
		if (activeTab.value != 0) {
			showBtn.value = false
		} else {
			showBtn.value = true
		}
		//获取宠物品种列表
		GetBreedList()
	}
	//点击字母显示高亮
	const scrollToLetter = (letter) => {
		currentLetter.value = `letter-${letter}`
	}
	// 初始化
	onMounted(() => {
		GetPetTypeList()
	})
	//获取宠物类别列表 
	const GetPetTypeList = async () => {
		try {
			const res = await request({
				url: '/app/pet/pet',
			})
			tabs.value = res.data
		} catch (error) {
			console.error('获取宠物类型失败:', error)
		}
	}
	// 获取宠物列表
	const GetBreedList = async () => {
		try {
			const res = await request({
				url: '/app/pet/breeds',
				data: {
					petClass: petClass.value,
					petBreed: petBreed.value
				}
			});
			hotPets.value = res.data.hot;
			petData.value = res.data.breeds;
		} catch (error) {
			uni.showToast({
				title: '获取品种数据失败',
				icon: 'none'
			});
		}
	};

	// 搜索框
	const petSearch = () => {
		GetBreedList()
	}
	// 点击宠物标签
	const checkPet = (breed, type) => {
		uni.navigateTo({
			url: `/pages/petSelection/petInfo?petBreed=${encodeURIComponent(breed)}&petClass=${encodeURIComponent(type)}`
		})
	}
</script>

<style scoped lang="scss">
	.pet-selection-container {
		display: flex;

		flex-direction: column;
	}

	.content-wrapper {
		width: 100%;
		height: 100%;
		margin-top: 40rpx;
		/* 将间距移到导航栏 */
		position: fixed;


	}

	/* 页签样式 */
	.tabs {
		display: flex;
		margin: 10px 0;
		padding: 0 15px;
		background: white;
		overflow-x: auto;
		scrollbar-width: none;
	}

	.tab-item {
		flex: 0 0 auto;
		/* 取消等分，改为自适应 */
		width: 25%;
		padding: 8px 12px;
		margin-right: 8px;
		border-radius: 20px;
		background-color: #F5F5F5;
		color: #666;
		font-size: 14px;
		text-align: center;
		white-space: nowrap;
	}

	.tab-item.active {
		background-color: #1989FA;
		color: white;
	}

	/* 未养宠内容区域 */
	.no-pet-section {
		flex: 1;
		height: 100vh;
		position: relative;
	}

	.fullscreen-bg {
		position: absolute;
		width: 100%;
		height: 100%;
		top: 0;
		left: 0;
	}

	.prompt-content {
		position: absolute;
		top: 20%;
		left: 0;
		right: 0;
		text-align: center;
		z-index: 1;
	}

	.prompt-text {
		display: block;
		font-size: 30rpx;
		color: #ffff80;
		/* 黄色文字 */
		margin-bottom: 10px;
	}

	.brand-text {
		display: block;
		font-size: 30rpx;
		color: #ffff80;
	}

	.slogan {
		display: block;
		font-size: 30rpx;
		color: #ffff80;
	}

	/* 选择宠物状态 */
	.pet-selection-section {
		flex: 1;
		padding: 0 7px;
	}


	.hot-section {
		width: 95%;
		margin-bottom: 10rpx;
	}

	.section-title {
		font-size: 14px;
		color: #666;
		margin: 20rpx 0;
	}

	.hot-tags {
		display: flex;
		flex-wrap: wrap;
		gap: 5px;
	}

	.hot-tag {
		padding: 6px 12px;
		background-color: #F5F5F5;
		border-radius: 15px;
		font-size: 12px;
		color: #333;

		&.active-tag {
			background-color: #1989fa !important;
			color: white !important;
			transform: scale(1.05);
			box-shadow: 0 2rpx 6rpx rgba(25, 137, 250, 0.3);
		}
	}

	/* 字母列表 */
	.alphabet-section {
		position: relative;
		height: 60vh;
	}

	.alphabet-list {
		height: 100%;
	}

	.letter-title {
		display: block;
		padding: 10px 0;
		margin-right: 50rpx;
		font-size: 16px;
		color: black;
		background-color: #f5f5f5;
		font-weight: bold;
	}

	.pet-item {
		padding: 12px 0;
		font-size: 14px;
		color: #333;
		border-bottom: 1px solid #EEE;
	}

	/* 字母导航容器（ */
	.alphabet-nav-wrapper {
		position: fixed;
		top: 57%;
		right: 12rpx;
		transform: translateY(-50%);
		z-index: 100;
		padding: 10rpx 0rpx 10rpx 0rpx;
		background: rgba(233, 233, 233, 0.9);
	}

	/* 字母导航项 */
	.alphabet-nav {
		display: flex;
		flex-direction: column;
		align-items: center;
	}

	.alphabet-char {
		font-size: 12px;
		color: #acacac;
		padding: 2px 6px;
		text-align: center;

		&.active {
			color: #1989FA;
			font-weight: bold;
		}
	}

	.alphabet-char.active {
		color: #1989FA;
		font-weight: bold;
		transform: scale(1.2);
		transition: all 0.2s;
	}

	/* 固定底部按钮 */
	.fixed-footer {
		width: 50%;
		position: absolute;
		bottom: 1%;
		left: 25%;
		// right: 0;
		z-index: 10;
	}
</style>