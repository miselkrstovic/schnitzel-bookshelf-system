import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookRemoveComponent } from './book-remove.component';

describe('BookRemoveComponent', () => {
  let component: BookRemoveComponent;
  let fixture: ComponentFixture<BookRemoveComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BookRemoveComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BookRemoveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
