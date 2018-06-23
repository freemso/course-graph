import { Component, OnInit } from '@angular/core';
import { Router, NavigationExtras } from '@angular/router';
import { UserService } from '../../services/user.service';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  disabled: boolean = false;
  user: any = {};
  passwordAgain: string;

  constructor(
    private router: Router,
    private userservice: UserService) {
  }

  ngOnInit() {
    this.user = {
      email: '',
      name: '',
      password: '',
      type: 'TEACHER',
      verificationCode: ''
    };
  }

  setTypeStu() {
    this.user.type = 'STUDENT';
  }

  setTypeTec() {
    this.user.type = 'TEACHER';
  }

  getVerificationCode() {
    console.log("get verification code");
    let body = { email: this.user.email };
    console.log(body);

    let _that = this;

    this.userservice.getVerificationCode(body).subscribe(function (suc) {
      console.log(suc);
      let sucResp = (suc['_body']);
      console.log("verification code resp:");
      console.log(sucResp);
      _that.disabled = true;
    }, function (err) {
      let errResp = JSON.parse(err['_body']);
      console.log(errResp);
      alert(errResp.message);
    });
  }

  register() {
    console.log("begin to register user: ");
    console.log(this.user);

    //与服务器端通信，确认是否注册成功
    let _that = this;
    this.userservice.register(this.user).subscribe(function (suc) {
      let sucResp = JSON.parse(suc['_body']);
      console.log("register resp:");
      console.log(sucResp);

      _that.router.navigate(['login']);
    }, function (err) {
      let errResp = JSON.parse(err['_body']);
      console.log(errResp);
      alert(errResp.message);
    });
  }

}
