import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot} from '@angular/router';
import { Router } from '@angular/router';
import { Role } from 'src/app/shared/models/Role';
import { AuthService } from '../auth.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {
  
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const currentUserRole: Role = this.authService.getCurrentUserRole();
    
    //check if route is restricted by role
    if(route.data.roles && route.data.roles.indexOf(currentUserRole) === -1) {
      // role not authorised so redirect to home page
      this.router.navigate(['/']);
      return false;
    }
  
    // authorised so return true
    return true;
  }
  
}
