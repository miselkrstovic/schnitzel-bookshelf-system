import { Component, Input, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  public keyword: any = '';

  constructor(private route: ActivatedRoute,
    private router: Router) {
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.keyword = params['keyword'];
    });
  }

  search(): void {
    this.router.navigate(['/'], {queryParams: {keyword: `${this.keyword}` }});
  }

}
