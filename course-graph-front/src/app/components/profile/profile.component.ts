import {Component, OnInit, TemplateRef} from '@angular/core';
import {BsModalRef, BsModalService} from 'ngx-bootstrap/modal';
import {Router} from '@angular/router';

import {StorageService} from '../../services/storage.service';
import {UserService} from '../../services/user.service';

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
    modalRef: BsModalRef;

    profile = {
        name: "",
        id: 0,
        type: "",
        email: ""
    };
    curUser;
    newProfile = {
        password: "",
        newPassword: ""
    }

    constructor(
        private storage: StorageService,
        private userService: UserService,
        private modalService: BsModalService,
        private router: Router
    ) {
    }

    ngOnInit() {
        this.getProfile();
        this.curUser = this.storage.getItem("curUser");
    }

    openModifyWindow(template: TemplateRef<any>) {
        this.modalRef = this.modalService.show(template);
    }

    cancelModify() {
        this.modalRef.hide();
        this.newProfile = {
            password: "",
            newPassword: ""
        }
    }

    confirmModify() {
        console.log(this.newProfile);

        let _that = this;
        this.userService.update(this.newProfile).subscribe(function (suc) {
            let sucResp = JSON.parse(suc['_body']);
            console.log("modify profile resp:");
            console.log(sucResp);

            _that.modalRef.hide();
            _that.storage.removeItem("token");
            _that.storage.removeItem("curUser");
            _that.router.navigate(['login']);
        }, function (err) {
            let errResp = JSON.parse(err['_body']);
            console.log(errResp);
            alert(errResp.message);
        });
    }

    getProfile() {
        console.log("get user profile");

        let _that = this;
        this.userService.getPrivate().subscribe(function (suc) {
            let sucResp = JSON.parse(suc['_body']);
            console.log("get profile resp:");
            console.log(sucResp);
            _that.profile = sucResp;
        }, function (err) {
            let errResp = JSON.parse(err['_body']);
            console.log(errResp);
            alert(errResp.message);
        });
    }

}
