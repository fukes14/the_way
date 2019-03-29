import java.util.Scanner;
import org.jdom2.Element;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.InputStream;

public class Engine 
{
	public static void main(String[] args)
	{
		InputStream instream = System.in;
		Scanner scan = new Scanner(instream);
		String dump;
		
		org.jdom2.Document jdomDoc;
		String[] req = null;
		String title;
		String act = "";
		String entered = "";
		String name = "";
		String d = "";
		String use = "";
		String path = "";
		int id = 2;
		int i = 0;
		Element root = null;
		Element room = null;
		java.util.List<Element> elements = null;
		
		Path Game = null;
		boolean pass = false;
		String exit = "";
		String extra = "";
		
		/*******************************/
		
		//Get user folder path
		i();
		System.out.println("Please input the game folder path:");
		c();
		path = scan.nextLine();
		
		//----Call xml parser to parse the doc to be read and pass in the file path from user-----
		jdomDoc = ReadXMLFile.Read(path);
		
		try
		{
			//Set root to the root element of the file
			root = jdomDoc.getRootElement();
			
			//Set the title, description and ID of the first room.
			title = root.getChild("room").getChild("title").getText();
			d = root.getChild("room").getChild("d").getText();
			id = root.getChild("room").getAttribute("id").getIntValue();
			
			//Ask for first user input
			i();
			System.out.println(root.getChild("title").getText());
			i(); 
			System.out.println(title);
			System.out.println(d);
			c();
			entered = scan.nextLine();
			
			//Split the user entry and put the words into act
			req = entered.split(" ");
			act = req[0];
			
			//While the user doesn't want to exit
			while (!act.equalsIgnoreCase("exit"))
			{	
				try
				{
					//Get list of rooms
					elements = root.getChildren("room");
					
					//Set i to 0 and search rooms for matching id
					i = 0;
					while(i<elements.size() && elements.get(i).getAttribute("id").getIntValue() != id)
					{
						i++;
					}
	
					//Try to set room to the room found if one was found
					room = elements.get(i);
					
				}
				catch(Exception e)
				{
					i();
					System.out.println("Something went wrong, the Room ID# could not be found.");
					c();
				}
				
				try
				{					  
					extra = req[2];   
					i();
					System.out.println("We only recognized " + req[0] + " and " + req[1] + ". Anything after that could not be read.");
				}
				catch(Exception e)
				{
					
				}
				
				/****************Look block*********************/
				if (act.equalsIgnoreCase("look"))
				{	
					Actions.Look(req, room, root, id);
				}
				/****************Inventory block****************/
				else if (act.equalsIgnoreCase("inventory"))
				{
					i();
					Inventory.List(root);
					c();
				}
				/****************Take block****************/
				else if (act.equalsIgnoreCase("take"))
				{
					Actions.Take(req, room, root);
				}
				/****************Drop block****************/
				else if (act.equalsIgnoreCase("drop"))
				{
					Actions.Drop(req, room, root);
				}
				/******************Help*******************/
				else if (act.equalsIgnoreCase("help"))
				{
					i();
					System.out.println("---Available Commands---"
						+ "\nnorth\t\tMove to the North"
						+ "\nsouth\t\tMove to the South"
						+ "\neast\t\tMove to the East"
						+ "\nwest\t\tMove to the West"
						+ "\nlook\t\tLook around you right now"
						+ "\nlook (item)\tLook at an Item in the room" 
						+ "\nuse  (item)\tUse an Item in the room"
						+ "\ndrop (item)\tDrops the Item in the room"
						+ "\ntake (item)\tPuts the Item in your inventory"
						+ "\ninventory  \tLists the items in your inventory"
						+ "\nhelp\t\tView commands"
						+ "\nsave (fileName)\tSaves your progress as the given file name"
						+ "\nexit\t\tExit the game\n");
					c();
				}
				/****************Use block****************/
				else if (act.equalsIgnoreCase("use"))
				{
					Actions.Use(req, room, root, id);
					
					if(instream.available() > 0)
					{
						dump = scan.nextLine();
					}
				}
				/****************Move block****************/
				else if (act.equalsIgnoreCase("west") || act.equalsIgnoreCase("east") || 
						act.equalsIgnoreCase("north") || act.equalsIgnoreCase("south"))
				{
					id = Move.Movement(act, room, root, id);
				}
				/****************Save block****************/
				else if (act.equalsIgnoreCase("save"))
				{
					i();
					ReadXMLFile.Save(jdomDoc, req);
					c();
				}
				else
				{
					i();
					System.out.println("Invalid command. For assistance, type 'help' for a list of usable commands.");
					c();
				}
				
				//Get and parse the next user input
				entered = scan.nextLine();
				req = entered.split(" ");
				act = req[0];
				
			}//END while
			
			//Ending message
			i();
			System.out.println("See you next time.");
			System.out.println("\n\n");
		}
		catch(Exception e)
		{
			//Exception error message
			i();
			System.out.println("A fatal error has occured. The program will now close.");
			System.out.println(e.toString());
			c();
		}
	}
	      
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