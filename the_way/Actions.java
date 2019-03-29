import org.jdom2.Element;

public class Actions 
{
	//Use
	public static int Use(String[] req, Element room, Element root, int roomId) 
	{
		int i = 0;
		String use = "";
		String name = "";
		Element elem = null;
		Element scpt = null;
		java.util.List<Element> elements = null;
		
		/*****************/
		
		try
		{
			//try to get a second elements from request
			name = req[1];
			
			try
			{
				if(Inventory.check(name, root) == true)
				{
					elem = Inventory.get(name, root).getChild("use");
				}
				else
				{
					//Get list of items in room
					elements = room.getChildren("item");
				
					//Set i to 0 and search items in room for matching item name
					i = 0;
					while(i<elements.size() && !elements.get(i).getChild("title").getText().equalsIgnoreCase(name))
					{
						i++;
					}
					
					//Get the element at i and get its use 
					elem = elements.get(i).getChild("use");
					scpt = elements.get(i).getChild("uscript");
				}
				
				//If the element existed but there was no use function
				if(scpt != null)
				{
					try
					{
						i();
						int iD = Scripting.run(scpt.getText());
						c();
						if(iD != 0)
						{
							try
							{
								roomId = iD;
							}
							catch(Exception e)
							{
								i();
								System.out.println("There was an unexpected error running a script.");
								c();
							}
						}
					}	
					catch(Exception e)
					{
						//Error if lua file isn't found
						i();
						System.out.println("Script referenced in Game not found. Please make sure scripts are saved to the appropriate directory.");
						c();
					}
				}
				//If the element existed but there was no use function
				else if(elem == null)
				{
					i();
					System.out.println("You cannot use this item.");
					c();
				}
				//If the element exists and there is a use function
				else
				{
					i();
					use = elem.getText();
					System.out.println(use);
					c();
				}
			}
			catch(Exception e)
			{
				//Error for if there was no item of the searched name in room 
				i();
				System.out.println("That item doesn't exist here.");
				c();
			}
		}
		catch(Exception e)
		{
			//Error for null pointer because there was no second element in request
			i();
			System.out.println("You need something after the \"use\" action. You can't use nothing.");
			c();
		}
		
		return roomId;
		
	}//End Use
	
	//Drop
	public static void Drop(String[] req, Element room, Element root)
	{
		String name = "";

		/********************************/
		
		try
		{
			//Try to get a second elements from request
			name = req[1];
			
			//Call inventory to add item
			Inventory.Drop(name, root, room);
		}
		catch(Exception e)
		{
			//Error for null pointer because there was no second element in request
			i();
			System.out.println("You need something after the \"drop\" action.");
			c();
		}
		
	}//End Drop
	
	//Take
	public static void Take(String[] req, Element room, Element root)
	{
		int i = 0;
		String name = "";
		Element elem = null;
		java.util.List<Element> elements = null;

		/********************************/
		
		try
		{
			//try to get a second elements from request
			name = req[1];
			
			try
			{
				//Get list of items in room
				elements = room.getChildren("item");
				
				//Set i to 0 and search items in room for matching item name
				i = 0;
				while(i<elements.size() && !elements.get(i).getChild("title").getText().equalsIgnoreCase(name))
				{
					i++;
				}
				
				//Get the element at i and get its take
				elem = elements.get(i).getChild("take"); 
				
				//If the element existed but there was no take function
				if(elem == null)
				{
					i();
					System.out.println("You cannot take this item.");
					c();
				}
				//If the element exists and there is a take function
				else
				{
					i();
					System.out.println("You take " + elements.get(i).getChild("title").getText());
					
					//Get the element at i and set it to elem
					elem = elements.get(i);
					
					//Call inventory to add item
					Inventory.Add(elem, root);
					c();
				}
			}
			catch(Exception e)
			{
				//Error for if there was no item of the searched name in room 
				i();
				System.out.println("That item doesn't exist here.");
				c();
			}
		}
		catch(Exception e)
		{
			//Error for null pointer because there was no second element in request
			i();
			System.out.println("You need something after the \"take\" action.");
			c();
		}
		
	}//End Take
	
	//Look
	public static int Look(String[] req, Element room, Element root, int roomId) 
	{
		int i = 0;
		String name = "";
		String d = "";
		String title = "";
		Element elem = null;
		Element scpt = null;
		java.util.List<Element> elements = null;

		/*****************/
		
		try
		{
			//try to get a second elements from request
			name = req[1];
			
			try
			{
				if(Inventory.check(name, root) == true)
				{
					elem = Inventory.get(name, root).getChild("d");
				}
				else
				{
					//Get list of items in room
					elements = room.getChildren("item");
					
					//Set i to 0 and search items in room for matching item name
					i = 0;
					while(i<elements.size() && !elements.get(i).getChild("title").getText().equalsIgnoreCase(name))
					{
						i++;
					}
					
					//Get the element at i and get its use 
					elem = elements.get(i).getChild("d");
					scpt = elements.get(i).getChild("lscript");
				}
				
				//If the element existed but there was no look function
				if(scpt != null)
				{
					try
					{
						i();
						int iD = Scripting.run(scpt.getText());
						c();
						if(iD != 0)
						{
							try
							{
								roomId = iD;
							}
							catch(Exception e)
							{
								i();
								System.out.println("Game Error, Something Happened");
								c();
							}
						}
					}	
					catch(Exception e)
					{
						//Error if lua file isn't found
						i();
						System.out.println("Game Error, file required not found ");
						c();
					}
				}
				//If the element existed but there was no look function
				else if(elem == null)
				{
					i();
					System.out.println("You cannot look at this item.");
					c();
				}
				//If the element exists and there is a look function
				else
				{
					//The description of the item
					i();
					d = elem.getText();
					System.out.println(d);
					c();
				}
			}
			catch(Exception e)
			{
				//Error for if there was no item of the searched name in room
				i();
				System.out.println("There is no item of that name in this room.");
				c();
			}
		}
		catch(Exception e)
		{
			try
			{
				//If they just type look with no other text print room stuff
				i();
				title = room.getChild("title").getText();
				d = room.getChild("d").getText();
				System.out.println(title);
				System.out.println(d);
				
				i();
				list(room);
				c();
			}
			catch(Exception et)
			{
				i();
				System.out.println("Could not look at this. Try opening your eyes. (Error)");
				c();
			}
		}
		
		return roomId;
		
	}//End Look
	
	//List of items in the room
	public static void list(Element room)
	{	
		int num = 0;
		String title = "";
		Element elem = null;
		java.util.List<Element> elements = null;
		
		/***********************/
		
		try
		{
			//Get list of items in room
			elements = room.getChildren("item");
			
			//For each item get the title and print it
			for(int i = 0; i < elements.size(); i++)
			{	
				//Get the element at i
				elem = elements.get(i).getChild("title");
				
				//Item number for display
				num++;
				
				//Get and print the title of item at i
				title = (elem.getText());
				System.out.println("Item " + num + ": " + title);
				
			}//END for
		}
		catch(Exception e)
		{
			System.out.println("Error in List in actions");
		}
		
	}//End list
	
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
	
}//End Actions