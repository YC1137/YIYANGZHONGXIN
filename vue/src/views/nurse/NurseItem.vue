<template>
  <div class="common-layout">
    <el-container>
      <el-header class="header-container">
        <div class="header-content">
          <el-button
              type="primary"
              icon="Plus"
              size="default"
              @click="addItem"
              class="add-button"
          >

            <span>登记</span>
          </el-button>
          <div class="filter-group">
            <el-radio-group
                v-model="queryParams.status"
                size="default"
                @change="handleStatusChange"
            >
              <el-radio-button label="1" class="active-radio">启用</el-radio-button>
              <el-radio-button label="0" class="inactive-radio">禁用</el-radio-button>
            </el-radio-group>
          </div>
        </div>
      </el-header>

      <el-divider style="margin:0"></el-divider>
      <el-main class="table-container">
        <div>
          <!-- 表格 -->
          <el-table
              :data="nurseItemList"
              stripe
              style="width: 100%"
              class="custom-table"
              :header-cell-style="{background: '#f5f7fa', color: '#606266'}"
          >
            <el-table-column
                align="center"
                type="index"
                :index="indexMethod"
                label="序号"
                width="60"
            />
            <el-table-column align="center" prop="serialNumber" label="编号" width="120" />
            <el-table-column align="center" prop="nursingName" label="名称" width="120" />
            <el-table-column align="center" prop="servicePrice" label="价格" width="120"/>
            <el-table-column align="center" prop="executionCycle" label="执行频率" width="180" />
            <el-table-column align="center" prop="executionTimes" label="执行次数" width="180" />
            <el-table-column align="center"  prop="message" label="描述" width="320"/>
            <el-table-column align="center" label="状态" width="150">
              <template #default="scope">
                <el-tag
                    :type="scope.row.status === 1 ? 'success' : 'danger'"
                    effect="dark"
                    size="medium"
                >
                  {{ scope.row.status === 1 ? '启用' : '禁用' }}
                </el-tag>
              </template>
            </el-table-column>

            <el-table-column align="center" fixed="right" label="操作" width="220">
              <template #default="scope">
                <el-button
                    type="primary"
                    icon="Edit"
                    size="small"
                    @click="edit(scope.row)"
                    class="action-button"
                >
                  修改
                </el-button>
                <el-button
                    type="danger"
                    icon="Delete"
                    size="small"
                    @click="del(scope.row.id)"
                    class="action-button"
                >
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <!-- 分页插件 -->
        <div style="margin-top:15px">
          <el-pagination
              :page-size="page.pageSize"
              background
              :current-page="page.currentPag"
              layout=" prev, pager, next"
              :total="page.total"
              @current-change="handleCurrentChange"
          />
        </div>
      </el-main>
    </el-container>

    <!-- 修改/添加对话框 -->
    <el-dialog
        v-model="dialog.dialogVisible"
        :title="dialog.tops"
        width="500px"
        align-center
        draggable
        :before-close="handleClose"
        class="custom-dialog"
    >
      <el-divider border-style="dashed" class="divider"/>
      <el-form
          label-position="right"
          label-width="100px"
          class="custom-form"
          ref="itemForm"
          :model="dialog.item"
          :rules="rules"
      >
        <el-form-item
            label="编号"
            prop="serialNumber"
            class="form-item"
        >
          <el-input
              v-model="dialog.item.serialNumber"
              placeholder="请输入编号"
              clearable
          />
        </el-form-item>
        <el-form-item
            label="名称"
            prop="nursingName"
            class="form-item"
        >
          <el-input
              v-model="dialog.item.nursingName"
              placeholder="请输入名称"
              clearable
          />
        </el-form-item>
        <el-form-item
            label="价格"
            prop="servicePrice"
            class="form-item"
        >
          <el-input
              v-model="dialog.item.servicePrice"
              placeholder="请输入价格"
              clearable
          />
        </el-form-item>
        <el-form-item
            label="执行频率"
            prop="executionCycle"
            class="form-item"
        >
          <el-input
              v-model="dialog.item.executionCycle"
              placeholder="请输入执行频率"
              clearable
          />
        </el-form-item>
        <el-form-item
            label="执行次数"
            prop="executionTimes"
            class="form-item"
        >
          <el-input
              v-model="dialog.item.executionTimes"
              placeholder="请输入执行次数"
              clearable
          />
        </el-form-item>
        <el-form-item
            label="描述"
            prop="message"
            class="form-item"
        >
          <el-input
              v-model="dialog.item.message"
              placeholder="请输入描述"
              clearable
          />
        </el-form-item>
        <el-form-item label="状态" prop="status" class="form-item">
          <el-select
              v-model="dialog.item.status"
              placeholder="请选择状态"
              style="width: 100%"
          >
            <el-option
                v-for="item in dialog.statusArr"
                :key="item.value"
                :label="item.label"
                :value="item.value"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button
              type="primary"
              icon="Check"
              size="default"
              @click="save('itemForm')"
              class="submit-button"
          >
            保存
          </el-button>
          <el-button
              icon="Close"
              size="default"
              @click="dialog.dialogVisible = false"
              class="cancel-button"
          >
            取消
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import {
  findNurseItemPage,
  addNurseItem,
  updateNurseItem,
  delNurseItem,
} from "@/api/nurseContentApi";
import {getCurDate} from "@/utils/common.js"
import {getSessionStorage} from '@/utils/common.js'
import { Plus } from '@element-plus/icons-vue'

