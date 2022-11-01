import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookAddComponent } from './components/book-add/book-add.component';
import { BookUpdateComponent } from './components/book-update/book-update.component';
import { BookRemoveComponent } from './components/book-remove/book-remove.component';
import { BookListComponent } from './components/book-list/book-list.component';

const routes: Routes = [
  { path: '', component: BookListComponent },
  { path: 'add', component: BookAddComponent },
  { path: 'update/:id', component: BookUpdateComponent },
  { path: 'remove', component: BookRemoveComponent },
  { path: '**', component: BookListComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
