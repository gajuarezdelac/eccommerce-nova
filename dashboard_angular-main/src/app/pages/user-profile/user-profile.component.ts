import { HttpErrorResponse, HttpEvent, HttpEventType } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Subscription } from 'rxjs';
import { FileUploadStatus } from 'src/app/models/file-upload-status';
import { User } from 'src/app/models/User';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  // Variables globales

  public subscriptions: Subscription[] = [];

  // Variables para actualizar la imagen de perfil
  public fileName!: string | null;
  public profileImage!: File | null;
  public fileStatus = new FileUploadStatus();

  // Variables para actualizar la información del usuario logueado.
  public editForm! : FormGroup;
  public submitted = true;
  public btnloader = false;
  
  public user: User | undefined;
  public currentUsername : User | undefined;

  public isLoadingEdit = false;
  public isLoadingView = false;

  constructor(
    private authenticationService: AuthService,
    private fb: FormBuilder,
    private message: NzMessageService,
    private router: Router,
    private userService: UserService,
    
  ) { }

  ngOnInit(): void {


    if(this.authenticationService.isUserLoggedIn()) {
      this.user = this.authenticationService.getUserFromLocalCache();
    } else {  
      this.router.navigateByUrl("/home");
    }

    this.editForm = this.fb.group({
      currentUsername: ['', Validators.required],
      lastName: ['', Validators.required],
      firstName: ['', Validators.required],
    });
  }

  
  get f() { return this.editForm.controls; }


  getUerByUsername() {

    this.isLoadingView = true;

    this.subscriptions.push(
      this.userService.getByUsername(this.user!.username).subscribe(
        (response: User) => {
          this.currentUsername = response;
          this.isLoadingView = false;
        },
        (errorResponse: HttpErrorResponse) => {
          this.isLoadingView = false;
          this.message.create("error",  "Ha ocurrido un error!");
        }
      )
    );
  }

  // Actualizar información del usuario
  onSubmit() {
    
    if (this.editForm.invalid) { return; }
    this.btnloader = true;
    this.isLoadingEdit = true;
    let form = this.editForm.value;
    
    this.subscriptions.push(
      this.userService.updateProfile(this.user?.username!, form).subscribe(
        (response: User) => {
          this.btnloader = false;
          this.isLoadingEdit = false;
          this.message.create("success",  "Perfil actualizado correctamnete!");
        },
        (errorResponse: HttpErrorResponse) => {
          this.btnloader = false;
          this.isLoadingEdit = false;
          this.message.create("error",  "Ha ocurrido un error!");
        }
      )
    );
  }

  
  public onUpdateProfileImage(): void {
    const formData = new FormData();
    formData.append('username', this.user!.username);
    formData.append('profileImage', this.profileImage!);
    this.subscriptions.push(
      this.userService.updateProfileImage(formData).subscribe(
        (event: HttpEvent<any>) => {
          this.reportUploadProgress(event);
        },
        (errorResponse: HttpErrorResponse) => {
          this.fileStatus.status = 'done';
        }
      )
    );
  }

  
  private reportUploadProgress(event: HttpEvent<any>): void {
    switch (event.type) {
      case HttpEventType.UploadProgress:
        this.fileStatus.percentage = Math.round(100 * event.loaded / event.total!);
        this.fileStatus.status = 'progress';
        break;
      case HttpEventType.Response:
        if (event.status === 200) {
          this.user!.profileImageUrl = `${event.body.profileImageUrl}?time=${new Date().getTime()}`;
          this.fileStatus.status = 'done';
          break;
        } else {
          this.message.create("error",  "Ha ocurrido un error!");
          break;
        }
      default:
        `Finished all processes`;
    }
  }

  public updateProfileImage(): void {
    this.clickButton('profile-image-input');
  }


  private clickButton(buttonId: string): void {
    // Puede llegar null
    document.getElementById(buttonId)?.click();
  }




}
