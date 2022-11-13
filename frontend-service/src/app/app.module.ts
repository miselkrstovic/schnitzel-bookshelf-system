import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms'; 
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { BookAddComponent } from './components/book-add/book-add.component';
import { BookUpdateComponent } from './components/book-update/book-update.component';
import { BookRemoveComponent } from './components/book-remove/book-remove.component';
import { BookListComponent } from './components/book-list/book-list.component';
import { AuthorPipe } from './models/author.pipe';
import { AuthorDialogComponent } from './components/modal-dialog/author-dialog.components';

@NgModule({
  declarations: [
    AppComponent,
    BookAddComponent,
    BookUpdateComponent,
    BookRemoveComponent,
    BookListComponent,
    AuthorPipe,
    AuthorDialogComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppRoutingModule,
    MatButtonModule,
    MatDialogModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
