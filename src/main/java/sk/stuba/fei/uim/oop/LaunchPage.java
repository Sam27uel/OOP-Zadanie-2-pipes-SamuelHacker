package sk.stuba.fei.uim.oop;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class LaunchPage extends JFrame implements ActionListener{

    int BoardSize = 8;
    int Level = 1;
    JPanel panelGameBoard = new JPanel();

    JPanel panelUserOptions = new JPanel();
    JComboBox cbSize = new JComboBox();

    JButton btnResetGame = new JButton();

    JButton btnCheckPath = new JButton();

    JLabel lblLevel = new JLabel();

    JLabel lblSize= new JLabel();

    Field arrMaze2D[][];

    MazeGenerator Maze = null;

    int FrameSize_8 = 500;
    int PanelSize_8 = 400;
    Rectangle dim8_GameBoardPanel = new Rectangle(40, 10, PanelSize_8, PanelSize_8);
    Rectangle dim8_UserOptionsPanel = new Rectangle(50, PanelSize_8 + 15, PanelSize_8, 50);
    Dimension dim8_FrameSize = new Dimension(FrameSize_8, FrameSize_8);

    int FrameSize_10 = 600;
    int PanelSize_10 = 500;

    Rectangle dim10_GameBoardPanel = new Rectangle(40, 10, PanelSize_10, PanelSize_10);
    Rectangle dim10_UserOptionsPanel = new Rectangle(100, PanelSize_10 + 15, PanelSize_10, 100);
    Dimension dim10_FrameSize = new Dimension(FrameSize_10, FrameSize_10);


    int FrameSize_12 = 700;
    int PanelSize_12 = 600;
    Rectangle dim12_GameBoardPanel = new Rectangle(40, 10, PanelSize_12, PanelSize_12);
    Rectangle dim12_UserOptionsPanel = new Rectangle(150, PanelSize_12 + 15, PanelSize_12, 150);
    Dimension dim12_FrameSize = new Dimension(FrameSize_12, FrameSize_12);

    int FrameSize_16 = 1000;
    int PanelSize_16 = 900;

    Rectangle dim16_GameBoardPanel = new Rectangle(40, 10, PanelSize_16, PanelSize_16);
    Rectangle dim16_UserOptionsPanel = new Rectangle(300, PanelSize_16 + 15, PanelSize_16, 300);
    Dimension dim16_FrameSize = new Dimension(FrameSize_16, FrameSize_16);

    LaunchPage() throws ExceptionResourceRead {
        JOptionPane.showMessageDialog(null,
                "This game is called Pipes. The objective is to connect the pipes on the board so that the water" +
                        " can flow\nfrom the starting point S to the ending point F. To rotate a pipe ( L or I ), click on " +
                        "it by mouse and when\nyou find a correct connection/path, you will see color evaluation and your " +
                        "level will increase.\n\nUse the 'Size' list to choose the size of the game board.\nUse 'Check path' " +
                        "button when you want to verify path.\nUse the 'Reset game' button to start a new game.\n\nPlease, if " +
                        "you want to see the correctly color-evaluated pipes, disconnect the other pipes from the start\nso " +
                        "that water does not leak. However, the path check always works.\n\nIf you are ready to play, press OK " +
                        "and have fun!!!",
                "Instructions - Game Plan",
                JOptionPane.INFORMATION_MESSAGE);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(dim8_FrameSize);
        this.setLayout(null);
        this.setTitle("Assignment2 - PIPES");

        panelGameBoard.setFocusable(true);
        panelGameBoard.addKeyListener(new KeyHandler(this));

        panelGameBoard.setBounds(dim8_GameBoardPanel);
        panelGameBoard.setLayout(new GridLayout(BoardSize,BoardSize));
        panelGameBoard.setBackground(Color.white);
        panelGameBoard.setVisible(true);

        panelUserOptions.setBounds(dim8_UserOptionsPanel);
        panelUserOptions.setLayout(new FlowLayout(10, 10, 10));

        cbSize.setToolTipText("Choose size of board");
        cbSize.addItem("8");
        cbSize.addItem("10");
        cbSize.addItem("12");
        cbSize.addItem("16");
        cbSize.addActionListener(this);
        cbSize.setFocusable(false);
        cbSize.requestFocus(false);

        btnCheckPath.setText("Check path");
        btnCheckPath.addActionListener(this);
        btnCheckPath.setFocusable(false);
        btnCheckPath.requestFocus(false);

        btnResetGame.setText("Reset game");
        btnResetGame.addActionListener(this);
        btnResetGame.setFocusable(false);
        btnResetGame.requestFocus(false);

        lblLevel.setText("Level: " + Level);

        lblSize.setText("Size:");

        panelUserOptions.add(btnCheckPath);
        panelUserOptions.add(lblLevel);
        panelUserOptions.add(lblSize);
        panelUserOptions.add(cbSize);
        panelUserOptions.add(btnResetGame);

        InitializeGameBoard(this.BoardSize);

        this.add(panelUserOptions);
        this.add(panelGameBoard);


        this.setVisible(true);
    }

    public void ResetGame(boolean resetLevel) throws ExceptionResourceRead {
        arrMaze2D = new Field[BoardSize][BoardSize];

        panelGameBoard.removeAll();

        if (BoardSize == 8)
        {
            this.setSize(dim8_FrameSize);
            panelGameBoard.setBounds(dim8_GameBoardPanel);
            panelGameBoard.setLayout(new GridLayout(BoardSize,BoardSize));
            panelUserOptions.setBounds(dim8_UserOptionsPanel);

            panelGameBoard.revalidate();
        }
        else if (BoardSize == 10)
        {
            this.setSize(dim10_FrameSize);
            panelGameBoard.setBounds(dim10_GameBoardPanel);
            panelGameBoard.setLayout(new GridLayout(BoardSize,BoardSize));
            panelUserOptions.setBounds(dim10_UserOptionsPanel);

            panelGameBoard.revalidate();
        }
        else if (BoardSize == 12)
        {
            this.setSize(dim12_FrameSize);
            panelGameBoard.setBounds(dim12_GameBoardPanel);
            panelGameBoard.setLayout(new GridLayout(BoardSize,BoardSize));
            panelUserOptions.setBounds(dim12_UserOptionsPanel);

            panelGameBoard.revalidate();
        }
        else if (BoardSize == 16)
        {
            this.setSize(dim16_FrameSize);
            panelGameBoard.setBounds(dim16_GameBoardPanel);
            panelGameBoard.setLayout(new GridLayout(BoardSize,BoardSize));
            panelUserOptions.setBounds(dim16_UserOptionsPanel);

            panelGameBoard.revalidate();
        }

        if (resetLevel) {
            Level = 1;
            lblLevel.setText(" Level: 1");
        }

        InitializeGameBoard(BoardSize);
    }
    private void InitializeGameBoard(int dimension) throws ExceptionResourceRead {

        Maze = new MazeGenerator(BoardSize);
        arrMaze2D = Maze.GetGeneratedMaze();

        for (int l = 0; l < BoardSize; l++)
            for(int m = 0; m < BoardSize; m++)
                panelGameBoard.add(arrMaze2D[l][m]);

        panelGameBoard.revalidate();
    }

    public void HandleSuccessfullySolvedMaze() throws IOException, ExceptionResourceRead {
        Level++;
        lblLevel.setText("Level: " + Level);
        Maze.SetGenerateMazeEndReached(false);

        JOptionPane.showMessageDialog(null,
                "Level up! ",
                "WELL DONE",
                JOptionPane.INFORMATION_MESSAGE);

        this.ResetGame(false);
    }

    public void ShowCheckResult()
    {
        boolean success = false;

        if( Maze.SolveMaze()) {
            success = true;
        }
        else{
            success = false;
        }

        ArrayList<Field> listPathToFinish = Maze.getListPathToFinish();

        for (Field field : listPathToFinish)
        {
            int i = field.GetRow() == 0 ? field.GetCol() : (field.GetRow() * BoardSize) + field.GetCol();
            ((JButton)panelGameBoard.getComponent(i)).setBorder(new LineBorder(Color.GREEN, 3));
        }

        if (!success) {
            if (listPathToFinish.size() > 0) {
                Field field = listPathToFinish.get(listPathToFinish.size() - 1);
                int i = field.GetRow() == 0 ? field.GetCol() : (field.GetRow() * BoardSize) + field.GetCol();
                ((JButton) panelGameBoard.getComponent(i)).setBorder(new LineBorder(Color.RED, 3));
            }
        }

        if (success)
        {
            try {
                HandleSuccessfullySolvedMaze();
            }

            catch (ExceptionResourceRead | IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null,
                    "Bad path",
                    "TRY AGAIN",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.cbSize){

            int size = Integer.parseInt(this.cbSize.getSelectedItem().toString());
            this.BoardSize = size;

            try {
                this.ResetGame(true);
            } catch (ExceptionResourceRead ex) {
                throw new RuntimeException(ex);
            }
        }

        if (e.getSource() == this.btnCheckPath){
            ShowCheckResult();
        }

        if (e.getSource() == this.btnResetGame){

            try {
                this.ResetGame(true);
            } catch (ExceptionResourceRead ex) {
                throw new RuntimeException(ex);
            }
        }

    }
}
