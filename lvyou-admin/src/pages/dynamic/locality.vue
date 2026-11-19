<template>
  <div class="menu">
    <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
      <el-tab-pane label="群列表" name="first">
        <div>
          <div style="display: inline-block;margin: 5px;">
            <span>审核状态 ：</span>
            <el-select v-model="auditStatusT" class="m-2" placeholder="请选择状态" style="width: 150px;"
              @change="handleSearch">
              <el-option v-for="(item, index) in auditStatusData" :key="index" :label="item.label"
                :value="item.value" />
            </el-select>
          </div>&nbsp;&nbsp;
          <div style="display: inline-block;margin: 5px;">
            <span>群主昵称 ：</span>
            <el-input v-model="userNameT" placeholder="请输入群主昵称" style="width: 150px;" />
          </div>&nbsp;&nbsp;
          <div style="display: inline-block;margin: 5px;">
            <span>群名称 ：</span>
            <el-input v-model="nameT" placeholder="请输入群名称" style="width: 150px;" />
          </div>&nbsp;&nbsp;
          <div style="display: inline-block;margin: 5px;">
            <span>状态 ：</span>
            <el-select v-model="statusT" class="m-2" placeholder="请选择状态" style="width: 150px;" @change="handleSearch">
              <el-option v-for="(item, index) in statusData" :key="index" :label="item.label" :value="item.value" />
            </el-select>
          </div>&nbsp;&nbsp;
          <div style="display: inline-block;margin: 5px;">
            <span>群类型名称 ：</span>
            <el-input v-model="inviteVerifyT" placeholder="请输入群类型名称" style="width: 150px;" />
          </div>&nbsp;&nbsp;
          <el-button size="small" type="primary" plain @click="handleSearch()">查询</el-button>
          <el-button size="small" type="warning" plain @click="clearSearch()">重置</el-button>
          <!-- <el-button size="small" type="primary" @click="addChangeU()">添加</el-button> -->
        </div>
        <el-table v-loading="tableDataLoading" :data="qunzuData">
          <el-table-column prop="id" label="编号" width="80" fixed="left"></el-table-column>
          <el-table-column prop="userName" label="群主昵称" fixed="left">
            <template #default="scope">
              <span v-if="scope.row.userId" style="color: #13ce66;cursor: pointer;" @click="updates(scope.row.userId)">
                {{ scope.row.userName }}
              </span>
              <span v-else>-</span>
            </template>
          </el-table-column>
          <el-table-column prop="avatar" label="群主头像" width="100">
            <template #default="scope">
              <img v-if="scope.row.avatar" :src="scope.row.avatar" style="width: 60px;height: 60px;" />
              <span v-else> - </span>
            </template>
          </el-table-column>
          <el-table-column prop="typeName" label="群类型名称" fixed="left"></el-table-column>
          <el-table-column prop="name" label="群名称" fixed="left"></el-table-column>
          <el-table-column prop="qrCode" label="群二维码" width="100">
            <template #default="scope">
              <el-button size="small" type="primary" @click="erweimaChange(scope.row)"
                style="margin: 5px;">查看</el-button>
            </template>
          </el-table-column>
          <!-- <el-table-column prop="coverImg" label="群封面图" width="100">
            <template #default="scope">
              <img v-if="scope.row.coverImg" :src="scope.row.coverImg" style="width: 60px;height: 60px;" />
              <span v-else> - </span>
            </template>
          </el-table-column> -->
          <el-table-column prop="charge" label="群费用金额"></el-table-column>
          <el-table-column prop="peopleCount" label="群人数"></el-table-column>
          <el-table-column prop="expiredDays" label="加入群聊有效天数"></el-table-column>
          <!-- <el-table-column prop="remark" label="群备注/详情" width="200"></el-table-column> -->
          <el-table-column prop="province" label="群地址" width="200">
            <template #default="scope">
              {{ scope.row.province }}{{ scope.row.city }}{{ scope.row.region }}{{ scope.row.address }}

            </template>
          </el-table-column>
          <el-table-column prop="description" label="群简介" width="200"></el-table-column>
          <el-table-column prop="remark" label="群备注/详情" width="200">
            <template #default="scope">
              <div v-if="scope.row.remark">
                <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;"
                  v-for="(item, index) in scope.row.remark.split(',')" :key="index">
                  <template #reference>
                    <img style="width: 50px; height: 50px;margin: 3px;" :src="item" alt="">
                  </template>
                  <template #default>
                    <img style="width: 400px; height: auto;" :src="item" alt="">
                  </template>
                </el-popover>
              </div>

            </template>
          </el-table-column>
          <!-- <el-table-column prop="inviteVerify" label="是否开启认证" width="100">
            <template #default="scope">
              <span v-if="scope.row.inviteVerify == 1">是</span>
              <span v-else>否</span>
            </template>
          </el-table-column> -->
          <el-table-column prop="qrStartTime" label="开始时间" width="180"></el-table-column>
          <el-table-column prop="expiredTime" label="结束时间" width="180"></el-table-column>
          <el-table-column prop="auditRemark" label="审核备注" width="200"></el-table-column>
          <el-table-column prop="auditStatus" label="审核状态" width="100">
            <template #default="scope">
              <span v-if="scope.row.auditStatus == 0">待审核</span>
              <span v-if="scope.row.auditStatus == 1">审核通过</span>
              <span v-if="scope.row.auditStatus == 2">审核拒绝</span>
            </template>
          </el-table-column>
          <el-table-column prop="status" label="状态" width="100" fixed="right">
            <template #default="scope">
              <span v-if="scope.row.status === 0">正常</span>
              <span v-if="scope.row.status == 1">禁用</span>
              <span v-if="scope.row.status == 2">过期</span>
              <span v-if="scope.row.status == 3">人数已达最大</span>
              <el-switch v-if="scope.row.status === 0 || scope.row.status === 1" v-model="scope.row.status" class="ml-2"
                :active-value="0" :inactive-value="1" @change="changeBtn(scope.row)"
                style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949" />
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
          <el-table-column label="操作" width="100" fixed="right">
            <template #default="scope">
              <el-button size="small" type="warning" @click="handleShenhe(scope.row)" v-if="scope.row.auditStatus == 0"
                style="margin: 5px;">审核</el-button>
              <el-button size="small" type="primary" @click="cyChange(scope.row)" style="margin: 5px;">群组成员</el-button>
              <!-- <el-button size="small" type="primary" @click="sqChange(scope.row)" style="margin: 5px;">入群申请</el-button> -->
              <!-- <el-button size="small" type="primary" @click="ltChange(scope.row)" style="margin: 5px;">聊天记录</el-button> -->
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
      <el-tab-pane label="群分类" name="second">
        <div style="margin: 0 20px;text-align: right;">
          <el-button size="small" type="primary" plain @click="flSearch()">刷新</el-button>
          <el-button size="small" type="warning" plain @click="addChangeFl()">添加</el-button>
        </div>
        <el-table v-loading="tableDataLoading" :data="fenleiData">

          <el-table-column fixed prop="id" label="编号" width="100"></el-table-column>
          <el-table-column prop="name" label="名称"></el-table-column>
          <el-table-column prop="remark" label="备注"></el-table-column>
          <el-table-column prop="maxPeopleNum" label="最大人数"></el-table-column>
          <el-table-column prop="maxAmount" label="最大金额"></el-table-column>
          <el-table-column prop="minAmount" label="最小金额"></el-table-column>
          <el-table-column prop="status" label="是否启用">
            <template #default="scope">
              <span v-if="scope.row.status == 1">显示</span>
              <span v-if="scope.row.status == 0" style="color: #f56c6c;">隐藏</span>
              <el-switch v-model="scope.row.status" @click="changeState(scope.row)"
                :disabled="!isAuth('bannerList:update')" :active-value="1" :inactive-value="0" active-color="#13ce66"
                inactive-color="#ff4949">
              </el-switch>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="160"></el-table-column>
          <el-table-column label="操作" width="100" fixed="right">
            <template #default="scope">
              <el-button size="small" type="primary" @click="addChangeFl(scope.row)" style="margin: 5px;">修改</el-button>
              <el-button size="small" type="danger" @click="flDelete(scope.row)" style="margin: 5px;">删除</el-button>
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

    <!-- 聊天内容 -->
    <el-dialog title="聊天内容" v-model="dialogFormVisible4" center width="70%">
      <el-table v-loading="tableDataLoading1" :data="huihuaData">
        <el-table-column prop="id" label="编号" width="100">
          <template #default="scope">
            {{ scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column prop="senderUserName" label="用户昵称">
          <template #default="scope">
            <span v-if="scope.row.senderUserName" style="color: #13ce66;cursor: pointer;"
              @click="updates(scope.row.senderUserId)">
              {{ scope.row.senderUserName ? scope.row.senderUserName : '未绑定' }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="senderAvatar" label="用户图头">
          <template #default="scope">
            <img v-if="scope.row.senderAvatar" :src="scope.row.senderAvatar" style="width: 60px;height: 60px;" />
          </template>
        </el-table-column>
        <el-table-column prop="type" label="内容类型">
          <template #default="scope">
            <span v-if="scope.row.type == 1">普通消息</span>
            <span v-if="scope.row.type == 2">表情</span>
            <span v-if="scope.row.type == 3">图片</span>
            <span v-if="scope.row.type == 4">视频</span>
          </template>
        </el-table-column>
        <el-table-column prop="messageCount" label="聊天内容">
          <template #default="scope">
            <span v-if="scope.row.type == 1">{{ scope.row.messageCount }}</span>
            <span v-if="scope.row.type == 2">
              <img style="width: 20px; height: 20px;margin: 3px;"
                :src="$emojiBase + '/' + scope.row.contemessageCountnt" alt="">
            </span>
            <span v-if="scope.row.type == 3">
              <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">
                <template #reference>
                  <img style="width: 50px; height: 50px;margin: 3px;" :src="scope.row.content" alt="">
                </template>
                <template #default>
                  <img style="width: 400px; height: auto;" :src="scope.row.content" alt="">
                </template>
              </el-popover>
            </span>
            <span v-if="scope.row.type == 4">
              <video style="width: 180px;height: 100px;" :src="scope.row.content" type="audio/wav"
                controls="controls"></video>
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="lastMessageTime" label="创建时间" width="180"></el-table-column>
      </el-table>
      <el-row class="el-rows">
        <el-col :span="24">
          <div class="topBtn flex align-center justify-end">
            <pagination :pageSize="limit1" v-model:currentPage="page1" :total="total1" @pageChange="neirongSelect()" />
          </div>
        </el-col>
      </el-row>
    </el-dialog>
    <!-- 评论列表 -->
    <el-dialog v-model="dialogFormVisiblePl" title="评论列表" width="80%" center>
      <el-table :data="tableDataPl" v-loading="loading" style="width: 100%">
        <el-table-column prop="userId" label="编号" width="100"></el-table-column>
        <el-table-column prop="userName" label="评论用户" width="160">
          <template #default="scope">
            <span v-if="scope.row.userName" style="color: #13ce66;cursor: pointer;" @click="updates(scope.row.userId)">
              {{ scope.row.userName ? scope.row.userName : '未绑定' }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="avatar" label="用户图头" width="100">
          <template #default="scope">
            <img :src="scope.row.avatar" style="width: 60px;height: 60px;" />
          </template>
        </el-table-column>
        <el-table-column prop="content" label="评论内容"></el-table-column>
        <el-table-column prop="createTime" label="评论时间" width="180"></el-table-column>
      </el-table>
    </el-dialog>
    <!--群成员 弹框 -->
    <el-dialog v-model="dialogFormVisibleCy" title="群成员列表" width="80%" center>
      <div style="margin: 10px;">
        <div style="display: inline-block;margin: 5px;">
          <span>用户昵称：</span>
          <el-input v-model="userNameC" placeholder="请输入用户昵称" style="width: 150px;" />
        </div>
        <!-- @keydown.enter.native="handleSearchCy" -->
        <!-- <div style="display: inline-block;margin: 5px;">
          <span>手机号：</span>
          <el-input v-model="phoneC" placeholder="请输入手机号" style="width: 150px;" />
        </div> -->
        <el-button size="small" type="primary" plain @click="handleSearchCy()">查询</el-button>
        <el-button size="small" type="primary" plain @click="clearSearchCy()">重置</el-button>
      </div>
      <el-table :data="tableDataCy" v-loading="loadingCy" style="width: 100%">
        <el-table-column prop="userId" label="编号" width="100"></el-table-column>
        <el-table-column prop="userName" label="用户昵称">
          <template #default="scope">
            <span v-if="scope.row.userName" style="color: #13ce66;cursor: pointer;" @click="updates(scope.row.userId)">
              {{ scope.row.userName ? scope.row.userName : '未绑定' }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="avatar" label="用户图头">
          <template #default="scope">
            <img :src="scope.row.avatar" style="width: 60px;height: 60px;" />
          </template>
        </el-table-column>
        <el-table-column prop="phone" label="手机号"></el-table-column>
        <el-table-column prop="createTime" label="入群时间" width="180"></el-table-column>
      </el-table>
      <el-row class="el-rows">
        <el-col :span="24">
          <div class="topBtn flex align-center justify-end">
            <pagination :pageSize="limit1" v-model:currentPage="page1" :total="total1" @pageChange="dataSelectCy()" />
          </div>
        </el-col>
      </el-row>

    </el-dialog>
    <!--申请信息 弹框 -->
    <el-dialog v-model="dialogFormVisibleSq" title="申请信息" width="80%" center>
      <div style="margin: 10px;">
        <div style="display: inline-block;margin: 5px;">
          <span>用户昵称：</span>
          <el-input v-model="userNameC" placeholder="请输入用户昵称" style="width: 150px;" />
        </div>
        <!-- @keydown.enter.native="handleSearchSq" -->
        <div style="display: inline-block;margin: 5px;">
          <span>手机号：</span>
          <el-input v-model="phoneC" placeholder="请输入手机号" style="width: 150px;" />
        </div>
        <el-button size="small" type="primary" plain @click="handleSearchSq()">查询</el-button>
        <el-button size="small" type="primary" plain @click="clearSearchSq()">查询</el-button>
      </div>
      <el-table :data="tableDataSp" v-loading="loadingCy" style="width: 100%">
        <el-table-column prop="userId" label="编号" width="100"></el-table-column>
        <el-table-column prop="userName" label="用户昵称">
          <template #default="scope">
            <span v-if="scope.row.userName" style="color: #13ce66;cursor: pointer;" @click="updates(scope.row.userId)">
              {{ scope.row.userName ? scope.row.userName : '未绑定' }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="avatar" label="用户图头">
          <template #default="scope">
            <img :src="scope.row.avatar" style="width: 60px;height: 60px;" />
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="申请时间" width="180"></el-table-column>
      </el-table>
    </el-dialog>
    <!--聊天记录 弹框 -->
    <el-dialog v-model="dialogFormVisibleLt" title="聊天记录" width="80%" center>
      <div style="margin: 10px;">
        <div style="display: inline-block;margin: 5px;">
          <span>用户昵称：</span>
          <!-- @keydown.enter.native="handleSearchLt" -->
          <el-input v-model="userNameC" placeholder="请输入用户昵称" style="width: 150px;" />
        </div>
        <div style="display: inline-block;margin: 5px;">
          <span>手机号：</span>
          <el-input v-model="phoneC" placeholder="请输入手机号" style="width: 150px;" />
        </div>
        <div style="display: inline-block;margin: 5px;">
          <span>内容类型：</span>
          <el-select v-model="typeC" class="m-2" placeholder="请选择内容类型" style="width: 150px;" @change="handleSearchLt">
            <el-option v-for="(item, index) in typeData" :key="index" :label="item.label" :value="item.value" />
          </el-select>
        </div>
        <div style="display: inline-block;margin: 5px;">
          <span>聊天内容：</span>
          <el-input v-model="contentC" placeholder="请输入聊天内容" style="width: 150px;" />
        </div>

        <el-button size="small" type="primary" plain @click="handleSearchLt()">查询</el-button>
        <el-button size="small" type="primary" plain @click="clearSearchLt()">查询</el-button>
      </div>
      <el-table :data="tableDataLt" v-loading="loadingCy" style="width: 100%">
        <el-table-column prop="userId" label="编号" width="100"></el-table-column>
        <el-table-column prop="userName" label="用户昵称">
          <template #default="scope">
            <span v-if="scope.row.userName" style="color: #13ce66;cursor: pointer;" @click="updates(scope.row.userId)">
              {{ scope.row.userName ? scope.row.userName : '未绑定' }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="avatar" label="用户图头">
          <template #default="scope">
            <img :src="scope.row.avatar" style="width: 60px;height: 60px;" />
          </template>
        </el-table-column>
        <el-table-column prop="type" label="内容类型">
          <template #default="scope">
            <span v-if="scope.row.type == 1">普通消息</span>
            <span v-if="scope.row.type == 2">表情</span>
            <span v-if="scope.row.type == 3">图片</span>
            <span v-if="scope.row.type == 4">视频</span>
          </template>
        </el-table-column>
        <el-table-column prop="content" label="聊天内容">
          <template #default="scope">
            <span v-if="scope.row.type == 1">{{ scope.row.content }}</span>
            <span v-if="scope.row.type == 2">
              <img style="width: 20px; height: 20px;margin: 3px;"
                :src="$emojiBase + '/' + scope.row.content" alt="">
              <!-- <el-popover placement="top-start" title="" trigger="hover"  popper-style="width:420px;">
                                    <template #reference>
                                        <img style="width: 50px; height: 50px;margin: 3px;" :src="scope.row.content" alt="">
                                    </template>
      <template #default style="width: 420px;">
                                        <img style="width: 400px; height: auto;" :src="scope.row.content" alt="">
                                    </template>
      </el-popover> -->
            </span>
            <span v-if="scope.row.type == 3">
              <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">
                <template #reference>
                  <img style="width: 50px; height: 50px;margin: 3px;" :src="scope.row.content" alt="">
                </template>
                <template #default>
                  <img style="width: 400px; height: auto;" :src="scope.row.content" alt="">
                </template>
              </el-popover>
            </span>
            <span v-if="scope.row.type == 4">
              <video style="width: 180px;height: 100px;" :src="scope.row.content" type="audio/wav"
                controls="controls"></video>
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
      </el-table>
    </el-dialog>
    <!-- 二维码审核 弹框 -->
    <el-dialog v-model="loadingerweima" title="群二维码" width="600px" center>
      <div style="text-align: center;">
        <img :src="qrCodeData" alt="QR Code" style="width: 400px;height: auto;">
      </div>

    </el-dialog>
    <!-- 添加/修改 分类 弹框 -->
    <el-dialog v-model="dialogFormVisibleU" :title="titles" width="80%" center>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">名称：</span>
        <el-input style="width:50%;" v-model="name" type="text" placeholder="请输入名称"></el-input>
      </div>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">最大人数：</span>
        <el-input style="width:50%;" v-model="maxPeopleNum" type="text" placeholder="请输入最大人数"></el-input>
      </div>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">最大金额：</span>
        <el-input style="width:50%;" v-model="maxAmount" type="text" placeholder="请输入最大金额"></el-input>
      </div>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">最小金额：</span>
        <el-input style="width:50%;" v-model="minAmount" type="text" placeholder="请输入最小金额"></el-input>
      </div>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">备注：</span>
        <el-input style="width:50%;" v-model="remark" type="text" placeholder="请输入备注"></el-input>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogFormVisibleU = false" style="margin: 10px;">取消</el-button>
          <el-button type="primary" @click="StairNoticeToU" style="margin: 10px;">确认</el-button>
        </span>
      </template>
    </el-dialog>
    <!-- 审核 -->
    <el-dialog v-model="loadingshenhe" title="审核" width="80%" center>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">审核结果：</span>
        <el-radio-group v-model="auditStatus">
          <el-radio :label="1">通过</el-radio>
          <el-radio :label="2">拒绝</el-radio>
        </el-radio-group>
      </div>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">审核说明：</span>
        <el-input style="width:50%;" v-model="auditReason" type="textarea" rows="4" placeholder="请输入审核说明"></el-input>
      </div>
      <template #footer>
        <el-button @click="loadingshenhe = false">取 消</el-button>
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
import { useRouter, useRoute } from 'vue-router';
import pagination from '@/components/pagination/pagination.vue'
import qrcode from 'qrcode';
import {
  ElMessageBox,
  ElMessage
} from 'element-plus';
const {
  proxy
} = getCurrentInstance();
const router = useRouter();
const activeName = ref('first'); //默认选中
const qunzuData = reactive([]); //我的群组 集合
const tousuData = reactive([]); //好友请求 集合
const huihuaData = reactive([]); //聊天内容 集合
const tableDataPl = reactive([]); //评论 集合
const tableDataCy = reactive([]); //群成员 集合
const tableDataLt = reactive([]); //群聊天 集合
const fenleiData = reactive([]);


const page = ref(1);//当前页
const limit = ref(10);//每页条数
const total = ref(0);//总条数
const page1 = ref(1);//当前页
const limit1 = ref(10);//每页条数
const total1 = ref(0);//总条数

//列表是否加载中
const dialogFormVisible4 = ref(false);
const dialogFormVisiblePl = ref(false);
const dialogFormVisibleCy = ref(false);
const dialogFormVisibleLt = ref(false);
const tableDataLoading = ref(false);
const tableDataLoading1 = ref(false);
const loadingCy = ref(false);
const receiverUserId = ref('');
const senderUserId = ref('');
const id = ref('');
const userNameC = ref('');
const phoneC = ref('');
const typeC = ref('');
const contentC = ref('');
const targetId = ref('');
const qrCodeData = ref('');
const loadingerweima = ref(false);
const userNameT = ref('');
const inviteVerifyT = ref('');
const statusT = ref('');
const auditStatusT = ref('');
const nameT = ref('');
const routeUrl = ref('');
const auditStatus = ref('');
const auditReason = ref('');
const loadingshenhe = ref(false);
//是否开启认证： 集合
const auditStatusData = reactive([
  {
    label: '全部',
    value: ''
  },

  {
    label: '待审核',
    value: 0
  },
  {
    label: '审核通过',
    value: 1
  }, {
    label: '审核拒绝',
    value: 2
  },
]);
// 状态类型
const statusData = reactive([ //0正常 1禁用 2过期 3人数已达最大
  {
    label: '全部',
    value: ''
  },
  {
    label: '正常',
    value: 0
  },
  {
    label: '禁用',
    value: 1
  },
  {
    label: '过期',
    value: 2
  },
  {
    label: '人数已达最大',
    value: 3
  }
]);
const dialogFormVisibleU = ref(false);
const titles = ref('');
const name = ref(''); //名称
const remark = ref(''); //备注
const maxPeopleNum = ref(''); //最大人数
const maxAmount = ref(''); //最大金额
const minAmount = ref(''); //最小金额


onMounted(() => {
  qunzuSelect();
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
  console.log(event.srcElement.innerText)

  if (event.srcElement.innerText == '群列表') {
    qunzuSelect();
  }
  if (event.srcElement.innerText == '群分类') {
    qunzufenleiSelect();
  }


}

// 群列表 列表
const qunzuSelect = () => {
  tableDataLoading.value = true
  qunzuData.splice(0, qunzuData.length)
  proxy.$Request.post('/admin/groupInfo/page', {
    page: page.value,
    limit: limit.value,
    userName: userNameT.value,
    typeName: inviteVerifyT.value,
    status: statusT.value,
    name: nameT.value,
    auditStatus: auditStatusT.value,
    province: '',
    city: '',
    region: '',
  }, {
    type: 'json'
  }).then((res) => {
    console.log(res)
    tableDataLoading.value = false;
    qunzuData.splice(0, qunzuData.length, ...res.data.records)
    total.value = res.data.total;
  })
}
// 刷新
const handleSearch = () => {
  page.value = 1
  qunzuSelect()
}
// 重置
const clearSearch = () => {
  page.value = 1
  userNameT.value = ''
  inviteVerifyT.value = ''
  statusT.value = ''
  auditStatusT.value = ''
  nameT.value = ''
  qunzuSelect()
}
// 修改状态
const changeBtn = (row) => {
  proxy.$Request.post('/admin/groupInfo/updateStatus', {

    id: row.id,
    status: row.status,
  }, {
    type: 'json'
  }).then((res) => {
    console.log(res)
    if (res.code == 0) {
      ElMessage({
        message: '修改成功',
        type: 'success',
        onClose: () => {
          qunzuSelect()
        }
      })
    } else {
      ElMessage({
        message: res.msg,
        type: 'warning',
        onClose: () => {
          qunzuSelect()
        }
      })
    }
  })
}
/** 群成员 弹框 */
const cyChange = (row) => {
  id.value = row.id
  userNameC.value = ''
  phoneC.value = ''
  dialogFormVisibleCy.value = true
  dataSelectCy();

}
/** 群成员 数据 */
const dataSelectCy = () => {
  loadingCy.value = true
  tableDataCy.splice(0, tableDataCy.length)
  proxy.$Request.post('/admin/groupUser/page', {
    // params: {
    groupInfoId: id.value,
    userName: userNameC.value,
    // phone: phoneC.value,
    page: page1.value,
    limit: limit1.value,

    // }
  }, {
    type: 'json'
  }).then((res) => {
    console.log(res)
    loadingCy.value = false;
    tableDataCy.splice(0, tableDataCy.length, ...res.data.records)
    total1.value = res.data.total;
  })
}
const handleSearchCy = () => {
  dataSelectCy();
}
const clearSearchCy = () => {
  userNameC.value = ''
  phoneC.value = ''
  dataSelectCy();
}
/** 聊天记录 弹框 */
const ltChange = (row) => {
  id.value = row.id
  page1.value = 1
  userNameC.value = ''
  phoneC.value = ''
  typeC.value = ''
  contentC.value = ''
  dataSelectLt()
  dialogFormVisibleLt.value = true
}
/** 聊天记录 数据 */
const dataSelectLt = () => {
  loadingCy.value = true
  tableDataLt.splice(0, tableDataLt.length)
  proxy.$Request.get('/admin/history/page', {
    params: {
      page: page1.value,
      limit: limit1.value,
      groupId: id.value,
      userName: userNameC.value,
      phone: phoneC.value,
      targetType: 2,
      type: typeC.value,
      content: contentC.value,

    }
  }, {}).then((res) => {
    console.log(res)
    loadingCy.value = false;
    tableDataLt.splice(0, tableDataLt.length, ...res.data)
    total1.value = res.data.total;
  })




}
const erweimaChange = (row) => {
  targetId.value = row.id
  var qrCodeDatas = targetId.value + '-getQrCode';
  qrcode.toDataURL(qrCodeDatas, (err, url) => {
    if (err) {
      console.error(err);
    } else {
      qrCodeData.value = url;
    }
  });
  loadingerweima.value = true;
}


/**  获取聊天内容列表*/
const neirongSelect = () => {
  tableDataLoading1.value = true
  huihuaData.splice(0, tousuData.length)
  proxy.$Request.post('/admin/history/chatHistory', {
    receiverUserId: receiverUserId.value,
    senderUserId: senderUserId.value,
    page: page.value,
    limit: limit.value,
  }, {
    type: 'json'
  }).then((res) => {
    console.log(res)
    tableDataLoading1.value = false;
    huihuaData.splice(0, huihuaData.length, ...res.data.records)
    total1.value = res.data.total;
  })
}
// 群分类 列表
const qunzufenleiSelect = () => {
  tableDataLoading.value = true
  fenleiData.splice(0, fenleiData.length)
  proxy.$Request.post('/admin/groupType/page', {
    page: page.value,
    limit: limit.value,
    name: nameT.value,
  }, {
    type: 'json'
  }).then((res) => {
    console.log(res)
    tableDataLoading.value = false;
    fenleiData.splice(0, fenleiData.length, ...res.data.records)
    total.value = res.data.total;
  })
}
// 刷新 分类
const flSearch = () => {
  page.value = 1
  qunzufenleiSelect()
}
// 添加/修改 分类 弹框
const addChangeFl = (row) => {
  if (row) {
    titles.value = '修改'
    id.value = row.id
    name.value = row.name
    remark.value = row.remark
    maxPeopleNum.value = row.maxPeopleNum
    maxAmount.value = row.maxAmount
    minAmount.value = row.minAmount
  } else {
    titles.value = '添加'
    id.value = ''
    name.value = ''
    remark.value = ''
    maxPeopleNum.value = ''
    maxAmount.value = ''
    minAmount.value = ''
  }
  dialogFormVisibleU.value = true
}
// 确认
const StairNoticeToU = () => {
  if (titles.value == '添加') {
    routeUrl.value = '/admin/groupType/save'
  } else {
    routeUrl.value = '/admin/groupType/update'
  }
  proxy.$Request.post(routeUrl.value, {
    id: id.value,
    name: name.value,
    remark: remark.value,
    maxPeopleNum: maxPeopleNum.value,
    maxAmount: maxAmount.value,
    minAmount: minAmount.value,

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
          qunzufenleiSelect()
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
// 删除 分类
const flDelete = (row) => {
  proxy.$Request.post('/admin/groupType/delete', {
    id: row.id,
  }, {
    type: 'json'
  }).then((res) => {
    console.log(res)
    if (res.code == 0) {
      ElMessage({
        message: '操作成功',
        type: 'success',
        onClose: () => {
          qunzufenleiSelect()
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
/** 修改状态*/
const changeState = (data) => {
  proxy.$Request.post('/admin/groupType/updateStatus', {
    id: data.id,
    status: data.status
  }, {
    type: 'json'
  }).then((res) => {
    console.log(res)
    if (res.code == 0) {
      ElMessage({
        message: '操作成功',
        type: 'success',
        onClose: () => {
          qunzufenleiSelect()
        }
      })
    } else {
      ElMessage({
        message: res.msg,
        type: 'warning',
        onClose: () => {
          qunzufenleiSelect()
        }
      })
    }
  })
}
// 审核弹框
const handleShenhe = (row) => {
  id.value = row.id
  auditStatus.value = ''
  auditReason.value = ''
  loadingshenhe.value = true;
}
const StairNoticeTo1 = () => {
  if (auditStatus.value == '') {
    ElMessage({
      message: '请选择审核结果',
      type: 'warning',
      onClose: () => {
      }
    })
    return
  }
  if (auditStatus.value == 2) {
    if (auditReason.value == '') {
      ElMessage({
        message: '审核原因不能为空',
        type: 'warning',
        onClose: () => {
        }
      })
      return
    }
  }

  proxy.$Request.post('/admin/groupInfo/examine', {
    id: id.value,
    auditStatus: auditStatus.value,
    auditReason: auditReason.value
  }, {
    type: 'json'
  }).then((res) => {
    console.log(res)
    if (res.code == 0) {
      ElMessage({
        message: '操作成功',
        type: 'success',
        onClose: () => {
          qunzufenleiSelect()
          loadingshenhe.value = false
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

span {
  font-size: 14px;
  color: #303133;
}
</style>
