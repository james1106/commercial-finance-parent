package com.xiangan.platform.demo.user;

import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;

import java.util.ArrayList;

/**
 * ca 用户数据定义
 * @Creater liuzhudong
 * @Date 2017/3/31 10:05
 * @Version 1.0
 * @Copyright
 */
public class CaUser implements User {
    private String name;
    private ArrayList<String> roles;
    private String account;
    private String affiliation;
    private String MSPID;
    private Enrollment enrollment;

    public CaUser(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public void setMSPID(String MSPID) {
        this.MSPID = MSPID;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    @Override
    public String getName() {
        return name;
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

    @Override
    public Enrollment getEnrollment() {
        return enrollment;
    }

    @Override
    public String getMSPID() {
        return MSPID;
    }

    @Override
    public String toString() {
        return "CaUser{" +
                "name='" + name + '\'' +
                ", roles=" + roles +
                ", account='" + account + '\'' +
                ", affiliation='" + affiliation + '\'' +
                ", MSPID='" + MSPID + '\'' +
                ", enrollment={\nkey=" + enrollment.getKey().getFormat() + "\n" +
                "publicKey=" + enrollment.getPublicKey() + "\n" +
                "cert=" + enrollment.getCert() + "\n}\n" +
                '}';
    }
}
