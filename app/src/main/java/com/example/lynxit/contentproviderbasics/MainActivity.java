package com.example.lynxit.contentproviderbasics;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String URL="content://com.example.lynxit.contentproviderbasics.College/students";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onClickAddName(View view)
    {
        //Add a new Student record
        ContentValues values = new ContentValues();

        values.put(StudentsProvider.NAME,
                ((EditText)findViewById(R.id.editText2)).getText().toString());
        values.put(StudentsProvider.GRADE,
                ((EditText)findViewById(R.id.editText3)).getText().toString());

        Uri uri = getContentResolver().insert(StudentsProvider.CONTENT_URI,values);

        Toast.makeText(getBaseContext(),uri.toString(),Toast.LENGTH_LONG).show();
    }

    public void onClickRetrieveStudents(View view){


        Uri students = Uri.parse(URL);
        Cursor cursor = getContentResolver().query(students, null, null, null, "name");

        if(cursor.moveToFirst()){
            do{
                Toast.makeText(this,
                        cursor.getString(cursor.getColumnIndex(StudentsProvider._ID))+
                        ","+cursor.getString(cursor.getColumnIndex(StudentsProvider.NAME))+
                        ","+cursor.getString(cursor.getColumnIndex(StudentsProvider.GRADE)),
                Toast.LENGTH_SHORT).show();
            }while(cursor.moveToNext());
        }

    }

    public void onClickDeleteStudent(View view)
    {
        //Delete a student record
        Uri students = Uri.parse(URL);
        String abc=  ((EditText)findViewById(R.id.editText4)).getText().toString();
        int recordsDeleted = getContentResolver().delete(students, "name=?", new String[]{abc});

        if(recordsDeleted>0){
            Toast.makeText(this,"Record deleted successfully",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"Record does not exists  ",Toast.LENGTH_SHORT).show();
        }


    }

}
