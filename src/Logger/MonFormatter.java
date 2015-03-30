/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logger;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.*;

/**
 *
 * @author NicolasG
 */
public class MonFormatter extends Formatter {

    // formatage d'un enregistrement
    @Override
    public String format(LogRecord record) {
        StringBuffer s = new StringBuffer(10000);
        Date d = new Date(record.getMillis());
        DateFormat df = DateFormat.getDateTimeInstance(
                DateFormat.LONG, DateFormat.MEDIUM, Locale.FRANCE);

        s.append("<tr class=" + record.getLevel().toString() + "><td>" + df.format(d) + "</td>");
        s.append("<td>" + record.getLevel() + " </td>");
        s.append("<td>" + record.getSourceClassName() + "</td>");
        s.append("<td>" + record.getSourceMethodName().replace("<", " ").replace(">", " ") + "</td>");

        if (record.getParameters() != null && record.getParameters().length > 0) {
            Exception e = (Exception) record.getParameters()[0];
            StackTraceElement stac[] = e.getStackTrace();
            s.append("<td>" + stac[0].toString() + "</td>");
            for (int i = 0; i < stac.length; i++) {
                System.out.println("n " + i + " " + stac[i].toString());
            }
        }

        s.append("<td>" + formatMessage(record)
                + " l  </td></tr>\n");

        return s.toString();
    }

    // entête du fichier de log
    @Override
    public String getHead(Handler h) {
        return "<html><!DOCTYPE html>\n<head>\n<style type=\"text/css\">\n"
                + "\n"
                + "tr, table,td {\n"
                + "    border: 1px solid black;\n"
                + "	padding = 0px;\n"
                + "	margin = 0px;\n"
                + "	border-collapse: collapse;\n"
                + "}\n"
                + "span{\n"
                + "	padding = 0px;\n"
                + "	margin = 0px;	\n"
                + "}\n"
                + ".SEVERE{\n"
                + "color: #A50000;"
                + "background-color:#895C5C;\n"
                + " font-weight:bold;"
                + "}\n"
                + ".WARNING{\n"
                + "color: #CC4D4D;\n"
                + "}\n"
                + ".INFO{\n"
                + "color: #2685A5;\n"
                + "}\n"
                + ".CONFIG{\n"
                + "color: #8AD2EA;\n"
                + "background-color:#5B7D9B;"
                + "}\n"
                + "\n"
                + " thead { background-color: #363caf;font-weight:bolder;color:#FFFFFF;}"
                + "</style>\n"
                + "<body>\n"
                + "<table width=\"100%\">\n"
                + "<thead>\n"
                + "<tr><td>Date</td><td>Level</td><td>Source</td><td>Methode</td><td>Message</td>\n"
                + "<tbody>\n";
    }

    // fin du fichier de log
    @Override
    public String getTail(Handler h) {
        return "</tbody></table>\n</body>\n</html><br/><br/>\n";
    }
}
