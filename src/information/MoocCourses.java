package information;

public class MoocCourses {

    private String agency, courses, no_of_weeks;
    private int mooc_id;

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

    public String getNo_of_weeks() {
        return no_of_weeks;
    }
}
