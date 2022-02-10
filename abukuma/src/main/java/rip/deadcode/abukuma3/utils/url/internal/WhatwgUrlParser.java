package rip.deadcode.abukuma3.utils.url.internal;


import com.google.common.base.Ascii;
import rip.deadcode.abukuma3.Mutable;
import rip.deadcode.abukuma3.collection.PersistentCollections;
import rip.deadcode.abukuma3.collection.PersistentList;
import rip.deadcode.abukuma3.utils.url.UrlModel;
import rip.deadcode.abukuma3.utils.url.UrlParseException;
import rip.deadcode.abukuma3.utils.url.UrlParseException.InvalidCharacter;
import rip.deadcode.abukuma3.utils.url.UrlParseResult;
import rip.deadcode.abukuma3.utils.url.UrlParser;
import rip.deadcode.abukuma3.utils.url.UrlQuery;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;


/**
 * URL parser that is (almost) compatible with
 * <a href="https://url.spec.whatwg.org/#concept-basic-url-parser">WHATWG basic URL parser</a>.
 *
 * <ul>
 *     <li>Always assumes the input url is special</li>
 *     <li>Does not support state override</li>
 * </ul>
 */
// TODO: consistent error message
// TODO: rewrite for unicode
public final class WhatwgUrlParser implements UrlParser {

    @Override public UrlParseResult parse( String url, @Nullable UrlModel baseUrl ) {
        return parseStatic( url, baseUrl );
    }

    public static UrlParseResult parseStatic( String url, @Nullable UrlModel baseUrl ) {
        State s = new State( url, url, baseUrl );
        State result = preconditions( s );
        if ( result.successful ) {
            return new UrlParseResultImpl.SuccessImpl( result.url.freeze(), result.validationErrors );
        } else {
            return new UrlParseResultImpl.ErrorImpl( result.validationErrors );
        }
    }

    private static State preconditions( State state ) {
        return state
                // Remove leading whitespaces
                .replaceAllInputOn( leadingC0ControlOrSpace, () -> "Cannot have leading whitespaces" )
                // Remove trailing whitespaces
                .replaceAllInputOn( trailingC0ControlOrSpace, () -> "Cannot have trailing whitespaces" )
                // Remove tabs/newlines
                .replaceAllInputOn( asciiTabOrNewline, () -> "The url contains invalid tabs or newlines" );
    }

    private static State parseSchemeStart( State state ) {
        char c = state.c();
        if ( isAsciiAlpha( c ) ) {
            return state
                    .appendBufferLowerCased( c )
                    .incAndMove( WhatwgUrlParser::parseScheme );
        } else {
            return state.decAndMove( WhatwgUrlParser::parseNoScheme );
        }
    }

    private static State parseScheme( State state ) {
        char c = state.c();
        if ( isAsciiAlphaNumeric( c ) || c == '+' || c == '-' || c == '.' ) {
            return state.appendBuffer( c )
                        .incAndMove( WhatwgUrlParser::parseScheme );
        } else if ( c == ':' ) {
            State newState = state.url( state.url.scheme( state.buffer.toString() ) )
                                  .clearBuffer();

            if ( Objects.equals( newState.url.scheme, "file" ) ) {
                if ( !newState.remainingStartsWith( "//" ) ) {
                    return newState.addValidationError(
                                           new UrlParseException( "Missing or invalid slashes: " + newState.originalUrl ) )
                                   .incAndMove( WhatwgUrlParser::parseFile );
                } else {
                    return newState.incAndMove( WhatwgUrlParser::parseFile );
                }
            } else if ( state.baseUrl != null &&
                        Objects.equals( state.url.scheme, state.baseUrl.scheme().orElse( null ) ) ) {
                return newState.incAndMove( WhatwgUrlParser::parseSpecialRelativeOrAuthority );
            } else {
                return newState.incAndMove( WhatwgUrlParser::parseSpecialAuthoritySlashes );
            }
        } else {
            return state.clearBuffer()
                        // The spec is a bit ambiguous about the pointer
                        // (Insisting 'start over (from the first code point in input)' but no mention for the pointer)
                        // We assume resetting the pointer.
                        .resetPointer()
                        .decAndMove( WhatwgUrlParser::parseNoScheme );
        }
    }

    private static State parseNoScheme( State state ) {
        if ( state.baseUrl == null ) {
            return state.addValidationError( new UrlParseException( "Invalid format: " + state.originalUrl ) );
        } else if ( state.baseUrl.scheme().map( s -> !Objects.equals( s, "file" ) ).orElse( true ) ) {
            return state.decAndMove( WhatwgUrlParser::parseRelative );
        } else {
            return state.decAndMove( WhatwgUrlParser::parseFile );
        }
    }

