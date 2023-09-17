package tht.feature.tohot.mapper

import android.util.Log
import java.lang.String.format
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.math.abs

fun Long.calculateInterval(timeMill: Long): String {
    return abs(timeMill - this)
        .let {
            format(
                Locale.getDefault(),
                "%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(it),
                TimeUnit.MILLISECONDS.toMinutes(it) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(it)),
                TimeUnit.MILLISECONDS.toSeconds(it) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(it))
            ).also { interval ->
                Log.d("cwj", "calculateInterval[$it] => $interval")
            }
        }
}
