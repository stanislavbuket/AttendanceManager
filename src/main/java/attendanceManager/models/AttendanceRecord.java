package attendanceManager.models;

public class AttendanceRecord {
    private String studentId;
    private String classId;
    private boolean present;

    public AttendanceRecord(String studentId, String classId, boolean present) {
        this.studentId = studentId;
        this.classId = classId;
        this.present = present;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getClassId() {
        return classId;
    }

    public boolean isPresent() {
        return present;
    }
}
