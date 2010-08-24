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
 * @author a
 */

import java.util.ArrayList;
import java.io.*;
import java.io.File;
import java.util.regex.Pattern;

public class Countries {
    public ArrayList<Country> countriesList; // TAG:NAME:RED:GREEN:BLUE
    public Countries(String basepath) {
        String counriesFile = basepath+"common/countries.txt";
        countriesList = new ArrayList<Country>();

        FileInputStream f = null;
        try{
            f = new FileInputStream(counriesFile);
        }catch (IOException ex) {
            //
        }
        DataInputStream in = new DataInputStream(f);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String strLine;
        String [] tmp; //  = new String[];
        String cTmp;
        String cFile;
        try{
            while ((strLine = br.readLine()) != null) {
                try{
                    tmp=strLine.split("=");
                    if(tmp[0].length() < 6){
                        cTmp=tmp[0].substring(0, 3);
                        System.out.println("tag="+cTmp);
                        cFile=tmp[1].split("\"")[1];
                        System.out.println("file="+cFile);

                        /* Open the country file and get the RGB */
                        FileInputStream cf = null;
                        try{
                            cf = new FileInputStream(basepath+"common/"+cFile);
                        }catch (IOException ex) {
                            //
                        }
                        DataInputStream cin = new DataInputStream(cf);
                        BufferedReader cbr = new BufferedReader(new InputStreamReader(cin));
                        String strLine2;
                        Boolean loop = true;
                        while ((strLine2 = cbr.readLine()) != null && loop) {
                            //
                            //try{
                                /*System.out.print(strLine2+" ");
                                Boolean b = Pattern.matches("color", strLine2);
                                System.out.println(b);*/
                                //String i = strLine2.substring(1, 6);
                                int i=strLine2.indexOf("color");
                                if(i != -1){
                                //if(strLine2.split(" ")[0].equals("color")) {
                                    //
                                    System.out.println("true");
                                    String[] l = strLine2.split("[{]");
                                    String[] k = l[1].trim().split("\\s+");
                                    System.out.println(k[0]+":"+k[1]+":"+k[2]+":");
                                    loop = false;
                                    Country c = new Country(cTmp,"a",Integer.valueOf(k[0]).intValue(),
                                            Integer.valueOf(k[1]).intValue(),Integer.valueOf(k[2]).intValue());
                                    countriesList.add(c);
                                }else{
                                    System.out.println("false: "+strLine2);
                                }

                            //}catch(Exception  excp){
                                //
                            //}
                        }
                    }

                }catch(Exception  excp) {
                    //
                }
            }
        }catch(IOException ex){
            System.out.println("Error");
        }
    }

    /* Find country by looking for the TAG and return RGB */
    int getRGB(String tag){
        int rgb = -1;
        for(int i=0;i<countriesList.size();i++){
            if(countriesList.get(i).tag.equals(tag)) {
                rgb = (countriesList.get(i).red << 16) | (countriesList.get(i).green << 8) |
                        countriesList.get(i).blue;
                break;
            }
        }
        return rgb;
    }


}
