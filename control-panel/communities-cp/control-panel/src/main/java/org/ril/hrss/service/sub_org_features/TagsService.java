package org.ril.hrss.service.sub_org_features;

import org.ril.hrss.model.tag.Tag;
import org.ril.hrss.model.tag.TagBlog;
import org.ril.hrss.model.tag.TagContent;
import org.ril.hrss.model.tag.TagDiscussion;
import org.ril.hrss.repository.TagCustom;
import org.ril.hrss.repository.TagsRepository;
import org.ril.hrss.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class TagsService {

    @Autowired
    TagCustom tagCustom;
    @Autowired
    private TagsRepository tagsRepository;

    public Page<Tag> getTags(Integer orgId, Integer perPage, HttpServletRequest request) {
        int page = request.getParameterMap().containsKey(Constants.PAGE) ? Integer.valueOf(request.getParameter(Constants.PAGE)) : 0;
        Pageable p = PageRequest.of(page, perPage);
        Page<Tag> t = tagsRepository.findAllByOrgId(orgId, p);
        return t;

    }

    public long getTagFrequency(Integer tagId) {
        Long countContent = tagCustom.getTagFrequency(TagContent.class, tagId);
        Long countBlog = tagCustom.getTagFrequency(TagBlog.class, tagId);
        Long countDiscussion = tagCustom.getTagFrequency(TagDiscussion.class, tagId);
        return countContent + countBlog + countDiscussion;
    }
}

