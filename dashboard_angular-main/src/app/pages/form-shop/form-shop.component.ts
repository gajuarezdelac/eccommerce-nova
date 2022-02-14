import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { Subscription } from 'rxjs';
import { Address } from 'src/app/models/Address';
import { AddressService } from 'src/app/services/address.service';
import { AuthService } from 'src/app/services/auth.service';
import { CartService } from 'src/app/services/cart.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-form-shop',
  templateUrl: './form-shop.component.html',
  styleUrls: ['./form-shop.component.css']
})
export class FormShopComponent implements OnInit {

  // Get all address
  public lstAddresses : Address[] = [];
  public isLoadingGet = false;

  // Agregar dirección de entrega
  public createAddress! : FormGroup;
  @ViewChild('e') editNgForm: NgForm | undefined;
  public isLoadingSave = false;

  // Seleccionar dirección
  public selectAddress : any;

  // Datos para calcular el total
  public products : any = [];
  public grandTotal !: number;
  public grandTotalDiscount! : number;
  public subscriptions : Subscription[] = [];
  
  constructor(
    private authenticationService : AuthService,
    private fb: FormBuilder,
    private message: NzMessageService,
    private router: Router,
    private productService: ProductService,
    private cartService: CartService,
    private addressService: AddressService,
    private notification: NzNotificationService
  ) { }

  ngOnInit(): void {

    
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
    
    this.createAddress = this.fb.group({
      names: ['', Validators.required],
      surnames: ['', Validators.required],
      cp: ['', Validators.required],
      phone: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      calle: ['', Validators.required],
      colonia: ['', Validators.required],
      noExternal: ['', Validators.required],
      noInternal: ['', Validators.required],
      // city: ['', Validators.required],
      // state: ['', Validators.required],
      moreInformation: ['', Validators.required],
      typeSend: ['1'],
    });

    this.fillAddress(this.selectAddress);
  }


  createSubmit () {

    for (const i in this.createAddress.controls) {
      if (this.createAddress.controls.hasOwnProperty(i)) {
        this.createAddress.controls[i].markAsDirty();
        this.createAddress.controls[i].updateValueAndValidity();
      }
    }

    
    if(!this.createAddress.valid) {
      return ; 
    }

    this.isLoadingSave = true;
    let form = this.createAddress.value;
    this.addressService.addAddressToLocalStorage({ userId: "" ,calculateDiscount: this.calculateDiscount(this.grandTotalDiscount,this.grandTotal) , total:this.calculateTotal(this.grandTotal), envio: this.cost(),...form});
    // this.createNotification("success","Dirección guardada correctamente");
    this.router.navigateByUrl('/payment');
    this.isLoadingSave = false;
  }


  //  ! Calculate discount total que se ha aplicado
  public calculateDiscount(total: number, discount: number) {
    return total - discount;
  }


  // Obtener el costo del envio
  public cost() {
    let envio = 0;
    if(this.createAddress.value['typeSend'] == '1'){ envio = 179;}else {envio = 279;}
    return envio;
  }

  // ! Calcular el total mas el envio
  public calculateTotal(amount : number) { 
    let envio = 0;
    if(this.createAddress.value['typeSend'] == '1'){ envio = 179;}else {envio = 279;}
    return amount + envio;
  }
  
  createMessage(type: string, message: string): void {
    this.message.create(type, message);
  }


  fillAddress(data : any) {

    this.createAddress = this.fb.group({
      names: [data.names, Validators.required],
      surnames: [data.surnames, Validators.required],
      cp: [data.cp, Validators.required],
      phone: [data.phone, Validators.required],
      email: [data.email,[Validators.required, Validators.email]],
      calle: [data.calle, Validators.required],
      colonia: [data.colonia, Validators.required],
      noExternal: [data.noExternal, Validators.required],
      noInternal: [data.noInternal, Validators.required],
      moreInformation: [data.moreInformation, Validators.required],
      typeSend: ['1', Validators.required],
    });
  }


  createNotification(type: string, message: string): void {
    this.notification.create(
      type,
      'Excelente!',
      `${message} 😀`,
      { nzPlacement: 'bottomLeft' }
    );
  }



}
