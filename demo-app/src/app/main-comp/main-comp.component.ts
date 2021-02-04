import { Component, OnInit } from '@angular/core';
import { DemoService } from "../demo.service";
import { CookieService} from "ngx-cookie-service";

@Component({
  selector: 'app-main-comp',
  templateUrl: './main-comp.component.html',
  styleUrls: ['./main-comp.component.sass']
})
export class MainCompComponent implements OnInit {

  strDemo: string ='';
  constructor(private demoService: DemoService, private cookieService: CookieService) { };
  consthelloStr
  helloStr: any;
  ngOnInit() {
    this.setAuthCookie();
    //this.getDataFromBackEnd()
  }
  setAuthCookie(): void {
    console.log('setAuthCookie() called');
    this.demoService
          .getAuthToken()
          .subscribe( (res: any) =>{
            const authorization = res.getHeader.get('Authorization')
            console.log('header is :'+authorization)
          if(authorization){
            this.cookieService.set('authorization', authorization,undefined, '/', undefined, undefined, 'Lax')
          }
    })
    console.log('The method setAuthCookie() completes' + this.cookieService.get('authorization'));
  }
  getDataFromBackEnd(): void {
    console.log('getDataFromBackEnd() called')
    this.demoService
          .getDataFromBackEnd({noDefaultParams: true})
          .subscribe((res: any) =>{
          if (res){
             this.helloStr = res;
          }
    })
  }

}
