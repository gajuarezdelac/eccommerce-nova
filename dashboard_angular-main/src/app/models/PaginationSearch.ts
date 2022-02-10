export class PaginationSearch {

    numberPage: number;
    sizePage: number;
    keyword: string;
    
    constructor() {
      this.keyword = '';
      this.numberPage = 0;
      this.sizePage = 10;
    }
  }