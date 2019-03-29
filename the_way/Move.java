import org.jdom2.Element;

public class Move 
{	
	public static int Movement(String act, Element room, Element root, int id)
	{
		int i = 0;
		String name = "";
		String d = "";
		String title = "";
		Element elem = null;
		java.util.List<Element> elements = null;

		/********************************/
		
		try
		{
			//Get the element of the direction we are going to
			elem = room.getChild(act);

			//Get the id of the room to move to
			id = Integer.parseInt(elem.getText());
			
			try
			{
				//Get list of rooms
				elements = root.getChildren("room");
				
				//Find the room with the id we are searching for
				i = 0;
				while(i<elements.size() && elements.get(i).getAttribute("id").getIntValue() != id)
				{
					i++;
				}

				//Try to set room to the room found if one was found
				try
				{
					room = elements.get(i);	
					title = room.getChild("title").getText();
					d = room.getChild("d").getText();
				
					i();
					System.out.println(title);
					System.out.println(d);
					c();
				}
				catch(Exception e)
				{
					i();
					System.out.println("The author made their game wrong. There is no room to the " + act);
					c();
				} 
			}
			catch(Exception e)
			{
				i();
				System.out.println("Coders did something wrong. The room id was not found.");
				c();
			} 
			
		}
		catch(Exception e)
		{
			//Error for there is no option for that direction
			i();
			System.out.println("There is no where to go to the " + act);
			c();
		}
		
		//Return the id of the room you should be in
		return id;
		
	}//END Move
	
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