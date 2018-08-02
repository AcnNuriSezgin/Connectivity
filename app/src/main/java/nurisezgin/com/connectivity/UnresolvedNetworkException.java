package nurisezgin.com.connectivity;

/**
 * Created by nuri on 02.08.2018
 */
public class UnresolvedNetworkException extends Exception {

    public UnresolvedNetworkException() {
        super("Unresolved network info, network info is null");
    }
}
