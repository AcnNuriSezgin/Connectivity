package nurisezgin.com.connectivity;

import android.content.Context;
import android.support.annotation.NonNull;

import androidx.work.Worker;

/**
 * Created by nuri on 02.08.2018
 */
public final class ConnectivityStateWorker extends Worker {

    @NonNull
    @Override
    public Result doWork() {
        Context context = ConnectivityMonitor.getInstance()
                .getAppContextProvider()
                .getContext();

        try {
            boolean isConnected = NetworkUtils.isConnectedOrConnecting(context);
            boolean isWifi = NetworkUtils.isWifiNetwork(context);
            boolean isMobile = NetworkUtils.isMobileNetwork(context);
            boolean isFast = NetworkUtils.isConnectionFast(context);

            NetworkStatus status = new NetworkStatus(isConnected, isWifi, isMobile, isFast);

            ConnectivityMonitor.getInstance()
                    .publishNetworkStatus(status);

            return Result.SUCCESS;
        } catch (UnresolvedNetworkException e) {
            e.printStackTrace();
            return Result.FAILURE;
        }
    }
}
