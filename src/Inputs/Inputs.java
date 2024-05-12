package Inputs;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Inputs
{
    public static int Direction;
    public static boolean KeyW = false,KeyS = false,KeyA = false,KeyD = false,KeySpace = false,KeyI = false;
    public static int GestionInputs(JFrame fenetre)
    {
        fenetre.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e)
            {
            }

            @Override
            public void keyPressed(KeyEvent e)
            {
                //D
                if(e.getKeyCode() == KeyEvent.VK_D)
                {
                    KeyD = true;
                    KeyA = false;
                }
                // A
                if(e.getKeyCode() == KeyEvent.VK_A)
                {
                    KeyA = true;
                    KeyD = false;
                }
                // W
                if(e.getKeyCode() == KeyEvent.VK_W)
                {
                    KeyW = true;
                    KeyS = false;
                }
                // S
                if(e.getKeyCode() == KeyEvent.VK_S)
                {
                    KeyS = true;
                    KeyW = false;
                }
                Direction = e.getKeyCode();

                Direction = 0;
                if(KeyW)
                {
                    if(KeyD)
                    {
                        Direction = 2;
                    } else if (KeyA)
                    {
                        Direction = 8;
                    }
                    else
                    {
                        Direction = 1;
                    }
                } else if (KeyS)
                {
                    if(KeyD)
                    {
                        Direction = 4;
                    } else if (KeyA)
                    {
                        Direction = 6;
                    }
                    else
                    {
                        Direction = 5;
                    }
                } else if (KeyA)
                {
                    Direction = 7;
                }else if (KeyD)
                {
                    Direction = 3;
                }
                if(e.getKeyCode() == KeyEvent.VK_SPACE)
                {
                  KeySpace = true;
                }
                if(e.getKeyCode() == KeyEvent.VK_I)
                {
                    KeyI = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_D)
                {
                    KeyD = false;
                    if (!KeyA)
                    {
                        Direction = 0;
                    }
                    if(KeyW)
                    {
                        if(KeyA)
                        {
                            Direction = 8;
                        }
                        else
                        {
                            Direction = 1;
                        }
                    } else if (KeyS)
                    {
                        if(KeyA)
                        {
                            Direction = 6;
                        }
                        else
                        {
                            Direction = 5;
                        }
                    }
                }

                if(e.getKeyCode() == KeyEvent.VK_A)
                {
                    if (!KeyD)
                    {
                        Direction = 0;
                    }
                    if(KeyW)
                    {
                        if(KeyD)
                        {
                            Direction = 2;
                        }
                        else
                        {
                            Direction = 1;
                        }
                    } else if (KeyS)
                    {
                        if(KeyD)
                        {
                            Direction = 4;
                        }
                        else
                        {
                            Direction = 5;
                        }
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_W)
                {
                    KeyW = false;
                    Direction = 0;
                    if(KeyA)
                    {
                        Direction = 7;
                    } else if (KeyD)
                    {
                        Direction = 3;
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_S)
                {
                    KeyS = false;
                    KeyW = false;
                    Direction = 0;
                    if(KeyA)
                    {
                        Direction = 7;
                    } else if (KeyD)
                    {
                        Direction = 3;
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_SPACE)
                {
                    KeySpace = false;
                }
                if(e.getKeyCode() == KeyEvent.VK_I)
                {
                    KeyI = false;
                }
            }
        });
        return Direction;
    }

}
