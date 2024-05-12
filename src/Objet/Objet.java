package Objet;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Objet extends JLabel
{

    public Objet(ImageIcon image,int posX,int posY,boolean Colision) {
        super.setVisible(true);
        super.setIcon(image);
        super.setLocation(posX, posY);
        super.setSize(50,50);
    }
}
