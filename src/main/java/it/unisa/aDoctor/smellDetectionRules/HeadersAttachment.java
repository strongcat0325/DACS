package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.MethodBean;

import java.util.regex.Pattern;

public class HeadersAttachment {
    public int isHeadersAttachment(ClassBean classBean) {
        int cnt = 0;

        Pattern pattern = Pattern.compile(".addHeader");

        for (MethodBean methodBean : classBean.getMethods()) {

            String content = methodBean.getTextContent();

            if (content.contains("HttpClient")
                    && methodBean.getTextContent().contains("addHeaders")) {
                cnt++;
            }

            if (content.contains("HttpURLConnection") && (content.contains("addRequestProperty")
                    || content.contains("setRequestProperty"))) {
                cnt++;
            }

            if (content.contains("OKHttpClient") && content.contains("addHeader")) {
                cnt++;
            }
        }
        return cnt;
    }
}
