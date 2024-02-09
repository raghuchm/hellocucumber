package Datareader;
import java.io.File;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Propertyreader
{
	static FileReader fileReader=null;
	static FileOutputStream outputStrem=null;
    static Properties properties;

    public String getPropertyFileData(String key) throws IOException {
            String keyVal="None";
            try {
                fileReader = new FileReader("src/test/resources/testdata.properties");
                properties = new Properties();
                properties.load(fileReader);
                keyVal=properties.get(key).toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        finally {
            fileReader.close();
        }
        return keyVal;
    }

 public void setPropertyFileData(String key,String value)
 {
	 properties.put(key,value);
	 try {
		outputStrem = new FileOutputStream("src/test/resources/token.properties");
		properties.store(outputStrem,"token updated");
		
	} catch (IOException e) 
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 }

}
