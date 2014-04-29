package hu.lnzs.android1wire;

import hu.lnzs.android1wire.data.ErzekeloData;
import hu.lnzs.android1wire.data.HostData;
import hu.lnzs.android1wire.data.VezerloData;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashActivity extends Activity {

    private final int SPLASH_DISPLAY_LENGHT = 2000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);
		if (HostData.loadDataFromFile(this) &&	ErzekeloData.loadDataFromFile(this) && VezerloData.loadDataFromFile(this)){
			Log.w("load", "betöltés sikeres");
		} else{
			Log.w("load", "betöltés sikertelen");
		}

        /* New Handler to start the Menu-Activity 
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this,HomeActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
    }
}