<template>
  <div class="menu">
    <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
      <el-tab-pane label="演唱会列表" name="first">
        <div>
          <div style="display: inline-block;margin: 5px;">
            <span>演出标题 ：</span>
            <!-- @keydown.enter.native="getUserList" -->
            <el-input v-model="titleT" placeholder="请输入演出标题" style="width: 150px;" />
          </div>&nbsp;&nbsp;
          <!-- <div style="display: inline-block;margin: 5px;">
            <span>景点位置 ：</span>
            <el-input v-model="locationT" placeholder="请输入景点位置" style="width: 150px;" />
          </div>&nbsp;&nbsp; -->
          <el-button type="primary" size="small" plain @click="handleSearch">查询</el-button>
          <el-button type="primary" size="small" @click="clearBtn">重置</el-button>
          <el-button size="small" type="warning" plain @click="addChangeU()">添加</el-button>
        </div>
        <el-table v-loading="tableDataLoading" :data="liaotianData">
          <el-table-column prop="id" label="编号" width="100" fixed="left"></el-table-column>
          <el-table-column prop="title" label="演出标题"></el-table-column>
          <el-table-column prop="img" label="图片">
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
          <el-table-column prop="pic" label="背景图">
            <template #default="scope">
              <div v-if="scope.row.pic">
                <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;"
                  v-for="(item, index) in scope.row.pic.split(',')" :key="index">
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
          <el-table-column prop="concertTime" label="演出时间"></el-table-column>
          <el-table-column prop="location" label="省市区">
            <template #default="scope">
              {{ scope.row.province }}{{ scope.row.city }}{{ scope.row.district }}
            </template>
          </el-table-column>
          <el-table-column prop="location" label="演出位置"></el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="160"></el-table-column>
          <el-table-column label="操作" width="100" fixed="right">
            <template #default="scope">
              <el-button size="small" type="primary" @click="addChangeU(scope.row)" style="margin: 5px;">修改</el-button>
              <el-button size="small" type="danger" @click="handleDelete(scope.row)" style="margin: 5px;">删除</el-button>
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
        <span style="width: 200px;display: inline-block;text-align: right;">演出标题 ：</span>
        <el-input style="width:50%;" v-model="title" type="text" placeholder="请输入名称"></el-input>
      </div>
      <div style="margin-bottom: 10px;display: flex;align-items: center;">
        <span style="width: 200px;display: inline-block;text-align: right;">图片 ：</span>
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
      <div style="margin-bottom: 10px;display: flex;align-items: center;">
        <span style="width: 200px;display: inline-block;text-align: right;">背景图 ：</span>
        <div style="margin-bottom: 10px;display:flex;flex-direction: row;flex-wrap: wrap;">
          <div class="imgs" v-for="(item, index) in pic" :key="index">
            <img width="100%" class="images" height="100%" :src="item" alt="">
              <span class="dels">
                <el-icon style="font-size: 28px;color: #D9D9D9;text-align: center;width: 148px;" @click="clear5(index)">
                  <delete style="width: 1em; height: 1em; margin-right: 8px" />
                </el-icon>
              </span>
          </div>
          <div class="imgs" v-if="pic.length == 0"
            style=" width:148px;height:148px;border: 1px dashed #c0ccda;border-radius: 6px;text-align: center;line-height: 148px;color: #efefef;">
            <el-upload :action="$uploadUrl" :show-file-list="false"
              :on-success="handleUploadSuccess5" :on-progress="onprogress2">
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
        <span style="width: 200px;display: inline-block;text-align: right;">演出时间 ：</span>
        <el-date-picker v-model="concertTime" type="datetime" format="YYYY-MM-DD HH:mm:ss"
          value-format="YYYY-MM-DD HH:mm:ss" time-format="HH:mm:ss" placeholder="选择演出时间" style="width:50%;" />
      </div>

      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">省市区：</span>
        <el-cascader style="width:50%" size="large" :options="options" ref="cascaderAddr" v-model="storeAddress"
          :placeholder="storeAddre" @change="handleChange55" :value="storeAddre">
        </el-cascader>
      </div>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">演出位置 ：</span>
        <el-input style="width:50%;" v-model="location" type="text" placeholder="请输入演出位置"></el-input>
        <!-- <el-button type="info" size="small" plain @click="selectDz" style="margin-left: 10px;">获取经纬度</el-button> -->
      </div>
      <!-- <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">纬度：</span>
        <el-input style="width:50%;" v-model="lat" type="text" placeholder="请输入纬度"></el-input>
      </div>
      <div style="margin-bottom: 10px;">
        <span style="width: 200px;display: inline-block;text-align: right;">经度：</span>
        <el-input style="width:50%;" v-model="lng" type="text" placeholder="请输入经度"></el-input>
      </div>
      <div>
        <div id="container1" style="width:80%;height:500px;margin-left: 10%;"></div>
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
import pagination from '@/components/pagination/pagination.vue'
import {
  ElMessage,
  ElMessageBox
} from 'element-plus';
const {
  proxy
} = getCurrentInstance();
import {
  jsonp
} from 'vue-jsonp'
import {
  pcaTextArr
} from 'element-china-area-data'
let geocoder = reactive([]);
let map = reactive([]);
let markersArray = reactive([]);

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
const title = ref('');//演出标题
const img = reactive([]); //图片
const pic = reactive([]); //背景图
const concertTime = ref('');//演出时间
const location = ref('');//演出位置
const percentage2 = ref(0);

