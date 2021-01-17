import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Review } from '../../shared/models/Review';
import PagingReturnValue, {
  BaseDynamicPagingService,
} from '../base/base-dynamic-paging.service';

@Injectable({
  providedIn: 'root',
})
export class ReviewService extends BaseDynamicPagingService<Review> {
  constructor() {
    super();
  }

  getPage(
    pageIndex: number,
    sortBy: string,
    descending: boolean
  ): PagingReturnValue<Review> {
    const psize = 10;
    const reviews = REVIEWS.slice(psize * pageIndex, psize * pageIndex + psize);
    return { items: of(reviews), total_count: REVIEWS.length };
  }

  add(object: Review): Observable<Review[]> {
    throw new Error('Method not implemented.');
  }

  update(id: number, object: Review): Observable<Review[]> {
    throw new Error('Method not implemented.');
  }

  delete(id: number): Observable<Review[]> {
    throw new Error('Method not implemented.');
  }
}

const REVIEWS: Review[] = [
  {
    id: 399,
    rating: 1,
    content:
      'Ullamco mollit qui commodo esse ad fugiat irure qui pariatur ullamco. Cillum sunt sint esse commodo sit tempor. Do laboris qui in officia enim. Fugiat veniam tempor culpa esse cupidatat labore elit. Ullamco id commodo eu laborum magna quis ad aliquip et nostrud dolor culpa. Culpa elit mollit irure ea incididunt consequat sunt. Laborum laborum sit irure sit nisi id.\r\n',
    user: {
      id: 275,
      name: 'Reva Frank',
      username: 'Freda',
      password: '',
    },
    culturalOfferId: 157,
    pictures: [''],
  },
  {
    id: 193,
    rating: 1,
    content:
      'Velit in esse anim excepteur amet. Proident dolor tempor magna consectetur amet. Occaecat nostrud id ad ipsum est ad occaecat magna ea. Eiusmod sit anim ut laborum commodo est labore magna aliquip tempor sint cupidatat voluptate. Labore pariatur laborum amet in duis veniam sunt. Sit irure ullamco nisi pariatur laboris enim est cupidatat veniam exercitation laborum exercitation.\r\n',
    user: {
      id: 268,
      name: 'Garcia Moss',
      username: 'Ada',
      password: '',
    },
    culturalOfferId: 334,
    pictures: [''],
  },
  {
    id: 315,
    rating: 3,
    content:
      'Magna culpa laborum aliqua sunt mollit. Et laborum ipsum velit eiusmod tempor do eiusmod in do. Ipsum et pariatur esse sunt ipsum id amet. Pariatur elit anim id ipsum sint est Lorem ut reprehenderit do veniam officia.\r\n',
    user: {
      id: 137,
      name: 'Mcpherson Diaz',
      username: 'Brenda',
      password: '',
    },
    culturalOfferId: 186,
    pictures: [''],
  },
  {
    id: 356,
    rating: 4,
    content:
      'Exercitation incididunt eu excepteur cupidatat officia velit nostrud do consectetur duis dolor. Lorem in irure ea ullamco incididunt. Reprehenderit voluptate aliqua eiusmod esse. Voluptate cillum aute ex in officia.\r\n',
    user: {
      id: 357,
      name: 'Minerva Jarvis',
      username: 'Price',
      password: '',
    },
    culturalOfferId: 23,
    pictures: [''],
  },
  {
    id: 124,
    rating: 5,
    content:
      'Veniam pariatur consectetur dolor anim dolor veniam eu. Non deserunt minim occaecat adipisicing minim aute est. Nisi aliqua pariatur dolor dolor amet voluptate. Ipsum ad sit minim amet dolor elit consectetur amet sint dolore fugiat. Consequat velit ex culpa veniam aute quis cupidatat consectetur labore nulla cupidatat Lorem eiusmod.\r\n',
    user: {
      id: 91,
      name: 'Vinson Melendez',
      username: 'Owens',
      password: '',
    },
    culturalOfferId: 237,
    pictures: [''],
  },
  {
    id: 301,
    rating: 3,
    content:
      'Esse elit eiusmod eu aliqua. Reprehenderit occaecat officia nisi deserunt et proident veniam do veniam veniam aliqua. Dolore qui id excepteur ad aute deserunt laboris. Nulla consequat veniam magna sunt est laborum dolor aliqua. Nostrud ex qui culpa ut.\r\n',
    user: {
      id: 237,
      name: 'Merle Stanley',
      username: 'Billie',
      password: '',
    },
    culturalOfferId: 299,
    pictures: [''],
  },
  {
    id: 159,
    rating: 4,
    content:
      'Tempor cillum sint consectetur do exercitation proident incididunt adipisicing sunt exercitation anim irure veniam. Ut id amet dolor ut id cupidatat deserunt ullamco do mollit velit ullamco quis nulla. Quis minim eiusmod id cillum veniam reprehenderit occaecat do magna magna qui ad. Deserunt Lorem eu minim veniam pariatur sint anim irure ut sint incididunt anim. Adipisicing dolor nisi ullamco minim officia aliquip amet ullamco pariatur excepteur. Reprehenderit excepteur eu est ea adipisicing voluptate incididunt. Fugiat et laboris velit incididunt et id aute eiusmod.\r\n',
    user: {
      id: 393,
      name: 'Dillon Reese',
      username: 'Bond',
      password: '',
    },
    culturalOfferId: 147,
    pictures: [''],
  },
  {
    id: 180,
    rating: 2,
    content:
      'Quis esse amet est consequat ullamco. Est et anim fugiat dolore dolor adipisicing est eu deserunt eiusmod labore exercitation dolore. Veniam labore elit id labore laborum nisi consequat. Fugiat tempor ea fugiat excepteur elit adipisicing irure excepteur pariatur laborum. Sit quis in fugiat ea qui.\r\n',
    user: {
      id: 389,
      name: 'Cheryl Manning',
      username: 'Winters',
      password: '',
    },
    culturalOfferId: 99,
    pictures: [''],
  },
  {
    id: 60,
    rating: 4,
    content:
      'Officia veniam ad sit cupidatat do sint. Aliqua ex ut ullamco et duis sunt dolore voluptate labore. Ex ut consectetur nulla nisi eiusmod deserunt ipsum Lorem in nisi. Voluptate tempor anim et mollit. Consectetur consequat sint id minim sunt sit laborum sunt commodo culpa. Esse id tempor incididunt quis laboris non ut aliqua in ipsum occaecat velit. Irure veniam id consequat non sint aute ipsum irure officia.\r\n',
    user: {
      id: 269,
      name: 'Alberta Crane',
      username: 'Mcneil',
      password: '',
    },
    culturalOfferId: 118,
    pictures: [''],
  },
  {
    id: 373,
    rating: 4,
    content:
      'Mollit nulla cillum ex anim reprehenderit deserunt. Exercitation magna nostrud enim aliquip non duis. Ad irure id cupidatat id sunt. Dolor officia reprehenderit laboris adipisicing. Magna incididunt cillum ea ut esse voluptate reprehenderit. Adipisicing esse enim aliqua in. Aliquip fugiat irure pariatur commodo.\r\n',
    user: {
      id: 237,
      name: 'Nona Maxwell',
      username: 'Tina',
      password: '',
    },
    culturalOfferId: 364,
    pictures: [''],
  },
  {
    id: 267,
    rating: 5,
    content:
      'Officia non id mollit cillum aute. Adipisicing aliquip ad incididunt aute magna duis qui adipisicing excepteur laborum. Eu tempor laboris velit culpa.\r\n',
    user: {
      id: 288,
      name: 'Wolf Brady',
      username: 'Leanna',
      password: '',
    },
    culturalOfferId: 324,
    pictures: [''],
  },
  {
    id: 26,
    rating: 4,
    content:
      'Incididunt ut excepteur commodo dolore voluptate dolore Lorem exercitation ea. Dolor Lorem labore excepteur nostrud. Officia sunt ullamco eiusmod ipsum in irure esse dolore id nostrud occaecat veniam nisi deserunt. Consectetur aliqua mollit velit dolor in ea ea aliquip ea dolor quis deserunt. Adipisicing sit nostrud quis ut cillum fugiat aliquip Lorem irure duis veniam nisi. Quis sint exercitation amet deserunt exercitation deserunt reprehenderit veniam sint duis.\r\n',
    user: {
      id: 37,
      name: 'Marcy Best',
      username: 'Garner',
      password: '',
    },
    culturalOfferId: 132,
    pictures: [''],
  },
  {
    id: 30,
    rating: 4,
    content:
      'Pariatur voluptate fugiat sunt dolor aute elit ullamco ut velit velit ex voluptate. Incididunt irure enim irure id qui voluptate. Nisi aute ad amet velit aliqua laboris do. Amet duis velit nisi non minim id ex. Amet anim aute ipsum cupidatat aute ipsum consectetur occaecat. Sunt sint aute in commodo ipsum nulla amet esse proident nulla ad enim.\r\n',
    user: {
      id: 343,
      name: 'Petersen French',
      username: 'Jenna',
      password: '',
    },
    culturalOfferId: 269,
    pictures: [''],
  },
  {
    id: 170,
    rating: 1,
    content:
      'Labore culpa non ea est. Eiusmod qui tempor anim laborum excepteur. Sit aliqua aliqua nostrud in id nisi. Aute anim aliqua pariatur voluptate. Do elit in deserunt laborum ut veniam pariatur qui cupidatat adipisicing. Deserunt mollit irure velit sunt excepteur.\r\n',
    user: {
      id: 204,
      name: 'Frye Mack',
      username: 'Herring',
      password: '',
    },
    culturalOfferId: 371,
    pictures: [''],
  },
  {
    id: 397,
    rating: 3,
    content:
      'Cupidatat eiusmod tempor magna sunt ut aute amet commodo voluptate. Sunt ut sit pariatur Lorem dolor est et labore cillum enim adipisicing. Consectetur cillum consequat adipisicing excepteur nostrud qui occaecat incididunt.\r\n',
    user: {
      id: 297,
      name: 'Chang Noel',
      username: 'Amparo',
      password: '',
    },
    culturalOfferId: 126,
    pictures: [''],
  },
  {
    id: 221,
    rating: 4,
    content:
      'In adipisicing Lorem nostrud nostrud tempor aliqua quis magna excepteur in. Dolor do aliquip nostrud nisi consectetur ipsum eiusmod sunt. Lorem elit nisi ullamco aliquip. Do occaecat non fugiat et culpa ut mollit dolore sunt magna cupidatat ipsum deserunt tempor.\r\n',
    user: {
      id: 268,
      name: 'Joyner Harrell',
      username: 'Misty',
      password: '',
    },
    culturalOfferId: 386,
    pictures: [''],
  },
  {
    id: 322,
    rating: 1,
    content:
      'Minim deserunt non culpa minim ut sint. Magna minim et nisi aliqua incididunt officia aliqua Lorem non exercitation aute. Sint occaecat deserunt sunt ea incididunt laboris mollit velit est consectetur incididunt. Duis eiusmod adipisicing laboris elit est aute ipsum ex minim exercitation. In irure reprehenderit occaecat deserunt magna proident consectetur labore sit quis qui sunt velit eu. Excepteur ut Lorem irure ullamco sit ut et ipsum laboris.\r\n',
    user: {
      id: 41,
      name: 'Edwina Merrill',
      username: 'Mabel',
      password: '',
    },
    culturalOfferId: 334,
    pictures: [''],
  },
  {
    id: 48,
    rating: 1,
    content:
      'Ut aute quis cupidatat commodo et duis nulla quis eiusmod cillum. Amet exercitation do cillum incididunt esse veniam nisi. Veniam duis amet veniam ipsum in id. Tempor mollit ad aute occaecat tempor elit. Voluptate minim nulla esse consectetur. Amet occaecat laboris fugiat ea elit mollit ut commodo. Consequat eu aliqua consequat anim ea est.\r\n',
    user: {
      id: 26,
      name: 'Rachelle Dunlap',
      username: 'Bartlett',
      password: '',
    },
    culturalOfferId: 234,
    pictures: [''],
  },
  {
    id: 329,
    rating: 3,
    content:
      'Officia excepteur quis elit eu ullamco non qui non nostrud exercitation nisi anim. Laborum mollit ut aute ea dolore amet ea aliqua eiusmod. Et Lorem fugiat consequat enim ullamco proident. Esse pariatur et dolore quis adipisicing voluptate ipsum non tempor esse mollit et. Minim eiusmod Lorem adipisicing et laboris nulla laborum qui ut mollit dolore cillum ipsum duis. Duis aliquip enim ullamco aliquip labore aute nisi minim. Laborum minim anim ad id.\r\n',
    user: {
      id: 342,
      name: 'Celeste Tate',
      username: 'Wallace',
      password: '',
    },
    culturalOfferId: 131,
    pictures: [''],
  },
  {
    id: 154,
    rating: 5,
    content:
      'Esse ea laborum veniam dolor elit adipisicing ea sit mollit dolore non dolor exercitation. Ut ut exercitation cupidatat exercitation reprehenderit sint sit culpa occaecat culpa. Officia aliqua reprehenderit nulla mollit tempor laboris ipsum do pariatur excepteur deserunt incididunt.\r\n',
    user: {
      id: 237,
      name: 'Huber Byrd',
      username: 'Jensen',
      password: '',
    },
    culturalOfferId: 6,
    pictures: [''],
  },
  {
    id: 231,
    rating: 3,
    content:
      'Aliqua ea irure officia adipisicing. Labore minim cupidatat ad fugiat cupidatat amet et aliquip nostrud elit eiusmod consectetur officia. Sint sunt irure aliquip adipisicing ex ea anim laboris ipsum et consectetur. Minim culpa sint elit velit labore velit. Excepteur dolor reprehenderit est anim dolore sint voluptate in do aliquip nostrud fugiat quis. Ullamco do ullamco duis tempor tempor ullamco adipisicing ex nulla eu in ea enim. Cupidatat sint anim officia id velit occaecat sit.\r\n',
    user: {
      id: 306,
      name: 'Ochoa Harding',
      username: 'Bernice',
      password: '',
    },
    culturalOfferId: 1,
    pictures: [''],
  },
  {
    id: 29,
    rating: 4,
    content:
      'Quis cillum elit nulla cillum non aliquip ullamco velit aliquip nostrud dolore eu. Ut ex et proident in ad labore quis. Sunt est aliquip do cupidatat officia consequat Lorem. Dolor eu magna excepteur officia occaecat Lorem cillum ex laboris culpa laborum.\r\n',
    user: {
      id: 41,
      name: 'Nancy Patton',
      username: 'Sandoval',
      password: '',
    },
    culturalOfferId: 39,
    pictures: [''],
  },
  {
    id: 389,
    rating: 4,
    content:
      'Sunt nulla adipisicing eu tempor ad sit Lorem ad ipsum fugiat exercitation mollit elit. Aliquip quis consectetur minim consequat sint eu incididunt incididunt et cillum elit ipsum officia ullamco. Enim velit veniam sunt nisi duis voluptate minim qui est incididunt labore laboris labore. Nisi excepteur commodo deserunt excepteur incididunt velit eiusmod officia fugiat officia. Do culpa eiusmod reprehenderit ut nulla aliqua et excepteur cupidatat minim deserunt fugiat aliquip ex.\r\n',
    user: {
      id: 158,
      name: 'Jolene Neal',
      username: 'Patricia',
      password: '',
    },
    culturalOfferId: 349,
    pictures: [''],
  },
  {
    id: 192,
    rating: 5,
    content:
      'Lorem reprehenderit nisi officia magna. Deserunt veniam ex culpa dolore laboris nulla consequat fugiat qui elit est. Tempor anim culpa veniam sunt occaecat id nisi mollit tempor eiusmod. Sit exercitation incididunt enim nostrud irure exercitation. Laborum amet irure qui minim cupidatat ut elit ipsum irure do magna. Laboris culpa minim nisi dolore nulla adipisicing exercitation laboris quis occaecat dolore amet cupidatat.\r\n',
    user: {
      id: 378,
      name: 'Margo Lyons',
      username: 'Tessa',
      password: '',
    },
    culturalOfferId: 214,
    pictures: [''],
  },
  {
    id: 85,
    rating: 3,
    content:
      'Labore ipsum adipisicing culpa excepteur reprehenderit nulla tempor tempor excepteur velit aliqua Lorem. Do sint dolore laboris adipisicing nulla commodo dolore consequat consequat eiusmod Lorem veniam. Aliqua elit enim ullamco in. Aliqua deserunt elit irure exercitation voluptate laboris esse dolor id aliquip id.\r\n',
    user: {
      id: 175,
      name: 'Mariana Carey',
      username: 'Alford',
      password: '',
    },
    culturalOfferId: 328,
    pictures: [''],
  },
  {
    id: 202,
    rating: 1,
    content:
      'Veniam in velit pariatur culpa minim occaecat do est sit. Laboris veniam Lorem elit dolore officia laboris sit sint et eu. Exercitation ex magna dolor velit sit. Duis ea eu tempor eiusmod.\r\n',
    user: {
      id: 180,
      name: 'Robbins Pennington',
      username: 'Hall',
      password: '',
    },
    culturalOfferId: 108,
    pictures: [''],
  },
  {
    id: 356,
    rating: 2,
    content:
      'Nisi tempor quis commodo culpa. Deserunt ea ut qui tempor incididunt eiusmod aute magna. Esse ex quis cupidatat cillum exercitation dolore nisi ex ut deserunt occaecat sit id magna.\r\n',
    user: {
      id: 258,
      name: 'Phoebe Hull',
      username: 'Deloris',
      password: '',
    },
    culturalOfferId: 324,
    pictures: [''],
  },
  {
    id: 74,
    rating: 5,
    content:
      'Consectetur ullamco enim minim ex exercitation proident ipsum veniam ut ullamco. Ea fugiat minim aute magna id laborum cupidatat consectetur est ipsum esse nulla aliqua. Sunt voluptate non dolore aliquip ad commodo cillum culpa qui officia nulla officia consequat officia. Ex irure cillum qui culpa quis qui incididunt sint sit cupidatat dolore eiusmod.\r\n',
    user: {
      id: 4,
      name: 'Swanson Wade',
      username: 'Eve',
      password: '',
    },
    culturalOfferId: 431,
    pictures: [''],
  },
  {
    id: 257,
    rating: 3,
    content:
      'Sint et elit ullamco sint dolor. Do et laborum do labore ea aute mollit. Incididunt ad laborum elit qui. Voluptate tempor nulla est qui ex amet irure. Duis minim nulla velit culpa. Nostrud minim consectetur elit irure consectetur. Ex id amet laborum do velit dolor minim nulla.\r\n',
    user: {
      id: 125,
      name: 'Ellison Dillard',
      username: 'Barr',
      password: '',
    },
    culturalOfferId: 232,
    pictures: [''],
  },
  {
    id: 58,
    rating: 5,
    content:
      'Nostrud nisi id reprehenderit ullamco aliqua aliqua ullamco irure ullamco. Consequat ad excepteur adipisicing officia. Id ea ut anim anim tempor magna. Consequat laboris dolor eiusmod ipsum.\r\n',
    user: {
      id: 214,
      name: 'Mcclure Mullins',
      username: 'Tabitha',
      password: '',
    },
    culturalOfferId: 33,
    pictures: [''],
  },
  {
    id: 140,
    rating: 3,
    content:
      'Cillum consequat ipsum consectetur cillum velit commodo sint commodo reprehenderit id tempor id enim. Et est ut laborum fugiat laboris. Sit incididunt minim excepteur do ut. Ad aute nisi minim adipisicing proident veniam exercitation minim elit sunt.\r\n',
    user: {
      id: 83,
      name: 'Berry Johns',
      username: 'Nannie',
      password: '',
    },
    culturalOfferId: 4,
    pictures: [''],
  },
  {
    id: 16,
    rating: 2,
    content:
      'Ullamco labore proident excepteur aliquip ea irure consequat. Et laboris ex aute irure non eu cillum cupidatat est duis anim. Do do sunt minim ea veniam minim pariatur enim ad. Non nisi id aliqua cillum velit labore occaecat deserunt non. Qui mollit est sunt veniam qui culpa ad pariatur. Ad ex anim labore veniam voluptate elit sunt ullamco quis do enim et eu exercitation. Veniam aliqua cupidatat ad labore dolore.\r\n',
    user: {
      id: 119,
      name: 'Wise Wong',
      username: 'Nanette',
      password: '',
    },
    culturalOfferId: 360,
    pictures: [''],
  },
  {
    id: 372,
    rating: 3,
    content:
      'Nulla labore dolor incididunt minim pariatur dolor dolor elit elit. Reprehenderit aliquip voluptate ad laborum aute in nulla id occaecat. Velit ea dolore elit tempor minim tempor ipsum velit.\r\n',
    user: {
      id: 199,
      name: 'Nita Dorsey',
      username: 'Esther',
      password: '',
    },
    culturalOfferId: 105,
    pictures: [''],
  },
  {
    id: 292,
    rating: 4,
    content:
      'Ipsum esse consectetur incididunt fugiat. Eu sunt laboris qui cupidatat exercitation eu eu proident. Amet est elit incididunt in eiusmod et eu minim adipisicing do eu exercitation. Quis do commodo elit ut est. Commodo eu eiusmod eiusmod ex nostrud consectetur duis sint. Sunt incididunt eu incididunt commodo magna laboris labore qui.\r\n',
    user: {
      id: 411,
      name: 'Mindy Lee',
      username: 'Vicky',
      password: '',
    },
    culturalOfferId: 133,
    pictures: [''],
  },
  {
    id: 389,
    rating: 5,
    content:
      'Do aliqua labore enim tempor laboris. Irure Lorem sunt ex anim tempor adipisicing laborum id laborum nisi. Dolor occaecat deserunt non eiusmod voluptate laboris sit aute ipsum Lorem. Do tempor aute proident eu dolor sint consectetur pariatur. Velit irure magna ullamco excepteur tempor non cillum nulla.\r\n',
    user: {
      id: 362,
      name: 'Jeannie Collins',
      username: 'Jana',
      password: '',
    },
    culturalOfferId: 355,
    pictures: [''],
  },
  {
    id: 88,
    rating: 1,
    content:
      'Ut ullamco cupidatat nulla aliqua et do. Excepteur amet sit tempor magna proident irure eu enim reprehenderit et est ullamco qui. Incididunt sunt fugiat mollit minim amet mollit eiusmod officia esse. Aliqua reprehenderit cupidatat irure proident sint eu consequat aliqua ea cupidatat. Qui in labore mollit laboris aute elit sint exercitation ipsum irure mollit aute anim.\r\n',
    user: {
      id: 68,
      name: 'Benson Raymond',
      username: 'Gregory',
      password: '',
    },
    culturalOfferId: 322,
    pictures: [''],
  },
  {
    id: 177,
    rating: 1,
    content:
      'Magna ipsum aliquip reprehenderit Lorem sint ex ad Lorem commodo do sunt. Anim in et aliqua dolor exercitation nisi exercitation nostrud fugiat velit esse esse sint cillum. Laboris tempor in exercitation aute cillum id ut et elit. Culpa anim dolor excepteur dolor esse culpa eu adipisicing veniam tempor ad magna pariatur. Excepteur in ea minim nostrud laborum elit elit. Magna elit minim velit eiusmod ut id. Aliquip velit laborum in adipisicing nulla exercitation.\r\n',
    user: {
      id: 415,
      name: 'Bird Booker',
      username: 'Knowles',
      password: '',
    },
    culturalOfferId: 113,
    pictures: [''],
  },
  {
    id: 335,
    rating: 5,
    content:
      'Excepteur nisi ex tempor sint tempor. Ullamco fugiat cupidatat ea dolore exercitation laborum. Ea ut proident est et deserunt aliquip ipsum enim magna. Incididunt commodo ex quis enim consequat aliqua dolor. Adipisicing veniam consectetur amet et.\r\n',
    user: {
      id: 397,
      name: 'Sabrina Brooks',
      username: 'Adkins',
      password: '',
    },
    culturalOfferId: 337,
    pictures: [''],
  },
  {
    id: 44,
    rating: 2,
    content:
      'Non adipisicing mollit ullamco reprehenderit aliquip ipsum. Cupidatat et id consequat ex. Sit quis voluptate exercitation irure cillum est. Ad ut est nisi dolor amet cupidatat elit aliquip. Labore minim veniam elit id non occaecat.\r\n',
    user: {
      id: 266,
      name: 'Marci Walls',
      username: 'Pearl',
      password: '',
    },
    culturalOfferId: 124,
    pictures: [''],
  },
  {
    id: 3,
    rating: 4,
    content:
      'Incididunt fugiat aliqua Lorem sunt duis sint duis. Magna fugiat consequat pariatur veniam dolor eu anim nisi excepteur nostrud laborum. Commodo incididunt sit est proident eiusmod.\r\n',
    user: {
      id: 156,
      name: 'Faulkner Schwartz',
      username: 'Horn',
      password: '',
    },
    culturalOfferId: 73,
    pictures: [''],
  },
  {
    id: 146,
    rating: 1,
    content:
      'Est irure commodo aliqua eiusmod laborum enim sit. Quis nisi pariatur sunt do consectetur voluptate. Nulla voluptate id laboris enim eiusmod reprehenderit. Et proident aliqua ea cupidatat in et commodo Lorem ad. Laboris ullamco eiusmod reprehenderit minim excepteur. Minim laborum tempor minim tempor Lorem.\r\n',
    user: {
      id: 193,
      name: 'Alana Gray',
      username: 'Hill',
      password: '',
    },
    culturalOfferId: 57,
    pictures: [''],
  },
  {
    id: 225,
    rating: 5,
    content:
      'Proident nostrud consectetur quis laboris elit. Aliqua ut consectetur aute voluptate in id eu minim. Proident irure id ut quis adipisicing reprehenderit commodo est consectetur. Velit consequat nostrud non minim.\r\n',
    user: {
      id: 146,
      name: 'Coffey Snider',
      username: 'Bonnie',
      password: '',
    },
    culturalOfferId: 103,
    pictures: [''],
  },
  {
    id: 214,
    rating: 5,
    content:
      'Reprehenderit aliquip magna consequat aliquip do enim id velit consequat irure mollit ad. Sit duis pariatur fugiat nulla mollit. Sint nostrud officia ut irure consectetur do fugiat nulla incididunt dolore. Consequat labore incididunt voluptate esse. Nisi adipisicing ullamco quis minim nostrud commodo quis ad nostrud duis. Aute sit excepteur tempor duis minim.\r\n',
    user: {
      id: 76,
      name: 'Kelly Mason',
      username: 'Ernestine',
      password: '',
    },
    culturalOfferId: 186,
    pictures: [''],
  },
  {
    id: 112,
    rating: 3,
    content:
      'Occaecat ea ipsum laborum deserunt ea laborum ut ea culpa labore aliqua. Consequat duis Lorem eu in non ut consequat aliquip Lorem excepteur non Lorem aute. Duis elit sint velit deserunt quis. Tempor eiusmod sit officia duis mollit eu id aliquip adipisicing aute ea. Exercitation sint cupidatat minim voluptate cupidatat nisi irure adipisicing nostrud Lorem dolor cupidatat. Minim ex Lorem sunt ex mollit deserunt esse.\r\n',
    user: {
      id: 251,
      name: 'Hebert Rowe',
      username: 'Lana',
      password: '',
    },
    culturalOfferId: 268,
    pictures: [''],
  },
  {
    id: 293,
    rating: 5,
    content:
      'Amet dolore enim labore et consequat id cupidatat. Magna nostrud reprehenderit deserunt labore et consequat ea voluptate laborum proident dolore ad consectetur. Irure cillum exercitation consequat adipisicing aliquip. Veniam sit non irure nulla. Aliquip consequat adipisicing est ullamco aliquip esse dolor pariatur sunt esse. Pariatur aliqua minim et Lorem est aliquip culpa ut.\r\n',
    user: {
      id: 168,
      name: 'Lewis Lewis',
      username: 'Doreen',
      password: '',
    },
    culturalOfferId: 24,
    pictures: [''],
  },
  {
    id: 409,
    rating: 3,
    content:
      'Anim velit velit est non incididunt aliquip voluptate est aute velit qui Lorem proident nulla. Commodo proident incididunt esse exercitation sunt do. Duis ullamco reprehenderit duis esse exercitation reprehenderit veniam. Nulla velit nostrud non elit et consequat. Minim aliqua officia enim consectetur ex mollit. Aute est velit adipisicing do cillum dolor dolore nostrud.\r\n',
    user: {
      id: 143,
      name: 'Beck Cabrera',
      username: 'Strickland',
      password: '',
    },
    culturalOfferId: 115,
    pictures: [''],
  },
  {
    id: 212,
    rating: 5,
    content:
      'Culpa exercitation cupidatat sit et velit fugiat aliquip officia. Fugiat consequat laboris nostrud cupidatat cillum enim pariatur nostrud. Esse veniam minim labore Lorem est pariatur nostrud exercitation occaecat cillum sint dolore. Non eu pariatur est ea dolore. Aliquip minim exercitation cillum velit incididunt nisi occaecat nisi ex. Commodo ut aliqua id adipisicing cillum nostrud non anim dolor sunt Lorem velit deserunt.\r\n',
    user: {
      id: 370,
      name: 'Bryan Benjamin',
      username: 'Maryellen',
      password: '',
    },
    culturalOfferId: 53,
    pictures: [''],
  },
  {
    id: 157,
    rating: 4,
    content:
      'Voluptate qui ea exercitation do mollit. Cillum labore cupidatat dolor reprehenderit laboris sunt magna ea fugiat fugiat adipisicing. Consectetur eiusmod occaecat minim occaecat labore irure tempor amet laborum.\r\n',
    user: {
      id: 350,
      name: 'Savage Cardenas',
      username: 'Mccullough',
      password: '',
    },
    culturalOfferId: 138,
    pictures: [''],
  },
  {
    id: 142,
    rating: 5,
    content:
      'Ad laboris quis commodo velit consequat duis nostrud fugiat dolor eiusmod. Voluptate eu dolor pariatur quis consectetur irure. Amet amet aliqua exercitation incididunt sit fugiat sit proident. Ipsum Lorem commodo fugiat duis officia cillum ex excepteur deserunt labore elit irure.\r\n',
    user: {
      id: 114,
      name: 'Velazquez Warner',
      username: 'Martin',
      password: '',
    },
    culturalOfferId: 185,
    pictures: [''],
  },
  {
    id: 106,
    rating: 3,
    content:
      'Aute ipsum elit laborum id ullamco cillum. Pariatur id non laborum veniam enim. Non duis qui mollit consequat sint ea excepteur minim non laboris aliqua eiusmod. Commodo velit deserunt Lorem ut occaecat veniam excepteur officia minim est laboris aute. Eu sunt consequat reprehenderit velit nostrud eiusmod tempor eiusmod tempor ipsum nostrud excepteur fugiat. Fugiat sit tempor cupidatat anim pariatur nostrud sint eiusmod consectetur duis. Laboris tempor duis aute reprehenderit enim minim esse velit minim.\r\n',
    user: {
      id: 382,
      name: 'Vaughn Andrews',
      username: 'Alice',
      password: '',
    },
    culturalOfferId: 297,
    pictures: [''],
  },
  {
    id: 4,
    rating: 5,
    content:
      'Sint quis qui ipsum amet culpa laborum proident. Mollit do excepteur id ipsum do dolore qui enim fugiat cillum officia. Sint eu et nisi ea adipisicing occaecat tempor laborum exercitation adipisicing amet sint laboris. Amet minim anim velit consectetur voluptate voluptate Lorem. Excepteur adipisicing et id sit sit esse proident proident ullamco cillum amet dolor commodo ad. Voluptate occaecat tempor voluptate dolore minim laboris aute sunt consequat. Consequat in elit exercitation sit adipisicing consectetur enim sunt sint.\r\n',
    user: {
      id: 9,
      name: 'Jewell Miranda',
      username: 'Christensen',
      password: '',
    },
    culturalOfferId: 7,
    pictures: [''],
  },
  {
    id: 37,
    rating: 4,
    content:
      'Consectetur aliqua fugiat anim ullamco esse aliqua nisi aute ut tempor. Irure veniam eiusmod occaecat amet esse amet cillum officia deserunt. Irure eu nostrud magna tempor eu dolor. Aliquip enim non aute amet ad amet proident mollit aute ullamco deserunt in exercitation.\r\n',
    user: {
      id: 248,
      name: 'Latisha Fletcher',
      username: 'Silva',
      password: '',
    },
    culturalOfferId: 272,
    pictures: [''],
  },
  {
    id: 174,
    rating: 3,
    content:
      'Sunt dolor enim ex consequat do enim excepteur sunt sit enim aute. Id consequat aliquip aliquip consequat culpa exercitation laboris nulla incididunt culpa. Id excepteur do consequat mollit esse aliquip quis ipsum do officia magna do aliqua. Et excepteur ut ullamco Lorem duis duis culpa. Laborum sint cupidatat mollit laboris mollit ut. Dolor magna irure enim in proident cillum commodo excepteur sint.\r\n',
    user: {
      id: 13,
      name: 'Rhodes Mendez',
      username: 'Lowery',
      password: '',
    },
    culturalOfferId: 297,
    pictures: [''],
  },
  {
    id: 89,
    rating: 1,
    content:
      'Non do enim esse qui reprehenderit nisi do aliqua dolore dolor. Mollit exercitation duis magna deserunt exercitation duis voluptate ullamco Lorem proident dolore reprehenderit eu duis. Irure deserunt eiusmod dolor incididunt voluptate ad culpa. Sunt sit aute do deserunt voluptate nisi amet esse sint dolore elit ea nostrud.\r\n',
    user: {
      id: 404,
      name: 'Rowena Mclean',
      username: 'Rios',
      password: '',
    },
    culturalOfferId: 335,
    pictures: [''],
  },
];
