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
    private int NbrDash = 0;
    etatPerso etat = etatPerso.InTheAir;
    colision colisionX = colision.AUCUN;
    colision colisionY = colision.AUCUN;

    public Personnage(ImageIcon image, int posX, int posY)
    {
        super(image, posX, posY,false);
        this.vitesseX = 0d;
        this.vitesseY = 0d;
        setSize(50,50);
    }

    public void Update()
    {
        Dash();
        Saut();
        if(this.vitesseY >= 0 && this.vitesseX != 0 && this.colisionY == colision.BAS && this.etat == etatPerso.Dashing)
        {
            this.etat = etatPerso.Waving;
        }
        GestionX();
        GestionY();
        colisionX();
        colisionY();
    }

    private void GestionX()
    {
        switch (this.etat)
        {
            case OnWall,OnGround : this.vitesseX = VitesseInputX();
            break;
            case Waving : FreinX(6);
                if(this.vitesseX > 0d)
                {
                    if(this.vitesseX == 10d)
                    {
                        this.etat = etatPerso.InTheAir;
                    }
                }
                else
                {
                    if(this.vitesseX == -10d)
                    {
                        this.etat = etatPerso.InTheAir;
                    }
                }
            break;
            case WaveDashing :
                double Vitesse = VitesseInputX();
                if(Vitesse > 0 && vitesseX < 0)
                {
                    this.vitesseX = Vitesse;
                    this.etat = etatPerso.InTheAir;
                }
                if(Vitesse < 0 && this.vitesseX > 0)
                {
                    this.vitesseX = Vitesse;
                    this.etat = etatPerso.InTheAir;
                }
            break;
            case Dashing: FreinX(5);
            if(this.vitesseY >= 0d)
            {
                if(this.vitesseX > 0d)
                {
                    if(this.vitesseX == 10d)
                    {
                        this.etat = etatPerso.InTheAir;
                    }
                }
                else
                {
                    if(vitesseX == -10d)
                    {
                        this.etat = etatPerso.InTheAir;
                    }
                }
            }
            break;
            case InTheAir: vitesseX = VitesseInputX();
            break;
            case WallJumping:
                if(this.vitesseX > 0d)
                {
                    this.vitesseX -= 0.5d;
                } else if (this.vitesseX < 0d)
                {
                    this.vitesseX += 0.5d;
                }
                break;
        }
    }

    private void FreinX(int frein)
    {
        if(this.vitesseX >= 0d)
        {
            if(this.vitesseX >= 10d)
            {
                this.vitesseX -= frein;
                if(this.vitesseX < 10d)
                {
                    this.vitesseX = 10d;
                }
            }
        }
        else
        {
            if(this.vitesseX <= -10d)
            {
                this.vitesseX += frein;
                if(this.vitesseX > -10d)
                {
                    this.vitesseX = -10d;
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
        switch (this.etat)
        {
            case OnWall : this.vitesseY = 5d;
                break;
            case Waving,OnGround : this.vitesseY = 0d;
                break;
            case InTheAir,WaveDashing :
                this.vitesseY += 2d;
                if(this.vitesseY >= 50d)
                {
                    this.vitesseY = 50d;
                }
                break;
            case Dashing:
            if(this.vitesseY >= 0d)
            {
                if(this.vitesseX == 0)
                {
                    this.etat = etatPerso.InTheAir;
                }
            }
            else
            {
                this.vitesseY += 2d;
            }
            break;
            case WallJumping:
                this.vitesseY += 2d;
                if(this.vitesseY >= 0d)
                {
                    this.etat = etatPerso.InTheAir;
                }
        }
    }
    private void colisionX()
    {
        int tempX = getX()+(int) this.vitesseX;
        if(tempX <= 0)
        {
            this.colisionX = colision.GAUCHE;
            setLocation(0,getY());
            if(this.vitesseY > 0)
            {
                this.etat = etatPerso.OnWall;
            }
        } else if (tempX >= 1000)
        {
            this.colisionX = colision.DROITE;
            setLocation(1000,getY());
            if(this.vitesseY > 0)
            {
                this.etat = etatPerso.OnWall;
            }
        }
        else
        {
            this.colisionX = colision.AUCUN;
            setLocation(tempX,getY());
            if(this.etat != etatPerso.Dashing)
            {
                if(this.etat != etatPerso.Waving && this.etat != etatPerso.WaveDashing && this.etat != etatPerso.WallJumping)
                {
                    this.etat = etatPerso.InTheAir;
                }

            }
        }
    }

    private void colisionY()
    {
        int tempY = getY()+(int) this.vitesseY;
        if(tempY >= 400)
        {
            if(!(this.vitesseY == 0 && this.etat == etatPerso.Dashing))
            {
                this.colisionY = colision.BAS;
            }
            setLocation(getX(),400);
            if(this.etat != etatPerso.Dashing && this.etat != etatPerso.Waving)
            {
                this.etat = etatPerso.OnGround;
                this.NbrDash = 1;

            }
        }
        else
        {
            setLocation(getX(),tempY);
            this.colisionY = colision.AUCUN;
        }
    }
    private void Saut()
    {
        if(!this.FlagSpace && Inputs.KeySpace && (this.etat == etatPerso.OnGround || this.etat == etatPerso.OnWall || this.etat == etatPerso.Waving))
        {
            FlagSpace = true;
            this.vitesseY = -30d;
            if(this.etat == etatPerso.Waving)
            {
                this.etat = etatPerso.WaveDashing;
            }
            if(this.etat == etatPerso.OnWall)
            {
                this.etat = etatPerso.WallJumping;
                if(this.colisionX == colision.GAUCHE)
                {
                    this.vitesseX = 15d;
                }
                else
                {
                    this.vitesseX = -15d;
                }
            }
            else
            {
                if(this.etat != etatPerso.WaveDashing)
                {
                    this.etat = etatPerso.InTheAir;
                }
            }
        }
        if(!Inputs.KeySpace)
        {
            if(this.vitesseY < 0 && etat == etatPerso.InTheAir)
            {
                this.vitesseY = 0;
            }
            this.FlagSpace = false;
        }
        else
        {
            this.FlagSpace = true;
        }
    }

    private void Dash()
    {
        if(!this.FlagI && Inputs.KeyI && this.NbrDash > 0)
        {
            this.NbrDash --;
            this.colisionY = colision.AUCUN;
            this.FlagI = true;
            this.etat = etatPerso.Dashing;
            switch (Inputs.Direction)
            {
                case 1:
                    this.vitesseY = - 35;
                    this.vitesseX = 0;
                    break;
                case 2:
                    this.vitesseX = 35;
                    this.vitesseY = - 35;
                    break;
                case 3:
                    this.vitesseX = 50;
                    this.vitesseY = 0;
                    break;
                case 4:
                    this.vitesseX = 40;
                    this.vitesseY = 40;
                    break;
                case 5:
                    this.vitesseX = 0;
                    this.vitesseY = 40;
                    break;
                case 6:
                    this.vitesseX = - 40;
                    this.vitesseY =  40;
                    break;
                case 7:
                    this.vitesseX =  - 50;
                    this.vitesseY = 0;
                    break;
                case 8:
                    this.vitesseX = - 35;
                    this.vitesseY = - 35;
                    break;
            }

        }
        if(!Inputs.KeyI)
        {
            this.FlagI = false;
        }
    }

    public double getVitesseX() {
        return this.vitesseX;
    }
}