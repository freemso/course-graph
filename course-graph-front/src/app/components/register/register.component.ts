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

  mailAllowed = false;
  nameAllowed = false;
  pwdAllowed = false;
  againAllowed = false;
  verCodeAllowed = true;


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

  checkMail(e) {
    // 匹配字符串
    const pattern = '^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$';
    const n = this.user.email.search(pattern);
    console.log(n);
    if (n == -1) {
      this.mailAllowed = false;
    } else {
      this.mailAllowed = true;
    }
  }
  checkName(e) {
    // 匹配字符串
    if (this.user.name == "") {
      this.nameAllowed = false;
    } else {
      this.nameAllowed = true;
    }
  }
  checkPwd(e) {
    // 匹配字符串
    const pattern = '^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$';
    const n = this.user.password.search(pattern);
    console.log(n);
    if (n === -1) {
      this.pwdAllowed = false;
    } else {
      this.pwdAllowed = true;
    }
  }
  checkAgain(e) {
    // 匹配字符串
    if (this.user.password == this.passwordAgain){
      this.againAllowed = true;
    } else {
      this.againAllowed = false;
    }
  }

  checkVerCode(e) {
    // 匹配字符串
    if (this.disabled) {
      if (this.user.verificationCode == ""){
        this.verCodeAllowed = false;
      } else {
        this.verCodeAllowed = true;
      }
    }
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
      _that.verCodeAllowed = false;
      alert("验证码已发送至邮箱！");
    }, function (err) {
      let errResp = JSON.parse(err['_body']);
      console.log(errResp);
      alert("验证码发送失败，请重试！");
      // alert(errResp.message);
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
      alert("注册成功");
      _that.router.navigate(['login']);
    }, function (err) {
      let errResp = JSON.parse(err['_body']);
      console.log(errResp);
      alert(errResp.message);
    });
  }

}
