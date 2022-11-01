import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

import { Author } from 'src/app/models/author';

@Component({
  selector: 'app-author-dialog',
  templateUrl: './author-dialog.component.html',
  styleUrls: ['./author-dialog.component.css']
})
export class AuthorDialogComponent {

  public author: Author;

  constructor(public dialogRef: MatDialogRef<AuthorDialogComponent>) {
    this.author = {id: 0, firstName: '', lastName: ''};
  }

  addAuthor() {
    this.dialogRef.close(this.author);
  }

  cancel() {
    this.dialogRef.close();
  }
}