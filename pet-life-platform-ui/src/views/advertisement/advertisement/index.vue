<template>
  <div class="app-container">
    <!-- 统计卡片 -->
    <el-row :gutter="20" class="statistics-cards" style="margin-bottom: 20px;">
      <el-col :span="6">
        <el-card class="box-card">
          <div class="statistic-item">
            <div class="statistic-title">总广告数</div>
            <div class="statistic-value">{{ statistics.totalAds || 0 }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="box-card">
          <div class="statistic-item">
            <div class="statistic-title">运行中</div>
            <div class="statistic-value" style="color: #67C23A;">{{ statistics.runningAds || 0 }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="box-card">
          <div class="statistic-item">
            <div class="statistic-title">总点击量</div>
            <div class="statistic-value" style="color: #409EFF;">{{ statistics.totalClicks || 0 }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="box-card">
          <div class="statistic-item">
            <div class="statistic-title">总收入(元)</div>
            <div class="statistic-value" style="color: #E6A23C;">{{ statistics.totalRevenue || 0 }}</div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="广告位" prop="adPosition">
        <el-select v-model="queryParams.adPosition" placeholder="请选择广告位" clearable>
          <el-option label="广告位1" value="1" />
          <el-option label="广告位2" value="2" />
          <el-option label="广告位3" value="3" />
          <el-option label="广告位4" value="4" />
          <el-option label="广告位5" value="5" />
          <el-option label="广告位6" value="6" />
        </el-select>
      </el-form-item>
      <el-form-item label="广告名" prop="adName">
        <el-input v-model="queryParams.adName" placeholder="请输入广告名" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="品牌方" prop="brand">
        <el-input v-model="queryParams.brand" placeholder="请输入品牌方" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option label="运行中" value="1" />
          <el-option label="已结清" value="2" />
          <el-option label="已失效" value="3" />
        </el-select>
      </el-form-item>
      <el-form-item label="是否结清" prop="cleard">
        <el-select v-model="queryParams.cleard" placeholder="请选择是否结清" clearable>
          <el-option label="未结清" value="0" />
          <el-option label="已结清" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['advertisement:advertisement:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['advertisement:advertisement:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['advertisement:advertisement:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['advertisement:advertisement:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="advertisementList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="广告位" align="center" prop="adPosition" width="80">
        <template slot-scope="scope">
          <span>{{ scope.row.adPosition }}</span>
        </template>
      </el-table-column>
      <el-table-column label="广告名" align="center" prop="adName" :show-overflow-tooltip="true" />
      <el-table-column label="品牌方" align="center" prop="brand" :show-overflow-tooltip="true" />
      <el-table-column label="状态" align="center" prop="status" width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.advertisement_status" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="点击量" align="center" prop="clickCount" width="80" />
      <el-table-column label="是否结清" align="center" prop="cleard" width="90">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.advertisement_cleard" :value="scope.row.cleard" />
        </template>
      </el-table-column>
      <el-table-column label="收入(元)" align="center" prop="adRevenue" width="100" />
      <el-table-column label="广告开始时间" align="center" prop="adStartTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.adStartTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="广告结束时间" align="center" prop="adEndTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.adEndTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建人" align="center" prop="creator" width="80" />
      <el-table-column label="最后更新时间" align="center" prop="lastUpdateTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lastUpdateTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最后更新人" align="center" prop="lastUpdater" width="100" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['advertisement:advertisement:edit']" v-if="scope.row.status === 1">编辑</el-button>
          <el-button size="mini" type="text" icon="el-icon-money" @click="handleClearance(scope.row)"
            v-hasPermi="['advertisement:advertisement:edit']"
            v-if="scope.row.status === 1 && scope.row.cleard === 0">结清</el-button>
          <el-button size="mini" type="text" icon="el-icon-close" @click="handleInvalidate(scope.row)"
            v-hasPermi="['advertisement:advertisement:edit']" v-if="scope.row.status === 1">失效</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['advertisement:advertisement:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改广告信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="广告位" prop="adPosition">
              <el-select v-model="form.adPosition" placeholder="请选择广告位" style="width: 100%">
                <el-option label="广告位1" value="1" />
                <el-option label="广告位2" value="2" />
                <el-option label="广告位3" value="3" />
                <el-option label="广告位4" value="4" />
                <el-option label="广告位5" value="5" />
                <el-option label="广告位6" value="6" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择状态" style="width: 100%">
                <el-option label="运行中" :value="1" />
                <el-option label="已结清" :value="2" />
                <el-option label="已失效" :value="3" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="广告名" prop="adName">
              <el-input v-model="form.adName" placeholder="请输入广告名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="品牌方" prop="brand">
              <el-input v-model="form.brand" placeholder="请输入品牌方" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="目标链接" prop="targetUrl">
          <el-input v-model="form.targetUrl" placeholder="请输入目标链接（https格式）" />
        </el-form-item>
        <!-- 广告图片 -->
        <el-row>
          <el-col :span="24">
            <el-form-item label="广告图片" prop="adImage">
              <AdvertisementImageUpload v-model="form.adImage" :crop-width="400" :crop-height="240" />
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 广告投放时间（位于广告图片下方，独占一行） -->
        <el-row>
          <el-col :span="24">
            <el-form-item label="广告投放" prop="adTimeRange">
              <el-date-picker v-model="form.adTimeRange" type="datetimerange" range-separator="~"
                start-placeholder="开始时间" end-placeholder="结束时间" value-format="yyyy-MM-dd HH:mm:ss"
                format="yyyy-MM-dd HH:mm:ss" style="width: 100%" :picker-options="pickerOptions">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="cancel">取消</el-button>
      </div>
    </el-dialog>

    <!-- 结清广告收入对话框 -->
    <el-dialog title="结清广告收入" :visible.sync="clearanceOpen" width="500px" append-to-body>
      <el-form ref="clearanceForm" :model="clearanceForm" :rules="clearanceRules" label-width="120px">
        <el-form-item label="收入 (CNY):" prop="adRevenue">
          <el-input v-model="clearanceForm.adRevenue" placeholder="请输入收入金额" />
        </el-form-item>
        <el-form-item label="上传打款截图:" prop="revenueAttachment">
          <SimpleImageUpload v-model="clearanceForm.revenueAttachment" title="上传打款截图" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitClearance">确认</el-button>
        <el-button @click="cancelClearance">取消</el-button>
      </div>
    </el-dialog>

    <!-- 失效广告确认对话框 -->
    <el-dialog title="失效广告确认" :visible.sync="invalidateOpen" width="400px" append-to-body>
      <div style="text-align: center; padding: 20px;">
        <p>确认是否失效此行广告</p>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitInvalidate">确认</el-button>
        <el-button @click="cancelInvalidate">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAdvertisement, getAdvertisement, delAdvertisement, addAdvertisement, updateAdvertisement, exportAdvertisement, invalidateAdvertisement, clearanceAdvertisement, getAdvertisementStatistics } from "@/api/advertisement/advertisement";

export default {
  name: "Advertisement",
  dicts: ['advertisement_status', 'advertisement_cleard'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 广告信息表格数据
      advertisementList: [],
      // 统计数据
      statistics: {},
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示结清弹出层
      clearanceOpen: false,
      // 是否显示失效弹出层
      invalidateOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        adPosition: null,
        adName: null,
        brand: null,
        status: null,
        cleard: null,
        adStartTime: null,
        adEndTime: null
      },
      // 表单参数
      form: {},
      // 结清表单参数
      clearanceForm: {},
      // 当前行ID
      currentId: null,
      // 失效广告ID
      invalidateId: null,
      // 表单校验
      rules: {
        adPosition: [
          { required: true, message: "广告位标识不能为空", trigger: "change" }
        ],
        adName: [
          { required: true, message: "广告自定义名称不能为空", trigger: "blur" }
        ],
        brand: [
          { required: true, message: "品牌方名称不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ],
        targetUrl: [
          { required: true, message: "广告目标链接不能为空", trigger: "blur" },
          { pattern: /^https:\/\//, message: "目标链接必须以https://开头", trigger: "blur" }
        ],
        adImage: [
          { required: true, message: "广告图片不能为空", trigger: "blur" }
        ],
        adStartTime: [
          { required: true, message: "广告开始时间不能为空", trigger: "blur" }
        ],
        adEndTime: [
          { required: true, message: "广告结束时间不能为空", trigger: "blur" }
        ],
        adTimeRange: [
          { required: true, message: "广告投放时间不能为空", trigger: "blur" }
        ]
      },
      // 结清表单校验
      clearanceRules: {
        adRevenue: [
          { required: true, message: "收入金额不能为空", trigger: "blur" }
        ],
        revenueAttachment: [
          { required: true, message: "打款截图不能为空", trigger: "blur" }
        ]
      },
      // 时间选择器配置
      pickerOptions: {
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }]
      }
    };
  },
  created() {
    this.getList();
    this.getStatistics();
  },
  methods: {
    /** 查询广告信息列表 */
    getList() {
      this.loading = true;
      listAdvertisement(this.queryParams).then(response => {
        this.advertisementList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 获取统计数据 */
    getStatistics() {
      getAdvertisementStatistics().then(response => {
        this.statistics = response.data;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        adPosition: null,
        adName: null,
        brand: null,
        status: 1,
        clickCount: null,
        cleard: 0,
        adRevenue: null,
        revenueAttachment: null,
        targetUrl: null,
        adImage: null,
        adStartTime: null,
        adEndTime: null,
        adTimeRange: [],
        createTime: null,
        creator: null,
        lastUpdateTime: null,
        lastUpdater: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "新增广告";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getAdvertisement(id).then(response => {
        this.form = response.data;
        // 处理时间范围
        if (this.form.adStartTime && this.form.adEndTime) {
          this.form.adTimeRange = [this.form.adStartTime, this.form.adEndTime];
        } else {
          this.form.adTimeRange = [];
        }
        // 确保必填字段有默认值
        if (!this.form.status) {
          this.form.status = 1;
        }
        if (this.form.cleard === null || this.form.cleard === undefined) {
          this.form.cleard = 0;
        }
        // 确保文件字段正确初始化
        this.form.adImage = row.adImage || null;
        this.form.revenueAttachment = row.revenueAttachment || null;
        this.open = true;
        this.title = "编辑广告";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 处理时间范围
          if (this.form.adTimeRange && this.form.adTimeRange.length === 2) {
            this.form.adStartTime = this.form.adTimeRange[0];
            this.form.adEndTime = this.form.adTimeRange[1];
          } else {
            this.$modal.msgError("请选择广告投放时间");
            return;
          }

          // 验证必填字段
          if (!this.form.adImage || this.form.adImage === "" || this.form.adImage === "undefined") {
            this.$modal.msgError("请上传广告图片");
            return;
          }

          // 验证广告位是否已被占用（仅新增时检查）
          if (!this.form.id) {
            const existingAd = this.advertisementList.find(ad =>
              ad.adPosition === this.form.adPosition && ad.status === 1
            );
            if (existingAd) {
              this.$modal.msgError("该广告位已存在运行中的广告，请选择其他广告位");
              return;
            }
          }

          if (this.form.id != null) {
            updateAdvertisement(this.form).then(() => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
              this.getStatistics();
            }).catch(error => {
              console.error('修改失败:', error);
              this.handleError(error);
            });
          } else {
            addAdvertisement(this.form).then(() => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
              this.getStatistics();
            }).catch(error => {
              console.error('新增失败:', error);
              this.handleError(error);
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id ? [row.id] : this.ids;
      this.$modal.confirm('是否确认删除广告信息编号为"' + ids + '"的数据项？').then(function () {
        return delAdvertisement(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('advertisement/advertisement/export', {
        ...this.queryParams
      }, `advertisement_${new Date().getTime()}.xlsx`)
    },
    /** 结清按钮操作 */
    handleClearance(row) {
      this.clearanceForm = {
        adRevenue: row.adRevenue || null,
        revenueAttachment: row.revenueAttachment || null
      };
      this.currentId = row.id;
      this.clearanceOpen = true;
    },
    /** 取消结清 */
    cancelClearance() {
      this.clearanceOpen = false;
      this.clearanceForm = {};
      this.currentId = null;
    },
    /** 提交结清 */
    submitClearance() {
      this.$refs["clearanceForm"].validate(valid => {
        if (valid) {
          // 使用专门的结清广告API
          const clearanceData = {
            id: this.currentId,
            adRevenue: this.clearanceForm.adRevenue,
            revenueAttachment: this.clearanceForm.revenueAttachment
          };
          clearanceAdvertisement(clearanceData).then(() => {
            this.$modal.msgSuccess("结清成功");
            this.clearanceOpen = false;
            this.clearanceForm = {};
            this.currentId = null;
            this.getList();
            this.getStatistics();
          }).catch(error => {
            this.handleError(error);
          });
        }
      });
    },
    /** 失效按钮操作 */
    handleInvalidate(row) {
      this.invalidateId = row.id;
      this.invalidateOpen = true;
    },
    /** 取消失效 */
    cancelInvalidate() {
      this.invalidateOpen = false;
      this.invalidateId = null;
    },
    /** 提交失效 */
    submitInvalidate() {
      invalidateAdvertisement(this.invalidateId).then(() => {
        this.$modal.msgSuccess("广告已失效");
        this.invalidateOpen = false;
        this.invalidateId = null;
        this.getList();
        this.getStatistics();
      }).catch(error => {
        this.handleError(error);
      });
    },
    /** 错误处理方法 */
    handleError(error) {
      if (error.response && error.response.data) {
        const errorMsg = error.response.data.msg || error.response.data.message || "操作失败";

        // 特殊处理约束冲突错误
        if (errorMsg.includes("该广告位已存在运行中的广告") ||
          errorMsg.includes("Duplicate entry") ||
          errorMsg.includes("uk_ad_position")) {
          this.$modal.msgError("该广告位已存在运行中的广告，请选择其他广告位或先停用现有广告");
        } else {
          this.$modal.msgError(errorMsg);
        }
      } else {
        this.$modal.msgError("操作失败，请检查网络连接");
      }
    }
  }
};
</script>

<style scoped>
/* 文件上传组件样式优化 */
.upload-file .el-button {
  width: 100%;
  max-width: 120px;
}

/* 美化时间选择器 */
.el-date-editor.el-range-editor.el-input__inner {
  width: 100%;
  padding: 0 10px;
}

.el-date-editor .el-range-separator {
  width: 8%;
  text-align: center;
  color: #606266;
  font-size: 14px;
}

.el-date-editor .el-range-input {
  appearance: none;
  border: none;
  outline: none;
  display: inline-block;
  background: 0 0;
  color: #606266;
  font-size: 13px;
  padding: 0;
  width: 46%;
  text-align: center;
}

/* 时间选择器在右侧列的样式优化 */
.el-col:last-child .el-form-item__label {
  padding-right: 8px;
  min-width: 80px;
  display: inline-block;
}

/* 确保两列布局中的标签对齐 */
.el-row .el-form-item__label {
  line-height: 32px;
  vertical-align: top;
}

/* 确保时间选择器在小屏幕上也能正常显示 */
@media (max-width: 992px) {
  .el-date-editor .el-range-input {
    font-size: 12px;
  }

  .el-date-editor .el-range-separator {
    width: 10%;
  }
}

/* 表单布局优化 */
.el-form-item {
  margin-bottom: 18px;
}

.el-form-item__label {
  font-weight: 500;
  color: #303133;
}

/* 表单响应式优化 */
@media (max-width: 768px) {
  .el-row .el-col {
    margin-bottom: 10px;
  }

  /* 移动端时间选择器全宽显示 */
  .el-row .el-col:last-child {
    margin-top: 10px;
  }

  .el-date-editor.el-range-editor {
    width: 100% !important;
  }
}

/* 统计卡片样式 */
.statistics-cards .box-card {
  border: none;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.statistic-item {
  text-align: center;
  padding: 10px 0;
}

.statistic-title {
  font-size: 14px;
  color: #909399;
  margin-bottom: 8px;
}

.statistic-value {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
}
</style>
