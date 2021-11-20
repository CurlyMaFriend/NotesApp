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
import android.widget.Toast;

public class FragmentNoteDetails extends Fragment {

    private View view;
    private MyViewModel viewModel;
    private EditText txtDescription, txtTitle;
    private Note selectedNote;

    public FragmentNoteDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_note_details, container, false);
        setHasOptionsMenu(true);
        viewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtTitle = view.findViewById(R.id.noteDetailsTitle);
        txtDescription = view.findViewById(R.id.noteDetailsDescription);

        selectedNote = viewModel.getSelectedNote().getValue();

        txtTitle.setText(selectedNote.getTitle());
        txtDescription.setText(selectedNote.getDescription());

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

        Note editedNote;
        String description = "", title = "";

        if(id == R.id.save){
            description = txtDescription.getText().toString();
            title = txtTitle.getText().toString();

            editedNote = new Note(selectedNote.getId(), title, description);

            if(title.equals("")){
                Toast.makeText(view.getContext(), "Please insert some title", Toast.LENGTH_SHORT).show();
            } else {
                viewModel.editSelectedNote(editedNote);
                Toast.makeText(view.getContext(), "Saved changes", Toast.LENGTH_SHORT).show();
            }

        }
        if(id == R.id.back){
            viewModel.setSaveDone();
        }
        return true;
    }

}