    export class Product {
        id: string;
        code: string;
        name: string;
        shortDescription: string;
        cantd: number;
        size: string;
        price: number;
        createAt: string;
        discount: number;
        category: string;
        rating: number;
        typeGarment: string;
        images: Image[];

        constructor() {
            this.id = '';
            this.code = '';
            this.name = '';
            this.shortDescription = '';
            this.cantd = 0;
            this.size = '';
            this.price = 0;
            this.createAt = '';
            this.discount = 0;
            this.category = '';
            this.rating = 0;
            this.typeGarment = '';
            this.images = [];
        }


    }


    
    export class Image {
        id: string;
        routeFile: string;
        nameEntity: string;
        nameFile: string;

        constructor() {
            this.id = '';
            this.routeFile = '';
            this.nameEntity = '';
            this.nameFile = '';
        }
    }