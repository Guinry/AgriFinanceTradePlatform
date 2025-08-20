package com.qst.agrifinancetrade.entity;

import lombok.Data;

/**
 * @author lzh
 * @creat 2022-08-10 14:57
 */
@Data
public class Recommend {
    private String userName;
    private String realName;
    private String phone;
    private String address;

    private String avatar;
    private Integer area;

    private String item;

    private String amount;
    private Integer num;
}
