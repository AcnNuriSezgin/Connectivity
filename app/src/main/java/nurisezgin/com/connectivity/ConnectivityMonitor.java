package nurisezgin.com.connectivity;

import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.concurrent.TimeUnit;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by nuri on 02.08.2018
 */
public final class ConnectivityMonitor {

    private static final String TAG = "ConnectivityMonitor";
    private BehaviorRelay<NetworkStatus> publisher = BehaviorRelay.create();
    private AppContextProvider appContextProvider;

    private ConnectivityMonitor() { }

    private static final class CREATOR {
        private static final ConnectivityMonitor INSTANCE = new ConnectivityMonitor();
    }

    public static ConnectivityMonitor getInstance() {
        return CREATOR.INSTANCE;
    }

    public void registerAppContext(AppContextProvider contextProvider) {
        this.appContextProvider = contextProvider;
    }

    private void checkContext() {
        if (appContextProvider == null || appContextProvider.getContext() == null) {
            throw new IllegalStateException("ConnectivityMonitor, startProfiling, call registerAppContext before");
        }
    }

    public void startProfiling() {
        checkContext();

        WorkManager workManager = WorkManager.getInstance();
        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(
                ConnectivityStateWorker.class, 5, TimeUnit.SECONDS)
                .build();

        workManager.enqueueUniquePeriodicWork(TAG, ExistingPeriodicWorkPolicy.REPLACE, request);
    }

    public void stopProfiling() {
        checkContext();

        WorkManager workManager = WorkManager.getInstance();
        workManager.cancelAllWorkByTag(TAG);
    }

    AppContextProvider getAppContextProvider() {
        return appContextProvider;
    }

    void publishNetworkStatus(NetworkStatus networkStatus) {
        publisher.accept(networkStatus);
    }

    public Disposable addWatcher(Consumer<NetworkStatus> onNext) {
        return publisher.distinctUntilChanged((f, s) -> f.same(s))
                .subscribe(onNext, throwable -> throwable.printStackTrace());
    }
}
