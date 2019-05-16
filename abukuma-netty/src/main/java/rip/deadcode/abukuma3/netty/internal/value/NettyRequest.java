package rip.deadcode.abukuma3.netty.internal.value;

import com.google.common.collect.Multimap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import rip.deadcode.abukuma3.AbuExecutionContext;
import rip.deadcode.abukuma3.netty.internal.NettyHandler;
import rip.deadcode.abukuma3.value.AbuRequest;
import rip.deadcode.abukuma3.value.AbuRequestHeader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;


public class NettyRequest implements AbuRequest {

    private final AbuExecutionContext context;
    private final AbuRequestHeader header;
    private final NettyHandler.RequestAndContent nettyRequest;
    private final ChannelHandlerContext rawResponse;
    private final Map<String, String> pathParams;

    public NettyRequest(
            AbuExecutionContext context,
            AbuRequestHeader header,
            NettyHandler.RequestAndContent nettyRequest,
            ChannelHandlerContext rawResponse,
            Map<String, String> pathParams ) {

        this.context = context;
        this.header = header;
        this.nettyRequest = nettyRequest;
        this.rawResponse = rawResponse;
        this.pathParams = pathParams;
    }

    @Override
    public <T> T body( Class<T> cls ) {
//        buf.toString(buf.readerIndex(), buf.readableBytes(), charsetName)
        ByteBuf buf = nettyRequest.content.content();
        byte[] arr = new byte[buf.readableBytes()];
        buf.getBytes( buf.readerIndex(), arr );

        try ( InputStream is = new ByteArrayInputStream( arr ) ) {
            Object result = context.parser().parse( cls, is, header );
            checkNotNull( result, "Could not find an appropriate parser for the type '%s'.", cls );
            checkState(
                    cls.isInstance( result ),
                    "Illegal instance '%s' of type '%s' was returned by the parser for the request '%s'. This may be caused by a bug of the parsers.",
                    result,
                    result.getClass(),
                    cls
            );
            return (T) result;

        } catch ( IOException e ) {
            throw new UncheckedIOException( e );
        }
    }

    @Override
    public AbuExecutionContext context() {
        return context;
    }

    @Override
    public String method() {
        return header.method();
    }

    @Override
    public URL url() {
        return header.url();
    }

    @Override
    public String requestUri() {
        return header.requestUri();
    }

    @Override
    public AbuRequestHeader header() {
        return header;
    }

    @Override
    public Optional<String> pathParam( String key ) {
        return Optional.ofNullable( pathParams.get( key ) );
    }

    @Override
    public Map<String, String> pathParams() {
        return pathParams;
    }

    @Override
    public Optional<String> queryParam( String key ) {
        return Optional.empty();
    }

    @Override
    public Multimap<String, String> queryParams() {
        return null;
    }

    @Override
    public Optional<String> host() {
        return Optional.empty();
    }

    @Override
    public String remoteAddress() {
        return null;
    }

    @Override
    public NettyHandler.RequestAndContent rawRequest() {
        return nettyRequest;
    }

    @Override
    public ChannelHandlerContext rawResponse() {
        return rawResponse;
    }
}
