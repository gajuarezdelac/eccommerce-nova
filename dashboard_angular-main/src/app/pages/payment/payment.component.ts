import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Subscription } from 'rxjs';
import { Order } from 'src/app/models/Order';
import { AuthService } from 'src/app/services/auth.service';
import { OrderService } from 'src/app/services/order.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {


  public isLoadingCreate = true;
  public subscriptions : Subscription[] = [];


  constructor(
    private authenticationService: AuthService,
    private fb: FormBuilder,
    private message: NzMessageService,
    private router: Router,
    private productService: ProductService,
    private orderService: OrderService
  ) { }

  ngOnInit(): void {}


   // Create order
   public createOrder(): void {
    this.isLoadingCreate = true;

    this.subscriptions.push(
      this.orderService.createOrder({}).subscribe(
        (response: Order) => {
          this.message.create("success",  "Tu orden se genero de manera correcta");
          this.isLoadingCreate = false;
        },
        (errorResponse: HttpErrorResponse) => {
          this.isLoadingCreate = false;
          this.message.create("error",  "Ha ocurrido un error!");
        }
      )
    );
   }

   





}
