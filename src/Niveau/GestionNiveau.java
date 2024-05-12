package Niveau;

import Objet.Objet;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GestionNiveau {
    private static ImageIcon Ciel;
    static private Objet[][] Niveau = new Objet[39][22];
    GestionNiveau()
    {
        try
        {
            BufferedImage sprite1 = ImageIO.read(new File("Ressources/ciel.png"));
            Ciel = new ImageIcon(sprite1);
        }
        catch (IOException e)
        {
            System.out.println(e.getCause());
        }
    }
    static public void CreationNiveau(JFrame fenetre)
    {
        for(int idx = 0;idx <= 21;idx++)
        {
            for(int idx1 = 0;idx1<= 38;idx1++)
            {
                Niveau[idx][idx1] = new Objet(Ciel,0,0,true);
                fenetre.add(Niveau[idx1][idx]);
            }
        }
    }

    static public void AffichageNiveau()
    {
        for(int idx = 0;idx <= 21;idx++)
        {
            for(int idx1 = 0;idx1<= 38;idx1++)
            {
                Niveau[idx1][idx].setLocation(50*idx1,50*idx);
            }
        }
    }
}
