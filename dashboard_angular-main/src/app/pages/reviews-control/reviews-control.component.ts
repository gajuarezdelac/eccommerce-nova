import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-reviews-control',
  templateUrl: './reviews-control.component.html',
  styleUrls: ['./reviews-control.component.css']
})
export class ReviewsControlComponent implements OnInit {


  public listOfData: ItemData[] = [];

  constructor() { }


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