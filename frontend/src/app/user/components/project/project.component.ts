import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { map, startWith, takeWhile } from 'rxjs/operators';

import { ProjectService, UserService } from '../../services';
import { Router } from '@angular/router';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.scss']
})
export class ProjectComponent implements OnInit, OnDestroy {
  private isComponentActive: boolean;
  form: FormGroup;
  projects: any[];
  filteredProjects: Observable<any[]>;
  countries: any[];

  constructor(
    private formBuilder: FormBuilder,
    private projectService: ProjectService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.isComponentActive = true;

    this.form = this.formBuilder.group({
      isNewProject: [true, [Validators.required]],
      projectId: [null, [Validators.required]],
      name: [null, [Validators.required]],
      country: [null, [Validators.required]],
      startDate: [null, [Validators.required]],
      endDate: [null, [Validators.required]],
      observations: [null, [Validators.required]],
      description: [null, [Validators.required]],
      budget: [null],
      status: [null]
    });

    this.filteredProjects = this.form.get('projectId').valueChanges
      .pipe(
        startWith(''),
        map(value => this._filter(value))
      );

    this.userService.getCountries()
      .then((countries) => {
        this.countries = countries;
      })
      .catch((error) => {
        console.error(`ERROR in getCountries: ${error.message}`);
      });

    this.userService.getProjects()
      .then((projects) => {
        this.projects = projects;
      })
      .catch((error) => {
        console.error(`ERROR in getProjects: ${error.message}`);
      });
  }

  ngOnDestroy() {
    this.isComponentActive = false;
  }

  onSubmit(form: FormGroup) {
    console.log('form value', form.value);
    if (form.invalid) {
      return;
    }

    const { username, projectData } = this.projectService.getProjectData();
    const data = {
      ...form.value,
      country: {
        id: form.value.country
      },
      id: projectData.id,
      user: username
    };
    this.userService.setProjectData(data, form.get('isNewProject').value)
      .then(() => {
        console.log('posted project');
        this.projectService.setProjectData(form.value);
      })
      .catch((error) => {
        console.error(error);
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

    const selectedProject = this.projects.find(project => project.projectId === projectId);
    if (!selectedProject) { return; }
    this.userService.getProjectData(selectedProject.id)
      .then((project) => {
        console.log('projectData', project);
        this.projectService.setProjectData(project);
        this.form.patchValue({
          ...project,
          country: project.country.id
        });
      })
      .catch((error) => {
        console.error(error);
      });
  }

}
