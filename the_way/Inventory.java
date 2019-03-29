import java.util.LinkedList;
import org.jdom2.Element;

public class Inventory
{	
	//List the items in inventory
	public static void List(Element root)
	{
		//Holds the list of elements
		java.util.List<Element> list = null;
		
		/*********************************/
		
		//If there are no items in inventory
		if (root.getChild("inventory") != null && root.getChild("inventory").getChildren().size() > 0)
		{
			//Set list to the list of elements in inventory
			list = root.getChild("inventory").getChildren();
			
			//For all the elements
			for(int i = 0; i < list.size(); i++)
			{
				//Print out the elements title at i
				System.out.println("Item " + (i + 1) + ": " + list.get(i).getChild("title").getText());
			}
		}
		else
		{
			//Tell the user there are no items in the inventory
			System.out.println("There are no items in your inventory");
		}
	}
	
	//Find the item by name and return the element
	public static Element get(String name, Element root)
	{
		Element result = null;
		
		/*************************************/
		
		//If there is at least one element in inventory
		if (root.getChild("inventory") != null)
		{
			//For all elements
			for(int i = 0; i < root.getChild("inventory").getChildren().size(); i++)
			{
				//If the name that is searched for is found
				if(root.getChild("inventory").getChildren().get(i).getChild("title").getText().equalsIgnoreCase(name))
				{
					//Set result to the found element
					result = root.getChild("inventory").getChildren().get(i);
				}
			}
		}
		
		//Return the element if found and null if not found
		return result;
	}
	
	//Check to see if the item in in the inventory
	public static boolean check(String name, Element root)
	{
		boolean result = false;
		
		//If there is at least one element in inventory
		if (root.getChild("inventory") != null)
		{
			//For all elements
			for(int i = 0; i < root.getChild("inventory").getChildren().size(); i++)
			{
				//If the name that is searched for is found
				if(root.getChild("inventory").getChildren().get(i).getChild("title").getText().equalsIgnoreCase(name))
				{
					//Set result to true
					result = true;
				}
			}
		}
		
		//Return true if found and false if not
		return result;
	}
	
	//Adds an item to the inventory in the xml
	public static void Add(Element elem, Element root)
	{
		//Remove item from xml
		elem.getParentElement().removeContent(elem);
		
		//Initialize check to null
		Element check = null;
		
		//Try to get the child inventory
		check = root.getChild("inventory");
		
		//If check is null then inventory isnt there
		if(check == null)
		{
			//Make check an element inventory
			check = new Element("inventory");
			
			//Add check to xml
			root.addContent(check);
		}
		
		//Add item to the inventory
		root.getChild("inventory").addContent(elem);
	}
	
	//Drop
	public static void Drop(String name, Element root, Element room)
	{
		Element elem = null;
		boolean found = false;
		
		/*************************************/
		
		//If there is at least one element in inventory
		if (root.getChild("inventory") != null && root.getChild("inventory").getChildren().size() > 0)
		{
			//For all elements
			for(int i = 0; i < root.getChild("inventory").getChildren().size(); i++)
			{
				//If the name that is searched for is found
				if(root.getChild("inventory").getChildren().get(i).getChild("title").getText().equalsIgnoreCase(name))
				{
					//Set elem to the found element
					elem = root.getChild("inventory").getChildren().get(i);
					found = true;
				}
			}
		
			if(found == false)
			{
				//Error for if there was no item of the searched name in inventory 
				i();
				System.out.println("There are no items of that name in your inventory.");
				c();
			}
			else
			{	
				//Remove item from inventory
				root.getChild("inventory").removeContent(elem);
				
				//Drop item in the room
				room.addContent(elem);
				
				i();
				System.out.println("You drop " + elem.getChild("title").getText());
				c();
			}
		}
		else
		{
			//Else if there was no items in your inventory 
			i();
			System.out.println("There are no items in your inventory.");
			c();
		}
		
	}//END Drop
	
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