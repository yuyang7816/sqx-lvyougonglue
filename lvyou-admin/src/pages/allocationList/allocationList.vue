<template>
  <div class="menu">
    <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
      <el-tab-pane v-for="(item, index) in liaotianData" :key="index" :label="item.paramTypeDesc"
        :name="'index' + index">

        <el-table :data="item.sysConfigEntityList" v-loading="loading" style="width: 100%">
          <el-table-column prop="id" label="编号" width="100"></el-table-column>
          <el-table-column prop="remark" label="类型" width="300"></el-table-column>
          <el-table-column prop="paramValue" label="内容">
            <template #default="scope">
              <el-popover placement="top-start" title="" trigger="hover">

                <div style="width: 300px;">{{ scope.row.paramValue }}</div>
                <template #reference>
                  <div
                    style="display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 3;overflow: hidden;max-height:80px;">
                    {{ scope.row.paramValue }}</div>
                </template>
              </el-popover>
            </template>
          </el-table-column>

          <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
          <el-table-column label="操作" width="100">
            <template #default="scope">
              <el-button size="small" type="primary" @click="addChangeU(scope.row)" style="margin: 5px;">修改</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-row class="el-rows">
          <el-col :span="24">
            <div class="topBtn flex align-center justify-end">
              <pagination :pageSize="limit" v-model:currentPage="page" :total="total" @pageChange="qunzuSelect()" />
            </div>
          </el-col>
        </el-row>
      </el-tab-pane>

    </el-tabs>
    <!-- 弹框 -->
    <!-- 审核 弹框 -->
    <el-dialog v-model="dialogFormVisibleU" title="修改" width="80%" center>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">类型：</span>
        <el-input style="width:50%;" v-model="remark" type="text" placeholder="请输入类型"></el-input>
      </div>
      <div style="margin-bottom: 10px;position: relative;" v-if="paramType == 'xieyi'">
        <span style="width: 200px;display: inline-block;text-align: right;position: absolute;top: 0;">描述：</span>
        <div style="width:50%;display: inline-block;margin-left: 200px;">
          <QuillEditor v-model:content="paramValue" contentType="html" :options="editorOptions" @ready="onEditorReady"
            style="min-height: 300px;" />
        </div>
        <el-input style="width:50%;" v-model="paramValue" type="text" placeholder="请输入描述"></el-input>
        <!-- <VueQuillEditor v-model:content="paramValue" /> -->
      </div>
      <div style="margin-bottom: 10px;" v-else>
        <span style="width: 200px;display: inline-block;text-align: right;">描述：</span>
        <el-input style="width:50%;" v-model="paramValue" type="text" placeholder="请输入描述"></el-input>
      </div>

      <!-- <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">图像：</span>
                    <div style="display: inline-block;">
                        <el-upload class="avatar-uploader" v-model="imageUrl" style=" width:148px;height:148px;border: 1px dashed #c0ccda;border-radius: 6px;text-align: center;line-height: 148px;color: #efefef;"
                            :action="$uploadUrl" :show-file-list="false"
                            :on-success="handleAvatarSuccess1">
                            <img v-if="imageUrl" :src="imageUrl" class="avatar"
                                style="width: 148px;height: 148px;" />
                            <SvgIcon v-else style="width:23px;height:23px;color: #efefef;" name="svg:plus.svg"></SvgIcon>
                        </el-upload>
                    </div>
                </div> -->
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
import { useRouter } from 'vue-router';
import pagination from '@/components/pagination/pagination.vue'
import {
  useUserStore
} from '@/stores/user'
import {
  ElMessage
} from 'element-plus';
import { QuillEditor } from '@vueup/vue-quill';
import '@vueup/vue-quill/dist/vue-quill.snow.css';
const {
  proxy
} = getCurrentInstance();
const router = useRouter();
const activeName = ref('index0'); //默认选中
const liaotianData = reactive([]); //聊天记录 集合
const userStore = useUserStore() //获取 管理员信息
const page = ref(1);//当前页
const limit = ref(10);//每页条数
const total = ref(0);//总条数

