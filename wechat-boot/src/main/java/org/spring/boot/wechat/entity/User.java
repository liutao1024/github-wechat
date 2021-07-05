package org.spring.boot.wechat.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class User {
    @Id
    private String number;
    private String name;
    private String id_no;
    private String phone;
    private String birthday;
    private String age;
    private String six;
    private String address;
    private String e_mail;
}
