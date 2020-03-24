import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

import { ProjectService, UserService } from '../../services';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.scss']
})
export class ProjectComponent implements OnInit {
  form: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private projectService: ProjectService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    const { projectData } = this.projectService.getProjectData();

    this.form = this.formBuilder.group({
      projectId: [projectData.projectId, [Validators.required]],
      projectName: [projectData.projectName, [Validators.required]]
    });

    if (projectData.projectId) {
      this.userService.getProjectData(projectData.projectId)
        .then((project) => {
          console.log('project', project);
          if (project) {
            this.form.patchValue(project);
          }
        })
        .catch((err) => {
          console.error(err);
        });
    }
  }

  onSubmit(form: FormGroup) {
    console.log('form value', form.value);
    if (form.invalid) {
      return;
    }

    const { userData } = this.projectService.getProjectData();
    this.userService.setProjectData(form.value, userData.type)
      .then(() => {
        console.log('posted project');
        this.projectService.setProjectData(form.value);
      })
      .catch((error) => {
        console.error(error);
      });
  }

}
