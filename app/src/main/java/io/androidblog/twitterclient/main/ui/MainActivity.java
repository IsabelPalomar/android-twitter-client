package io.androidblog.twitterclient.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.twitter.sdk.android.Twitter;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.androidblog.twitterclient.hashtags.HashtagsFragment;
import io.androidblog.twitterclient.images.ui.ImagesFragment;
import io.androidblog.twitterclient.login.ui.LoginActivity;
import io.androidblog.twitterclient.R;
import io.androidblog.twitterclient.main.ui.adapters.MainSectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.container)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupAdapter();

    }

    private void setupAdapter() {
        Fragment[] fragments  = new Fragment[]{ new ImagesFragment(), new HashtagsFragment()};
        String[] titles = new String[]{getString(R.string.main_header_images), getString(R.string.main_header_hashtags)};
        MainSectionsPagerAdapter adapter = new MainSectionsPagerAdapter(getSupportFragmentManager(),
                                                                        fragments, titles);
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_logout) {
            logout();
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        Twitter.logOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
