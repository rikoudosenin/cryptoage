package me.sage.crypto;

import java.awt.Color;
import java.sql.Timestamp;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class BotListener extends ListenerAdapter {

	
	private static long time, currentTime;
	
	private static String coinName = new String();
	private static String playingNow = new String();
	private static long author = 271888508728246274L;
	private DataFetcher df = new DataFetcher();
	private Timestamp timestamp = null;

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {

		if (e.getMessage().getRawContent().equalsIgnoreCase("//test")) {
			e.getChannel().sendMessage("testing, testing?!").queue();
		}

		if (e.getMessage().getRawContent().startsWith(("//coin"))) {
			timestamp = new Timestamp(System.currentTimeMillis());
			currentTime = (timestamp.getTime() / 1000);
			if ((time + 10) < currentTime) {
				if (e.getMessage().getRawContent().equalsIgnoreCase("//coin")) {
					e.getChannel()
							.sendMessage(new EmbedBuilder().setTitle("Top 10 Crypto Currency List")
									.setDescription(df.getUserData(10)).setColor(Color.MAGENTA)
									.setFooter("Source: Coinmarketcap", null).build())
							.queue();
					Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
					time = (timestamp1.getTime() / 1000);
				} else {
					coinName = e.getMessage().getRawContent().substring(7);
					e.getChannel().sendMessage(df.getCoin(coinName)).queue();
					Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
					time = (timestamp1.getTime() / 1000);
				}
			} else {
				e.getChannel().sendMessage("**" + e.getAuthor().getName() + "**" + ", slow down with the commands!")
						.queue();
			}

		}

		if (e.getMessage().getRawContent().equalsIgnoreCase("//info")) {
			e.getChannel()
					.sendMessage(new EmbedBuilder().setTitle("Source Code\n")
							.setDescription("[Github - CryptoAge](https://github.com/rikoudosenin/cryptoage/)")
							.setAuthor("Sage", "https://github.com/rikoudosenin", null).setColor(Color.orange).build())
					.queue();
		}

		if (e.getMessage().getRawContent().startsWith("//playing")) {
			if (e.getAuthor().getIdLong() == author) {
				playingNow = e.getMessage().getRawContent().substring(10);
				e.getJDA().getPresence().setGame(Game.of(playingNow));
			} else {
				e.getChannel().sendMessage("You don't tell me what to do.").queue();
			}
		}

	}

}
