<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="宠物分类" prop="petClass">
        <el-select v-model="queryParams.petClass" placeholder="请选择宠物分类" clearable>
          <el-option v-for="pet in petClassType" :key="pet.petClassId" :label="pet.petClass" :value="pet.petClass" />
        </el-select>
      </el-form-item>
      <el-form-item label="宠物品种" prop="petBreed">
        <el-input v-model="queryParams.petBreed" placeholder="请输入宠物品种" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="宠物品种（英文）" prop="petBreedEn" label-width="140px">
        <el-input v-model="queryParams.petBreedEn" placeholder="请输入宠物品种（英文）" clearable
          @keyup.enter.native="handleQuery" />
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
          v-hasPermi="['breed:breed:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['breed:breed:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-remove" size="mini" :disabled="single" @click="handleInvalidate"
          v-hasPermi="['petType:petType:invalidate']">失效</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['breed:breed:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="breedList" @selection-change="handleSelectionChange">
<el-table-column type="selection" width="55" align="center" />
      <el-table-column label="中文排序" align="center" prop="cnNo" />
      <el-table-column label="英文排序" align="center" prop="enNo" />
      <!-- <el-table-column label="宠物分类" align="center" prop="petClass" /> -->
         <el-table-column label="宠物分类" align="center" prop="petClass" width="100">
    <template slot-scope="scope">
      <el-select v-model="scope.row.petClass" placeholder="请选择宠物类型" disabled>
        <el-option v-for="pet in petClassType" :key="pet.petClassId" :label="pet.petClass" :value="pet.petClass"></el-option>
      </el-select>
    </template>
  </el-table-column>
      <el-table-column label="中文首字母" align="center" prop="cnInitials" />
      <el-table-column label="宠物品种" align="center" prop="petBreed" />
      <el-table-column label="英文首字母" align="center" prop="enInitials" />
      <el-table-column label="宠物品种（英文）" align="center" prop="petBreedEn" width="130"/>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.pet_class_status" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="最后更新时间" align="center" prop="lastUpdateTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lastUpdateTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建人" align="center" prop="creator" />
      <el-table-column label="最后更新人" align="center" prop="lastUpdater" />
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />


    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="11">
            <el-form-item label="中文排序" prop="cnNo">
              <el-input v-model="form.cnNo" placeholder="请输入中文排序" />
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="状态" prop="status" label-width="140px">
              <el-select v-model="form.status" placeholder="请选择状态" :disabled="true">
                <el-option v-for="dict in dict.type.pet_class_status" :key="dict.value" :label="dict.label"
                  :value="parseInt(dict.value)"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="11">
            <el-form-item label="英文排序" prop="enNo">
              <el-input v-model="form.enNo" placeholder="请输入英文排序" />
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="宠物类型" prop="petClass" label-width="140px">
              <el-select v-model="form.petClass" placeholder="请选择宠物类型" clearable>
                <el-option v-for="pet in petClassType" :key="pet.petClassId" :label="pet.petClass"
                  :value="pet.petClass"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="11">
            <el-form-item label="中文首字母" prop="cnInitials">
              <el-input v-model="form.cnInitials" placeholder="请输入中文首字母" />
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="宠物品种" prop="petBreed" label-width="140px">
              <el-input v-model="form.petBreed" placeholder="请输入宠物品种" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="11">
            <el-form-item label="英文首字母" prop="enInitials">
              <el-input v-model="form.enInitials" placeholder="请输入英文首字母" />
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="宠物品种（英文）" prop="petBreedEn" label-width="140px">
              <el-input v-model="form.petBreedEn" placeholder="请输入宠物品种（英文）" />
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
import { listBreed, getBreed, delBreed, addBreed, updateBreed, updateBreedStatus } from "@/api/breed/breed"
import { listPet } from "@/api/petType/petType"
export default {
  name: "Breed",
  dicts: ['pet_class_status'],
  data() {
    return {
      petClassType: null,
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
      // 宠物品种表格数据
      breedList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        petClass: null,
        petBreed: null,
        enInitials: null,
        petBreedEn: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        cnNo: [
          { required: true, message: "中文排序不能为空", trigger: "blur" }
        ],
        enNo: [
          { required: true, message: "英文排序不能为空", trigger: "blur" }
        ],
        petClass: [
          { required: true, message: "宠物分类不能为空", trigger: "change" }
        ],
        cnInitials: [
          { required: true, message: "中文首字母不能为空", trigger: "blur" }
        ],
        petBreedId: [
          { required: true, message: "宠物品种ID不能为空", trigger: "blur" }
        ],
        petBreed: [
          { required: true, message: "宠物品种不能为空", trigger: "blur" }
        ],
        enInitials: [
          { required: true, message: "英文首字母不能为空", trigger: "blur" }
        ],
        petBreedEn: [
          { required: true, message: "宠物品种不能为空", trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.getListPet()
  },
  methods: {
    /** 查询宠物品种列表 */
    getList() {
      this.loading = true
      listBreed(this.queryParams).then(response => {
        this.breedList = response.rows
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
        id: null,
        cnNo: null,
        enNo: null,
        petClass: null,
        petClassId: null,
        cnInitials: null,
        petBreedId: null,
        petBreed: null,
        enInitials: null,
        petBreedEn: null,
        status: null,
        lastUpdateTime: null,
        createTime: null,
        creator: null,
        lastUpdater: null
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
      this.ids = selection.map(item => item.petBreedId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加宠物品种"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
     
      this.reset()
      const petBreedId = row.petBreedId || this.ids
      getBreed(petBreedId).then(response => {
         console.log(response.data)
        this.form = response.data
        this.open = true
        this.title = "修改宠物品种"
      })
    },
    //失效
    handleInvalidate() {
      if (this.ids.length === 0) {
        this.$modal.msgError("请先选择要失效的宠物分类");
        return;
      }
      this.$modal.confirm('是否确认将所选宠物分类设置为失效状态？').then(() => {
        return updateBreedStatus({ ids: this.ids });
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("失效成功");
      }).catch(() => { });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.petBreedId != null) {
            updateBreed(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addBreed(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除宠物品种编号为"' + petClassIds + '"的数据项？').then(function () {
        return delBreed(petClassIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => { })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('breed/export', {
        ...this.queryParams
      }, `breed_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
