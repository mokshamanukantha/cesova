package com.android.cesova.Activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.cesova.Adapters.CustomDrawerAdapter;
import com.android.cesova.Adapters.DrawerItem;
import com.android.cesova.BluetoothConnector;
import com.android.cesova.DeviceListActivity;
import com.android.cesova.Fragments.HelpFragment;
import com.android.cesova.Fragments.HomeFragment;
import com.android.cesova.Fragments.RealTimeInformationFragment;
import com.android.cesova.Fragments.SettingsFragment;
import com.android.cesova.Fragments.TestResultsFragment;
import com.android.cesova.GlobalClass;
import com.android.cesova.Map.DirectionsInputActivity;
import com.android.cesova.R;
import com.android.cesova.obd.commands.protocol.EchoOffObdCommand;
import com.android.cesova.obd.commands.protocol.LineFeedOffObdCommand;
import com.android.cesova.obd.commands.protocol.SelectProtocolObdCommand;
import com.android.cesova.obd.commands.protocol.TimeoutObdCommand;
import com.android.cesova.obd.enums.ObdProtocols;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    public static final String TAG = "MainActivity";

    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;
    public BluetoothConnector conector;
    public BluetoothConnector.BluetoothSocketWrapper bluSocket;
    public GlobalClass socket;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ListView leftDrawerList;
    private ArrayAdapter<String> navigationDrawerAdapter;
    private String[] leftSliderData = {"Home", "Help","Speedometer","LogBook","COMPASS","Settings"};
    private BluetoothAdapter mBluetoothAdapter = null;
    private HomeFragment fragmentHome;
    private RealTimeInformationFragment fragmentRealTimeInformation;
    private CheckFaultCodesActivty fragmentCheckFaultCodes;
    private TestResultsFragment fragmentTestResults;
    private SettingsFragment fragmentSettings;
    private HelpFragment fragmentHelp;
    List<DrawerItem> dataList;
    private android.app.FragmentTransaction transaction;
    private FragmentManager manager;
    CustomDrawerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
        }
        initView();
        if (toolbar != null) {
            toolbar.setTitle("CarDroid");
            setSupportActionBar(toolbar);
        }
        initDrawer();
        if (savedInstanceState == null) {
            manager = getFragmentManager();
            transaction = manager.beginTransaction();
            fragmentHome = new HomeFragment();
            transaction.replace(R.id.content_frame, fragmentHome);
            transaction.commit();

        }
        leftDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }


    private void initView() {
        leftDrawerList = (ListView) findViewById(R.id.left_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        //navigationDrawerAdapter =new CustomDrawerAdapter(this, R.layout.custom_drawer_item,dataList);
        navigationDrawerAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, leftSliderData);
        leftDrawerList.setAdapter(navigationDrawerAdapter);
    }


    private void initDrawer() {

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.btConnect) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            return true;
        }
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.secure_connect_scan: {
                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
                }
                Intent serverIntent = new Intent(this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);

                return true;
            }
            case R.id.insecure_connect_scan: {
                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
                }
                Intent serverIntent = new Intent(this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);
                return true;
            }
            case R.id.discoverable: {
                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
                }
                ensureDiscoverable();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mBluetoothAdapter.isEnabled()) {
            //Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
    }

    private void ensureDiscoverable() {
        if (mBluetoothAdapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true);
                }
                break;
            case REQUEST_CONNECT_DEVICE_INSECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, false);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(this, R.string.bt_not_enabled_leaving,
                            Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void connectDevice(Intent data, boolean secure) {
        String address = data.getExtras()
                .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        conector = new BluetoothConnector(device, secure, mBluetoothAdapter, null);
        try {
            bluSocket = conector.connect();
            initOBDAdapter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initOBDAdapter() {
        socket = new GlobalClass(bluSocket.getUnderlyingSocket());
        try {
            try {
                new EchoOffObdCommand().run(socket.getInputStream(), socket.getOutputStream());
                new LineFeedOffObdCommand().run(socket.getInputStream(), socket.getOutputStream());
                new TimeoutObdCommand(62).run(socket.getInputStream(), socket.getOutputStream());
                new SelectProtocolObdCommand(ObdProtocols.AUTO).run(socket.getInputStream(), socket.getOutputStream());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                //TroubleCodesObdCommand tr = new TroubleCodesObdCommand();
                //executeCommands(tr);
                fragmentHome.startGauge();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    private void setStatus(CharSequence subTitle) {
        if (null == this) {
            return;
        }
        final android.support.v7.app.ActionBar actionBar = MainActivity.this.getSupportActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setSubtitle(subTitle);
    }

    public void changeLayout(int position) {
        fragmentHome.mTimer.cancel();
        if (position == 0) {
            manager = getFragmentManager();
            transaction = manager.beginTransaction();
            fragmentHome = new HomeFragment();
            transaction.addToBackStack(String.valueOf(fragmentHome));
            transaction.replace(R.id.content_frame, fragmentHome);
            transaction.commit();
            drawerLayout.closeDrawers();

        } else if (position == 1) {
            /*manager = getFragmentManager();
            transaction = manager.beginTransaction();
            fragmentRealTimeInformation = new RealTimeInformationFragment();
            transaction.addToBackStack(String.valueOf(fragmentRealTimeInformation));
            transaction.replace(R.id.content_frame, fragmentRealTimeInformation);
            transaction.commit();*/
            Intent i = new Intent(this, RealTimeInformationFragment.class);
            startActivity(i);
        } else if (position == 2) {
            Intent i = new Intent(this, CheckFaultCodeMain.class);
            startActivity(i);
        } else if (position == 3) {
            Intent i = new Intent(this, ActivitySelectGraph.class);
            startActivity(i);
        } else if (position == 4) {
            Intent i = new Intent(this, DirectionsInputActivity.class);
            startActivity(i);
        } else if (position == 5) {
            manager = getFragmentManager();
            transaction = manager.beginTransaction();
            fragmentTestResults = new TestResultsFragment();
            transaction.addToBackStack(String.valueOf(fragmentTestResults));
            transaction.replace(R.id.content_frame, fragmentTestResults);
            transaction.commit();
        } else if (position == 6) {
            manager = getFragmentManager();
            transaction = manager.beginTransaction();
            fragmentHelp = new HelpFragment();
            transaction.addToBackStack(String.valueOf(fragmentHelp));
            transaction.replace(R.id.content_frame, fragmentHelp);
            transaction.commit();
        } else if (position == 7) {
            manager = getFragmentManager();
            transaction = manager.beginTransaction();
            fragmentSettings = new SettingsFragment();
            transaction.addToBackStack(String.valueOf(fragmentSettings));
            transaction.replace(R.id.content_frame, fragmentSettings);
            transaction.commit();
        } else if (position == 8) {
            Intent i = new Intent(this, Speedometer.class);
            startActivity(i);
        } else if (position == 9) {
            //Intent i = new Intent(this, Main_Screen.class);
            //startActivity(i);
            openApp(this, "com.enadein.carlogbook");
        } else if (position == 10) {
            Intent i = new Intent(this, Compass.class);
            startActivity(i);
        } else if (position == 11) {
            Intent i = new Intent(this, Speedometer.class);
            startActivity(i);
        }
        drawerLayout.closeDrawers();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private class DrawerItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(position==0) {
                changeLayout(0);
            }
            if(position==1) {
                changeLayout(6);
            }
            if(position==2) {
                changeLayout(11);
            }
            if(position==3) {
                changeLayout(9);
            }
            if(position==4) {
                changeLayout(10);
            }
            if(position==5) {
                changeLayout(7);
            }
        }
    }

    public static boolean openApp(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        Intent i = manager.getLaunchIntentForPackage(packageName);
        if (i == null) {
            return false;
            //throw new PackageManager.NameNotFoundException();
        }
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        context.startActivity(i);
        return true;
    }
}