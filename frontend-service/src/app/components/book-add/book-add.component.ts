import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Author } from 'src/app/models/author';
import { Book } from 'src/app/models/book';
import { SchnitzelService } from 'src/app/services/schnitzel-service/schnitzel.service';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { AuthorDialogComponent } from '../modal-dialog/author-dialog.components';
import { Constants } from 'src/app/common/constants';

@Component({
  selector: 'app-book-add',
  templateUrl: './book-add.component.html',
  styleUrls: ['./book-add.component.css']
})
export class BookAddComponent {

  public book: Book;
  public selectedAuthor?: Author = undefined;
  public lastError?: string;
  public isbnRegex: string;

  constructor(private router: Router,
    private service: SchnitzelService,
    public matDialog: MatDialog) { 
      this.isbnRegex = Constants.isbn10orISBN13Regex;
      this.book = {id: 0, isbn: '', name: '', annotation: ''};
  }
  
  addBook($myParam: string = ''): void {
    this.service.addBook(this.book).subscribe({
      next: (res) => console.log(res),
      error: (err) => {
        switch(err.status) {
          case 200:
            const navigationDetails: string[] = ['/'];
            if($myParam.length) {
              navigationDetails.push($myParam);
            }
            this.router.navigate(navigationDetails);
            break;
          default:
            this.lastError = err.error;
        }
      },
      complete: () => {} 
    });
  }

  addAuthor() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.disableClose = true;
    dialogConfig.id = "author-dialog";
    const modalDialog = this.matDialog.open(AuthorDialogComponent, dialogConfig);
    modalDialog.afterClosed().subscribe(author => {
      if (!this.book.authors) {
        this.book.authors = [];
      }
      this.book.authors?.push(new Author(0, author.firstName, author.lastName));
    });
  }

  removeAuthor() {
    this.book.authors?.filter((value, index, arr) => {
      if (value === this.selectedAuthor) {
          arr.splice(index, 1);
          return true;
      }
      return false;
    });
    console.log(JSON.stringify(this.selectedAuthor));
  }
  
  cancel($myParam: string = ''): void { 
    history.back();
  }
}
