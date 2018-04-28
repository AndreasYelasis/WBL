package com.andreas.wbl;

/**
 * Created by Andreas on 11/3/2018.
 */

public class Report {

    //fields
    private int report_id;
    private String area;
    private String address;
    private int zip_code;
    private String customer_name;
    private String timestamp_taken;
    private int phone;
    private String synergio;
    private String timestamp_completed;
    private String thema;
    private String reason;
    private String action;
    private String diametros;
    private String type;
    private String damage;
    private int vathos;
    private String photo;
    private int lat;
    private int lon;
    private int completed;

    //default constructor
    public Report( int report_id,
                   String area,
                   String address,
                   int zip_code,
                   String customer_name,
                   String timestamp_taken,
                   int phone,
                   String synergio,
                   String timestamp_completed,
                   String thema,
                   String reason,
                   String action,
                   String diametros,
                   String type,
                   String damage,
                   int vathos,
                   String photo,
                   int lat,
                   int lon,
                   int completed) {
        this.report_id = report_id;
        this.area = area;
        this.address = address;
        this.zip_code = zip_code;
        this.customer_name = customer_name;
        this.timestamp_taken = timestamp_taken;
        this.phone = phone;
        this.synergio = synergio;
        this.timestamp_completed = timestamp_completed;
        this.thema = thema;
        this.reason = reason;
        this.action = action;
        this.diametros = diametros;
        this.type = type;
        this.damage = damage;
        this.vathos = vathos;
        this.photo = photo;
        this.lat = lat;
        this.lon = lon;
        this.completed = completed;
    }

    //methods set,get
    public int getReportId() {
        return report_id;
    }

    public void setReportId(int report_id) {
        this.report_id = report_id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getZipCode() {
        return zip_code;
    }

    public void setZipCode(int zip_code) {
        this.zip_code = zip_code;
    }

    public String getCustomerName() {
        return customer_name;
    }

    public void setCustomerName(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getTimestampTaken() {
        return timestamp_taken;
    }

    public void setTimestampTaken(String timestamp_taken) {
        this.timestamp_taken = timestamp_taken;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getSynergio() {
        return synergio;
    }

    public void setSynergio(String synergio) {
        this.synergio = synergio;
    }

    public String getTimestampCompleted() {
        return timestamp_completed;
    }

    public void setTimestampCompleted(String timestamp_completed) {
        this.timestamp_completed = timestamp_completed;
    }

    public String getThema() {
        return thema;
    }

    public void setThema(String thema) {
        this.thema = thema;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDiametros() {
        return diametros;
    }

    public void setDiametros(String diametros) {
        this.diametros = diametros;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDamage() {
        return damage;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }

    public int getVathos() {
        return vathos;
    }

    public void setVathos(int vathos) {
        this.vathos = vathos;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public int getLon() {
        return lon;
    }

    public void setLon(int lon) {
        this.lon = lon;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }
}