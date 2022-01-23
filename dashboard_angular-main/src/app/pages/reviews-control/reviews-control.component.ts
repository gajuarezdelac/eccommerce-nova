import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { AuthService } from 'src/app/services/auth.service';
import { ReviewService } from 'src/app/services/review.service';

@Component({
  selector: 'app-reviews-control',
  templateUrl: './reviews-control.component.html',
  styleUrls: ['./reviews-control.component.css']
})
export class ReviewsControlComponent implements OnInit {


  public listOfData: ItemData[] = [];

  constructor(
    private authenticationService : AuthService,
    private fb: FormBuilder,
    private message: NzMessageService,
    private router: Router,
    private reviewService: ReviewService
  ) { }


  ngOnInit(): void {
    const data = [];
    for (let i = 0; i < 100; i++) {
      data.push({
        name: `Edward King ${i}`,
        age: 32,
        address: `London, Park Lane no. ${i}`
      });
    }
    this.listOfData = data;
  }

}

interface ItemData {
  name: string;
  age: number;
  address: string;
}