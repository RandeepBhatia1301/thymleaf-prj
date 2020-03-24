package org.ril.hrss.model;

public class TopBlog {
    Long blogId;
    Integer count;
    String name;

    public TopBlog() {
    }

    public TopBlog(Long blogId, Integer count, String name) {
        this.blogId = blogId;
        this.count = count;
        this.name = name;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TopBlog{" +
                "blogId=" + blogId +
                ", count=" + count +
                ", name='" + name + '\'' +
                '}';
    }
}
