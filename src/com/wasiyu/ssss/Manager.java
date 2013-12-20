package com.wasiyu.ssss;

import android.util.Base64;
import android.util.Log;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by ttpod on 13-12-18.
 */
public class Manager {
    private static final String ACCESS_KEY = "b5e1aeca-3092-40a4-b0d1-751d8b7de2c6";
    private static final String SECRET_KEY = "30eb4872-14da-4e74-975d-5f4883a1545e";

    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    public static String getSignature(String data,String key) throws Exception {

        // get an hmac_sha1 key from the raw key bytes
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);

        // get an hmac_sha1 Mac instance and initialize with the signing key
        Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
        mac.init(signingKey);

        // compute the hmac on input data bytes
        byte[] rawHmac = mac.doFinal(data.getBytes());

        return bytArrayToHex(rawHmac);
    }

    private static String bytArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder();
        for(byte b: a)
            sb.append(String.format("%02x", b&0xff));
        return sb.toString();
    }

    public void init() throws Exception {

        String tonce = ""+(System.currentTimeMillis() * 1000);

        String params = "tonce="+tonce.toString()+"&accesskey="+ACCESS_KEY+"&requestmethod=post&id=1&method=getAccountInfo&params=";

        String hash = getSignature(params, SECRET_KEY);

        String url = "https://api.btcchina.com/api_trade_v1.php";
        HttpsURLConnection conn = (HttpsURLConnection)new URL(url).openConnection();
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, null, new SecureRandom());
//        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//        HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());
        conn.setSSLSocketFactory(sc.getSocketFactory());


        String userpass = ACCESS_KEY + ":" + hash;
        String basicAuth = "Basic " + Base64.encodeToString(userpass.getBytes(), Base64.NO_WRAP);
        conn.setRequestProperty ("Authorization", basicAuth);

        //add reuqest header
        conn.setRequestProperty("Json-Rpc-Tonce", tonce.toString());

        // Send post request
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        String postdata = "{\"method\":\"getAccountInfo\",\"params\":[],\"id\":1}";
        byte[] bytes = postdata.getBytes();
        conn.getOutputStream().write(bytes, 0, bytes.length);
        conn.getOutputStream().flush();
        conn.getOutputStream().close();

        conn.connect();

        int responseCode = conn.getResponseCode();
        String result = "";
        InputStream is = conn.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            result += inputLine;
        }
        Log.d("TEST", "CODE: " + responseCode + "data: " + result);
    }

    private void GetHttps(){

        String https = " https://800wen.com/";
        try{

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new MyTrustManager()}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());
            HttpsURLConnection conn = (HttpsURLConnection)new URL(https).openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null)
                sb.append(line);

        }catch(Exception e){
            Log.e(this.getClass().getName(), e.getMessage());
        }
    }

    private class MyHostnameVerifier implements HostnameVerifier {

        @Override

        public boolean verify(String hostname, SSLSession session) {
            // TODO Auto-generated method stub

            return true;
        }

    }

    private class MyTrustManager implements X509TrustManager{

        @Override

        public void checkClientTrusted(X509Certificate[] chain, String authType)

                throws CertificateException {
            // TODO Auto-generated method stub


        }
        @Override

        public void checkServerTrusted(X509Certificate[] chain, String authType)

                throws CertificateException {
            // TODO Auto-generated method stub

        }
        @Override

        public X509Certificate[] getAcceptedIssuers() {

            // TODO Auto-generated method stub

            return null;
        }
    }


}
