import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NoAuthGuard } from 'src/app/services/auth/guards/no-auth.guard';
import { RoleGuard } from 'src/app/services/auth/guards/role.guard';
import { Role } from 'src/app/shared/models/Role';

import { AdminPage } from './pages/admin-page/admin.page';

const routes: Routes = [
  {
    path: '',
    component: AdminPage,
    canActivate: [NoAuthGuard, RoleGuard],
    data: { roles: [Role.ADMIN] },
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminRoutingModule {}
