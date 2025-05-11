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
import dayjs from 'dayjs';

type TaskForm = {
  id: FormControl<string>;
  title: FormControl<string>;
  description: FormControl<string>;
  status: FormControl<string>;
  deadline: FormControl<string>;
};

@Component({
  selector: 'app-task-form',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatCardModule,
    MatIconModule,
    MatToolbarModule,
    MatFormFieldModule,
    MatSelectModule,
    MatButtonModule,
    MatTableModule,
    MatInputModule
  ],
  templateUrl: './task-form.component.html',
  styleUrl: './task-form.component.css'
})
export class TaskFormComponent implements OnInit {
  form!: FormGroup<TaskForm>;

  constructor(
    private fb: NonNullableFormBuilder,
    private taskService: TaskService,
    private snackBar: MatSnackBar,
    private location: Location,
    private route: ActivatedRoute,
    public formUtils: FormUtilsService
  ) {}


  ngOnInit(): void {
    const task: Task = this.route.snapshot.data['task'];
    const deadlineValue = this.getDeadlineDifferenceInDays(task);

    this.form = this.buildForm(task || { status: 'PENDING' } as Task, deadlineValue);
  }

  private buildForm(task: Task, deadline: string ):
  FormGroup<TaskForm> {
    const initialStatus = task?.status === 'PENDING' || !task?.status ? 'Pendente' : task.status;
    return this.fb.group({
      id: this.fb.control(task.id),
      title: this.fb.control(task.title, {
        validators: [Validators.required, Validators.minLength(3), Validators.maxLength(100)]
      }),
      description: this.fb.control(task.description, {
        validators: [Validators.required, Validators.maxLength(300)]
      }),
      status: this.fb.control(initialStatus, {
        validators: [Validators.required]
      }),
      deadline: this.fb.control(deadline, {
        validators: [Validators.required, Validators.pattern(/^[0-9]+$/)]
      })
    });
  }

  private getDeadlineDifferenceInDays(task: Task): string {
  if (!task.deadline) return '';
  const createdOn = dayjs(task.createdOn);
  const deadline = dayjs(task.deadline);
  const diffInDays = deadline.diff(createdOn, 'day');
  return diffInDays.toString();
}

  async onSubmit(): Promise<void> {
    if (this.form.invalid) {
      this.formUtils.validateAllFormFields(this.form);
      return;
    }

    try {
      const result = await firstValueFrom(
        this.taskService.save(this.form.value as Partial<Task>)
      );
      this.onSaveSuccess();
    } catch {
      this.onError();
    }
  }

  onCancel(): void {
    this.location.back();
  }

  private onSaveSuccess(): void {
    this.showSnackBar('Tarefa salva com sucesso!');
    this.onCancel();
  }

  private onError(): void {
    this.showSnackBar('Erro ao salvar a tarefa.');
  }

  private showSnackBar(message: string): void {
    this.snackBar.open(message, '', { duration: 5000 });
  }
}
