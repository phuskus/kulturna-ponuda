import Model from './Model';

export class Admin extends Model {
  name: string;
  username: string;
  password: string;
  categories: any[];
  culutralOffers: any[];
}
