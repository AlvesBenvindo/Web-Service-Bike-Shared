package ao.uan.fc.cc4.bikeshared.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class Utils {

    public static Integer extractInteger (String string) {
        String number="";
        for (int i = 0; i < string.length(); i++)
            if (isNumeric(string.charAt(i))) number+=string.charAt(i);
        return toInteger(number);
    }

    private static boolean isNumeric(char c){
        return (c>='0' && c<='9')? true:false;
    }

    public static Integer toInteger (String string) {
        Integer number = 0;
        switch (string.charAt(0)) {
            case '0':;
            case '1': number = 1; break;
            case '2': number = 2; break;
            case '3': number = 3; break;
            case '4': number = 4; break;
            case '5': number = 5; break;
            case '6': number = 6; break;
            case '7': number = 7; break;
            case '8': number = 8; break;
            case '9': number = 9; break;
        }
        for (int i = 1; i < string.length(); i++) {
            switch (string.charAt(i)) {
                case '0': number = (number*10) + 0; break;
                case '1': number = (number*10) + 1; break;
                case '2': number = (number*10) + 2; break;
                case '3': number = (number*10) + 3; break;
                case '4': number = (number*10) + 4; break;
                case '5': number = (number*10) + 5; break;
                case '6': number = (number*10) + 6; break;
                case '7': number = (number*10) + 7; break;
                case '8': number = (number*10) + 8; break;
                case '9': number = (number*10) + 9; break;
            }
        }
        return number;
    }
    
    public String getInstante () {    
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        System.out.println(dtf.format(now));  
        return dtf.format(now);
    }

}
