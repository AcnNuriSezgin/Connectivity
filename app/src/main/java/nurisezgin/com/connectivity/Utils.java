package nurisezgin.com.connectivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Created by nuri on 02.08.2018
 */
public final class Utils {

    NetworkInfo getActiveNetworkInfo(Context context) throws UnresolvedNetworkException {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);


        final NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo == null) {
            throw new UnresolvedNetworkException();
        }

        return networkInfo;
    }

    boolean isConnected(Context context) throws UnresolvedNetworkException {
        return getActiveNetworkInfo(context).isConnected();
    }

    boolean isConnectedOrConnecting(Context context) throws UnresolvedNetworkException {
        return getActiveNetworkInfo(context).isConnectedOrConnecting();
    }

    boolean isMobileNetwork(Context context) throws UnresolvedNetworkException {
        return getActiveNetworkInfo(context).getType() == ConnectivityManager.TYPE_MOBILE;
    }

    boolean isWifiNetwork(Context context) throws UnresolvedNetworkException {
        return getActiveNetworkInfo(context).getType() == ConnectivityManager.TYPE_WIFI;
    }

    boolean isConnectionFast(Context context) throws UnresolvedNetworkException {
        if (isWifiNetwork(context)) {
            return true;
        }

        NetworkInfo info = getActiveNetworkInfo(context);
        int subType = info.getSubtype();

        switch (subType) {
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_LTE:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
            case TelephonyManager.NETWORK_TYPE_IWLAN:
                return true;
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
            case TelephonyManager.NETWORK_TYPE_GSM:
            case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
            default:
                return false;
        }
    }
}
