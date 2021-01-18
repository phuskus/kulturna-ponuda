import Model from "./Model";

export interface Picture extends Model {
  placeholder: string;
  image: string;
  path: string;
}
