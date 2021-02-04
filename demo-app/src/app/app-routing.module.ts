import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainCompComponent } from "./main-comp/main-comp.component";


const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'maincomp'
  },
  {
    path: 'maincomp',
    component: MainCompComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
