package org.ril.hrss.repository;

import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

@Repository
public interface LanguageCustom {
    public String findLanguage(Integer id, HttpServletRequest httpServletRequest, Model model);

    public String listLanguage(HttpServletRequest httpServletRequest, Model model);


}
