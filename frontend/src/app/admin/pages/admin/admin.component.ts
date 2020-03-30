import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { ProjectService } from 'src/app/user/services';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  constructor(
    private router: Router,
    public projectService: ProjectService
  ) { }

  ngOnInit(): void {
    if (!this.projectService.isLoggedIn()) {
      this.logout();
    }
}

  logout() {
    this.projectService.clear();
    this.router.navigate(['']);
  }

}
