import Model from './Model';

export interface User extends Model {
  name: string;
  username: string;
  password: string;
}
