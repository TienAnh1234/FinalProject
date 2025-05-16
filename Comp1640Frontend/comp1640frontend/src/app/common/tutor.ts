import { Major } from "./major";

export class Tutor {

    constructor(
        public id?: number,
        public name?: string,
        public birthday?: Date,
        public imageFile?: string,
        public majorDto?: Major,
        public username?: string,
        public status?: string    
    ){}


}
