package Objet;

import Inputs.Inputs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Personnage extends Objet
{
    private long dernierTemps;
    private boolean FlagI,FlagSpace;
    private double vitesseX,vitesseY;
    public enum colision {AUCUN, HAUT, BAS, GAUCHE, DROITE}
    public enum etatPerso {Dashing, WaveDashing, InTheAir, OnWall, Waving, WallJumping, OnGround}
    etatPerso etat = etatPerso.InTheAir;
    colision colisionX = colision.AUCUN;
    colision colisionY = colision.AUCUN;

    public Personnage(ImageIcon image, int posX, int posY)
    {
        super(image, posX, posY,false);
        this.vitesseX = 5d;
        this.vitesseY = 0d;
    }

    public void Update()
    {
        Saut();
        GestionX();
        GestionY();
        colisionX();
        colisionY();
    }

    private void GestionX()
    {
        switch (etat)
        {
            case OnWall,OnGround : vitesseX = VitesseInputX();
            break;
            case Waving : FreinX(10);
            break;
            case WaveDashing :
                double Vitesse = VitesseInputX();
                if(Vitesse > 0 && vitesseX < 0)
                {
                    vitesseX = Vitesse;
                    etat = etatPerso.InTheAir;
                }
                if(Vitesse < 0 && vitesseX > 0)
                {
                    vitesseX = Vitesse;
                    etat = etatPerso.InTheAir;
                }
            break;
            case Dashing: FreinX(5);
            break;
            case InTheAir: vitesseX = VitesseInputX();
            break;
            case WallJumping:
                break;
        }
    }

    private void FreinX(int frein)
    {
        if(vitesseX >= 0d)
        {
            if(vitesseX >= 10d)
            {
                vitesseX -= frein;
                if(vitesseX < 10d)
                {
                    vitesseX = 10d;
                }
            }
        }
        else
        {
            if(vitesseX <= -10d)
            {
                vitesseX += frein;
                if(vitesseX > -10d)
                {
                    vitesseX = -10d;
                }
            }
        }
    }

    private double VitesseInputX()
    {
        int Direction = Inputs.Direction;
        if(2 <= Direction && Direction <= 4)
        {
            return 10d;
        }
        else if(6 <= Direction)
        {
            return -10d;
        } else
        {
            return 0d;
        }
    }

    private void GestionY()
    {
        switch (etat)
        {
            case OnWall : vitesseY = 5;
                break;
            case Waving,OnGround : vitesseY = 0;
                break;
            case InTheAir,WaveDashing,Dashing,WallJumping :
                vitesseY += 2;
                if(vitesseY >= 50)
                {
                    vitesseY = 50;
                }
                break;
        }
    }
    private void colisionX()
    {
        int tempX = getX()+(int) vitesseX;
        if(tempX <= 0)
        {
            colisionX = colision.GAUCHE;
            setLocation(0,getY());
            etat = etatPerso.OnWall;
        } else if (tempX >= 1000)
        {
            colisionX = colision.DROITE;
            setLocation(1000,getY());
            etat = etatPerso.OnWall;
        }
        else
        {
            colisionX = colision.AUCUN;
            setLocation(tempX,getY());
            etat = etatPerso.InTheAir;
        }
    }

    private void colisionY()
    {
        int tempY = getY()+(int) vitesseY;
        if(tempY >= 400)
        {
            colisionY = colision.BAS;
            setLocation(getX(),400);
            etat = etatPerso.OnGround;
        }
        else
        {
            setLocation(getX(),tempY);
            colisionX = colision.AUCUN;
        }
    }
    private void Saut()
    {
        if(!FlagSpace && Inputs.KeySpace && (etat == etatPerso.OnGround || etat == etatPerso.OnWall))
        {
            FlagSpace = true;
            vitesseY = -30d;
            etat = etatPerso.InTheAir;
        }
        if(!Inputs.KeySpace)
        {
            if(vitesseY < 0)
            {
                vitesseY = 0;
            }
            FlagSpace = false;
        }
    }
}