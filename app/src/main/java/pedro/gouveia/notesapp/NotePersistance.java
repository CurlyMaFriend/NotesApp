package pedro.gouveia.notesapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class NotePersistance implements Runnable {

    private ArrayList<Note> noteArrayList;
    private Context context;

    public NotePersistance(ArrayList<Note> aArray, Context aContext){
        noteArrayList = aArray;
        context = aContext;
    }

    @Override
    public void run() {
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
            Log.d("notePers", "Exception");
        }

        if(flag){
            for(Note n : noteArrayList){
                mSet.add(n.getId()+"-"+n.getTitle());
                output.println(n.getId()+"-"+n.getDescription()+"\t");
            }
            output.close();
            editor.putStringSet("notes", mSet);
            editor.apply();
        }
    }
}
