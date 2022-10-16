package ecom.udpm.vn.service;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class SendMailService {
    public void sendMail(String fullName, String phone, String address, String role, String username, String dob, String email){
        try {
            // Construct manually a JSON object in Java, for testing purposes an object with an object
            JSONObject data = new JSONObject();
            data.put("fullname", fullName);
            data.put("phone", phone);
            data.put("address", address);
            data.put("role", role);
            data.put("username", username);
            data.put("dob", dob);
            data.put("email", email);

            URL url = new URL("http://180.93.175.189:2509/sendMail/");
            HttpURLConnection httpConnection  = (HttpURLConnection) url.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.setRequestProperty("Accept", "application/json");

            DataOutputStream wr = new DataOutputStream(httpConnection.getOutputStream());
            wr.write(data.toString().getBytes());
            Integer responseCode = httpConnection.getResponseCode();

            BufferedReader bufferedReader;

            if (responseCode > 199 && responseCode < 300) {
                bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getErrorStream()));
            }

            // To receive the response
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            bufferedReader.close();

            // Prints the response
            System.out.println(content.toString());

        } catch (Exception e) {
            System.out.println("Error Message");
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }
}
