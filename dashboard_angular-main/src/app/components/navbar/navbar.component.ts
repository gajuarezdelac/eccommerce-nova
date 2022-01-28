import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalService } from 'ng-zorro-antd/modal';
import { User } from 'src/app/models/User';
import { AuthService } from 'src/app/services/auth.service';
import { CartService } from 'src/app/services/cart.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  
  public user: User | undefined;
  public validateForm!: FormGroup;
  public submitted: boolean = false;


  constructor(
    private authenticationService : AuthService,
    private fb: FormBuilder,
    private message: NzMessageService,
    private router: Router,
    private cartService : CartService
  ) { }

  ngOnInit(): void {

    this.validateForm = this.fb.group({
      keyword: [null]
    });

    if(this.authenticationService.isUserLoggedIn()) {
      this.user = this.authenticationService.getUserFromLocalCache();
    }
  }

  public onLogOut(): void {
    this.authenticationService.logOut();
    this.user = undefined;
    this.message.create("success",  "Sesión cerrada de manera correcta!");
  }

  public onSubmit(): void {
    this.submitted = true;
    console.log(this.validateForm.value);
    if(this.validateForm.value.keyword == null && this.validateForm.value.keyword == '') {       
         this.router.navigate(['/search']);
    } else {
      this.router.navigate(['/search'], { queryParams: { keyword: this.validateForm.value.keyword } });
    }   
  }
  
}
