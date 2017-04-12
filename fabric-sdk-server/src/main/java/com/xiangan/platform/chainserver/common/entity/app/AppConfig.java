package com.xiangan.platform.chainserver.common.entity.app;

import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;

import java.util.ArrayList;

/**
 * TODO
 *
 * @Creater liuzhudong
 * @Date 2017/4/11 13:59
 * @Version 1.0
 * @Copyright
 */
public class AppConfig implements User {

    private String name;

    private ArrayList<String> roles;

    private String account;

    private String affiliation;

    private String MSPID;

    private Enrollment enrollment;

    private String enrollmentPasswd;

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

    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }

    @Override
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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
}
