import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

import { ProjectService } from '../../services';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  form: FormGroup;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private projectService: ProjectService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    if (this.projectService.isLoggedIn()) {
      this.router.navigate(['manage']);
    }

    this.form = this.formBuilder.group({
      username: [null, [Validators.required]],
      password: [null, [Validators.required]]
    });
  }

  onSubmit(form: FormGroup) {
    if (form.invalid) {
      return;
    }

    this.snackBar.open('Successfully logged in.', 'Ok', {
      duration: 3000
    });
    this.projectService.setUserData(form.value);

    if (form.get('username').value === 'admin') {
      this.router.navigate(['admin']);
    } else {
      this.router.navigate(['manage']);
    }
  }

}
