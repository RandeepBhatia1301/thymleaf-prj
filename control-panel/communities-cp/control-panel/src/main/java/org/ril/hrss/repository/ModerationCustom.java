package org.ril.hrss.repository;

import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;


@Repository
public interface ModerationCustom {
    public Long saveOrUpdate(HttpServletRequest httpServletRequest);


}
