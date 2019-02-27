import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UnauthorityComponent } from './unauthority.component';

describe('UnauthorityComponent', () => {
  let component: UnauthorityComponent;
  let fixture: ComponentFixture<UnauthorityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UnauthorityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UnauthorityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
