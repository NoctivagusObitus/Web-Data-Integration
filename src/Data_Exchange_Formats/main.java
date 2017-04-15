package Data_Exchange_Formats;

import java.io.File;
import java.io.IOException;

import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class main {
	public static void main(String argv[]) throws XPathExpressionException, IOException{		

		//xml_tasks();
		json_tasks();
	}
	
	public static void xml_tasks(){
		xml_tasks xml_tasks = new xml_tasks();
		
		String x_path_expr = "";
		
		//task 2.1
		//getting example xml file from harddrive
		Document doc = xml_tasks.get_doc("./mondial-3.0.xml");
		
		//task 2.2
		//x_path_expr = "/mondial/*";
		//xml_tasks.duplicat_free_attributes(doc, x_path_expr);
				
		//task 2.3
		/*
		we are looking for continents with id = "f0_119" because the the id of the continent "europe"
		extract from XML: <continent id="f0_119" name="Europe"/>		
		
		get all country nodes which have an child node (encompassed) which has an attribute with value "f0_119"
		*/
		//x_path_expr = "//country[encompassed/@continent = 'f0_119']";
		//xml_tasks.get_countries_by_continent(doc, x_path_expr, false);
		
		//task 2.4
		/*
		same as task 2.3 but adding aisa (id = "f0_123") via an "or" statement in xpath
		*/
		//x_path_expr = "//country[encompassed/@continent = 'f0_119' or encompassed/@continent = 'f0_123']";
		//xml_tasks.get_countries_by_continent(doc, x_path_expr, false);
		
		//task 2.5
		/*
		same as task 2.4 but printing all all attributes
		*/
		x_path_expr = "//country[encompassed/@continent = 'f0_119' or encompassed/@continent = 'f0_123']";
		xml_tasks.get_countries_by_continent(doc, x_path_expr, true);
	}

	public static void json_tasks() throws IOException, XPathExpressionException{
		json_tasks json_tasks = new json_tasks();
		xml_tasks xml_tasks = new xml_tasks();

		Document doc = xml_tasks.get_doc("./mondial-3.0.xml");
		XPathExpression expr = xml_tasks.get_xpath("//country[encompassed/@continent = 'f0_119' or encompassed/@continent = 'f0_123']");
		NodeList list = xml_tasks.execute_xpath(doc, expr);
		
		json_tasks.xml_to_json(doc);
		
	}
}
