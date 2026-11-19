<div align="center">

# **省钱兄 JAVA 旅游攻略全套开源系统（JAVA+Uni-app+Vue3）**

**服务端 · 管理端 · 用户端**

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
[![JDK](https://img.shields.io/badge/JDK-1.8-orange.svg)](docs/服务端-运行教程.md)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.6.11-6DB33F.svg)](docs/服务端-运行教程.md)
[![Vue](https://img.shields.io/badge/Vue-3-42b883.svg)](docs/管理端-运行教程.md)
[![uni-app](https://img.shields.io/badge/uni--app-用户端-2b9939.svg)](docs/用户端-运行教程.md)

八年沉淀，分批次开源由 AI 辅助开发的系统源码。  
开源为生态，服务才是根基。欢迎各位开发者一同交流。

</div>

---
⭐ 如果觉得项目不错，请点击右上角 Star 收藏支持！您的 Star 是我们持续开源迭代的最大动力！

## **1. 项目简介**

**省钱兄旅游攻略全套开源系统**是一套围绕目的地、景点、路线、攻略群与社交互动的旅游攻略业务系统，采用前后端分离架构，服务端、管理端、用户端全套代码开源。

服务端基于 **Spring Boot 2.6.11**（**Java 8**）构建，负责接口、数据库，以及支付、短信、地图等外部能力。  
管理端基于 **Vue 3 + Vite** 开发，供运营人员在电脑浏览器中使用。  
用户端基于 **uni-app** 开发，覆盖手机网页和微信小程序，应用名称是「旅游攻略」。

本仓库为服务端、管理端、用户端全套源码。

- **官方网站开源计划**：[https://www.xiansqx.com](https://www.xiansqx.com)
- **开源计划**：[https://opensource.xiansqx.cn](https://opensource.xiansqx.cn)
- **官方文档**：[功能说明与运行教程](docs/服务端-运行教程.md)
- **Gitee 仓库**：[https://gitee.com/maxiaodong1996/sqx-lvyougonglue.git](https://gitee.com/maxiaodong1996/sqx-lvyougonglue.git)
- **GitHub 仓库**：[https://github.com/yuyang7816/sqx-lvyougonglue.git](https://github.com/yuyang7816/sqx-lvyougonglue.git)

## **2. 用户端能力概览**

底部四个主入口：**首页、攻略群、消息、我的**。

| 模块 | 能做什么 |
|---|---|
| 首页 | 目的地、景点、精选路线、演唱会、玩乐租赁、搜索与内容浏览 |
| 攻略群 | 加入 / 创建群聊，按攻略主题交流 |
| 消息 | 站内消息、官方公告、会话聊天 |
| 我的 | 资料、钱包提现、邀请团队、收藏关注、动态、设置与帮助 |

## **3. 开源说明**

> **【省钱兄科技｜开源计划启动】**
>
> 八年沉淀，我们将分批次开源全套由 AI 辅助开发的系统源码。  
> 本项目采用 **Apache 2.0** 许可协议，源码供学习、技术研究、商业使用、二次开发与参考使用。  
> **开源为生态，服务才是根基；** 欢迎各位开发者一同交流。

## **4. 快速开始**

建议顺序：**先服务端，再管理端，最后用户端。** 管理端和用户端都要等服务端已经启动，才能登录和加载数据。

### **4.1 服务端**

安装 JDK 8、Maven、MySQL 8。可选安装 Redis（当前配置默认关闭）。导入 [`server/db/lvyougonglve.sql`](server/db/lvyougonglve.sql)（演示库，无隐私数据），再在 `server` 目录启动服务端。  
详细步骤见 [服务端运行教程](docs/服务端-运行教程.md)。

### **4.2 管理端**

安装 Node.js 20 或更高版本，在 `lvyou-admin(旅游1.0-管理端)` 里执行安装并启动。  
详细步骤见 [管理端运行教程](docs/管理端-运行教程.md)。

### **4.3 用户端**

用 HBuilderX 打开 `lvyou-uniapp`，运行到浏览器。  
详细步骤见 [用户端运行教程](docs/用户端-运行教程.md)。

后台演示账号一般为：**admin** / **123456**（以导入库中数据为准）。  
地图、短信、微信、支付宝等密钥需要自行申请。申请入口写在 [用户端功能说明](docs/用户端-功能说明.md) 末尾。

## **5. 文档**

| 想做的事 | 打开哪一份 |
|---|---|
| 把服务端跑起来 | [服务端运行教程](docs/服务端-运行教程.md) |
| 看服务端能做什么 | [服务端功能说明](docs/服务端-功能说明.md) |
| 把管理端跑起来 | [管理端运行教程](docs/管理端-运行教程.md) |
| 看管理端能做什么 | [管理端功能说明](docs/管理端-功能说明.md) |
| 把用户端跑起来 | [用户端运行教程](docs/用户端-运行教程.md) |
| 看用户端能做什么 | [用户端功能说明](docs/用户端-功能说明.md) |

## **6. 目录结构**

仓库根目录放三端代码、说明和协议。编译出来的 `target`、`dist`、`unpackage`、`node_modules` 是运行时生成的，不需要改。

### **6.1 仓库根目录**

| 路径 | 是什么 |
|---|---|
| `server` | **服务端**。接口、支付、短信、文件上传等能力都在这里；演示库在 `server/db/lvyougonglve.sql` |
| `lvyou-admin(旅游1.0-管理端)` | **管理端**。运营人员在电脑浏览器里打开的后台 |
| `lvyou-uniapp` | **用户端**。游客用的手机应用，也能在电脑浏览器里看 |
| `docs` | 三端的功能说明和运行教程 |
| `LICENSE` | Apache License 2.0 全文 |

### **6.2 服务端 `server`**

| 路径 | 是什么 |
|---|---|
| `sqx-application` | 启动入口。主程序在 `SqxApplication.java`，数据库和 Redis 配置在 `src/main/resources` |
| `sqx-modules/sqx-app` | 旅游攻略业务代码。目的地、景点、路线、演唱会、群聊、动态、店铺、订单相关能力在这里 |
| `sqx-modules/sqx-system` | 系统模块。管理员、角色、菜单等 |
| `sqx-modules/sqx-generator` | 按数据表生成代码的工具，日常运行用不到 |
| `sqx-domain` | 数据持久相关模块 |
| `sqx-common/sqx-common-web` | 网页请求、统一异常提示 |
| `sqx-common/sqx-common-mybatis` | 访问 MySQL |
| `sqx-common/sqx-common-redis` | 访问 Redis |
| `sqx-common/sqx-common-shiro` | 管理端登录和权限 |
| `sqx-common/sqx-common-pay` | 微信支付、支付宝 |
| `sqx-common/sqx-common-sms` | 短信能力 |
| `sqx-common/sqx-common-mail` | 发邮件 |
| `sqx-common/sqx-common-file` | 上传图片 |
| `sqx-common/sqx-common-doc` | 接口文档页面（Knife4j） |
| `sqx-common/sqx-common-websocket` | 实时消息 |
| `sqx-common/sqx-common-core` | 公共工具 |

### **6.3 管理端 `lvyou-admin(旅游1.0-管理端)`**

| 路径 | 是什么 |
|---|---|
| `src/pages` | 业务与系统页面。用户、订单、目的地、景点、动态、店铺、轮播、财务、VIP、角色菜单等都在这里 |
| `src/pages/Login` | 登录页 |
| `src/pages/Dashboard` | 登录后的数据统计 |
| `src/router` | 页面地址和菜单对应关系 |
| `src/stores` | 登录状态等临时数据 |
| `src/layouts` | 左侧菜单和顶部栏 |
| `src/components` | 分页等共用组件 |
| `comment/httpUrl.js` | 服务端接口地址 |
| `comment/api.js` | Axios 请求封装 |
| `vite.config.js` | 开发端口等 Vite 配置 |

### **6.4 用户端 `lvyou-uniapp`**

| 路径 | 是什么 |
|---|---|
| `pages` | 底部四个主入口：首页、攻略群、消息、我的，以及登录注册 |
| `my` | 分包页面。演唱会、路线、景点、租赁、钱包、团队、动态、设置、收藏关注等 |
| `common` | 接口地址、公共逻辑 |
| `components` | 页面里重复使用的组件 |
| `static` | 图标、默认图片 |
| `uni_modules` | 界面组件库，其中 **uView Plus** 负责按钮、输入框、轮播 |
| `pages.json` | 有哪些页面、底部导航怎么排 |
| `manifest.json` | 应用名称、微信小程序 AppId 等 |

## **7. 核心特性与技术栈**

### **7.1 核心特性**

· **目的地、景点、精选路线一站浏览**  
· **演唱会、玩乐租赁等内容扩展**  
· **攻略群聊、站内消息与官方公告**  
· **动态社交：发布、点赞、评论、关注、收藏**  
· **钱包提现、邀请分销与团队收益**  
· **后台统计、内容运营、订单与权限配置**  
· **服务端统一处理登录、订单和支付结果**

每项怎么用，见 [用户端功能说明](docs/用户端-功能说明.md)、[管理端功能说明](docs/管理端-功能说明.md)、[服务端功能说明](docs/服务端-功能说明.md)。

版本以仓库里的配置文件为准。没写死版本号的，按运行教程安装对应大版本即可。

### **7.2 服务端**

| 类别 | 技术 | 版本 | 用来做什么 |
|---|---|---|---|
| 语言 | **Java** | **1.8** | 整个服务端。不能换成 11 或 17 |
| 框架 | **Spring Boot** | **2.6.11** | 提供网页接口，端口 `9097`，路径 `/sqx_fast` |
| 构建 | **Maven** | 3.6 及以上 | 下载依赖、编译、启动 |
| 数据库 | **MySQL** | 8.0 及以上 | 业务数据。库名 `lvyougonglve` |
| 数据库访问 | **MyBatis-Plus** | 3.5.3.2 | 读写表 |
| 连接池 | **Druid** | 1.2.19 | 管理数据库连接 |
| 缓存 | **Redis**、**Redisson** | Redis 可选，Redisson 3.18.0 | 当前默认关闭；打开后用于登录状态等 |
| 管理端登录 | **Apache Shiro** | 1.4.0 | 后台账号、角色、菜单权限 |
| 用户端登录 | **JWT**（jjwt） | 0.9.1 | 用户登录后的凭证 |
| 接口文档 | **Knife4j** | 4.4.0 | 浏览器打开 `doc.html` 查看接口 |
| JSON | **Fastjson2** | 2.0.41 | 接口数据格式 |
| 工具库 | **Hutool** | 5.8.16 | 日期、字符串等常用处理 |
| 对象存储 | **阿里云 OSS SDK** | 3.4.0 | 可选。密钥要自己申请 |
| 支付 | 微信支付 SDK、支付宝 SDK | 随支付模块 | 下单、退款。密钥要自己申请 |

### **7.3 管理端**

| 类别 | 技术 | 版本 | 用来做什么 |
|---|---|---|---|
| 运行环境 | **Node.js** | 20 及以上（见 `package.json` engines） | 安装依赖、启动开发网站 |
| 页面框架 | **Vue** | 3.5 | 后台每一个页面 |
| 构建 | **Vite** | 7.x | 开发时热更新，默认端口 `8888` |
| 界面组件 | **Element Plus** | 2.11 | 表格、表单、弹窗、菜单 |
| 页面跳转 | **Vue Router** | 4.5 | 登录页、统计页和各业务页的地址 |
| 状态 | **Pinia** | 3.0 | 记住是否已登录 |
| 请求 | **Axios** | 1.13 | 把操作发给服务端 |
| 图表 | **ECharts** | 6.x | 数据统计页上的图 |
| 样式 | **Sass** | 1.92 | 后台样式 |

### **7.4 用户端**

| 类别 | 技术 | 版本 | 用来做什么 |
|---|---|---|---|
| 框架 | **uni-app** | 用 HBuilderX 打开 | 同一套页面可运行到浏览器、微信小程序 |
| 开发工具 | **HBuilderX** | 正式版 | 编译并运行到 Chrome 或微信开发者工具 |
| 界面组件 | **uView Plus** | 随 `uni_modules` | 按钮、输入框、轮播、弹窗 |
| 状态 | **Pinia** | 3.0 | 用户端临时状态 |
| 微信小程序 | 微信开发者工具 | 可选 | 要自己的小程序 AppId 才能真机预览 |

## **8. 开源协议**

<div align="center">

本仓库使用 **Apache License 2.0**

全文见 [LICENSE](LICENSE)

</div>

## **9. 社区与支持**

运行、二次开发或商务合作，可以扫码联系 **省钱兄科技**。

使用过程中如果接口起不来、页面打不开，先按本文 **4. 快速开始** 和 `docs` 里的运行教程排查。地图、短信、微信、支付宝等密钥需要自行申请，申请入口在 [用户端功能说明](docs/用户端-功能说明.md) 末尾。仍解决不了时，再扫下面的码说明你的环境和报错。

当前项目正在测试中，如遇到 BUG，可自行修复或者留言反馈，我们将第一时间进行版本迭代。  
欢迎一起交流学习。开源是把源码开放出来，具体落地、定制和长期维护仍由服务来承接。

<div align="center">

<img src="docs/images/community-qr.jpg" alt="省钱兄科技二维码" width="220" />

**省钱兄科技**

</div>
