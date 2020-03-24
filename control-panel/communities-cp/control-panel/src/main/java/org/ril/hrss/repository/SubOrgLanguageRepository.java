package org.ril.hrss.repository;

import org.ril.hrss.model.language.SubOrgLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SubOrgLanguageRepository extends JpaRepository<SubOrgLanguage, Integer> {

    List<SubOrgLanguage> findAllByStatus(@Param(value = "status") Integer status);

    SubOrgLanguage findBySuborgIdAndId(@Param(value = "subOrgId") Integer subOrgId, @Param(value = "id") Integer id);

    List<SubOrgLanguage> findBySuborgId(@Param(value = "subOrgid") Integer subOrgId);

    Integer countBySuborgIdAndStatus(@Param(value = "subOrgId") Integer subOrgId, @Param(value = "status") Integer status);

    SubOrgLanguage findBySuborgIdAndLangCode(@Param(value = "subOrgId") Integer subOrgId, @Param(value = "langCode") String langCode);
}
