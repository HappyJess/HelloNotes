package com.hp.lzf.hellonotes;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class SelectActivity extends AppCompatActivity implements View.OnClickListener {

    private Button s_delete, s_back;
    private ImageView s_img;
    private VideoView s_video;
    private TextView s_tv;
    private NotesDB notesDB;
    private SQLiteDatabase dbWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        // System.out.println(getIntent().getIntExtra(NotesDB.ID, 0));
        s_delete = (Button) findViewById(R.id.s_delete);
        s_back = (Button) findViewById(R.id.s_back);
        s_img = (ImageView) findViewById(R.id.s_img);
        s_video = (VideoView) findViewById(R.id.s_video);
        s_tv = (TextView) findViewById(R.id.s_tv);
        notesDB = new NotesDB(this);
        dbWriter = notesDB.getWritableDatabase();
        s_back.setOnClickListener(this);
        s_delete.setOnClickListener(this);

        if (getIntent().getStringExtra(NotesDB.PATH).equals("null")) {
            s_img.setVisibility(View.GONE);
        } else {
            s_img.setVisibility(View.VISIBLE);
            Bitmap bitmap = BitmapFactory.decodeFile(getIntent().getStringExtra(
                    NotesDB.PATH));
            s_img.setImageBitmap(CompressImage.comp(bitmap));
        }
        if (getIntent().getStringExtra(NotesDB.VIDEO).equals("null")) {
            s_video.setVisibility(View.GONE);
        } else {
            s_video.setVisibility(View.VISIBLE);
            s_video.setVideoURI(Uri
                    .parse(getIntent().getStringExtra(NotesDB.VIDEO)));
            s_video.start();
        }
        s_tv.setText(getIntent().getStringExtra(NotesDB.CONTENT));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.s_delete:
                deleteDate();
                finish();
                break;

            case R.id.s_back:
                finish();
                break;
        }
    }

    public void deleteDate() {
        dbWriter.delete(NotesDB.TABLE_NAME,
                " id=" + getIntent().getIntExtra(NotesDB.ID, 0), null);
    }
}
