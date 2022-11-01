import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { Book } from '../../models/book';
import { Author } from '../../models/author';

@Injectable({
  providedIn: 'root'
})
export class SchnitzelService {
  constructor(private http: HttpClient) { }

  public httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };
  
  rootURL = 'http://127.0.0.1:8080';

  getAllBooks(keyword: string = ''): Observable<any> {
    if (keyword) {
      return this.http.get(this.rootURL + `/books?keyword=${keyword}`);
    } else {
      return this.http.get(this.rootURL + `/books`);
    }
  }

  fetchBook(bookId: string = ''): Observable<any> {
    return this.http.get(this.rootURL + `/books/${bookId}`);
  }

  addBook(book: Book): Observable<any> {
    return this.http.post(this.rootURL + '/books', 
      {
        name: book.name,
        isbn: book.isbn,
        annotation: book.annotation,
        authors: book.authors
      },
      this.httpOptions);
  }

  updateBook(book: Book): Observable<any> {
    return this.http.put(this.rootURL + `/books/${book.id}`, 
    {
      name: book.name,
      isbn: book.isbn,
      annotation: book.annotation,
      authors: book.authors
    }, 
    this.httpOptions);
  }

  removeBook(bookId: number): Observable<any> {
    return this.http.delete(this.rootURL + `/books/${bookId}`);
  }

  addAuthor(bookId: number, author: Author): Observable<any> {
    return this.http.post(this.rootURL + `/books/${bookId}/authors/`, 
    {
      fistName: author.firstName,
      lastName: author.lastName
    }, 
    this.httpOptions);
  }

  getBookAuthors(bookId: number): Observable<any> {
    return this.http.get(this.rootURL + `/books/${bookId}/authors/`);
  }

  updateBookAuthor(bookId: number, authorId: number, author: Author): Observable<any> {
    return this.http.put(this.rootURL + `/books/${bookId}/authors/${authorId}`, 
    {
      firstName: author.firstName,
      lastName: author.lastName
    },
    this.httpOptions);
  }

  removeBookAuthor(bookId: number, authorId: number): Observable<any> {
    return this.http.delete(this.rootURL + `/books/${bookId}/authors/${authorId}`);
  }
}
