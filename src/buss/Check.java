/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buss;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author oadm
 */
public class Check {
    void checkAll(){
        /*
        1 Проверяем сначала глубину архива и файлов.
        2 Затем проверяем - не пора ли делать архив.
        */
        checkAndClear();
        checkAndMake();
    }
    void checkAndClear(){
        
        //1.1 Получаем и сравниваем дату созданного архива с разницей в днях
        String values[] = new String[2];
        values[0]="id";
        values[1]="date_created";
        //String db = Configuration.getDBName();
        SQL sql = new SQL();
        ArrayList<String[]> record_bu = sql.select("select * from `list` order by `id` ASC LIMIT 1", values);
        String date_db = record_bu.get(0)[1];
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date_sql=null;
        try{
        date_sql = df.parse(date_db);
        }catch(ParseException e){
            e.printStackTrace();
        }
        long now = new Date().getTime();
        long db_time = date_sql.getTime();
        long diff=now-db_time;
        int days=(int)diff/86400000;
        int depth_arch=Configuration.getDepth();
        //System.out.println(diff);
        //System.out.println(days);
        //System.out.println(depth_arch);
        if(days>depth_arch){
            BackUp bu = new BackUp();
            bu.delete(Integer.parseInt(record_bu.get(0)[0]));
            checkAndClear();
        }
        
    }
    void checkAndMake(){
        //2 Получаем последнюю дату
        //Сравниваем с нау
        String values[] = new String[2];
        values[0]="id";
        values[1]="date_created";
        //String db = Configuration.getDBName();
        SQL sql = new SQL();
        ArrayList<String[]> record_bu = sql.select("select * from `list` order by `id` DESC LIMIT 1", values);
        String date_db = record_bu.get(0)[1];
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date_sql=null;
        try{
        date_sql = df.parse(date_db);
        }catch(ParseException e){
            e.printStackTrace();
        }
        long now = new Date().getTime();
        long db_time = date_sql.getTime();
        long diff=now-db_time;
        int time=(int)diff/3600000;
        int max_time=Configuration.getTime();
        //System.out.println(diff);
        //System.out.println(time);
        //System.out.println(max_time);
        if(max_time<time){
            BackUp bu = new BackUp();
            bu.make("auto");
        }
    }
}
