import { Component, OnInit, ViewChild, TemplateRef, ElementRef, AfterViewInit } from '@angular/core';
import { MindmapComponent } from '../mindmap/mindmap.component';
import { HomeworkComponent } from '../homework/homework.component';
import { LectureComponent } from '../lecture/lecture.component';
import { FileUploader } from 'ng2-file-upload';
import { FileItem } from 'ng2-file-upload';
import { ActivatedRoute, Params } from '@angular/router';
import * as jsMind from '../../jsmind/js/jsmind.js';

import { StorageService } from '../../services/storage.service';
import { MyHttpService } from '../../services/MyHttp.service';
import { QuestionService } from '../../services/question.service';

import * as $ from 'jquery';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';
import { ResourceComponent } from '../resource/resource.component';
import { GraphService } from '../../services/graph.service';

@Component({
    selector: 'app-course',
    templateUrl: './course.component.html',
    styleUrls: ['./course.component.css']
})

export class CourseComponent implements OnInit {
    curUser = this.storage.getItem("curUser");
    serverUrl = this.myHttp.baseURL;
    curGraphId;
    curNodeId;
    courseId;
    sidebarType = 0;
    description = "";
    index = "hello";

    public graphs = [];
    newGraph = {
        name: "",
        description: "",
        jsmind: JSON.stringify({
            "meta": {
                "name": "jsMind remote",
                "author": "hizzgdev@163.com",
                "version": "0.2"
            },
            "format": "node_tree",
            "data": {
                "id": "root" + jsMind.util.uuid.newid(), "topic": "根节点", "children": []
            }
        })
    };
    homeworkContent = {
        newMultichoice: {
            "description": "",
            "type": "MULTIPLE_CHOICE",
            "choices": [
                {
                    "key": "A",
                    "value": ""
                }, {
                    "key": "B",
                    "value": ""
                }, {
                    "key": "C",
                    "value": ""
                }, {
                    "key": "D",
                    "value": ""
                }],
            "answer": "A"
        },

        newShortanswer: {
            "type": "SHORT_ANSWER",
            "description": ""
        }
    };

    lectureContent = {
        uploader: new FileUploader({
            url: this.serverUrl + "/nodes/" + this.curNodeId + "/lectures",
            authToken: this.storage.getItem('token'),
            method: "POST",
            itemAlias: "lecture",
            autoUpload: false
        })
    };

    recourcesContent = {
        uploader: new FileUploader({
            url: this.serverUrl + "/nodes/" + this.curNodeId + "/resources/files",
            authToken: this.storage.getItem('token'),
            method: "POST",
            itemAlias: "file",
            autoUpload: false
        }),
        URL: {
            title: "",
            link: "",
            type: "URL"
        }
    };

    @ViewChild(MindmapComponent) child: MindmapComponent;
    @ViewChild(HomeworkComponent) homework: HomeworkComponent;
    @ViewChild(LectureComponent) lecture: LectureComponent;
    @ViewChild(ResourceComponent) resource: ResourceComponent;

    modalRef: BsModalRef;

    constructor(
        private graphService: GraphService,
        private questionService: QuestionService,
        private myHttp: MyHttpService,
        private routerIonfo: ActivatedRoute,
        private modalService: BsModalService,
        private storage: StorageService) {
    }

    ngOnInit() {
        this.courseId = this.routerIonfo.snapshot.queryParams["cid"];
        this.getGraphs();
        this.curGraphId = null;

        this.startJquery();
        this.recourcesContent.uploader.onSuccessItem = this.successItem.bind(this);
        this.recourcesContent.uploader.onAfterAddingFile = this.afterAddFile.bind(this);
        this.recourcesContent.uploader.onBuildItemForm = (fileItem: any, form: any) => {
            fileItem.url = this.serverUrl + "/nodes/" + this.curNodeId + "/resources/files";
            form.append("description", this.description);
        };
        this.lectureContent.uploader.onSuccessItem = this.successItem.bind(this);
        this.lectureContent.uploader.onAfterAddingFile = this.afterAddFile.bind(this);
        this.lectureContent.uploader.onBuildItemForm = (fileItem: any, form: any) => {
            fileItem.url = this.serverUrl + "/nodes/" + this.curNodeId + "/lectures";
            // alert('test');
            form.append("description", this.description);
        };
        // this.uploader.onSuccessItem = this.successItem.bind(this);
        // this.uploader.onAfterAddingFile = this.afterAddFile;
        // this.uploader.onBuildItemForm = this.buildItemForm;
        // this.child.getData(this.graphs[0].id);
    }

