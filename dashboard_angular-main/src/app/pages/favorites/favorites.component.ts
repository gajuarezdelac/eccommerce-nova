import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalService } from 'ng-zorro-antd/modal';
import { AuthService } from 'src/app/services/auth.service';
import { ProductService } from 'src/app/services/product.service';
import { WishesService } from 'src/app/services/wishes.service';

@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.css']
})
export class FavoritesComponent implements OnInit {

  public products : any = [];

  constructor(
    private authenticationService : AuthService,
    private fb: FormBuilder,
    private message: NzMessageService,
    private router: Router,
    private productService: ProductService,
    private wishService : WishesService,
    ) { }

  ngOnInit(): void {

    
    this.wishService.getWhishes()
    .subscribe(res=>{
      this.products = res;
      // this.grandTotal = this.cartService.getTotalPrice();
    });
    
  }

  // Get all favorites


  // delete favorite





}
