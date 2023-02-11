import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DomainSelectComponent } from './domain-select.component';

describe('DomainSelectComponent', () => {
  let component: DomainSelectComponent;
  let fixture: ComponentFixture<DomainSelectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DomainSelectComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DomainSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
