import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SchnitzelService } from 'src/app/services/schnitzel-service/schnitzel.service';

@Component({
  selector: 'app-book-remove',
  templateUrl: './book-remove.component.html',
  styleUrls: ['./book-remove.component.css']
})
export class BookRemoveComponent implements OnInit {

  id: any = undefined;

  constructor(private route: ActivatedRoute,
    private router: Router, 
    private service: SchnitzelService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.route.queryParams.subscribe(params => {
        this.id = params['id'];
      });
    });
  }

  removeBook($myParam: string = ''): void { 
    this.service.removeBook(this.id).subscribe({
      next: (res) => console.log(res),
      error: (err) => {
        switch(err.status) {
          case 200:
            history.back();
            break;
          default:
            alert(err.error);
        }
      },
      complete: () => {} 
    });
  }

  cancel($myParam: string = ''): void { 
    history.back();
  }

}
