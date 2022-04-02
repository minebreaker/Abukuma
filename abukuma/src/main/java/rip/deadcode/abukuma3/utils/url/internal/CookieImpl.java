package rip.deadcode.abukuma3.utils.url.internal;

import rip.deadcode.abukuma3.value.Cookie;
import rip.deadcode.abukuma3.value.internal.SerializeCookie;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.OptionalInt;

import static com.google.common.base.Preconditions.checkNotNull;


public final class CookieImpl implements Cookie {

    public CookieImpl(
            String name,
            String value
    ) {
        this.name = checkNotNull( name );
        this.value = checkNotNull( value );
        this.secure = false;
        this.httpOnly = false;
    }

    public CookieImpl(
            String name,
            String value,
            @Nullable Integer maxAge,
            @Nullable String domain,
            @Nullable String path,
            boolean secure,
            boolean httpOnly,
            @Nullable String sameSite
    ) {
        this.name = checkNotNull( name );
        this.value = checkNotNull( value );
        this.maxAge = maxAge;
        this.domain = domain;
        this.path = path;
        this.secure = secure;
        this.httpOnly = httpOnly;
        this.sameSite = sameSite;
    }

    private CookieImpl copy() {
        return new CookieImpl( name, value, maxAge, domain, path, secure, httpOnly, sameSite );
    }

    private String name;

    @Override
    public String name() {
        return name;
    }

    @Override
    public CookieImpl name( String name ) {
        checkNotNull( name );
        CookieImpl copy = copy();
        copy.name = name;
        return copy;
    }

    private String value;

    @Override
    public String value() {
        return value;
    }

    @Override
    public CookieImpl value(
            String value
    ) {
        checkNotNull( value );
        CookieImpl copy = copy();
        copy.value = value;
        return copy;
    }

    @Nullable private Integer maxAge;

    @Override
    public OptionalInt maxAge() {
        return maxAge == null ? OptionalInt.empty() : OptionalInt.of( maxAge );

    }

    @Override
    public CookieImpl maxAge(
            Integer maxAge
    ) {
        checkNotNull( maxAge );
        CookieImpl copy = copy();
        copy.maxAge = maxAge;
        return copy;
    }

    @Nullable private String domain;

    @Override
    public Optional<String> domain() {
        return Optional.ofNullable( domain );
    }

    @Override
    public CookieImpl domain(
            String domain
    ) {
        checkNotNull( domain );
        CookieImpl copy = copy();
        copy.domain = domain;
        return copy;
    }

    @Nullable private String path;

    @Override
    public Optional<String> path() {
        return Optional.ofNullable( path );
    }

    @Override
    public CookieImpl path(
            String path
    ) {
        checkNotNull( path );
        CookieImpl copy = copy();
        copy.path = path;
        return copy;
    }

    private boolean secure;

    @Override
    public boolean secure() {
        return secure;
    }

    @Override
    public CookieImpl secure(
            boolean secure
    ) {

        CookieImpl copy = copy();
        copy.secure = secure;
        return copy;
    }

    private boolean httpOnly;

    @Override
    public boolean httpOnly() {
        return httpOnly;
    }

    @Override
    public CookieImpl httpOnly(
            boolean httpOnly
    ) {

        CookieImpl copy = copy();
        copy.httpOnly = httpOnly;
        return copy;
    }

    @Nullable private String sameSite;

    @Override public Optional<String> sameSite() {
        return Optional.ofNullable( sameSite );
    }

    @Override public CookieImpl sameSite(
            String sameSite
    ) {
        checkNotNull( sameSite );
        CookieImpl copy = copy();
        copy.sameSite = sameSite;
        return copy;
    }

    @Override public String toString() {
        // FIXME: Should not use toString()
        return SerializeCookie.serialize( this );
    }
}
