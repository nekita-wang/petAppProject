import { ref, onMounted, computed } from 'vue'

export interface NavBarInfo {
	navBarHeight: number
	statusBarHeight: number
}

export const useNavBar = () => {
	const navBarHeight = ref<number>(0)
	const statusBarHeight = ref<number>(0)
	
	const getNavBarInfo = (): void => {
		try {
			// 获取系统信息
			const systemInfo = uni.getSystemInfoSync()
			statusBarHeight.value = systemInfo.statusBarHeight || 0
			
			// APP端导航栏高度：状态栏高度 + 44px（导航栏内容高度）
			navBarHeight.value = statusBarHeight.value + 44
		} catch (error) {
			console.error('获取导航栏信息失败:', error)
			// 设置默认值
			statusBarHeight.value = 20
			navBarHeight.value = 64
		}
	}
	
	// 计算内容区域样式
	const contentStyle = computed(() => ({
		paddingTop: `${navBarHeight.value}px`
	}))
	
	// 计算安全区域样式
	const safeAreaStyle = computed(() => ({
		paddingTop: `${statusBarHeight.value}px`
	}))
	
	onMounted(() => {
		getNavBarInfo()
	})
	
	return {
		navBarHeight,
		statusBarHeight,
		contentStyle,
		safeAreaStyle,
		getNavBarInfo
	}
}