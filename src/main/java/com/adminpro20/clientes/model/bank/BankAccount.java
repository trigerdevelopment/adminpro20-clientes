package com.adminpro20.clientes.model.bank;

import com.adminpro20.clientes.model.BaseEntity;
import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "bank_account")
public class BankAccount extends BaseEntity {


    @Column()
    public String name;
    @Column()
    public String account;
    @Column()
    public Date date;
}
