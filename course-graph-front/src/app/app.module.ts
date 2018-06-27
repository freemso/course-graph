import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule, JsonpModule } from '@angular/http';

import { AppRoutingModule } from './app-routing.module';

//引入UI模块
import { ModalModule } from 'ngx-bootstrap/modal';
import { TabsModule } from 'ngx-bootstrap/tabs';
import { AccordionModule } from 'ngx-bootstrap/accordion';
import { CommonModule } from '@angular/common';
import { FileUploadModule } from 'ng2-file-upload';
import { FileSaverModule } from 'ngx-filesaver';
// import {} from 'jsmind';



//引入组件
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { CourselistComponent } from './components/courselist/courselist.component';
import { CourseComponent } from './components/course/course.component';
import { MindmapComponent } from './components/mindmap/mindmap.component';
import { HomeworkComponent } from './components/homework/homework.component';

//引入服务
import { MyHttpService } from './services/MyHttp.service';
import { AuthenticationService } from './services/authentication.service';
import { UserService } from './services/user.service';
import { CourseService } from './services/course.service';
import { GraphService } from './services/graph.service';
import { NodeService } from './services/node.service';
import { ResourceService } from './services/resource.service';
import { LectureService } from './services/lecture.service';
import { QuestionService } from './services/question.service';
import { AnswerService } from './services/answer.service';
import { StorageService } from './services/storage.service';
import { LectureComponent } from './components/lecture/lecture.component';
import { ResourceComponent } from './components/resource/resource.component';
import { ProfileComponent } from './components/profile/profile.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginComponent,
    RegisterComponent,
    CourselistComponent,
    CourseComponent,
    MindmapComponent,
    LectureComponent,
    ResourceComponent,
    ProfileComponent,
    HomeworkComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpModule,
    JsonpModule,
    ModalModule.forRoot(),
    TabsModule.forRoot(),
    AccordionModule.forRoot(),
    CommonModule,
    FileUploadModule,
    FileSaverModule,

  ],
  providers: [
    MyHttpService,
    AuthenticationService,
    UserService,
    CourseService,
    GraphService,
    NodeService,
    ResourceService,
    LectureService,
    QuestionService,
    AnswerService,
    StorageService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
