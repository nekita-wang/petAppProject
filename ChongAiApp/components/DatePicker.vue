<template>
	<view class="date-picker-wrapper">
		<picker-view class="picker" :value="dateData.pickerValue" @change="handleDateChange"
			:indicator-style="'height: 80rpx;'">
			<picker-view-column>
				<view class="item" v-for="year in dateData.years" :key="year">{{year}}年</view>
			</picker-view-column>
			<picker-view-column>
				<view class="item" v-for="month in dateData.months" :key="month">{{month}}月</view>
			</picker-view-column>
			<picker-view-column>
				<view class="item" v-for="day in dateData.days" :key="day">{{day}}日</view>
			</picker-view-column>
		</picker-view>
	</view>
</template>

<script setup lang="ts">
	import { ref, reactive, onMounted, watch } from 'vue'

	//Props类型
	interface Props {
		modelValue ?: string
		defaultDate ?: Date | string | number
	}
	//数据类型定义
	interface DateData {
		years : number[]
		months : number[]
		days : number[]
		selectedYear : number
		selectedMonth : number
		selectedDay : number
		pickerValue : number[]
	}

	// 定义事件类型
	interface DateChangeEvent {
		detail : {
			value : number[]
		}
	}
	
	const props = withDefaults(defineProps<Props>(), {
		modelValue: '',
		defaultDate: () => Date.now()
	})

	const emit = defineEmits<{
		'update:modelValue' : [value: string]
		'dateChange' : [value: string]
	}>()

	// 基础数据
	const currentDate = new Date()

	const dateData = reactive<DateData>({
		years: [],
		months: [],
		days: [],
		selectedYear: 2000,
		selectedMonth: 6,
		selectedDay: 6,
		pickerValue: [0, 0, 0]
	})


	// 生成年份列表
	const generateYears = () : void => {
		const startYear = 1950
		const endYear = currentDate.getFullYear()
		dateData.years = Array.from({ length: endYear - startYear + 1 }, (_, i) => startYear + i)
	}

	// 生成月份列表
	const generateMonths = () : void => {
		const maxMonth = dateData.selectedYear === currentDate.getFullYear() ?
			currentDate.getMonth() + 1 : 12
		dateData.months = Array.from({ length: maxMonth }, (_, i) => i + 1)
	}

	// 生成日期列表
	const generateDays = () : void => {
		// 获取当月最大天数
		const maxDay = new Date(dateData.selectedYear, dateData.selectedMonth, 0).getDate()

		// 如果是当前年月，限制到当前日期
		const limitDay = dateData.selectedYear === currentDate.getFullYear() &&
			dateData.selectedMonth === currentDate.getMonth() + 1 ?
			currentDate.getDate() : maxDay

		dateData.days = Array.from({ length: limitDay }, (_, i) => i + 1)
	}

	// 更新选择器位置
	const updatePickerPosition = () : void => {
		const yearIndex = dateData.years.indexOf(dateData.selectedYear)
		const monthIndex = dateData.months.indexOf(dateData.selectedMonth)
		const dayIndex = dateData.days.indexOf(dateData.selectedDay)

		dateData.pickerValue = [
			yearIndex >= 0 ? yearIndex : 0,
			monthIndex >= 0 ? monthIndex : 0,
			dayIndex >= 0 ? dayIndex : 0
		]
	}

	// 初始化日期数据
	const initDateData = () : void => {
		// 解析默认日期
		const defaultDate = new Date(props.defaultDate || '2000-06-06')

		dateData.selectedYear = defaultDate.getFullYear()
		dateData.selectedMonth = defaultDate.getMonth() + 1
		dateData.selectedDay = defaultDate.getDate()

		// 生成所有数据
		generateYears()
		generateMonths()
		generateDays()
		updatePickerPosition()

	}



	// 处理日期变化
	const handleDateChange = (e : DateChangeEvent) : void => {
		const [yearIndex, monthIndex, dayIndex] = e.detail.value

		// 更新选中的日期
		dateData.selectedYear = dateData.years[yearIndex]
		dateData.selectedMonth = dateData.months[monthIndex]
		dateData.selectedDay = dateData.days[dayIndex]

		// 如果年份或月份变化，需要重新生成数据
		if (yearIndex !== dateData.pickerValue[0]) {
			generateMonths()
			generateDays()
		} else if (monthIndex !== dateData.pickerValue[1]) {
			generateDays()
		}

		// 更新选择器位置
		updatePickerPosition()

		// 格式化日期并发送事件
		const formattedDate = [
			dateData.selectedYear,
			String(dateData.selectedMonth).padStart(2, '0'),
			String(dateData.selectedDay).padStart(2, '0')
		].join('-')

		emit('update:modelValue', formattedDate)
		emit('dateChange', formattedDate)
	}

	// 组件挂载时初始化
	onMounted(() => {
		initDateData()
	})

	// 监听默认日期变化
	watch(() => props.defaultDate, () => {
		initDateData()
	}, { immediate: true })
</script>

<style scoped>
	.date-picker-wrapper {
		display: flex;
		align-items: center;
		width: 75%;
		min-height: 200rpx;
		/* 添加最小高度 */
		background-color: #f8f8f8;
		/* 添加背景色便于调试 */
	}

	.picker {
		flex: 1;
		height: 200rpx;
	}

	picker-view {
		width: 100%;
		height: 100%;
	}

	.item {
		line-height: 80rpx;
		text-align: center;
		font-size: 32rpx;
		color: #666;
	}

	picker-view-column view.item {
		font-weight: bold;
		color: black;
		background-color: #f4f4f4;
	}
</style>