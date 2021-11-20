package pedro.gouveia.notesapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

public class NoteNew extends Fragment {

    private View view;
    private MyViewModel viewModel;
    private EditText txtTitle, txtDescription;

    public NoteNew() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_note_new, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);

        txtTitle = view.findViewById(R.id.notesNewTitle);
        txtDescription = view.findViewById(R.id.notesNewDescription);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    viewModel.setSaveDone();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.menu_new_note, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        String description = "", title = "";

        if(id == R.id.save){
            description = txtDescription.getText().toString();
            title = txtTitle.getText().toString();
            Log.d("teste", "Save pressed");

            if(title.equals("")){
                Toast.makeText(view.getContext(), "Please insert some title", Toast.LENGTH_SHORT).show();
            } else {
                viewModel.addNewNote(title, description);
                Toast.makeText(view.getContext(), "Saved changes", Toast.LENGTH_SHORT).show();
            }

        }
        if(id == R.id.back){
            viewModel.setSaveDone();
        }
        return true;
    }
}