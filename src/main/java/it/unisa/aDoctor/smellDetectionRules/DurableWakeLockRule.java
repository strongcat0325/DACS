package it.unisa.aDoctor.smellDetectionRules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.MethodBean;

public class DurableWakeLockRule {

    public int isDurableWakeLock(ClassBean pClass) {

        Pattern regexAcquire = Pattern.compile("(.*)acquire(\\s*)()", Pattern.MULTILINE);
        Pattern regexRelease = Pattern.compile("(.*)release(\\s*)()", Pattern.MULTILINE);

        int cnt = 0;
        for (MethodBean method : pClass.getMethods()) {
            Matcher regexAcquireMatcher = regexAcquire.matcher(method.getTextContent());
            if (regexAcquireMatcher.find()) {
                Matcher regexReleaseMatcher = regexRelease.matcher(method.getTextContent());
                if(!regexReleaseMatcher.find()) {
                    cnt++;
                }
            }
        }
        return cnt;

    }

}
