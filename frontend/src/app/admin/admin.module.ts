import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

import { SharedModule } from '../shared/shared.module';
import { AuditComponent } from './components';
import { AdminComponent } from './pages';

const ROUTES: Routes = [
  {
    path: 'admin',
    component: AdminComponent,
    children: [
      {
        path: '',
        component: AuditComponent
      }
    ]
  }
];

@NgModule({
  declarations: [
    AdminComponent,
    AuditComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(ROUTES),
  ]
})
export class AdminModule { }
