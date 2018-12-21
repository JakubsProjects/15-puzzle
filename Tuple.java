import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.util.ArrayList;
import java.util.List;



public class Tuple {


    private String board[];
    private List<Integer> moverepresantation = new ArrayList<Integer>();


    public Tuple(String[] setstring)
    {
        board = setstring;
    }

    public Tuple(String[] s, int l)
    {
        board = s;
        moverepresantation.add(l);

    }


    public String[] getBoard (){
        return board;
    }

    public List<Integer> getmoverepresantation(){

        return moverepresantation;
    }
}
