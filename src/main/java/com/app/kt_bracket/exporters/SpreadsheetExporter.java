package com.app.kt_bracket.exporters;

import com.app.kt_bracket.structure.Mat;
import javafx.stage.DirectoryChooser;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


@Component
public class SpreadsheetExporter
{
    public void exportToXls(Mat mat)
    {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        File currDir = directoryChooser.showDialog(null);

        if ( currDir == null ) return;
        if ( mat == null || mat.getBrackets().isEmpty() ) return;

        mat.getBrackets().stream()
            .forEach(bracket -> {
                Workbook workbook = new HSSFWorkbook();

                CellStyle initialStyle = workbook.createCellStyle();
                initialStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
                initialStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                initialStyle.setWrapText(true);

                CellStyle competitorStyle = workbook.createCellStyle();
                competitorStyle.setBorderTop(BorderStyle.THIN);
                competitorStyle.setBorderBottom(BorderStyle.THIN);
                competitorStyle.setBorderLeft(BorderStyle.THIN);
                competitorStyle.setBorderRight(BorderStyle.THIN);
                competitorStyle.setWrapText(true);

                Sheet sheet = workbook.createSheet("category");
                HSSFPatriarch patriarch = (HSSFPatriarch) sheet.createDrawingPatriarch();

                List<Row> rows = new ArrayList<>();
                int spreadsheetRowCount = (int) Math.pow(2, bracket.getColumns().size()) * 2;
                int spreadsheetColCount = bracket.getColumns().get(bracket.getColumns().size() - 1)
                        .getFights().get(0).getWinner().getCoordinates().getX() + 1;

                IntStream.iterate(0, rowNo -> ++rowNo)
                    .limit(spreadsheetRowCount)
                    .forEach(rowNo -> {
                        Row row = sheet.createRow(rowNo);
                        IntStream.iterate(0, colNo -> ++colNo)
                                .limit(spreadsheetColCount)
                                .forEach(colNo -> {
                                    Cell cell = row.createCell(colNo);
                                    cell.setCellStyle(initialStyle);
                                });
                        rows.add(row);
                    });

                bracket.getColumns().stream()
                    .forEach(bracketColumn -> {
                        bracketColumn.getFights().stream()
                            .forEach(fight -> {
                                Cell shiroCell = rows.get(fight.getShiro().getCoordinates().getY()).getCell(fight.getShiro().getCoordinates().getX());
                                shiroCell.setCellValue(fight.getShiro().getFullName());
                                shiroCell.setCellStyle(competitorStyle);
                                sheet.setColumnWidth(fight.getShiro().getCoordinates().getX(), 5000);

                                Cell akaCell = rows.get(fight.getWinner().getCoordinates().getY()).getCell(fight.getWinner().getCoordinates().getX());
                                akaCell.setCellValue(fight.getWinner().getFullName());
                                akaCell.setCellStyle(competitorStyle);

                                Cell winnerCell = rows.get(fight.getAka().getCoordinates().getY()).getCell(fight.getAka().getCoordinates().getX());
                                winnerCell.setCellValue(fight.getAka().getFullName());
                                winnerCell.setCellStyle(competitorStyle);
                                sheet.setColumnWidth(fight.getWinner().getCoordinates().getX(), 5000);

                                Cell numberCell = rows.get(fight.getNumberCoordinates().getY()).getCell(fight.getNumberCoordinates().getX());
                                numberCell.setCellValue(fight.getNumber());
                                numberCell.setCellStyle(competitorStyle);
                                sheet.setColumnWidth(fight.getNumberCoordinates().getX(), 1000);

                                HSSFClientAnchor shiroHorizontalLineanchor = new HSSFClientAnchor( 0, 127,
                                        512, 129,
                                        (short)fight.getDecoration().getShiroHorizontalLine().getX(),
                                        fight.getDecoration().getShiroHorizontalLine().getY(),
                                        (short) (fight.getDecoration().getShiroHorizontalLine().getX() +1),
                                        fight.getDecoration().getShiroHorizontalLine().getY()  );
                                //anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_DO_RESIZE);
                                HSSFSimpleShape shiroHorizontalLine = patriarch.createSimpleShape(shiroHorizontalLineanchor);
                                shiroHorizontalLine.setShapeType(HSSFSimpleShape.OBJECT_TYPE_LINE);
                                shiroHorizontalLine.setLineStyleColor(10, 10, 10);
                                shiroHorizontalLine.setFillColor(90, 10, 200);
                                shiroHorizontalLine.setLineWidth(HSSFShape.LINEWIDTH_ONE_PT);
                                shiroHorizontalLine.setLineStyle(HSSFShape.LINESTYLE_SOLID);

                                HSSFClientAnchor akaHorizontalLineAnchor = new HSSFClientAnchor( 0, 127,
                                        512, 129,
                                        (short)fight.getDecoration().getAkaHorizontalLine().getX(),
                                        fight.getDecoration().getAkaHorizontalLine().getY(),
                                        (short) (fight.getDecoration().getAkaHorizontalLine().getX() +1),
                                        fight.getDecoration().getAkaHorizontalLine().getY()  );
                                HSSFSimpleShape akaHorizontalLine = patriarch.createSimpleShape(akaHorizontalLineAnchor);
                                akaHorizontalLine.setShapeType(HSSFSimpleShape.OBJECT_TYPE_LINE);
                                akaHorizontalLine.setLineStyleColor(10, 10, 10);
                                akaHorizontalLine.setFillColor(90, 10, 200);
                                akaHorizontalLine.setLineWidth(HSSFShape.LINEWIDTH_ONE_PT);
                                akaHorizontalLine.setLineStyle(HSSFShape.LINESTYLE_SOLID);

                                // t-junction: winner horizontal line and vertical line
                                HSSFClientAnchor winnerHorizontalLineAnchor = new HSSFClientAnchor( 512, 127,
                                        0, 129,
                                        (short)fight.getDecoration().gettJunction().getX(),
                                        fight.getDecoration().gettJunction().getY(),
                                        (short) (fight.getDecoration().gettJunction().getX() +1),
                                        fight.getDecoration().gettJunction().getY()  );
                                HSSFSimpleShape winnerHorizontalLine = patriarch.createSimpleShape(winnerHorizontalLineAnchor);
                                winnerHorizontalLine.setShapeType(HSSFSimpleShape.OBJECT_TYPE_LINE);
                                winnerHorizontalLine.setLineStyleColor(10, 10, 10);
                                winnerHorizontalLine.setFillColor(90, 10, 200);
                                winnerHorizontalLine.setLineWidth(HSSFShape.LINEWIDTH_ONE_PT);
                                winnerHorizontalLine.setLineStyle(HSSFShape.LINESTYLE_SOLID);

                                HSSFClientAnchor winnerVerticalLineAnchor = new HSSFClientAnchor( 511, 127,
                                        513, 129,
                                        (short)(fight.getDecoration().getShiroHorizontalLine().getX() + 1 ),
                                        fight.getDecoration().getShiroHorizontalLine().getY(),
                                        (short) (fight.getDecoration().getAkaHorizontalLine().getX() + 1 ),
                                        fight.getDecoration().getAkaHorizontalLine().getY()  );
                                HSSFSimpleShape winnerVerticalLine = patriarch.createSimpleShape(winnerVerticalLineAnchor);
                                winnerVerticalLine.setShapeType(HSSFSimpleShape.OBJECT_TYPE_LINE);
                                winnerVerticalLine.setLineStyleColor(10, 10, 10);
                                winnerVerticalLine.setFillColor(90, 10, 200);
                                winnerVerticalLine.setLineWidth(HSSFShape.LINEWIDTH_ONE_PT);
                                winnerVerticalLine.setLineStyle(HSSFShape.LINESTYLE_SOLID);
                            });
                    });

                String path = currDir.getAbsolutePath();
                String fileLocation = path + "/" + bracket.getCategoryName().replace(".txt" , "") + ".xls";

                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(fileLocation);
                    workbook.write(outputStream);
                    workbook.close();
                    outputStream.close();
                }
                catch (FileNotFoundException e) { e.printStackTrace(); }
                catch (IOException e) { e.printStackTrace(); }
            });
    }
}
