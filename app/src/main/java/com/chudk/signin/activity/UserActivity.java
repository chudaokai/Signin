package com.chudk.signin.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.chudk.signin.R;
import com.chudk.signin.entity.LocalEntity.UserInfo;
import com.chudk.signin.entity.LocationEntity.CommonLocation;
import com.chudk.signin.util.ControlUtil;
import com.chudk.signin.util.FileUtil;
import com.chudk.signin.util.JSONUtil;

import java.util.ArrayList;
import java.util.List;


public class UserActivity extends Activity {
    private String m_localFile;
    private BaseAdapter adapter = null;
    List<UserInfo> list = new ArrayList<UserInfo>();
    private TextView tv1 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        m_localFile =getFilesDir().getPath()+this.getString(R.string.file_userinfo);
        //绑定按钮事件
        bindEventListener();
        //加载文件内容
        loadUserInfo();
    }

    /**
     * 加载用户信息
     */
    private void loadUserInfo() {
        String json = FileUtil.readFromFile(m_localFile);
        if(json == null || "".equals(json))
            return;
        try{

            list = JSONUtil.parseListFormJSON(json, UserInfo.class);
            if(list == null || list.size()==0)
                return;
            adapter = new BaseAdapter() {
                @Override
                public int getCount() {
                    return list.size();
                }

                @Override
                public UserInfo getItem(int position) {
                    return list.get(position);
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    LayoutInflater inflater = UserActivity.this.getLayoutInflater();
                    View view;

                    if (convertView==null) {
                        //因为getView()返回的对象，adapter会自动赋给ListView
                        view = inflater.inflate(R.layout.listview_userinfo, null);
                    }else{
                        view=convertView;
                        Log.i("info", "有缓存，不需要重新生成" + position);
                    }

                    UserInfo us = list.get(position);
                    tv1 = (TextView) view.findViewById(R.id.userName);//找到Textviewname
                    tv1.setText(us.getUsername());//设置参数

                    tv1 = (TextView) view.findViewById(R.id.location);//找到Textviewage
                    tv1.setText(us.getAddr());//设置参数

                    tv1 = (TextView) view.findViewById(R.id.tvUserChkin);//找到Textviewage
                    tv1.setText(us.getIscheckin() ==1?"是":"否");//设置参数

                    tv1 = (TextView) view.findViewById(R.id.tvUserChkout);//找到Textviewage
                    tv1.setText(us.getIscheckout() ==1?"是":"否");//设置参数

                    tv1 = (TextView) view.findViewById(R.id.tvUserEnable);//找到Textviewage
                    tv1.setText(us.getIsenabled() ==1?"是":"否");//设置参数
                    return view;
                }
            };
            ListView lv =(ListView)findViewById(R.id.lvUser);
            lv.setAdapter(adapter);

        }catch (Exception ex){

        }
    }

    /**
     * 绑定按钮事件
     */
    private void bindEventListener() {
        Button btnAddNew = (Button) this.findViewById(R.id.btnNewUser);
        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddUserActivity.UserList = list;
                AddUserActivity.CurrentUser = null;
                Intent i = new Intent(UserActivity.this,AddUserActivity.class);
                startActivityForResult(i,R.id.btnNewUser);
            }
        });
        btnAddNew = (Button) this.findViewById(R.id.btnNewUserBack);
        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ListView lv =(ListView)findViewById(R.id.lvUser);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AddUserActivity.UserList = list;
                AddUserActivity.CurrentUser=list.get(position);
                Intent i = new Intent(UserActivity.this,AddUserActivity.class);
                startActivity(i);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final UserInfo us = (UserInfo)parent.getItemAtPosition(position);
                AlertDialog alertDialog2 = new AlertDialog.Builder(UserActivity.this)
                        .setTitle("提示信息")
                        .setMessage("是否删除用户："+us.getUsername()+"？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(list.contains(us)){
                                    list.remove(us);
                                    String json = JSONUtil.toJSON(list);
                                    FileUtil.writeToFile(m_localFile,json);
                                    if(adapter!=null)
                                        adapter.notifyDataSetChanged();
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create();
                alertDialog2.show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_user, menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(adapter!=null)
            adapter.notifyDataSetChanged();
    }
}
