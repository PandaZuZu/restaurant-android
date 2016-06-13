package com.cpl.restaurantrezervation.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cpl.restaurantrezervation.R;
import com.cpl.restaurantrezervation.application.GoogleMapsInfoWindowAdapter;
import com.cpl.restaurantrezervation.application.PermissionUtils;
import com.cpl.restaurantrezervation.application.ReservedApplication;
import com.cpl.restaurantrezervation.model.Coordinate;
import com.cpl.restaurantrezervation.model.Restaurant;
import com.cpl.restaurantrezervation.model.RestaurantList;
import com.cpl.restaurantrezervation.utils.Utils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * Show the google maps API
 * First it gets our Location and move camera to our location
 * Secondly add markers on map with our restaurants list
 * Personalise infoShowWindow with our GoogleMapsInfoAdapter class
 * Check distance to see if user is close to the reward place
 */

public class RestaurantMapActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMapClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback{

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    public static final double MIN_DISTANCE = 0.0004;

    private boolean mPermissionDenied = false;

    private ImageLoader imageLoader = ImageLoader.getInstance();
    private GoogleMapsInfoWindowAdapter googleMapsInfoWindowAdapter;

    public static HashMap <String, Bitmap> restaurantImages = new HashMap<>();
    public static final String RESTAURANT_REFERENCE_TAG = "clickedRestaurant";

    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    private boolean mRequestingUpdate = true;

    protected LocationRequest mLocationRequest = new LocationRequest();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        initGoogleMapInfoAdapter();
        initImageLoader();



        Call<List<Restaurant>> result = ((ReservedApplication)getApplication())
                .getReservedAPI().getData(Utils.parseURL(MainActivity.currentUser.getEmail()));

        result.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                RestaurantList.restaurantList = response.body();

                for (Restaurant rs : RestaurantList.restaurantList) {
                    Log.d("body", rs.getName() + "\n" +
                                    rs.getDescription() + "\n" +
                                    rs.getOpened() + "\n" +
                                    rs.getWebsite() + "\n" +
                                    rs.getLatitude() + "\n" +
                                    rs.getLongitude() + "\n" +
                                    rs.getPictures().getUrl() + "\n" +
                                    rs.getPictures().getStandard().getUrl() + "\n" +
                                    rs.getPictures().getThumbnail().getUrl()
                    );
                    Marker restaurantMarker = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(rs.getLatitude(), rs.getLongitude()))
                            .title(rs.getName())
                            .snippet("Opened: " + rs.getOpened() + "\n" +
                                    "Website: " + rs.getWebsite() + "\n" +
                                    "Phone: " + rs.getPhone()));

                    getImageFromUrl(rs.getPictures().getUrl(), restaurantMarker.getId());
                }
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                Log.d("body", "error");
            }
        });
    }

    //for each marker we add bitmap loaded to our hash so we can add it on view later
    private void getImageFromUrl(String url, final String markerId){
        imageLoader.loadImage(url, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri,
                                          View view, Bitmap loadedImage) {

                restaurantImages.put(markerId, loadedImage);
            }
        });
    }


    private void initGoogleMapInfoAdapter(){
        googleMapsInfoWindowAdapter = new GoogleMapsInfoWindowAdapter(this);
    }

    //we configure the imageLoader then initiate it
    private void initImageLoader(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher) // resource or drawable
                .resetViewBeforeLoading(false)  // default
                .delayBeforeLoading(1000)
                .cacheInMemory(false) // default
                .cacheOnDisk(false) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getBaseContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(options)
                .build();

        imageLoader.init(config);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        CameraUpdate zoom = CameraUpdateFactory.zoomTo(11);
        mMap.animateCamera(zoom);

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setInfoWindowAdapter(googleMapsInfoWindowAdapter);
        mMap.setOnMapClickListener(this);

        enableMyLocation();

        //coonect api to get current location
        googleApiClient.connect();

    }

    @Override
    public void onStop() {
        super.onStop();
        if( googleApiClient != null && googleApiClient.isConnected() ) {
            googleApiClient.disconnect();
        }
    }


    private void enableMyLocation(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        mRequestingUpdate = true;
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onConnected(Bundle bundle) {
        getLocation();
        if (mRequestingUpdate) {
            startLocationUpdates();
        }
    }

    private void getLocation(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location mLocation;
            mLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            initCamera(mLocation);
        }
    }

    public void initCamera(Location mLocation){
        if(mLocation != null && mRequestingUpdate) {
            Log.d("update", mLocation.getLatitude() + " " + mLocation.getLongitude());
            CameraUpdate center = CameraUpdateFactory.newLatLng(
                    new LatLng(mLocation.getLatitude(), mLocation.getLongitude()));

            CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

            mMap.moveCamera(center);
            mMap.animateCamera(zoom);
        }

        createLocationRequest();
    }

    protected void createLocationRequest() {
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(500);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient,
                        builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates locationSettingsStates = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                    RestaurantMapActivity.this,
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.

                        break;
                }
            }
        });




    }


    protected void startLocationUpdates() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    googleApiClient, mLocationRequest, this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        mRequestingUpdate = true;

                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        finish();
                        break;
                    default:
                        break;
                }
                break;
        }
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        marker.hideInfoWindow();
        Intent intent = new Intent(this, RestaurantShow.class);
        intent.putExtra(RESTAURANT_REFERENCE_TAG, marker.getTitle());
        startActivity(intent);
    }

    @Override
    public void onLocationChanged(Location location) {
        initCamera(location);
        Log.d("location", location.getLatitude() + " " + location.getLongitude());

        for(Restaurant rs:RestaurantList.restaurantList) {
            if (calculateDistance(location, rs)) {
                Toast.makeText(this, "added", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private boolean calculateDistance(Location currentLocation, Restaurant rs) {
     return Utils.distanceBetweenPoints(new Coordinate(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                        new Coordinate(rs.getLatitude(), rs.getLongitude())) <= MIN_DISTANCE;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mRequestingUpdate = false;
    }
}
