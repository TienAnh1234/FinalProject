export class Friendrequestdto {

    constructor(
        public id: number,
        public senderId: number,
        public recipientId: number,
        public content: string,
        public status: string
    ){}

}
