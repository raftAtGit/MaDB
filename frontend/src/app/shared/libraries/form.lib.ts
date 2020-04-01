import { FormGroup } from '@angular/forms';

export class FormLibrary {

  /**
   * Marks all controls in a form group as touched
   * @param formGroup - The form group to touch
   */
  public static markFormGroupTouched(formGroup: FormGroup) {
    Object.keys(formGroup.controls)
      .map(x => formGroup.controls[x])
      .forEach(control => {
        control.markAsTouched();
      });
  }

}
