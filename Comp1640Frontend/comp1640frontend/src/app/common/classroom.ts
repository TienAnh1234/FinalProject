export class Classroom {
    
    id: number | null;
    tutorId: number | null;
    studentsId: number[];

    constructor(id: number | null, tutorId: number | null, studentsId: number[]) {
        this.id = id;
        this.tutorId = tutorId;
        this.studentsId = studentsId;
    }


}   

