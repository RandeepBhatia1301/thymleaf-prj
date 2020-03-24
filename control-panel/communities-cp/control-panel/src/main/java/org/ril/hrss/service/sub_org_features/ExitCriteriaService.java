package org.ril.hrss.service.sub_org_features;

import org.ril.hrss.model.ContentParticipant;
import org.ril.hrss.model.auth.User;
import org.ril.hrss.model.category_hierarchy.CategoryHierarchy;
import org.ril.hrss.model.category_hierarchy.CategorySubscription;
import org.ril.hrss.repository.*;
import org.ril.hrss.service.content.ContentConsentService;
import org.ril.hrss.service.rest_api_services.ElasticUploadService;
import org.ril.hrss.utility.Constants;
import org.ril.hrss.utility.ControlPanelUtil;
import org.ril.hrss.utility.ExitCriteriaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.ril.hrss.utility.Constants.ELASTIC_USER_CONTENT_TYPE_ID;

@Service
public class ExitCriteriaService {

    @Autowired
    ElasticUploadService elasticUploadService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategorySubscriptionRepository categorySubscriptionRepository;
    @Autowired
    private DiscussionRepository discussionRepository;
    @Autowired
    private UserLogRepository userLogRepository;

    @Autowired
    ContentParticipationRepository contentParticipationRepository;

    @Autowired
    ContentConsentService contentConsentService;
    @Autowired
    UserFollowerRepository userFollowerRepository;

    @Autowired
    CategoryHierarchyRepository categoryHierarchyRepository;

    @Transactional
    public String exitUser(Long userId, HttpServletRequest httpServletRequest) {
        try {
            changeUserStatus(userId, httpServletRequest);
            removeUserFromCommunityAndAoiAndGroup(userId, httpServletRequest);
            removeFromLeaderBoard(userId, httpServletRequest);
            removeUserFromSearch(userId);
            removeFromImportMember(userId);
            removeFromFollow(userId, userId);
            List<Integer> list = new ArrayList<Integer>();
            list.add(5);
            list.add(6);
            contentConsentService.updateApprovals(list, userId);
            return "true";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "false";
        }
    }

    private void changeUserStatus(Long userId, HttpServletRequest httpServletRequest) {
        User user = userRepository.findByIdAndSubOrgId(userId, ControlPanelUtil.setSubOrgId(httpServletRequest));
        if (user != null) {
            user.setStatus(Constants.ZERO);
            userRepository.save(user);
        }
    }

    private void removeUserFromCommunityAndAoiAndGroup(Long userId, HttpServletRequest httpServletRequest) {
        List<CategorySubscription> categorySubscriptionRepositoryList = categorySubscriptionRepository.findByUserIdAndSubOrgId(userId, ControlPanelUtil.setSubOrgId(httpServletRequest));
        List<CategorySubscription> categorySubscriptionList = ExitCriteriaUtil.unsubscribeUser(categorySubscriptionRepositoryList);
        if (categorySubscriptionList != null && !categorySubscriptionList.isEmpty()) {
            categorySubscriptionRepository.saveAll(categorySubscriptionList);
        }
        adjustMemberCountForUnsubscribedCategory(categorySubscriptionRepositoryList, ControlPanelUtil.setOrgId(httpServletRequest));
    }

    private void removeFromLeaderBoard(Long userId, HttpServletRequest httpServletRequest) {
        userLogRepository.deleteAllByUserId(userId.intValue());
    }

    private void removeUserFromSearch(Long userId) {
        elasticUploadService.elasticDelete(ELASTIC_USER_CONTENT_TYPE_ID, Math.toIntExact(userId));
    }

    private void removeFromImportMember(Long userId) {
        ContentParticipant contentParticipant = contentParticipationRepository.findContentParticipantById(userId);
        if (contentParticipant != null) {
            contentParticipant.setStatus(Constants.STATUS_MINUSONE);
            contentParticipationRepository.save(contentParticipant);
        }
    }

    private void removeFromFollow(Long userId, Long followerId) {
        userFollowerRepository.deleteAllByUserIdOrFollowerId(userId, followerId);
    }


    private void adjustMemberCountForUnsubscribedCategory(List<CategorySubscription> subscriptions, Integer orgId) {
        List<Integer> categoryIds = subscriptions.stream()
                .map(CategorySubscription::getCategoryHierarchyId).collect(Collectors.toList());
        categoryIds.forEach(integer -> {
            Integer count = categorySubscriptionRepository.countByCategoryHierarchyIdAndStatusAndOrganizationId(integer, Constants.ONE, orgId);
            Optional<CategoryHierarchy> categoryHierarchy = categoryHierarchyRepository.findById(integer);
            if (categoryHierarchy.isPresent()) {
                CategoryHierarchy hierarchy = categoryHierarchy.get();
                hierarchy.setMemberCount(count);
                categoryHierarchyRepository.save(hierarchy);
            }
        });

    }
}
