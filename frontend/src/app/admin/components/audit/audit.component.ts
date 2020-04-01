import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

import { ProjectService, UserService } from 'src/app/user/services';

@Component({
  selector: 'app-audit',
  templateUrl: './audit.component.html',
  styleUrls: ['./audit.component.scss']
})
export class AuditComponent implements OnInit {
  projects: any[] = [];
  projectDetails: any;
  isActionDisabled: boolean;

  constructor(
    private userService: UserService,
    private projectService: ProjectService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.userService.getAll('admin/pending_projects')
      .then((projects) => {
        this.projects = projects;
      })
      .catch((error) => {
        console.error(error.message);
      });
  }

  requestDetails(project: any) {
    if (this.projectDetails && this.projectDetails[0].id === project.id) {
      return;
    }

    this.projectDetails = undefined;
    const requests = [
      this.userService.get('beneficiaries', project.id),
      this.userService.get('budgets', project.id),
      this.userService.get('contacts', project.id),
      this.userService.get('project_countries', project.id),
      this.userService.get('fundings', project.id),
      this.userService.get('partnerships', project.id),
      this.userService.get('themes', project.id)
    ];
    Promise.all(requests)
      .then((data) => {
        this.projectDetails = [
          project,
          ...data
        ];
      })
      .catch((error) => {
        console.error(error.message);
        this.snackBar.open(`Failed to retrieve project data: ${project.projectId}.`, 'Ok', {
          duration: 5000
        });
      });
  }

  approve(project: any) {
    this.isActionDisabled = true;
    const projectData = this.projectService.getProjectData();
    const data = {
      user: projectData ? projectData.username : null
    };
    this.userService.put('admin/approve_project', data, project.id)
      .then(() => {
        this.projects = this.projects.filter(p => p.id !== project.id);
        this.isActionDisabled = false;
        this.snackBar.open(`Successfully approved project: ${project.projectId}.`, 'Ok', {
          duration: 5000
        });
      })
      .catch((error) => {
        this.isActionDisabled = false;
        this.snackBar.open(`Failed to approve project: ${project.projectId}.`, 'Ok', {
          duration: 5000
        });
        console.error(error.message);
      });
  }

  reject(project: any) {
    this.isActionDisabled = true;
    const projectData = this.projectService.getProjectData();
    const data = {
      user: projectData ? projectData.username : null
    };
    this.userService.put('admin/reject_project', data, project.id)
      .then(() => {
        this.projects = this.projects.filter(p => p.id !== project.id);
        this.isActionDisabled = false;
        this.snackBar.open(`Successfully rejected project: ${project.projectId}.`, 'Ok', {
          duration: 5000
        });
      })
      .catch((error) => {
        this.isActionDisabled = false;
        this.snackBar.open(`Failed to reject project: ${project.projectId}.`, 'Ok', {
          duration: 5000
        });
        console.error(error.message);
      });
  }

}
