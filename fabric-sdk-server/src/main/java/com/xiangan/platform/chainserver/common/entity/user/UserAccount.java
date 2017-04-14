package com.xiangan.platform.chainserver.common.entity.user;

import com.xiangan.platform.chainserver.common.domain.BaseEntity;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;

import java.util.ArrayList;

/**
 * 用户账户
 *
 * @Creater liuzhudong
 * @Date 2017/4/11 14:11
 * @Version 1.0
 * @Copyright
 */
public class UserAccount extends BaseEntity implements User {
    /**
     * 用户证书颁发CA的URL
     */
    private String caServerURL;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户角色
     */
    private ArrayList<String> roles;

    /**
     * 用户账户
     */
    private String account;

    /**
     * 用户组织
     */
    private String affiliation;

    /**
     * 用户组织MSPID
     */
    private String MSPID;

    /**
     * 用户登录信息
     */
    private Enrollment enrollment;

    /**
     * 用户登录密码
     */
    private String enrollmentSecret;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public ArrayList<String> getRoles() {
        return roles;
    }

    @Override
    public String getAccount() {
        return account;
    }

    @Override
    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    @Override
    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    @Override
    public String getMSPID() {
        return MSPID;
    }

    public void setMSPID(String MSPID) {
        this.MSPID = MSPID;
    }

    public String getEnrollmentSecret() {
        return enrollmentSecret;
    }

    public void setEnrollmentSecret(String enrollmentSecret) {
        this.enrollmentSecret = enrollmentSecret;
    }

    public String getCaServerURL() {
        return caServerURL;
    }

    public void setCaServerURL(String caServerURL) {
        this.caServerURL = caServerURL;
    }
}
