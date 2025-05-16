import { Student } from "./student";
import { Tutor } from "./tutor";

export class ClassroomMain {

    id: number | null;
    tutor: Tutor | null;
    student: Student | null;

    constructor(id: number | null, tutor: Tutor | null, student: Student | null) {
        this.id = id;
        this.tutor = tutor;
        this.student = student;
    }

}
