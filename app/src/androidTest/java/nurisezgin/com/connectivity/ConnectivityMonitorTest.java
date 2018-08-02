package nurisezgin.com.connectivity;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by nuri on 02.08.2018
 */
@RunWith(AndroidJUnit4.class)
public class ConnectivityMonitorTest {

    private Context ctx;
    private ConnectivityMonitor monitor;

    @Before
    public void setUp_() {
        ctx = InstrumentationRegistry.getTargetContext();
        monitor = ConnectivityMonitor.getInstance();
    }

    @After
    public void tearDown_() {
        monitor.stopProfiling();
    }

    @Test
    public void should_StartProfilingCorrect() throws InterruptedException {
        monitor.registerAppContext(() -> ctx);

        BlockingQueue<NetworkStatus> queue = new LinkedBlockingQueue<>(1);
        monitor.addWatcher(status -> queue.offer(status));

        monitor.startProfiling();
        NetworkStatus status = queue.poll(1, TimeUnit.SECONDS);

        boolean isConnected = false;

        try {
            isConnected = NetworkUtils.isConnectedOrConnecting(ctx);
        } catch (UnresolvedNetworkException e) { }

        if (isConnected) {
            assertThat(status).isNotNull();
        } else {
            assertThat(status).isNull();
        }
    }

    @Test
    public void should_SendTemporaryStatusCorrect() throws Exception {
        Consumer<NetworkStatus> mockConsumer = mock(Consumer.class);
        monitor.publishNetworkStatus(new NetworkStatus(false, false, false, false));

        monitor.addWatcher(mockConsumer);

        verify(mockConsumer).accept(any());
    }

    @Test
    public void should_SendUniqueStatusCorrect() throws Exception {
        Consumer<NetworkStatus> mockConsumer = mock(Consumer.class);
        monitor.addWatcher(mockConsumer);

        monitor.publishNetworkStatus(new NetworkStatus(false, false, false, false));
        monitor.publishNetworkStatus(new NetworkStatus(false, false, false, false));
        monitor.publishNetworkStatus(new NetworkStatus(false, false, false, false));
        monitor.publishNetworkStatus(new NetworkStatus(false, false, false, false));

        verify(mockConsumer).accept(any());
    }
}