    setSidebar(type) {
        this.sidebarType = type;
        this.startJquery();
        switch (this.sidebarType) {
            case 0:
                break;
            case 1:
                this.child.save();
                this.homework.getQuestions(this.curNodeId);
                break;
            case 2:
                this.child.save();
                this.lecture.getLectures(this.curNodeId);
                break;
            case 3:
                this.child.save();
                this.resource.getResources(this.curNodeId);
                break;
            default:
                break;
        }
    }

    prtScn() {
        if (this.curGraphId == null) {
            alert("请先选中课程");
        } else {
        this.child.prtScn();
    }
}

    changeGraph(item) {
        this.child.getData(item.id);
        this.curGraphId = item.id;
    }

    changeStatus(event) {        
        this.curNodeId = event;
    }

    save() {
        this.child.save();
    }

    startJquery() {
        $(function () {
            // nav收缩展开
            $('.nav-item>a').on('click', function () {
                if (!$('.my-nav').hasClass('nav-mini')) {
                    if ($(this).next().css('display') == "none") {
                        //展开未展开
                        $('.nav-item').children('ul').slideUp(300);
                        $(this).next('ul').slideDown(300);
                        $(this).parent('li').addClass('nav-show').siblings('li').removeClass('nav-show');
                    } else {
                        //收缩已展开
                        $(this).next('ul').slideUp(300);
                        $('.nav-item.nav-show').removeClass('nav-show');
                    }
                }
            });
            //nav-mini切换
            $('#mini').on('click', function () {
                if (!$('.my-nav').hasClass('nav-mini')) {
                    $('.nav-item.nav-show').removeClass('nav-show');
                    $('.nav-item').children('ul').removeAttr('style');
                    $('.my-nav').addClass('nav-mini');
                } else {
                    $('.my-nav').removeClass('nav-mini');
                }
            });
        });
    }

    openAddCourseWindow(template: TemplateRef<any>) {
        this.modalRef = this.modalService.show(template);
    }

    cancelAdd() {
        this.modalRef.hide();
    }

    getGraphs() {
        console.log("get all graphs:");

        let _that = this;
        this.graphService.listGraphsOfCourse(this.courseId).subscribe(function (suc) {
            let sucResp = JSON.parse(suc['_body']);
            console.log("get graphs resp:");
            console.log(sucResp);
            _that.graphs = sucResp;
        }, function (err) {
            let errResp = JSON.parse(err['_body']);
            console.log(errResp);
            alert(errResp.message);
        });
    }

    addNewGraph() {
        // console.log("begin to add graph:");
        // console.log(this.newGraph);

        let _that = this;
        this.graphService.addGraphToCourse(this.courseId, this.newGraph).subscribe(function (suc) {
            let sucResp = JSON.parse(suc['_body']);
            console.log("add graph resp");

            _that.graphs.push(sucResp);
            _that.child.getData(sucResp.id);
            _that.modalRef.hide();

            //重置
            _that.newGraph = {
                name: "",
                description: "",
                jsmind: JSON.stringify({
                    "meta": {
                        "name": "jsMind remote",
                        "author": "hizzgdev@163.com",
                        "version": "0.2"
                    },
                    "format": "node_tree",
                    "data": {
                        "id": "root" + jsMind.util.uuid.newid(), "topic": "根节点", "children": []
                    }
                })
            };
        }, function (err) {
            let errResp = JSON.parse(err['_body']);
            console.log(errResp);
            alert(errResp);
        });
    }

    //发送delete请求
    deleteGraph() {
        let _that = this;
        let len = _that.graphs.length;
        for(var i = 0; i < len; i++){
            console.log(_that.curGraphId);
            console.log(_that.graphs[i]);
            if(_that.graphs[i].id == _that.curGraphId) {
                _that.graphs.splice(i, 1);
            }
        }
        console.log(this.curGraphId);
        this.graphService.delete(this.curGraphId).subscribe(function (suc) {
            _that.curGraphId = null;
            _that.curNodeId = null;
            console.log(_that.graphs);
            
            // _that.graphs.
            _that.child.clear();
        }, function (err) {
            let errResp = JSON.parse(err['_body']);
            console.log(errResp);
            alert(errResp);
        });
    }

