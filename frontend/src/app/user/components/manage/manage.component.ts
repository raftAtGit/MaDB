import { Component, OnInit } from '@angular/core';

import { ProjectService } from '../../services';

@Component({
  selector: 'app-manage',
  templateUrl: './manage.component.html',
  styleUrls: ['./manage.component.scss']
})
export class ManageComponent implements OnInit {

  constructor(
    public projectService: ProjectService
  ) { }

  ngOnInit(): void {}
}
