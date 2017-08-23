package rip.deadcode.abukuma3.route.matcher;

import com.google.common.collect.ImmutableMap;
import rip.deadcode.abukuma3.route.matcher.external.AntPathMatcher;

import java.util.Map;

/**
 * Ant style matcher.
 */
public final class AntMatcher implements Matcher {

    private final AntPathMatcher matcher;

    public AntMatcher() {
        matcher = new AntPathMatcher();
        matcher.setCachePatterns(false);  // TODO どの設定がいいかテスト
    }

    @Override
    public MatchResult matches(String pattern, String url) {
        if (!matcher.match(pattern, url)) {
            return new MatchResult(false, null);
        }

        Map<String, String> pathVariables = matcher.extractUriTemplateVariables(pattern, url);

        return new MatchResult(true, ImmutableMap.copyOf(pathVariables));
    }

}
