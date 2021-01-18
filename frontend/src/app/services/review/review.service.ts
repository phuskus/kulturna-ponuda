import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { delay } from 'rxjs/operators';
import { Review } from '../../shared/models/Review';
import PagingReturnValue, {
  BaseDynamicPagingService,
} from '../base/base-dynamic-paging.service';

@Injectable({
  providedIn: 'root',
})
export class ReviewService extends BaseDynamicPagingService {
  constructor() {
    super();
  }

  getPage(
    pageNumber: number,
    pageSize: number,
    sortBy: string,
    descending: boolean
  ): Observable<PagingReturnValue<Review>> {
    const reviews = REVIEWS.slice(
      pageSize * pageNumber,
      pageSize * pageNumber + pageSize
    );
    return of({ items: reviews, total_count: REVIEWS.length });
  }

  add(object: Review): Observable<Review[]> {
    alert('REVIEW ADDED');
    console.log(object);
    return null;
  }

  update(id: number, object: Review): Observable<Review[]> {
    alert('REVIEW UPDATED');
    return null;
  }

  delete(id: number): Observable<Review[]> {
    alert(`REVIEW WITH ID ${id} DELETED`);
    return null;
  }
}

const REVIEWS: Review[] = [
  {
    id: 392,
    rating: 1,
    content:
      'Et consequat sunt sit irure culpa non amet ad et officia. Magna ipsum consectetur qui exercitation culpa ut cupidatat culpa proident in minim minim incididunt. Cupidatat ut eu ipsum do ullamco irure eu do aliquip aute id ut.\r\n',
    user: {
      id: 279,
      name: 'Celia James',
      username: 'Marian',
      password: '',
    },
    culturalOfferId: 314,
    culturalOfferName: 'Acruex',
    pictures: [],
  },
  {
    id: 409,
    rating: 3,
    content:
      'Pariatur adipisicing Lorem nisi esse officia nisi dolor sunt consequat quis id duis. Sint laboris aute minim occaecat sunt magna labore. Lorem excepteur ad elit qui adipisicing aute et ea dolore excepteur reprehenderit adipisicing. Commodo voluptate ullamco ad ut laboris. Consectetur et nulla occaecat voluptate laborum laborum voluptate do consequat.\r\n',
    user: {
      id: 94,
      name: 'Owens Dalton',
      username: 'Linda',
      password: '',
    },
    culturalOfferId: 180,
    culturalOfferName: 'Gynk',
    pictures: [],
  },
  {
    id: 270,
    rating: 3,
    content:
      'Proident fugiat occaecat ex minim tempor culpa dolor ullamco cupidatat. Irure deserunt voluptate fugiat aute ullamco nisi duis dolore nulla commodo. Sunt veniam ut nulla id reprehenderit dolore sunt veniam sint commodo incididunt sunt id ea. Ad excepteur excepteur nostrud eiusmod culpa commodo est. Nostrud ea fugiat ullamco deserunt reprehenderit occaecat laborum non. Sint consequat sunt magna ad ullamco magna sunt commodo. Occaecat sit velit nostrud id dolore amet cillum aute Lorem ipsum cillum.\r\n',
    user: {
      id: 142,
      name: 'Malone Marquez',
      username: 'Delacruz',
      password: '',
    },
    culturalOfferId: 283,
    culturalOfferName: 'Isosure',
    pictures: [],
  },
  {
    id: 339,
    rating: 3,
    content:
      'Sunt et Lorem exercitation laborum adipisicing id ex sint laborum esse et cupidatat. Non nisi irure proident consectetur est deserunt dolor ex pariatur consequat do ad nisi irure. Amet cupidatat sit nisi exercitation anim aute eu occaecat enim et nisi aliqua aute aliquip. Duis et culpa cupidatat laboris aliqua officia incididunt. Proident esse nulla dolor consectetur est non do laboris ex consequat Lorem consectetur. Non velit consectetur cillum aliqua minim reprehenderit labore consectetur minim eu ut velit sint.\r\n',
    user: {
      id: 205,
      name: 'Naomi Montgomery',
      username: 'Leach',
      password: '',
    },
    culturalOfferId: 173,
    culturalOfferName: 'Limozen',
    pictures: [],
  },
  {
    id: 337,
    rating: 1,
    content:
      'Commodo minim tempor aliquip officia in commodo. Laborum exercitation quis labore elit quis nostrud nostrud est est commodo tempor do ad. Sunt mollit mollit cillum nulla tempor adipisicing. Culpa eu elit sint commodo qui.\r\n',
    user: {
      id: 12,
      name: 'Debbie Miles',
      username: 'Hampton',
      password: '',
    },
    culturalOfferId: 309,
    culturalOfferName: 'Suretech',
    pictures: [],
  },
  {
    id: 154,
    rating: 4,
    content:
      'Ad ex amet quis consequat mollit qui eu veniam esse sint ex ipsum incididunt nostrud. Elit cillum eiusmod tempor duis tempor aliqua officia duis cupidatat aute laborum. Do anim tempor consectetur proident reprehenderit cillum tempor nulla ullamco.\r\n',
    user: {
      id: 265,
      name: 'Lamb Travis',
      username: 'Lang',
      password: '',
    },
    culturalOfferId: 243,
    culturalOfferName: 'Comtest',
    pictures: [],
  },
  {
    id: 160,
    rating: 2,
    content:
      'Eiusmod aute laborum officia sit ex fugiat consectetur nisi est ex officia velit in. Cupidatat ullamco nisi culpa deserunt ullamco quis ad ut. Officia proident magna nisi magna est eiusmod aute aliqua fugiat laborum proident cupidatat sint.\r\n',
    user: {
      id: 45,
      name: 'Kelsey Justice',
      username: 'Esther',
      password: '',
    },
    culturalOfferId: 16,
    culturalOfferName: 'Undertap',
    pictures: [],
  },
  {
    id: 170,
    rating: 4,
    content:
      'Consequat magna pariatur ullamco cillum. Dolor fugiat ullamco eu ipsum irure consequat. Tempor nulla nulla excepteur proident consequat non labore ut labore veniam proident.\r\n',
    user: {
      id: 191,
      name: 'Bowen House',
      username: 'Nolan',
      password: '',
    },
    culturalOfferId: 295,
    culturalOfferName: 'Lotron',
    pictures: [],
  },
  {
    id: 198,
    rating: 5,
    content:
      'Pariatur nulla minim nostrud qui voluptate aute dolor ut. Labore velit do laboris nostrud. Labore cillum irure eiusmod magna fugiat eiusmod. Eiusmod do minim eiusmod anim consequat aliqua consectetur consectetur officia. Duis occaecat mollit commodo dolore nostrud. Tempor minim magna sunt ex eiusmod culpa reprehenderit eiusmod.\r\n',
    user: {
      id: 351,
      name: 'Gabriela Casey',
      username: 'Cook',
      password: '',
    },
    culturalOfferId: 107,
    culturalOfferName: 'Enomen',
    pictures: [],
  },
  {
    id: 258,
    rating: 2,
    content:
      'Ex deserunt dolor amet officia occaecat pariatur fugiat magna ut Lorem minim officia exercitation sint. Laborum in esse officia occaecat esse excepteur irure laboris elit est id exercitation enim. Proident duis exercitation cillum adipisicing qui aliqua laborum ipsum mollit in incididunt do do.\r\n',
    user: {
      id: 57,
      name: 'Simmons Rodriquez',
      username: 'Benjamin',
      password: '',
    },
    culturalOfferId: 148,
    culturalOfferName: 'Imaginart',
    pictures: [],
  },
  {
    id: 20,
    rating: 3,
    content:
      'Veniam enim eu dolor veniam tempor magna duis in aute sint. Sint aute ut pariatur sit aliquip duis dolore proident exercitation voluptate. Dolore exercitation pariatur est veniam deserunt. Reprehenderit irure dolore et sit sunt tempor fugiat enim enim. Esse Lorem voluptate irure do laborum ex laborum veniam laboris. Dolore laboris laborum fugiat reprehenderit in non laborum. Elit officia sit non do fugiat ea sit.\r\n',
    user: {
      id: 339,
      name: 'Mcmahon Hays',
      username: 'Camacho',
      password: '',
    },
    culturalOfferId: 174,
    culturalOfferName: 'Daido',
    pictures: [],
  },
  {
    id: 43,
    rating: 1,
    content:
      'Anim elit adipisicing deserunt sit pariatur labore aute do in ex ea mollit ad. Commodo id Lorem fugiat occaecat laboris cillum culpa nostrud aute laboris do quis labore eu. Duis incididunt Lorem ipsum exercitation sit irure. Lorem adipisicing adipisicing adipisicing cillum est consequat in irure aliqua consequat magna dolore est. Velit minim magna nisi pariatur dolor deserunt ea commodo est adipisicing nostrud sit nostrud. Quis consequat culpa fugiat reprehenderit adipisicing sint excepteur tempor duis nisi culpa.\r\n',
    user: {
      id: 36,
      name: 'Leona Kaufman',
      username: 'Small',
      password: '',
    },
    culturalOfferId: 133,
    culturalOfferName: 'Zaphire',
    pictures: [],
  },
  {
    id: 262,
    rating: 4,
    content:
      'Ullamco elit magna in aliquip incididunt reprehenderit. Ex laboris reprehenderit Lorem veniam cupidatat veniam reprehenderit. Qui labore eu pariatur aliqua tempor in consectetur elit nulla Lorem. Adipisicing reprehenderit nulla deserunt officia qui laboris commodo. Consectetur reprehenderit dolore excepteur in sit sit elit magna dolore. Excepteur exercitation officia dolor tempor labore consequat minim nostrud aliqua ut ipsum esse. Sit consectetur do proident ullamco.\r\n',
    user: {
      id: 192,
      name: 'Margo Bauer',
      username: 'Wilkerson',
      password: '',
    },
    culturalOfferId: 52,
    culturalOfferName: 'Valreda',
    pictures: [],
  },
  {
    id: 21,
    rating: 1,
    content:
      'Occaecat ad enim in esse est irure amet elit proident. Anim voluptate eu sunt adipisicing ea ex tempor ea eiusmod quis. Laboris eu dolor aute anim ea.\r\n',
    user: {
      id: 147,
      name: 'Nadine Short',
      username: 'Keller',
      password: '',
    },
    culturalOfferId: 410,
    culturalOfferName: 'Extragen',
    pictures: [],
  },
  {
    id: 116,
    rating: 2,
    content:
      'Minim deserunt pariatur aliquip fugiat sint minim sint consequat sunt fugiat nulla ullamco aliqua irure. Ex elit culpa cupidatat velit. Qui veniam pariatur culpa laboris commodo. Irure in elit magna aliqua. Culpa aute ad tempor reprehenderit occaecat cupidatat minim nostrud duis et occaecat in. Ut velit aliquip aliqua incididunt est minim.\r\n',
    user: {
      id: 328,
      name: 'Latasha Guthrie',
      username: 'Candy',
      password: '',
    },
    culturalOfferId: 155,
    culturalOfferName: 'Quotezart',
    pictures: [],
  },
  {
    id: 295,
    rating: 3,
    content:
      'Ea officia esse do exercitation proident. Ipsum enim fugiat sint non cupidatat irure. Fugiat duis nostrud reprehenderit Lorem Lorem ex ipsum cillum aute est do adipisicing anim. Enim nisi adipisicing excepteur ea fugiat laborum irure tempor proident aliqua tempor laborum. Non voluptate enim est Lorem esse voluptate exercitation excepteur aliqua nulla Lorem quis tempor in. Nostrud non minim ad mollit sunt ut eu amet pariatur occaecat sunt ea.\r\n',
    user: {
      id: 359,
      name: 'Lloyd Garrison',
      username: 'Maggie',
      password: '',
    },
    culturalOfferId: 36,
    culturalOfferName: 'Genmex',
    pictures: [],
  },
  {
    id: 156,
    rating: 5,
    content:
      'Officia labore irure elit dolor adipisicing pariatur culpa esse occaecat irure reprehenderit ipsum proident eiusmod. Ipsum occaecat quis elit anim excepteur magna deserunt velit eu in esse consectetur. Et esse qui irure laboris proident mollit do sint.\r\n',
    user: {
      id: 141,
      name: 'Mathews Jefferson',
      username: 'Wanda',
      password: '',
    },
    culturalOfferId: 367,
    culturalOfferName: 'Kangle',
    pictures: [],
  },
  {
    id: 284,
    rating: 2,
    content:
      'Velit fugiat anim eu labore qui. Reprehenderit laboris fugiat elit duis aliqua exercitation nisi qui anim sunt. Elit ipsum et ipsum irure. Magna sit cupidatat et enim exercitation et dolor. In ut ut ad exercitation consequat officia quis nulla id cillum aute.\r\n',
    user: {
      id: 17,
      name: 'Rachael Conley',
      username: 'Zimmerman',
      password: '',
    },
    culturalOfferId: 241,
    culturalOfferName: 'Combogen',
    pictures: [],
  },
  {
    id: 209,
    rating: 1,
    content:
      'Officia veniam minim tempor in. Deserunt qui enim officia deserunt dolor ex. Ullamco cupidatat tempor adipisicing non aute do excepteur labore enim laborum in. Est irure quis sint nulla culpa occaecat veniam veniam. Est consequat dolore esse id sint ullamco. Irure cillum cillum minim mollit adipisicing quis non dolore. Esse voluptate eu magna velit laboris consequat reprehenderit magna fugiat sint deserunt elit.\r\n',
    user: {
      id: 102,
      name: 'Schwartz Bush',
      username: 'Melba',
      password: '',
    },
    culturalOfferId: 148,
    culturalOfferName: 'Oulu',
    pictures: [],
  },
  {
    id: 226,
    rating: 5,
    content:
      'Irure elit culpa ex consequat irure adipisicing non nostrud incididunt velit. Laborum enim ad sint ex esse. Ullamco culpa nostrud nulla ullamco dolore minim amet nisi elit.\r\n',
    user: {
      id: 98,
      name: 'Burns Tran',
      username: 'Barbara',
      password: '',
    },
    culturalOfferId: 374,
    culturalOfferName: 'Fiberox',
    pictures: [],
  },
  {
    id: 413,
    rating: 1,
    content:
      'Officia culpa qui laboris laboris labore laboris labore eiusmod in sit dolor duis amet non. Ad proident aliquip labore veniam. Proident ex sint sint ea adipisicing minim dolore. Consequat ea consectetur duis aute laboris enim mollit consectetur sit.\r\n',
    user: {
      id: 34,
      name: 'Simon Copeland',
      username: 'Montoya',
      password: '',
    },
    culturalOfferId: 385,
    culturalOfferName: 'Talkalot',
    pictures: [],
  },
  {
    id: 418,
    rating: 4,
    content:
      'Proident sint consectetur commodo veniam magna sint sit culpa quis in commodo id eiusmod labore. Excepteur cillum adipisicing commodo sunt sunt ut quis ad id. Aute cupidatat ipsum irure cupidatat exercitation eu mollit occaecat in cillum ea occaecat. Ipsum qui irure esse non magna eu deserunt non. Occaecat adipisicing laborum nostrud non.\r\n',
    user: {
      id: 418,
      name: 'Louella Boyd',
      username: 'Pearson',
      password: '',
    },
    culturalOfferId: 383,
    culturalOfferName: 'Quilk',
    pictures: [],
  },
  {
    id: 163,
    rating: 2,
    content:
      'Amet laboris velit qui excepteur culpa irure ad ipsum adipisicing tempor. Culpa ut fugiat in eu amet. Ex eu occaecat fugiat veniam laboris. Elit reprehenderit incididunt officia cillum. Veniam id ex quis proident consequat culpa reprehenderit aliquip officia culpa adipisicing est. Officia pariatur velit non officia.\r\n',
    user: {
      id: 218,
      name: 'Britney Allen',
      username: 'Chan',
      password: '',
    },
    culturalOfferId: 391,
    culturalOfferName: 'Savvy',
    pictures: [],
  },
  {
    id: 371,
    rating: 1,
    content:
      'Eu esse fugiat amet sunt id exercitation velit do in. Dolore amet cillum ex nostrud deserunt mollit est. Consequat et labore voluptate ut ea. Nostrud qui laborum irure est sunt non ad fugiat. Proident ut sit labore deserunt id id consequat quis aliquip excepteur eiusmod anim ullamco proident. Eu voluptate occaecat consequat nulla irure mollit. Elit ut fugiat et proident velit.\r\n',
    user: {
      id: 25,
      name: 'Sanchez Battle',
      username: 'Casey',
      password: '',
    },
    culturalOfferId: 3,
    culturalOfferName: 'Comdom',
    pictures: [],
  },
  {
    id: 393,
    rating: 2,
    content:
      'Eu amet irure magna incididunt irure ea commodo labore in consequat. Enim aliquip ipsum qui culpa enim culpa eu pariatur proident dolore voluptate sint anim. Veniam cupidatat in qui ad irure deserunt enim labore do reprehenderit laboris sunt cupidatat ipsum. Consectetur ad in consectetur minim sint esse ea labore sunt aute amet fugiat. Dolore qui cillum deserunt enim irure sit tempor voluptate. Proident sit mollit dolor cillum consectetur voluptate dolore ullamco ad fugiat aliqua. Mollit commodo aute sunt anim amet velit aliquip esse dolore enim duis.\r\n',
    user: {
      id: 371,
      name: 'Lynda Ellis',
      username: 'Mcintyre',
      password: '',
    },
    culturalOfferId: 272,
    culturalOfferName: 'Unq',
    pictures: [],
  },
  {
    id: 125,
    rating: 5,
    content:
      'Velit veniam nostrud labore aliquip deserunt magna voluptate sit culpa. Proident officia tempor aliquip ut dolore aliquip. Nulla deserunt in exercitation do nisi sunt eiusmod quis cillum culpa esse fugiat ex. Laborum sunt duis dolore Lorem. Dolor tempor Lorem elit ut ipsum nulla do veniam et enim sunt adipisicing exercitation tempor. Nisi ea non quis sint consectetur.\r\n',
    user: {
      id: 355,
      name: 'Latoya Landry',
      username: 'Christy',
      password: '',
    },
    culturalOfferId: 124,
    culturalOfferName: 'Kineticut',
    pictures: [],
  },
  {
    id: 115,
    rating: 1,
    content:
      'Anim nisi amet deserunt proident. Aliquip Lorem fugiat non dolore ipsum tempor pariatur magna ipsum excepteur proident id. Qui ad est nostrud velit nisi incididunt adipisicing qui sunt dolore irure aliqua. Dolor cupidatat consequat pariatur excepteur elit irure Lorem exercitation duis ipsum magna voluptate ut dolor. Et sint proident nostrud id non adipisicing incididunt eu sit ipsum. Voluptate nisi dolor culpa do minim minim incididunt do incididunt in incididunt proident proident incididunt.\r\n',
    user: {
      id: 302,
      name: 'Duffy Hess',
      username: 'Cecilia',
      password: '',
    },
    culturalOfferId: 123,
    culturalOfferName: 'Fishland',
    pictures: [],
  },
  {
    id: 339,
    rating: 1,
    content:
      'Eiusmod voluptate quis in est in commodo cillum. Excepteur nostrud tempor dolore duis non. Consectetur mollit commodo velit minim deserunt eu deserunt officia dolor exercitation incididunt tempor amet anim. Amet eiusmod magna nulla amet non.\r\n',
    user: {
      id: 159,
      name: 'Lyons Stevenson',
      username: 'Mccray',
      password: '',
    },
    culturalOfferId: 25,
    culturalOfferName: 'Zillan',
    pictures: [],
  },
  {
    id: 37,
    rating: 5,
    content:
      'Cupidatat minim cillum ullamco minim enim dolore tempor commodo sunt Lorem ad. Minim aliquip fugiat excepteur in non quis veniam mollit nulla. Adipisicing anim non pariatur incididunt consequat cupidatat elit excepteur est proident. Proident consequat duis labore sint minim officia proident exercitation tempor nulla duis consectetur ipsum. Nostrud elit elit enim excepteur esse Lorem.\r\n',
    user: {
      id: 335,
      name: 'Kim Garza',
      username: 'Selma',
      password: '',
    },
    culturalOfferId: 259,
    culturalOfferName: 'Silodyne',
    pictures: [],
  },
  {
    id: 353,
    rating: 5,
    content:
      'Aliqua dolor eu commodo velit occaecat enim excepteur sunt officia nisi quis amet amet. Qui pariatur et veniam aute veniam incididunt occaecat. Anim eiusmod ad velit laboris sunt fugiat. Pariatur quis dolor Lorem nulla labore mollit et mollit nostrud sunt officia cupidatat laborum aliqua. Ullamco sunt consequat labore excepteur laborum enim sit proident proident irure excepteur duis esse. Laborum incididunt exercitation veniam tempor eu ex commodo do magna elit. Dolore incididunt mollit et occaecat anim amet ullamco nulla minim.\r\n',
    user: {
      id: 222,
      name: 'Drake Brewer',
      username: 'Tia',
      password: '',
    },
    culturalOfferId: 247,
    culturalOfferName: 'Dancity',
    pictures: [],
  },
  {
    id: 52,
    rating: 3,
    content:
      'Laboris elit amet nostrud consequat ullamco. Excepteur occaecat quis fugiat amet et culpa. Tempor ad minim do culpa veniam eu. Esse fugiat dolore occaecat pariatur anim laborum deserunt incididunt sunt.\r\n',
    user: {
      id: 74,
      name: 'Margery Foreman',
      username: 'Jeannette',
      password: '',
    },
    culturalOfferId: 431,
    culturalOfferName: 'Kyaguru',
    pictures: [],
  },
  {
    id: 299,
    rating: 1,
    content:
      'Culpa nulla eiusmod labore consequat. Culpa voluptate ea ipsum tempor culpa cillum. Non sit do est Lorem deserunt quis ullamco.\r\n',
    user: {
      id: 408,
      name: 'Henry Mcdonald',
      username: 'Morris',
      password: '',
    },
    culturalOfferId: 54,
    culturalOfferName: 'Phuel',
    pictures: [],
  },
  {
    id: 100,
    rating: 5,
    content:
      'Aute commodo laborum elit veniam do reprehenderit quis laboris exercitation. Quis dolor sit incididunt id laborum. Incididunt enim occaecat consequat pariatur. Est ullamco cupidatat veniam velit dolor nisi amet. Velit quis eu velit id cillum fugiat consectetur ipsum deserunt laboris esse sit est dolore. Et incididunt consequat anim minim. Mollit tempor exercitation do excepteur ut non id dolor enim laboris aliqua aute mollit sunt.\r\n',
    user: {
      id: 358,
      name: 'Miller Deleon',
      username: 'Hooper',
      password: '',
    },
    culturalOfferId: 94,
    culturalOfferName: 'Zogak',
    pictures: [],
  },
  {
    id: 265,
    rating: 3,
    content:
      'Anim ut cupidatat labore cillum laborum consequat eu tempor ea sit cillum adipisicing incididunt. Ullamco nostrud sint reprehenderit irure in occaecat velit dolore dolore esse est eiusmod nostrud. Exercitation ad consequat qui ad fugiat voluptate amet sint. Ipsum enim commodo reprehenderit non dolor. Quis laboris qui do aliqua nulla dolor dolor reprehenderit exercitation irure mollit.\r\n',
    user: {
      id: 257,
      name: 'Sonia Mcgowan',
      username: 'Dominique',
      password: '',
    },
    culturalOfferId: 279,
    culturalOfferName: 'Animalia',
    pictures: [],
  },
  {
    id: 217,
    rating: 5,
    content:
      'Qui reprehenderit sunt occaecat sit quis esse do officia dolore. Amet aute in do eiusmod labore irure ea mollit nostrud ut est elit duis quis. Reprehenderit reprehenderit incididunt sit officia proident sint commodo eu ut consectetur ex eu do. Laborum reprehenderit officia et dolor aliqua.\r\n',
    user: {
      id: 413,
      name: 'Karin Valdez',
      username: 'Hodges',
      password: '',
    },
    culturalOfferId: 177,
    culturalOfferName: 'Wazzu',
    pictures: [],
  },
  {
    id: 230,
    rating: 2,
    content:
      'Aliquip cillum tempor veniam proident. Et veniam consectetur duis velit deserunt amet excepteur minim. Pariatur dolore aliquip proident sit exercitation tempor mollit. Dolore et est sint occaecat ipsum culpa pariatur id duis pariatur cillum minim. Excepteur Lorem nulla deserunt ea cillum.\r\n',
    user: {
      id: 116,
      name: 'Stafford Ayers',
      username: 'Robin',
      password: '',
    },
    culturalOfferId: 413,
    culturalOfferName: 'Comtext',
    pictures: [],
  },
  {
    id: 60,
    rating: 4,
    content:
      'Reprehenderit quis sunt ullamco commodo reprehenderit irure Lorem elit. Magna enim dolor consequat magna occaecat aliqua nostrud consectetur exercitation incididunt. Commodo Lorem ut consectetur officia esse irure eu aliqua cupidatat fugiat. Sit exercitation exercitation elit adipisicing Lorem occaecat ad ex veniam. Laboris ipsum enim sunt veniam aliquip pariatur. Esse elit reprehenderit officia deserunt.\r\n',
    user: {
      id: 268,
      name: 'Mitzi Long',
      username: 'Copeland',
      password: '',
    },
    culturalOfferId: 358,
    culturalOfferName: 'Ontagene',
    pictures: [],
  },
  {
    id: 383,
    rating: 5,
    content:
      'Et in culpa deserunt magna eiusmod minim consectetur in ea. Veniam tempor commodo labore dolore ipsum deserunt cupidatat eiusmod ad sint laboris. Sit velit sit magna velit cupidatat velit deserunt deserunt. Adipisicing eiusmod dolor aliqua deserunt non amet proident. Fugiat laborum non minim eu dolor aliqua.\r\n',
    user: {
      id: 29,
      name: 'Wynn Schwartz',
      username: 'Helen',
      password: '',
    },
    culturalOfferId: 63,
    culturalOfferName: 'Prismatic',
    pictures: [],
  },
  {
    id: 404,
    rating: 1,
    content:
      'Laborum veniam id exercitation officia officia. Eu velit qui ullamco ullamco nostrud culpa esse commodo cupidatat occaecat et dolore. Ipsum ad eu sint magna eu elit ipsum commodo anim ea proident. Id anim in minim in proident tempor ipsum irure commodo commodo. Nulla velit consequat aliquip excepteur minim dolore id non laborum. Ea ipsum incididunt in officia culpa velit cupidatat anim cupidatat cillum. Officia commodo labore nulla ea id irure aliqua sunt nisi labore excepteur elit fugiat tempor.\r\n',
    user: {
      id: 316,
      name: 'Rodriquez Cantu',
      username: 'Justice',
      password: '',
    },
    culturalOfferId: 180,
    culturalOfferName: 'Beadzza',
    pictures: [],
  },
  {
    id: 77,
    rating: 2,
    content:
      'Nulla aliqua occaecat excepteur Lorem quis aute. Laboris excepteur reprehenderit pariatur Lorem fugiat ex qui exercitation mollit excepteur voluptate cillum anim. Fugiat aute do reprehenderit voluptate qui elit aute consequat incididunt enim sit. Laboris anim culpa deserunt nulla. Laboris proident Lorem ullamco reprehenderit ad excepteur deserunt.\r\n',
    user: {
      id: 431,
      name: 'Maryellen Marshall',
      username: 'Whitley',
      password: '',
    },
    culturalOfferId: 365,
    culturalOfferName: 'Repetwire',
    pictures: [],
  },
  {
    id: 323,
    rating: 2,
    content:
      'Consectetur occaecat reprehenderit dolore et amet amet deserunt reprehenderit qui qui amet officia. Incididunt non reprehenderit eiusmod voluptate amet adipisicing nisi cupidatat mollit. Sit ad exercitation Lorem eiusmod non velit pariatur minim officia ullamco mollit.\r\n',
    user: {
      id: 252,
      name: 'Pickett Sellers',
      username: 'Warner',
      password: '',
    },
    culturalOfferId: 403,
    culturalOfferName: 'Comvene',
    pictures: [],
  },
  {
    id: 361,
    rating: 5,
    content:
      'Ex cillum reprehenderit pariatur consectetur ea id aliqua irure adipisicing eiusmod aliqua minim. Ullamco sit labore nisi dolor laboris esse anim nostrud irure exercitation ad commodo excepteur. Dolor velit duis pariatur reprehenderit qui fugiat exercitation nisi aliqua labore nulla. Voluptate pariatur ullamco et in aute veniam aute sunt irure incididunt elit consequat qui.\r\n',
    user: {
      id: 95,
      name: 'Washington Whitaker',
      username: 'Graciela',
      password: '',
    },
    culturalOfferId: 132,
    culturalOfferName: 'Inear',
    pictures: [],
  },
  {
    id: 74,
    rating: 3,
    content:
      'Adipisicing cupidatat enim ex ad nostrud non fugiat occaecat. Esse ullamco enim aute ex cupidatat Lorem amet consequat. Cillum pariatur sunt ex elit elit duis nostrud pariatur consectetur anim cupidatat officia. Quis aliquip ad nostrud anim officia ea quis pariatur ea nisi ut labore sunt. Nostrud amet adipisicing non est. Quis cupidatat incididunt in adipisicing laboris sit. Proident consectetur deserunt nisi culpa ad proident ex aliquip.\r\n',
    user: {
      id: 128,
      name: 'Kidd Hopper',
      username: 'Greta',
      password: '',
    },
    culturalOfferId: 308,
    culturalOfferName: 'Dentrex',
    pictures: [],
  },
  {
    id: 90,
    rating: 5,
    content:
      'Fugiat et ad amet occaecat pariatur deserunt eu sit duis qui. Minim nulla aliqua consectetur occaecat magna excepteur ullamco et adipisicing consectetur labore Lorem. Exercitation ipsum dolor ad et commodo aute.\r\n',
    user: {
      id: 6,
      name: 'Amalia Griffith',
      username: 'Trina',
      password: '',
    },
    culturalOfferId: 97,
    culturalOfferName: 'Colaire',
    pictures: [],
  },
  {
    id: 351,
    rating: 5,
    content:
      'Deserunt amet officia culpa sunt. Fugiat magna aute est ipsum minim velit ullamco et irure duis duis ex ex. Officia consequat exercitation deserunt ullamco reprehenderit. Elit laborum aute aliqua sunt consectetur nostrud in consequat Lorem ea consequat proident. Mollit cillum ex nisi aliqua reprehenderit laboris deserunt do. Consequat quis ullamco elit sunt mollit elit consequat occaecat et adipisicing anim occaecat occaecat et.\r\n',
    user: {
      id: 353,
      name: 'Solis Fisher',
      username: 'Alice',
      password: '',
    },
    culturalOfferId: 230,
    culturalOfferName: 'Zolar',
    pictures: [],
  },
  {
    id: 198,
    rating: 3,
    content:
      'Aute aute sint ad nisi dolor aliquip. Elit qui ipsum laborum pariatur eu anim tempor eiusmod ea dolore ea eu duis non. Culpa exercitation non proident cillum laboris aliqua ex enim mollit do enim sunt consectetur. Cillum id consequat ut culpa sit ea incididunt duis laboris dolor consectetur sunt labore aute. Dolor aute Lorem minim qui reprehenderit occaecat ut est consectetur sint pariatur ullamco.\r\n',
    user: {
      id: 383,
      name: 'Langley Delaney',
      username: 'Moss',
      password: '',
    },
    culturalOfferId: 199,
    culturalOfferName: 'Ovation',
    pictures: [],
  },
  {
    id: 138,
    rating: 1,
    content:
      'Elit fugiat amet tempor ex duis deserunt occaecat sunt exercitation pariatur pariatur. Ad voluptate irure non incididunt mollit incididunt tempor mollit eu et. Aute et quis enim magna laboris. Adipisicing eu duis elit duis anim aliqua mollit commodo ullamco esse do. Pariatur officia esse sit ad ad esse sunt tempor ullamco id dolor non irure. Eiusmod qui non laborum exercitation duis.\r\n',
    user: {
      id: 431,
      name: 'Cheryl Kelly',
      username: 'Hale',
      password: '',
    },
    culturalOfferId: 398,
    culturalOfferName: 'Digial',
    pictures: [],
  },
  {
    id: 254,
    rating: 3,
    content:
      'Sit commodo anim sit tempor elit ut ullamco cupidatat adipisicing consectetur ut ullamco laboris ut. Minim id elit deserunt tempor eiusmod laborum irure do voluptate. Laborum mollit anim nisi laborum quis nisi laboris eiusmod ipsum deserunt veniam. Dolore ex cupidatat elit qui officia qui. Nisi fugiat consectetur ad deserunt ex excepteur.\r\n',
    user: {
      id: 230,
      name: 'Opal Vazquez',
      username: 'Olivia',
      password: '',
    },
    culturalOfferId: 322,
    culturalOfferName: 'Pheast',
    pictures: [],
  },
  {
    id: 383,
    rating: 2,
    content:
      'Minim ea anim pariatur ea culpa. Lorem laboris sint nisi enim irure incididunt ut non nisi amet. Lorem ullamco et dolor nisi tempor enim non aliqua anim amet dolore nulla. Lorem eu irure magna cillum labore laborum enim. Ex ad sunt ipsum et dolore.\r\n',
    user: {
      id: 384,
      name: 'Sheena Lynn',
      username: 'Pitts',
      password: '',
    },
    culturalOfferId: 137,
    culturalOfferName: 'Tsunamia',
    pictures: [],
  },
  {
    id: 347,
    rating: 5,
    content:
      'Irure est sit ullamco exercitation aute id ea velit laboris ea. Excepteur culpa occaecat in cillum amet consectetur aute. Mollit anim esse commodo labore sint exercitation nulla incididunt eiusmod elit laboris aute dolore magna. Sint occaecat esse magna laborum ipsum adipisicing exercitation incididunt magna commodo cupidatat. Fugiat et cillum pariatur labore nisi incididunt dolore tempor excepteur incididunt culpa eiusmod qui. Fugiat ipsum adipisicing nulla labore cillum enim aliqua magna sit.\r\n',
    user: {
      id: 327,
      name: 'Hood Lara',
      username: 'Kirkland',
      password: '',
    },
    culturalOfferId: 224,
    culturalOfferName: 'Globoil',
    pictures: [],
  },
  {
    id: 342,
    rating: 5,
    content:
      'Laborum ipsum magna sit sit nisi qui. Ullamco duis pariatur et enim aliquip ex proident aliqua dolor adipisicing qui. Laborum eu sunt occaecat do. Lorem adipisicing cillum consequat dolor non minim nulla occaecat eu dolore cupidatat ullamco. Consectetur nulla pariatur consequat voluptate nostrud aliquip.\r\n',
    user: {
      id: 185,
      name: 'Marguerite Fitzpatrick',
      username: 'Grant',
      password: '',
    },
    culturalOfferId: 238,
    culturalOfferName: 'Sloganaut',
    pictures: [],
  },
  {
    id: 46,
    rating: 2,
    content:
      'Lorem ex nisi mollit ea fugiat occaecat dolor nisi consequat eu sunt eiusmod. Nostrud tempor qui est commodo culpa commodo laboris ut. Dolore irure cupidatat amet ad aute anim non. Do deserunt nisi pariatur nisi est minim qui ullamco tempor id tempor ex. Non deserunt cillum anim magna irure do esse aliquip proident minim nostrud eu Lorem magna. Laboris cupidatat tempor magna ea ipsum commodo do nulla sit occaecat commodo commodo fugiat. Proident quis do aliquip fugiat.\r\n',
    user: {
      id: 412,
      name: 'Ayala Lee',
      username: 'Rachel',
      password: '',
    },
    culturalOfferId: 192,
    culturalOfferName: 'Vertide',
    pictures: [],
  },
  {
    id: 98,
    rating: 4,
    content:
      'Nostrud qui eu aliqua dolor proident reprehenderit tempor eiusmod. Exercitation anim elit amet elit ullamco id in eiusmod ut sint commodo id adipisicing. Labore dolore proident incididunt tempor. Ipsum aliquip sunt ut nulla cupidatat. Anim reprehenderit esse ullamco laborum officia ad sunt labore.\r\n',
    user: {
      id: 259,
      name: 'Jane Chapman',
      username: 'Kristie',
      password: '',
    },
    culturalOfferId: 279,
    culturalOfferName: 'Zorromop',
    pictures: [],
  },
  {
    id: 139,
    rating: 5,
    content:
      'In sit sunt dolor dolor tempor aliquip magna voluptate laborum labore. Pariatur irure deserunt aliqua nostrud ad. Enim magna in cupidatat veniam voluptate exercitation nulla. Aliqua occaecat nulla ea excepteur et eiusmod sint culpa. Aliquip duis Lorem ad in anim exercitation esse dolore ut aliquip ullamco sit. Anim ut velit culpa occaecat excepteur ullamco fugiat incididunt nisi laboris commodo dolore mollit.\r\n',
    user: {
      id: 409,
      name: 'Clemons Hamilton',
      username: 'Velasquez',
      password: '',
    },
    culturalOfferId: 375,
    culturalOfferName: 'Illumity',
    pictures: [],
  },
  {
    id: 389,
    rating: 5,
    content:
      'Elit quis nulla id enim officia nostrud eiusmod enim reprehenderit consequat. Eiusmod veniam voluptate proident magna sunt eu est officia anim esse sint incididunt eiusmod mollit. Velit excepteur excepteur anim est non nulla minim reprehenderit officia voluptate ex. Lorem excepteur irure aliquip culpa esse ut qui. Ex do exercitation officia velit anim non veniam eu et eu aute reprehenderit fugiat non. Sint fugiat irure do eu sint amet non dolor.\r\n',
    user: {
      id: 420,
      name: 'Boone Rich',
      username: 'Cooley',
      password: '',
    },
    culturalOfferId: 63,
    culturalOfferName: 'Velity',
    pictures: [],
  },
  {
    id: 16,
    rating: 4,
    content:
      'Veniam excepteur id aliqua sit veniam laboris do anim magna irure mollit irure voluptate. Ipsum laboris est enim eiusmod dolore culpa elit. Consequat velit adipisicing ea minim. Id dolore eu ad veniam duis irure ad ipsum id dolor. Adipisicing elit esse sint exercitation quis in laboris et id voluptate. Eu enim laborum dolore cupidatat aliqua nostrud exercitation sint deserunt dolore ut.\r\n',
    user: {
      id: 104,
      name: 'Gilbert Peters',
      username: 'Effie',
      password: '',
    },
    culturalOfferId: 251,
    culturalOfferName: 'Quantasis',
    pictures: [],
  },
  {
    id: 245,
    rating: 3,
    content:
      'Do reprehenderit ad aute reprehenderit esse in irure. Ullamco incididunt elit commodo Lorem. Occaecat veniam tempor enim labore non reprehenderit. Eiusmod velit ex quis incididunt adipisicing nostrud eiusmod aliqua laboris esse. Fugiat esse duis ullamco consequat enim quis nostrud excepteur laboris nulla proident ipsum aliqua. Esse consectetur magna anim sunt ut labore commodo nisi mollit dolor sint nulla. Adipisicing cillum sunt qui laboris in proident dolor aliqua irure.\r\n',
    user: {
      id: 408,
      name: 'Noel Schmidt',
      username: 'Glenda',
      password: '',
    },
    culturalOfferId: 292,
    culturalOfferName: 'Zipak',
    pictures: [],
  },
  {
    id: 67,
    rating: 5,
    content:
      'Laboris consequat adipisicing proident labore nulla magna proident dolor consequat qui aliquip esse minim. Et ad qui deserunt aliqua velit ad laboris adipisicing minim ullamco. Laboris est deserunt officia dolor eiusmod labore enim ea dolor. Sint tempor pariatur ipsum nisi nostrud sit dolor ut. Incididunt excepteur occaecat est deserunt. Dolore eiusmod commodo fugiat minim aute elit ullamco adipisicing duis in pariatur ex.\r\n',
    user: {
      id: 217,
      name: 'Anna Weaver',
      username: 'Aimee',
      password: '',
    },
    culturalOfferId: 383,
    culturalOfferName: 'Yurture',
    pictures: [],
  },
  {
    id: 2,
    rating: 4,
    content:
      'Deserunt aliqua ad eiusmod anim quis nisi. Ut ad dolore dolor cupidatat eu aute ut culpa ad esse. Irure id do eiusmod esse in mollit ad nulla laborum magna. Veniam irure aliqua deserunt proident. Nisi exercitation sint dolore labore laborum deserunt reprehenderit consequat voluptate.\r\n',
    user: {
      id: 70,
      name: 'Margaret Berry',
      username: 'Beach',
      password: '',
    },
    culturalOfferId: 298,
    culturalOfferName: 'Rodeocean',
    pictures: [],
  },
  {
    id: 249,
    rating: 4,
    content:
      'Exercitation Lorem minim Lorem Lorem proident quis nulla eu fugiat ullamco nostrud Lorem et fugiat. Voluptate et eu cillum ullamco fugiat tempor elit consequat sit nostrud occaecat eiusmod. Officia non Lorem nisi ea quis sunt ad nisi quis.\r\n',
    user: {
      id: 342,
      name: 'Sheree Solomon',
      username: 'Johnnie',
      password: '',
    },
    culturalOfferId: 69,
    culturalOfferName: 'Sustenza',
    pictures: [],
  },
  {
    id: 279,
    rating: 1,
    content:
      'Eiusmod labore ut dolor laboris eu id in do aliqua velit fugiat non ea. Ipsum excepteur labore ad sunt elit quis duis. Aliquip aliqua tempor enim aute minim ad nulla cupidatat adipisicing nostrud. Lorem minim fugiat ut esse enim irure incididunt laborum excepteur quis. Qui voluptate adipisicing deserunt nisi in veniam eu nisi sit incididunt quis. Id adipisicing duis adipisicing reprehenderit nisi eu ipsum.\r\n',
    user: {
      id: 167,
      name: 'Bobbi Kinney',
      username: 'Laurie',
      password: '',
    },
    culturalOfferId: 429,
    culturalOfferName: 'Ecosys',
    pictures: [],
  },
  {
    id: 69,
    rating: 3,
    content:
      'Deserunt elit dolor elit qui magna. Est proident et dolore cupidatat Lorem et dolore ipsum ullamco id eiusmod in. Non aliquip nostrud dolor proident cillum ipsum. Exercitation enim anim ex consectetur nostrud elit ad sit non. Occaecat sit dolore quis consectetur tempor occaecat anim ullamco elit cillum eu. Esse do ad cupidatat amet.\r\n',
    user: {
      id: 361,
      name: 'Petty Smith',
      username: 'Morse',
      password: '',
    },
    culturalOfferId: 82,
    culturalOfferName: 'Fitcore',
    pictures: [],
  },
  {
    id: 111,
    rating: 4,
    content:
      'Aliquip officia ipsum culpa commodo mollit exercitation laborum in sint. Amet aute laboris ea consectetur duis exercitation consectetur pariatur. Ad consectetur fugiat reprehenderit veniam pariatur ex dolor ea non in adipisicing. Eiusmod reprehenderit aute aute consectetur.\r\n',
    user: {
      id: 165,
      name: 'Hazel Roach',
      username: 'Nellie',
      password: '',
    },
    culturalOfferId: 296,
    culturalOfferName: 'Idego',
    pictures: [],
  },
  {
    id: 414,
    rating: 2,
    content:
      'Nisi ad elit laboris voluptate occaecat. Laborum cupidatat et commodo occaecat. Magna officia laborum mollit cillum amet qui dolore sint ad aliquip magna occaecat. Mollit in excepteur mollit deserunt nostrud duis et labore officia laboris tempor voluptate. Sint voluptate ut laboris non aute.\r\n',
    user: {
      id: 176,
      name: 'King Ashley',
      username: 'Kirsten',
      password: '',
    },
    culturalOfferId: 183,
    culturalOfferName: 'Andryx',
    pictures: [],
  },
  {
    id: 191,
    rating: 1,
    content:
      'Consectetur aute non consectetur ullamco. Elit dolor est nulla dolor quis irure pariatur ad sunt incididunt Lorem voluptate aliqua. Amet amet aute aute deserunt sunt esse cupidatat.\r\n',
    user: {
      id: 110,
      name: 'Chen Green',
      username: 'Gilmore',
      password: '',
    },
    culturalOfferId: 220,
    culturalOfferName: 'Opticon',
    pictures: [],
  },
];
