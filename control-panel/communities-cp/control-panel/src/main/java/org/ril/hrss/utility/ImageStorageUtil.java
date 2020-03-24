package org.ril.hrss.utility;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Component
public class ImageStorageUtil {
    public static String storeSuccess(Boolean status, RedirectAttributes redirectAttributes) {
        if (status) {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.SUCCESS);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, Constants.IMAGE_STORE_SUCCESS);
        } else {
            redirectAttributes.addFlashAttribute(Constants.STATUS, Constants.ERROR);
            redirectAttributes.addFlashAttribute(Constants.MESSAGE, Constants.SOMETHING_WENT_WRONG);
        }
        return "redirect:/image-storage?v=467543557hr";
    }

    public static String index(Model model, Map map) {
        model.addAttribute(Constants.STORAGE, map);
        return "storageConfig/edit";
    }

}
