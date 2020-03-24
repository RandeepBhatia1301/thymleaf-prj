package org.ril.hrss.repository;

import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;


@Repository
public interface UserActivityCustom {
    public List getCommunityName(HttpServletRequest httpServletRequest);

    public List getUserName(HttpServletRequest httpServletRequest);

    public List getAOIName(HttpServletRequest httpServletRequest);

    public List<LinkedHashMap> graphContentType(HttpServletRequest httpServletRequest);

    public Long getTotalPostCount(HttpServletRequest httpServletRequest);


}
