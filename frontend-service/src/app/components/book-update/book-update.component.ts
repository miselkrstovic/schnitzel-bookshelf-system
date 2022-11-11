import { Component, Inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Author } from 'src/app/models/author';
import { Book } from 'src/app/models/book';
import { SchnitzelService } from 'src/app/services/schnitzel-service/schnitzel.service';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { AuthorDialogComponent } from '../modal-dialog/author-dialog.components';

@Component({
  selector: 'app-book-update',
  templateUrl: './book-update.component.html',
  styleUrls: ['./book-update.component.css']
})
export class BookUpdateComponent implements OnInit {

  public book: Book;
  public selectedAuthor?: Author = undefined;
  public lastError?: string;

  constructor(private route: ActivatedRoute,
    private router: Router,
    private service: SchnitzelService,
    public matDialog: MatDialog) { 
      this.book = {id: 0, isbn: '', name: '', annotation: ''};
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.service.fetchBook(params['id']).subscribe({
        next: (res) => {
          this.book = res;
        },
        error: (err) => {
          switch(err.status) {
            case 200:
              break;
            default:
              this.lastError = err.error;
          }
        },
        complete: () => {} 
      });
    });
  }

  updateBook($myParam: string = ''): void {
    this.service.updateBook(this.book).subscribe({
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
  }
  
  cancel(): void { 
    history.back();
  }
}
