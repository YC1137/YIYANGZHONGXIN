<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <div>
          <el-row :gutter="30">
            <el-col :span="7">
              <el-input
                  placeholder="客户姓名"
                  @clear="query"
                  v-model="condition.customerName"
                  clearable
                  size="large"
              >
                <template #append>
                  <el-button type="info" @click="query" style="color:black">查询</el-button>
                </template>
              </el-input>
            </el-col>
            <el-col :span="17" v-if="roleId===2">
              <el-button type="primary" @click="addItem" color="#337ab7" style="margin-top:4px">
                <el-icon>
                  <Plus />
                </el-icon>
                <span>添加退住申请</span>
              </el-button>
            </el-col>
          </el-row>
        </div>
      </el-header>
      <el-divider style="margin:0"></el-divider>
      <el-main>
        <el-row :gutter="20">
          <el-col :span="9">
            <div class="table-main">
              <div class="table-main-header">客户信息</div>
              <!-- 表格 -->
              <el-table
                  :data="khxxList"
                  highlight-current-row
                  @current-change="handleChangeCustomer"
                  size="small"
                  style="width: 100% ;color:black;"
                  stripe
              >
                <el-table-column
                    align="center"
                    fixed
                    type="index"
                    :index="indexMethod"
                    label="序号"
                    width="40"
                />
                <el-table-column align="center" prop="customerName" label="姓名"/>
                <el-table-column align="center" prop="customerAge" label="年龄"/>
                <el-table-column align="center" prop="customerSex" label="性别">
                  <template #default="scope">{{ scope.row.customerSex==0?'男':'女'}}</template>
                </el-table-column>
                <el-table-column align="center" prop="bedNo" label="床号"/>
                <el-table-column align="center" prop="levelName" label="护理级别" />
              </el-table>
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
            </div>
          </el-col>
          <el-col :span="15">
            <div class="table-main">
              <div class="table-main-header">退住登记</div>
              <!-- 表格 -->
              <el-table :data="backdownList" size="small">
                <el-table-column
                    align="center"
                    :index="indexMethodRecord"
                    type="index"
                    label="序号"
                    width="40"
                />
                <el-table-column align="center" prop="customerName" label="客户名称" width="100" />
                <el-table-column align="center" prop="checkinDate" label="入住时间" width="100" />
                <el-table-column align="center" prop="retreattime" label="退住时间" width="70" />
                <el-table-column align="center" prop="retreattype" label="退住类型" width="100" />
                <el-table-column align="center" prop="retreatreason" label="退住原因" width="100" />
                <el-table-column align="center" prop="audittime" label="审批时间" width="100" />
                <!-- <el-table-column align="center" prop="status" label="审批状态" width="100" /> -->
                <el-table-column align="center" prop="auditstatus" label="审批状态" width="100">
                  <template #default="{row}">
                    {{row.auditstatus===0?'已提交':row.auditstatus===1?'同意':'拒绝'}}
                  </template>
                </el-table-column>
                <el-table-column align="center" prop="bedId" label="床位" width="100" />
                <el-table-column align="center" label="操作" width="100">
                  <template #default="scope">
                    <!-- <el-button v-if="scope.row.auditstatus === 1 && dialog.item.roleId === 2" :disabled="scope.row.actualreturntime" type="success" size="small" round plain @click="updateTime(scope.row.id)">登记回院时间</el-button> -->
                    <el-button  v-if="scope.row.auditstatus === 0 && roleId === 2" type="danger" size="small" round plain @click="del(scope.row.id)">撤销申请</el-button>
                    <el-button  v-if="scope.row.auditstatus === 0 && roleId !== 2" type="primary" size="small" round plain @click="examine(scope.row.id)">审批</el-button>
                  </template>
                </el-table-column>
              </el-table>
              <!-- 分页插件 -->
              <div style="margin-top:15px" v-if="backdownList.length>0">
                <el-pagination
                    :page-size="pageRecord.pageSize"
                    background
                    :current-page="pageRecord.currentPag"
                    layout=" prev, pager, next"
                    :total="pageRecord.total"
                    @current-change="handleRecordChange"
                />
              </div>
            </div>
          </el-col>
        </el-row>
      </el-main>
    </el-container>

    <!-- 修改/添加对话框 -->
    <el-dialog
        v-model="dialog.dialogExamineVisible"
        :title="dialog.tops"
        width="40%"
        align-center
        draggable
        :before-close="handleExamineClose"
    >
      <el-divider border-style="double" style="margin:0;" />
      <el-form
          label-position="right"
          label-width="auto"
          style="max-width:380px;margin:20px auto"
          class="demo-form-inline"
          ref="itemExamineForm"
          :model="dialog.item"
          :rules="rules"
      >

        <el-form-item label="审批：" prop="auditstatus">
          <el-radio-group v-model="dialog.item.auditstatus" class="ml-4">
            <el-radio label="1">同意</el-radio>
            <el-radio label="2">拒绝</el-radio>
          </el-radio-group>
        </el-form-item>

      </el-form>
      <el-divider border-style="double" style="margin:0;" />
      <template #footer>
	    <span class="dialog-footer" style="padding-top: 0px">
	      <el-button type="primary" @click="examineBackdown('itemExamineForm')">保存</el-button>
	      <el-button @click="cancelExamine">取消</el-button>
	    </span>
      </template>
    </el-dialog>
    <!-- 修改/添加对话框 -->
    <el-dialog
        v-model="dialog.dialogVisible"
        :title="dialog.tops"
        width="40%"
        align-center
        draggable
        :before-close="handleClose"
    >
      <el-divider border-style="double" style="margin:0;" />
      <el-form
          label-position="right"
          label-width="auto"
          style="max-width:380px;margin:20px auto"
          class="demo-form-inline"
          ref="itemForm"
          :model="dialog.item"
          :rules="rules"
      >
        <el-form-item label="客户姓名：" prop="customerId">
          <el-select v-model="dialog.item.customerId" style="width:320px" placeholder="客户姓名：">
            <el-option v-for="item in customerList" :label="item.customerName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="退住时间：" prop="retreattime">
          <el-date-picker
              style="width:320px"
              v-model="dialog.item.retreattime"
              type="date"
              placeholder="退住时间:"
              value-format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item label="退住类型：" prop="retreattype">
          <el-select v-model="dialog.item.retreattype" style="width:320px" placeholder="退住类型：">
            <el-option label="正常退住" value="0" />
            <el-option label="死亡退住" value="1" />
            <el-option label="保留床位" value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="退住原因： " prop="retreatreason">
          <el-input v-model="dialog.item.retreatreason" />
        </el-form-item>
      </el-form>
      <el-divider border-style="double" style="margin:0;" />
      <template #footer>
	    <span class="dialog-footer" style="padding-top: 0px">
	      <el-button type="primary" @click="save('itemForm')">保存</el-button>
	      <el-button @click="cancel">取消</el-button>
	    </span>
      </template>
    </el-dialog>
  </div>
