import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Subscription } from "src/app/shared/models/Subscription";
import { Observable, of } from "rxjs";
import { SubscriptionResponse } from "src/app/shared/models/SubscriptionResponse";
import { IsSubscribedResponse } from "src/app/shared/models/IsSubscribedResponse";
import Page from "src/app/shared/models/Page";
import { BaseDynamicPagingService } from "../base/base-dynamic-paging.service";

@Injectable({
    providedIn: 'root',
})
export class SubscriptionService extends BaseDynamicPagingService {
    constructor(public http: HttpClient) {
        super('subscriptions', http);
    }

    createEmpty(): Subscription {
        return {
            id: 0,
            dateOfSubscription: "2021-01-24T14:10:25Z",
            registeredUserId: 0,
            subcategoryId: null,
            culturalOfferId: 0,
            culturalOfferName: "n/a",
            subcategoryName: "n/a"
        }
    }

    getIsSubscribedToOffer(offerId: number) : Observable<IsSubscribedResponse> {
        return this.http
            .get<IsSubscribedResponse>(this.url + '/offer/' + offerId, this.httpOptions);
    }

    subscribeToOffer(offerId: number) : Observable<SubscriptionResponse> {
        return this.http
            .post<SubscriptionResponse>(this.url + '/subscribeOffer/' + offerId, this.httpOptions);
    }

    unsubscribeFromOffer(offerId: number) : Observable<SubscriptionResponse> {
        return this.http
            .post<SubscriptionResponse>(this.url + '/unsubscribeOffer/' + offerId, this.httpOptions);
    }

    getIsSubscribedToSubcategory(subcategoryName: string) : Observable<IsSubscribedResponse> {
        return this.http
            .get<IsSubscribedResponse>(this.url + '/subcategory/' + subcategoryName, this.httpOptions);
    }

    subscribeToSubcategory(subcategoryName: string) : Observable<SubscriptionResponse> {
        return this.http
        .post<SubscriptionResponse>(this.url + '/subscribeSubcategory/' + subcategoryName, this.httpOptions);
    }

    unsubscribeFromSubcategory(subcategoryName: string) : Observable<SubscriptionResponse> {
        return this.http
            .post<SubscriptionResponse>(this.url + '/unsubscribeSubcategory/' + subcategoryName, this.httpOptions);
    }
}