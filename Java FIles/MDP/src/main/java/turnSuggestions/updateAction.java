package turnSuggestions;
import java.net.*;
import java.io.*;

public class updateAction {

    public updateAction() {

      System.out.println("Reading file from server.");
      // try {
      //   updateServerFile();
      // }
      // catch(IOException e) {
      //   e.printStackTrace();
      // }

  	}

    public static void main(String[] args) throws Exception {


      System.out.println("Update action function.");
    }

    public void updateServerFile(String current_actions_in) throws java.io.IOException
    {
      String urlParameters = "action_string="+current_actions_in;
      URL url = new URL("http://192.168.4.91/ServerFiles/telepresence/updateCurrentAction.php");
      URLConnection conn = url.openConnection();

      conn.setDoOutput(true);

      OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

      writer.write(urlParameters);
      writer.flush();

      String line;
      BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

      while ((line = reader.readLine()) != null) {
          System.out.println(line);
      }
      writer.close();
      reader.close();
    }
}
