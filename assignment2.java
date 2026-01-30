public class UniversityManagementSystem {

    interface SportsParticipant {
        void playSport();
    }

    interface CulturalParticipant {
        void performActivity();
    }

    static abstract class Student {

        private int rollNumber;
        private String name;
        private int semester;

        static String universityName;

        final int MAX_SEMESTER = 8;

        static {
            universityName = "ABC University";
        }

        public Student(int rollNumber, String name, int semester) {
            this.rollNumber = rollNumber;
            this.name = name;
            setSemester(semester);
        }

        public int getRollNumber() {
            return rollNumber;
        }

        public String getName() {
            return name;
        }

        public int getSemester() {
            return semester;
        }

        public void setSemester(int semester) {
            if (semester > 0 && semester <= MAX_SEMESTER) {
                this.semester = semester;
            }
        }

        abstract double calculateGrade();

        public void displayDetails() {
            System.out.println("Roll No: " + rollNumber);
            System.out.println("Name: " + name);
            System.out.println("Semester: " + semester);
            System.out.println("University: " + universityName);
        }

        final void showRules() {
            System.out.println("Students must follow university rules");
        }

        static void displayUniversityName() {
            System.out.println("University Name: " + universityName);
        }
    }

    static class EngineeringStudent extends Student {

        private int internalMarks;
        private int externalMarks;

        public EngineeringStudent(int roll, String name, int sem, int internal, int external) {
            super(roll, name, sem);
            this.internalMarks = internal;
            this.externalMarks = external;
        }

        double calculateGrade() {
            return (internalMarks * 0.4) + (externalMarks * 0.6);
        }
    }

    static class MedicalStudent extends Student {

        private int theoryMarks;
        private int practicalMarks;

        public MedicalStudent(int roll, String name, int sem, int theory, int practical) {
            super(roll, name, sem);
            this.theoryMarks = theory;
            this.practicalMarks = practical;
        }

        double calculateGrade() {
            return (theoryMarks + practicalMarks) / 2.0;
        }
    }

    static class AllRounderStudent extends Student
            implements SportsParticipant, CulturalParticipant {

        public AllRounderStudent(int roll, String name, int sem) {
            super(roll, name, sem);
        }

        double calculateGrade() {
            return 85.0;
        }

        public void playSport() {
            System.out.println("Plays sports");
        }

        public void performActivity() {
            System.out.println("Performs cultural activities");
        }
    }

    public static void main(String[] args) {

        Student s1 = new EngineeringStudent(1, "Alice", 5, 80, 90);
        Student s2 = new MedicalStudent(2, "Bob", 6, 85, 88);
        Student s3 = new AllRounderStudent(3, "Charlie", 4);

        s1.displayDetails();
        System.out.println("Grade: " + s1.calculateGrade());
        System.out.println();

        s2.displayDetails();
        System.out.println("Grade: " + s2.calculateGrade());
        System.out.println();

        s3.displayDetails();
        System.out.println("Grade: " + s3.calculateGrade());
        System.out.println();

        SportsParticipant sp = new AllRounderStudent(4, "David", 3);
        sp.playSport();

        Student.displayUniversityName();
    }
}
