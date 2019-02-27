package com.manage.footballapi.Util;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.IIOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.rmi.server.ExportException;
import java.util.HashMap;
import java.util.Iterator;

public class ReadJSONFileToImportData {
    public void readJSONFile(){
        JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse(new FileReader("D:\\soccer.json"));

        }catch (FileNotFoundException e){e.printStackTrace();}
        catch (IIOException e){e.printStackTrace();}
        catch (ParseException e){e.printStackTrace();}
        catch (Exception e){e.printStackTrace();}
    }
}
