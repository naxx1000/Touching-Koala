package com.raki.cociocompany2.myapp2;


import android.app.IntentService;
import android.content.Intent;

public class GameService extends IntentService {

    public GameService(){
        super("KoalaService");
    }

    @Override
    protected void onHandleIntent(Intent workIntent){
        synchronized (this){

            while(true){
                if(!MainActivity1.DuckWorker) {
                    MainActivity1.koalaP += MainActivity1.addHarder;
                    MainActivity1.totalPoints += MainActivity1.addHarder;
                }else{
                    MainActivity1.koalaP += (MainActivity1.addHarder + (1+MainActivity1.duckNum));
                    MainActivity1.totalPoints += (MainActivity1.addHarder + (1+MainActivity1.duckNum));
                }
                try {
                    wait(MainActivity1.kppm);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }


}
