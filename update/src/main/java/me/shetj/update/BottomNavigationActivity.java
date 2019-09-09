package me.shetj.update;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BottomNavigationActivity extends AppCompatActivity {

	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
					= new BottomNavigationView.OnNavigationItemSelectedListener() {

		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item) {
			int id = item.getItemId();
			if (id == R.id.navigation_home) {
				viewpage2.setCurrentItem(0,false);
				return true;
			} else if (id == R.id.navigation_dashboard) {
				viewpage2.setCurrentItem(1);
				return true;
			} else if (id == R.id.navigation_notifications) {
				viewpage2.setCurrentItem(2);
				return true;
			}
			return false;
		}
	};
	private ViewPager2 viewpage2;
	private 	BottomNavigationView navigation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bottom_navigation);


		 viewpage2 = findViewById(R.id.viewpage2);

		setViewPage(viewpage2);

		  navigation = findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
	}

	private void setViewPage(ViewPager2 viewpage2) {
		viewpage2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
		viewpage2.setAdapter(new AFragmentStateAdapter(this));
		viewpage2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				super.onPageScrolled(position, positionOffset, positionOffsetPixels);
			}

			@Override
			public void onPageSelected(int position) {
				super.onPageSelected(position);
				navigation.setSelectedItemId(position);
			}

			@Override
			public void onPageScrollStateChanged(int state) {
				super.onPageScrollStateChanged(state);
			}
		});
	}
}
