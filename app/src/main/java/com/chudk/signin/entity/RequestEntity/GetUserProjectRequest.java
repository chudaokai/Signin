package com.chudk.signin.entity.RequestEntity;

import com.chudk.signin.util.JSONUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GetUserProjectRequest extends ARequestEntity {

    public GetUserProjectRequest(){
        this.setQueryUrl("/mobilemode/browser/commonBrowserAction.jsp");
        this.setQueryMethod("GET");

        Map<String,String> queryString = new HashMap<String, String>();
        queryString.put("action","getListData");
        queryString.put("pageNo","1");
        queryString.put("browseId","161");
        queryString.put("browseName","browser.zs_project_name_worklog");
        queryString.put("seelctedIds","");
        ProjectInfoRequest req = new ProjectInfoRequest(new Date());
        queryString.put("params", req.toString());
        queryString.put("",(new Date()).getTime()+"");
        queryString.put("searchKey","");
        queryString.put("pageSize","10");
        this.setQueryString(queryString);
    }
}
