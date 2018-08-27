package me.shetj.transition.transitionexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionInflater;
import android.view.View;

import me.shetj.transition.R;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        getWindow().setExitTransition(TransitionInflater.from(this).inflateTransition(R.transition.slide));
//        getWindow().setExitTransition(new Fade());
        //未设置setReenterTransition()默认和setExitTransition一样
    }

    public void goContentTransitions(View view){
        Intent intent = new Intent(this, ContentTransitionsActivity.class);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        startActivity(intent,activityOptionsCompat.toBundle());
    }



    public void goScene(View view){
        startActivity(new Intent(this,SceneActivity.class));
    }
    public void goBeginDelayed(View view){
        startActivity(new Intent(this,BeginDelayedActivity.class));
    }

    public void goContent_and_Shared(View view){
        startActivity(new Intent(this,ContentAndSharedTransitionActivity.class));
    }




}
