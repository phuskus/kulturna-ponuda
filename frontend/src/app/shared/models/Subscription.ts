import Model from "./Model";

export class Subscription extends Model {
    registeredUserId: number;
    culturalOfferId: number;
    subcategoryId: number;
    dateOfSubscription: string;
    culturalOfferName: string;
    subcategoryName: string;
  }