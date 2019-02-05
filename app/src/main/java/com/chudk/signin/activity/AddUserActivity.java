package com.chudk.signin.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.chudk.signin.R;
import com.chudk.signin.entity.LocalEntity.UserInfo;
import com.chudk.signin.entity.LocationEntity.ALocationEntity;
import com.chudk.signin.entity.LocationEntity.CommonLocation;
import com.chudk.signin.util.ControlUtil;
import com.chudk.signin.util.FileUtil;
import com.chudk.signin.util.JSONUtil;

import java.util.ArrayList;
import java.util.List;


public class AddUserActivity extends Activity {
    private String m_locaFile;
    private String m_userFile;
    public static List<UserInfo> UserList;
    public static UserInfo CurrentUser;
    private BaseAdapter adp = null;
    private List<CommonLocation> m_locList;
    private String preStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        if(CurrentUser == null)
            preStr="新增";
        else
            preStr="修改";
        bindButtonListener();
        m_locaFile = getFilesDir().getPath()+this.getString(R.string.file_location);
        m_userFile = getFilesDir().getPath()+this.getString(R.string.file_userinfo);
        m_locList=new ArrayList<CommonLocation>();
        String json = FileUtil.readFromFile(m_locaFile);
        if(json !=null && !"".equals(json)) {
            m_locList = JSONUtil.parseListFormJSON(json,CommonLocation.class);
            SetSpinnerAdapter();
        }
        if(CurrentUser!=null)
            LoadClickedUserInfo();
    }

    /**
     * 绑定点击事件
     */
    private void bindButtonListener() {
        //保存
        Button btn = (Button)findViewById(R.id.btnUserSave);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!SaveInfoToUser(v)) {
                    return;
                }
                AddUserToList();
                SaveToFile();
                //ControlUtil.showDialog(AddUserActivity.this,"添加用户成功！");
                AlertDialog alertDialog2 = new AlertDialog.Builder(AddUserActivity.this)
                        .setTitle("提示信息")
                        .setMessage(preStr+"用户成功！")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                onBackPressed();
                            }
                        })
                        .create();
                alertDialog2.show();
            }

            /**
             * 保存数据到文件
             */
            private void SaveToFile() {
                String json = JSONUtil.toJSON(UserList);
                FileUtil.writeToFile(m_userFile,json);
            }

            /**
             * 添加用户到列表中
             */
            private void AddUserToList() {
                UserInfo us = getUserInfoFromList(CurrentUser.getUsername());
                if(us == null)
                    UserList.add(CurrentUser);
                else
                    us.copyInfo(CurrentUser);
            }

            /**
             * 读取填写的信息到UserInfo对象
             */
            private boolean SaveInfoToUser(View v) {
                if(CurrentUser == null)
                    CurrentUser = new UserInfo();
                View view = (View)v.getParent();
                //用户名
                EditText tv1 = (EditText) view.findViewById(R.id.etUser);
                if("".equals(tv1.getText().toString())) {
                    ControlUtil.showDialog(AddUserActivity.this,"请填写用户名！");
                    return false;
                }
                CurrentUser.setUsername(tv1.getText().toString());
                //密码
                tv1 = (EditText) view.findViewById(R.id.etPasswd);
                if("".equals(tv1.getText().toString())) {
                    ControlUtil.showDialog(AddUserActivity.this,"请填写用户密码！");
                    return false;
                }
                CurrentUser.setPasswd(tv1.getText().toString());
                //地址
                Spinner sp = (Spinner)view.findViewById(R.id.spinnerLocation);
                ALocationEntity loc = (ALocationEntity)sp.getSelectedItem();
                if(loc == null) {
                    ControlUtil.showDialog(AddUserActivity.this,"请先填写一个地址信息！");
                    return false;
                }
                CurrentUser.setAddr(loc.getName());
                //
                CheckBox ch1 = (CheckBox)view.findViewById(R.id.chkIn);
                CurrentUser.setIscheckin(ch1.isChecked()?1:0);

                ch1 = (CheckBox)view.findViewById(R.id.chkOut);
                CurrentUser.setIscheckout(ch1.isChecked() ? 1 : 0);

                ch1 = (CheckBox)view.findViewById(R.id.chkEnabled);
                CurrentUser.setIsenabled(ch1.isChecked() ? 1 : 0);

                return true;
            }
        });
        //取消
        btn = (Button)findViewById(R.id.btnUserCancel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * 设置spinner适配器
     */
    private void SetSpinnerAdapter() {
        adp = new BaseAdapter() {
            @Override
            public int getCount() {
                return m_locList.size();
            }

            @Override
            public CommonLocation getItem(int position) {
                return m_locList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = View.inflate(AddUserActivity.this, R.layout.spinner_location, null);
                }
                CommonLocation loc = m_locList.get(position);
                TextView tv1 = (TextView)convertView.findViewById(R.id.spLocMC);
                tv1.setText(loc.getName());

                tv1 = (TextView)convertView.findViewById(R.id.spLocMS);
                tv1.setText(loc.getAddr());

                return convertView;
            }
        };
        Spinner sp = (Spinner)findViewById(R.id.spinnerLocation);
        sp.setAdapter(adp);
    }

    /**
     * 加载选中的用户信息
     */
    private void LoadClickedUserInfo() {
        //用户名
        EditText tv1 = (EditText) findViewById(R.id.etUser);
        tv1.setText(CurrentUser.getUsername());//设置参数
        //密码
        tv1 = (EditText) findViewById(R.id.etPasswd);
        tv1.setText(CurrentUser.getPasswd());//设置参数
        //地址
        Spinner sp = (Spinner)findViewById(R.id.spinnerLocation);
        boolean f = false;
        for(int i=0;i<m_locList.size();i++){
            if(m_locList.get(i).getName().equals(CurrentUser.getAddr())){
                sp.setSelection(i);
                f=true;
                break;
            }
        }
        if(!f){
            ControlUtil.showDialog(AddUserActivity.this,"未找到位置："+CurrentUser.getAddr());
        }
        //
        CheckBox ch1 = (CheckBox)findViewById(R.id.chkIn);
        ch1.setChecked(CurrentUser.getIscheckin()==1);

        ch1 = (CheckBox)findViewById(R.id.chkOut);
        ch1.setChecked(CurrentUser.getIscheckout()==1);

        ch1 = (CheckBox)findViewById(R.id.chkEnabled);
        ch1.setChecked(CurrentUser.getIsenabled()==1);
    }

    private UserInfo getUserInfoFromList(String userName){
        if(userName == null || "".equals(userName))
            return null;
        if(UserList == null)
            UserList = new ArrayList<UserInfo>();
        for(int i=0;i<UserList.size();i++){
            if(UserList.get(i).getUsername().equals(userName))
                return UserList.get(i);
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_add_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
