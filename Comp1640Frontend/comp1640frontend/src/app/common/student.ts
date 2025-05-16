import { Major } from "./major";
import { User } from "./user";

export class Student {

    constructor(
        public id?: number,
        public name?: string,
        public birthday?: Date,
        public imageFile?: string,
        public majorDto?: Major,
        public username?: string    
    ){}

}
