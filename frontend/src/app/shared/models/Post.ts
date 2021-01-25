import Model from "./Model";
import { Picture } from "./Picture";

export class Post extends Model{
    id: number;
    title: string;
    content: string;
    culturalOffer: number;
    offerName: string;
    datePosted: Date;
    pictures: Picture[];
  }