package entity;

import java.util.List;
 
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement(name = "clients")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClientXML {
     
    private List<Client> client;
 
    public List<Client> getClient() {
        return client;
    }
 
    public void setClient(List<Client> client) {
        this.client = client;
    }
}