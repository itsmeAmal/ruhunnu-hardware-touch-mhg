/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cazzendra.pos.model;

/**
 *
 * @author personal
 */
public class SubCategory {
    
    private int id;
    private String name;
    private String detail;
    private int status;
    private int mainCategoryId;
    private String mainCategoryName;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * @param detail the detail to set
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the mainCategoryId
     */
    public int getMainCategoryId() {
        return mainCategoryId;
    }

    /**
     * @param mainCategoryId the mainCategoryId to set
     */
    public void setMainCategoryId(int mainCategoryId) {
        this.mainCategoryId = mainCategoryId;
    }

    /**
     * @return the mainCategoryName
     */
    public String getMainCategoryName() {
        return mainCategoryName;
    }

    /**
     * @param mainCategoryName the mainCategoryName to set
     */
    public void setMainCategoryName(String mainCategoryName) {
        this.mainCategoryName = mainCategoryName;
    }
}
