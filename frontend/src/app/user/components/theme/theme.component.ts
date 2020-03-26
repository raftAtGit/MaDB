import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

import { ProjectService, UserService } from '../../services';

@Component({
  selector: 'app-theme',
  templateUrl: './theme.component.html',
  styleUrls: ['./theme.component.scss']
})
export class ThemeComponent implements OnInit {
  form: FormGroup;
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
  displayedColumns: string[] = ['theme', 'addedBy', 'action'];
  dataSource = [];

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
      .then((themes) => {
        this.dataSource = themes;
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
      .then((theme) => {
        this.dataSource.push(theme);
        this.snackBar.open('Successfully added theme.', 'Ok', {
          duration: 3000
        });
        this.projectService.setThemeData(data.theme);
      })
      .catch((error) => {
        this.snackBar.open('Failed to add theme.', 'Ok', {
          duration: 5000
        });
        console.error(error.message);
      });
  }

  remove(theme: any) {
    this.userService.delete('themes', theme.id)
      .then(() => {
        this.snackBar.open('Successfully removed theme.', 'Ok', {
          duration: 3000
        });
        this.dataSource = this.dataSource.filter(data => data.id !== theme.id);
      })
      .catch((error) => {
        this.snackBar.open('Failed to remove theme.', 'Ok', {
          duration: 5000
        });
        console.error(error.message);
      });
  }

}
