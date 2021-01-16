import { Picture } from "./Picture";
import { Post } from "./Post";
import { Review } from "./Review";

export class CulturalOffer {
    id: number;
    name: string;
    description: string;
    latitude: number;
    longitude: number;
    address: string;
    city: string;
    region: string;
    admin: number;
    category: number;
    reviews: Review[];
    posts: Post[];
    pictures: Picture[];
  }