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
				<view v-for="tab in tabs" :key="tab.petClassEn"
					:class="['tab-item', { active: activeTab === tab.petClassEn }]" @click="handleTabChange(tab)">
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
		// 宠物分类
		const tabs = ref('')
		const token = ref(
			'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImxvZ2luX3VzZXJfa2V5IjoiMWMwNTRiOTItYzQyZi00OTI0LWI1YzgtYzlmNzU3ZDUxNGVjIn0.C9rJ-jH5ZTPh7YQzDql2_O1uLlrCfaa-AcOI79cUoCpowTJ9qC3RP_JOuQ2lfkYn5BB_f75mmHA83fQ1OXseEw'
		)
		const buttonText = ref('完成') //默认按钮内容
		const activeTab = ref('none') //默认按钮
		const currentLetter = ref('letter-A') // 默认选中A字母
		const petClass = ref('')
		// 宠物数据
		// const petData = {
		// 	cat: {
		// 		'A': ['阿比西尼亚猫', '埃及猫', '澳大利亚雾猫'],
		// 		'B': ['巴厘猫', '布偶猫', '伯曼猫', '波斯猫', '褴褛猫'],
		// 		'C': ['褴褛猫', '柯尼斯卷毛猫', '卡尔特猫'],
		// 		'D': ['德文卷毛猫', '东方短毛猫', '东奇尼猫', '短腿猫'],
		// 		'E': ['俄罗斯蓝猫'],
		// 		'F': ['菲律宾无毛猫'],
		// 		'G': ['暹罗猫', '冠毛猫'],
		// 		'H': ['暹罗猫', '喜马拉雅猫', '虎斑猫'],
		// 		'J': ['金吉拉', '加菲猫', '橘猫'],
		// 		'K': ['科拉特猫'],
		// 		'L': ['临清狮猫'],
		// 		'M': ['美国短毛猫', '美国卷耳猫', '缅因猫', '孟买猫'],
		// 		'N': ['挪威森林猫', '拿破仑猫'],
		// 		'O': ['欧西猫', '奥西猫'],
		// 		'P': ['皮特博德猫'],
		// 		'Q': ['奇异猫'],
		// 		'R': ['日本短尾猫', '热带草原猫'],
		// 		'S': ['苏格兰折耳猫', '斯芬克斯猫', '山东狮子猫'],
		// 		'T': ['土耳其安哥拉猫', '土耳其梵猫'],
		// 		'W': ['威尔士猫'],
		// 		'X': ['新加坡猫'],
		// 		'Y': ['英国短毛猫', '异国短毛猫', '云南森林猫'],
		// 		'Z': ['中国狸花猫', '折耳猫']
		// 	},
		// 	dog: {
		// 		'A': ['阿拉斯加犬', '阿富汗猎犬', '澳大利亚牧牛犬', '阿根廷杜高犬'],
		// 		'B': ['比格犬', '边境牧羊犬', '波士顿梗', '比利时牧羊犬', '巴哥犬', '贝灵顿梗'],
		// 		'C': ['柴犬', '藏獒', '查理王小猎犬', '大白熊犬', '川东猎犬'],
		// 		'D': ['杜宾犬', '大丹犬', '斗牛犬', '德国牧羊犬', '杜高犬'],
		// 		'E': ['俄罗斯黑梗', '恶霸犬'],
		// 		'F': ['法国斗牛犬', '法老王猎犬'],
		// 		'G': ['贵宾犬', '格力犬', '高加索牧羊犬', '冠毛犬'],
		// 		'H': ['哈士奇', '惠比特犬', '蝴蝶犬', '哈巴狗'],
		// 		'J': ['金毛犬', '吉娃娃', '杰克罗素梗', '京巴犬'],
		// 		'K': ['柯基犬', '可卡犬', '昆明犬'],
		// 		'L': ['拉布拉多', '罗威纳犬', '腊肠犬'],
		// 		'M': ['马耳他犬', '迷你杜宾', '牧羊犬'],
		// 		'N': ['牛头梗', '挪威猎鹿犬'],
		// 		'O': ['欧亚大陆犬'],
		// 		'P': ['彭布罗克威尔士柯基', '平毛寻回犬'],
		// 		'Q': ['秋田犬', '骑士查理王小猎犬'],
		// 		'R': ['日本柴犬', '瑞典瓦赫德犬'],
		// 		'S': ['萨摩耶', '松狮犬', '沙皮犬', '圣伯纳犬'],
		// 		'T': ['泰迪犬', '土佐犬', '泰皇犬'],
		// 		'W': ['威尔士柯基', '万能梗'],
		// 		'X': ['西施犬', '雪纳瑞', '西伯利亚哈士奇'],
		// 		'Y': ['英国斗牛犬', '约克夏梗', '意大利灵缇'],
		// 		'Z': ['中国冠毛犬', '藏獒', '中亚牧羊犬']
		// 	}
		// }
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
		const handleTabChange = (tab) => {
			petClass.value = tab.petClass
			activeTab.value = tab.petClassEn
			if (activeTab.value != 'none') {
				buttonText.value = '下一个'
			} else {
				buttonText.value = '完成'
			}
			// 获取宠物品种
			uni.request({
				// url: 'http://localhost/dev-api/breed/list',
				url:'http://192.168.0.224:8080/breed/list',
				method: 'GET',
				header: {
					'Authorization': `Bearer ${token.value}`
				},
				data: {
					petClass: petClass.value,
					pageNum: 1,
					pageSize: 9999, // 模拟获取全部
				},
				success: (res) => {
					if (res.data.rows.length === 0) {
					  uni.showToast({ title: '暂无数据', icon: 'none' })
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
				}
			})
		}
		//点击字母显示高亮
		const scrollToLetter = (letter) => {
			currentLetter.value = `letter-${letter}`
		}
		// 获取宠物分类
		uni.request({
			// url: `http://localhost/dev-api/petType/list`,
			url: 'http://192.168.0.224:8080/petType/list',
			method: 'GET',
			header: {
				'Authorization': `Bearer ${token.value}`
			},
			success: (res) => {
				console.log(res);
				tabs.value = res.data.rows
				tabs.value.unshift({
					petClass: '尚未养宠',
					petClassEn: 'none'
				})
			}
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