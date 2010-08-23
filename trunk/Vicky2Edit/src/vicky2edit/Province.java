/*
 *    Copyright (C) 2010 Robin Karlsson
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package vicky2edit;

/**
 *
 * @author Robin Karlsson
 */

import java.io.*;
import java.io.File;

public class Province {
    public int provID;
    public String name;
    public String owner;
    public String controller;
    public String trade_goods;
    public int life_rating;
    //public File provFile = null;
    public String provFile = null;

    public Province(int ProvinceID, String basepath) {
        provID=ProvinceID;

        // Get Prov Name from "localisation/text.csv"
        // ex. PROV334;Malmö;Malmö;Malmö;;;;;;;;;;;x
        FileInputStream f = null;
        try{
            f = new FileInputStream(basepath+"localisation/text.csv");
        }catch (IOException ex) {
            //
        }
        DataInputStream in = new DataInputStream(f);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String strLine;
        String [] tmp; //  = new String[];
        try{
            while ((strLine = br.readLine()) != null) {
                tmp = strLine.split(";");
                if(tmp[0].equals("PROV"+provID)){
                    name = tmp[1];
                }
            }
        }catch(IOException ex){
            System.out.println("Error");
        }

        // Find the province file
        // listFiles()
        File dir = null;
        File provDir = new File(basepath+"history/provinces");
        String[] provDirList = provDir.list();
        for(int i=0;i < provDirList.length; i++ ) {
            System.out.println(provDirList[i]);
            dir = new File (basepath+"history/provinces/"+provDirList[i]);
            if(dir.isDirectory()){
                System.out.println("isDirector==true");
                String[] tmplist = dir.list();
                for(int k=0;k < tmplist.length ; k++) {
                    System.out.println(tmplist[k]);
                    String[] t = tmplist[k].split(" ");
                    if(Integer.valueOf(t[0]).intValue() == provID ) {
                        //provFile = new File(basepath+"history/provinces"+provDirList[i]+"/"+tmplist[k]);
                        provFile = (basepath+"history/provinces/"+provDirList[i]+"/"+tmplist[k]);
                        System.out.println("Found: "+basepath+"history/provinces"+provDirList[i]+"/"+tmplist[k]);
                        break;
                    }
                }
                if(provFile != null)
                    break;
            }
             else {
                System.out.println("isDirector==false");
             }

        }
    }

    public String getProvText() {
        String data = "";

        FileInputStream f = null;
        try{
            f = new FileInputStream(provFile);
        }catch (IOException ex) {
            //
        }
        DataInputStream in = new DataInputStream(f);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String strLine;
        String [] tmp; //  = new String[];
        try{
            while ((strLine = br.readLine()) != null) {

                data += strLine+"\n";
                System.out.println(data);
            }
        }catch(IOException ex){
            System.out.println("Error");
        }

        return data;
    }

    public int setProvText(String provData) {
              try{
                FileWriter fstream = new FileWriter(provFile);
                BufferedWriter out = new BufferedWriter(fstream);
                out.write(provData);
                //Close the output stream
                out.close();
                }catch (Exception e){//Catch exception if any
                  System.err.println("Error: " + e.getMessage());
                }
        return 0;
    }
}
