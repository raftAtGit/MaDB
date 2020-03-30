import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatButtonModule } from '@angular/material/button';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTableModule } from '@angular/material/table';

const MATERIAL_MODULES = [
  MatAutocompleteModule,
  MatButtonModule,
  MatDatepickerModule,
  MatExpansionModule,
  MatNativeDateModule,
  MatInputModule,
  MatListModule,
  MatRadioModule,
  MatSelectModule,
  MatSnackBarModule,
  MatTableModule
];

import { AuthGuard } from './guards/auth.guard';
import { FilterPipe } from './pipes/filter.pipe';

@NgModule({
  declarations: [
    FilterPipe
  ],
  exports: [
    FilterPipe,
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
    AuthGuard,
    FilterPipe
  ]
})
export class SharedModule {}
