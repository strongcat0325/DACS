package it.unisa.aDoctor.smellDetectionRules;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;

public class UnacknowledgedDistribution {

    public int isUnacknowledgedDistribution(File androidManifest) throws IOException {
        if(androidManifest == null) {
            return 0;
        }
        LineIterator iter = FileUtils.lineIterator(androidManifest);
        while (iter.hasNext()) {
            String row = iter.next();
            if(row.contains("android.permission.INSTALL_PACKAGES")) {
                return 1;
            }
        }
        return 0;
    }
}
