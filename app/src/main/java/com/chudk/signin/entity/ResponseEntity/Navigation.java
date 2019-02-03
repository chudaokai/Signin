package com.chudk.signin.entity.ResponseEntity;

public class Navigation implements IResponseEntity {
    private String id;
    private String _default;
    private String ulogo_url;
    private String logo_url;
    private String url;
    private String displayname;

    public Navigation() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDefault() {
        return _default;
    }

    public void setDefault(String _default) {
        this._default = _default;
    }

    public String getUlogo_url() {
        return ulogo_url;
    }

    public void setUlogo_url(String ulogo_url) {
        this.ulogo_url = ulogo_url;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }
}
