import java.io.File;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;

public class ChatBotExample {

	public static final boolean TRACE_MODE=false;
	public static void main(String[] args){
		try{
			String resourcePath = getResourcePath();
			System.out.println(resourcePath);
			
			MagicBooleans.trace_mode = TRACE_MODE;
			Bot bot = new Bot("super", resourcePath);
			Chat chatSession = new Chat(bot);
			bot.brain.nodeStats();
			String text_line = "";
			
			while(true){
				System.out.println("Human:");
				text_line=IOUtils.readInputTextLine();
				if((text_line == null) || (text_line.length()<1)){
					text_line=MagicStrings.null_input;
				}
				if(text_line.equals("q")){
					System.exit(0);
				}
				else if(text_line.equals("wq")){
					bot.writeQuit();
					System.exit(0);
				}else{
					String request  = text_line;
					if(MagicBooleans.trace_mode)
						System.out.println("STATE="+request+":THAT="+((History)chatSession.thatHistory.get(0).get(0))
								+":TOPIC="+chatSession.predicates.get("topic"));
					
					String response = chatSession.multisentenceRespond(request);
					while(response.contains("&lt;"))
						response = response.replace("&lt;", "<");
					while(response.contentEquals("&gt;"))
						response = response.replace("&gt;",">");
					System.out.println("Robot :"+response);
				}
					
					
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static String getResourcePath(){
		File currDir =new File(".");
		
		String path = currDir.getAbsolutePath();
		path= path.substring(0, path.length()-2);
		System.out.println(path);
		String resourcePath = 	path+File.separator+"src"
				+File.separator+"main"+File.separator+"resources";
		
		return resourcePath;
	}
}
