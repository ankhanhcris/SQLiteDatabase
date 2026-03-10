package com.example.sqlitedatabase;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNote;
    private Button buttonSave;
    private ListView listViewNotes;
    private DatabaseHandler dbHandler;
    private ArrayAdapter<String> adapter;
    private List<String> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNote = findViewById(R.id.editTextNote);
        buttonSave = findViewById(R.id.buttonSave);
        listViewNotes = findViewById(R.id.listViewNotes);
        dbHandler = new DatabaseHandler(this);

        refreshNotesList();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noteText = editTextNote.getText().toString().trim();
                if (!noteText.isEmpty()) {
                    dbHandler.addNote(noteText);
                    editTextNote.setText("");
                    refreshNotesList();
                    Toast.makeText(MainActivity.this, "Đã lưu ghi chú", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập ghi chú", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listViewNotes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String noteToDelete = notesList.get(position);
                dbHandler.deleteNote(noteToDelete);
                refreshNotesList();
                Toast.makeText(MainActivity.this, "Đã xóa ghi chú", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void refreshNotesList() {
        notesList = dbHandler.getAllNotes();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesList);
        listViewNotes.setAdapter(adapter);
    }
}
