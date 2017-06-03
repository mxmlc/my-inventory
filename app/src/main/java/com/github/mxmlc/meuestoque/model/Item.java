package com.github.mxmlc.meuestoque.model;

import com.github.mxmlc.meuestoque.util.DateUtil;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

/**
 * Classe representando a entidade Item.
 *
 * @author mxmlc - Fabiano Ramos dos Santos
 */
public class Item implements Serializable {

    public enum OVERDUE_IN {
        INDIFERENT, OVERDUE, CLOSE_OVERDUE;
    }

    public static String TABLE_NAME = "item";
    public static String ID_COLUMN = "id";
    public static String NAME_COLUMN = "name";
    public static String DESC_COLUMN = "description";
    public static String MANUFACTURE_COLUMN = "manufacture_date";
    public static String VALID_THRU_COLUMN = "valid_thru";
    public static String QUANTITY_COLUMN = "quantity";

    private Long id;
    private String name;
    private String description;
    private Date manufactureDate;
    private Date validThru;
    private Integer quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(Date manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public Date getValidThru() {
        return validThru;
    }

    public void setValidThru(Date validThru) {
        this.validThru = validThru;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return quantity + " - " + name + " - " + DateUtil.formatDate(validThru);
    }
}
