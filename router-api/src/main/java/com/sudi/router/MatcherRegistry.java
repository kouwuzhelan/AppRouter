package com.sudi.router;

import com.sudi.router.matcher.AbsMatcher;
import com.sudi.router.matcher.PathMatcher;
import com.sudi.router.matcher.RouteInfoMatcher;
import com.sudi.router.matcher.SchemeMatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sudi on 2017/11/20.
 * Email：sudi@yiche.com
 */

public class MatcherRegistry {
    private static final List<AbsMatcher> registry = new ArrayList<>();

    static {
        registry.add(new RouteInfoMatcher(0x1000));
//        registry.add(new PathMatcher(0x1000));
//        registry.add(new SchemeMatcher(0x0100));
//        registry.add(new ImplicitMatcher(0x0010));
//        registry.add(new BrowserMatcher(0x0000));
        Collections.sort(registry);
    }

    public static void register(AbsMatcher matcher) {
        registry.add(matcher);
        Collections.sort(registry);
    }

    public static List<AbsMatcher> getMatcher() {
        return registry;
    }

    public static void clear() {
        registry.clear();
    }
}
