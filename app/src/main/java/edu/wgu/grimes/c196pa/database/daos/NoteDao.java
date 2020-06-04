package edu.wgu.grimes.c196pa.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.wgu.grimes.c196pa.database.entities.NoteEntity;

@Dao
public interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(NoteEntity note);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(List<NoteEntity> notes);

    @Delete
    void delete(NoteEntity note);

    @Query("delete from notes")
    void deleteAll();

    @Query("delete from notes where course_id = :courseId")
    void deleteNotesForCourse(int courseId);

    @Query("select * from notes where note_id = :noteId")
    NoteEntity getNoteById(int noteId);

    @Query("select * from notes where course_id = :courseId")
    LiveData<List<NoteEntity>> getNotesForCourse(int courseId);

    @Query("select count(*) from notes")
    int getCount();

}
