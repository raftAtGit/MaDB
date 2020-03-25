import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatButtonModule } from '@angular/material/button';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatInputModule } from '@angular/material/input';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';

const MATERIAL_MODULES = [
  MatAutocompleteModule,
  MatButtonModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatInputModule,
  MatRadioModule,
  MatSelectModule
];

import { AuthGuard } from './guards/auth.guard';

@NgModule({
  exports: [
    ...MATERIAL_MODULES,
    FormsModule,
    ReactiveFormsModule
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    ...MATERIAL_MODULES
  ],
  providers: [
    AuthGuard
  ]
})
export class SharedModule {}
