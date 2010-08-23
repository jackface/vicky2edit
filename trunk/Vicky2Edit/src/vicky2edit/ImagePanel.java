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
//import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;

public class ImagePanel extends JPanel{

    private BufferedImage image;
    private int zoom=1;

    public ImagePanel(String basepath) {
       try {
          image = ImageIO.read(new File(basepath+"map/provinces.bmp"));

       } catch (IOException ex) {
            // handle exception...
       }
        //int w = image.getWidth();
        //int h = image.getHeight();
        //BufferedImage dimg = dimg = new BufferedImage(w, h, image.getColorModel().getTransparency());
        //Graphics2D g = dimg.createGraphics();
        //g.dispose();
    }

    @Override
    public void paintComponent(Graphics g) {
        //g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters
        g.drawImage(image, 0, 0, image.getWidth()*zoom, image.getHeight()*zoom, 0, image.getHeight(), image.getWidth(), 0, null);

    }
    public void setZoom(int i){
        zoom=i;
    }

}