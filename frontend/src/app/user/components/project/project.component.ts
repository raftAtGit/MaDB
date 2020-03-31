import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

import { ProjectService, UserService } from '../../services';
import { FormLibrary } from 'src/app/shared/libraries/form.lib';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.scss']
})
export class ProjectComponent implements OnInit {
  form: FormGroup;
  projects: any[];
  filteredProjects: Observable<any[]>;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private snackBar: MatSnackBar,
    private projectService: ProjectService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      isNewProject: [true, [Validators.required]],
      projectId: [null, [Validators.required]],
      name: [null, [Validators.required]],
      start_date: [null, [Validators.required]],
      end_date: [null, [Validators.required]],
      summary: [null, [Validators.required]],
      comments: [null],
      budget: [null],
      status: [null]
    });

    this.filteredProjects = this.form.get('projectId').valueChanges
      .pipe(
        startWith(''),
        map(value => this._filter(value))
      );

    this.userService.getAll('projects')
      .then((projects) => {
        this.projects = projects;
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

    const isNewProject = form.get('isNewProject').value;
    const projectData = this.projectService.getProjectData();
    const data = {
      ...form.value,
      user: projectData ? projectData.username : null
    };

    if (!isNewProject) {
      data.id = projectData && projectData.projectData ? projectData.projectData.id : null;
    }

    this.userService.setProjectData(data, isNewProject)
      .then((project) => {
        this.snackBar.open('Successfully uploaded project data.', 'Ok', {
          duration: 3000
        });
        this.projectService.setProjectData({
          ...form.value,
          id: project ? project.id : null
        });
        this.router.navigate(['/manage']);
      })
      .catch((error) => {
        this.snackBar.open('Failed to upload project data.', 'Ok', {
          duration: 5000
        });
        console.error(error.message);
      });
  }

  private _filter(value: string): any[] {
    const filterValue = value.toLowerCase();
    return this.projects.filter(project => project.name.toLowerCase().includes(filterValue)
      || project.projectId.toLowerCase().includes(filterValue));
  }

  getProjectData(projectId) {
    if (this.form.get('isNewProject').value) {
      return;
    }

    const doesProjectExist = this.projects.find(project => project.projectId === projectId);
    if (!doesProjectExist) {
      return;
    }

    const selectedProject = this.projects.find(project => project.projectId === projectId);
    if (!selectedProject) { return; }
    this.userService.getProject('projects', selectedProject.id)
      .then((project) => {
        this.projectService.setProjectData(project);
        this.form.patchValue(project);
      })
      .catch((error) => {
        this.snackBar.open('Failed to retrieve project data.', 'Ok', {
          duration: 5000
        });
        console.error(error.message);
      });
  }

}
