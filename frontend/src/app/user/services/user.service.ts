import { Injectable } from '@angular/core';

import { environment } from 'src/environments/environment';
import { RequestLibrary } from 'src/app/shared/libraries/request.lib';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor() { }

  getCountries(): Promise<any[]> {
    const endPoint = 'v1/countries';
    const url = `${environment.settings.api}/${endPoint}`;

    return RequestLibrary.get(undefined, url);
  }

  getProjects(): Promise<any[]> {
    const endPoint = 'v1/projects';
    const url = `${environment.settings.api}/${endPoint}`;

    return RequestLibrary.get(undefined, url);
  }

  getProjectData(projectId: string): Promise<any> {
    const endPoint = `v1/projects/${projectId}`;
    const url = `${environment.settings.api}/${endPoint}`;

    return RequestLibrary.get(undefined, url);
  }

  setProjectData(projectData: any, isNewProject: boolean): Promise<any> {
    const endPoint = `v1/projects`;
    let url = `${environment.settings.api}/${endPoint}`;
    delete projectData.isNewProject;

    if (isNewProject) {
      return RequestLibrary.post(undefined, url, {
        ...projectData,
        budget: 0,
        status: 'PENDING'
      });
    } else {
      url = `${url}/${projectData.id}`;
      return RequestLibrary.put(undefined, url, {
        ...projectData
      });
    }
  }

  setThemeData(data: any, projectId: number): Promise<any> {
    const endPoint = `v1/themes`;
    const url = `${environment.settings.api}/${endPoint}`;
    return RequestLibrary.post(undefined, url, data, { projectId });
  }

  setFundingData(data: any, projectId: number): Promise<any> {
    const endPoint = `v1/fundings`;
    const url = `${environment.settings.api}/${endPoint}`;
    return RequestLibrary.post(undefined, url, data, { projectId });
  }

  setBudgetData(data: any, projectId: number): Promise<any> {
    const endPoint = `v1/budgets`;
    const url = `${environment.settings.api}/${endPoint}`;
    return RequestLibrary.post(undefined, url, data, { projectId });
  }

}
