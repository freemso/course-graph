# JSON Data Format Definition

## 用户
```javascript
user = {
	"userType": 1,
	"userId": "15302010001"
	"useName": "kaiyu Dai",
	"password": "12345abcd"
};
```

属性 | 含义 | 取值 |
------- | ------- | ------
userId | 用户的唯一标识符 | string
userType | 用户类型，学生或老师 | 0: 学生 </br>1: 老师
uesrName | 用户姓名 | string
password | 用户密码 | string

* 适用场景：登录，注册页面

```javascript
student = {
	"userId": "15302010001",
	"userName": "Tom"
};
```

* 适用场景 ：查询注册学生信息


## 课程信息
```javascript
courseInfo = {
	"courseId": "web2018",
	"courseName": "Advanced Web Technology",
	"teacher": {
		"userId": "15302010038",
		"userName": "Kaiyu Dai"
	}
	"studentNum": 53
};
```

属性| 含义 | 取值 |
------- | ------- | -------
courseId |课程标示符 | string
courseName | 课程名称 | string
teacher | 授课教师 | JSON对象
studentNum | 注册学生数 | int

* 适用场景：用户登录后显示的课程界面

## 思维导图
jsMind 支持三种数据格式，分别是树对象格式、表对象格式、freemind格式。jsMind 可以加载其中任一种格式，也能将数据导出为任一种格式。

* **树对象格式** 默认格式，节点之间是包含关系，便于程序进行处理，适合与 MongoDB 及其它文档型数据库进行数据交互；
* **表对象格式** 节点之间是并列关系，使用 parentid 标识上下级关系，适合与关系型数据库进行数据交互；
* **freemind格式** 使用 freemind 的 xml 格式，适合与 freemind 进行数据交互。

下面是三种数据格式的简单示例：

A. 树对象格式示例
---

```javascript
var mind = {
    /* 元数据，定义思维导图的名称、作者、版本等信息 */
    "meta":{
        "name":"jsMind-demo-tree",
        "author":"hizzgdev@163.com",
        "version":"0.2"
    },
    /* 数据格式声明 */
    "format":"node_tree",
    /* 数据内容 */
    "data":{"id":"root","topic":"jsMind","children":[
        {"id":"easy","topic":"Easy","direction":"left","expanded":false,"children":[
            {"id":"easy1","topic":"Easy to show"},
            {"id":"easy2","topic":"Easy to edit"},
            {"id":"easy3","topic":"Easy to store"},
            {"id":"easy4","topic":"Easy to embed"}
        ]},
        {"id":"open","topic":"Open Source","direction":"right","expanded":true,"children":[
            {"id":"open1","topic":"on GitHub"},
            {"id":"open2","topic":"BSD License"}
        ]},
        {"id":"powerful","topic":"Powerful","direction":"right","children":[
            {"id":"powerful1","topic":"Base on Javascript"},
            {"id":"powerful2","topic":"Base on HTML5"},
            {"id":"powerful3","topic":"Depends on you"}
        ]},
        {"id":"other","topic":"test node","direction":"left","children":[
            {"id":"other1","topic":"I'm from local variable"},
            {"id":"other2","topic":"I can do everything"}
        ]}
    ]}
};
```

B. 表对象格式示例
---

```javascript
var mind = {
    /* 元数据，定义思维导图的名称、作者、版本等信息 */
    "meta":{
        "name":"example",
        "author":"hizzgdev@163.com",
        "version":"0.2"
    },
    /* 数据格式声明 */
    "format":"node_array",
    /* 数据内容 */
    "data":[
        {"id":"root", "isroot":true, "topic":"jsMind"},

        {"id":"easy", "parentid":"root", "topic":"Easy", "direction":"left"},
        {"id":"easy1", "parentid":"easy", "topic":"Easy to show"},
        {"id":"easy2", "parentid":"easy", "topic":"Easy to edit"},
        {"id":"easy3", "parentid":"easy", "topic":"Easy to store"},
        {"id":"easy4", "parentid":"easy", "topic":"Easy to embed"},

        {"id":"open", "parentid":"root", "topic":"Open Source", "expanded":false, "direction":"right"},
        {"id":"open1", "parentid":"open", "topic":"on GitHub"},
        {"id":"open2", "parentid":"open", "topic":"BSD License"},

        {"id":"powerful", "parentid":"root", "topic":"Powerful", "direction":"right"},
        {"id":"powerful1", "parentid":"powerful", "topic":"Base on Javascript"},
        {"id":"powerful2", "parentid":"powerful", "topic":"Base on HTML5"},
        {"id":"powerful3", "parentid":"powerful", "topic":"Depends on you"},
    ]
};
```

