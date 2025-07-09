<template>
  <div class="home-page">
    <!-- Header Section -->
    <el-header>
      <div class="logo">宠爱平台</div>
    </el-header>

    <!-- Main Content Section -->
    <el-main>
      <!-- Statistics Cards -->
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
      </div>

      <!-- Pet Breed Distribution Chart -->
      <el-card>
        <div slot="header" class="chart-title">宠物品种分布</div>
        <div class="chart-container" id="chart"></div>
      </el-card>
    </el-main>

    <!-- Footer Section -->
    <el-footer>
      <div class="footer-text">宠爱平台 © 2025</div>
    </el-footer>
  </div>
</template>

<script>
import axios from 'axios';
import * as echarts from 'echarts';

export default {
  data() {
    return {
      totalPetClasses: 0,
      totalUsers: 0,
      lastLoginUser: '',
    };
  },
  mounted() {
    this.fetchData();
    this.initChart();
  },
  methods: {
    fetchData() {
      axios.get('http://localhost:8080/api/pet-classes')
        .then(response => {
          this.totalPetClasses = response.data.data.length;
        })
        .catch(error => {
          console.error('Error fetching pet classes:', error);
        });

      // 假设有另一个API端点提供用户数量和最近登录用户信息
      axios.get('http://localhost:8080/api/users')
        .then(response => {
          this.totalUsers = response.data.totalUsers;
          this.lastLoginUser = response.data.lastLoginUser;
        })
        .catch(error => {
          console.error('Error fetching user information:', error);
        });
    },
    initChart() {
      const myChart = echarts.init(document.getElementById('chart'));
      const option = {
        title: {
          text: '宠物品种分布',
          textStyle: {
            fontWeight: 'normal',
            fontSize: 16,
            color: '#333',
          },
        },
        tooltip: {},
        xAxis: {
          data: ['猫', '狗', '鸟', '鱼'],
        },
        yAxis: {},
        series: [
          {
            name: '数量',
            type: 'bar',
            data: [5, 10, 2, 7],
          },
        ],
      };
      myChart.setOption(option);
    },
  },
};
</script>

<style scoped>
.home-page {
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.el-header {
  background-color: #ffffff;
  color: #333;
  padding: 15px;
  text-align: center;
  font-size: 24px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.dashboard-cards {
  display: flex;
  justify-content: space-evenly;
  margin: 20px 0;
}

.stat-card {
  width: 30%;
  padding: 20px;
  background-color: #f9f9f9;
  text-align: center;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
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

.chart-title {
  font-size: 18px;
  color: #42b983;
  margin-bottom: 20px;
}

.chart-container {
  width: 100%;
  height: 350px;
  background-color: #f4f4f4;
}

.el-footer {
  text-align: center;
  padding: 10px;
  background-color: #f1f1f1;
  margin-top: auto;
}
</style>