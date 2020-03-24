package org.ril.hrss.service.product_hierarchy;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ril.hrss.model.product_hierarchy.SubOrgProductHierarchy;
import org.ril.hrss.repository.SubOrgProductHierarchyRepository;
import org.ril.hrss.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Service
public class SubOrgProductHierarchyService {
    @Autowired
    private SubOrgProductHierarchyRepository subOrgProductHierarchyRepository;

    public List<SubOrgProductHierarchy> getProductData(Integer subOrgId) {
        List<SubOrgProductHierarchy> subOrgProductHierarchies = subOrgProductHierarchyRepository.findBySubOrgId(subOrgId);
        return subOrgProductHierarchies;
    }

    public boolean setActivation(Integer id, HttpServletRequest httpServletRequest, Integer value, Integer subOrgId) {
        try {
            SubOrgProductHierarchy subOrgProductHierarchy = subOrgProductHierarchyRepository.findBySubOrgIdAndId(subOrgId, id);
            subOrgProductHierarchy.setStatus(value);
            subOrgProductHierarchyRepository.save(subOrgProductHierarchy);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean updateProductHierarchy(Integer subOrgId, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        try {
            Integer id = Integer.valueOf(httpServletRequest.getParameter(Constants.ID));
            SubOrgProductHierarchy subOrgProductHierarchy = subOrgProductHierarchyRepository.findBySubOrgIdAndId(subOrgId, id);
            //subOrgProductHierarchy.setName(httpServletRequest.getParameter(Constants.NAME));
            subOrgProductHierarchy.setDescription(httpServletRequest.getParameter(Constants.DESCRIPTION));
            subOrgProductHierarchy.setIsEdited(Constants.ONE);

            subOrgProductHierarchyRepository.save(subOrgProductHierarchy);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public SubOrgProductHierarchy getProductHierarchyDataById(Integer id, Integer subOrgId) throws IOException {
        SubOrgProductHierarchy subOrgProductHierarchy = subOrgProductHierarchyRepository.findBySubOrgIdAndId(subOrgId, id);
        return subOrgProductHierarchy;
    }
}
