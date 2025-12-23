package essths.li3.fileflow;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ModifyFileActivity extends AppCompatActivity {

    EditText edtOldFileName, edtOldFileURL, edtNewFileName, edtNewFileURL, edtNewFileCategory;
    CheckBox checkUrgentModify;
    Button btnModify, btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_file);

        edtOldFileName = findViewById(R.id.edtOldFileName);
        edtOldFileURL = findViewById(R.id.edtOldFileURL);
        edtNewFileName = findViewById(R.id.edtNewFileName);
        edtNewFileURL = findViewById(R.id.edtNewFileURL);
        edtNewFileCategory = findViewById(R.id.edtNewFileCategory);
        checkUrgentModify = findViewById(R.id.checkUrgentModify);
        btnModify = findViewById(R.id.btnModify);
        btnReturn = findViewById(R.id.btnReturn);

        btnModify.setOnClickListener(v -> modifyFile());
        btnReturn.setOnClickListener(v -> finish());
    }

    private void modifyFile() {

        String oldName = edtOldFileName.getText().toString().trim();
        String oldURL = edtOldFileURL.getText().toString().trim();
        String newName = edtNewFileName.getText().toString().trim();
        String newURL = edtNewFileURL.getText().toString().trim();
        String newCategory = edtNewFileCategory.getText().toString().trim();
        boolean newImportant = checkUrgentModify.isChecked();

        // -------- 1) Empty fields --------
        if (oldName.isEmpty() || oldURL.isEmpty()) {
            Toast.makeText(this, "Old name and old file URL cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (newName.isEmpty() || newURL.isEmpty() || newCategory.isEmpty()) {
            Toast.makeText(this, "New file fields cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convertir oldURL en Uri
        Uri oldUri = Uri.parse(oldURL);

        // -------- 2) Search old file --------
        File target = null;

        for (File f : MainActivity.fileList) {
            if (f.getName().equals(oldName) && f.getUri().equals(oldUri)) {
                target = f;
                break;
            }
        }

        if (target == null) {
            Toast.makeText(this, "File not found.", Toast.LENGTH_SHORT).show();
            return;
        }

        // -------- 3) Check if new file (name + URL) already exists --------
        Uri newUri = Uri.parse(newURL);

        for (File f : MainActivity.fileList) {
            if (f != target && f.getName().equals(newName) && f.getUri().equals(newUri)) {
                Toast.makeText(this, "A file with this name and URL already exists.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // -------- 4) Update file --------
        target.setName(newName);
        target.setUri(newUri);
        target.setCategory(newCategory);
        target.setImportant(newImportant);

        Toast.makeText(this, "File modified successfully.", Toast.LENGTH_SHORT).show();
        finish();
    }
}
