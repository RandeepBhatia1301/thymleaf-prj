package org.ril.hrss.repository;

import org.ril.hrss.model.OrgConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrgConfigurationRepository extends JpaRepository<OrgConfiguration, Integer> {

    OrgConfiguration findByOrganizationIdAndSettingName(@Param("orgId") Integer orgId, @Param("settingName") String settingName);
}
