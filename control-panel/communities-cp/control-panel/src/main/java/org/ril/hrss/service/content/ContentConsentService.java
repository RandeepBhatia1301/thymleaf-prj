package org.ril.hrss.service.content;

import org.ril.hrss.model.moderation.ContentConsent;
import org.ril.hrss.repository.ContentConsentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentConsentService {
    @Autowired
    ContentConsentRepository contentConsentRepository;

    public Boolean updateApprovals(List<Integer> contentTypeId, Long sendByUserId) {
        List<ContentConsent> contentConsents = contentConsentRepository.findByContentTypeIdInAndSendByUserIdAndPendingStatus(contentTypeId, sendByUserId, 1);
        if (contentConsents != null && !contentConsents.isEmpty()) {
            contentConsents.stream().forEach(contentConsent -> {
                contentConsent.setPendingStatus(-1);
                contentConsent.setPublishStatus(-1);
                contentConsent.setRejectReason("REJECTED");
                contentConsent.setModeratedBy(0L);
                contentConsentRepository.save(contentConsent);
            });
            return true;
        }
        return false;
    }
}
