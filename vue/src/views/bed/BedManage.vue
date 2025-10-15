<template>
  <div class="common-layout">
    <el-container>
      <el-header class="header-container">
        <div class="header-content">
          <el-row :gutter="20" align="middle">
            <!-- 日期选择器放在第一列 -->
            <el-col :span="12">
              <el-date-picker
                  v-model="dateChange"
                  type="daterange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  value-format="YYYY-MM-DD"
                  format="YYYY/MM/DD"
                  size="large"
                  style="width: 100%"
              />
            </el-col>

            <!-- 查询框放在第二列 -->
            <el-col :span="6">
              <el-input
                  placeholder="客户姓名"
                  v-model="queryParams.customerName"
                  @clear="query"
                  clearable
                  size="large"
                  style="margin-left: 15px"
              >
                <template #append>
                  <el-button type="info" @click="query" style="color:black">查询</el-button>
                </template>
              </el-input>
            </el-col>

            <!-- 状态按钮放在第三列 -->
            <el-col :span="6" class="button-group">
              <el-button
                  :class="[{activeBtn: btnFlag}]"
                  @click="doing"
                  size="large"
              >正在使用</el-button>
              <el-button
                  :class="[{activeBtn: !btnFlag}]"
                  @click="history"
                  size="large"
              >使用历史</el-button>
            </el-col>
          </el-row>
        </div>
      </el-header>

      <el-divider style="margin:0"></el-divider>

      <el-main class="table-container">
        <!-- 表格 -->
        <el-table
            :data="bedDetailsList"
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
          <el-table-column align="center" prop="customerName" label="客户姓名" width="120" />
          <el-table-column align="center" prop="customerSex" label="性别" width="100">
            <template #default="scope">{{ scope.row.customerSex==0?'男':'女'}}</template>
          </el-table-column>
          <el-table-column align="center" prop="bedDetails" label="床位详情" width="120" />
          <el-table-column align="center" prop="startDate" label="使用起始日期" width="150" />
          <el-table-column align="center" prop="endDate" label="使用结束日期" width="150" />
          <el-table-column align="center" fixed="right" label="操作" width="220">
            <template #default="scope">
              <el-button
                  type="warning"
                  icon="Switch"
                  size="small"
                  v-if="btnFlag"
                  @click="exchange(scope.row)"
                  class="action-button"
              >调换</el-button>
              <el-button
                  type="primary"
                  icon="Edit"
                  size="small"
                  v-if="btnFlag"
                  @click="editMessage(scope.row)"
                  class="action-button"
              >修改</el-button>
              <el-button
                  type="danger"
                  icon="Delete"
                  size="small"
                  v-if="!btnFlag"
                  @click="del(scope.row.id)"
                  class="action-button"
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <!-- 分页 -->
        <div class="pagination-container">
          <el-pagination
              :page-size="page.pageSize"
              background
              :current-page="page.currentPag"
              layout="prev, pager, next"
              :total="page.total"
              @current-change="handleCurrentChange"
          />
        </div>
      </el-main>
    </el-container>

    <!-- 修改对话框 -->
    <el-dialog
        v-model="edit.dialogVisible"
        :title="edit.tops || '信息修改'"
        width="600px"
        align-center
        draggable
        class="custom-dialog"
        :before-close="editClose"
    >
      <el-divider border-style="dashed" class="divider"/>
      <el-form
          label-position="right"
          label-width="100px"
          class="custom-form"
          :model="edit.bedDetalsForm"
          :rules="rules"
          ref="editForm"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="客户姓名：" prop="customerName">
              <el-input v-model="edit.bedDetalsForm.customerName" readonly />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别：" prop="customerSex">
              <el-input
                  :value="edit.bedDetalsForm.customerSex==0?'男':'女'"
                  readonly
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="床位详情：" prop="bedDetails">
          <el-input v-model="edit.bedDetalsForm.bedDetails" readonly />
        </el-form-item>

        <el-form-item label="起始日期：" prop="startDate">
          <el-input v-model="edit.bedDetalsForm.startDate" readonly />
        </el-form-item>

        <el-form-item label="结束日期：" prop="endDate" class="form-item-required">
          <el-date-picker
              v-model="edit.bedDetalsForm.endDate"
              type="date"
              placeholder="选择日期"
              value-format="YYYY-MM-DD"
              style="width: 100%"
          />
        </el-form-item>
      </el-form>

      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="editSave('editForm')">保存</el-button>
          <el-button @click="editCancel">取消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 调换对话框 -->
    <el-dialog
        v-model="exchangeData.dialogVisible"
        title="床位调换"
        width="700px"
        align-center
        draggable
        class="custom-dialog"
        :before-close="exchangeClose"
    >
      <el-divider border-style="dashed" class="divider"/>
      <el-form
          label-position="right"
          label-width="100px"
          class="custom-form"
          :model="exchangeData.exchangeForm"
          ref="exchangeForm"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="客户姓名：" prop="customerName">
              <el-input v-model="exchangeData.exchangeForm.customerName" readonly />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别：" prop="customerSex">
              <el-input
                  :value="exchangeData.exchangeForm.customerSex==0?'男':'女'"
                  readonly
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="原床位：" prop="bedDetails">
              <el-input v-model="exchangeData.exchangeForm.bedDetails" readonly />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="楼号：" prop="buildingNo">
              <el-input v-model="exchangeData.exchangeForm.buildingNo" readonly />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="新房间：" prop="newRoomNo">
              <el-select
                  v-model="exchangeData.exchangeForm.newRoomNo"
                  placeholder="请选择房间"
                  style="width: 100%"
                  @change="getBed"
              >
                <el-option-group
                    v-for="group in exchangeData.roomList"
                    :key="group.label"
                    :label="group.label"
                >
                  <el-option
                      v-for="item in group.options"
                      :key="item.roomNo"
                      :label="item.roomNo"
                      :value="item.roomNo"
                  />
                </el-option-group>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="新床位：" prop="newBedId">
              <el-select
                  v-model="exchangeData.exchangeForm.newBedId"
                  placeholder="请选择床位"
                  style="width: 100%"
                  :disabled="!exchangeData.exchangeForm.newRoomNo"
              >
                <el-option
                    v-for="item in exchangeData.bedList"
                    :key="item.bedNo"
                    :label="item.bedNo"
                    :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="起始日期：" prop="startDate">
              <el-input v-model="exchangeData.exchangeForm.startDate" readonly />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="结束日期：" prop="endDate">
              <el-input v-model="exchangeData.exchangeForm.endDate" readonly />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="exchangeSave">保存</el-button>
          <el-button @click="exchangeCancel">取消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import {
  findBedDetailsList,
  updateDetailsStartDate,
  findBedByRoom,
  exchangeBed,
  delBedDetails
} from "@/api/bedApi.js";
import { listRoom } from "@/api/roomApi.js";

