//*********************************************************************************
//  File:             AppRepository.java
//*********************************************************************************
//  Course:           Software Development Capstone - C868
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c868pa.database;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.wgu.grimes.c868pa.database.daos.AccountDao;
import edu.wgu.grimes.c868pa.database.daos.AssessmentDao;
import edu.wgu.grimes.c868pa.database.daos.CourseDao;
import edu.wgu.grimes.c868pa.database.daos.MentorDao;
import edu.wgu.grimes.c868pa.database.daos.NoteDao;
import edu.wgu.grimes.c868pa.database.daos.TermDao;
import edu.wgu.grimes.c868pa.database.entities.AccountEntity;
import edu.wgu.grimes.c868pa.database.entities.AssessmentEntity;
import edu.wgu.grimes.c868pa.database.entities.CourseEntity;
import edu.wgu.grimes.c868pa.database.entities.MentorEntity;
import edu.wgu.grimes.c868pa.database.entities.NoteEntity;
import edu.wgu.grimes.c868pa.database.entities.TermCompetencyUnitsTuple;
import edu.wgu.grimes.c868pa.database.entities.TermEntity;
import edu.wgu.grimes.c868pa.database.entities.TermWithCourses;
import edu.wgu.grimes.c868pa.utilities.SampleData;

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
    private final Executor executor = Executors.newSingleThreadExecutor();
    /**
     * Account Data Access Object
     */
    private final AccountDao accountDao;
    /**
     * Term Data Access Object
     */
    private final TermDao termDao;
    /**
     * Course Data Access Object
     */
    private final CourseDao courseDao;
    /**
     * Assessment Data Access Object
     */
    private final AssessmentDao assessmentDao;
    /**
     * Note Data Access Object
     */
    private final NoteDao noteDao;
    /**
     * Mentor Data Access Object
     */
    private final MentorDao mentorDao;
    /**
     * Database instance
     */
    private final AppDatabase mDb;
    /**
     * Logged in user account id
     */
    private int loggedInAccountId;

    /**
     * Private constructor to ensure single instance, use getInstance()
     *
     * @param context The context
     */
    private AppRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
        termDao = mDb.termDao();
        courseDao = mDb.courseDao();
        assessmentDao = mDb.assessmentDao();
        noteDao = mDb.noteDao();
        mentorDao = mDb.mentorDao();
        accountDao = mDb.accountDao();

    }

    /**
     * Returns a single instance of the repo
     *
     * @param context The context
     * @return The app repo
     */
    public static AppRepository getInstance(Context context) {
        if (instance == null) {
            instance = new AppRepository(context);
        }
        return instance;
    }

    public void setLoggedInAccountId(int loggedInAccountId) {
        this.loggedInAccountId = loggedInAccountId;
        Log.d("LOGIN", "repo setting terms for user: " + loggedInAccountId);
        mTerms = termDao.getAllTermsForAccount(loggedInAccountId);
    }

    /**
     * Saves (insert / update) the given term
     *
     * @param term The term to save
     */
    public void saveTerm(TermEntity term) {
        term.setAccount_id(loggedInAccountId);
        executor.execute(() -> termDao.save(term));
    }

    /**
     * Deletes the given term
     *
     * @param term The term to delete
     */
    public void deleteTerm(TermEntity term) {
        executor.execute(() -> termDao.delete(term));
    }

    /**
     * Saves (insert / update) the given course
     *
     * @param course The course to save
     */
    public void saveCourse(CourseEntity course) {
        executor.execute(() -> courseDao.save(course));
    }

    /**
     * Deletes the given course
     *
     * @param course The course to delete
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
     *
     * @param note The note to save
     */
    public void saveNote(NoteEntity note) {
        executor.execute(() -> noteDao.save(note));
    }

    /**
     * Deletes the given note
     *
     * @param note The note to delete
     */
    public void deleteNote(NoteEntity note) {
        executor.execute(() -> noteDao.delete(note));
    }

    /**
     * Saves (insert / update) the given assessment
     *
     * @param assessment The assessment to save
     */
    public void saveAssessment(AssessmentEntity assessment) {
        executor.execute(() -> assessmentDao.save(assessment));
    }

    /**
     * Deletes the given assessment
     *
     * @param assessment The assessment ot delete
     */
    public void deleteAssessment(AssessmentEntity assessment) {
        executor.execute(() -> assessmentDao.delete(assessment));
    }

    /**
     * Saves (insert / update) the given mentor
     *
     * @param mentor The mentor to save
     */
    public void saveMentor(MentorEntity mentor) {
        executor.execute(() -> mentorDao.save(mentor));
    }

    /**
     * Deletes the given mentor
     *
     * @param mentor The mentor to delete
     */
    public void deleteMentor(MentorEntity mentor) {
        executor.execute(() -> mentorDao.delete(mentor));
    }

    public void saveAccount(AccountEntity account) {
        executor.execute(() -> accountDao.save(account));
    }

    /**
     * Deletes the entire database
     */
    public void deleteAllData() {
        executor.execute(() -> {
            mentorDao.deleteAll(loggedInAccountId);
            noteDao.deleteAll(loggedInAccountId);
            assessmentDao.deleteAll(loggedInAccountId);
            courseDao.deleteAll(loggedInAccountId);
            termDao.deleteAll(loggedInAccountId);
        });
    }

    /**
     * Gets an observable list of terms
     *
     * @return An observable list of all terms
     */
    public LiveData<List<TermEntity>> getAllTerms() {
        return mTerms;
    }

    /**
     * Gets a term by id
     *
     * @param termId The id of the term
     * @return The term with the given id
     */
    public TermEntity getTermById(int termId) {
        return mDb.termDao().getTermById(termId);
    }

    /**
     * Gets an observable count of courses by status
     *
     * @param status The status of the course
     * @return An observable count of courses with the given status
     */
    public LiveData<Integer> getCoursesByStatus(String status) {
        Log.d("LOGIN", "getting courses by status: " + status + ", for user: " + loggedInAccountId);
        return courseDao.getCoursesByStatus(status, loggedInAccountId);
    }

    /**
     * Gets an observable count of courses by term id
     *
     * @param termId The term id
     * @return An observable list of courses associated with the given term id
     */
    public LiveData<List<CourseEntity>> getCoursesByTermId(int termId) {
        return courseDao.getAllCoursesForTerm(termId);
    }

    /**
     * Gets an observable list of notes by course id
     *
     * @param courseId The course id
     * @return An observable list of notes associated with the given course id
     */
    public LiveData<List<NoteEntity>> getNotesForCourse(int courseId) {
        return noteDao.getNotesForCourse(courseId);
    }

    /**
     * Gets a note by id
     *
     * @param noteId The id of the note
     * @return The note with the given id
     */
    public NoteEntity getNoteById(int noteId) {
        return noteDao.getNoteById(noteId);
    }

    /**
     * Gets an observable count of assessments by status
     *
     * @param status The status of the assessment
     * @return An observable count of assessments with the given status
     */
    public LiveData<Integer> getAssessmentsByStatus(String status) {
        return assessmentDao.getAssessmentsByStatus(status, loggedInAccountId);
    }

    /**
     * Gets an observable list of assessments by course
     *
     * @param courseId The course id
     * @return An observable list of assessment associated with the given course id
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
     * @param termId The term termId
     * @return TermWithCourses for the given term termId
     */
    public TermWithCourses getTermWithCourses(int termId) {
        return termDao.getTermWithCourses(termId);
    }

    /**
     * Gets a course by id
     *
     * @param courseId The id of the course
     * @return The course with the given id
     */
    public CourseEntity getCourseById(int courseId) {
        return courseDao.getCourseById(courseId);
    }

    /**
     * Gets an observable list of terms with a sum of competency units in each
     *
     * @return An observable list of TermCusTuples for all terms
     */
    public LiveData<List<TermCompetencyUnitsTuple>> getAllTermCompetencyUnits() {
        return termDao.getTermCompetencyUnitsForAccount(loggedInAccountId);
    }

    /**
     * Gets an assessment by id
     *
     * @param assessmentId The id of the assessment
     * @return Assessment with the given id
     */
    public AssessmentEntity getAssessmentById(int assessmentId) {
        return assessmentDao.getAssessmentById(assessmentId);
    }

    /**
     * Get an observable list of mentors by course id
     *
     * @param courseId The course id
     * @return An observable list of mentors associated with the given course id
     */
    public LiveData<List<MentorEntity>> getMentorsForCourse(int courseId) {
        return mentorDao.getAllMentorsForCourse(courseId);
    }

    /**
     * Gets a mentor by id
     *
     * @param mentorId The id of the mentor
     * @return The mentor with the given id
     */
    public MentorEntity getMentorById(int mentorId) {
        return mentorDao.getMentorById(mentorId);
    }

    /**
     * Gets an observable count of total courses
     *
     * @return An observable count of all courses
     */
    public LiveData<Integer> getTotalCourseCount() {
        return courseDao.getLiveCount(loggedInAccountId);
    }

    /**
     * Gets an observable count of total assessments
     *
     * @return An observable count of all assessments
     */
    public LiveData<Integer> getTotalAssessmentCount() {
        return assessmentDao.getLiveCount(loggedInAccountId);
    }

    public AccountEntity getAccountByUsername(String username) {
        return accountDao.getAccountByUsername(username);
    }

    public int getCountByUsername(String username) {
        return accountDao.getCountByUsername(username);
    }

    public void deleteAllAccounts() {
        executor.execute(() -> {
            accountDao.deleteAll();
        });
    }
}
