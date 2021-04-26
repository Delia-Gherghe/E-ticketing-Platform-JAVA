package ro.unibuc.project.ioservices;

import ro.unibuc.project.clients.Client;
import ro.unibuc.project.common.Date;
import ro.unibuc.project.common.DateTime;
import ro.unibuc.project.common.Location;
import ro.unibuc.project.events.OnlineEvent;
import ro.unibuc.project.events.PhysicalEvent;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class Reader {
    private static Reader readerInstance = null;

    private Reader(){}

    public static Reader getInstance(){
        if (readerInstance == null){
            readerInstance = new Reader();
        }
        return readerInstance;
    }

    public List<Location> readLocationsCSV(){
        List<Location> locations = new ArrayList<>();

        try(RandomAccessFile in = new RandomAccessFile("locations.csv", "r")){
            String currentLine;
            while ((currentLine = in.readLine()) != null){

                String[] getAttributes = currentLine.split(",");
                Location newLocation = new Location(getAttributes[0], getAttributes[1], getAttributes[2], Integer.parseInt(getAttributes[3]));
                locations.add(newLocation);
            }

        } catch (IOException e){
            System.out.println(e.getMessage());
        }

        return locations;
    }

    public List<Client> readClientsCSV(){
        List<Client> clients = new ArrayList<>();

        try(RandomAccessFile in = new RandomAccessFile("clients.csv", "r")){
            String currentLine;
            while ((currentLine = in.readLine()) != null){

                String[] getAttributes = currentLine.split(",");
                Date birthday = new Date(Integer.parseInt(getAttributes[2]), Integer.parseInt(getAttributes[3]), Integer.parseInt(getAttributes[4]));
                Location address = new Location(getAttributes[5], getAttributes[6], getAttributes[7], Integer.parseInt(getAttributes[8]));
                Client newClient = new Client(getAttributes[0], getAttributes[1], birthday, address);
                clients.add(newClient);
            }

        } catch (IOException e){
            System.out.println(e.getMessage());
        }

        return clients;
    }

    public List<OnlineEvent> readOnlineEventsCSV(){
        List<OnlineEvent> onlineEvents = new ArrayList<>();

        try(RandomAccessFile in = new RandomAccessFile("onlineEvents.csv", "r")){
            String currentLine;
            while ((currentLine = in.readLine()) != null){

                String[] getAttributes = currentLine.split(",");
                DateTime date = new DateTime(Integer.parseInt(getAttributes[3]), Integer.parseInt(getAttributes[4]),
                        Integer.parseInt(getAttributes[5]), Integer.parseInt(getAttributes[6]),
                        Integer.parseInt(getAttributes[7]));
                OnlineEvent newOnlineEvent = new OnlineEvent(getAttributes[0], Integer.parseInt(getAttributes[1]),
                        getAttributes[2], date, Float.parseFloat(getAttributes[8]), getAttributes[9]);
                onlineEvents.add(newOnlineEvent);
            }

        } catch (IOException e){
            System.out.println(e.getMessage());
        }

        return onlineEvents;
    }

    public List<PhysicalEvent> readPhysicalEventsCSV(){
        List<PhysicalEvent> physicalEvents = new ArrayList<>();

        try(RandomAccessFile in = new RandomAccessFile("physicalEvents.csv", "r")){
            String currentLine;
            while ((currentLine = in.readLine()) != null){

                String[] getAttributes = currentLine.split(",");
                DateTime date = new DateTime(Integer.parseInt(getAttributes[3]), Integer.parseInt(getAttributes[4]),
                        Integer.parseInt(getAttributes[5]), Integer.parseInt(getAttributes[6]),
                        Integer.parseInt(getAttributes[7]));
                Location location = new Location(getAttributes[9], getAttributes[10], getAttributes[11], Integer.parseInt(getAttributes[12]));
                PhysicalEvent newPhysicalEvent = new PhysicalEvent(getAttributes[0], Integer.parseInt(getAttributes[1]),
                        getAttributes[2], date, Float.parseFloat(getAttributes[8]), location);
                physicalEvents.add(newPhysicalEvent);
            }

        } catch (IOException e){
            System.out.println(e.getMessage());
        }

        return physicalEvents;
    }

}
