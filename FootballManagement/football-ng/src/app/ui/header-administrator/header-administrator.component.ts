import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from '../../ApiService/auth-login/auth.service';
import {TokenStorageService} from '../../ApiService/auth-login/token-storage.service';

@Component({
  selector: 'app-header-administrator',
  templateUrl: './header-administrator.component.html',
  styleUrls: ['./header-administrator.component.css']
})
export class HeaderAdministratorComponent implements OnInit {

  constructor(private router: Router,
              private authService: AuthService,
              private tokenStorage: TokenStorageService) { }

  ngOnInit() {
  }
  logout() {
    this.tokenStorage.signOut();
    this.router.navigate(['/', ]);
  }
}
