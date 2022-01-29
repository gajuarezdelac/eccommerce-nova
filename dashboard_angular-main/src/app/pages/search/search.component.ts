import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Subscription } from 'rxjs';
import { ProductPaginate, Content } from 'src/app/models/ProductPaginate';
import { AuthService } from 'src/app/services/auth.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

   
  
  // Variables para obtener el listado de productos
  public pageSize: number = 10;
  public current: number = 1;
  public subscriptions: Subscription[] = [];
  public data : Content[] = [];
  public temp : Content[] = [];
  public total: number = 0;
  public totalElementByPage = 0;
  public isLoadingTable = false;

  // 
  
  constructor(
    private authenticationService: AuthService,
    private fb: FormBuilder,
    private message: NzMessageService,
    private router: Router,
    private productService: ProductService
  ) { }

  ngOnInit(): void {
  }


  // ! Listado de registros para llenar la tabla 
  getListPaginate() : void {
    this.isLoadingTable = true;
    this.subscriptions.push(
      this.productService.searchProducts({ numberPage: (this.current - 1), sizePage: this.pageSize}).subscribe(
        (response: ProductPaginate) => {
          this.temp = response.content;
          this.data = response.content;
          this.total = response.totalElements;
          this.totalElementByPage = response.numberOfElements;
          this.isLoadingTable = false;
        },
        (errorResponse: HttpErrorResponse) => {
          this.isLoadingTable = false;
          this.message.create("error",  "Ha ocurrido un error!");
        }
      )
    );
  }

  changePageSize($event: number) : void {
    console.log("Change page size: "  + $event);
    this.pageSize = $event;
    this.getListPaginate();
  }

   changeCurrentPage($event: number) : void {
    console.log("Change page: "  + $event);
    this.current = $event;
    this.getListPaginate();
   }

   // Add to favorite






}
