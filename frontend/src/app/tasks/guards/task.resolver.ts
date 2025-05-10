import { ResolveFn } from '@angular/router';
import { Task } from '../model/task';
import { TaskService } from '../services/task.service';
import { inject } from '@angular/core';
import { Observable, of } from 'rxjs';

export const taskResolver: ResolveFn<Task> = (
  route,
  state,
service: TaskService = inject(TaskService)
):Observable<Task> => {
  if (route.params?.['id']) {
    return service.loadById(route.params['id']);
  } else  {
    return of ({
      id: '',
      title: '',
      description: '',
      status: 'PENDING',
      createdOn: '',
      deadline: '',
    });
  }

 };
