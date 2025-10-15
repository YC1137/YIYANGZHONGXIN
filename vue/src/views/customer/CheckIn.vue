<template>
  <div class="common-layout">
    <el-container>
      <el-header class="header-container">
        <div class="header-content">
          <el-row :gutter="30" type="flex" align="middle">  <!-- 添加 flex 布局和对齐方式 -->
            <el-col :span="7">
              <el-input
                  placeholder="姓名"
                  v-model="queryParams.customerName"
                  style="width: 300px"
                  @clear="query"
                  clearable
                  size="large"
              >
                <template #append>
                  <el-button type="info" @click="query" style="color:black">查询</el-button>
                </template>
              </el-input>
            </el-col>
            <el-col :span="17">
              <div class="filter-group" style="display: flex; justify-content: flex-end;">  <!-- 使用 flex 布局右对齐 -->
                <el-button type="primary" @click="addItem" class="add-button">
                  <el-icon>
                    <Plus />
                  </el-icon>
                  <span>登记入住</span>
                </el-button>
              </div>
            </el-col>
          </el-row>
        </div>
      </el-header>

      <el-divider style="margin:0"></el-divider>
      <el-main class="table-container">
        <div>
          <!-- 表格 -->
          <el-table
              :data="customerList"
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
            <el-table-column align="center" prop="id" label="编号" width="120" />
            <el-table-column align="center" prop="customerName" label="姓名" width="120" />
            <el-table-column align="center" prop="customerSex" label="性别" width="120">
              <template #default="scope">{{ scope.row.customerSex==1?'男':'女'}}</template>
            </el-table-column>
            <el-table-column align="center" prop="customerAge" label="年龄" width="180" />
            <el-table-column align="center" prop="bedId" label="床位号" width="180" />
            <el-table-column align="center" prop="checkinDate" label="入住时间" width="150"/>
            <el-table-column align="center" prop="expirationDate" label="合同到期时间" width="150" />
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
            label="姓名"
            prop="customerName"
            class="form-item"
        >
          <el-input
              v-model="dialog.item.customerName"
              placeholder="请输入姓名"
              clearable
          />
        </el-form-item>

        <el-form-item
            label="性别"
            prop="customerSex"
            class="form-item"
        >
          <el-select
              v-model="dialog.item.customerSex"
              placeholder="请选择性别"
              style="width: 100%"
          >
            <el-option label="男" value="1" />
            <el-option label="女" value="2" />
          </el-select>
        </el-form-item>
        <el-form-item
            label="年龄"
            prop="customerAge"
            class="form-item"
        >
          <el-input
              v-model="dialog.item.customerAge"
              placeholder="请输入年龄"
              clearable
          />
        </el-form-item>
        <el-form-item
            label="床位号"
            prop="bedId"
            class="form-item"
        >
          <el-input
              v-model="dialog.item.bedId"
              placeholder="请输入床位号"
              clearable
          />
        </el-form-item>
        <el-form-item
            label="入住时间"
            prop="checkinDate"
            class="form-item"
        >
          <el-date-picker
              style="width:200px"
              format="YYYY/MM/DD"
              value-format="YYYY-MM-DD"
              v-model="dialog.item.checkinDate"
              type="date"
              :disabled-date="disabledDate"
              placeholder="选择日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item
            label="合同到期时间"
            prop="expirationDate"
            class="form-item"
        >
          <el-date-picker
              style="width:200px"
              format="YYYY/MM/DD"
              value-format="YYYY-MM-DD"
              v-model="dialog.item.expirationDate"
              type="date"
              :disabled-date="disabledDate"
              placeholder="选择日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item
            label="状态"
            prop="isDeleted"
            class="form-item"
        >
          <el-select
              v-model="dialog.item.isDeleted"
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
  addCustomer,
  updateCustomer,
  delCustomer,
  findAllCustomer
} from "@/api/customerApi.js";
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
          id:"",
          bedId: "",
          bloodType: "O",
          buildingNo: "606", //楼号固定606
          contactTel: "",
          customerAge: "",
          customerName: "",
          customerSex: "0",
          roomNo: "",
          idcard: "",
          isDeleted: "",//状态 1：启用；2：停用
          checkinDate:"",
          expirationDate:"",
        },
        statusArr: [
          {
            value: 1,
            label: "停用"
          },
          {
            value: 2,
            label: "启用"
          }
        ]
      },
      //校验规则
      rules: {
        customerName: [
          { required: true, message: "请输入用户名", trigger: "blur" }
        ],
        isDeleted: [{ required: true, message: "请选择状态", trigger: "change" }]
      },
      //分页属性封装
      page: {
        total: 0,
        pageSize: 6,
        currentPag: 1,
        pagCount: 0
      },
      btnFlag: true,
      queryParams: {
        status: "1", //查询默认状态1 -启用
        customerName:"",
        pageSize: "1" //默认第一页
      },
      customerList: []
    };
  },
  mounted() {
    this.getCustomerList();
  },
  methods: {
    //点击查询
    query() {
      this.queryParams.pageSize = "1"; //回到第一页
      console.log('AAA'+this.queryParams.customerName);
      this.getCustomerList();
    },
    //点击启用
    enable() {
      this.btnFlag = true;
      this.queryParams.status = "1"; //1-启用
      this.queryParams.pageSize = "1"; //回到第一页
      this.getCustomerList();
    },
    //点击停用
    disable() {
      this.btnFlag = false;
      this.queryParams.pageSize = "1"; //2-停用
      this.queryParams.status = "2"; //0-生效床位信息
      this.getCustomerList();
    },
    //选中页码
    handleCurrentChange(curPage) {
      this.page.currentPag = curPage;
      this.queryParams.pageSize = curPage; //参数pageSize是服务端接收页码参数名
      //重新渲染表格
      this.getCustomerList();
    },
    //点击修改
    edit(row) {
      this.dialog.tops = "修改登记信息";
      this.dialog.dialogVisible = true;
      //初始化模态框数据
      this.$nextTick(() => {
        this.dialog.item.id = row.id;
        this.dialog.item.customerName = row.customerName;
        this.dialog.item.customerSex = row.customerSex;
        this.dialog.item.customerAge = row.customerAge;
        this.dialog.item.isDeleted = row.isDeleted;
      });
    },
    //点击添加按钮
    addItem() {
      this.dialog.tops = "登记入住信息";
      this.dialog.dialogVisible = true;
      this.dialog.item = {
        id: "",
        bedId: "",
        bloodType: "O",
        buildingNo: "606",
        contactTel: "",
        customerAge: "",
        customerName: "",
        customerSex: "0",
        roomNo: "",
        idcard: "",
        isDeleted: 1, //默认启用状态
        checkinDate: "",
        expirationDate: "",
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
            this.dialog.item.createTime = getCurDate();
            this.dialog.item.createBy = getSessionStorage("user").id;
            this.dialog.item.roleId = 2;
            console.log(this.dialog.item);
            addCustomer(this.dialog.item).then(res => {
              if (res.flag) {
                this.$message.success(res.message);
                //刷新数据表格(回到最初查询状态)
                this.queryParams.status = "1";
                this.queryParams.pageSize = "1"; //回到第一页
                this.queryParams.customerName = "";
                this.getCustomerList();
                this.handleClose(); //关闭模态框
              } else {
                this.$message.error(res.message);
              }
            });
          } else {
            updateCustomer(this.dialog.item).then(res => {
              if (res.flag) {
                this.$message.success(res.message);
                //刷新数据表格
                this.getCustomerList();
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
            delCustomer({ id: id }).then(res => {
              if (res.flag) {
                this.$message.success(res.message);
                //重载表格
                this.getCustomerList();
              } else {
                this.$message.error(res.message);
              }
            });
          })
          .catch(() => {});
    },
    //api-查询客户(分页)
    getCustomerList() {
      findAllCustomer(this.queryParams).then(res => {
        this.customerList = res.data.records;
        this.page.total = res.data.total; //总记录数
        this.page.pageSize = res.data.size; //每页显示条数
        this.page.currentPag = res.data.current; //当前页码
        this.page.pagCount = res.data.pages; //总页数
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
}

.filter-group {
  display: flex;
  align-items: center;
  margin-left: 20px;
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