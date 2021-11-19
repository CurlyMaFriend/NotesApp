package pedro.gouveia.notesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private MyViewModel viewModel;
    private Toolbar toolbarListNotes;
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbarListNotes = findViewById(R.id.toolbar);

        setSupportActionBar(toolbarListNotes);

        dummyData();

        if(findViewById(R.id.frameView) != null){
            viewModel = new ViewModelProvider(this).get(MyViewModel.class);

            viewModel.retrieveData(this);

            //viewModel.setNotes(getNotes());
            Log.d("teste", "Antes do observver");
            viewModel.getNotes().observe(this, item ->{
                Log.d("teste", "Entrou no observver");
                replaceFragment(new NoteList());
            });
            //viewModel.getSelectedAnimal().observe(this, item ->{
                //replaceFragment(new FragmentC());
                // });
       }
    }

    public void replaceFragment(Fragment frag){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.frameView, frag);
        ft.commit();
    }

    private void dummyData(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Set<String> auxSet = sp.getStringSet("notes", null);

        //if(auxSet == null || auxSet.size() == 0){
            SharedPreferences.Editor editor = sp.edit();
            editor.clear().commit();
            HashSet<String> mSet = new HashSet<>();
            mSet.add("1-Titulo da cena");
            mSet.add("2-Lista de compras");
            editor.putStringSet("notes", mSet);
            editor.apply();
        //}
    }

}