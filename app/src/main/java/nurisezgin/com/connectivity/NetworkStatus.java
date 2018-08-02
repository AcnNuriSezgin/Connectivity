package nurisezgin.com.connectivity;

/**
 * Created by nuri on 02.08.2018
 */
public final class NetworkStatus {

    private boolean isConnected;
    private boolean isWifi;
    private boolean isMobile;
    private boolean isFast;

    NetworkStatus(boolean isConnected, boolean isWifi, boolean isMobile, boolean isFast) {
        this.isConnected = isConnected;
        this.isWifi = isWifi;
        this.isMobile = isMobile;
        this.isFast = isFast;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public boolean isWifi() {
        return isWifi;
    }

    public boolean isMobile() {
        return isMobile;
    }

    public boolean isFast() {
        return isFast;
    }

    boolean same(NetworkStatus status) {
        return isConnected == status.isConnected
                && isWifi == status.isWifi
                && isMobile == status.isMobile
                && isFast == status.isFast;
    }
}
