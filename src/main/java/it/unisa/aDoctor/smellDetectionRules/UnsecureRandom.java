package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.MethodBean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnsecureRandom {
    public int isUnsecureRandom(ClassBean classBean) {
        int cnt = 0;

        Pattern pattern = Pattern.compile("SecureRandom.SecureRandom\\((.*)\\)");
        Pattern pattern2 = Pattern.compile("SecureRandom.setSeed\\((.*)\\)");

        boolean flag = false;
        String classBeanTextContent = classBean.getTextContent();
        if (classBeanTextContent.contains("java.security") || classBeanTextContent.contains("javax.crypto")
                || classBeanTextContent.contains("javax.security") || classBeanTextContent.contains("android.security")) {
            flag = true;
        }
        if (flag) {
            for (MethodBean methodBean : classBean.getMethods()) {
                String content = methodBean.getTextContent();
                Matcher matcher = pattern.matcher(content);
                Matcher matcher2 = pattern2.matcher(content);

                if (content.contains("Random")) {
                    cnt++;
                }

                if (matcher.find()) {
                    if (matcher.group(1).length() > 0) {
                        cnt++;
                    }
                }

                if (matcher2.find()) {
                    if (matcher2.group(1).length() > 0) {
                        cnt++;
                    }
                }
            }
        }

        return cnt;
    }

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("SecureRandom.SecureRandom\\((.*)\\)");
        Matcher matcher = pattern.matcher("SecureRandom.SecureRandom(byte[] seed)");
        if (matcher.find()) {
            System.out.println(matcher.group(1));
        }
    }
}
