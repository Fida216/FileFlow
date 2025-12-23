package essths.li3.fileflow;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<File> fileList = new ArrayList<>();
    private ArrayList<File> filteredList = new ArrayList<>();

    private ListView listViewFiles;
    private Spinner spinnerCategory, spinnerImportant;

    private static final int PICK_FILE_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewFiles = findViewById(R.id.listViewFiles);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerImportant = findViewById(R.id.spinnerUrgent);

        Button btnAdd = findViewById(R.id.btnAddTask);
        Button btnModify = findViewById(R.id.btnModifyTask);
        Button btnDelete = findViewById(R.id.btnDeleteTask);

        updateCategorySpinner();
        setupImportantSpinner();
        applyFilter();

        btnAdd.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, AddFileActivity.class);
            startActivityForResult(i, PICK_FILE_CODE);
        });

        btnModify.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ModifyFileActivity.class)));

        btnDelete.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, DeleteFileActivity.class)));

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                applyFilter();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerImportant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                applyFilter();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    @Override
    protected void onActivityResult(int req, int res, Intent data) {
        super.onActivityResult(req, res, data);

        if (req == PICK_FILE_CODE && res == RESULT_OK) {

            Uri uri = data.getData();
            if (uri != null) {

                String name = uri.getLastPathSegment(); // simple name display

                File f = new File(name, "Default", false, uri);

                fileList.add(f);
                applyFilter();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCategorySpinner();
        applyFilter();
    }

    private void applyFilter() {
        filteredList.clear();

        String selectedCategory = spinnerCategory.getSelectedItem().toString();
        String selectedImportant = spinnerImportant.getSelectedItem().toString();

        for (File f : fileList) {

            boolean matchCategory =
                    selectedCategory.equals("All")
                            || f.getCategory().equalsIgnoreCase(selectedCategory);

            boolean matchImportance =
                    selectedImportant.equals("All")
                            || (selectedImportant.equals("Important") && f.isImportant())
                            || (selectedImportant.equals("Not important") && !f.isImportant());

            if (matchCategory && matchImportance) {
                filteredList.add(f);
            }
        }

        ArrayAdapter<File> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                filteredList
        );

        listViewFiles.setAdapter(adapter);
    }

    private void updateCategorySpinner() {
        HashSet<String> categories = new HashSet<>();
        categories.add("All");

        for (File f : fileList) {
            categories.add(f.getCategory());
        }

        ArrayList<String> list = new ArrayList<>(categories);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                list
        );

        spinnerCategory.setAdapter(adapter);
    }

    private void setupImportantSpinner() {
        ArrayList<String> options = new ArrayList<>();
        options.add("All");
        options.add("Important");
        options.add("Not important");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                options
        );

        spinnerImportant.setAdapter(adapter);
    }
}
