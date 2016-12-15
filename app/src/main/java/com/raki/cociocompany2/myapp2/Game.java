package com.raki.cociocompany2.myapp2;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.text.DecimalFormat;


public class Game extends Activity {


    //Sets the format for koala point counter
    static DecimalFormat decFor = new DecimalFormat(",##0.0");
    public String s = decFor.format(MainActivity1.koalaP);

    //String for shared preferences for saving data
    public static final String SAVE = "savedData";

    //Creates the handler for updating the screen
    private final Handler handler = new Handler();

    //Sets up shop fragment
    ShopFragment shopFragment = new ShopFragment();

    //Cast objects
    TextView duckButt,raiser,workers,raises,ducks,poi,hej;
    ImageView duckIcon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clickergame);

        duckButt = (TextView)findViewById(R.id.duckButton);
        raiser = (TextView)findViewById(R.id.button2);
        workers = (TextView)findViewById(R.id.hatNumb);
        raises = (TextView)findViewById(R.id.raiseNumb);
        ducks = (TextView)findViewById(R.id.duckNumb);
        poi = (TextView)findViewById(R.id.TextViewPoints);
        hej = (TextView)findViewById(R.id.buttonWorker);
        duckIcon = (ImageView)findViewById(R.id.duckIcon);

        //Starts the music
        MainActivity1.player.start();

        UpdatePrices();

        //Starts runnable that helps update the screen
        handler.post(runnable);

        //perform a screen update
        UpdateScreen();
    }


    //Update screen method
    public void UpdateScreen(){
        //Set format for this object
        s = decFor.format(MainActivity1.koalaP);
        poi.setText(s);

        if(MainActivity1.DuckWorker){
            duckIcon.setBackgroundResource(R.drawable.duckworkericon);
        }

        //You can only give a raise this amount of times
        if(MainActivity1.DuckWorker && MainActivity1.c <= 19){
            raiser.setVisibility(View.VISIBLE);
        }
        else if(MainActivity1.c > 19 || MainActivity1.duckNum < 5){
            raiser.setVisibility(View.INVISIBLE);
        }else{raiser.setVisibility(View.VISIBLE);}

        //Sets the numbers in the top
        workers.setText(Integer.toString(MainActivity1.koalaWorker-1));
        raises.setText(Integer.toString(MainActivity1.raiseNum));
        ducks.setText(Integer.toString(MainActivity1.duckNum));

        //Sets button text
        duckButt.setText("Buy a duck for " + MainActivity1.cost3);
        hej.setText("Worker cost: "+Integer.toString(MainActivity1.cost));
        raiser.setText("Give a raise: " + MainActivity1.cost2);
    }

    public void UpdateShopFragment(){
        getFragmentManager().beginTransaction().
        detach(shopFragment).
        attach(shopFragment).
        commit();
    }

    public void UpdatePrices(){
        MainActivity1.cost3 = 90*MainActivity1.duckNum+100;
    }

    //Koala head button. Method for clicking on the koala (1 + duck amount per click)
    public void increment(View view) {
        MainActivity1.koalaP += 1+(MainActivity1.duckNum*4);
        MainActivity1.totalPoints += 1+(MainActivity1.duckNum*4);
        UpdateScreen();
    }


    // Hire Koala worker
    public void hireWorker(View v)
    {
        if (MainActivity1.koalaP >= MainActivity1.cost){

            MainActivity1.koalaP -= MainActivity1.cost;
            MainActivity1.cost += 5*MainActivity1.koalaWorker+50;
            MainActivity1.koalaWorker++;
            MainActivity1.addHarder += 0.5;
        }
        UpdateScreen();
    }

    //Give a raise button
    public void raise(View v)
    {
        if (MainActivity1.koalaP >= MainActivity1.cost2){

            MainActivity1.c++;
            MainActivity1.koalaP -= MainActivity1.cost2;
            MainActivity1.cost2 += 25*MainActivity1.counter+100;
            MainActivity1.counter++;
            MainActivity1.kppm /= 1.2;
            MainActivity1.raiseNum++;
        }
        UpdateScreen();
    }

    //Buy duck button
    public void duckClick(View duck)
    {
        if(MainActivity1.koalaP >= MainActivity1.cost3){
            MainActivity1.koalaP -= MainActivity1.cost3;
            MainActivity1.duckNum++;
            UpdatePrices();
        }

        UpdateScreen();
    }

    public void onPause(){
        super.onPause();
    }

    public void onStop(){

        // Saves the score when the game is stopped
        super.onStop();
        MainActivity1.player.pause();
    }

    public void onResume() {
        super.onResume();
        UpdateScreen();
        MainActivity1.player.start();
    }

    public void onDestroy(){
        super.onDestroy();
        SavedSharedPref();
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            UpdateScreen();
            handler.postDelayed(runnable, MainActivity1.kppm);
        }
    };

    public void SavedSharedPref(){
        SharedPreferences saveGame = getSharedPreferences(SAVE, MODE_PRIVATE);
        SharedPreferences.Editor editor = saveGame.edit();
        editor.putInt("savedWorker", MainActivity1.koalaWorker);
        editor.putFloat("savedHarder", MainActivity1.addHarder);
        editor.putInt("savedCost", MainActivity1.cost);
        editor.putInt("savedCost2", MainActivity1.cost2);
        editor.putInt("savedCost3", MainActivity1.cost3);
        editor.putInt("savedPerM", MainActivity1.kppm);
        editor.putInt("savedCounter", MainActivity1.counter);
        editor.putInt("savedDuckNum", MainActivity1.duckNum);
        editor.putInt("savedRaiseNum", MainActivity1.raiseNum);
        editor.putLong("savedKP", Double.doubleToLongBits(MainActivity1.koalaP));
        editor.putLong("savedTP", Double.doubleToLongBits(MainActivity1.totalPoints));
        editor.putInt("savedC", MainActivity1.c);
        editor.putBoolean("savedDuckWorker",MainActivity1.DuckWorker);
        editor.apply();
    }

    // Everything shop below -----------------------------------------------------------------------

    //Open shop button
    public void OpenShop(View view){
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentParentViewGroup2, shopFragment)
                .commit();

        //setting background dim when showing popup
        RelativeLayout backgroundDimLayout = (RelativeLayout) findViewById(R.id.backgroundDimage);
        backgroundDimLayout.setVisibility(View.VISIBLE);
    }

    //Close shop button
    public void RemoveShop(View view){
        getFragmentManager().beginTransaction()
                .remove(shopFragment)
                .commit();

        //setting background dim when showing popup
        RelativeLayout backgroundDimLayout = (RelativeLayout) findViewById(R.id.backgroundDimage);
        backgroundDimLayout.setVisibility(View.GONE);
    }

    //Remove shop fragment when clicking outside
    public void ShopRemoveFragment(View view){
        getFragmentManager().beginTransaction()
                .remove(shopFragment)
                .commit();

        //setting background dim when showing popup
        RelativeLayout backgroundDimLayout = (RelativeLayout) findViewById(R.id.backgroundDimage);
        backgroundDimLayout.setVisibility(View.GONE);
    }

    //Upgrade ducks to workers button in shop
    public void UpgradeDucks(View view){


        if(MainActivity1.duckNum>=50 && MainActivity1.koalaP>=100000 && !MainActivity1.DuckWorker){
            MainActivity1.duckNum-=50;
            MainActivity1.koalaP-=100000;
            MainActivity1.DuckWorker = true;
            UpdateShopFragment();
            UpdatePrices();
            UpdateScreen();
        }
    }
}
