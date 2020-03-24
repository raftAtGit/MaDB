import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

import { ManageComponent, ProjectComponent, UserComponent } from './components';
import { SharedModule } from '../shared/shared.module';

const ROUTES: Routes = [
  {
    path: '',
    component: ManageComponent
  },
  {
    path: 'user',
    component: UserComponent
  },
  {
    path: 'project',
    component: ProjectComponent
  }
];

@NgModule({
  declarations: [
    UserComponent,
    ProjectComponent,
    ManageComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(ROUTES),
  ]
})
export class UserModule { }
