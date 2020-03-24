package org.ril.hrss.utility;

import org.ril.hrss.model.Country;
import org.ril.hrss.model.Module;
import org.ril.hrss.model.Org;
import org.ril.hrss.model.auth.AdminUser;
import org.ril.hrss.model.content.Content;
import org.ril.hrss.model.content.OrgContent;
import org.ril.hrss.model.content.OrgContentList;
import org.ril.hrss.model.content.SubOrgContent;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ContentUtil {
    public static Content create(HttpServletRequest httpServletRequest) {
        Content content = new Content();
        content.setName(httpServletRequest.getParameter(Constants.NAME));
        content.setDescription(httpServletRequest.getParameter(Constants.DESCRIPTION));
        content.setStatus(Constants.ONE);
        content.setIsConfigurable(Constants.ONE);
        content.setContentSetting(httpServletRequest.getParameter(Constants.JSON_INPUT));
        content.setDefaultSetting(httpServletRequest.getParameter(Constants.JSON_INPUT));
        return content;
    }

    public static String createModel(HttpServletRequest httpServletRequest) {

        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }
        return "content/add-content";
    }


    public static Content update(HttpServletRequest httpServletRequest, Optional<Content> contentOptional) {
        Content content = contentOptional.get();
        content.setName(httpServletRequest.getParameter(Constants.NAME));
        content.setDescription(httpServletRequest.getParameter(Constants.DESCRIPTION));
        content.setContentSetting(httpServletRequest.getParameter(Constants.JSON_INPUT));

        return content;
    }


    public static String createModel(List<Content> contents, List<Module> modules, List<Country> countries, Model model) {
        model.addAttribute(Constants.CONTENTS, contents);
        model.addAttribute(Constants.MODULES, modules);
        model.addAttribute(Constants.COUNTRIES, countries);

        return "organization/create";
    }

    public static String editModel(List<OrgContent> contents, Org org, AdminUser admin, List<Integer> ids, Integer orgId, List<Country> countries, Model model, Map map, String decryptedPassword) {
        model.addAttribute(Constants.CONTENTS, contents);
        model.addAttribute(Constants.ORG, org);
        model.addAttribute(Constants.ADMIN, admin);
        model.addAttribute(Constants.AVAILABLE_CONTENTS, ids);
        model.addAttribute(Constants.COUNTRIES, countries);
        model.addAttribute(Constants.ORG_ID, orgId);
        model.addAttribute(Constants.DECRYPTED_PASSWORD, decryptedPassword);
        model.addAttribute(Constants.STORAGE, map);
        return "organization/edit";
    }

    public static List<OrgContent> getSubOrgContent(List<SubOrgContent> subOrgContents, List<OrgContent> orgContents) {
        List<OrgContent> list = new ArrayList<>();
        subOrgContents.forEach(subOrgContent -> {
            orgContents.forEach(orgContent -> {
                if (orgContent.getId().equals(subOrgContent.getOrgContentTypeId())) {
                    OrgContentList orgContentList = new OrgContentList();
                    orgContentList.setId(subOrgContent.getId());
                    orgContentList.setStatus(subOrgContent.getStatus());
                    orgContentList.setContentSetting(subOrgContent.getContentSetting());
                    orgContentList.setName(orgContent.getName());
                    orgContentList.setContentTypeId(orgContent.getContentTypeId());
                    orgContentList.setIsAvailable(orgContent.getIsAvailable());
                    orgContentList.setDescription(orgContent.getDescription());
                    list.add(orgContentList);
                }
            });
        });
        return list;
    }

}
