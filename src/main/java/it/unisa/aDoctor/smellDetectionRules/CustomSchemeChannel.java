package it.unisa.aDoctor.smellDetectionRules;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomSchemeChannel {
    public int isCustomSchemeChannel(File androiManifetsFile) throws IOException {
        Pattern pattern = Pattern.compile("<data android:scheme=\"(.*)\"");

        List<String> lines = FileUtils.readLines(androiManifetsFile, "utf-8");
        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                if (!matcher.group(1).isEmpty()) {
                    return 1;
                }
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("<data android:scheme=\"(.*)\"");
        Matcher matcher = pattern.matcher("<data android:scheme=\"sh\">");
        if (matcher.find()) {
            if (!matcher.group(1).isEmpty()) {
                System.out.println("here");
            }
        }
    }
}
