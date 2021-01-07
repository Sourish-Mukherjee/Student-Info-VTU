package information;

public class SubjectList {

    private String subjectCode;
    private String subjectName;
    private String subjectBranch;
    private int scheme;

    public SubjectList(String subjectCode, String subjectName, String subjectBranch, int scheme) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.subjectBranch = subjectBranch;
        this.scheme = scheme;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getSubjectBranch() {
        return subjectBranch;
    }

    public int getScheme() {
        return scheme;
    }

}
