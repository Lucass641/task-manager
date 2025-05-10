import { Pipe, PipeTransform } from '@angular/core';
import dayjs from 'dayjs';
import 'dayjs/locale/pt-br';
import relativeTime from 'dayjs/plugin/relativeTime'; // Importe o plugin relativeTime

dayjs.extend(relativeTime); // Use o plugin
dayjs.locale('pt-br');

@Pipe({
  name: 'formatarDatas'
})
export class FormatarDatasPipe implements PipeTransform {
  transform(value: string, type: 'criado' | 'prazo', createdOn?: string): string {
    if (!value) {
      return '';
    }

    const currentDate = dayjs();
    const valueDayjs = dayjs(value);

    if (type === 'criado') {
      return valueDayjs.fromNow();
    } else if (type === 'prazo') {
      const diff = valueDayjs.diff(currentDate);

      if (diff <= 0) {
        return 'Expirado';
      }
      return currentDate.to(valueDayjs);
    }

    return '';
  }
}
