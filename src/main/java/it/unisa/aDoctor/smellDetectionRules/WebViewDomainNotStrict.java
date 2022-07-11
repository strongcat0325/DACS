package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.MethodBean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebViewDomainNotStrict {

    public int isWebViewDomainNotStrict(ClassBean classBean) {
        int cnt = 0;
        Pattern pattern = Pattern.compile("startsWith\\(\"file://\"\\).*\\{(.*)}", Pattern.DOTALL);
        for (MethodBean methodBean : classBean.getMethods()) {
            String content = methodBean.getTextContent();
            if (content.contains("setAllowFileAccess(true);")) {
                Matcher matcher = pattern.matcher(content);
                if (matcher.find()) {
                    if (!matcher.group(1).contains("setJavaScriptEnabled(false);")) {
                        cnt++;
                    }
                }
            }
            if (content.contains("setAllowFileAccessFromFileURLs(true);")) {
                cnt++;
            }
            if (content.contains("setAllowUniversalAccessFromFileURLs(true);")) {
                cnt++;
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("startsWith\\(\"file://\"\\).*\\{(.*)}", Pattern.DOTALL);
        Matcher matcher = pattern.matcher("if (url.startsWith(\"file://\") {\n" +
                "setJavaScriptEnabled(false);\n" +
                "} else {\n" +
                "setJavaScriptEnabled(true);\n" +
                "}");

        if (matcher.find()) {
            if (matcher.group(1).contains("setJavaScriptEnabled(true);")) {
                System.out.println("here");
            }
        }
    }
}
