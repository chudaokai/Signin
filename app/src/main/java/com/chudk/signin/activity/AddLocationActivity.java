package com.chudk.signin.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.chudk.signin.R;
import com.chudk.signin.entity.LocationEntity.ALocationEntity;
import com.chudk.signin.entity.LocationEntity.CommonLocation;
import com.chudk.signin.util.ControlUtil;
import com.chudk.signin.util.FileUtil;
import com.chudk.signin.util.JSONUtil;

import java.util.ArrayList;
import java.util.List;

public class AddLocationActivity extends Activity {
    private String m_localFile;
    public static List<CommonLocation> LocationList;
    public static CommonLocation CurrentLocation;
    private static View ClickedView;
    private String preStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        if(CurrentLocation == null)
            preStr="新增";
        else
            preStr="更新";
        m_localFile = getFilesDir().getPath()+getString(R.string.file_location);
        bindButtonListener();
        if(CurrentLocation!=null){
            LoadLocation();
        }
    }

    /**
     * 加载位置信息
     */
    private void LoadLocation() {
        //名称
        EditText tv1 = (EditText) findViewById(R.id.etAddrMC);
        tv1.setText(CurrentLocation.getName());
        //经度
        tv1 = (EditText) findViewById(R.id.etDJ);
        tv1.setText(CurrentLocation.getLng_bd()+"");
        //纬度
        tv1 = (EditText) findViewById(R.id.etBW);
        tv1.setText(CurrentLocation.getLat_bd()+"");
        //描述（详细地址）
        tv1 = (EditText) findViewById(R.id.etAddrMS);
        tv1.setText(CurrentLocation.getAddr());
        //
        CheckBox ch1 = (CheckBox)findViewById(R.id.chkAddrEnable);
        ch1.setChecked(CurrentLocation.getIsenable()==1);
    }

    /**
     * 添加监听器
     */
    private void bindButtonListener() {
        Button btn1 = (Button)findViewById(R.id.btnAddSave);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!SaveInfoToLocation(v)) {
                    return;
                }
                AddLocationToList();
                SaveToFile();
                //ControlUtil.showDialog(AddLocationActivity.this,"添加位置成功！");
                AlertDialog alertDialog2 = new AlertDialog.Builder(AddLocationActivity.this)
                        .setTitle("提示信息")
                        .setMessage(preStr+"位置成功！")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                onBackPressed();
                            }
                        })
                        .create();
                alertDialog2.show();
            }
            //保存到文件中
            private void SaveToFile() {
                String json = JSONUtil.toJSON(LocationList);
                FileUtil.writeToFile(m_localFile, json);
            }

            //添加location到集合中
            private void AddLocationToList() {
                ALocationEntity us = getLocationFromList(CurrentLocation.getName());
                if(us == null)
                    LocationList.add(CurrentLocation);
                else
                    us.copyInfo(CurrentLocation);
            }

            //保存填写内容到location对象
            private boolean SaveInfoToLocation(View v) {
                if(CurrentLocation == null)
                    CurrentLocation = new CommonLocation();
                View view = (View)v.getParent();
                //名称
                EditText tv1 = (EditText) view.findViewById(R.id.etAddrMC);
                if("".equals(tv1.getText().toString())) {
                    ControlUtil.showDialog(AddLocationActivity.this,"请填写位置名称！");
                    return false;
                }
                CurrentLocation.setName(tv1.getText().toString());
                //经度
                tv1 = (EditText) view.findViewById(R.id.etDJ);
                if("".equals(tv1.getText().toString())) {
                    ControlUtil.showDialog(AddLocationActivity.this,"请填写经度！");
                    return false;
                }
                CurrentLocation.setLng_bd(Double.parseDouble(tv1.getText().toString()));
                //纬度
                tv1 = (EditText) view.findViewById(R.id.etBW);
                if("".equals(tv1.getText().toString())) {
                    ControlUtil.showDialog(AddLocationActivity.this,"请填写纬度！");
                    return false;
                }
                CurrentLocation.setLat_bd(Double.parseDouble(tv1.getText().toString()));
                //描述（详细地址）
                tv1 = (EditText) view.findViewById(R.id.etAddrMS);
                if("".equals(tv1.getText().toString())) {
                    ControlUtil.showDialog(AddLocationActivity.this,"请填写位置描述！");
                    return false;
                }
                CurrentLocation.setAddr(tv1.getText().toString());
                //
                CheckBox ch1 = (CheckBox)view.findViewById(R.id.chkAddrEnable);
                CurrentLocation.setIsenable(ch1.isChecked() ? 1 : 0);

                return true;
            }
        });

        btn1 = (Button)findViewById(R.id.btnAddCancel);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * 获取位置对象
     * @param name
     * @return
     */
    private ALocationEntity getLocationFromList(String name) {
        if(name == null || "".equals(name))
            return null;
        if(LocationList == null)
            LocationList = new ArrayList<CommonLocation>();
        for(int i=0;i<LocationList.size();i++){
            if(LocationList.get(i).getName().equals(name))
                return LocationList.get(i);
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_add_location, menu);
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
