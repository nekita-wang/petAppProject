<template>
  <view class="date-picker-wrapper">
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
import { ref, onMounted, watch } from 'vue'

const props = defineProps({
  modelValue: String,
  defaultDate: {
    type: [Date, String],
    default: () => new Date()
  }
})

const emit = defineEmits(['update:modelValue', 'date-change'])

// 日期数据
const currentDate = new Date()
const years = ref([])
const year = ref(currentDate.getFullYear())
const months = ref([])
const month = ref(currentDate.getMonth() + 1)
const days = ref([])
const day = ref(currentDate.getDate())
const pickerValue = ref([0, 0, 0])
const indicatorStyle = ref('')

// 更新月份列表
const updateMonths = () => {
  months.value = []
  const maxMonth = year.value === currentDate.getFullYear() ? 
    currentDate.getMonth() + 1 : 12

  for (let i = 1; i <= maxMonth; i++) {
    months.value.push(i)
  }

  // 确保当前月份在有效范围内
  month.value = Math.min(Math.max(month.value, 1), maxMonth)
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

  // 确保当前日期在有效范围内
  day.value = Math.min(Math.max(day.value, 1), maxDay)
}

// 更新选择器值
const updatePickerValue = () => {
  pickerValue.value = [
    Math.max(0, years.value.indexOf(year.value)),
    Math.max(0, months.value.indexOf(month.value)),
    Math.max(0, days.value.indexOf(day.value))
  ]
}

// 处理日期变化
const handleDateChange = (event) => {
  const [yearIndex, monthIndex, dayIndex] = event.detail.value
  
  // 获取新值（添加默认值保护）
  const newYear = years.value[yearIndex] || currentDate.getFullYear()
  const newMonth = months.value[monthIndex] || 1
  const newDay = days.value[dayIndex] || 1

  // 记录是否需要更新月份/日期列表
  const yearChanged = year.value !== newYear
  const monthChanged = month.value !== newMonth

  // 更新值
  year.value = newYear
  month.value = newMonth
  day.value = newDay

  // 按需更新列表
  if (yearChanged) {
    updateMonths()
    updateDays()
  } else if (monthChanged) {
    updateDays()
  }

  // 更新选择器位置
  updatePickerValue()

  // 格式化日期并触发事件
  const selectedDate = `${newYear}-${String(newMonth).padStart(2,'0')}-${String(newDay).padStart(2,'0')}`
  emit('update:modelValue', selectedDate)
  emit('date-change', selectedDate)
}

// 初始化日期选择器
const initDatePicker = () => {
  // 初始化年份范围(1950-当前年份)
  years.value = Array.from({length: currentDate.getFullYear() - 1949}, (_, i) => 1950 + i)

  // 设置初始值（优先使用props.defaultDate）
  const initDate = props.defaultDate ? new Date(props.defaultDate) : new Date()
  year.value = initDate.getFullYear()
  month.value = initDate.getMonth() + 1
  day.value = initDate.getDate()

  // 初始化月份和日期列表
  updateMonths()
  updateDays()
  updatePickerValue()

  // 设置选择器样式
  indicatorStyle.value = `height: ${uni.upx2px(80)}px;`
}

// 监听默认日期变化
watch(() => props.defaultDate, (newVal) => {
  if (newVal) {
    const date = new Date(newVal)
    year.value = date.getFullYear()
    month.value = date.getMonth() + 1
    day.value = date.getDate()
    updateMonths()
    updateDays()
    updatePickerValue()
  }
})

// 组件挂载时初始化
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