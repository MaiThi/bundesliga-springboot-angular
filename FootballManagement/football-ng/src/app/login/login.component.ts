import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../ApiService/auth-login/auth.service';
import {AuthLoginInfo} from '../ApiService/auth-login/login-info';
import {TokenStorageService} from '../ApiService/auth-login/token-storage.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: any = {};
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  private loginInfo: AuthLoginInfo;
  private formSubmitAttempt: boolean;

  @Input() fileUpload: string;

  constructor( private router: Router,
               private authService: AuthService,
               private tokenStorage: TokenStorageService) { }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getAuthorities();
    }
  }
   isFieldInvalid(field: string) {
     return (
      (!this.form.get(field).valid && this.form.get(field).touched) ||
       (this.form.get(field).untouched && this.formSubmitAttempt)
     );
   }
   onSubmit() {
     console.log(this.form);

     this.loginInfo = new AuthLoginInfo(
       this.form.username,
       this.form.password);

     this.authService.attemptAuth(this.loginInfo).subscribe(
       data => {
         this.tokenStorage.saveToken(data.accessToken);
         this.tokenStorage.saveUsername(data.username);
         this.tokenStorage.saveAuthorities(data.authorities);

         this.isLoginFailed = false;
         this.isLoggedIn = true;
         this.roles = this.tokenStorage.getAuthorities();
         this.router.navigate(['/teamAdmin', ]);
       },
       error => {
         console.log(error);
         this.errorMessage = error.error.message;
         this.isLoginFailed = true;
       }
     );
   }
    logout() {
      this.tokenStorage.signOut();
      window.location.reload();
    }

  reloadPage() {
    window.location.reload();
  }
}
