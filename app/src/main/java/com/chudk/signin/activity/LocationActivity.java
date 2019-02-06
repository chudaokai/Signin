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
import com.chudk.signin.entity.LocationEntity.CommonLocation;
import com.chudk.signin.util.FileUtil;
import com.chudk.signin.util.JSONUtil;

import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends Activity {
    private String m_localFile;
    private List<CommonLocation> list;
    private BaseAdapter adapter;
    private TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        m_localFile = getFilesDir().getPath()+getString(R.string.file_location);
        list = new ArrayList<CommonLocation>();
        bindButtonListener();
        loadLocationList();
    }

    /**
     * 加载位置列表
     */
    private void loadLocationList() {
        String json = FileUtil.readFromFile(m_localFile);
        if(json == null || "".equals(json))
            return;
        try{
            Log.i("info",json);
            list = JSONUtil.parseListFormJSON(json, CommonLocation.class);
            if(list == null || list.size()==0)
                return;
            adapter = new BaseAdapter() {
                @Override
                public int getCount() {
                    return list.size();
                }

                @Override
                public CommonLocation getItem(int position) {
                    return list.get(position);
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    LayoutInflater inflater = LocationActivity.this.getLayoutInflater();
                    View view;

                    if (convertView==null) {
                        //因为getView()返回的对象，adapter会自动赋给ListView
                        view = inflater.inflate(R.layout.listview_location, null);
                    }else{
                        view=convertView;
                        Log.i("info", "有缓存，不需要重新生成" + position);
                    }
                    tv1 = view.findViewById(R.id.locName);//找到Textviewname
                    tv1.setText(list.get(position).getName());//设置参数

                    tv1 = view.findViewById(R.id.locAddr);//找到Textviewage
                    tv1.setText(list.get(position).getAddr());//设置参数

                    tv1 = view.findViewById(R.id.tvLocLng);//找到Textviewage
                    tv1.setText(list.get(position).getLng_bd()+"");//设置参数

                    tv1 = view.findViewById(R.id.tvLocLat);//找到Textviewage
                    tv1.setText(list.get(position).getLat_bd()+"");//设置参数

                    return view;
                }
            };
            ListView lv =(ListView)findViewById(R.id.lvLocation);
            lv.setAdapter(adapter);

        }catch (Exception ex){

        }
    }

    private void bindButtonListener() {
        Button btn1 = (Button)findViewById(R.id.btnAddLoc);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddLocationActivity.LocationList = list;
                AddLocationActivity.CurrentLocation=null;
                Intent intent = new Intent(LocationActivity.this,AddLocationActivity.class);
                startActivityForResult(intent, R.id.btnAddLoc);
            }
        });

        btn1 = (Button)findViewById(R.id.buLocBack);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ListView lv = (ListView)findViewById(R.id.lvLocation);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AddLocationActivity.LocationList = list;
                AddLocationActivity.CurrentLocation = list.get(position);
                Intent intent = new Intent(LocationActivity.this,AddLocationActivity.class);
                startActivity(intent);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final CommonLocation us = (CommonLocation)parent.getItemAtPosition(position);
                AlertDialog alertDialog2 = new AlertDialog.Builder(LocationActivity.this)
                        .setTitle("提示信息")
                        .setMessage("是否删除位置："+us.getName()+"？")
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
        //getMenuInflater().inflate(R.menu.menu_location, menu);
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
