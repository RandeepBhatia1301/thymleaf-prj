package org.ril.hrss.service.product_hierarchy;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ril.hrss.model.product_hierarchy.OrgProductHierarchy;
import org.ril.hrss.repository.OrgProductHierarchyRepository;
import org.ril.hrss.repository.ProductHierarchyRepository;
import org.ril.hrss.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Service
public class OrgProductHierarchyService {
    @Autowired
    private OrgProductHierarchyRepository orgProductHierarchyRepository;
    @Autowired
    private ProductHierarchyRepository productHierarchyRepository;

    @Autowired
    private ProductHierarchyService productHierarchyService;

    public List<OrgProductHierarchy> getProductData(Integer orgId) {
        /*List<ProductHierarchy> productHierarchies = productHierarchyRepository.findAllByStatusOrderByLevel(1);*/
        List<OrgProductHierarchy> orgProductHierarchies = orgProductHierarchyRepository.findByOrgId(orgId);

      /*  for (ProductHierarchy productHierarchy : productHierarchies) {

            for (OrgProductHierarchy orgProductHierarchy : orgProductHierarchies) {
                if (productHierarchy.getId().equals(orgProductHierarchy.getProductId()) && orgProductHierarchy.getStatus() != null) {
                    productHierarchy.setStatus(orgProductHierarchy.getStatus());
                }
            }
        }*/
        return orgProductHierarchies;
    }

    public boolean setActivation(Integer id, HttpServletRequest httpServletRequest, Integer value, Integer orgId) {
        try {
            OrgProductHierarchy orgProductHierarchy = orgProductHierarchyRepository.findByOrgIdAndId(orgId, id);
            orgProductHierarchy.setStatus(value);
            orgProductHierarchyRepository.save(orgProductHierarchy);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean updateProductHierarchy(Integer orgId, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        try {
            Integer id = Integer.valueOf(httpServletRequest.getParameter(Constants.ID));
            OrgProductHierarchy orgProductHierarchy = orgProductHierarchyRepository.findByOrgIdAndId(orgId, id);
            //orgProductHierarchy.setName(httpServletRequest.getParameter(Constants.NAME));
            orgProductHierarchy.setDescription(httpServletRequest.getParameter(Constants.DESCRIPTION));
            orgProductHierarchy.setIsEdited(Constants.ONE);
           /* String name = httpServletRequest.getParameter("name");
            String description = httpServletRequest.getParameter("description");
            Map<String, String> productDataForOrg = new HashMap<>();
            productDataForOrg.put("name", name);
            productDataForOrg.put("description", description);
            String settingJson = new ObjectMapper().writeValueAsString(productDataForOrg);
            System.out.println(settingJson);
            orgProductHierarchy.setSetting(settingJson);*/
            orgProductHierarchyRepository.save(orgProductHierarchy);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public OrgProductHierarchy getProductHierarchyDataById(Integer id, Integer orgId) throws IOException {
        OrgProductHierarchy orgProductHierarchy = orgProductHierarchyRepository.findByOrgIdAndId(orgId, id);
        /*Map<String, String> orgProductData = new HashMap<>();*/
      /*  if (orgProductHierarchy.getSetting() != null) {
            *//*if setting is present show it*//*
            ObjectMapper objectMapper = new ObjectMapper();

            orgProductData = objectMapper.readValue(orgProductHierarchy.getSetting(), Map.class);*//*string to map*//*
            System.out.println(orgProductData);

        } else {*//*if the data is null get it from master*//*
            ProductHierarchy productHierarchy = productHierarchyService.getProductHierarchyDataById(id);
            orgProductData.put("name", productHierarchy.getName());
            orgProductData.put("description", productHierarchy.getDescription());
        }*/
        return orgProductHierarchy;
    }
}
