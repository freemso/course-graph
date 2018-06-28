import {Component} from '@angular/core';
import {StorageService} from '../../services/storage.service';
import {AuthenticationService} from '../../services/authentication.service';


@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.css']
})
export class HeaderComponent {
    curUser;

    constructor(
        private authentication: AuthenticationService,
        private storage: StorageService) {
    }

    ngOnInit() {
        this.curUser = this.storage.getItem("curUser");
    }

    logout() {
        console.log("user logout");
        let _that = this;
        this.authentication.logout().subscribe(function (suc) {
            let sucResp = JSON.parse(suc['_body']);
            console.log("log out resp:");
            console.log(sucResp);
            console.log("remove token and curUser");
            _that.storage.removeItem("token");
            _that.storage.removeItem("curUser");
        }, function (err) {
            let errResp = JSON.parse(err['_body']);
            console.log(errResp);
            alert(errResp.message);
        });
    }

}
