package rip.deadcode.abukuma3.renderer;

import rip.deadcode.abukuma3.internal.utils.CheckedConsumer;
import rip.deadcode.abukuma3.value.AbuResponse;

import java.io.OutputStream;
import java.util.function.Supplier;


public final class AbuRenderingResult {

    private CheckedConsumer<OutputStream, Exception> rendering;
    private Supplier<AbuResponse> modifying;

    public AbuRenderingResult(
            CheckedConsumer<OutputStream, Exception> rendering,
            Supplier<AbuResponse> modifying ) {
        this.rendering = rendering;
        this.modifying = modifying;
    }

    public CheckedConsumer<OutputStream, Exception> rendering() {
        return rendering;
    }

    public Supplier<AbuResponse> modifying() {
        return modifying;
    }
}
