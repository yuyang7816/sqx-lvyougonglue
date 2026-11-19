<template>
  <div class="menu">
    <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
      <el-tab-pane label="商户列表" name="first">
        <div>
          <div style="display: inline-block;margin: 5px;">
            <span>联系人姓名 ：</span>
            <el-input v-model="userNameT" placeholder="请输入联系人姓名" style="width: 150px;" />
          </div>&nbsp;&nbsp;
          <div style="display: inline-block;margin: 5px;">
            <span>店铺名称 ：</span>
            <el-input v-model="nameT" placeholder="请输入店铺名称" style="width: 150px;" />
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
          <el-button size="small" type="primary" @click="addChangeU()">添加</el-button>
        </div>
        <el-table v-loading="tableDataLoading" :data="qunzuData">
          <el-table-column prop="id" label="编号" width="80" fixed="left"></el-table-column>
          <el-table-column prop="contactName" label="联系人姓名" fixed="left">
          </el-table-column>
          <el-table-column prop="phone" label="联系方式" width="160">
          </el-table-column>
          <el-table-column prop="shopTypeName" label="类型名称" fixed="left"></el-table-column>
          <el-table-column prop="name" label="店铺名称" fixed="left"></el-table-column>
          <!-- <el-table-column prop="qrCode" label="群二维码" width="100">
            <template #default="scope">
              <el-button size="small" type="primary" @click="erweimaChange(scope.row)"
                style="margin: 5px;">查看</el-button>
            </template>
