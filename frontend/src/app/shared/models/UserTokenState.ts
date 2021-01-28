import Model from "./Model";

export interface UserTokenState extends Model {
    token: string;
    expiresIn: string;
    userRole: string;
}