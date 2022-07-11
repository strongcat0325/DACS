package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.MethodBean;

public class PublicDataRule {

    public int isPublicData(ClassBean pClass) {

        int cnt = 0;
        for (MethodBean method : pClass.getMethods()) {
            if (method.getTextContent().contains("Context.MODE_WORLD_READABLE")) {
                cnt++;
            } else if (method.getTextContent().contains("Context.MODE_WORLD_WRITEABLE")) {
                cnt++;
            }
        }
        return cnt;
    }

}
