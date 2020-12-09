package tutorial;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

/**
 * @author thanhvt
 * @project jax-ws-server
 */
public class CustomSOAPHandler implements SOAPHandler<SOAPMessageContext> {
    private static Logger logger;

    private final DocumentBuilder db;

    public CustomSOAPHandler() throws ParserConfigurationException {
        super();
        logger = Logger.getLogger(CustomSOAPHandler.class);
        db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    @Override
    public void close(MessageContext arg0) {

    }

    @Override
    public boolean handleFault(SOAPMessageContext arg0) {
        System.out.println(arg0.getMessage());
        return false;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext smc) {
        try {
            System.out.println("Handling soap message...");
            File responseFile = new File("F:\\response.xml");
            if (!responseFile.exists()) {
                boolean success = responseFile.createNewFile();
                System.out.println("Response file has been created " + (success ? "successfully!" : "failed!"));
            }
//            Map<String, List<String>> map = (Map<String, List<String>>) smc.get(MessageContext.HTTP_REQUEST_HEADERS);
            Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

            if (outboundProperty) {
                logger.debug("\nOutbound message:");
                //this is underlying http response object
                HttpServletResponse response = (HttpServletResponse) smc.get(MessageContext.SERVLET_RESPONSE);
                response.addHeader("Access-Control-Allow-Origin", "*");
                response.addHeader("Access-Control-Allow-Credentials", "false");
                response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, OPTIONS, DELETE");
                response.setHeader("Access-Control-Max-Age", "3600");
                response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, x-requested-with, Content-Type, SOAPAction, Access-Control-Allow-Headers, Access-Control-Response-Headers, Access-Control-Allow-Methods, Access-Control-Allow-Origin");

                FileOutputStream fileOutputStream = new FileOutputStream("F:\\response.xml");
//                smc.getMessage().writeTo(fileOutputStream);
                SOAPEnvelope envelope = smc.getMessage().getSOAPPart().getEnvelope();
                String xmlString = this.convertSoapEnvelopeToString(envelope);
                System.out.println(xmlString);
//                Document document = this.convertStringToDom(xmlString);
//                this.writeDomToOutput(document, fileOutputStream);
            } else {
                logger.debug("\nInbound message:");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    public String convertSoapEnvelopeToString(SOAPEnvelope soapEnvelope) {
        DOMImplementationLS lsImpl = (DOMImplementationLS) soapEnvelope.getOwnerDocument().getImplementation().getFeature("LS", "3.0");
        LSSerializer serializer = lsImpl.createLSSerializer();
        serializer.getDomConfig().setParameter("xml-declaration", false); //by default its true, so set it to false to get String without xml-declaration
        return serializer.writeToString(soapEnvelope);
    }

    public Document convertStringToDom(String xmlString) throws IOException, SAXException {
        return db.parse(new ByteArrayInputStream(xmlString.getBytes()));
    }

    public void writeDomToOutput(Document document, OutputStream outputStream) throws IOException {
//        OutputFormat format = new OutputFormat(document);
//        format.setIndenting(true);
//        XMLSerializer serializer2 = new XMLSerializer(outputStream, format);
//        serializer2.serialize(document);
    }

}