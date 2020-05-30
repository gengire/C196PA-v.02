package edu.wgu.grimes.c196pa.database.daos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

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

    @Query("select * from courses")
    LiveData<List<CourseEntity>> getAllCourses();

    @Query("select * from courses where term_id = :termId")
    LiveData<List<CourseEntity>> getAllCoursesForTerm(int termId);

    @Query("select * from courses where course_id = :courseId")
    CourseEntity getCourseById(int courseId);

    @Query("select count(*) from courses where status = :status")
    LiveData<Integer> getCoursesByStatus(String status);

    @Query("select count(*) from courses")
    Integer getCount();

//    @Transaction
//    @Query("select * from courses")
//    LiveData<List<CourseWithMentors>> getAllCoursesWithMentors();
//
//    @Transaction
//    @Query("select * from courses where course_id = :courseId")
//    LiveData<List<CourseWithMentors>> getCourseWithMentors(int courseId);
//
//    @Transaction
//    @Query("select * from courses")
//    LiveData<List<CourseWithAssessments>> getAllCoursesWithAssessments();
//
//    @Transaction
//    @Query("select * from courses where course_id = :courseId")
//    LiveData<List<CourseWithAssessments>> getCourseWithAssessments(int courseId);
//
//    @Transaction
//    @Query("select * from courses")
//    LiveData<List<CourseWithNotes>> getAllCoursesWithNotes();
//
//    @Transaction
//    @Query("select * from courses where course_id = courseId")
//    LiveData<List<CourseWithNotes>> getCourseWithNotes(int courseId);

}
