import { Component, Input, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { Book } from 'src/app/models/book';
import { SchnitzelService } from 'src/app/services/schnitzel-service/schnitzel.service';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {
  
  bookList: Book[] = [];
  keyword: any = undefined;

  constructor(private route: ActivatedRoute,
    private router: Router, 
    private service: SchnitzelService) {
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.keyword = params['keyword'];
      this.bookList = [];
      this.service.getAllBooks(this.keyword).subscribe(data => {
        this.bookList = data
      });
    });
  }

  isBookshelfEmpty() {
    return this.bookList && this.bookList.length > 0;
  }

  bookAdd($myParam: string = ''): void {
    const navigationDetails: string[] = ['/add'];
    if($myParam.length) {
      navigationDetails.push($myParam);
    }
    this.router.navigate(navigationDetails);
  }

  bookUpdate(id: any = undefined): void {
    this.router.navigate(['/update', id]);
  }

  bookRemove(id: any = undefined): void {
    this.router.navigate(['/remove'], {queryParams: {id: `${id}` }});
  }
  
}
