//package org.pdsd.server.http;
 
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
 
import org.json.simple.JSONObject;

 
public class Server {
 
        /**
         * O cerere de distribuire a unui mesaje este trimisa folosind o cerere de tip POST,
         * la adresa de mai jos.
         */
        public static String REQUEST_URL = "https://android.googleapis.com/gcm/send";
 
        /**
         * O cerere este compusa din header-ele HTTP si corpul JSON.
         * 
         * Acest corp trebuie sa contina minimum parametrul "registration_ids"
         * care identifica terminalele Android vizate ca destinatie. 
         */
    private static String GCM_ID = "744081677873";
    private static String API_KEY = "AIzaSyBH7xFpmFbYCIFXbqmD5n9Fy1JTH_WshLE";
 
    /**
     * @param args
     */
        public static void main(String[] args) {
 
                /**
                 * Folosim biblioteca Apache HTTP Components pentru a efectua cererea POST.
                 * 
                 * Aceasta se gaseste la: https://hc.apache.org/downloads.cgi
                 */
                HttpClient client = HttpClientBuilder.create().build();
 
            HttpPost request = new HttpPost(REQUEST_URL);
            HttpResponse response;
 
            /**
             * Trebuie setate header-ele:
             * 
             * Authorization:key=YOUR_API_KEY
             * Content-Type:YOUR_CHOSEN_ENCODING
             * 
             * Exemplul foloseste "plaintext" ca format al cererii.
             */
            request.setHeader("Authorization","key=" + API_KEY);
            request.setHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
 
            /**
             * Construim continutul cererii.
             * 
             * Parametrul registration_id va identifica destinatarul/destinatarii.
             * Parametrul data este folosit pentru transmisa efectiva de date.
             * 
             * Pentru lista completa a parametrilor: http://developer.android.com/google/gcm/server.html#params
             */
            List<NameValuePair> params = new ArrayList<NameValuePair>();
 
            params.add(new BasicNameValuePair("registration_id", GCM_ID));
            params.add(new BasicNameValuePair("data.greeting", "Hello world"));
 
            JSONObject obj=new JSONObject();
            obj.put("registration_id", GCM_ID);
            obj.put("data.greeting", "Hello world");
            System.out.print(obj);
            
            
            try {
            	if(true)
            		request.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
            	//else
            		//request.setEntity(new UrlEncodedFormEntity(obj, "utf-8"));
            		
                client.execute(request);
 
                //Get the response
                response = client.execute(request);
 
                int responseCode = response.getStatusLine().getStatusCode();      
                System.out.println("HTTP POST Status: " + Integer.toString(responseCode));
 
                 /*Checking response */
                 if(response!=null){
                     InputStream in = response.getEntity().getContent(); //Get the data in the entity
                     // IOUtils is part of Apache Commons IO library: http://commons.apache.org/proper/commons-io/download_io.cgi
                     System.out.println("HTTP POST Response: " + IOUtils.toString(in));
                 }
                //Print result
                System.out.println("Complete response: " + response.toString());
 
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
 
        }
}


//            