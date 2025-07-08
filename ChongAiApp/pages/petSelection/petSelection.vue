	<template>
		<view class="pet-selection-container">
			<!-- 顶部导航栏 -->
			<uni-nav-bar :border="false" background-color="#ffffff" fixed status-bar>
				<!-- 左侧返回按钮 -->
				<template #left>
					<view class="nav-left" @click="handleBack">
						<uni-icons type="arrow-left" size="24"></uni-icons>
					</view>
				</template>

				<!-- 中间标题 -->
				<view class="nav-title">您养的宠物(1/2)</view>

				<!-- 右侧跳过按钮 -->
				<template #right>
					<view class="nav-right" @click="handleSkip">
						<text class="skip-text">跳过</text>
					</view>
				</template>
			</uni-nav-bar>
			<!-- 页签区域 -->
			<view class="tabs">
				<view v-for="tab in tabs" :key="tab.petClassId"
					:class="['tab-item', { active: activeTab === tab.petClassId }]" @click="handleTabChange(tab)">
					{{ tab.petClass }}
				</view>
			</view>
			<!-- 未养宠内容区域 -->
			<view v-if="activeTab === 'none'" class="no-pet-section">
				<!-- 全屏背景图 -->
				<image src="/static/home.png" class="fullscreen-bg" mode="aspectFill" />

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
						cancelButton="none" />
				</view>
				<!-- 热门品种 -->
				<view class="hot-section">
					<view class="section-title">热门:</view>
					<view class="hot-tags">
						<text v-for="(pet, index) in hotPets" :key="index" class="hot-tag">
							{{ pet }}
						</text>
					</view>
				</view>

				<!-- 字母分类列表 -->
				<view class="alphabet-section">
					<scroll-view class="alphabet-list" :show-scrollbar='false' scroll-y :scroll-into-view="currentLetter">
						<view v-for="(pets, letter) in petData" :key="letter" :id="`letter-${letter}`">
							<text class="letter-title">{{ letter }}</text>
							<view v-for="(pet, index) in pets" :key="index" class="pet-item">
								{{ pet }}
							</view>
						</view>
					</scroll-view>
					<!-- 字母导航（右侧固定定位） -->
					<view class="alphabet-nav-wrapper">
						<view class="alphabet-nav">
							<text v-for="letter in Object.keys(petData)" :key="letter"
								:class="['alphabet-char', { active: currentLetter === `letter-${letter}` }]"
								@click="scrollToLetter(letter)">
								{{ letter }}
							</text>
						</view>
					</view>
				</view>
			</view>
			<!-- 固定底部按钮 -->
			<view class="fixed-footer">
				<button class="next-btn">{{buttonText}}</button>
			</view>
		</view>
	</template>

	<script setup>
		import {
			ref,
			onMounted,
			nextTick
		} from 'vue'
		import {
			getCurrentInstance
		} from 'vue'
		import {
			apiGetBreedList,
			apiGetPetTypeList
		} from '../../api/petSelection'
		// 宠物分类
		const tabs = ref('')

		const buttonText = ref('完成') //默认按钮内容
		const activeTab = ref('none') //默认按钮
		const currentLetter = ref('letter-A') // 默认选中A字母
		const petClass = ref('')
		const petData = ref('')
		// 热门品种
		const hotPets = ref([
			'布偶猫', '欧洲蓝猫', '美国短毛猫',
			'埃及猫', '暹罗猫', '日本短尾猫'
		])

		// 导航方法
		const handleBack = () => {
			uni.navigateBack()
		}
		const handleSkip = () => {
			uni.navigateTo({
				url: './petInfo'
			})
		}
		// 切换tab栏
		const handleTabChange = async (tab) => {
			console.log(tab);
			petClass.value = tab.petClass
			activeTab.value = tab.petClassId
			if (activeTab.value != 0) {
				buttonText.value = '下一个'
			} else {
				buttonText.value = '完成'
			}
			//获取宠物品种列表
			GetBreedList()
		}
		//点击字母显示高亮
		const scrollToLetter = (letter) => {
			currentLetter.value = `letter-${letter}`
		}
		onMounted(() => {
			GetPetTypeList()
		})
		//获取宠物分类列表 
		const GetPetTypeList = async () => {
			try {
				const res = await apiGetPetTypeList()
				console.log('宠物类型：',res);
				tabs.value = res.data
			} catch (error) {
				console.error('获取宠物类型失败:', error)
			}
		}
		const GetBreedList = async () => {
			try {
				const res = await apiGetBreedList(petClass.value)
					console.log('宠物列表：',res);
				if (res.data.rows.length === 0) {
					uni.showToast({
						title: '暂无数据',
						icon: 'none'
					})
					petData.value = {}
					return
				}
				const groupedData = {}

				// 先排序原始数据
				const sortedRows = [...res.data.rows].sort((a, b) => {
					return (a.cnInitials || a.petBreed.charAt(0))
						.localeCompare(b.cnInitials || b.petBreed.charAt(0), 'zh-Hans-CN')
				})

				// 再分组
				sortedRows.forEach(item => {
					const initial = item.cnInitials || item.petBreed.charAt(0).toUpperCase()
					if (!groupedData[initial]) {
						groupedData[initial] = []
					}
					groupedData[initial].push(item.petBreed)
				})

				petData.value = groupedData
			} catch (error) {
				console.error('获取宠物列表失败:', error)
			}
		}
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
			padding: 0 15px;
		}


		.hot-section {
			margin-bottom: 20px;
		}

		.section-title {
			font-size: 14px;
			color: #666;
			margin: 20rpx 0;
		}

		.hot-tags {
			display: flex;
			flex-wrap: wrap;
			gap: 10px;
		}

		.hot-tag {
			padding: 6px 12px;
			background-color: #F5F5F5;
			border-radius: 15px;
			font-size: 12px;
			color: #333;
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
			top: 55%;
			right: 12rpx;
			transform: translateY(-50%);
			z-index: 100;
			padding: 10rpx 0rpx 10rpx 0rpx;
			background: rgba(233, 233, 233, 0.9);
			// border-radius: 12px 0 0 12px;
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
			position: fixed;
			bottom: 0;
			left: 0;
			right: 0;
			padding: 15px;
			z-index: 10;
		}

		.next-btn {
			width: 50%;
			background-color: #1989FA;
			color: white;
			border-radius: 25px;
			height: 40px;
			line-height: 40px;
			font-size: 16px;
		}
	</style>