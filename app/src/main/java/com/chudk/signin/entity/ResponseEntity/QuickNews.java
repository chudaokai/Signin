package com.chudk.signin.entity.ResponseEntity;

public class QuickNews implements IResponseEntity {
    private String id;
    private String module;
    private String scope;
    private String iconname;
    private String componentid;
    private String label;
    private String url;

    public QuickNews() {
    }

    public QuickNews(String id, String module, String scope, String iconname, String componentid, String label, String url) {
        this.id = id;
        this.module = module;
        this.scope = scope;
        this.iconname = iconname;
        this.componentid = componentid;
        this.label = label;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getIconname() {
        return iconname;
    }

    public void setIconname(String iconname) {
        this.iconname = iconname;
    }

    public String getComponentid() {
        return componentid;
    }

    public void setComponentid(String componentid) {
        this.componentid = componentid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
