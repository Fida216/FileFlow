package essths.li3.fileflow;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddFileActivity extends AppCompatActivity {

    Button btnSelectFile, btnReturnAdd;
    Uri selectedFileUri = null;
    private static final int PICK_FILE_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_file);

        btnSelectFile = findViewById(R.id.btnSelectFile);
        btnReturnAdd = findViewById(R.id.btnReturnAdd);

        btnSelectFile.setOnClickListener(v -> openFilePicker());
        btnReturnAdd.setOnClickListener(v -> finish());
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(Intent.createChooser(intent, "Choose File"), PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null && data.getData() != null) {

                selectedFileUri = data.getData();

                Intent resultIntent = new Intent();
                resultIntent.setData(selectedFileUri);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        }
    }
}
