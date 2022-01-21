export class User {
    public userId: string;
    public firstName: string;
    public lastName: string;
    public username: string;
    public lastLoginDate: Date | null;
    public lastLoginDateDisplay: Date | null;
    public joinDate: Date | null;
    public profileImageUrl: string;
    public active: boolean;
    public notLocked: boolean;
    public role: string;
    public authorities: [];
    public numberPhone?: any;
    public token?: any;
    public expireToken?: any;
    public location?: any;


    constructor() {
      this.userId = '';
      this.firstName = '';
      this.lastName = '';
      this.username = '';
      this.numberPhone = 0;
      this.token = '';
      this.expireToken = '';
      this.location = '';
      this.lastLoginDate = null;
      this.lastLoginDateDisplay = null;
      this.joinDate = null;
      this.profileImageUrl = '';
      this.active = false;
      this.notLocked = false;
      this.role = '';
      this.authorities = [];
    }
  
  }
  