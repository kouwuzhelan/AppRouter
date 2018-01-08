package com.sudi.router;

import com.sudi.route.annotation.model.RouteInfo;

import java.util.Map;

/**
 * Created by sudi on 2017/11/20.
 * Email：sudi@yiche.com
 */

public interface RouteInfoTable {
    void handle(Map<String, RouteInfo> map);
}
