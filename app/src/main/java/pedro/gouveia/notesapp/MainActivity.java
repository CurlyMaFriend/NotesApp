package pedro.gouveia.notesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

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

        if(findViewById(R.id.frameView) != null){
            viewModel = new ViewModelProvider(this).get(MyViewModel.class);

            viewModel.retrieveData(this);

            Log.d("teste", "Antes do observver");
            viewModel.getNotes().observe(this, item ->{
                Log.d("teste", "Entrou no observver");
                if(viewModel.saveDone){
                    replaceFragment(new NoteList());
                    getSupportActionBar().setTitle("Notes");
                    viewModel.saveDone = false;
                }
            });

            viewModel.getNavigateNewNote().observe(this, item ->{
                if(item > 0){
                    Log.d("teste", "Entrou no navigateNewNote");
                    replaceFragment(new NoteNew());
                    getSupportActionBar().setTitle("New Note");
                }
            });

            viewModel.getSelectedNote().observe(this, item ->{
                if(item != null){
                    Log.d("teste", "Entrou no navigateDetails");
                    replaceFragment(new FragmentNoteDetails());
                    getSupportActionBar().setTitle("Note Details");
                }
            });
       }
    }

    public void replaceFragment(Fragment frag){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.frameView, frag);
        ft.commit();
    }
}