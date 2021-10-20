package rip.deadcode.abukuma3.netty.internal.value;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import rip.deadcode.abukuma3.ExecutionContext;
import rip.deadcode.abukuma3.collection.PersistentMap;
import rip.deadcode.abukuma3.collection.PersistentMultimap;
import rip.deadcode.abukuma3.netty.internal.NettyHandler;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.RequestHeader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;


public class NettyRequest implements Request {

    private final ExecutionContext context;
    private final RequestHeader header;
    private final NettyHandler.RequestAndContent nettyRequest;
    private final ChannelHandlerContext rawResponse;
    private final PersistentMap<String, String> pathParams;

    public NettyRequest(
            ExecutionContext context,
            RequestHeader header,
            NettyHandler.RequestAndContent nettyRequest,
            ChannelHandlerContext rawResponse,
            PersistentMap<String, String> pathParams ) {

        this.context = context;
        this.header = header;
        this.nettyRequest = nettyRequest;
        this.rawResponse = rawResponse;
        this.pathParams = pathParams;
    }

    @SuppressWarnings( "unchecked" )
    @Override public <T> T body( Class<T> cls ) {

        ByteBuf buf = nettyRequest.content.content();
        byte[] arr = new byte[buf.readableBytes()];
        buf.getBytes( buf.readerIndex(), arr );

        try ( InputStream is = new ByteArrayInputStream( arr ) ) {
            Object result = context.parser().parse( cls, is, header );
            checkNotNull( result, "Could not find an appropriate parser for the type '%s'.", cls );
            checkState(
                    cls.isInstance( result ),
                    "An illegal instance '%s' of type '%s' was returned by the parser for the request '%s'. This may be caused by a bug of the parsers.",
                    result,
                    result.getClass(),
                    cls
            );
            return (T) result;

        } catch ( IOException e ) {
            throw new UncheckedIOException( e );
        }
    }

    @Override public String method() {
        return header.method();
    }

    @Override public URI url() {
        return header.url();
    }

    @Override public String urlString() {
        return header.urlString();
    }

    @Override public RequestHeader header() {
        return header;
    }

    @Override public Optional<String> pathParam( String key ) {
        return Optional.ofNullable( pathParams.get( key ) );
    }

    @Override public PersistentMap<String, String> pathParams() {
        return pathParams;
    }

    @Override public Optional<String> queryParam( String key ) {
        return Optional.empty();
    }

    @Override public PersistentMultimap<String, String> queryParams() {
        return null;
    }

    @Override public Optional<String> host() {
        return Optional.empty();
    }

    @Override public String remoteAddress() {
        return null;
    }

    @Override public NettyHandler.RequestAndContent rawRequest() {
        return nettyRequest;
    }

    @Override public ChannelHandlerContext rawResponse() {
        return rawResponse;
    }
}
