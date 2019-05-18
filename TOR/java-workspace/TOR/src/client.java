import java.io.BufferedReader;
import java.io.FileReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import org.json.simple.JSONObject;


public class client {

    static int N = 2;

    public static void main(String[] args) throws Exception
    {
        class node_data
        {
            InetAddress IP;
            int port;

            node_data(String IP, int port) throws UnknownHostException {
                this.IP = InetAddress.getByName(IP);
                this.port = port;
            }
        }

//        ArrayList<node_data> nodes = new ArrayList<node_data>();
//        nodes.add(new node_data("",501));
//        nodes.add(new node_data("",666));
//        nodes.add(new node_data("",759));
//        nodes.add(new node_data("",812));
//        nodes.add(new node_data("",1024));
//        Collections.shuffle(nodes);

        List<List<String>> nodes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("nodes.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                nodes.add(Arrays.asList(values));
            }
        }
        Collections.shuffle(nodes);



        Scanner sc = new Scanner(System.in);
        System.out.println("Podaj wiadomość:");
        String message = sc.nextLine();
        System.out.println("Podaj adresata:");
//        node_data target = nodes.get(Integer.parseInt(sc.nextLine()));
//
//        nodes = (ArrayList<node_data>) nodes.subList(0,N);
//        nodes.add(target);
//
//        node_data next = nodes.remove(0);

        JSONObject obj = new JSONObject();
        obj.put("forward",1);
        obj.put("nodes", nodes);
        obj.put("message", message);
        byte[] send_data = obj.toString().getBytes();

        DatagramSocket socket = new DatagramSocket();
        DatagramPacket sentPacket = new DatagramPacket(send_data, send_data.length);
//        sentPacket.setAddress(next.IP);
//        sentPacket.setPort(next.port);
        socket.send(sentPacket);
    }




}
