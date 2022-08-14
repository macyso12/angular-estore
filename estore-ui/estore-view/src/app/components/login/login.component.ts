import { Component, OnInit} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import { User, UserState } from 'src/app/models/user';
import { CurrentUserService } from 'src/app/services/current-user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  message: string = '';
  users: User[] = [];
  currentUser!: User;

  ngOnInit(): void {
    this.buildForm();
  }
  constructor(
    private router: Router,
    private builder: FormBuilder,
    private userService: UserService,
    private currentUserService: CurrentUserService
  ) { }

  buildForm() {
    this.loginForm = this.builder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  login() {
    let username: string = this.loginForm.value.username;
    this.userService.searchUsers(username).subscribe((data: any) => {
      this.users = data;
      if (this.users.length == 0) this.message = 'User not found';
      else {
        for (let i = 0; i < this.users.length; i++) {
          if (this.users[i].username == username) {
            if (this.users[i].password == this.loginForm.value.password) {
              this.currentUser = new User(
                this.users[i].id,
                this.users[i].username,
                this.users[i].password,
                this.users[i].name,
                this.users[i].email,
                this.users[i].isAdmin,
                this.users[i].userState
              );
              this.userService
                .loginUser(this.currentUser)
                .subscribe((user: any) => {
                  this.currentUser = user;
                  this.currentUserService.saveCurrentUser(this.currentUser); 
                  if (this.currentUser.isAdmin) {
                    this.router.navigate(['./admin']);
                  } else {
                    this.router.navigate(['./shop']);
                  }
                });
            } else this.message = UserState.INCORRECT_PASSWORD;
          } else this.message = UserState.USER_NOT_FOUND;
        }
      }
    });
  }
}
