package org.ril.hrss.repository;

import org.ril.hrss.model.SubOrgConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubOrgConfigurationRepository extends JpaRepository<SubOrgConfiguration, Integer> {
    SubOrgConfiguration findByOrganizationIdAndSubOrgIdAndSettingName(@Param(value = "orgId") Integer orgId, @Param(value = "subOrgId") Integer subOrgId, @Param(value = "settingName") String settingName);

}
