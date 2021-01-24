import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Subscription } from "src/app/shared/models/Subscription";
import { BaseService } from "../base/base.service";
import { catchError } from 'rxjs/operators';
import { Observable, of } from "rxjs";

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

    getIsSubscribedToOffer(offerId: number) {
        return this.http
            .get<boolean>(this.url + '/offer/' + offerId, this.httpOptions);
    }

    getIsSubscribedToSubcategory(subcategoryName: string) {
        return this.http
            .get<boolean>(this.url + '/subcategory/' + subcategoryName, this.httpOptions);
    }

    subscribeToOffer(offerId: number) : Observable<string> {
        return this.http
            .post<string>(this.url + '/subscribeOffer/' + offerId, this.httpOptions);
    }

    unsubscribeFromOffer(offerId: number) : Observable<string> {
        return this.http
            .post<string>(this.url + '/unsubscribeOffer/' + offerId, this.httpOptions);
    }

    subscribeToSubcategory(subcategoryName: string) : Observable<string> {
        return this.http
        .post<string>(this.url + '/subscribeSubcategory/' + subcategoryName, this.httpOptions);
    }

    unsubscribeFromSubcategory(subcategoryName: string) : Observable<string> {
        return this.http
            .post<string>(this.url + '/subscribeSubcategory/' + subcategoryName, this.httpOptions);
    }
}