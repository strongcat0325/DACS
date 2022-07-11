package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.MethodBean;

public class SDVisit {
    public int isSDVisit(ClassBean classBean) {
        int cnt = 0;
        for (MethodBean methodBean : classBean.getMethods()) {
            if (methodBean.getTextContent().contains("getExternalStorageDirectory")) {
                cnt++;
            }
        }
        return cnt;
    }
}
