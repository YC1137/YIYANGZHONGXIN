  <template>
    <div class="common-layout">
      <el-container>
        <el-header class="header-container">
          <div class="header-content">
            <el-button
                type="primary"
                icon="Plus"
                size="default"
                @click="edit()"
                class="add-button"
            >
              添加护理级别
            </el-button>
            <div class="filter-group">
              <el-radio-group
                  v-model="queryParams.levelStatus"
                  @change="fetchNurseLevels"
                  size="default"
              >
                <el-radio-button label="1" class="active-radio">启用</el-radio-button>
                <el-radio-button label="0" class="inactive-radio">禁用</el-radio-button>
              </el-radio-group>
            </div>
          </div>
        </el-header>

        <el-main class="table-container">
          <el-table
              :data="listLevel"
              stripe
              style="width: 100%"
              class="custom-table"
              :header-cell-style="{background: '#f5f7fa', color: '#606266'}"
          >
            <el-table-column
                align="center"
                type="index"
                label="序号"
                width="80"
            />
            <el-table-column
                align="center"
                prop="levelName"
                label="护理级别"
                width="220"
            />
            <el-table-column
                align="center"
                prop="levelStatus"
                label="状态"
                width="180"
            >
              <template #default="scope">
                <el-tag
                    :type="scope.row.levelStatus ? 'success' : 'danger'"
                    effect="dark"
                    size="medium"
                >
                  {{ scope.row.levelStatus ? '启用' : '禁用' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column
                align="center"
                fixed="right"
                label="操作"
                width="320"
            >
              <template #default="scope">
                <el-button
                    type="primary"
                    icon="Edit"
                    size="small"
                    @click="edit(scope.row)"
                    class="action-button"
                >
                  编辑
                </el-button>
                <el-button
                    type="danger"
                    icon="Delete"
                    size="small"
                    @click="deleteLevel(scope.row.id)"
                    class="action-button"
                >
                  删除
                </el-button>
                <el-button
                    type="info"
                    icon="Setting"
                    size="small"
                    @click="showSettings(scope.row)"
                    class="action-button"
                >
                  护理项目设置              </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-main>
      </el-container>

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
            ref="levelForm"
            :model="dialog.level"
            :rules="dialog.rules"
        >
          <el-form-item
              label="护理级别"
              prop="levelName"
              class="form-item"
          >
            <el-input
                v-model="dialog.level.levelName"
                placeholder="请输入护理级别名称"
                clearable
            />
          </el-form-item>
          <el-form-item
              label="状态"
              prop="levelStatus"
              class="form-item"
          >
            <el-select
                v-model="dialog.level.levelStatus"
                placeholder="请选择状态"
                style="width: 100%"
            >
              <el-option
                  v-for="item in dialog.statusArray"
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
                @click="submitLevel"
                class="submit-button"
            >
              提交
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
    getAllNurseLevels,
    addNurseLevel,
    updateNurseLevel,
    delNurseLevel
  } from '@/api/nurseLevel'

  export default {
    data() {
      return {
        queryParams: { levelStatus: '1' }, // 默认显示启用的
        listLevel: [],
        dialog: {
          dialogVisible: false,
          tops: '',
          level: {
            id: null,
            levelName: '',
            levelStatus: 1
          },
          rules: {
            levelName: [
              { required: true, message: '请输入护理级别', trigger: 'blur' },
              { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
            ],
            levelStatus: [
              { required: true, message: '请选择状态', trigger: 'change' }
            ]
          },
          statusArray: [
            { value: 1, label: '启用' },
            { value: 0, label: '禁用' }
          ]
        }
      };
    },
    methods: {
      edit(row) {
        this.dialog.dialogVisible = true;
        this.dialog.tops = row ? `编辑 ${row.levelName} 护理` : '添加护理级别';

        if (row) {
          this.dialog.level = {
            id: row.id,
            levelName: row.levelName,
            levelStatus: row.levelStatus
          };
        } else {
          this.dialog.level = {
            id: null,
            levelName: '',
            levelStatus: 1
          };
        }
      },

      showSettings(row) {
        // 例如打开一个新的对话框或页面来设置护理项目


        this.$router.push({
              path: '/nurse/AddItemToLevel',
          query: { userId: row.id,levelName:row.levelName }
        });
      },

      handleClose(done) {
        this.$confirm('确定要关闭吗？未保存的更改将会丢失', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          done();
        }).catch(() => {});
      },

      async submitLevel() {
        this.$refs.levelForm.validate(async (valid) => {
          if (valid) {
            try {
              if (this.dialog.level.id) {
                await updateNurseLevel(this.dialog.level);
                this.$message.success('更新成功');
              } else {
                await addNurseLevel(this.dialog.level);
                this.$message.success('添加成功');
              }
              await this.fetchNurseLevels();
              this.dialog.dialogVisible = false;
            } catch (error) {
              this.$message.error('操作失败: ' + error.message);
            }
          }
        });
      },

      async deleteLevel(id) {
        try {
          await this.$confirm('确定要删除该护理级别吗?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
            customClass: 'delete-confirm'
          });

          await delNurseLevel(id);
          this.$message.success('删除成功');
          await this.fetchNurseLevels();
        } catch (error) {
          if (error !== 'cancel') {
            this.$message.error('删除失败: ' + error.message);
          }
        }
      },

      async fetchNurseLevels() {
        try {
          const params = this.queryParams.levelStatus === ''
              ? {}
              : { levelStatus: this.queryParams.levelStatus };

          const { data } = await getAllNurseLevels(params);
          this.listLevel = data;
        } catch (error) {
          this.$message.error('获取护理级别失败: ' + error.message);
        }
      }
    },

    async mounted() {
      await this.fetchNurseLevels();
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
  }

  .filter-group {
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

  .delete-confirm :deep(.el-message-box__header) {
    background: #f56c6c;
    color: white;
  }

  .delete-confirm :deep(.el-message-box__title) {
    color: white;
  }
  </style>