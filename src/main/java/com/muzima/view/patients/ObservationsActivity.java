package com.muzima.view.patients;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.SearchView;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.muzima.R;
import com.muzima.adapters.observations.ObservationsPagerAdapter;
import com.muzima.api.model.Patient;
import com.muzima.utils.Fonts;
import com.muzima.view.BroadcastListenerActivity;
import com.muzima.view.customViews.PagerSlidingTabStrip;

public class ObservationsActivity extends BroadcastListenerActivity {

    public boolean quickSearch = false;
    private ViewPager viewPager;
    private ObservationsPagerAdapter cohortPagerAdapter;
    private PagerSlidingTabStrip pagerTabsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_observations);

        // Show the Up button in the action bar.
        setupActionBar();
        initPager();
        initPagerIndicator();
    }

    private void initPagerIndicator() {
        pagerTabsLayout = (PagerSlidingTabStrip) findViewById(R.id.pager_indicator);
        pagerTabsLayout.setTextColor(Color.WHITE);
        pagerTabsLayout.setTextSize((int) getResources().getDimension(R.dimen.pager_indicator_text_size));
        pagerTabsLayout.setSelectedTextColor(getResources().getColor(R.color.tab_indicator));
        pagerTabsLayout.setTypeface(Fonts.roboto_medium(this), -1);
        pagerTabsLayout.setViewPager(viewPager);
        viewPager.setCurrentItem(0);
        pagerTabsLayout.markCurrentSelected(0);
    }

    private void initPager() {
        viewPager = (ViewPager) findViewById(R.id.pager);
        cohortPagerAdapter = new ObservationsPagerAdapter(getApplicationContext(), getSupportFragmentManager());
        cohortPagerAdapter.initPagerViews();
        viewPager.setAdapter(cohortPagerAdapter);
    }

    /**
     * Set up the {@link android.app.ActionBar}.
     */
    private void setupActionBar() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Patient patient = (Patient) getIntent().getSerializableExtra(PatientSummaryActivity.PATIENT);
        getActionBar().setTitle(patient.getSummary());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.observation_list, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search)
                .getActionView();
        searchView.setQueryHint("Search observations");
        searchView.setOnQueryTextListener(cohortPagerAdapter);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
