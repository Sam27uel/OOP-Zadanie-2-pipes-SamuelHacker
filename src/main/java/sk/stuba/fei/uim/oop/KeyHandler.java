package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class KeyHandler implements KeyListener {

    LaunchPage instance;
    public KeyHandler(LaunchPage instance)
    {
        this.instance = instance;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_R)
        {
            try {
                instance.ResetGame(true);
            } catch (ExceptionResourceRead ex) {
                throw new RuntimeException(ex);
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            instance.ShowCheckResult();
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            instance.dispose();
            instance.setVisible(false);
            instance.dispatchEvent(new WindowEvent(instance, WindowEvent.WINDOW_CLOSING));
        }
    }
}
