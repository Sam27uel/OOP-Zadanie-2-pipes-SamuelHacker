package sk.stuba.fei.uim.oop;

import java.util.ArrayList;
import java.util.Random;
public class MazeGenerator {

    ArrayList<Field> listPathToFinish = new ArrayList<>();
    Field maze2D[][] = null;
    int BoardSize;

    int[] arrStartFieldIndices;
    int[] arrEndFieldIndices;

    boolean GenerateMazeEndReached = false;

    Random rand = new Random();

    public MazeGenerator(int boardSize) throws ExceptionResourceRead {
        this.maze2D = new Field[BoardSize][BoardSize];
        this.BoardSize = boardSize;
        SetStartFinishFieldAndInitializeRest();
        GenerateMaze();
    }

    private void GenerateMaze() throws ExceptionResourceRead {
        arrStartFieldIndices = GetStartOrEndField(true);
        arrEndFieldIndices = GetStartOrEndField(false);

        maze2D[arrStartFieldIndices[0]][arrStartFieldIndices[1]].SetWasVisited(true);

        GenerateMaze(arrStartFieldIndices[0], arrStartFieldIndices[1], arrEndFieldIndices[0], arrEndFieldIndices[1]);
        SetAllFieldsVisitedToFalse();
        GenerateMazeEndReached = false;
    }

    private void SetStartFinishFieldAndInitializeRest() throws ExceptionResourceRead {

        int sideStart = rand.nextInt(4);

        int sideFinish = 0;

        if (sideStart == 0)
            sideFinish = 2;
        else if (sideStart == 1)
            sideFinish = 3;
        else if (sideStart == 2)
            sideFinish = 0;
        else if (sideStart == 3)
            sideFinish = 1;

        int StartFieldPos = rand.nextInt(BoardSize);
        int EndFieldPos = rand.nextInt(BoardSize);

        int helpCounter = 0;
        int row = 0;
        int col = 0;

        this.maze2D = new Field[BoardSize][BoardSize];

        while (helpCounter < (BoardSize * BoardSize)) {

            Field temp = null;
            int num = 0;

            if (sideStart == 0 && temp == null)
            {
                if (helpCounter == 0 && StartFieldPos == 0)
                {
                    temp = new Field('S', row, col);
                }
                else if (helpCounter % BoardSize == 0 && StartFieldPos == helpCounter / BoardSize)
                {
                    temp = new Field('S', row, col);
                }
            } else if (sideStart == 2 && temp == null)
            {
                if (helpCounter == (BoardSize - 1) && StartFieldPos == 0) {
                    temp = new Field('S', row, col);
                }
                else if ((helpCounter + 1) % BoardSize == 0 && StartFieldPos == (helpCounter + 1) / BoardSize) {
                    temp = new Field('S', row, col);
                }
            } else if (sideStart == 1 && temp == null)
            {
                if (helpCounter == StartFieldPos) {

                    temp = new Field('S', row, col);
                }
            } else if (sideStart == 3 && temp == null)
            {
                if (helpCounter >= ((BoardSize * BoardSize) - BoardSize) && helpCounter == ((BoardSize * BoardSize) - BoardSize + StartFieldPos))
                    temp = new Field('S', row, col);
            }

            if (sideFinish == 0 && temp == null)
            {
                if (helpCounter == 0 && EndFieldPos == 0) {
                    temp = new Field('K', row, col);
                }
                else if (helpCounter % BoardSize == 0 && EndFieldPos == helpCounter / BoardSize) {

                    temp = new Field('K', row, col);
                }

            } else if (sideFinish == 2 && temp == null )
            {
                if (helpCounter == (BoardSize - 1) && EndFieldPos == 0) {
                    temp = new Field('K', row, col);
                }
                else if ((helpCounter + 1) % BoardSize == 0 && EndFieldPos == (helpCounter + 1) / BoardSize) {
                    temp = new Field('K', row, col);
                }
            } else if (sideFinish == 1 && temp == null)
            {
                if (helpCounter == EndFieldPos)
                {
                    temp = new Field('K', row, col);
                }

            } else if (sideFinish == 3 && temp == null)
            {
                if (helpCounter >= ((BoardSize * BoardSize) - BoardSize) && helpCounter == ((BoardSize * BoardSize) - BoardSize + EndFieldPos))
                {
                    temp = new Field('K', row, col);
                }
            }

            if (temp != null)
            {
                this.maze2D[row][col] = temp;
            }
            else
            {
                this.maze2D[row][col] = new Field();
            }

            helpCounter++;
            col++;

            if ( helpCounter  % BoardSize == 0) {
                row++;
                col = 0;
            }
        }
    }

