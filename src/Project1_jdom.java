import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * @author Mcod
 *
 */
/**
 * @author Mcod
 *
 */
public class Project1_jdom
{
	private final static String VAR1 = "ABC"; 
	private final static String VAR2 = "IBM"; 
	private final static String POSTFIX = "_COMP";
	private final static String TARGET_ATTR = "comp_name";
	private final static String TARGET_FILE = "ipo.xml";
	
    public static void main(String[] args) throws Exception
    {
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(new File(TARGET_FILE));
        Element element = document.getRootElement();

        // 获取子元素
        List<Element> purchaseOrderList = element.getChildren("purchaseOrder");
        Document doc1 = new Document();
        Document doc2 = new Document();
        Element root1 = new Element("purchaseOrders");
        Element root2 = new Element("purchaseOrders");

        Element e1 = new Element(VAR1+POSTFIX);
        Element e2 = new Element(VAR2+POSTFIX);
        
        for(int i=0;i<purchaseOrderList.size();i++){
        	Element e = purchaseOrderList.get(i);
        	Element temp = e;
        	
        	if(e.getAttributeValue(TARGET_ATTR).equals(VAR1)){
        		temp.removeAttribute(TARGET_ATTR);
        		e1.addContent(temp.clone());
        	}else{
        		temp.removeAttribute(TARGET_ATTR);
        		e2.addContent(temp.clone());
        	}
        }
        root1.addContent(e1);
        root2.addContent(e2);
        doc1.addContent(root1);
        doc2.addContent(root2);
        output(doc1, VAR1+POSTFIX+".xml");
        output(doc2, VAR2+POSTFIX+".xml");
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
			//JDOM 格式化字符串
			Format format = Format.getPrettyFormat();  
		    format.setEncoding("UTF-8"); 
			File file = new File(filename);
			if(file.exists()){
				System.out.println(filename+"文件存在，删除成功");
                file.delete();  
            } 
			
			XMLOutputter xo =new XMLOutputter(format);
			FileWriter writer = new FileWriter(file);
			xo.output(document, writer);
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