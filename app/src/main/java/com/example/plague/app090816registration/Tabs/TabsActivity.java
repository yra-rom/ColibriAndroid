package com.example.plague.app090816registration.Tabs;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.plague.app090816registration.Messaging.Receiver;
import com.example.plague.app090816registration.Messaging.clients.Status;
import com.example.plague.app090816registration.R;
import com.example.plague.app090816registration.Tabs.ChatsList.TwoFragment;
import com.example.plague.app090816registration.Tabs.FriendsList.OneFragment;
import com.example.plague.app090816registration.connection_defaults.Constants.SendKeys;

import java.util.ArrayList;
import java.util.List;

public class TabsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private MessageHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Colibri");
        setContentView(R.layout.tabs_main);

        //В майбутньому винести в сервіс
        startReceivingMessages();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        //Without back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void startReceivingMessages() {
        handler = new MessageHandler();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String email =  prefs.getString(SendKeys.EMAIL, "");
//        new Receiver(getIntent().getStringExtra(SendKeys.EMAIL), handler);
        new Receiver(email, handler);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        OneFragment one = new OneFragment();
        one.setHandler(handler);
        TwoFragment two = new TwoFragment();
        two.setHandler(handler);
        adapter.addFragment(one, "Friends");
        adapter.addFragment(two, "Chats");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        //override not to get back to login activity
    }

    @Override
    protected void onResume() {
        super.onResume();
        Message msg = handler.obtainMessage(Status.CH_WHERE_SHOW, Status.IN_SERVICE, 0, null);
        handler.sendMessage(msg);
    }
}