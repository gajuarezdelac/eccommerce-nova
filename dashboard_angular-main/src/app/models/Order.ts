
export class Order {

    id: string;
    codeProd: string;
    userId: string;
    amount: number;
    listProducts: string[];
    status: number;
    createdAt: string;
    editAt?: any;

    constructor() {
        this.id = '';
        this.codeProd = ''
        this.userId = ''
        this.amount = 0;
        this.listProducts = [];
        this.status = 0;
        this.createdAt = '';
        this.editAt = '';
    }
    
}