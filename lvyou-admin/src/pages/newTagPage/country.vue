<template>
  <div class="menu">
    <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
      <el-tab-pane label="国家列表" name="first">
        <div style="text-align:rightz;margin: 5px;">
          <!-- <div style="display: inline-block;margin: 5px;">
                        <span>是否热门：</span>
                        <el-select v-model="dataContainer.popularT" class="m-2" placeholder="请选择是否热门"
                            style="width: 150px;" @change="handleSearch">
                            <el-option v-for="(item, index) in dataContainer.statesnum" :key="index" :label="item.label"
                                :value="item.value" />
                        </el-select>
                    </div>
                    <div style="display: inline-block;margin: 5px;">
                        <span>是否精选：</span>
                        <el-select v-model="dataContainer.choicenessT" class="m-2" placeholder="请选择是否精选"
                            style="width: 150px;" @change="handleSearch">
                            <el-option v-for="(item, index) in dataContainer.statesnum" :key="index" :label="item.label"
                                :value="item.value" />
                        </el-select>
                    </div>
                    <div style="display: inline-block;margin: 5px;">
                        <span>城市：</span>
                        <el-input v-model="dataContainer.cityT" placeholder="请输入城市" @keydown.enter.native="handleSearch"
                            style="width: 150px;" />
                    </div> -->
          <el-button size="small" type="primary" plain @click="handleSearch()">刷新</el-button>
          <!-- <el-button size="small" type="warning" plain @click="handleClear()">重置</el-button> -->
          <el-button size="small" type="primary" @click="addChangeU()"
            :disabled="!isAuth('vipPrivilege:add')">新增</el-button>
        </div>
        <el-table :data="liaotianData" v-loading="tableDataLoading" style="width: 100%">
          <el-table-column prop="id" label="编号" width="80">
          </el-table-column>
          <el-table-column prop="countryName" label="国家名称">
          </el-table-column>
          <el-table-column prop="englishName" label="国家英文名">
          </el-table-column>
          <el-table-column prop="cover" label="封面">
            <template #default="scope">
              <img v-if="scope.row.cover" style="width: 50px; height: 50px;margin: 3px;" :src="scope.row.cover" alt="">
            </template>
          </el-table-column>
          <el-table-column prop="album" label="相册" width="300">
            <template #default="scope">
              <div v-if="scope.row.album == null || scope.row.album == ''">
                暂无图片
              </div>
              <div v-if="scope.row.album" style="display:flex;flex-wrap: wrap;">
                <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;"
                  v-for="(item, index) in scope.row.album.split(',')" :key="index">
                  <template #reference>
                    <img style="width: 50px; height: 50px;margin: 3px;" :src="item" alt="">
                  </template>
                  <template #default style="width: 420px;">
                    <img style="width: 400px; height: auto;" :src="item" alt="">
                  </template>
                </el-popover>
              </div>
            </template>
          </el-table-column>
          <!-- <el-table-column prop="description" label="描述" width="300">
                    </el-table-column>
                    <el-table-column prop="popular" label="是否热门">
                        <template #default="scope">
                            <span v-if="scope.row.popular == 1">是</span>
                            <span v-if="scope.row.popular == 2">否</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="choiceness" label="是否精选">
                        <template #default="scope">
                            <span v-if="scope.row.choiceness == 1">是</span>
                            <span v-if="scope.row.choiceness == 2">否</span>
                        </template>
                    </el-table-column> -->
          <!-- <el-table-column prop="sort" label="排序">
                    </el-table-column> -->
          <el-table-column prop="createTime" label="创建时间">
          </el-table-column>
          <el-table-column label="操作" width="160">
            <template #default="scope">

              <el-button size="small" type="primary" @click="addChangeU(scope.row)" style="margin: 5px;"
                :disabled="!isAuth('vipPrivilege:update')">修改</el-button>
              <el-button size="small" type="danger" @click="handleDelete(scope.row)" style="margin: 5px;"
                :disabled="!isAuth('vipPrivilege:delete')">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-row class="el-rows">
          <el-col :span="24">
            <div class="topBtn flex align-center justify-end">
              <pagination :pageSize="limit" v-model:currentPage="page" :total="total" @pageChange="liaotianSelect()" />
            </div>
          </el-col>
        </el-row>
      </el-tab-pane>
    </el-tabs>
    <!-- 弹框 -->
    <!-- 添加/修改 弹框 -->
    <el-dialog v-model="dialogFormVisibleU" :title="titles" width="80%" center>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">名称：</span>
        <el-input style="width:50%;" v-model="name" type="text" placeholder="请输入名称"></el-input>
      </div>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">图片 ：</span>
        <div style="display: inline-block;">
          <el-upload class="avatar-uploader" v-model="imageUrl"
            style="width: 148px;height: 148px;line-height: 148px;text-align: center;"
            :action="$uploadUrl" :show-file-list="false"
            :on-success="handleAvatarSuccess1">
            <img v-if="imageUrl" :src="imageUrl" class="avatar" style="width: 148px;height: 148px;" />
            <el-icon v-else style="font-size: 28px;color: #D9D9D9">
              <Plus />
            </el-icon>
          </el-upload>
        </div>
      </div>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">排序：</span>
        <el-input style="width:50%;" v-model="sort" type="text" placeholder="请输入排序"></el-input>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogFormVisibleU = false" style="margin: 10px;">取消</el-button>
          <el-button type="primary" @click="StairNoticeToU" style="margin: 10px;">确认</el-button>
        </span>
      </template>
    </el-dialog>

  </div>
