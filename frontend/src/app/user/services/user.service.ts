import { Injectable } from '@angular/core';

import { environment } from 'src/environments/environment';
import { RequestLibrary } from 'src/app/shared/libraries/request.lib';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor() { }

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

  setProjectData(projectData: any, type: string): Promise<any> {
    const isNewProject = type === 'new';
    const endPoint = `v1/projects`;
    let url = `${environment.settings.api}/${endPoint}`;

    if (isNewProject) {
      return RequestLibrary.post(undefined, url, projectData);
    } else {
      url = `${url}/${projectData.projectId}`;
      return RequestLibrary.put(undefined, url, projectData);
    }
  }

}
