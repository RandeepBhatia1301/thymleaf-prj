package org.ril.hrss.service.category_hierarchy;

import org.ril.hrss.model.auth.User;
import org.ril.hrss.model.category_hierarchy.CategorySubscription;
import org.ril.hrss.repository.CategorySubscriptionRepository;
import org.ril.hrss.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategorySubscriptionService {
    @Autowired
    CategorySubscriptionRepository categorySubscriptionRepository;

    @Autowired
    UserRepository userRepository;

    public List<User> getUserIds() {
        List<CategorySubscription> categorySubscriptions = categorySubscriptionRepository.findByIsAdmin(1);

        List<Long> userIds = categorySubscriptions.stream()
                .map(CategorySubscription::getUserId).collect(Collectors.toList());

        return userRepository.findAllByIdIn(userIds);

    }
}