</template>

<script setup>
import {
  ref,
  reactive,
  getCurrentInstance,
  onMounted
} from 'vue';
import pagination from '@/components/pagination/pagination.vue'
import {
  ElMessage,
  ElMessageBox
} from 'element-plus';
const {
  proxy
} = getCurrentInstance();
const activeName = ref('first'); //默认选中
const liaotianData = reactive([]); //数据 集合
const page = ref(1);//当前页
const limit = ref(10);//每页条数
const total = ref(0);//总条数

const tableDataLoading = ref(false);//列表是否加载中
const dialogFormVisibleU = ref(false);
const routeUrl = ref('');
const titles = ref('添加');

const classify = ref('');
const id = ref('');
const name = ref('');
const imageUrl = ref('');
const sort = ref('');


onMounted(() => {
  liaotianSelect();
});

// 切换tab 事件
const handleClick = (tab, event) => {
  if (event.srcElement.innerText == '国家列表') {
    classify.value = 1
    liaotianSelect();
  }
}

//分类数据 列表
const liaotianSelect = () => {
  tableDataLoading.value = true
  liaotianData.splice(0, liaotianData.length)
  proxy.$Request.get('/admin/member/getMemberList', {
    params: {
      pageNum: page.value,
      pageSize: limit.value,
      memberName: '',
    }
  }, {
    // type: 'json'
  }).then((res) => {
    console.log(res)
    tableDataLoading.value = false;
    liaotianData.splice(0, liaotianData.length, ...res.data.records)
    total.value = res.data.total;
  })
}

// 刷新 分类
const handleSearch = () => {
  page.value = 1
  liaotianSelect()
}

// 添加/修改 分类 弹框
const addChangeU = (row) => {
  if (row) {
    titles.value = '修改'
    id.value = row.id
    name.value = row.memberName
    imageUrl.value = row.memberImg
    sort.value = row.sort
  } else {
    titles.value = '添加'
    id.value = ''
    name.value = ''
    imageUrl.value = ''
    sort.value = ''
  }
  dialogFormVisibleU.value = true
}

const StairNoticeToU = () => {
  if (titles.value == '添加') {
    routeUrl.value = '/admin/member/saveMember'
  } else {
    routeUrl.value = '/admin/member/saveMember'
  }
  proxy.$Request.post(routeUrl.value, {
    id: id.value,
    memberName: name.value,
    memberImg: imageUrl.value,
    sort: sort.value,
  }, {
    type: 'json'
  }).then((res) => {
    console.log(res)
    if (res.code == 0) {
      ElMessage({
        message: '操作成功',
        type: 'success',
        onClose: () => {
          dialogFormVisibleU.value = false
          liaotianSelect()
        }
      })
    } else {
      ElMessage({
        message: res.msg,
        type: 'warning',
        onClose: () => {
        }
      })
    }
  })
}

/** 删除 */
const handleDelete = (data) => {
  ElMessageBox.confirm('确定要删除数据?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  })
    .then(() => {
      proxy.$Request.post('/admin/member/deleteMember', {
        memberId: data.memberId,
      }, {
        type: 'json'
      }).then((res) => {
        console.log(res)
        if (res.code == 0) {
          ElMessage({
            message: '操作成功',
            type: 'success',
            onClose: () => {
              liaotianSelect()
            }
          })
        } else {
          ElMessage({
            message: res.msg,
            type: 'warning',
            onClose: () => {
            }
          })
        }
      })
    })

}

function handleAvatarSuccess1(file) {
  imageUrl.value = file.data.path
}


</script>

<style lang="scss" scoped>
.menu {
  width: 100%;
  // height: 100%;
  background-color: #ffffff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: var(--el-box-shadow-light);
  display: flow-root; // 创建块格式化上下文，防止外部浮动影响
}

.el-rows {
  margin-top: 20px;
}

span {
  color: #8c939d;
}

.main-view {
  display: flex;
  flex-direction: column;
  width: 100%;

  .container {
    // background-color: white;
    width: 100%;
    // min-height: 800px;
    height: fit-content;
    border-radius: 5px;
    padding: 30px;
    box-sizing: border-box;

    >* {
      margin: 0 0 30px 0;

      &:last-child {
        margin: 0;
      }
    }
  }
}

.detailtabel h2 {
  margin-top: 0;
}

.detailtabel .table_main table {
  width: 100%;
}

.detailtabel .table_main {
  border: 1px solid #e8e8e8;
}

.detailtabel table tr {
  border-bottom: 1px solid #e8e8e8;
}

.detailtabel table tr th {
  background-color: #fafafa;
  padding: 16px 24px;
  border-right: 1px solid #e8e8e8;
  border-bottom: 1px solid #e8e8e8;
}

.detailtabel table tr td {
  padding: 16px 24px;
  border-right: 1px solid #e8e8e8;
  border-bottom: 1px solid #e8e8e8;
}

.detailtabel table th {
  color: rgba(0, 0, 0, 0.85);
  font-weight: 400;
  font-size: 14px;
  line-height: 1.5;
}

.border-rt {
  border-right: none !important;
}

.border-bt {
  border-bottom: none !important;
}

.el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  border-color: #409EFF;
  width: 148px;
  height: 148px;
}

.el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 148px;
  height: 148px;
  line-height: 148px;
  text-align: center;
}

.avatar {
  width: 148px;
  height: 148px;
  display: block;
}
</style>
