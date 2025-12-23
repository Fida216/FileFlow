package essths.li3.fileflow;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.net.Uri;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteFileActivity extends AppCompatActivity {

    EditText edtDeleteURL;
    Button btnDelete, btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_file);

        edtDeleteURL = findViewById(R.id.edtDeleteURL);
        btnDelete = findViewById(R.id.btnDelete);
        btnReturn = findViewById(R.id.btnReturn);

        btnDelete.setOnClickListener(v -> deleteRealFile());
        btnReturn.setOnClickListener(v -> finish());
    }

    private void deleteRealFile() {

        String uriText = edtDeleteURL.getText().toString().trim();

        if (uriText.isEmpty()) {
            Toast.makeText(this, "URL field is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Uri uri = Uri.parse(uriText);

        int rows = getContentResolver().delete(uri, null, null);

        if (rows > 0) {
            Toast.makeText(this, "File deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
        }
    }
}
