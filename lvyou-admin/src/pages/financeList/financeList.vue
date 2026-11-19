<template>
  <div class="menu">
    <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">

      <el-tab-pane label="支付统计" name="third">
        <div style="margin-top: 20px;">
          <el-select v-model="flag" style="width:150px;margin-left: 10px;" @change="orderfenxi()">
            <el-option v-for="(item, index) in flags" :key="index" :label="item.label" :value="item.value">
            </el-option>
          </el-select>&nbsp;&nbsp;
          <el-date-picker v-model="info" type="date" placeholder="选择时间" format="YYYY-MM-DD" date-format="MMM DD, YYYY"
            time-format="HH:mm" @change="orderfenxi()" />
        </div>
        <div class="dashboard">
          <el-row :gutter="20" style="margin-top: 40px;" v-loading="tableDataLoading">
            <el-col :span="6" class="stat-col">
              <el-card class="stat-card">
                <div class="stat-content">
                  <div class="stat-number">{{ zongMoney ? zongMoney : 0 }}</div>
                  <div class="stat-label">支付总金额</div>
                </div>
              </el-card>
            </el-col>
            <el-col :span="6" class="stat-col" v-for="(item, index) in withdrawDataZf" :key="index">
              <el-card class="stat-card">
                <div class="stat-content">
                  <div class="stat-number">{{ item.amount ? item.amount : '0' }}</div>
                  <div class="stat-label" v-if="item.platform == 1">微信支付金额</div>
                  <div class="stat-label" v-if="item.platform == 2">支付宝支付金额</div>
                  <div class="stat-label" v-if="item.platform == 5">余额支付金额</div>
                </div>
              </el-card>
            </el-col>
          </el-row>
        </div>
      </el-tab-pane>
      <el-tab-pane label="支付记录" name="fourth">

        <div style="display: inline-block;margin: 5px;">
          <span>状态：</span>
          <el-select v-model="statusT" class="m-2" placeholder="请选择状态" style="width: 150px;" @change="timeDate2">
            <el-option v-for="(item, index) in statusDataZ" :key="index" :label="item.label" :value="item.value" />
          </el-select>
        </div>&nbsp;&nbsp;
        <!-- <div style="display: inline-block;margin: 5px;">
                <span>支付方式：</span>
                <el-select v-model="inviteVerifyT" class="m-2" placeholder="请选择支付方式："
                    style="width: 150px;" @change="timeDate2">
                    <el-option v-for="(item, index) in inviteVerifyDataZ" :key="index"
                        :label="item.label" :value="item.value" />
                </el-select>
            </div> -->
        <div style="display: inline-block;">
          <span>开始时间：</span>
          <el-date-picker style="width: 160px;margin-left: 10px;" v-model="startTime" align="right" type="date"
            format="YYYY-MM-DD" date-format="MMM DD, YYYY" placeholder="选择开始时间">
          </el-date-picker>&nbsp;
          <span>截止时间：</span>
          <el-date-picker style="width: 160px;margin-left: 10px;" v-model="endTime" align="right" type="date"
            format="YYYY-MM-DD" date-format="MMM DD, YYYY" placeholder="选择截止时间">
          </el-date-picker>&nbsp;&nbsp;
          <el-button style="margin-left:15px;" size="small" type="primary" icon="document"
            @click="timeDate2">查询</el-button>
          <el-button size="small" type="warning" plain @click="clearSearchZf()">重置</el-button>
        </div>
        <el-table v-loading="tableDataLoading" :data="rechargeData" style="width: 100%">
          <el-table-column fixed prop="id" label="编号" width="80">
          </el-table-column>
          <el-table-column prop="userName" label="用户名称">
            <template #default="scope">
              <el-button size="mini" style="color: #008000;background: #fff;border: none;padding: 0;" type="primary"
                :disabled="!isAuth('userList:details')" @click="updates(scope.row)">
                {{ scope.row.userName ? scope.row.userName : '未绑定' }}
              </el-button>
            </template>
          </el-table-column>
          <el-table-column prop="orderNo" label="支付订单号" width="250">
          </el-table-column>
          <el-table-column prop="amount" label="支付金额" width="100">
          </el-table-column>
          <el-table-column label="支付方式" width="120">
            <template #default="scope">
              <span style="color: #4f9dec;cursor: pointer;" v-if="scope.row.platform == 1">微信</span>
              <span style="color: #4f9dec;cursor: pointer;" v-else-if="scope.row.platform == 2">支付宝</span>
              <span style="color: #4f9dec;cursor: pointer;" v-else-if="scope.row.platform == 3">PayPal支付</span>
              <span style="color: #4f9dec;cursor: pointer;" v-else-if="scope.row.platform == 4">Stripe支付</span>
              <span style="color: #4f9dec;cursor: pointer;" v-else-if="scope.row.platform == 5">余额</span>
              <span style="color: #4f9dec;cursor: pointer;" v-else>暂无</span>
            </template>
          </el-table-column>
          <el-table-column label="状态" width="120">
            <template #default="scope">
              <span style="color: #4f9dec;" v-if="scope.row.state == 0">待支付</span>
              <span style="color: #4f9dec;" v-if="scope.row.state == 1">支付成功</span>
              <span style="color: #4f9dec;" v-if="scope.row.state == 2">支付失败</span>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="170">
          </el-table-column>
          <el-table-column prop="payTime" label="支付时间" width="170">
          </el-table-column>
        </el-table>
        <el-row class="el-rows">
          <el-col :span="24">
            <div class="topBtn flex align-center justify-end">
              <pagination :pageSize="limit" v-model:currentPage="page" :total="total" @pageChange="rechargeSelect()" />
            </div>
          </el-col>
        </el-row>
      </el-tab-pane>
    </el-tabs>
    <!-- 弹框 -->

    <!-- 审核   -->
    <el-dialog title="审核" v-model="dialogFormVisible1" center>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">审核结果：</span>
        <el-radio-group v-model="type">
          <el-radio :label="1">通过</el-radio>
          <el-radio :label="-1">拒绝</el-radio>
        </el-radio-group>
      </div>
      <!-- <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">审核说明：</span>
        <el-input style="width:50%;" v-model="money" type="textarea" rows="4" placeholder="请输入审核说明"></el-input>
      </div> -->
      <template #footer>
        <el-button @click="dialogFormVisible1 = false">取 消</el-button>
        <el-button type="primary" @click="StairNoticeTo1()">确 定</el-button>
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
  ElMessage
} from 'element-plus';
const {
  proxy
} = getCurrentInstance();
const router = useRouter();
const activeName = ref('third'); //默认选中
const tableData = reactive([]); //提现管理
const walletData = ref({}); //提现统计 集合
const rechargeData = reactive([]); //支付记录 集合
// 支付统计
const zongMoney = ref(0);
// 支付统计合集
const withdrawDataZf = reactive([])



