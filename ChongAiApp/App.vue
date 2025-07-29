<script>
	export default {
		onLaunch: function() {
			console.log('App Launch')
			  console.log('应用启动', uni.getSystemInfoSync())
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style lang="scss">
</style>