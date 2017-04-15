package Data_Exchange_Formats;

import java.io.File;

import javax.xml.xpath.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.util.*;

public class xml_tasks {
	public static void main(String argv[]){		
		String x_path_expr = "";
		
		//task 2.1
		File file = new File("./mondial-3.0.xml");
		Document doc = parse_xml_file(file);	
		doc.getDocumentElement().normalize();
		
		//task 2.2
		//x_path_expr = "/mondial/*";
		//duplicat_free_attributes(doc, x_path_expr);
				
		//task 2.3
		/*
		we are looking for continents with id = "f0_119" because the the id of the continent "europe"
		extract from XML: <continent id="f0_119" name="Europe"/>		
		
		get all country nodes which have an child node (encompassed) which has an attribute with value "f0_119"
		*/
		//x_path_expr = "//country[encompassed/@continent = 'f0_119']";
		//get_countries_by_continent(doc, x_path_expr, false);
		
		//task 2.4
		/*
		same as task 2.3 but adding aisa (id = "f0_123") via an "or" statement in xpath
		*/
		//x_path_expr = "//country[encompassed/@continent = 'f0_119' or encompassed/@continent = 'f0_123']";
		//get_countries_by_continent(doc, x_path_expr, false);
		
		//task 2.5
		/*
		same as task 2.4 but printing all all attributes
		*/
		x_path_expr = "//country[encompassed/@continent = 'f0_119' or encompassed/@continent = 'f0_123']";
		get_countries_by_continent(doc, x_path_expr, true);
	}
	
	public static void get_countries_by_continent(Document doc, String expr, boolean with_attributes){
		// get list of all nodes matching xpath
		XPathExpression xpath = get_xpath(expr);
		NodeList nodes = execute_xpath(doc, xpath);
		
		// loop all nodes
		for(int i = 0; i < nodes.getLength(); i++){
			
			if(with_attributes){
				// get all the attributes of the current node (item[i])
				NamedNodeMap attr = nodes.item(i).getAttributes();
				
				// loop through all the attributes
				for(int j = 0; j < attr.getLength(); j++){
					//get the name of the attribute
					String name = attr.item(j).getNodeName();
					// get the value of the attribute
					String value = attr.item(j).getNodeValue();
					
					System.out.println("Attribut Name: " + name + " \twith Value: " + value);
				}
			}else{
				// task 2.3 and 2.4
				
				// for validation get the continent attribute of the encompassed node
				// cast the current node to "Element" so that specific properties of the object are accessible
				Element current = (Element) nodes.item(i);
				// get the continent attribute in a static way (expecting always one encompassed child node with one attribute)
				String continent = current.getElementsByTagName("encompassed").item(0).getAttributes().getNamedItem("continent").getNodeValue().toString();
				String country = current.getAttribute("name");
				
				System.out.println("country: " + country + " \tin continent: " +  continent);			
			}
		}
	}
	
	public static void duplicat_free_attributes(Document doc, String expr_str){
		List<String> attributes = new ArrayList<String>();		
		NodeList nodes = null;
		
		XPathExpression expr = get_xpath(expr_str);		
		
		try{
			nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

		}catch(Exception e){
			e.printStackTrace();
		}
				
		for(int i = 0; i < nodes.getLength(); i++){
			Node current = nodes.item(i);
			NamedNodeMap attr = current.getAttributes();
			
			if(null != attr){
				for(int j = 0; j < attr.getLength(); j++){
					String attribut = attr.item(j).getNodeName().toString();

					if(!exists_in_array(attributes, attribut)){
						attributes.add(attribut);
					}
				}
			}	

		}
		print_array(attributes);
	}
	
	public static boolean exists_in_array(List<String> arr, String text){
		boolean found = false;
		
		for(int i=0; i<arr.size(); i++){
			if(arr.get(i) == text){
				found = true;
				break;
			}
			//System.out.println(attr.item(j).getNodeName());
		}
		
		return found;
	}

	public static Document parse_xml_file(File file){
		Document doc = null;
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(file);
		}catch(Exception e){
			e.printStackTrace();
		}
		return doc;

	}

	public static NodeList execute_xpath(Document doc, XPathExpression expr){
		NodeList nodes = null;
		try{
			nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		}catch(Exception e){
			e.printStackTrace();
		}
		return nodes;
	}
	
	public static XPathExpression get_xpath(String x_path_expr){
		XPathExpression expr = null;
		try{
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xpath = xPathFactory.newXPath();
			expr = xpath.compile(x_path_expr);
		}catch(Exception e){
			e.printStackTrace();
		}
		return expr;
	}
	
	public static void print_array(List<String> arr){
		for(int i = 0; i < arr.size(); i++){
			System.out.println(arr.get(i));
		}
	}
	
	public static void print_node_map(NamedNodeMap arr){
		for(int i = 0; i < arr.getLength(); i++){
			System.out.println(arr.item(i));
		}
	}
	
	
}
