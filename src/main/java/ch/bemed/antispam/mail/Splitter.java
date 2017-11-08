package ch.bemed.antispam.mail;


import ch.bemed.antispam.Loader;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Splitter {
    //overriding an interface directly
    Loader<InputStream> txtLoader = new Loader<InputStream>() {
        public InputStream load() {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("text/spam.txt").getFile());
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
    };

    public List<String> split(){
        List<String> mails = new ArrayList<String>();
        InputStream txtStream = this.txtLoader.load();
        BufferedReader br = new BufferedReader(new InputStreamReader(txtStream));
        StringBuilder allLines = new StringBuilder();
        String  line = "";
        try {
            while ((line = br.readLine()) != null) {
                allLines.append(line);
                allLines.append(System.lineSeparator());
//                if (line.matches("\\s+")) {
//                if (line.matches("[ \\t\\r\\n\\f]+")) {
                //TODO
                //line.matches Vergleichsausdruck überprüfen; nicht immer letzte Zeile
                if (line.matches("X-MS-Exchange-Organization-AuthAs:.*")) {
                    mails.add(allLines.toString());
                    allLines = new StringBuilder();
                } else {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mails;
    }
}
