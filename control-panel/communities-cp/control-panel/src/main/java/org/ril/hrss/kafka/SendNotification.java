package org.ril.hrss.kafka;

import org.ril.hrss.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class SendNotification {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendNotification(String communityTitle, Integer parentId, Integer orgId, Integer subOrgId, Integer subCategoryId, String aoiTitle, Integer categoryId) {
        HashMap<String, Object> notification = new HashMap<>();
        notification.put(Constants.ORG_ID_PARAM, orgId);
        notification.put(Constants.SUB_ORG_ID_PARAM, subOrgId);
        notification.put(Constants.TITLE, communityTitle);
        notification.put(Constants.CATEGORY_ID, categoryId);
        String activityType;
        if (parentId == 0) {
            activityType = Constants.COMMUNITY_CREATED_ACTIVITY;
        } else {
            activityType = Constants.AOI_CREATED_ACTIVITY;
            notification.put(Constants.SUB_CATEGORY_ID, subCategoryId);
            notification.put(Constants.AOI_TITLE, aoiTitle);
        }

        notification.put(Constants.ACTIVITY_TYPE_PARAM, activityType);
        kafkaTemplate.send(Constants.KAFKA_NOTIFICATION_FEED_TOPIC, notification);
    }

}
