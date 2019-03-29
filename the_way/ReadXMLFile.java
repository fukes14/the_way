import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jdom2.Element;
import org.jdom2.input.DOMBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.w3c.dom.Document;

public class ReadXMLFile 
{
	 private static Path Game = null;
	 private static org.jdom2.Document original = null;
	 private static String in = "";
	 
   public static org.jdom2.Document Read(String pathToCheck) //Cam changed
   {   
   	   InputStream instream = System.in;
	   Scanner scan = new Scanner(instream);
       org.jdom2.Document jdomDoc = new org.jdom2.Document();
       DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
       String exit = "";
       boolean pass = false;
       
       while (pass == false && !exit.equalsIgnoreCase("yes"))
       {
			try 
			{
				Game = Paths.get(pathToCheck);
				in = pathToCheck; 
				String fileName = Game + "/main.xml";
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(new File(fileName));
				DOMBuilder domBuilder = new DOMBuilder();
				jdomDoc = domBuilder.build(doc);
				pass = true;
			}
			catch (Exception e) 
			{
				System.out.println("Read Error Occured. Make sure you entered the game folder path correctly");
				i();
				System.out.println("Would you like to exit The Way (yes/no):");
				c();
				exit = scan.nextLine();
				if (!exit.equalsIgnoreCase("yes"))
				{
					//Get user folder path
					i();
					System.out.println("Please input the game folder path:");
					c();
					pathToCheck = scan.nextLine();
				}
			}
       }
       
       //Check if Game has a Save File
       original = jdomDoc;
       jdomDoc = ReadXMLFile.SaveCheck(jdomDoc);
       return jdomDoc;
    }
   
   public static org.jdom2.Document SaveCheck(org.jdom2.Document gameFile) 
   {
	   Element elem = null;
	   Element root = null;
       try 
       {
    	   //Check if game has a "Save" Element
    	   root = gameFile.getRootElement();
    	   elem = root.getChild("save");
    	   
    	   //If Save Element Exists, load the Save
    	   if(elem != null)
    	   {
    		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
   			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
   			Document doc = dBuilder.parse(new File(elem.getText()));
   			DOMBuilder domBuilder = new DOMBuilder();
   			gameFile = domBuilder.build(doc);
   			return gameFile;
    	   }
    	   
    	   //No save file, return original GameFile
    	   else
    	   {
    		   return gameFile;
    	   }
       } 
       catch (Exception e) 
       {
       	System.out.println("Game Save Retrival Error Occured");
       }
       
       return gameFile;
       
   }//End SaveCheck
   
   public static void Save(org.jdom2.Document jdomDoc, String[] req)
   {
	   Element elem = null;
	   Element root = null;
	   XMLOutputter xmlOutput = new XMLOutputter();
	   
	   try 
	   {
		   String save = Game + "/" + req[1] + ".xml";
		   String ogame = Game + "/main.xml";
		   xmlOutput.setFormat(Format.getPrettyFormat());
		   xmlOutput.output(jdomDoc, new FileWriter(save));
		   
		   try
		   { 
			   ReadXMLFile.Read(in); 
			   root = original.getRootElement();
			   root.addContent(new Element("save"));
			   root.getChild("save").setText(save);
			   xmlOutput.setFormat(Format.getPrettyFormat());
			   xmlOutput.output(original, new FileWriter(ogame));
			   System.out.println("Game Saved");
		   }
		   catch(Exception e)
		   {
			   System.out.println("Game Save Error, Game File couldn't store save");
		   }
	   }
	   catch(Exception e) 
	   {
	       	System.out.println("Game Save Error, Save Failed");
	   }
	   
   }//End Save
    
    //Add carrot
	private static void c()
	{
		System.out.print(">");
	}
	//Add new line
	private static void i()
	{
		System.out.print("\n");
	}
}