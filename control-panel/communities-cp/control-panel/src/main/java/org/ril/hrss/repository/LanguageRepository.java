package org.ril.hrss.repository;

import org.ril.hrss.model.language.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {


    List<Language> findAllById(@Param("id") Integer id);

    List<Language> findAllByStatus(@Param("status") Integer status);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update organization.language_master t set t.name =:name,t.file_content=:content where t.id= :id ", nativeQuery = true)
    Integer editLanguage(@Param("name") String name, @Param("id") Integer id, @Param("content") String content);

    /* STATUS= active=1 inactive=0*/
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update organization.language_master set status=:status WHERE language_master.id=:id", nativeQuery = true)
    Integer setActivation(@Param("id") Integer id, @Param("status") Integer status);
}




