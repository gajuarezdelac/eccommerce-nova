
import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { AuthService } from 'src/app/services/auth.service';
import { OrderService } from 'src/app/services/order.service';


@Component({
  selector: 'app-orders-control',
  templateUrl: './orders-control.component.html',
  styleUrls: ['./orders-control.component.css']
})
export class OrdersControlComponent implements OnInit {


  public listOfData: ItemData[] = [];

  constructor(
    private authenticationService : AuthService,
    private fb: FormBuilder,
    private message: NzMessageService,
    private router: Router,
    private orderService: OrderService) { }


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