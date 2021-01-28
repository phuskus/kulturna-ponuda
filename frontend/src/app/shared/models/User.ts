import Model from './Model';

export interface User extends Model {
  name: string;
  surname: string;
  username: string;
  password: string;
}