</template>
<script>
import { getCustomerList } from "@/api/customerApi.js";
import { addBackdown, examineBackdown, delBackdown, queryBackdownVo } from "@/api/backDownApi.js";
import { getSessionStorage, getCurDate } from '@/utils/common.js';

export default {
  computed: {
    indexMethod() {
      return this.page.currentPag * this.page.pageSize - this.page.pageSize + 1;
    },
    indexMethodRecord() {
      return this.pageRecord.currentPag * this.pageRecord.pageSize - this.pageRecord.pageSize + 1;
    },
  },
  data() {
    return {
      dialog: {
        dialogVisible: false,
        dialogTimeVisible: false,
        dialogExamineVisible: false,
        tops: "",
        item: {
          id: "",
          username: "",
          customerName: "",
          checkinDate: "",
          retreattime: "",
          retreattype: "",
          retreatreason: "",
          audittime: "",
          auditstatus: 0,
          bedId: "",
          roleId: ""
        },
        statusArr: [
          { value: 1, label: "停用" },
          { value: 2, label: "启用" }
        ]
      },
      roleId: '',
      page: {
        total: 0,
        pageSize: 6,
        currentPag: 1,
        pagCount: 0
      },
      pageRecord: {
        total: 0,
        pageSize: 6,
        currentPag: 1,
        pagCount: 0
      },
      backdownList: [],
      khxxList: [],
      customerList: [],
      condition: {
        customerName: "",
        userId: null,
        pageSize: "1"
      },
      conditionRecord: {
        userId: null,
        pageSize: "1"
      },
    };
  },
  methods: {
    // 清理空参数
    cleanParams(params) {
      const clean = {};
      for (const key in params) {
        if (params[key] !== null && params[key] !== "") {
          clean[key] = params[key];
        }
      }
      return clean;
    },

    query() {
      this.backdownList = [];
      this.condition.pageSize = "1";
      this.getCustomerList();
      this.queryBackdownVo();
    },

    handleCurrentChange(curPage) {
      this.page.currentPag = curPage;
      this.condition.pageSize = curPage;
      this.getCustomerList();
      this.backdownList = [];
      this.conditionRecord.customerId = "";
    },

    handleChangeCustomer(row) {
      if (row != null) {
        this.conditionRecord.userId = row.id;
        this.queryBackdownVo();
      }
    },

    handleRecordChange(curPage) {
      this.pageRecord.currentPag = curPage;
      this.conditionRecord.pageSize = curPage;
      this.queryBackdownVo();
    },

    getCustomerList() {
      getCustomerList(this.cleanParams(this.condition)).then(res => {
        this.khxxList = res.data.records;
        this.customerList = res.data.records;
        this.page.total = res.data.total;
        this.page.pageSize = res.data.size;
        this.page.currentPag = res.data.current;
        this.page.pagCount = res.data.pages;
      });
    },

    queryBackdownVo() {
      queryBackdownVo(this.cleanParams(this.conditionRecord)).then(res => {
        this.backdownList = res.data.records;
        this.pageRecord.total = res.data.total;
        this.pageRecord.pageSize = res.data.size;
        this.pageRecord.currentPag = res.data.current;
        this.pageRecord.pagCount = res.data.pages;
      });
    },

    examine(id) {
      this.dialog.tops = "审批";
      this.dialog.dialogExamineVisible = true;
      this.dialog.item.id = id;
    },
    handleExamineClose() {
      this.dialog.dialogExamineVisible = false;
      this.resetForm("itemExamineForm");
    },
    cancelExamine() {
      this.handleExamineClose();
    },

    addItem() {
      this.dialog.tops = "添加退住申请";
      this.dialog.dialogVisible = true;
    },
    handleClose() {
      this.dialog.dialogVisible = false;
      this.resetForm("itemForm");
    },
    cancel() {
      this.handleClose();
    },

    resetForm(formName) {
      this.$refs[formName].resetFields();
    },

    examineBackdown(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.dialog.item.audittime = getCurDate();
          this.dialog.item.auditperson = getSessionStorage('user').id;
          examineBackdown(this.dialog.item).then(res => {
            if (res.flag) {
              this.$message.success(res.message);
              this.queryBackdownVo();
              this.handleExamineClose();
            } else {
              this.$message.error(res.message);
            }
          });
        }
      });
    },

    save(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          if (!this.dialog.item.id) {
            this.dialog.item.createTime = getCurDate();
            this.dialog.item.createBy = getSessionStorage("user").id;
            this.dialog.item.password = "000000";
            this.dialog.item.roleId = 2;
            this.dialog.item.isDeleted = 0;
            addBackdown(this.dialog.item).then(res => {
              if (res.flag) {
                this.$message.success(res.message);
                this.queryBackdownVo();
                this.handleClose();
              } else {
                this.$message.error(res.message);
              }
            });
          }
        }
      });
    },

    del(id) {
      this.$confirm("确定删除?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
          .then(() => {
            delBackdown({ id }).then(res => {
              if (res.flag) {
                this.$message.success(res.message);
                this.queryBackdownVo();
              }
            });
          })
          .catch(() => {});
    },
  },

  mounted() {
    const user = getSessionStorage('user');
    if (!user || !user.id) {
      this.$message.error("未获取到用户登录信息");
      return;
    }

    this.roleId = user.roleId;
    const userId = user.id;

    this.conditionRecord.userId = userId;   // 💡 一定要赋值
    this.condition.userId = userId;

    this.getCustomerList();
    this.queryBackdownVo();
  }

};
</script>
<style scoped>
.table-main {
  height: 600px;
  border: 1px solid #d4c5c5;
}
.table-main .table-main-header {
  height: 50px;
  background-color: #3ca2e0;
  color: #fff;
  font-size: 20px;
  font-family: "Microsoft YaHei";
  line-height: 50px;
}
</style>