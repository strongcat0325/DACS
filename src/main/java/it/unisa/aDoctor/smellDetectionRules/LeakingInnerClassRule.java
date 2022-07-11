package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;

public class LeakingInnerClassRule {

    public int isLeakingInnerClass(ClassBean pClass) {
        int cnt = 0;
        for (ClassBean inner : pClass.getInnerClasses()) {
            if (!inner.isStatic()) {
                cnt++;
            }
        }
        return cnt;
    }

}