//列表是否加载中
const tableDataLoading = ref(false);
const classify = ref(1);
const userNameT = ref('');
const typeT = ref('');
const contentT = ref('');
const dialogFormVisibleU = ref(false);
const id = ref('');
const targetId = ref('');
const description = ref('');
const status = ref('');
const classifyIdT = ref('');

const classifyIds = reactive([]);
const paramKey = ref('');
const paramType = ref('');
const paramTypeDesc = ref('');
const paramValue = ref('');
const remark = ref('');
// 富文本
// const router = useRouter();
// const route = useRoute();
const url = ''
const formData = ref({
  id: null,
  CONT: null
});
const editorOptions = {
  modules: {
    toolbar: [
      ['bold', 'italic', 'underline', 'strike'], // 加粗 斜体 下划线 删除线
      [{ color: [] }, { background: [] }], // 字体颜色、字体背景颜色
      [{ align: [] }], // 对齐方式
      [{ size: ['small', false, 'large', 'huge'] }], // 字体大小
      [{ font: [] }], // 字体种类
      [{ header: [1, 2, 3, 4, 5, 6, false] }], // 标题
      [{ direction: 'ltl' }], // 文本方向
      [{ direction: 'rtl' }], // 文本方向
      [{ indent: '-1' }, { indent: '+1' }], // 缩进
      [{ list: 'ordered' }, { list: 'bullet' }], // 有序、无序列表
      [{ script: 'sub' }, { script: 'super' }], // 上标/下标
      ['blockquote', 'code-block'], // 引用  代码块
      ['clean'], // 清除文本格式
      ['link', 'image', 'video'], // 链接、图片、视频
    ]
  }
}

onMounted(() => {
  liaotianSelect();
});
//跳转用户详情
const updates = (userId) => {
  router.push({
    name: 'userDetail1',
    query: {
      userId: userId,
    },

  });
};

// 切换tab 事件
const handleClick = (tab, event) => {
  for (var i in classifyIds) {
    if (event.srcElement.innerText == classifyIds[i].name) {
      classifyIdT.value = classifyIds[i].id
      liaotianSelect();
    }
  }

}

// 聊天记录 列表
const liaotianSelect = () => {

  tableDataLoading.value = true
  liaotianData.splice(0, liaotianData.length)
  proxy.$Request.get('/sys/config/list', {
    // params: {
    //   page: page.value,
    //   limit: limit.value,
    //   targetType: classify.value,
    //   userName: userNameT.value,
    //   status: typeT.value,
    //   targetUserName: contentT.value,
    //   targetChatRoomsName: '',

    // }
  }, {
    // type: 'json'
  }).then((res) => {
    console.log(res)
    tableDataLoading.value = false;
    liaotianData.splice(0, liaotianData.length, ...res.data)
    // total.value = res.data.total;
  })


}
// 刷新
const handleSearch = () => {
  page.value = 1
  liaotianSelect()
}
// 重置
const clearSearch = () => {
  page.value = 1
  userNameT.value = ''
  typeT.value = ''
  contentT.value = ''
  liaotianSelect()
}
// 修改弹框
const addChangeU = (row) => {
  id.value = row.id
  paramKey.value = row.paramKey
  paramType.value = row.paramType
  paramTypeDesc.value = row.paramTypeDesc
  paramValue.value = row.paramValue
  remark.value = row.remark
  status.value = row.status



  dialogFormVisibleU.value = true
}
const StairNoticeToU = () => {

  proxy.$Request.post('/sys/config/update', {
    id: id.value,
    paramKey: paramKey.value,
    paramType: paramType.value,
    paramTypeDesc: paramTypeDesc.value,
    paramValue: paramValue.value,
    remark: remark.value,
    status: status.value,

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
  proxy.$Request.post('/admin/base/banner/delete', {
    id: data.id,
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

.editor-container {
  width: 50%;
  max-height: 100%;
  overflow-y: auto;
  border: 1px solid #d8d8d8;
}

.ql-.ql-toolbar.ql-snow {
  width: 50% !important;

}
</style>
