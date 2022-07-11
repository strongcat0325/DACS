package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.MethodBean;

public class InefficientSQLQueryRule {

    public int isInefficientSQLQuery(ClassBean pClass) {

        int cnt = 0;
        for (String importString : pClass.getImports()) {
            if (importString.contains("java.sql")) {
                for (MethodBean method : pClass.getMethods()) {
                    if (method.getTextContent().contains("Connection ")
                            || method.getTextContent().contains("Statement ")
                            || method.getTextContent().contains("ResultSet ")) {
//                        return true;
                        cnt++;
                    }
                }
            }
        }
        return cnt;
    }
}
