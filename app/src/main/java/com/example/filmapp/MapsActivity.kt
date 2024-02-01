package com.example.filmapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var googleMap: GoogleMap
    private lateinit var placesClient: PlacesClient
    lateinit var backBtn: Button

    companion object
    {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val DEFAULT_ZOOM = 15.0f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        backBtn = findViewById(R.id.backBtn)

        // Places API 초기화
        Places.initialize(applicationContext, "AIzaSyAL1R5QkGk-aknifguaJ5XPNTP6wPZqpsg") // Replace with your API key
        placesClient = Places.createClient(this)

        // 지도 초기화 및 위치 권한 확인
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        checkLocationPermission()
        setupAutocompleteFragment()

        backBtn.setOnClickListener {
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
    }

    private fun setupAutocompleteFragment() {
        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        
        // 반환할 장소 데이터 유형 지정
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))

        // 'establishment' 유형의 결과만 표시하도록 필터 설정
        autocompleteFragment.setTypeFilter(TypeFilter.ESTABLISHMENT)

        autocompleteFragment.setHint("주변 영화관을 검색하세요!")

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: com.google.android.libraries.places.api.model.Place) {
                // 선택한 장소 처리
                searchNearbyCinemas(place.latLng!!)
            }

            override fun onError(status: com.google.android.gms.common.api.Status) {
                // 에러 핸들링
                Log.i("Autocomplete", "An error occurred: $status")
            }
        })

        // 자동완성 위한 경계 설정
        val bounds = RectangularBounds.newInstance(
            LatLng(-34.041458, 150.790100),
            LatLng(-33.682247, 151.383362)
        )
        autocompleteFragment.setLocationBias(bounds)
    }

    private fun checkLocationPermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            // 권한이 부여되었으면 마지막으로 알려진 위치(lastKnownLocation) 가져오기
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    // 사용자의 현재 위치를 검색 중심으로 사용
                    Log.d("LastLocation", "Latitude: ${it.latitude}, Longitude: ${it.longitude}")
                    val center = LatLng(it.latitude, it.longitude)
                    googleMap.isMyLocationEnabled = true
                    searchNearbyCinemas(center)
                }
            }
        } else {
            // 권한이 부여되지 않았으면 사용자에게 요청
            requestLocationPermission()
        }
    }


    private fun requestLocationPermission()
    {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        when (requestCode)
        {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    // 권한 부여됨, 내 위치 활성화
                    enableMyLocation()
                } else {
                    // 권한 거부
                    Toast.makeText(this, "권한이 없어 해당 기능을 실행할 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun enableMyLocation()
    {
        googleMap.isMyLocationEnabled = true
        // myLocation 활성화 후 추가 작업
        checkLocationPermission()
    }

    fun searchNearbyCinemas(center : LatLng)
    {
        googleMap.clear()

        val bounds = RectangularBounds.newInstance(
            LatLng(-33.880490, 151.184363),
            LatLng(-33.858754, 151.229596)
        )

        // Create a request to find nearby cinemas
        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery("cinema")
            .setLocationBias(bounds)
            .build()

        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response ->
                for (prediction in response.autocompletePredictions) {
                    // Fetch details about each predicted place
                    val placeId = prediction.placeId
                    fetchPlaceDetails(placeId)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Autocomplete", "Autocomplete prediction failed: $exception")
            }
    }

    private fun fetchPlaceDetails(placeId: String) {
        val request = FetchPlaceRequest.builder(placeId, listOf(Place.Field.NAME, Place.Field.LAT_LNG))
            .build()

        placesClient.fetchPlace(request)
            .addOnSuccessListener { response ->
                val place = response.place
                // 검색된 장소를 지도에 표시하는 마커 추가
                googleMap.addMarker(
                    MarkerOptions()
                        .position(place.latLng!!)
                        .title(place.name)
                )

                // 카메라를 검색된 장소의 중심으로 이동
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.latLng, DEFAULT_ZOOM))
            }

            .addOnFailureListener { exception ->
                Log.e("FetchPlace", "Place fetching failed: $exception")
            }
    }

    private fun handleCallbackLocation() {
        // 예시로 사용할 위치 서울로 설정(예: 마지막 위치가 널인 경우)
        val fallbackLocation = LatLng(37.532600, 127.024612)
        // MyLocation 활성화 전에 위치 권한이 부여되었는지 확인
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.isMyLocationEnabled = true
        } else {
            // 권한이 부여되지 않았으면 사용자에게 요청
            requestLocationPermission()
        }
        searchNearbyCinemas(fallbackLocation)
    }
}