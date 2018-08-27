package me.shetj.transition.transitionexample.scene;

import android.graphics.Rect;
import android.os.Bundle;
import android.transition.ChangeClipBounds;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import me.shetj.transition.R;


public class SceneChangeClipBoundsActivity extends BaseSceneActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_change_clip_bounds);
        initToolbar();
        ViewGroup sceneRoot= (ViewGroup) findViewById(R.id.scene_root);


        View inflate = LayoutInflater.from(this).inflate(R.layout.scene_1_changeclipbounds, null);
        View inflate2 = LayoutInflater.from(this).inflate(R.layout.scene_2_changeclipbounds, null);

         ImageView imageView= (ImageView) inflate.findViewById(R.id.cutegirl);
         ImageView imageView2= (ImageView) inflate2.findViewById(R.id.cutegirl);

        imageView.setClipBounds(new Rect(0,0,200,200));
        imageView2.setClipBounds(new Rect(200,200,400,400));

        scene1=new Scene(sceneRoot,inflate);
        scene2=new Scene(sceneRoot,inflate2);
        TransitionManager.go(scene1);
    }

    @Override
    Transition getTransition() {
        return new ChangeClipBounds();
    }
}
