package controller;

import java.security.MessageDigest;

public class SHAHashingController
{
    public String encode(String password)throws Exception
    {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    public boolean passwordMatch(String password1, String password2) throws Exception {


        // Assume from UI
        SHAHashingController encoder1 = new SHAHashingController();
        String hash1 = encoder1.encode(password1);

        // Assume the same present in db
        SHAHashingController encoder2 = new SHAHashingController();
        String hash2 = encoder2.encode(password2);

        if(hash1.equalsIgnoreCase(hash2))
            return true;
        else
            return false;
    }
}
