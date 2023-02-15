import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BackendSelectComponent } from './backend-select.component';

describe('BackendSelectComponent', () => {
  let component: BackendSelectComponent;
  let fixture: ComponentFixture<BackendSelectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ BackendSelectComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BackendSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
