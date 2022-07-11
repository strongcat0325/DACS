package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.MethodBean;

public class BrokenWebViewSandbox {
    public int isBrokenWebViewSandbox(ClassBean classBean) {
        int cnt = 0;
        boolean flag = false;
        for (MethodBean methodBean : classBean.getMethods()) {
            if (methodBean.getName().contains("prompt")) {
                flag = true;
            }
        }

        if (classBean.getName().contains("WebView")) {

            for (MethodBean methodBean : classBean.getMethods()) {
                if (methodBean.getName().equals("addJavascriptInterface")) {
                    if (!methodBean.getTextContent().contains("@JavascriptInterface") && !flag) {
                        cnt++;
                    }
                }
                String content = methodBean.getTextContent();
                if (content.contains("searchBoxJavaBridge") || content.contains("accessibility") ||
                        content.contains("accessibilityTraversal")) {
                    cnt++;
                }
            }
        }
        return cnt;
    }
}
