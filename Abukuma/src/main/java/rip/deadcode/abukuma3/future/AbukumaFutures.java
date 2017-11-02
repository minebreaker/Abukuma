package rip.deadcode.abukuma3.future;

import com.twitter.util.FuturePool;
import com.twitter.util.FuturePools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// TODO
public final class AbukumaFutures {

    private AbukumaFutures() {
        ExecutorService ioExec = Executors.newFixedThreadPool(8);
        io = FuturePools.newFuturePool(ioExec);
    }

    public static AbukumaFutures getInstance() {
        return new AbukumaFutures();
    }

    private FuturePool io;

    public FuturePool io() {
        return io;
    }

}
