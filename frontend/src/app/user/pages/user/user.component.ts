import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { ProjectService } from '../../services';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {

  constructor(
    private router: Router,
    private projectService: ProjectService
  ) { }

  ngOnInit(): void {}

  logout() {
    this.projectService.clear();
    this.router.navigate(['']);
  }

}
