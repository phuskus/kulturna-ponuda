import Model from "./Model";
import { Picture } from "./Picture";

export class Subcategory extends Model {
    name: string;
    categoryName: string;
    categoryId: number;
    icon: Picture;
    containsOffers: boolean;
  }