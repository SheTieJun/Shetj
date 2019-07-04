package me.shetj.transition.transitionexample;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import me.shetj.transition.R;
import me.shetj.transition.transitionexample.scene.SceneChangeBoundsActivity;
import me.shetj.transition.transitionexample.scene.SceneChangeClipBoundsActivity;
import me.shetj.transition.transitionexample.scene.SceneChangeImageTransformActivity;
import me.shetj.transition.transitionexample.scene.SceneChangeTransformActivity;
import me.shetj.transition.transitionexample.scene.SceneFadeSlideExplodeActivity;

public class SceneActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene);
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void changeBounds(View view){
        startActivity(new Intent(this, SceneChangeBoundsActivity.class));
    }
    public void changeTransform(View view){
        startActivity(new Intent(this, SceneChangeTransformActivity.class));
    }
    public void changeClipBounds(View view){
        startActivity(new Intent(this, SceneChangeClipBoundsActivity.class));
    }
    public void changeImageTransform(View view){
        startActivity(new Intent(this, SceneChangeImageTransformActivity.class));
    }
    public void fade(View view){
        startActivity(new Intent(this, SceneFadeSlideExplodeActivity.class));
    }

}
