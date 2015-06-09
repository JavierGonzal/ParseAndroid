package uk.co.imobilize.parseexample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import org.apache.http.ParseException;

import java.util.List;

import uk.co.imobilize.parseexample.Utils.Utils;


public class MainActivity extends ActionBarActivity implements  View.OnClickListener{

    private TextView mTextView;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonGet = (Button) findViewById(R.id.activity_main_button_get);
        mTextView = (TextView) findViewById(R.id.activity_main_textView);
        Button buttonUpdate = (Button) findViewById(R.id.activity_main_button_update);
        mEditText = (EditText) findViewById(R.id.activity_main_editText);

        buttonUpdate.setOnClickListener(this);

        buttonGet.setOnClickListener(this);



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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_main_button_get:
                if(Utils.isOnline(MainActivity.this)){
                    getData();
                }else{
                    Toast.makeText(MainActivity.this,R.string.error_retrofit,Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.activity_main_button_update:
                if(mEditText.getText().toString().isEmpty())
                    mEditText.setError(getString(R.string.empty_field));
                else{
                    if(Utils.isOnline(MainActivity.this)){
                        updateData();
                    }else{
                        Toast.makeText(MainActivity.this,R.string.error_retrofit,Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public void updateData(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameScore");
        query.getInBackground("aaQjwRnDUx", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject gameScore, com.parse.ParseException e) {

                if (e == null) {
                    // Now let's update it with some new data. In this case, only cheatMode and score
                    // will get sent to the Parse Cloud. playerName hasn't changed.

                    gameScore.put("score", 1338);
                    gameScore.put("cheatMode", true);
                    gameScore.put("playerName", mEditText.getText().toString());
                    gameScore.saveInBackground();
                }
            }
        });
    }

    public void getData(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameScore");
        query.getInBackground("aaQjwRnDUx", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject gameScore, com.parse.ParseException e) {
                if (e == null) {
                    // object will be your game score
                    Log.d("MainActivity",gameScore.toString());

                    int score = gameScore.getInt("score");
                    String playerName = gameScore.getString("playerName");
                    boolean cheatMode = gameScore.getBoolean("cheatMode");

                    mTextView.setText(playerName);
                } else {
                    // something went wrong
                }
            }
        });
    }
}