    private void GenerateMaze(int iRow, int iCol, int iRowEnd, int iColEnd) throws ExceptionResourceRead {
        Field fieldTemp = null;

        while ((((iCol + 1 < BoardSize) && !maze2D[iRow][iCol + 1].GetWasVisited()) || ((iRow + 1 < BoardSize) && !maze2D[iRow + 1][iCol].GetWasVisited())
                || ((iCol - 1  >= 0) && !maze2D[iRow][iCol - 1].GetWasVisited()) || ((iRow - 1  >= 0) && !maze2D[iRow - 1][iCol].GetWasVisited()))) {

            while (true) {

                if (!GenerateMazeEndReached && iRow == iRowEnd && iCol == iColEnd)
                {
                    maze2D[iRow][iCol].SetWasVisited(true);
                    GenerateMazeEndReached = true;
                    break;
                }

                int r = rand.nextInt(4);
                int turnL= 1 + (int)(Math.random() * ((4 - 1) + 1));
                int turnI = 1 + (int)(Math.random() * ((2 - 1) + 1));

                if (r == 0 && (iCol + 1 < BoardSize) && !maze2D[iRow][iCol + 1].GetWasVisited()) {

                    if(!GenerateMazeEndReached) {
                        if (!maze2D[iRow][iCol + 1].IsFinishFieldFunc()) {
                            fieldTemp = new Field('L', iRow, iCol + 1);
                            maze2D[iRow][iCol + 1] = fieldTemp;
                            maze2D[iRow][iCol + 1].TurnPicture(turnL);
                        }
                    }
                    else{
                        if (!maze2D[iRow][iCol + 1].IsFinishFieldFunc()) {
                            fieldTemp = new Field('I', iRow, iCol + 1);
                            maze2D[iRow][iCol + 1] = fieldTemp;
                            maze2D[iRow][iCol + 1].TurnPicture(turnI);
                        }
                    }

                    maze2D[iRow][iCol].SetWasVisited(true);
                    maze2D[iRow][iCol + 1].SetWasVisited(true);

                    GenerateMaze(iRow, iCol + 1, iRowEnd, iColEnd);
                    break;
                }
                else if (r == 1 && (iRow + 1 < BoardSize) && !maze2D[iRow + 1][iCol].GetWasVisited()) {

                    if(!GenerateMazeEndReached) {
                        if (!maze2D[iRow + 1][iCol].IsFinishFieldFunc()) {
                            fieldTemp = new Field('I', iRow + 1, iCol);
                            maze2D[iRow + 1][iCol] = fieldTemp;
                            maze2D[iRow + 1][iCol].TurnPicture(turnI);
                        }
                    }
                    else{
                        if (!maze2D[iRow + 1][iCol].IsFinishFieldFunc()) {
                            fieldTemp = new Field('L', iRow + 1, iCol);
                            maze2D[iRow + 1][iCol] = fieldTemp;
                            maze2D[iRow + 1][iCol].TurnPicture(turnL);
                        }
                    }

                    maze2D[iRow][iCol].SetWasVisited(true);
                    maze2D[iRow + 1][iCol].SetWasVisited(true);

                    GenerateMaze(iRow + 1, iCol, iRowEnd, iColEnd);

                    break;
                }
                else if (r == 2 && (iCol - 1 >= 0) && !maze2D[iRow][iCol - 1].GetWasVisited()) {

                    if(!GenerateMazeEndReached) {
                        if (!maze2D[iRow][iCol - 1].IsFinishFieldFunc()) {
                            fieldTemp = new Field('L', iRow, iCol - 1);
                            maze2D[iRow][iCol - 1] = fieldTemp;
                            maze2D[iRow][iCol - 1].TurnPicture(turnL);
                        }
                    }
                    else{
                        if (!maze2D[iRow][iCol - 1].IsFinishFieldFunc()) {
                            fieldTemp = new Field('I', iRow, iCol - 1);
                            maze2D[iRow][iCol - 1] = fieldTemp;
                            maze2D[iRow][iCol - 1].TurnPicture(turnI);
                        }
                    }

                    maze2D[iRow][iCol - 1].SetWasVisited(true);
                    maze2D[iRow][iCol].SetWasVisited(true);

                    GenerateMaze(iRow, iCol - 1, iRowEnd, iColEnd);

                    break;
                }
                else if (r == 3 && (iRow - 1 >= 0) && !maze2D[iRow - 1][iCol].GetWasVisited()) {

                    if(!GenerateMazeEndReached) {
                        if (!maze2D[iRow - 1][iCol].IsFinishFieldFunc()) {
                            fieldTemp = new Field('I', iRow - 1, iCol);
                            maze2D[iRow - 1][iCol] = fieldTemp;
                            maze2D[iRow - 1][iCol].TurnPicture(turnI);
                        }
                    }
                    else{
                        if (!maze2D[iRow - 1][iCol].IsFinishFieldFunc()) {
                            fieldTemp = new Field('L', iRow - 1, iCol);
                            maze2D[iRow - 1][iCol] = fieldTemp;
                            maze2D[iRow - 1][iCol].TurnPicture(turnL);
                        }
                    }

                    maze2D[iRow][iCol].SetWasVisited(true);
                    maze2D[iRow - 1][iCol].SetWasVisited(true);

                    GenerateMaze(iRow - 1, iCol, iRowEnd, iColEnd);

                    break;
                }
            }
        }
    }