const page = ref(1);//当前页
const limit = ref(10);//每页条数
const total = ref(0);//总条数

//列表是否加载中
const menuLoading = ref(false);
const tableDataLoading = ref(false);
const startTime = ref('');
const endTime = ref('');
const id = ref('');
const userNameT = ref('');
const zhifubaoT = ref('');
const statusT = ref('');
const inviteVerifyT = ref('');

const statusData = reactive([
  {
    label: '全部',
    value: ''
  },
  {
    label: '待审核',
    value: 0
  },
  {
    label: '转账成功',
    value: 1
  },
  {
    label: '拒绝转账',
    value: 2
  }
])
const inviteVerifyData = reactive([
  {
    label: '全部',
    value: ''
  },
  {
    label: '支付宝',
    value: 1
  },
  {
    label: '微信',
    value: 2
  }
])
const statusDataZ = reactive([
  {
    label: '全部',
    value: ''
  },
  {
    label: '待支付',
    value: 0
  },
  {
    label: '支付成功',
    value: 1
  },
  {
    label: '支付失败',
    value: 2
  }
])

// 审核弹框的
const dialogFormVisible1 = ref(false);
const type = ref('');
const money = ref('');
// 绑定当前时间
const info = ref(null);
// 时间类型
const flag = ref('day');
// 时间类型集合
const flags = reactive([
  {
    value: 'day',
    label: '按天查询'
  }, {
    value: 'month',
    label: '按月查询'
  }, {
    value: 'year',
    label: '按年查询'
  }
]);




onMounted(() => {
  info.value = getNowTime()
  withdrawSelect();
});
// 获取当前时间
const getNowTime = () => {
  const now = new Date();
  const year = now.getFullYear(); //得到年份
  const month = now.getMonth(); //得到月份
  const date = now.getDate(); //得到日期
  // const hh = now.getHours() < 10 ? "0" + now.getHours() : now.getHours();
  // const mm = now.getMinutes() < 10 ? "0" + now.getMinutes() : now.getMinutes();
  // const ss = now.getSeconds() < 10 ? "0" + now.getSeconds() : now.getSeconds();
  console.log(month)
  const month1 = month + 1;
  const month2 = month1.toString().padStart(2, "0");
  const date1 = date.toString().padStart(2, "0");
  var defaultDate = `${year}-${month2}-${date1}`;
  // var defaultDate = `${year}-${month}-${date} ${hh}:${mm}:${ss}`;

  return defaultDate;
}
//跳转用户详情
const updates = (userId) => {
  router.push({
    name: 'userDetail1',
    query: {
      userId: userId,
    },

  });
};
//获取 提现管理
const getUserList = () => {
  tableDataLoading.value = true;
  tableData.splice(0, tableData.length)
  proxy.$Request.get('/cash/selectAdminHelpProfit', {
    params: {
      page: page.value,
      limit: limit.value,
      userName: userNameT.value,
      classify: inviteVerifyT.value,
      // state: statusT.value,
      phone: '',
      zhifubaoName: '',
      startTime: startTime.value,
      endTime: endTime.value,
      zhifubao: zhifubaoT.value,
    }
  }, {
    // type: 'json'
  }).then(res => {
    // console.log(res)
    tableDataLoading.value = false;
    if (res.code == 0) {
      tableData.splice(0, tableData.length, ...res.data.records)
      total.value = res.data.total;
    }
  })
};
/**
  * 搜索
  */
