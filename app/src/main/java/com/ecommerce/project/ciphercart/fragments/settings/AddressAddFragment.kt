package com.ecommerce.project.ciphercart.fragments.settings

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.FragmentAddressAddBinding
import com.ecommerce.project.ciphercart.utils.Constants
import com.ecommerce.project.ciphercart.utils.hideKeyboard
import com.ecommerce.project.ciphercart.utils.setUpActionBar
import com.ecommerce.project.ciphercart.utils.toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.io.IOException
import java.util.Locale


class AddressAddFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding:FragmentAddressAddBinding
    private lateinit var mMap: GoogleMap
    private var centerMarker: Marker? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var placesClient: PlacesClient



//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        // The usage of an interface lets you inject your own implementation
//        val menuHost: MenuHost = requireActivity()
//
//        // Add menu items without using the Fragment Menu APIs
//        // Note how we can tie the MenuProvider to the viewLifecycleOwner
//        // and an optional Lifecycle.State (here, RESUMED) to indicate when
//        // the menu should be visible
//        menuHost.addMenuProvider(object : MenuProvider {
//            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                // Add menu items here
//                menuInflater.inflate(R.menu.search_menu, menu)
//            }
//
//            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                // Handle the menu selection
//                return when (menuItem.itemId) {
//                    R.id.action_search -> {
//                        toast(requireContext(), "search clicked")
//                        true
//
//                    }
//
//                    else -> false
//                }
//            }
//        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
//    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddressAddBinding.inflate(layoutInflater, container, false)

        initialize()
        setUpActionBar(binding.toolbar, requireActivity())
        search()
        focusListeners()
        bottomSheetSetup()

        return binding.root
    }

    private fun search() {

        // Initialize Places SDK
        Places.initialize(requireContext(), "AIzaSyA2bBjAntKMPc4yF58Y5tWtC-0-Q9P_dw4")
        placesClient = Places.createClient(requireContext())


        binding.catSearchView.editText.doOnTextChanged { text, start, before, count ->
            if(!text.isNullOrBlank()){
                showSuggestions(text.toString())
            }
            else{
                binding.listView.visibility = ListView.GONE
            }
        }
    }

    private fun showSuggestions(query:String) {
        val autocompleteRequest = FindAutocompletePredictionsRequest.builder()
            .setTypeFilter(TypeFilter.ADDRESS)
            .setSessionToken(AutocompleteSessionToken.newInstance())
            .setQuery(query)
            .build()

        placesClient.findAutocompletePredictions(autocompleteRequest)
            .addOnSuccessListener { response ->
                val suggestions = response.autocompletePredictions
                    .map { prediction -> prediction.getFullText(null).toString() }

                toast(requireContext(), suggestions.size.toString())
                val adapter = binding.listView.adapter as ArrayAdapter<String>
                adapter.clear()
                adapter.addAll(suggestions)
                adapter.notifyDataSetChanged()

                binding.listView.visibility = ListView.VISIBLE
            }
            .addOnFailureListener { exception ->
                // Handle errors
                toast(requireContext(), exception.localizedMessage)
                binding.listView.visibility = ListView.GONE
            }
    }

    private fun initialize() {
        // Set up the ListView adapter
        val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1)
        binding.listView.adapter = adapter


        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())


    }

    private fun bottomSheetSetup() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        bottomSheetBehavior.peekHeight = 200
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

//        bottomSheetBehavior.addBottomSheetCallback(object :
//            BottomSheetBehavior.BottomSheetCallback() {
//            override fun onStateChanged(bottomSheet: View, newState: Int) {
//
//                // Handle state changes
//            }
//
//            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                // Handle slide offset changes
//            }
//        })


    }

    private fun focusListeners() {
        binding.apply {
            container.setOnClickListener {
                addressDetails.clearFocus()
                nameAddress.clearFocus()
                hideKeyboard(requireActivity(), view)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Check for location permission
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true

           mMap.setOnMarkerDragListener(object : OnMarkerDragListener{
               override fun onMarkerDrag(p0: Marker) {

               }

               override fun onMarkerDragEnd(p0: Marker) {
                   val address = getAddressFromLatLng(requireContext(),p0.position.latitude,p0.position.longitude)
                   binding.addressDetails.setText(address)
               }

               override fun onMarkerDragStart(p0: Marker) {

               }

           })
//            val customMarkerIcon: BitmapDescriptor = getCustomMarkerIcon()
            centerMarker = mMap.addMarker(
                MarkerOptions()
                    .position(LatLng(0.0, 0.0))
                    .title("Address")
                    .draggable(false)
//                    .icon(customMarkerIcon)
            )
            // Get the last known location and move the camera
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    location?.let {
                        val currentLatLng = LatLng(it.latitude, it.longitude)
                        centerMarker?.position = currentLatLng
//                        mMap.addMarker(MarkerOptions().position(currentLatLng).title("Address").draggable(true))
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 18f))
                        val address = getAddressFromLatLng(requireContext(), it.latitude, it.longitude)
                        binding.addressDetails.setText(address)
                        binding.pbLoader.visibility = View.GONE
                    }
                }

            // Move camera to the center of the screen
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerMarker!!.position, 15f))

            // Set up map move listener
            mMap.setOnCameraMoveListener {
                // Update the marker position to the center of the screen
                centerMarker?.position = mMap.cameraPosition.target
            }

            // Set up map idle listener
            mMap.setOnCameraIdleListener {
                // Get the address of the center of the screen
                val address = getAddressFromLatLng(requireContext(),centerMarker?.position!!.latitude, centerMarker?.position!!.longitude)
                binding.addressDetails.setText(address)
                binding.pbLoader.visibility = View.GONE
            }



        } else {
            // Request location permission if not granted
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }
    private fun getCustomMarkerIcon(): BitmapDescriptor {
        // Load the custom marker icon from the drawable folder
        val resourceId = R.drawable.map_marker
        val bitmap: Bitmap = BitmapFactory.decodeResource(resources, resourceId)

        // Resize the bitmap if needed
        val scaledBitmap: Bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false)

        // Create a BitmapDescriptor from the resized bitmap
        return BitmapDescriptorFactory.fromBitmap(scaledBitmap)
    }
    private fun getAddressFromLatLng(context: Context, latitude: Double, longitude: Double): String {
        binding.pbLoader.visibility = View.VISIBLE
        val geocoder = Geocoder(context, Locale.getDefault())
        var addressText = ""

        try {
            val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)

            if (!addresses.isNullOrEmpty()) {
                val address: Address = addresses[0]
                val addressParts = mutableListOf<String>()

                // Fetch the address lines using getAddressLine,
                // join them, and separate with commas
                for (i in 0..address.maxAddressLineIndex) {
                    addressParts.add(address.getAddressLine(i))
                }

                addressText = addressParts.joinToString(separator = ", ")
            }
        } catch (e: IOException) {
            toast(requireContext(), e.localizedMessage)
            e.printStackTrace()

        }

        return addressText
    }
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }




}