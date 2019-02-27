import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderAdministratorComponent } from './header-administrator.component';

describe('HeaderAdministratorComponent', () => {
  let component: HeaderAdministratorComponent;
  let fixture: ComponentFixture<HeaderAdministratorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HeaderAdministratorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HeaderAdministratorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
