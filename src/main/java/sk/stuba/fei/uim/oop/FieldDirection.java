package sk.stuba.fei.uim.oop;

/*
 0 - left, 1 - up , 2 - right, 3 - down

 L cases:        lef   up   right  down

 L cases:  ┍      0,   0,    +1,    1

 L cases:  ┑     -1,   0,     0,     1

 L cases:  ┙     -1,   -1,    0,    -0

 L cases:  ┕	 0,    -1,    +1,   -0

 I cases:  ─	 -1,    0,    +1,    0

 I cases:  │      0,    -1,    0,    +1
 */

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
            // 0 - left, 1 - up , 2 - right, 3 - down

            // L cases:  ┍               0, 0, +1, +1
            listDirections.add(new int[]{0, 0,  1,  1});

            // L cases:  ┑               -1, 0,  0,  +1
            listDirections.add(new int[]{-1, 0,  0,  1});

            // L cases:  ┙               -1,  -1,  0,  0
            listDirections.add(new int[]{-1,  -1,  0,  0});

            // L cases:  ┕	             0,  -1, +1,  0
            listDirections.add(new int[]{0,  -1,  1,  0});
        }

        if (type == 'I')
        {
            // 0 - left, 1 - up , 2 - right, 3 - down

            // I cases:  │      0,    -1,    0,    +1
            listDirections.add(new int[]{0, -1, 0, 1}); // default

            // I cases:  ─	 -1,    0,    +1,    0
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