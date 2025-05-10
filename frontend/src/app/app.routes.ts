import { Routes } from '@angular/router';
import { CORE_ROUTES } from './routes/core-routes';
import { TasksComponent } from './tasks/containers/tasks/tasks.component';
import { TaskFormComponent } from './tasks/containers/task-form/task-form.component';
import { taskResolver } from './tasks/guards/task.resolver';

export const routes: Routes = [
  {
    path: CORE_ROUTES.HOME,
    pathMatch: 'full',
    redirectTo: 'tasks',
  },
  {
    path: CORE_ROUTES.TASKS,
    component: TasksComponent,
  },
  {
    path: CORE_ROUTES.TASK_FORM,
    component: TaskFormComponent,
    resolve: { task: taskResolver },
  },

  {
    path: `${CORE_ROUTES.TASK_EDIT}/:id`,
    component: TaskFormComponent,
    resolve: { task: taskResolver },
  },
];
