import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { Subscription } from 'rxjs';
import { Order } from 'src/app/models/Order';
import { User } from 'src/app/models/User';
import { AddressService } from 'src/app/services/address.service';
import { AuthService } from 'src/app/services/auth.service';
import { CartService } from 'src/app/services/cart.service';
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
  public user : User | undefined;

  // ! Datos para calcular el totoal entre muchas otras cosas mas
    public products : any = [];
    public grandTotal !: number;
    public grandTotalDiscount! : number;


    // ! Datos de la dirección
   public selectAddress : any;
  

  constructor(
    private authenticationService : AuthService,
    private fb: FormBuilder,
    private message: NzMessageService,
    private router: Router,
    private productService: ProductService,
    private cartService: CartService,
    private orderService: OrderService,
    private addressService: AddressService,
    private notification: NzNotificationService
  ) { }

  ngOnInit(): void {

    if(this.authenticationService.isUserLoggedIn()) {
      this.user = this.authenticationService.getUserFromLocalCache();
    }

    this.cartService.getProducts()
    .subscribe(res=>{
      this.products = res;
      this.grandTotal = this.cartService.getTotalPrice();

      if(this.grandTotal == 0){ 
        this.router.navigateByUrl("/home");
        this.createMessage("warning",  "No tienes ningún producto en tu carrito");
      }

      this.grandTotalDiscount = this.cartService.getTotalDiscount();
    });

    this.selectAddress = this.addressService.getAddressFromLocalCache();
  }


    //  ! Calculate discount total que se ha aplicado
    public calculateDiscount(total: number, discount: number) {
      return total - discount;
    }
  
  
    // Obtener el costo del envio
    public cost() {
      let envio = 0;
      if(this.selectAddress.typeSend == '1'){ envio = 179;}else {envio = 279;}
      return envio;
    }
  
    // ! Calcular el total mas el envio
    public calculateTotal(amount : number) { 
      let envio = 0;
      if(this.selectAddress.typeSend == '1'){ envio = 179;}else {envio = 279;}
      return amount + envio;
    }
  



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

   createNotification(type: string, message: string): void {
    this.notification.create(
      type,
      'Excelente!',
      `${message} 😀`,
      { nzPlacement: 'bottomLeft' }
    );
  }

  createMessage(type: string, message: string): void {
    this.message.create(type, message);
  }





}
