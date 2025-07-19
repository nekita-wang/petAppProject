import App from './App'
import 'url-search-params-polyfill';
// #ifndef VUE3
import Vue from 'vue'
import './uni.promisify.adaptor'
import polyfills from '@/utils/polyfills.js.js'

Vue.config.productionTip = false
App.mpType = 'app'
const app = new Vue({
	...App
})
app.$mount()
// #endif

// #ifdef VUE3
import {
	createSSRApp
} from 'vue'
import {
	createPinia
} from 'pinia'	
import uviewPlus from 'uview-plus'
export function createApp() {
	const app = createSSRApp(App)
	const pinia = createPinia()
	app.use(pinia)
	app.use(uviewPlus)
	return {
		app,
		pinia
	}
}
// #endif