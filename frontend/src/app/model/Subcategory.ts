import { Picture } from "./Picture";

export class Subcategory {
    id: number;
    name: string;
    categoryId: number;
    icon: Picture;
    containsOffers: boolean;
  }