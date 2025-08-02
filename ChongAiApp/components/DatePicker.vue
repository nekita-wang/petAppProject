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

<script setup lang="ts">
import { ref, onMounted, watch, nextTick } from 'vue'

// 类型定义
interface DatePickerProps {
  modelValue?: string
  defaultDate?: Date | string
}

interface DateChangeEvent {
  detail: {
    value: number[]
  }
}

// Props 定义
const props = withDefaults(defineProps<DatePickerProps>(), {
  modelValue: '',
  defaultDate: () => new Date('2000-06-06')
})

// Emits 定义
const emit = defineEmits<{
  'update:modelValue': [value: string]
  'date-change': [value: string]
}>()

// 数据定义
const currentDate = new Date()
const years = ref<number[]>(Array.from({length: currentDate.getFullYear() - 1949}, (_, i) => 1950 + i))
const months = ref<number[]>([])
const days = ref<number[]>([])
const year = ref<number>(2000) // 显式设置初始值
const month = ref<number>(6)
const day = ref<number>(6)
const pickerValue = ref<number[]>([])
const indicatorStyle = `height: ${uni.upx2px(80)}px;`

// 同步日期数据
const syncDateData = (): void => {
  const date = props.defaultDate ? new Date(props.defaultDate) : new Date('2000-06-06')
  year.value = date.getFullYear()
  month.value = date.getMonth() + 1
  day.value = date.getDate()
  
  // 更新关联数据
  updateMonths()
  updateDays()
  updatePickerValue()
}

// 更新月份列表
const updateMonths = (): void => {
  const maxMonth = year.value === currentDate.getFullYear() ? 
    currentDate.getMonth() + 1 : 12
  months.value = Array.from({length: maxMonth}, (_, i) => i + 1)
  month.value = Math.min(month.value, maxMonth)
}

// 更新日期列表
const updateDays = (): void => {
  let maxDay = new Date(year.value, month.value, 0).getDate()
  if (year.value === currentDate.getFullYear() && month.value === currentDate.getMonth() + 1) {
    maxDay = currentDate.getDate()
  }
  days.value = Array.from({length: maxDay}, (_, i) => i + 1)
  day.value = Math.min(day.value, maxDay)
}

// 更新选择器位置
const updatePickerValue = async (): Promise<void> => {
  await nextTick()
  pickerValue.value = [
    years.value.indexOf(year.value),
    months.value.indexOf(month.value),
    days.value.indexOf(day.value)
  ]
}

// 日期变化处理
const handleDateChange = (e: DateChangeEvent): void => {
  const [yIdx, mIdx, dIdx] = e.detail.value
  year.value = years.value[yIdx]
  month.value = months.value[mIdx]
  day.value = days.value[dIdx]
  
  // 联动更新
  if (yIdx !== pickerValue.value[0]) updateMonths()
  if (mIdx !== pickerValue.value[1]) updateDays()
  
  updatePickerValue()
  
  const formattedDate = [
    year.value,
    String(month.value).padStart(2, '0'),
    String(day.value).padStart(2, '0')
  ].join('-')
  
  emit('update:modelValue', formattedDate)
  emit('date-change', formattedDate)
}

// 初始化
onMounted((): void => {
  syncDateData()
  
  // 双重保险：500ms后再次同步
  setTimeout(() => {
    syncDateData()
  }, 500)
})

// 监听默认值变化
watch(() => props.defaultDate, () => {
  syncDateData()
}, { immediate: true })
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