import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import {environment} from "../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class DemoService {

  url = environment.HELLO_WORLD
  constructor(private http: HttpClient ) { }

  getDataFromBackEnd(params: object){
    return this.http.post(this.url + '/api/helloworld', params);
  }

  getAuthToken() {
    console.log('GetAuthToken() : '+ location.href)
    return this.http.get(location.href ,  {
      observe: 'response',
      responseType: 'text',
      headers: new HttpHeaders({
        Accept: 'text/html',
        'Content-Type': 'application/json',
        'nice':'hello'
      })
    });
  }
}
