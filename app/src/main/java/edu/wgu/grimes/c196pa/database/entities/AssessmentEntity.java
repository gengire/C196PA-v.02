package edu.wgu.grimes.c196pa.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "assessments")
public class AssessmentEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "assessment_id")
    private int id;
    private int course_id;
    private String type;
    private String title;
    private String status;
    private Date completionDate;

    @Ignore
    public AssessmentEntity(int course_id, String type, String title, String status, Date completionDate) {
        this.course_id = course_id;
        this.type = type;
        this.title = title;
        this.status = status;
        this.completionDate = completionDate;
    }

    public AssessmentEntity(int id, int course_id, String type, String title, String status, Date completionDate) {
        this.id = id;
        this.course_id = course_id;
        this.type = type;
        this.title = title;
        this.status = status;
        this.completionDate = completionDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    @Override
    public String toString() {
        return "AssessmentEntity{" +
                "id=" + id +
                ", course_id=" + course_id +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", completionDate=" + completionDate +
                '}';
    }
}
