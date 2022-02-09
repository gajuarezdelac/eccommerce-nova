import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Address } from 'src/app/models/Address';
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
  public isLoadingSave = false;

  // Seleccionar dirección
  public selectAddress : Address | undefined = undefined;

  constructor(
    private authenticationService : AuthService,
    private fb: FormBuilder,
    private message: NzMessageService,
    private router: Router,
    private productService: ProductService,
    private cartService: CartService,
  ) { }

  ngOnInit(): void {
    
    this.createAddress = this.fb.group({
      names: [null, Validators.required],
      surnames: [null, Validators.required],
      isYourHome: [true, Validators.required],
      cp: [null, Validators.required],
      phone: [null, Validators.required],
      email: [null, Validators.required, Validators.email],
      calle: [null, Validators.required],
      colonia: [null, Validators.required],
      noExternal: [null, Validators.required],
      noInternal: [null, Validators.required],
      moreInformation: [null, Validators.required]
    });
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

    let form = this.createAddress.value;
  }
}
