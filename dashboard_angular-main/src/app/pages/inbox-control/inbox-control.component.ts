import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Subscription } from 'rxjs';
import { Content, Inbox } from 'src/app/models/Inbox';
import { AuthService } from 'src/app/services/auth.service';
import { InboxService } from 'src/app/services/inbox.service';

@Component({
  selector: 'app-inbox-control',
  templateUrl: './inbox-control.component.html',
  styleUrls: ['./inbox-control.component.css']
})
export class InboxControlComponent implements OnInit {

  // Variables de la tabla
  public pageSize: number = 10;
  public current: number = 1;
  public subscriptions : Subscription[] = [];
  public total: number = 0;

  public data : Content[] = [];
  public temp : Content[] = [];
  public isLoadingTable = false;

  constructor(
    private authenticationService : AuthService,
    private fb: FormBuilder,
    private message: NzMessageService,
    private router: Router,
    private inboxService : InboxService
  ) { }

  ngOnInit(): void {

    if(this.authenticationService.isUserLoggedIn()) {
      this.getListPaginate();
    } else {
       this.router.navigateByUrl("/auth/login");
    } 

  }


  // ! Get list of messages

  getListPaginate() : void {
    this.isLoadingTable = true;
    this.subscriptions.push(
      this.inboxService.getAllMessagesPaginate({ numberPage: (this.current - 1), sizePage: this.pageSize }).subscribe(
        (response: Inbox) => {
          this.temp = response.content;
          this.data = response.content;
          this.total = response.totalElements;
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



  








  // ! View message


  // ! Delete message




}




interface ItemData {
  name: string;
  age: number;
  address: string;
}