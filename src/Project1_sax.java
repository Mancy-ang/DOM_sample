import java.io.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import org.xml.sax.helpers.DefaultHandler;

public class Project1_sax extends DefaultHandler {
	
	public static void main(String args[]) {
		Project1_sax obj = new Project1_sax();
		obj.childLoop("ipo.xml");
	}

	public void childLoop(String uri) {
		DefaultHandler saxHandler = this;
		SAXParserFactory saxFactory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = saxFactory.newSAXParser();
			saxParser.parse(new File(uri), saxHandler);
		} catch (Throwable t) {
		}
	}
	
	public void startElement(String uri, String localName,
			String qualifiedName, Attributes attributes) {
		if (attributes != null) {
			int numberAttributes = attributes.getLength();
			for (int loopIndex = 0; loopIndex < numberAttributes; loopIndex++) {
				System.out.println(attributes.getQName(loopIndex)+"=\""+attributes.getValue(loopIndex)+"\"");
			}
		}
	}
}
