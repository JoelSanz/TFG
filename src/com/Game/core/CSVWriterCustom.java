package com.Game.core;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class CSVWriterCustom {

    File file;
    CSVWriter writer;
    Writer outputFile;
    String separator = "\t";

    public CSVWriterCustom(String filename){
        this.file = new File("Data/"+filename);
        try {
            this.outputFile = new FileWriter(this.file);
            this.writer = new CSVWriter(this.outputFile, ',', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, "");

            String[] data1 = {"Tower Type", "Location", "Action", "Target Type", "Value", "Round", "\n"};
            writer.writeNext(data1);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeData(String[] data){
            writer.writeNext(data);

    }

    public void closeWriter(){
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
