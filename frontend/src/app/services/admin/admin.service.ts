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
      categories: [],
      culutralOffers: []
    }
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
    categories: [],
    culutralOffers: [],
  },
  {
    id: 396,
    name: 'Hendrix Sharp',
    username: 'Keri',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 147,
    name: 'Bartlett Harper',
    username: 'Hays',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 220,
    name: 'Dickson Casey',
    username: 'Rachel',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 259,
    name: 'Mcguire Fuentes',
    username: 'Britt',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 391,
    name: 'Bolton Carver',
    username: 'Kris',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 219,
    name: 'Faye Park',
    username: 'Mcbride',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 278,
    name: 'George Lancaster',
    username: 'Cherry',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 183,
    name: 'Fitzpatrick Waters',
    username: 'Gayle',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 401,
    name: 'Leblanc Bryan',
    username: 'Kathy',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 74,
    name: 'Janie Ball',
    username: 'Leigh',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 234,
    name: 'Woods Valenzuela',
    username: 'Gilliam',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 20,
    name: 'Boyle Patel',
    username: 'Rachael',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 313,
    name: 'Bush Figueroa',
    username: 'Cora',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 201,
    name: 'Avis Jenkins',
    username: 'Trevino',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 259,
    name: 'Bianca Francis',
    username: 'Brandi',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 217,
    name: 'Molina Mcdonald',
    username: 'Nieves',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 336,
    name: 'Wilder Fox',
    username: 'Cardenas',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 7,
    name: 'Sonya Sanders',
    username: 'Massey',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 35,
    name: 'Bernadine Buck',
    username: 'Maxwell',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 34,
    name: 'Pittman Dyer',
    username: 'Kelsey',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 294,
    name: 'Perkins Mcneil',
    username: 'Walsh',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 392,
    name: 'Lana Levy',
    username: 'Jennie',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 363,
    name: 'Bright Randolph',
    username: 'Chasity',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 196,
    name: 'Hahn Huber',
    username: 'Lucile',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 344,
    name: 'Pacheco Nolan',
    username: 'Michael',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 92,
    name: 'Arnold Booker',
    username: 'Adkins',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 370,
    name: 'Stout Clements',
    username: 'Dora',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 329,
    name: 'Aguilar Morales',
    username: 'Cochran',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 402,
    name: 'Barrera Hoover',
    username: 'Burt',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 149,
    name: 'Lenora Gillespie',
    username: 'Aline',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 190,
    name: 'Salinas Tillman',
    username: 'Mai',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 31,
    name: 'Jaclyn Vargas',
    username: 'Paul',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 119,
    name: 'Boone Dalton',
    username: 'Leta',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 199,
    name: 'Rollins Winters',
    username: 'Harmon',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 324,
    name: 'Coffey Stuart',
    username: 'Tami',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 178,
    name: 'Walter Weeks',
    username: 'Hannah',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 118,
    name: 'Logan Ward',
    username: 'Hood',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 60,
    name: 'Banks Morse',
    username: 'Gina',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 133,
    name: 'Lois Brewer',
    username: 'Meyers',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 397,
    name: 'Betsy Bauer',
    username: 'England',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 263,
    name: 'Spears Miller',
    username: 'Taylor',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 300,
    name: 'Lewis Sloan',
    username: 'Reeves',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 169,
    name: 'Blair Lindsey',
    username: 'Faulkner',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 42,
    name: 'Smith Soto',
    username: 'Wyatt',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 62,
    name: 'Dejesus Combs',
    username: 'Sally',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 189,
    name: 'Mcconnell Kim',
    username: 'Rosie',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 85,
    name: 'Bauer Hoffman',
    username: 'Wilma',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 344,
    name: 'Hooper Reed',
    username: 'Simpson',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 245,
    name: 'Chambers Greene',
    username: 'Lynne',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 53,
    name: 'Billie Leblanc',
    username: 'Sullivan',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 414,
    name: 'Patrica Petty',
    username: 'Guthrie',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 331,
    name: 'Angelia Blanchard',
    username: 'Kristie',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 183,
    name: 'Hamilton Harrington',
    username: 'Hollie',
    password: '',
    categories: [],
    culutralOffers: [],
  },
  {
    id: 197,
    name: 'Kerri Hurst',
    username: 'Ursula',
    password: '',
    categories: [],
    culutralOffers: [],
  },
];
