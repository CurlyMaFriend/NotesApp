package pedro.gouveia.notesapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class MyViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Note>> notes = new MutableLiveData<>();
    private MutableLiveData<Note> selectedNote = new MutableLiveData<>();
    private Context context;

    public void changeNote(String aTitle, String aDescription){
        Note selected = selectedNote.getValue();
        int index = notes.getValue().indexOf(selectedNote.getValue());
        if(!aTitle.equals("") && aTitle != null){
            selected.setTitle(aTitle);
        }
        if(!aDescription.equals("") && aDescription != null){
            selected.setDescription(aDescription);
        }
        ArrayList<Note> auxList = notes.getValue();
        auxList.set(index, selected);
        notes.setValue(auxList);
    }

    public void retrieveData(Context aContext){
        context = aContext;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> auxSet = sp.getStringSet("notes", null);

        if(auxSet != null){
            String auxString = auxSet.toString();
            String[] strArray = auxString.substring(1, auxString.length()-1).split(",");

            ArrayList<Note> noteArrayList = new ArrayList<>();
            boolean primeiro = true;
            for ( String str : strArray) {
                if(!primeiro){
                    str = str.substring(1);
                }
                primeiro = false;
                try {
                    int id = Integer.parseInt(str.substring(0,str.indexOf('-')));
                    String title = str.substring(str.indexOf('-')+1);
                    noteArrayList.add(new Note(id, title, ""));
                } catch (NumberFormatException nfe) {
                    Log.d("Erros", nfe.toString());
                }
            }
            setNotes(noteArrayList);
        }

    }

    public void setNotes(ArrayList<Note> aAnimals){
        notes.setValue(aAnimals);
    }

    public LiveData<ArrayList<Note>> getNotes( ){
        return notes;
    }

    public void setSelectedAnimal(Note aNote){
        selectedNote.setValue(aNote);
    }

    public LiveData<Note> getSelectedAnimal() {
        return selectedNote;
    }

    public void newNote(String aTitle, String aDescription){

        Note nNote = new Note(getNewId(), aTitle, aDescription);

        ArrayList<Note> notesAux = notes.getValue();
        notesAux.add(nNote);

        notes.setValue(notesAux);
    }

    public int getNewId(){
        ArrayList<Note> notesAux = notes.getValue();
        if(notesAux == null || notesAux.size() == 0){
            return 0;
        } else {

            Collections.sort(notesAux, Comparator.comparingLong(Note::getId));

            Note nMax = notesAux.get(notesAux.size() - 1);

            return nMax.getId() + 1;
        }
    }
}
