import {Component, OnInit} from '@angular/core';
import {Router, NavigationExtras} from '@angular/router';
import {StorageService} from '../../services/storage.service';
import {AuthenticationService} from '../../services/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: any = {};
  mailAllowed = false;
  pwdAllowed = false;

  constructor(private authentication: AuthenticationService,
              private router: Router,
              private storage: StorageService) {
  }

  ngOnInit() {
    if (this.storage.getItem('curUser')) {
      this.storage.removeItem('curUser');
    }
    this.user = {
      email: '',
      password: ''
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


  login() {
    console.log('begin to login: ');
    console.log(this.user);

    //删除上一个token
    if (this.storage.getItem('token')) {
      this.storage.removeItem('token');
    }

    let _that = this;
    this.authentication.login(this.user).subscribe(function (suc) {
      let sucResp = JSON.parse(suc['_body']);
      console.log('log in resp:');
      console.log(sucResp);

      console.log('set token to localStorage: ');
      let token = sucResp.token;
      _that.storage.setItem('token', token);

      console.log('log in success, store user');
      let user = sucResp.user;
      console.log(user);
      _that.storage.setItem('curUser', user);
      _that.router.navigate(['courselist']);

    }, function (err) {
      let errResp = JSON.parse(err['_body']);
      console.log('login error');
      console.log(errResp);
      let message = '用户名或密码错误';
      alert(message);
    });
  }

}
