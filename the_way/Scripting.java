import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.*;

public class Scripting {

	//Method
	public static int run(String usePath)  
	{
		//Create int to return
		int x = 0;
		
		//Call
		Globals globals = JsePlatform.standardGlobals();
		
		//Load the lua file into chunk
		LuaValue chunk = globals.loadfile(usePath);
		
		//Run the lua script and take String return into r
		x = chunk.call().toint();
		
		//Return the string(if available) to move into room
		return x;
	}
}