package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.MethodBean;

public class ConfReadWrite {
    public int isConfReadWrite(ClassBean classBean) {
        int cnt = 0;
        for (MethodBean methodBean : classBean.getMethods()) {
            String content = methodBean.getTextContent();
            if (content.contains("getSharedPreferences") &&
                    !content.contains("MODE_PRIVATE")) {
                cnt++;
            }
        }
        return cnt;
    }
}
