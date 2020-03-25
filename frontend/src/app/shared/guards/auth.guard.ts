import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

import { ProjectService } from 'src/app/user/services';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private router: Router,
    private projectService: ProjectService
  ) { }

  /**
   * @description Prevents unauthorized users from accessing route.
   */
  canActivate(): boolean {
    return this.validate();
  }

  /**
   * @description Guard method called when going to a guarded route.
   */
  private validate(): boolean {
    const projectData = this.projectService.getProjectData();
    if (projectData && projectData.username) {
      return true;
    }

    this.projectService.clear();
    this.router.navigate([]);
  }

}
