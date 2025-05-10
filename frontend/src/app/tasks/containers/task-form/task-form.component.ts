import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ActivatedRoute } from '@angular/router';
import { firstValueFrom } from 'rxjs';

import { FormUtilsService } from '../../../shared/form/form-utils.service';
import { Task } from '../../model/task';
import { TaskService } from '../../services/task.service';

type TaskForm = {
  id: FormControl<string | null>;
  title: FormControl<string | null>;
  description: FormControl<string | null>;
  deadline: FormControl<string | null>;
};

@Component({
  selector: 'app-task-form',
  standalone: true,
  imports: [ReactiveFormsModule,
    MatCardModule,
    MatIconModule,
    MatToolbarModule,
    MatFormFieldModule,
    MatSelectModule,
    MatButtonModule,
    MatTableModule,
    MatInputModule,],
  templateUrl: './task-form.component.html',
  styleUrl: './task-form.component.css'
})
export class TaskFormComponent implements OnInit {
  form!: FormGroup<TaskForm>;

  constructor(
    private formBuilder: NonNullableFormBuilder,
    private service: TaskService,
    private snackBar: MatSnackBar,
    private location: Location,
    private route: ActivatedRoute,
    public formUtils: FormUtilsService
  ) {}

  ngOnInit(): void {
    const task: Task = this.route.snapshot.data['task'];
    this.form = this.formBuilder.group<TaskForm>({
      id: new FormControl(task.id),
      title: new FormControl(task.title, {
        validators: [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(100),
        ],
      }),
      description: new FormControl(task.description, {
        validators: [
          Validators.required,
          Validators.maxLength(300),
        ],
      }),
      deadline: new FormControl(task.deadline, {
        validators: [Validators.required],
      }),
    });
  }

  async onSubmit() {
    if (this.form.valid) {
      try {
        const result: Task = await firstValueFrom(
          this.service.save(this.form.value as Partial<Task>)
        );
        this.onSaveSuccess();
      } catch (error) {
        this.onError();
      }
    } else {
      this.formUtils.validateAllFormFields(this.form);
    }
  }

  onCancel() {
    this.location.back();
  }

  private showSnackBar(message: string) {
    this.snackBar.open(message, '', { duration: 5000 });
  }

  private onSaveSuccess() {
    this.showSnackBar('Tarefa salvo com sucesso!');
    this.onCancel();
  }

  private onError() {
    this.showSnackBar('Erro ao salvar a tarefa.');
  }
}
