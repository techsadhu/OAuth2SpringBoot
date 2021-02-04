import { Injectable } from '@angular/core';
import {
  HttpEvent,
  HttpInterceptor,
  HttpHandler,
  HttpRequest, HttpResponse
} from '@angular/common/http';

import {CookieService} from "ngx-cookie-service";
import {Observable, throwError} from 'rxjs';
import {catchError, map} from "rxjs/operators";
import * as _ from 'lodash';

/** Pass untouched request through to the next request handler. */
@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private cookieService: CookieService) {
  }
  intercept(req: HttpRequest<any>, next: HttpHandler):
    Observable<HttpEvent<any>> {
    let tokenizedReq = req;
    if (req.url !== location.href) {
      console.log('Intercepted***');
      const authorization = this.cookieService.get("authorization");
      console.log('AuthorizationCookie Value***'+authorization);
      tokenizedReq = req.clone({
        setHeaders: {
          authorization
        },
        body: req
      });
    }
    return next.handle(tokenizedReq)
      .pipe(
        map((event: HttpEvent<any>) => {
          if (event instanceof HttpResponse) {
            if (!event.body) {
              return event;
            }
          }
          return event;
        }),
        catchError(err => {
          this.handleError(err);
          return throwError(err);
        })
      );
  }

  handleError(err: any) {
    if (err.status && _.includes([401, 403], err.status)) {
      console.log('Error 401 or 403')
    }
  }
}
