package edu.wgu.grimes.c196pa.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.wgu.grimes.c196pa.database.entities.MentorEntity;

@Dao
public interface MentorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(MentorEntity course);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(List<MentorEntity> mentors);

    @Delete
    void delete(MentorEntity course);

    @Query("delete from mentors")
    void deleteAll();

    @Query("select * from mentors")
    LiveData<List<MentorEntity>> getAllMentors();

    @Query("select * from mentors where course_id = :courseId")
    LiveData<List<MentorEntity>> getAllMentorsForCourse(int courseId);

    @Query("select * from mentors where mentor_id = :mentorId")
    MentorEntity getMentorById(int mentorId);

    @Query("select count(*) from mentors")
    Integer getCount();

}