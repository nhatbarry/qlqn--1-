package dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JOptionPane;

import entity.Client;
import entity.ComHistory;
import entity.ComHistoryXML;
import entity.Computer;
import entity.ComputerXML;
import utils.*;
import view.mainFrame;

public class ComputerDao {
    private static final String COMPUTER_FILE_NAME = "computer.xml";
    private static final String COMPUTER_HISTORY_FILE_NAME = "computerHistory.xml";
    private List<Computer> listComputers;
    private List<ComHistory> history;
    private List<Client> listClients;

    public ComputerDao() {
        this.listComputers = readListComputers();
        this.history = readListHistoryComputers();
        if (history == null) {
            history = new ArrayList<ComHistory>();
        }
        if (listComputers == null) {
            listComputers = new ArrayList<Computer>();
        }
    }

    public void writeListComputer(List<Computer> computers) {
        ComputerXML computerXML = new ComputerXML();
        computerXML.setComputer(computers);
        FileUtils.writeXMLtoFile(COMPUTER_FILE_NAME, computerXML);
    }

    public List<Computer> readListComputers() {
        List<Computer> list = new ArrayList<Computer>();
        ComputerXML computerXML = (ComputerXML) FileUtils.readXMLFile(COMPUTER_FILE_NAME, ComputerXML.class);
        if (computerXML != null) {
            list = computerXML.getComputer();
        }
        return list;
    }

    public void writeListHistoryComputer(List<ComHistory> computers) {
        ComHistoryXML xml = new ComHistoryXML();
        xml.setList(computers);
        FileUtils.writeXMLtoFile(COMPUTER_HISTORY_FILE_NAME, xml);
    }

    public List<ComHistory> readListHistoryComputers() {
        List<ComHistory> list = new ArrayList<ComHistory>();
        ComHistoryXML xml = (ComHistoryXML) FileUtils.readXMLFile(COMPUTER_HISTORY_FILE_NAME, ComHistoryXML.class);
        if (xml != null) {
            list = xml.getList();
        }
        return list;
    }

    

    public List<ComHistory> getHistory() {
        return history;
    }

    public void setHistory(List<ComHistory> history) {
        this.history = history;
    }

    public List<Computer> getListComputers() {
        return listComputers;
    }

    public void setListComputers(List<Computer> listComputers) {
        this.listComputers = listComputers;
    }

    public void add(Computer computer) {
        int id = 1;
        if (listComputers != null && listComputers.size() > 0) {
            id = listComputers.size() + 1;
        }
        computer.setId(id);
        listComputers.add(computer);
        writeListComputer(listComputers);
    }

    public void edit(Computer computer) {
        int size = listComputers.size();
        for (int i = 0; i < size; i++) {
            if (listComputers.get(i).getId() == computer.getId()) {
                listComputers.get(i).setModel(computer.getModel());
                listComputers.get(i).setPrice(computer.getPrice());
                writeListComputer(listComputers);
                break;
            }
        }
    }

    public boolean delete(Computer computer) {
        boolean isFound = false;
        int size = listComputers.size();
        for (int i = 0; i < size; i++) {
            if (listComputers.get(i).getId() == computer.getId()) {
                computer = listComputers.get(i);
                isFound = true;
                break;
            }
        }
        if (isFound) {
            int id = 1;
            listComputers.remove(computer);
            size = listComputers.size();
            for (int i = 0; i < size; i++) {
                listComputers.get(i).setId(id++);
            }
            writeListComputer(listComputers);
            return true;
        }
        return false;
    }


