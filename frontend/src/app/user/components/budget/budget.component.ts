import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTable } from '@angular/material/table';

import { ProjectService, UserService } from '../../services';
import { FormLibrary } from 'src/app/shared/libraries/form.lib';

@Component({
  selector: 'app-budget',
  templateUrl: './budget.component.html',
  styleUrls: ['./budget.component.scss']
})
export class BudgetComponent implements OnInit {
  form: FormGroup;
  displayedColumns: string[] = ['budget', 'financialYear', 'addedBy', 'action'];
  dataSource = [];

  @ViewChild(MatTable) table: MatTable<any>;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    public projectService: ProjectService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      budget: [null, [Validators.required]],
      financialYear: [null, [Validators.required]]
    });

    const projectData = this.projectService.getProjectData();
    this.userService.get('budgets', projectData && projectData.projectData ? projectData.projectData.id : null)
      .then((data) => {
        this.dataSource = data;
      })
      .catch((error) => {
        console.error(error.message);
      });
  }

  onSubmit(form: FormGroup) {
    if (form.invalid) {
      FormLibrary.markFormGroupTouched(form);
      return;
    }

    const projectData = this.projectService.getProjectData();
    const data = {
      ...form.value,
      id: projectData && projectData.projectData ? projectData.projectData.id : null,
      user: projectData ? projectData.username : null
    };
    this.userService.post('budgets', data, projectData && projectData.projectData ? projectData.projectData.id : null)
      .then((res) => {
        this.dataSource.push(res);
        if (this.table) { this.table.renderRows(); }
        this.snackBar.open('Successfully added budget.', 'Ok', {
          duration: 3000
        });
      })
      .catch((error) => {
        this.snackBar.open('Failed to add budget.', 'Ok', {
          duration: 5000
        });
        console.error(error.message);
      });
  }

  remove(data: any) {
    this.userService.delete('budgets', data.id)
      .then(() => {
        this.snackBar.open('Successfully removed budget.', 'Ok', {
          duration: 3000
        });
        this.dataSource = this.dataSource.filter(row => row.id !== data.id);
      })
      .catch((error) => {
        this.snackBar.open('Failed to remove budget.', 'Ok', {
          duration: 5000
        });
        console.error(error.message);
      });
  }

}
