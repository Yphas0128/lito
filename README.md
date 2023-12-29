## 本分支为精简版
* 移除了完整版的 工作流、支付、报表、测试等模块，只保留了系统管理、基础设施

## 🐶 新手必读

* 演示地址：<http://dashboard.olto.iocoder.cn>
* 启动文档：<https://doc.iocoder.cn/quick-start/>
* 视频教程：<https://doc.iocoder.cn/video/>

## 🐯 平台简介

**芋道**，以开发者为中心，打造中国第一流的快速开发平台，全部开源，个人与企业可 100% 免费使用。

> 有任何问题，或者想要的功能，可以在 _Issues_ 中提给艿艿。
>
> 😜 给项目点点 Star 吧，这对我们真的很重要！

![架构图](https://static.iocoder.cn/ruoyi-vue-pro-architecture.png)

* 管理后台的 Vue3 版本采用 [vue-element-plus-admin](https://gitee.com/kailong110120130/vue-element-plus-admin) ，Vue2 版本采用 [vue-element-admin](https://github.com/PanJiaChen/vue-element-admin) 
* 管理后台的移动端采用 [uni-app](https://github.com/dcloudio/uni-app) 方案，一份代码多终端适配，同时支持 APP、小程序、H5！
* 后端采用 Spring Boot 多模块架构、MySQL + MyBatis Plus、Redis + Redisson
* 数据库可使用 MySQL、Oracle、PostgreSQL、SQL Server、MariaDB、国产达梦 DM、TiDB 等
* 权限认证使用 Spring Security & Token & Redis，支持多终端、多种用户的认证系统，支持 SSO 单点登录
* 支持加载动态权限菜单，按钮级别权限控制，本地缓存提升性能
* 支持 SaaS 多租户系统，可自定义每个租户的权限，提供透明化的多租户底层封装
* 高效率开发，使用代码生成器可以一键生成前后端代码 + 单元测试 + Swagger 接口文档 + Validator 参数校验
* 集成微信小程序、微信公众号、企业微信、钉钉等三方登陆
* 集成阿里云、腾讯云等短信渠道，集成 MinIO、阿里云、腾讯云、七牛云等云存储服务

| 项目名                  | 说明                     | 传送门                                                                                                                                 |
|----------------------|------------------------|-------------------------------------------------------------------------------------------------------------------------------------|
| `ruoyi-vue-pro`      | Spring Boot 多模块        | **[Gitee](https://gitee.com/zhijiantianya/ruoyi-vue-pro)** &nbsp;&nbsp;&nbsp; [Github](https://github.com/YunaiV/ruoyi-vue-pro)     |
| `olto-cloud`        | Spring Cloud 微服务       | **[Gitee](https://gitee.com/zhijiantianya/olto-cloud)** &nbsp;&nbsp;&nbsp; [Github](https://github.com/YunaiV/olto-cloud)         |
| `Spring-Boot-Labs`   | Spring Boot & Cloud 入门 | **[Gitee](https://gitee.com/zhijiantianya/SpringBoot-Labs)** &nbsp;&nbsp;&nbsp; [Github](https://github.com/YunaiV/SpringBoot-Labs) |

## 🐰 分支说明

|       | JDK 8 完整版                                                 | JDK 8 精简版                                                          | JDK 17 完整版                                                                  |
|-------|-----------------------------------------------------------|--------------------------------------------------------------------|-----------------------------------------------------------------------------|
| 分支    | [`master`](https://gitee.com/zhijiantianya/ruoyi-vue-pro) | [`mini`](https://gitee.com/zhijiantianya/ruoyi-vue-pro/tree/mini/) | [`boot-dev`](https://gitee.com/zhijiantianya/ruoyi-vue-pro/tree/boot3-dev/) |
| 说明    | 包括所有功能                                                    | 只保留核心功能                                                            | 适配 Spring Boot 3.X                                                          |
| 系统功能  | √                                                         | √                                                                  | √                                                                           |
| 基础设施  | √                                                         | √                                                                  | √                                                                           |
| 会员中心  | √                                                         | √                                                                  | √                                                                           |
| 工作流程  | √                                                         | x                                                                  | 适配中                                                                         |
| 数据报表  | √                                                         | x                                                                  | 适配中                                                                         |
| 商城系统  | √                                                         | x                                                                  | √                                                                           |
| 微信公众号 | √                                                         | x                                                                  | √                                                                           |

## 😎 开源协议

**为什么推荐使用本项目？**

① 本项目采用比 Apache 2.0 更宽松的 [MIT License](https://gitee.com/zhijiantianya/ruoyi-vue-pro/blob/master/LICENSE) 开源协议，个人与企业可 100% 免费使用，不用保留类作者、Copyright 信息。

② 代码全部开源，不会像其他项目一样，只开源部分代码，让你无法了解整个项目的架构设计。[国产开源项目对比](https://www.yuque.com/xiatian-bsgny/lm0ec1/wqf8mn)

![开源项目对比](https://static.iocoder.cn/project-vs.png?imageView2/2/format/webp/w/1280)

③ 代码整洁、架构整洁，遵循《阿里巴巴 Java 开发手册》规范，代码注释详细，57000 行 Java 代码，22000 行代码注释。

## 🤝 项目外包

我们也是接外包滴，如果你有项目想要外包，可以微信联系【**Aix9975**】。

团队包含专业的项目经理、架构师、前端工程师、后端工程师、测试工程师、运维工程师，可以提供全流程的外包服务。

项目可以是商城、SCRM 系统、OA 系统、物流系统、ERP 系统、CMS 系统、HIS 系统、支付系统、IM 聊天、微信公众号、微信小程序等等。

## 🐼 内置功能

系统内置多种多种业务功能，可以用于快速你的业务系统：

![功能分层](https://static.iocoder.cn/ruoyi-vue-pro-biz.png)

* 系统功能
* 基础设施
* 工作流程
* 支付系统
* 会员中心
* 数据报表
* 商城系统
* 微信公众号

> 友情提示：本项目基于 RuoYi-Vue 修改，**重构优化**后端的代码，**美化**前端的界面。
>
> * 额外新增的功能，我们使用 🚀 标记。
> * 重新实现的功能，我们使用 ⭐️ 标记。

🙂 所有功能，都通过 **单元测试** 保证高质量。

### 系统功能

|     | 功能    | 描述                              |
|-----|-------|---------------------------------|
|     | 用户管理  | 用户是系统操作者，该功能主要完成系统用户配置          |
| ⭐️  | 在线用户  | 当前系统中活跃用户状态监控，支持手动踢下线           |
|     | 角色管理  | 角色菜单权限分配、设置角色按机构进行数据范围权限划分      |
|     | 菜单管理  | 配置系统菜单、操作权限、按钮权限标识等，本地缓存提供性能    |
|     | 部门管理  | 配置系统组织机构（公司、部门、小组），树结构展现支持数据权限  |
|     | 岗位管理  | 配置系统用户所属担任职务                    |
| 🚀  | 租户管理  | 配置系统租户，支持 SaaS 场景下的多租户功能        |
| 🚀  | 租户套餐  | 配置租户套餐，自定每个租户的菜单、操作、按钮的权限       |
|     | 字典管理  | 对系统中经常使用的一些较为固定的数据进行维护          |
| 🚀  | 短信管理  | 短信渠道、短息模板、短信日志，对接阿里云、腾讯云等主流短信平台 |
| 🚀  | 邮件管理  | 邮箱账号、邮件模版、邮件发送日志，支持所有邮件平台       |
| 🚀  | 站内信   | 系统内的消息通知，提供站内信模版、站内信消息          |
| 🚀  | 操作日志  | 系统正常操作日志记录和查询，集成 Swagger 生成日志内容 |
| ⭐️  | 登录日志  | 系统登录日志记录查询，包含登录异常               |
| 🚀  | 错误码管理 | 系统所有错误码的管理，可在线修改错误提示，无需重启服务     |
|     | 通知公告  | 系统通知公告信息发布维护                    |
| 🚀  | 敏感词   | 配置系统敏感词，支持标签分组                  |
| 🚀  | 应用管理  | 管理 SSO 单点登录的应用，支持多种 OAuth2 授权方式 |
| 🚀  | 地区管理  | 展示省份、城市、区镇等城市信息，支持 IP 对应城市      |

### 基础设施

|     | 功能       | 描述                                           |
|-----|----------|----------------------------------------------|
| 🚀  | 代码生成     | 前后端代码的生成（Java、Vue、SQL、单元测试），支持 CRUD 下载       |
| 🚀  | 系统接口     | 基于 Swagger 自动生成相关的 RESTful API 接口文档          |
| 🚀  | 数据库文档    | 基于 Screw 自动生成数据库文档，支持导出 Word、HTML、MD 格式      |
| 🚀  | 配置管理     | 对系统动态配置常用参数，支持 SpringBoot 加载                 |
| ⭐️  | 定时任务     | 在线（添加、修改、删除)任务调度包含执行结果日志                     |
| 🚀  | 文件服务     | 支持将文件存储到 S3（MinIO、阿里云、腾讯云、七牛云）、本地、FTP、数据库等   | 
| 🚀  | API 日志   | 包括 RESTful API 访问日志、异常日志两部分，方便排查 API 相关的问题   |
|     | MySQL 监控 | 监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈              |
|     | Redis 监控 | 监控 Redis 数据库的使用情况，使用的 Redis Key 管理           |
| 🚀  | 消息队列     | 基于 Redis 实现消息队列，Stream 提供集群消费，Pub/Sub 提供广播消费 |
| 🚀  | Java 监控  | 基于 Spring Boot Admin 实现 Java 应用的监控           |
| 🚀  | 链路追踪     | 接入 SkyWalking 组件，实现链路追踪                      |
| 🚀  | 日志中心     | 接入 SkyWalking 组件，实现日志中心                      |
| 🚀  | 分布式锁     | 基于 Redis 实现分布式锁，满足并发场景                       |
| 🚀  | 幂等组件     | 基于 Redis 实现幂等组件，解决重复请求问题                     |
| 🚀  | 服务保障     | 基于 Resilience4j 实现服务的稳定性，包括限流、熔断等功能          |
| 🚀  | 日志服务     | 轻量级日志中心，查看远程服务器的日志                           |

## 🐨 技术栈

| 项目                    | 说明                 |
|-----------------------|--------------------|
| `olto-dependencies`  | Maven 依赖版本管理       |
| `olto-framework`     | Java 框架拓展          |
| `olto-server`        | 管理后台 + 用户 APP 的服务端 |
| `olto-ui-admin`      | 管理后台的 Vue2 前端项目        |
| `olto-ui-admin-vue3`      | 管理后台的 Vue3 前端项目        |
| `olto-module-system` | 系统功能的 Module 模块    |
| `olto-module-member` | 会员中心的 Module 模块    |
| `olto-module-infra`  | 基础设施的 Module 模块    |

### 后端

| 框架                                                                                          | 说明               | 版本          | 学习指南                                                           |
|---------------------------------------------------------------------------------------------|------------------|-------------|----------------------------------------------------------------|
| [Spring Boot](https://spring.io/projects/spring-boot)                                       | 应用开发框架           | 2.7.8       | [文档](https://github.com/YunaiV/SpringBoot-Labs)                |
| [MySQL](https://www.mysql.com/cn/)                                                          | 数据库服务器           | 5.7 / 8.0+  |                                                                |
| [Druid](https://github.com/alibaba/druid)                                                   | JDBC 连接池、监控组件    | 1.2.15      | [文档](http://www.iocoder.cn/Spring-Boot/datasource-pool/?olto) |
| [MyBatis Plus](https://mp.baomidou.com/)                                                    | MyBatis 增强工具包    | 3.5.3.1     | [文档](http://www.iocoder.cn/Spring-Boot/MyBatis/?olto)         |
| [Dynamic Datasource](https://dynamic-datasource.com/)                                       | 动态数据源            | 3.6.1       | [文档](http://www.iocoder.cn/Spring-Boot/datasource-pool/?olto) |
| [Redis](https://redis.io/)                                                                  | key-value 数据库    | 5.0 / 6.0   |                                                                |
| [Redisson](https://github.com/redisson/redisson)                                            | Redis 客户端        | 3.18.0      | [文档](http://www.iocoder.cn/Spring-Boot/Redis/?olto)           |
| [Spring MVC](https://github.com/spring-projects/spring-framework/tree/master/spring-webmvc) | MVC 框架           | 5.3.24      | [文档](http://www.iocoder.cn/SpringMVC/MVC/?olto)               |
| [Spring Security](https://github.com/spring-projects/spring-security)                       | Spring 安全框架      | 5.7.6       | [文档](http://www.iocoder.cn/Spring-Boot/Spring-Security/?olto) |
| [Hibernate Validator](https://github.com/hibernate/hibernate-validator)                     | 参数校验组件           | 6.2.5       | [文档](http://www.iocoder.cn/Spring-Boot/Validation/?olto)      |
| [Quartz](https://github.com/quartz-scheduler)                                               | 任务调度组件           | 2.3.2       | [文档](http://www.iocoder.cn/Spring-Boot/Job/?olto)             |
| [Knife4j](https://gitee.com/xiaoym/knife4j)                                                 | Swagger 增强 UI 实现 | 4.0.0       | [文档](http://www.iocoder.cn/Spring-Boot/Swagger/?olto)         |
| [Resilience4j](https://github.com/resilience4j/resilience4j)                                | 服务保障组件           | 1.7.1       | [文档](http://www.iocoder.cn/Spring-Boot/Resilience4j/?olto)    |
| [SkyWalking](https://skywalking.apache.org/)                                                | 分布式应用追踪系统        | 8.12.0      | [文档](http://www.iocoder.cn/Spring-Boot/SkyWalking/?olto)      |
| [Spring Boot Admin](https://github.com/codecentric/spring-boot-admin)                       | Spring Boot 监控平台 | 2.7.10      | [文档](http://www.iocoder.cn/Spring-Boot/Admin/?olto)           |
| [Jackson](https://github.com/FasterXML/jackson)                                             | JSON 工具库         | 2.13.3      |                                                                |
| [MapStruct](https://mapstruct.org/)                                                         | Java Bean 转换     | 1.5.3.Final | [文档](http://www.iocoder.cn/Spring-Boot/MapStruct/?olto)       |
| [Lombok](https://projectlombok.org/)                                                        | 消除冗长的 Java 代码    | 1.18.24     | [文档](http://www.iocoder.cn/Spring-Boot/Lombok/?olto)          |

### [管理后台 Vue2 前端](./olto-ui-admin)

| 框架                                                                           | 说明            | 版本     |
|------------------------------------------------------------------------------|---------------|--------|
| [Vue](https://cn.vuejs.org/index.html)                                       | JavaScript 框架 | 2.7.14 |
| [Vue Element Admin](https://panjiachen.github.io/vue-element-admin-site/zh/) | 后台前端解决方案      | -      |

### [管理后台 Vue3 前端](./olto-ui-admin-vue3)

| 框架                                                                   |      说明      |   版本   |
|----------------------------------------------------------------------|:------------:|:------:|
| [Vue](https://staging-cn.vuejs.org/)                                 |    Vue 框架    | 3.2.47 |
| [Vite](https://cn.vitejs.dev//)                                      |   开发与构建工具    | 4.1.1  |
| [Element Plus](https://element-plus.org/zh-CN/)                      | Element Plus | 2.2.29 |
| [TypeScript](https://www.typescriptlang.org/docs/)                   |  TypeScript  | 4.9.5  |
| [pinia](https://pinia.vuejs.org/)                                    |    vuex5     | 2.0.30 |
| [vue-i18n](https://kazupon.github.io/vue-i18n/zh/introduction.html/) |     国际化      | 9.2.2  |
| [vxe-table](https://vxetable.cn/)                                    |   vue最强表单    | 4.3.9  |