export default {
  components: {
    Plus
  },
  computed: {
    indexMethod() {
      return this.page.currentPag * this.page.pageSize - this.page.pageSize + 1;
    }
  },
  data() {
    return {
      //模态框数据
      dialog: {
        dialogVisible: false,
        tops: "",
        item: {
          id: "",
          serialNumber: "",
          nursingName: "",
          servicePrice: "",
          executionCycle: "",
          executionTimes: "",
          message: "",
        },
        statusArr: [
          {
            value: 0,
            label: "禁用"
          },
          {
            value: 1,
            label: "启用"
          }
        ]
      },
      //校验规则
      rules: {
        nursingName: [
          { required: true, message: "请输入名称", trigger: "blur" }
        ],
        status: [{ required: true, message: "请选择状态", trigger: "change" }]
      },
      //分页属性封装
      page: {
        total: 0,
        pageSize: 6,
        currentPag: 1,
        pagCount: 0
      },
      queryParams: {
        status: "1",      // 正确
        name: "",         // nursingName 改为 name
        pageNum: 1        // pageSize 改为 pageNum
      },
      nurseItemList: []
    };
  },
  mounted() {
    this.getNurseItemList();
  },
  methods: {
    //点击查询
    handleStatusChange() {
      this.queryParams.pageNum = 1; // 切换时从第一页开始
      this.getNurseItemList();      // 重新加载列表
    },
    query() {
      this.queryParams.pageSize = "1"; //回到第一页
      console.log('AAA'+this.queryParams.nursingName);
      this.getNurseItemList();
    },
    //点击启用
    enable() {
      this.queryParams.status = "1"; //1-启用
      this.queryParams.pageSize = "1"; //回到第一页
      this.getNurseItemList();
    },
    //点击停用
    disable() {
      this.queryParams.pageSize = "1"; //2-停用
      this.queryParams.status = "2"; //0-生效床位信息
      this.getNurseItemList();
    },
    //选中页码
    handleCurrentChange(currentPage) {
      this.page.currentPage = currentPage;
      this.queryParams.pageNum = currentPage;
      this.getNurseItemList();
    },

    //点击修改
    edit(row) {
      this.dialog.tops = "修改登记信息";
      this.dialog.dialogVisible = true;
      //初始化模态框数据
      this.$nextTick(() => {
        this.dialog.item.id = row.id;
        this.dialog.item.serialNumber = row.serialNumber;
        this.dialog.item.nursingName = row.nursingName;
        this.dialog.item.servicePrice = row.servicePrice;
        this.dialog.item.executionCycle = row.executionCycle;
        this.dialog.item.executionTimes = row.executionTimes;
        this.dialog.item.message = row.message;
        this.dialog.item.status = row.status;
      });
    },
    //点击添加按钮
    addItem() {
      this.dialog.tops = "登记护理信息";
      this.dialog.dialogVisible = true;
      this.dialog.item = {
        id: "",
        serialNumber: "",
        nursingName: "",
        servicePrice: "",
        executionCycle: "",
        executionTimes: "",
        message: "",
        status: null
      };
    },
    handleClose() {
      this.dialog.dialogVisible = false;
      this.resetForm("itemForm"); //重置表单
    },
    cancel() {
      this.handleClose();
    },
    //重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    //api-保存(新增/编辑)
    save(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          //通过id判断是添加还是编辑
          if (this.dialog.item.id == null || this.dialog.item.id == "") {
            console.log(this.dialog.item);
            addNurseItem(this.dialog.item).then(res => {
              if (res.flag) {
                this.$message.success(res.message);
                //刷新数据表格(回到最初查询状态)
                this.queryParams.status = "1";
                this.queryParams.pageSize = "1"; //回到第一页
                this.queryParams.name = "";
                this.getNurseItemList();
                this.handleClose(); //关闭模态框
              } else {
                this.$message.error(res.message);
              }
            });
          } else {
            updateNurseItem(this.dialog.item).then(res => {
              if (res.flag) {
                this.$message.success(res.message);
                //刷新数据表格
                this.getNurseItemList();
                this.handleClose(); //关闭模态框
              } else {
                this.$message.error(res.message);
              }
            });
          }
        } else {
          return false;
        }
      });
    },
    //api-删除
    del(id) {
      this.$confirm("此操作删除记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
          .then(() => {
            delNurseItem(id).then(res => {
              if (res.flag) {
                this.$message.success(res.message);
                this.getNurseItemList();
              } else {
                this.$message.error(res.message);
              }
            });
          })
          .catch(() => {});
    },
    //api-查询护理项目(分页)
    getNurseItemList() {
      findNurseItemPage(this.queryParams).then(res => {
        this.nurseItemList = res.data.records;
        this.page.total = res.data.total; //总记录数
        this.page.pageSize = res.data.size; //每页显示条数
        this.page.currentPag = res.data.current; //当前页码
        this.page.pagCount = res.data.pages; //总页数
      })
          .catch(error => {
            console.error("API Error:", error.response);
            this.$message.error("请求失败：" + error.message);
          });
    }
  }
};
</script>

