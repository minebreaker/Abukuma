package rip.deadcode.abukuma3.route.matcher;

public interface Matcher {

    public MatchResult matches(String pattern, String url);

}
