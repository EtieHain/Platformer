package Objet;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Objet extends JLabel
{
    public Boolean colision;
    public int positionX,positionY;
    public Objet(ImageIcon image,int posX,int posY,boolean Colision) {
        super.setVisible(true);
        super.setIcon(image);
        super.setLocation(posX, posY);
        positionX = posX;
        positionY = posY;
        super.setSize(50,50);
        this.colision = Colision;
    }
}
