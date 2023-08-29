package core;

import java.io.*;

import java.util.*;

import org.apache.poi.hssf.record.HCenterRecord;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.plot.XYPlot;
import org.threeten.bp.LocalDate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class SaveResults {
    /**
     * Java class that writes results on excel file.
     */

    public static String excelFilePath = "C:/Users/Mauro/Desktop/Universita/Tesi/results.xlsx";   //path to the excel results file

    /**
     * Functions that writes results on the excel file.
     * @param subject_name name of the repository whose data are saved.
     * @param subject_url url of the repository whose data are saved.
     * @param data data to be written on file
     */

    public static void WriteResultToExcel_GetRepoStat (String subject_name, String subject_url, String version, String[] data) {

        try {
            FileInputStream fileInputStream = new FileInputStream(new File(excelFilePath));
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            FileOutputStream fileOut = new FileOutputStream(excelFilePath);

            // get excel sheet or create a new one
            Sheet sheet = workbook.getSheet(subject_name);
            if (sheet == null) {
                sheet = workbook.createSheet(subject_name);
                write_column_titles(sheet, subject_name);
            }
            //System.out.println(sheet.getLastRowNum());

            boolean foundSubject = false;

            for (int i = 0; i <= sheet.getLastRowNum(); i++) {   // counter starts from 3 because the first 3 lines of the excel file should not be modified
                Row row = sheet.getRow(i);

                if (row != null) {
                    Cell cell = row.getCell(0);
                    if (cell != null && cell.getStringCellValue().equals(version)) {


                                Cell urlCell = row.getCell(1);
                                if (urlCell == null) {
                                    urlCell = row.createCell(1);
                                }
                                urlCell.setCellValue(subject_url);


                                Cell watchersCell = row.getCell(21);
                                if (watchersCell == null) {
                                    watchersCell = row.createCell(21);
                                }
                                watchersCell.setCellValue(data[1]);

                                Cell starsCell = row.getCell(22);
                                if (starsCell == null) {
                                    starsCell = row.createCell(22);
                                }
                                starsCell.setCellValue(data[0]);

                                Cell commitsCell = row.getCell(24);
                                if (commitsCell == null) {
                                    commitsCell = row.createCell(24);
                                }
                                commitsCell.setCellValue(data[7]);

                                Cell forksCell = row.getCell(23);
                                if (forksCell == null) {
                                    forksCell = row.createCell(23);
                                }
                                forksCell.setCellValue(data[2]);

                                Cell branchesCell = row.getCell(25);
                                if (branchesCell == null) {
                                    branchesCell = row.createCell(25);
                                }
                                branchesCell.setCellValue(data[5]);

                                Cell releasesCell = row.getCell(26);
                                if (releasesCell == null) {
                                    releasesCell = row.createCell(26);
                                }
                                releasesCell.setCellValue(data[3]);

                                Cell usedByCell = row.getCell(27);
                                if (usedByCell == null) {
                                    usedByCell = row.createCell(27);
                                }
                                usedByCell.setCellValue(data[4]);

                                Cell contributorsCell = row.getCell(28);
                                if (contributorsCell == null) {
                                    contributorsCell = row.createCell(28);
                                }
                                contributorsCell.setCellValue(data[8]);



                        foundSubject = true;
                    }
                }
            }

            if (!foundSubject) {
                Row newRow = sheet.createRow(sheet.getLastRowNum() + 1);


                        Cell nameCell = newRow.createCell(0);
                        nameCell.setCellValue(version);

                        Cell urlCell = newRow.createCell(1);
                        urlCell.setCellValue(subject_url);

                        Cell watchersCell = newRow.createCell(21);
                        watchersCell.setCellValue(data[1]);

                        Cell starsCell = newRow.createCell(22);
                        starsCell.setCellValue(data[0]);

                        Cell forksCell = newRow.createCell(23);
                        forksCell.setCellValue(data[2]);

                        Cell commitsCell = newRow.createCell(24);
                        commitsCell.setCellValue(data[7]);

                        Cell branchCell = newRow.createCell(25);
                        branchCell.setCellValue(data[5]);

                        Cell releasesCell = newRow.createCell(26);
                        releasesCell.setCellValue(data[3]);

                        Cell usedByCell = newRow.createCell(27);
                        usedByCell.setCellValue(data[4]);

                        Cell contributorsCell = newRow.createCell(28);
                        contributorsCell.setCellValue(data[8]);


            }


            workbook.write(fileOut);
            fileInputStream.close();
            fileOut.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void WriteResultToExcel_TestCodePropertyAnalyzer ( String subject_name, String subject_url, String version, double[] data) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(excelFilePath));
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            FileOutputStream fileOut = new FileOutputStream(excelFilePath);

            // get excel sheet or create a new one
            Sheet sheet = workbook.getSheet(subject_name);
            if (sheet == null) {
                sheet = workbook.createSheet(subject_name);
                write_column_titles(sheet, subject_name);
            }
            //System.out.println(sheet.getLastRowNum());

            boolean foundSubject = false;


            for (int i = 3; i <= sheet.getLastRowNum(); i++) {   // counter starts from 3 because the first 3 lines of the excel file should not be modified
                Row row = sheet.getRow(i);

                if (row != null) {
                    Cell cell = row.getCell(0);
                    if (cell != null && cell.getStringCellValue().equals(version)) {
                        Cell numTestCell = row.getCell(11);
                        if (numTestCell == null) {
                            numTestCell = row.createCell(11);
                        }
                        numTestCell.setCellValue(data[0]);

                        Cell numAsyncTCell = row.getCell(12);
                        if (numAsyncTCell == null) {
                            numAsyncTCell = row.createCell(12);
                        }
                        numAsyncTCell.setCellValue(data[1]);

                        Cell numAssertionCell = row.getCell(14);
                        if (numAssertionCell == null) {
                            numAssertionCell = row.createCell(14);
                        }
                        numAssertionCell.setCellValue(data[2]);

                        Cell numFunCallTCell = row.getCell(16);
                        if (numFunCallTCell == null) {
                            numFunCallTCell = row.createCell(16);
                        }
                        numFunCallTCell.setCellValue(data[3]);

                        Cell maxFunCallCell = row.getCell(17);
                        if (maxFunCallCell == null) {
                            maxFunCallCell = row.createCell(17);
                        }
                        maxFunCallCell.setCellValue(data[4]);

                        Cell aveFunCallCell = row.getCell(18);
                        if (aveFunCallCell == null) {
                            aveFunCallCell = row.createCell(18);
                        }
                        aveFunCallCell.setCellValue(data[5]);

                        Cell numTriggerCell = row.getCell(19);
                        if (numTriggerCell == null) {
                            numTriggerCell = row.createCell(19);
                        }
                        numTriggerCell.setCellValue(data[6]);

                        foundSubject = true;
                    }
                }
            }

            if (!foundSubject) {
                Row newRow = sheet.createRow(sheet.getLastRowNum() + 1);

                Cell nameCell = newRow.createCell(0);
                nameCell.setCellValue(version);

                Cell urlCell = newRow.createCell(1);
                urlCell.setCellValue(subject_url);

                Cell numTestCell = newRow.createCell(11);
                numTestCell.setCellValue(data[0]);

                Cell numAsyncTCell = newRow.createCell(12);
                numAsyncTCell.setCellValue(data[1]);

                Cell numAssertionCell = newRow.createCell(14);
                numAssertionCell.setCellValue(data[2]);

                Cell numFunCallTCell = newRow.createCell(16);
                numFunCallTCell.setCellValue(data[3]);

                Cell maxFunCallCell = newRow.createCell(17);
                maxFunCallCell.setCellValue(data[4]);

                Cell aveFunCallCell = newRow.createCell(18);
                aveFunCallCell.setCellValue(data[5]);

                Cell numTriggerCell = newRow.createCell(19);
                numTriggerCell.setCellValue(data[6]);
            }

            workbook.write(fileOut);
            fileInputStream.close();
            fileOut.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void WriteResultToExcel_ProductionCodeCoverageAnalyzer ( String subject_name, String subject_url, String version, double[] data) {
            try {
                FileInputStream fileInputStream = new FileInputStream(new File(excelFilePath));
                Workbook workbook = WorkbookFactory.create(fileInputStream);
                FileOutputStream fileOut = new FileOutputStream(excelFilePath);

                // get excel sheet or create a new one
                Sheet sheet = workbook.getSheet(subject_name);
                if (sheet == null) {
                    sheet = workbook.createSheet(subject_name);
                    write_column_titles(sheet, subject_name);
                }
                //System.out.println(sheet.getLastRowNum());

                boolean foundSubject = false;


                for (int i = 3; i <= sheet.getLastRowNum(); i++) {   // counter starts from 3 because the first 3 lines of the excel file should not be modified
                    Row row = sheet.getRow(i);

                    if (row != null) {
                        Cell cell = row.getCell(0);
                        if (cell != null && cell.getStringCellValue().equals(version)) {
                            Cell totCovFunCell = row.getCell(31);
                            if (totCovFunCell == null) {
                                totCovFunCell = row.createCell(31);
                            }
                            totCovFunCell.setCellValue(data[0]);

                            Cell totMissFunCell = row.getCell(32);
                            if (totMissFunCell == null) {
                                totMissFunCell = row.createCell(32);
                            }
                            totMissFunCell.setCellValue(data[1]);

                            Cell covCallbackCell = row.getCell(33);
                            if (covCallbackCell == null) {
                                covCallbackCell = row.createCell(33);
                            }
                            covCallbackCell.setCellValue(data[2]);

                            Cell missCallbackCell = row.getCell(34);
                            if (missCallbackCell == null) {
                                missCallbackCell = row.createCell(34);
                            }
                            missCallbackCell.setCellValue(data[3]);

                            Cell covAsyncCallbackCell = row.getCell(35);
                            if (covAsyncCallbackCell == null) {
                                covAsyncCallbackCell = row.createCell(35);
                            }
                            covAsyncCallbackCell.setCellValue(data[4]);

                            Cell missAsyncCallbackCell = row.getCell(36);
                            if (missAsyncCallbackCell == null) {
                                missAsyncCallbackCell = row.createCell(36);
                            }
                            missAsyncCallbackCell.setCellValue(data[5]);

                            Cell covEvCallbackCell = row.getCell(37);
                            if (covEvCallbackCell == null) {
                                covEvCallbackCell = row.createCell(37);
                            }
                            covEvCallbackCell.setCellValue(data[6]);

                            Cell missEvCallbackCell = row.getCell(38);
                            if (missEvCallbackCell == null) {
                                missEvCallbackCell = row.createCell(38);
                            }
                            missEvCallbackCell.setCellValue(data[7]);

                            Cell covClosureCell = row.getCell(39);
                            if (covClosureCell == null) {
                                covClosureCell = row.createCell(39);
                            }
                            covClosureCell.setCellValue(data[8]);

                            Cell missCovClosureCell = row.getCell(40);
                            if (missCovClosureCell == null) {
                                missCovClosureCell = row.createCell(40);
                            }
                            missCovClosureCell.setCellValue(data[9]);

                            Cell covDOMCell = row.getCell(41);
                            if (covDOMCell == null) {
                                covDOMCell = row.createCell(41);
                            }
                            covDOMCell.setCellValue(data[10]);

                            Cell missDOMCell = row.getCell(42);
                            if (missDOMCell == null) {
                                missDOMCell = row.createCell(42);
                            }
                            missDOMCell.setCellValue(data[11]);

                            Cell funCovCell = row.getCell(6);
                            if (funCovCell == null) {
                                funCovCell = row.createCell(6);
                            }
                            funCovCell.setCellValue(data[14]);

                            Cell callbackCovCell = row.getCell(43);
                            if (callbackCovCell == null) {
                                callbackCovCell = row.createCell(43);
                            }
                            callbackCovCell.setCellValue(data[15]);

                            Cell asyncCallbackCovCell = row.getCell(44);
                            if (asyncCallbackCovCell == null) {
                                asyncCallbackCovCell = row.createCell(44);
                            }
                            asyncCallbackCovCell.setCellValue(data[16]);

                            Cell evCallbackCov = row.getCell(45);
                            if (evCallbackCov == null) {
                                evCallbackCov = row.createCell(45);
                            }
                            evCallbackCov.setCellValue(data[17]);

                            Cell closureCovCell = row.getCell(46);
                            if (closureCovCell == null) {
                                closureCovCell = row.createCell(46);
                            }
                            closureCovCell.setCellValue(data[18]);

                            Cell domCovCell = row.getCell(47);
                            if (domCovCell == null) {
                                domCovCell = row.createCell(47);
                            }
                            domCovCell.setCellValue(data[19]);

                            Cell uncovStatementCell = row.getCell(48);
                            if (uncovStatementCell == null) {
                                uncovStatementCell = row.createCell(48);
                            }
                            uncovStatementCell.setCellValue(data[13]);

                            foundSubject = true;
                        }
                    }
                }

                if (!foundSubject) {
                    Row newRow = sheet.createRow(sheet.getLastRowNum() + 1);

                    Cell nameCell = newRow.createCell(0);
                    nameCell.setCellValue(version);

                    Cell urlCell = newRow.createCell(1);
                    urlCell.setCellValue(subject_url);

                    Cell totCovFunCell = newRow.createCell(31);
                    totCovFunCell.setCellValue(data[0]);


                    Cell totMissFunCell = newRow.createCell(32);
                    totMissFunCell.setCellValue(data[1]);


                    Cell covCallbackCell = newRow.createCell(33);
                    covCallbackCell.setCellValue(data[2]);


                    Cell missCallbackCell = newRow.createCell(34);
                    missCallbackCell.setCellValue(data[3]);


                    Cell covAsyncCallbackCell = newRow.createCell(35);
                    covAsyncCallbackCell.setCellValue(data[4]);


                    Cell missAsyncCallbackCell = newRow.createCell(36);
                    missAsyncCallbackCell.setCellValue(data[5]);


                    Cell covEvCallbackCell = newRow.createCell(37);
                    covEvCallbackCell.setCellValue(data[6]);


                    Cell missEvCallbackCell = newRow.createCell(38);
                    missEvCallbackCell.setCellValue(data[7]);


                    Cell covClosureCell = newRow.createCell(39);
                    covClosureCell.setCellValue(data[8]);

                    Cell missCovClosureCell = newRow.createCell(40);
                    missCovClosureCell.setCellValue(data[9]);


                    Cell covDOMCell = newRow.createCell(41);
                    covDOMCell.setCellValue(data[10]);

                    Cell missDOMCell = newRow.createCell(42);
                    missDOMCell.setCellValue(data[11]);

                    Cell funCovCell = newRow.createCell(6);
                    funCovCell.setCellValue(data[14]);


                    Cell callbackCovCell = newRow.createCell(43);
                    callbackCovCell.setCellValue(data[15]);

                    Cell asyncCallbackCovCell = newRow.createCell(44);
                    asyncCallbackCovCell.setCellValue(data[16]);


                    Cell evCallbackCov = newRow.createCell(45);
                    evCallbackCov.setCellValue(data[17]);

                    Cell closureCovCell = newRow.createCell(46);
                    closureCovCell.setCellValue(data[18]);


                    Cell domCovCell = newRow.createCell(47);
                    domCovCell.setCellValue(data[19]);

                    Cell uncovStatementCell = newRow.createCell(48);
                    uncovStatementCell.setCellValue(data[13]);
                }



                workbook.write(fileOut);
                fileInputStream.close();
                fileOut.close();
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public static void write_column_titles(Sheet sheet, String repo_name) {
            // Column titles to be written
            String[] data = {
                    "Subject name", "URL", "Failure note", "Notes", "Statement coverage", "Branch coverage", "Function coverage",
                    "# JS Files", "JS SLOC", "Test SLOC", "Test code ratio", "Num tests", "Num async tests", "Async test ratio",
                    "Num assertions", "ave num assert per test", "Num fun calls", "Max fun calls", "Ave fun calls",
                    "Num trigger in test", "Has DOM fixture", "Watches", "Stars", "Forks", "Commits", "Branches", "Releases",
                    "Used by", "Contributors", "Note on test coverage report", "Total # functions", "Total covered function",
                    "Total missed function", "# covered callback", "# missed callback", "# covered async callback",
                    "# missed async callback", "# covered event-dep callback", "# missed event-dep callback",
                    "# covered closure", "# missed closure", "# covered DOM related", "# missed DOM related",
                    "Callback coverage", "Async callback coverage", "Event-dep callback coverage", "Closure coverage",
                    "DOM related coverage", "Uncovered Statement in Uncovered Function Ratio"
            };

            Font boldFont = sheet.getWorkbook().createFont();
            boldFont.setBold(true);

            CellStyle titleCellStyle = sheet.getWorkbook().createCellStyle();
            titleCellStyle.setWrapText(true);
            titleCellStyle.setFont(boldFont);

            CellStyle centerCellStyle = sheet.getWorkbook().createCellStyle();
            centerCellStyle.setFont(boldFont);
            centerCellStyle.setAlignment(HorizontalAlignment.CENTER);

            // Insert title in row 0
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(1);
            cell.setCellValue(repo_name.toUpperCase(Locale.ROOT));
            cell.setCellStyle(centerCellStyle);

            // Insert column titles in row 3
            row = sheet.createRow(2);
            for (int i = 0; i < data.length; i++) {
                cell = row.createCell(i);
                cell.setCellValue(data[i]);
                cell.setCellStyle(titleCellStyle);
                if (i == 1) {
                    sheet.setColumnWidth(i, 55 * 256); // Set width of column 2
                }else{
                    sheet.setColumnWidth(i, 11*256);
                }
            }
    }
}




