package mehwish.ghazi.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import mehwish.ghazi.R;
import mehwish.ghazi.fragment.FragmentDrawer;
import mehwish.ghazi.fragment.FriendsListFragment;
import mehwish.ghazi.fragment.FriendsRequestFragment;
import mehwish.ghazi.fragment.HomeFragment;
import mehwish.ghazi.fragment.TrackFriendListFragment;

public class HomeActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = HomeActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportFragmentManager().beginTransaction().add(R.id.container_body, new HomeFragment()).commit();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        //displayView(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_search){
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);

        if (getSupportFragmentManager().getBackStackEntryCount() > 1){
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = getString(R.string.title_my_current_location);
                break;
            case 1:
                fragment = new FriendsListFragment();
                title = getString(R.string.title_friend_list);
                break;
            case 2:
                fragment = new FriendsRequestFragment();
                title = getString(R.string.title_friend_requests);
                break;
            case 3:
                fragment = new TrackFriendListFragment();
                title = getString(R.string.title_friends_location);
                break;
            case 4:
                Toast.makeText(HomeActivity.this, "TODO Analyse!", Toast.LENGTH_SHORT).show();
                title = getString(R.string.title_analyse_friend);
                break;
            case 5:
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
                sp.edit().putBoolean("isUserAlreadyLoggedIn",false).apply();
                startActivity(new Intent(this, LandingActivity.class));
                finish();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
            setActionBarTitle(title);
        }
    }
    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}