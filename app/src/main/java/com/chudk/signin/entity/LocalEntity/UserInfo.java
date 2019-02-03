package com.chudk.signin.entity.LocalEntity;

/**
 * Created by chudaokai on 2018/11/22.
 */
public class UserInfo {
    private String username;
    private String passwd;
    private int ischeckin;
    private int ischeckout;
    private String addr;
    private int isenabled;

    public int getIsenabled() {
        return isenabled;
    }

    public void setIsenabled(int isenabled) {
        this.isenabled = isenabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public int getIscheckin() {
        return ischeckin;
    }

    public void setIscheckin(int ischeckin) {
        this.ischeckin = ischeckin;
    }

    public int getIscheckout() {
        return ischeckout;
    }

    public void setIscheckout(int ischeckout) {
        this.ischeckout = ischeckout;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void copyInfo(UserInfo us){
        if(us == null)
            return;
        this.setAddr(us.getAddr());
        this.setUsername(us.getUsername());
        this.setPasswd(us.getPasswd());
        this.setIscheckin(us.getIscheckin());
        this.setIscheckout(us.getIscheckout());
        this.setIsenabled(us.getIsenabled());
    }
}
