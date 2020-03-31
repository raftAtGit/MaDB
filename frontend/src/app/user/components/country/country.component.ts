import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTable } from '@angular/material/table';

import { ProjectService, UserService } from '../../services';
import { FormLibrary } from 'src/app/shared/libraries/form.lib';

@Component({
  selector: 'app-country',
  templateUrl: './country.component.html',
  styleUrls: ['./country.component.scss']
})
export class CountryComponent implements OnInit {
  form: FormGroup;
  displayedColumns: string[] = ['country', 'addedBy', 'action'];
  dataSource = [];
  countries: any[];

  @ViewChild(MatTable) table: MatTable<any>;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    public projectService: ProjectService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      country: [null, [Validators.required]]
    });

    const projectData = this.projectService.getProjectData();
    this.userService.get('project_countries', projectData && projectData.projectData ? projectData.projectData.id : null)
      .then((data) => {
        this.dataSource = data;
      })
      .catch((error) => {
        console.error(error.message);
      });

    this.userService.getAll('countries')
      .then((countries) => {
        this.countries = countries;
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
      country: {
        id: form.get('country').value.id,
        name: form.get('country').value.name
      },
      id: projectData && projectData.projectData ? projectData.projectData.id : null,
      user: projectData ? projectData.username : null
    };
    this.userService.post('project_countries', data, projectData && projectData.projectData ? projectData.projectData.id : null)
      .then((res) => {
        this.dataSource.push(res);
        if (this.table) { this.table.renderRows(); }
        this.snackBar.open('Successfully added country.', 'Ok', {
          duration: 3000
        });
      })
      .catch((error) => {
        this.snackBar.open('Failed to add country.', 'Ok', {
          duration: 5000
        });
        console.error(error.message);
      });
  }

  remove(data: any) {
    this.userService.delete('project_countries', data.id)
      .then(() => {
        this.snackBar.open('Successfully removed country.', 'Ok', {
          duration: 3000
        });
        this.dataSource = this.dataSource.filter(row => row.id !== data.id);
      })
      .catch((error) => {
        this.snackBar.open('Failed to remove country.', 'Ok', {
          duration: 5000
        });
        console.error(error.message);
      });
  }

}
