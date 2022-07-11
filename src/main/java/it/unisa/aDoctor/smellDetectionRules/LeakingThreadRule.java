package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import org.apache.commons.lang.StringUtils;

public class LeakingThreadRule {

    public int isLeakingThread(ClassBean pClass) {

        int cntRun = StringUtils.countMatches(pClass.getTextContent(), "run()");
        int cntStop = StringUtils.countMatches(pClass.getTextContent(), "stop()");

        return cntRun - cntStop > 0 ? cntRun - cntStop : 0;
    }
}
