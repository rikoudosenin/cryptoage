package me.sage.crypto;

import java.awt.Color;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.swing.text.StyleContext.SmallAttributeSet;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import me.sage.crypto.coinmarketcap.COINAPI;
import me.sage.crypto.coinmarketcap.IndividualCoin;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BotListener extends ListenerAdapter {
	
	private static OkHttpClient client = new OkHttpClient();
	private static Color colorr;
	private static int smallCounter;
	private static long time, currentTime;
	private static Gson gson = new Gson();	
	
	public static String getJSON(String url) throws IOException {
		  Request request = new Request.Builder()
		      .url(url)
		      .build();

		  Response response = client.newCall(request).execute();
		  return response.body().string();
		}
	
	public static String getUserData(int limit) {
		String json = null;
				
		try {
			json = getJSON("https://api.coinmarketcap.com/v1/ticker/?limit=" + limit);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		COINAPI[] coinobj = gson.fromJson(json, COINAPI[].class);
		String coinlist = "\n";
		smallCounter=1;
		for(int i=0;i<10;i++) {
			coinlist = coinlist + "**"+smallCounter+".** "+ coinobj[i].getName()+"["+coinobj[i].getSymbol()+"] - $"+coinobj[i].getPrice_usd() + "\t\t\t\t\n\n";
			smallCounter++;
		}
		return coinlist;
	}
	
	public static String getCoin(String name) {
		
		String json = null;
		try {
			json = getJSON("https://api.coinmarketcap.com/v1/ticker/"+name);		
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		String coinData = null;
		
		IndividualCoin[] icoin = gson.fromJson(json, IndividualCoin[].class); 
		
		coinData =  icoin[0].getName() + "[" + icoin[0].getSymbol() + "]" + ": $" + icoin[0].getPrice_usd();
		
		return coinData;
		
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		
		if(e.getMessage().getRawContent().equalsIgnoreCase("//test"))  {			
			e.getChannel().sendMessage("testing, testing?!").queue();
		}
			
		if(e.getMessage().getRawContent().startsWith(("//coin"))) {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			currentTime = (timestamp.getTime()/1000);
			if((time+10)<currentTime) {
				if(e.getMessage().getRawContent().equalsIgnoreCase("//coin")) {	
					e.getChannel().sendMessage(new EmbedBuilder().setTitle("Top 10 Crypto Currency List").setDescription(getUserData(10)).setColor(Color.MAGENTA).setFooter("Source: Coinmarketcap", null).build()).queue();
					Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
					time = (timestamp1.getTime()/1000);
				} else {			
					String coinName = e.getMessage().getRawContent().substring(7);
					e.getChannel().sendMessage(getCoin(coinName)).queue();
					Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
					time = (timestamp1.getTime()/1000);
				}
			} else {
				e.getChannel().sendMessage("**"+e.getAuthor().getName() +"**" + ", slow down with the commands!").queue();
			}
		
		}
				
		if(e.getMessage().getRawContent().equals("//help")) {
			e.getChannel().sendMessage("**Available Command List**\nUse `//<command>` to use a command\n"
					+ "**1.** `//coin` - `Gets information about the top 10 coins`\n"
					+ "**2.** `//coin <coinname>` - `Gets info about the specific coin`\n"
					+ "**3.** `//test` - `something?`\n").queue();
		}
	}
	
}
