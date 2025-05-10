import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';

import { StatusPipe } from '../../../shared/pipes/status.pipe';
import { Task } from '../../model/task';
import { FormatarDatasPipe } from '../../../shared/pipes/formatar-datas.pipe';

@Component({
  selector: 'app-tasks-list',
  standalone: true,
  imports: [FormatarDatasPipe, StatusPipe, MatTableModule, MatIconModule, MatButtonModule],
  templateUrl: './tasks-list.component.html',
  styleUrl: './tasks-list.component.css'
})
export class TasksListComponent {
  @Input() tasks: Task[] = [];
  @Output() add = new EventEmitter(false);
  @Output() edit = new EventEmitter(false);
  @Output() remove = new EventEmitter(false);

  readonly displayedColumns = ['title', 'description', 'status', 'createdOn', 'deadline', 'actions'];

  constructor() {}

  onAdd(): void {
    this.add.emit(true);
  }

  onEdit(task: Task) {
    this.edit.emit(task)
  }

  onDelete(task: Task) {
    this.remove.emit(task)
  }
}
