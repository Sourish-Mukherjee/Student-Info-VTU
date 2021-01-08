package information;

public class MoocCourses {

    private String agency;
    private String courses;
    private String no_of_weeks;
    private String name;
    private String usn;
    private String status;
    private int mooc_id, marks;

    public MoocCourses(String name, String usn, int mooc_id){
        this.name = name;
        this.usn = usn;
        this.mooc_id = mooc_id;
    }

    public MoocCourses(String name, String usn, String courses, String agency, String no_of_weeks, String status, int marks) {
        this.name = name;
        this.usn = usn;
        this.status = status;
        this.marks = marks;
        this.courses = courses;
        this.agency = agency;
        this.no_of_weeks=no_of_weeks;
    }

    public MoocCourses(int mooc_id, String courses, String agency, String no_of_weeks) {
        this.agency = agency;
        this.courses = courses;
        this.mooc_id = mooc_id;
        this.no_of_weeks = no_of_weeks;
    }

    public String getAgency() {
        return agency;
    }

    public String getCourses() {
        return courses;
    }

    public int getMooc_id() {
        return mooc_id;
    }

    public String getName() {
        return name;
    }

    public String getUsn() {
        return usn;
    }

    public String getNo_of_weeks() {
        return no_of_weeks;
    }

    public String getStatus() {
        return status;
    }

    public int getMarks() {
        return marks;
    }
}