const handleSearch = () => {
  page.value = 1
  getUserList();
}
const clearSearch = () => {
  page.value = 1
  userNameT.value = ''
  inviteVerifyT.value = ''
  statusT.value = ''
  zhifubaoT.value = ''
  startTime.value = ''
  endTime.value = ''
  getUserList();
}
// 切换tab 事件
const handleClick = (tab, event) => {
  console.log(event.srcElement.innerText)
  info.value = getNowTime()
  if (event.srcElement.innerText == '提现管理') {
    getUserList();
  }
  if (event.srcElement.innerText == '提现统计') {
    walletSelect();
  }
  if (event.srcElement.innerText == '支付统计') {
    withdrawSelect();
  }
  if (event.srcElement.innerText == '支付记录') {
    rechargeSelect();
  }

}
// 提现统计 列表
const walletSelect = () => {
  menuLoading.value = true
  proxy.$Request.get('/cash/statisticsCashMoney', {
    params: {
      time: info.value,
      flag: flag.value
    }
  }, {
    // type: 'json'
  }).then((res) => {
    console.log(res)
    menuLoading.value = false;
    walletData.value = res.data

  })
}
// 类型、时间筛选
const orderfenxi = () => {
  walletSelect()
  withdrawSelect()
}
// 支付统计 列表
const withdrawSelect = () => {
  tableDataLoading.value = true

  withdrawDataZf.splice(0, withdrawDataZf.length)
  proxy.$Request.post('/admin/index/payStatistics',
    {
      // params: {
      findDate: info.value,
      findType: flag.value
      // }
    }
    , {
      type: 'json',
    }
  ).then(res => {
    if (res.code == 0) {
      withdrawDataZf.value = []
      zongMoney.value = 0

      for (const i in res.data.dataInfoList) {
        console.log('Number(withdrawDataZf[i].amount)', Number(res.data.dataInfoList[i].amount));

        if (Number(res.data.dataInfoList[i].amount) > 0) {
          zongMoney.value = zongMoney.value + Number(res.data.dataInfoList[i].amount)
        }
      }

    }
    withdrawDataZf.splice(0, withdrawDataZf.length, ...res.data.dataInfoList)
    tableDataLoading.value = false
  })
}
// 支付记录 列表
const rechargeSelect = () => {
  tableDataLoading.value = true
  rechargeData.splice(0, rechargeData.length)
  proxy.$Request.post('/admin/index/payDetailPage', {
    // params: {
    state: statusT.value,
    page: page.value,
    limit: limit.value,
    startTime: startTime.value,
    endTime: endTime.value,

    // }
  }, {
    type: 'json'
  }).then((res) => {
    console.log(res)
    tableDataLoading.value = false;
    rechargeData.splice(0, rechargeData.length, ...res.data.records)
    total.value = res.data.total;
  })
}
const clearSearchZf = () => {
  statusT.value = ''
  startTime.value = ''
  endTime.value = ''
  page.value = 1
  rechargeSelect()
}
const timeDate2 = () => {
  page.value = 1
  rechargeSelect()
}

// 审核
const addChangeU = (row) => {
  id.value = row.id
  type.value = ''
  money.value = ''
  dialogFormVisible1.value = true
}
const StairNoticeTo1 = () => {
  if (type.value == '') {
    ElMessage({
      message: '请选择审核结果',
      type: 'warning',
      onClose: () => {
      }
    })

    return
  }
  // if (type.value == -1) {
  //   if (money.value == '') {
  //     ElMessage({
  //       message: '拒绝原因不能为空',
  //       type: 'warning',
  //       onClose: () => {
  //       }
  //     })
  //     return
  //   }
  // }

  proxy.$Request.post('/cash/alipay/' + id.value, {
    // id: id.value,
    // refund: money.value,
    // state: type.value
  }, {
    type: 'json'
  }).then((res) => {
    console.log(res)
    if (res.code == 0) {
      ElMessage({
        message: '操作成功',
        type: 'success',
        onClose: () => {
          getUserList()
          dialogFormVisible1.value = false
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

.dashboard {
  .welcome-card {
    .content {
      p {
        font-size: 16px;
        color: #666;
      }
    }
  }

  .stat-col {
    margin-bottom: 20px;
  }

  .stat-card {
    text-align: center;

    .stat-content {
      padding: 30px 0;

      .stat-number {
        font-size: 24px;
        font-weight: bold;
        color: #409eff;
      }

      .stat-label {
        margin-top: 10px;
        color: #666;
      }
    }
  }
}

span {
  font-size: 14px;
  color: #303133;
}
</style>
