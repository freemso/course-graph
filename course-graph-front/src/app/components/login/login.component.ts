import { Component, OnInit } from '@angular/core';
import { Router, NavigationExtras } from '@angular/router';
import { StorageService } from '../../services/storage.service';
import { MyHttpService } from '../../services/MyHttp.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: any = {};

  constructor(
    private myHttp:MyHttpService,
    private router: Router,
    private storage: StorageService) {
  }

  ngOnInit() {
    if(this.storage.getItem("curUser")){
      this.storage.removeItem("curUser");
    }
    this.user = {
      email: '',
      password: ''
    };
  }

  login() {
    console.log("begin to login: ");
    console.log(this.user);

    //删除上一个token
    if(this.storage.getItem("token")){
      this.storage.removeItem("token");
    }

    let url = "/token";
    let body = JSON.stringify(this.user);

    let _that = this;
    this.myHttp.post(url, body).subscribe(function (data) {
      console.log("log in resp:");
      console.log(data);
      console.log("got token:");
      console.log(JSON.parse(data['_body']));

      console.log("set token to localStorage: ");
      let token = JSON.parse(data['_body'])["token"];
      _that.storage.setItem("token", token);
      console.log("log in success, store user");
      let user = JSON.parse(data['_body'])["user"];
      console.log(user);
      _that.storage.setItem("curUser", user);
      _that.router.navigate(['courselist']);

    }, function (err) {
      console.dir(err);
    });

  }
}
