package com.mango.edi.clm.parser;

import com.berryworks.edireader.EDIReader;
import com.berryworks.edireader.util.XmlFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@Slf4j
public class EDIToCSVParsingEngine {


    @Autowired
    EDIReader ediReader;

    @Value("${input.clim.file.path}")
    private String inputClimFilePath;

    @Value("${output.xml.file.path}")
    private String outputXmlPath;
    @Value("${output.csv.file.path}")
    private String outputCsvPath;
    @Value("${is.indenting.enabled}")
    private boolean isIndentingEnabled;


    public void execute() {
        try {
            // list all files in the directory
            Files.list(Paths.get(inputClimFilePath)).forEach(path -> {
                if (path.toString().endsWith(".clm")) {
                    log.info("Processing file: {}", path);
                    try {
                        processFile(path.toString());
                    } catch (TransformerException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            });

        } catch (IOException e) {
            log.error("Error while processing file: {}", e.getMessage(), e);
        }
    }


    private void processFile(String filePath) throws TransformerException, IOException {
        //read file to string
        Reader reader = createInputReader(filePath);
        // Establish the SAXSource
        //get the file name from file path
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf("."));
        SAXSource source = new SAXSource(ediReader, new InputSource(reader));
        // Create a writer for the output file
        var writer = createWriter(outputXmlPath + fileName + ".xml");
        // Use a StreamResult to capture the generated XML output
        StreamResult result = new StreamResult(writer);
        // Use a Transformer to generate XML output from the parsed input
        TransformerFactory.newInstance().newTransformer().transform(source, result);
        reader.close();
        writer.close();

    }


    private Reader createInputReader(String inputFileName) {
        Reader inputReader;
        try {
            inputReader = new InputStreamReader(
                    new FileInputStream(inputFileName), StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        return inputReader;
    }

    private Writer createWriter(String outputFileName) {
        Writer generatedOutput;
        try {
            generatedOutput = new OutputStreamWriter(new FileOutputStream(
                    outputFileName), StandardCharsets.ISO_8859_1);
            System.out.println("Output file " + outputFileName + " opened");
            if (isIndentingEnabled) {
                // Wrap the Writer for the generated output with an indenting filter
                generatedOutput = new XmlFormatter(generatedOutput);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        return generatedOutput;
    }


}
