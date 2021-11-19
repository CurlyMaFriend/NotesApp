package pedro.gouveia.notesapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

public class NoteList extends Fragment implements AdapterView.OnItemClickListener {

    private View view;
    private MyViewModel viewModel;
    private ArrayList<Note> notesArray;
    private ListView listViewNotes;

    public NoteList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_note_list, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);

        listViewNotes = view.findViewById(R.id.listViewNotes);

        this.notesArray = viewModel.getNotes().getValue();

        ArrayAdapter<Note> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, this.notesArray);
        listViewNotes.setAdapter(adapter);

        listViewNotes.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String title = adapterView.getItemAtPosition(i).toString();
        Log.d("teste", "Clicked: " + title);
    }
}