import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TestAuthorityComponent } from './test-authority.component';

describe('TestAuthorityComponent', () => {
  let component: TestAuthorityComponent;
  let fixture: ComponentFixture<TestAuthorityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TestAuthorityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TestAuthorityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
