import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'status',
})
export class StatusPipe implements PipeTransform {
  transform(value: string): string {
    switch (value) {
      case 'Pendente':
        return 'pending';
      case 'Em andamento':
        return 'autorenew';
      case 'Concluída':
        return 'task_alt';
    }
    return 'pending';
  }
}
