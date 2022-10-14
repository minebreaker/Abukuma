package rip.deadcode.abukuma3.netty.internal.value;

import io.netty.channel.ChannelHandlerContext;
import rip.deadcode.abukuma3.collection.PersistentMap;
import rip.deadcode.abukuma3.collection.PersistentMultimap;
import rip.deadcode.abukuma3.netty.internal.NettyHandler;
import rip.deadcode.abukuma3.value.Request;
import rip.deadcode.abukuma3.value.RequestHeader;

import java.net.URI;
import java.util.Optional;


public class NettyRequest<T> implements Request<T> {

    private final T body;

    private final RequestHeader header;
    private final NettyHandler.RequestAndContent nettyRequest;
    private final ChannelHandlerContext rawResponse;
    private final PersistentMap<String, String> pathParams;

    public NettyRequest(
            T body,
            RequestHeader header,
            NettyHandler.RequestAndContent nettyRequest,
            ChannelHandlerContext rawResponse,
            PersistentMap<String, String> pathParams ) {

        this.body = body;
        this.header = header;
        this.nettyRequest = nettyRequest;
        this.rawResponse = rawResponse;
        this.pathParams = pathParams;
    }

    @Override public T body() {
        return body;
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

    @Override public NettyHandler.RequestAndContent rawRequest() {
        return nettyRequest;
    }

    @Override public ChannelHandlerContext rawResponse() {
        return rawResponse;
    }
}
