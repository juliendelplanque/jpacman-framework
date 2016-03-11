package nl.tudelft.jpacman.jannou.score.feat;

/**
 * Created by Jannou on 6/03/16.
 */
public class TestFeat extends  Feat {

    public TestFeat(){
        setName("TestFeat");
        setValue(100);
        setDesc("Test Feat score >= 60");
        setRealised(true);
    }
    public String toMaps(){
        return  "<feat>\n\t\t<name>"+getName() + "</name>\n\t\t<description>" + getDesc() +  "</description>\n\t\t<value>" + getValue() +
                "</value>\n\t\t<realised>" +isRealised() +"</realised>\n\t\t<state>"+getState()+"</state>\n\t</feat>" ;
    }
    public boolean condition(int i,int j){
        if(i>=60)
            return true;
        return false;
    }

    @Override
    public void updatestate(int i) {

    }
}