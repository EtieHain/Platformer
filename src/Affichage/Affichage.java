package Affichage;

import Inputs.Inputs;
import Objet.Objet;
import Objet.Personnage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Affichage extends JPanel implements Runnable
{
    private Personnage perso;
    private static ImageIcon PersoAv,PersoAr,Ciel;
    private JFrame fenetre = new JFrame("test");
    Thread AffichageThread;
    private long dernierTemps;
    private Objet[][] Niveau = new Objet[39][22];
    private static int temp = 0;
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
        //Ajout perso
        try
        {
            BufferedImage sprite1 = ImageIO.read(new File("Ressources/arriere.png"));
            PersoAr = new ImageIcon(sprite1);
        }
        catch (IOException e)
        {
            System.out.println(e.getCause());
        }
        try
        {
            BufferedImage sprite1 = ImageIO.read(new File("Ressources/ciel.png"));
            Ciel = new ImageIcon(sprite1);
        }
        catch (IOException e)
        {
            System.out.println(e.getCause());
        }
        //Fenetre setup
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setSize(1920,1080);
        fenetre.setLocation(0,0);
        //Inputs
        int temp = Inputs.GestionInputs(fenetre);
        perso = new Personnage(PersoAv,1000,400);
        fenetre.add(perso);
        dernierTemps = System.nanoTime();
        AffichageThread = new Thread(this);
        fenetre.setVisible(true);
        AffichageThread.start();
    }


    @Override
    public void run()
    {
        CreationNiveau();
        while (AffichageThread!= null)
        {
            long maintenant = System.nanoTime();
            double deltaTemps = (maintenant - dernierTemps) / 1_000_000.0; // Convertir en mili secondes
            if(deltaTemps >= 16.6d)
            {
                perso.Update();
                AffichageNiveau();
                fenetre.repaint();
                if(perso.getVitesseX() > 0)
                {
                    perso.setIcon(PersoAv);
                } else if (perso.getVitesseX() < 0)
                {
                    perso.setIcon(PersoAr);
                }
                dernierTemps = maintenant;
                Component[] components = fenetre.getContentPane().getComponents();
                int a = 0;
            }
        }
    }

    void CreationNiveau()
    {
        for(int idx = 0;idx <= 38;idx++)
        {
            for(int idx1 = 0;idx1<= 21;idx1++)
            {
                Niveau[idx][idx1] = new Objet(Ciel,0,0,true);
                fenetre.add(Niveau[idx][idx1]);
            }
        }
    }

    void AffichageNiveau()
    {
        temp++;
        for(int idx = 0;idx <= 38;idx++)
        {
            for(int idx1 = 0;idx1<= 21;idx1++)
            {
                Niveau[idx][idx1].setLocation(50*idx,50*idx1);
            }
        }
    }
}
