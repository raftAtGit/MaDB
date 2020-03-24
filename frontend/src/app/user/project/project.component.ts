import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { map, startWith, takeWhile } from 'rxjs/operators';
import { RequestLibrary } from 'src/app/shared/libraries/request.lib';
import { environment } from 'src/environments/environment';

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

  constructor(
    private formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {
    this.isComponentActive = true;

    this.form = this.formBuilder.group({
      firstName: [null, [Validators.required]],
      lastName: [null, [Validators.required]],
      type: [null, [Validators.required]],
      projectId: [null]
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

    this.getProjects()
      .then((projects) => {
        this.projects = projects;
      });
  }

  ngOnDestroy() {
    this.isComponentActive = false;
  }

  private _filter(value: string): any[] {
    const filterValue = value.toLowerCase();
    return this.projects.filter(project => project.name.toLowerCase().includes(filterValue)
      || project.id.toLowerCase().includes(filterValue));
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

  private getProjects(): Promise<any[]> {
    const endPoint = 'v1/projects';
    const url = `${environment.settings.api}/${endPoint}`;

    return RequestLibrary.get(undefined, url)
      .then((projects) => {
        console.log('projects', projects);
        return projects;
      })
      .catch((err) => {
        console.error(err);
      });
  }

  onSubmit(form: FormGroup) {
    console.log('form value', form.value);
  }

}
