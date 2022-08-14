import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ShoppingCartComponent } from './components/shopping-cart/shopping-cart.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { PageNotFoundComponent } from './components/shared/page-not-found/page-not-found.component';
import { AdminComponent } from './components/admin/admin.component';
import { ProductDetailsComponent } from './components/shopping-cart/product-list/product-details/product-details.component';
import { AdminEditComponent } from './components/admin/admin-edit/admin-edit.component';

const routes: Routes = [
  { path: '', redirectTo: '/shop', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'shop', component: ShoppingCartComponent },
  { path: 'detail/:id', component: ProductDetailsComponent },
  { path: 'admin/:id', component: AdminEditComponent },
  { path: 'admin', component: AdminComponent },
  { path: '**', component: PageNotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
