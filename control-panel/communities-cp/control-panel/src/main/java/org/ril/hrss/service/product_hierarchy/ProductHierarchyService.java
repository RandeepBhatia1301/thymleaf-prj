package org.ril.hrss.service.product_hierarchy;

import org.ril.hrss.model.product_hierarchy.ProductHierarchy;
import org.ril.hrss.repository.ProductHierarchyRepository;
import org.ril.hrss.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductHierarchyService {
    @Autowired
    private ProductHierarchyRepository productHierarchyRepository;

    public List<ProductHierarchy> getProductData() {
        List<ProductHierarchy> productHierarchies;
        productHierarchies = productHierarchyRepository.findAllByOrderByLevel();
        return productHierarchies;
    }

    public ProductHierarchy getProductHierarchyDataById(Integer id) {
        ProductHierarchy productHierarchyData = productHierarchyRepository.findAllById(id);
        return productHierarchyData;
    }

    public Boolean updateProductHierarchy(HttpServletRequest httpServletRequest) {
        Integer id = Integer.valueOf(httpServletRequest.getParameter(Constants.ID));
        // String name = httpServletRequest.getParameter(Constants.NAME);
        String description = httpServletRequest.getParameter(Constants.DESCRIPTION);

    /*    Integer level = Integer.valueOf(httpServletRequest.getParameter("level"));


        *//*find the level of current product*//*

        ProductHierarchy productHierarchy1 = productHierarchy.get();
        Integer originalLevel = productHierarchy1.getLevel();
        *//*find the product by level *//*
        ProductHierarchy productHierarchy2 = productHierarchyRepository.findByLevel(level);
        productHierarchy2.setLevel(originalLevel);
        productHierarchyRepository.save(productHierarchy2);*//*update the product with old level*//*
        productHierarchy1.setLevel(level);
        */
        Optional<ProductHierarchy> productHierarchy = productHierarchyRepository.findById(id);
        ProductHierarchy productHierarchy1 = new ProductHierarchy();
        if (productHierarchy.isPresent()) {
            productHierarchy1 = productHierarchy.get();
        }
        // productHierarchy1.setName(name);
        productHierarchy1.setDescription(description);
        productHierarchyRepository.save(productHierarchy1);/*update the current product with new data*/

        return true;
    }

    public boolean setActivation(Integer id, HttpServletRequest httpServletRequest, Integer value) {
        Integer status = value;
        Integer count = productHierarchyRepository.setActivation(id, status);
        if (count > Constants.ZERO) {
            return true;
        }
        return false;
    }

    public Map<String, String> getProductLevel() {
        Map<String, String> productData = new HashMap<>();
        ProductHierarchy productHierarchy = productHierarchyRepository.findByLevel(1);
        ProductHierarchy productHierarchy1 = productHierarchyRepository.findByLevel(2);
        productData.put(Constants.LEVEL_ONE_NAME, productHierarchy.getName());
        productData.put(Constants.LEVEL_TWO_NAME, productHierarchy1.getName());
        return productData;
    }

}
