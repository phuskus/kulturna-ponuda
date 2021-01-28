import Model from './Model';
import { Subcategory } from './Subcategory';

export interface Category extends Model {
  name: string;
  subcategories: Subcategory[];
}