    public ArrayList<Field> getListPathToFinish()
    {
        return this.listPathToFinish;
    }

    int[] GetStartOrEndField(boolean startField)
    {
        int i = 0;
        int j = 0;
        int[] arr = new int[2];

        for (i = 0; i < BoardSize; i++)
        {
            for(j=0; j < BoardSize; j++)
            {
                if (maze2D[i][j] != null) {
                    if (startField && maze2D[i][j].IsStartFieldFunc()) {
                        arr[0] = i;
                        arr[1] = j;

                        return arr;
                    } else if (!startField && maze2D[i][j].IsFinishFieldFunc()) {
                        arr[0] = i;
                        arr[1] = j;

                        return arr;
                    }
                }

            }
        }

        return arr;
    }

    public Field[][] GetGeneratedMaze()
    {
        return this.maze2D;
    }

    private void SetAllFieldsVisitedToFalse()
    {
        for (int i = 0; i < BoardSize; i++)
            for(int j = 0; j < BoardSize; j++)
                    maze2D[i][j].SetWasVisited(false);
    }

    public boolean SolveMaze()
    {
        boolean value = SolveMaze(arrStartFieldIndices[0], arrStartFieldIndices[1],  arrEndFieldIndices[0], arrEndFieldIndices[1]);
        SetAllFieldsVisitedToFalse();
        return value;
    }

