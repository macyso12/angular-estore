import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WrittenReviewComponent } from './written-review.component';

describe('WrittenReviewComponent', () => {
  let component: WrittenReviewComponent;
  let fixture: ComponentFixture<WrittenReviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WrittenReviewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WrittenReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
