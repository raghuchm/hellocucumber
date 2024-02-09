
package utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyfileData
{
   static FileReader fileReader = null;
    static Properties properties;

    public static String getPropertyFileData(String key) throws IOException {
            String keyVal="None";
            try {
                fileReader = new FileReader("src/test/java/resource/testdata.properties");
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

}
