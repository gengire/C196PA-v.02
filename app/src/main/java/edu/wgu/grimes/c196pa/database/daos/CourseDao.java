package edu.wgu.grimes.c196pa.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.wgu.grimes.c196pa.database.entities.CourseEntity;

@Dao
public interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(CourseEntity course);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(List<CourseEntity> sampleCourses);

    @Delete
    void delete(CourseEntity course);

    @Query("delete from courses")
    void deleteAll();

    @Query("select * from courses where term_id = :termId")
    LiveData<List<CourseEntity>> getAllCoursesForTerm(int termId);

    @Query("select * from courses where course_id = :courseId")
    CourseEntity getCourseById(int courseId);

    @Query("select count(*) from courses where status = :status")
    LiveData<Integer> getCoursesByStatus(String status);

    @Query("select count(*) from courses")
    Integer getCount();

    @Query("select count(*) from courses")
    LiveData<Integer> getLiveCount();

}
