package me.shetj.transition.transitionexample.scene;

import android.os.Bundle;
import android.transition.ArcMotion;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.view.View;

import me.shetj.transition.R;


public class SceneChangeBoundsActivity extends BaseSceneActivity {

    private boolean isArc = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_change_bounds);
        initToolbar();
        initScene(R.id.scene_root,R.layout.scene_1_changebounds,R.layout.scene_2_changebounds);

    }

    @Override
    Transition getTransition() {
        ChangeBounds  tansition =  new ChangeBounds();
        if (isArc) {
            ArcMotion arcMotion = new ArcMotion();
            arcMotion.setMinimumVerticalAngle(50);
            arcMotion.setMinimumHorizontalAngle(80);
            tansition.setPathMotion(arcMotion);
        }
        return tansition;
    }

    public void arcPath(View view){
        isArc = !isArc;
    }

}
