package com.example.ambulanceservice;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import java.util.List;
// Classes needed to add the location engine
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import java.lang.ref.WeakReference;
// Classes needed to add the location component
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;

import com.google.android.material.navigation.NavigationView;




public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback, PermissionsListener{


    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;

    public MapboxMap mapboxMap;
    public MapView mapView;


    // Variables needed to handle location permissions
    private PermissionsManager permissionsManager;
    // Variables needed to add the location engine
    private LocationEngine locationEngine;
    private long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
    private long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;
// Variables needed to listen to location updates
    //Globally declare an instance of the class you created above:
    private MainActivityLocationCallback callback = new MainActivityLocationCallback(this);

    /**
     * That's where the PermissionsManager class comes into play.
     * With the PermissionsManager class, you can check whether the user has granted
     * location permission and request permissions if the user hasn't granted them yet.
     * You can use PermissionsManager permissionsManager = new PermissionsManager(this);
     * if you're implementing PermissionsListener.
     * */
    /**
     * Once you have set up your permissions manager, you will still need to override
     * onRequestPermissionsResult() and call the permissionsManager's same method.
     * Note: The PermissionsManager can be used for requesting other permissions in addition to location.
     * */
   // private Location originLocation;

    /*
    * The PermissionsListener is an interface that returns information about the state of permissions.
    * Set up the interface and pass it into the PermissionsManager's constructor.
The permission result is invoked once the user decides whether to allow or deny the permission.
* A boolean value is given, which you can then use to write an if statement. Both cases should be handled correctly.
* Continue with your permission-sensitive logic if the user approves. Otherwise, if the user denies, display a message
* that tells the user that the permission is required for your application to work. An explanation isn't required but
* strongly encouraged to allow the user to understand why you are requesting this permission.
    * */


    @SuppressLint("MissingPermission")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // we pass access token to get our map
        Mapbox.getInstance(this, getString(R.string.access_token));

        setContentView(R.layout.activity_main);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this); //after this whenever the map is loaded we go to on mapready method  in this class because we have implemented OnMapReadyCallback


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nav = (NavigationView) findViewById(R.id.nav_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);


        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                return false;
            }
        });


    }

    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap; //when map get's ready it passes mapboxMap instance

        //setting style
        mapboxMap.setStyle(Style.TRAFFIC_NIGHT,
                new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                        enableLocationComponent(style);

                    }
                });
    }




    /**
     * Initialize the Maps SDK's LocationComponent
     */
    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
// Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

// Get an instance of the component
            LocationComponent locationComponent = mapboxMap.getLocationComponent();

// Set the LocationComponent activation options
            //Retrieve and activate the LocationComponent once the user has granted location permission and the map has fully loaded.
            LocationComponentActivationOptions locationComponentActivationOptions =
                    LocationComponentActivationOptions.builder(this, loadedMapStyle)
                            .useDefaultLocationEngine(false)
                            .build();

// Activate with the LocationComponentActivationOptions object
            locationComponent.activateLocationComponent(locationComponentActivationOptions);

// Enable to make component visible
            /*
            * There is a single method to either enable or disable the LocationComponent's visibility after activation.
            *  The setLocationComponentEnabled() method requires a true/false boolean parameter. When set to false,
            * this method will hide the device location icon and stop map camera animations from occurring.
            * */
            locationComponent.setLocationComponentEnabled(true);

// Set the component's camera mode
            /*
            * The method LocationComponent#setCameraMode(@CameraMode.Mode int cameraMode) allows developers to
            *  set specific camera tracking instructions as the device location changes.
            * There are 7 CameraMode options available:
            * */
            locationComponent.setCameraMode(CameraMode.TRACKING);

// Set the component's render mode type of compass it is rendered
            //The RenderMode class contains preset options for the device location image.
            //NORMAL
            //COMPASS
            //GPS
            locationComponent.setRenderMode(RenderMode.COMPASS);

            initLocationEngine();
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }


    /**
     * Set up the LocationEngine and the parameters for querying the device's location
     */
    @SuppressLint("MissingPermission")
    private void initLocationEngine() {

        /*
        * If your application needs location information,
        * the LocationEngine class can help you get this information while also simplifying
        * the process and being flexible enough to use different services. The LocationEngine found in the core
        * module now supports the following location providers:
        * */

        /**
         * This will obtain the best location engine that is available and eliminate the need to
         * create a new LocationEngine from scratch.
         * */
        locationEngine = LocationEngineProvider.getBestLocationEngine(this);


        /*
        * Request location updates once you know location permissions have been granted:
        * */
        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();

        locationEngine.requestLocationUpdates(request, callback, getMainLooper());
        locationEngine.getLastLocation(callback);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            if (mapboxMap.getStyle() != null) {
                enableLocationComponent(mapboxMap.getStyle());
            }
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /*
    * To prevent your application from having a memory leak, it is a good idea to stop
    * requesting location updates inside of your activity's onStop() method.
    * */
    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    /*
     * To prevent your application from having a memory leak, it is a good idea to stop
     * requesting location updates inside of your activity's onStop() method.
     * */
    protected void onDestroy() {
        super.onDestroy();
        //hide lines
// Prevent leaks
        if (locationEngine != null) {
            locationEngine.removeLocationUpdates(callback);
        }
      //  hide lines
        mapView.onDestroy();
    }

}