package core;

import com.aspose.cells.*;
import java.util.Locale;

public class PlotGenerator {
        public static void create_plot(String repo_name) throws Exception {

        Workbook workbook = new Workbook(SaveResults.excelFilePath);

        // Obtain the reference of the first worksheet
        Worksheet worksheet = workbook.getWorksheets().get(repo_name);

        int numRows = worksheet.getCells().getLastDataRow(0) + 1;

        // Add a chart to the worksheet
        int chartIndex = worksheet.getCharts().add(ChartType.LINE, numRows+3, 4, numRows+36, 24);

        Chart chart = worksheet.getCharts().get(chartIndex);


        String xAxisRange = "'" + repo_name + "'!$A$4:$A$" + numRows;
        chart.getNSeries().add(xAxisRange, true);

        // Add data from column AR
        String yAxisRange1 = "'" + repo_name + "'!$AR$4:$AR$" + numRows;
        Series seriesY1 = chart.getNSeries().get(0); // Assuming the x-axis data is added at index 0
        seriesY1.getDataLabels().setShowValue(false);
        seriesY1.setXValues(xAxisRange);
        seriesY1.setValues(yAxisRange1);
        seriesY1.setName("Callback Coverage");


        // Add data from column G
        String yAxisRange2 = "'" + repo_name + "'!$G$4:$G$" + numRows;
        chart.getNSeries().add(yAxisRange2, true);
        Series seriesY2 = chart.getNSeries().get(1); // Assuming the new series is added at index 1
        seriesY2.getDataLabels().setShowValue(false);
        seriesY2.setValues(yAxisRange2);
        seriesY2.setName("Function Coverage");

        // Add data from column AS
        String yAxisRange3 = "'" + repo_name + "'!$AS$4:$AS$" + numRows;
        chart.getNSeries().add(yAxisRange3, true);
        Series seriesY3 = chart.getNSeries().get(2); // Assuming the new series is added at index 1
        seriesY3.getDataLabels().setShowValue(false);
        seriesY3.setValues(yAxisRange3);
        seriesY3.setName("Async Callback Coverage");

        // Add data from column AT
        String yAxisRange4 = "'" + repo_name + "'!$AT$4:$AT$" + numRows;
        chart.getNSeries().add(yAxisRange4, true);
        Series seriesY4 = chart.getNSeries().get(3); // Assuming the new series is added at index 1
        seriesY4.getDataLabels().setShowValue(false);
        seriesY4.setValues(yAxisRange4);
        seriesY4.setName("Event-Dep Callback Coverage");

        // Add data from column AU
        String yAxisRange5 = "'" + repo_name + "'!$AU$4:$AU$" + numRows;
        chart.getNSeries().add(yAxisRange5, true);
        Series seriesY5 = chart.getNSeries().get(4); // Assuming the new series is added at index 1
        seriesY5.getDataLabels().setShowValue(false);
        seriesY5.setValues(yAxisRange5);
        seriesY5.setName("Closure Coverage");

        // Add data from column AV
        String yAxisRange6 = "'" + repo_name + "'!$AV$4:$AV$" + numRows;
        chart.getNSeries().add(yAxisRange6, true);
        Series seriesY6 = chart.getNSeries().get(5); // Assuming the new series is added at index 1
        seriesY6.getDataLabels().setShowValue(false);
        seriesY6.setValues(yAxisRange6);
        seriesY6.setName("DOM Related Coverage");



        // Setting the title of a chart
        Title title = chart.getTitle();
        title.setText(repo_name.toUpperCase(Locale.ROOT) + " COVERAGE RESULTS");

        // Increase the font size of the title
        Font fontSetting = title.getFont();
        fontSetting.setSize(14); // Set the font size to 14 points


        // Setting the title of category axis of the chart
        Axis categoryAxis = chart.getCategoryAxis();
        title = categoryAxis.getTitle();
        title.setText("Version of the repository");

        // Setting the title of value axis of the chart
        Axis valueAxis = chart.getValueAxis();
        title = valueAxis.getTitle();
        title.setText("Percentage of code coverage");


        ChartArea chartArea = chart.getChartArea();
        chartArea.getArea().setForegroundColor(Color.getWhite()); // Set background color to white
        chart.getPlotArea().getArea().setForegroundColor(Color.getWhite());
        chart.getCategoryAxis().getMajorGridLines().setVisible(false); // Hide grid lines
        chart.getValueAxis().getMajorGridLines().setVisible(false);

        chart.getPlotArea().getBorder().setVisible(false);


        chart.getValueAxis().setMaxValue(100); // set max value to 100

        // Save the Excel file
        workbook.save(SaveResults.excelFilePath, SaveFormat.XLSX);

    }
}
