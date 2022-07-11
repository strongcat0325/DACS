package it.unisa.aDoctor.smellDetectionRules;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataBackUpAny {

    public int isDataBackUpAny(File androidManifest) throws IOException {
//        DebuggableReleaseRule.isDebuggableRelease(DebuggableReleaseRule.java:15)
//        java.lang.NullPointerException
        if(androidManifest == null) {
            return 0;
        }
        Pattern regex = Pattern.compile("(.*)android:allowBackup(\\s*)=(\\s*)\"true\"", Pattern.MULTILINE);
        LineIterator iter = FileUtils.lineIterator(androidManifest);
        while (iter.hasNext()) {
            String row = iter.next();
            Matcher regexMatcher = regex.matcher(row);
            if (regexMatcher.find()) {
                return 1;
            }
        }
        return 0;
    }
}
