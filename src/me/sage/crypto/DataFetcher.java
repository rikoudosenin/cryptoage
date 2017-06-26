package me.sage.crypto;

import java.awt.Color;
import java.io.IOException;

import com.google.gson.Gson;

import me.sage.crypto.coinmarketcap.COINAPI;
import me.sage.crypto.coinmarketcap.IndividualCoin;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DataFetcher {

	private static OkHttpClient client = new OkHttpClient();
	private static Gson gson = new Gson();
	private static int smallCounter; // TOP 10 List counter

	public static String getJSON(String url) throws IOException {
		Request request = new Request.Builder().url(url).build();

		Response response = client.newCall(request).execute();
		return response.body().string();
	}

	public String getUserData(int limit) {
		String json = null;

		try {
			json = getJSON("https://api.coinmarketcap.com/v1/ticker/?limit=" + limit);
		} catch (IOException e) {
			e.printStackTrace();
		}

		COINAPI[] coinobj = gson.fromJson(json, COINAPI[].class);
		String coinlist = "\n";
		smallCounter = 1;
		for (int i = 0; i < 10; i++) {
			coinlist = coinlist + "**" + smallCounter + ".** " + coinobj[i].getName() + "[" + coinobj[i].getSymbol()
					+ "] - $" + coinobj[i].getPrice_usd() + "\t\t\t\t\n\n";
			smallCounter++;
		}
		return coinlist;
	}

	public MessageEmbed getCoin(String name) {

		String json = null;

		try {
			json = getJSON("https://api.coinmarketcap.com/v1/ticker/" + name);

		} catch (IOException e) {
			e.printStackTrace();
		}
		IndividualCoin[] icoin = null;
		MessageEmbed coinDATA = new EmbedBuilder().setTitle("No such coin found!")
				.setDescription("Are you sure you typed it correctly?").setColor(Color.RED).build();
		try {
			icoin = gson.fromJson(json, IndividualCoin[].class);

			coinDATA = new EmbedBuilder().setTitle(icoin[0].getName() + "[" + icoin[0].getSymbol() + "]")
					.setDescription("Price in USD:  $" + icoin[0].getPrice_usd() + "\n" + "Price in BTC:  "
							+ icoin[0].getPrice_btc() + "\n\n" + "Market Cap:  $" + icoin[0].getMarket_cap_usd() + "\n"
							+ "Percent Change in 1h:  " + icoin[0].getPercent_change_1h())
					.setColor(Color.ORANGE).build();
		} catch (Exception e) {
			System.out.println("Error occured on line 75");
		}

		return coinDATA;

	}
}
