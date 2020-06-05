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

public class AppRepository {

    private static AppRepository instance;

    private TermDao termDao;
    private CourseDao courseDao;
    private AssessmentDao assessmentDao;
    private NoteDao noteDao;
    private MentorDao mentorDao;

    public LiveData<List<TermEntity>> mTerms;

    private AppDatabase mDb;
    Executor executor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) {
        if (instance == null) {
            instance = new AppRepository(context);
        }
        return instance;
    }

    public AppRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
        termDao = mDb.termDao();
        courseDao = mDb.courseDao();
        assessmentDao = mDb.assessmentDao();
        noteDao = mDb.noteDao();
        mentorDao = mDb.mentorDao();
        mTerms = termDao.getAllTerms();
    }

    public void saveTerm(TermEntity term) {
        executor.execute(() -> termDao.save(term));
    }

    public void deleteTerm(TermEntity term) {
        executor.execute(() -> termDao.delete(term));
    }

    public void saveCourse(CourseEntity course) {
        executor.execute(() -> courseDao.save(course));
    }

    public void deleteCourse(CourseEntity course) {
        executor.execute(() -> {
                courseDao.delete(course);
                noteDao.deleteNotesForCourse(course.getId());
                assessmentDao.deleteAssessmentsForCourse(course.getId());
            });
    }

    public void saveNote(NoteEntity note) {
        executor.execute(() -> noteDao.save(note));
    }

    public void deleteNote(NoteEntity note) {
        executor.execute(() -> noteDao.delete(note));
    }

    public void saveAssessment(AssessmentEntity assessment) {
        executor.execute(() -> assessmentDao.save(assessment));
    }

    public void deleteAssessment(AssessmentEntity assessment) {
        executor.execute(() -> assessmentDao.delete(assessment));
    }

    public void saveMentor(MentorEntity mentor) { executor.execute(() -> mentorDao.save(mentor)); }

    public void deleteMentor(MentorEntity mentor) { executor.execute(() -> mentorDao.delete(mentor)); }

    public void deleteAllData() {
        executor.execute(() -> {
            assessmentDao.deleteAll();
            noteDao.deleteAll();
            courseDao.deleteAll();
            termDao.deleteAll();
        });
    }

    public LiveData<List<TermEntity>> getAllTerms() {
        return mTerms;
    }

    public TermEntity getTermById(int termId) {
        return mDb.termDao().getTermById(termId);
    }

    public LiveData<Integer> getCoursesByStatus(String status) {
        return courseDao.getCoursesByStatus(status);
    }

    public LiveData<List<CourseEntity>> getCoursesByTermId(int termId) {
        return courseDao.getAllCoursesForTerm(termId);
    }

    public LiveData<List<NoteEntity>> getNotesForCourse(int courseId) {
        return noteDao.getNotesForCourse(courseId);
    }

    public NoteEntity getNoteById(int noteId) {
        return noteDao.getNoteById(noteId);
    }

    public LiveData<Integer> getAssessmentsByStatus(String status) {
        return assessmentDao.getAssessmentsByStatus(status);
    }

    public LiveData<List<AssessmentEntity>> getAssessmentsForCourse(int courseId) {
        return assessmentDao.getAllAssessmentsForCourse(courseId);
    }

    public void addSampleData() {
        executor.execute(() -> {
            termDao.saveAll(SampleData.getSampleTerms());
            courseDao.saveAll(SampleData.getSampleCourses());
            assessmentDao.saveAll(SampleData.getSampleAssessments());
            noteDao.saveAll(SampleData.getSampleCourseNotes());
        });
    }

    public TermWithCourses getTermWithCourses(int id) {
        return termDao.getTermWithCourses(id);
    }

    public CourseEntity getCourseById(int i) {
        return courseDao.getCourseById(i);
    }

    public LiveData<List<TermCusTuple>> getAllTermCus() {
        return termDao.getTermCus();
    }

    public AssessmentEntity getAssessmentById(int assessmentId) {
        return assessmentDao.getAssessmentById(assessmentId);
    }

    public LiveData<List<MentorEntity>> getMentorsForCourse(int courseId) {
        return mentorDao.getAllMentorsForCourse(courseId);
    }

    public MentorEntity getMentorById(int mentorId) {
        return mentorDao.getMentorById(mentorId);
    }
}
