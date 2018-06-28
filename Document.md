# 高级 Web 文档

## 流程分析

## 功能分析

## 总体架构

## 前端开发

前端基于Angular 4框架进行开发，使用Bootstrap 4框架进行页面样式设计，jQuery实现动态侧边栏效果

### Angular 4 框架

#### 组件结构设计

header.component

login.component

courselist.component

course.component

​	mindmap.component

​	homework.component

​	lecture.component

​	resource.component

profile.component

register.component

#### 路由配置

- 在Angular中，页面之间的跳转可以使用传统的做法，但使用路由是一个更灵活的做法。
- 路由配置可以单独写成一个Module，在使用到的Module里引入即可（也需要引入Router模块）。
- 路由配置的核心是编写path与component的映射。配置之后，可以通过a标签的“router-link”属性指定跳转的路径，也可以在component里，通过Router模块提供的navigate，redirectTo等方法指定跳转目标，为复杂的跳转条件判断提供了方便的支持。
- 可以方便地匹配错误的路径，为其指定跳转页面。

#### 组件间传值

- 在模板中通过selector引用组件内容时，可以在标签内填写要传递的字段，相应地，在子组件中通过@Input注解接收对应值。这一方式应用在了course组件与其四个子组件之间，子组件根据父组件提供的node id向后端请求数据。
- 通过a标签的router-link在组件间跳转时，可以使用queryParams属性承载要传递的数据，相应地在接收传值的模块里能够通过ActivatedRoute模块提供的routerIonfo.snapshot.queryParams方法得到数据。这一方式应用在courselist组件与course组件之间，后者需要前者提供的course id向后端请求数据。

#### 服务注入

- 能够复用的代码抽取出来封装成service是一个较好的选择，能够降低耦合度。比如项目中的StorageService用于向浏览器的localStorage中读写条目，使用时只需要在组件中注入即可（也可以new一个实例，但是效率不高，不推荐）。
- 对于数据的获取和处理（与后端交互）也应封装成service，比如基于Http模块包装的MyHttpService在每一个请求头部加入用于身份验证的token，同时能够方便地更改后端服务的api请求地址。此外，将每个页面向后端获取数据的操作内聚成一个service（如UserService，CourseService）。

#### 表单实时验证

- 通过双向数据绑定和条件语句结合实现表单的实时验证。
- 在模板中，用[(ngModel)]绑定component中的数据，用*ngIf语句决定满足条件与否时分别要显示的内容。通过这种方式，注册登录的输入框可以做到4实时检测到是否表项是否填写，是否满足填写条件，并给出相应的提示内容。

#### 身份验证

- 登录之后，从后端收到唯一的token，使用StorageService将其储存在浏览器的localStrorage里。
- 每次需要向后端发送请求，MyHttpService会从localStorage中取出token并添加到请求报文的header的Ahthorization字段里。

#### 文件上传下载

- 上传文件使用ng2-file-upload模块
- 下载文件

----待续....

## 后端开发
后端主要使用了 Spring Boot 框架，Neo4j 数据库和 Redis 内存数据库。

### 项目结构说明

### 关键功能实现细节

#### 用户登录状态保存

#### 邮件验证

#### 权限控制

#### 表单数据验证

### 单元测试

### 数据库设计

#### Neo4j 图数据库
使用 Neo4j 图数据库作为主要的数据库

#### Redis 内存数据库

## 前后端对接

### REST 接口设计

### ID 设计的考虑

### 跨域访问

## 部署

### 部署流程

### Neo4j 浏览器访问受限问题的解决

### 邮件服务器垃圾邮件问题的解决

## 项目管理

### GitHub 代码管理