C. freemind格式示例
---

```javascript
var mind = {
    /* 元数据，定义思维导图的名称、作者、版本等信息 */
    "meta":{
        "name":"example",
        "author":"hizzgdev@163.com",
        "version":"0.2"
    },
    /* 数据格式声明 */
    "format":"freemind",
    /* 数据内容 */
    "data":"<map version=\"1.0.1\"> <node ID=\"root\" TEXT=\"jsMind\" > <node ID=\"easy\" POSITION=\"left\" TEXT=\"Easy\" > <node ID=\"easy1\" TEXT=\"Easy to show\" /> <node ID=\"easy2\" TEXT=\"Easy to edit\" /> <node ID=\"easy3\" TEXT=\"Easy to store\" /> <node ID=\"easy4\" TEXT=\"Easy to embed\" /> </node> <node ID=\"open\" POSITION=\"right\" TEXT=\"Open Source\" > <node ID=\"open1\" TEXT=\"on GitHub\" /> <node ID=\"open2\" TEXT=\"BSD License\" /> </node> <node ID=\"powerful\" POSITION=\"right\" TEXT=\"Powerful\" > <node ID=\"powerful1\" TEXT=\"Base on Javascript\" /> <node ID=\"powerful2\" TEXT=\"Base on HTML5\" /> <node ID=\"powerful3\" TEXT=\"Depends on you\" /> </node> <node ID=\"other\" POSITION=\"left\" TEXT=\"test node\" > <node ID=\"other1\" TEXT=\"I'm from local variable\" /> <node ID=\"other2\" TEXT=\"I can do everything\" /> </node> </node> </map>"
};
```

注
---
除 freemind 格式外，其余两种格式的基本数据结构如下：

```javascript

    {
        "id":"open",           // [必选] ID, 所有节点的ID不应有重复，否则ID重复的结节将被忽略
        "topic":"Open Source", // [必选] 节点上显示的内容
        "direction":"right",   // [可选] 节点的方向，此数据仅在第一层节点上有效，目前仅支持 left 和 right 两种，默认为 right
        "expanded":true,       // [可选] 该节点是否是展开状态，默认为 true
    }

```


## 问题/作业

* 适用场景：设置问题，发布作业，查看作业完成情况

A. 选择题
---
```javascript
choiceQuestion = {
	"problemId" : "cq1",
	"description": "what's html",   // 问题描述
	"choices" : {
		"A": "Hypertext markup language", //选项A描述
		"B": "Cascading style sheet", //选项B描述
		"C": "programming language", //选项C描述
		"D": "All choices above are wrong" //选项D描述
	},
	"answer": ["A"] //答案
};
```


属性| 含义 | 取值
------- | ------- | -------
problemId | 该问题的唯一标识符 | string
description | 问题描述 | string
choices | 问题所有选项 | JSON对象
answer |问题答案组成的数组，通常只有一个元素（单选题）| string数组



B. 简答题
---
```javascript
shortAnswerQuestion = {
	"problemId" : "shtQ1"
	"description": "what's html? ", //问题描述
};
```

属性 | 含义 | 取值
------- | ------- | ------
problemId | 该问题唯一标识符 | string
description | 该问题描述 | string


C. 作业提交信息
---
* 适用于查看作业统计信息场景

```javascript
homeworkStats = {
	"problemId":"cq1"
	"submissionNum": 10,
	"rightNum": 5,
	"wrongNum": 5,
};
```
属性 | 含义 | 取值
--- | --- | ---
problemId | 问题的ID | string
submissionNum | 该问题当前提交人数  | int
rightNum | 正确人数 | int
wrongNum | 错误人数 | int

---

* 查看作业完成信息场景

```javascript
submission = {
	"studentId": "15302010038", 
	"studentName": "tom",
	"problemId": "cq1"
	"studentAnswer": "A"
	"status": "true"
};
```
属性 | 含义
--- | ---
studentId | 提交学生的id和该学生的use id相同
studentName | 该学生的姓名
problemId | 回答的问题的problemId
studentAnswer | 提交的答案
status | 提交的答案是否正确



## 课程资源

* 适用场景：课程资源提供如课件，教学视频，资料链接等

```javascript
source = {
	"courseId": "web2018",
	"sourceId": "web1",
	"sourceType": "slides",
	"sourceName": "advanced_web_8.ppt",
	"sourceUri": "http://www.google.com.hk",
};
```
属性 | 含义 | 取值
--- | --- | ---
courseId | 对应课程的表示符 | string 
sourceId | 资源的Id，作为资源的唯一标识符 | string
sourceType | 资源类型 | string 
sourceName | 资源名称 | string
sourceUri | 资源对应uri | string















