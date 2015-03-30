package Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author NicolasG
 */
public class MonLogger {

    public static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static MonFormatter format;
    private FileHandler fh = null;
    private ConsoleHandler cons = null;
    private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;

    public MonLogger(int t) {
        try {
            Handler[] hand = Logger.getLogger("").getHandlers();
            for (int i = 0; i < hand.length; i++) {
                hand[i].setLevel(Level.INFO);
                Logger.getLogger("").removeHandler(hand[i]);
            }

            if(t == 0){
                //HTML formatter
                format = new MonFormatter();
                fh = new FileHandler("log/Multichat%g.log.html", 100000, 5, true);
                logger.addHandler(fh);
                fh.setFormatter(format);

                // create a TXT formatter
                fileTxt = new FileHandler("log/Multichat%g.txt", 100000, 5, true);
                formatterTxt = new SimpleFormatter();
                fileTxt.setFormatter(formatterTxt);
                logger.addHandler(fileTxt);
            }else{
                cons = new ConsoleHandler();
                cons.setFormatter(new SimpleFormatter());
                cons.setLevel(Level.CONFIG);
                
                logger.addHandler(cons);
            }
            //logger.setResourceBundle(ResourceBundle.getBundle("Multichat", Locale.CANADA));
            logger.setLevel(Level.CONFIG);
            //logger.config("Lancement du Programme");
            //close();
        } catch (IOException ex) {
            MonLogger.logger.log(Level.SEVERE, ex.getMessage(), ex);
        } catch (SecurityException ex) {
            MonLogger.logger.log(Level.SEVERE, ex.getMessage(), ex);
        } /*finally {
         //  lastly close anything that may be open
         if (fh != null) {
         try {
         fh.close();
         } catch (Exception ex) {
         // error closing   
         }
         }
         }*/

    }

    public void close() {
        if (fh != null) {
            try {
                fh.close();
                fileTxt.close();
            } catch (Exception ex) {
                MonLogger.logger.log(Level.SEVERE, ex.getMessage(), ex);
                // error closing   
            }
        }
    }
}
