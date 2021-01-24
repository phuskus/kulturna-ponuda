import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Subscription } from "src/app/shared/models/Subscription";
import { BaseService } from "../base/base.service";
import { catchError } from 'rxjs/operators';
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root',
})
export class SubscriptionService extends BaseService {
    constructor(public http: HttpClient) {
        super('subscriptions', http);
    }

    createEmpty(): Subscription {
        return {
            id: 0,
            dateOfSubscription: "2021-01-24T14:10:25Z",
            registeredUserId: 0,
            subcategoryId: null,
            culturalOfferId: 0
        }
    }

    getIsSubscribed(offerId: number) {
        return this.http
            .get<boolean>(this.url + '/offer/' + offerId, this.httpOptions)
            .pipe(catchError(this.handleError<boolean>()));
    }

    subscribeToOffer(offerId: number) : Observable<string> {
        return this.http
            .post<string>(this.url + '/subscribeOffer/' + offerId, this.httpOptions)
            .pipe(catchError(this.handleError<string>()));
    }

    unsubscribeFromOffer(offerId: number) : Observable<string> {
        return this.http
            .post<string>(this.url + '/unsubscribeOffer/' + offerId, this.httpOptions)
            .pipe(catchError(this.handleError<string>()));
    }

    subscribeToSubcategory(offerId: number) : Observable<string> {
        return this.http
        .post<string>(this.url + '/subscribeSubcategory/' + offerId, this.httpOptions)
        .pipe(catchError(this.handleError<string>()));
    }

    unsubscribeFromSubcategory(offerId: number) : Observable<string> {
        return this.http
            .post<string>(this.url + '/subscribeSubcategory/' + offerId, this.httpOptions)
            .pipe(catchError(this.handleError<string>()));
    }
}