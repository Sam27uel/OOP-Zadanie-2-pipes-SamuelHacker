package sk.stuba.fei.uim.oop;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Field extends JButton implements ActionListener {
    int row;
    int col;

    public int GetRow()
    {
        return row;
    }

    public int GetCol()
    {
        return col;
    }

    ImageIcon image = null;

    FieldDirection Direction = null;
    boolean IsStartField = false;
    boolean IsFinishField = false;

    boolean WasVisited = false;

    char TypeOfPipe = 'X';

    public Field(){

    }
    public Field(char type, int x, int y) throws ExceptionResourceRead {

        this.setFocusable(false);
        this.requestFocus(false);

        this.row = x;
        this.col = y;

        BufferedImage imageBuffer = null;

        if (type == 'L') {
            try{
                imageBuffer = ImageIO.read(Field.class.getResourceAsStream("/L.png"));
                image = new ImageIcon(imageBuffer);

                TypeOfPipe = 'L';
                Direction = new FieldDirection('L');
            }
            catch (IOException ex)
            {
                throw new ExceptionResourceRead("Problem occurred when loading image from resources into JButton element of JPanel (picture L.png)");
            }
        }
        else if (type == 'I') {
            try {
                imageBuffer = ImageIO.read(Field.class.getResourceAsStream("/I.png"));
                image = new ImageIcon(imageBuffer);

                TypeOfPipe = 'I';
                Direction = new FieldDirection('I');
            }
            catch (IOException ex)
            {
                throw new ExceptionResourceRead("Problem occurred when loading image from resources into JButton element of JPanel (picture I.png)");
            }
        }
        else if (type == 'K') {
            try {
                Direction = new FieldDirection('K');

                imageBuffer = ImageIO.read(Field.class.getResourceAsStream("/end.png"));
                image = new ImageIcon(imageBuffer);

                Direction.SetDirectionIndex(0);

                IsFinishField = true;
                TypeOfPipe = 'K';
            }
            catch (IOException ex)
            {
                throw new ExceptionResourceRead("Problem occurred when loading image from resources into JButton element of JPanel (picture end.png)");
            }
        }
        else if (type == 'S') {
            try {
                Direction = new FieldDirection('S');

                imageBuffer = ImageIO.read(Field.class.getResourceAsStream("/start.png"));
                image = new ImageIcon(imageBuffer);


                Direction.SetDirectionIndex(0);

                IsStartField = true;
                TypeOfPipe = 'S';
            }
            catch (IOException ex)
            {
                throw new ExceptionResourceRead("Problem occurred when loading image from resources into JButton element of JPanel (picture start.png)");
            }
        }

        super.setSize(50, 50);

        super.setIcon(image);
        this.addActionListener(this);
    }

    public boolean IsStartFieldFunc()
    {
        return IsStartField;
    }

    public boolean IsFinishFieldFunc()
    {
        return IsFinishField;
    }

    public void SetWasVisited(boolean visited)
    {
        this.WasVisited = visited;
    }

    public boolean GetWasVisited(){
        return this.WasVisited;
    }

    public void TurnPicture(int times)
    {
        int w = image.getIconWidth();
        int h = image.getIconHeight();
        int type = BufferedImage.TYPE_INT_RGB;
        BufferedImage imageBuffered = new BufferedImage(h, w, type);
        Graphics2D g2 = imageBuffered.createGraphics();
        double x = (h - w) / 2.0;
        double y = (w - h) / 2.0;
        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        at.rotate(Math.toRadians(times * 90), w / 2.0, h / 2.0);
        g2.drawImage(image.getImage(), at, null);
        g2.dispose();
        image = new ImageIcon(imageBuffered);

        setIcon(image);
        Direction.SetDirectionIndex(times);
    }

    public FieldDirection GetFieldDirection()
    {
        return Direction;
    }

    // function which checks if there is connection between current field where we stand ( parameter - FieldDirection from )
    // and another object - field where we should jump
    // function is called upon object of class Field where object is field which will be checked if jump into this field is possible
    public boolean IsConnectionCorrect(Field from)
    {
        int [] direction = Direction.GetDirection();
        int [] directionFrom = from.GetFieldDirection().GetDirection();

        int rowFrom = from.GetRow();
        int colFrom = from.GetCol();

        boolean connectionCorrect = false;

        if (TypeOfPipe == 'L')
        {

            // ┍      0,   0,    +1,    +1
            if (direction[0] == 0 && direction[1] == 0 && direction[2] == 1 && direction[3] == 1)
            {
                // 0 - left, 1 - up , 2 - right, 3 - down

                // if we are below of ┍
                if (directionFrom[1] == -1 && rowFrom > this.row)
                {
                    connectionCorrect = true;
                }

                // if we are next to ┍ on right side
                if (directionFrom[0] == -1 && this.col < colFrom)
                {
                    connectionCorrect = true;
                }

            }
            // ┑     -1,   0,     0,     1
            if (direction[0] == -1 && direction[1] == 0 && direction[2] == 0 && direction[3] == 1)
            {
                // 0 - left, 1 - up , 2 - right, 3 - down

                // if we are below of  ┑
                if (directionFrom[1] == -1 && rowFrom > this.row)
                {
                    connectionCorrect = true;
                }

                // if we are next to  ┑ on left side
                if (directionFrom[2] == 1 && colFrom < this.col)
                {
                    connectionCorrect = true;
                }

            }
            // ┙     -1,   -1,    0,    -0
            if (direction[0] == -1 && direction[1] == -1 && direction[2] == 0 && direction[3] == 0)
            {
                // 0 - left, 1 - up , 2 - right, 3 - down

                // if we are above of  ┙
                if (directionFrom[3] == 1 && rowFrom < this.row)
                {
                    connectionCorrect = true;
                }

                // if we are next to  ┙ on left side
                if (directionFrom[2] == 1 && colFrom < this.col)
                {
                    connectionCorrect = true;
                }

            }
            //┕	 0,    -1,    +1,   0
            if (direction[0] == 0 && direction[1] == -1 && direction[2] == 1 && direction[3] == 0)
            {
                // 0 - left, 1 - up , 2 - right, 3 - down

                // if we are above of  ┕
                if (directionFrom[3] == 1 && rowFrom < this.row)
                {
                    connectionCorrect = true;
                }

                // if we are next to  ┕ on right side
                if (directionFrom[0] == -1 &&  this.col < colFrom)
                {
                    connectionCorrect = true;
                }
            }
        }

        if (TypeOfPipe == 'I')
        {
            //   ─	 -1,    0,    +1,    0
            if (direction[0] == -1 && direction[1] == 0 && direction[2] == 1 && direction[3] == 0)
            {
                // 0 - left, 1 - up , 2 - right, 3 - down

                // if we are on left side next to ─
                if (directionFrom[2] == 1 && colFrom < this.col)
                {
                    connectionCorrect = true;
                }

                // if we are on right side next to ─
                if (directionFrom[0] == -1 && this.col < colFrom)
                {
                    connectionCorrect = true;
                }
            }

            //   │      0,    -1,    0,    +1
            if (direction[0] == 0 && direction[1] == -1 && direction[2] == 0 && direction[3] == 1)
            {
                // 0 - left, 1 - up , 2 - right, 3 - down

                // if we are above to │
                if (directionFrom[3] == 1 && rowFrom < this.row)
                {
                    connectionCorrect = true;
                }

                // if we are below to │
                if (directionFrom[1] == -1 && rowFrom > this.row)
                {
                    connectionCorrect = true;
                }
            }

        }

        if (TypeOfPipe == 'S')
        {
            connectionCorrect =  true;
        }

        if (TypeOfPipe == 'K')
        {
            // 0 - left, 1 - up , 2 - right, 3 - down

            // if we are above to K
            if (direction[1] == - 1) {
                if (directionFrom[3] == 1 && rowFrom < this.row) {
                    connectionCorrect = true;
                }
            }

            // if we are below to K
            if (direction[3] == 1) {
                if (directionFrom[1] == -1 && rowFrom > this.row) {
                    connectionCorrect = true;
                }
            }

            // if we are on right side next to K
            if (direction[2] == 1) {
                if (directionFrom[0] == -1 && colFrom > this.col) {
                    connectionCorrect = true;
                }
            }

            // if we are on left side next to K
            if (direction[0] == -1) {
                if (directionFrom[2] == 1 && colFrom < this.col) {
                    connectionCorrect = true;
                }
            }

        }

        return connectionCorrect;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (!this.IsStartField && !this.IsFinishField) {

            TurnPicture(1);
        }
    }
}
