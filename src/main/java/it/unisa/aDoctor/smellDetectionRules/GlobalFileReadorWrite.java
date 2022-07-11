package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.MethodBean;

public class GlobalFileReadorWrite {
    public int isGlobalFileReadorWrite(ClassBean classBean) {
        int cnt = 0;
        for (MethodBean methodBean : classBean.getMethods()) {
            String content = methodBean.getTextContent();
            if (content.contains("openFileOutput")) {
                if (content.contains("MODE_WORLD_READABLE") || content.contains("MODE_WORLD_WRITEABLE")) {
                    cnt++;
                }
            }
        }
        return cnt;
    }
}
