<template>
  <div class="home-page">
    <!-- 顶部Header -->
    <el-header>
      <div class="logo">宠爱平台</div>
    </el-header>

    <!-- 主体内容 -->
    <el-main>
      <!-- 数据看板 -->
      <div class="dashboard-cards">
        <el-card class="stat-card" shadow="always">
          <div slot="header" class="stat-card-header">宠物分类总数</div>
          <div class="card-content">{{ totalPetClasses }}</div>
        </el-card>

        <el-card class="stat-card" shadow="always">
          <div slot="header" class="stat-card-header">用户数量</div>
          <div class="card-content">{{ totalUsers }}</div>
        </el-card>

        <el-card class="stat-card" shadow="always">
          <div slot="header" class="stat-card-header">最近登录用户</div>
          <div class="card-content">{{ lastLoginUser }}</div>
        </el-card>

        <el-card class="stat-card" shadow="always">
          <div slot="header" class="stat-card-header">宠物品种数量</div>
          <div class="card-content">{{ petBreedsCount }}</div>
        </el-card>
      </div>

      <!-- 最新公告 -->
      <el-card class="mb-20">
        <div slot="header" class="news-header">最新公告</div>
        <div class="news-content">
          <p>欢迎来到宠爱平台！我们已完成系统优化，提升数据加载效率。</p>
        </div>
      </el-card>

      <!-- 系统状态 -->
      <el-card>
        <div slot="header" class="system-status-header">系统状态</div>
        <div class="system-status-content">
          <p>数据库连接状态：<span :class="dbStatusClass">{{ dbStatus }}</span></p>
          <p>服务运行时长：<span>{{ formatRunTime(runTime) }}</span></p>
        </div>
      </el-card>
    </el-main>

    <!-- 底部Footer -->
    <el-footer>
      <div class="footer-text">宠爱平台 © 2025</div>
    </el-footer>
  </div>
</template>

<script>
// 导入封装的API
import { getHomeStatistics, select } from '@/api/home/home';

export default {
  data() {
    return {
      totalPetClasses: 0,
      totalUsers: 0,
      lastLoginUser: '',
      petBreedsCount: 0,
      dbStatus: '正常',
      runTime: 0,
    };
  },
  computed: {
    dbStatusClass() {
      return this.dbStatus === '正常' ? 'status-normal' : 'status-error';
    }
  },
  mounted() {
    this.fetchData();
    this.simulateSystemStatus();
  },
  methods: {
    // 使用封装的API获取数据
    async fetchData() {
      try {
        // 1. 获取首页统计数据（合并了原有的两个请求）
        const row  = await getHomeStatistics();
        this.totalPetClasses = row.data;
       
        
        // 2. 获取宠物分类列表（如果需要完整列表）
        const { data } = await select();
         this.totalUsers = data.totalUsers;
        this.lastLoginUser = data.lastLoginUser;
        this.petBreedsCount = data.petBreedsCount;
      } catch (error) {
        console.error('获取数据失败:', error);
      }
    },
    // 保持原有方法不变
    simulateSystemStatus() {
      setInterval(() => {
        this.runTime += 1;
      }, 1000);
    },
    formatRunTime(seconds) {
      const hours = Math.floor(seconds / 3600);
      const minutes = Math.floor((seconds % 3600) / 60);
      const secs = seconds % 60;
      return `${hours}小时${minutes}分钟${secs}秒`;
    }
  }
};
</script>

<style scoped>
/* 保持原有样式不变 */



.home-page {
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.el-header {
  background-color: #fff;
  color: #333;
  padding: 15px;
  text-align: center;
  font-size: 24px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.dashboard-cards {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  margin: 20px 0;
  justify-content: center;
}

.stat-card {
  width: 280px;
  min-width: 200px;
  padding: 20px;
  background-color: #f9f9f9;
  text-align: center;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

.stat-card-header {
  font-size: 18px;
  color: #42b983;
  margin-bottom: 10px;
}

.card-content {
  font-size: 28px;
  color: #42b983;
}

.el-card {
  margin-bottom: 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

.news-header,
.system-status-header {
  font-size: 18px;
  color: #42b983;
  margin-bottom: 10px;
}

.news-content,
.system-status-content {
  font-size: 16px;
  color: #666;
  padding: 20px;
  line-height: 1.6;
}

.status-normal {
  color: #42b983; 
}

.status-error {
  color: #f56c6c; 
}

.el-footer {
  text-align: center;
  padding: 10px;
  background-color: #f1f1f1;
  margin-top: auto;
}
</style>