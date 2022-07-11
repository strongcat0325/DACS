package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.MethodBean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaliciousUnzip {
    public int isMaliciousUnzip(ClassBean classBean) {
        int cnt = 0;

        Pattern pattern = Pattern.compile("contains\\(\"\\.\\./\"\\).*\\{(.*)}", Pattern.DOTALL);

        for (MethodBean methodBean : classBean.getMethods()) {
            String content = methodBean.getTextContent();
            Matcher matcher = pattern.matcher(content);
            if (content.contains("zip")) {
                if (matcher.find()) {
                    if (matcher.group(1).length() >= 0) {
                        continue;
                    }
                } else {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("contains\\(\"\\.\\./\"\\).*\\{(.*)}", Pattern.DOTALL);
        Matcher matcher = pattern.matcher("contains(\"../\") {\n" +
                "continue;\n" +
                "}");

        if (matcher.find()) {
            System.out.println("here");
            System.out.println(matcher.group(1));
        }
    }
}
