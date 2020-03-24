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
      projectData: {},
      userData: {}
    };
  }

  getProjectData() {
    return this.project;
  }

  setProjectData(data: any) {
    this.project = {
      ...this.project,
      projectData: {
        projectId: data.projectId,
        projectName: data.projectName
      }
    };
  }

  setUserData(data: any) {
    this.project = {
      ...this.project,
      userData: {
        firstName: data.firstName,
        lastName: data.lastName,
        type: data.type,
        projectId: data.projectId
      }
    };
  }

}
