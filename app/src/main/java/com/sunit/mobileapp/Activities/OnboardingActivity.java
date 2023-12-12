package com.sunit.mobileapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunit.mobileapp.R;

import me.relex.circleindicator.CircleIndicator;

public class OnboardingActivity extends AppCompatActivity {

    private TextView tvSkip;
    private ViewPager viewPager;
    private RelativeLayout layoutBottom;
    private CircleIndicator circleIndicator;
    private LinearLayout layoutNext;
    private ViewPaperAdapter viewPaperAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        initUI();

        viewPaperAdapter = new ViewPaperAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPaperAdapter);

        circleIndicator.setViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    tvSkip.setVisibility(View.GONE);
                    layoutBottom.setVisibility(View.GONE);
                } else {
                    tvSkip.setVisibility(View.VISIBLE);
                    layoutBottom.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initUI() {
        tvSkip = findViewById(R.id.tv_skip);
        viewPager = findViewById(R.id.view_paper);
        layoutBottom = findViewById(R.id.layout_bottom);
        circleIndicator = findViewById(R.id.circle_indicator);
        layoutNext = findViewById(R.id.layout_next);

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });

        layoutNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() < 2) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
            }
        });

    }
}