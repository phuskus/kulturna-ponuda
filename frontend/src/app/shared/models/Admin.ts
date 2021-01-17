import Model from './Model';

export interface Admin extends Model {
  name: string;
  username: string;
  password: string;
  categories: any[];
  culutralOffers: any[];
}
