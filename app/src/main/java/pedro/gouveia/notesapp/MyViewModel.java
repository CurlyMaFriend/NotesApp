package pedro.gouveia.notesapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {

    private MutableLiveData<Note> notes = new MutableLiveData<>();

    public LiveData<Note> getText() {
        return notes;
    }

    public void setText(Note input) {
        notes.setValue(input);
    }

    @Override
    public String toString() {
        return "MyViewModel{" +
                "notes=" + notes +
                '}';
    }
}
