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
	private static int smallCounter;
	private static long time, currentTime;
	private static Gson gson = new Gson();
	private static int errorCount = 0;	
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
	
	public static MessageEmbed getCoin(String name) {
		
		String json = null;
		try {
			json = getJSON("https://api.coinmarketcap.com/v1/ticker/"+name);		
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		IndividualCoin[] icoin = null;
		MessageEmbed coinDATA = new EmbedBuilder().setTitle("No such coin found!").setDescription("Are you sure you typed it correctly?").setColor(Color.RED).build();
		try {
			icoin = gson.fromJson(json, IndividualCoin[].class); 
		
		
		//coinData =  icoin[0].getName() + "[" + icoin[0].getSymbol() + "]" + ": $" + icoin[0].getPrice_usd();
		
			coinDATA = new EmbedBuilder().setTitle(icoin[0].getName()+"["+ icoin[0].getSymbol() + "]")
				.setDescription("Price in USD:  $"+icoin[0].getPrice_usd() + "\n" +
								"Price in BTC:  "+icoin[0].getPrice_btc() + "\n\n" +
								"Market Cap:  $"+ icoin[0].getMarket_cap_usd() + "\n" +
								"Percent Change in 1h:  "+icoin[0].getPercent_change_1h())
				.setColor(Color.ORANGE)
				.build();
		} catch (Exception e) {
			System.out.println("Error occured on line 75");
			errorCount = 1;
		}
		
		return coinDATA;
		
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
				
		if(e.getMessage().getRawContent().equals("//info")) {			
			e.getChannel().sendMessage(new EmbedBuilder().setTitle("Source Code\n").setDescription("[Github - CryptoAge](https://github.com/rikoudosenin/cryptoage/)").setAuthor("Sage", "https://github.com/rikoudosenin", null).setColor(Color.orange).build()).queue();
		}
	}
	
}
