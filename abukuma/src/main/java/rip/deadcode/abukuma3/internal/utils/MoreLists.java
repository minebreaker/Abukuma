package rip.deadcode.abukuma3.internal.utils;

import java.util.List;

import static com.google.common.base.Preconditions.checkState;

public final class MoreLists {

    private MoreLists() {}

    public static <T> T first( List<T> list ) {
        checkState( !list.isEmpty() );
        return list.get( 0 );
    }

    public static <T> T last( List<T> list ) {
        checkState( !list.isEmpty() );
        return list.get( list.size() - 1 );
    }
}
