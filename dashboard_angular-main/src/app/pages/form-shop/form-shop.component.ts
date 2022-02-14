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
  public selectAddress : Address | undefined = undefined;

  // Datos para calcular el total
  public products : any = [];
  public grandTotal !: number;
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
    this.addressService.addAddressToLocalStorage(form);
    this.createNotification("success","Dirección guardada correctamente");
    this.isLoadingSave = false;
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
