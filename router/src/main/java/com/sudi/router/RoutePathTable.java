package com.sudi.router;

import java.util.Map;

/**
 * Created by sudi on 2017/11/20.
 * Email：sudi@yiche.com
 */

public interface RoutePathTable {
    void handle(Map<String, Class<?>> map);
}
