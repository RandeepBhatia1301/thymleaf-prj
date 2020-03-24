package org.ril.hrss.utility;

import org.ril.hrss.model.category_hierarchy.CategorySubscription;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExitCriteriaUtil {

    public static List<CategorySubscription> unsubscribeUser(List<CategorySubscription> categorySubscriptionRepositoryList) {
        List<CategorySubscription> categorySubscriptionList = new ArrayList<>();
        if (categorySubscriptionRepositoryList != null && !categorySubscriptionRepositoryList.isEmpty()) {
            categorySubscriptionRepositoryList.forEach(categorySubscription -> {
                categorySubscription.setStatus(0);
                categorySubscriptionList.add(categorySubscription);
            });
        }
        return categorySubscriptionList;
    }
}