    private static State parseSpecialRelativeOrAuthority( State state ) {
        if ( state.c() == 0x2f && state.remainingStartsWith( "/" ) ) { // /
            return state.inc().incAndMove( WhatwgUrlParser::parseSpecialAuthorityIgnoreSlashes );
        } else {
            return state
                    .addValidationError( new UrlParseException( "Lacks slashes" ) )
                    .decAndMove( WhatwgUrlParser::parseRelative );
        }
    }

    private static State parseRelative( State state ) {
        State state0 = state.url( ( u, b ) -> u.scheme( b.scheme().orElse( null ) ) );
        char c = state.c();

        if ( c == '/' ) {
            return state0.incAndMove( WhatwgUrlParser::parseRelativeSlash );
        } else if ( c == '\\' ) { // \
            return state0
                    .addValidationError(
                            new UrlParseException.InvalidCharacter( "Invalid backslash: " + state.originalUrl ) )
                    .incAndMove( WhatwgUrlParser::parseRelativeSlash );
        } else {
            State state1 = state0.url( ( u, b ) -> u.username( b.username().orElse( null ) )
                                                    .password( b.password().orElse( null ) )
                                                    .host( b.host().orElse( null ) )
                                                    .port( b.port().orElse( -1 ) )
                                                    .path( b.path() )
                                                    .query( b.query().orElse( null ) ) );
            if ( c == '?' ) {
                return state1
                        // We should set empty string according to the spec, but leave it for the query parser.
                        .url( state1.url.query( null ) )
                        .incAndMove( WhatwgUrlParser::parseQuery );
            } else if ( c == '#' ) {
                return state1
                        .url( state1.url.fragment( "" ) )
                        .incAndMove( WhatwgUrlParser::parseFragment );
            } else if ( !state1.eof() ) {
                return state1
                        .url( state1.url.query( null ) )
                        .shorten()
                        .decAndMove( WhatwgUrlParser::parsePath );
            } else {
                return state1;
            }
        }
    }

    private static State parseRelativeSlash( State state ) {
        char c = state.c();
        if ( c == '/' ) {
            return state.incAndMove( WhatwgUrlParser::parseSpecialAuthorityIgnoreSlashes );
        } else if ( c == '\\' ) {
            return state
                    .addValidationError( new InvalidCharacter( "Invalid backslash: " + state.originalUrl ) )
                    .incAndMove( WhatwgUrlParser::parseSpecialAuthorityIgnoreSlashes );
        } else {
            return state
                    .url( ( u, b ) -> u.username( b.username().orElse( null ) )
                                       .password( b.password().orElse( null ) )
                                       .host( b.host().orElse( null ) )
                                       .port( b.port().orElse( -1 ) )
                                       .path( b.path() ) )
                    .decAndMove( WhatwgUrlParser::parsePath );
        }
    }

    private static State parseSpecialAuthoritySlashes( State state ) {
        if ( state.c() == '/' && state.remainingStartsWith( "/" ) ) {
            return state.incAndMove( WhatwgUrlParser::parseSpecialAuthorityIgnoreSlashes );
        } else {
            return state
                    .addValidationError( new UrlParseException( "Slash expected." ) )
                    .decAndMove( WhatwgUrlParser::parseSpecialAuthorityIgnoreSlashes );
        }
    }

    private static State parseSpecialAuthorityIgnoreSlashes( State state ) {
        char c = state.c();
        if ( c != '/' && c != '\\' ) {
            return state.incAndMove( WhatwgUrlParser::parseAuthority );
        } else {
            return state.addValidationError( new UrlParseException( "Slash or backslash expected." ) )
                        .incAndMove( WhatwgUrlParser::parseSpecialAuthorityIgnoreSlashes );
        }
    }

    private static State parseAuthority( State state ) {
        char c = state.c();
        if ( c == '@' ) {
            return state.addValidationError( new InvalidCharacter( "Unexpected @." ) )
                        .prependBuffer( "%40" )
                        .setAtSignSeen()
                        .forEachCodePointInBuffer( ( s, cp ) -> {
                            if ( cp == ':' && !s.passwordTokenSeen ) {
                                return s.setPasswordTokenSeen();
                            } else {
                                String encodedCodePoints = urf8PercentEncode( cp );
                                if ( s.passwordTokenSeen ) {
                                    return s.url( ( url, base ) -> url.appendPassword( encodedCodePoints ) );
                                } else {
                                    return s.url( ( url, base ) -> url.appendUsername( encodedCodePoints ) );
                                }
                            }
                        } )
                        .clearBuffer();
        } else if ( state.eof() || c == '/' || c == '?' || c == '#' || c == '\\' ) {
            if ( state.atSignSeen && state.isBufferEmpty() ) {
                return state.addValidationError( new UrlParseException( "Invalid character." ) );
            } else {
                return state.dec( state.buffer.length() + 1 ).move( WhatwgUrlParser::parseHost );
            }
        } else {
            return state.appendBuffer( c ).incAndMove( WhatwgUrlParser::parseAuthority );
        }
    }

