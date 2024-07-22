package entity;

import java.util.List;
 
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement(name = "computersHistory")
@XmlAccessorType(XmlAccessType.FIELD)


public class ComHistoryXML {
    private List<ComHistory> list;

    public List<ComHistory> getList() {
        return list;
    }

    public void setList(List<ComHistory> list) {
        this.list = list;
    }
    
}
