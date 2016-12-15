package com.raki.cociocompany2.myapp2;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;


public class MainActivity1 extends Activity {

    //String for shared preferences for saving data
    public static final String SAVE = "savedData";
    //Is the amount of koala points
    public static double koalaP;
    //Default worker speed
    public static int kppm = 3000;
    // Adds more koala points per worker over time
    public static float addHarder = 0;
    //Amount of workers + 1
    public static int koalaWorker = 1;
    //Cost of worker
    public static int cost = 10;
    //Cost of a raise
    public static int cost2 = 50;
    //Cost of a duck
    public static int cost3 = 50;
    //Counter for amount of raises
    public static int counter = 1;
    //Counter for when the raise button should go away
    public static int c = 0;
    public static int duckNum = 0;
    public static int raiseNum = 0;
    //Have you upgraded ducks to duck workers?
    public static boolean DuckWorker = false;
    public static double totalPoints;

    boolean muteOn;
    static MediaPlayer player;

    AreYouSure fragment1 = new AreYouSure();

@Override
public void onCreate (Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.menuscreen);

    //Start intent service
    Intent intentService = new Intent(this,GameService.class);
    startService(intentService);

    LoadSharedPref();

   player = MediaPlayer.create(this, R.raw.background);
   player.setLooping(true);
}

    public void toggled(View view){
        muteOn = ((ToggleButton) view).isChecked();
        if(muteOn){
            player.setVolume(0,0);
        }
        else{
            player.setVolume(0,1);
        }
    }



public void jumpToPage(View page2) {
    Intent Intent1 = new Intent(this, Game.class);
    startActivity(Intent1);
    overridePendingTransition(R.animator.fadein, R.animator.fadeout);
}

public void jumpToInstructions(View Instruct){
    Intent Intent2 = new Intent(this, Instructions.class);
    startActivity(Intent2);
    overridePendingTransition(R.animator.fadein, R.animator.fadeout);
}

public void gotoHighscore(View HS){
    Intent Intent3 = new Intent(this, Highscore.class);
    startActivity(Intent3);
    overridePendingTransition(R.animator.fadein, R.animator.fadeout);
}

public void onDestroy(){
    super.onDestroy();
}

public void onResume(){
    super.onResume();
}

public void onStop(){
    super.onStop();
}

//Erase data button
public void EraseData(View view){

        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentParentViewGroup, fragment1)
                .commit();
    //setting background dim when showing fragment
    RelativeLayout backgroundDimLayout2 = (RelativeLayout) findViewById(R.id.backgroundDimage2);
    backgroundDimLayout2.setVisibility(View.VISIBLE);
    }

    public void NOerase(View view){
        getFragmentManager().beginTransaction()
                .remove(fragment1)
                .commit();
        //setting background dim when showing fragment
        RelativeLayout backgroundDimLayout2 = (RelativeLayout) findViewById(R.id.backgroundDimage2);
        backgroundDimLayout2.setVisibility(View.GONE);
    }

    public void YESerase(View view){
        koalaP = 0;

        totalPoints = 0;
        koalaWorker = 1;
        addHarder = 0;
        kppm = 3000;
        counter = 1;
        cost = 10;
        cost2 = 50;
        cost3 = 50;
        c = 0;
        duckNum = 0;
        raiseNum = 0;
        DuckWorker = false;

        Toast.makeText(this, "Data erased", Toast.LENGTH_LONG).show();

        getFragmentManager().beginTransaction()
                .remove(fragment1)
                .commit();
        //setting background dim when showing fragment
        RelativeLayout backgroundDimLayout2 = (RelativeLayout) findViewById(R.id.backgroundDimage2);
        backgroundDimLayout2.setVisibility(View.GONE);
    }

    public void BlackFragmentRemover(View view){
        getFragmentManager().beginTransaction()
                .remove(fragment1)
                .commit();
        //setting background dim when showing fragment
        RelativeLayout backgroundDimLayout2 = (RelativeLayout) findViewById(R.id.backgroundDimage2);
        backgroundDimLayout2.setVisibility(View.GONE);
    }

    //Load shared preferences method
    public void LoadSharedPref(){
        // Loads the saved score
        SharedPreferences loadGame = getSharedPreferences(MainActivity1.SAVE, 0);
        MainActivity1.addHarder = loadGame.getFloat("savedHarder", 0);
        MainActivity1.koalaWorker = loadGame.getInt("savedWorker", 1);
        MainActivity1.cost = loadGame.getInt("savedCost", 10);
        MainActivity1.cost2 = loadGame.getInt("savedCost2", 50);
        MainActivity1.cost3 = loadGame.getInt("savedCost3", 50);
        MainActivity1.kppm = loadGame.getInt("savedPerM", 3000);
        MainActivity1.counter = loadGame.getInt("savedCounter", 1);
        MainActivity1.duckNum = loadGame.getInt("savedDuckNum", 0);
        MainActivity1.raiseNum = loadGame.getInt("savedRaiseNum", 0);
        MainActivity1.koalaP = Double.longBitsToDouble(loadGame.getLong("savedKP", 0));
        MainActivity1.totalPoints = Double.longBitsToDouble(loadGame.getLong("savedTP", 0));
        MainActivity1.c = loadGame.getInt("savedC", 0);
        MainActivity1.DuckWorker = loadGame.getBoolean("savedDuckWorker",false);
    }

    public void ToHighscores(View view){
        Intent intentScore = new Intent(this, Highscore.class);
        startActivity(intentScore);
    }

}
