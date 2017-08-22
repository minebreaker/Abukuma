package rip.deadcode.abukuma3.route.matcher;

import lombok.Value;

import java.util.Map;

@Value
public final class MatchResult {

    private boolean matched;
    private Map<String, String> parameters;

}