</el-table-column> -->
          <el-table-column prop="grade" label="评分"></el-table-column>
          <el-table-column prop="openTime" label="开放时间"></el-table-column>
          <el-table-column prop="tags" label="标签"></el-table-column>
          <el-table-column prop="collectNum" label="收藏数" width="200"></el-table-column>
          <el-table-column prop="province" label="店铺地址" width="200">
            <template #default="scope">
              {{ scope.row.province }}{{ scope.row.city }}{{ scope.row.region }}{{ scope.row.address }}
            </template>
          </el-table-column>
          <el-table-column prop="intro" label="简介" width="200"></el-table-column>
          <el-table-column prop="img" label="店铺图片" width="200">
            <template #default="scope">
              <div v-if="scope.row.img">
                <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;"
                  v-for="(item, index) in scope.row.img.split(',')" :key="index">
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

          <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
          <el-table-column label="操作" width="100" fixed="right">
            <template #default="scope">
              <el-button size="small" type="primary" @click="dongtaiSpotBtn(scope.row)"
                style="margin: 5px;">动态列表</el-button>
              <el-button size="small" type="primary" @click="addChangeU(scope.row)" style="margin: 5px;">修改</el-button>
              <!-- <el-button size="small" type="primary" @click="sqChange(scope.row)" style="margin: 5px;">入群申请</el-button> -->
              <el-button size="small" type="danger" @click="shopDelete(scope.row)" style="margin: 5px;">删除</el-button>
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
      <el-tab-pane label="商户分类" name="second">
        <div style="margin: 0 20px;text-align: right;">
          <el-button size="small" type="primary" plain @click="flSearch()">刷新</el-button>
          <el-button size="small" type="warning" plain @click="addChangeFl()">添加</el-button>
        </div>
        <el-table v-loading="tableDataLoading" :data="fenleiData">
          <el-table-column fixed prop="id" label="编号" width="100"></el-table-column>
          <el-table-column prop="name" label="名称"></el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="160"></el-table-column>
          <el-table-column label="操作" width="160" fixed="right">
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
    <!-- 添加、修改 店铺弹框 -->
    <el-dialog :title="titles" v-model="dialogFormVisible4" center width="70%">
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">店铺类型 ：</span>
        <el-select v-model="typeId" class="m-2" placeholder="请选择店铺类型" style="width: 50%;">
          <el-option v-for="(item, index) in fenleiData" :key="index" :label="item.name" :value="item.id" />
        </el-select>
      </div>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">店铺名称：</span>
        <el-input style="width:50%;" v-model="name" type="text" placeholder="请输入店铺名称"></el-input>
      </div>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">联系人姓名：</span>
        <el-input style="width:50%;" v-model="contactName" type="text" placeholder="请输入联系人姓名"></el-input>
      </div>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">联系方式：</span>
        <el-input style="width:50%;" v-model="phone" type="text" placeholder="请输入联系方式"></el-input>
      </div>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">简介：</span>
        <el-input style="width:50%;" v-model="intro" type="text" placeholder="请输入简介"></el-input>
      </div>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">评分：</span>
        <el-input style="width:50%;" v-model="grade" type="text" placeholder="请输入评分"></el-input>
      </div>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">开放时间：</span>
        <el-input style="width:50%;" v-model="openTime" type="text" placeholder="请输入开放时间"></el-input>
      </div>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">标签：</span>
        <el-tag v-for="(item, index) in tags" :key="index" class="mx-1" closable :disable-transitions="false"
          @close="handleClose(index)" style="margin: 3px;">
          {{ item }}
        </el-tag>
        <el-input v-model="inputValue" class="ml-1 w-20" size="small" style="width: 150px;margin: 3px;" />
        <el-button class="button-new-tag ml-1" size="small" @click="handleInputConfirm()" style="margin: 3px;">
          + 添加
        </el-button>
      </div>
      <div style="margin-bottom: 10px;display: flex;align-items: center;">
        <span style="width: 200px;display: inline-block;text-align: right;">店铺图片：</span>
        <div style="margin-bottom: 10px;display:flex;flex-direction: row;flex-wrap: wrap;">
          <div class="imgs" v-for="(item, index) in img" :key="index">
            <img width="100%" class="images" height="100%" :src="item" alt="">
              <span class="dels">
                <el-icon style="font-size: 28px;color: #D9D9D9;text-align: center;width: 148px;" @click="clear2(index)">
                  <delete style="width: 1em; height: 1em; margin-right: 8px" />
                </el-icon>
              </span>
          </div>
          <div class="imgs"
            style=" width:148px;height:148px;border: 1px dashed #c0ccda;border-radius: 6px;text-align: center;line-height: 148px;color: #efefef;">
            <el-upload :action="$uploadUrl" :show-file-list="false"
              :on-success="handleUploadSuccess4" :on-progress="onprogress2">
              <el-progress v-if="percentage2 > 0 && percentage2 < 100" type="circle"
                :percentage="percentage2"></el-progress>
              <el-icon v-else style="font-size: 28px;color: #D9D9D9">
                <Plus />
              </el-icon>
            </el-upload>
          </div>
        </div>
      </div>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">省市区：</span>
        <el-cascader style="width:50%" size="large" :options="options" ref="cascaderAddr" v-model="storeAddress"
          :placeholder="storeAddre" @change="handleChange55" :value="storeAddre">
        </el-cascader>
      </div>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">详细地址：</span>
        <el-input style="width:50%;" v-model="address" type="text" placeholder="请输入详细地址"></el-input>
        <el-button type="info" size="small" plain @click="selectDz" style="margin-left: 10px;">获取经纬度</el-button>
      </div>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">纬度：</span>
        <el-input style="width:50%;" v-model="lat" type="text" placeholder="请输入纬度"></el-input>
      </div>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">经度：</span>
        <el-input style="width:50%;" v-model="lng" type="text" placeholder="请输入经度"></el-input>
      </div>
      <div>
        <div id="container1" style="width:80%;height:500px;margin-left: 10%;"></div>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogFormVisibleU = false" style="margin: 10px;">取消</el-button>
          <el-button type="primary" @click="StairNoticeTo" style="margin: 10px;">确认</el-button>
        </span>
      </template>
    </el-dialog>
    <!-- 评论列表 -->
    <!-- <el-dialog v-model="dialogFormVisiblePl" title="评论列表" width="80%" center>
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
    </el-dialog> -->
    <!--群成员 弹框 -->
    <el-dialog v-model="dialogFormVisibleCy" title="群成员列表" width="80%" center>
      <div style="margin: 10px;">
        <div style="display: inline-block;margin: 5px;">
          <span>用户昵称：</span>
          <el-input v-model="userNameC" placeholder="请输入用户昵称" style="width: 150px;" />
        </div>
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
    <el-dialog v-model="dialogFormVisibleUF" :title="titles" width="80%" center>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">名称：</span>
        <el-input style="width:50%;" v-model="name" type="text" placeholder="请输入名称"></el-input>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogFormVisibleUF = false" style="margin: 10px;">取消</el-button>
          <el-button type="primary" @click="StairNoticeToU" style="margin: 10px;">确认</el-button>
        </span>
      </template>
    </el-dialog>
    <!-- 动态列表 -->
    <el-dialog v-model="dialogFormVisibleDt" title="动态列表" width="70%" center>

      <div style="display: inline-block;">
        <div style="margin: 20px;">
          <div style="display: inline-block;margin: 5px;">
            <span>发布用户 :</span>
            <el-input v-model="userNameT" placeholder="请输入发布用户" style="width: 150px;" />
          </div>
          <div style="display: inline-block;margin: 5px;">
            <span>状态：</span>
            <el-select v-model="privacyT" class="m-2" placeholder="请选择状态" style="width: 150px;" @change="handleSearch">
              <el-option v-for="(item, index) in statusDataDt" :key="index" :label="item.label" :value="item.value" />
            </el-select>
          </div>
          <el-button size="small" type="primary" plain @click="handleSearchDt()">查询</el-button>
          <el-button size="small" type="warning" plain @click="clearSearchDt()">重置</el-button>
        </div>
      </div>
      <el-table :data="dongtaiData" v-loading="tableDataLoadingU" style="width: 100%">
        <el-table-column prop="id" label="编号" width="100" fixed="left"></el-table-column>
        <el-table-column prop="userName" label="发布用户" fixed="left" width="150">
          <template #default="scope">
            <span v-if="scope.row.userName" style="color: #13ce66;cursor: pointer;" @click="updates(scope.row.userId)">
              {{ scope.row.userName ? scope.row.userName : '未绑定' }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="avatar" label="头像" width="100">
          <template #default="scope">
            <img :src="scope.row.avatar" style="width: 60px;height: 60px;" />
          </template>
        </el-table-column>
        <el-table-column prop="title" label="标题" width="200"></el-table-column>
        <el-table-column prop="img" label="图片" width="300">
          <template #default="scope">
            <div v-if="scope.row.img">
              <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;"
                v-for="(item, index) in scope.row.img.split(',')" :key="index">
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
        <el-table-column prop="videoUrl" label="视频" width="300">
          <template #default="scope">
            <video v-if="scope.row.videoUrl" style="width: 180px;height: 100px;" :src="scope.row.videoUrl"
              type="audio/wav" controls="controls"></video>
          </template>
        </el-table-column>
        <el-table-column prop="content" label="文字描述">
          <template #default="scope">
            <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">

              <template #default>
                <div style="width: 400px;">{{ scope.row.content }}</div>
              </template>
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
        <el-table-column prop="likeNum" label="点赞次数"></el-table-column>
        <el-table-column prop="collectNum" label="收藏数"></el-table-column>
        <el-table-column prop="commentNum" label="评论数"></el-table-column>
        <el-table-column prop="auditStatus" label="状态" width="100" fixed="right">
          <template #default="scope">
            <span v-if="scope.row.auditStatus == 0">待审核</span>
            <span v-if="scope.row.auditStatus == 1">已通过</span>
            <span v-if="scope.row.auditStatus == 2">已拒绝</span>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
        <el-table-column label="操作" width="120" fixed="right">
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
            <pagination :pageSize="limit1" v-model:currentPage="page1" :total="total1"
              @pageChange="routeSelectJingdian()" />
          </div>
        </el-col>
      </el-row>
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
      <el-row class="el-rows">
        <el-col :span="24">
          <div class="topBtn flex align-center justify-end">
            <pagination :pageSize="limit2" v-model:currentPage="page2" :total="total2" @pageChange="dianzanSelect()" />
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
      <el-row class="el-rows">
        <el-col :span="24">
          <div class="topBtn flex align-center justify-end">
            <pagination :pageSize="limit2" v-model:currentPage="page2" :total="total2" @pageChange="pinglunSelect()" />
          </div>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script setup>
import {
  jsonp
} from 'vue-jsonp'
import {
  ref,
  reactive,
  getCurrentInstance,
  onMounted
} from 'vue';
import { useRouter } from 'vue-router';
import pagination from '@/components/pagination/pagination.vue'
import qrcode from 'qrcode';
import {
  ElMessage
} from 'element-plus';
import {
  pcaTextArr
} from 'element-china-area-data'
const {
  proxy
} = getCurrentInstance();
let geocoder = reactive([]);
let map = reactive([]);
let markersArray = reactive([]);

const router = useRouter();
const activeName = ref('first'); //默认选中
const qunzuData = reactive([]); //我的群组 集合
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
const loadingCy = ref(false);
const id = ref('');
const typeId = ref(''); //商户类型id
const name = ref(''); //商户名称
const intro = ref(''); //简介
const grade = ref(''); //评分
const openTime = ref(''); //开放时间
const province = ref(''); //省
const city = ref(''); //市
const region = ref(''); //区
const address = ref('');//详细地址
const lat = ref(''); //纬度
const lng = ref(''); //经度
const tags = reactive([]); //标签, 逗号分隔
const img = reactive([]); //图片，都好分隔
const phone = ref(''); //联系方式
const contactName = ref('');//联系人姓名
const inputValue = ref('');
const percentage2 = ref(0);

const userNameC = ref('');
const phoneC = ref('');
const typeC = ref('');
const contentC = ref('');
const qrCodeData = ref('');
const loadingerweima = ref(false);
const userNameT = ref('');
const inviteVerifyT = ref('');
const statusT = ref('');
const nameT = ref('');
const routeUrl = ref('');
const options = pcaTextArr;
const storeAddress = reactive([]);
const storeAddre = ref('请选择城市')

const dialogFormVisibleJd = ref(false);
const tableDataLoadingU = ref(false);
const userData = reactive([]);
const scenicSpotId = ref('');
const dialogFormVisibleDt = ref(false);
const dongtaiData = reactive([]);
const locationT = ref('');
const privacyT = ref('');
const scenicSpotNameT = ref('');
const dialogFormVisibleDz = ref(false);
const loading = ref(false);
const tableDataDz = reactive([]); //点赞 集合
const page2 = ref(1);//当前页
const limit2 = ref(10);//每页条数
const total2 = ref(0);//总条数

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
const dialogFormVisibleUF = ref(false);
const titles = ref('');


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
  page.value = 1
  if (event.srcElement.innerText == '商户列表') {
    qunzuSelect();
  }
  if (event.srcElement.innerText == '商户分类') {
    qunzufenleiSelect();
  }


}

