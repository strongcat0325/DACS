package it.unisa.aDoctor.smellDetectionRules;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnconstrainedInterComponentCommunication {

    public int isUnconstrainedInterComponentCommunication(File androidManifest) throws IOException {
        if (androidManifest == null) {
            return 0;
        }
        Pattern regex = Pattern.compile("(.*)Activity|Service|ContentProvider|BroadcastReceiver:exported(\\s*)=(\\s*)\"true\"", Pattern.MULTILINE);
        Pattern regex2 = Pattern.compile("(.*)<intent-filter>(.*)</intent-filter>", Pattern.DOTALL);

        LineIterator iter = FileUtils.lineIterator(androidManifest);
        if(FileUtils.readFileToString(androidManifest).contains("uses-permission android:name")) {
            return 0;
        }

        while (iter.hasNext()) {
            String row = iter.next();
            if(row.contains("android:exported=\"true\"")) {
                return 1;
            } else if(row.contains("<intent-filer>") && !row.contains("android:exported=\"false\"")) {
                return 1;
            }
//            Matcher regexMatcher = regex.matcher(row);
//            Matcher regexMatcher2 = regex2.matcher(row);
//
//            if (regexMatcher.find()) {
//                return 1;
//            }
//            if (regexMatcher2.find()) {
//                if (regexMatcher2.group(1).length() == 0) {
//                    return 1;
//                }
//            }
        }
        return 0;
    }

    public static void main(String[] args) {
        Pattern regex = Pattern.compile("(.*)Activity|Service|ContentProvider|BroadcastReceiver:exported(\\s*)=(\\s*)\"true\"", Pattern.MULTILINE);
        Matcher matcher = regex.matcher("Service:exported=true");
        if (matcher.find()) {
            System.out.println("here");
        }

        Pattern regex2 = Pattern.compile("(.*)<intent-filter>(.*)</intent-filter>", Pattern.DOTALL);
        Matcher matcher2 = regex2.matcher("<intent-filter> <action\n" +
                "android:name=\"com.test.action\"></action> </intent-filter>");

        if (matcher2.find()) {
            System.out.println("here1");
            if (matcher2.group(1).length() == 0) {
                System.out.println("here2");
            }
        }

    }
}
