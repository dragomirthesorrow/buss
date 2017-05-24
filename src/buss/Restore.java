/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buss;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author oadm
 */
public class Restore {
    private static int id_bu;
    public Restore(int id){
        this.id_bu=id;
    }
    static void restore() {
        /*
        Удаляем папки, распаковываем архив, восстанавливаем дамп, удаляем временный скуль.
        */
        Configuration cf = new Configuration();
        String root_dir = cf.getRootDir();
        File rd = new File (root_dir);
        deleteDirectory(rd);
        File n_root_dir = new File("root_dir");
        n_root_dir.mkdir();
        //получаем имя необходимого архива.
        //Запрос даты из базы
        String values[] = new String[2];
        values[0]="id";
        values[1]="date_created";
        SQL request = new SQL();
        String date_at_base=request.select("select * from `list` where `id`='"+id_bu+"'", values).get(0)[1];
        //System.out.println(date_at_base);
        //Преобразуем дату в необходимый формат
        String filename ="";
        
        try{
            SimpleDateFormat sql_form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //sql_form.applyPattern("YYYY-MM-dd HH:mm:ss");
            Date date = sql_form.parse(date_at_base);
            SimpleDateFormat name_form=new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            filename=name_form.format(date.getTime());
        }catch(ParseException e){
            
        }
        String file_name_form_date=filename;
        filename = cf.getBUFolder()+filename+".tar";
        System.out.println(filename);
        
        //File archive = new File(Configuration.getBUFolder()+filename+".tar");
        
        //распаковываем архив
        ProcessBuilder proc = new ProcessBuilder("bash","-c","tar -xf "+filename+" -C "+root_dir+"");
        try{
            Process processtar = proc.start();
            InputStream stdout = processtar.getInputStream();
           InputStreamReader isrStdout = new InputStreamReader(stdout);
           BufferedReader brStdout = new BufferedReader(isrStdout);
           String line = null;
             while((line = brStdout.readLine()) != null) {
                 System.out.println(line);
             }
            int exitVal = processtar.waitFor();
        }catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
        File src = new File(cf.getRootDir()+"etc/buss/arch");
        File dst = new File(cf.getRootDir());
        moveDirectory(src,dst);
        File temp = new File(cf.getRootDir()+"etc");
        deleteDirectory(temp);
        //восстанавливаем дамп скуль
        ProcessBuilder procdump = new ProcessBuilder("bash","-c","mysqldump -u"+cf.mysqlAcc()[0]+" -hlocalhost "+cf.getDBName()+" --password="+cf.mysqlAcc()[1]+" < "+cf.getRootDir()+file_name_form_date+".sql");
        try{
            Process processtar = procdump.start();
            InputStream stdout = processtar.getInputStream();
           InputStreamReader isrStdout = new InputStreamReader(stdout);
           BufferedReader brStdout = new BufferedReader(isrStdout);
           String line = null;
             while((line = brStdout.readLine()) != null) {
                 System.out.println(line);
             }
            int exitVal = processtar.waitFor();
        }catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
        //удаляем скуль-файл
        File oldsql = new File(cf.getRootDir()+file_name_form_date+".sql");
        oldsql.delete();
        System.out.println("Backup "+filename+" was restored.");
        
    }
    static void moveDirectory(File src, File dst){
        //Configuration cf = new Configuration();
        //String root = cf.getRootDir();
        //String app = "/etc/buss/arch";
        if(src.isDirectory()){
        String files[] = src.list();
        //File folder = new File();
        dst.mkdir();
            for(String file : files){
                File fl = new File(src.getAbsolutePath()+"/"+file);
                File nf = new File(dst.getAbsolutePath()+"/"+file);
                moveDirectory(fl,nf);
            }
        }else{
            try{
            Files.copy(src.toPath(), dst.toPath());
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    } 
    public static void deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                File f = new File(dir, children[i]);
                deleteDirectory(f);
            }
            dir.delete();
        } else dir.delete();
    }
    public static void deleteDirectoryContent(File dir) {
        String[] first = dir.list();
        for(String frst : first){
            System.out.println(dir+frst);
            deleteDirectory(new File(dir+frst));
        }
        }
}
