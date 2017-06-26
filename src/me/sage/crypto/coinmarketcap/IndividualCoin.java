package me.sage.crypto.coinmarketcap;

public class IndividualCoin {
	private String total_supply;

	private String id;

	private String percent_change_24h;

	private String _24h_volume_usd_;

	private String rank;

	private String symbol;

	private String available_supply;

	private String percent_change_1h;

	private String name;

	private String price_usd;

	private String last_updated;

	private String percent_change_7d;

	private String price_btc;

	private String market_cap_usd;

	public String getTotal_supply() {
		return total_supply;
	}

	public void setTotal_supply(String total_supply) {
		this.total_supply = total_supply;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPercent_change_24h() {
		return percent_change_24h;
	}

	public void setPercent_change_24h(String percent_change_24h) {
		this.percent_change_24h = percent_change_24h;
	}

	public String get24h_volume_usd() {
		return _24h_volume_usd_;
	}

	public void set24h_volume_usd(String _24h_volume_usd_) {
		this._24h_volume_usd_ = _24h_volume_usd_;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getAvailable_supply() {
		return available_supply;
	}

	public void setAvailable_supply(String available_supply) {
		this.available_supply = available_supply;
	}

	public String getPercent_change_1h() {
		return percent_change_1h;
	}

	public void setPercent_change_1h(String percent_change_1h) {
		this.percent_change_1h = percent_change_1h;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice_usd() {
		return price_usd;
	}

	public void setPrice_usd(String price_usd) {
		this.price_usd = price_usd;
	}

	public String getLast_updated() {
		return last_updated;
	}

	public void setLast_updated(String last_updated) {
		this.last_updated = last_updated;
	}

	public String getPercent_change_7d() {
		return percent_change_7d;
	}

	public void setPercent_change_7d(String percent_change_7d) {
		this.percent_change_7d = percent_change_7d;
	}

	public String getPrice_btc() {
		return price_btc;
	}

	public void setPrice_btc(String price_btc) {
		this.price_btc = price_btc;
	}

	public String getMarket_cap_usd() {
		return market_cap_usd;
	}

	public void setMarket_cap_usd(String market_cap_usd) {
		this.market_cap_usd = market_cap_usd;
	}

	@Override
	public String toString() {
		return "ClassPojo [total_supply = " + total_supply + ", id = " + id + ", percent_change_24h = "
				+ percent_change_24h + ", 24h_volume_usd = " + _24h_volume_usd_ + ", rank = " + rank + ", symbol = "
				+ symbol + ", available_supply = " + available_supply + ", percent_change_1h = " + percent_change_1h
				+ ", name = " + name + ", price_usd = " + price_usd + ", last_updated = " + last_updated
				+ ", percent_change_7d = " + percent_change_7d + ", price_btc = " + price_btc + ", market_cap_usd = "
				+ market_cap_usd + "]";
	}
}