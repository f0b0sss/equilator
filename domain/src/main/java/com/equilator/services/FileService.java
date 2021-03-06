package com.equilator.services;

import com.equilator.DAO.DefaultData;
import com.equilator.models.UploadForm;
import com.equilator.models.calculator.GameInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FileService {
    private static Logger logger = LogManager.getLogger(FileService.class);

    @Autowired
    private GameInfo gameInfo;
    @Autowired
    private DefaultData defaultData;
    @Autowired
    private ServletContext servletContext;

    private void createResult(String fileName) throws IOException {
        File file = new File("classpath:" + fileName + ".txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            logger.error("Creating new file");
            e.printStackTrace();
        }

        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            logger.error("Creating new stream to write in file");
            e.printStackTrace();
        }

        logger.debug("Start write export data");
        writer.write("rangePlayer1=" + defaultData.getCalculatorMainTables().get(0).getRangePlayer1() + "\n");
        writer.write("rangePlayer2=" + defaultData.getCalculatorMainTables().get(0).getRangePlayer2() + "\n");
        writer.write("board=" + defaultData.getCalculatorMainTables().get(0).getBoard() + "\n");
        writer.write("equityPlayer1=" + defaultData.getCalculatorMainTables().get(0).getEquityPlayer1() + "\n");
        writer.write("equityPlayer2=" + defaultData.getCalculatorMainTables().get(0).getEquityPlayer2() + "\n");
        writer.write("equityByRangePlayer1=" + gameInfo.getEquityByRangeP1() + "\n");
        writer.write("equityByRangePlayer2=" + gameInfo.getEquityByRangeP2() + "\n");
        writer.write("equityByCardPlayer1=" + gameInfo.getEquityByCardP1() + "\n");
        writer.write("equityByCardPlayer2=" + gameInfo.getEquityByCardP2() + "\n");

        writer.flush();
        writer.close();

        logger.debug("Close write stream");
    }

    public ResponseEntity<InputStreamResource> safe(String fileName) {
        try {
            logger.info("Start create file with export data");
            createResult(fileName);
        } catch (IOException e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
        }

        MediaType mediaType = MediaType.parseMediaType(servletContext.getMimeType(fileName + ".txt"));

        File file = new File("classpath:" + fileName + ".txt");
        InputStreamResource resource = null;

        try {
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            logger.error("Not found created file to save");
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType)
                .contentLength(file.length())
                .body(resource);
    }

    public void openFile(UploadForm uploadForm) {
        String filename = uploadForm.getFileDatas().getOriginalFilename();
     
        File uploadRootDir = new File("classpath:" + filename);

        List<File> uploadedFiles = new ArrayList<File>();
        List<String> failedFiles = new ArrayList<String>();

        if (filename != null && filename.length() > 0) {
            try {
                logger.debug("Copy Information from import file to stream");
                File serverFile = new File(String.valueOf(uploadRootDir));

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(uploadForm.getFileDatas().getBytes());
                stream.close();
                uploadedFiles.add(serverFile);
            } catch (Exception e) {
                logger.error("When copy information from import file to stream");
                failedFiles.add(filename);
            }
        }

        FileInputStream fis = null;
        BufferedReader reader = null;

        try {
            logger.debug("Start upload data from file to application");
            fis = new FileInputStream(uploadRootDir);
            reader = new BufferedReader(new InputStreamReader(fis));
            String line = reader.readLine();
            int lineNumber = 0;
            while (line != null) {
                lineNumber++;
                switch (lineNumber) {
                    case 1:
                        defaultData.getCalculatorMainTables().get(0).setRangePlayer1(line.substring(13));
                        break;
                    case 2:
                        defaultData.getCalculatorMainTables().get(0).setRangePlayer2(line.substring(13));
                        break;
                    case 3:
                        defaultData.getCalculatorMainTables().get(0).setBoard(line.substring(6));
                        break;
                    case 4:
                        defaultData.getCalculatorMainTables().get(0).setEquityPlayer1(line.substring(14));
                        break;
                    case 5:
                        defaultData.getCalculatorMainTables().get(0).setEquityPlayer2(line.substring(14));
                        break;
                    case 6:
                        gameInfo.setEquityByRangeP1(getImportMapData(line, 21, 4, 5));
                        break;
                    case 7:
                        gameInfo.setEquityByRangeP2(getImportMapData(line, 21, 4, 5));
                        break;
                    case 8:
                        gameInfo.setEquityByCardP1(getImportMapData(line, 20, 2, 3));
                        break;
                    case 9:
                        gameInfo.setEquityByCardP2(getImportMapData(line, 20, 2, 3));
                        break;
                }
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            logger.error("Not created temp file in system");
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("When add data from temp file to system");
            e.printStackTrace();
        }catch (NullPointerException e){
            logger.error("Export file is blank");
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
                fis.close();
            } catch (IOException e) {
                logger.error("When close stream from read file");
                e.printStackTrace();
            }
        }
    }

    private Map<String, String> getImportMapData(String line, int cutReadLine, int endKeyData, int startValueData) {
        logger.debug("Import data to right cell");
        Map<String, String> map = new LinkedHashMap<>();
        String n = line.substring(cutReadLine);
        String[] arr = n.substring(1, n.length() - 1).split(", ");

        for (int i = 0; i < arr.length; i++) {
            map.put(arr[i].substring(0, endKeyData), arr[i].substring(startValueData));
        }

        return map;
    }
}
