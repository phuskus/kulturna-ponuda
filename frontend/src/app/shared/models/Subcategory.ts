import Model from "./Model";
import { Picture } from "./Picture";

export class Subcategory extends Model {
    name: string;
    categoryId: number;
    icon: Picture;
    containsOffers: boolean;
  }