    private boolean SolveMaze(int iRow, int iCol, int iRowEnd, int iColEnd)
    {
        while ( ( !GenerateMazeEndReached) &&
                (((iCol + 1 < BoardSize) && !maze2D[iRow][iCol + 1].GetWasVisited() && maze2D[iRow][iCol + 1].IsConnectionCorrect(maze2D[iRow][iCol])) ||
                ((iRow + 1 < BoardSize) && !maze2D[iRow + 1][iCol].GetWasVisited() && maze2D[iRow + 1][iCol].IsConnectionCorrect(maze2D[iRow][iCol]) ) ||
                ((iCol - 1  >= 0) && !maze2D[iRow][iCol - 1].GetWasVisited() && maze2D[iRow][iCol - 1].IsConnectionCorrect(maze2D[iRow][iCol])) ||
                ((iRow - 1  >= 0) && !maze2D[iRow - 1][iCol].GetWasVisited() && maze2D[iRow - 1][iCol].IsConnectionCorrect(maze2D[iRow][iCol])) ) )
        {
            while (true) {

                if (!GenerateMazeEndReached && iRow == iRowEnd && iCol == iColEnd)
                {
                    GenerateMazeEndReached = true;
                    break;
                }

                if ((iCol + 1 < BoardSize) && !maze2D[iRow][iCol + 1].GetWasVisited() && maze2D[iRow][iCol + 1].IsConnectionCorrect(maze2D[iRow][iCol])) {
                    maze2D[iRow][iCol].SetWasVisited(true);
                    maze2D[iRow][iCol + 1].SetWasVisited(true);
                    listPathToFinish.add(maze2D[iRow][iCol + 1]);

                    if (maze2D[iRow][iCol + 1].IsFinishFieldFunc())
                    {
                        GenerateMazeEndReached = true;
                        break;
                    }

                    SolveMaze(iRow, iCol + 1, iRowEnd, iColEnd);

                    break;
                }
                else if ((iRow + 1 < BoardSize) && !maze2D[iRow + 1][iCol].GetWasVisited() && maze2D[iRow + 1][iCol].IsConnectionCorrect(maze2D[iRow][iCol])) {
                    maze2D[iRow][iCol].SetWasVisited(true);
                    maze2D[iRow + 1][iCol].SetWasVisited(true);
                    listPathToFinish.add(maze2D[iRow + 1][iCol]);

                    if (maze2D[iRow + 1][iCol].IsFinishFieldFunc())
                    {
                        GenerateMazeEndReached = true;
                        break;
                    }

                    SolveMaze(iRow + 1, iCol, iRowEnd, iColEnd);
                    break;
                }
                else if ((iCol - 1  >= 0) && !maze2D[iRow][iCol - 1].GetWasVisited() && maze2D[iRow][iCol - 1].IsConnectionCorrect(maze2D[iRow][iCol])) {
                    maze2D[iRow][iCol - 1].SetWasVisited(true);
                    maze2D[iRow][iCol].SetWasVisited(true);
                    listPathToFinish.add(maze2D[iRow][iCol - 1]);

                    if (maze2D[iRow][iCol - 1].IsFinishFieldFunc())
                    {
                        GenerateMazeEndReached = true;
                        break;
                    }

                    SolveMaze(iRow, iCol - 1, iRowEnd, iColEnd);
                    break;
                }
                else if ((iRow - 1  >= 0) && !maze2D[iRow - 1][iCol].GetWasVisited() && maze2D[iRow - 1][iCol].IsConnectionCorrect(maze2D[iRow][iCol])) {
                    maze2D[iRow][iCol].SetWasVisited(true);
                    maze2D[iRow - 1][iCol].SetWasVisited(true);
                    listPathToFinish.add(maze2D[iRow - 1][iCol]);

                    if (maze2D[iRow - 1][iCol].IsFinishFieldFunc())
                    {
                        GenerateMazeEndReached = true;
                        break;
                    }

                    SolveMaze(iRow - 1, iCol, iRowEnd, iColEnd);
                    break;
                }

                break;
            }
        }

        return GenerateMazeEndReached;
    }

    public void SetGenerateMazeEndReached(boolean val)
    {
        GenerateMazeEndReached = val;
    }

}
