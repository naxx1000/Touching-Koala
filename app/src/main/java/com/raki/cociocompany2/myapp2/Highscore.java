package com.raki.cociocompany2.myapp2;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class Highscore extends Activity {

    //array list for score names
    List<String> arrayListName;
    //array list for scores
    List<String> arrayListScore;

    Double yourScore = MainActivity1.totalPoints;
    EditText editTextName;
    String yourName;

    private List<HSDataProvider> myDataset = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    //private TextView loading;
    private Firebase rootRef;

protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Firebase.setAndroidContext(this);
    setContentView(R.layout.highscore);
    rootRef = new Firebase("https://touching-koala.firebaseio.com/Highscore/NamesAndScores");
    editTextName = (EditText)findViewById(R.id.editTextName);

    arrayListName = new ArrayList<>();
    arrayListScore = new ArrayList<>();

    mRecyclerView = (RecyclerView)findViewById(R.id.scoreView);
    //loading = (TextView)findViewById(R.id.loading);
    mRecyclerView.setHasFixedSize(true);

    mAdapter = new MyAdapter(myDataset);
    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
    mRecyclerView.setLayoutManager(mLayoutManager);
    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    mRecyclerView.setAdapter(mAdapter);

    GetFirebaseData(rootRef);
    }

    public void submitHigh(View view){
        yourScore = MainActivity1.totalPoints;
        try{
            yourName = editTextName.getText().toString();
            if(!yourName.equals("")){
                HSPost post = new HSPost(yourName,yourScore);
                rootRef.push().setValue(post);
                Toast.makeText(Highscore.this, "Your score is submitted!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(Highscore.this, "Your name is empty", Toast.LENGTH_SHORT).show();
            }
        }catch (NumberFormatException e){
            Toast.makeText(Highscore.this, "Your name is empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void GetFirebaseData(Firebase rootRef){
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                arrayListName.clear();
                arrayListScore.clear();
                myDataset.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    HSPost post = postSnapshot.getValue(HSPost.class);
                    HSDataProvider dataProvider = new HSDataProvider(post.scoreName, post.yourScore);
                    myDataset.add(dataProvider);
                    arrayListName.add(post.getScoreName());
                    arrayListScore.add(post.getYourScore().toString());
                }
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: "+ firebaseError.getMessage() + "\n"+firebaseError.getDetails());
            }
        });
    }

    public static class HSPost {
        private String scoreName;
        private Double yourScore;
        public HSPost(){
        }

        public HSPost(String scoreName, Double yourScore){
            this.scoreName = scoreName;
            this.yourScore = yourScore;
        }

        //getters
        public String getScoreName(){
            return scoreName;
        }
        public Double getYourScore(){
            return yourScore;
        }
    }
}
