import { Task } from './task';
export interface TaskPage {
  tasks: Task[];
  totalElements: number;
  totalPages: number;
}
