package com.database.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by perfection on 17-3-1.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemUser implements Serializable{

    private String account;

    private String pwd;
}
