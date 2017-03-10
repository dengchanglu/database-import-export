package com.database.vo;

import com.database.utils.HbaseAnnotation;
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
public class AuthenticationVo implements Serializable{

    @HbaseAnnotation(familyName = "fam_base_info")
    private String prefix;

    @HbaseAnnotation(familyName = "fam_base_info")
    private String phone;

    @HbaseAnnotation(familyName = "fam_base_info")
    private String province;

    @HbaseAnnotation(familyName = "fam_base_info")
    private String city;

    @HbaseAnnotation(familyName = "fam_base_info")
    private String isp;

    @HbaseAnnotation(familyName = "fam_base_info")
    private String post_code;

    @HbaseAnnotation(familyName = "fam_base_info")
    private String city_code;

    @HbaseAnnotation(familyName = "fam_base_info")
    private String area_code;

    @HbaseAnnotation(familyName = "fam_base_info")
    private String types;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }
}
