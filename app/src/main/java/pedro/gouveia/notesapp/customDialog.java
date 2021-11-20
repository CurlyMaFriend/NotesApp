package pedro.gouveia.notesapp;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class customDialog extends Dialog implements View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button btnEdit, btnDel, btnSave, btnCancel;
    public EditText editTitulo;
    public ImageButton cancelX;

    public customDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Edit:
                btnEdit.setVisibility(View.GONE);
                btnDel.setVisibility(View.GONE);
                editTitulo.setVisibility(View.VISIBLE);
                btnSave.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_Delete:
                // Eliminar a note
                break;
            case R.id.btn_Save:
                // Alterar o título da note
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
