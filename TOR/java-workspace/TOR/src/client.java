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
        //Wczytuje adresy z pliku
        List<List<String>> nodes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("nodes.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                nodes.add(Arrays.asList(values));
            }
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Podaj wiadomość:");
        String message = sc.nextLine();
        //Tutaj można by dać listę potencjalnych adresów
        System.out.println("Podaj adresata:");
        List<String > target = nodes.remove(Integer.parseInt(sc.nextLine()));
        Collections.shuffle(nodes);

        nodes = nodes.subList(0,N);
        nodes.add(target);

        List<String> next = nodes.remove(0);

        JSONObject obj = new JSONObject();
        obj.put("addresses", nodes);
        obj.put("message", message);
        byte[] send_data = obj.toString().getBytes();

        DatagramSocket socket = new DatagramSocket();
        DatagramPacket sentPacket = new DatagramPacket(send_data, send_data.length);
        sentPacket.setAddress(InetAddress.getByName(next.get(0)));
        sentPacket.setPort(Integer.parseInt(next.get(1)));
        socket.send(sentPacket);
    }

}
