import { Post } from "./Post";
import Model from "./Model";
import { Review } from "./Review";
import { Picture } from "./Picture";

export class CulturalOffer extends Model {
    name: string;
    description: string;
    latitude: number;
    longitude: number;
    address: string;
    city: string;
    region: string;
    admin: number;
    category: number;
    categoryName: string;
    reviews: Review[];
    posts: Post[];
    pictures: Picture[];
  }