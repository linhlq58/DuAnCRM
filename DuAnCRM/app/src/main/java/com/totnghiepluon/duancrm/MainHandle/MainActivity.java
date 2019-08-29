package com.totnghiepluon.duancrm.MainHandle;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.totnghiepluon.duancrm.Base.StartApplication;
import com.totnghiepluon.duancrm.ContactData.ContactsActivity;
import com.totnghiepluon.duancrm.Customers.CustomersFragment;
import com.totnghiepluon.duancrm.Graph.GraphFragment;
import com.totnghiepluon.duancrm.Labels.LabelsFragment;
import com.totnghiepluon.duancrm.Leads.LeadsFragment;
import com.totnghiepluon.duancrm.R;
import com.totnghiepluon.duancrm.Tasks.TasksFragment;
import com.totnghiepluon.duancrm.Base.BaseActivity;
import com.totnghiepluon.duancrm.data.DatabaseHelper;
import com.totnghiepluon.duancrm.utils.Constants;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private DrawerLayout drawer;
    private TabLayout tabs;
    private ViewPager pager;
    private boolean isManager;
    private RelativeLayout btnMenu;
    private TextView tvTitle;
    private String user;
    private RelativeLayout btnManageAccount;
    private RelativeLayout btnImport;

    private static int PERMISSIONS_REQUEST_READ_CONTACTS = 1267;

    private DatabaseHelper db;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        drawer = findViewById(R.id.drawer_layout);
        tabs = findViewById(R.id.tabs);
        pager = findViewById(R.id.pager);
        btnMenu = findViewById(R.id.btn_menu);
        tvTitle = findViewById(R.id.tv_title);
        isManager = getIntent().getBooleanExtra(Constants.LOGIN, false);
        user = getIntent().getStringExtra(Constants.USERNAME);
        btnImport = findViewById(R.id.btn_import);
        btnManageAccount = findViewById(R.id.btn_acc_manage);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initDb();
        createDrawerLayout();
        setupTabLayout();
        if (!isManager) {
            btnManageAccount.setVisibility(View.GONE);
        }
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        }

        btnImport.setOnClickListener(this);
        btnManageAccount.setOnClickListener(this);

    }

    private void initDb() {
        db = new DatabaseHelper(this);
        StartApplication.setDb(db);
    }

    private void setupTabLayout() {
        //Create Tablayout
        Bundle bundle = new Bundle();
        bundle.putString(Constants.USERNAME, user);
// set Fragmentclass Arguments
        LeadsFragment leadsFragment = LeadsFragment.createInstance();
        leadsFragment.setArguments(bundle);
        CustomersFragment customersFragment = CustomersFragment.createInstance();
        LabelsFragment labelsFragment = LabelsFragment.createInstance();
        TasksFragment tasksFragment = TasksFragment.createInstance();
        GraphFragment graphFragment = GraphFragment.createInstance();
        ArrayList<Fragment> listFragment = new ArrayList<>();
        listFragment.add(leadsFragment);
        listFragment.add(customersFragment);
        listFragment.add(labelsFragment);
        listFragment.add(tasksFragment);
        listFragment.add(graphFragment);

        TabsPagerAdapter pagerAdapter = new TabsPagerAdapter(getSupportFragmentManager(), listFragment);
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(5);

        tabs.setupWithViewPager(pager);

        TabLayout.Tab tab1 = tabs.getTabAt(0);
        if (tab1 != null) {
            tab1.setCustomView(createTabView(R.drawable.img_tab_leads, getResources().getString(R.string.string_leads)));
        }
        TabLayout.Tab tab2 = tabs.getTabAt(1);
        if (tab2 != null) {
            tab2.setCustomView(createTabView(R.drawable.img_tab_customers, getResources().getString(R.string.string_customers)));
        }
        TabLayout.Tab tab3 = tabs.getTabAt(2);
        if (tab3 != null) {
            tab3.setCustomView(createTabView(R.drawable.img_tab_labels, getResources().getString(R.string.string_labels)));
        }
        TabLayout.Tab tab4 = tabs.getTabAt(3);
        if (tab4 != null) {
            tab4.setCustomView(createTabView(R.drawable.img_tab_tasks, getResources().getString(R.string.string_tasks)));
        }
        TabLayout.Tab tab5 = tabs.getTabAt(4);
        if (tab5 != null) {
            tab5.setCustomView(createTabView(R.drawable.img_tab_graph, getResources().getString(R.string.string_graph)));
        }

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        tvTitle.setText(getResources().getString(R.string.string_leads));
                        break;
                    case 1:
                        tvTitle.setText(getResources().getString(R.string.string_customers));
                        break;
                    case 2:
                        tvTitle.setText(getResources().getString(R.string.string_labels));
                        break;
                    case 3:
                        tvTitle.setText(getResources().getString(R.string.string_tasks));
                        break;
                    case 4:
                        tvTitle.setText(getResources().getString(R.string.string_graph));
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private View createTabView(int imgRes, String name) {
        View itemView = LayoutInflater.from(this).inflate(R.layout.tabs_main_layout, null);

        ImageView tabsIcon = itemView.findViewById(R.id.tabs_icon);
        TextView tabsName = itemView.findViewById(R.id.tabs_text);
        tabsIcon.setImageResource(imgRes);
        tabsName.setText(name);

        return itemView;
    }

    private void createDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_import:
                startActivity(ContactsActivity.class);
                break;
            case R.id.btn_acc_manage:
                startActivity(AccountManager.class);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Huybv", "permission has been granted");
            } else {
                finish();
            }
        }
    }

    public String getUser() {
        return user;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (db != null) {
            db.close();
        }
    }
}
