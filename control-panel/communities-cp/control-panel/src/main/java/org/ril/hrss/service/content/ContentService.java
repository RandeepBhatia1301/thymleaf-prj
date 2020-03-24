package org.ril.hrss.service.content;

import org.ril.hrss.model.content.Content;
import org.ril.hrss.model.content.OrgContent;
import org.ril.hrss.model.content.SubOrgContent;
import org.ril.hrss.repository.ContentRepository;
import org.ril.hrss.repository.OrgContentTypeRepository;
import org.ril.hrss.repository.SubOrgContentTypeRepository;
import org.ril.hrss.utility.Constants;
import org.ril.hrss.utility.ContentUtil;
import org.ril.hrss.utility.ControlPanelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
public class ContentService {
    @Autowired
    ContentRepository contentRepository;
    @Autowired
    OrgContentTypeRepository orgContentTypeRepository;

    @Autowired
    OrgContentsService orgContentsService;

    @Autowired
    private SubOrgContentService subOrgContentService;

    @Autowired
    private SubOrgContentTypeRepository subOrgContentTypeRepository;

    public String index(Model model, HttpServletRequest httpServletRequest, HttpSession httpSession) {
        if (httpSession.getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }
        Integer orgId = (Integer) httpSession.getAttribute(Constants.ORG_ID);
        List<Content> contents = null;

        if (httpSession.getAttribute(Constants.ROLE) == Constants.SAAS_ADMIN) {
            contents = this.getContentList();
            model.addAttribute(Constants.CONTENT_LIST, contents);

        } else if (httpSession.getAttribute(Constants.ROLE) == Constants.ORG_ADMIN) {
            List<OrgContent> orgContents = orgContentsService.getOrgContents(orgId);
            model.addAttribute(Constants.CONTENT_LIST, orgContents);

        } else if (httpSession.getAttribute(Constants.ROLE) == Constants.SUB_ORG_ADMIN) {

            Integer subOrgId = (Integer) httpSession.getAttribute(Constants.SUB_ORG_ID);
            List<OrgContent> subOrgContents = subOrgContentService.getSubOrgContent(subOrgId, orgId);
            model.addAttribute(Constants.CONTENT_LIST, subOrgContents);
        }
        return "content/content-list";
    }

    public String createModel(HttpServletRequest httpServletRequest) {
        return ContentUtil.createModel(httpServletRequest);
    }

    public String editModel(HttpServletRequest httpServletRequest, Model model, Integer Id) {
        String role = (String) httpServletRequest.getSession().getAttribute(Constants.ROLE);
        switch (role) {
            case Constants.ORG_ADMIN:
                Integer orgId = (Integer) httpServletRequest.getSession().getAttribute(Constants.ORG_ID);
                OrgContent orgContent = orgContentsService.getContentById(orgId, Id);
                model.addAttribute(Constants.CONTENT, orgContent);
                break;
            case Constants.SUB_ORG_ADMIN:
                Integer subOrgId = (Integer) httpServletRequest.getSession().getAttribute(Constants.SUB_ORG_ID);
                SubOrgContent subOrgContent = subOrgContentTypeRepository.findByIdAndSubOrgId(Id, subOrgId);
                OrgContent orgContent1 = new OrgContent();
                Optional<OrgContent> orgContent2 = orgContentTypeRepository.findById(subOrgContent.getOrgContentTypeId());
                if (orgContent2.isPresent()) {
                    orgContent1 = orgContent2.get();
                }
                model.addAttribute(Constants.CONTENT, subOrgContent);
                model.addAttribute("contentName", "Content Type");
                if (orgContent1.getName() != null) {
                    model.addAttribute("contentName", orgContent1.getName());
                }
                break;
            default:
                Content defaultContent = this.getContentById(Id);
                model.addAttribute(Constants.CONTENT, defaultContent);
                break;
        }
        return "content/edit-content";
    }


    public List<Content> getContentList() {
        return contentRepository.findAllByIsConfigurable(1);

    }

    public List<Content> getContentForOrgCreation() {
        return contentRepository.findAllByStatusAndIsConfigurable(1, 1);
    }

