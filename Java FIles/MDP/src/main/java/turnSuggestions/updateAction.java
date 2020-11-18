package turnSuggestions;
import java.net.*;
import java.io.*;

public class updateAction {

    public updateAction() {}

    public static void main(String[] args) throws Exception {


      System.out.println("Update action function.");
    }

    public void updateServerFile(String current_actions_in) throws java.io.IOException
    {
      String urlParameters = "action_string="+current_actions_in;
      URL url = new URL("http://10.201.11.74/telepresence/updateCurrentAction.php");
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

	/*
	public void newUpdateServerFile(String current_actions_in) throws java.io.IOException
	{
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost("http://10.201.11.74/telepresence/updateCurrentAction.php");

		// Request parameters and other properties.
		List<NameValuePair> params = new ArrayList<NameValuePair>(1);
		params.add(new BasicNameValuePair("action_string", current_actions_in));
		httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

		//Execute and get the response.
		HttpResponse response = httpclient.execute(httppost);
		
		HttpEntity entity = response.getEntity();

		if (entity != null) {
			try (InputStream instream = entity.getContent()) {
				// do something useful
			}
		}
		
	}
	*/
}
