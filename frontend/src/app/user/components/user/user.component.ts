import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { map, startWith, takeWhile } from 'rxjs/operators';

import { ProjectService, UserService } from '../../services';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit, OnDestroy {
  private isComponentActive: boolean;
  form: FormGroup;
  projects: any[];
  filteredProjects: Observable<any[]>;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private projectService: ProjectService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.isComponentActive = true;

    const { userData } = this.projectService.getProjectData();

    this.form = this.formBuilder.group({
      firstName: [userData.firstName, [Validators.required]],
      lastName: [userData.lastName, [Validators.required]],
      type: [userData.type, [Validators.required]],
      projectId: [userData.projectId]
    }, { validator: this.validateProject });

    this.filteredProjects = this.form.get('projectId').valueChanges
      .pipe(
        startWith(''),
        map(value => this._filter(value))
      );

    this.form.get('type').valueChanges
      .pipe(
        takeWhile(() => this.isComponentActive),
        map(value => {
          if (value === 'new') {
            this.form.get('projectId').setErrors(null);
          }
        })
      ).subscribe();

    this.userService.getProjects()
      .then((projects) => {
        console.log('projects', projects);
        this.projects = projects;
      })
      .catch((err) => {
        console.error(err);
      });
  }

  ngOnDestroy() {
    this.isComponentActive = false;
  }

  private _filter(value: string): any[] {
    const filterValue = value.toLowerCase();
    return this.projects.filter(project => project.name.toLowerCase().includes(filterValue)
      || project.projectId.toLowerCase().includes(filterValue));
  }

  private validateProject(form: FormGroup) {
    const typeControl = form.get('type');
    const projectIdControl = form.get('projectId');

    if (typeControl.value === 'existing' && !projectIdControl.value) {
      projectIdControl.setErrors({ required: true });
      return { validProjectId: true };
    }

    return null;
  }

  onSubmit(form: FormGroup) {
    console.log('form value', form.value);
    if (form.invalid) {
      return;
    }

    this.userService.getProjectData(form.value.projectId)
      .then((project) => {
        console.log('got project', project);
        this.projectService.setProjectData(project);
        this.projectService.setUserData(form.value);
        this.router.navigate(['project']);
      })
      .catch((error) => {
        console.error(error);
      });
  }

}
