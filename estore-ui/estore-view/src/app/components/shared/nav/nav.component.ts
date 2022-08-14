import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CurrentUserService } from 'src/app/services/current-user.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css'],
})
export class NavComponent implements OnInit {
  constructor(
    private currentUserService: CurrentUserService,
    private userService: UserService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  logout() {
    const user = this.currentUserService.getCurrentUser();
    if (user) {
      this.userService.logoutUser(user).subscribe((data: any) => {
        console.log(data);
        this.currentUserService.clearCurrentUser();
      }
      );
      this.router.navigate(['./shop']);
    }
  }

  IsUserLoggedIn() {
    return this.currentUserService.isExistUser();
  }
}
