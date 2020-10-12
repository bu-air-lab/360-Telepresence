package turnSuggestions;
import java.net.*;
import java.io.*;

public class getState {

    public getState() {

      System.out.println("Reading file from server.");
      try {
        readServerFile();
      }
      catch(IOException e) {
        e.printStackTrace();
      }

  	}

    public static void main(String[] args) throws Exception {


      System.out.println("Main function.");
    }

    public String readServerFile() throws java.io.IOException
    {
      String inputLine = "";
      System.out.println("Reading file from server.");
      try{
          URL current_state_request = new URL("http://192.168.4.91/ServerFiles/telepresence/current_state.txt");
          BufferedReader in = new BufferedReader(
          new InputStreamReader(current_state_request.openStream()));

          // String inputLine;
          while ((inputLine = in.readLine()) != null)
          return inputLine;
          // System.out.println(inputLine);
          in.close();
          //more code goes here
      }catch(MalformedURLException ex){
        System.out.println("Error in the URL.");
      //do exception handling here
      }
      return inputLine;
    }
}
