package mdy.klt.myatmyat.ui

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import mdy.klt.myatmyat.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}