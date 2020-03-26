import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

import { ProjectService, UserService } from '../../services';

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
    private router: Router,
    private snackBar: MatSnackBar,
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
    if (form.invalid) {
      return;
    }

    const projectData = this.projectService.getProjectData();
    const data = {
      ...form.value,
      country: {
        id: form.value.country
      },
      id: projectData && projectData.projectData ? projectData.projectData.id : null,
      user: projectData ? projectData.username : null
    };
    this.userService.setProjectData(data, form.get('isNewProject').value)
      .then(() => {
        this.snackBar.open('Successfully uploaded project data.', 'Ok', {
          duration: 3000
        });
        this.projectService.setProjectData(form.value);
        this.router.navigate(['theme']);
      })
      .catch((error) => {
        this.snackBar.open('Failed to upload project data.', 'Ok', {
          duration: 5000
        });
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

    const doesProjectExist = this.projects.find(project => project.projectId === projectId);
    if (!doesProjectExist) {
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
