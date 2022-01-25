export class User {
        
        names: string;
        surnames: string;
        username: string;
        gender: string;
        dateOfBirth: any;
        numberPhone?: any;
        role: string;
        token: string;
        expireToken?: number;
        profileImageUrl: string;
        joinDate: any;
        authorities: string[];
        location?: any;
        lastLoginDate: any;
        lastLoginDateDisplay: any;
        active: boolean;
        notLocked: boolean;


    constructor() {
      this.names = '';
      this.surnames = '';
      this.username = '';
      this.numberPhone = 0;
      this.token = '';
      this.expireToken = 0;
      this.gender = '';
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
  