package edu.wgu.grimes.c196pa.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.wgu.grimes.c196pa.database.entities.AssessmentEntity;

@Dao
public interface AssessmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(AssessmentEntity course);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(List<AssessmentEntity> sampleAssessments);

    @Delete
    void delete(AssessmentEntity course);

    @Query("delete from assessments")
    void deleteAll();

    @Query("select * from assessments where course_id = :courseId")
    LiveData<List<AssessmentEntity>> getAllAssessmentsForCourse(int courseId);

    @Query("select * from assessments where assessment_id = :assessmentId")
    AssessmentEntity getAssessmentById(int assessmentId);

    @Query("select count(*) from assessments where status = :status")
    LiveData<Integer> getAssessmentsByStatus(String status);

    @Query("delete from assessments where course_id = :courseId")
    void deleteAssessmentsForCourse(int courseId);

    @Query("select count(*) from assessments")
    LiveData<Integer> getLiveCount();

}