export default {
  computed: {
    indexMethod() {
      return this.page.currentPag * this.page.pageSize - this.page.pageSize + 1;
    }
  },
  mounted() {
    this.getBedDetailsList();
  },
  data() {
    return {
      queryParams: {
        customerName: "",
        startDate: "",
        endDate: "",
        pageSize: "1",
        isDeleted: "0"
      },
      dateChange: [],
      bedDetailsList: [],
      page: {
        total: 0,
        pageSize: 6,
        currentPag: 1,
        pagCount: 0
      },
      edit: {
        dialogVisible: false,
        tops: "信息修改",
        bedDetalsForm: {
          id: "",
          customerName: "",
          customerSex: "",
          bedDetails: "",
          startDate: "",
          endDate: ""
        }
      },
      exchangeData: {
        dialogVisible: false,
        exchangeForm: {
          customerName: "",
          customerSex: "",
          bedDetails: "",
          startDate: "",
          oldRoomNo: "",
          id: "",
          customerId: "",
          buildingNo: "606",
          newRoomNo: "",
          newBedId: "",
          oldBedId: "",
          endDate: ""
        },
        roomList: [],
        bedList: []
      },
      rules: {
        endDate: [
          { required: true, message: "请选择结束日期", trigger: "change" }
        ]
      },
      btnFlag: true
    };
  },
  methods: {
    query() {
      if (this.dateChange && this.dateChange.length === 2) {
        this.queryParams.startDate = this.dateChange[0];
        this.queryParams.endDate = this.dateChange[1];
      } else {
        this.queryParams.startDate = "";
        this.queryParams.endDate = "";
      }
      this.queryParams.pageSize = "1";
      this.getBedDetailsList();
    },
    handleCurrentChange(curPage) {
      this.page.currentPag = curPage;
      this.queryParams.pageSize = curPage;
      this.getBedDetailsList();
    },
    doing() {
      this.btnFlag = true;
      this.queryParams.isDeleted = "0";
      this.queryParams.pageSize = "1";
      this.getBedDetailsList();
    },
    history() {
      this.btnFlag = false;
      this.queryParams.isDeleted = "1";
      this.queryParams.pageSize = "1";
      this.getBedDetailsList();
    },
    editMessage(row) {
      this.edit.dialogVisible = true;
      this.edit.bedDetalsForm = {
        ...row,
        id: row.id
      };
    },
    resetForm(formName) {
      this.$refs[formName]?.resetFields();
    },
    editClose() {
      this.edit.dialogVisible = false;
      this.resetForm("editForm");
    },
    editCancel() {
      this.editClose();
    },
    exchangeClose() {
      this.exchangeData.dialogVisible = false;
      this.resetForm("exchangeForm");
    },
    exchange(row) {
      this.exchangeData.dialogVisible = true;
      this.exchangeData.exchangeForm = {
        ...row,
        id: row.id,
        customerId: row.customerId,
        oldBedId: row.bedId,
        newRoomNo: "",
        newBedId: ""
      };
      this.getRoomList();
    },
    exchangeCancel() {
      this.exchangeClose();
    },
    del(id) {
      this.$confirm("确定删除此记录吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        delBedDetails({id}).then(res => {
          if(res.flag) {
            this.$message.success(res.message);
            this.getBedDetailsList();
          } else {
            this.$message.error(res.message);
          }
        });
      }).catch(() => {});
    },
    exchangeSave() {
      if (!this.exchangeData.exchangeForm.newRoomNo) {
        this.$message.warning("请选择新房间");
        return;
      }
      if (!this.exchangeData.exchangeForm.newBedId) {
        this.$message.warning("请选择新床位");
        return;
      }

      exchangeBed(this.exchangeData.exchangeForm).then(res => {
        if (res.flag) {
          this.$message.success(res.message);
          this.exchangeClose();
          this.getBedDetailsList();
        } else {
          this.$message.error(res.message);
        }
      });
    },
    getRoomList() {
      listRoom().then(res => {
        const floor1 = res.data.filter(item => item.roomFloor == "一楼");
        const floor2 = res.data.filter(item => item.roomFloor == "二楼");
        this.exchangeData.roomList = [
          { label: "一楼", options: floor1 },
          { label: "二楼", options: floor2 }
        ];
      });
    },
    getBed() {
      this.exchangeData.bedList = [];
      this.exchangeData.exchangeForm.newBedId = "";

      if (this.exchangeData.exchangeForm.newRoomNo) {
        findBedByRoom({
          bedStatus: 1,
          roomNo: this.exchangeData.exchangeForm.newRoomNo
        }).then(res => {
          this.exchangeData.bedList = res.data;
        });
      }
    },
    editSave(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          updateDetailsStartDate(this.edit.bedDetalsForm).then(res => {
            if (res.flag) {
              this.$message.success(res.message);
              this.editClose();
              this.getBedDetailsList();
            } else {
              this.$message.error(res.message);
            }
          });
        }
      });
    },
    getBedDetailsList() {
      findBedDetailsList(this.queryParams).then(res => {
        this.bedDetailsList = res.data.records;
        this.page.total = res.data.total;
        this.page.pageSize = res.data.size;
        this.page.currentPag = res.data.current;
        this.page.pagCount = res.data.pages;
      });
    }
  }
};
</script>

<style scoped>
.common-layout {
  padding: 20px;
  background-color: #f8fafc;
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

.button-group {
  display: flex;
  justify-content: flex-end;
}

.activeBtn {
  background: #f0f9eb;
  color: #67c23a;
  border-color: #e1f3d8;
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

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
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

.el-row {
  margin-bottom: 20px;
}
</style>