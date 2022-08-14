import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';
import { User, UserState } from 'src/app/models/user';

/**
 * valid re-enter password
 * @param form 
 */

function passwordsMatchValidator(form: any) {
  const password = form.get('password');
  const confirmPassword = form.get('confirmPassword');

  if (password.value !== confirmPassword.value) {
    confirmPassword.setErrors({ passwordsMatch: true });
  } else {
    confirmPassword.setErrors(null);
  }

  return null;
}

/**
 * If the data is valid return null else return an object
 */
function symbolValidator(control: any) {
  //control = registerForm.get('password')
  if (control.hasError('required')) return null;
  if (control.hasError('minlength')) return null;

  if (control.value.indexOf('@') > -1) {
    return null;
  } else {
    return { symbol: true };
  }
}

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;

  constructor(private builder: FormBuilder, private userService: UserService) { }

  ngOnInit() {
    this.buildForm();
  }

  /**
   * Build the form with inputs
   * - username
   * - password
   * - confirm password
   * - name
   * - email
   */
  buildForm() {
    this.registerForm = this.builder.group(
      {
        name: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        username: ['', Validators.required],
        password: [
          '',
          [Validators.required, symbolValidator, Validators.minLength(4)],
        ],
        confirmPassword: '',
      },
      {
        validators: passwordsMatchValidator,
      }
    );
  }

  /**
   * Register a new user if the form is valid
   * console logs the response from the server
   */
  register() {
    console.log(this.registerForm.value);
    // random user id
    const randomId = Math.floor(Math.random() * 1000000);
    const newUser = new User(randomId, this.registerForm.value.username,
      this.registerForm.value.password, this.registerForm.value.name,
      this.registerForm.value.email, false, UserState.LOGGED_OUT);
    this.userService.createUser(newUser).subscribe((response) => {
      console.log(response);
    });
  }
}
