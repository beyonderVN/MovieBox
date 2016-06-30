package com.vinhsang.demo.v_chat.activity.main;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.flyco.systembar.SystemBarHelper;
import com.vinhsang.demo.v_chat.activity.login.BeautifullLoginActivity;
import com.vinhsang.demo.v_chat.activity.main.fragment.MessagesFragment3;
import com.vinhsang.demo.v_chat.application.ChatApplication;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class VChatActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    private static final String TAG = "VChatActivity";
    @Bind(com.vinhsang.demo.R.id.tabs) TabLayout mTabs;
    @Bind(com.vinhsang.demo.R.id.viewpager) ViewPager mViewpager;
    @Bind(com.vinhsang.demo.R.id.appbar) AppBarLayout mAppbar;
    @Bind(com.vinhsang.demo.R.id.collapsing_toolbar) CollapsingToolbarLayout mCollapsingToolbar;
    @Bind(com.vinhsang.demo.R.id.toolbar) Toolbar mToolbar;
    @Bind(com.vinhsang.demo.R.id.nickname) TextView mNickname;
    @Bind(com.vinhsang.demo.R.id.avatar) CircleImageView circleImageView;
//    @Bind(R.id.nav_view)
//    NavigationView mNavigationView;
    @Bind(com.vinhsang.demo.R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    //Socket
    private static final int REQUEST_LOGIN = 0;

    //
    //private ChatService chatService;
    private boolean binded=false;
    SimpleViewPagerAdapter mAdapter;
    MessagesFragment3 messagesFragment;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.vinhsang.demo.R.layout.activity_vchat);
        ButterKnife.bind(this);
        //startService(new Intent(this, ChatService.class));
        //mNavigationView.setNavigationItemSelectedListener(this);
        int color = getResources().getColor(com.vinhsang.demo.R.color.colorPrimary);
        app = (ChatApplication) this.getApplication();
        //SystemBarHelper.tintStatusBarForDrawer(this, mDrawerLayout, color);
        SystemBarHelper.tintStatusBar(this,color);
        //SystemBarHelper.setPadding(this, mNavigationView.getHeaderView(0));
        setSupportActionBar(mToolbar);

//        mToolbar.setNavigationIcon(R.drawable.ic_menu_white_36dp);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toggleDrawer();
//            }
//        });
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        circleImageView.bringToFront();

        //SystemBarHelper.immersiveStatusBar(this, 0);
        //SystemBarHelper.setHeightAndPadding(this, mToolbar);

        mAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                boolean showTitle = mCollapsingToolbar.getHeight() + verticalOffset <= mToolbar.getHeight();
                boolean showTitle = mCollapsingToolbar.getHeight() + verticalOffset <= mToolbar.getHeight() * 2;
                mNickname.setVisibility(showTitle ? View.VISIBLE : View.GONE);
                circleImageView.setVisibility(showTitle ? View.GONE : View.VISIBLE);

            }
        });

        SimpleViewPagerAdapter mAdapter = new SimpleViewPagerAdapter(getSupportFragmentManager());
        //adapter.addFragment(MainFragment.newInstance(), "Chat");

        messagesFragment = MessagesFragment3.newInstance();


        mAdapter.addFragment( messagesFragment, "Messages");

        mViewpager.setAdapter(mAdapter);
        mTabs.setupWithViewPager(mViewpager);



        startSignIn();
    }

    private void startSignIn() {
        if(app.isOnSignIn()==true){
            getSupportActionBar().setTitle(app.user.getName());
            return;
        }
        if (ChatApplication.onSignIn == false) {
            Intent intent = new Intent(this, BeautifullLoginActivity.class);
            startActivityForResult(intent, REQUEST_LOGIN);

        }

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.vinhsang.demo.R.menu.main, menu);
        return true;
    }
    ChatApplication app;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.vinhsang.demo.R.id.action_settings) {
            return true;
        }
        if (id == com.vinhsang.demo.R.id.action_logout) {
            app.logout();
            finish();
            startActivity(getIntent());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")


    public void toggleDrawer() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == com.vinhsang.demo.R.id.nav_camera) {
            // Handle the camera action
        } else if (id == com.vinhsang.demo.R.id.nav_gallery) {

        } else if (id == com.vinhsang.demo.R.id.nav_slideshow) {

        } else if (id == com.vinhsang.demo.R.id.nav_manage) {

        } else if (id == com.vinhsang.demo.R.id.nav_share) {

        } else if (id == com.vinhsang.demo.R.id.nav_send) {

        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    class SimpleViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public SimpleViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: ");
        getSupportActionBar().setTitle(app.user.getName());
//        mNameNV.setText(app.user.getName());
//        mEmailNV.setText(app.user.getName()+"@vinhsang.vn");
        if (RESULT_OK != resultCode) {
            finish();
            return;
        }


    }


}
