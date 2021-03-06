package info.nightscout.androidaps.utils.buildHelper

import android.os.Build
import info.nightscout.androidaps.BuildConfig
import info.nightscout.androidaps.interfaces.Config
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigImpl @Inject constructor() : Config {

    override val SUPPORTEDNSVERSION = 1002 // 0.10.00
    override val APS = BuildConfig.FLAVOR == "full"
    override val NSCLIENT = BuildConfig.FLAVOR == "nsclient" || BuildConfig.FLAVOR == "nsclient2"
    override val PUMPCONTROL = BuildConfig.FLAVOR == "pumpcontrol"
    override val PUMPDRIVERS = BuildConfig.FLAVOR == "full" || BuildConfig.FLAVOR == "pumpcontrol"
    override val FLAVOR = BuildConfig.FLAVOR
    override val VERSION_NAME = BuildConfig.VERSION_NAME

    override val currentDeviceModelString =
        Build.MANUFACTURER + " " + Build.MODEL + " (" + Build.DEVICE + ")"
}