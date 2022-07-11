package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.MethodBean;

public class ExposedClipboard {
    public int isExposedClipboard(ClassBean classBean) {
        int cnt = 0;
        for (MethodBean methodBean : classBean.getMethods()) {
            String content = methodBean.getTextContent();
            if (methodBean.getBelongingClass() != null && methodBean.getBelongingClass().getName().equals("ClipboardManager")) {
                if (content.contains("setText") || content.contains("getText") ||
                        content.contains("setPrimaryClip") || content.contains("getPrimaryClip")) {
                    cnt++;
                }
            }
        }
        return cnt;
    }
}
