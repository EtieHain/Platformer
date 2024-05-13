package Affichage;

import Inputs.Inputs;
import Objet.Objet;
import Objet.Personnage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import com.fasterxml.jackson.databind.*;

public class Affichage extends JPanel implements Runnable
{
    public int[][] Niveau = new int[22][39];
    private Personnage perso;
    private static ImageIcon PersoAv,PersoAr,Ciel,Sol;
    private JFrame fenetre = new JFrame("test");
    Thread AffichageThread;
    private long dernierTemps;
    public static Objet[][] NiveauObj = new Objet[22][39];
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
        try
        {
            BufferedImage sprite1 = ImageIO.read(new File("Ressources/sol.png"));
            Sol = new ImageIcon(sprite1);
        }
        catch (IOException e)
        {
            System.out.println(e.getCause());
        }
        //Fenetre setup
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setSize(1920,1000);
        fenetre.setLocation(0,0);
        //Inputs
        int temp = Inputs.GestionInputs(fenetre);
        perso = new Personnage(PersoAv,1000,400);
        fenetre.add(perso);
        dernierTemps = System.nanoTime();
        AffichageThread = new Thread(this);
        fenetre.setVisible(true);
        perso.setLocation(1000,400);
        AffichageThread.start();
    }


    @Override
    public void run()
    {
        CreationNiveau();
        perso.setLocation(50,700);
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
                perso.setSize(50,50);
            }
        }
    }

    void CreationNiveau()
    {
        ObjectMapper mapper = new ObjectMapper();
        // Lecture du fichier JSON et conversion en tableau d'entiers bidimensionnel
        try
        {
            Niveau = mapper.readValue(new File("test.json"), int[][].class);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        for(int idx = 0;idx <= 21;idx++)
        {
            for(int idx1 = 0;idx1<= 38;idx1++)
            {

                if(Niveau[idx][idx1] == 1)
                {
                    NiveauObj[idx][idx1] = new Objet(Sol,0,0,true);
                }
                else
                {
                    NiveauObj[idx][idx1] = new Objet(Ciel,0,0,false);
                }
                fenetre.add(NiveauObj[idx][idx1]);
            }
        }
    }

    void AffichageNiveau()
    {
        for(int idx = 0;idx <= 21;idx++)
        {
            for(int idx1 = 0;idx1<= 38;idx1++)
            {
                NiveauObj[idx][idx1].setLocation(50*idx1,50*idx);
            }
        }
    }
}
