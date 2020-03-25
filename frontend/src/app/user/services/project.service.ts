import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  private project: any;

  constructor() {
    this.clear();
  }

  clear() {
    this.project = {
      projectData: {}
    };
  }

  isLoggedIn(): boolean {
    return !!this.project.username;
  }

  getProjectData() {
    return this.project;
  }

  setProjectData(data: any) {
    this.project = {
      ...this.project,
      projectData: data
    };
  }

  setUserData(data: any) {
    this.project = {
      ...this.project,
      username: data.username
    };
  }

}
