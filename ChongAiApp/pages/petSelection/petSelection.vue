<template>
	<view class="pet-selection-container">
		<!-- 自定义导航栏 -->
		<up-navbar title="您养的宠物（1/2）" rightText="跳过" @leftClick="customBack" @rightClick="handleSkip" fixed></up-navbar>
		<view class="content-no-roll" :style="contentStyle">
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
						cancelButton="none" @confirm="petSearch" v-model="petBreed" @clear="handleClearSearch" />
				</view>
				<!-- 热门品种 -->
				<view class="hot-section" v-if="hotPets">
					<view class="section-title">热门:</view>
					<view class="hot-tags">
						<text v-for="(pet, index) in hotPets" :key="index" class="hot-tag"
							@click="checkPet(pet,petClass)">
							{{ pet }}
						</text>
					</view>
				</view>
			</view>
		</view>
		<!-- 字母分类列表 -->
		<view class="alphabet-section">
			<scroll-view
				class="alphabet-list"
				:show-scrollbar="false"
				scroll-y
				:scroll-into-view="currentLetter"
				:scroll-with-animation="true"
				@scroll="onScroll"
			>
				<view
					v-for="(pets, letter) in petData"
					:key="letter"
					:id="`letter-${letter}`"
				>
					<text class="letter-title">{{ letter }}</text>
					<view
						v-for="(pet, index) in pets"
						:key="index"
						class="pet-item"
						@click="checkPet(pet.petBreed, petClass)"
					>
						{{ pet.petBreed }}
					</view>
				</view>
			</scroll-view>
			<!-- 字母导航（右侧固定定位） -->
			<view class="alphabet-nav-wrapper" v-if="!showBtn">
				<view class="alphabet-nav">
					<text v-for="letter in Object.keys(petData)" :key="letter"
						:class="['alphabet-char', { active: highlightLetter === `letter-${letter}` }]"
						@click="scrollToLetter(letter)">
						{{ letter }}
					</text>
				</view>
			</view>
		</view>
		<!-- 固定底部按钮 -->
		<view class="fixed-footer" v-if="showBtn">
			<up-button type="primary" shape="circle" hair-line="false" @click="handleSkip">完成</up-button>
		</view>
	</view>
</template>