    private static State parseHost( State state ) {
            return state.next(WhatwgUrlParser::parseHost, WhatwgUrlParser::parseHostname);
    }

    private static State parseHostname( State state ) {
        char c = state.c();
        if (c == ':' && state.insideBracket) {
            if (state.isBufferEmpty()) {
                return state.addValidationError( new UrlParseException( "Unexpected :." ) );
            }



        }

        throw new UnsupportedOperationException();  // TODO
    }

    private static State parseFile( State state ) {
        throw new UnsupportedOperationException();  // TODO
    }

    private static State parsePath( State state ) {
        throw new UnsupportedOperationException();  // TODO
    }

    private static State parseQuery( State state ) {
        throw new UnsupportedOperationException();  // TODO
    }

    private static State parseFragment( State state ) {
        throw new UnsupportedOperationException();  // TODO
    }

    private static String urf8PercentEncode( char cp ) {
        // userinfo percent-encode set
        throw new UnsupportedOperationException();  // TODO
    }

    private static final Pattern leadingC0ControlOrSpace = Pattern.compile( "^[\\u0000-\\u001f\\u0020]+" );
    private static final Pattern trailingC0ControlOrSpace = Pattern.compile( "[\\u0000-\\u001f\\u0020]+$" );
    private static final Pattern asciiTabOrNewline = Pattern.compile( "[\\u0009\\u000a\\u000d]" );

    private static boolean isAsciiDigit( char c ) {
        return 0x30 <= c && c <= 0x39;
    }

    private static boolean isAsciiAlpha( char c ) {
        return ( 0x41 <= c && c <= 0x51 ) || ( 0x61 <= c && c <= 0x71 );
    }

    private static boolean isAsciiAlphaNumeric( char c ) {
        return isAsciiDigit( c ) || isAsciiAlpha( c );
    }

    /**
     * We use mutable state for performance
     */
    @Mutable
    private static final class State {
        private boolean successful;
        /** Resulting url */
        private MutableUrlModel url;
        private final String originalUrl;
        private String input;
        @Nullable private UrlModel baseUrl;
        private int pointer;
        private final StringBuilder buffer;
        private boolean atSignSeen;
        private boolean insideBracket;
        private boolean passwordTokenSeen;
        private PersistentList<UrlParseException> validationErrors;

        public State( String originalUrl, String input, @Nullable UrlModel baseUrl ) {
            this.successful = false;
            this.url = new MutableUrlModel();
            this.originalUrl = originalUrl;
            this.baseUrl = baseUrl;
            this.input = input;
            this.pointer = 0;
            this.buffer = new StringBuilder();
            this.atSignSeen = false;
            this.validationErrors = PersistentCollections.createList();
        }

        private State successful() {
            this.successful = true;
            return this;
        }

        private State url( MutableUrlModel url ) {
            this.url = url;
            return this;
        }

        private State url( BiFunction<MutableUrlModel, UrlModel, MutableUrlModel> url ) {
            if ( this.baseUrl != null ) {
                this.url = url.apply( this.url, this.baseUrl );
            }
            return this;
        }

        private State input( String input ) {
            this.input = input;
            return this;
        }

        // Assuming the url does not contain non-ascii
        private char c() {
            if ( eof() ) {
                return (char) -1;  // Uses -1 for the EOF
            } else {
                return input.charAt( pointer );
            }
        }

        private boolean eof() {
            return pointer >= input.length();
        }

        private State resetPointer() {
            this.pointer = 0;
            return this;
        }

        private boolean remainingStartsWith( String s ) {
            int pRem = pointer + 1;
            if ( pRem < input.length() ) {  // Ensure we have the remaining
                return input.substring( pRem ).startsWith( s );
            } else {
                return false;
            }
        }

        private State prependBuffer( String s ) {
            buffer.insert( 0, s );
            return this;
        }

        private State appendBuffer( char c ) {
            buffer.append( c );
            return this;
        }

        private State appendBufferLowerCased( char c ) {
            buffer.append( Ascii.toLowerCase( c ) );
            return this;
        }

        @FunctionalInterface
        private interface BufferF {
            public State apply( State s, char cp );
        }

