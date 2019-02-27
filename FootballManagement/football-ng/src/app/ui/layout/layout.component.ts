import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-layout',
  templateUrl: '<app-header></app-header>'  +
    '<router-outlet></router-outlet>'  +
    '<app-footer></app-footer>',
  styleUrls: ['./layout.component.css']
})
export class LayoutComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
