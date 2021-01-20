import Model from './Model';

export default interface Page<T> {
  content: T[];
  pageable: {
    sort: Sort;
    offset: number;
    pageNumber: number;
    pageSize: number;
    paged: boolean;
    unpaged: boolean;
  };
  totalPages: number;
  totalElements: number;
  last: boolean;
  size: number;
  number: number;
  sort: Sort;
  numberofElements: number,
  first: boolean,
  empty: boolean
}


interface Sort {
    sorted: boolean;
    unsorted: boolean;
    empty: boolean;
}