package uk.co.imobilize.parseexample;

import android.app.Application;

import com.parse.Parse;

import uk.co.imobilize.parseexample.Utils.Constans;

/**
 * Created by javiergonzalezcabezas on 9/6/15.
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, Constans.PARSE_APPLICATION_ID, Constans.PARSE_CLIENT_KEY);


    }
}
