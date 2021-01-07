package information;

/*
    Author: Sourish Mukherjee
    Link: https://github.com/Sourish-Mukherjee/StudentInfoManagement
 */

// UserDefined Variable , Type - InternalMarks ----> Stores information of Internal Marks
// of the students

public class InternalMarks {

    private String name,
            usn;
    private int iat1,
            iat2,
            iat3,
            assignMarks,
            total;
    private float avg;
    private String status;

    public InternalMarks(String name, String usn, int iat1, int iat2, int iat3, int assignMarks, int total, float avg, String status) {
        this.name = name;
        this.usn = usn;
        this.iat1 = iat1;
        this.iat2 = iat2;
        this.iat3 = iat3;
        this.assignMarks = assignMarks;
        this.total = total;
        this.avg = avg;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getUsn() {
        return usn;
    }

    public int getIat1() {
        return iat1;
    }

    public int getIat2() {
        return iat2;
    }

    public int getIat3() {
        return iat3;
    }

    public int getAssignMarks() {
        return assignMarks;
    }

    public int getTotal() {
        return total;
    }

    public float getAvg() {
        return avg;
    }

    public String getStatus() {
        return status;
    }

}
