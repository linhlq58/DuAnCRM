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

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.totnghiepluon.duancrm.ContactData.ContactsActivity;
import com.totnghiepluon.duancrm.Customers.CustomersFragment;
import com.totnghiepluon.duancrm.Graph.GraphFragment;
import com.totnghiepluon.duancrm.Labels.LabelsFragment;
import com.totnghiepluon.duancrm.Leads.LeadsFragment;
import com.totnghiepluon.duancrm.R;
import com.totnghiepluon.duancrm.Tasks.TasksFragment;
import com.totnghiepluon.duancrm.Base.BaseActivity;
import com.totnghiepluon.duancrm.utils.Constants;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private DrawerLayout drawer;
    private TabLayout tabs;
    private ViewPager pager;
    private TabsPagerAdapter pagerAdapter;
    private boolean isManager;
    private RelativeLayout btnMenu;
    private TextView tvTitle;

    private LeadsFragment leadsFragment;
    private CustomersFragment customersFragment;
    private LabelsFragment labelsFragment;
    private TasksFragment tasksFragment;
    private GraphFragment graphFragment;

    private ArrayList<Fragment> listFragment;
    private RelativeLayout btnManageAccount;
    private RelativeLayout btnImport;

    private static int PERMISSIONS_REQUEST_READ_CONTACTS = 1267;

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
        btnImport = findViewById(R.id.btn_import);
        btnManageAccount = findViewById(R.id.btn_acc_manage);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        createDrawerLayout();
        setupTabLayout();
        if (!isManager){
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
    }

    private void setupTabLayout() {
        //Create Tablayout
        leadsFragment = LeadsFragment.createInstance();
        customersFragment = CustomersFragment.createInstance();
        labelsFragment = LabelsFragment.createInstance();
        tasksFragment = TasksFragment.createInstance();
        graphFragment = GraphFragment.createInstance();

        listFragment = new ArrayList<>();
        listFragment.add(leadsFragment);
        listFragment.add(customersFragment);
        listFragment.add(labelsFragment);
        listFragment.add(tasksFragment);
        listFragment.add(graphFragment);

        pagerAdapter = new TabsPagerAdapter(getSupportFragmentManager(), listFragment);
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(5);

        tabs.setupWithViewPager(pager);

        TabLayout.Tab tab1 = tabs.getTabAt(0);
        tab1.setCustomView(createTabView(R.drawable.img_tab_leads, getResources().getString(R.string.string_leads)));
        TabLayout.Tab tab2 = tabs.getTabAt(1);
        tab2.setCustomView(createTabView(R.drawable.img_tab_customers, getResources().getString(R.string.string_customers)));
        TabLayout.Tab tab3 = tabs.getTabAt(2);
        tab3.setCustomView(createTabView(R.drawable.img_tab_labels, getResources().getString(R.string.string_labels)));
        TabLayout.Tab tab4 = tabs.getTabAt(3);
        tab4.setCustomView(createTabView(R.drawable.img_tab_tasks, getResources().getString(R.string.string_tasks)));
        TabLayout.Tab tab5 = tabs.getTabAt(4);
        tab5.setCustomView(createTabView(R.drawable.img_tab_graph, getResources().getString(R.string.string_graph)));

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
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted

            } else {
                finish();
            }
        }
    }
}
