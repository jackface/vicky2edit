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

import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
        
/**
 *
 * @author Robin Karlsson
 */

public class Map {
    public String definitionCSV; // Contains province RGB and id
    public String provincesPath; // Path to the map bitmap
    File provincesFile;
    BufferedImage provincesImage;
    public int height;
    public int width;

    public Map(String basepath) {
        definitionCSV = basepath+"map/definition.csv";
        provincesPath = basepath+"map/provinces.bmp";
        provincesFile = new File(provincesPath);
        try {
            provincesImage = ImageIO.read(provincesFile);
        }catch (IOException ex) {
            // handle exception...
        }
        height = provincesImage.getHeight();
        width  = provincesImage.getWidth();
    }

    public int getProvID(int x, int y) {
        int provID = 0;

        int xpos = x; //(x+width)-width;
        int ypos = height-y;
        
        int  clr   = provincesImage.getRGB(xpos,ypos);
        int  red   = (clr & 0x00ff0000) >> 16;
        int  green = (clr & 0x0000ff00) >> 8;
        int  blue  = clr & 0x000000ff;
        int r,g,b;

        FileInputStream f = null;
        try{
            f = new FileInputStream(definitionCSV);
        }catch (IOException ex) {
            return -1;
        }

        DataInputStream in = new DataInputStream(f);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        String [] tmp = new String[10];

        /* DEBUG STUFF */
        System.out.println(red+":"+green+":"+blue);
        try{
            while ((strLine = br.readLine()) != null)   {
                // province;red;green;blue;x;x
                // \s;red;gren;blue\s
                tmp = strLine.split(";");
                
                /*if( Integer.valueOf(tmp[1]).intValue() == red &&
                    Integer.valueOf(tmp[2]).intValue() == green &&
                    Integer.valueOf(tmp[3]).intValue() == blue ) {
                    provID=Integer.valueOf(tmp[0]).intValue();
                    break;
                }*/
                try {
                    r=Integer.valueOf(tmp[1]).intValue();
                    g=Integer.valueOf(tmp[2]).intValue();
                    b=Integer.valueOf(tmp[3]).intValue();
                    if( r == red && g == green && b == blue ) {
                        provID=Integer.valueOf(tmp[0]).intValue();
                        System.out.println("provID="+provID);
                        System.out.println("name="+tmp[4]);
                        System.out.println("X="+xpos+"Y="+ypos);
                        break;
                    }
                }catch (NumberFormatException n) {
                    System.out.println(tmp[1]+" : is not a integer");
                }
            }
        }catch(IOException ex){
            System.out.println("Error");
            //return -2;
        }
        
        return provID;
    }

}
