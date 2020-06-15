package edu.wgu.grimes.c196pa.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "courses")
public class CourseEntity implements HasId {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "course_id")
    private int id;
    @ColumnInfo(name = "term_id")
    private int termId;
    private int competencyUnits;
    private String code;
    private String title;
    private Date startDate;
    private Date startDateAlarm;
    private Date endDate; // anticipated
    private Date endDateAlarm;
    private String status;

    @Ignore
    public CourseEntity(int termId, int competencyUnits, String code, String title, Date startDate, Date startDateAlarm, Date endDate, Date endDateAlarm, String status) {
        this.termId = termId;
        this.competencyUnits = competencyUnits;
        this.code = code;
        this.title = title;
        this.startDate = startDate;
        this.startDateAlarm = startDateAlarm;
        this.endDate = endDate;
        this.endDateAlarm = endDateAlarm;
        this.status = status;
    }

    public CourseEntity(int id, int termId, int competencyUnits, String code, String title, Date startDate, Date startDateAlarm, Date endDate, Date endDateAlarm, String status) {
        this.id = id;
        this.termId = termId;
        this.competencyUnits = competencyUnits;
        this.code = code;
        this.title = title;
        this.startDate = startDate;
        this.startDateAlarm = startDateAlarm;
        this.endDate = endDate;
        this.endDateAlarm = endDateAlarm;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public int getCompetencyUnits() {
        return competencyUnits;
    }

    public void setCompetencyUnits(int competencyUnits) {
        this.competencyUnits = competencyUnits;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDateAlarm() {
        return startDateAlarm;
    }

    public void setStartDateAlarm(Date startDateAlarm) {
        this.startDateAlarm = startDateAlarm;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDateAlarm() {
        return endDateAlarm;
    }

    public void setEndDateAlarm(Date endDateAlarm) {
        this.endDateAlarm = endDateAlarm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @NonNull
    @Override
    public String toString() {
        return "CourseEntity{" +
                "id=" + id +
                ", termId=" + termId +
                ", competencyUnits=" + competencyUnits +
                ", code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", startDateAlarm=" + startDateAlarm +
                ", endDate=" + endDate +
                ", endDateAlarm=" + endDateAlarm +
                ", status='" + status + '\'' +
                '}';
    }
}
