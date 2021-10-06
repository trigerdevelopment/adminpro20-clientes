package com.adminpro20.clientes.model.product;

import com.adminpro20.clientes.model.BaseEntity;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.prefs.BackingStoreException;

@Getter
@Setter
@Entity
public class Product extends BaseEntity {


    @Column
    public Date date;

    @Column
    public String code;

    @Column
    public String productName;

    @Column
    public BigDecimal unitPrice;

    @Column
    public BigDecimal unitCost;

    @Column
    public String category;

    @Column
    public String subCategory;
}