const province = ref(''); //省
const city = ref(''); //市
const region = ref(''); //区
const address = ref('');//详细地址
const lat = ref(''); //纬度
const lng = ref(''); //经度
const options = pcaTextArr;
const storeAddress = reactive([]);
const storeAddre = ref('请选择城市')
const titleT = ref('');

onMounted(() => {
  liaotianSelect();
});

// 切换tab 事件
const handleClick = (tab, event) => {
  if (event.srcElement.innerText == '演唱会列表') {
    classify.value = 1
    liaotianSelect();
  }
}

//分类数据 列表
const liaotianSelect = () => {
  tableDataLoading.value = true
  liaotianData.splice(0, liaotianData.length)
  proxy.$Request.post('/admin/concert/page', {
    // params: {
    pageNum: page.value,
    pageSize: limit.value,
    memberName: titleT.value,
    // }
  }, {
    type: 'json'
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
// 重置
const clearBtn = () => {
  titleT.value = ''
  page.value = 1
  liaotianSelect()
}
// 添加/修改 分类 弹框
const addChangeU = (row) => {
  if (row) {
    titles.value = '修改'
    id.value = row.id
    title.value = row.title
    if (row.img) {
      img.splice(0, img.length, ...row.img.split(','))
    } else {
      img.splice(0, img.length)
    }
    if (row.pic) {
      pic.splice(0, pic.length, ...row.pic.split(','))
    } else {
      pic.splice(0, pic.length)
    }
    concertTime.value = row.concertTime
    location.value = row.location
    province.value = row.province
    city.value = row.city
    region.value = row.district
    // lat.value = row.lat
    // lng.value = row.lng
    storeAddress.splice(0, storeAddress.length)
    storeAddre.value = province.value + '/' + city.value + '/' + region.value
    // getMyLocation()
  } else {
    titles.value = '添加'
    id.value = ''
    title.value = ''
    concertTime.value = ''
    location.value = ''
    img.splice(0, img.length)
    pic.splice(0, pic.length)
    province.value = ''
    city.value = ''
    region.value = ''
    address.value = ''
    // lat.value = ''
    // lng.value = ''
    storeAddress.splice(0, storeAddress.length)
    storeAddre.value = '请选择城市'
    // let data = {
    //   key: "IUPBZ-YYY6Z-A7NXB-TXWBO-CZIPE-ICF2P", //申请的密钥
    //   output: "jsonp",
    // };
    // let url = "https://apis.map.qq.com/ws/location/v1/ip"
    // jsonp(url, data).then(res => {
    //   console.log('获取Ip定位成功----')
    //   lng.value = res.result.location.lng
    //   lat.value = res.result.location.lat
    //   getMyLocation()
    // }).catch(err => {
    //   console.log('获取Ip定位失败--', err.message)
    // })
  }
  percentage2.value = 0
  dialogFormVisibleU.value = true
}

const StairNoticeToU = () => {
  if (title.value == '') {
    ElMessage({
      message: '演出标题不能为空',
      type: 'warning',
      onClose: () => {
      }
    })
    return
  }
  if (concertTime.value == '') {
    ElMessage({
      message: '演出时间不能为空',
      type: 'warning',
      onClose: () => {
      }
    })
    return
  }
  if (img.length == 0) {
    ElMessage({
      message: '图片不能为空',
      type: 'warning',
      onClose: () => {
      }
    })
    return
  }
  if (pic.length == 0) {
    ElMessage({
      message: '背景图不能为空',
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
  if (location.value == '') {
    ElMessage({
      message: '演出位置不能为空',
      type: 'warning',
      onClose: () => {
      }
    })
    return
  }
  if (titles.value == '添加') {
    routeUrl.value = '/admin/concert/save'
  } else {
    routeUrl.value = '/admin/concert/update'
  }
  proxy.$Request.post(routeUrl.value, {
    id: id.value,
    title: title.value,
    img: img.toString(),
    pic: pic.toString(),
    concertTime: concertTime.value,
    location: location.value,
    province: province.value,
    city: city.value,
    district: region.value,
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
      proxy.$Request.post('/admin/concert/delete', {
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
    })

}

function handleAvatarSuccess1(file) {
  img.value = file.data.path
}
const clear2 = (index) => {
  img.splice(index, 1);
}
const handleUploadSuccess4 = (file) => {
  img.push(file.data.path)
}
const handleUploadSuccess5 = (file) => {
  pic.push(file.data.path)
}
const clear5 = (index) => {
  pic.splice(index, 1);
}
const onprogress2 = (event,) => {
  console.log('event,', parseInt(event.percent))
  percentage2.value = parseInt(event.percent)
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
      location.value = response.result.formatted_addresses.recommend
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

  console.log('搜索地址：', province.value + city.value + region.value + location.value)
  var addr = province.value + city.value + region.value + location.value //

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
