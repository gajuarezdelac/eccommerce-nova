import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalService } from 'ng-zorro-antd/modal';
import { Subscription } from 'rxjs';
import { Product } from 'src/app/models/Product';
import { User } from 'src/app/models/User';
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
  public user : User | undefined;
  public isLoadingView = false;
  public lstProducts : Product[] = [];
  public subscriptions : Subscription[] = [];
  

  constructor(
    private authenticationService : AuthService,
    private fb: FormBuilder,
    private message: NzMessageService,
    private router: Router,
    private productService: ProductService,
    private wishService : WishesService,
    ) { }

  ngOnInit(): void {

    if(this.authenticationService.isUserLoggedIn()) {
      this.user = this.authenticationService.getUserFromLocalCache();
    }
    
    this.wishService.setProduct(JSON.parse(localStorage.getItem('MyWhises_Cotta') || '[]'));
    
    this.wishService.getWhishes()
    .subscribe(res=>{
      this.products = res;
      this.getProductsByIds(this.products);
    });
    
  }

  // Get all favorites
  public getProductsByIds(lst : any) {


    let newLst : any[] = [];
    lst.forEach((product : any) => { newLst.push(product.id);});


    this.subscriptions.push(
      this.productService.getAllProductsById(newLst).subscribe(
        (response: Product[]) => {
          this.isLoadingView = false;
          this.lstProducts = response;
          console.log();
        },
        (errorResponse: HttpErrorResponse) => {
          this.isLoadingView = false;
          this.message.create("error",  errorResponse.error.message);
        }
      )
    );
  }





  // delete favorite





}
