package tutorial;
//
//import com.sun.org.apache.xml.internal.serialize.OutputFormat;
//import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.*;
import java.util.Set;

/**
 * @author thanhvt
 * @project jax-ws-demo
 */
public class CustomSOAPHandler implements SOAPHandler<SOAPMessageContext> {

    private final DocumentBuilder db;

    public CustomSOAPHandler() throws ParserConfigurationException {
        super();
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
            File responseFile = new File("F:\\request.xml");
            if (!responseFile.exists()) {
                boolean success = responseFile.createNewFile();
                System.out.println("Response file has been created " + (success ? "successfully!" : "failed!"));
            }
            boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY); // check soap request
            if (outboundProperty) {
                SOAPEnvelope envelope = smc.getMessage().getSOAPPart().getEnvelope();
                FileOutputStream fileOutputStream = new FileOutputStream("F:\\request.xml");
                String xmlString = this.convertSoapEnvelopeToString(envelope);
                System.out.println(xmlString);
                Document document = this.convertStringToDom(xmlString);
                this.writeDomToOutput(document, fileOutputStream);
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