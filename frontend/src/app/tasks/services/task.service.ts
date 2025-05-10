import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TaskPage } from '../model/task-page';
import { first, Observable } from 'rxjs';
import { Task } from '../model/task';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  private readonly API = 'api/tasks';

  constructor(private httpClient: HttpClient) { }

  list(page = 0, pageSize = 10) {
    return this.httpClient.get<TaskPage>(this.API, {params: {page, pageSize}}).pipe(first());
  }

  loadById(id: string): Observable<Task> {
    return this.httpClient.get<Task>(`${this.API}/${id}`);
  }

  save(record: Partial<Task>) {
    if (record.id) {
      return this.update(record);
    } else {
      return this.create(record);
    }
  }

  private create(record: Partial<Task>) {
    return this.httpClient.post<Task>(this.API, record).pipe(first());
  }

  private update(record: Partial<Task>) {
    return this.httpClient
      .put<Task>(`${this.API}/${record.id}`, record)
      .pipe(first());
  }

  remove(id: string) {
    return this.httpClient.delete(`${this.API}/${id}`).pipe(first());
  }
}
