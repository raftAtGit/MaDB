import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

import { ProjectService, UserService } from '../../services';
import { Router } from '@angular/router';

@Component({
  selector: 'app-budget',
  templateUrl: './budget.component.html',
  styleUrls: ['./budget.component.scss']
})
export class BudgetComponent implements OnInit {
  form: FormGroup;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private userService: UserService,
    public projectService: ProjectService
  ) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      budget: [null, [Validators.required]],
      financialYear: [null, [Validators.required]]
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
    this.userService.setBudgetData(data, projectData && projectData.projectData ? projectData.projectData.id : null)
      .then(() => {
        this.projectService.setBudgetData(form.value);
        this.router.navigate(['manage']);
      })
      .catch((error) => {
        console.error(error);
      });
  }

}
