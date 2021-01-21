import Model from "./Model";
import { Picture } from "./Picture";

export class Post extends Model{
    id: number;
    content: string;
    culturalOffer: number;
    pictures: Picture[];
  }