import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTable } from '@angular/material/table';

import { ProjectService, UserService } from '../../services';

@Component({
  selector: 'app-theme',
  templateUrl: './theme.component.html',
  styleUrls: ['./theme.component.scss']
})
export class ThemeComponent implements OnInit {
  form: FormGroup;
  displayedColumns: string[] = ['theme', 'addedBy', 'action'];
  dataSource = [];
  themes: string[] = [
    'Digital',
    'Entrepreneurship',
    'Gender',
    'Green Solutions',
    'LEAD: Youth driving change',
    'Life Skills',
    'Migration',
    'Protection',
    'SRHR',
    'TVET',
    'Village Savings & Loan Associations (VSLA)',
    'WASH',
    'YEEiE',
    'Youth Savings Groups'
  ];

  @ViewChild(MatTable) table: MatTable<any>;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    public projectService: ProjectService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      theme: [null, [Validators.required]]
    });

    const projectData = this.projectService.getProjectData();
    this.userService.get('themes', projectData && projectData.projectData ? projectData.projectData.id : null)
      .then((data) => {
        this.dataSource = data;
      })
      .catch((error) => {
        console.error(error.message);
      });
  }

  onSubmit(form: FormGroup) {
    if (form.invalid) {
      return;
    }

    const projectData = this.projectService.getProjectData();
    const data = {
      theme: form.get('theme').value,
      id: projectData && projectData.projectData ? projectData.projectData.id : null,
      user: projectData ? projectData.username : null
    };
    this.userService.post('themes', data, projectData && projectData.projectData ? projectData.projectData.id : null)
      .then((res) => {
        this.dataSource.push(res);
        if (this.table) { this.table.renderRows(); }
        this.snackBar.open('Successfully added theme.', 'Ok', {
          duration: 3000
        });
      })
      .catch((error) => {
        this.snackBar.open('Failed to add theme.', 'Ok', {
          duration: 5000
        });
        console.error(error.message);
      });
  }

  remove(data: any) {
    this.userService.delete('themes', data.id)
      .then(() => {
        this.snackBar.open('Successfully removed theme.', 'Ok', {
          duration: 3000
        });
        this.dataSource = this.dataSource.filter(row => row.id !== data.id);
      })
      .catch((error) => {
        this.snackBar.open('Failed to remove theme.', 'Ok', {
          duration: 5000
        });
        console.error(error.message);
      });
  }

}
