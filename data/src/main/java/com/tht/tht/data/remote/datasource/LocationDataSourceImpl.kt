package com.tht.tht.data.remote.datasource

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.tht.tht.data.di.IODispatcher
import com.tht.tht.data.remote.response.location.LocationResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.*
import javax.inject.Inject


class LocationDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : LocationDataSource {

    override suspend fun fetchCurrentLocation(): LocationResponse {
        return withContext(dispatcher) {

            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                throw SecurityException("Location permission not granted")
            }
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!

            val lat = location.latitude
            val lng = location.longitude

            LocationResponse(lat, lng, getAddress(context, lat, lng))
        }
    }

    private fun getAddress(mContext: Context?, lat: Double, lng: Double): String {
        val geocoder = Geocoder(mContext, Locale.KOREA)
        val location = geocoder.getFromLocation(lat, lng, 1)
        if (location == null || location.size == 0)
            throw IOException("Can not find location")
        val address = StringBuilder()
        location[0].getAddressLine(0).split(" ").forEachIndexed { index, name ->
            if(index == 0) return@forEachIndexed
            if(index == 1 && name.last() == '도') return@forEachIndexed
            address.append(name).append(" ")
        }

        return address.toString()
    }
}
