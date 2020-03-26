import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  private project: any = {};

  constructor() { }

  clear() {
    this.project = {};
  }

  isLoggedIn(): boolean {
    return !!this.project.username;
  }

  selectedProject(): any {
    return this.project.projectData;
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

  setThemeData(data: any) {
    this.project = {
      ...this.project,
      themeData: data
    };
  }

  setFundingData(data: any) {
    this.project = {
      ...this.project,
      fundingData: data
    };
  }

  setBudgetData(data: any) {
    this.project = {
      ...this.project,
      budgetData: data
    };
  }

  setUserData(data: any) {
    this.project = {
      ...this.project,
      username: data.username
    };
  }

}
