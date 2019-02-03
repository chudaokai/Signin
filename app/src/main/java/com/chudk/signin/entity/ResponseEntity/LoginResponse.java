package com.chudk.signin.entity.ResponseEntity;

import java.util.List;

public class LoginResponse implements IResponseEntity {
    private String sessionkey;
    private String hrmorgshow;
    private List<QuickNews> quicknews;
    private String rongAppKey;
    private String commonGroupshow;
    private String mysubordinateshow;
    private List<String> messagetypes;
    private String version;
    private String openfireDomain;
    private String ryudidNew;
    private String headpic;
    private String openfireHost;
    private String openfireModule;
    private List<Navigation> navigation;
    private String sameDepartmentshow;
    private String createworkflow;
    private List<Module> modules;
    private String groupChatshow;
    private String allPeopleshow;
    private List<Group> groups;
    private String creategroupchat;

    public LoginResponse() {
    }

    public String getSessionkey() {
        return sessionkey;
    }

    public void setSessionkey(String sessionkey) {
        this.sessionkey = sessionkey;
    }

    public String getHrmorgshow() {
        return hrmorgshow;
    }

    public void setHrmorgshow(String hrmorgshow) {
        this.hrmorgshow = hrmorgshow;
    }

    public List<QuickNews> getQuicknews() {
        return quicknews;
    }

    public void setQuicknews(List<QuickNews> quicknews) {
        this.quicknews = quicknews;
    }

    public String getRongAppKey() {
        return rongAppKey;
    }

    public void setRongAppKey(String rongAppKey) {
        this.rongAppKey = rongAppKey;
    }

    public String getCommonGroupshow() {
        return commonGroupshow;
    }

    public void setCommonGroupshow(String commonGroupshow) {
        this.commonGroupshow = commonGroupshow;
    }

    public String getMysubordinateshow() {
        return mysubordinateshow;
    }

    public void setMysubordinateshow(String mysubordinateshow) {
        this.mysubordinateshow = mysubordinateshow;
    }

    public List<String> getMessagetypes() {
        return messagetypes;
    }

    public void setMessagetypes(List<String> messagetypes) {
        this.messagetypes = messagetypes;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOpenfireDomain() {
        return openfireDomain;
    }

    public void setOpenfireDomain(String openfireDomain) {
        this.openfireDomain = openfireDomain;
    }

    public String getRyudidNew() {
        return ryudidNew;
    }

    public void setRyudidNew(String ryudidNew) {
        this.ryudidNew = ryudidNew;
    }

    public String getHeadpic() {
        return headpic;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    public String getOpenfireHost() {
        return openfireHost;
    }

    public void setOpenfireHost(String openfireHost) {
        this.openfireHost = openfireHost;
    }

    public String getOpenfireModule() {
        return openfireModule;
    }

    public void setOpenfireModule(String openfireModule) {
        this.openfireModule = openfireModule;
    }

    public List<Navigation> getNavigation() {
        return navigation;
    }

    public void setNavigation(List<Navigation> navigation) {
        this.navigation = navigation;
    }

    public String getSameDepartmentshow() {
        return sameDepartmentshow;
    }

    public void setSameDepartmentshow(String sameDepartmentshow) {
        this.sameDepartmentshow = sameDepartmentshow;
    }

    public String getCreateworkflow() {
        return createworkflow;
    }

    public void setCreateworkflow(String createworkflow) {
        this.createworkflow = createworkflow;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public String getGroupChatshow() {
        return groupChatshow;
    }

    public void setGroupChatshow(String groupChatshow) {
        this.groupChatshow = groupChatshow;
    }

    public String getAllPeopleshow() {
        return allPeopleshow;
    }

    public void setAllPeopleshow(String allPeopleshow) {
        this.allPeopleshow = allPeopleshow;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public String getCreategroupchat() {
        return creategroupchat;
    }

    public void setCreategroupchat(String creategroupchat) {
        this.creategroupchat = creategroupchat;
    }
}
