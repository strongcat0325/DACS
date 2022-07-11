package it.unisa.aDoctor.smellDetectionRules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.MethodBean;

public class InefficientDataStructureRule {

    public int isInefficientDataStructure(ClassBean pClass) {

        Pattern regex = Pattern.compile("(.*)HashMap<(\\s*)(Integer|Long)(\\s*),(\\s*)(.+)(\\s*)>", Pattern.MULTILINE);
        int cnt = 0;

        for (MethodBean methodBean : pClass.getMethods()) {
            Matcher regexMatcher = regex.matcher(methodBean.getTextContent());
            if(regexMatcher.find()) {
                cnt++;
            }
        }
        return cnt;
    }

}
