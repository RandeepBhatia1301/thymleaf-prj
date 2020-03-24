package org.ril.hrss.repository;

import org.ril.hrss.model.StorageConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageConfigurationRepository extends JpaRepository<StorageConfiguration, Integer> {
    StorageConfiguration findBySettingName(@Param("settingName") String settingName);

}
