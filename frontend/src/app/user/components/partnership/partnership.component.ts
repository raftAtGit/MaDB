import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTable } from '@angular/material/table';

import { ProjectService, UserService } from '../../services';
import { FormLibrary } from 'src/app/shared/libraries/form.lib';

@Component({
  selector: 'app-partnership',
  templateUrl: './partnership.component.html',
  styleUrls: ['./partnership.component.scss']
})
export class PartnershipComponent implements OnInit {
  form: FormGroup;
  types: string[] = [
    'Plan International National Office',
    'Governmental / Intergovernmental',
    'NGOs / Civil Society',
    'Private Sector'
  ];
  displayedColumns: string[] = ['partner', 'partnership_type', 'addedBy', 'action'];
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
      partner: [null, [Validators.required]],
      partnership_type: [null, [Validators.required]]
    });

    const projectData = this.projectService.getProjectData();
    this.userService.get('partnerships', projectData && projectData.projectData ? projectData.projectData.id : null)
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
    this.userService.post('partnerships', data, projectData && projectData.projectData ? projectData.projectData.id : null)
      .then((res) => {
        this.dataSource.push(res);
        if (this.table) { this.table.renderRows(); }
        this.snackBar.open('Successfully added partnership.', 'Ok', {
          duration: 3000
        });
      })
      .catch((error) => {
        this.snackBar.open('Failed to add partnership.', 'Ok', {
          duration: 5000
        });
        console.error(error.message);
      });
  }

  remove(data: any) {
    this.userService.delete('partnerships', data.id)
      .then(() => {
        this.snackBar.open('Successfully removed partnership.', 'Ok', {
          duration: 3000
        });
        this.dataSource = this.dataSource.filter(row => row.id !== data.id);
      })
      .catch((error) => {
        this.snackBar.open('Failed to remove partnership.', 'Ok', {
          duration: 5000
        });
        console.error(error.message);
      });
  }

}
