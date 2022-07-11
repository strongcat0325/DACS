package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.MethodBean;

public class InefficientDataFormatAndParserRule {

    public int isInefficientDataFormatAndParser(ClassBean pClass) {

        int cnt = 0;

        for (MethodBean methodBean : pClass.getMethods()) {
            if (methodBean.getTextContent().contains("DocumentBuilderFactory")
                    || methodBean.getTextContent().contains("DocumentBuilder")
                    || methodBean.getTextContent().contains("NodeList")) {
                cnt++;
            }
        }

        if(cnt == 0) {
            for (String importedResource : pClass.getImports()) {
                if (importedResource.equals("javax.xml.parsers.DocumentBuilder")) {
                    return 1;
                }
            }
        }

        return cnt;

    }
}
