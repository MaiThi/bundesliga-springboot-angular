import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-layout-admin',
  templateUrl: '<app-header-administrator></app-header-administrator>'  +
    '<router-outlet></router-outlet>'  +
    '<app-footer></app-footer>',
  styleUrls: ['./layout-admin.component.css']
})
export class LayoutAdminComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
