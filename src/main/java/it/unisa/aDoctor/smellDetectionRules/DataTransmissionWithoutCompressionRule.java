package it.unisa.aDoctor.smellDetectionRules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import it.unisa.aDoctor.beans.ClassBean;
import org.apache.commons.lang.StringUtils;

public class DataTransmissionWithoutCompressionRule {

    private final List<String> compressList;

    public DataTransmissionWithoutCompressionRule() throws IOException {

        compressList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader
                (DataTransmissionWithoutCompressionRule.class.getResourceAsStream("/compress.txt")))) {
            while (br.ready()) {
                compressList.add(br.readLine());
            }
        }
    }

    public int isDataTransmissionWithoutCompression(ClassBean pClassBean) throws IOException {

        if(pClassBean.getTextContent().contains("HttpPost") || pClassBean.getTextContent().contains("HttpResponse")) {
            if (pClassBean.getTextContent().contains("File ")) {
//            if (!pClassBean.getTextContent().contains("zip")) {
//                return true;
//            }
                if(hasCompress(pClassBean)) {
                    return 1;
                }
//                return StringUtils.countMatches(pClassBean.getTextContent(), "zip");
            }
        }

        return 0;
    }

    /**
     * Detect compress method in class
     * @param pClass
     * @return
     */
    public boolean hasCompress(ClassBean pClass) {
        for (String compress : compressList) {
            if(pClass.getTextContent().contains(compress)) {
                return true;
            }
        }
        return false;
    }
}
