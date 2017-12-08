import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSException;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

public class Project1_dom {
	private final static String VAR1 = "ABC"; 
	private final static String VAR2 = "IBM"; 
	private final static String POSTFIX = "_COMP";
	private final static String TARGET_ATTR = "comp_name";
	private final static String TARGET_FILE = "ipo.xml";
	
	public static void main(String[] args) {
			Document document = load(TARGET_FILE);
			Document doc1 = load("");
			Document doc2 = load("");

			NodeList nList = document.getElementsByTagName("purchaseOrder");
			Element root1 = doc1.createElement("purchaseOrders");
			Element root2 = doc2.createElement("purchaseOrders");
			Element ABC_ele = doc1.createElement(VAR1+POSTFIX);
			Element IBM_ele = doc2.createElement(VAR2+POSTFIX);

			for (int i = 0; i < nList.getLength(); i++) {
				Element e = (Element) nList.item(i);
				if (e.getAttribute(TARGET_ATTR).equals(VAR1)) {
					Element temp = (Element) doc1.importNode(e, true);
					temp.removeAttribute(TARGET_ATTR);
					ABC_ele.appendChild(temp);
				} else {
					Element temp = (Element) doc2.importNode(e, true);
					temp.removeAttribute(TARGET_ATTR);
					IBM_ele.appendChild(temp);
				}
			}
			root1.appendChild(ABC_ele);
			root2.appendChild(IBM_ele);
			doc1.appendChild(root1);
			doc2.appendChild(root2);

			output(doc1, VAR1+POSTFIX+".xml");
	        output(doc2, VAR2+POSTFIX+".xml");
	}
	
	/**
	 * 加载文档
	 * @param filename
	 * @return
	 */
	public static Document load(String filename) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbuilder = dbFactory.newDocumentBuilder();
			Document document;
			if(!"".equals(filename)){
				document = dbuilder.parse(filename);	
			}else{
				document = dbuilder.newDocument(); 
			}
			return document;
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 创建XML文档
	 * @param document
	 * @param fileName
	 */
	public static void output(Document document,String fileName){
		File file = new File(fileName);
		if(file.exists()){
			file.delete();
			System.out.println(file.getName()+"文件存在，删除成功");
		}
		try {
			DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
			DOMImplementationLS domILS = (DOMImplementationLS) registry.getDOMImplementation("LS");
			LSSerializer lsSerializer = domILS.createLSSerializer();
			lsSerializer.getDomConfig().setParameter("format-pretty-print", true);
			LSOutput out = domILS.createLSOutput();
			out.setEncoding("UTF-8");
			FileWriter fileWriter = new FileWriter(file);
			out.setCharacterStream(fileWriter);
			lsSerializer.write(document, out);
			System.out.println(fileName+"创建成功");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | ClassCastException | DOMException
				| LSException | IOException e) {
			System.out.println(fileName+"创建失败");
			e.printStackTrace();
		}
	}
}
