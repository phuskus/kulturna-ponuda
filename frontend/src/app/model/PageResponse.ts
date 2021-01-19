export class PageResponse {
    pageable: {
        sort: Sort;
        offset: number;
        pageSize: number;
        pageNumber: number;
        paged: boolean;
        unpaged: boolean;
    };
    totalElements: number;
    totalPages: number;
    last: boolean;
    size: number;
    sort: Sort;
    number: number;
    numberOfElements: number;
    first: boolean;
    empty: boolean;
}

class Sort {
    sorted: boolean; 
    unsorted: boolean;
    empty: boolean;
}