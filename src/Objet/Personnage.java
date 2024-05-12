package Objet;

import Affichage.Affichage;
import Inputs.Inputs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Personnage extends Objet
{
    private long dernierTemps;
    private boolean FlagI,FlagSpace;
    private double vitesseX,vitesseY;
    public enum etatColision {AUCUN, HAUT, BAS, GAUCHE, DROITE}
    public enum etatPerso {Dashing, WaveDashing, InTheAir, OnWall, Waving, WallJumping, OnGround}
    private int NbrDash = 0;
    etatPerso etat = etatPerso.InTheAir;
    etatColision colisionX = etatColision.AUCUN;
    etatColision colisionY = etatColision.AUCUN;

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
        if(this.vitesseY > 0 && this.vitesseX != 0 && this.colisionY == etatColision.BAS && this.etat == etatPerso.Dashing)
        {
            this.etat = etatPerso.Waving;
        }
        GestionX();
        GestionY();
        ColisionX();
        ColisionY();
        System.out.println(etat);
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
    private void ColisionX()
    {
        int tempX = getX()+(int) this.vitesseX;
        int tempY = getY();
        int posX = getX();
        int posY = getY();
        int posTabX = posX / 50;
        int posTabY = posY / 50;
        //Test Colision axe X

        //Test colision gauche
        if(vitesseX < 0)
        {
            if(posTabX != 0)
            {
                if(tempY % 50 == 0) // si le perso est parfaitement alligné à une ligne
                {
                    if(Affichage.NiveauObj[posTabY][posTabX-1].colision && tempX <= posTabX * 50) //si il y a une colision détectée
                    {
                        posX = posTabX * 50;
                        this.colisionX = etatColision.GAUCHE;
                        if(this.vitesseY > 0)
                        {
                            this.etat = etatPerso.OnWall;
                        }
                    }
                    else // Si aucune colision est trouvée
                    {
                        posX = tempX;
                        this.colisionX = etatColision.AUCUN;
                        if(this.etat != etatPerso.Dashing && this.etat != etatPerso.Waving && this.etat != etatPerso.WaveDashing && this.etat != etatPerso.WallJumping)
                        {
                            this.etat = etatPerso.InTheAir;
                        }
                    }
                }
                else//Si le perso est sur deux lignes
                {
                    if((Affichage.NiveauObj[posTabY][posTabX-1].colision || Affichage.NiveauObj[posTabY + 1][posTabX-1].colision) && tempX <= posTabX * 50) //si il y a une colision détectée
                    {
                        posX = posTabX * 50;
                        this.colisionX = etatColision.GAUCHE;
                        if(this.vitesseY > 0)
                        {
                            this.etat = etatPerso.OnWall;
                        }
                    }
                    else // Si aucune colision est trouvée
                    {
                        posX = tempX;
                        this.colisionX = etatColision.AUCUN;
                        if(this.etat != etatPerso.Dashing && this.etat != etatPerso.Waving && this.etat != etatPerso.WaveDashing && this.etat != etatPerso.WallJumping)
                        {
                            this.etat = etatPerso.InTheAir;
                        }
                    }
                }
            }
        } else if (vitesseX > 0) // test colision droite
        {
            if(tempY % 50 == 0) // si le perso est parfaitement alligné à une ligne
            {
                if(posX % 50 == 0) //Si le perso est pafaitement aligné a une colone
                {
                    if(Affichage.NiveauObj[posTabY][posTabX+1].colision && tempX >= posTabX * 50) //si il y a une colision détectée
                    {
                        posX = posTabX * 50;
                        this.colisionX = etatColision.DROITE;
                        if(this.vitesseY > 0)
                        {
                            this.etat = etatPerso.OnWall;
                        }
                    }
                    else // Si aucune colision est trouvée
                    {
                        posX = tempX;
                        this.colisionX = etatColision.AUCUN;
                        if(this.etat != etatPerso.Dashing && this.etat != etatPerso.Waving && this.etat != etatPerso.WaveDashing && this.etat != etatPerso.WallJumping)
                        {
                            this.etat = etatPerso.InTheAir;
                        }
                    }
                }
                else
                {
                    if(Affichage.NiveauObj[posTabY][posTabX+2].colision && tempX >= (posTabX +1)* 50) //si il y a une colision détectée
                    {
                        posX = (posTabX+1) * 50;
                        this.colisionX = etatColision.DROITE;
                        if(this.vitesseY > 0)
                        {
                            this.etat = etatPerso.OnWall;
                        }
                    }
                    else // Si aucune colision est trouvée
                    {
                        posX = tempX;
                        this.colisionX = etatColision.AUCUN;
                        if(this.etat != etatPerso.Dashing && this.etat != etatPerso.Waving && this.etat != etatPerso.WaveDashing && this.etat != etatPerso.WallJumping)
                        {
                            this.etat = etatPerso.InTheAir;
                        }
                    }
                }
            }
            else//Si le perso est sur deux lignes
            {
                if(posX % 50 == 0)
                {
                    if((Affichage.NiveauObj[posTabY][posTabX+1].colision || Affichage.NiveauObj[posTabY + 1][posTabX+1].colision)&& tempX >= posTabX* 50) //si il y a une colision détectée
                    {
                        posX = posTabX * 50;
                        this.colisionX = etatColision.DROITE;
                        if(this.vitesseY > 0)
                        {
                            this.etat = etatPerso.OnWall;
                        }
                    }
                    else // Si aucune colision est trouvée
                    {
                        posX = tempX;
                        this.colisionX = etatColision.AUCUN;
                        if(this.etat != etatPerso.Dashing && this.etat != etatPerso.Waving && this.etat != etatPerso.WaveDashing && this.etat != etatPerso.WallJumping)
                        {
                            this.etat = etatPerso.InTheAir;
                        }
                    }
                }
                else
                {
                    if((Affichage.NiveauObj[posTabY][posTabX+2].colision || Affichage.NiveauObj[posTabY + 1][posTabX+2].colision)&& tempX >= (posTabX + 1)* 50) //si il y a une colision détectée
                    {
                        posX = (posTabX +1)* 50;
                        this.colisionX = etatColision.DROITE;
                        if(this.vitesseY > 0)
                        {
                            this.etat = etatPerso.OnWall;
                        }
                    }
                    else // Si aucune colision est trouvée
                    {
                        posX = tempX;
                        this.colisionX = etatColision.AUCUN;
                        if(this.etat != etatPerso.Dashing && this.etat != etatPerso.Waving && this.etat != etatPerso.WaveDashing && this.etat != etatPerso.WallJumping)
                        {
                            this.etat = etatPerso.InTheAir;
                        }
                    }
                }
            }
        }
        if(posTabX != 0  && etat == etatPerso.OnWall)
            if (!(Affichage.NiveauObj[posTabY][posTabX + 1].colision && tempX + 1 >= posTabX * 50) && !(Affichage.NiveauObj[posTabY][posTabX - 1].colision && tempX - 1 <= posTabX * 50)) // Test pour sortir de l'état sur le mur
            {
                colisionX = etatColision.AUCUN;
                etat = etatPerso.InTheAir;
            }
        this.setLocation(posX,posY);
    }

    private void ColisionY()
    {
        int tempY = getY()+(int) this.vitesseY;
        int posX = getX();
        int posY = getY();
        int posTabX = posX / 50;
        int posTabY = posY / 50;
        if(vitesseY > 0) // Test collision bas
        {
            if(posTabX != 0) //att
            {
                if(posX % 50 == 0) // si le perso est parfaitement alligné à une colonne
                {
                    if(posY % 50 == 0)
                    {
                        if(Affichage.NiveauObj[posTabY+1][posTabX].colision && tempY >= posTabY * 50) //si il y a une colision détectée
                        {
                            posY = posTabY * 50;
                            this.colisionY = etatColision.BAS;
                            if(etat != etatPerso.Dashing && etat != etatPerso.Waving)
                            {
                                etat = etatPerso.OnGround;
                            }
                            NbrDash = 1;
                        }
                        else // Si aucune colision est trouvée
                        {
                            posY = tempY;
                            this.colisionY = etatColision.AUCUN;
                        }
                    }
                    else
                    {
                        if(Affichage.NiveauObj[posTabY+2][posTabX].colision && tempY >= (posTabY +1)* 50) //si il y a une colision détectée
                        {
                            posY = (posTabY + 1)* 50;
                            this.colisionY = etatColision.BAS;
                            if(etat != etatPerso.Dashing && etat != etatPerso.Waving)
                            {
                                etat = etatPerso.OnGround;
                            }
                            NbrDash = 1;
                        }
                        else // Si aucune colision est trouvée
                        {
                            posY = tempY;
                            this.colisionY = etatColision.AUCUN;
                        }
                    }
                }
                else//Si le perso est sur deux lignes
                {
                    if(posY % 50 == 0)
                    {
                        if((Affichage.NiveauObj[posTabY + 1][posTabX].colision || Affichage.NiveauObj[posTabY + 1][posTabX + 1].colision) && tempY >= posTabY * 50) //si il y a une colision détectée
                        {
                            posY = posTabY * 50;
                            this.colisionY = etatColision.BAS;
                            if(etat != etatPerso.Dashing && etat != etatPerso.Waving)
                            {
                                etat = etatPerso.OnGround;
                            }
                            NbrDash = 1;
                        }
                        else // Si aucune colision est trouvée
                        {
                            posY = tempY;
                            this.colisionY = etatColision.AUCUN;
                        }
                    }
                    else
                    {
                        if((Affichage.NiveauObj[posTabY + 2][posTabX].colision || Affichage.NiveauObj[posTabY + 2][posTabX + 1].colision) && tempY >= (posTabY + 1) * 50) //si il y a une colision détectée
                        {
                            posY = (posTabY + 1) * 50;
                            this.colisionY = etatColision.BAS;
                            if(etat != etatPerso.Dashing && etat != etatPerso.Waving)
                            {
                                etat = etatPerso.OnGround;
                            }
                            NbrDash = 1;
                        }
                        else // Si aucune colision est trouvée
                        {
                            posY = tempY;
                            this.colisionY = etatColision.AUCUN;
                        }
                    }
                }
            }
        } else if (vitesseY < 0)
        {
            posY = tempY;
        }
        else
        {
            if(posX % 50 == 0) // si le perso est parfaitement alligné à une colonne
            {
                if(Affichage.NiveauObj[posTabY+1][posTabX].colision && posY + 1 > posTabY * 50) //si il y a une colision détectée
                {
                    this.colisionY = etatColision.BAS;
                    if(etat != etatPerso.Dashing && etat != etatPerso.Waving)
                    {
                        etat = etatPerso.OnGround;
                    }
                    NbrDash = 1;
                }
            }
            else//Si le perso est sur deux lignes
            {
                if((Affichage.NiveauObj[posTabY + 1][posTabX].colision || Affichage.NiveauObj[posTabY + 1][posTabX + 1].colision && posY + 1 >= posTabY * 50)) //si il y a une colision détectée
                {
                    this.colisionY = etatColision.BAS;
                    if(etat != etatPerso.Dashing && etat != etatPerso.Waving)
                    {
                        etat = etatPerso.OnGround;
                    }
                    NbrDash = 1;
                }
            }
        }
        setLocation(posX,posY);
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
                if(this.colisionX == etatColision.GAUCHE)
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
            this.colisionY = etatColision.AUCUN;
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