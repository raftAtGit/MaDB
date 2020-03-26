import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

import { ProjectService, UserService } from '../../services';
import { Router } from '@angular/router';

@Component({
  selector: 'app-funding',
  templateUrl: './funding.component.html',
  styleUrls: ['./funding.component.scss']
})
export class FundingComponent implements OnInit {
  form: FormGroup;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private userService: UserService,
    public projectService: ProjectService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      funding_source: [null, [Validators.required]]
    });
  }

  onSubmit(form: FormGroup) {
    if (form.invalid) {
      return;
    }

    const projectData = this.projectService.getProjectData();
    const data = {
      ...form.value,
      id: projectData && projectData.projectData ? projectData.projectData.id : null,
      user: projectData ? projectData.username : null
    };
    this.userService.setFundingData(data, projectData && projectData.projectData ? projectData.projectData.id : null)
      .then(() => {
        this.snackBar.open('Successfully uploaded funding data.', 'Ok', {
          duration: 3000
        });
        this.projectService.setFundingData(form.value);
        this.router.navigate(['budget']);
      })
      .catch((error) => {
        this.snackBar.open('Failed to upload funding data.', 'Ok', {
          duration: 5000
        });
        console.error(error);
      });
  }

}
