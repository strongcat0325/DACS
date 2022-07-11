package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.MethodBean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImproperCertificateValidation {
    public int isImproperCertificateValidation(ClassBean pClassBean) {
        int cnt = 0;
        Pattern pattern = Pattern.compile("onReceivedSslError.*\\{(.*)}", Pattern.DOTALL);
        Pattern pattern2 = Pattern.compile("\\{(.*)}", Pattern.DOTALL);
        Pattern pattern3 = Pattern.compile("\\s*|\t|\r|\n", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

        boolean flag = false;
        if (pClassBean.getTextContent().contains("implements HostnameVerifier")) {
            flag = true;
        }

        for (MethodBean methodBean : pClassBean.getMethods()) {
            Matcher matcher = pattern.matcher(methodBean.getTextContent());
            while (matcher.find()) {
                if (matcher.group(1).contains("proceed();")) {
                    cnt++;
                }
            }

            if (methodBean.getTextContent().contains("implements X509TrustManager") &&
                    (methodBean.getName().equals("checkClientTrusted") ||
                            methodBean.getName().equals("checkServerTrusted"))) {
                Matcher matcher2 = pattern2.matcher(methodBean.getTextContent());
                while (matcher2.find()) {
                    if (matcher2.group(1).length() == 0) {
                        cnt++;
                    }
                }
            }

            if (flag) {
                if (methodBean.getName().equals("verify")) {

                    Matcher matcher4 = pattern3.matcher(methodBean.getTextContent());
                    String temp = matcher4.replaceAll("");

                    Matcher matcher3 = pattern2.matcher(temp);
                    while (matcher3.find()) {
                        if (matcher3.group(1).equals("returntrue;")) {
                            cnt++;
                        }
                    }
                }
            }

            if (methodBean.getTextContent().contains("AllowAllHostnameVerifier")
                    || methodBean.getTextContent().contains("ALLOW_ALL_HOSTNAME_VERIFIER")) {
                cnt++;
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        Pattern pattern2 = Pattern.compile("\\{(.*)}", Pattern.DOTALL);
        Pattern pattern3 = Pattern.compile("\\s*|\t|\r|\n", Pattern.DOTALL);

        Matcher matcher4 = pattern3.matcher("public boolean verify(String arg0, SSLSession arg1) {\n" +
                "return true;\n" +
                "}");
        String temp = matcher4.replaceAll("");

        System.out.println(temp);

        Matcher matcher3 = pattern2.matcher(temp);
        while (matcher3.find()) {
            if (matcher3.group(1).equals("returntrue;")) {
            }
        }
    }

}
