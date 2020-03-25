import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

import { ManageComponent, ProjectComponent, LoginComponent } from './components';
import { UserComponent } from './pages';
import { SharedModule } from '../shared/shared.module';
import { AuthGuard } from '../shared/guards/auth.guard';

const ROUTES: Routes = [
  {
    path: '',
    component: UserComponent,
    children: [
      {
        path: '',
        component: LoginComponent
      },
      {
        path: 'manage',
        component: ManageComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'project',
        component: ProjectComponent,
        canActivate: [AuthGuard]
      }
    ]
  }
];

@NgModule({
  declarations: [
    UserComponent,
    ProjectComponent,
    ManageComponent,
    LoginComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(ROUTES),
  ]
})
export class UserModule { }
