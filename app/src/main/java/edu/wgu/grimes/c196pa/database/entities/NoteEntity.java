package edu.wgu.grimes.c196pa.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class NoteEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    private int id;
    private int course_id;
    private String title;
    private String description;

    @Ignore
    public NoteEntity(int course_id, String title, String description) {
        this.course_id = course_id;
        this.title = title;
        this.description = description;
    }

    public NoteEntity(int id, int course_id, String title, String description) {
        this.id = id;
        this.course_id = course_id;
        this.title = title;
        this.description = description;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "NoteEntity{" +
                "id=" + id +
                ", course_id=" + course_id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
