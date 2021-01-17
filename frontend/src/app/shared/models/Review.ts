import { User } from "./User";

export interface Review {
  id: number;
  rating: number;
  content: string;
  user: User;
  culturalOfferId: number;
  pictures: Array<string>;
}
