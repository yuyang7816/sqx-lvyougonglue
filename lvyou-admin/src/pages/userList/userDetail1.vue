<template>
  <div class="menu">
    <div style="display: inline-block;font-size:18px;margin: 10px;">
      <a href="#" @click="prev" style="text-decoration:none;font-size: 14px;">
        返回
      </a>
      <span style="display: inline-block;margin: 0 15px;color: #D9D9D9;">|</span>
      <span>用户详情</span>
    </div>
    <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
      <el-tab-pane label="用户详情" name="first">
        <div class="detailtabel">
          <div class="table_main">
            <table>
              <tbody>
                <tr>
                  <th>邀请人邀请码</th>
                  <td>
                    {{ tableData.inviterCode }}
                    <el-button size="small" style="margin-left: 10px;" @click="ageChangeU()">修改</el-button>
                  </td>
                  <th>当前收益</th>
                  <td style="color: rgb(245, 108, 108)">
                    {{ tableData.money ? `${tableData.money}元` : '0元' }}
                    <el-button size="small" style="margin-left: 10px;" @click="rechargenone()">修改金额</el-button>
                  </td>
                  <th>总收入</th>
                  <td class="border-rt">
                    {{ tableData.balance ? tableData.balance : '0元' }}</td>
                </tr>
                <tr>
                  <th>邀请码</th>
                  <td>
                    {{ tableData.invitationCode }}
                    <el-button size="small" style="margin-left: 10px;" @click="ageChangeU()">修改</el-button>
                  </td>

                  <th>收款二维码</th>
                  <td>
                    <div style="display:flex;flex-wrap: wrap;">
                      <div style="margin: 2px;" v-if="tableData.wxImg">
                        <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">
                          <template #reference>
                            <img style="width: 50px; height: 50px;margin: 3px;" :src="tableData.wxImg" alt="">
                          </template>
                          <template #default>
                            <img style="width: 400px; height: auto;" :src="tableData.wxImg" alt="">
                          </template>
                        </el-popover>

                      </div>
                    </div>
                  </td>
                  <th>图像</th>
                  <td class="border-rt">
                    <img v-if="tableData.avatar" :src="tableData.avatar" width="80" height="80" />
                    <img v-else src="../../assets/logo.png" alt="" width="80" height="80">

                    <el-button size="small" style="margin-left: 10px;" @click="ageChangeU()">修改</el-button>
                  </td>
                </tr>
                <tr>
                  <th>微信名称</th>
                  <td>
                    {{ tableData.userName }}
                    <el-button size="small" style="margin-left: 10px;" @click="ageChangeU()">修改</el-button>
                  </td>
                  <th>手机号</th>
                  <td>
                    <span v-if="tableData.phone">
                      {{ tableData.phone ? tableData.phone : '未绑定' }}
                    </span>

                    <el-button size="small" style="margin-left: 10px;" @click="ageChangeU()">修改</el-button>
                  </td>
                  <th>用户状态</th>
                  <td class=" border-rt">
                    <span v-if="tableData.status == 1">正常</span>
                    <span v-if="tableData.status == 2" style="color: #f56c6c;">禁用</span>
                    <el-button size="small" style="margin-left: 10px;" @click="stateChange(tableData)">更改状态</el-button>
                  </td>

                </tr>
                <tr>

                  <!-- <th>支付宝账号</th>
                  <td>
                    <span v-if="tableData.zhiFuBao">
                      {{ tableData.zhiFuBao ? tableData.zhiFuBao : '未绑定' }}
                    </span>
                  </td> -->
                  <!-- <th>支付宝姓名</th>
                  <td class="border-rt">
                    {{ tableData ? tableData.zhiFuBaoName : '未绑定' }}
                  </td> -->
                </tr>
                <tr>
                  <th>是否实名</th>
                  <td>
                    <span v-if="tableData.isAuthentication == 1">待审核</span>
                    <span v-else-if="tableData.isAuthentication == 2">已实名</span>
                    <span v-else-if="tableData.isAuthentication == 3">审核未通过</span>
                    <span v-else>未实名</span>
                  </td>
                  <th>性别</th>
                  <td>
                    <span v-if="tableData.sex == 0">未设置</span>
                    <span v-if="tableData.sex == 1">男</span>
                    <span v-if="tableData.sex == 2">女</span>
                    <el-button size="small" style="margin-left: 10px;" @click="ageChangeU()">修改</el-button>
                  </td>
                  <th>年龄</th>
                  <td class="border-rt">
                    <span v-if="tableData.age">{{ tableData.age }}</span>
                    <span v-else>未设置</span>
                    <el-button size="small" style="margin-left: 10px;" @click="ageChangeU()">修改</el-button>
                  </td>
                </tr>
                <tr>
                  <!-- <th>个性签名</th>
                  <td>
                    <span>{{ tableData.signature ? tableData.signature : '暂无' }}</span>
                    <el-button size="small" style="margin-left: 10px;" @click="ageChangeU()">修改</el-button>
                  </td> -->
                  <th>创建时间</th>
                  <td> {{ tableData.createTime }}</td>
                  <th>是否是会员</th>
                  <td>
                    <span v-if="tableData.isVip == 1">是</span>
                    <span v-else>否</span>
                  </td>
                  <th>会员到期时间</th>
                  <td class="border-rt">
                    {{ tableData.vipEndTime }}
                  </td>
                </tr>
                <tr>
                  <th class="border-bt">个人简介</th>
                  <td class="border-bt">
                    {{ tableData.remark }}
                  </td>
                  <th class="border-bt">用户背景图</th>
                  <td class="border-bt">
                    <div v-if="tableData.userBackgroundImage">
                      <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;"
                        v-for="(item, index) in tableData.userBackgroundImage.split(',')" :key="index">
                        <template #reference>
                          <img style="width: 50px; height: 50px;margin: 3px;" :src="item" alt="">
                        </template>
                        <template #default>
                          <img style="width: 400px; height: auto;" :src="item" alt="">
                        </template>
                      </el-popover>
                    </div>

                  </td>
                  <th class="border-bt">个人相册</th>
                  <td class="border-bt border-rt">
                    <div v-if="tableData.pic">
                      <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;"
                        v-for="(item, index) in tableData.pic.split(',')" :key="index">
                        <template #reference>
                          <img style="width: 50px; height: 50px;margin: 3px;" :src="item" alt="">
                        </template>
                        <template #default>
                          <img style="width: 400px; height: auto;" :src="item" alt="">
                        </template>
                      </el-popover>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </el-tab-pane>
      <!-- <el-tab-pane label="钱包明细" name="second">
        <el-table :data="walletData" v-loading="tableDataLoading" style="width: 100%">
          <el-table-column prop="id" label="编号" width="100"></el-table-column>
          <el-table-column prop="title" label="标题"></el-table-column>
          <el-table-column prop="content" label="内容"></el-table-column>
          <el-table-column prop="money" label="金额" width="100">
            <template #default="scope">
              <span style="color: #f56c6c;" v-if="scope.row.type == 2">- {{ scope.row.money }}</span>
              <span style="color: #006600;" v-if="scope.row.type == 1">+ {{ scope.row.money }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
        </el-table>
        <el-row class="el-rows">
          <el-col :span="24">
            <div class="topBtn flex align-center justify-end">
              <pagination :pageSize="limit" v-model:currentPage="page" :total="total" @pageChange="walletSelect()" />
            </div>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="提现记录" name="third">
        <el-table v-loading="tableDataLoading" :data="withdrawData">
          <el-table-column prop="id" label="编号" width="100" fixed="left"></el-table-column>
          <el-table-column prop="accountName" label="支付宝名称" width="150">
            <template #default="scope">
              <span>{{ scope.row.accountName ? scope.row.accountName : '暂无' }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="accountNumber" label="支付宝账号" width="100">
            <template #default="scope">
              <span v-if="scope.row.type == 1">{{ scope.row.accountNumber }}</span>
              <span v-else> - </span>
            </template>
          </el-table-column>
          <el-table-column prop="accountNumber" label="收款二维码" width="150">
            <template #default="scope">
              <div v-if="scope.row.type == 2 && scope.row.accountNumber" style="display:flex;flex-wrap: wrap;">
                <el-popover placement="top-start" title="" trigger="hover" popper-style="width:220px;">
                  <template #reference>
                    <img style="width: 50px; height: 50px" :src="scope.row.accountNumber" alt="">
                  </template>
                  <template #default>
                    <img style="width: 200px; height: auto" :src="scope.row.accountNumber" alt="">
                  </template>
                </el-popover>
              </div>
              <div v-else>
                暂无图片
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="amount" label="提现金额" width="100"></el-table-column>
          <el-table-column prop="outAmount" label="实际到账金额" width="100"></el-table-column>
          <el-table-column prop="serviceFee" label="平台服务费" width="100"></el-table-column>
          <el-table-column prop="refund" label="审核说明" width="100"></el-table-column>
          <el-table-column prop="type" label="提现方式" width="100">
            <template #default="scope">
              <span v-if="scope.row.type == 1">支付宝</span>
              <span v-else>微信</span>
            </template>
          </el-table-column>
          <el-table-column prop="state" label="状态" width="100" fixed="right">
            <template #default="scope">
              <span v-if="scope.row.state == 0">待转账</span>
              <span v-if="scope.row.state == 1">转账成功</span>
              <span v-if="scope.row.state == -1">拒绝转账</span>
            </template>
          </el-table-column>
          <el-table-column prop="outTime" label="转账时间" width="180"></el-table-column>
          <el-table-column prop="createTime" label="申请时间" width="180"></el-table-column>
        </el-table>
        <el-row class="el-rows">
          <el-col :span="24">
            <div class="topBtn flex align-center justify-end">
              <pagination :pageSize="limit" v-model:currentPage="page" :total="total" @pageChange="withdrawSelect()" />
            </div>
          </el-col>
        </el-row>
      </el-tab-pane> -->
      <el-tab-pane label="支付明细" name="fourth">
        <div style="display: inline-block;">
          <span>开始时间：</span>
          <el-date-picker style="width: 160px;margin-left: 10px;" v-model="startTime" align="right" type="date"
            format="YYYY-MM-DD" date-format="MMM DD, YYYY" placeholder="选择开始时间">
          </el-date-picker>&nbsp;&nbsp;&nbsp;
          <span>截止时间：</span>
          <el-date-picker style="width: 160px;margin-left: 10px;" v-model="endTime" align="right" type="date"
            format="YYYY-MM-DD" date-format="MMM DD, YYYY" placeholder="选择截止时间">
          </el-date-picker>
          <el-button style="margin-left:15px;" size="small" type="primary" icon="document"
            @click="timeDate2">查询</el-button>
        </div>
        <el-table v-loading="tableDataLoading" :data="rechargeData" style="width: 100%">
          <el-table-column prop="id" label="编号" width="100" fixed="left"></el-table-column>
          <!-- <el-table-column prop="orderNo" label="订单编号"></el-table-column> -->
          <el-table-column prop="money" label="支付金额"></el-table-column>
          <el-table-column prop="classify" label="支付类型" width="100">
            <template #default="scope">
              <span v-if="scope.row.classify == 1">注册</span>
              <span v-if="scope.row.classify == 2">购买</span>
              <span v-if="scope.row.classify == 3">提现</span>
            </template>
          </el-table-column>
          <el-table-column prop="state" label="状态" width="100" fixed="right">
            <template #default="scope">
              <span v-if="scope.row.state == 1">待支付</span>
              <span v-if="scope.row.state == 2">支付成功</span>
              <span v-if="scope.row.state == 3">支付失败</span>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间"></el-table-column>
        </el-table>
        <el-row class="el-rows">
          <el-col :span="24">
            <div class="topBtn flex align-center justify-end">
              <pagination :pageSize="limit" v-model:currentPage="page" :total="total" @pageChange="rechargeSelect()" />
            </div>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="我的动态" name="five">
        <el-table v-loading="tableDataLoading" :data="dongtaiData" style="width: 100%">
          <el-table-column prop="id" label="编号" width="100" fixed="left"></el-table-column>

          <el-table-column prop="title" label="标题" width="200"></el-table-column>
          <el-table-column prop="imgUrlList" label="图片" width="300">
            <template #default="scope">
              <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;"
                v-for="(item, index) in scope.row.imgUrlList" :key="index">
                <template #reference>
                  <img style="width: 50px; height: 50px;margin: 3px;" :src="item" alt="">
                </template>
                <template #default>
                  <img style="width: 400px; height: auto;" :src="item" alt="">
                </template>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column prop="videoUrl" label="视频" width="300">
            <template #default="scope">
              <video v-if="scope.row.videoUrl" style="width: 180px;height: 100px;" :src="scope.row.videoUrl"
                type="audio/wav" controls="controls"></video>
            </template>
          </el-table-column>
          <el-table-column prop="content" label="文字描述">
            <template #default="scope">
              <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">
                <div style="width: 400px;">{{ scope.row.content }}</div>
                <template #reference>
                  <div
                    style="display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 3;overflow: hidden;max-height:80px;">
                    {{ scope.row.content }}</div>
                </template>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column prop="province" label="地址" width="200">
            <template #default="scope">
              {{ scope.row.province }}{{ scope.row.city }}{{ scope.row.district }} {{ scope.row.location }}
            </template>
          </el-table-column>
          <el-table-column prop="views" label="浏览次数"></el-table-column>
          <el-table-column prop="views" label="点赞次数"></el-table-column>
          <!-- <el-table-column prop="privacy" label="权限设置" width="100" fixed="right">
            <template #default="scope">
              <span v-if="scope.row.privacy == 0">仅自己可见</span>
              <span v-if="scope.row.privacy == 1">仅好友</span>
              <span v-if="scope.row.privacy == 2">公开</span>
            </template>
          </el-table-column> -->
          <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
          <el-table-column label="操作" width="100" fixed="right">
            <template #default="scope">
              <el-button size="small" type="primary" @click="dianzanChange(scope.row)"
                style="margin: 5px;">点赞列表</el-button>
              <el-button size="small" type="primary" @click="pinglunChange(scope.row)"
                style="margin: 5px;">评论列表</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-row class="el-rows">
          <el-col :span="24">
            <div class="topBtn flex align-center justify-end">
              <pagination :pageSize="limit" v-model:currentPage="page" :total="total" @pageChange="dongtaiSelect()" />
            </div>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="我的关注" name="guanzhu">
        <el-table v-loading="tableDataLoading" :data="friendData">
          <el-table-column prop="userId" label="编号" width="100"></el-table-column>
          <el-table-column prop="targetUserName" label="用户昵称">
            <template #default="scope">
              <span style="color: #13ce66;cursor: pointer;" @click="updates(scope.row.targetUserId)">
                {{ scope.row.targetUserName ? scope.row.targetUserName : '未绑定' }}
              </span>
            </template>
          </el-table-column>
          <el-table-column prop="targetUserAvatar" label="用户图头">
            <template #default="scope">
              <img :src="scope.row.targetUserAvatar" style="width: 60px;height: 60px;" />
            </template>
          </el-table-column>
          <el-table-column prop="targetUserAge" label="年龄"></el-table-column>

        </el-table>
        <el-row class="el-rows">
          <el-col :span="24">
            <div class="topBtn flex align-center justify-end">
              <pagination :pageSize="limit" v-model:currentPage="page" :total="total" @pageChange="haoyouSelect()" />
            </div>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="我的粉丝" name="fensi">
        <el-table v-loading="tableDataLoading" :data="friendData">
          <el-table-column prop="userId" label="编号" width="100"></el-table-column>
          <el-table-column prop="userName" label="用户昵称">
            <template #default="scope">
              <span style="color: #13ce66;cursor: pointer;" @click="updates(scope.row.userId)">
                {{ scope.row.userName ? scope.row.userName : '未绑定' }}
              </span>
            </template>
          </el-table-column>
          <el-table-column prop="avatar" label="用户图头">
            <template #default="scope">
              <img :src="scope.row.avatar" style="width: 60px;height: 60px;" />
            </template>
          </el-table-column>
          <el-table-column prop="age" label="年龄"></el-table-column>

        </el-table>
        <el-row class="el-rows">
          <el-col :span="24">
            <div class="topBtn flex align-center justify-end">
              <pagination :pageSize="limit" v-model:currentPage="page" :total="total" @pageChange="haoyouSelect()" />
            </div>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="我收藏的景点" name="six">
        <el-table v-loading="tableDataLoading" :data="friendData">
          <el-table-column prop="id" label="编号" width="100"></el-table-column>
          <el-table-column prop="scenicSpot.name" label="景点名称" width="120">
          </el-table-column>
          <el-table-column prop="scenicSpot.img" label="景点图片" width="200">
            <template #default="scope">
              <div v-if="scope.row.scenicSpot">
                <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;"
                  v-for="(item, index) in scope.row.scenicSpot.img.split(',')" :key="index">
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
          <el-table-column prop="scenicSpot.openHours" label="开放时间">
          </el-table-column>
          <el-table-column prop="scenicSpot.location" label="景点位置" width="180">
          </el-table-column>
          <el-table-column prop="scenicSpot.ticketPrice" label="门票价格" width="180">
          </el-table-column>
          <el-table-column prop="scenicSpot.intro" label="简介" width="180">
          </el-table-column>
          <el-table-column prop="scenicSpot.traffic" label="交通攻略" width="180">
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="180">
          </el-table-column>

        </el-table>
        <el-row class="el-rows">
          <el-col :span="24">
            <div class="topBtn flex align-center justify-end">
              <pagination :pageSize="limit" v-model:currentPage="page" :total="total" @pageChange="haoyouSelect()" />
            </div>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="我收藏的动态" name="scdongtai">
        <el-table v-loading="tableDataLoading" :data="friendData">
          <el-table-column prop="id" label="编号" width="100"></el-table-column>
          <el-table-column prop="userName" label="用户昵称">
            <template #default="scope">
              <span style="color: #13ce66;cursor: pointer;" v-if="scope.row.moments" @click="updates(scope.row.userId)">
                {{ scope.row.moments.userName ? scope.row.moments.userName : '未绑定' }}
              </span>
            </template>
          </el-table-column>
          <el-table-column prop="moments.avatar" label="用户图头">
            <template #default="scope">
              <img v-if="scope.row.moments" :src="scope.row.moments.avatar" style="width: 60px;height: 60px;" />
            </template>
          </el-table-column>
          <el-table-column prop="moments.title" label="标题" width="200"></el-table-column>
          <el-table-column prop="moments.imgUrlList" label="图片" width="300">
            <template #default="scope">
              <div v-if="scope.row.moments">
                <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;"
                  v-for="(item, index) in scope.row.moments.imgUrlList" :key="index">
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
          <el-table-column prop="moments.videoUrl" label="视频" width="300">
            <template #default="scope">
              <video v-if="scope.row.moments" style="width: 180px;height: 100px;" :src="scope.row.moments.videoUrl"
                type="audio/wav" controls="controls"></video>
            </template>
          </el-table-column>
          <el-table-column prop="content" label="文字描述">
            <template #default="scope">
              <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;"
                v-if="scope.row.moments">
                <div style="width: 400px;">{{ scope.row.moments.content }}</div>
                <template #reference>
                  <div
                    style="display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 3;overflow: hidden;max-height:80px;">
                    {{ scope.row.moments.content }}</div>
                </template>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column prop="province" label="地址" width="200">
            <template #default="scope">
              <div v-if="scope.row.moments">
                {{ scope.row.moments.province }}{{ scope.row.moments.city }}{{ scope.row.moments.district }} {{
                  scope.row.moments.location }}
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="views" label="浏览次数"></el-table-column>
          <el-table-column prop="views" label="点赞次数"></el-table-column>

        </el-table>
        <el-row class="el-rows">
          <el-col :span="24">
            <div class="topBtn flex align-center justify-end">
              <pagination :pageSize="limit" v-model:currentPage="page" :total="total" @pageChange="haoyouSelect()" />
            </div>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="我收藏的线路" name="xianlu">
        <el-table v-loading="tableDataLoading" :data="friendData">
          <el-table-column prop="id" label="编号" width="100" fixed="left"></el-table-column>
          <el-table-column prop="baseInfo.title" label="标题" fixed="left" width="300">
          </el-table-column>
          <el-table-column prop="baseInfo.remark" label="描述" width="200">
          </el-table-column>
          <el-table-column prop="baseInfo.days" label="行程天数">
          </el-table-column>
          <el-table-column prop="baseInfo.kilometres" label="行驶公里数">
          </el-table-column>
          <el-table-column prop="baseInfo.times" label="最佳时间">
          </el-table-column>
          <el-table-column prop="baseInfo.tags" label="标签">
          </el-table-column>
          <el-table-column prop="baseInfo.imgUrl" label="封面图">
            <template #default="scope">
              <div v-if="scope.row.baseInfo && scope.row.baseInfo.imgUrl">
                <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">
                  <template #reference>
                    <img style="width: 50px; height: 50px;margin: 3px;" :src="scope.row.imgUrl" alt="">
                  </template>
                  <template #default>
                    <img style="width: 400px; height: auto;" :src="scope.row.imgUrl" alt="">
                  </template>
                </el-popover>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <el-row class="el-rows">
          <el-col :span="24">
            <div class="topBtn flex align-center justify-end">
              <pagination :pageSize="limit" v-model:currentPage="page" :total="total" @pageChange="haoyouSelect()" />
            </div>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="我收藏的商户" name="scshop">
        <el-table v-loading="tableDataLoading" :data="friendData">
          <el-table-column prop="userId" label="编号" width="100"></el-table-column>
          <el-table-column prop="userName" label="用户昵称">
            <template #default="scope">
              <span style="color: #13ce66;cursor: pointer;" @click="updates(scope.row.userId)">
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

        </el-table>
        <el-row class="el-rows">
          <el-col :span="24">
            <div class="topBtn flex align-center justify-end">
              <pagination :pageSize="limit" v-model:currentPage="page" :total="total" @pageChange="haoyouSelect()" />
            </div>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="我的群组" name="seven">
        <el-table v-loading="tableDataLoading" :data="qunzuData">
          <el-table-column prop="id" label="编号" width="100" fixed="left"></el-table-column>
          <!-- <el-table-column prop="ownerUserInfo" label="群主昵称" fixed="left">
                        <template #default="scope">
                            <span v-if="scope.row.ownerUserInfo" style="color: #13ce66;cursor: pointer;"
                                @click="updates(scope.row.ownerUserId)">
                                {{ scope.row.ownerUserInfo.userName}}
                            </span>
                            <span v-else>-</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="ownerUserInfo" label="群主头像">
                        <template #default="scope">
                            <img v-if="scope.row.ownerUserInfo"
                                :src="scope.row.ownerUserInfo.avatar" style="width: 60px;height: 60px;" />
                            <span v-else> - </span>
                        </template>
                    </el-table-column> -->
          <el-table-column prop="name" label="群名称" fixed="left"></el-table-column>
          <el-table-column prop="notice" label="群公告" width="100"></el-table-column>
          <el-table-column prop="qrCode" label="群二维码">
            <template #default="scope">
              <el-button size="small" type="primary" @click="erweimaChange(scope.row)"
                style="margin: 5px;">查看</el-button>
            </template>
          </el-table-column>
          <el-table-column prop="noticeImg" label="组公告图片">
            <template #default="scope">
              <div v-if="scope.row.noticeImg">
                <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;"
                  v-for="(item, index) in scope.row.noticeImg.split(',')" :key="index">
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
          <el-table-column prop="inviteVerify" label="是否开启认证" width="100">
            <template #default="scope">
              <span v-if="scope.row.inviteVerify == 1">是</span>
              <span v-else>否</span>
            </template>
          </el-table-column>
          <el-table-column prop="status" label="状态" width="100" fixed="right">
            <template #default="scope">
              <span v-if="scope.row.status == 1">正常</span>
              <span v-else>禁用</span>

            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
          <el-table-column label="操作" width="100" fixed="right">
            <template #default="scope">
              <el-button size="small" type="primary" @click="cyChange(scope.row)" style="margin: 5px;">群组成员</el-button>
              <!-- <el-button size="small" type="primary" @click="sqChange(scope.row)" style="margin: 5px;">入群申请</el-button> -->
              <el-button size="small" type="primary" @click="ltChange(scope.row)" style="margin: 5px;">聊天记录</el-button>
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
      <el-tab-pane label="我喜欢的动态" name="nine">
        <el-table v-loading="tableDataLoading" :data="tousuData">
          <el-table-column prop="id" label="编号" width="100"></el-table-column>
          <el-table-column prop="userName" label="用户昵称">
            <template #default="scope">
              <span style="color: #13ce66;cursor: pointer;" v-if="scope.row.moments" @click="updates(scope.row.userId)">
                {{ scope.row.moments.userName ? scope.row.moments.userName : '未绑定' }}
              </span>
            </template>
          </el-table-column>
          <el-table-column prop="moments.avatar" label="用户图头">
            <template #default="scope">
              <img v-if="scope.row.moments" :src="scope.row.moments.avatar" style="width: 60px;height: 60px;" />
            </template>
          </el-table-column>
          <el-table-column prop="moments.title" label="标题" width="200"></el-table-column>
          <el-table-column prop="moments.imgUrlList" label="图片" width="300">
            <template #default="scope">
              <div v-if="scope.row.moments">
                <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;"
                  v-for="(item, index) in scope.row.moments.imgUrlList" :key="index">
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
          <el-table-column prop="moments.videoUrl" label="视频" width="300">
            <template #default="scope">
              <video v-if="scope.row.moments" style="width: 180px;height: 100px;" :src="scope.row.moments.videoUrl"
                type="audio/wav" controls="controls"></video>
            </template>
          </el-table-column>
          <el-table-column prop="content" label="文字描述">
            <template #default="scope">
              <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;"
                v-if="scope.row.moments">
                <div style="width: 400px;">{{ scope.row.moments.content }}</div>
                <template #reference>
                  <div
                    style="display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 3;overflow: hidden;max-height:80px;">
                    {{ scope.row.moments.content }}</div>
                </template>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column prop="province" label="地址" width="200">
            <template #default="scope">
              <div v-if="scope.row.moments">
                {{ scope.row.moments.province }}{{ scope.row.moments.city }}{{ scope.row.moments.district }} {{
                  scope.row.moments.location }}
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="views" label="浏览次数"></el-table-column>
          <el-table-column prop="views" label="点赞次数"></el-table-column>
        </el-table>
        <el-row class="el-rows">
          <el-col :span="24">
            <div class="topBtn flex align-center justify-end">
              <pagination :pageSize="limit" v-model:currentPage="page" :total="total" @pageChange="qingqiuSelect()" />
            </div>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="我喜欢的景点" name="nineJd">
        <el-table v-loading="tableDataLoading" :data="tousuData">
          <el-table-column prop="id" label="编号" width="100"></el-table-column>
          <el-table-column prop="scenicSpot.name" label="景点名称" width="120">
          </el-table-column>
          <el-table-column prop="scenicSpot.img" label="景点图片" width="200">
            <template #default="scope">
              <div v-if="scope.row.scenicSpot">
                <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;"
                  v-for="(item, index) in scope.row.scenicSpot.img.split(',')" :key="index">
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
          <el-table-column prop="scenicSpot.openHours" label="开放时间">
          </el-table-column>
          <el-table-column prop="scenicSpot.location" label="景点位置" width="180">
          </el-table-column>
          <el-table-column prop="scenicSpot.ticketPrice" label="门票价格" width="180">
          </el-table-column>
          <el-table-column prop="scenicSpot.intro" label="简介" width="180">
          </el-table-column>
          <el-table-column prop="scenicSpot.traffic" label="交通攻略" width="180">
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="180">
          </el-table-column>

        </el-table>
        <el-row class="el-rows">
          <el-col :span="24">
            <div class="topBtn flex align-center justify-end">
              <pagination :pageSize="limit" v-model:currentPage="page" :total="total" @pageChange="qingqiuSelect()" />
            </div>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="我喜欢的路线" name="nineXl">
        <el-table v-loading="tableDataLoading" :data="tousuData">
          <el-table-column prop="id" label="编号" width="100" fixed="left"></el-table-column>
          <el-table-column prop="baseInfo.title" label="标题" fixed="left" width="300">
          </el-table-column>
          <el-table-column prop="baseInfo.remark" label="描述" width="200">
          </el-table-column>
          <el-table-column prop="baseInfo.days" label="行程天数">
          </el-table-column>
          <el-table-column prop="baseInfo.kilometres" label="行驶公里数">
          </el-table-column>
          <el-table-column prop="baseInfo.times" label="最佳时间">
          </el-table-column>
          <el-table-column prop="baseInfo.tags" label="标签">
          </el-table-column>
          <el-table-column prop="baseInfo.imgUrl" label="封面图">
            <template #default="scope">
              <div v-if="scope.row.baseInfo && scope.row.baseInfo.imgUrl">
                <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">
                  <template #reference>
                    <img style="width: 50px; height: 50px;margin: 3px;" :src="scope.row.imgUrl" alt="">
                  </template>
                  <template #default>
                    <img style="width: 400px; height: auto;" :src="scope.row.imgUrl" alt="">
                  </template>
                </el-popover>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <el-row class="el-rows">
          <el-col :span="24">
            <div class="topBtn flex align-center justify-end">
              <pagination :pageSize="limit" v-model:currentPage="page" :total="total" @pageChange="qingqiuSelect()" />
            </div>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="我喜欢的商户" name="nineSh">
        <el-table v-loading="tableDataLoading" :data="tousuData">
          <el-table-column prop="userId" label="编号" width="100"></el-table-column>
          <el-table-column prop="userName" label="用户昵称">
            <template #default="scope">
              <span style="color: #13ce66;cursor: pointer;" @click="updates(scope.row.userId)">
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
        </el-table>
        <el-row class="el-rows">
          <el-col :span="24">
            <div class="topBtn flex align-center justify-end">
              <pagination :pageSize="limit" v-model:currentPage="page" :total="total" @pageChange="qingqiuSelect()" />
            </div>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="我的黑名单" name="heimingdan">
        <el-table v-loading="tableDataLoading" :data="friendData">
          <el-table-column prop="userId" label="编号" width="100"></el-table-column>
          <el-table-column prop="userName" label="用户昵称">
            <template #default="scope">
              <span style="color: #13ce66;cursor: pointer;" @click="updates(scope.row.userId)">
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

        </el-table>
        <el-row class="el-rows">
          <el-col :span="24">
            <div class="topBtn flex align-center justify-end">
              <pagination :pageSize="limit" v-model:currentPage="page" :total="total" @pageChange="haoyouSelect()" />
            </div>
          </el-col>
        </el-row>
      </el-tab-pane>
      <!-- <el-tab-pane label="聊天记录" name="ten">
        <el-table v-loading="tableDataLoading" :data="liaotianData">
          <el-table-column prop="id" label="编号" width="100">
            <template #default="scope">
              {{ scope.$index + 1 }}
            </template>
          </el-table-column>
          <el-table-column prop="senderUserName" label="用户昵称">
            <template #default="scope">
              <div v-if="scope.row.senderUserName" style="color: #13ce66;cursor: pointer;"
                @click="updates(scope.row.senderUserId)">
                {{ scope.row.senderUserName ? scope.row.senderUserName : '未绑定' }}
              </div>
              <div v-if="scope.row.receiverUserName" style="color: #13ce66;cursor: pointer;"
                @click="updates(scope.row.receiverUserId)">
                {{ scope.row.receiverUserName ? scope.row.receiverUserName : '未绑定' }}
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="senderAvatar" label="用户图头">
            <template #default="scope">
              <img v-if="scope.row.senderAvatar" :src="scope.row.senderAvatar"
                style="width: 60px;height: 60px;margin: 3px;" />
              <img v-if="scope.row.receiverAvatar" :src="scope.row.receiverAvatar"
                style="width: 60px;height: 60px;margin: 3px;" />
            </template>
          </el-table-column>
          <el-table-column prop="messageCount" label="信息数量" width="180"></el-table-column>

          <el-table-column prop="lastMessageTime" label="创建时间" width="180"></el-table-column>
          <el-table-column fixed='right' label="操作" width="150">
            <template #default="scope">
              <el-button type="primary" @click="complainDetails(scope.row)">聊天内容
              </el-button>
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
      </el-tab-pane> -->
    </el-tabs>
    <!-- 弹框 -->
    <!-- 修改用户信息 弹框 -->
    <el-dialog v-model="dialogFormVisibleU" title="用户信息" width="80%" center>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">微信名称：</span>
        <el-input style="width:50%;" v-model="userNameU" type="text" placeholder="请输入微信名称"></el-input>
      </div>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">手机号：</span>
        <el-input style="width:50%;" v-model="phoneU" type="text" placeholder="请输入手机号"></el-input>
      </div>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">邀请人邀请码：</span>
        <el-input style="width:50%;" v-model="inviterCodeU" type="text" placeholder="请输入邀请人邀请码"></el-input>
      </div>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">邀请码：</span>
        <el-input style="width:50%;" v-model="invitationCodeU" type="text" placeholder="请输入邀请码"></el-input>
      </div>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">年龄：</span>
        <el-input style="width:50%;" v-model="ageU" type="number" :min="0" :controls="false"
          placeholder="请输入年龄"></el-input>
      </div>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">性别：</span>
        <el-radio-group v-model="sexU" class="ml-4">
          <el-radio :label="1" size="large">男</el-radio>
          <el-radio :label="2" size="large">女</el-radio>
        </el-radio-group>
      </div>
      <!-- <div style="margin-bottom: 10px;">
					<span style="width: 200px;display: inline-block;text-align: right;">直属佣金比例：</span>
					<el-input style="width:50%;" v-model="zhiRateU" type="number" :min="0" :controls="false"
						placeholder="输入佣金比例"></el-input>
				</div>
				<div style="margin-bottom: 10px;">
					<span style="width: 200px;display: inline-block;text-align: right;">非直属佣金比例：</span>
					<el-input style="width:50%;" v-model="feiRateU" type="number" :min="0" :controls="false"
						placeholder="输入佣金比例"></el-input>
				</div> -->
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">个性签名</span>
        <el-input style="width:50%;" v-model="msgU" type="text" placeholder="请输入个性签名"></el-input>
      </div>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">图像：</span>
        <div style="display: inline-block;">
          <el-upload class="avatar-uploader" v-model="avatarU"
            :action="$uploadUrl" :show-file-list="false"
            :on-success="handleAvatarSuccess1">
            <img v-if="avatarU" :src="avatarU" class="avatar" style="width: 148px;height: 148px;" />
            <el-icon v-else style="font-size: 28px;color: #D9D9D9">
              <Plus />
            </el-icon>

            <!-- <i v-else class="el-icon-plus avatar-uploader-icon" style="font-size: 28px;color: #8c939d"></i> -->
          </el-upload>
        </div>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogFormVisibleU = false" style="margin: 10px;">取消</el-button>
          <el-button type="primary" @click="StairNoticeToU" style="margin: 10px;">确认</el-button>
        </span>
      </template>
    </el-dialog>
    <!-- 动态评论 弹框 -->
    <el-dialog v-model="dialogTableVisible2" title="动态评论" center width="80%">
      <el-table v-loading="tableDataLoading1" :data="pinglunDatas.records">
        <el-table-column prop="commentId" label="编号" width="80">
        </el-table-column>
        <el-table-column prop="nickName" label="用户昵称">
          <template #default="scope">
            <div style="color: #4f9dec;cursor: pointer;" @click="updates(scope.row.userId)">
              {{ scope.row.nickName ? scope.row.nickName : '未绑定' }}
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="imageUrl" label="用户头像">
          <template #default="scope">
            <div v-if="scope.row.imageUrl == null || scope.row.imageUrl == ''">
              暂无图片
            </div>
            <div v-else>
              <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">
                <template #reference>
                  <img style="width: 50px; height: 50px;margin: 3px;" :src="scope.row.imageUrl" alt="">
                </template>
                <template #default>
                  <img style="width: 400px; height: auto;" :src="scope.row.imageUrl" alt="">
                </template>
              </el-popover>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="content" label="信息内容" width="280">
          <template #default="scope">
            <div>{{ scope.row.content }}</div>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="150">
        </el-table-column>
      </el-table>

    </el-dialog>
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
    <!-- 修改钱包  -->
    <el-dialog title="修改钱包" v-model="dialogFormVisible1" center>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">修改类型：</span>
        <el-radio-group v-model="type">
          <el-radio label="incr">添加</el-radio>
          <el-radio label="decr">减少</el-radio>
        </el-radio-group>
      </div>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">金额：</span>
        <el-input style="width:50%;" v-model="money" type="number" :min="0" :max="100" :controls="false"
          placeholder="请输入金额"></el-input>
      </div>
      <template #footer>
        <el-button @click="dialogFormVisible1 = false">取 消</el-button>
        <el-button type="primary" @click="StairNoticeTo1()">确 定</el-button>
      </template>
    </el-dialog>
    <!-- 点赞列表 -->
    <el-dialog v-model="dialogFormVisibleDz" title="点赞列表" width="80%" center>
      <el-table :data="tableDataDz" v-loading="loading" style="width: 100%">
        <el-table-column prop="userId" label="编号" width="100"></el-table-column>
        <el-table-column prop="userName" label="点赞用户">
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
        <el-table-column prop="createTime" label="点赞时间" width="180"></el-table-column>
      </el-table>
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
        <div style="display: inline-block;margin: 5px;">
          <span>手机号：</span>
          <el-input v-model="phoneC" placeholder="请输入手机号" style="width: 150px;" />
        </div>
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
import {
  ElMessageBox,
  ElMessage
} from 'element-plus';
import qrcode from 'qrcode';
const {
  proxy
} = getCurrentInstance();
const route = useRoute();
const router = useRouter();
const activeName = ref('first'); //默认选中
const userList = reactive([]);//列表集合
const tableData = ref({}); //用户详情
const walletData = reactive([]); //钱包明细 集合
const withdrawData = reactive([]); //提现记录 集合
const rechargeData = reactive([]); //支付明细 集合
const dongtaiData = reactive([]); //我的动态 集合
const friendData = reactive([]); //我的通讯录 集合
const qunzuData = reactive([]); //我的群组 集合
const tousuData = reactive([]); //好友请求 集合
const liaotianData = reactive([]); //聊天记录 集合
const huihuaData = reactive([]); //聊天内容 集合
const tableDataDz = reactive([]); //点赞 集合
const tableDataPl = reactive([]); //评论 集合
const tableDataCy = reactive([]); //群成员 集合
const tableDataLt = reactive([]); //群聊天 集合



const page = ref(1);//当前页
const limit = ref(10);//每页条数
const total = ref(0);//总条数
const page1 = ref(1);//当前页
const limit1 = ref(10);//每页条数
const total1 = ref(0);//总条数

//列表是否加载中
const dialogFormVisibleU = ref(false);
const dialogFormVisible4 = ref(false);
const dialogFormVisibleDz = ref(false);
const dialogFormVisiblePl = ref(false);
const dialogFormVisibleCy = ref(false);
const dialogFormVisibleLt = ref(false);
const tableDataLoading = ref(false);
const tableDataLoading1 = ref(false);
const loadingCy = ref(false);
const startTime = ref('');
const endTime = ref('');
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
const stas = ref(1);




//聊天类型 集合
const typeData = reactive([
  {
    label: '全部',
    value: ''
  },
  {
    label: '普通消息',
    value: 1
  },
  {
    label: '表情',
    value: 2
  },
  {
    label: '图片',
    value: 3
  },
  {
    label: '视频',
    value: 4
  }
]);



// 修改用户信息
const loading = ref(false);
const userNameU = ref('');
const phoneU = ref('');
const inviterCodeU = ref('');
const invitationCodeU = ref('');
const avatarU = ref('');
const ageU = ref('');
const sexU = ref('');
const zhiRateU = ref('');
const feiRateU = ref('');
const msgU = ref('');
// 修改钱包弹框的
const dialogFormVisible1 = ref(false);
const type = ref('');
const money = ref('');
const typeIndex = ref(1);


onMounted(() => {
  getUserList();
});
//跳转用户详情
const updates = (userId) => {
  router.push({
    name: 'userDetail',
    query: {
      userId: userId,
    },

  });
};
//获取用户详情
const getUserList = () => {
  loading.value = true
  const userId = route.query.userId
  userList.splice(0, userList.length)
  proxy.$Request.get('/admin/base/user/info', {
    params: {
      userId: userId,
    }
  }, {
    type: 'json'
  }).then(res => {
    // console.log(res)
    loading.value = false;
    if (res.code == 0) {
      let returnData = res.data
      tableData.value = returnData
    }
  })
};
// 切换tab 事件
const handleClick = (tab, event) => {
  console.log(event.srcElement.innerText)

  if (event.srcElement.innerText == '用户详情') {
    getUserList();
  }
  if (event.srcElement.innerText == '钱包明细') {
    walletSelect();
  }
  if (event.srcElement.innerText == '提现记录') {
    withdrawSelect();
  }
  if (event.srcElement.innerText == '支付明细') {
    rechargeSelect();
  }
  if (event.srcElement.innerText == '我的动态') {
    dongtaiSelect();
  }
  if (event.srcElement.innerText == '我的关注') {
    guanzhuSelect();
  }
  if (event.srcElement.innerText == '我的粉丝') {
    fensiSelect();
  }
  if (event.srcElement.innerText == '我收藏的景点') {
    typeIndex.value = 2
    haoyouSelect();
  }
  if (event.srcElement.innerText == '我收藏的动态') {
    typeIndex.value = 1
    haoyouSelect();
  }
  if (event.srcElement.innerText == '我收藏的线路') {
    typeIndex.value = 3
    haoyouSelect();
  }
  if (event.srcElement.innerText == '我收藏的商户') {
    typeIndex.value = 4
    haoyouSelect();
  }
  if (event.srcElement.innerText == '我的群组') {
    qunzuSelect();
  }
  if (event.srcElement.innerText == '我的消息') {
    tousuSelect();
  }
  if (event.srcElement.innerText == '我喜欢的动态') {
    typeIndex.value = 1
    qingqiuSelect();
  }
  if (event.srcElement.innerText == '我喜欢的景点') {
    typeIndex.value = 2
    qingqiuSelect();
  }
  if (event.srcElement.innerText == '我喜欢的路线') {
    typeIndex.value = 3
    qingqiuSelect();
  }
  if (event.srcElement.innerText == '我喜欢的商户') {
    typeIndex.value = 4
    qingqiuSelect();
  }

  if (event.srcElement.innerText == '聊天记录') {
    liaotianSelect();
  }
  if (event.srcElement.innerText == '我的黑名单') {
    heimingdanSelect();
  }

}
// 钱包明细 列表
const walletSelect = () => {
  tableDataLoading.value = true
  walletData.splice(0, walletData.length)
  let userId = route.query.userId
  proxy.$Request.get('admin/wallet/userMoneyDetails', {
    params: {
      userId: userId,
      page: page.value,
      limit: limit.value,

    }
  }, {}).then((res) => {
    console.log(res)
    tableDataLoading.value = false;
    walletData.splice(0, walletData.length, ...res.data.records)
    total.value = res.data.total;
  })
}
// 提现记录 列表
const withdrawSelect = () => {
  tableDataLoading.value = true
  withdrawData.splice(0, withdrawData.length)
  let userId = route.query.userId
  proxy.$Request.post('/admin/withdrawal/page', {
    // params: {
    userId: userId,
    page: page.value,
    limit: limit.value,

    // }
  }, {
    type: 'json'
  }).then((res) => {
    console.log(res)
    tableDataLoading.value = false;
    withdrawData.splice(0, withdrawData.length, ...res.data.records)
    total.value = res.data.total;
  })
}
// 支付明细 列表
const rechargeSelect = () => {
  tableDataLoading.value = true
  rechargeData.splice(0, rechargeData.length)
  let userId = route.query.userId
  proxy.$Request.get('/admin/wallet/fundDetail', {
    params: {
      userId: userId,
      page: page.value,
      limit: limit.value,
      startTime: startTime.value,
      endTime: endTime.value,
      classify: '',
      state: '',

    }
  }, {
    // type: 'json'
  }).then((res) => {
    console.log(res)
    tableDataLoading.value = false;
    rechargeData.splice(0, rechargeData.length, ...res.data.records)
    total.value = res.data.total;
  })
}
const timeDate2 = () => {
  page.value = 1
  rechargeSelect()
}
// 我的动态 列表
const dongtaiSelect = () => {
  tableDataLoading.value = true
  dongtaiData.splice(0, dongtaiData.length)
  let userId = route.query.userId
  proxy.$Request.post('/admin/moments/page', {
    // params: {
    userId: userId,
    page: page.value,
    limit: limit.value,
    userName: '',
    location: '',
    privacy: '',

    // }
  }, {
    type: 'json'
  }).then((res) => {
    console.log(res)
    tableDataLoading.value = false;
    dongtaiData.splice(0, dongtaiData.length, ...res.data.records)
    total.value = res.data.total;
  })
}
/** 点赞用户 弹框 */
const dianzanChange = (row) => {

  tableDataDz.splice(0, tableDataDz.length, ...row.imPostsLikeDataList)
  dialogFormVisibleDz.value = true

}
/** 评论用户 弹框 */
const pinglunChange = (row) => {

  tableDataPl.splice(0, tableDataPl.length, ...row.imPostsCommentDataList)
  dialogFormVisiblePl.value = true
}

// 我的关注 列表
const guanzhuSelect = () => {
  tableDataLoading.value = true
  friendData.splice(0, friendData.length)
  let userId = route.query.userId
  proxy.$Request.post('/admin/follow/page', {
    // params: {
    userId: userId,
    page: page.value,
    limit: limit.value,

    // }
  }, {
    type: 'json'
  }).then((res) => {
    console.log(res)
    tableDataLoading.value = false;
    friendData.splice(0, friendData.length, ...res.data.records)
    total.value = res.data.total;
  })

}
// 我的粉丝 列表
const fensiSelect = () => {
  tableDataLoading.value = true
  friendData.splice(0, friendData.length)
  let userId = route.query.userId
  proxy.$Request.post('admin/follow/page', {
    // params: {
    targetUserId: userId,
    page: page.value,
    limit: limit.value,

    // }
  }, {
    type: 'json'
  }).then((res) => {
    console.log(res)
    tableDataLoading.value = false;
    friendData.splice(0, friendData.length, ...res.data.records)
    total.value = res.data.total;
  })

}
// 我的收藏 列表
const haoyouSelect = () => {
  tableDataLoading.value = true
  friendData.splice(0, friendData.length)
  let userId = route.query.userId
  proxy.$Request.post('admin/collect/page', {
    // params: {
    userId: userId,
    page: page.value,
    limit: limit.value,
    type: typeIndex.value,

    // }
  }, {
    type: 'json'
  }).then((res) => {
    console.log(res)
    tableDataLoading.value = false;
    friendData.splice(0, friendData.length, ...res.data.records)
    total.value = res.data.total;
  })

}
// 我的群组 列表
const qunzuSelect = () => {
  tableDataLoading.value = true
  qunzuData.splice(0, qunzuData.length)
  let userId = route.query.userId
  proxy.$Request.post('admin/groupInfo/page', {
    // params: {
    userId: userId,
    page: page.value,
    limit: limit.value,

    // }
  }, {
    type: 'json'
  }).then((res) => {
    console.log(res)
    tableDataLoading.value = false;
    qunzuData.splice(0, qunzuData.length, ...res.data.records)
    total.value = res.data.total;
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
  proxy.$Request.get('admin/group/groupUserList', {
    params: {
      groupId: id.value,
      userName: userNameC.value,
      phone: phoneC.value,

    }
  }, {}).then((res) => {
    console.log(res)
    loadingCy.value = false;
    tableDataCy.splice(0, tableDataCy.length, ...res.data)
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
// 我的黑名单 列表
const heimingdanSelect = () => {
  tableDataLoading.value = true
  friendData.splice(0, friendData.length)
  let userId = route.query.userId
  proxy.$Request.post('/admin/blackList/page', {
    // params: {
    userId: userId,
    page: page.value,
    limit: limit.value,

    // }
  }, {
    type: 'json'
  }).then((res) => {
    console.log(res)
    tableDataLoading.value = false;
    friendData.splice(0, friendData.length, ...res.data.records)
    total.value = res.data.total;
  })

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
  proxy.$Request.get('admin/history/page', {
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




// 我的消息 列表
const tousuSelect = () => {
  tableDataLoading.value = true
  tousuData.splice(0, tousuData.length)
  let userId = route.query.userId
  proxy.$Request.post('/admin/messageInfo/page', {
    userId: userId,
    page: page.value,
    limit: limit.value,
    type: 1,
  }, {
    type: 'json'
  }).then((res) => {
    console.log(res)
    tableDataLoading.value = false;
    tousuData.splice(0, tousuData.length, ...res.data.records)
    total.value = res.data.total;
  })


}
// 我的喜欢 列表
const qingqiuSelect = () => {
  tableDataLoading.value = true
  tousuData.splice(0, tousuData.length)
  let userId = route.query.userId
  proxy.$Request.post('admin/like/page', {
    // params: {
    userId: userId,
    page: page.value,
    limit: limit.value,
    type: typeIndex.value,

    // }
  }, {
    type: 'json'
  }).then((res) => {
    console.log(res)
    tableDataLoading.value = false;
    tousuData.splice(0, tousuData.length, ...res.data.records)
    total.value = res.data.total;
  })
}
// 聊天记录 列表
const liaotianSelect = () => {
  tableDataLoading.value = true
  liaotianData.splice(0, tousuData.length)
  let userId = route.query.userId
  proxy.$Request.post('/admin/history/chatHistory', {
    userId: userId,
    page: page.value,
    limit: limit.value,
  }, {
    type: 'json'
  }).then((res) => {
    console.log(res)
    tableDataLoading.value = false;
    liaotianData.splice(0, liaotianData.length, ...res.data.records)
    total.value = res.data.total;
  })
}
//         /**  聊天内容*/
const complainDetails = (row) => {


  page1.value = 1
  dialogFormVisible4.value = true
  receiverUserId.value = row.receiverUserId
  senderUserId.value = row.senderUserId
  neirongSelect()
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

// 修改钱包
const rechargenone = () => {
  type.value = ''
  money.value = ''
  dialogFormVisible1.value = true
}
const StairNoticeTo1 = () => {
  let userId = route.query.userId
  if (type.value == '') {
    ElMessage({
      message: '请选择修改类型',
      type: 'warning',
      onClose: () => {
      }
    })

    return
  }
  if (money.value == '') {
    ElMessage({
      message: '请输入金额',
      type: 'warning',
      onClose: () => {
      }
    })
    return
  }
  proxy.$Request.get('/admin/wallet/updateUserMoney', {
    params: {
      userId: userId,
      amount: money.value,
      opt: type.value
    }
  }, {}).then((res) => {
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
/** 修改信息弹框 */
const ageChangeU = () => {
  userNameU.value = tableData.value.userName
  phoneU.value = tableData.value.phone
  inviterCodeU.value = tableData.value.inviterCode
  invitationCodeU.value = tableData.value.invitationCode
  avatarU.value = tableData.value.avatar
  ageU.value = tableData.value.age
  sexU.value = tableData.value.sex
  zhiRateU.value = tableData.value.zhiRate
  feiRateU.value = tableData.value.feiRate
  msgU.value = tableData.value.signature
  dialogFormVisibleU.value = true
  console.log(avatarU.value)
}
/** 修改性别 */
const StairNoticeToU = () => {
  let userId = route.query.userId
  proxy.$Request.post('admin/base/user/update', {
    userId: userId,
    inviterCode: inviterCodeU.value,
    avatar: avatarU.value,
    userName: userNameU.value,
    phone: phoneU.value,
    invitationCode: invitationCodeU.value,
    age: ageU.value,
    sex: sexU.value,
    zhiRate: zhiRateU.value,
    feiRate: feiRateU.value,
    signature: msgU.value,
  }, { type: 'json' }).then((res) => {
    console.log(res)
    loading.value = false;
    if (res.code == 0) {
      ElMessage({
        message: '修改成功',
        type: 'success',
        onClose: () => {
          dialogFormVisibleU.value = false
          getUserList()
        }
      })
    } else {
      ElMessage({
        message: res.msg,
        type: 'warning',
        onClose: () => {
          getUserList()
        }
      })
    }
  })
}
/** 更改状态 */
const stateChange = (val) => {

  if (val.status == 1) {
    stas.value = 2

  } else {
    stas.value = 1

  }
  ElMessageBox.confirm(`确定要更改用户状态吗?`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    proxy.$Request.post('/admin/base/user/update', {
      userId: val.userId,
      status: stas.value,
    }, {
      type: 'json'
    }).then((res) => {
      if (res.code == 0) {
        ElMessage({
          message: '修改成功',
          type: 'success',
          onClose: () => {
            getUserList()
          }
        })
      } else {
        ElMessage({
          message: res.msg,
          type: 'warning',
          onClose: () => {
            getUserList()
          }
        })
      }
    })

  })
}
// 返回上一页
const prev = () => {
  router.back()
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
</style>
