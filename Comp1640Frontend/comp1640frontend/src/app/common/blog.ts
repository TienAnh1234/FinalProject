import { Commentblog } from "./commentblog";
import { User } from "./user";

export class Blog {

        public id: number | null
        public content: string | null
        public userDto: User | null
        public createdAt: Date | null
        public updatedAt: Date | null
        public likeCount: number | null
        public status: string | null
        fileName?: string;
        fileType?: string;
        fileData?: any;

        public comments: Commentblog[] | null
        constructor(id: number | null, content: string | null, userDto: User | null,
            createdAt: Date | null, updatedAt: Date | null,
            likeCount: number | null,comments: Commentblog[] | null, status: string | null,
            fileName?: string, fileType?: string, fileData?: any) {
            this.id = id;
            this.content = content;
            this.userDto = userDto;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.likeCount = likeCount;
            this.fileName = fileName;
            this.fileType = fileType;
            this.fileData = fileData;
            this.comments = comments
            this.status = status
        }



}
