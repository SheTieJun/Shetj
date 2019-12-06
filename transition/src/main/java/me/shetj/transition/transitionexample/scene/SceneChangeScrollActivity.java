package me.shetj.transition.transitionexample.scene;


import android.os.Build;
import android.os.Bundle;
import android.transition.ChangeScroll;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import me.shetj.transition.R;

public class SceneChangeScrollActivity extends BaseSceneActivity {

    private ViewGroup sceneRoot;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_change_scroll);
        initToolbar();
        sceneRoot= findViewById(R.id.scene_root);
        imageView= findViewById(R.id.cuteboy);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void change(View view) {
        TransitionManager.beginDelayedTransition(sceneRoot,getTransition());
        imageView.setScrollX(-100);
        imageView.setScrollY(-200);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    Transition getTransition() {
        return new ChangeScroll();
    }
}
