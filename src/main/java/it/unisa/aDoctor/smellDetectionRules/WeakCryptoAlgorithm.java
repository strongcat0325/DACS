package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.MethodBean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeakCryptoAlgorithm {
    public int isWeakCryptoAlgorithm(ClassBean pClassBean) {
        int cnt = 0;

        Pattern pattern = Pattern.compile("initialize");

        for (MethodBean method : pClassBean.getMethods()) {
            String content = method.getTextContent();
            if (content.contains("MD2") || content.contains("MD4") || content.contains("MD5")
                    || content.contains("SHA-1") || content.contains("RIPEMD")) {
                cnt++;
            }

            if (content.contains("DES")) {
                cnt++;
            }

            if (content.contains("AES/ECB")) {
                cnt++;
            }

            if(content.contains("RSA")){
                if(!content.contains("RSA/ECB/OAEPWithSHA256AndMGF1Padding")){
                    cnt++;
                }
                Matcher matcher = pattern.matcher(content);
                while (matcher.find()) {
                    if (Integer.parseInt(matcher.group(2)) < 512) {
                        cnt++;
                    }
                }
            }
        }
        return cnt;
    }
}
