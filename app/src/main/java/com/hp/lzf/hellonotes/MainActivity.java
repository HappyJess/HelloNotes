package com.hp.lzf.hellonotes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button text;
    private Button img;
    private Button video;
    private ListView lv;
    private Intent i;
    private MyAdapter adapter;
    private NotesDB notesDB;
    private SQLiteDatabase dbReader;
    private Cursor cursor;

    /*private NotesDB notesDB;
        private SQLiteDatabase dbWriter;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*notesDB = new NotesDB(this);
        dbWriter = notesDB.getWritableDatabase();
        addDB();*/
        initView();
    }

    private void initView() {

        lv = (ListView) findViewById(R.id.list);
        text = (Button) findViewById(R.id.text);
        img = (Button) findViewById(R.id.img);
        video = (Button) findViewById(R.id.video);

        text.setOnClickListener(this);
        img.setOnClickListener(this);
        video.setOnClickListener(this);

        notesDB = new NotesDB(this);
        dbReader = notesDB.getReadableDatabase();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                Intent i = new Intent(MainActivity.this,SelectActivity.class);
                i.putExtra(NotesDB.ID,cursor.getInt(cursor.getColumnIndex(NotesDB.ID)));
                i.putExtra(NotesDB.CONTENT,cursor.getString(cursor.getColumnIndex(NotesDB.CONTENT)));
                i.putExtra(NotesDB.TIME,cursor.getString(cursor.getColumnIndex(NotesDB.TIME)));
                i.putExtra(NotesDB.PATH,cursor.getString(cursor.getColumnIndex(NotesDB.PATH)));
                i.putExtra(NotesDB.VIDEO,cursor.getString(cursor.getColumnIndex(NotesDB.VIDEO)));
                startActivity(i);
            }
        });

    }

    @Override
    public void onClick(View v) {
        i = new Intent(this, AddContent.class);
        switch (v.getId()) {
            case R.id.text:
                i.putExtra("flag", "1");
                startActivity(i);
                break;
            case R.id.img:
                i.putExtra("flag", "2");
                startActivity(i);
                break;
            case R.id.video:
                i.putExtra("flag", "3");
                startActivity(i);
                break;
        }
    }

    public void selectDB(){
        cursor = dbReader.query(NotesDB.TABLE_NAME,null,null,null,null,
                null,null);
        adapter = new MyAdapter(this,cursor);
        lv.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        selectDB();
    }

    /*public void addDB(){
        ContentValues cv = new ContentValues();
        cv.put(NotesDB.CONTENT,"Hello");
        cv.put(NotesDB.TIME,getTime());
        dbWriter.insert(NotesDB.TAVLE_NAME,null,cv);
    }

    public String getTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date cueDate = new Date();
        String str = format.format(cueDate);
        return str;
    }*/
}
