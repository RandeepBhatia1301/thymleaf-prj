package org.ril.hrss.repository;

import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@Repository
public interface ContentCustom {
    public List<Map<Object, Object>> findUserSubscriptionTotal(HttpServletRequest httpServletRequest);
}
