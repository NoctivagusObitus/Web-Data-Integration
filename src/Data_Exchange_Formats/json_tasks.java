package Data_Exchange_Formats;

//import org.json.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.*;

public class json_tasks {
	
	public static void xml_to_json(Document doc) throws IOException, XPathExpressionException{
		Gson gson = new Gson();

		BufferedWriter writer = get_writer("tasks.json");
		NodeList list = get_node_list(doc);
		
		for(int i = 0; i < list.getLength(); i++) {
			Node n = (Node) list.item(i);
			NamedNodeMap map = n.getAttributes();
			Map<String, String> values = new HashMap<String, String>();
			
			for (int j = 0; j < map.getLength(); j++) {
				values.put(map.item(j).getNodeName(), map.item(j).getTextContent());
			}
			String jsonString = gson.toJson(values);
			writer.write(jsonString + "\n");
			System.out.println(gson.toJson(values));
		}
		
		writer.close();
	}	
	
    public static BufferedWriter get_writer(String path) throws IOException{
    	BufferedWriter writer = null;
    	writer = new BufferedWriter(new FileWriter(new File(path)));

    	return writer;
    }
    
    public static NodeList get_node_list(Document doc) throws XPathExpressionException{    	
    	XPathFactory xpathFactory = XPathFactory.newInstance();
    	XPath xpath = xpathFactory.newXPath();
    	XPathExpression expr = xpath.compile("//country[encompassed/@continent = 'f0_119' or encompassed/@continent = 'f0_123']");
    	
    	NodeList list = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
    	return list;
    }
}
