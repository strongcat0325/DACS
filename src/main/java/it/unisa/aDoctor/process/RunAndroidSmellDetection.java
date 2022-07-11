package it.unisa.aDoctor.process;

import it.unisa.aDoctor.smellDetectionRules.*;
import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.PackageBean;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.eclipse.core.runtime.CoreException;

import java.io.FileWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang.StringUtils;

public class RunAndroidSmellDetection {

    private static final String NEW_LINE_SEPARATOR = "\n";
    public static String[] FILE_HEADER;

    // The folder contains the set of Android apps that need to be analyzed
    public static void main(String[] args) throws IOException, CoreException {

        SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
        System.out.println("Started at " + ft.format(new Date()));

        // Folder containing android apps to analyze
        File experimentDirectory = FileUtils.getFile(args[0]);
        File fileName = new File(args[1]);
        String smellsNeeded = args[2];

        FILE_HEADER = new String[StringUtils.countMatches(smellsNeeded, "1") + 1];

        //15
        DataTransmissionWithoutCompressionRule dataTransmissionWithoutCompressionRule = new DataTransmissionWithoutCompressionRule();
        DebuggableReleaseRule debbugableReleaseRule = new DebuggableReleaseRule();
        DurableWakeLockRule durableWakeLockRule = new DurableWakeLockRule();
        InefficientDataFormatAndParserRule inefficientDataFormatAndParserRule = new InefficientDataFormatAndParserRule();
        InefficientDataStructureRule inefficientDataStructureRule = new InefficientDataStructureRule();
        InefficientSQLQueryRule inefficientSQLQueryRule = new InefficientSQLQueryRule();
        InternalGetterSetterRule internaleGetterSetterRule = new InternalGetterSetterRule();
        LeakingInnerClassRule leakingInnerClassRule = new LeakingInnerClassRule();
        LeakingThreadRule leakingThreadRule = new LeakingThreadRule();
        MemberIgnoringMethodRule memberIgnoringMethodRule = new MemberIgnoringMethodRule();
        NoLowMemoryResolverRule noLowMemoryResolverRule = new NoLowMemoryResolverRule();
        PublicDataRule publicDataRule = new PublicDataRule();
        RigidAlarmManagerRule rigidAlarmManagerRule = new RigidAlarmManagerRule();
        SlowLoopRule slowLoopRule = new SlowLoopRule();
        UnclosedCloseableRule unclosedCloseableRule = new UnclosedCloseableRule();

        // 16
        BrokenWebViewSandbox brokenWebViewSandbox = new BrokenWebViewSandbox();
        ConfReadWrite confReadWrite = new ConfReadWrite();
        CustomSchemeChannel customSchemeChannel = new CustomSchemeChannel();
        DataBackUpAny dataBackUpAny = new DataBackUpAny();
        ExposedClipboard exposedClipboard = new ExposedClipboard();
        GlobalFileReadorWrite globalFileReadorWrite = new GlobalFileReadorWrite();
        HeadersAttachment headersAttachment = new HeadersAttachment();
        ImproperCertificateValidation improperCertificateValidation = new ImproperCertificateValidation();
        MaliciousUnzip maliciousUnzip = new MaliciousUnzip();
        SDVisit sdVisit = new SDVisit();
        UnacknowledgedDistribution unacknowledgedDistribution = new UnacknowledgedDistribution();
        UnconstrainedInterComponentCommunication unconstrainedInterComponentCommunication = new UnconstrainedInterComponentCommunication();
        UnsecureRandom unsecureRandom = new UnsecureRandom();
        WeakCryptoAlgorithm weakCryptoAlgorithm = new WeakCryptoAlgorithm();
        WebViewDomainNotStrict webViewDomainNotStrict = new WebViewDomainNotStrict();
        WebViewPlainSecret webViewPlainSecret = new WebViewPlainSecret();


        String[] smellsType = {"DTWC", "DR", "DW", "IDFP", "IDS", "ISQLQ", "IGS", "LIC", "LT", "MIM", "NLMR", "PD", "RAM", "SL", "UC",
                                "BWS", "CRW", "CSC", "DBA", "EC", "GFRW", "HA", "ICV", "MU", "SDV", "UD", "UICC", "UR", "WCA", "WDNS", "WPS"};

        FILE_HEADER[0] = "Class";

        int headerCounter = 1;

        for (int i = 0; i < smellsNeeded.length(); i++) {
            if (smellsNeeded.charAt(i) == '1') {
                FILE_HEADER[headerCounter] = smellsType[i];
                headerCounter++;
            }
        }

        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
        FileWriter fileWriter = new FileWriter(fileName);
        try (CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat)) {
            csvFilePrinter.printRecord((Object[]) FILE_HEADER);

            for (File project : experimentDirectory.listFiles()) {

                if (!project.isHidden()) {

                    // Method to convert a directory into a set of java packages.
                    ArrayList<PackageBean> packages = FolderToJavaProjectConverter.convert(project.getAbsolutePath());

                    for (PackageBean packageBean : packages) {

                        for (ClassBean classBean : packageBean.getClasses()) {

                            List record = new ArrayList();

                            System.out.println("-- Analyzing class: " + classBean.getBelongingPackage() + "." + classBean.getName());

                            record.add(classBean.getBelongingPackage() + "." + classBean.getName());

                            if (smellsNeeded.charAt(0) == '1') {
                                int c0 = dataTransmissionWithoutCompressionRule.isDataTransmissionWithoutCompression(classBean);
                                record.add(Integer.toString(c0));
                            }

                            // TODO null pointer exception 2
                            if (smellsNeeded.charAt(1) == '1') {
                                if (debbugableReleaseRule.isDebuggableRelease(RunAndroidSmellDetection.getAndroidManifest(project))) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }

                            if (smellsNeeded.charAt(2) == '1') {
                                int c2 = durableWakeLockRule.isDurableWakeLock(classBean);
                                record.add(Integer.toString(c2));
                            }

                            if (smellsNeeded.charAt(3) == '1') {
                                int c3 = inefficientDataFormatAndParserRule.isInefficientDataFormatAndParser(classBean);
                                record.add(Integer.toString(c3));
                            }

                            if (smellsNeeded.charAt(4) == '1') {
                                int c4 = inefficientDataStructureRule.isInefficientDataStructure(classBean);
                                record.add(Integer.toString(c4));
                            }

                            if (smellsNeeded.charAt(5) == '1') {
                                int c5 = inefficientSQLQueryRule.isInefficientSQLQuery(classBean);
                                record.add(Integer.toString(c5));
                            }

                            if (smellsNeeded.charAt(6) == '1') {
                                int c6 = internaleGetterSetterRule.isInternalGetterSetter(classBean);
                                record.add(Integer.toString(c6));
                            }

                            if (smellsNeeded.charAt(7) == '1') {
                                int c7 = leakingInnerClassRule.isLeakingInnerClass(classBean);
                                record.add(Integer.toString(c7));
                            }

                            if (smellsNeeded.charAt(8) == '1') {
                                int c8 = leakingThreadRule.isLeakingThread(classBean);
                                record.add(Integer.toString(c8));
                            }

                            if (smellsNeeded.charAt(9) == '1') {
                                int c9 = memberIgnoringMethodRule.isMemberIgnoringMethod(classBean);
                                record.add(Integer.toString(c9));
                            }

                            if (smellsNeeded.charAt(10) == '1') {
                                int c10 = noLowMemoryResolverRule.isNoLowMemoryResolver(classBean);
                                record.add(Integer.toString(c10));
                            }

                            if (smellsNeeded.charAt(11) == '1') {
                                int c11 = publicDataRule.isPublicData(classBean);
                                record.add(Integer.toString(c11));
                            }

                            if (smellsNeeded.charAt(12) == '1') {
                                int c12 = rigidAlarmManagerRule.isRigidAlarmManager(classBean);
                                record.add(Integer.toString(c12));
                            }

                            if (smellsNeeded.charAt(13) == '1') {
                                int c13 = slowLoopRule.isSlowLoop(classBean);
                                record.add(Integer.toString(c13));
                            }

                            if (smellsNeeded.charAt(14) == '1') {
                                int c14 = unclosedCloseableRule.isUnclosedCloseable(classBean);
                                record.add(Integer.toString(c14));
                            }

                            // add

                            if (smellsNeeded.charAt(15) == '1') {
                                record.add(Integer.toString(brokenWebViewSandbox.isBrokenWebViewSandbox(classBean)));
                            }

                            if (smellsNeeded.charAt(16) == '1') {
                                record.add(Integer.toString(confReadWrite.isConfReadWrite(classBean)));
                            }

                            if (smellsNeeded.charAt(17) == '1') {
                                record.add(Integer.toString(customSchemeChannel.isCustomSchemeChannel(RunAndroidSmellDetection.getAndroidManifest(project))));
                            }

                            if (smellsNeeded.charAt(18) == '1') {
                                record.add(Integer.toString(dataBackUpAny.isDataBackUpAny(RunAndroidSmellDetection.getAndroidManifest(project))));
                            }

                            if (smellsNeeded.charAt(19) == '1') {
                                record.add(Integer.toString(exposedClipboard.isExposedClipboard(classBean)));
                            }

                            if (smellsNeeded.charAt(20) == '1') {
                                record.add(Integer.toString(globalFileReadorWrite.isGlobalFileReadorWrite(classBean)));
                            }

                            if (smellsNeeded.charAt(21) == '1') {
                                record.add(Integer.toString(headersAttachment.isHeadersAttachment(classBean)));
                            }

                            if (smellsNeeded.charAt(22) == '1') {
                                record.add(Integer.toString(improperCertificateValidation.isImproperCertificateValidation(classBean)));
                            }

                            if (smellsNeeded.charAt(23) == '1') {
                                record.add(Integer.toString(maliciousUnzip.isMaliciousUnzip(classBean)));
                            }

                            if (smellsNeeded.charAt(24) == '1') {
                                record.add(Integer.toString(sdVisit.isSDVisit(classBean)));
                            }

                            if (smellsNeeded.charAt(25) == '1') {
                                record.add(Integer.toString(unacknowledgedDistribution.isUnacknowledgedDistribution(RunAndroidSmellDetection.getAndroidManifest(project))));
                            }

                            if (smellsNeeded.charAt(26) == '1') {
                                record.add(Integer.toString(unconstrainedInterComponentCommunication.isUnconstrainedInterComponentCommunication(RunAndroidSmellDetection.getAndroidManifest(project))));
                            }

                            if (smellsNeeded.charAt(27) == '1') {
                                record.add(Integer.toString(unsecureRandom.isUnsecureRandom(classBean)));
                            }

                            if (smellsNeeded.charAt(28) == '1') {
                                record.add(Integer.toString(weakCryptoAlgorithm.isWeakCryptoAlgorithm(classBean)));
                            }

                            if (smellsNeeded.charAt(29) == '1') {
                                record.add(Integer.toString(webViewDomainNotStrict.isWebViewDomainNotStrict(classBean)));
                            }

                            if (smellsNeeded.charAt(30) == '1') {
                                record.add(Integer.toString(webViewPlainSecret.isWebViewPlainSecret(classBean)));
                            }

                            csvFilePrinter.printRecord(record);
                        }
                    }
                }
            }
        }
        System.out.println("CSV file was created successfully!");
        System.out.println("Finished at " + ft.format(new Date()));
    }

    public static File getAndroidManifest(File dir) {
        File androidManifest = null;
        List<File> files = (List<File>) FileUtils.listFiles(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        for (File file : files) {
            if (file.getName().equals("AndroidManifest.xml")) {
                androidManifest = file;
            }
        }
        return androidManifest;
    }

}
