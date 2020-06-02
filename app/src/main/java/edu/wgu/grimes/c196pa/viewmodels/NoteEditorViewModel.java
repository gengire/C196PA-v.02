package edu.wgu.grimes.c196pa.viewmodels;

import android.app.Application;
import android.text.TextUtils;
import android.view.TextureView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import edu.wgu.grimes.c196pa.database.AppRepository;
import edu.wgu.grimes.c196pa.database.entities.NoteEntity;

public class NoteEditorViewModel extends AndroidViewModel {

    public MutableLiveData<NoteEntity> mLiveNote = new MutableLiveData<>();

    private AppRepository mRepository;

    private Executor executor = Executors.newSingleThreadExecutor();

    public NoteEditorViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadNote(int noteId) {
        executor.execute(() -> {
            NoteEntity note = mRepository.getNoteById(noteId);
            mLiveNote.postValue(note);
        });
    }

    public void saveNote(int courseId, String title, String description) {
        if (TextUtils.isEmpty(title.trim())) {
            return; // no saving blank titles
        }
        NoteEntity note = mLiveNote.getValue();
        if (note == null) {
            note = new NoteEntity(courseId, title, description);
        } else {
            note.setCourse_id(courseId);
            note.setTitle(title);
            note.setDescription(description);
        }
        mRepository.saveNote(note);
    }

    public void deleteNote() {
        mRepository.deleteNote(mLiveNote.getValue());
    }
}
