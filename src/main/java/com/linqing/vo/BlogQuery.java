package com.linqing.vo;

public class BlogQuery {

    private String title;
    private Long typeId;
    private boolean recommend;

    public BlogQuery(){
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public Long getTypeId(){
        return typeId;
    }

    public void setTypeId(Long typeId){
        this.typeId = typeId;
    }

    public boolean isRecommended() {
        return recommend;
    }

    public void setRecommended(boolean recommend) {
        this.recommend = recommend;
    }
}