// 群列表 列表
const qunzuSelect = () => {
  tableDataLoading.value = true
  qunzuData.splice(0, qunzuData.length)
  proxy.$Request.post('/admin/shop/page', {
    page: page.value,
    limit: limit.value,
    contactName: userNameT.value,
    typeName: inviteVerifyT.value,
    status: statusT.value,
    name: nameT.value,
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
  nameT.value = ''
  qunzuSelect()
}
// 添加、修改 商铺 弹框
const addChangeU = (row) => {
  if (row) {
    titles.value = '修改'
    id.value = row.id
    name.value = row.name
    typeId.value = row.typeId
    intro.value = row.intro
    grade.value = row.grade
    openTime.value = row.openTime
    province.value = row.province
    city.value = row.city
    region.value = row.region
    address.value = row.address
    lat.value = row.lat
    lng.value = row.lng
    phone.value = row.phone
    contactName.value = row.contactName
    if (row.img) {
      img.splice(0, img.length, ...row.img.split(','))
    } else {
      img.splice(0, img.length)
    }
    if (row.tags) {
      tags.splice(0, tags.length, ...row.tags.split(','))
    } else {
      tags.splice(0, tags.length)
    }
    getMyLocation()
  } else {
    titles.value = '添加'
    id.value = ''
    name.value = ''
    typeId.value = ''
    intro.value = ''
    grade.value = ''
    openTime.value = ''
    province.value = ''
    city.value = ''
    region.value = ''
    address.value = ''
    lat.value = ''
    lng.value = ''
    tags.splice(0, tags.length)
    img.splice(0, img.length)
    phone.value = ''
    contactName.value = ''

    let data = {
      key: "IUPBZ-YYY6Z-A7NXB-TXWBO-CZIPE-ICF2P", //申请的密钥
      output: "jsonp",
    };
    let url = "https://apis.map.qq.com/ws/location/v1/ip"
    jsonp(url, data).then(res => {
      console.log('获取Ip定位成功----')
      lng.value = res.result.location.lng
      lat.value = res.result.location.lat
      getMyLocation()
    }).catch(err => {
      console.log('获取Ip定位失败--', err.message)
    })

  }
  inputValue.value = ''
  percentage2.value = 0
  qunzufenleiSelect()

  dialogFormVisible4.value = true
}
// 确认
const StairNoticeTo = () => {
  if (typeId.value == '') {
    ElMessage({
      message: '店铺类型不能为空',
      type: 'warning',
      onClose: () => {
      }
    })
    return
  }
  if (name.value == '') {
    ElMessage({
      message: '店铺名称不能为空',
      type: 'warning',
      onClose: () => {
      }
    })
    return
  }
  if (contactName.value == '') {
    ElMessage({
      message: '联系人姓名不能为空',
      type: 'warning',
      onClose: () => {
      }
    })
    return
  }
  if (phone.value == '') {
    ElMessage({
      message: '联系方式不能为空',
      type: 'warning',
      onClose: () => {
      }
    })
    return
  }
  if (grade.value == '') {
    ElMessage({
      message: '评分不能为空',
      type: 'warning',
      onClose: () => {
      }
    })
    return
  }
  if (openTime.value == '') {
    ElMessage({
      message: '开放时间不能为空',
      type: 'warning',
      onClose: () => {
      }
    })
    return
  }
  if (tags.length == 0) {
    ElMessage({
      message: '标签不能为空',
      type: 'warning',
      onClose: () => {
      }
    })
    return
  }
  if (img.length == 0) {
    ElMessage({
      message: '店铺图片不能为空',
      type: 'warning',
      onClose: () => {
      }
    })
    return
  }
  if (province.value == '') {
    ElMessage({
      message: '省市区不能为空',
      type: 'warning',
      onClose: () => {
      }
    })
    return
  }
  if (address.value == '') {
    ElMessage({
      message: '详细地址不能为空',
      type: 'warning',
      onClose: () => {
      }
    })
    return
  }
  if (titles.value == '添加') {
    routeUrl.value = '/admin/shop/save'
  } else {
    routeUrl.value = '/admin/shop/update'
  }
  proxy.$Request.post(routeUrl.value, {
    id: id.value,
    name: name.value,
    typeId: typeId.value,
    intro: intro.value,
    grade: grade.value,
    openTime: openTime.value,
    province: province.value,
    city: city.value,
    region: region.value,
    address: address.value,
    lat: lat.value,
    lng: lng.value,
    tags: tags.toString(),
    img: img.toString(),
    phone: phone.value,
    contactName: contactName.value,

  }, {
    type: 'json'
  }).then((res) => {
    console.log(res)
    if (res.code == 0) {
      ElMessage({
        message: '操作成功',
        type: 'success',
        onClose: () => {

        }
      })
      dialogFormVisible4.value = false
      qunzuSelect()
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
// 删除 商铺
const shopDelete = (row) => {
  proxy.$Request.post('/admin/shop/delete', {
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
          qunzuSelect()
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
const handleClose = (index) => {
  tags.splice(index, 1);
}
const handleInputConfirm = () => {
  tags.push(inputValue.value)
  inputValue.value = ''
}
const clear2 = (index) => {
  img.splice(index, 1);
}
const handleUploadSuccess4 = (file) => {
  img.push(file.data.path)
}
const onprogress2 = (event,) => {
  console.log('event,', parseInt(event.percent))
  percentage2.value = parseInt(event.percent)
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

// 群分类 列表
const qunzufenleiSelect = () => {
  tableDataLoading.value = true
  fenleiData.splice(0, fenleiData.length)
  proxy.$Request.post('/admin/shopType/page', {
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

  } else {
    titles.value = '添加'
    id.value = ''
    name.value = ''

  }
  dialogFormVisibleUF.value = true
}
// 确认
const StairNoticeToU = () => {
  if (name.value == '') {
    ElMessage({
      message: '名称不能为空',
      type: 'warning',
      onClose: () => {
      }
    })
    return
  }
  if (titles.value == '添加') {
    routeUrl.value = '/admin/shopType/save'
  } else {
    routeUrl.value = '/admin/shopType/update'
  }
  proxy.$Request.post(routeUrl.value, {
    id: id.value,
    name: name.value,

  }, {
    type: 'json'
  }).then((res) => {
    console.log(res)
    if (res.code == 0) {
      ElMessage({
        message: '操作成功',
        type: 'success',
        onClose: () => {

        }
      })
      dialogFormVisibleUF.value = false
      qunzufenleiSelect()
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
  proxy.$Request.post('/admin/shopType/delete', {
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

//定位获得当前位置信息
const getMyLocation = () => {
  var geolocation = new qq.maps.Geolocation("IUPBZ-YYY6Z-A7NXB-TXWBO-CZIPE-ICF2P", "用户端");
  geolocation.getIpLocation(showPosition, showErr);
  // geolocation.getLocation(this.showPosition, this.showErr);//或者用getLocation精确度比较高
}
const showPosition = (position) => {
  console.log(position);
  setMap();
}
const showErr = (e) => {
  console.log("定位失败", e);
  getMyLocation(); //定位失败再请求定位，测试使用
}
//位置信息在地图上展示
const setMap = () => {
  //步骤：定义map变量 调用 qq.maps.Map() 构造函数   获取地图显示容器
  //设置地图中心点
  var myLatlng = new qq.maps.LatLng(lat.value, lng.value);
  //定义工厂模式函数
  var myOptions = {
    zoom: 13, //设置地图缩放级别
    center: myLatlng, //设置中心点样式
    mapTypeId: qq.maps.MapTypeId.ROADMAP //设置地图样式详情参见MapType
  }
  // //获取dom元素添加地图信息
  var map = new qq.maps.Map(document.getElementById("container1"), myOptions);
  //给地图添加点击事件

  //给定位的位置添加图片标注
  var marker = new qq.maps.Marker({
    position: myLatlng,
    map: map
  });
  // `````````````


  if (lat.value == '') {
    var center = new qq.maps.LatLng(34.34281541842994, 108.93970884382725);
  } else {
    var center = new qq.maps.LatLng(lat.value, lng.value);
  }
  var map = new qq.maps.Map(document.getElementById("container1"), {
    center: center,
    zoom: 13
  });
  var marker = new qq.maps.Marker({
    position: center,
    map: map
  });

  var latlngBounds = new qq.maps.LatLngBounds();
  qq.maps.event.addListener(map, "click", function (event) {
    console.log(event, qq.maps);
    lng.value = event.latLng.getLng(); // 经度
    lat.value = event.latLng.getLat(); // 纬度
    jsonp('https://apis.map.qq.com/ws/geocoder/v1/?location=' + event.latLng.getLat() + ',' + event
      .latLng
      .getLng() + '&key=IUPBZ-YYY6Z-A7NXB-TXWBO-CZIPE-ICF2P&get_poi=1&output=jsonp', {
      myCustomUrlParam: 'veryNice'
    }).then(response => {
      console.log('response', response)
      address.value = response.result.formatted_addresses.recommend
      province.value = response.result.ad_info.province
      city.value = response.result.ad_info.city
      region.value = response.result.ad_info.district

    }).catch(error => {
      // handle error
    }).then(() => {
      // always executed
    });
    if (markersArray) {
      for (let i in markersArray) {
        markersArray[i].setMap(null);
      }
    }
    var marker = new qq.maps.Marker({
      map: map,
      position: event.latLng
    });
    markersArray.push(marker);
  });
  geocoder = new qq.maps.Geocoder({
    complete: function (result) {
      console.log(result);
      lng.value = result.detail.location.lng;
      lat.value = result.detail.location.lat;
      map.setCenter(result.detail.location);
      var marker = new qq.maps.Marker({
        map: map,
        position: result.detail.location
      });
      markersArray.push(marker);
    }
  });
}
// 地图定位
const selectDz = () => {

  console.log('搜索地址：', province.value + city.value + region.value + address.value)
  var addr = province.value + city.value + region.value + address.value //

  jsonp('https://apis.map.qq.com/ws/geocoder/v1/?address==' + addr +
    '&key=IUPBZ-YYY6Z-A7NXB-TXWBO-CZIPE-ICF2P&get_poi=1&output=jsonp', {
    myCustomUrlParam: 'veryNice'
  }).then(response => {
    // handle success
    console.log('response', response)
    lng.value = response.result.location.lng; // 经度
    lat.value = response.result.location.lat; // 纬度
    setMap()
  }).catch(error => {
    // handle error
  }).then(() => {
    // always executed
  });
}
const handleChange55 = (value) => {
  console.log('=============', value)

  // value = ref['cascaderAddr'].currentLabels
  province.value = value[0]
  city.value = value[1]
  region.value = value[2]
  if (city.value == '市辖区') {
    city.value = province.value
  }
}
// 动态列表 弹框
const dongtaiSpotBtn = (row) => {
  scenicSpotId.value = row.id
  page1.value = 1
  dialogFormVisibleDt.value = true
  dongtaiSelect();
}
// 我的动态 列表
const dongtaiSelect = () => {
  tableDataLoadingU.value = true
  dongtaiData.splice(0, dongtaiData.length)
  proxy.$Request.post('admin/moments/page', {
    // params: {
    page: page1.value,
    limit: limit.value,
    userName: userNameT.value,
    location: '',
    auditStatus: privacyT.value,
    shopId: scenicSpotId.value,

    // }
  }, {
    type: 'json'
  }).then((res) => {
    console.log(res)
    tableDataLoadingU.value = false;
    dongtaiData.splice(0, dongtaiData.length, ...res.data.records)
    total.value = res.data.total;
  })
}
const handleSearchDt = () => {
  page.value = 1
  dongtaiSelect()
}
const clearSearchDt = () => {
  userNameT.value = ''
  locationT.value = ''
  privacyT.value = ''
  scenicSpotNameT.value = ''
  page1.value = 1
  dongtaiSelect()
}
/** 点赞用户 弹框 */
const dianzanChange = (row) => {
  id.value = row.id
  dianzanSelect()
  dialogFormVisibleDz.value = true

}
const dianzanSelect = () => {
  loading.value = true
  tableDataDz.splice(0, tableDataDz.length)
  proxy.$Request.post('admin/like/momentLikePage', {
    // params: {
    page: page2.value,
    limit: limit2.value,
    momentId: id.value,

    // }
  }, {
    type: 'json'
  }).then((res) => {
    console.log(res)
    loading.value = false;
    tableDataDz.splice(0, tableDataDz.length, ...res.data.records)
    total2.value = res.data.total;
  })
}
/** 评论 弹框 */
const pinglunChange = (row) => {
  id.value = row.id

  pinglunSelect();
  dialogFormVisiblePl.value = true
}
const pinglunSelect = () => {
  loading.value = true
  tableDataPl.splice(0, tableDataPl.length)
  proxy.$Request.post('admin/comment/momentCommentPage', {
    // params: {
    page: page2.value,
    limit: limit2.value,
    momentId: id.value,

    // }
  }, {
    type: 'json'
  }).then((res) => {
    console.log(res)
    loading.value = false;
    tableDataPl.splice(0, tableDataPl.length, ...res.data.records)
    total2.value = res.data.total;
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

.imgs {
  position: relative;
  border-radius: 6px;
  width: 148px;
  height: 148px;
  margin-right: 10px;
  display: inline-block;
}

.dels {
  position: absolute;
  top: 0;
  left: 0;
  display: none;
}

.dels .el-icon-delete {
  line-height: 148px;
  padding-left: 58px;
  font-size: 25px;
  color: #fff;

}

.imgs:hover .dels {
  width: 100%;
  height: 100%;
  background: #000;
  display: block;
  opacity: 0.5;
  line-height: 148px;
}

span {
  font-size: 14px;
  color: #303133;
}
</style>
