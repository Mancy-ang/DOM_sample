import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;


public class Project1_dom4j {

	private final static String VAR1 = "ABC"; 
	private final static String VAR2 = "IBM"; 
	private final static String POSTFIX = "_COMP";
	private final static String TARGET_ATTR = "comp_name";
	private final static String TARGET_FILE = "ipo.xml";
	
	public static void main(String[] args) {
		Document dom = load(TARGET_FILE);
		Element root = dom.getRootElement();
		List<Element> purchaseOrderList = root.elements("purchaseOrder");
		
		Document doc1 = DocumentHelper.createDocument();
        Document doc2 = DocumentHelper.createDocument();
        Element root1 = doc1.addElement("purchaseOrders");
        Element root2 = doc2.addElement("purchaseOrders");

        Element e1 = root1.addElement(VAR1+POSTFIX);
        Element e2 = root2.addElement(VAR2+POSTFIX);
        
		for(int i=0;i<purchaseOrderList.size();i++){
			Element e = purchaseOrderList.get(i);
			Element temp ;
			if(e.attributeValue(TARGET_ATTR).equals(VAR1)) {
				temp = (Element) e.clone();
				temp.remove(temp.attribute(TARGET_ATTR));
				e1.add(temp);
			}else{
				temp = (Element) e.clone();
				temp.remove(temp.attribute(TARGET_ATTR));
				e2.add(temp);
			}
		}
		output(doc1,VAR1+POSTFIX+".xml");
		output(doc2,VAR2+POSTFIX+".xml");
	}

	/**
	 * 加载文档
	 * @param filename
	 * @return
	 */
	public static Document load(String filename) {
		Document document = null;
		try {
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(new File(filename)); // 读取XML文件,获得document对象
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return document;
	}

	/**
	 * 创建XML文档
	 * @param document 
	 * @param filename
	 * @return
	 */
	public static boolean output(Document document, String filename) {
		boolean flag = true;
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			File file = new File(filename);
			if(file.exists()){
                file.delete();
                System.out.println(filename+"文件存在，删除成功");
            }  
			XMLWriter writer =new XMLWriter(new FileWriter(file),format);
			writer.write(document);
			writer.flush();
			writer.close();
		} catch (Exception ex) {
			flag = false;
			ex.printStackTrace();
		}
		if(flag){
			System.out.println(filename+"创建成功");
		}else{
			System.out.println(filename+"创建失败");
		}
		return flag;
	}


}
