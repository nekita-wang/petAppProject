<template>
	<view class="date-picker-wrapper">
		<!-- 日期选择器 -->
		<picker-view class="picker" :value="pickerValue" @change="handleDateChange" :indicator-style="indicatorStyle">
			<picker-view-column>
				<view class="item" v-for="year in years" :key="year">{{year}}年</view>
			</picker-view-column>
			<picker-view-column>
				<view class="item" v-for="month in months" :key="month">{{month}}月</view>
			</picker-view-column>
			<picker-view-column>
				<view class="item" v-for="day in days" :key="day">{{day}}日</view>
			</picker-view-column>
		</picker-view>
	</view>
</template>

<script setup>
	import {
		ref,
		computed,
		onMounted
	} from 'vue'
	const props = defineProps({
		modelValue: String,
		defaultDate: {
			type: Date,
			default: () => new Date()
		}
	})
	
	const emit = defineEmits(['update:modelValue', 'date-change']) // 新增date-change事件
	
	// 日期选择器相关
	const indicatorStyle = ref('')
	const currentDate = new Date
	const years = ref([])
	const year = ref(currentDate.getFullYear())
	const months = ref([])
	const month = ref(currentDate.getMonth() + 1)
	const days = ref([])
	const day = ref(currentDate.getDate())
	const pickerValue = ref([])

	// 更新月份列表
	const updateMonths = () => {
		months.value = []
		const maxMonth = year.value === currentDate.getFullYear() ?
			currentDate.getMonth() + 1 :
			12

		for (let i = 1; i <= maxMonth; i++) {
			months.value.push(i)
		}

		// 调整当前选择的月份
		if (month.value > maxMonth) {
			month.value = maxMonth
		}
	}

	// 更新日期列表
	const updateDays = () => {
		days.value = []
		let maxDay = new Date(year.value, month.value, 0).getDate()

		// 如果是当前年月，则最大天数不超过当前日
		if (year.value === currentDate.getFullYear() && month.value === currentDate.getMonth() + 1) {
			maxDay = currentDate.getDate()
		}

		for (let i = 1; i <= maxDay; i++) {
			days.value.push(i)
		}

		// 调整当前选择的天数
		if (day.value > maxDay) {
			day.value = maxDay
		}
	}

	// 初始化日期选择器
	const initDatePicker = () => {
		// 年份范围(1950-当前年份)
		for (let i = 1950; i <= currentDate.getFullYear(); i++) {
			years.value.push(i)
		}

		// 初始化月份
		updateMonths()

		// 初始化日期
		updateDays()

		// 设置初始选择器值
		const yearIndex = years.value.indexOf(year.value)
		const monthIndex = months.value.indexOf(month.value)
		const dayIndex = days.value.indexOf(day.value)
		pickerValue.value = [74, 5, 5]  //选择器默认值
		// 设置选择器样式
		indicatorStyle.value = `height: ${uni.upx2px(80)}px;`
	}

	// 处理日期变化
	const handleDateChange = (event) => {
		const [yearIndex, monthIndex, dayIndex] = event.detail.value

		// 更新年份
		year.value = years.value[yearIndex]

		// 更新月份列表（如果年份变化）
		if (yearIndex !== pickerValue.value[0]) {
			updateMonths()
			month.value = months.value[Math.min(monthIndex, months.value.length - 1)]
		}

		// 更新月份
		month.value = months.value[monthIndex]

		// 更新日期列表
		updateDays()

		// 更新选择的日期
		day.value = days.value[Math.min(dayIndex, days.value.length - 1)]

		// 更新选择器位置
		pickerValue.value = [
			years.value.indexOf(year.value),
			months.value.indexOf(month.value),
			days.value.indexOf(day.value)
		]
		
		const selectedDate = `${year.value}-${String(month.value).padStart(2,'0')}-${String(day.value).padStart(2,'0')}` //日期格式化
		emit('date-change', selectedDate)
	}

	onMounted(() => {
		initDatePicker()
	})
</script>

<style scoped>
	.date-picker-wrapper {
		display: flex;
		align-items: center;
		width: 75%;
	}

	.date-display {
		min-width: 200rpx;
		font-size: 32rpx;
	}

	.picker {
		flex: 1;
		height: 200rpx;
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
		background-color: #f4f4f4;
	}
</style>