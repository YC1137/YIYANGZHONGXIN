<template>
  <div class="common-layout">
    <el-container>
      <el-main>
        <el-row :gutter="20">
          <!-- 左侧 客户信息盒子 -->
          <el-col :span="11">
            <div class="table-main">
              <div class="table-main-header">护理项目</div>
              <el-table
                  :data="nurseItemList"
                  stripe
                  style="width: 100%"
                  class="nurseItemList-table"
                  :header-cell-style="{background: '#f5f7fa', color: '#606266'}"
              >
                <el-table-column
                    align="center"
                    type="index"
                    :index="indexMethod"
                    label="序号"
                    width="60"
                />
                <el-table-column align="center" prop="serialNumber" label="|编号|" width="110" />
                <el-table-column align="center" prop="nursingName" label="|名称|" width="130" />
                <el-table-column align="center" prop="servicePrice" label="|价格|" width="130"/>
                <el-table-column align="center" prop="executionCycle" label="|执行频率|" width="130" />
                <el-table-column align="center" prop="executionTimes" label="|执行次数|" width="130" />

              </el-table>
              <div style="margin-top:15px">
                <el-pagination
                    :page-size="page.pageSize"
                    background
                    :current-page="page.currentPag"
                    layout="prev, pager, next"
                    :total="page.total"
                    @current-change="handleCurrentChange"
                />
              </div>
            </div>
          </el-col>

          <!-- 右侧 外出登记盒子 -->
          <el-col :span="13">
            <div class="table-main">
              <div class="table-main-header">{{levelName}}护理项目</div>
              <el-table :data="outwardList" size="small">
                <el-table-column
                    align="center"
                    type="index"
                    :index="indexMethod"
                    label="序号"
                    width="60"
                />
                <el-table-column align="center" prop="serialNumber" label="|编号|" width="120" />
                <el-table-column align="center" prop="nursingName" label="|名称|" width="130" />
                <el-table-column align="center" prop="servicePrice" label="|价格|" width="130"/>
                <el-table-column align="center" prop="executionCycle" label="|执行频率|" width="130" />
                <el-table-column align="center" prop="executionTimes" label="|执行次数|" width="130" />


              </el-table>

              <div style="margin-top: 15px" v-if="outwardList.length > 0">
                <el-pagination
                    :page-size="pageRecord.pageSize"
                    :current-page="pageRecord.currentPag"
                    layout="prev, pager, next"
                    :total="pageRecord.total"
                    background
                />
              </div>
            </div>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
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
    // 分页序号计算（左侧）
    indexMethod() {
      return (index) => {
        return (this.page.currentPag - 1) * this.page.pageSize + index + 1;
      };
    },
    // 右侧外出登记序号
    indexMethodRecord() {
      return (index) => index + 1;
    }
  },
  data() {
    return {
      // 模态框数据
      levelId: this.$route.query.levelId,
      levelName: this.$route.query.levelName,
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
        },
        statusArr: [
          { value: 0, label: "禁用" },
          { value: 1, label: "启用" }
        ]
      },
      // 分页属性（左侧护理项目）
      page: {
        total: 0,
        pageSize: 6,
        currentPag: 1,
        pagCount: 0
      },
      // 分页参数
      queryParams: {
        status: "1",
        name: "",
        pageNum: 1
      },
      // 护理项目数据
      nurseItemList: [],
      // 外出登记数据（模拟）
      outwardList: [],
      // 外出登记分页（如你后续需要分页功能）
      pageRecord: {
        total: 0,
        pageSize: 10,
        currentPag: 1
      }
    };
  },
  mounted() {
    this.getNurseItemList();
  },
  methods: {
    // 点击查询
    query() {
      this.queryParams.pageNum = 1;
      this.getNurseItemList();
    },
    // 分页跳转
    handleCurrentChange(currentPage) {
      this.page.currentPag = currentPage;
      this.queryParams.pageNum = currentPage;
      this.getNurseItemList();
    },
    // 获取护理项目分页列表
    getNurseItemList() {
      findNurseItemPage(this.queryParams).then(res => {
        this.nurseItemList = res.data.records;
        this.page.total = res.data.total;
        this.page.pageSize = res.data.size;
        this.page.currentPag = res.data.current;
        this.page.pagCount = res.data.pages;
      }).catch(error => {
        console.error("API Error:", error.response);
        this.$message.error("请求失败：" + error.message);
      });
    }
  }
};
</script>

<style scoped>
::v-deep(.nurseItemList-table .el-table__row) {
  height: 70px !important;
}
::v-deep(.table-main .el-table__row) {
  height: 70px !important;
}
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
  padding-left: 20px;
}
</style>
