package org.ril.hrss.service.rest_api_services;

import org.ril.hrss.utility.Constants;
import org.ril.hrss.utility.ControlPanelUtil;
import org.ril.hrss.utility.ImageStorageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class ImageStorageService {
    @Autowired
    StorageContainer storageContainer;

    @Autowired
    StorageConfigService storageConfigService;

    public String index(Model model, HttpServletRequest httpServletRequest) {
        Map map = storageContainer.getAzureData(Constants.ONE, ControlPanelUtil.setOrgId(httpServletRequest));
        return ImageStorageUtil.index(model, map);
    }

    public String store(HttpServletRequest httpServletRequest, Model model, RedirectAttributes redirectAttributes) throws Exception {
        Boolean status = storageConfigService.update(httpServletRequest, ControlPanelUtil.setOrgId(httpServletRequest));
        return ImageStorageUtil.storeSuccess(status, redirectAttributes);
    }
}
