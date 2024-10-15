package attendanceManager.models;

public class Schedule {
    private String classId;
    private String subject;
    private String dateTime;

    public Schedule(String classId, String subject, String dateTime) {
        this.classId = classId;
        this.subject = subject;
        this.dateTime = dateTime;
    }

    public String getClassId() {
        return classId;
    }

    public String getSubject() {
        return subject;
    }

    public String getDateTime() {
        return dateTime;
    }
}
