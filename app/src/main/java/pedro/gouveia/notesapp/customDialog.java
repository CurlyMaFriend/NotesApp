package pedro.gouveia.notesapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

public class customDialog extends Dialog implements View.OnClickListener {

    private Dialog d;
    private Button btnEdit, btnDel, btnSave, btnCancel;
    private EditText editTitulo;
    private ImageButton cancelX;
    private int selectedNoteId;
    private String selectedNoteTitle;
    private MyViewModel viewModel;
    private FragmentActivity fa;

    public customDialog(FragmentActivity aFA, int noteId, String noteTitle) {
        super(aFA);
        selectedNoteId = noteId;
        fa = aFA;
        selectedNoteTitle = noteTitle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);

        btnEdit = (Button) findViewById(R.id.btn_Edit);
        btnDel = (Button) findViewById(R.id.btn_Delete);
        btnSave = (Button) findViewById(R.id.btn_Save);
        btnCancel = (Button) findViewById(R.id.btn_Cancel);
        editTitulo = (EditText) findViewById(R.id.edit_Titulo);
        cancelX = (ImageButton) findViewById(R.id.imageSair);

        btnEdit.setOnClickListener(this);
        btnDel.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        cancelX.setOnClickListener(this);

        viewModel = new ViewModelProvider(fa).get(MyViewModel.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Edit:
                btnEdit.setVisibility(View.GONE);
                btnDel.setVisibility(View.GONE);
                editTitulo.setVisibility(View.VISIBLE);
                editTitulo.setText(selectedNoteTitle);
                btnSave.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_Delete:
                viewModel.deleteTitleNote(selectedNoteId);
                Toast.makeText(fa, "Note deleted", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            case R.id.btn_Save:
                String title = editTitulo.getText().toString();
                if(!title.equals("")){
                    viewModel.editTitleNote(selectedNoteId, title);;
                    Toast.makeText(fa, "Saved changes", Toast.LENGTH_SHORT).show();
                    dismiss();
                } else {
                    Toast.makeText(fa, "Title is mandatory", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_Cancel:
                btnEdit.setVisibility(View.VISIBLE);
                btnDel.setVisibility(View.VISIBLE);
                editTitulo.setVisibility(View.GONE);
                btnSave.setVisibility(View.GONE);
                btnCancel.setVisibility(View.GONE);
                break;
            case R.id.imageSair:
                dismiss();
                break;
            default:
                break;
        }
    }

}
