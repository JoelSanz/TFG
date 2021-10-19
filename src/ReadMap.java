import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ReadMap {
    FileInputStream file;
    InputStreamReader reader;
    Scanner scanner;

    public ReadMap(Map map){
        getMap("Maps/Map2/Map2.level", map);
    }
    public Map getMap(String filename, Map map){
        try{
            file = new FileInputStream(filename);
            reader = new InputStreamReader(file);
            scanner = new Scanner(reader);
            int[][] mapRead= new int[22][18];
            int x = 0, y = 0;

            while(scanner.hasNext()){
                mapRead[x][y] = scanner.nextInt();

                if(x < 22 - 1)
                    x++;
                else{
                    y++;
                    x=0;
                }
            }
            map.setMap(mapRead);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }
}
