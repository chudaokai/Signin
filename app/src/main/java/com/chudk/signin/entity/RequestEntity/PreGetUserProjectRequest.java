package com.chudk.signin.entity.RequestEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chudaokai on 2018/11/13.
 */
public class PreGetUserProjectRequest extends ARequestEntity {

    public PreGetUserProjectRequest(){
        this.setQueryUrl("/mobilemode/browser/commonBrowser.jsp");
        this.setQueryMethod("GET");

        Map<String,String> queryString = new HashMap<String, String>();
        queryString.put("noHeader","1");
        queryString.put("fieldId","b95928d30c0a4ef69d17f58aa09ede57");
        queryString.put("fieldSpanId","b95928d30c0a4ef69d17f58aa09ede57_span");
        queryString.put("seelctedIds","");
        queryString.put("browseId","161");
        queryString.put("browseName","browser.zs_project_name_worklog");
        queryString.put("browseText","项目名称");
        ProjectInfoRequest req = new ProjectInfoRequest(new Date());
        queryString.put("params", req.toString());
        this.setQueryString(queryString);
    }
}
