import { HttpErrorResponse } from '@angular/common/http';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
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
import { IPayPalConfig, ICreateOrderRequest } from 'ngx-paypal';

declare var paypal: any;

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css'],
})
export class PaymentComponent implements OnInit {
  public payPalConfig?: IPayPalConfig;
  public isLoadingCreate = false;
  public subscriptions: Subscription[] = [];
  public user: User | undefined;

  // ! Datos para calcular el totoal entre muchas otras cosas mas
  public products: any = [];
  public grandTotal!: number;
  public grandTotalDiscount!: number;

  // ! Datos de la dirección
  public selectAddress: any;

  @ViewChild('paypal', { static: true }) paypalElement: ElementRef | undefined;

  constructor(
    private authenticationService: AuthService,
    private fb: FormBuilder,
    private message: NzMessageService,
    private router: Router,
    private productService: ProductService,
    private cartService: CartService,
    private orderService: OrderService,
    private addressService: AddressService,
    private notification: NzNotificationService
  ) {}

  ngOnInit(): void {

    this.isLoadingCreate = true;
    this.selectAddress = this.addressService.getAddressFromLocalCache();

    if (this.authenticationService.isUserLoggedIn()) {
      // this.initConfig();
      this.user = this.authenticationService.getUserFromLocalCache();
    }

    this.cartService.getProducts().subscribe((res) => {
      this.products = res;
      this.grandTotal = this.cartService.getTotalPrice();
      this.grandTotalDiscount = this.cartService.getTotalDiscount();
    });

    if (this.grandTotal == 0) {
      this.router.navigateByUrl('/home');
      this.createMessage('warning', 'No tienes ningún producto en tu carrito');
    }

    paypal
      .Buttons({
        createOrder: (data: any, actions: any) => {
          return actions.order.create({
            purchase_units: [
              {
                description: 'Cotta Store',
                amount: {
                  currency_code: 'MXN',
                  value: this.calculateTotal(this.grandTotal),
                },
              },
            ],
          });
        },
        onApprove: async (data: any, actions: any) => {
          const order = await actions.order.capture();
          console.log(order);

          this.isLoadingCreate = true;


        },
        onError: (err: any) => { 
          this.router.navigateByUrl('/error');
        },
      })
      .render(this.paypalElement?.nativeElement);
      
      setTimeout(() => {
        this.isLoadingCreate = false;
      }, 2500)

  }

  //  ! Calculate discount total que se ha aplicado
  public calculateDiscount(total: number, discount: number) {
    return total - discount;
  }

  // Obtener el costo del envio
  public cost() {
    let envio = 0;
    if (this.selectAddress.typeSend == '1') {
      envio = 179;
    } else {
      envio = 279;
    }
    return envio;
  }

  // ! Calcular el total mas el envio
  public calculateTotal(amount: number) {
    let envio = 0;
    if (this.selectAddress.typeSend == '1') {
      envio = 179;
    } else {
      envio = 279;
    }
    return amount + envio;
  }

  // Create order
  public createOrder(): void {
    this.isLoadingCreate = true;

    let data = {
      address: {
        calle: 'string',
        colonia: 'string',
        cp: 'string',
        details: 'string',
        email: 'string',
        id: 'string',
        names: 'string',
        noExterior: 'string',
        noInterior: 'string',
        phone: 'string',
        surnames: 'string',
        town: 'string',
        typeSend: 'string',
        userId: 'string',
      },
      list: [
        {
          cantd: 0,
          code: 'string',
          discount: 0,
          name: 'string',
          price: 0,
          productId: 'string',
          size: 'string',
        },
      ],
      order: {
        subtotal: 0,
        total: 0,
        typeSend: 'string',
        userId: 'string',
      },
    };

    this.subscriptions.push(
      this.orderService.createOrder(data).subscribe(
        (response: Order) => {
          
          this.message.create(
            'success',
            'Tu orden se genero de manera correcta'
          );

          
          setTimeout(() => {
            this.isLoadingCreate = false;
            this.router.navigateByUrl('/success');
            }, 3000)

        },
        (errorResponse: HttpErrorResponse) => {
          this.isLoadingCreate = false;
          this.message.create('error', 'Ha ocurrido un error!');
        }
      )
    );
  }

  private initConfig(): void {
    this.payPalConfig = {
      currency: 'MXN',
      clientId:
        'AakILISfnWYBR4jFCfkqvtAgS_94wWo98Noc6i3Tkl6LpWjTDlUxMEOBcF_fnmCF6ePTRaCiJ37SUawe',
      createOrderOnClient: (data) =>
        <ICreateOrderRequest>{
          intent: 'CAPTURE',
          purchase_units: [
            {
              amount: {
                currency_code: 'MXN',
                value: '1999.99',
                breakdown: {
                  item_total: {
                    currency_code: 'MXN',
                    value: '1999.99',
                  },
                },
              },
              items: [
                {
                  name: 'Enterprise Subscription',
                  quantity: '1',
                  category: 'DIGITAL_GOODS',
                  unit_amount: {
                    currency_code: 'MXN',
                    value: '1999.99',
                  },
                },
              ],
            },
          ],
        },
      advanced: {
        commit: 'true',
      },
      style: {
        label: 'paypal',
        layout: 'vertical',
      },
      onApprove: (data, actions) => {
        console.log(
          'onApprove - transaction was approved, but not authorized',
          data,
          actions
        );
        alert('En proceso');

        actions.order.get().then((details: any) => {
          console.log(
            'onApprove - you can get full order details inside onApprove: ',
            details
          );
          alert('Se encuentra aprobada');
        });
      },
      onClientAuthorization: (data) => {
        console.log(
          'onClientAuthorization - you should probably inform your server about completed transaction at this point',
          data
        );
      },
      onCancel: (data, actions) => {
        console.log('OnCancel', data, actions);
      },
      onError: (err) => {
        console.log('OnError', err);
      },
      onClick: (data, actions) => {
        console.log('onClick', data, actions);
      },
    };
  }

  createNotification(type: string, message: string): void {
    this.notification.create(type, 'Excelente!', `${message} 😀`, {
      nzPlacement: 'bottomLeft',
    });
  }

  createMessage(type: string, message: string): void {
    this.message.create(type, message);
  }
}
