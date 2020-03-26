import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

import { ProjectService, UserService } from '../../services';
import { Router } from '@angular/router';

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

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private userService: UserService,
    public projectService: ProjectService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      theme: [null, [Validators.required]]
    });
  }

  onSubmit(form: FormGroup) {
    if (form.invalid) {
      return;
    }

    const projectData = this.projectService.getProjectData();
    const data = {
      theme: form.get('theme').value.join(','),
      id: projectData && projectData.projectData ? projectData.projectData.id : null,
      user: projectData ? projectData.username : null
    };
    this.userService.setThemeData(data, projectData && projectData.projectData ? projectData.projectData.id : null)
      .then(() => {
        this.snackBar.open('Successfully uploaded theme data.', 'Ok', {
          duration: 3000
        });
        this.projectService.setThemeData(data.theme);
        this.router.navigate(['funding']);
      })
      .catch((error) => {
        this.snackBar.open('Failed to upload theme data.', 'Ok', {
          duration: 5000
        });
        console.error(error);
      });
  }

}
