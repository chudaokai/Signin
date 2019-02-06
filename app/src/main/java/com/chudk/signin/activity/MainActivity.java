package com.chudk.signin.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.chudk.signin.R;
import com.chudk.signin.entity.LocalEntity.UserInfo;
import com.chudk.signin.entity.LocationEntity.CommonLocation;
import com.chudk.signin.entity.RequestEntity.ARequestEntity;
import com.chudk.signin.entity.RequestEntity.CheckInRequest;
import com.chudk.signin.entity.RequestEntity.LoginRequestEntity;
import com.chudk.signin.entity.ResponseEntity.LoginResponse;
import com.chudk.signin.entity.LocationEntity.ALocationEntity;
import com.chudk.signin.util.ControlUtil;
import com.chudk.signin.util.FileUtil;
import com.chudk.signin.util.HttpUtil;
import com.chudk.signin.util.JSONUtil;

import java.util.List;


public class MainActivity extends Activity {
    private static final String SPLIT_CHAR="@@@@@";
    private String m_userFile;
    private String m_locationFile;
    private LinearLayout m_svLog;
    private TextView textViewMsg;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            textViewMsg = new TextView(MainActivity.this);
            textViewMsg.setText((String)msg.obj);
            m_svLog.addView(textViewMsg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_userFile = getFilesDir().getPath()+getString(R.string.file_userinfo);
        m_locationFile = getFilesDir().getPath()+getString(R.string.file_location);

        m_svLog = (LinearLayout)findViewById(R.id.svLog);
        bindButtonClickEvent();
    }

