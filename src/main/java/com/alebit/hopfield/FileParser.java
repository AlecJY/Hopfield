package com.alebit.hopfield;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Alec on 2016/12/10.
 */
public class FileParser {
    public void parseTrainingData(String path) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            ArrayList<String> data = new ArrayList<>();
            int maxWidth = 0;
            int maxHeight = 0;
            int height = 0;
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    if (height > maxHeight) {
                        maxHeight = height;
                    }
                    break;
                } else {
                    data.add(line);
                    if (!line.matches("\\A\\s*\\z")) {
                        int width = line.replaceAll("\\s+$", "").length();
                        if (width > maxWidth) {
                            maxWidth = width;
                        }
                        height++;
                    } else {
                        if (height > maxHeight) {
                            maxHeight = height;
                        }
                        height = 0;
                    }
                }
            }

        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
    }
}
