<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">

      <el-form-item label="宠物类型" prop="petClass">
        <el-select v-model="queryParams.petClass" placeholder="请选择宠物类型" clearable>
          <el-option v-for="pet in petClassType" :key="pet.petClassId" :label="pet.petClass" :value="pet.petClass" />
        </el-select>
      </el-form-item>

      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option v-for="dict in dict.type.pet_class_status" :key="dict.value" :label="dict.label"
            :value="dict.value" />
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
          v-hasPermi="['petType:petType:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['petType:petType:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-remove" size="mini" :disabled="single" @click="handleInvalidate"
          v-hasPermi="['petType:petType:invalidate']">失效</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="petTypeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="no" />
      <el-table-column label="宠物分类" align="center" prop="petClass" />
      <el-table-column label="宠物分类(英文)" align="center" prop="petClassEn"  />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.pet_class_status" :value="scope.row.status" />
        </template>
      </el-table-column>
            <el-table-column label="最后更新时间" align="center" prop="lastUpdateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lastUpdateTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建人" align="center" prop="creator" />

      <el-table-column label="最后更新人" align="center" prop="lastUpdater" />
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改宠物分类对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="序号" prop="no">
              <el-input v-model="form.no" placeholder="请输入序号" />
            </el-form-item>
          </el-col>
          <el-col :span="16">
            <el-form-item label="状态" prop="status" label-width="120px">
              <el-select v-model="form.status" placeholder="请选择状态" :disabled="true">
                <el-option v-for="dict in dict.type.pet_class_status" :key="dict.value" :label="dict.label"
                  :value="parseInt(dict.value)"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="宠物分类" prop="petClass">
              <el-input v-model="form.petClass" placeholder="请输入宠物分类" />
            </el-form-item>
          </el-col>
          <el-col :span="16">
            <el-form-item label="宠物分类(英文)" prop="petClassEn" label-width="120px">
              <el-input v-model="form.petClassEn" placeholder="请输入宠物分类(英文)" style="width: 221px;" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPetType, getPetType, delPetType, addPetType, updatePetType ,listPet,updatePetTypeStatusBatch} from "@/api/petType/petType"

export default {
  name: "PetType",
  dicts: ['pet_class_status'],
  data() {
    return {
      petClassType:null,
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
      // 宠物分类表格数据
      petTypeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        petClass: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        no: [
          { required: true, message: "序号不能为空", trigger: "blur" }
        ],
        petClass: [
          { required: true, message: "宠物分类不能为空", trigger: "change" }
        ],
        petClassEn: [
          { required: true, message: "宠物分类不能为空", trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.getListPet()
  },
  methods: {
    /** 查询宠物分类列表 */
    getList() {
      this.loading = true
      listPetType(this.queryParams).then(response => {
        this.petTypeList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    getListPet() {
      listPet().then(response => {
         console.log(response)
        this.petClassType = response.data
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        no: null,
        petClassId: null,
        petClass: null,
        petClassEn: null,
        status: null,
        createTime: null,
        creator: null,
        lastUpdateTime: null,
        lastUpdater: null,
        petClassType:null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.petClassId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加宠物分类"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const petClassId = row.petClassId || this.ids
      getPetType(petClassId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改宠物分类"
      })
    },
    //失效
    handleInvalidate(){
    if (this.ids.length === 0) {
      this.$modal.msgError("请先选择要失效的宠物分类");
      return;
    }
    this.$modal.confirm('是否确认将所选宠物分类设置为失效状态？').then(() => {
      return updatePetTypeStatusBatch({ ids: this.ids}); 
    }).then(() => {
      this.getList();
      this.$modal.msgSuccess("失效成功");
    }).catch(() => {});
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.petClassId != null) {
            updatePetType(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addPetType(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const petClassIds = row.petClassId || this.ids
      this.$modal.confirm('是否确认删除宠物分类编号为"' + petClassIds + '"的数据项？').then(function() {
        return delPetType(petClassIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('petType/petType/export', {
        ...this.queryParams
      }, `petType_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
