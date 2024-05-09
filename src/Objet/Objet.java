package Objet;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Objet extends JLabel
{

    public Objet(ImageIcon image,int posX,int posY,boolean Colision) {
        this.setIcon(image);
        this.setLocation(posX, posY);
        this.setVisible(true);
    }
}