    public boolean returnCom(Computer computer) {
        boolean isFound = false;
        boolean isFound2 = false;
        Client client = new Client();
        int size = listComputers.size();
        for (int i = 0; i < size; i++) {
            if (listComputers.get(i).getId() == computer.getId()) {
                computer = listComputers.get(i);
                isFound = true;
                break;
            }
        }
        ClientDao clientDao = new ClientDao();
        listClients = clientDao.readListClient();
        int size2 = listClients.size();
        for (int i = 0; i < size2; i++) {
            if (listClients.get(i).getId() == computer.getIdUser()) {
                client = listClients.get(i);
                isFound2 = true;
                break;
            }
        }
        if (isFound && isFound2) {
            long curTime = System.currentTimeMillis();
            long timeUse = curTime - computer.getStartedTime();
            double priceInMilis = computer.getPrice()/ 3600000;

            ComHistory his = new ComHistory();
            his.setId(computer.getId());
            his.setName(computer.getModel());
            his.setUser(computer.getUser());
            his.setStartTime(computer.getStartedTime());
            his.setEndTime(curTime);
            his.setUseTime(curTime - his.getStartTime());
            his.setFee(timeUse * priceInMilis);
            history.add(his);
            writeListHistoryComputer(history);
            
            computer.setIsUsed(false);
            computer.setUser("");
            computer.setStartedTime(0);
            computer.setLeftTime(0);
            client.setUsed(false);
            client.setBalance(client.getBalance() - timeUse * priceInMilis);
            if (client.getBalance() < 0) {
                client.setBalance(0);
            }
            clientDao.writeListClient(listClients);
            writeListComputer(listComputers);
            return true;
        }
        return false;
    }

    public boolean rentCom(Computer computer){
        boolean isFound = false;
        boolean isFound2 = false;
        Client client = new Client();
        int size = listComputers.size();
        mainFrame view = new mainFrame();
        ClientDao clientDao = new ClientDao();
        listClients = clientDao.readListClient();
        int id = view.showIDClient(listClients);

        for (int i = 0; i < size; i++) {
            if (listComputers.get(i).getId() == computer.getId()) {
                computer = listComputers.get(i);
                isFound = true;
                break;
            }
        }
        int size2 = listClients.size();
        for (int i = 0; i < size2; i++) {
            if (listClients.get(i).getId() == id) {
                client = listClients.get(i);
                isFound2 = true;
                if (client.getBalance() == 0) {
                    return false;
                }
                break;
            }
        }
        long timeLeft = (long) ((client.getBalance()) / (computer.getPrice()) * 3600000);

        if (isFound && isFound2) {
            computer.setUsed(true);
            computer.setUser(client.getName());
            computer.setIdUser(id);
            computer.setStartedTime(System.currentTimeMillis());
            computer.setLeftTime(timeLeft);
            client.setUsed(true);
            clientDao.writeListClient(listClients);
            writeListComputer(listComputers);
            return true;
        }
        return false;
    }

    public void sortComByID() {
        Collections.sort(listComputers, new Comparator<Computer>() {
            public int compare(Computer com1, Computer com2) {
                if (com1.getId() > com2.getId()) {
                    return 1;
                }
                return -1;
            }
        });
    }

    public void sortComByName() {
        Collections.sort(listComputers, new Comparator<Computer>() {
            public int compare(Computer com1, Computer com2) {
                return com1.getModel().compareTo(com2.getModel());
            }
        });
    }

    public void sortComByPrice() {
        Collections.sort(listComputers, new Comparator<Computer>() {
            public int compare(Computer com1, Computer com2) {
                if (com1.getPrice() > com2.getPrice()) {
                    return 1;
                }
                return -1;
            }
        });
    }

    public String idleCom(){
        int size = listComputers.size();
        String res = String.format("Danh sách máy trống:\n");
        boolean isFound = false;
        for(int i = 0; i < size; i++){
            if (!listComputers.get(i).isUsed()){
                res += String.format("- %s tại phòng máy %s\n", listComputers.get(i).getModel(), listComputers.get(i).getRoom());
                isFound = true;
            }
        }
        if (!isFound) {
            return "Không có máy trống";
        }
        return res;
    }

    public String usingCom(){
        int size = listComputers.size();
        String res = String.format("Danh sách máy đang được sử dụng:\n");
        boolean isFound = false;
        for(int i = 0; i < size; i++){
            if (listComputers.get(i).isUsed()){
                res += String.format("- %s tại phòng máy %s đang được %s sử dụng\n", listComputers.get(i).getModel(), listComputers.get(i).getRoom(), listComputers.get(i).getUser().trim());
                isFound = true;
            }
        }
        if (!isFound) {
            return "Không có máy đang được sử dụng";
        }
        return res;
    }
}
