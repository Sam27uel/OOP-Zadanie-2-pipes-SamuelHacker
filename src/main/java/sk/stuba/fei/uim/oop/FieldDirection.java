package sk.stuba.fei.uim.oop;

import java.util.ArrayList;
public class FieldDirection
{
    int indexInList;

    ArrayList<int[]> listDirections = new ArrayList<int[]>();

    public void SetDirectionIndex(int index)
    {
        if (indexInList + index > listDirections.size() - 1)
        {
            indexInList = (indexInList + index)  - listDirections.size();
        }
        else
        {
            indexInList += index;
        }
    }

    FieldDirection(char type)
    {
        indexInList = 0;

        if (type == 'L')
        {
            listDirections.add(new int[]{0, 0,  1,  1});

            listDirections.add(new int[]{-1, 0,  0,  1});

            listDirections.add(new int[]{-1,  -1,  0,  0});

            listDirections.add(new int[]{0,  -1,  1,  0});
        }

        if (type == 'I')
        {
            listDirections.add(new int[]{0, -1, 0, 1});

            listDirections.add(new int[]{-1, 0, 1, 0});
        }

        if (type == 'S' || type == 'K')
        {
            listDirections.add(new int[]{-1, -1, 1, 1});
        }
    }

    int[] GetDirection()
    {
        return listDirections.get(indexInList);
    }
}