<script setup lang="ts">
	import {
		ref,
		onMounted,
		computed,
		nextTick
	} from 'vue'
	import {
		request
	} from '../../utils/request'

	const tabs = ref([]) // 宠物分类
	const activeTab = ref('0') //默认尚无养宠
	const currentLetter = ref(''); // 用于 scroll-into-view
	const highlightLetter = ref(''); // 用于高亮
	const petClass = ref('') //宠物类名

	const petBreed = ref('')
	const petData = ref({})

	const hotPets = ref([]) //热门宠物
	const showBtn = ref(true) //显示按钮

	// 动态计算导航栏高度
	const navBarHeight = ref(0)
	const statusBarHeight = ref(0)
	// 返回
	const customBack = () => {
		uni.redirectTo({
			url: '/pages/login/sms'
		})
	}
	// 跳转
	const handleSkip = () => {
		uni.navigateTo({
			url: '/pages/home/home'
		})
	}
	// 初始化
	onMounted(() => {
		// 获取系统信息
		try {
			const systemInfo = uni.getSystemInfoSync()
			statusBarHeight.value = systemInfo.statusBarHeight || 0

			// 计算导航栏高度：状态栏高度 + 导航栏内容高度(44px)
			navBarHeight.value = statusBarHeight.value + 44
		} catch (error) {
			console.error('获取系统信息失败:', error)
			// 设置默认值
			statusBarHeight.value = 20
			navBarHeight.value = 64
		}

		GetPetTypeList()
	})

	// 计算内容区域样式
	const contentStyle = computed(() => ({
		marginTop: navBarHeight.value + 'px',
		top: navBarHeight.value + 'px'
	}))
	// 切换tab栏
	const handleTabChange = async (tab) => {
		petClass.value = tab.petClass;
		activeTab.value = tab.petClassId;
		petBreed.value = '';
		currentLetter.value = '';
		highlightLetter.value = '';

		if (activeTab.value === '0') {
			uni.pageScrollTo({
				scrollTop: 0,
				duration: 0
			});
			showBtn.value = true;
			return;
		}
		showBtn.value = false;

		await GetBreedList();

		// 先清空 currentLetter，nextTick 后再设置
		nextTick(() => {
			currentLetter.value = '';
			highlightLetter.value = '';
			nextTick(() => {
				const letters = Object.keys(petData.value);
				if (letters.length > 0) {
					currentLetter.value = `letter-${letters[0]}`;
					highlightLetter.value = `letter-${letters[0]}`;
					ignoreNextScroll = true;
				}
			});
		});
	};
	//点击字母显示高亮并滚动
	const scrollToLetter = (letter) => {
		isManualScroll = true;
		currentLetter.value = `letter-${letter}`;
		highlightLetter.value = `letter-${letter}`;
		setTimeout(() => {
			isManualScroll = false;
		}, 500);
	};
	//获取宠物类别列表 
	const GetPetTypeList = async () => {
		try {
			const res = await request({
				url: '/app/pet/pet',
			})
			console.log(res);
			tabs.value = res.data

		} catch (error) { 
			console.error('获取宠物类型失败:', error)
		}
	}
	// 获取宠物列表
	const GetBreedList = async () => {
		uni.showLoading({
			title: '加载中'
		});
		try {
			const res = await request({
				url: '/app/pet/breeds',
				data: {
					petClass: petClass.value,
					petBreed: petBreed.value
				},
			});
			// 处理空数据情况
			if (Object.keys(res.data.breeds).length === 0 && petBreed.value) {
				uni.showToast({
					title: `未找到"${petBreed.value}"相关品种`,
					icon: 'none',
					duration: 2000
				});
			}
			hotPets.value = res.data.hot;
			petData.value = res.data.breeds;

			// 设置默认选中的字母为数据的第一个字母
			const letters = Object.keys(res.data.breeds);
			if (letters.length > 0) {
				currentLetter.value = `letter-${letters[0]}`;
			}

			uni.hideLoading();
		} catch (error) {
			console.log(error);
			uni.showToast({
				title: '获取品种数据失败',
				icon: 'none'
			});
		}
	};

	// 搜索框
	const petSearch = () => {
		petBreed.value = petBreed.value.trim();
		if (petBreed.value) {
			GetBreedList();
		}
	}

	// 清空搜索
	const handleClearSearch = () => {
		petBreed.value = '';
		GetBreedList(); // 重新获取完整数据
	}
	// 点击宠物标签
	const checkPet = (breed, type) => {
		// 使用 uni-app 的 encodeURIComponent
		const encodedBreed = encodeURIComponent(breed);
		const encodedType = encodeURIComponent(type);

		uni.navigateTo({
			url: `/pages/petSelection/petInfo?petBreed=${encodedBreed}&petClass=${encodedType}`
		});
	}

	let isManualScroll = false;
	let ignoreNextScroll = false;

	// 监听滚动
	const onScroll = () => {
		if (isManualScroll) return;
		if (ignoreNextScroll) {
			ignoreNextScroll = false;
			return;
		}
		nextTick(() => {
			const query = uni.createSelectorQuery();
			const letters = Object.keys(petData.value);
			// 依次查询每个字母标题的位置
			letters.forEach(letter => {
				query.select(`#letter-${letter}`).boundingClientRect();
			});
			query.select('.alphabet-list').boundingClientRect();
			query.exec((res) => {
				if (!res || res.length < 2) return;
				const scrollViewRect = res[res.length - 1];
				// 找到第一个出现在顶部的字母
				let active = letters[0];
				for (let i = 0; i < letters.length; i++) {
					const rect = res[i];
					if (rect && rect.top - scrollViewRect.top <= 10) {
						active = letters[i];
					}
				}
				highlightLetter.value = `letter-${active}`;
			});
		});
	};
</script>

<style scoped lang="scss">
	.pet-selection-container {
		display: flex;
		flex-direction: column;
		min-height: 100vh; // 确保容器至少占满整个视口高度
	}

	.content-no-roll {
		width: 100%;
		position: sticky;
		z-index: 10;
		background: white;
		flex-shrink: 0; // 防止被压缩
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
		flex: 1;
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
		position: relative;
		height: 100vh;
		/* 占满整个视口 */
		overflow: hidden;
		/* 禁止内部滚动 */
		background: white;
		/* 避免透明背景导致下方内容可见 */
		touch-action: none;
		/* 禁止触摸滚动 */
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
		top: 10%;
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
		display: flex;
		flex-direction: column;
		min-height: 0;
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

		// 添加点击效果
		&:active {
			background-color: #e5e5e5;
		}
	}

	/* 字母列表 */
	.alphabet-section {
		position: relative;
		padding: 0 px;
		 height: calc(100vh - 300px);
	}

	.alphabet-list {
		flex: 1;
		min-height: 0;
		height: 100%;
		overflow-y: auto;
	}

	.letter-title {
		display: block;
		padding: 10px 5px;
		// margin-right: 50rpx;
		font-size: 16px;
		color: black;
			border-bottom: 1px solid #EEE;
		background-color: #f5f5f5;
		font-weight: bold;
	}

	.pet-item {
		padding: 12px 3px;
		font-size: 14px;
		color: #333;
		border-bottom: 1px solid #EEE;

		// 添加点击效果
		&:active {
			background-color: #e5e5e5;
		}
	}

	/* 字母导航容器 */
	.alphabet-nav-wrapper {
		position: fixed;
		top: 57%;
		right: 12rpx;
		transform: translateY(-50%);
		z-index: 100;
		padding: 10rpx 0rpx 10rpx 0rpx;
		background: rgba(233, 233, 233, 0.9);
		border-radius: 20rpx;
		box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.1);
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
		position: fixed;
		bottom: 1%;
		left: 25%;
		// right: 0;
		z-index: 10;
	}
</style>