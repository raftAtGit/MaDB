import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

import { ProjectComponent } from './project/project.component';
import { SharedModule } from '../shared/shared.module';
import { UserComponent } from './user/user.component';

const ROUTES: Routes = [
  {
    path: '',
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
    ProjectComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(ROUTES),
  ]
})
export class UserModule { }
