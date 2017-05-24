/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buss;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author oadm
 */
public class BackUp {
    private int id;

    void show(){
        String[] values=new String[3];
            values[0]="id";
            values[1]="date_created";
            values[2]="res";
            SQL sh = new SQL();
            ArrayList<String[]> ar = sh.select("select * from `list`", values);
            System.out.println("id          date                   commit");
            for(String[] arr : ar){
                System.out.println(arr[0]+"         "+arr[1]+"          "+arr[2]);
            }
    }
    void make(String s){
        if(s.equals("")){
        MakeBU new_bu=new MakeBU();
        new_bu.make();
        }else{
        MakeBU new_bu=new MakeBU(s);
        new_bu.make();   
        }
    }
    void restore(int id){
        Restore rs = new Restore(id);
        rs.restore();
        
    }
    void delete(int id){
        SQL rq = new SQL();
        String values[] = new String[2];
        values[0]="id";
        values[1]="date_created";
        ArrayList<String[]> to_del=rq.select("select * from `list` where `id`='"+id+"'", values);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=null;
        try{
        date = sf.parse(to_del.get(0)[1]);
        }catch(ParseException e){
            
        }
        SimpleDateFormat name_form=new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String filename=name_form.format(date.getTime());
        String buFolder=Configuration.getBUFolder();
        File oldbu = new File(buFolder+filename+".tar");
        oldbu.delete();
        SQL delete = new SQL();
        delete.insertUpdate("delete from `list` where `id`='"+id+"'");
        System.out.println(id+" was deleted.");
    }
}
