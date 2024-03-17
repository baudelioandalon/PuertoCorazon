import java.text.SimpleDateFormat
import java.util.*

object AndroidConfig {
    const val minSdk = 23
    const val targetSdk = 30
    const val compileSdk = 31
    const val namespace = "com.boreal.puertocorazon"
    const val versionCode = 3
    val versionName = "1.1.0-${SimpleDateFormat("yyyyMMdd", Locale.US).format(Date())}"
    const val testRunner = "androidx.test.runner.AndroidJUnitRunner"
}