<style scoped>
.common-layout {
  padding: 20px;
  background-color: #f5f7fa;
  min-height: 100vh;
}

.header-container {
  background: #ffffff;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  padding: 15px 20px;
  border-radius: 4px;
  margin-bottom: 20px;
}

.header-content {
  display: flex;
  align-items: center;
}

.add-button {
  background: linear-gradient(135deg, #409EFF, #337ecc);
  border: none;
  box-shadow: 0 2px 6px rgba(64, 158, 255, 0.3);
  color: white;
  margin-right: 10px;
}

.filter-group {
  display: flex;
  align-items: center;
}

.active-radio {
  background-color: #f0f9eb;
  color: #67c23a;
}

.inactive-radio {
  background-color: #fef0f0;
  color: #f56c6c;
}

.table-container {
  background: #ffffff;
  padding: 20px;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.custom-table {
  border: 1px solid #ebeef5;
  border-radius: 4px;
}

.custom-table :deep(.el-table__header-wrapper) {
  border-radius: 4px 4px 0 0;
}

.action-button {
  margin: 0 5px;
  padding: 7px 12px;
  font-size: 13px;
  border-radius: 6px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.08);
}

.custom-dialog {
  border-radius: 8px;
}

.custom-dialog :deep(.el-dialog__header) {
  background: linear-gradient(135deg, #409EFF, #337ecc);
  color: white;
  border-radius: 8px 8px 0 0;
  padding: 15px 20px;
}

.custom-dialog :deep(.el-dialog__title) {
  color: white;
}

.divider {
  margin: 10px 0;
}

.custom-form {
  padding: 0 20px;
}

.form-item {
  margin-bottom: 22px;
}

.form-item :deep(.el-form-item__label) {
  color: #606266;
  font-weight: 500;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  padding-top: 10px;
}

.submit-button {
  background: linear-gradient(135deg, #409EFF, #337ecc);
  border: none;
  box-shadow: 0 2px 6px rgba(64, 158, 255, 0.3);
}

.cancel-button {
  margin-left: 15px;
}

/* 状态标签样式 */
.status-tag {
  padding: 4px 8px;
  border-radius: 4px;
  font-weight: bold;
}

.status-active {
  background-color: #f0f9eb;
  color: #67c23a;
}

.status-inactive {
  background-color: #fef0f0;
  color: #f56c6c;
}

/* 表格行悬停效果 */
.custom-table :deep(.el-table__body tr:hover>td) {
  background-color: #f5f7fa !important;
}

/* 分页样式 */
.el-pagination {
  margin-top: 20px;
  justify-content: flex-end;
}

.el-pagination :deep(.btn-prev),
.el-pagination :deep(.btn-next) {
  border-radius: 4px;
}

.el-pagination :deep(.number) {
  border-radius: 4px;
}

.el-pagination :deep(.active) {
  background-color: #409EFF;
  color: white;
}
</style>