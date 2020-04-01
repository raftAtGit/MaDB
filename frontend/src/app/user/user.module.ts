import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

import { ManageComponent, ProjectComponent, LoginComponent,
  ThemeComponent, FundingComponent, BudgetComponent,
  PartnershipComponent, BeneficiaryComponent, ContactComponent, CountryComponent
} from './components';
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
      },
      {
        path: 'themes',
        component: ThemeComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'budgets',
        component: BudgetComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'funding-sources',
        component: FundingComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'contacts',
        component: ContactComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'countries',
        component: CountryComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'beneficiaries',
        component: BeneficiaryComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'partnerships',
        component: PartnershipComponent,
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
    LoginComponent,
    ThemeComponent,
    FundingComponent,
    BudgetComponent,
    ContactComponent,
    CountryComponent,
    BeneficiaryComponent,
    PartnershipComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(ROUTES),
  ]
})
export class UserModule { }
