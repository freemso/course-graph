# 高级 Web 文档

## 流程分析

## 功能分析

## 总体架构

## 前端开发

前端基于Angular 4框架进行开发，使用Bootstrap 4框架进行页面样式设计，jQuery实现动态侧边栏效果。

### 组件结构设计

基本上是一个组件代表一个页面，Header组件是顶部导航栏，Course组件包含了四个子组件。

```
-header.component
-login.component
-courselist.component
-course.component (parent)
 	--mindmap.component (child)
 	--homework.component (child)
 	--lecture.component (child)
 	--resource.component (child)
-profile.component
-register.component
```

###页面间交互
#### 路由配置

- 在Angular中，页面之间的跳转可以使用传统的做法，但使用路由是一个更灵活的做法。
- 路由配置可以单独写成一个Module，在使用到的Module里引入即可（也需要引入Router模块）。
- 路由配置的核心是编写path与component的映射。配置之后，可以通过a标签的“router-link”属性指定跳转的路径，也可以在component里，通过Router模块提供的navigate，redirectTo等方法指定跳转目标，为复杂的跳转条件判断提供了方便的支持。
- 可以方便地匹配错误的路径，为其指定跳转页面。

#### 组件间传值

- 在模板中通过selector引用组件内容时，可以在标签内填写要传递的字段，相应地，在子组件中通过@Input注解接收对应值。这一方式应用在了course组件与其四个子组件之间，子组件根据父组件提供的node id向后端请求数据。
- 通过a标签的router-link在组件间跳转时，可以使用queryParams属性承载要传递的数据，相应地在接收传值的模块里能够通过ActivatedRoute模块提供的routerIonfo.snapshot.queryParams方法得到数据。这一方式应用在courselist组件与course组件之间，后者需要前者提供的course id向后端请求数据。

###与后端交互
#### 服务注入

- 能够复用的代码抽取出来封装成service是一个较好的选择，能够降低耦合度。比如项目中的StorageService用于向浏览器的localStorage中读写条目，使用时只需要在组件中注入即可（也可以new一个实例，但是效率不高，不推荐）。
- 对于数据的获取和处理（与后端交互）也应封装成service，比如基于Http模块包装的MyHttpService在每一个请求头部加入用于身份验证的token，同时能够方便地更改后端服务的api请求地址。此外，将每个页面向后端获取数据的操作内聚成一个service（如UserService，CourseService）。

###关键功能实现

#### 表单实时验证

- 通过双向数据绑定和条件语句结合实现表单的实时验证。
- 在模板中，用[(ngModel)]绑定component中的数据，用*ngIf语句决定满足条件与否时分别要显示的内容。通过这种方式，注册登录的输入框可以做到4实时检测到是否表项是否填写，是否满足填写条件，并给出相应的提示内容。

#### 身份验证

- 登录之后，从后端收到唯一的token，使用StorageService将其储存在浏览器的localStrorage里。
- 每次需要向后端发送请求，MyHttpService会从localStorage中取出token并添加到请求报文的header的Ahthorization字段里。

#### 弹窗

- 使用ngx-bootstrap提供的Modal模块可以很容易地实现弹窗效果。
- 具体做法是：将弹窗内容放在<ng-template>标签内，点击按钮时将template的引用传给点击方法，在方法内调用show或hide方法。

#### 文件上传下载

- 文件上传使用了ng2-file-uploader模块，该模块对于文件上传实现了拓展操作，例如显示进度条、取消下载操作以及队列操作等，该模块并且显性支持对文件上传各阶段的操作，通过设置各阶段的回调函数，将函数注入到uploader的实例中。

  使用方法：首先在ts文件内载入ng2-file-uploader，导入FileUpLoader类。在html文件中提供选择文件、上传文件等选项，并将其与ts文件中uploader的实例相绑定。Ts文件中主要涉及uploader实例的初始化，包括设置URL、method、token等参数。然后设置uploader实例的增加文件回调函数、构造单个上传回调函数、上传成功回调函数，在构造单个上传文件的回调函数中，将文件的额外参数如描述等注入HTTP请求，在上传成功的回调函数中通知前端变更状态。

- 文件下载使用了ngx-filesaver模块，该模块包装了文件下载的相关实现细节，将操作接口暴露在HTML的属性中方便操作。

  使用方法：在html文件中添加下载文件的按钮，在按钮中声明filesaver，并绑定method、url、header等参数，设置成功与失败的回调函数。在ts文件中只需要实现两种回调函数即可。

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

## 项目管理、合作

### GitHub 代码管理

