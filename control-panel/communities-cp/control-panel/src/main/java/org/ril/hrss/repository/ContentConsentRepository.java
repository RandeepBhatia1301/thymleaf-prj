package org.ril.hrss.repository;

import org.ril.hrss.model.moderation.ContentConsent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentConsentRepository extends JpaRepository<ContentConsent, Long> {

    //List<ContentConsent> findBySubOrgIdAndContentTypeIdAndCategoryIdAndPendingStatus(@Param("subOrgId") Integer subOrgId, @Param("contentTypeId") Integer contentTypeId, @Param("categoryId") Integer categoryId, @Param("pendingStatus") Integer pendingStatus);
    //List<ContentConsent> findByOrgIdAndContentTypeIdAndCategoryIdAndPendingStatus(@Param("orgId") Integer orgId, @Param("contentTypeId") Integer contentTypeId, @Param("categoryId") Integer categoryId, @Param("pendingStatus") Integer pendingStatus);
    List<ContentConsent> findByContentTypeIdAndCategoryIdAndPendingStatus(@Param("contentTypeId") Integer contentTypeId, @Param("categoryId") Integer categoryId, @Param("pendingStatus") Integer pendingStatus);
    public Page<ContentConsent> findByAdminUserIdAndPendingStatus(Long approverUserId, Integer pendingStatus, Pageable pageable);
    public List<ContentConsent> findByContentTypeIdInAndSendByUserIdAndPendingStatus(List<Integer> contentTypeIds, Long sendByUserId, Integer pendingStatus);

}
