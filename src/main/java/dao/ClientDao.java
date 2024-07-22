package dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JOptionPane;

import entity.Client;
import entity.ClientXML;
import entity.Computer;
import utils.FileUtils;

public class ClientDao {
    private static final String CLIENT_FILE_NAME = "client.xml";
    private List<Client> listClients;

    public ClientDao() {
        this.listClients = readListClient();
        if (listClients == null) {
            listClients = new ArrayList<Client>();
        }
    }

    public void writeListClient(List<Client> clients) {
        ClientXML clientXML = new ClientXML();
        clientXML.setClient(clients);
        FileUtils.writeXMLtoFile(CLIENT_FILE_NAME, clientXML);
    }

    public List<Client> readListClient() {
        List<Client> list = new ArrayList<Client>();
        ClientXML clientXML = (ClientXML) FileUtils.readXMLFile(CLIENT_FILE_NAME, ClientXML.class);
        if (clientXML != null) {
            list = clientXML.getClient();
        }
        return list;
    }

    public void add(Client client) {
        int id = 1;
        if (listClients != null && listClients.size() > 0) {
            id = listClients.size() + 1;
        }
        client.setId(id);
        listClients.add(client);
        writeListClient(listClients);
    }

    public void edit(Client client) {
        int size = listClients.size();
        for (int i = 0; i < size; i++) {
            if (listClients.get(i).getId() == client.getId()) {
                listClients.get(i).setName(client.getName());
                listClients.get(i).setAge(client.getAge());
                listClients.get(i).setGender(client.getGender());
                listClients.get(i).setPhone(client.getPhone());
                writeListClient(listClients);
                break;
            }
        }
    }

    public boolean delete(Client client) {
        boolean isFound = false;
        int size = listClients.size();
        for (int i = 0; i < size; i++) {
            if (listClients.get(i).getId() == client.getId()) {
                client = listClients.get(i);
                isFound = true;
                break;
            }
        }
        if (isFound) {
            int id = 1;
            listClients.remove(client);
            size = listClients.size();
            for (int i = 0; i < size; i++) {
                listClients.get(i).setId(id++);
            }
            writeListClient(listClients);
            return true;
        }
        return false;
    }

    public void sortClientByName() {
        Collections.sort(listClients, new Comparator<Client>() {
            public int compare(Client client1, Client client2) {
                return client1.getName().compareTo(client2.getName());
            }
        });
    }

    public void sortClientByID() {
        Collections.sort(listClients, new Comparator<Client>() {
            public int compare(Client client1, Client client2) {
                if (client1.getId() > client2.getId()) {
                    return 1;
                }
                return -1;
            }
        });
    }

    public void sortClientByBalance() {
        Collections.sort(listClients, new Comparator<Client>() {
            public int compare(Client client1, Client client2) {
                if (client1.getBalance() > client2.getBalance()) {
                    return 1;
                }
                return -1;
            }
        });
    }

    public void charge(Client client) {
        int charge = Integer.parseInt(JOptionPane.showInputDialog("Nhập số tiền cần nạp (vnd):"));
        int size = listClients.size();
        for (int i = 0; i < size; i++) {
            if (listClients.get(i).getId() == client.getId()) {
                listClients.get(i).setBalance(listClients.get(i).getBalance() + charge + charge * listClients.get(i).getDiscount());
                listClients.get(i).setTotal(listClients.get(i).getTotal() + charge);
                if (listClients.get(i).getTotal() >= 100000 && listClients.get(i).getTotal() <= 200000) {
                    listClients.get(i).setDiscount(10);
                } else if (listClients.get(i).getTotal() >= 200000 && listClients.get(i).getTotal() <= 300000) {
                    listClients.get(i).setDiscount(20);
                } else if (listClients.get(i).getTotal() >= 300000 && listClients.get(i).getTotal() <= 400000) {
                    listClients.get(i).setDiscount(30);
                } else if (listClients.get(i).getTotal() >= 400000 && listClients.get(i).getTotal() <= 500000) {
                    listClients.get(i).setDiscount(40);
                } else if (listClients.get(i).getTotal() >= 500000) {
                    listClients.get(i).setDiscount(50);
                }
                writeListClient(listClients);
                break;
            }
        }

    }

    public void sortClientByTotal() {
        Collections.sort(listClients, new Comparator<Client>() {
            public int compare(Client client1, Client client2) {
                if (client1.getTotal() < client2.getTotal()) {
                    return 1;
                }
                return -1;
            }
        });
    }

    public List<Client> sortClientNam() {
        List<Client> searchResults = new ArrayList<Client>();

            for (Client client : listClients) {
                if (client.getGender().equals("Nam")) {
                    searchResults.add(client);
                } 
            }
            return searchResults;
    }

    public List<Client> sortClientNu() {
        List<Client> searchResults = new ArrayList<Client>();

            for (Client client : listClients) {
                if (client.getGender().equals("Nữ")) {
                    searchResults.add(client);
                } 
            }
            return searchResults;
    }

    public List<Client> sortClientKhac() {
        List<Client> searchResults = new ArrayList<Client>();

            for (Client client : listClients) {
                if (client.getGender().equals("Khác")) {
                    searchResults.add(client);
                } 
            }
            return searchResults;
    }

    public List<Client> getListClients() {
        return listClients;
    }

    public void setListClients(List<Client> listClients) {
        this.listClients = listClients;
    }
}
