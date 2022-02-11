export class PaginationSearch {

    typeClothing : string;
    clasification : string;
    category: string;
    numberPage: number;
    sizePage: number;
    keyword: string;
    
    constructor() {
      this.typeClothing = '';
      this.clasification = '';
      this.category = '';
      this.keyword = '';
      this.numberPage = 0;
      this.sizePage = 10;
    }
  }