package me.shetj.transition.transitionexample.scene;

import android.os.Bundle;
import android.transition.ChangeTransform;
import android.transition.Transition;

import me.shetj.transition.R;


public class SceneChangeTransformActivity extends BaseSceneActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_change_transform);
        initToolbar();
        initScene(R.id.scene_root,R.layout.scene_1_changetransform,R.layout.scene_2_changetransform);
    }

    @Override
    Transition getTransition() {
//        return new ChangeClipBounds();
        return new ChangeTransform();
    }


}
