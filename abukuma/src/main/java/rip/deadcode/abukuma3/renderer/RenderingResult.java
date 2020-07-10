package rip.deadcode.abukuma3.renderer;

import rip.deadcode.abukuma3.internal.utils.CheckedConsumer;
import rip.deadcode.abukuma3.value.Response;

import java.io.OutputStream;
import java.util.function.Supplier;


public final class RenderingResult {

    private CheckedConsumer<OutputStream, Exception> rendering;
    private Supplier<Response> modifying;

    public RenderingResult(
            CheckedConsumer<OutputStream, Exception> rendering,
            Supplier<Response> modifying ) {
        this.rendering = rendering;
        this.modifying = modifying;
    }

    public CheckedConsumer<OutputStream, Exception> rendering() {
        return rendering;
    }

    public Supplier<Response> modifying() {
        return modifying;
    }
}
