import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Subscription } from 'rxjs';
import { ProductPaginate, Content } from 'src/app/models/ProductPaginate';
import { AuthService } from 'src/app/services/auth.service';
import { ProductService } from 'src/app/services/product.service';
import { SearchService } from 'src/app/services/search.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {


  // Variables para obtener el listado de productos
  public pageSize: number = 12;
  public current: number = 1;
  public subscriptions: Subscription[] = [];
  public products : Content[] = [];
  public temp : Content[] = [];
  public total: number = 0;
  public totalElementByPage = 0;
  public isLoadingTable = false;
  public filterForm! : FormGroup;
  public key : string = "";

  //   
  constructor(
    private authenticationService: AuthService,
    private fb: FormBuilder,
    private message: NzMessageService,
    private route: ActivatedRoute,
    private router: Router,
    private productService: ProductService,
    private searchService: SearchService,
  ) { }

  ngOnInit(): void {

    // this.route.queryParams.subscribe((params: any) => {
    //   if(params.keyword){
    //    this.key = params.keyword; 
    //   }else {
    //     this.key = "";
    //   }
    // });

    this.searchService.getKeyword()
    .subscribe(res=>{
      this.key = res;
      this.getListPaginate();
    });
    

    this.filterForm = this.fb.group({
      category: [''],
      clasification: [''],
      typeClothing: [''],
      minPrice: [0],
      maxPrice: [100000],
    });

  }

  // ! Listado de registros para llenar la tabla 
  getListPaginate() : void {
    this.isLoadingTable = true;
    this.subscriptions.push(
      this.productService.searchProducts({ numberPage: (this.current - 1), sizePage: this.pageSize, keyword: this.key}).subscribe(
        (response: ProductPaginate) => {
          this.temp = response.content;
          this.products = response.content;
          this.total = response.totalElements;
          this.totalElementByPage = response.numberOfElements;
          this.onActivate();
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
    this.current = $event;
    this.getListPaginate();
   }


  //  ! Agregar a favoritos
  addFavoritos($event: string) : void {
    this.message.create("success",  "Agregado correctamente!");    
  }


   
  onActivate() {
    // window.scroll(0,0);
 
    window.scroll({ 
            top: 0, 
            left: 0, 
            behavior: 'smooth' 
     });
 
     
 }

   // Add to favorite






}
