package com.chudk.signin.entity.LocationEntity;

import java.io.Serializable;

public abstract class ALocationEntity implements Serializable {
    private double lat_bd ;
    private double lng_bd ;

    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }

    private String latlng;
    private String addr;
    private int isenable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public int getIsenable() {
        return isenable;
    }

    public void setIsenable(int isenable) {
        this.isenable = isenable;
    }

    public double getLat_bd() {
        return lat_bd;
    }

    public void setLat_bd(double lat_bd) {
        this.lat_bd = lat_bd;
    }

    public double getLng_bd() {
        return lng_bd;
    }

    public void setLng_bd(double lng_bd) {
        this.lng_bd = lng_bd;
    }

    /**
     * 百度坐标转高德坐标
     * @param bd_lng 经度
     * @param bd_lat 维度
     * @return
     */
    private String convertBDToGD(double bd_lng,double bd_lat){
        double X_PI = Math.PI * 3000.0 / 180.0;
        double x = bd_lng - 0.0065+Math.random()*0.0005;
        double y = bd_lat - 0.006+Math.random()*0.0005;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * X_PI);
        double gg_lng = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);
        return gg_lat+","+gg_lng;
    }

    public String getLatlng(){
        return convertBDToGD(lng_bd,lat_bd);
    }

    public String getAddr(){
        return addr;
    }

    public void setAddr(String s){
        this.addr = s;
    }

    public static ALocationEntity createInstance(String name){
        if(null == name || "".equals(name))
            return null;
        return null;
    }

    public String toString(){
        return getAddr();
    }

    public void copyInfo(ALocationEntity loc){
        if(loc == null)
            return;
        this.setName(loc.getName());
        this.setAddr(loc.getAddr());
        this.setLat_bd(loc.getLat_bd());
        this.setLng_bd(loc.getLng_bd());
        this.setIsenable(loc.getIsenable());
    }
}
