export class Friendrequest {

    constructor(
        public id: number,
        public sender: string,
        public recipient: string,
        public content: string,
        public status: string,
    ){}
    

}
