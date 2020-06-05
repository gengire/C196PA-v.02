package edu.wgu.grimes.c196pa.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.wgu.grimes.c196pa.database.AppRepository;
import edu.wgu.grimes.c196pa.database.entities.CourseEntity;
import edu.wgu.grimes.c196pa.database.entities.NoteEntity;
import edu.wgu.grimes.c196pa.database.entities.TermEntity;

public class NotesListViewModel extends AndroidViewModel {

    AppRepository mRepository;
    private LiveData<List<NoteEntity>> allNotes;
    Executor executor = Executors.newSingleThreadExecutor();

    public NotesListViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadCoursesNotes(int courseId) {
        allNotes = mRepository.getNotesForCourse(courseId);
    }

    public LiveData<List<NoteEntity>> getCourseNotes() {
        return allNotes;
    }

    public void deleteNote(NoteEntity note) {
        mRepository.deleteNote(note);
    }

}
