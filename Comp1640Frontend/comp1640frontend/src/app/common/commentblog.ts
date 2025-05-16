export class Commentblog {
    id: number| null;
    content: string| null;
    createdAt: string| null;
    blogId: number| null;
    userId: number| null;

        constructor(id: number | null, content: string | null, createdAt: string | null,blogId: number | null,userId: number | null ) {
            this.id = id;
            this.content = content;
            this.createdAt = createdAt;
            this.blogId = blogId;
            this.userId = userId;

        }
}
