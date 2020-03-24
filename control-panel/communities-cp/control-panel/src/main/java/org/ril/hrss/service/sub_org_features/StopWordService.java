package org.ril.hrss.service.sub_org_features;

import org.ril.hrss.model.StopWord;
import org.ril.hrss.repository.StopWordRepository;
import org.ril.hrss.utility.Constants;
import org.ril.hrss.utility.ControlPanelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class StopWordService {
    @Autowired
    StopWordRepository stopWordRepository;

    public String index(Model model, HttpServletRequest httpServletRequest) {

        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }
        List<StopWord> stopWords = this.getStopWordList(ControlPanelUtil.setOrgId(httpServletRequest));
        model.addAttribute("stopWordList", stopWords);
        return "stop-words/list";
    }

    public String createModel(Model model, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute(Constants.ROLE) == null) {
            return "redirect:/login";
        }
        return "stop-words/add";
    }

    private List<StopWord> getStopWordList(Integer orgId) {
        return stopWordRepository.findAllByOrganizationId(orgId);

    }

    public Boolean create(HttpServletRequest httpServletRequest, Integer orgId) {
        try {
            StopWord stopWord = new StopWord();
            stopWord.setName(httpServletRequest.getParameter("name"));
            stopWord.setOrganizationId(orgId);
            stopWord.setStatus(1);
            stopWordRepository.save(stopWord);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public StopWord getById(Integer id) {
        Optional<StopWord> stopWord = stopWordRepository.findById(id);
        StopWord stopWord1 = new StopWord();
        if (stopWord.isPresent()) {
            stopWord1 = stopWord.get();
        }
        return stopWord1;
    }

    public Boolean update(HttpServletRequest httpServletRequest, Integer orgId) {
        try {
            Integer id = Integer.valueOf(httpServletRequest.getParameter(Constants.ID));
            Optional<StopWord> stopWord = stopWordRepository.findById(id);
            StopWord stopWord1 = new StopWord();
            if (stopWord.isPresent()) {
                stopWord1 = stopWord.get();
            }
            stopWord1.setName(httpServletRequest.getParameter("name"));
            stopWordRepository.save(stopWord1);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
