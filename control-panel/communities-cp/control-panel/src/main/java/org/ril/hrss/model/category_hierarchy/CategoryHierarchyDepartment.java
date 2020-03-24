package org.ril.hrss.model.category_hierarchy;

import javax.persistence.*;

@Entity
@Table(name = "organization.category_hierarchy_department")
public class CategoryHierarchyDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "category_hierarchy_id")
    private Integer categoryHierarchyId;

    @Column(name = "department_id")
    private Integer departmentId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryHierarchyId() {
        return categoryHierarchyId;
    }

    public void setCategoryHierarchyId(Integer categoryHierarchyId) {
        this.categoryHierarchyId = categoryHierarchyId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }
}
