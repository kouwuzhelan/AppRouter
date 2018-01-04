package com.sudi.router;

import java.util.Map;

/**
 * Created by sudi on 2017/11/20.
 * Email：sudi@yiche.com
 */

public interface InterceptorTable {
    void handle(Map<String, Class<? extends RouteInterceptor>> map);
}