    /**
     * 绑定按钮点击事件
     */
    private void bindButtonClickEvent(){

        //用户信息
        Button btnUser = (Button)findViewById(R.id.btnUser);
        btnUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,UserActivity.class);
                startActivity(i);
            }
        });
        //位置信息
        Button btnLoc = (Button)findViewById(R.id.btnLocation);
        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,LocationActivity.class);
                startActivity(i);
            }
        });
        //签到
        Button btnTestin = (Button)findViewById(R.id.btnSignin);
        btnTestin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                List<UserInfo> list = getEnabledUsers("checkin");
                for(int i=0;i<list.size();i++){
                    doSign(list.get(i),"checkin");
                }
            }
        });
        //签退
        Button btnTestout = (Button)findViewById(R.id.btnSignout);
        btnTestout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<UserInfo> list = getEnabledUsers("checkout");
                for(int i=0;i<list.size();i++){
                    doSign(list.get(i),"checkout");
                }
            }
        });
    }

    private CommonLocation getLocationByName(String name){
        String json = FileUtil.readFromFile(m_locationFile);
        if(json == null || "".equals(json))
            return null;
        List<CommonLocation> list = JSONUtil.parseListFormJSON(json,CommonLocation.class);
        for(int i=0;i<list.size();i++){
            if(list.get(i).getName().equals(name)){
                return list.get(i);
            }
        }
        return null;
    }
    private List<UserInfo> getEnabledUsers(String checkType){
        String json = FileUtil.readFromFile(m_userFile);
        if(json == null || "".equals(json))
            return null;
        List<UserInfo> list = JSONUtil.parseListFormJSON(json,UserInfo.class);

        for(int i=list.size()-1;i>=0;i--){
            if(list.get(i).getIsenabled()==0)
            {
                list.remove(i);
                continue;
            }
            if("checkin".equals(checkType) && list.get(i).getIscheckin() ==0){
                list.remove(i);
                continue;
            }
            else if("checkout".equals(checkType) && list.get(i).getIscheckout() ==0){
                list.remove(i);
                continue;
            }
        }
        return list;
    }

    private void doSign(UserInfo user, String type){
        final String _user = user.getUsername();
        final String _pwd = user.getPasswd();
        final String _addr = user.getAddr();
        final String _type = type;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Message msg = new Message();
                msg.what = 0;
                String mm = "";
                try {
                    LoginRequestEntity login = new LoginRequestEntity(_user, _pwd);
                    HttpUtil.doPost(login);
                    String result = getResponse(login);
                    if(result!=null && result.indexOf("error")>0){
                        mm = _user+" "+_type+" "+result+"\n";
                    }else {
                        LoginResponse res = JSONUtil.parseFromJSON(result, LoginResponse.class);
                        ALocationEntity location = getLocationByName(_addr);
                        if(location == null){
                            mm=_user+" "+_type+" 位置："+_addr+" 不存在\n";
                        }else {
                            CheckInRequest checkin = new CheckInRequest();
                            checkin.setQueryCookies(login.getQueryCookies());
                            checkin.setType(_type);
                            checkin.setSessionkey(res.getSessionkey());
                            checkin.setLatlng(location.getLatlng());
                            checkin.setAddr(location.getAddr());
                            checkin.revalueQueryString();
                            HttpUtil.doGet(checkin);
                            result = getResponse(checkin);
                            mm = _user + " " + _type + " " + result + "\n";
                        }
                    }
                }catch (Exception ex){
                    mm = ex.getMessage();
                }
                msg.obj = mm;
                handler.sendMessage(msg);
            }
        }).start();
    }

    private String getResponse(ARequestEntity requestEntity) {
        if(requestEntity.getQueryResponseState()>0)
            return requestEntity.getQueryResponse();
        while(requestEntity.getQueryResponseState()<=0){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(requestEntity.getQueryResponseState()>0)
                return requestEntity.getQueryResponse();
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        boolean flag = true;
        switch(id){
            case R.id.expFile:
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                    try{
                        String path = Environment.getExternalStorageDirectory().getPath()+getString(R.string.file_expimp);
                        StringBuilder sb = new StringBuilder();
                        String json = FileUtil.readFromFile(m_userFile);
                        if(json == null ||"".equals(json) || "null".equalsIgnoreCase(json))
                            json="";
                        sb.append(json);sb.append(SPLIT_CHAR);
                        json = FileUtil.readFromFile(m_locationFile);
                        if(json == null ||"".equals(json) || "null".equalsIgnoreCase(json))
                            json="";
                        sb.append(json);
                        FileUtil.writeToFile(path,sb.toString());
                        ControlUtil.showDialog(MainActivity.this,"导出配置成功！");
                    }catch (Exception ex){
                        flag=false;
                        ControlUtil.showDialog(MainActivity.this,"保存文件时出错："+ex.getMessage());
                    }
                }else{
                    ControlUtil.showDialog(MainActivity.this,"没有找到外部存储设备！");
                    flag= false;
                }
                break;
            case R.id.impFile:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");//无类型限制
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, R.id.impFile);
                break;
            case R.id.about:
                ControlUtil.showDialog(MainActivity.this,"这是我的程序！");
                break;
        }

        return flag;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case R.id.impFile:  //导入配置选项
                if(resultCode == Activity.RESULT_OK){
                    Uri uri = data.getData();
                    String file = getFileName(uri.getPath());
                    if(file == null)
                    {
                        ControlUtil.showDialog(MainActivity.this,"选择文件有误，请重新选择。");
                        return;
                    }
                    String path = Environment.getExternalStorageDirectory().getPath();
                    String json = FileUtil.readFromFile(path+file);
                    if(json == null || SPLIT_CHAR.equals(json)){
                        ControlUtil.showDialog(MainActivity.this,"选择文件有误，请重新选择。");
                        return;
                    }
                    try {
                        //只有location
                        if (json.startsWith(SPLIT_CHAR)) {
                            FileUtil.writeToFile(m_locationFile, json.substring(SPLIT_CHAR.length()));
                        } else if (json.endsWith(SPLIT_CHAR)) {
                            FileUtil.writeToFile(m_userFile, json.substring(0, json.length() - SPLIT_CHAR.length()));
                        } else {
                            String[] strs = json.split(SPLIT_CHAR);
                            FileUtil.writeToFile(m_userFile, strs[0]);
                            FileUtil.writeToFile(m_locationFile, strs[1]);
                        }
                        ControlUtil.showDialog(MainActivity.this,"导入配置成功！");
                    }catch (Exception ex){
                        ControlUtil.showDialog(MainActivity.this,ex.getMessage());
                    }
                }
                break;
        }
    }

    //获取文件名
    private String getFileName(String path) {
        if(path.startsWith("/external_files")){
            return path.replace("/external_files","");
        }
        return null;
    }
}
