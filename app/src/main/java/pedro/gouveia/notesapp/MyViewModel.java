package pedro.gouveia.notesapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class MyViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Note>> notes = new MutableLiveData<>();
    private MutableLiveData<Note> selectedNote = new MutableLiveData<>();
    private Note longPressedNote;
    private MutableLiveData<Integer> navigateNewNote = new MutableLiveData<>(0);
    public boolean saveDone = false;
    private Context context;
    private Note newNote = null; //editLongPressedNote

    public void setNavigateNewNote(){
        navigateNewNote.setValue(navigateNewNote.getValue() + 1);
    }

    public LiveData<Integer> getNavigateNewNote(){
        return navigateNewNote;
    }

    public void setSaveDone(){
        saveDone = true;
        notes.setValue(notes.getValue());
        newNote = null;
    }

    public void retrieveData(Context aContext){
        context = aContext;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> auxSet = sp.getStringSet("notes", null);
        ArrayList<String> descriptions = getDescriptions();

        if(auxSet != null){
            String auxString = auxSet.toString();
            String[] strArray = auxString.substring(1, auxString.length()-1).split(",");
            ArrayList<Note> noteArrayList = new ArrayList<>();
            boolean primeiro = true;
            int i = 0;
            for ( String str : strArray) {
                if(!primeiro){
                    str = str.substring(1);
                }
                primeiro = false;
                try {
                    int id = Integer.parseInt(str.substring(0,str.indexOf('-')));
                    String title = str.substring(str.indexOf('-')+1);
                    String description = "";
                    for (String des: descriptions) {
                        if(Integer.parseInt(des.substring(0,des.indexOf('-'))) == id){
                            description = des.substring(des.indexOf('-')+1);
                            break;
                        }
                    }

                    noteArrayList.add(new Note(id, title, description));
                } catch (NumberFormatException nfe) {
                    Log.d("Erros", nfe.toString());
                }
                i++;
            }

            noteArrayList.forEach(note -> {
                Log.d("teste", "Titulo: " + note.getTitle());
                Log.d("teste", "Descricao: " + note.getDescription());
            });

            setNotes(noteArrayList);
            setSaveDone();
        }

    }

    public void setNotes(ArrayList<Note> aAnimals){
        notes.setValue(aAnimals);
    }

    public LiveData<ArrayList<Note>> getNotes( ){
        return notes;
    }

    public void setSelectedNote(Note aNote){
        selectedNote.setValue(aNote);
    }

    public LiveData<Note> getSelectedNote() {
        return selectedNote;
    }

    public void setLongPressedNote(Note aNote){
        longPressedNote = aNote;
    }

    public void addNewNote(String aTitle, String aDescription){

        Note nNote;

        ArrayList<Note> notesAux = notes.getValue();

        if(newNote == null){
            nNote = new Note(getNewId(), aTitle, aDescription);
            notesAux.add(nNote);
            newNote = nNote;
        } else {
            nNote = new Note(newNote.getId(), aTitle, aDescription);
            notesAux.set(notesAux.indexOf(newNote), nNote);
            newNote = nNote;
        }
        persistNotes(notesAux);
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

    private ArrayList<String> getDescriptions(){

        ArrayList<String> descs = new ArrayList<>();
        String descriptionHolder = "";
        try {
            Scanner scan = new Scanner(context.openFileInput("notes.txt"));

            while(scan.hasNextLine()) {
                String text = scan.nextLine();
                descriptionHolder += text + "\n";
                if(descriptionHolder.substring(descriptionHolder.length()-2).equals("\t\n")){
                    descs.add(descriptionHolder.substring(0, descriptionHolder.length()-1));
                    descriptionHolder = "";
                }
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return descs;
    }

    public void editSelectedNote(Note editedNote){
        saveDone = false;
        ArrayList<Note> auxArray = notes.getValue();

        auxArray.set(auxArray.indexOf(selectedNote.getValue()), editedNote);

        persistNotes(auxArray);
        notes.setValue(auxArray);
    }

    public void editTitleNote(int aNoteId, String aTitle){
        ArrayList<Note> auxArray = notes.getValue();

        Note n = getNoteById(aNoteId);
        int i = auxArray.indexOf(n);

        n.setTitle(aTitle);
        auxArray.set(i, n);

        persistNotes(auxArray);
        notes.setValue(auxArray);
        setSaveDone();
    }

    public Note getNoteById(int id){

        for (Note n : notes.getValue()) {
            if(n.getId() == id){
                return n;
            }
        }

        return null;
    }

    public void deleteTitleNote(int aSelectedNoteId){
        ArrayList<Note> auxArray = notes.getValue();

        auxArray.remove(getNoteById(aSelectedNoteId));

        persistNotes(auxArray);
        notes.setValue(auxArray);
        setSaveDone();
    }

    public void persistNotes(ArrayList<Note> notesArray){

        boolean flag = false;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> auxSet = sp.getStringSet("notes", null);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().commit();
        HashSet<String> mSet = new HashSet<>();

        PrintStream output = null;
        try {
            output = new PrintStream(context.openFileOutput("notes.txt", context.MODE_PRIVATE));
            flag = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(flag){
            for(Note n : notesArray){
                mSet.add(n.getId()+"-"+n.getTitle());
                output.println(n.getId()+"-"+n.getDescription()+"\t");
            }
            output.close();
            editor.putStringSet("notes", mSet);
            editor.apply();
        }

    }
}
