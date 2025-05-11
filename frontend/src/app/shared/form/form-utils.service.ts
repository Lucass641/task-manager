import { Injectable } from '@angular/core';
import { UntypedFormControl, UntypedFormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root',
})
export class FormUtilsService {
  constructor() {}

  validateAllFormFields(formGroup: UntypedFormGroup) {
    Object.keys(formGroup.controls).forEach((field) => {
      const control = formGroup.get(field);
      if (control instanceof UntypedFormControl) {
        control.markAsTouched({ onlySelf: true });
      } else if (control instanceof UntypedFormGroup) {
        control.markAsTouched({ onlySelf: true });
        this.validateAllFormFields(control);
      }
    });
  }

  getErrorMessage(formGroup: UntypedFormGroup, fieldName: string) {
    const field = formGroup.get(fieldName) as UntypedFormControl;
    return this.getErrorMessageFromField(field);
  }
  getErrorMessageFromField(field: UntypedFormControl) {
    if (field?.hasError('required')) {
      return 'Campo obrigatório';
    }

    if (field?.hasError('minLength')) {
      const requiredLength = field.errors
        ? field.errors['minLength']['requiredLength']
        : 3;
      return `Tamanho minimo precisa ser de ${requiredLength} caracteres.`;
    }
    if (field?.hasError('maxLength')) {
      const requiredLength = field.errors
        ? field.errors['maxLength']['requiredLength']
        : 200;
      return `Tamanho Máximo excedido de ${requiredLength} caracteres.`;
    }
    if (field?.hasError('pattern')) {
      return 'Por favor, insira apenas números';
    }
    return 'Campo Inválido';
  }
}
