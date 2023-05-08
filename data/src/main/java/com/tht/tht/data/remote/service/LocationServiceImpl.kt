package com.tht.tht.data.remote.service

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.tht.tht.data.remote.response.location.LocationResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LocationService {

    override suspend fun fetchCurrentLocation(): LocationResponse {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            throw SecurityException("Location permission not granted")
        }

        val location = requireNotNull(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER))

        val lat = location.latitude
        val lng = location.longitude

        return LocationResponse(lat, lng, getAddress(lat, lng))
    }

    override suspend fun fetchLocationByAddress(address: String): LocationResponse {
        val coordinate = getCoordinate(address)
        return LocationResponse(coordinate.first, coordinate.second, address)
    }

    private suspend fun getAddress(lat: Double, lng: Double): String {
        val geocoder = Geocoder(context, Locale.KOREA)

        val location = if (Build.VERSION.SDK_INT >= 33) {
            suspendCancellableCoroutine { cancellableContinuation ->
                geocoder.getFromLocation(
                    lat, lng, 1
                ) { list ->
                    cancellableContinuation.resume(list)
                }
            }
        } else {
            geocoder.getFromLocation(lat, lng, 1)!!
        }

        return location[0].getAddressLine(0)
    }

    private suspend fun getCoordinate(locationName: String): Pair<Double, Double> {
        val geocoder = Geocoder(context, Locale.KOREA)

        val location = if (Build.VERSION.SDK_INT >= 33) {
            suspendCancellableCoroutine { cancellableContinuation ->
                geocoder.getFromLocationName(
                    locationName, 1
                ) { list ->
                    cancellableContinuation.resume(list)
                }
            }
        } else {
            geocoder.getFromLocationName(locationName, 1)!!
        }

        return Pair(location[0].latitude, location[0].longitude)
    }
}
