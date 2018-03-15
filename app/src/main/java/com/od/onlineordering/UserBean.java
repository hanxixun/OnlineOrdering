package com.od.onlineordering;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by ${hanxixun} on 2017/5/13.
 */

public class UserBean extends BmobObject  {
    //手机号
    String userPhone;
    //头像
    BmobFile Photo;
    //密码
    String passWord;
    //金额
    String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public BmobFile getPhoto() {
        return Photo;
    }

    public void setPhoto(BmobFile photo) {
        Photo = photo;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

}
