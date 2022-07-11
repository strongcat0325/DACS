package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.MethodBean;

public class RigidAlarmManagerRule {

    public int isRigidAlarmManager(ClassBean pClass) {
        int cnt = 0;
        for (MethodBean method : pClass.getMethods()) {
            if (method.getTextContent().contains("AlarmManager")) {
                for (MethodBean call : method.getMethodCalls()) {
                    if (call.getName().equals("setRepeating")) {
                        cnt++;
                    }
                }
            }
        }
        return cnt;
    }

}
