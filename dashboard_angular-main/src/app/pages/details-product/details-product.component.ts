import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Subscription } from 'rxjs/internal/Subscription';
import { Product } from 'src/app/models/Product';
import { Content } from 'src/app/models/Review';
import { AuthService } from 'src/app/services/auth.service';
import { ProductService } from 'src/app/services/product.service';
import { ReviewService } from 'src/app/services/review.service';

@Component({
  selector: 'app-details-product',
  templateUrl: './details-product.component.html',
  styleUrls: ['./details-product.component.css']
})
export class DetailsProductComponent implements OnInit {



  // Variables para visualizar el producto
  public subscriptions : Subscription[] = [];
  public element : Product = new Product();
  public isLoadingView = false;
  public isLoadingReview = false;

  // Reviews
  public lstReviews : Content[] = [];

  // Agregar review
  public createForm! : FormGroup;

  // Editar review
  public editForm! : FormGroup;
  
  constructor(
    private authenticationService : AuthService,
    private fb: FormBuilder,
    private message: NzMessageService,
    private router: Router,
    private productService: ProductService,
    private reviewService: ReviewService
  ) { }

   ngOnInit(): void {

    this.createForm = this.fb.group({
      code: ['', Validators.required],
      name: ['', Validators.required],
      price: [0, Validators.required],
    });

    this.editForm = this.fb.group({
      code: ['', Validators.required],
      name: ['', Validators.required],
      price: [0, Validators.required],
    });

   }

  // Get Product
  getElementById(id : string) : void {
    this.isLoadingView = true;
    this.subscriptions.push(
      this.productService.getProductById(id).subscribe(
        (response: Product) => {
          this.element = response;
          this.isLoadingView = false;
        },
        (errorResponse: HttpErrorResponse) => {
          this.isLoadingView = false;
          this.message.create("error",  "Ha ocurrido un error!");
        }
      )
    );
  }

  // Get all reviews by code
  public getAllReviews(id : string) : void {
    this.isLoadingView = true;
    this.subscriptions.push(
      this.reviewService.getAllReviews().subscribe(
        (response: Content[]) => {
          this.lstReviews = response;
          this.isLoadingView = false;
        },
        (errorResponse: HttpErrorResponse) => {
          this.isLoadingView = false;
          this.message.create("error",  "Ha ocurrido un error!");
        }
      )
    );
  }

  // Add new review
  public addReview() : void {

    this.isLoadingReview = true;
    if(!this.createForm.valid) {
      return ; 
    }

    let form = this.createForm.value;
    const formData = this.productService.createFormDate('', form); 

    this.subscriptions.push(
      this.reviewService.createReview(form).subscribe(
        (response: Content) => {
          this.message.create("success",  "Ha ocurrido un error!");
          this.isLoadingReview = false;
        },
        (errorResponse: HttpErrorResponse) => {
          this.isLoadingReview = false;
          this.message.create("error",  "Ha ocurrido un error!");
        }
      )
    );
  }

  public editReview() : void {
    
    this.isLoadingReview = true;
    if(!this.createForm.valid) {
      return ; 
    }

    let form = this.editForm.value;
    const formData = this.productService.createFormDate('', form); 

    this.subscriptions.push(
      this.reviewService.updateReview(form).subscribe(
        (response: Content) => {
          this.message.create("success",  "Ha ocurrido un error!");
          this.isLoadingReview = false;
        },
        (errorResponse: HttpErrorResponse) => {
          this.isLoadingReview = false;
          this.message.create("error",  "Ha ocurrido un error!");
        }
      )
    );
  }

  // Delete review by user

  public deleteReviewByUSer() : void {

    this.isLoadingReview = true;
    if(!this.createForm.valid) {
      return ; 
    }

    let form = this.createForm.value;
    const formData = this.productService.createFormDate('', form); 

    this.subscriptions.push(
      this.reviewService.createReview(form).subscribe(
        (response: Content) => {
          this.message.create("success",  "Ha ocurrido un error!");
          this.isLoadingReview = false;
        },
        (errorResponse: HttpErrorResponse) => {
          this.isLoadingReview = false;
          this.message.create("error",  "Ha ocurrido un error!");
        }
      )
    );
  }






  // Add cart
  public AddElement(e : any) {

  }


}
