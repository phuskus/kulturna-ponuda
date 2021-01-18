import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Admin } from 'src/app/shared/models/Admin';
import { BaseService } from '../base/base.service';

@Injectable({
  providedIn: 'root',
})
export class AdminService extends BaseService {
  constructor() {
    super();
  }

  createEmpty(): Admin {
    return {
      id: 0,
      name: '',
      username: '',
      password: '',
    };
  }

  getAll(): Observable<Admin[]> {
    return of(ADMINS);
  }

  add(admin: Admin): Observable<Admin[]> {
    alert('admin added');
    console.log(admin);
    return null;
  }

  update(id: number, admin: Admin): Observable<Admin[]> {
    alert(`admin ${id} updated`);
    console.log(admin);
    return null;
  }

  delete(id: number): Observable<Admin[]> {
    alert(`admin ${id} deleted`);
    return null;
  }
}

const ADMINS: Admin[] = [
  {
    id: 430,
    name: 'Ora Marshall',
    username: 'Cameron',
    password: '',
  },
  {
    id: 396,
    name: 'Hendrix Sharp',
    username: 'Keri',
    password: '',
  },
  {
    id: 147,
    name: 'Bartlett Harper',
    username: 'Hays',
    password: '',
  },
  {
    id: 220,
    name: 'Dickson Casey',
    username: 'Rachel',
    password: '',
  },
  {
    id: 259,
    name: 'Mcguire Fuentes',
    username: 'Britt',
    password: '',
  },
  {
    id: 391,
    name: 'Bolton Carver',
    username: 'Kris',
    password: '',
  },
  {
    id: 219,
    name: 'Faye Park',
    username: 'Mcbride',
    password: '',
  },
  {
    id: 278,
    name: 'George Lancaster',
    username: 'Cherry',
    password: '',
  },
  {
    id: 183,
    name: 'Fitzpatrick Waters',
    username: 'Gayle',
    password: '',
  },
  {
    id: 401,
    name: 'Leblanc Bryan',
    username: 'Kathy',
    password: '',
  },
  {
    id: 74,
    name: 'Janie Ball',
    username: 'Leigh',
    password: '',
  },
  {
    id: 234,
    name: 'Woods Valenzuela',
    username: 'Gilliam',
    password: '',
  },
  {
    id: 20,
    name: 'Boyle Patel',
    username: 'Rachael',
    password: '',
  },
  {
    id: 313,
    name: 'Bush Figueroa',
    username: 'Cora',
    password: '',
  },
  {
    id: 201,
    name: 'Avis Jenkins',
    username: 'Trevino',
    password: '',
  },
  {
    id: 259,
    name: 'Bianca Francis',
    username: 'Brandi',
    password: '',
  },
  {
    id: 217,
    name: 'Molina Mcdonald',
    username: 'Nieves',
    password: '',
  },
  {
    id: 336,
    name: 'Wilder Fox',
    username: 'Cardenas',
    password: '',
  },
  {
    id: 7,
    name: 'Sonya Sanders',
    username: 'Massey',
    password: '',
  },
  {
    id: 35,
    name: 'Bernadine Buck',
    username: 'Maxwell',
    password: '',
  },
  {
    id: 34,
    name: 'Pittman Dyer',
    username: 'Kelsey',
    password: '',
  },
  {
    id: 294,
    name: 'Perkins Mcneil',
    username: 'Walsh',
    password: '',
  },
  {
    id: 392,
    name: 'Lana Levy',
    username: 'Jennie',
    password: '',
  },
  {
    id: 363,
    name: 'Bright Randolph',
    username: 'Chasity',
    password: '',
  },
  {
    id: 196,
    name: 'Hahn Huber',
    username: 'Lucile',
    password: '',
  },
  {
    id: 344,
    name: 'Pacheco Nolan',
    username: 'Michael',
    password: '',
  },
  {
    id: 92,
    name: 'Arnold Booker',
    username: 'Adkins',
    password: '',
  },
  {
    id: 370,
    name: 'Stout Clements',
    username: 'Dora',
    password: '',
  },
  {
    id: 329,
    name: 'Aguilar Morales',
    username: 'Cochran',
    password: '',
  },
  {
    id: 402,
    name: 'Barrera Hoover',
    username: 'Burt',
    password: '',
  },
  {
    id: 149,
    name: 'Lenora Gillespie',
    username: 'Aline',
    password: '',
  },
  {
    id: 190,
    name: 'Salinas Tillman',
    username: 'Mai',
    password: '',
  },
  {
    id: 31,
    name: 'Jaclyn Vargas',
    username: 'Paul',
    password: '',
  },
  {
    id: 119,
    name: 'Boone Dalton',
    username: 'Leta',
    password: '',
  },
  {
    id: 199,
    name: 'Rollins Winters',
    username: 'Harmon',
    password: '',
  },
  {
    id: 324,
    name: 'Coffey Stuart',
    username: 'Tami',
    password: '',
  },
  {
    id: 178,
    name: 'Walter Weeks',
    username: 'Hannah',
    password: '',
  },
  {
    id: 118,
    name: 'Logan Ward',
    username: 'Hood',
    password: '',
  },
  {
    id: 60,
    name: 'Banks Morse',
    username: 'Gina',
    password: '',
  },
  {
    id: 133,
    name: 'Lois Brewer',
    username: 'Meyers',
    password: '',
  },
  {
    id: 397,
    name: 'Betsy Bauer',
    username: 'England',
    password: '',
  },
  {
    id: 263,
    name: 'Spears Miller',
    username: 'Taylor',
    password: '',
  },
  {
    id: 300,
    name: 'Lewis Sloan',
    username: 'Reeves',
    password: '',
  },
  {
    id: 169,
    name: 'Blair Lindsey',
    username: 'Faulkner',
    password: '',
  },
  {
    id: 42,
    name: 'Smith Soto',
    username: 'Wyatt',
    password: '',
  },
  {
    id: 62,
    name: 'Dejesus Combs',
    username: 'Sally',
    password: '',
  },
  {
    id: 189,
    name: 'Mcconnell Kim',
    username: 'Rosie',
    password: '',
  },
  {
    id: 85,
    name: 'Bauer Hoffman',
    username: 'Wilma',
    password: '',
  },
  {
    id: 344,
    name: 'Hooper Reed',
    username: 'Simpson',
    password: '',
  },
  {
    id: 245,
    name: 'Chambers Greene',
    username: 'Lynne',
    password: '',
  },
  {
    id: 53,
    name: 'Billie Leblanc',
    username: 'Sullivan',
    password: '',
  },
  {
    id: 414,
    name: 'Patrica Petty',
    username: 'Guthrie',
    password: '',
  },
  {
    id: 331,
    name: 'Angelia Blanchard',
    username: 'Kristie',
    password: '',
  },
  {
    id: 183,
    name: 'Hamilton Harrington',
    username: 'Hollie',
    password: '',
  },
  {
    id: 197,
    name: 'Kerri Hurst',
    username: 'Ursula',
    password: '',
  },
];
