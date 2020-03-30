import { Injectable } from '@angular/core';

import { environment } from 'src/environments/environment';
import { RequestLibrary } from 'src/app/shared/libraries/request.lib';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor() { }

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

  post(controller: string, data: any, projectId: number): Promise<any> {
    const endPoint = `v1/${controller}`;
    const url = `${environment.settings.api}/${endPoint}`;
    return RequestLibrary.post(undefined, url, data, { projectId });
  }

  get(controller: string, projectId: number): Promise<any> {
    const endPoint = `v1/${controller}`;
    const url = `${environment.settings.api}/${endPoint}`;
    return RequestLibrary.get(undefined, url, { projectId });
  }

  getAll(controller: string): Promise<any> {
    const endPoint = `v1/${controller}`;
    const url = `${environment.settings.api}/${endPoint}`;
    return RequestLibrary.get(undefined, url);
  }

  getProject(controller: string, projectId: number): Promise<any> {
    const endPoint = `v1/${controller}/${projectId}`;
    const url = `${environment.settings.api}/${endPoint}`;
    return RequestLibrary.get(undefined, url);
  }

  put(controller: string, body: any, projectId: number): Promise<any> {
    const endPoint = `v1/${controller}/${projectId}`;
    const url = `${environment.settings.api}/${endPoint}`;
    return RequestLibrary.put(undefined, url, body);
  }

  delete(controller: string, id: number): Promise<any> {
    const endPoint = `v1/${controller}/${id}`;
    const url = `${environment.settings.api}/${endPoint}`;
    return RequestLibrary.delete(undefined, url);
  }

}
