package Affichage;

import Inputs.Inputs;
import Objet.Personnage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Affichage extends JPanel implements Runnable
{
    private Personnage test;
    private static ImageIcon PersoAv,PersoAr;
    private static JFrame fenetre = new JFrame("test");
    Thread AffichageThread;
    private long dernierTemps;
    public Affichage()
    {
        //Ajout perso
        try
        {
            BufferedImage sprite1 = ImageIO.read(new File("Ressources/avant.png"));
            PersoAv = new ImageIcon(sprite1);
        }
        catch (IOException e)
        {
            System.out.println(e.getCause());
        }
        //Fenetre setup
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setSize(800,600);
        fenetre.setLocation(0,0);
        fenetre.setVisible(true);
        //Inputs
        int temp = Inputs.GestionInputs(fenetre);
        test = new Personnage(PersoAv,400,300);
        fenetre.add(test);
        dernierTemps = System.nanoTime();
        AffichageThread = new Thread(this);
        AffichageThread.start();
    }


    @Override
    public void run()
    {

        while (AffichageThread!= null)
        {
            long maintenant = System.nanoTime();
            double deltaTemps = (maintenant - dernierTemps) / 1_000_000.0; // Convertir en mili secondes
            if(deltaTemps >= 16.6d)
            {
                test.Update();
                repaint();
                dernierTemps = maintenant;
            }
        }
    }
}
