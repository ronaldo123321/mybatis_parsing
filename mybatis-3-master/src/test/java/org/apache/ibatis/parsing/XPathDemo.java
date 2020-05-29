package org.apache.ibatis.parsing;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;

/**
 * @author ronaldo
 * @date 2020/5/8 11:57
 * @description
 */
public class XPathDemo {


  public static void main(String[] args) {

    try {
      File file = new File("D:\\workspace\\mybatis-3\\mybatis-3-master\\src\\test\\java\\org\\apache\\ibatis\\parsing\\input.txt");
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = factory.newDocumentBuilder();

      Document document = dBuilder.parse(file);
      document.getDocumentElement().normalize();

      XPath xPath = XPathFactory.newInstance().newXPath();
      String expression = "/class/student";
      NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
      for(int i = 0; i < nodeList.getLength(); i++){
        Node node = nodeList.item(i);
        System.out.println("\n Current Element is :"+node.getNodeName());
        if(node.getNodeType() ==  Node.ELEMENT_NODE){
            Element element = (Element) node;
            System.out.println("Student roll no is :" + element.getAttribute("rollno"));
            System.out.println("First Name: " + element.getElementsByTagName("firstname").item(0).getTextContent());
            System.out.println("Last Name: " + element.getElementsByTagName("lastname").item(0).getTextContent());
            System.out.println("nickName: " + element.getElementsByTagName("nickname").item(0).getTextContent());
            System.out.println("Marks: " + element.getElementsByTagName("marks").item(0).getTextContent());
        }
      }


    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (XPathExpressionException e) {
      e.printStackTrace();
    }
  }
}
