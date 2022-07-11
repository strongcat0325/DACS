package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.MethodBean;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebViewPlainSecret {
    public int isWebViewPlainSecret(ClassBean classBean) {
        int cnt = 0;
        Pattern pattern = Pattern.compile("setSavePassword\\((.*)\\)");

        for (MethodBean methodBean : classBean.getMethods()) {

            boolean flag = false;
            List<MethodBean> methods = (List<MethodBean>) methodBean.getMethodCalls();
            for (MethodBean method : methods) {
                if (method.getBelongingClass() != null && method.getBelongingClass().getName().equals("Webview")) {
                    flag = true;
                    break;
                }
            }

            if (flag) {
                String content = methodBean.getTextContent();
                Matcher matcher = pattern.matcher(content);
                if (matcher.find()) {
                    if (matcher.group(1).equals("true")) {
                        cnt++;
                    }
                }
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("setSavePassword\\((.*)\\)");
        Matcher matcher = pattern.matcher("setSavePassword(true)");
        if (matcher.find()) {
            System.out.println(matcher.group(1));
        }

    }
}
