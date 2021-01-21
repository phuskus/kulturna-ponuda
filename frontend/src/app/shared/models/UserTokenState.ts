import Model from "./Model";

export class UserTokenState extends Model {
    token: string;
    expiresIn: string;
    userRole: string;
}