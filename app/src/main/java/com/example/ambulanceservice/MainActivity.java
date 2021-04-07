package com.example.ambulanceservice;

import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineListener;
import com.mapbox.android.core.location.LocationEnginePriority;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.CameraMode;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.RenderMode;

import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, LocationEngineListener, PermissionsListener, MapboxMap.OnMapClickListener {

    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    private MapView mapView;
    private MapboxMap map;
    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    private LocationLayerPlugin locationLayerPlugin;
    private Location originLocation;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nav=(NavigationView)findViewById(R.id.nav_menu);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);


        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {



                return false;
            }
        });


//       Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
//       Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
//       startActivity(intent);

    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        map=mapboxMap;
        enableLocation();
    }

    //user build method in which we see if get location access is granted or not if not the we ask for permission
    private void enableLocation() {
        if (permissionsManager.areLocationPermissionsGranted(this)) {
            //we need to set our location engine and location plugin
            initializeLocationEngine();
            initializeLocationLayer();
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @SuppressWarnings("MissingPermission")
    private void initializeLocationEngine() {

        locationEngine = new LocationEngineProvider(this).obtainBestLocationEngineAvailable();
        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
        locationEngine.activate();

        Location lastLocation = locationEngine.getLastLocation();
        if (lastLocation != null) {
            originLocation = lastLocation;
            setCameraPosition(lastLocation);
        } else {
            locationEngine.addLocationEngineListener(this);
        }
    }

    @SuppressWarnings("MissingPermission")
    private void initializeLocationLayer() {
        locationLayerPlugin = new LocationLayerPlugin(mapView, map, locationEngine);
        locationLayerPlugin.setLocationLayerEnabled(true);
        locationLayerPlugin.setCameraMode(CameraMode.TRACKING);
        locationLayerPlugin.setRenderMode(RenderMode.NORMAL);
    }

    private void setCameraPosition(Location location) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13.0));
    }

    @Override
    @SuppressWarnings("MissingPermission")
    public void onConnected() {
        locationEngine.requestLocationUpdates();

    }

    @Override
    public void onLocationChanged(Location location) {

        if(location!=null){
            originLocation=location;
            setCameraPosition(location);
        }

    }

    @SuppressWarnings("MissingPermission")
    protected void onStart() {

        super.onStart();
        if(locationEngine!=null)
        {
            locationEngine.removeLocationUpdates();
            if(locationLayerPlugin!=null)
            {
                locationLayerPlugin.onStart();
            }
        }
        mapView.onStart();
    }

    protected  void onResume()
    {
        super.onResume();
        mapView.onResume();
    }

    protected  void onPause()
    {
        super.onPause();
        mapView.onPause();
    }

    @SuppressWarnings("MissingPermission")
    protected void onStop()
    {
        super.onStop();
        if(locationEngine!=null)
        {
            locationEngine.removeLocationUpdates();
        }
        if(locationLayerPlugin!=null)
        {
            locationLayerPlugin.onStop();
        }
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(locationEngine!=null)
        {
            locationEngine.deactivate();
        }
        mapView.onDestroy();
    }

    //when user denies the permission for the first time next time  you need to tell the user why granting permission to location is necessary
    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        //present toast or dialog
    }

    //after permission in ask based this method is called
    @Override
    public void onPermissionResult(boolean granted) {
        if(granted){
            enableLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }


    @Override
    public void onMapClick(@NonNull LatLng point) {

    }


}