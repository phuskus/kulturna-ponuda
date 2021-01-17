import Model from "./Model";
import { User } from "./User";

export interface Review extends Model {
  rating: number;
  content: string;
  user: User;
  culturalOfferId: number;
  pictures: Array<string>;
}
