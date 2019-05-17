package jsonTest;

import org.json.simple.JSONObject;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

class Test {

   public static void main(String[] args) throws UnknownHostException {
	
      JSONParser parser = new JSONParser();
      ArrayList<InetAddress> inetAddress = new ArrayList<InetAddress>();
	  ArrayList<Integer> ports = new ArrayList<Integer>();
      //String s = "[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";
	  JSONObject tmp = new JSONObject();
	  tmp.put("port", new Integer("9999"));
	  tmp.put("address", new Integer(123));
	  ArrayList<Integer> ints = new ArrayList<Integer>();
	  ints.add(993);
	  ints.add(994);
	  ints.add(995);
	  System.out.println(ints);
	  
	  tmp.put("addresses",  ints);
	  tmp.put("message", new String("Hello"));
	  
	  JSONArray a = new JSONArray();
	  a.add(new Boolean(true));
	  a.add(tmp);
	  
	  String s = a.toString();
	  System.out.println(s);
	  
      //String s = "[1,{\"port\":9999, \"address\":992, \"addresses\":[993,994,995], \"message\":\"Hello\"}]";
		
      try{
         Object obj = parser.parse(s);
         JSONArray array = (JSONArray)obj;
         
         System.out.println("The 2nd element of array");
         System.out.println(array.get(1));
         System.out.println();
         
         JSONObject obj2 = (JSONObject)array.get(1);
         System.out.println("Field \"Address\"");
         System.out.println(obj2.get("address"));
         
         ArrayList<Long> addressesList = new ArrayList<Long>();
         addressesList = (ArrayList<Long>)obj2.get("addresses");
         System.out.println(addressesList);
         System.out.println(addressesList.get(0));
         Long nextAddress = addressesList.remove(0);
         
         obj2.put("address", nextAddress);
         obj2.put("addresses", addressesList);
         array.set(0, new Boolean(false));
         array.set(1, obj2);
         Object json = (Object)array;
         
         System.out.println(json);
    	 
         
         
      }catch(ParseException pe) {
		
         System.out.println("position: " + pe.getPosition());
         System.out.println(pe);
      }
   }
}
