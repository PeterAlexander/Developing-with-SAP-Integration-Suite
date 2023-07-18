/* Refer the link below to learn more about the use cases of script.
https://help.sap.com/viewer/368c481cd6954bdfa5d0435479fd4eaf/Cloud/en-US/148851bf8192412cba1f9d2c17f4bd25.html

If you want to know more about the SCRIPT APIs, refer the link below
https://help.sap.com/doc/a56f52e1a58e4e2bac7f7adbf45b2e26/Cloud/en-US/index.html */
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.xml.MarkupBuilder;
def Message processData(Message message) {
 //Body 
 def body     = message.getBody();
 def props    = message.getProperties(); 
 def exStacktrace = props.get("exStacktrace");
 def exMessage = props.get("exMessage"); 
 def stringWriter = new StringWriter();
 def exceptionBuiler = new MarkupBuilder(stringWriter); 
 exceptionBuiler.exception {
 exceptionMessage(exMessage)
 stacktrace(exStacktrace)
 } 
 message.setHeader("Content-Type", "application/xml")
 message.setHeader("CamelHttpResponseCode", 500) 
 message.setBody(stringWriter.toString());
 
 def messageLog = messageLogFactory.getMessageLog(message);
 if(messageLog != null) {
 messageLog.addAttachmentAsString("Exception Messages", message.getBody(), "text/plain")
 }
 
 return message;
}


