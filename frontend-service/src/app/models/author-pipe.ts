import { Pipe, PipeTransform } from '@angular/core';
import { Author } from './author';

@Pipe({name: 'authorPipe'})
export class AuthorPipe implements PipeTransform {
  transform(value: Author): string {
    return value.firstName + ' ' + value.lastName;
  }
}