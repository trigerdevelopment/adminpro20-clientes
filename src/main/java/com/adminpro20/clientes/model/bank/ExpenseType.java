package com.adminpro20.clientes.model.bank;

import com.adminpro20.clientes.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table
public class ExpenseType extends BaseEntity {

    String expense;
}
