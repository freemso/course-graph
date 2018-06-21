import { Component, OnInit } from '@angular/core';
import { Router, NavigationExtras } from '@angular/router';
import { UserService } from '../../services/user.service';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

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
      type: 'TEACHER'
    };
  }

  setTypeStu() {
    this.user.type = 'STUDENT';
  }

  setTypeTec() {
    this.user.type = 'TEACHER';
  }

  register() {
    console.log("begin to register user: ");
    console.log(this.user);
    this.router.navigate(['login']);

    //与服务器端通信，确认是否注册成功
    this.userservice.register(this.user);

  }
}