    //作业部分方法--------------------------------------------------------------
    openModal(template: TemplateRef<any>) {
        this.modalRef = this.modalService.show(template);
    }

    addMultichoice() {
        console.log("add multichoice question");

        let _that = this;
        this.questionService.addQuestionToNode(this.curNodeId, this.homeworkContent.newMultichoice).subscribe(function (suc) {
            let sucResp = JSON.parse(suc['_body']);
            console.log("add multichoice resp");
            console.log(sucResp);

            _that.homework.getQuestions(_that.curNodeId);
            _that.modalRef.hide();
        }, function (err) {
            let errResp = JSON.parse(err['_body']);
            console.log(errResp);
            alert(errResp.message);
        });

        this.clearQuestion();
    }

    addShortanswer() {
        console.log("add shortanswer question");

        let _that = this;
        this.questionService.addQuestionToNode(this.curNodeId, this.homeworkContent.newShortanswer).subscribe(function (suc) {
            let sucResp = JSON.parse(suc['_body']);
            console.log("add shortanswer resp");
            console.log(sucResp);

            _that.homework.getQuestions(_that.curNodeId);
            _that.modalRef.hide();
        }, function (err) {
            console.dir(err);
        });

        this.clearQuestion();
    }

    cancelAddQuestion() {
        this.modalRef.hide();
        this.clearQuestion();
    }

    setMultiAnswer(choice) {
        this.homeworkContent.newMultichoice.answer = choice;
    }

    clearQuestion() {
        this.homeworkContent = {
            newMultichoice: {
                "type": "MULTIPLE_CHOICE",
                "description": "",
                "choices": [
                    {
                        "key": "A",
                        "value": ""
                    }, {
                        "key": "B",
                        "value": ""
                    }, {
                        "key": "C",
                        "value": ""
                    }, {
                        "key": "D",
                        "value": ""
                    }],
                "answer": "A"
            },

            newShortanswer: {
                "type": "SHORT_ANSWER",
                "description": ""
            }
        }
    }

    //上传所有文件
    fileAllUp(upload): any {
        console.log("file all up");
        upload.uploadAll();
    }

    //取消所有文件
    fileAllCancel(): any {
        console.log("file all cancel");
        this.recourcesContent.uploader.cancelAll();
    }

    //删除所有文件
    fileAllDelete(): any {
        console.log("file all delete");
        this.recourcesContent.uploader.clearQueue();
    }

    //构造上传文件表项
    buildItemForm(fileItem: FileItem, form: any): any {
        // this.curContent.uploader.options.url = "http://10.222.174.42:8080/nodes/" + this.curNodeId + "/" + this.curContent.formData.type;
        // fileItem.url = "http://10.222.174.42:8080/nodes/" + this.curNodeId + "/" + this.curContent.formData.type;
        if (this.sidebarType == 2) {
            fileItem.url = this.serverUrl + "/nodes/" + this.curNodeId + "/lectures";
        } else {
            fileItem.url = this.serverUrl + "/nodes/" + this.curNodeId + "/resources/files";
        }
        alert("testefasfdadfasfdas");
        form.append("description", "description");
    }

    //增加文件回执
    afterAddFile(fileItem: FileItem): any {
        fileItem.withCredentials = false;
    }

    //上传文件成功回执
    successItem(item: FileItem, response: string, status: number): any {
        // 上传文件成功
        if (status == 201) {
            // 上传文件后获取服务器返回的数据
            let tempRes = JSON.parse(response);
            this.lecture.getLectures(this.curNodeId);
            this.resource.getResources(this.curNodeId);
        } else {
            // 上传文件后获取服务器返回的数据错误
        }
        console.info(" for " + item.file.name + " status " + status);
    }

    //资源*----------------------------------------------------------------------------------------
    openRecourcesModal(template: TemplateRef<any>) {
        this.modalRef = this.modalService.show(template);
    }

    addURL() {
        //提交新的URL资源
        console.log("add url");
        let url = "/nodes/" + this.curNodeId + "/resources/url";
        let body = JSON.stringify(this.recourcesContent.URL);
        console.log(body);

        let _that = this;
        this.myHttp.post(url, body).subscribe(function (data) {
            console.log("add url resp");
            console.dir(data);

            //刷新子模块问题列表
            _that.resource.getResources(_that.curNodeId);
            _that.modalRef.hide();
        }, function (err) {
            console.dir(err);
        });

    }


}