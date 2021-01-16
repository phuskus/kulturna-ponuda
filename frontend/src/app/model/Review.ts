import { Picture } from "./Picture";

export class Review {
    id: number;
    rating: number;
    content: string;
    user: number;
    culturalOffer: number;
    pictures: Picture[];
  }