        private State forEachCodePointInBuffer( BufferF f ) {
            return forEachCodePointInBuffer( this, f, 0 );
        }

        private static State forEachCodePointInBuffer( State s, BufferF f, int i ) {
            if ( i >= s.buffer.length() ) {
                return s;
            }

            return forEachCodePointInBuffer( f.apply( s, s.buffer.charAt( i ) ), f, i + 1 );
        }

        private boolean isBufferEmpty() {
            return buffer.length() == 0;
        }

        private State clearBuffer() {
            buffer.setLength( 0 );
            return this;
        }

        private State setAtSignSeen() {
            this.atSignSeen = true;
            return this;
        }

        private State setInsideBrackets() {
            this.insideBracket = true;
            return this;
        }

        private State setPasswordTokenSeen() {
            this.passwordTokenSeen = true;
            return this;
        }

        private State replaceAllInputOn( Pattern p, Supplier<String> onError ) {
            int l = input.length();
            State newState = this.input( p.matcher( input ).replaceAll( "" ) );
            if ( l != newState.input.length() ) {
                // TODO should report erroneous chars and indexes
                return newState.addValidationError( new InvalidCharacter(
                        onError.get() + ": " + newState.originalUrl ) );
            } else {
                return newState;
            }
        }

        private State shorten() {
            throw new UnsupportedOperationException();  // TODO
        }

        private State addValidationError( UrlParseException e ) {
            this.validationErrors = validationErrors.addLast( e );
            return this;
        }

        private State inc() {
            this.pointer++;
            return this;
        }

        private State dec() {
            this.pointer--;
            return this;
        }

        private State dec( int count ) {
            this.pointer -= count;
            return this;
        }

        private State incAndMove( UnaryOperator<State> op ) {
            if ( eof() ) {
                return this;
            }
            return op.apply( this.inc() );
        }

        private State move( UnaryOperator<State> op ) {
            return op.apply( this );
        }

        private State decAndMove( UnaryOperator<State> op ) {
            return op.apply( this.dec() );
        }

        private State next( UnaryOperator<State> thisOp, UnaryOperator<State> next ) {
            if ( eof() ) {
                return next.apply( this );
            }

            return thisOp.apply( this );
        }
    }

    @Mutable
    private static final class MutableUrlModel {

        private @Nullable String scheme;
        private @Nullable StringBuilder username;
        private @Nullable StringBuilder password;
        private @Nullable String host;
        /** Negative number for the empty port. */
        private int port = -1;
        /** We don't assume path is opaque since we always expect special schemes */
        private PersistentList<String> path = PersistentCollections.createList();
        private @Nullable UrlQuery query;
        @Nullable String fragment;

        private MutableUrlModel scheme( @Nullable String scheme ) {
            this.scheme = scheme;
            return this;
        }

        private MutableUrlModel scheme( StringBuilder scheme ) {
            this.scheme = scheme.toString();
            return this;
        }

        private MutableUrlModel username( @Nullable String username ) {
            if ( username == null ) {
                this.username = null;
            } else if ( this.username == null ) {
                this.username = new StringBuilder( username );
            } else {
                this.username.append( username );
            }
            return this;
        }

        private MutableUrlModel appendUsername( String username ) {
            if ( this.username == null ) {
                this.username = new StringBuilder( username );
            } else {
                this.username.append( username );
            }
            return this;
        }

        private MutableUrlModel password( @Nullable String password ) {
            if ( password == null ) {
                this.password = null;
            } else if ( this.password == null ) {
                this.password = new StringBuilder( password );
            } else {
                this.password.append( password );
            }
            return this;
        }

        private MutableUrlModel appendPassword( String password ) {
            if ( this.password == null ) {
                this.password = new StringBuilder( password );
            } else {
                this.password.append( password );
            }
            return this;
        }

        private MutableUrlModel host( @Nullable String host ) {
            this.host = host;
            return this;
        }

        private MutableUrlModel port( @Nullable int port ) {
            this.port = port;
            return this;
        }

        private MutableUrlModel path( PersistentList<String> path ) {
            this.path = path;
            return this;
        }

        private MutableUrlModel query( @Nullable UrlQuery query ) {
            this.query = query;
            return this;
        }

        private MutableUrlModel fragment( @Nullable String fragment ) {
            this.fragment = fragment;
            return this;
        }

        private UrlModel freeze() {
            return new UrlModelImpl(
                    this.scheme,
                    this.username == null ? null : this.username.toString(),
                    this.password == null ? null : this.password.toString(),
                    this.host,
                    this.port,
                    this.path,
                    this.query,
                    this.fragment
            );
        }
    }
}
