import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/services/admin/admin.service';
import { Admin } from 'src/app/shared/models/Admin';
import { AbstractTable } from '../../table/AbstractTable';

@Component({
  selector: 'app-admin-table',
  templateUrl: './admin-table.component.html',
  styleUrls: ['./admin-table.component.scss'],
})
export class AdminTableComponent extends AbstractTable<Admin> {
  constructor(service: AdminService) {
    super(service);
    this.tableColumns = ['id', 'name', 'username'];
  }
}
