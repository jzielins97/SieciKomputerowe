import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class client {

    static int N = 2;
    static int BUFFER_SIZE = 256;

    public static void main(String[] args) throws Exception
    {
        //Wczytuje adresy z pliku
        final List<List<String>> nodes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("nodes.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                nodes.add(Arrays.asList(values));
            }
        }
        while (true){
        Scanner sc = new Scanner(System.in);
        System.out.println("Podaj wiadomość:");
        String message = sc.nextLine();
        //Tutaj można by dać listę potencjalnych adresów
        System.out.println("Podaj adresata:");
        List<String > target = nodes.remove(Integer.parseInt(sc.nextLine()));
        Collections.shuffle(nodes);

        List<List<String>> track = nodes.subList(0,N);
        track.add(target);

        List<String> next = track.remove(0);

//      Paczka z paczką z paczką...
        JSONObject obj = new JSONObject();
        obj.put("direction",true);
        obj.put("inetAddress", null);
        obj.put("port",null);
        obj.put("message", message);
        for(int i = nodes.size()-1; i>=0; i--)
        {
            JSONObject obj2 = new JSONObject();
            obj.put("direction",true);
            obj2.put("address",InetAddress.getByName((track.get(i)).get(0)));
            obj2.put("port",Integer.parseInt((track.get(i)).get(1)));
            obj2.put("message",obj);
            obj = obj2;
        }
        byte[] send_data = obj.toString().getBytes();

        DatagramSocket socket = null;
        socket = new DatagramSocket();
        DatagramPacket sentPacket = new DatagramPacket(send_data, send_data.length);
        sentPacket.setAddress(InetAddress.getByName(next.get(0)));
        sentPacket.setPort(Integer.parseInt(next.get(1)));
        socket.send(sentPacket);
//                System.out.println("Wiadomość wysłana");

        DatagramPacket receivedPacket = new DatagramPacket( new byte[BUFFER_SIZE], BUFFER_SIZE);
        socket.receive(receivedPacket);
        int length = receivedPacket.getLength();

        String messageReceived = new String(receivedPacket.getData(), 0, length, "utf8");
        JSONParser parser = new JSONParser();
        Object answer = parser.parse(messageReceived);
        JSONArray array = (JSONArray)answer;
        org.json.JSONObject obj2 = (org.json.JSONObject)array.get(1);
        System.out.print(obj2.get("message"));

    }}

}
