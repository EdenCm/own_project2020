package com.cmblog.blog.vo;

/**
 * 动态查询时条件实体类
 */
public class BlogQueryByCondition {
    private String title;
     private Long typeId ;
     // 接收前端传过来的字符串类型
     private String recommend;
    private Integer recommend2;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public Integer getRecommend2() {
        if (this.recommend != null && !"".equals(this.recommend) && "false".equals(this.recommend)) {
            this.recommend2 = 1;
        }
        return recommend2;
    }

    public void setRecommend2(Integer recommend2) {
        this.recommend2 = recommend2;
    }


}
