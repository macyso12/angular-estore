import { Component, OnInit, Input } from '@angular/core';

import { ReviewService } from 'src/app/services/review.service';
import { Review } from '../../models/review';
import { CurrentUserService } from 'src/app/services/current-user.service';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';
@Component({
  selector: 'app-written-review',
  templateUrl: './written-review.component.html',
  styleUrls: ['./written-review.component.css'],
})
export class WrittenReviewComponent implements OnInit {
  @Input() productId?: number;
  model: any = {};
  currentUser?: User | null;
  username = "";
  reviews: Review[] = [];
  printReviews: String[] = [];
  
  constructor(
    private reviewService: ReviewService,
    public currentUserService: CurrentUserService,
    private userService: UserService,
  ) { }

  ngOnInit(): void {
    this.reviewService.getReviews().subscribe(reviews => {
      if (this.productId) {
        this.reviews = reviews.filter(review => review.productId == this.productId);
      }
      this.reviews.forEach(review => {
        this.userService.getUser(review.userId).subscribe(user => {
          this.printReviews.push(`${user.username} - ${review.rating} - ${review.comment}`);
          console.log( user.username + ": " + review.comment);
        });
      });
      console.log("from ngOnInit: ", this.reviews.length);
    });

    if (this.currentUserService.isExistUser()) {
      this.currentUser = this.currentUserService.getCurrentUser();
      if (this.currentUser) {
        this.username = this.currentUser.username;
      }
    }
  }

  add(comment: string, rating: string): void {
    let ratingNum = parseInt(rating, 10);
    console.log("add review", comment);
    if (this.productId) {
      let newReview = new Review(13, this.productId, this.currentUser!.id, ratingNum, comment);
      this.reviewService.createReview(newReview).subscribe((review) => {
        this.ngOnInit();
        window.location.reload();
      });
    }
  }
}
