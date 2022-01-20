import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NavbarComponent } from './components/navbar/navbar.component';
import { AuthGuard } from './guard/auth.guard';
import { AuthComponent } from './pages/auth/auth.component';
import { LoginComponent } from './pages/auth/login/login.component';
import { RecoveryPasswordComponent } from './pages/auth/recovery-password/recovery-password.component';
import { RegisterComponent } from './pages/auth/register/register.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { DetailsProductComponent } from './pages/details-product/details-product.component';
import { FaqComponent } from './pages/faq/faq.component';
import { FavoritesComponent } from './pages/favorites/favorites.component';
import { FormShopComponent } from './pages/form-shop/form-shop.component';
import { HomeComponent } from './pages/home/home.component';
import { PaymentComponent } from './pages/payment/payment.component';
import { SearchComponent } from './pages/search/search.component';
import { ShopCartComponent } from './pages/shop-cart/shop-cart.component';
import { UserProfileComponent } from './pages/user-profile/user-profile.component';
import { UsersComponent } from './pages/users/users.component';
import { WelcomeComponent } from './pages/welcome/welcome.component';

const routes: Routes = [
  { path: 'welcome', component: WelcomeComponent, children: [
     { path: "payment", component: PaymentComponent },
     { path: "dashboard", component: DashboardComponent },
     { path: "profile", component: FavoritesComponent },
     { path: "users", component: UsersComponent },
     { path: 'welcome', pathMatch: 'full', redirectTo: '/dashboard' },
    ],
  // canActivate: [ AuthGuard ]
  },
  {path: 'auth', component: AuthComponent, children : [
    { path: "login", component: LoginComponent },
    { path: "register", component: RegisterComponent },
    { path: "recovery-password", component: RecoveryPasswordComponent },
    { path: 'auth', pathMatch: 'full', redirectTo: '/login' },
  ]},

  {path: '', component: NavbarComponent, children : [
    {path: 'home', component: HomeComponent },
    {path: 'search', component: SearchComponent },
    {path: 'product/:id', component: DetailsProductComponent },
    { path: 'my-cart' , component: ShopCartComponent },
    { path: 'my-profile' , component: UserProfileComponent },
    { path: "faq",  component: FaqComponent },
    { path: 'check-delivery', component: FormShopComponent },
    { path: 'payment', component: PaymentComponent },
    { path: '', pathMatch: 'full', redirectTo: '/home' },
  ]},
  {path: '**', redirectTo: '',     pathMatch: 'full'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
