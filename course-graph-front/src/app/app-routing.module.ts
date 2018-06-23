import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { CourselistComponent } from './components/courselist/courselist.component';
import { CourseComponent } from './components/course/course.component';
import { MindmapComponent } from './components/mindmap/mindmap.component';
import { HomeworkComponent } from './components/homework/homework.component';
import { ProfileComponent } from './components/profile/profile.component';
import { LectureComponent } from './components/lecture/lecture.component';
import { ResourceComponent } from './components/resource/resource.component';

const routes: Routes = [
    { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: 'home', component: AppComponent },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'profile', component: ProfileComponent },
    { path: 'courselist', component: CourselistComponent },
    {
        path: 'course', component: CourseComponent,
        children: [
            { path: 'mindmap', component: MindmapComponent },
            { path: 'homework', component: HomeworkComponent },
            { path: 'lecture', component: LectureComponent },
            { path: 'resource', component: ResourceComponent }
        ]
    },

];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})

export class AppRoutingModule { }