    public String createContent(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }
        try {
            contentRepository.save(ContentUtil.create(httpServletRequest));
            return ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, Constants.CONTENT_ADD_SUCCESS, "redirect:/content?v=abc");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ControlPanelUtil.redirect(redirectAttributes, Constants.ERROR, Constants.SOMETHING_WENT_WRONG, "redirect:/content?v=abc");
        }
    }

    public List<Content> getContentdatabyId(Integer id) {
        List<Content> contentData = contentRepository.findAllById(id);
        return contentData;
    }

    public String update(HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {

        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }
        Integer id = Integer.valueOf(httpServletRequest.getParameter(Constants.ID));
        Boolean status = false;
        String role = (String) httpServletRequest.getSession().getAttribute(Constants.ROLE);

        switch (role) {
            case Constants.ORG_ADMIN:
                Integer orgId = (Integer) httpServletRequest.getSession().getAttribute(Constants.ORG_ID);
                status = orgContentsService.updateContent(orgId, httpServletRequest, id);
                break;
            case Constants.SUB_ORG_ADMIN:
                Integer subOrgId = (Integer) httpServletRequest.getSession().getAttribute(Constants.SUB_ORG_ID);
                status = subOrgContentService.updateContent(subOrgId, httpServletRequest, id);
                break;
            default:
                status = this.updateContent(httpServletRequest, id);
                break;
        }
        if (status) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.SUCCESS);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, Constants.CONTENT_EDIT_SUCCESS);
        } else {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.ERROR);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
        }
        return "redirect:/content?v=abc";
    }

    private boolean updateContent(HttpServletRequest httpServletRequest, Integer Id) {

        Optional<Content> contentOptional = contentRepository.findById(Id);

        if (contentOptional.isPresent()) {
            contentRepository.save(ContentUtil.update(httpServletRequest, contentOptional));
            return true;
        }

        return false;
    }

    public String activation(Integer id, HttpServletRequest httpServletRequest, Integer value, RedirectAttributes redirectAttributes) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return Constants.REDIRECT_LOGIN;
        }

        Boolean status = false;
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.SAAS_ADMIN) {
            status = this.setActivation(id, httpServletRequest, value);

        } else if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.ORG_ADMIN) {

            Integer orgId = (Integer) httpServletRequest.getSession().getAttribute(Constants.ORG_ID);

            status = orgContentsService.setActivation(id, httpServletRequest, value, orgId);

        } else if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.SUB_ORG_ADMIN) {

            Integer subOrgId = (Integer) httpServletRequest.getSession().getAttribute(Constants.SUB_ORG_ID);

            status = subOrgContentService.setActivation(id, httpServletRequest, value, subOrgId);

        }
        if (status) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.SUCCESS);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, Constants.STATUS_UPDATE_SUCCESS);
        } else {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.ERROR);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
        }

        String referer = httpServletRequest.getHeader(Constants.REFERER);
        return "redirect:/content?v=a667bc";
    }

    public Boolean setActivation(Integer id, HttpServletRequest httpServletRequest, Integer value) {
        Integer status = value;
        Integer count = contentRepository.setActivation(id, status);
        if (count > 0) {
            return true;
        }
        return false;
    }

    private Content getContentById(Integer Id) {
        Optional<Content> contentOptional = contentRepository.findById(Id);
        return (contentOptional.isPresent()) ? contentOptional.get() : new Content();
    }

    public String setDefaultValue(Integer id, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.SAAS_ADMIN) {
            Content content = contentRepository.findById(id).get();
            content.setContentSetting(content.getDefaultSetting());
            contentRepository.save(content);

        }
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.ORG_ADMIN) {
            OrgContent orgContent = orgContentTypeRepository.findByIdAndOrgId(id, ControlPanelUtil.setOrgId(httpServletRequest));
            orgContent.setContentSetting(orgContent.getDefaultSetting());
            orgContentTypeRepository.save(orgContent);

        }
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == Constants.SUB_ORG_ADMIN) {

            SubOrgContent subOrgContent = subOrgContentTypeRepository.findByIdAndSubOrgId(id, ControlPanelUtil.setSubOrgId(httpServletRequest));
            subOrgContent.setContentSetting(subOrgContent.getDefaultSetting());
            subOrgContentTypeRepository.save(subOrgContent);

        }
        String referer = httpServletRequest.getHeader(Constants.REFERER);
        return ControlPanelUtil.redirect(redirectAttributes, Constants.SUCCESS, "Content set to default successfully", Constants.REDIRECT + referer);

    }
}
