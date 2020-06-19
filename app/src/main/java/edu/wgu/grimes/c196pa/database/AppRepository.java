//*********************************************************************************
//  File:             AppRepository.java
//*********************************************************************************
//  Course:           Mobile Applications Development - C196
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c196pa.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.wgu.grimes.c196pa.database.daos.AssessmentDao;
import edu.wgu.grimes.c196pa.database.daos.CourseDao;
import edu.wgu.grimes.c196pa.database.daos.MentorDao;
import edu.wgu.grimes.c196pa.database.daos.NoteDao;
import edu.wgu.grimes.c196pa.database.daos.TermDao;
import edu.wgu.grimes.c196pa.database.entities.AssessmentEntity;
import edu.wgu.grimes.c196pa.database.entities.CourseEntity;
import edu.wgu.grimes.c196pa.database.entities.MentorEntity;
import edu.wgu.grimes.c196pa.database.entities.NoteEntity;
import edu.wgu.grimes.c196pa.database.entities.TermCusTuple;
import edu.wgu.grimes.c196pa.database.entities.TermEntity;
import edu.wgu.grimes.c196pa.database.entities.TermWithCourses;
import edu.wgu.grimes.c196pa.utilities.SampleData;

/**
 * Database Repository responsible for ensuring all database calls that need to be off the
 * GUI thread are handled asynchronously.  Also meant to separate the view models from the
 * database implementation.
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class AppRepository {

    /**
     * only one instance of the repo
     */
    private static AppRepository instance;
    /**
     * observable list of terms
     */
    private LiveData<List<TermEntity>> mTerms;
    /**
     * enables executing code bits in a separate thread
     */
    private Executor executor = Executors.newSingleThreadExecutor();
    /**
     * Term Data Access Object
     */
    private TermDao termDao;
    /**
     * Course Data Access Object
     */
    private CourseDao courseDao;
    /**
     * Assessment Data Access Object
     */
    private AssessmentDao assessmentDao;
    /**
     * Note Data Access Object
     */
    private NoteDao noteDao;
    /**
     * Mentor Data Access Object
     */
    private MentorDao mentorDao;
    /**
     * Database instance
     */
    private AppDatabase mDb;

    /**
     * Private constructor to ensure single instance, use getInstance()
     *
     * @param context
     */
    private AppRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
        termDao = mDb.termDao();
        courseDao = mDb.courseDao();
        assessmentDao = mDb.assessmentDao();
        noteDao = mDb.noteDao();
        mentorDao = mDb.mentorDao();
        mTerms = termDao.getAllTerms();
    }

    /**
     * Returns a single instance of the repo
     *
     * @param context
     * @return
     */
    public static AppRepository getInstance(Context context) {
        if (instance == null) {
            instance = new AppRepository(context);
        }
        return instance;
    }

    /**
     * Saves (insert / update) the given term
     * @param term
     */
    public void saveTerm(TermEntity term) {
        executor.execute(() -> termDao.save(term));
    }

    /**
     * Deletes the given term
     * @param term
     */
    public void deleteTerm(TermEntity term) {
        executor.execute(() -> termDao.delete(term));
    }

    /**
     * Saves (insert / update) the given course
     * @param course
     */
    public void saveCourse(CourseEntity course) {
        executor.execute(() -> courseDao.save(course));
    }

    /**
     * Deletes the given course
     * @param course
     */
    public void deleteCourse(CourseEntity course) {
        executor.execute(() -> {
            courseDao.delete(course);
            noteDao.deleteNotesForCourse(course.getId());
            assessmentDao.deleteAssessmentsForCourse(course.getId());
            mentorDao.deleteMentorsForCourse(course.getId());
        });
    }

    /**
     * Saves (insert / update) the given note
     * @param note
     */
    public void saveNote(NoteEntity note) {
        executor.execute(() -> noteDao.save(note));
    }

    /**
     * Deletes the given note
     * @param note
     */
    public void deleteNote(NoteEntity note) {
        executor.execute(() -> noteDao.delete(note));
    }

    /**
     * Saves (insert / update) the given assessment
     * @param assessment
     */
    public void saveAssessment(AssessmentEntity assessment) {
        executor.execute(() -> assessmentDao.save(assessment));
    }

    /**
     * Deletes the given assessment
     * @param assessment
     */
    public void deleteAssessment(AssessmentEntity assessment) {
        executor.execute(() -> assessmentDao.delete(assessment));
    }

    /**
     * Saves (insert / update) the given mentor
     * @param mentor
     */
    public void saveMentor(MentorEntity mentor) {
        executor.execute(() -> mentorDao.save(mentor));
    }

    /**
     * Deletes the given mentor
     * @param mentor
     */
    public void deleteMentor(MentorEntity mentor) {
        executor.execute(() -> mentorDao.delete(mentor));
    }

    /**
     * Deletes the entire database
     */
    public void deleteAllData() {
        executor.execute(() -> {
            mentorDao.deleteAll();
            noteDao.deleteAll();
            assessmentDao.deleteAll();
            courseDao.deleteAll();
            termDao.deleteAll();
        });
    }

    /**
     * Gets an observable list of terms
     * @return
     */
    public LiveData<List<TermEntity>> getAllTerms() {
        return mTerms;
    }

    /**
     * Gets a term by id
     * @param termId
     * @return
     */
    public TermEntity getTermById(int termId) {
        return mDb.termDao().getTermById(termId);
    }

    /**
     * Gets an observable count of courses by status
     * @param status
     * @return
     */
    public LiveData<Integer> getCoursesByStatus(String status) {
        return courseDao.getCoursesByStatus(status);
    }

    /**
     * Gets an observable count of courses by term id
     * @param termId
     * @return
     */
    public LiveData<List<CourseEntity>> getCoursesByTermId(int termId) {
        return courseDao.getAllCoursesForTerm(termId);
    }

    /**
     * Gets an observable list of notes by course id
     * @param courseId
     * @return
     */
    public LiveData<List<NoteEntity>> getNotesForCourse(int courseId) {
        return noteDao.getNotesForCourse(courseId);
    }

    /**
     * Gets a note by id
     * @param noteId
     * @return
     */
    public NoteEntity getNoteById(int noteId) {
        return noteDao.getNoteById(noteId);
    }

    /**
     * Gets an observable count of assessments by status
     * @param status
     * @return
     */
    public LiveData<Integer> getAssessmentsByStatus(String status) {
        return assessmentDao.getAssessmentsByStatus(status);
    }

    /**
     * Gets an observable list of assessments by course
     * @param courseId
     * @return
     */
    public LiveData<List<AssessmentEntity>> getAssessmentsForCourse(int courseId) {
        return assessmentDao.getAllAssessmentsForCourse(courseId);
    }

    /**
     * Saves all the sample data to the database
     */
    public void addSampleData() {
        executor.execute(() -> {
            termDao.saveAll(SampleData.getSampleTerms());
            courseDao.saveAll(SampleData.getSampleCourses());
            assessmentDao.saveAll(SampleData.getSampleAssessments());
            noteDao.saveAll(SampleData.getSampleCourseNotes());
            mentorDao.saveAll(SampleData.getSampleMentors());
        });
    }

    /**
     * Returns a TermWithCourses object that has a list of associated courses
     *
     * @param id
     * @return
     */
    public TermWithCourses getTermWithCourses(int id) {
        return termDao.getTermWithCourses(id);
    }

    /**
     * Gets a course by id
     * @param i
     * @return
     */
    public CourseEntity getCourseById(int i) {
        return courseDao.getCourseById(i);
    }

    /**
     * Gets an observable list of terms with a sum of competency units in each
     * @return
     */
    public LiveData<List<TermCusTuple>> getAllTermCus() {
        return termDao.getTermCus();
    }

    /**
     * Gets an assessment by id
     * @param assessmentId
     * @return
     */
    public AssessmentEntity getAssessmentById(int assessmentId) {
        return assessmentDao.getAssessmentById(assessmentId);
    }

    /**
     * Get an observable list of mentors by course id
     * @param courseId
     * @return
     */
    public LiveData<List<MentorEntity>> getMentorsForCourse(int courseId) {
        return mentorDao.getAllMentorsForCourse(courseId);
    }

    /**
     * Gets a mentor by id
     * @param mentorId
     * @return
     */
    public MentorEntity getMentorById(int mentorId) {
        return mentorDao.getMentorById(mentorId);
    }

    /**
     * Gets an observable count of total courses
     * @return
     */
    public LiveData<Integer> getTotalCourseCount() {
        return courseDao.getLiveCount();
    }

    /**
     * Gets an observable count of total assessments
     * @return
     */
    public LiveData<Integer> getTotalAssessmentCount() {
        return assessmentDao.getLiveCount();
    }

}
