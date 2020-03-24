package org.ril.hrss.repository;

import org.springframework.stereotype.Repository;


@Repository
public interface TagCustom {
    public Long getTagFrequency(Class<?> classType, Integer id);

}
