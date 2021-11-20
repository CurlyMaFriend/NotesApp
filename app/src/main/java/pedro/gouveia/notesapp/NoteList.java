package pedro.gouveia.notesapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

public class NoteList extends Fragment implements AdapterView.OnItemClickListener {

    private View view;
    private MyViewModel viewModel;
    private ArrayList<Note> notesArray;
    private ListView listViewNotes;
    private ArrayAdapter<Note> adapter;

    public NoteList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_note_list, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);

        listViewNotes = view.findViewById(R.id.listViewNotes);

        this.notesArray = viewModel.getNotes().getValue();

        adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, this.notesArray);
        listViewNotes.setAdapter(adapter);

        listViewNotes.setOnItemClickListener(this);

        listViewNotes.setScrollContainer(true);

        listViewNotes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> adapterView, View v,
                                           int i, long l) {
                Note note = notesArray.get(i);

                customDialog cdd=new customDialog(requireActivity(), note.getId(), note.getTitle());
                cdd.show();


                Window window = cdd.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                return true;
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Note note = notesArray.get(i);

        viewModel.setSelectedNote(note);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.menu_list_notes, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Type to search notes by title");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                adapter.getFilter().filter(s);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.create_note){
            viewModel.setNavigateNewNote();
            Log.d("teste", "Create note pressed");
        }
        return true;
    }
}