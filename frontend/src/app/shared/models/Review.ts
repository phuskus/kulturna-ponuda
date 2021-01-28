import Model from './Model';
import { Picture } from './Picture';
import { User } from './User';

export interface Review extends Model {
  rating: number;
  content: string;
  user: User;
  datePosted: Date;
  culturalOfferId: number;
  culturalOfferName: string;
  pictures: Picture[];
}
