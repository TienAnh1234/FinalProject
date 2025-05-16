import { Student } from "./student";
import { Tutor } from "./tutor";

export class Schedule {


        public id: number | null
        public studentDto: Student | null
        public tutorDto: Tutor | null
        public startTime: Date | null
        public endTime: Date | null
        public dayOfWeek: string | null
        public weekOfYear: string | null
        public status: string | null
        public scheduleFormat: string | null
        public address: string | null



        constructor(id: number | null, studentDto: Student | null, tutorDto: Tutor | null,startTime: Date | null, endTime: Date | null,dayOfWeek: string | null,weekOfYear: string | null,status: string | null,scheduleFormat: string | null,address: string | null) {
            this.id = id;
            this.studentDto = studentDto;
            this.tutorDto = tutorDto;
            this.startTime = startTime;
            this.endTime = endTime;
            this.dayOfWeek = dayOfWeek;
            this.weekOfYear = weekOfYear;
            this.status =status;
            this.scheduleFormat = scheduleFormat
            this.address = address
        }

}
