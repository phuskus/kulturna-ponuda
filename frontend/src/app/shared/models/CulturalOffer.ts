import { Post } from './Post';
import Model from './Model';
import { Review } from './Review';
import { Picture } from './Picture';

export interface CulturalOffer extends Model {
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
  averageRating: number;
  posts: Post[];
  pictures: Picture[];
}
